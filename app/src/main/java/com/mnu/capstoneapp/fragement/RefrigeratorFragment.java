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

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.GetMyRefrigerator;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RefrigeratorData;
import com.mnu.capstoneapp.RefrigeratrotAdapter;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RefrigeratorFragment extends Fragment {

    //리사이클러뷰 생성
    public RecyclerView rc_refrigerater;
    //어댑터 생성
    public RecyclerView.Adapter adapter_refrigerater;
    //냉장고 데이터 생성
    public ArrayList<RefrigeratorData> total_items = new ArrayList<>();
    //통신후 얻은 item[]를 저장하는 공간
    List<GetMyRefrigerator.OBG> result_list = new ArrayList<>();



    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("로그", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);
        rc_refrigerater = (RecyclerView) view.findViewById(R.id.rv_refrigerator);
        rc_refrigerater.setHasFixedSize(true);


        return view;
    }

    public List<GetMyRefrigerator.OBG> sendToServer() {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.30.1.63:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice getItems = retrofit.create(APIservice.class);
        //보낼요청에 사용자 아이디넣어서 보냄
        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);
        /***
         * 동기로 처리하는 방법 테스트해봐야함
         */
        Call<GetMyRefrigerator> callSync = getItems.getMyRefrigerator(request);
        try {
            Log.e("로그","1");
            Response<GetMyRefrigerator> response = callSync.execute();
            return response.body().getItems();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e("로그","통신중 결함",ex);
        }
        return null;
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
        /**
         * 서버로 데이터 요청
         */
        new Thread() {
            public void run(){
                Log.e("로그","스레드실행");
                result_list = sendToServer(); // network 동작, 인터넷에서 xml을 받아오는 코드

            }
        }.start();

        for (int i = 0; i < result_list.size(); i++) {
            Log.e("로그",result_list.get(i).item_name);
            Log.e("로그",result_list.get(i).item_date);
            Log.e("로그",result_list.get(i).item_counts);
            total_items.add(new RefrigeratorData(result_list.get(i).item_name,result_list.get(i).item_date,result_list.get(i).item_counts));
        }

        /***
         * recycleview에 데이터전송
         */

        adapter_refrigerater = new RefrigeratrotAdapter(total_items);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        rc_refrigerater.setLayoutManager(mlayoutManager);
        rc_refrigerater.setAdapter(adapter_refrigerater);

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("로그", "onResume");
    }

    @Override
    public void onStop() {
        super.onStop();
        total_items.clear();
    }
}