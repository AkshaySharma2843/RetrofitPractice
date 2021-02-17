package com.retex.retrofitpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;

public class MainActivity extends AppCompatActivity {

    RecyclerView recyclerView;
    MyAdapter myAdapter;
    Retrofit retrofit;
    ApiService apiService;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        recyclerView = findViewById(R.id.rec);
        fetchData();
    }

    private void fetchData() {

        retrofit = new RetrofitFactory().CreateRetrofitInstance();
        apiService = retrofit.create(ApiService.class);

        Call<List<User>> client = apiService.user();
        client.enqueue(new Callback<List<User>>() {
            @Override
            public void onResponse(Call<List<User>> call, Response<List<User>> response) {
                Log.e("fdfkf0",""+response.body());
                if(response.isSuccessful()){
                    if(response.body()!=null){
                        if(!response.body().isEmpty()){
                            Log.e("fdfkf",""+response.body());
                            drawData(response.body());                        }
                    }
                    else{
                        Toast.makeText(MainActivity.this, "No data found", Toast.LENGTH_SHORT).show();
                    }
                }
                else
                {
                    Log.e("fdfkf2",""+response.body());
                }


                Log.e("fdfkf3",""+response.body());
            }

            @Override
            public void onFailure(Call<List<User>> call, Throwable t) {
                Toast.makeText(MainActivity.this, "Some error occured", Toast.LENGTH_SHORT).show();

            }
        });


    }

    private void drawData(List<User> client) {

        myAdapter = new MyAdapter(client);
        recyclerView.setLayoutManager(new LinearLayoutManager(MainActivity.this));
        recyclerView.setAdapter(new MyAdapter(client));
    }

}