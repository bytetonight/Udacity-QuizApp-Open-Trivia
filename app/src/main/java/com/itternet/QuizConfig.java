package com.itternet;

/**
 * Created by dns on 09.03.2017.
 */

public class QuizConfig {
    private static String apiBaseURL;
    private static int currentQuestionIndex = 0;
    private static int lastQuestionIndex = 0;
    private static String correctAnswer;
    private static int amountOfQuestions = 10;
    private static String difficulty = null;//"any";
    private static String questionType = null;//"any";
    private static String sessionToken = null;

    public static String getSessionToken() {
        return sessionToken;
    }

    public static void setSessionToken(String sessionToken) {
        QuizConfig.sessionToken = sessionToken;
    }

    public static String getApiBaseURL() {
        return apiBaseURL;
    }

    public static void setApiBaseURL(String apiBaseURL) {
        QuizConfig.apiBaseURL = apiBaseURL;
    }

    public static int getCurrentQuestionIndex() {
        return currentQuestionIndex;
    }

    public static void ResetCurrentQuestionIndex() {
        currentQuestionIndex = 0;
    }

    public static void setNextQuestionIndex()
    {
        ++currentQuestionIndex;
    }

    public static int getLastQuestionIndex() {
        return lastQuestionIndex;
    }

    public static void setLastQuestionIndex(int index) {
        lastQuestionIndex = index;
    }

    public static String getCorrectAnswer() {
        return correctAnswer;
    }

    public static void setCorrectAnswer(String correctAnswer) {
        QuizConfig.correctAnswer = correctAnswer;
    }

    public static boolean isCorrectAnswer(String answer)
    {
        return answer.equals(correctAnswer);
    }

    public static int getAmountOfQuestions() {
        return amountOfQuestions;
    }

    public static void setAmountOfQuestions(int amountOfQuestions) {
        QuizConfig.amountOfQuestions = amountOfQuestions;
    }

    public static String getDifficulty() {
        return difficulty;
    }

    public static void setDifficulty(String difficulty) {
        QuizConfig.difficulty = difficulty;
    }

    public static String getQuestionType() {
        return questionType;
    }

    public static void setQuestionType(String questionType) {
        QuizConfig.questionType = questionType;
    }
}
