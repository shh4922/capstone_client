package com.mnu.capstoneapp.adpter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.FloatData;

import java.util.ArrayList;

public class FloatButtonAdapter extends RecyclerView.Adapter<FloatButtonAdapter.ViewHolder> {

    ArrayList<FloatData> floatData =null;
    private boolean isTextChange =false;

    public FloatButtonAdapter(ArrayList<FloatData> floatData){
        this.floatData=floatData;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_float,parent,false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        String et_itemname = floatData.get(position).getItem_name();
        holder.et_itemname.setText(et_itemname);
        String et_counts = floatData.get(position).getCount();
        holder.et_counts.setText(et_counts);

    }

    @Override
    public int getItemCount() {
        return floatData.size();
    }

    public class ViewHolder extends  RecyclerView.ViewHolder{

        private EditText et_itemname;
        private EditText et_counts;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            et_itemname = (EditText) itemView.findViewById(R.id.item_name);
            et_counts = (EditText) itemView.findViewById(R.id.item_count);

            et_itemname.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    isTextChange = true;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (isTextChange) {
                        isTextChange = false;
                        try {
                            floatData.get(getAdapterPosition()).setItem_name(et_itemname.getText().toString());
                        } catch (Exception e) {
                            Log.e("로그", "텍스트수정에러", e);
                        } finally {

                        }

                    }
                }
            });
            et_counts.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    isTextChange = true;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (isTextChange) {
                        isTextChange = false;
                        try {
                            floatData.get(getAdapterPosition()).setCount(et_counts.getText().toString());
                        } catch (Exception e) {
                            Log.e("로그", "텍스트수정에러", e);
                        } finally {

                        }

                    }
                }
            });

        }
    }

    public void addItem(FloatData data){
        floatData.add(data);
        notifyDataSetChanged();
    }

    public ArrayList<FloatData> returnItem(){
        return floatData;
    }


}
