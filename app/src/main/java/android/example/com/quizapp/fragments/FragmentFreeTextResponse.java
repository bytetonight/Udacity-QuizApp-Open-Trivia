/*
 * Open Trivia QuizApp is a Udacity EU-Scholarship Project
 * written by Thorsten Itter, Copyright (c) 2017.
 * This Software may be used solely for non-profit educational purposes
 * unless specified otherwise by the original author Thorsten Itter
 * Questions and answers provided by Open Trivia Database
 * through a free for commercial use API maintained by PIXELTAIL GAME
 * This source code including this header may not be modified
 *
 */

package android.example.com.quizapp.fragments;

import android.example.com.quizapp.UdacityQuizRequirementsActivity;
import android.example.com.quizapp.R;
import android.example.com.quizapp.udacity_required.FreeTextResponseQnA;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

import com.itternet.interfaces.Questionable;
import com.itternet.utils.Utils;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentFreeTextResponse extends Fragment implements View.OnClickListener, Questionable {

    private View rootView;
    private FreeTextResponseQnA qNa;
    private EditText editTextAnswer;
    private String answerText = "";
    private Button submitButton;

    public static FragmentFreeTextResponse newInstance(FreeTextResponseQnA questionAndAnswer) {
        FragmentFreeTextResponse fmr = new FragmentFreeTextResponse();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PAYLOAD, questionAndAnswer);
        fmr.setArguments(args);
        return fmr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            answerText = savedInstanceState.getString(KEY_FREE_TEXT_ANSWER);
        }
        // Get back arguments
        Bundle b = this.getArguments();
        if (b != null) {
            qNa = b.getParcelable(KEY_PAYLOAD);
        }
    }

    @Override
    public void onSaveInstanceState(Bundle outState) {
        super.onSaveInstanceState(outState);
        outState.putString(KEY_FREE_TEXT_ANSWER, editTextAnswer.getText().toString());
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_fragment_free_text_response, container, false);
        submitButton = (Button) rootView.findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(this);
        TextView questionBox = (TextView) rootView.findViewById(R.id.questionBox);
        questionBox.setText(Utils.fromHtml(qNa.getQuestion()));
        editTextAnswer = (EditText) rootView.findViewById(R.id.editTextAnswer);
        editTextAnswer.setText(answerText);
        return rootView;
    }

    @Override
    public void onClick(View view) {
        answerText = editTextAnswer.getText().toString().trim();
        if (answerText.length() > 0) {
            submitButton.setEnabled(false);
            //Call onFragmentSubmit method in MainActivity
            ((UdacityQuizRequirementsActivity) getActivity()).onFragmentSubmit(answerText);
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.no_answer).toString(), Toast.LENGTH_SHORT).show();
        }
    }
}
