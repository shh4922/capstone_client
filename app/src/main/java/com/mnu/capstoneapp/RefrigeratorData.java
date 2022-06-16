package com.mnu.capstoneapp;

public class RefrigeratorData {

    private String tv_itemname;
    private String tv_date;
    private String tv_count;

    public RefrigeratorData(String tv_itemname, String tv_date, String tv_count) {
        this.tv_itemname = tv_itemname;
        this.tv_date = tv_date;
        this.tv_count = tv_count;
    }
    public String getTv_itemname() {
        return tv_itemname;
    }

    public void setTv_itemname(String tv_itemname) {
        this.tv_itemname = tv_itemname;
    }

    public String getTv_date() {
        return tv_date;
    }

    public void setTv_date(String tv_date) {
        this.tv_date = tv_date;
    }

    public String getTv_count() {
        return tv_count;
    }

    public void setTv_count(String tv_count) {
        this.tv_count = tv_count;
    }


}
