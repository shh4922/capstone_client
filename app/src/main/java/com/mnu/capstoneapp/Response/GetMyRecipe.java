package com.mnu.capstoneapp.Response;

import com.google.gson.annotations.SerializedName;

import java.util.List;

/***
 * 나의 가능레시피 데이터를 받아오는곳
 */
public class GetMyRecipe {

    @SerializedName("recipe_list")
    private List<Recipe> recipe_list;

    public class Recipe{
        @SerializedName("recipe_name")
        public String recipe_name;

        @SerializedName("points")
        public String points;
    }

    public List<Recipe> getRecipe_list() {
        return recipe_list;
    }

    public void setRecipe_list(List<Recipe> recipe_list) {
        this.recipe_list = recipe_list;
    }
}
