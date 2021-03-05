package com.retex.retrofitpractice;

import androidx.appcompat.app.AppCompatActivity;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.text.Html;

public class Login extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_login);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.parseColor("#69C9F5")));
        getSupportActionBar().setTitle((Html.fromHtml("<font color=\"#000000\">" + "LogIn Menu" + "</font>")));
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
    }
}