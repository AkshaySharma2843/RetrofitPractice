package com.retex.retrofitpractice;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;

import okhttp3.OkHttpClient;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

public class RetrofitFactory {
private Retrofit retrofit;

    Gson gson = new GsonBuilder()
            .setLenient()
            .create();

public Retrofit CreateRetrofitInstance(){


    if(retrofit!=null){
        return retrofit;
    }
    else{
        return createRetrofit();

    }

}

    private Retrofit createRetrofit() {

    return new Retrofit.Builder()
            .baseUrl("https://akshay.jainsoftware.in/expenses_track/")
            .addConverterFactory(GsonConverterFactory.create(gson))
            .build();
    }
    private OkHttpClient CreateOkHttpClient(){
        HttpLoggingInterceptor loggingInterceptor = new HttpLoggingInterceptor();
        loggingInterceptor.setLevel(HttpLoggingInterceptor.Level.BODY);
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .addInterceptor(loggingInterceptor)
                .build();
        return  okHttpClient;
    }

}
