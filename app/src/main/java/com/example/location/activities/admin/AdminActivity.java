package com.example.location.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MenuItem;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.example.location.R;
import com.example.location.activities.admin.fragments.FragmentCreature;
import com.example.location.activities.admin.fragments.FragmentHome;
import com.example.location.model.Museum;
import com.example.location.model.User;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class AdminActivity extends AppCompatActivity {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");

    BottomNavigationView bottomNavigationView;
    FragmentManager ft;
    FragmentHome fragmentHome;
    FragmentCreature fragmentCreature;
    Fragment active;

    User user;
    List<Museum> museums = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        user = (User) getIntent().getSerializableExtra("userData");
        getMuseumList();
        setContentView(R.layout.activity_admin);
        bottomNavigationView = findViewById(R.id.bottomAdminNav);
        handleFragment();
        handleBottomNavigation();
    }

    private void handleFragment() {
        fragmentHome = FragmentHome.newInstance();
        fragmentCreature = FragmentCreature.newInstance();
        ft = getSupportFragmentManager();
        ft.beginTransaction().add(R.id.frameAdminLayout, fragmentCreature,"2").hide(fragmentCreature).commit();
        ft.beginTransaction().add(R.id.frameAdminLayout, fragmentHome,"1").commit();
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
                    case R.id.btnCreature:
                        ft.beginTransaction().hide(active).show(fragmentCreature).commit();
                        active = fragmentCreature;
                        return true;
                }
                return true;
            }
        });
    }

    private void setLayout() {
        boolean check = false;
        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getUser().equals(user.getId())) {
                check = true;
                break;
            }
        }

        if (!check) {
            Intent intent = new Intent(getApplicationContext(), WelcomeActivity.class);
            intent.putExtra("userData", user);
            startActivity(intent);
            return;
        }
    }

    private void getMuseumList() {
        museumsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Museum museum = child.getValue(Museum.class);
                    if(museum != null){
                        museums.add(museum);
                    }
                }
                setLayout();
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}
