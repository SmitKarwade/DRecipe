package com.example.drecipe;

import android.app.Application;
import android.os.Handler;
import android.os.Looper;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

public class RepositoryRecipe {
    private final RecipeDAO recipeDAO;
    ExecutorService executor;
    Handler handler;

    public RepositoryRecipe(Application application) {
        DB_Recipe dbRecipe = DB_Recipe.getInstance(application);
        this.recipeDAO = dbRecipe.getRecipeDAO();

        // for background
        executor = Executors.newSingleThreadExecutor();

        // for updating the UI
        handler = new Handler(Looper.getMainLooper());
    }

    public void addReceipe(Recipe recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.insert(recipe);
            }
        });
    }

    public void deleteReceipe(Recipe recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.delete(recipe);
            }
        });
    }

    public void updateReceipe(Recipe recipe){
        executor.execute(new Runnable() {
            @Override
            public void run() {
                recipeDAO.update(recipe);
            }
        });
    }

    public LiveData<List<Recipe>> getALLRecipe(){
        return recipeDAO.getAllRecipe();
    }

    public LiveData<Recipe> getSelectedRecipe(int id) {
        MutableLiveData<Recipe> data = new MutableLiveData<>();
        Executors.newSingleThreadExecutor().execute(() -> {
            Recipe recipe = recipeDAO.searchRecipes(id);
            data.postValue(recipe);
        });
        return data;
    }

}
