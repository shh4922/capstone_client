package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.ArrayList;
import java.util.List;


/***
 * 나의 냉장고 데이터를 받아오는 응답
 */
public class GetMyRefrigerator {
    @SerializedName("items")
    private List<OBG> items =null;
    
    public class OBG{
        @SerializedName("item_name")
        public String item_name;

        @SerializedName("item_date")
        public String item_date;

        @SerializedName("item_counts")
        public String item_counts;
    }

    public List<OBG> getItems() {
        return items;
    }

    public void setItems(List<OBG> items) {
        this.items = items;
    }
}
