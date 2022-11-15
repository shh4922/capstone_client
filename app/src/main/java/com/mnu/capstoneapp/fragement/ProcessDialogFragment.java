package com.mnu.capstoneapp.fragement;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.RecipeProcessResponse;
import com.mnu.capstoneapp.adpter.DialogAdapter;
import com.mnu.capstoneapp.adpter.ProcessDialogAdapter;
import com.mnu.capstoneapp.data.RecipeProcessData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class ProcessDialogFragment extends DialogFragment implements View.OnClickListener{

    private Button btn_cancle;
    private Button btn_useItem;
    private String recipename;
    RecyclerView recyclerView;
    ProcessDialogAdapter adapter;
    ArrayList<RecipeProcessData> processDataArrayList = new ArrayList<>();



    public ProcessDialogFragment(String recipename){
        this.recipename=recipename;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipedialog, container, false);
        btn_cancle = view.findViewById(R.id.btn_cancle);
        btn_useItem = view.findViewById(R.id.btn_useRecipe);
        btn_cancle.setOnClickListener(this);
        btn_useItem.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rc_recipeProcess);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        getRecipeProcess();

    }
    private void getRecipeProcess(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice service = retrofit.create(APIservice.class);
        //보낼요청에 사용자 아이디넣어서 보냄
        Map request = new LinkedHashMap();
        request.put("recipename",recipename);

        service.getRecipeProcess(request).enqueue(new Callback<RecipeProcessResponse>() {
            @Override
            public void onResponse(Call<RecipeProcessResponse> call, Response<RecipeProcessResponse> response) {
                List<RecipeProcessResponse.RecipeProcess> result = response.body().getProcesslist();
                for(int i=0;i<result.size();i++){
                    processDataArrayList.add(new RecipeProcessData(result.get(i).order,result.get(i).process));
                }
                onResume();
            }

            @Override
            public void onFailure(Call<RecipeProcessResponse> call, Throwable t) {

            }
        });
    }

    @Override
    public void onResume() {
        super.onResume();
        adapter = new ProcessDialogAdapter(processDataArrayList);
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

    }
}
