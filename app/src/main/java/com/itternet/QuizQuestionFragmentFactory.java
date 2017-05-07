package com.itternet;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import android.example.com.quizapp.fragments.FragmentMultipleChoice;
import android.example.com.quizapp.fragments.FragmentTrueFalse;

/**
 * Created by dns on 11.03.2017.
 */

public class QuizQuestionFragmentFactory
{
    public static Fragment create(String type, String question, ArrayList<String> choices)
    {
        switch(type)
        {
            case "multiple":
                return FragmentMultipleChoice.newInstance(question, choices);

            case "boolean":
                return FragmentTrueFalse.newInstance(question, choices);

            default:
                return null;
        }
    }
}
