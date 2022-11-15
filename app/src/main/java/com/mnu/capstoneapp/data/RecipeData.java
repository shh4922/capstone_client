package com.mnu.capstoneapp.data;

public class RecipeData {

    private String tv_recipe_name;
    private String tv_points;

    public String getTv_points() {
        return tv_points;
    }

    public void setTv_points(String tv_points) {
        this.tv_points = tv_points;
    }

    public RecipeData(String tv_recipe_name,String tv_points){
        this.tv_recipe_name=tv_recipe_name;
        this.tv_points=tv_points;
    }

    public String getTv_recipe_name() {
        return tv_recipe_name;
    }

    public void setTv_recipe_name(String tv_recipe_name) {
        this.tv_recipe_name = tv_recipe_name;
    }
}
