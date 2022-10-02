package com.mnu.capstoneapp.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.data.RefrigeratorData;
import com.mnu.capstoneapp.RefrigeratrotAdapter;

import java.util.ArrayList;

public class RefrigeratorFragment extends Fragment {

    public RecyclerView rc_refrigerater;
    public RecyclerView.Adapter adapter_refrigerater;
    private RecyclerView.LayoutManager mLayoutManager;
    private ArrayList<RefrigeratorData> refrigeratorData;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);

        initUI(view);

        return view;
    }

    public void initUI(View view){
//        rc_refrigerater = view.findViewById(R.id.rv_refrigerator);
//        mLayoutManager = new LinearLayoutManager(getContext());
//        rc_refrigerater.setLayoutManager(mLayoutManager);
//        adapter_refrigerater =new RefrigeratrotAdapter();
//
//
//        /***
//         * 디비에서 불러오는거 실행
//         */
//
//        rc_refrigerater.setAdapter(adapter_refrigerater);
    }


}