package com.mnu.capstoneapp.fragement;

import android.os.Bundle;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView;

import com.mnu.capstoneapp.Interface.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.adpter.RecipeAdapter;
import com.mnu.capstoneapp.Response.GetMyRecipe;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RecipeData;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;


public class RecipeFragment extends Fragment {



    RecyclerView recyclerView;
    public RecipeAdapter recipeAdapter;
    ArrayList<RecipeData> recipeData = new ArrayList<>();


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
        getRecipyOnServer();
    }

    @Override
    public void onResume() {
        super.onResume();
        recipeAdapter = new RecipeAdapter(recipeData);
        recipeAdapter.setOnItemClickListener(new RecipeAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(View v, int pos) {
                ProcessDialogFragment processDialogFragment = new ProcessDialogFragment(recipeData.get(pos).getTv_recipe_name());
                processDialogFragment.show(getActivity().getSupportFragmentManager(),"dialog3");
            }
        });
        RecyclerView.LayoutManager mlayoutManager = new LinearLayoutManager(getActivity());
        recyclerView.setLayoutManager(mlayoutManager);
        recyclerView.setAdapter(recipeAdapter);
    }

    @Override
    public void onStop() {
        super.onStop();
        recipeData.clear();
    }
    
    private void getRecipyOnServer(){
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();
        APIservice service = retrofit.create(APIservice.class);

        Map request = new LinkedHashMap();
        request.put("userid", LoginActivity.userid_local);

        service.getMyRecipe(request).enqueue(new Callback<GetMyRecipe>() {
            @Override
            public void onResponse(Call<GetMyRecipe> call, Response<GetMyRecipe> response) {
                List<GetMyRecipe.Recipe> result = response.body().getRecipe_list();
                for (int i=0;i<result.size();i++){
                    recipeData.add(new RecipeData(result.get(i).recipe_name,result.get(i).points));
                }
                onResume();
            }

            @Override
            public void onFailure(Call<GetMyRecipe> call, Throwable t) {

            }
        });
    }




}