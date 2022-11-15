package com.mnu.capstoneapp.fragement;

import android.app.Dialog;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.adpter.DialogAdapter;
import com.mnu.capstoneapp.data.RunningItemList;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class CustomeDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button btn_save;
    RecyclerView recyclerView;
    DialogAdapter adapter;
    ArrayList<RunningItemList> runningItemLists = new ArrayList<>();

    //생성시, Arraylist를 받음
    public CustomeDialogFragment(ArrayList<RunningItemList> runningItemLists) {
        this.runningItemLists = runningItemLists;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_customdialog, container, false);
        btn_save = view.findViewById(R.id.btn_save);
        btn_save.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rc_runningData);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new DialogAdapter(runningItemLists);
        RecyclerView.LayoutManager layoutManager =new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }



    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.btn_save:{
                adapter.saveItem();
                dismiss();
                break;
            }
        }
    }
}
