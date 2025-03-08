package com.example.drecipe;

import android.content.Context;

import androidx.annotation.NonNull;
import androidx.room.Database;
import androidx.room.DatabaseConfiguration;
import androidx.room.InvalidationTracker;
import androidx.room.Room;
import androidx.room.RoomDatabase;
import androidx.room.TypeConverters;
import androidx.sqlite.db.SupportSQLiteOpenHelper;
@Database(entities = {Recipe.class}, version = 1)
@TypeConverters(Converters.class)
public abstract class DB_Recipe extends RoomDatabase {
    public abstract RecipeDAO getRecipeDAO();

    public static DB_Recipe dbRecipe;

    public static synchronized DB_Recipe getInstance(Context context){
        if(dbRecipe == null){
            dbRecipe = Room.databaseBuilder(context.getApplicationContext(),
                    DB_Recipe.class,
                    "recipe_db").fallbackToDestructiveMigration().build();
        }
        return dbRecipe;
    }
}
