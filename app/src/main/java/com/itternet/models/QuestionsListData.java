package com.itternet.models;

/**
 * Created by dns on 04.03.2017.
 */

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

    protected QuestionsListData(Parcel in)
    {
    }

    public static final Creator<QuestionsListData> CREATOR = new Creator<QuestionsListData>()
    {
        @Override
        public QuestionsListData createFromParcel(Parcel in)
        {
            return new QuestionsListData(in);
        }

        @Override
        public QuestionsListData[] newArray(int size)
        {
            return new QuestionsListData[size];
        }
    };

    @Override
    public int describeContents()
    {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags)
    {
    }

    //SerializedName needs to be used only when the generated property name you are trying to map,
    // does not match the actual JSON field name
    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @Expose
    private List<Result> results = null;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public List<Result> getResults() {
        return results;
    }

    public Result getResultAtIndex(int index)
    {
        if (index >= 0 && index <= results.size()-1)
            return results.get(index);
        else
            return results.get(results.size()-1);
    }

    public void setResults(List<Result> results) {
        this.results = results;
    }


}


/*
* {"response_code":0,"results":[{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"Which company was established on April 1st, 1976 by Steve Jobs, Steve Wozniak and Ronald Wayne?","correct_answer":"Apple","incorrect_answers":["Microsoft","Atari","Commodore"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"The programming language &#039;Swift&#039; was created to replace what other programming language?","correct_answer":"Objective-C","incorrect_answers":["C#","Ruby","C++"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"What amount of bits commonly equals one byte?","correct_answer":"8","incorrect_answers":["1","2","64"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"What is the code name for the mobile operating system Android 7.0?","correct_answer":"Nougat","incorrect_answers":["Ice Cream Sandwich","Jelly Bean","Marshmallow"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"How many kilobytes in one gigabyte?","correct_answer":"1048576","incorrect_answers":["1024","1000","1000000"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"On Twitter, what is the character limit for a Tweet?","correct_answer":"140","incorrect_answers":["120","160","100"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"In &quot;Hexadecimal&quot;, what color would be displayed from the color code? &quot;#00FF00&quot;?","correct_answer":"Green","incorrect_answers":["Red","Blue","Yellow"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"Which computer language would you associate Django framework with?","correct_answer":"Python","incorrect_answers":["C#","C++","Java"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"What does LTS stand for in the software market?","correct_answer":"Long Term Support","incorrect_answers":["Long Taco Service","Ludicrous Transfer Speed","Ludicrous Turbo Speed"]},{"category":"Science: Computers","type":"multiple","difficulty":"easy","question":"The numbering system with a radix of 16 is more commonly referred to as ","correct_answer":"Hexidecimal","incorrect_answers":["Binary","Duodecimal","Octal"]}]}
* */