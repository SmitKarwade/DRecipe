package com.example.drecipe;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.Toast;

import com.example.drecipe.databinding.ActivityAddNewRecipeBinding;

import java.util.ArrayList;
import java.util.List;

public class AddNewRecipe extends AppCompatActivity {
    ArrayList<Ingredient>  ingredientArrayList = new ArrayList<>();
    MyViewModel viewModel;
    ActivityAddNewRecipeBinding activityAddNewRecipeBinding;
    Recipe recipe;
    RecyclerView f3recyclerView;
    Add_new_Adapter addNewAdapter;
    List<Recipe> recipesList;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_new_recipe);

        activityAddNewRecipeBinding = DataBindingUtil.setContentView(this, R.layout.activity_add_new_recipe);

        viewModel = new ViewModelProvider(this).get(MyViewModel.class);
        ingredientArrayList.add(new Ingredient("", "", ""));
        recipe = new Recipe("", ingredientArrayList, "", "", "");
        activityAddNewRecipeBinding.setNewrecipe(recipe);

        f3recyclerView = activityAddNewRecipeBinding.f3RecyclerView;
        f3recyclerView.setLayoutManager(new LinearLayoutManager(this));
        addNewAdapter = new Add_new_Adapter(ingredientArrayList, viewModel);
        f3recyclerView.setAdapter(addNewAdapter);

        activityAddNewRecipeBinding.f3addbtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ingredientArrayList.add(new Ingredient("", "", ""));
                addNewAdapter.notifyItemInserted(ingredientArrayList.size()-1);
            }
        });
        viewModel.getAllReceipe().observe(AddNewRecipe.this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                recipesList = recipes;
            }
        });
        activityAddNewRecipeBinding.f3save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String recipeTitle = activityAddNewRecipeBinding.f3editText.getText().toString();
                if (recipeTitle == null || recipeTitle.isEmpty()) {
                    Toast.makeText(AddNewRecipe.this, "Recipe name cannot be empty", Toast.LENGTH_SHORT).show();
                    return;
                }

                // Retrieve the current list of recipes from LiveData
                boolean isDuplicate = false;
                if (recipesList != null) {
                    for (Recipe r : recipesList) {
                        if (recipeTitle.equals(r.getTitle())) {
                            isDuplicate = true;
                            break;
                        }
                    }
                }

                if (isDuplicate) {
                    Toast.makeText(AddNewRecipe.this, "Recipe name cannot be the same", Toast.LENGTH_SHORT).show();
                } else {
                    viewModel.addNewRecipe(recipe);
                    Toast.makeText(AddNewRecipe.this, "Recipe added successfully", Toast.LENGTH_SHORT).show();
                }
            }
        });


    }
}