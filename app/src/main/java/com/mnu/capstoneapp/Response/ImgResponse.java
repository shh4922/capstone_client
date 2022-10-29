package com.mnu.capstoneapp.Response;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

/***
 * 카카오로 이미지전송후, 응답으로온 text 와 좌표
 */
public class ImgResponse {
    @SerializedName("result")
    public List<Result> result = null;


    public class Result{
        @SerializedName("boxes")
        public List<int[]> boxes;

        @SerializedName("recognition_words")
        public String[] recognition_words;


    }



}





