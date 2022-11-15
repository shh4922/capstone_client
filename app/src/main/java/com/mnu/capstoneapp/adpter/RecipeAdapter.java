package com.mnu.capstoneapp.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RecipeData;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<RecipeData> recipeData;
    public RecipeAdapter(ArrayList<RecipeData> recipeData){
        this.recipeData=recipeData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_recipe_name.setText(recipeData.get(position).getTv_recipe_name());
        holder.tv_points.setText(recipeData.get(position).getTv_points());
    }

    @Override
    public int getItemCount() {
        return recipeData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_recipe_name;
        private TextView tv_points;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_recipe_name=(TextView) itemView.findViewById(R.id.tv_recipe_name);
            tv_points=(TextView) itemView.findViewById(R.id.tv_points);
        }
    }


}
