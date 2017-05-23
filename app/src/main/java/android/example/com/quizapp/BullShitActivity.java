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

package android.example.com.quizapp;

import android.content.Intent;
import android.example.com.quizapp.bullshit.FreeTextResponseQnA;
import android.example.com.quizapp.bullshit.MultipleResponseQnA;
import android.example.com.quizapp.bullshit.interfaces.BaseQuestion;
import android.example.com.quizapp.bullshit.QuestionHolder;
import android.example.com.quizapp.fragments.FragmentCorrectAnswer;
import android.example.com.quizapp.fragments.FragmentFreeTextResponse;
import android.example.com.quizapp.fragments.FragmentMultipleResponse;
import android.support.v4.app.DialogFragment;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Toast;

import com.itternet.interfaces.Communicator;
import com.itternet.utils.Utils;

import java.util.HashMap;
import java.util.List;


import static android.example.com.quizapp.QuizActivity.CORRECT_ANSWER_DIALOG_TAG;
import static android.example.com.quizapp.QuizActivity.QUESTION_FRAGMENT_TAG;
import static android.example.com.quizapp.R.string.and;

public class BullShitActivity extends AppCompatActivity implements Communicator {

    private List<BaseQuestion> questions;
    private int currentQuestionIndex = 0;
    private int playerScore = 0;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_bull_shit);

        QuestionHolder bSp = new QuestionHolder();
        questions = bSp.getQuestions();

        /*
        * Don't recreate the Fragment if there is no savedInstanceState
        * */
        if (savedInstanceState == null)
            switchQuizFragment(null);
        Toast.makeText(BullShitActivity.this, "bullshit", Toast.LENGTH_SHORT).show();
    }


    public void switchQuizFragment(View v) {
        Fragment questionFragment = null;
        //Check if there are questions at all
        if (questions != null && !questions.isEmpty()) {
            if (currentQuestionIndex <= questions.size()) {
                BaseQuestion qBase = questions.get(currentQuestionIndex);
                String qName = qBase.getClass().getSimpleName();
                switch (qName) {
                    case "MultipleResponseQnA":
                        questionFragment = FragmentMultipleResponse.newInstance((MultipleResponseQnA) qBase);
                        break;

                    case "FreeTextResponseQnA":
                        questionFragment = FragmentFreeTextResponse.newInstance((FreeTextResponseQnA) qBase);
                        break;
                }

                FragmentManager fm = getSupportFragmentManager();
                FragmentTransaction ft = fm.beginTransaction();
                ft.setCustomAnimations(R.anim.enter_from_right, R.anim.exit_to_left);
                ft.replace(R.id.fragment_container, questionFragment, QUESTION_FRAGMENT_TAG);
                ft.commit();
            }
        }
    }

    public void onFragmentSubmit(HashMap<String, String> answersMap) {
        MultipleResponseQnA q = (MultipleResponseQnA) questions.get(currentQuestionIndex);
        ++currentQuestionIndex;
        StringBuilder correctWouldHaveBeen = new StringBuilder();
        String and = "";
        String aaand = getString(R.string.and);
        int hits = 0;
        for (String correctAnswer : q.getCorrectAnswers()) {
            if (!answersMap.containsValue(correctAnswer)) {
                correctWouldHaveBeen.append(and + correctAnswer + "\n");
                and = aaand;
            } else {
                ++hits;
            }
        }

        if (correctWouldHaveBeen.length() == 0) {
            ++playerScore;
            if (currentQuestionIndex <= questions.size() - 1) {

                switchQuizFragment(null);
            } else {
                runResultsActivity();
            }
        } else {
            if (hits > 0)
                correctWouldHaveBeen.append(getString(R.string.additionally));
            displayCorrectAnswerDialog(correctWouldHaveBeen.toString());
        }
    }

    public void onFragmentSubmit(String submitted) {
        FreeTextResponseQnA q = (FreeTextResponseQnA) questions.get(currentQuestionIndex);
        ++currentQuestionIndex;
        if (submitted.equalsIgnoreCase(q.getAnswer())) {
            ++playerScore;
            if (currentQuestionIndex <= questions.size() - 1) {

                switchQuizFragment(null);
            } else {
                runResultsActivity();
            }
        } else {
            displayCorrectAnswerDialog(q.getAnswer());
        }

    }

    @Override
    public void onDialogMessage(String msg) {
        if (msg.equals(getString(R.string.ok))) {
            if (currentQuestionIndex <= questions.size() - 1)
                switchQuizFragment(null);
            else {
                runResultsActivity();
            }
        }
    }

    public void displayCorrectAnswerDialog(String msg) {
        DialogFragment fca = FragmentCorrectAnswer.newInstance(msg);
        fca.setCancelable(false);
        fca.show(getSupportFragmentManager(), CORRECT_ANSWER_DIALOG_TAG);
    }

    private void runResultsActivity() {
        Intent resultsIntent = new Intent(BullShitActivity.this, ResultsActivity.class);
        Bundle passData = new Bundle();
        passData.putInt("score", playerScore);
        passData.putInt("questions", questions.size());
        resultsIntent.putExtras(passData);
        startActivity(resultsIntent);
        finish();//Once we leave the Quiz, we don't want to come back by pressing back
    }
}
