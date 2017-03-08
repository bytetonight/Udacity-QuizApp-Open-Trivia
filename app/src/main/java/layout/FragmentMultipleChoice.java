package layout;

import android.example.com.quizapp.MainActivity;
import android.example.com.quizapp.R;
import android.os.Bundle;
//import android.support.v4.app.Fragment;
import android.app.Fragment; //It seems that Fragments must import and extend this and not the above !!
import android.text.Html;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;
import android.widget.Toast;

import java.util.ArrayList;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMultipleChoice extends Fragment implements View.OnClickListener {

    private String question;
    private ArrayList<String> choices;
    private RadioGroup radioChoices;
    private View rootView;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        // Get back arguments
        Bundle b = this.getArguments();
        question = b.getString("question", "");
        if (b.getSerializable("choices") != null)
            choices = b.getStringArrayList("choices");
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {

        inflater = getActivity().getLayoutInflater();
        // Inflate the layout for this fragment
        rootView = inflater.inflate(R.layout.fragment_fragment_multiple_choice, container, false);
        //Once inflated, search the Fragment for desired components
        //and set them
        Button btn = (Button) rootView.findViewById(R.id.btnSubmit);
        btn.setOnClickListener(this);
        TextView questionBox = (TextView) rootView.findViewById(R.id.questionBox);
        radioChoices = (RadioGroup) rootView.findViewById(R.id.radioGroup);

        for (String item : choices) {
            //http://belencruz.com/2013/04/set-styles-programmatically-in-android/
            RadioButton rb = (RadioButton) inflater.inflate(R.layout.template_radiobutton, null);
            rb.setText(Html.fromHtml(item));
            radioChoices.addView(rb);
        }

        questionBox.setText(Html.fromHtml(question));
        return rootView;
    }

    public static FragmentMultipleChoice newInstance(String question, ArrayList<String> options) {
        FragmentMultipleChoice fmc = new FragmentMultipleChoice();
        Bundle args = new Bundle();
        args.putString("question", question);
        args.putStringArrayList("choices", options);
        fmc.setArguments(args);
        return fmc;
    }

    @Override
    public void onClick(View view) {

        int selectedId = radioChoices.getCheckedRadioButtonId();
        RadioButton rb = (RadioButton) rootView.findViewById(selectedId);
        if (null != rb) {
            String radiovalue = rb.getText().toString();
            ((MainActivity) getActivity()).fragmentSubmit(radiovalue);
        } else {
            Toast.makeText(this.getActivity(), getResources().getText(R.string.no_selection), Toast.LENGTH_LONG).show();
        }
    }
}
