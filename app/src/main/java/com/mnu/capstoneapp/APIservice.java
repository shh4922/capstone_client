package com.mnu.capstoneapp;

import com.mnu.capstoneapp.Response.LoginResponse;

import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface APIservice {

    //로그인
    @POST("/login/")
    Call<LoginResponse> getLoginResponse(@Body Map request);


    //회원가입
    @POST("/signup/")
    Call<LoginResponse> getSignupResponse(@Body Map request);

}
