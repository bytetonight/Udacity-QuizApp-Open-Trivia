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

import android.example.com.quizapp.BullShitActivity;
import android.example.com.quizapp.QuizActivity;
import android.example.com.quizapp.R;
import android.example.com.quizapp.bullshit.MultipleResponseQnA;
import android.example.com.quizapp.bullshit.interfaces.BaseQuestion;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.CheckBox;
import android.widget.RadioButton;
import android.widget.TextView;
import android.widget.Toast;

import com.itternet.interfaces.Questionable;
import com.itternet.utils.Utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;
import java.util.List;
import java.util.Map;


/**
 * A simple {@link Fragment} subclass.
 */
public class FragmentMultipleResponse extends Fragment implements View.OnClickListener, Questionable {

    private View rootView;
    private MultipleResponseQnA qNa;
    private HashMap<String, String> checkedCheckBoxIds = new HashMap();
    private Button submitButton;

    public static FragmentMultipleResponse newInstance(MultipleResponseQnA questionAndAnswer) {
        FragmentMultipleResponse fmr = new FragmentMultipleResponse();
        Bundle args = new Bundle();
        args.putParcelable(KEY_PAYLOAD, questionAndAnswer);
        fmr.setArguments(args);
        return fmr;
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (savedInstanceState != null) {
            checkedCheckBoxIds = (HashMap) savedInstanceState.getSerializable(KEY_CHECKBOX_CHECKED_IDS);
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
        outState.putSerializable(KEY_CHECKBOX_CHECKED_IDS, checkedCheckBoxIds);
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container,
                             Bundle savedInstanceState) {
        inflater = getActivity().getLayoutInflater();
        rootView = inflater.inflate(R.layout.fragment_fragment_multiple_response, container, false);
        submitButton = (Button) rootView.findViewById(R.id.btnSubmit);
        submitButton.setOnClickListener(this);
        TextView questionBox = (TextView) rootView.findViewById(R.id.questionBox);
        questionBox.setText(Utils.fromHtml(qNa.getQuestion()));
        String packageName = getActivity().getPackageName();
        List<String> tempList = new ArrayList<>(qNa.getCorrectAnswers());
        tempList.addAll(qNa.getIncorrectAnswers());
        int counter = 1;
        for (String item : tempList) {
            CheckBox cb = (CheckBox) rootView.findViewById(getResources()
                    .getIdentifier("cbAnswer" + counter, "id", packageName));
            cb.setText(Utils.fromHtml(item));
            cb.setOnClickListener(this);
            ++counter;
        }

        return rootView;
    }

    @Override
    public void onClick(View view) {
        if (view instanceof CheckBox) {
            CheckBox cb = (CheckBox) view;
            String checkBoxRealId = getResources().getResourceName(view.getId());
            if (cb.isChecked())
                checkedCheckBoxIds.put(checkBoxRealId, cb.getText().toString());
            else
                checkedCheckBoxIds.remove(checkBoxRealId);
            return;
        }

        //A click on the Non-RadioButton submitButton lead us here
        //Check if a RadioButton was previously selected
        if (checkedCheckBoxIds.size() > 0) {
            submitButton.setEnabled(false);
            //Call onFragmentSubmit method in MainActivity
            ((BullShitActivity) getActivity()).onFragmentSubmit(checkedCheckBoxIds);
        } else {
            Toast.makeText(getActivity(), getResources().getText(R.string.no_selection).toString(), Toast.LENGTH_SHORT).show();
        }
    }



}
