package android.example.com.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.widget.TextView;


public class ResultsActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_results);

        // Get the Intent that started this activity and extract the string
        Intent intent = getIntent();
        Bundle extrasBundle = intent.getExtras();
        if(!extrasBundle.isEmpty())
        {
            // Capture the layout's TextView and set the string as its text
            TextView scoreTextView = (TextView) findViewById(R.id.resultScore);
            TextView resultMsg1 = (TextView) findViewById(R.id.resultMsg2);
            int score = extrasBundle.getInt("score");
            int questions = extrasBundle.getInt("questions");
            scoreTextView.setText(String.valueOf(score));
            resultMsg1.setText(String.format(getResources().getString(R.string.ofAmount),  questions));
            //scoreTextView.setText(String.format(getResources().getString(R.string.scoreMessage), score, questions));
        }
    }

    @Override
    public void onBackPressed(){
        if (true)
            return;
        super.onBackPressed();
        startActivity(new Intent(this, StartActivity.class));

        //finish();
    }
}
