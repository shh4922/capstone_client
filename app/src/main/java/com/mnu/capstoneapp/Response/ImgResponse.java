package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

import javax.xml.transform.Result;

public class ImgResponse {
    @SerializedName("result")
    private Result result;

    @SerializedName("boxes")
    private List<int[]> boxes;

    @SerializedName("recognition_words")
    private String[] recognition_words;


    public ImgResponse(Result result,List<int[]> boxes, String[] recognition_words){
        this.result= result;
        this.boxes = boxes;
        this.recognition_words =recognition_words;
    }

    public Result getResult() {
        return result;
    }

    public void setResult(Result result) {
        this.result = result;
    }

    public List<int[]> getBoxes() {
        return boxes;
    }

    public void setBoxes(List<int[]> boxes) {
        this.boxes = boxes;
    }

    public String[] getRecognition_words() {
        return recognition_words;
    }

    public void setRecognition_words(String[] recognition_words){
        this.recognition_words=recognition_words;
    }



}
