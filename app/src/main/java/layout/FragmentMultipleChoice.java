package layout;

import android.example.com.quizapp.MainActivity;
import android.example.com.quizapp.R;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.TextView;

import com.itternet.interfaces.Questionable;

import java.util.ArrayList;




/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMultipleChoice extends Fragment implements View.OnClickListener, Questionable
{

    private View rootView;
    private int checkedRadioID = -1;
    private String realIDstring;
    private String question;
    private ArrayList<String> choices;
    private Button submitButton;


    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null)
        {
            realIDstring = savedInstanceState.getString(KEY_REAL_ID_STRING, null);
        }
        // Get back arguments
        Bundle b = this.getArguments();
        if (b != null)
        {
            question = b.getString(KEY_QUESTION, getResources().getString(R.string.blank_question));
            if (b.getSerializable(KEY_CHOICES) != null)
                choices = b.getStringArrayList(KEY_CHOICES);

        }

    }


   @Override
    public void onSaveInstanceState(Bundle outState)
    {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_REAL_ID_STRING, realIDstring);
    }



    /**
     * When using orientation-based Layouts AND doing so in the recommended way
     * by having your portrait-Layout abc.xml in the layout folder
     * and your Landscape Layouts in the layout-land folder with the same filename abc.xml
     * Android will automatically "source-in" your alternative layout.
     *
     * You then do not have to check the current orientation yourself
     *
     * @param inflater : the pump
     * @param container : the Fragment root
     * @param savedInstanceState : null or the data bundle that you saved prior to orientation-change
     * @return
     */
    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {

        inflater = getActivity().getLayoutInflater();
        // Inflate the layout for this fragment with the correct layout.xml
        //(R.layout.fragment_fragment_multiple_choice) is automatically replaced with the layout
        //matching the current orientation. See comments for this function.
        rootView = inflater.inflate(R.layout.fragment_fragment_multiple_choice, container, false);
        //Once inflated, search the Fragment for desired components
        //and set them
        submitButton = (Button) rootView.findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(this);
        TextView questionBox = (TextView) rootView.findViewById(R.id.questionBox);
        questionBox.setText(Html.fromHtml(question));

        String packageName = getActivity().getPackageName();

        int counter = 1;
        for (String item : choices)
        {
            RadioButton rb = (RadioButton) rootView.findViewById(getResources()
                    .getIdentifier("radioBtn" + counter, "id", packageName));
            if (realIDstring != null)
            {
                setRadioSelectionByRealID(realIDstring);

            }
            rb.setText(Html.fromHtml(item));
            rb.setOnClickListener(this);
            ++counter;
        }

        //Yet Another Interesting Find :
        //View States are saved/restored internally by Android  BUT ...
        //If these RadioButtons have no id, the selected state will not
        //survive orientation changes
        /*int counter = 1;
        for (String item : choices)
        {
            //http://belencruz.com/2013/04/set-styles-programmatically-in-android/
            RadioButton rb = (RadioButton) inflater.inflate(R.layout.template_radiobutton, null);
            rb.setId(100 + counter);
            rb.setText(Html.fromHtml(item));
            radioChoices.addView(rb);
            ++counter;
        }*/
        return rootView;
    }

    public static FragmentMultipleChoice newInstance(String question, ArrayList<String> options)
    {
        FragmentMultipleChoice fmc = new FragmentMultipleChoice();
        Bundle args = new Bundle();
        args.putString(KEY_QUESTION, question);
        args.putStringArrayList(KEY_CHOICES, options);
        fmc.setArguments(args);
        return fmc;
    }

    @Override
    public void onClick(View view)
    {
        if (view instanceof RadioButton)
        {
            if (checkedRadioID != -1)
            {
                RadioButton rb = (RadioButton) rootView.findViewById(checkedRadioID);
                if (rb != null)
                    rb.setChecked(false);
            }
            checkedRadioID = view.getId();
            realIDstring = getResources().getResourceName(checkedRadioID);
            ((MainActivity) getActivity()).prepareToast(realIDstring);
            return;
        }

        //A click on the Non-RadioButton submitButton lead us here
        //Check if a RadioButton was previously selected
        if (checkedRadioID != -1)
        {
            //Disable button after a successful submission to prevent multiple
            // submissions from click-happy-users
            submitButton.setEnabled(false);
            String radiovalue = getCheckedText();

            //Call fragmentSubmit method in MainActivity
            ((MainActivity) getActivity()).fragmentSubmit(radiovalue);
        }
        else
        {
            ((MainActivity) getActivity()).playSound(R.raw.no_selection);
            ((MainActivity) getActivity()).prepareToast(getResources().getText(R.string.no_selection).toString());
            //Toast.makeText(this.getActivity(), getResources().getText(R.string.no_selection), Toast.LENGTH_LONG).show();
        }
    }

    private void setRadioSelectionByRealID(String idString)
    {
        RadioButton rb = (RadioButton) rootView.findViewById(getResources()
                .getIdentifier(idString, "id", getActivity().getPackageName()));
        rb.setSelected(true);
        checkedRadioID = rb.getId();
    }

    private String getCheckedText()
    {
        RadioButton rb = (RadioButton) rootView.findViewById(checkedRadioID);
        if (rb != null)
            return rb.getText().toString();
        return "";
    }

}
