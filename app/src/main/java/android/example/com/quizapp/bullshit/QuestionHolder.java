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

package android.example.com.quizapp.bullshit;

import android.example.com.quizapp.bullshit.interfaces.BaseQuestion;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by ByteTonight on 22.05.2017.
 */

public class QuestionHolder {
    private List<BaseQuestion> questions = new ArrayList<>();

    public QuestionHolder() {
        String question;
        String answer;
        List<IntStringPair> answers = new ArrayList<>();

        question = "What's 1+1 ?";
        answers.add(new IntStringPair(1, "2"));
        answers.add(new IntStringPair(0, "11"));
        answers.add(new IntStringPair(1, "two"));
        answers.add(new IntStringPair(0, "eleven"));
        questions.add(new MultipleResponseQnA(question, answers));

        question = "What is the Answer to the Ultimate Question of Life, The Universe, and Everything ?";
        answer = "42";
        questions.add(new FreeTextResponseQnA(question, answer));


    }

    public List<BaseQuestion> getQuestions() {
        return questions;
    }
}
