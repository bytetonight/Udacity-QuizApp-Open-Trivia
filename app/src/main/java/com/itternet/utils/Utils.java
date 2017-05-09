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

package com.itternet.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.example.com.quizapp.R;
import android.text.Html;
import android.text.Spanned;
import android.util.Log;

import static android.R.attr.key;

/**
 * Created by ByteTonight on 07.05.2017.
 */

public class Utils
{

    public static final String NUMBER_OF_QUESTIONS = "NumberOfQuestions";
    public static final String DIFFICULTY = "difficulty";
    public static final String CATEGORY_ID = "categoryID";
    public static final String CATEGORY_NAME = "categoryName";

    /**
     * Yet another deprecation in Android-N to work around
     * @param html is the string that might contain unwanted HTML entities
     * @return
     */
    @SuppressWarnings("deprecation")
    public static Spanned fromHtml(String html)
    {
        Spanned result;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N)
        {
            result = Html.fromHtml(html, Html.FROM_HTML_MODE_LEGACY);
        }
        else
        {
            result = Html.fromHtml(html);
        }
        return result;
    }


    /**
     * Store key,value pairs in Android Shared Preferences
     * @param key to store
     * @param value to store
     */
    public static void writeStringToPreferences(Context context, String key, String value)
    {

        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        SharedPreferences.Editor editor = sharedPref.edit();
        editor.putString(key, value);
        editor.apply();
        Log.v("writePreferences", key +" : " +value);
    }


    /**
     * Read key,value pairs from Android Shared Preferences
     * @param key to read
     * @return
     */
    public static String readStringFromPreferences(Context context, String key)
    {
        SharedPreferences sharedPref = context.getSharedPreferences(
                context.getString(R.string.preference_file_key), Context.MODE_PRIVATE);

        String returnData = sharedPref.getString(key, null);
        //Let's see what we got from shared preferences
        Log.v("readPreferences", key + " = " + returnData);
        return returnData;
    }
}
