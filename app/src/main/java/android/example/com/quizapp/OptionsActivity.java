package android.example.com.quizapp;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.Spinner;
import android.widget.TextView;

import com.crystal.crystalrangeseekbar.interfaces.OnSeekbarChangeListener;
import com.crystal.crystalrangeseekbar.widgets.CrystalSeekbar;
import com.itternet.utils.Utils;

import static android.example.com.quizapp.R.id.difficultySpinner;
import static com.itternet.utils.Utils.CATEGORY_ID;
import static com.itternet.utils.Utils.CATEGORY_NAME;


public class OptionsActivity extends AppCompatActivity
{
    private CrystalSeekbar seekBarAmountOfQuestions = null;
    private TextView tvNumberOfQuestions = null;
    private int numberOfQuestions = 1;


    @Override
    protected void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_options);


        tvNumberOfQuestions = (TextView) findViewById(R.id.tvNumberOfQuestions);
        seekBarAmountOfQuestions = (CrystalSeekbar) findViewById(R.id.SeekBarAmountOfQuestions);
        TextView currentCategory = (TextView)findViewById(R.id.tvCurrentCategory);
        Spinner difficultySpinner = (Spinner) findViewById(R.id.difficultySpinner);

        // Create an ArrayAdapter using the string array and a default spinner layout
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(this,
                R.array.array_difficulty, android.R.layout.simple_spinner_item);
        // Specify the layout to use when the list of choices appears
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        // Apply the adapter to the spinner
        difficultySpinner.setAdapter(adapter);

        int lastSelectedValue = 1;
        String temp = Utils.readStringFromPreferences(OptionsActivity.this, Utils.NUMBER_OF_QUESTIONS);
        if ( null != temp && !temp.isEmpty())
            lastSelectedValue = Integer.parseInt(temp);
        seekBarAmountOfQuestions.setMinStartValue(lastSelectedValue); //Set seekbar value to last setting
        seekBarAmountOfQuestions.setMinValue(1);
        seekBarAmountOfQuestions.setMaxValue(50);
        seekBarAmountOfQuestions.setOnSeekbarChangeListener(mSeekBarChangeListener);
        seekBarAmountOfQuestions.setPosition(CrystalSeekbar.Position.LEFT).apply();


        Intent previousIntent = getIntent();
        Bundle extras = null;
        if (previousIntent != null)
            extras = previousIntent.getExtras();

        /**
         * find out if previous intent was category selector.
         * Other Activities shouldn't pass categoryID
         */

        if (extras != null)
        {
            if (extras.containsKey(CATEGORY_ID))
            {
                Utils.writeStringToPreferences(OptionsActivity.this,
                        CATEGORY_ID,
                        String.valueOf(previousIntent.getIntExtra(CATEGORY_ID, -1)));
            }

            if (extras.containsKey(CATEGORY_NAME))
            {
                Utils.writeStringToPreferences(OptionsActivity.this,
                        CATEGORY_NAME,
                        String.valueOf(previousIntent.getStringExtra(CATEGORY_NAME)));
            }
        }

        temp = Utils.readStringFromPreferences(OptionsActivity.this, Utils.CATEGORY_NAME);
        if (temp != null)
            currentCategory.setText(temp);
    }

    private OnSeekbarChangeListener  mSeekBarChangeListener = new OnSeekbarChangeListener() {

        @Override
        public void valueChanged(Number value)
        {
            numberOfQuestions =  Integer.parseInt( String.valueOf(value) );
            tvNumberOfQuestions.setText(String.valueOf(numberOfQuestions));
            Utils.writeStringToPreferences(OptionsActivity.this, Utils.NUMBER_OF_QUESTIONS,
                    String.valueOf(numberOfQuestions));
        }
    };

    public void openCategoriesActivity(View view)
    {
        Intent intent = new Intent(this, CategorySelectionActivity.class);
        startActivity(intent);
    }
}
