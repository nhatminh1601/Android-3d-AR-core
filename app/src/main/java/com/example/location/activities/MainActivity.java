package com.example.location.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.annotation.SuppressLint;
import android.content.SharedPreferences;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.WindowManager;

import com.example.location.R;
import com.example.location.activities.user.fragments.FragmentDiscover;
import com.example.location.activities.user.fragments.FragmentFavourite;
import com.example.location.activities.user.fragments.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Set;

public class MainActivity extends AppCompatActivity {
    SharedPreferences sharedPreferences;
    BottomNavigationView bottomNavigationView;
    FragmentManager ft;
    FragmentDiscover fragmentDiscover;
    FragmentFavourite fragmentFavourite;
    FragmentHome fragmentHome;
    Fragment active;
    private ActionBar toolbar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        sharedPreferences = getSharedPreferences("App_settings", MODE_PRIVATE);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        toolbar = getSupportActionBar();
        toolbar.setTitle("");
        handleFragment();
        handleBottomNavigation();

    }

    public ArrayList<String> getFavorites() {
        ArrayList<String> favorites = new ArrayList<>();
        Set<String> set = sharedPreferences.getStringSet("FAVORITES", new HashSet<>());
        favorites.addAll(set);
        return favorites;
    }

    private void handleFragment() {
        fragmentDiscover = FragmentDiscover.newInstance();
        fragmentFavourite = FragmentFavourite.newInstance();
        fragmentHome = FragmentHome.newInstance();
        ft = getSupportFragmentManager();
        ft.beginTransaction().add(R.id.frameLayout, fragmentFavourite, "3").hide(fragmentFavourite).commit();
        ft.beginTransaction().add(R.id.frameLayout, fragmentDiscover, "2").hide(fragmentDiscover).commit();
        ft.beginTransaction().add(R.id.frameLayout, fragmentHome, "1").commit();
        active = fragmentHome;
    }

    private void handleBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        ft.beginTransaction().hide(active).show(fragmentHome).commit();
                        active = fragmentHome;
                        return true;
                    case R.id.btnFavorite:
                        ft.beginTransaction().hide(active).show(fragmentFavourite).commit();
                        fragmentFavourite.updateAdapter(getFavorites());
                        active = fragmentFavourite;
                        return true;
                    case R.id.btnNavigate:
                        ft.beginTransaction().hide(active).show(fragmentDiscover).commit();
                        active = fragmentDiscover;
                        return true;
                }
                return true;
            }
        });
    }

    @SuppressLint("ResourceAsColor")
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setBackgroundColor(R.color.colorPrimary);
        searchView.setQueryHint("Tìm kiếm mọi thứ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG", "onQueryTextChange: "+newText);
                fragmentHome.search(newText);
                return false;
            }
        });

        return super.onCreateOptionsMenu(menu);
    }
}