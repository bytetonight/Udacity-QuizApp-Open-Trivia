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


/**
 * Wrapper for the custom response codes coming from Open Trivia Database API
 */
public class OpenTDbResponse
{
    /**
     * Returned results successfully.
     */
    public static final int RESPONSE_CODE_SUCCESS = 0;

    /**
     * Could not return results. The API doesn't have enough questions for your query.
     * (Ex. Asking for 50 Questions in a Category that only has 20.)
     */
    public static final int RESPONSE_CODE_NO_RESULTS = 1;

    /**
     * Contains an invalid parameter. Arguments passed in aren't valid. (Ex. Amount = Five)
     */
    public static final int RESPONSE_CODE_INVALID_PARAM = 2;

    /**
     * Session Token does not exist.
     */
    public static final int RESPONSE_CODE_TOKEN_NOT_FOUND = 3;

    /**
     * Session Token has returned all possible questions for the specified query. Resetting the Token is necessary.
     */
    public static final int RESPONSE_CODE_TOKEN_EMPTY = 4;
}
