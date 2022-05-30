package com.mnu.capstoneapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.Signature;
import android.os.Bundle;
import android.util.Base64;
import android.util.Log;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.fragement.Camera2BasicFragment;
import com.mnu.capstoneapp.fragement.RecipeFragment;
import com.mnu.capstoneapp.fragement.RefrigeratorFragment;
import com.mnu.capstoneapp.fragement.SettingFragment;

import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;

public class MainActivity extends AppCompatActivity {

    RefrigeratorFragment refrigeratorFragment;
    SettingFragment settingFragment;
    RecipeFragment recipeFragment;
    Camera2BasicFragment camera2BasicFragment;
    NavigationBarView navigationBarView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        init();
    }

    public void init(){
        refrigeratorFragment = new RefrigeratorFragment();
        recipeFragment = new RecipeFragment();
        settingFragment = new SettingFragment();
        camera2BasicFragment = new Camera2BasicFragment();
        navigationBarView = findViewById(R.id.bottom_menubar);


        getAppKeyHash();

        //처음에 나올 프레그먼트 설정
        getSupportFragmentManager().beginTransaction().replace(R.id.container,refrigeratorFragment).commit();


        navigationBarView.setOnItemSelectedListener(new NavigationBarView.OnItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()){
                    case R.id.refrigerator:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,refrigeratorFragment).commit();
                        return true;
                    case R.id.recipe:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,recipeFragment).commit();
                        return true;
                    case R.id.setting:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,settingFragment).commit();
                        return true;
                    case R.id.camera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,camera2BasicFragment).commit();
                        return true;
                }
                return false;
            }
        });

    }
    private void getAppKeyHash() {
        try {
            PackageInfo info = getPackageManager().getPackageInfo(getPackageName(), PackageManager.GET_SIGNATURES);
            for (Signature signature : info.signatures) {
                MessageDigest md;
                md = MessageDigest.getInstance("SHA");
                md.update(signature.toByteArray());
                String something = new String(Base64.encode(md.digest(), 0));
                Log.e("Hash key", something);
            }
        } catch (PackageManager.NameNotFoundException e) {
            Log.e("name not found", e.toString());
        } catch (NoSuchAlgorithmException e) {
            e.printStackTrace();
        }
    }
}