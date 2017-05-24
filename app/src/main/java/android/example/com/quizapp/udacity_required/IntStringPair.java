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

package android.example.com.quizapp.udacity_required;

/**
 * Created by ByteTonight on 22.05.2017.
 */

public class IntStringPair {
    private int key;
    private String value;

    public IntStringPair(int key, String value) {
        this.key = key;
        this.value = value;
    }

    public int getKey() {
        return key;
    }

    public String getValue() {
        return value;
    }
}
