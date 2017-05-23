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

package com.itternet.models;


import android.os.Parcel;
import android.os.Parcelable;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * This is actually a Model of a Question list where list
 * holds Models of Result which holds questions which hold answers
 * and to allow storing the instance of this class into
 * savedInstanceState, you need to implement Parcelable
 * which is an Interface forcing you by design to implement lots of other methods
 * Shall I go on ?
 */
public class QuestionsListData implements Parcelable {

    public static final Creator<QuestionsListData> CREATOR = new Creator<QuestionsListData>() {
        @Override
        public QuestionsListData createFromParcel(Parcel in) {
            return new QuestionsListData(in);
        }

        @Override
        public QuestionsListData[] newArray(int size) {
            return new QuestionsListData[size];
        }
    };
    //SerializedName needs to be used only when the generated property name you are trying to map,
    // does not match the actual JSON field name
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @Expose
    private List<Result> results = null;

    protected QuestionsListData(Parcel in) {
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
    }

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Result> getResults() {
        return results;
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }

    public Result getResultAtIndex(int index) {
        if (index >= 0 && index <= results.size() - 1)
            return results.get(index);
        else
            return results.get(results.size() - 1);
    }


}
