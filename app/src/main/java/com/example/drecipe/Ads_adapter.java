package com.example.drecipe;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ArrayAdapter;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drecipe.databinding.AdsLayoutBinding;

import java.util.ArrayList;

public class Ads_adapter extends RecyclerView.Adapter<Ads_adapter.MyViewholder2> {
    private ArrayList<Ads_Recipe> adsArrayList;
    private Context context;


    public Ads_adapter(Context context, ArrayList<Ads_Recipe> adsArrayList) {
        this.context = context;
        this.adsArrayList = adsArrayList;
    }

    public Ads_adapter() {
    }

    @NonNull
    @Override
    public MyViewholder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AdsLayoutBinding adsLayoutBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.ads_layout,
                parent,
                false);
        return new MyViewholder2(adsLayoutBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder2 holder, int position) {
        Ads_Recipe adsRecipe = adsArrayList.get(position);
        holder.imageView3.setImageResource(adsRecipe.imageRes);
        holder.adsLayoutBinding.setAdsRecipe(adsRecipe);
    }

    @Override
    public int getItemCount() {
        return adsArrayList.size();
    }

    public class MyViewholder2 extends RecyclerView.ViewHolder {
        AdsLayoutBinding adsLayoutBinding;
        ImageView imageView3;
        public MyViewholder2(@NonNull AdsLayoutBinding adsLayoutBinding) {
            super(adsLayoutBinding.getRoot());
            this.adsLayoutBinding = adsLayoutBinding;
            imageView3 = adsLayoutBinding.getRoot().findViewById(R.id.imageView3);
        }
    }
}
