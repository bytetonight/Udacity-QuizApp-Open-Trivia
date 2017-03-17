package android.example.com.quizapp.fragments;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.example.com.quizapp.R;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v7.app.AlertDialog;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.TextView;

import java.util.ArrayList;

import static android.example.com.quizapp.R.id.tvCorrectAnswer;


public class FragmentCorrectAnswer extends DialogFragment implements View.OnClickListener
{

    private static final String CORRECT_ANSWER = "param1";

    Communicator communicator;
    Button ok;
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
        getDialog().setTitle(R.string.correctAnswerTitle);
        View thisView = inflater.inflate(R.layout.fragment_fragment_correct_answer, container, false);
        ok = (Button) thisView.findViewById(R.id.btnDialogSubmit);
        TextView tvCorrectAnswer = (TextView)thisView.findViewById(R.id.tvCorrectAnswer);
        tvCorrectAnswer.setText(correctAnswer);
        setCancelable(false);

        ok.setOnClickListener(this);
        return thisView;
    }

    @Override
    public void onClick(View v)
    {
        if (v.getId() == R.id.btnDialogSubmit)
        {
            communicator.onDialogMessage("OK");
            dismiss();
            //Call parent swithfragment or somethnig
        }
    }

    @Override
    public void onAttach(Activity activity)
    {
        super.onAttach(activity);
        communicator = (Communicator) activity;
    }

    public interface Communicator
    {
        public void onDialogMessage(String msg);

    }
}

