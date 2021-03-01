package com.retex.retrofitpractice;

import android.content.Context;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

public class ViewUploadFileViewHolder extends RecyclerView.ViewHolder {
    ImageView view_image;
    public ViewUploadFileViewHolder(@NonNull View itemView) {
        super(itemView);

        view_image = itemView.findViewById(R.id.iv_view_image);
    }


    public void bind(ImageModel imageModels, Context context) {
        Glide.with(itemView.getContext())
                .load(imageModels.getUser_image())
                .into(view_image);


    }
}
