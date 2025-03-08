package com.example.drecipe;

import androidx.room.TypeConverter;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import java.lang.reflect.Type;
import java.util.List;
public class Converters {
    @TypeConverter
    public static String fromIngredientsList(List<Ingredient> ingredients) {
        if (ingredients == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.toJson(ingredients, type);
    }

    @TypeConverter
    public static List<Ingredient> toIngredientsList(String ingredientsString) {
        if (ingredientsString == null) {
            return null;
        }
        Gson gson = new Gson();
        Type type = new TypeToken<List<Ingredient>>() {}.getType();
        return gson.fromJson(ingredientsString, type);
    }
}
