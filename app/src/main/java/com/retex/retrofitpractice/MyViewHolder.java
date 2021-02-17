package com.retex.retrofitpractice;

import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

public class MyViewHolder extends RecyclerView.ViewHolder {

    TextView name;
    TextView account_number;
    TextView phone_number;

    public MyViewHolder(@NonNull View itemView) {
        super(itemView);


        name = itemView.findViewById(R.id.tv_name);
        account_number = itemView.findViewById(R.id.tv_account);
        phone_number = itemView.findViewById(R.id.tv_phone);


    }

    public void bind(User client) {

        name.setText(client.getName());
        account_number.setText(client.getAccountNumber());
        phone_number.setText(client.getPhoneNumber());
    }
}
