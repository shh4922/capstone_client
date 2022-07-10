package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

public class TextDataResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;


    public TextDataResponse(String code,String msg){
        this.code = code;
        this.msg =msg;
    }


    public String getMsg() {
        return msg;
    }
    public void setMsg(String msg) {
        this.msg = msg;
    }
    public String getCode() {
        return code;
    }
    public void setCode(String code) {
        this.code = code;
    }
}
