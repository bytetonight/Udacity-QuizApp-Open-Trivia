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

package de.vogella.algorithms.shuffle;


import java.util.List;
import java.util.Random;

/**
 * Created by dns on 07.03.2017.
 * It's a shame that a lot of the n00bs in the course don't mention the source of code
 * they obviously didn't create themselves and don't even bother to credit the original makers.
 * The code below was taken from de.vogella.algorithms.shuffle
 */

public class ShuffleArray {

    public static void shuffleList(List<String> a) {
        int n = a.size();
        Random random = new Random();
        random.nextInt();
        for (int i = 0; i < n; i++) {
            int change = i + random.nextInt(n - i);
            swap(a, i, change);
        }
    }

    private static void swap(List<String> a, int i, int change) {
        String helper = a.get(i);
        a.set(i, a.get(change));
        a.set(change, helper);
    }
}
