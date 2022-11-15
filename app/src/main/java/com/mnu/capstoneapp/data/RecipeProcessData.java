package com.mnu.capstoneapp.data;

public class RecipeProcessData {
    private String order;
    private String process;

    public RecipeProcessData(String order, String process){
        this.order=order;
        this.process=process;
    }
    public String getOrder() {
        return order;
    }

    public void setOrder(String order) {
        this.order = order;
    }

    public String getProcess() {
        return process;
    }

    public void setProcess(String process) {
        this.process = process;
    }
}
