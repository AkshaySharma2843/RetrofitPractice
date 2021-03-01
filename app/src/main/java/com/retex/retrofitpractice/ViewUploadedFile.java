package com.retex.retrofitpractice;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;
import android.util.Log;
import android.widget.Toast;

import com.android.volley.Request;
import com.android.volley.RequestQueue;
import com.android.volley.Response;
import com.android.volley.VolleyError;
import com.android.volley.toolbox.StringRequest;
import com.android.volley.toolbox.Volley;

import org.json.JSONArray;
import org.json.JSONObject;

import java.util.ArrayList;

public class ViewUploadedFile extends AppCompatActivity {

    ArrayList<ImageModel> imageModelList = new ArrayList<>();
    RecyclerView recyclerView;
    String col_id;
    String id = "";
    ImageModel imageModel;
    ViewUploadedAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_view_uploaded_file);
        col_id = getIntent().getStringExtra("lead_id");
        initView();
        getImage();
    }

    private void getImage() {
        if(id.equals(col_id)){

            StringRequest stringRequest = new StringRequest(Request.Method.GET, "http://arjun.jain.software/arjunguru/get_image.php",
                    new Response.Listener<String>() {
                        @Override
                        public void onResponse(String response) {
                            imageModelList.clear();
                            try {
                                Log.e("jokjkjk", response);
                                // JSONObject jsonObject = new JSONObject(response);
                                JSONArray jsonArray = new JSONArray(response);


                                for (int i = 0; i < jsonArray.length(); i++) {

                                    JSONObject object = jsonArray.getJSONObject(i);

                                    String id = object.getString("id");
                                    String col_lead_id = object.getString("col_lead_id");
                                    String user_image = object.getString("user_image");


                                    imageModel = new ImageModel(id, col_lead_id, user_image);
                                    imageModelList.add(imageModel);

                                }
                                adapter = new ViewUploadedAdapter(imageModelList, ViewUploadedFile.this);
                                recyclerView.setLayoutManager(new LinearLayoutManager(ViewUploadedFile.this));
                                recyclerView.setAdapter(adapter);
                                adapter.notifyDataSetChanged();

                            } catch (Exception e) {
                                e.printStackTrace();

                            }
                        }
                    }, new Response.ErrorListener() {
                @Override
                public void onErrorResponse(VolleyError error) {
                    Toast.makeText(ViewUploadedFile.this, error.getMessage(), Toast.LENGTH_SHORT).show();
                }
            });
            RequestQueue requestQueue = Volley.newRequestQueue(this);
            requestQueue.add(stringRequest);
        }
    }

    private void initView() {
        recyclerView = findViewById(R.id.rec_view_image);
    }
}
