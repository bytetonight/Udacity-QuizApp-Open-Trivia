package android.example.com.quizapp;

import android.content.Intent;
import android.content.res.Resources;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ImageView;

public class StartActivity extends AppCompatActivity
{

    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_start);
        //setImages();
    }

    public void openCategorySelection(View view)
    {
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivity(intent);
    }

    public void openQuiz(View view)
    {
        Intent intent = new Intent(this, MainActivity.class);
        startActivity(intent);
    }

    public void quitApp(View view)
    {
        Intent intent = new Intent(Intent.ACTION_MAIN);
        intent.addCategory(Intent.CATEGORY_HOME);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        startActivity(intent);
    }

    private void setImages()
    {
        Resources resources = getResources();

        for (int i = 1; i <= 3; ++i)
        {
            ImageView currentimageView = (ImageView) findViewById(resources.getIdentifier("imageView"+ i, "id", getPackageName()));
            if (currentimageView != null)
                currentimageView.setImageResource(resources.getIdentifier("image"+ i , "drawable", getPackageName()));
        }
    }
}
