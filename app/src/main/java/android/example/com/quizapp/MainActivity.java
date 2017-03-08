package android.example.com.quizapp;

import android.app.Fragment;
import android.app.FragmentManager;
import android.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
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

import java.util.ArrayList;

import de.vogella.algorithms.shuffle.ShuffleArray;
import layout.FragmentMultipleChoice;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import static android.R.attr.data;


public class MainActivity extends AppCompatActivity {

    private String apiBaseURL;
    private int currentQuestion = 0;
    private int lastQuestion = 0;
    private String correctAnswer;

    private int amountOfQuestions = 20;
    private ProgressBar progBar;
    private int progBarStatus = 0;
    private ProgressDialog pDialog;
    //todo set sessionToken to null in release
    private String sessionToken = "0304ddc8b67fd80a526ce64a0bd2fe752f2708db9374e31b7555ce1e9ee79c9a";//null;
    private OpenTriviaDataBaseAPI openTDbAPI = null;
    private QuestionsListData qListData = null; //The Model holding the list of questions


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        readMetaData();
        showActionBarIcon();
        setContentView(R.layout.activity_main);
        progBar = (ProgressBar) findViewById(R.id.progressBar);
        //mainText = (TextView) findViewById(R.id.main_text);
        //todo : If questions are not loading -> before you take the code apart totally, just uncomment line below
        //initialize();
    }

    @Override
    public void onConfigurationChanged(Configuration newConfig) {
        super.onConfigurationChanged(newConfig);
    }

    private void initialize() {
        Gson gson = new GsonBuilder().create();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(apiBaseURL)
                //.baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
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


    private void loadQuizSessionToken() {
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
        Call<QuestionsListData> qlistDataCall = openTDbAPI.getQuizQuestions(amountOfQuestions, sessionToken);
        qlistDataCall.enqueue(new Callback<QuestionsListData>() {
            @Override
            public void onResponse(Call<QuestionsListData> call, Response<QuestionsListData> response) {
                if (response.code() == 200) { //Server responded with "everything cool"
                    qListData = response.body();
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
                                loadQuizSessionToken();
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
            //todo: update score and progress, get next question etc. Winner Winner Chicken Dinner
            switchFragment(null);
        } else {
            String message = String.format(getResources().getString(R.string.incorrectMessage), Html.fromHtml(correctAnswer));
            Toast.makeText(this, message, Toast.LENGTH_LONG).show();
        }
    }

}
