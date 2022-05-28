package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class ImgResponse {
    @SerializedName("boxes")
    private List<int[]> boxes;
    @SerializedName("recognition_words")
    private String[] recognition_words;


    public ImgResponse(List<int[]> boxes, String[] recognition_words){
        this.boxes = boxes;
        this.recognition_words =recognition_words;
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

    public void setRecognition_words(String[] recognition_words) {
        this.recognition_words = recognition_words;
    }
}
