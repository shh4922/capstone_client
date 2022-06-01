
package com.mnu.capstoneapp.activity;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.fragement.Camera2BasicFragment;



public class CameraActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_camera);
        if (null == savedInstanceState) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.container, Camera2BasicFragment.newInstance())
                    .commit();
        }
    }

}