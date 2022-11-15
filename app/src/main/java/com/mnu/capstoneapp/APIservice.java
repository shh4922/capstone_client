package com.mnu.capstoneapp;

import com.mnu.capstoneapp.Response.GetMyRecipe;
import com.mnu.capstoneapp.Response.GetMyRefrigerator;
import com.mnu.capstoneapp.Response.ImgResponse;
import com.mnu.capstoneapp.Response.LoginResponse;
import com.mnu.capstoneapp.Response.TextDataResponse;
import com.mnu.capstoneapp.Response.dafaultResponce;

import java.util.Map;

import okhttp3.MultipartBody;
import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.DELETE;
import retrofit2.http.Header;
import retrofit2.http.Multipart;
import retrofit2.http.POST;
import retrofit2.http.Part;
import retrofit2.http.Path;

public interface APIservice {

    //로그인
    @POST("/login/")
    Call<LoginResponse> getLoginResponse(@Body Map request);


    //회원가입
    @POST("/signup/")
    Call<LoginResponse> getSignupResponse(@Body Map request);


    //텍스트결과 보내기
    @POST("/textrunning/")
    Call<TextDataResponse> getResultTexts(@Body Map<String,Object> request);

    //이미지 데이터 전송
    @Multipart
    @POST("v2/vision/text/ocr")
    Call<ImgResponse> getImgResponse(@Header("Authorization")String appkey,@Part MultipartBody.Part image);


    //내 냉장고 아이템 가져오기
    @POST("/getMyRefrigerator/")
    Call<GetMyRefrigerator> getMyRefrigerator(@Body Map request);


    @POST("/getMyRecipe/")
    Call<GetMyRecipe> getMyRecipe(@Body Map request);


    @POST("/removeItem/")
    Call<dafaultResponce> deleteMyItem(@Body Map request);

    @POST("/saveItem/")
    Call<dafaultResponce> saveData(@Body Map request);


    @POST("/updateItem/")
    Call<dafaultResponce> updateData(@Body Map request);
}
