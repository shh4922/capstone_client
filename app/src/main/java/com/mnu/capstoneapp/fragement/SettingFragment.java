package com.mnu.capstoneapp.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.ImageView;

import com.mnu.capstoneapp.R;


public class SettingFragment extends Fragment {

    ViewGroup viewGroup;
    final private static String TAG = "GILBOMI";
    Button btn_photo;
    ImageView iv_photo;
    String KakaoRestApiKey = "9a1ca247a8a58968ceef53e69d4187ef";
    final static int TAKE_PICTURE = 1;

    String mCurrentPhotoPath;
    final static int REQUEST_TAKE_PHOTO = 1;


    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {

        viewGroup = (ViewGroup) inflater.inflate(R.layout.fragment_setting,container,false);
        return viewGroup;
    }
}