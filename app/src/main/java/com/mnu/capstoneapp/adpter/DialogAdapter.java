package com.mnu.capstoneapp.adpter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RunningItemList;
import com.mnu.capstoneapp.fragement.CustomeDialogFragment;

import java.util.ArrayList;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {

    private ArrayList<RunningItemList> runningItemLists;

    public DialogAdapter(ArrayList<RunningItemList> runningItemLists) {
        this.runningItemLists = runningItemLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textdata, parent, false);
        return new ViewHolder(v);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        holder.et_items.setText(runningItemLists.get(position).getItem_name());
    }


    @Override
    public int getItemCount() {
        return runningItemLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText et_items;

        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            et_items = (EditText) itemview.findViewById(R.id.et_items);
        }
    }
}
