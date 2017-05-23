/*
 * Open Trivia QuizApp is a Udacity EU-Scholarship Project
 * written by Thorsten Itter, Copyright (c) 2017.
 * This Software may be used solely for non-profit educational purposes
 * unless specified otherwise by the original author Thorsten Itter
 * Questions and answers provided by Open Trivia Database
 * through a free for commercial use API maintained by PIXELTAIL GAME
 * This source code including this header may not be modified
 *
 */

package android.example.com.quizapp;

import android.content.Intent;
import android.support.v4.app.NavUtils;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import static android.example.com.quizapp.R.string.youAnswered;


public class ResultsActivity extends AppCompatActivity {


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);
        int[] scoreHeaders = {
                R.string.score_oops,
                R.string.score_oops,
                R.string.score_oops,
                R.string.score_oops,
                R.string.score_oops,
                R.string.score_notBad,
                R.string.score_nice,
                R.string.score_wellDone,
                R.string.score_congrats,
                R.string.score_awesome,
                R.string.score_perfect,
        };
        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        if (!extrasBundle.isEmpty()) {
            // Capture the layout's TextView and set the string as its text
            TextView scoreTextView = (TextView) findViewById(R.id.resultScore);
            TextView resultMsg1 = (TextView) findViewById(R.id.resultMsg2);
            TextView resultsHeader = (TextView) findViewById(R.id.resultsHeader);

            int correctAnswers = extrasBundle.getInt("score");
            int questions = extrasBundle.getInt("questions");

            int score = (int) (correctAnswers / (float) questions * 10);
            if (score > 10) {
                score = 10;
            }
            scoreTextView.setText(String.valueOf(correctAnswers));
            resultMsg1.setText(String.format(getResources().getString(R.string.ofAmount), questions));
            resultsHeader.setText(getString(scoreHeaders[score]));

            //You want a Toast to show the score ? You get a Toast
            Toast.makeText(this,
                    getString(scoreHeaders[score]) + "\n" +
                            getString(R.string.youAnswered) + "\n" +
                            String.valueOf(correctAnswers) + "\n" +
                            String.format(getResources().getString(R.string.ofAmount), questions) + "\n" +
                            getString(R.string.questionsCorrectly)
                    , Toast.LENGTH_LONG).show();

        }
    }

    @Override
    public void onBackPressed() {
        NavUtils.navigateUpFromSameTask(this);
        finish();
    }

    public void onClickAction(View view) {
        if (view instanceof Button) {
            Button b = (Button) view;
            Intent targetIntent;
            switch (b.getTag().toString()) {
                case "MORE_QUESTIONS":
                    targetIntent = new Intent(ResultsActivity.this, QuizActivity.class);
                    startActivity(targetIntent);
                    finish();
                    break;

                case "OPTIONS":
                    targetIntent = new Intent(ResultsActivity.this, OptionsActivity.class);
                    startActivity(targetIntent);
                    finish();
                    break;
                case "QUIT":
                    Intent intent = new Intent(Intent.ACTION_MAIN);
                    intent.addCategory(Intent.CATEGORY_HOME);
                    intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
                    startActivity(intent);
                    break;
            }
        }
    }
}
