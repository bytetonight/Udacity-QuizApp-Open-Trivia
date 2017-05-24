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

package android.example.com.quizapp.udacity_required;

import android.example.com.quizapp.udacity_required.interfaces.BaseQuestion;
import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by ByteTonight on 22.05.2017.
 */

public class FreeTextResponseQnA implements BaseQuestion, Parcelable {
    public static final Creator<FreeTextResponseQnA> CREATOR =
            new Creator<FreeTextResponseQnA>() {

                @Override
                public FreeTextResponseQnA createFromParcel(Parcel source) {
                    return new FreeTextResponseQnA(source);
                }

                @Override
                public FreeTextResponseQnA[] newArray(int size) {
                    return new FreeTextResponseQnA[size];
                }
            };
    private String question;
    private String answer;

    public FreeTextResponseQnA(Parcel parcel) {

    }

    public FreeTextResponseQnA(String question, String answer) {
        this.question = question;
        this.answer = answer;
    }

    public String getQuestion() {
        return question;
    }

    public String getAnswer() {
        return answer;
    }

    /**
     * Describe the kinds of special objects contained in this Parcelable
     * instance's marshaled representation. For example, if the object will
     * include a file descriptor in the output of {@link #writeToParcel(Parcel, int)},
     * the return value of this method must include the
     * {@link #CONTENTS_FILE_DESCRIPTOR} bit.
     *
     * @return a bitmask indicating the set of special object types marshaled
     * by this Parcelable object instance.
     * @see #CONTENTS_FILE_DESCRIPTOR
     */
    @Override
    public int describeContents() {
        return 0;
    }

    /**
     * Flatten this object in to a Parcel.
     *
     * @param dest  The Parcel in which the object should be written.
     * @param flags Additional flags about how the object should be written.
     *              May be 0 or {@link #PARCELABLE_WRITE_RETURN_VALUE}.
     */
    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(question);
        dest.writeString(answer);
    }
}
