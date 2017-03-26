package android.example.com.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;


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
        if(!extrasBundle.isEmpty())
        {
            // Capture the layout's TextView and set the string as its text
            TextView scoreTextView = (TextView) findViewById(R.id.resultScore);
            TextView resultMsg1 = (TextView) findViewById(R.id.resultMsg2);
            TextView resultsHeader = (TextView)findViewById(R.id.resultsHeader);

            int score = extrasBundle.getInt("score");
            int questions = extrasBundle.getInt("questions");
            scoreTextView.setText(String.valueOf(score));
            resultMsg1.setText(String.format(getResources().getString(R.string.ofAmount),  questions));
            resultsHeader.setText(getString(scoreHeaders[score]));



            //scoreTextView.setText(String.format(getResources().getString(R.string.scoreMessage), score, questions));
        }
    }

    @Override
    public void onBackPressed(){
        /*if (true)
            return;*/
        //Let's be mean and disable the back-key
        super.onBackPressed();
        startActivity(new Intent(this, StartActivity.class));

        //finish();
    }

    public void onClickAction(View view)
    {
        if (view instanceof Button)
        {
            Button b = (Button) view;
            Intent targetIntent;
            switch (b.getTag().toString())
            {
                case "MORE_QUESTIONS":
                    targetIntent = new Intent(ResultsActivity.this, MainActivity.class);
                    startActivity(targetIntent);
                    finish();
                break;

                case "OPTIONS":
                    targetIntent = new Intent(ResultsActivity.this, StartActivity.class);
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
