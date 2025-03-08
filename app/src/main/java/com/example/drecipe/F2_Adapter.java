package com.example.drecipe;

import android.content.Context;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AutoCompleteTextView;
import android.widget.EditText;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.RecyclerView;

import com.example.drecipe.databinding.F2IngredientItemBinding;

import java.util.ArrayList;
import java.util.List;

public class F2_Adapter extends RecyclerView.Adapter<F2_Adapter.f2_ViewHolder>{
    List<Ingredient> f2_ingredients;
    boolean isEditable = false;
    private Context context;
    private MyViewModel viewModel;
    public F2_Adapter(List<Ingredient> f2_ingredients, Context context, MyViewModel viewModel) {
        this.f2_ingredients = f2_ingredients;
        this.context = context;
        this.viewModel = viewModel;
    }

    public void setEditable(boolean isEditable){
        this.isEditable = isEditable;
        notifyDataSetChanged();
    }

    @NonNull
    @Override
    public f2_ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        F2IngredientItemBinding f2IngredientItemBinding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.f2_ingredient_item,
                parent,
                false);
        return new f2_ViewHolder(f2IngredientItemBinding);
    }

    @Override
    public void onBindViewHolder(@NonNull f2_ViewHolder holder, int position) {
        Ingredient ingredient = f2_ingredients.get(position);
        holder.f2IngredientItemBinding.setF2Ingredient(ingredient);
        holder.bind(isEditable);
        holder.f2IngredientItemBinding.button3.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                int getPosition = holder.getAdapterPosition();
                if(getPosition != RecyclerView.NO_POSITION){
                    f2_ingredients.remove(getPosition);
                    notifyItemRemoved(getPosition);
                }
                updateDatabase();
            }
        });
    }

    private void updateDatabase() {
        viewModel.getRecipe().observeForever(new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                recipe.getIngredients().clear();
                recipe.getIngredients().addAll(f2_ingredients);
                viewModel.updateRecipe(recipe);
            }
        });

    }

    @Override
    public int getItemCount() {
        return f2_ingredients.size();
    }

    public class f2_ViewHolder extends RecyclerView.ViewHolder {
        F2IngredientItemBinding f2IngredientItemBinding;
        public f2_ViewHolder(@NonNull F2IngredientItemBinding f2IngredientItemBinding) {
            super(f2IngredientItemBinding.getRoot());
            this.f2IngredientItemBinding = f2IngredientItemBinding;
        }

        public void bind(boolean isEditable) {
            EditText quantityEditText = f2IngredientItemBinding.editTextTextPassword;
            AutoCompleteTextView nameAutoCompleteTextView = f2IngredientItemBinding.autoCompleteTextView;
            AutoCompleteTextView unitAutoCompleteTextView = f2IngredientItemBinding.autoCompleteTextView2;

            quantityEditText.setEnabled(isEditable);
            nameAutoCompleteTextView.setEnabled(isEditable);
            unitAutoCompleteTextView.setEnabled(isEditable);
        }
    }

}
