package com.sm.sdk.myapplication;

import android.content.Context;
import android.graphics.Bitmap;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;

import java.util.ArrayList;
import java.util.List;

public class RecyclerCaptureImage extends RecyclerView.Adapter<RecyclerCaptureImage.VieweHolder> {
    Context context;
    ArrayList<DataImageCapture> imageList;


    public RecyclerCaptureImage(Context context, ArrayList<DataImageCapture> postList) {
        this.context = context;
        imageList = postList;

    }

    @NonNull
    @Override
    public VieweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.capture_image, parent, false);
        return new VieweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerCaptureImage.VieweHolder holder, int position) {
        DataImageCapture dataAssetsImage = imageList.get(position);
        Glide.with(context).load(dataAssetsImage.getImage()).into(holder.imageView1);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class VieweHolder extends RecyclerView.ViewHolder {

        ImageView imageView1;
        ConstraintLayout constraintLayout;

        public VieweHolder(@NonNull View itemView) {
            super(itemView);
            imageView1 = itemView.findViewById(R.id.captureImage);
            constraintLayout = itemView.findViewById(R.id.main_layout_capture);
        }
    }
}
