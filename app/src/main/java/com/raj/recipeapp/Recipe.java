package com.raj.recipeapp;

public class Recipe {
    private int id;
    private String title;
    private String description;
    private String imageUrl;
    private String ingredients;
    private String steps;

    // Empty constructor required for Firebase
    public Recipe() {
    }

    // Full constructor
    public Recipe(int id, String title, String description, String imageUrl, String ingredients, String steps) {
        this.id = id;
        this.title = title;
        this.description = description;
        this.imageUrl = imageUrl;
        this.ingredients = ingredients;
        this.steps = steps;
    }

    // Getters
    public int getId() {
        return id;
    }

    public String getTitle() {
        return title;
    }

    public String getDescription() {
        return description;
    }

    public String getImageUrl() {
        return imageUrl;
    }

    public String getIngredients() {
        return ingredients;
    }

    public String getSteps() {
        return steps;
    }
}

