package com.mnu.capstoneapp.fragement;

import static android.app.Activity.RESULT_OK;

import static androidx.core.content.ContextCompat.checkSelfPermission;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.RequiresApi;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.core.content.FileProvider;
import androidx.fragment.app.Fragment;

import android.os.Environment;
import android.provider.MediaStore;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.ImgResponse;

import java.io.File;
import java.io.IOException;
import java.lang.reflect.Array;
import java.util.Arrays;
import java.util.Collection;
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
    ImageView iv_photo;
    final static int TAKE_PICTURE = 1;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;


    public CameraFragement() {

    }



    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }


    @Override
    public View onCreateView(LayoutInflater inflater, @NonNull ViewGroup container,
                             @NonNull Bundle savedInstanceState) {

        View view = inflater.inflate(R.layout.fragment_camera, container, false);
        iv_photo = view.findViewById(R.id.iv_photo);
        btn_photo = view.findViewById(R.id.btn_photo);


        if(Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
            if(ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.CAMERA) == PackageManager.PERMISSION_GRANTED && ContextCompat.checkSelfPermission(getActivity(),Manifest.permission.WRITE_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED){
                Log.d(TAG, "권한 설정 완료");
            }
            else {
                Log.d(TAG, "권한 설정 요청");
                requestPermissions( new String[]{Manifest.permission.CAMERA, Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
            }
        }

        btn_photo.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                switch (view.getId()){
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
        if (grantResults[0] == PackageManager.PERMISSION_GRANTED && grantResults[1] == PackageManager.PERMISSION_GRANTED ) {
            Log.d(TAG, "Permission: " + permissions[0] + "was " + grantResults[0]);
        }
    }

    // 카메라로 촬영한 사진의 썸네일을 가져와 이미지뷰에 띄워줌
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent intent) {
        super.onActivityResult(requestCode, resultCode, intent);
        try{
            switch (requestCode) {
                case REQUEST_TAKE_PHOTO:
                    if (resultCode == RESULT_OK) {
                        File file = new File(mCurrentPhotoPath);
                        Bitmap bitmap = MediaStore.Images.Media.getBitmap(getActivity().getContentResolver(), Uri.fromFile(file));
                        sendImgKaKaO(file);
                        if (bitmap != null) {
                            iv_photo.setImageBitmap(bitmap);
                        }
                    }
                    break;

            }
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    // 사진 촬영 후 썸네일만 띄워줌. 이미지를 파일로 저장해야 함
    private File createImageFile() throws IOException {

        String imageFileName = "img" ;
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
        if(takePictureIntent.resolveActivity(getActivity().getPackageManager()) != null) {
            File photoFile = null;

            try {
                photoFile = createImageFile();
            }
            catch (IOException ex) { }
            if(photoFile != null) {
                Uri photoURI = FileProvider.getUriForFile(getContext(), "com.mnu.capstoneapp.fileprovider", photoFile);
                takePictureIntent.putExtra(MediaStore.EXTRA_OUTPUT, photoURI);
                startActivityForResult(takePictureIntent, REQUEST_TAKE_PHOTO);
            }
        }
    }


    //카카오로 이미지 전송하는 함수 (찍었던 사진을 받아와야함)
    public void sendImgKaKaO(File photoFile){

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
        imgservice.getImgResponse(appkey,body).enqueue(new Callback<ImgResponse>() {
            @Override
            public void onResponse(Call<ImgResponse> call, Response<ImgResponse> response) {

                //통신에 성공하였을떄 응답이 온것을 imgResponse에 넣어둠
                ImgResponse imgResponse = response.body();

                //응답이 제대로 왔을경우
                if (response.isSuccessful()){
                    List<ImgResponse.Result> resultList = imgResponse.result;
                    getOneLine(resultList);
                }else {
                    Log.e("실패","실패");
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ImgResponse> call, Throwable t) {
                //통신에 실패할경우
                Log.e("tag","실패",t);
            }
        });
    }


    //응답이 왔을때의 text를 한줄로 만들기위해 만든 함수
    public void getOneLine(List<ImgResponse.Result> resultList){
        //x,y좌표를 담고있을 배열
        int[] arry1;
        int[] arry2;

        //배열에서 가져온 y값을 담을 변수
        int y1=0,y2=0;
        int y1_,y2_;

        // 한 줄의 라인의 조건을 판별해줄 count
        int count=0;
        int key =0;
        //전체 텍스트 한줄을 담을 String
        String onelinestr="";

        //전체 좌표box를 담을 List<int[]>
        List<int[]> tatalbox = null;

        //json으로 보내기 위한 linkedMap
        Map<Integer,String> request =new LinkedHashMap<Integer,String>();

        for (ImgResponse.Result result : resultList){
            tatalbox= result.boxes;
            arry1=tatalbox.get(0);
            arry2=tatalbox.get(2);

            if(count == 0){
                onelinestr+=Arrays.deepToString(result.recognition_words);
                y1=arry1[1];
                y2=arry2[1];
                count++;

            }else {
                y1_=arry1[1];
                y2_=arry2[1];
                if( (y1_>= y1-50 && y1_<=y1+50) && (y2_>=y2-50 && y2_<=y2+50) ) {
                    onelinestr += Arrays.deepToString(result.recognition_words);
                    count++;

                }else{
                    request.put(key,onelinestr);
                    key++;
                    System.out.println(onelinestr);
                    Log.e("줄바꿈 & 추가완료","줄바꿈 & 추가완료");
                    onelinestr="";
                    onelinestr += Arrays.deepToString(result.recognition_words);
                    count=0;
                }
                y1=y1_;
                y2=y2_;
            }
        }
        Log.e("끝","해쉽맵 반복으로 출력");
        for(int i=0;i<request.size();i++){
            Log.e("결과",(request.get(i)));
        }
    }
}
