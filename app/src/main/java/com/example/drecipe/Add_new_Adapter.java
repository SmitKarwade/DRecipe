package com.example.drecipe;

import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drecipe.databinding.AddnewrecipeitemBinding;

import java.util.ArrayList;

public class Add_new_Adapter extends RecyclerView.Adapter<Add_new_Adapter.NewViewholder>{
    ArrayList<Ingredient> ingredientArrayList;
    private  MyViewModel viewModel;

    public Add_new_Adapter(ArrayList<Ingredient> ingredientArrayList, MyViewModel viewModel) {
        this.ingredientArrayList = ingredientArrayList;
        this.viewModel = viewModel;
    }

    @NonNull
    @Override
    public NewViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        AddnewrecipeitemBinding addnewrecipeitemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.addnewrecipeitem,
                parent,
                false);
        return new NewViewholder(addnewrecipeitemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull NewViewholder holder, int position) {
        Ingredient ingredient = ingredientArrayList.get(position);
        holder.addnewrecipeitemBinding.setNewingredient(ingredient);
        holder.addnewrecipeitemBinding.executePendingBindings();
    }

    @Override
    public int getItemCount() {
        return ingredientArrayList.size();
    }

    public class NewViewholder extends RecyclerView.ViewHolder {
        AddnewrecipeitemBinding addnewrecipeitemBinding;
        public NewViewholder(@NonNull AddnewrecipeitemBinding addnewrecipeitemBinding) {
            super(addnewrecipeitemBinding.getRoot());
            this.addnewrecipeitemBinding = addnewrecipeitemBinding;
        }
    }
}
