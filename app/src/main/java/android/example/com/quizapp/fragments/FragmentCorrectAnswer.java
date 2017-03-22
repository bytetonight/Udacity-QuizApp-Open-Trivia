package android.example.com.quizapp.fragments;

import android.app.Activity;
import android.example.com.quizapp.R;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;
import com.itternet.interfaces.Communicator;




public class FragmentCorrectAnswer extends DialogFragment implements View.OnClickListener
{

    private static final String CORRECT_ANSWER = "param1";

    Communicator communicator;
    Button okButton;
    private String correctAnswer;


    public static FragmentCorrectAnswer newInstance(String param1)
    {
        FragmentCorrectAnswer fragment = new FragmentCorrectAnswer();
        Bundle args = new Bundle();
        args.putString(CORRECT_ANSWER, param1);
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onCreate(Bundle savedInstanceState)
    {
        super.onCreate(savedInstanceState);
        if (getArguments() != null)
        {
            correctAnswer = getArguments().getString(CORRECT_ANSWER);
        }
    }


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState)
    {
        //getDialog().setTitle(R.string.correctAnswerTitle);
        getDialog().requestWindowFeature(Window.FEATURE_NO_TITLE);
        View thisView = inflater.inflate(R.layout.fragment_fragment_correct_answer, container, false);
        //thisView.setClipToOutline(true);
        okButton = (Button) thisView.findViewById(R.id.btnDialogSubmit);
        TextView tvCorrectAnswer = (TextView)thisView.findViewById(R.id.tvDialogCorrectAnswer);
        tvCorrectAnswer.setText(correctAnswer);
        setCancelable(false);

        okButton.setOnClickListener(this);
        return thisView;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnDialogSubmit)
        {
            //onDialogMessage will trigger in MainActivity upon which the next question is displayed
            communicator.onDialogMessage("OK");
            dismiss();
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }


}

