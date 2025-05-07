package com.raj.recipeapp;

import android.os.Bundle;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.content.ContextCompat;

public class RecipeDetailActivity extends AppCompatActivity {

    private TextView recipeTitle, recipeDescription;
    private ImageView recipeImage;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        recipeTitle = findViewById(R.id.details_recipeTitle);
        recipeDescription = findViewById(R.id.details_recipeDescription);
        recipeImage = findViewById(R.id.details_recipeImage);

        String title = getIntent().getStringExtra("title");
        String description = getIntent().getStringExtra("description");
        int imageResource = getIntent().getIntExtra("imageResource", 0); // default to 0 if not found

        recipeTitle.setText(title);
        recipeDescription.setText(description);

        if (imageResource != 0) {
            recipeImage.setImageResource(imageResource);
        } else {
            recipeImage.setImageResource(R.drawable.pizza);
        }

        Toolbar toolbar = findViewById(R.id.toolbar);
        toolbar.setTitleTextColor(ContextCompat.getColor(this, R.color.white));
        setSupportActionBar(toolbar);
        if (getSupportActionBar() != null) {
            getSupportActionBar().setDisplayHomeAsUpEnabled(true);
            getSupportActionBar().setHomeAsUpIndicator(R.drawable.ic_arrow_back_white);

        }
    }

    @Override
    public boolean onSupportNavigateUp() {
        onBackPressed(); // Go back to the previous activity (MainActivity)
        return true;
    }
}
