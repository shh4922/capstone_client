package com.mnu.capstoneapp.fragement;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AlertDialog;
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.FragmentActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.Interface.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.RecipeProcessResponse;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
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

public class ProcessDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button btn_cancle;
    private Button btn_useItem;
    private String recipename;
    RecyclerView recyclerView;
    ProcessDialogAdapter adapter;
    ArrayList<RecipeProcessData> processDataArrayList = new ArrayList<>();


    public ProcessDialogFragment(String recipename) {
        this.recipename = recipename;
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

    private void getRecipeProcess() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice service = retrofit.create(APIservice.class);
        //보낼요청에 사용자 아이디넣어서 보냄
        Map request = new LinkedHashMap();
        request.put("recipename", recipename);

        service.getRecipeProcess(request).enqueue(new Callback<RecipeProcessResponse>() {
            @Override
            public void onResponse(Call<RecipeProcessResponse> call, Response<RecipeProcessResponse> response) {
                List<RecipeProcessResponse.RecipeProcess> result = response.body().getProcesslist();
                for (int i = 0; i < result.size(); i++) {
                    processDataArrayList.add(new RecipeProcessData(result.get(i).order, result.get(i).process));
                }
                if(processDataArrayList!=null){
                    onResume();
                }else {
                    new AlertDialog.Builder(getContext())
                            .setTitle("알림!")
                            .setMessage("아직 준비중입니다ㅠㅠ")
                            .create()
                            .show();
                }

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
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onStop() {
        super.onStop();
    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.btn_cancle: {
                dismiss();
                Toast.makeText(getContext(), "눈팅했습니다 ㅎ", Toast.LENGTH_SHORT).show();
                break;
            }
            case R.id.btn_useRecipe: {
                useItemSendToServer();
                break;
            }
        }
    }

    private void useItemSendToServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);

        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);
        request.put("recipename", recipename);

        service.useToItem(request).enqueue(new Callback<dafaultResponce>() {
            @Override
            public void onResponse(Call<dafaultResponce> call, Response<dafaultResponce> response) {
                switch (response.body().getCode()) {
                    case "0000": {
                        dismiss();
                        Toast.makeText(getContext(), "해당재료 사용합니다!", Toast.LENGTH_SHORT).show();
                        break;
                    }

                }
            }

            @Override
            public void onFailure(Call<dafaultResponce> call, Throwable t) {
                Log.e("로그", "아이탬사용 통신중 결함", t);
            }
        });
    }


}
