package com.example.drecipe;

public class Ads_Recipe {
    String name;
    int imageRes;

    public Ads_Recipe(String name, int imageRes) {
        this.name = name;
        this.imageRes = imageRes;
    }

    public Ads_Recipe() {
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public int getImageRes() {
        return imageRes;
    }

    public void setImageRes(int imageRes) {
        this.imageRes = imageRes;
    }
}
