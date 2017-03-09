package android.example.com.quizapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.SharedPreferences;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.content.res.Resources;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itternet.OpenTDbResponse;
import com.itternet.interfaces.OpenTriviaDataBaseAPI;
import com.itternet.models.QuestionsListData;
import com.itternet.models.QuizSessionToken;
import com.itternet.models.QuizSessionTokenReset;

import java.util.ArrayList;

import de.vogella.algorithms.shuffle.ShuffleArray;
import layout.FragmentMultipleChoice;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.id.message;


public class MainActivity extends AppCompatActivity {

    private Toast toaster;
    private String apiBaseURL;
    private int currentQuestion = 0;
    private int lastQuestion = 0;
    private String correctAnswer;

    private int amountOfQuestions = 10;
    private String difficulty = null;//"any";
    private String questionType = null;//"any";
    private ProgressBar progBar;
    private int progBarStatus = 0;
    private ProgressDialog pDialog;

    private String sessionToken = null;
    private OpenTriviaDataBaseAPI openTDbAPI = null;
    private QuestionsListData qListData = null; //The Model holding the list of questions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sessionToken = readStringFromPreferences("sessionToken");
        Log.v("OnCreate", "readStringFromPreferences -> "+sessionToken);
        readMetaData();


        showActionBarIcon();
        setContentView(R.layout.activity_main);
        progBar = (ProgressBar) findViewById(R.id.progressBar);
        //mainText = (TextView) findViewById(R.id.main_text);
        //todo : If questions are not loading -> before you take the code apart totally, just uncomment line below
        initialize();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initialize() {
        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder okHTTPClientBuilder =  new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);
        okHTTPClientBuilder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                //.baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHTTPClientBuilder.build())
                .build();

        openTDbAPI = retrofit.create(OpenTriviaDataBaseAPI.class);
        if (null == sessionToken) {
            loadQuizSessionToken();
        } else {
            loadQuizQuestions();
        }
    }

    private void readMetaData()
    {
        try {
            PackageItemInfo info = getPackageManager().getActivityInfo(new ComponentName(this, MainActivity.class),PackageManager.GET_META_DATA);
            apiBaseURL = info.metaData.getString("baseUrl");
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    private void writeStringToPreferences(String key, String value)
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
    }

    private String readStringFromPreferences(String key)
    {
        SharedPreferences sharedPref = getPreferences(Context.MODE_PRIVATE);
        return sharedPref.getString(key, null);
    }

    private void showActionBarIcon()
    {
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.mipmap.ic_launcher);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
    }
    /**
     * Get a set of Questions and Answers from the Model
     * Create a new Instance of MultipleChoiceFragment Fragment which accepts Data,
     * Inject question and options
     * Replace FragmentContainer content with newly created Fragment
     *
     * @param v : the view
     */
    public void switchFragment(View v) {
        Fragment f;

        //f = new MultipleChoiceFragment();
        String question = "A Blank Question";
        ArrayList<String> choices = new ArrayList<String>();

        //Check if there are questions at all
        if (qListData != null && qListData.getResults().size() > 0) {

            if (currentQuestion <= lastQuestion) {

                progBarStatus = (currentQuestion + 1) * 100 / amountOfQuestions;
                progBar.setProgress(progBarStatus);
                question = qListData.getResultAtIndex(currentQuestion).getQuestion();
                correctAnswer = qListData.getResultAtIndex(currentQuestion).getCorrectAnswer();
                choices.add(correctAnswer);

                for (String answer : qListData.getResultAtIndex(currentQuestion).getIncorrectAnswers()) {
                    choices.add(answer);

                }
                //Randomize Array to not have the correct answer in the first radio button
                ShuffleArray.shuffleList(choices);
                f = FragmentMultipleChoice.newInstance(question, choices);
                FragmentManager fm = getFragmentManager();

                FragmentTransaction ft = fm.beginTransaction();/*
                            .setCustomAnimations(android.R.anim.slide_in_left, android.R.anim.slide_out_right);;*/
                ft.replace(R.id.fragment_container, f);
                ft.commit();
                ++currentQuestion;

            } else {
                //The End
                currentQuestion = 0;
            }

        }


    }

    private void resetToken()
    {
        Log.v("entered function","resetToken");
        //https://opentdb.com/api_token.php?command=reset&token=e283af58893a13155d45c5700d3144d27f1969ad45428bb9c493f39d511d3b13
        Call<QuizSessionTokenReset> qTokenCall = openTDbAPI.resetToken(sessionToken);
        //enqueue for async calls, execute for synced calls
        qTokenCall.enqueue(new Callback<QuizSessionTokenReset>() {
            @Override
            public void onResponse(Call<QuizSessionTokenReset> call, Response<QuizSessionTokenReset> response) {

                if (null != pDialog && pDialog.isShowing())
                    pDialog.dismiss();
                Log.v("resetToken()", "HTTP-Response: "+response.code());
                if (response.code() == 200) { //Server responded with "everything cool"
                    if (response.body().getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS) {
                        loadQuizQuestions();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizSessionTokenReset> call, Throwable t) {

            }
        });
    }

    private void loadQuizSessionToken() {
        Log.v("entered function","loadQuizSessionToken");
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getResources().getString(R.string.req_sess_token));
        pDialog.setCancelable(false);
        pDialog.show();

        Call<QuizSessionToken> qTokenCall = openTDbAPI.getQuizSessionToken();
        //enqueue for async calls, execute for synced calls
        qTokenCall.enqueue(new Callback<QuizSessionToken>() {
            @Override
            public void onResponse(Call<QuizSessionToken> call, Response<QuizSessionToken> response) {

                if (pDialog.isShowing())
                    pDialog.dismiss();

                if (response.code() == 200) { //Server responded with "everything cool"
                    if (response.body().getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS) {
                        sessionToken = response.body().getToken();
                        writeStringToPreferences("sessionToken", sessionToken);
                        //Log.v("token", sessionToken);
                        loadQuizQuestions();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizSessionToken> call, Throwable t) {

            }
        });
    }

    private void loadQuizQuestions() {
        Log.v("entered function","loadQuizQuestions");
        Call<QuestionsListData> qlistDataCall;
        qlistDataCall = openTDbAPI.getQuizQuestions(amountOfQuestions, sessionToken, difficulty, questionType);
        qlistDataCall.enqueue(new Callback<QuestionsListData>() {
            @Override
            public void onResponse(Call<QuestionsListData> call, Response<QuestionsListData> response) {
                Log.v("HTTP Response", ""+response.code());
                if (response.code() == 200) { //Server responded with "everything cool"
                    qListData = response.body();
                    Log.v("loadQuizQuestions","quiz response :"+qListData.getResponseCode());
                    if (qListData.getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS) {
                        //All good here
                        //mainText.setText(qListData.getResultAtIndex(0).getCategory());
                        if (qListData != null)
                            lastQuestion = qListData.getResults().size() - 1;
                    } else {
                        switch (qListData.getResponseCode()) {
                            case OpenTDbResponse.RESPONSE_CODE_NO_RESULTS:
                                //Not enough questions for requested amount in category
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_INVALID_PARAM:
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_TOKEN_NOT_FOUND:
                                //Request a Token
                                loadQuizSessionToken();
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_TOKEN_EMPTY:
                                //No more questions in Session
                                // todo request Token Reset and start over
                                resetToken();
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionsListData> call, Throwable t) {

            }
        });
    }

    public void fragmentSubmit(String data) {
        String htmlifiedanswer = Html.fromHtml(correctAnswer).toString();

        if (data.equals(htmlifiedanswer)) {
            playSound(R.raw.right);
            //todo: update score and progress, get next question etc. Winner Winner Chicken Dinner
            switchFragment(null);
            if (toaster != null)
                toaster.cancel();
        } else {
            playSound(R.raw.wrong);
            String message = String.format(getResources().getString(R.string.incorrectMessage), Html.fromHtml(correctAnswer));
           prepareToast(message);
            /* toaster = Toast.makeText(this, message, Toast.LENGTH_LONG);
            toaster.show();*/
        }
    }

    public void prepareToast(String msg)
    {
        if (toaster != null)
            toaster.cancel();
        toaster = Toast.makeText(this, msg, Toast.LENGTH_LONG);
        toaster.show();
    }

    public void playSound(int resource)
    {
        MediaPlayer mediaPlayer = MediaPlayer.create(this, resource);
        mediaPlayer.start();
        /*mediaPlayer.release();
        mediaPlayer = null;*/
    }
}
