package com.example.drecipe;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drecipe.databinding.Fragment1ItemBinding;

import java.util.ArrayList;

public class AdapterIngredients extends RecyclerView.Adapter<AdapterIngredients.ViewHoder2>{
    private ArrayList<Ingredient>  ingredients ;

    public AdapterIngredients(ArrayList<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    @NonNull
    @Override
    public ViewHoder2 onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Fragment1ItemBinding fragment1ItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.fragment1_item,
                parent,
                false);
        return new ViewHoder2(fragment1ItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHoder2 holder, int position) {
        Ingredient ItemIngredient = ingredients.get(position);
        holder.fragment1ItemBinding.setIngredient(ItemIngredient);
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    public class ViewHoder2 extends RecyclerView.ViewHolder {
        Fragment1ItemBinding fragment1ItemBinding;
        public ViewHoder2(@NonNull Fragment1ItemBinding fragment1ItemBinding) {
            super(fragment1ItemBinding.getRoot());
            this.fragment1ItemBinding = fragment1ItemBinding;
        }
    }
}
