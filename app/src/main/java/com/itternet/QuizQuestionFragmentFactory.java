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

package com.itternet;

import android.support.v4.app.Fragment;

import java.util.ArrayList;

import android.example.com.quizapp.fragments.FragmentMultipleChoice;
import android.example.com.quizapp.fragments.FragmentTrueFalse;

/**
 * Created by dns on 11.03.2017.
 */

public class QuizQuestionFragmentFactory {
    public static Fragment create(String type, String question, ArrayList<String> choices) {
        switch (type) {
            case "multiple":
                return FragmentMultipleChoice.newInstance(question, choices);

            case "boolean":
                return FragmentTrueFalse.newInstance(question, choices);

            default:
                return null;
        }
    }
}
