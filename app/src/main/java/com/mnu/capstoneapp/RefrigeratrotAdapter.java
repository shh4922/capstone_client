package com.mnu.capstoneapp;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.data.RefrigeratorData;

import java.util.ArrayList;

public class RefrigeratrotAdapter extends RecyclerView.Adapter<RefrigeratrotAdapter.ViewHolder> {

    private ArrayList<RefrigeratorData> refrigeratorData;
    private Context mContext;


    @NonNull
    @Override
    public RefrigeratrotAdapter.ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refrigerator, parent, false);
        return new ViewHolder(v);
    }


    @Override
    public void onBindViewHolder(@NonNull RefrigeratrotAdapter.ViewHolder holder, int position) {
        holder.tv_item_name.setText(refrigeratorData.get(position).getTv_itemname());
        holder.tv_item_count.setText(refrigeratorData.get(position).getTv_count());
        holder.tv_item_date.setText(refrigeratorData.get(position).getTv_date());
    }


    @Override
    public int getItemCount() {
        return refrigeratorData.size();
    }


    //이름,등록일,개수 만든거 구성
    public class ViewHolder extends RecyclerView.ViewHolder {
        private TextView tv_item_name;
        private TextView tv_item_date;
        private TextView tv_item_count;

        /***
         * DB에서 데이터가져와서  holder에 저장
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView) {
            super(itemView);

            tv_item_name = itemView.findViewById(R.id.tv_item_name);
            tv_item_date = itemView.findViewById(R.id.tv_item_date);
            tv_item_count = itemView.findViewById(R.id.tv_item_count);
        }
    }

    public void addItem(RefrigeratorData data){
        refrigeratorData.add(data);
    }

}
