package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

public class RecipeProcessResponse {
    @SerializedName("processlist")
    private List<RecipeProcess> processlist;

    public class RecipeProcess{
        @SerializedName("order")
        public String order;
        @SerializedName("process")
        public String process;
    }

    public List<RecipeProcess> getProcesslist() {
        return processlist;
    }

    public void setProcesslist(List<RecipeProcess> processlist) {
        this.processlist = processlist;
    }
}
