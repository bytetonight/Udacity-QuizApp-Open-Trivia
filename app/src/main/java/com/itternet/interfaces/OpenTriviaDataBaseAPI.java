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

import com.itternet.models.QuestionsListData;
import com.itternet.models.QuizSessionToken;
import com.itternet.models.QuizSessionTokenReset;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dns on 04.03.2017.
 */

public interface OpenTriviaDataBaseAPI {
    @GET("api_token.php?command=reset")
    Call<QuizSessionTokenReset> resetToken(@Query("token") String token);

    @GET("api_token.php?command=request")
    Call<QuizSessionToken> getQuizSessionToken();

    @GET("api.php")
    Call<QuestionsListData> getQuizQuestions(@Query("category") Integer category,
                                             @Query("amount") int amount,
                                             @Query("token") String token,
                                             @Query("difficulty") String difficulty,
                                             @Query("type") String type);
}
