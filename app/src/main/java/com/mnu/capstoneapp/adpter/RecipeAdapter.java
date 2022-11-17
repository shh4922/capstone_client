package com.mnu.capstoneapp.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RecipeData;
import com.mnu.capstoneapp.fragement.CustomeDialogFragment;
import com.mnu.capstoneapp.fragement.ProcessDialogFragment;

import java.util.ArrayList;

public class RecipeAdapter extends RecyclerView.Adapter<RecipeAdapter.ViewHolder> {

    private ArrayList<RecipeData> recipeData;
    int Mposition = RecyclerView.NO_POSITION;
    public RecipeAdapter(ArrayList<RecipeData> recipeData) {
        this.recipeData = recipeData;
    }

    private OnItemClickListener itemClickListener=null;

    //인터페이스 선언
    public interface OnItemClickListener{
        //클릭시 동작할 함수
        void onItemClick(View v, int pos);
    }
    public void setOnItemClickListener(OnItemClickListener listener){
        this.itemClickListener = listener;
    }


    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_recipe, parent, false);
        OnItemClickListener listener = new OnItemClickListener() {
            @Override
            public void onItemClick(View v, int position) {
                Mposition=position;
            }
        };
        return new ViewHolder(view,listener);
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
        private Button btn_recipeProcess;

        public ViewHolder(@NonNull View itemView, OnItemClickListener listener) {
            super(itemView);
            tv_recipe_name = (TextView) itemView.findViewById(R.id.tv_recipe_name);
            tv_points = (TextView) itemView.findViewById(R.id.tv_points);
            btn_recipeProcess = (Button) itemView.findViewById(R.id.btn_recipeProcess);

            btn_recipeProcess.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    //존재하는 포지션인지 확인
                    int pos = getAdapterPosition();
                    if(pos != RecyclerView.NO_POSITION){
                        //동작 호출 (onItemClick 함수 호출)
                        if(itemClickListener != null){
                            itemClickListener.onItemClick(view, pos);
                        }
                    }
                }
            });
        }

    }


}
