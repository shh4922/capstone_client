package com.mnu.capstoneapp.fragement;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

import com.mnu.capstoneapp.Interface.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.adpter.RefrigeratrotAdapter;
import com.mnu.capstoneapp.data.RefrigeratorData;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class UpdateDialogFragment extends DialogFragment implements View.OnClickListener {

    Button btn_update;
    EditText et_itemName;
    EditText et_itemCount;
    RefrigeratorData data;
    RefrigeratrotAdapter adapter;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

    }

    public UpdateDialogFragment(RefrigeratorData data,RefrigeratrotAdapter adapter) {
        this.data =data;
        this.adapter =adapter;
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_update_dialog, container, false);
        getDialog().getWindow().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        btn_update = view.findViewById(R.id.btn_update);
        btn_update.setOnClickListener(this);

        et_itemName = view.findViewById(R.id.itemName);
        et_itemCount = view.findViewById(R.id.itemCount);

        et_itemName.setText(data.getTv_itemname());
        et_itemCount.setText(data.getTv_itemcounts());

        return view;
    }


    private void updateItem() {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice service = retrofit.create(APIservice.class);

        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);
        request.put("old_name", data.getTv_itemname());
        request.put("old_count", data.getTv_itemcounts());
        request.put("update_name", et_itemName.getText().toString());
        request.put("update_count", et_itemCount.getText().toString());
        service.updateData(request).enqueue(new Callback<dafaultResponce>() {
            @Override
            public void onResponse(Call<dafaultResponce> call, Response<dafaultResponce> response) {
                switch (response.body().getCode()) {
                    case "0000":
                        Toast.makeText(getContext(), "업데이트완료 ㅎ", Toast.LENGTH_LONG).show();
                        adapter.notifyDataSetChanged();
                        dismiss();
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


    @Override
    public void onClick(View v) {
        switch (v.getId()){
            case R.id.btn_update:{
                updateItem();
                break;
            }
        }
    }
}
