package com.raj.recipeapp;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.android.material.appbar.MaterialToolbar;
import com.google.firebase.FirebaseApp;
import com.google.firebase.firestore.CollectionReference;
import com.google.firebase.firestore.FirebaseFirestore;
import com.google.firebase.firestore.QueryDocumentSnapshot;

import java.util.ArrayList;
import java.util.List;

public class MainActivity extends AppCompatActivity {

    private RecyclerView recyclerView;
    private RecipeAdapter recipeAdapter;
    private List<Recipe> recipeList;
    private FirebaseFirestore firestore;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Initialize Firebase
        FirebaseApp.initializeApp(this);
        firestore = FirebaseFirestore.getInstance();

        insertSampleRecipes();

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        getSupportActionBar().setTitle("Recipe App");
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        // Load recipes from Firestore
        loadRecipesFromFirestore();
    }

    private void loadRecipesFromFirestore() {
        CollectionReference recipesRef = firestore.collection("RecipeApp");
        recipeList.clear();

        recipesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                for (QueryDocumentSnapshot document : task.getResult()) {
                    Recipe recipe = document.toObject(Recipe.class);
                    recipeList.add(recipe);
                }
                recipeAdapter.notifyDataSetChanged();
            } else {
                Log.e("Firestore", "Error loading data: ", task.getException());
            }
        });
    }

    private void insertSampleRecipes() {
        CollectionReference recipesRef = firestore.collection("RecipeApp");

        recipesRef.document("1").set(new Recipe(1, "Paneer Tikka", "Grilled chunks of paneer marinated in spices and yogurt.", "https://c.ndtvimg.com/2024-07/rvdidqqo_paneer-tikka_120x90_01_July_24.jpg"));
        recipesRef.document("2").set(new Recipe(2, "Chole Bhature", "Spicy chickpea curry served with deep-fried bread.", "https://sitaramdiwanchand.com/blog/wp-content/uploads/2024/04/Image-1-1-1024x576.webp"));
        recipesRef.document("3").set(new Recipe(3, "Masala Dosa", "Crispy fermented rice crepe filled with spiced potatoes.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHu-vEn4T3y8SSfgMjXyP64CZl8DXjSt5yhw&s"));
        recipesRef.document("4").set(new Recipe(4, "Rajma Chawal", "Red kidney beans in thick curry served with steamed rice.", "https://www.kuchpakrahahai.in/wp-content/uploads/2023/05/Rajma-chawal.jpg"));
        recipesRef.document("5").set(new Recipe(5, "Pav Bhaji", "Spiced mashed vegetables served with buttered buns.", "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOwW1NJozZMtgGB1wAyO3PUxXkzgSLtQ78Cw&s"));
        recipesRef.document("6").set(new Recipe(6, "Veg Pulao", "Fragrant rice dish cooked with vegetables and spices.", "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgFPjIluL4H7E7Qznz6kq0DziLeDpnSg0RqM_0J-3nemYyxHbWSK0EaaAZ70NBiy1kLAFy5ZCxgBfCpETdfnL2oSqhS88KOdNTexX9Me5y1xKbFCU0D0DocySLaCPdiP1eXT7dvEWHXstDA/s1600/vegetable+pulao.JPG"));
        recipesRef.document("7").set(new Recipe(7, "Aloo Paratha", "Flatbread stuffed with spicy mashed potatoes.", "https://www.saffrontrail.com/wp-content/uploads/2015/05/recipe-for-aloo-parathas-potato-stuffed-indian-flatbread.1024x1024.jpg"));
        recipesRef.document("8").set(new Recipe(8, "Idli Sambhar", "Steamed rice cakes with a side of lentil soup.", "https://static.toiimg.com/thumb/msid-113810989,width-1280,height-720,resizemode-4/113810989.jpg"));
        recipesRef.document("9").set(new Recipe(9, "Dhokla", "Savory steamed cake made with fermented gram flour.", "https://myheartbeets.com/wp-content/uploads/2019/11/khaman-dhokla-besan-dhokla-instant.jpg"));
        recipesRef.document("10").set(new Recipe(10, "Sev Usal", "Spicy curry topped with sev and served with pav.", "https://images.slurrp.com/webstories/wp-content/uploads/2023/05/cropped-shutterstock_2079986389-1.jpg"));
    }
}
