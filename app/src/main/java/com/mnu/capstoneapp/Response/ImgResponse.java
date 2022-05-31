package com.mnu.capstoneapp.Response;

import com.google.gson.JsonArray;
import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;

import javax.xml.transform.Result;

public class ImgResponse {
    @SerializedName("result")
    private List<Result> result= null;

    public List<Result> getResult() {
        return result;
    }

    public void setResult(List<Result> result) {
        this.result = result;
    }


    public class Result{
        @SerializedName("boxes")
        private List<int[]> boxes;

        @SerializedName("recognition_words")
        private String[] recognition_words;

        public List<int[]> getBoxes() {
            return boxes;
        }

        public void setBoxes(List<int[]> boxes) {
            this.boxes = boxes;
        }

        public String[] getRecognition_words() {
            return recognition_words;
        }

        public void setRecognition_words(String[] recognition_words) {
            this.recognition_words = recognition_words;
        }


    }



}



