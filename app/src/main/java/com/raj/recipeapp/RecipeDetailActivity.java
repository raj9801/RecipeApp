package com.raj.recipeapp;

import android.os.Bundle;
import android.util.Log;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

import com.bumptech.glide.Glide;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle, recipeDescription, recipeIngredients, recipeSteps;
    private ImageView recipeImage;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();

        // Bind views
        recipeTitle = findViewById(R.id.details_recipeTitle);
        recipeDescription = findViewById(R.id.details_recipeDescription);
        recipeIngredients = findViewById(R.id.details_ingredients);
        recipeSteps = findViewById(R.id.details_recipeFull);
        recipeImage = findViewById(R.id.details_recipeImage);

        // Get recipe title from intent
        String titleFromIntent = getIntent().getStringExtra("title");

        if (titleFromIntent != null && !titleFromIntent.trim().isEmpty()) {
            fetchRecipeByTitle(titleFromIntent);
        } else {
            Toast.makeText(this, "Invalid recipe title", Toast.LENGTH_SHORT).show();
            finish(); // End activity if no title
        }

        // Set up toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setTitle("Recipe Details");
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);
        }
    }

    private void fetchRecipeByTitle(String title) {
        firestore.collection("RecipeApp")
                .whereEqualTo("title", title)
                .get()
                .addOnSuccessListener(queryDocumentSnapshots -> {
                    if (!queryDocumentSnapshots.isEmpty()) {
                        for (QueryDocumentSnapshot doc : queryDocumentSnapshots) {
                            String titleStr = doc.getString("title");
                            String description = doc.getString("description");
                            String imageUrl = doc.getString("imageUrl");
                            String ingredients = doc.getString("ingredients");
                            String steps = doc.getString("steps");

                            recipeTitle.setText(titleStr != null ? titleStr : "No Title");
                            recipeDescription.setText(description != null ? description : "No Description");

                            recipeIngredients.setText(ingredients != null ? ingredients.trim() : "No ingredients available.");
                            recipeSteps.setText(steps != null ? steps.trim().replaceAll("(?m)^\\d+\\.", " $0") : "No instructions available.");

                            Glide.with(this)
                                    .load(imageUrl)
                                    .into(recipeImage);
                        }
                    } else {
                        Toast.makeText(this, "Recipe not found", Toast.LENGTH_SHORT).show();
                    }
                })
                .addOnFailureListener(e -> {
                    Log.e("Firestore", "Error fetching recipe", e);
                    Toast.makeText(this, "Failed to load recipe", Toast.LENGTH_SHORT).show();
                });
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed();
        return true;
    }
}

