package com.mnu.capstoneapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
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

public class SignupActivity extends AppCompatActivity {
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_signup);

        findViewById(R.id.btn_signup).setOnClickListener(onClickListener);
    }


    View.OnClickListener onClickListener = new View.OnClickListener() {
        @Override
        public void onClick(View view) {
            switch (view.getId()){
                case R.id.btn_signup:
                    signup();
                    break;
            }
        }
    };




    private void signup( ){
        String username = ((EditText)findViewById(R.id.et_username)).getText().toString();
        String userid= ((EditText)findViewById(R.id.et_userid)).getText().toString();
        String password= ((EditText)findViewById(R.id.et_password)).getText().toString();

        Map request =new LinkedHashMap();
        request.put("username",username);
        request.put("userid",userid);
        request.put("password",password);

        //studio 가상머신 localhost >> 10.0.2.2

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://10.0.2.2:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        APIservice signupservice = retrofit.create(APIservice.class);
        signupservice.getSignupResponse(request);
        signupservice.getSignupResponse(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.body().getCode()){
                    case "0000":
                        startToast(response.body().getMsg());
                        break;
                    case "0001":
                        startToast("비밀번호 6자리이상");
                        break;
                    case "0002":
                        startToast(response.body().getMsg());
                        moveActivity(MainActivity.class);
                        break;
                    case "0003":

                        break;
                    default:
                        throw new IllegalStateException("Unexpected value: " + response.body().getCode());
                }
            }
            @Override
            public void onFailure(Call<LoginResponse> call, Throwable t) {

            }
        });
    }

    private void startToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    private void moveActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }
}