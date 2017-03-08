package com.itternet.models;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * Created by dns on 08.03.2017.
 */

public class QuizSessionTokenReset {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;

    @SerializedName("token")
    @Expose
    private String token;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }


    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }
}
