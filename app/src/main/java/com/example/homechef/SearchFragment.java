/* Assignment: 3
Campus: Ashdod
Authors:
Eliran Naduyev 312089105
Maria Garber
*/

package com.example.homechef;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

public class SearchFragment extends Fragment {
    private List<Ingredient> lstIngredient = new ArrayList<>();
    private RecyclerView myrv;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
        initializeIngredients();
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_search, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_search);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbarContact);
        myrv = RootView.findViewById(R.id.recycleview_ingredients);
        myrv.setLayoutManager(new GridLayoutManager(getActivity(), 3));
        RecyclerViewAdapterIngredient myAdapter = new RecyclerViewAdapterIngredient(getContext(), lstIngredient);
        myrv.setAdapter(myAdapter);

        Button searchIngredients = RootView.findViewById(R.id.ingredients_search);
        searchIngredients.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                List<String> tmp = RecyclerViewAdapterIngredient.ingredientsList;
                if(tmp.isEmpty()){
                    Toast.makeText(getActivity(), "You must select at least one ingredient", Toast.LENGTH_LONG).show();
                }
                else{
                    Intent searchResultsIntent = new Intent(getActivity(), SearchResultsActivity.class);
                    startActivity(searchResultsIntent);
                }
            }
        });


        return RootView;
    }

    private void initializeIngredients() {
        lstIngredient.add(new Ingredient("Beef", "beef-cubes-raw.png"));
        lstIngredient.add(new Ingredient("Fish", "fish-fillet.jpg"));
        lstIngredient.add(new Ingredient("Chicken", "chicken-breasts.png"));
        lstIngredient.add(new Ingredient("Tuna", "canned-tuna.png"));
        lstIngredient.add(new Ingredient("Flour", "flour.png"));
        lstIngredient.add(new Ingredient("Rice", "uncooked-white-rice.png"));
        lstIngredient.add(new Ingredient("Pasta", "fusilli.jpg"));
        lstIngredient.add(new Ingredient("Cheese", "cheddar-cheese.png"));
        lstIngredient.add(new Ingredient("Butter", "butter.png"));
        lstIngredient.add(new Ingredient("Bread", "white-bread.jpg"));
        lstIngredient.add(new Ingredient("Onion", "brown-onion.png"));
        lstIngredient.add(new Ingredient("Garlic", "garlic.jpg"));
        lstIngredient.add(new Ingredient("Milk", "milk.png"));
        lstIngredient.add(new Ingredient("Eggs", "egg.png"));
        lstIngredient.add(new Ingredient("Oil", "vegetable-oil.jpg"));
        lstIngredient.add(new Ingredient("Yogurt", "plain-yogurt.jpg"));
        lstIngredient.add(new Ingredient("Salt", "salt.jpg"));
        lstIngredient.add(new Ingredient("Sugar", "sugar-in-bowl.png"));
        lstIngredient.add(new Ingredient("Pepper", "pepper.jpg"));
        lstIngredient.add(new Ingredient("Water", "water.jpg"));
        lstIngredient.add(new Ingredient("Parsley", "parsley.jpg"));
        lstIngredient.add(new Ingredient("Basil", "basil.jpg"));
        lstIngredient.add(new Ingredient("Chocolate", "milk-chocolate.jpg"));
        lstIngredient.add(new Ingredient("Nuts", "hazelnuts.png"));
        lstIngredient.add(new Ingredient("Tomato", "tomato.png"));
        lstIngredient.add(new Ingredient("Cucumber", "cucumber.jpg"));
        lstIngredient.add(new Ingredient("Bell pepper", "red-bell-pepper.jpg"));
        lstIngredient.add(new Ingredient("Mushrooms", "portabello-mushrooms.jpg"));
        lstIngredient.add(new Ingredient("Lemon", "lemon.jpg"));
        lstIngredient.add(new Ingredient("Orange", "orange.jpg"));
        lstIngredient.add(new Ingredient("Banana", "bananas.jpg"));
        lstIngredient.add(new Ingredient("Wine", "red-wine.jpg"));
        lstIngredient.add(new Ingredient("Apple", "apple.jpg"));
        lstIngredient.add(new Ingredient("Berries", "berries-mixed.jpg"));
        lstIngredient.add(new Ingredient("Biscuits", "buttermilk-biscuits.jpg"));
        lstIngredient.add(new Ingredient("Pineapple", "pineapple.jpg"));
    }

    @Override
    public void onCreateOptionsMenu(@NonNull Menu menu, MenuInflater inflater) {
        inflater.inflate(R.menu.log_out, menu);
        super.onCreateOptionsMenu(menu, inflater);
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.main_log_out) {
            FirebaseAuth.getInstance().signOut();
            sendToLogin();
        }
        return super.onOptionsItemSelected(item);
    }


    private void sendToLogin() {
        Intent loginIntent = new Intent(getActivity(), LoginActivity.class);
        startActivity(loginIntent);
        Objects.requireNonNull(getActivity()).finish();// The user can't come back to this page
    }
}