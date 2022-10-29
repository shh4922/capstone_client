package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;


/***
 * 로그인&회원가입 인증을 위한곳
 */
public class LoginResponse {
    @SerializedName("code")
    private String code;
    @SerializedName("msg")
    private String msg;


    public LoginResponse(String code,String msg){
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
