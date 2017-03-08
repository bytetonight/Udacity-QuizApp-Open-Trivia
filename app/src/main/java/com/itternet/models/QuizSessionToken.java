package com.itternet.models;

/**
 * Created by dns on 05.03.2017.
 */

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

/**
 * {"response_code":0,"response_message":"Token Generated Successfully!","token":"27f58e149f95f2b3bc1a5e8ba5dd2e31621fe1ec2635ae258e2835b4bc9e15c2"}
 */
public class QuizSessionToken {

    @SerializedName("response_code")
    @Expose
    private Integer responseCode;
    @SerializedName("response_message")
    @Expose
    private String responseMessage;
    @SerializedName("token")
    @Expose
    private String token;

    public Integer getResponseCode() {
        return responseCode;
    }

    public void setResponseCode(Integer responseCode) {
        this.responseCode = responseCode;
    }

    public String getResponseMessage() {
        return responseMessage;
    }

    public void setResponseMessage(String responseMessage) {
        this.responseMessage = responseMessage;
    }

    public String getToken() {
        return token;
    }

    public void setToken(String token) {
        this.token = token;
    }

}
