package com.mnu.capstoneapp.fragement;

import android.app.ProgressDialog;
import android.os.AsyncTask;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.GetMyRefrigerator;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RefrigeratorData;
import com.mnu.capstoneapp.adpter.RefrigeratrotAdapter;

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
    public RefrigeratrotAdapter adapter_refrigerater;
    //냉장고 데이터 생성
    public ArrayList<RefrigeratorData> total_items = new ArrayList<>();


    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        Log.e("로그", "onCreateView");
        View view = inflater.inflate(R.layout.fragment_refrigerator, container, false);


        rc_refrigerater = (RecyclerView) view.findViewById(R.id.rv_refrigerator);
        rc_refrigerater.setHasFixedSize(true);


        return view;
    }

    private void getMyRefrigerator() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice getItems = retrofit.create(APIservice.class);
        //보낼요청에 사용자 아이디넣어서 보냄
        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);

        getItems.getMyRefrigerator(request).enqueue(new Callback<GetMyRefrigerator>() {
            @Override
            public void onResponse(Call<GetMyRefrigerator> call, Response<GetMyRefrigerator> response) {
                List<GetMyRefrigerator.OBG> result = response.body().getItems();
                for (int i = 0; i < result.size(); i++) {
                    total_items.add(new RefrigeratorData(result.get(i).item_name, result.get(i).item_date, result.get(i).item_counts));
                }
                onResume();
            }

            @Override
            public void onFailure(Call<GetMyRefrigerator> call, Throwable t) {

            }
        });

    }


    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        Log.e("로그", "냉장고 온크레이트");
    }

    @Override
    public void onStart() {
        super.onStart();
        Log.e("로그", "냉장고 온스타트");

        getMyRefrigerator();
        /**
         * 서버로 데이터 요청
         */

    }

    @Override
    public void onResume() {
        super.onResume();
        Log.e("로그", "냉장고 온리줌");
        /***
         * recycleview에 데이터전송
         */

        adapter_refrigerater = new RefrigeratrotAdapter(total_items);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        rc_refrigerater.setLayoutManager(mlayoutManager);
        rc_refrigerater.setAdapter(adapter_refrigerater);
    }

    @Override
    public void onStop() {
        super.onStop();
        Log.e("로그", "냉장고 온스탑");
        total_items.clear();
    }


    @Override
    public boolean onContextItemSelected(MenuItem item) {
        int position = adapter_refrigerater.getPosition();
        RefrigeratorData data = total_items.get(position);

        switch (item.getItemId()) {
            case R.id.action_update:
                UpdateDialogFragment updateDialogFragment = new UpdateDialogFragment(data,adapter_refrigerater);
                updateDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog2");
                break;
            case R.id.action_delete:
                adapter_refrigerater.removeItem(position);
                removeItem(data);
                break;
            default:
                break;
        }

        return true;
    }

    public void removeItem(RefrigeratorData data) {

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);

        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);
        request.put("item_name", data.getTv_itemname());

        service.deleteMyItem(request).enqueue(new Callback<dafaultResponce>() {
            @Override
            public void onResponse(Call<dafaultResponce> call, Response<dafaultResponce> response) {
                switch (response.body().getCode()) {
                    case "0000":
                        Toast.makeText(getContext(), "삭제되었습니다", Toast.LENGTH_LONG).show();
                        break;
                    default:
                        Toast.makeText(getContext(), "통신은했지만 오류", Toast.LENGTH_LONG).show();
                }
            }

            @Override
            public void onFailure(Call<dafaultResponce> call, Throwable t) {
                Log.e("로그", "통신실패", t);
            }
        });

    }


}