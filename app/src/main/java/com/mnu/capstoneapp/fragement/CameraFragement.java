package com.mnu.capstoneapp.fragement;

import static android.app.Activity.RESULT_OK;

import static java.lang.Math.abs;

import android.Manifest;
import android.app.Dialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AlertDialog;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.widget.Button;
import android.widget.ImageView;

import com.mnu.capstoneapp.Interface.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.ImgResponse;
import com.mnu.capstoneapp.Response.TextDataResponse;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RunningItemList;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
//implements View.OnClickListener

public class CameraFragement extends Fragment {

    final private static String TAG = "GILBOMI";
    Button btn_photo;

    final static int TAKE_PICTURE = 1;
    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;


    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);

        btn_photo = view.findViewById(R.id.btn_photo);


        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if (ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(), Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED) {
                Log.d(TAG, "권한 설정 완료");
            } else {
                Log.d(TAG, "권한 설정 요청");
                requestPermissions(new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()) {
                    case R.id.btn_photo:
                        dispatchTakePictureIntent();
//                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
//                        startActivityForResult(cameraIntent, TAKE_PICTURE);
                        break;
                }
            }
        });

        return view;
    }

    // 권한 요청  onRequestPermissionsResult
    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        Log.d(TAG, "onRequestPermissionsResult");
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try {
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                        sendImgKaKaO(file);
                        if (bitmap != null) {

                        }
                    }
                    break;

            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    private File createImageFile() throws IOException {

        String imageFileName = "img";
        File storageDir = getActivity().getExternalFilesDir(Environment.DIRECTORY_PICTURES);
        File image = File.createTempFile(
                imageFileName,
                ".jpg",
                storageDir
        );

        mCurrentPhotoPath = image.getAbsolutePath();
        return image;
    }


    // 카메라 인텐트 실행하는 부분
    private void dispatchTakePictureIntent() {
        Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            } catch (IOException ex) {
            }
            if (photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.mnu.capstoneapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    //카카오로 이미지 전송하는 함수 (찍었던 사진을 받아와야함)
    public void sendImgKaKaO(File photoFile) {
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        //앱키 설정
        String appkey = "KakaoAK 9a1ca247a8a58968ceef53e69d4187ef";
        //이미지는 png든 jpng든 모든파일이 가능하도록 설정
        RequestBody requestimg = RequestBody.create(MediaType.parse("image/*"), photoFile);
        //사진의 이름과, 찍은 사진을  body에 묶음
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", photoFile.getName(), requestimg);
        //APIservice 생성
        APIservice imgservice = retrofit.create(APIservice.class);
        //앱키와 body를 넣어서 통신
        imgservice.getImgResponse(appkey, body).enqueue(new Callback<ImgResponse>() {
            @Override
            public void onResponse(Call<ImgResponse> call, Response<ImgResponse> response) {
                //통신에 성공하였을떄 응답이 온것을 imgResponse에 넣어둠
                ImgResponse imgResponse = response.body();
                //응답이 제대로 왔을경우
                if (response.isSuccessful()) {
                    List<ImgResponse.Result> resultList = imgResponse.result;
                    getOneLine(resultList);
                } else {
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ImgResponse> call, Throwable t) {
                //통신에 실패할경우
                Log.e("로그", "카카오 통신 실패", t);
            }
        });
    }


    //응답이 왔을때의 text를 한줄로 만들기위해 만든 함수
    //귀찮아서 그냥 라인띄어쓰기하는곳에서 바로 json으로 묶어서 서버로 보내게 하겠음
    public void getOneLine(List<ImgResponse.Result> resultList) {
        //전체묶일 json 하나
        Map<String, Object> total = new LinkedHashMap<>();

        //아이디값을 묶을 json
        Map<String, String> id = new LinkedHashMap<>();
        id.put("userid", LoginActivity.userid_local);

        //단어배열을 각각{}로 묶어서 보내기
        List<Map<Integer, Object>> listMapInsert = new ArrayList<Map<Integer, Object>>();

        //x,y좌표를 담고있을 배열
        int[] arry1;
        int[] arry2;
        //첫번째 단어 x,y좌표
        int y1 = 0;
        // 두번째 단어 x,y 좌표
        int y1_;

        // 한 줄의 라인의 조건을 판별해줄 count
        int count = 0;
        int key = 0;
        //전체 텍스트 한줄을 담을 String
        String onelinestr = "";
        //전체 좌표box를 담을 List<int[]>
        List<int[]> tatalbox = null;

        //json으로 보내기 위한 linkedMap
        //Map<Integer,String> request =new LinkedHashMap<Integer,String>();

        for (ImgResponse.Result result : resultList) {
            tatalbox = result.boxes;
            //단어의 왼쪽 위 의 (x,y)좌표
            arry1 = tatalbox.get(0);

            Log.d("상품", Arrays.deepToString(result.recognition_words));
            if (Arrays.deepToString(result.recognition_words).equals("[상품(코드)]") || Arrays.deepToString(result.recognition_words).equals("[단가]") || Arrays.deepToString(result.recognition_words).equals("[수량]") || Arrays.deepToString(result.recognition_words).equals("[금액]")) {
                Log.e("상품", "오 같음");
                count = 0;
                onelinestr = "";
                key =0;
                listMapInsert.clear();

            } else if (Arrays.deepToString(result.recognition_words).equals("[총구매액]") || Arrays.deepToString(result.recognition_words).equals("[총구매액:]") || Arrays.deepToString(result.recognition_words).equals("[내실금액]")) {
                break;
            } else {
                //첫번쨰 단어는 그냥 비교할 객체가 없기에 그냥 저장하고 끝
                if (count == 0) {
                    onelinestr += Arrays.deepToString(result.recognition_words);
                    //arry[]가 x,y좌표를 가지고있고 arry[1]은 y좌표임
                    y1 = arry1[1];

                    count++;

                } else {
                    //다음 단어의 y좌표 갖고옴
                    y1_ = arry1[1];
                    if (abs(y1_ - y1) >= 0 && abs(y1_ - y1) <= 10) {
                        onelinestr += Arrays.deepToString(result.recognition_words);
                        count++;
                    } else {
                        Map<Integer, Object> map = new LinkedHashMap<Integer, Object>();
                        map.put(key, onelinestr);
                        listMapInsert.add(map);
                        //<"키값","한줄의 텍스트"> 가 들어가기에 키값++, 한줄단어는 다시 null로
                        key++;
                        onelinestr = "";
                        onelinestr += Arrays.deepToString(result.recognition_words);
                        count = 0;
                    }
                    //현재위치가 이젠 이전위치로 바껴야함.
                    y1 = y1_;

                }
            }

        }

        // 카카오에서 나온 텍스트들 json으로 묶기  ### 22.07.11
        Log.e("로그", listMapInsert.toString());
        total.put("user", id);
        total.put("word", listMapInsert);

        //ㅋ;;
        sendServer(total);


    }

    private void sendServer(Map<String, Object> total) {
        ArrayList<RunningItemList> arylist = new ArrayList<>();
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.30.1.38:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //통신을 위한 APIservice 생성
        APIservice textsend = retrofit.create(APIservice.class);
        //APIservice에 있는 getLoginResponse호출 후, 만들어둔 request(JSON) 를 입력
        textsend.getResultTexts(total);
        if (arylist == null) {
            new AlertDialog.Builder(getContext())
                    .setTitle("알림!")
                    .setMessage("데이터를 받아오는중... 기다려주세용")
                    .create()
                    .show();
        }
        /***
         * 띄어쓰기해서 묶은 데이터 확인하는 Log
         */
        textsend.getResultTexts(total).enqueue(new Callback<TextDataResponse>() {
            @Override
            public void onResponse(Call<TextDataResponse> call, Response<TextDataResponse> response) {

                List<TextDataResponse.ItemList> result = response.body().getText_items();

                for (int i = 0; i < result.size(); i++) {
                    arylist.add(new RunningItemList(result.get(i).item_name));
                }
                //dialog 생성 및 호출
                CustomeDialogFragment customeDialogFragment = new CustomeDialogFragment(arylist);
                customeDialogFragment.show(getActivity().getSupportFragmentManager(), "dialog");


            }

            @Override
            public void onFailure(Call<TextDataResponse> call, Throwable t) {
                Log.e("로그", "서버에 text데이터 전송&응답 실패", t);
            }
        });
    }
}
