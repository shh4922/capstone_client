package com.mnu.capstoneapp;

import com.mnu.capstoneapp.Response.ImgResponse;
import com.mnu.capstoneapp.Response.LoginResponse;

import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.POST;

public interface APIservice {

    //로그인
    @POST("/login/")
    Call<LoginResponse> getLoginResponse(@Body Map request);


    //회원가입
    @POST("/signup/")
    Call<LoginResponse> getSignupResponse(@Body Map request);



    //이미지 데이터 전송



    @Headers({
            "Content-Type: multipart/form-data",
            "Authorization: KakaoAK 9a1ca247a8a58968ceef53e69d4187ef"
    })
    @POST("v2/vision/text/ocr/")
    Call<ImgResponse> getImgResponse(@Body String str);
}
