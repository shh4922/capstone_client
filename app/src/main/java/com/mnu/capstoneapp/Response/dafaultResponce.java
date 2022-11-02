package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

public class dafaultResponce {

    @SerializedName("code")
    private String code;

    public String getCode() {
        return code;
    }

    public void setCode(String code) {
        this.code = code;
    }
}
