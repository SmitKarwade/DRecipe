package com.example.drecipe;

import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drecipe.databinding.ActivityMainBinding;
import com.example.drecipe.databinding.RecipeItemsBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

public class RecyclerAdapter extends RecyclerView.Adapter<RecyclerAdapter.MyViewholder> {
    private ArrayList<Recipe> recipeArrayList;
    private Set<Integer> selectedPosition = new HashSet<>();
    Context context;
    boolean isInSelctionMode = false;
    private onItemLongClick onitemlongclick;
    private MyViewModel viewModel;
    public RecyclerAdapter(Context context,ArrayList<Recipe> recipeArrayList, onItemLongClick onItemLongClick, MyViewModel viewModel) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;
        this.onitemlongclick = onItemLongClick;
        this.viewModel = viewModel;
    }
    public RecyclerAdapter(Context context,ArrayList<Recipe> recipeArrayList) {
        this.context = context;
        this.recipeArrayList = recipeArrayList;}

    @NonNull
    @Override
    public MyViewholder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        RecipeItemsBinding recipeItemsBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.recipe_items,
                parent,
                false);
        return new MyViewholder(recipeItemsBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull MyViewholder holder, int position) {
        Recipe recipe = recipeArrayList.get(position);
        holder.recipeItemsBinding.setRecipe(recipe);
        holder.changeBackground(selectedPosition.contains(position));
//        holder.itemView.setOnClickListener(new View.OnClickListener() {
//            @Override
//            public void onClick(View v) {
//                Toast.makeText(context, "Item clicked " + recipe.getTitle(), Toast.LENGTH_SHORT).show();
//            }
//        });
        holder.recipeItemsBinding.getRoot().setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if(isInSelctionMode){
                    toggleSelection(holder.getAdapterPosition());
                }else {
                    Toast.makeText(context, "Item clicked " + recipe.getTitle(), Toast.LENGTH_SHORT).show();
                    Intent intent = new Intent(context, MainActivity2.class);
                    intent.putExtra("Recipe", ""+ recipe.getId());
                    context.startActivity(intent);
                }

            }
        });
        holder.recipeItemsBinding.getRoot().setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                int getPosition = holder.getAdapterPosition();
                isInSelctionMode = true;
                if(onitemlongclick != null){
                    onitemlongclick.onLongClick(isInSelctionMode);
                }
                toggleSelection(getPosition);
                return true;
            }
        });
    }

    public void toggleSelection(int pos){
        if(selectedPosition.contains(pos)){
            selectedPosition.remove(pos);
        }else{
            selectedPosition.add(pos);
        }
        notifyDataSetChanged();
    }

    public void onDelete(){
        if(selectedPosition.isEmpty()){
            isInSelctionMode = false;
            notifyDataSetChanged();
            onitemlongclick.onLongClick(isInSelctionMode);
        }else {
            ArrayList<Recipe> recipesToRemove = new ArrayList<>();
            for (int i = 0; i < recipeArrayList.size(); i++) {
                if (selectedPosition.contains(i)) {
                    recipesToRemove.add(recipeArrayList.get(i));
                }
            }

            // Remove collected items
            for (Recipe recipe : recipesToRemove) {
                recipeArrayList.remove(recipe);
                viewModel.removeRecipe(recipe);
            }

            // Clear selected positions
            selectedPosition.clear();
            isInSelctionMode = false;
            notifyDataSetChanged();
        }
    }
    public void deleteAll(){
        List<Recipe> recipes = new ArrayList<>(recipeArrayList);
        for (Recipe r:recipes
             ) {
            viewModel.removeRecipe(r);
        }
        recipeArrayList.clear();
        selectedPosition.clear();
        notifyDataSetChanged();
    }

    @Override
    public int getItemCount() {
        return recipeArrayList.size();
    }

    public class MyViewholder extends RecyclerView.ViewHolder{

        RecipeItemsBinding recipeItemsBinding;
        public MyViewholder(RecipeItemsBinding recipeItemsBinding ) {
            super(recipeItemsBinding.getRoot());
            this.recipeItemsBinding = recipeItemsBinding;
        }

        public void changeBackground(boolean isSelected) {
            recipeItemsBinding.itemCardView.setBackgroundColor(isSelected ? Color.LTGRAY : Color.TRANSPARENT);
        }
    }
}
