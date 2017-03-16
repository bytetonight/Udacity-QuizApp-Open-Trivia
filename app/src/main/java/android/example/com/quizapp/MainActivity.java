package android.example.com.quizapp;

import android.app.Dialog;
//import android.app.Fragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.app.ProgressDialog;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageItemInfo;
import android.content.pm.PackageManager;
import android.content.res.Configuration;
import android.media.MediaPlayer;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.v4.app.FragmentManager;
import android.support.v7.app.ActionBar;
import android.support.v7.app.AppCompatActivity;
import android.text.Html;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.itternet.OpenTDbResponse;
import com.itternet.QuizConfig;
import com.itternet.QuizQuestionFragmentFactory;
import com.itternet.interfaces.OpenTriviaDataBaseAPI;
import com.itternet.models.QuestionsListData;
import com.itternet.models.QuizSessionToken;
import com.itternet.models.QuizSessionTokenReset;
import com.itternet.models.Result;

import java.util.ArrayList;
import java.util.Timer;
import java.util.TimerTask;
//import java.util.logging.Handler;

import de.vogella.algorithms.shuffle.ShuffleArray;
import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class MainActivity extends AppCompatActivity
{
    //Toolbar mToolbar;
    private int playerScore = 0;
    private Toast toaster;
    private ProgressBar progBar;
    private ProgressDialog pDialog;
    private OpenTriviaDataBaseAPI openTDbAPI = null; //Instantiated on demand. No more eager loading
    private QuestionsListData qListData = null; //The Model holding the list of questions
    private TextView scoreField;
    //private Handler mHandler;

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        //Read session token from shared preferences, perhaps there is one stored
        if (QuizConfig.getApiBaseURL() == null)
            QuizConfig.setSessionToken(readStringFromPreferences("sessionToken"));

        //Get the API-URL from manifest
        if (QuizConfig.getApiBaseURL() == null)
            QuizConfig.setApiBaseURL(readMetaData("baseUrl"));

        //showActionBarIcon();
        setContentView(R.layout.activity_main);
        progBar = (ProgressBar) findViewById(R.id.progressBar);

        //mHandler = new Handler(Looper.getMainLooper());

        if (savedInstanceState != null)
        {
            playerScore = savedInstanceState.getInt("playerScore");
            qListData = savedInstanceState.getParcelable("qListData");
            displayPlayerScore();
        }
        else
        {
            startQuiz();
        }

    }

    @Override
    protected void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putInt("playerScore", playerScore);
        outState.putParcelable("qListData", qListData);
    }

    /*
    onRestoreInstanceState seems a redundant callback,
    according to Google it's OPTIONAL
    @Override
    protected void onRestoreInstanceState(Bundle savedInstanceState) {
        super.onRestoreInstanceState(savedInstanceState);
        playerScore = savedInstanceState.getInt("playerScore");
        qListData = savedInstanceState.getParcelable("qListData");
        String a = "b";
        //QuizConfig.setApiBaseURL(savedInstanceState.getString("baseUrl"));
    }*/


    @Override
    public void onConfigurationChanged(Configuration newConfig)
    {
        super.onConfigurationChanged(newConfig);
    }

    /**
     * Just to say that If your older device has a menu-button, the overflow-icon won't show, on the newer phones the overflow button will show.
     * Duh ?!?!? I was wondering and wondering why on Earth my "3 dots" were not appearing in the action bar
     * @param menu : the Menu instance passed to this event
     * @return : ideally true
     */

    @Override
    public boolean onCreateOptionsMenu(Menu menu)
    {
        MenuInflater mnuPump = getMenuInflater();
        mnuPump.inflate(R.menu.navigation_menu, menu);
        //return true;
        return super.onCreateOptionsMenu(menu);
    }

    private void initializeQuizAPI()
    {
        Log.v("entered","initializeQuizAPI()");

        Gson gson = new GsonBuilder().create();

        OkHttpClient.Builder okHTTPClientBuilder = new OkHttpClient.Builder();
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        //loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.HEADERS);
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.NONE);

        okHTTPClientBuilder.addInterceptor(loggingInterceptor);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(QuizConfig.getApiBaseURL())
                //.baseUrl(getResources().getString(R.string.api_base_url))
                .addConverterFactory(GsonConverterFactory.create(gson))
                .client(okHTTPClientBuilder.build())
                .build();

        openTDbAPI = retrofit.create(OpenTriviaDataBaseAPI.class);

    }

    private String readMetaData(String which)
    {
        try
        {
            PackageItemInfo info = getPackageManager().getActivityInfo(new ComponentName(this, MainActivity.class), PackageManager.GET_META_DATA);
            return info.metaData.getString(which);
        }
        catch (PackageManager.NameNotFoundException e)
        {
            return "";
            //e.printStackTrace();
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
        String returnData = sharedPref.getString(key, null);
        //Let's see what we got from shared preferences
        Log.v("readPreferences", key + " = " + returnData);
        return returnData;
    }

    private void showActionBarIcon()
    {
        ActionBar ab = getSupportActionBar();
        ab.setLogo(R.mipmap.ic_launcher);
        ab.setDisplayUseLogoEnabled(true);
        ab.setDisplayShowHomeEnabled(true);
    }


    /**
     * Get a set of Questions and Answers from the Model previously populated by Retrofit
     * Create a new Instance of MultipleChoiceFragment Fragment which accepts Data,
     * Inject question and options
     * Replace FragmentContainer content with newly created Fragment
     *
     * @param v : the view
     */
    public void switchQuizFragment(View v)
    {
        Fragment f;

        //f = new MultipleChoiceFragment();
        String question = "A Blank Question";
        ArrayList<String> choices = new ArrayList<String>();

        //Check if there are questions at all
        if (qListData != null && !qListData.getResults().isEmpty())
        {
            if (QuizConfig.getCurrentQuestionIndex() <= QuizConfig.getLastQuestionIndex())
            {

                int cIndex = QuizConfig.getCurrentQuestionIndex();
                progBar.setProgress((cIndex + 1) * 100 / QuizConfig.getAmountOfQuestions());
                //The current Record or DataSet is a type of Result Model
                Result currentRecord = qListData.getResultAtIndex(cIndex);
                question = currentRecord.getQuestion();
                String typeOfQuestion = currentRecord.getType();
                QuizConfig.setCorrectAnswer(currentRecord.getCorrectAnswer());
                choices.add(QuizConfig.getCorrectAnswer());

                for (String answer : currentRecord.getIncorrectAnswers())
                {
                    choices.add(answer);
                }
                //Randomize Array to not always have the correct answer in the first radio button
                ShuffleArray.shuffleList(choices);

                f = QuizQuestionFragmentFactory.create(typeOfQuestion, question, choices);

                if (f != null)
                {
                    //FragmentManager fm = getFragmentManager();
                    FragmentManager fm = getSupportFragmentManager();
                    FragmentTransaction ft = fm.beginTransaction();
                            /*.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);*/
                    ft.replace(R.id.fragment_container, f);
                    ft.commit();

                    QuizConfig.setNextQuestionIndex();
                }
            }
            else
            {
                QuizConfig.ResetCurrentQuestionIndex();
            }
        }
    }

    private void startQuiz()
    {
        if (null == QuizConfig.getSessionToken())
        {
            loadQuizSessionToken();
        }
        else
        {
            loadQuizQuestions();
        }
    }


    private void resetToken()
    {
        Log.v("entered function", "resetToken");
        //https://opentdb.com/api_token.php?command=reset&token=e283af58893a13155d45c5700d3144d27f1969ad45428bb9c493f39d511d3b13
        Call<QuizSessionTokenReset> qTokenCall = openTDbAPI.resetToken(QuizConfig.getSessionToken());
        //enqueue for async calls, execute for synced calls
        qTokenCall.enqueue(new Callback<QuizSessionTokenReset>()
        {
            @Override
            public void onResponse(Call<QuizSessionTokenReset> call, Response<QuizSessionTokenReset> response)
            {

                if (null != pDialog && pDialog.isShowing())
                    pDialog.dismiss();
                Log.v("resetToken()", "HTTP-Response: " + response.code());
                if (response.code() == 200)
                { //Server responded with "everything cool"
                    if (response.body().getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS)
                    {
                        loadQuizQuestions();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizSessionTokenReset> call, Throwable t)
            {

            }
        });
    }

    private void loadQuizSessionToken()
    {
        Log.v("entered function", "loadQuizSessionToken");
        if (null == openTDbAPI)
            initializeQuizAPI();

        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getResources().getString(R.string.req_sess_token));
        pDialog.setCancelable(false);
        pDialog.show();

        Call<QuizSessionToken> qTokenCall = openTDbAPI.getQuizSessionToken();
        //enqueue for async calls, execute for synced calls
        qTokenCall.enqueue(new Callback<QuizSessionToken>()
        {
            @Override
            public void onResponse(Call<QuizSessionToken> call, Response<QuizSessionToken> response)
            {
                if (response.code() == 200)
                {
                    //Server responded with "everything cool"
                    if (response.body().getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS)
                    {
                        QuizConfig.setSessionToken(response.body().getToken());
                        writeStringToPreferences("sessionToken", QuizConfig.getSessionToken());
                        //Log.v("token", sessionToken);
                        loadQuizQuestions();
                    }
                }
            }

            @Override
            public void onFailure(Call<QuizSessionToken> call, Throwable t)
            {

            }
        });
    }

    private void loadQuizQuestions()
    {
        if (null == openTDbAPI)
            initializeQuizAPI();

        Log.v("entered function", "loadQuizQuestions");


        if (pDialog != null && pDialog.isShowing())
            pDialog.dismiss();
        pDialog = new ProgressDialog(MainActivity.this);
        pDialog.setMessage(getResources().getString(R.string.req_questions));
        pDialog.setCancelable(false);
        pDialog.show();


        Call<QuestionsListData> qlistDataCall;
        qlistDataCall = openTDbAPI.getQuizQuestions(
                QuizConfig.getAmountOfQuestions(),
                QuizConfig.getSessionToken(),
                QuizConfig.getDifficulty(),
                QuizConfig.getQuestionType());

        qlistDataCall.enqueue(new Callback<QuestionsListData>()
        {
            @Override
            public void onResponse(Call<QuestionsListData> call, Response<QuestionsListData> response)
            {
                Log.v("HTTP Response", "" + response.code());

                if (pDialog != null && pDialog.isShowing())
                    pDialog.dismiss();

                if (response.code() == 200)
                {
                    qListData = response.body();
                    Log.v("loadQuizQuestions", "quiz response :" + qListData.getResponseCode());
                    if (qListData.getResponseCode() == OpenTDbResponse.RESPONSE_CODE_SUCCESS)
                    {
                        //All good here
                        if (qListData.getResults() != null)
                            QuizConfig.setLastQuestionIndex(qListData.getResults().size() - 1);
                    }
                    else
                    {
                        switch (qListData.getResponseCode())
                        {
                            case OpenTDbResponse.RESPONSE_CODE_NO_RESULTS:
                                //Not enough questions for requested amount in category
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_INVALID_PARAM:
                                //Now that Retrofit is configured, this shouldn't actually happen
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_TOKEN_NOT_FOUND:
                                //Request a Token
                                loadQuizSessionToken();
                                break;

                            case OpenTDbResponse.RESPONSE_CODE_TOKEN_EMPTY:
                                //No more questions in Session
                                resetToken();
                                break;
                        }
                    }
                }
            }

            @Override
            public void onFailure(Call<QuestionsListData> call, Throwable t)
            {

            }
        });
    }

    public void fragmentSubmit(String answer)
    {


        if (isCorrectAnswer(answer))
        {
            ++playerScore;

            displayPlayerScore();

            playSound(R.raw.right);

            switchQuizFragment(null);

            if (toaster != null)
                toaster.cancel();
        }
        else
        {

            playSound(R.raw.wrong);
            String message = String.format(getResources().getString(R.string.incorrectMessage), Html.fromHtml(QuizConfig.getCorrectAnswer()));


            prepareToast(message);

            //new AsyncDelaySwitchFragement().execute(null, null, null);
            new Timer().schedule(
                    new TimerTask()
                    {
                        @Override
                        public void run()
                        {
                            switchQuizFragment(null);
                        }
                    }, 2000
            );
        }

        if (QuizConfig.getCurrentQuestionIndex() > QuizConfig.getLastQuestionIndex())
        {
            Intent resultsIntent = new Intent(MainActivity.this, Results.class);
            Bundle passData = new Bundle();
            passData.putInt("score", playerScore);
            passData.putInt("questions", QuizConfig.getAmountOfQuestions());
            resultsIntent.putExtras(passData);
            startActivity(resultsIntent);
        }
    }

    private void displayPlayerScore()
    {
        /*if (scoreField == null)
            scoreField = (TextView)findViewById(R.id.score);
        scoreField.setText(""+playerScore);*/
        /*runOnUiThread(new Runnable() {
                @Override
                public void run() {
                    //Update Score TextView
                    scoreField.setText(""+playerScore);
                }
            });*/
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
        mediaPlayer.setOnCompletionListener(
                new MediaPlayer.OnCompletionListener()
                {
                    @Override
                    public void onCompletion(MediaPlayer mp)
                    {
                        mp.reset();
                        mp.release();
                        mp = null;
                    }
                });
        mediaPlayer.start();

    }

    private boolean isCorrectAnswer(String a)
    {
        return Html.fromHtml(QuizConfig.getCorrectAnswer()).toString().equals(a);
    }


    @Override
    public boolean onOptionsItemSelected(MenuItem item)
    {
        //prepareToast(item.getTitle().toString());
        Intent targetIntent;
        //Bundle passData = new Bundle();
        //passData.putInt("score", playerScore);
        //passData.putInt("questions", QuizConfig.getAmountOfQuestions());
        //targetIntent.putExtras(passData);

        switch(item.getItemId())
        {
            case R.id.nav_categories:
                targetIntent = new Intent(MainActivity.this, CategorySelection.class);
                startActivity(targetIntent);
                finish(); //Finishes the current Activity but I should better reset than finish
                return true;
        }
        return super.onOptionsItemSelected(item);
    }

    private class AsyncDelaySwitchFragement extends AsyncTask<Void, Void, Void>
    {
        protected Void doInBackground(Void...params) {
            try
            {
                Thread.sleep(3000);
            }
            catch (InterruptedException e)
            {
                e.printStackTrace();
            }
            return null;
        }

        protected void onProgressUpdate(Void...params) {

        }

        protected void onPostExecute(Void...params) {
            switchQuizFragment(null);
        }

    }


}
