package com.sm.sdk.myapplication.Recycler;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.recyclerview.widget.RecyclerView;

import com.bumptech.glide.Glide;
import com.sm.sdk.myapplication.Data.DataAssetImages;
import com.sm.sdk.myapplication.R;

import java.util.List;

public class RecyclerAssetImage extends RecyclerView.Adapter<RecyclerAssetImage.VieweHolder> {
    Context context;
    List<DataAssetImages> imageList;

    public RecyclerAssetImage(Context context, List<DataAssetImages> postList) {
        this.context = context;
        imageList = postList;

    }

    @NonNull
    @Override
    public VieweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(context).inflate(R.layout.list_image, parent, false);
        return new VieweHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull VieweHolder holder, int position) {
        DataAssetImages dataAssetsImage = imageList.get(position);
        Glide.with(context).load(dataAssetsImage.getImage()).into(holder.imageView);
    }

    @Override
    public int getItemCount() {
        return imageList.size();
    }

    public class VieweHolder extends RecyclerView.ViewHolder {

        ImageView imageView;
        View view;
        ConstraintLayout constraintLayout;

        public VieweHolder(@NonNull View itemView) {
            super(itemView);
            imageView = itemView.findViewById(R.id.imageview);
            constraintLayout = itemView.findViewById(R.id.main_layout);
        }
    }
}
