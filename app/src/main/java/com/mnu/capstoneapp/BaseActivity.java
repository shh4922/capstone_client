package com.mnu.capstoneapp;

import androidx.appcompat.app.AppCompatActivity;

import com.mnu.capstoneapp.fragement.CameraFragement;

public class BaseActivity extends AppCompatActivity {


    public void progressON() {
        CameraFragement.getInstance().progressON(CameraFragement.getInstance(), null);
    }

    public void progressON(String message) {
        CameraFragement.getInstance().progressON(CameraFragement.getInstance(), message);
    }

    public void progressOFF() {
        CameraFragement.getInstance().progressOFF();
    }

}