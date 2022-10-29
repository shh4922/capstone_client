package com.mnu.capstoneapp.data;

public class RefrigeratorData {


    private String tv_itemname;
    private String tv_itemdate;
    private String tv_itemcounts;

    public RefrigeratorData(String tv_itemname, String tv_itemdate, String tv_itemcounts) {
        this.tv_itemname = tv_itemname;
        this.tv_itemdate = tv_itemdate;
        this.tv_itemcounts = tv_itemcounts;
    }

    public String getTv_itemname() {
        return tv_itemname;
    }

    public void setTv_itemname(String tv_itemname) {
        this.tv_itemname = tv_itemname;
    }

    public String getTv_itemdate() {
        return tv_itemdate;
    }

    public void setTv_itemdate(String tv_itemdate) {
        this.tv_itemdate = tv_itemdate;
    }

    public String getTv_itemcounts() {
        return tv_itemcounts;
    }

    public void setTv_itemcounts(String tv_itemcounts) {
        this.tv_itemcounts = tv_itemcounts;
    }
}
