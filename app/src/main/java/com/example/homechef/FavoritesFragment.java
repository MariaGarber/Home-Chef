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
import android.widget.ProgressBar;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.fragment.app.Fragment;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;

public class FavoritesFragment extends Fragment {
    private List<Recipe> lstFavorites;
    private RecyclerView myrv;
    private DatabaseReference mRootRef;
    private FirebaseAuth mAuth;
    private ProgressBar progressBar;
    private TextView emptyView;

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setHasOptionsMenu(true);
    }

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        final View RootView = inflater.inflate(R.layout.fragment_favorites, container, false);
        Toolbar mToolbarContact = RootView.findViewById(R.id.toolbar_favorites);
        progressBar = RootView.findViewById(R.id.progressbar);
        emptyView= RootView.findViewById(R.id.empty_view);
        ((AppCompatActivity) Objects.requireNonNull(getActivity())).setSupportActionBar(mToolbarContact);
        getFavorites(RootView);
        return RootView;
    }

    private void getFavorites(final View rootView) {
        mAuth = FirebaseAuth.getInstance();
        String uid = Objects.requireNonNull(mAuth.getCurrentUser()).getUid();
        mRootRef = FirebaseDatabase.getInstance().getReference().child(uid);
        mRootRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(@NonNull DataSnapshot dataSnapshot) {
                lstFavorites = new ArrayList<>();
                HashMap favorites = (HashMap) dataSnapshot.getValue();
                if (favorites != null) {
                    for (Object recipe : favorites.keySet()) {
                        String title = (String) dataSnapshot.child(recipe.toString()).child("title").getValue();
                        String img = (String) dataSnapshot.child(recipe.toString()).child("img").getValue();
                        lstFavorites.add(new Recipe(recipe.toString(), title, img, 0, 0));
                    }
                }
                progressBar.setVisibility(View.GONE);
                myrv = rootView.findViewById(R.id.recycleview_favorites);
                if(lstFavorites.isEmpty()){
                    myrv.setVisibility(View.GONE);
                    emptyView.setVisibility(View.VISIBLE);
                }
                else{
                    myrv.setLayoutManager(new GridLayoutManager(getActivity(), 1));
                    RecyclerViewAdapterFavorites myAdapter = new RecyclerViewAdapterFavorites(getContext(), lstFavorites);
                    myrv.setAdapter(myAdapter);
                }
            }

            @Override
            public void onCancelled(@NonNull DatabaseError databaseError) {

            }
        });
    }

    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
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
