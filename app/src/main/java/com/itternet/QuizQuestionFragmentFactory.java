package com.itternet;

import android.app.Fragment;

import java.util.ArrayList;
import java.util.Dictionary;
import java.util.HashMap;
import java.util.Map;

import layout.FragmentMultipleChoice;
import layout.FragmentTrueFalse;

/**
 * Created by dns on 11.03.2017.
 */

public class QuizQuestionFragmentFactory
{
    //private static Map<String, Fragment> qFragments = new HashMap<String, Fragment>();



    public static Fragment create(String type, String question, ArrayList<String> choices)
    {
        /*if (qFragments.isEmpty())
        {
            qFragments.put("multiple", FragmentMultipleChoice.newInstance(question, choices));
            qFragments.put("boolean", FragmentTrueFalse.newInstance(question, choices));
        }
        return qFragments.get("type");*/
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
