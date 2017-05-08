package android.example.com.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.itternet.utils.Utils;


public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        setDefaults();
    }

    public void openOptionsActivity(View view)
    {
        Intent intent = new Intent(this, OptionsActivity.class);
        startActivity(intent);
    }

    public void openQuiz(View view)
    {
        Intent intent = new Intent(this, QuizActivity.class);
        startActivity(intent);
    }

    public void quitApp(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setDefaults()
    {
        String temp = Utils.readStringFromPreferences(StartActivity.this, Utils.NUMBER_OF_QUESTIONS);
        if ( null == temp )
            Utils.writeStringToPreferences(StartActivity.this, Utils.NUMBER_OF_QUESTIONS, "10");
    }
}
