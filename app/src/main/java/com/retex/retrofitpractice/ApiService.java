package com.retex.retrofitpractice;

import java.util.List;

import retrofit2.Call;
import retrofit2.http.Body;
import retrofit2.http.POST;

public interface ApiService {
    @POST("retrieve.php")
    Call<List<User>> user();
}
