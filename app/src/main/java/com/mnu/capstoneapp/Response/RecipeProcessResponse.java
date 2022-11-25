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



    @SerializedName("need_items")
    private List<NeedItem> needitems;
    public class NeedItem{
        @SerializedName("itemname")
        public String itemname;

        @SerializedName("have")
        public String have;
    }

    public List<NeedItem> getNeeditems() {
        return needitems;
    }

    public void setNeeditems(List<NeedItem> needitems) {
        this.needitems = needitems;
    }
}
