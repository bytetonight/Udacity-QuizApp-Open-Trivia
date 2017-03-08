package com.itternet.interfaces;

import android.example.com.quizapp.MainActivity;

import com.itternet.models.QuestionsListData;
import com.itternet.models.QuizSessionToken;

import retrofit2.Call;
import retrofit2.http.GET;
import retrofit2.http.Query;

/**
 * Created by dns on 04.03.2017.
 */

public interface OpenTriviaDataBaseAPI {
    @GET("api_token.php?command=request")
    Call<QuizSessionToken> getQuizSessionToken();

    @GET("api.php?category=18&difficulty=easy&type=multiple")
    Call<QuestionsListData> getQuizQuestions(@Query("amount") int amount, @Query("token") String token);

}
