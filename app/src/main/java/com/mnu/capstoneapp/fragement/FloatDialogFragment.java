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
import androidx.fragment.app.DialogFragment;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.Interface.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.adpter.FloatButtonAdapter;
import com.mnu.capstoneapp.data.FloatData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class FloatDialogFragment extends DialogFragment implements View.OnClickListener {

    private Button btn_add;
    private Button btn_save;
    RecyclerView recyclerView;
    FloatButtonAdapter adapter;
    ArrayList<FloatData> floatDataArrayList = new ArrayList<>();

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_floatdialog, container, false);
        btn_add = view.findViewById(R.id.add);
        btn_save = view.findViewById(R.id.save);
        btn_add.setOnClickListener(this);
        btn_save.setOnClickListener(this);

        recyclerView = (RecyclerView) view.findViewById(R.id.rc_float);
        recyclerView.setHasFixedSize(true);

        return view;

    }

    @Override
    public void onStart() {
        super.onStart();
        adapter = new FloatButtonAdapter(floatDataArrayList);
        RecyclerView.LayoutManager layoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(adapter);
    }

    @Override
    public void onResume() {
        super.onResume();

    }

    @Override
    public void onStop() {
        super.onStop();
        floatDataArrayList.clear();
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.add:{
                addColum();
                break;
            }
            case R.id.save:{
                saveItem();
                break;
            }
        }
    }

    public void addColum(){
        adapter.addItem(new FloatData());
    }

    public void saveItem(){
        ArrayList<FloatData> arylisy = new ArrayList<>();
        arylisy = adapter.returnItem();
        Map request = new LinkedHashMap();
        Map user = new LinkedHashMap();
        user.put("userid",LoginActivity.userid_local);
        List list = new ArrayList();
        for(int i=0;i<arylisy.size();i++){
            Map request_i = new LinkedHashMap();
            request_i.put("item_name",arylisy.get(i).getItem_name());
            request_i.put("item_count",arylisy.get(i).getCount());
            list.add(request_i);
        }
        request.put("user", user);
        request.put("items",list);
        Log.e("로그",request.toString());
        sendToItems(request);
    }

    private void sendToItems(Map request){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //통신을 위한 APIservice 생성
        APIservice service = retrofit.create(APIservice.class);
        //APIservice에 있는 getLoginResponse호출 후, 만들어둔 request(JSON) 를 입력
        service.floatButtonAddItem(request).enqueue(new Callback<dafaultResponce>() {
            @Override
            public void onResponse(Call<dafaultResponce> call, Response<dafaultResponce> response) {
                switch (response.body().getCode()){
                    case "0000":{
                        Toast.makeText(getContext(),"추가되었습니다",Toast.LENGTH_SHORT).show();
                        dismiss();
                        break;
                    }
                }
            }

            @Override
            public void onFailure(Call<dafaultResponce> call, Throwable t) {
                Log.e("로그","개별아이탬추가 통신중결함",t);
            }
        });
    }
}
