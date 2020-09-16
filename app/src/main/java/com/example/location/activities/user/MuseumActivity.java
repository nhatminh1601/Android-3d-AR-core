package com.example.location.activities.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.location.R;
import com.example.location.adapters.MuseumAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class MuseumActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    MuseumType museumType;
    List<Museum> museums = new ArrayList<>();
    RecyclerView recyclerView;
    MuseumAdapter museumAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;
    ProgressDialog dialog;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        museumType = (MuseumType) getIntent().getSerializableExtra("museumTypeData");
        recyclerView = findViewById(R.id.recyclerview);
        actionBar = getSupportActionBar();
        actionBar.setTitle(museumType.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        getMuseumList();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    private void getMuseumList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Museum museum = child.getValue(Museum.class);
                    if(museum != null){
                        museums.add(museum);
                    }
                }
                setAdapter();
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        museumsRef.addValueEventListener(eventListener);
    }

    private void setAdapter() {
        ArrayList<Museum> museumList = new ArrayList<>();

        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getType().equals(museumType.getId())) {
                museumList.add(museums.get(i));
            }
        }

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        museumAdapter = new MuseumAdapter(museumList, this);
        recyclerView.setAdapter(museumAdapter);

        dialog.hide();
    }

    @Override
    public void onItemClick(Object o) {
        Museum data = (Museum) o;
        Intent intent = new Intent(this, ImageGroupActivity.class);
        intent.putExtra("museumData", data);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}