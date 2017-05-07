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

public interface OpenTriviaDataBaseAPI
{
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
