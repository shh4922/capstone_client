package com.mnu.capstoneapp.activity;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.EditText;
import android.widget.Toast;

import com.mnu.capstoneapp.Interface.APIservice;
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


    //회원가입페이지의 버튼 이벤트
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



    //회원가입 함수
    private void signup( ){
        //입력값을 받아옴
        String username = ((EditText)findViewById(R.id.et_username)).getText().toString();
        String userid= ((EditText)findViewById(R.id.et_userid)).getText().toString();
        String password= ((EditText)findViewById(R.id.et_password)).getText().toString();

        //JSON으로 맵핑
        Map request =new LinkedHashMap();
        request.put("username",username);
        request.put("userid",userid);
        request.put("password",password);

        //studio 가상머신 localhost >> 10.0.2.2

        //서버로 전송하기위한 retrofit 설정
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //통신을 위한 APIservice 생성
        APIservice signupservice = retrofit.create(APIservice.class);
        //APIservice의 getSignupRespone에 만들어둔 JSON 입력
        signupservice.getSignupResponse(request);
        signupservice.getSignupResponse(request).enqueue(new Callback<LoginResponse>() {
            @Override
            public void onResponse(Call<LoginResponse> call, Response<LoginResponse> response) {
                switch (response.body().getCode()){
                    //성공
                    case "0000":
                        startToast(response.body().getMsg());
                        break;
                    case "0001":
                        startToast("비밀번호 6자리이상");
                        break;
                    case "0002":
                        startToast(response.body().getMsg());
                        moveActivity(LoginActivity.class);
                        break;
                    case "0003":
                        startToast(response.body().getMsg());
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

    //알림메세지 출력
    private void startToast(String msg){

        Toast.makeText(this, msg, Toast.LENGTH_SHORT).show();
    }

    //엑티비티 화면전환 함수
    private void moveActivity(Class c){
        Intent intent = new Intent(this,c);
        startActivity(intent);
    }

    //알림메시지 함수
    private void ShowDialog(String msg){
        new AlertDialog.Builder(this)
                .setTitle("알림!")
                .setMessage(msg)
                .create()
                .show();
    }
}