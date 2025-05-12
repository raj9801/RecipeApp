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

        // Setup RecyclerView
        recyclerView = findViewById(R.id.recipeRecyclerView);
        recyclerView.setLayoutManager(new LinearLayoutManager(this));
        recipeList = new ArrayList<>();
        recipeAdapter = new RecipeAdapter(this, recipeList);
        recyclerView.setAdapter(recipeAdapter);

        // Setup Toolbar
        MaterialToolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        toolbar.setTitleTextColor(getResources().getColor(android.R.color.white));

        // Load recipes
        loadRecipesFromFirestore();
        insertSampleRecipes();
    }

    private void loadRecipesFromFirestore() {
        CollectionReference recipesRef = firestore.collection("RecipeApp");
        recipeList.clear();

        recipesRef.get().addOnCompleteListener(task -> {
            if (task.isSuccessful()) {
                if (task.getResult().isEmpty()) {
                    insertSampleRecipes(); // Seed if empty
                } else {
                    for (QueryDocumentSnapshot document : task.getResult()) {
                        Recipe recipe = document.toObject(Recipe.class);
                        recipeList.add(recipe);
                    }
                    recipeAdapter.notifyDataSetChanged();
                }
            } else {
                Log.e("Firestore", "Error loading data: ", task.getException());
            }
        });
    }

    private void insertSampleRecipes() {
        CollectionReference recipesRef = firestore.collection("RecipeApp");

        recipesRef.document("1").set(new Recipe(1, "Paneer Tikka",
                "Grilled chunks of paneer marinated in spiced yogurt.",
                "https://c.ndtvimg.com/2024-07/rvdidqqo_paneer-tikka_120x90_01_July_24.jpg",
                "• 250g paneer\n• 1/2 cup yogurt\n• 1 tsp red chili powder\n• 1/2 tsp turmeric\n• 1 tsp garam masala\n• 1 tbsp lemon juice\n• 1 tbsp ginger-garlic paste\n• Salt to taste",
                "1. Cut paneer into cubes.\n2. Prepare marinade and coat paneer.\n3. Refrigerate for 1 hour.\n4. Skewer and grill until golden.\n5. Serve with mint chutney."));

        recipesRef.document("2").set(new Recipe(2, "Chole Bhature",
                "Spicy chickpeas with deep-fried fermented bread.",
                "https://sitaramdiwanchand.com/blog/wp-content/uploads/2024/04/Image-1-1-1024x576.webp",
                "• 1 cup soaked chickpeas\n• 2 onions\n• 2 tomatoes\n• 1 tbsp ginger-garlic paste\n• Spices (chole masala, turmeric, garam masala)\n• 2 cups maida\n• 1/4 cup yogurt\n• Baking soda, salt, water",
                "1. Cook chickpeas.\n2. Make curry base and simmer.\n3. Prepare dough and let rest.\n4. Roll and deep fry.\n5. Serve with onions and lemon."));

        recipesRef.document("3").set(new Recipe(3, "Masala Dosa",
                "Crispy rice crepe with spiced potato filling.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcRHu-vEn4T3y8SSfgMjXyP64CZl8DXjSt5yhw&s",
                "• 1 cup rice\n• 1/2 cup urad dal\n• 2 potatoes\n• 1 onion\n• Curry leaves, mustard seeds\n• Turmeric, salt",
                "1. Soak and ferment batter.\n2. Make potato filling.\n3. Spread dosa batter on pan.\n4. Add filling, fold, and serve."));

        recipesRef.document("4").set(new Recipe(4, "Rajma Chawal",
                "Red kidney bean curry served with rice.",
                "https://www.kuchpakrahahai.in/wp-content/uploads/2023/05/Rajma-chawal.jpg",
                "• 1 cup rajma\n• 2 onions\n• 2 tomatoes\n• 1 tbsp ginger-garlic paste\n• Spices (cumin, chili powder, garam masala)\n• Salt, oil, water",
                "1. Soak and boil rajma.\n2. Cook masala base.\n3. Add rajma and simmer.\n4. Serve with rice."));

        recipesRef.document("5").set(new Recipe(5, "Pav Bhaji",
                "Mashed vegetables in spicy curry served with buns.",
                "https://encrypted-tbn0.gstatic.com/images?q=tbn:ANd9GcSOwW1NJozZMtgGB1wAyO3PUxXkzgSLtQ78Cw&s",
                "• Potatoes, peas, cauliflower\n• Onion, tomato, capsicum\n• Pav bhaji masala\n• Butter\n• Lemon, buns",
                "1. Boil and mash vegetables.\n2. Sauté onion, capsicum, tomatoes.\n3. Add spices and mix mashed veggies.\n4. Toast buns.\n5. Serve hot."));

        recipesRef.document("6").set(new Recipe(6, "Veg Pulao",
                "Rice cooked with spices and vegetables.",
                "https://blogger.googleusercontent.com/img/b/R29vZ2xl/AVvXsEgFPjIluL4H7E7Qznz6kq0DziLeDpnSg0RqM_0J-3nemYyxHbWSK0EaaAZ70NBiy1kLAFy5ZCxgBfCpETdfnL2oSqhS88KOdNTexX9Me5y1xKbFCU0D0DocySLaCPdiP1eXT7dvEWHXstDA/s1600/vegetable+pulao.JPG",
                "• 1 cup basmati rice\n• Chopped carrots, peas, beans\n• Whole spices (bay leaf, cinnamon)\n• Onion, oil, salt",
                "1. Soak rice.\n2. Sauté spices and onion.\n3. Add vegetables and rice.\n4. Add water and cook.\n5. Serve hot."));

        recipesRef.document("7").set(new Recipe(7, "Aloo Paratha",
                "Stuffed flatbread with spicy potatoes.",
                "https://www.saffrontrail.com/wp-content/uploads/2015/05/recipe-for-aloo-parathas-potato-stuffed-indian-flatbread.1024x1024.jpg",
                "• 2 boiled potatoes\n• 1 cup wheat flour\n• Spices (chili, cumin, garam masala)\n• Coriander, ghee",
                "1. Make dough.\n2. Prepare potato filling.\n3. Stuff and roll parathas.\n4. Cook on tawa with ghee."));

        recipesRef.document("8").set(new Recipe(8, "Idli Sambhar",
                "Steamed rice cakes with lentil soup.",
                "https://static.toiimg.com/thumb/msid-113810989,width-1280,height-720,resizemode-4/113810989.jpg",
                "• 1 cup idli rice\n• 1/2 cup urad dal\n• 1/2 cup toor dal\n• Tamarind, vegetables\n• Sambhar powder",
                "1. Soak and ferment batter.\n2. Steam idlis.\n3. Cook sambhar with dal and veg.\n4. Serve with idli."));

        recipesRef.document("9").set(new Recipe(9, "Dhokla",
                "Savory steamed cake from gram flour.",
                "https://myheartbeets.com/wp-content/uploads/2019/11/khaman-dhokla-besan-dhokla-instant.jpg",
                "• 1 cup besan\n• 1 tsp Eno\n• Lemon juice\n• Water, turmeric\n• Mustard seeds, curry leaves",
                "1. Make smooth batter.\n2. Add Eno and steam.\n3. Prepare tempering.\n4. Pour over dhokla."));

        recipesRef.document("10").set(new Recipe(10, "Sev Usal",
                "Spicy peas curry topped with sev.",
                "https://images.slurrp.com/webstories/wp-content/uploads/2023/05/cropped-shutterstock_2079986389-1.jpg",
                "• White peas (soaked)\n• Onion, tomato, garlic\n• Spices (garam masala, chili powder)\n• Sev, coriander, pav",
                "1. Boil peas.\n2. Make masala curry.\n3. Simmer until thick.\n4. Top with sev and serve with pav."));
    }

}

