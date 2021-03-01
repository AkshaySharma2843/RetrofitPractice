package com.retex.retrofitpractice;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ViewUploadedAdapter extends RecyclerView.Adapter<ViewUploadFileViewHolder> {
    ArrayList<ImageModel> imageModel;
    Context context;

    public ViewUploadedAdapter(ArrayList<ImageModel> imageModel, Context context) {
        this.imageModel = imageModel;
        this.context = context;
    }

    @NonNull
    @Override
    public ViewUploadFileViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        return new ViewUploadFileViewHolder(LayoutInflater.from(parent.getContext()).inflate(R.layout.view_image, parent, false));
    }

    @Override
    public void onBindViewHolder(@NonNull ViewUploadFileViewHolder holder, int position) {
        ImageModel imageModels = imageModel.get(position);
        holder.bind(imageModels, context);

    }

    @Override
    public int getItemCount() {
        return imageModel.size();
    }
}
