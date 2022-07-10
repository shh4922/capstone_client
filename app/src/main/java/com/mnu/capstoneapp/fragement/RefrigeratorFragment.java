package com.mnu.capstoneapp.fragement;

import android.content.Context;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.RefrigeratorData;

import java.util.ArrayList;

public class RefrigeratorFragment extends Fragment {

    private RecyclerView rv_refrigerator;
    private RecyclerView.Adapter mAdapter;
    private RecyclerView.LayoutManager mLayoutManager;

    private ArrayList<RefrigeratorData> refrigeratorData;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);

        if(view instanceof RecyclerView){
            Context context = view.getContext();
            RecyclerView mRecyclerView = (RecyclerView) view;
            mRecyclerView.setHasFixedSize(true);
        }



        rv_refrigerator = view.findViewById(R.id.rv_refrigerator);
        refrigeratorData = new ArrayList<>();


        return view;
    }



}