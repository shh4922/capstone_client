package com.mnu.capstoneapp.Response;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ImgResponse {
    @SerializedName("result")
    public List<Result> result= null;



    public class Result{
        @SerializedName("boxes")
        public List<int[]> boxes;

        @SerializedName("recognition_words")
        public String[] recognition_words;


    }



}



