package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/***
 * text들을 넣았을때  데이터들을 응답으로 만듬.
 */
public class TextDataResponse {
    @SerializedName("item_list")
    private List<ItemList> text_items =null;

    public class ItemList{
        @SerializedName("item_name")
        public String item_name;

    }

    public List<ItemList> getText_items() {
        return text_items;
    }

    public void setText_items(List<ItemList> text_items) {
        this.text_items = text_items;
    }
}
