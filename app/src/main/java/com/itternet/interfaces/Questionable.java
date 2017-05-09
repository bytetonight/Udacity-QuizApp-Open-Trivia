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

package com.itternet.interfaces;

/**
 * This Interface is literally questionable
 */

public interface Questionable
{
   /**
    * Variables declared in an Interface are implicitly public static final
    * so it's not required to declare each one public static final
    */
   String KEY_QUESTION = "question";
   String KEY_CHOICES = "choices";
   String KEY_REAL_ID_STRING = "realIDstring";
}
