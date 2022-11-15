package com.mnu.capstoneapp.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RecipeProcessData;

import java.util.ArrayList;

public class ProcessDialogAdapter extends RecyclerView.Adapter<ProcessDialogAdapter.ViewHolder> {

    private ArrayList<RecipeProcessData> recipeProcessData;
    public ProcessDialogAdapter(ArrayList<RecipeProcessData> recipeProcessData) {
        this.recipeProcessData = recipeProcessData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_process,parent,false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.tv_order.setText(recipeProcessData.get(position).getOrder());
        holder.tv_process.setText(recipeProcessData.get(position).getProcess());
    }



    @Override
    public int getItemCount() {

        return recipeProcessData.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder{
        private TextView tv_order;
        private TextView tv_process;
        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            tv_order = (TextView) itemView.findViewById(R.id.recipe_order);
            tv_process= (TextView) itemView.findViewById(R.id.recipe_process);
        }
    }
}
