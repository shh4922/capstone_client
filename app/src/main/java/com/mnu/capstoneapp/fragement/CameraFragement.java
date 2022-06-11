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
import java.util.Arrays;
import java.util.List;

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
    String KakaoRestApiKey = "9a1ca247a8a58968ceef53e69d4187ef";
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


//    @Override
//    public void onClick(View view){
//        switch (view.getId()){
//            case R.id.btn_photo:
//                dispatchTakePictureIntent();
////                        Intent cameraIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
////                        startActivityForResult(cameraIntent, TAKE_PICTURE);
//                break;
//        }
//    }

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
    public void sendImgKaKaO(File photoFile){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("https://dapi.kakao.com/")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        String appkey = "KakaoAK 9a1ca247a8a58968ceef53e69d4187ef";

        RequestBody requestimg = RequestBody.create(MediaType.parse("image/*"), photoFile);
        System.out.println(requestimg);
        MultipartBody.Part body = MultipartBody.Part.createFormData("image", photoFile.getName(), requestimg);
        APIservice imgservice = retrofit.create(APIservice.class);

        imgservice.getImgResponse(appkey,body).enqueue(new Callback<ImgResponse>() {
            @Override
            public void onResponse(Call<ImgResponse> call, Response<ImgResponse> response) {
                ImgResponse imgResponse = response.body();
                if (response.isSuccessful()){
                    Log.e("성공","성공");
                    System.out.println(response.code());
                    String total = "";
                    List<int[]> tatalbox = null;
                    List<ImgResponse.Result> resultList = imgResponse.result;

                    for (ImgResponse.Result result : resultList){
                        total+=result.recognition_words;
                        tatalbox= result.boxes;
                        System.out.println(Arrays.toString(tatalbox.get(0)));
                        System.out.println(Arrays.deepToString(result.recognition_words));

                    }
//                    for(int i=0;i<tatalbox.size();i++){
//                        System.out.println(Arrays.toString(tatalbox.get(i)));
//                    }
                    Log.e("대박",total);


                }else {
                    Log.e("실패","실패");
                    System.out.println(response.code());
                }
            }

            @Override
            public void onFailure(Call<ImgResponse> call, Throwable t) {
                Log.e("실패2","실패2");
                Log.e("tag","실패",t);
            }
        });
    }
}