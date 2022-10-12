package com.mnu.capstoneapp.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.google.firebase.crashlytics.buildtools.reloc.com.google.common.util.concurrent.ExecutionError;
import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.GetMyRefrigerator;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RefrigeratorData;
import com.mnu.capstoneapp.RefrigeratrotAdapter;

import java.io.IOException;
import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefrigeratorFragment extends Fragment {

    //리사이클러뷰 생성
    public RecyclerView rc_refrigerater;
    //어댑터 생성
    public RecyclerView.Adapter adapter_refrigerater;
    //냉장고 데이터 생성
    public ArrayList<RefrigeratorData> arylist_refrigerator= new ArrayList<>();
    //통신후 얻은 item[]를 저장하는 공간
    List<String> result_list = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("로그", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);
        rc_refrigerater = (RecyclerView) view.findViewById(R.id.rv_refrigerator);
        rc_refrigerater.setHasFixedSize(true);


        return view;
    }

    public void sendToServer() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.28.64:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice getItems = retrofit.create(APIservice.class);

        //보낼요청에 사용자 아이디넣어서 보냄
        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);
        /***
         *  기존에 사용하던 비동기로 처리방식
         */

        getItems.getMyRefrigerator(request).enqueue(new Callback<GetMyRefrigerator>() {
            @Override
            public void onResponse(Call<GetMyRefrigerator> call, Response<GetMyRefrigerator> response) {
                result_list = response.body().getItems();
                for (int i = 0; i < result_list.size(); i++) {
                    arylist_refrigerator.add(new RefrigeratorData(result_list.get(i)));
                    Log.e("로그", "arylist_refrigerator " + i + " : " + arylist_refrigerator.get(i).getTv_itemname());
                }
                if (arylist_refrigerator.isEmpty()) {
                    Log.e("로그", "result2 is empty_2");
                } else {
                    Log.e("로그", "result2 is not empty_2");
                }
            }
            @Override
            public void onFailure(Call<GetMyRefrigerator> call, Throwable t) {
                Log.e("로그", "서버에서 내냉장고 아이탬 가져오기실패 : ", t);

            }
        });

        /***
         * 동기로 처리하는 방법 테스트해봐야함
         */
        Call<GetMyRefrigerator> callSync = getItems.getMyRefrigerator(request);
        try {
            Response<GetMyRefrigerator> response = callSync.execute();
            GetMyRefrigerator result_list = response.body();


        }catch (IOException e){
            Log.e("로그","동기 연결실패",e);
        }




    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("로그", "onCreate실행");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("로그", "onStart");


    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("로그", "onResume");
        /**
         * 서버로 데이터 요청
         */
        Log.e("로그",LoginActivity.userid_local);
        sendToServer();

        /***
         * recycleview에 데이터전송
         */
        adapter_refrigerater = new RefrigeratrotAdapter(arylist_refrigerator);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        rc_refrigerater.setLayoutManager(mlayoutManager);
        rc_refrigerater.setAdapter(adapter_refrigerater);
    }

    @Override
    public void onStop() {
        super.onStop();
        //화면이 안보일때 데이터를 clear
        //arylist_refrigerator.clear();
    }
}