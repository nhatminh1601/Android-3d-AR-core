package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlacesActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");

    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Place> dataPlaces = new ArrayList<>();
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Danh sách phòng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.places_main);
        getPlaces();
        recyclerView = findViewById(R.id.lvMenu);

    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataPlaces.clear();
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

    private void SetAdapter() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(dataPlaces, this, 1);

        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(Object o) {
        Place data = (Place) o;
        Log.d("TAG", "onItemClick: " + data.toString());
        Intent intent = new Intent(this, PlaceActivity.class);
        intent.putExtra("placeData", data);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}