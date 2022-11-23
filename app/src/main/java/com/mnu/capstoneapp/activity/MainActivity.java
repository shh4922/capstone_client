package com.mnu.capstoneapp.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.MenuItem;

import com.google.android.material.navigation.NavigationBarView;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.fragement.CameraFragement;
import com.mnu.capstoneapp.fragement.RecipeFragment;
import com.mnu.capstoneapp.fragement.RefrigeratorFragment;

public class MainActivity extends AppCompatActivity {

    RefrigeratorFragment refrigeratorFragment;
    RecipeFragment recipeFragment;
    CameraFragement cameraFragement;
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
        cameraFragement = new CameraFragement();
        navigationBarView = findViewById(R.id.bottom_menubar);


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
                    case R.id.camera:
                        getSupportFragmentManager().beginTransaction().replace(R.id.container,cameraFragement).commit();
                        return true;
                }
                return false;
            }
        });

    }

}