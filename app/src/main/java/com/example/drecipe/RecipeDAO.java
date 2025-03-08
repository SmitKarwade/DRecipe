package com.example.drecipe;

import static android.icu.text.MessagePattern.ArgType.SELECT;

import androidx.lifecycle.LiveData;
import androidx.room.Dao;
import androidx.room.Delete;
import androidx.room.Insert;
import androidx.room.Query;
import androidx.room.Update;

import java.util.List;

@Dao
public interface RecipeDAO {
    @Insert
    void insert(Recipe recipe);

    @Update
    void update(Recipe recipe);

    @Delete
    void delete(Recipe recipe);

    @Query("SELECT * FROM MyRecipe" )
    LiveData<List<Recipe>> getAllRecipe();

    @Query("SELECT * FROM MyRecipe WHERE Id = :searchId")
    Recipe searchRecipes(int searchId);


}
