package com.mnu.capstoneapp.adpter;

import android.text.Editable;
import android.text.TextWatcher;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.mnu.capstoneapp.APIservice;
import com.mnu.capstoneapp.R;
import com.mnu.capstoneapp.Response.dafaultResponce;
import com.mnu.capstoneapp.activity.LoginActivity;
import com.mnu.capstoneapp.data.RunningItemList;
import com.mnu.capstoneapp.fragement.CameraFragement;

import java.util.ArrayList;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class DialogAdapter extends RecyclerView.Adapter<DialogAdapter.ViewHolder> {

    private ArrayList<RunningItemList> runningItemLists;
    private boolean isTextChanged = false;


    public DialogAdapter(ArrayList<RunningItemList> runningItemLists) {
        this.runningItemLists = runningItemLists;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_textdata, parent, false);
        return new ViewHolder(v);
    }



    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        EditText et_items = holder.et_items;
        Button btn_delete = holder.btn_delete;

        et_items.setText(runningItemLists.get(position).getItem_name());



        btn_delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                remove(holder.getAdapterPosition());
            }
        });

    }



    private void remove(int position) {
        try{
            runningItemLists.remove(position);
            notifyItemRemoved(position);
        }catch (Exception e){
            Log.e("로그","btn_delete_errror",e);
        }
    }


    @Override
    public int getItemCount() {
        return runningItemLists.size();
    }


    public class ViewHolder extends RecyclerView.ViewHolder {
        private EditText et_items;
        private Button btn_delete;
        public ViewHolder(@NonNull View itemview) {
            super(itemview);
            et_items = (EditText) itemview.findViewById(R.id.et_items);
            btn_delete=(Button) itemview.findViewById(R.id.btn_delete);
            et_items.addTextChangedListener(new TextWatcher() {
                @Override
                public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

                }

                @Override
                public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                    isTextChanged = true;
                }

                @Override
                public void afterTextChanged(Editable editable) {
                    if (isTextChanged) {
                        isTextChanged = false;
                        try {
                            Log.e("로그","변경중");
                            runningItemLists.get(getAdapterPosition()).setItem_name(et_items.getText().toString());
                            Log.e("로그",et_items.getText().toString());
                        } catch (Exception e) {
                            Log.e("로그", "텍스트수정에러", e);
                        } finally {

                        }

                    }
                }
            });
        }
    }

    public void saveItem() {

        Map request = new LinkedHashMap();

        Map user =new LinkedHashMap();
        user.put("userid",LoginActivity.userid_local);

        List<Map> textlist = new ArrayList<>();
        for (int i=0;i<runningItemLists.size();i++){
            Map wordlist = new LinkedHashMap();
            wordlist.put("item_name",runningItemLists.get(i).getItem_name());
            textlist.add(wordlist);

        }
        Log.e("로그",textlist.toString());
        request.put("user",user);
        request.put("word", textlist);

        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl("http://172.17.220.103:8000")
                .addConverterFactory(GsonConverterFactory.create())
                .build();

        //통신을 위한 APIservice 생성
        APIservice service = retrofit.create(APIservice.class);
        //APIservice에 있는 getLoginResponse호출 후, 만들어둔 request(JSON) 를 입력
        service.saveData(request).enqueue(new Callback<dafaultResponce>() {
            @Override
            public void onResponse(Call<dafaultResponce> call, Response<dafaultResponce> response) {
                Log.e("로그","통신성공");
            }

            @Override
            public void onFailure(Call<dafaultResponce> call, Throwable t) {
                Log.e("로그","저장실패",t);
            }
        });
    }

}
