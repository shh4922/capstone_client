package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;


public class GetMyRefrigerator {

    @SerializedName("items")
    private List<String> items = null;

    public List<String> getItems() {
        return items;
    }

    public void setItems(List<String> items) {
        this.items = items;
    }
}
