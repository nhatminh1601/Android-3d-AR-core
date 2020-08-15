package com.example.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.WindowManager;
import android.widget.ImageView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.helpers.TempData;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    ImageView imgBg;
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    RecyclerView.LayoutManager layoutManager;
    TempData data;
    ArrayList<Place> dataPlaces;
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        dataPlaces = new ArrayList<>();
        SetViewId();
        SetAnimationBG();
        getPlaces();


    }

    private void SetViewId() {
        imgBg = findViewById(R.id.imgBg);
        recyclerView = findViewById(R.id.lvMenu);
    }

    private void SetAdapter() {
        data = new TempData();


        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(dataPlaces, this);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAnimationBG() {
        AnimationDrawable animationDrawable = (AnimationDrawable) imgBg.getDrawable();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

    @Override
    public void onItemClick(Object o) {
        Place data = (Place) o;
        Log.d("TAG", "onItemClick: " + data.toString());
        Intent intent = new Intent(MainActivity.this, CameraUserActivity.class);
        intent.putExtra("item", data);
        startActivity(intent);
    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Place place = child.getValue(Place.class);
                    if (place != null) {
                        dataPlaces.add(place);
                    }
                }
                SetAdapter();
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }
}