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
    private ArrayList<RefrigeratorData> data = new ArrayList<>();

    //통신후 얻은 item[]를 저장하는 공간
    List<String> result_list= null;


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

//        for(int i=0;i<5;i++){
//            RefrigeratorData d = new RefrigeratorData("gg");
//            data2.add(d);
//        }
        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);
        //서버에서 값 받아오기
        data=sendToServer();

        rc_refrigerater = (RecyclerView)view.findViewById(R.id.rv_refrigerator);
        rc_refrigerater.setHasFixedSize(true);
        adapter_refrigerater= new RefrigeratrotAdapter(data);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        rc_refrigerater.setLayoutManager(mlayoutManager);
        rc_refrigerater.setAdapter(adapter_refrigerater);
        return view;
    }

    public ArrayList<RefrigeratorData> sendToServer(){
        ArrayList<RefrigeratorData> refrigeratorData= new ArrayList<>();

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.16.28.114:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice getItems = retrofit.create(APIservice.class);

        //보낼요청에 사용자 아이디넣어서 보냄
        Map request =new LinkedHashMap();
        request.put("userid",LoginActivity.userid_local);

        getItems.getMyRefrigerator(request);
        getItems.getMyRefrigerator(request).enqueue(new Callback<GetMyRefrigerator>() {
            @Override
            public void onResponse(Call<GetMyRefrigerator> call, Response<GetMyRefrigerator> response) {
                result_list = response.body().getItems();
                RefrigeratorData  refrigerator_list= null;

                for(int i=0;i<result_list.size();i++){
                    //adapter에 넘겨줄 Refrigerator[] 만들기
                    refrigerator_list= new RefrigeratorData(result_list.get(i));
                    refrigeratorData.add(refrigerator_list);
                }
            }

            @Override
            public void onFailure(Call<GetMyRefrigerator> call, Throwable t) {
                Log.e("로그","아이탭 응답실패",t);

            }
        });
        return refrigeratorData;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sendToServer();
    }



    @Override
    public void onStop() {
        super.onStop();
        //화면이 안보일때 데이터를 clear
        //그래야 중복해서 계속쌓이지않음.
        data.clear();
    }
}