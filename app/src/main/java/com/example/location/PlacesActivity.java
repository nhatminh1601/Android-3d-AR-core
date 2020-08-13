package com.example.location;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Menu;
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
    ArrayList<Menu> dataMenu = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.places_main);
        getPlaces();
        recyclerView = findViewById(R.id.lvMenu);
    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Menu menu = child.getValue(Menu.class);
                    if(menu != null){
                        dataMenu.add(menu);
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
        layoutManager= new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter= new MenuAdapter(dataMenu, this);

        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    @Override
    public void onItemClick(Object o) {
        Menu data= (Menu) o;
        Log.d("TAG", "onItemClick: "+data.toString() );
    }
}