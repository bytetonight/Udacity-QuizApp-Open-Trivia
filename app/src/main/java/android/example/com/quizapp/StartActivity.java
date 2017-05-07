package android.example.com.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;


public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
    }

    public void openCategorySelection(View view)
    {
        Intent intent = new Intent(this, CategorySelectionActivity.class);
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


}
