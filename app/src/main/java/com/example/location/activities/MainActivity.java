package com.example.location.activities;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.SearchView;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;
import androidx.fragment.app.FragmentTransaction;

import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;

import com.example.location.R;
import com.example.location.activities.user.fragments.FragmentDiscover;
import com.example.location.activities.user.fragments.FragmentFavourite;
import com.example.location.activities.user.fragments.FragmentHome;
import com.google.android.material.bottomnavigation.BottomNavigationView;

public class MainActivity extends AppCompatActivity {
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
        setContentView(R.layout.activity_main);
        bottomNavigationView = findViewById(R.id.bottomNav);
        toolbar = getSupportActionBar();
        toolbar.setTitle("Bảo tàng");

        handleFragment();
        handleBottomNavigation();

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
                        toolbar.setTitle("Bảo tàng");
                        ft.beginTransaction().hide(active).show(fragmentHome).commit();
                        active = fragmentHome;
                        return true;
                    case R.id.btnFavorite:
                        toolbar.setTitle("Yêu thích");
                        ft.beginTransaction().hide(active).show(fragmentFavourite).commit();
                        active = fragmentFavourite;
                        return true;
                    case R.id.btnNavigate:
                        toolbar.setTitle("khám phá");
                        ft.beginTransaction().hide(active).show(fragmentDiscover).commit();
                        active = fragmentDiscover;
                        return true;
                }
                return true;
            }
        });
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.toobar, menu);
        MenuItem menuItem = menu.findItem(R.id.btnSearch);
        SearchView searchView = (SearchView) menuItem.getActionView();
        searchView.setQueryHint("tìm kiếm mọi thứ");
        searchView.setOnQueryTextListener(new SearchView.OnQueryTextListener() {
            @Override
            public boolean onQueryTextSubmit(String query) {
                fragmentHome.search(query);
                return false;
            }

            @Override
            public boolean onQueryTextChange(String newText) {
                Log.d("TAG", "onQueryTextChange: " + newText);
                return false;
            }
        });
        return super.onCreateOptionsMenu(menu);
    }
}