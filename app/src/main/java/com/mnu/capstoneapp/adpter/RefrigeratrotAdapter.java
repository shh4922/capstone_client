package com.mnu.capstoneapp.adpter;

import android.app.Activity;
import android.util.Log;
import android.view.ContextMenu;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.Interface.LongClickInterface;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RefrigeratorData;

import java.util.ArrayList;

public class RefrigeratrotAdapter extends RecyclerView.Adapter<RefrigeratrotAdapter.ViewHolder> {

    private ArrayList<RefrigeratorData> refrigeratorData;
    int Mposition = RecyclerView.NO_POSITION;

    public int getPosition() {
        Log.e("로그", String.valueOf(Mposition));
        return Mposition;
    }

    public RefrigeratrotAdapter(ArrayList<RefrigeratorData> refrigeratorData) {
        this.refrigeratorData = refrigeratorData;

    }

    public void removeItem(int position) {
        refrigeratorData.remove(position);
        notifyDataSetChanged();
    }




    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_refrigerator, parent, false);
        LongClickInterface listener = new LongClickInterface() {
            @Override
            public void onItemClick(View v, int position) {
                Mposition=position;
            }
        };
        return new ViewHolder(v,listener);
    }


    @Override
    public void onBindViewHolder(@NonNull RefrigeratrotAdapter.ViewHolder holder, int position) {
        holder.tv_item_name.setText(refrigeratorData.get(position).getTv_itemname());
        holder.tv_item_date.setText(refrigeratorData.get(position).getTv_itemdate());
        holder.tv_item_counts.setText(refrigeratorData.get(position).getTv_itemcounts());

    }


    @Override
    public int getItemCount() {
        return refrigeratorData.size();
    }


    //이름,등록일,개수 만든거 구성
    public class ViewHolder extends RecyclerView.ViewHolder implements View.OnCreateContextMenuListener {
        private TextView tv_item_name;
        private TextView tv_item_date;
        private TextView tv_item_counts;

        /***
         * DB에서 데이터가져와서  holder에 저장
         * @param itemView
         */
        public ViewHolder(@NonNull View itemView,LongClickInterface listener) {
            super(itemView);
            itemView.setOnCreateContextMenuListener(this);
            tv_item_name = (TextView) itemView.findViewById(R.id.tv_item_name);
            tv_item_date = (TextView) itemView.findViewById(R.id.tv_item_date);
            tv_item_counts = (TextView) itemView.findViewById(R.id.tv_item_counts);

            itemView.setOnLongClickListener(new View.OnLongClickListener() {
                @Override
                public boolean onLongClick(View view) {
                    int position = getAdapterPosition();
                    if (position != RecyclerView.NO_POSITION) {
                        listener.onItemClick(itemView,position);
                    }

                    return false;
                }
            });

        }

        @Override
        public void onCreateContextMenu(ContextMenu contextMenu, View view, ContextMenu.ContextMenuInfo contextMenuInfo) {
            ((Activity) view.getContext()).getMenuInflater().inflate(R.menu.item_longclick, contextMenu);

        }
    }

    public void Refresh(){
        notifyDataSetChanged();
    }


}
