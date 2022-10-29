package com.mnu.capstoneapp.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.RecipeAdapter;
import com.mnu.capstoneapp.RefrigeratrotAdapter;
import com.mnu.capstoneapp.Response.GetMyRecipe;
import com.mnu.capstoneapp.Response.GetMyRefrigerator;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RecipeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeFragment extends Fragment {



    RecyclerView recyclerView;
    public RecyclerView.Adapter recipeAdapter;
    ArrayList<RecipeData> recipeData = new ArrayList<>();
    List<GetMyRecipe.Recipe> result_list = new ArrayList<>();

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
    }
    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_recipe, container, false);

        recyclerView = (RecyclerView) view.findViewById(R.id.rv_recipe);
        recyclerView.setHasFixedSize(true);

        return view;
    }

    @Override
    public void onStart() {
        super.onStart();
        new Thread(){
            public void run(){
                Log.e("로그","스레드실행");
                result_list = getRecipeFromServer(); // network 동작, 인터넷에서 xml을 받아오는 코드
            }
        }.start();

        for(int i=0;i<result_list.size();i++){
            recipeData.add(new RecipeData(result_list.get(i).recipe_name));
            Log.e("로그",result_list.get(i).recipe_name);
        }


        recipeAdapter = new RecipeAdapter(recipeData);
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onResume() {
        super.onResume();


    }

    @Override
    public void onStop() {
        super.onStop();
        recipeData.clear();
    }


    public List<GetMyRecipe.Recipe> getRecipeFromServer(){

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.30.1.63:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice service = retrofit.create(APIservice.class);

        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);

        Call<GetMyRecipe> callSync = service.getMyRecipe(request);
        try {
            Response<GetMyRecipe> response = callSync.execute();
            return response.body().getRecipe_list();
        }catch (Exception ex){
            ex.printStackTrace();
            Log.e("로그","통신중 결함",ex);
        }
        return null;

    }



}