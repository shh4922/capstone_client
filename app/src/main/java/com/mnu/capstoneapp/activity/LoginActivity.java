package com.mnu.capstoneapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.Response.LoginResponse;
import com.mnu.capstoneapp.R;

import java.util.LinkedHashMap;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class LoginActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);

        findViewById(R.id.btn_login).setOnClickListener(onClickListener);
        findViewById(R.id.btn_sighup).setOnClickListener(onClickListener);
    }

    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_login:
                    login();
                    break;
                case R.id.btn_signup:
                    Log.e("10005","5??");
                    moveActivity(SignupActivity.class);

                case R.id.btn_sighup:
                    Log.e("100001","1??");
                    moveActivity(SignupActivity.class);
                    break;

            }
        }
    };

    private void moveActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    private void ShowDialog(String msg){
        new AlertDialog.Builder(this)
                .setTitle("알림!")
                .setMessage(msg)
                .create()
                .show();
    }



    private void login(){
        String userid= ((EditText)findViewById(R.id.et_userid)).getText().toString();
        String password= ((EditText)findViewById(R.id.et_password)).getText().toString();

        Map request =new LinkedHashMap();
        request.put("userid",userid);
        request.put("password",password);

        //studio 가상머신 localhost >> 10.0.2.2

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice loginService = retrofit.create(APIservice.class);
        loginService.getLoginResponse(request);
        loginService.getLoginResponse(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.body().getCode()){
                    case "0000":
                        moveActivity(MainActivity.class);
                        ShowDialog(response.body().getMsg());
                        break;
                    case "0001":
                        ShowDialog(response.body().getMsg());
                        break;
                    case "0002":
                        ShowDialog(response.body().getMsg());
                        break;
                    case "0003":
                        ShowDialog(response.body().getMsg());
                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + response.body().getCode());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {
                ShowDialog("통신에 실패했습니다.");
            }
        });
    }







    private void startToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }
}