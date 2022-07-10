package com.mnu.capstoneapp;

import android.media.Image;

import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.Base64;
import com.google.firebase.crashlytics.buildtools.reloc.org.apache.commons.codec.binary.BinaryCodec;
import com.mnu.capstoneapp.Response.ImgResponse;
import com.mnu.capstoneapp.Response.LoginResponse;
import com.mnu.capstoneapp.Response.TextDataResponse;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileOutputStream;
import java.nio.Buffer;
import java.nio.ByteBuffer;
import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.Header;
import retrofit2.http.Headers;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;

public interface APIservice {

    //로그인
    @POST("/login/")
    Call<LoginResponse> getLoginResponse(@Body Map request);


    //회원가입
    @POST("/signup/")
    Call<LoginResponse> getSignupResponse(@Body Map request);


    //텍스트결과 보내기
    @POST("/textrunning")
    Call<TextDataResponse> getResultTexts(@Body Map request);

    //이미지 데이터 전송
    @Multipart
    @POST("v2/vision/text/ocr")
    Call<ImgResponse> getImgResponse(@Header("Authorization")String appkey,@Part MultipartBody.Part image);
}
