package com.sm.sdk.myapplication;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class RecyclerAssets extends RecyclerView.Adapter<RecyclerAssets.VieweHolder> {

    private ArrayList<DataAssets> arrayList;

    public RecyclerAssets(ArrayList<DataAssets> arrayList){
        this.arrayList = arrayList;
    }

    @NonNull
    @Override
    public RecyclerAssets.VieweHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater layoutInflater = LayoutInflater.from(parent.getContext());
        View inflate = layoutInflater.inflate(R.layout.list_assets, null);
        return new VieweHolder(inflate);
    }

    @Override
    public void onBindViewHolder(@NonNull RecyclerAssets.VieweHolder holder, int position) {
        DataAssets dataAssets =  arrayList.get(position);
        holder.Description.setText(dataAssets.getDescription());
        holder.Qty.setText(dataAssets.getQty());
        holder.serial_no.setText(dataAssets.getSerial_no());
        holder.remark.setText(dataAssets.getFixed_no());
        holder.fixed_no.setText(dataAssets.getRemark());
        holder.AssetsData.setText(dataAssets.getAssetsData());

    }

    @Override
    public int getItemCount() {
        return arrayList.size();
    }

    public class VieweHolder extends RecyclerView.ViewHolder {

        TextView Description;
        TextView Qty;
        TextView serial_no;
        TextView fixed_no;
        TextView remark;

        TextView AssetsData;

        public VieweHolder(@NonNull View itemView) {
            super(itemView);

            Description = itemView.findViewById(R.id.Description);
            Qty = itemView.findViewById(R.id.Qty);
            serial_no = itemView.findViewById(R.id.serial_no);
            remark = itemView.findViewById(R.id.remark);
            fixed_no = itemView.findViewById(R.id.fixed_no);
            AssetsData = itemView.findViewById(R.id.AssetsData);
        }
    }
}
