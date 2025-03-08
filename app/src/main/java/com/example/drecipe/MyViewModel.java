package com.example.drecipe;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;

import java.util.List;

public class MyViewModel extends AndroidViewModel {
    private RepositoryRecipe repositoryRecipe;

    public MyViewModel(@NonNull Application application) {
        super(application);
        this.repositoryRecipe = new RepositoryRecipe(application);
    }
    public MutableLiveData<Recipe> setSelectedRecipe = new MutableLiveData<>();
    public void addNewRecipe(Recipe recipe){
        repositoryRecipe.addReceipe(recipe);
    }

    public void removeRecipe(Recipe recipe){
        repositoryRecipe.deleteReceipe(recipe);
    }

    public void updateRecipe(Recipe recipe){
        repositoryRecipe.updateReceipe(recipe);
    }

    public LiveData<List<Recipe>> getAllReceipe(){
        return repositoryRecipe.getALLRecipe();
    }

    public LiveData<Recipe> getSelectedRecipe(int id) {
        return repositoryRecipe.getSelectedRecipe(id);
    }

    public void setRecipe(Recipe recipe){
        setSelectedRecipe.setValue(recipe);
    }

    public MutableLiveData<Recipe> getRecipe(){
        return setSelectedRecipe;
    }


}
