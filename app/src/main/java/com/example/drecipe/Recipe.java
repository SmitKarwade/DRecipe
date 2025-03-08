package com.example.drecipe;

import androidx.room.ColumnInfo;
import androidx.room.Entity;
import androidx.room.PrimaryKey;

import java.util.List;

@Entity(tableName = "MyRecipe")
public class Recipe {
    @ColumnInfo(name = "Id")
    @PrimaryKey(autoGenerate = true)
    private int id;
    @ColumnInfo(name = "Title")
    private String title;
    @ColumnInfo(name = "Ingredients")
    private List<Ingredient> ingredients;
    @ColumnInfo(name = "Note")
    private String note;
    @ColumnInfo(name = "Category")
    private String category;
    @ColumnInfo(name = "Link")
    private String linkUrl;

    public Recipe(String title, List<Ingredient> ingredients, String note, String category, String linkUrl) {
        this.title = title;
        this.ingredients = ingredients;
        this.note = note;
        this.category = category;
        this.linkUrl = linkUrl;
    }

    public Recipe() {
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public List<Ingredient> getIngredients() {
        return ingredients;
    }

    public void setIngredients(List<Ingredient> ingredients) {
        this.ingredients = ingredients;
    }

    public String getNote() {
        return note;
    }

    public void setNote(String note) {
        this.note = note;
    }

    public String getCategory() {
        return category;
    }

    public void setCategory(String category) {
        this.category = category;
    }

    public String getLinkUrl() {
        return linkUrl;
    }

    public void setLinkUrl(String linkUrl) {
        this.linkUrl = linkUrl;
    }

}
