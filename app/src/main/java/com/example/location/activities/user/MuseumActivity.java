package com.example.location.activities.user;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;

import com.example.location.R;
import com.example.location.adapters.MuseumAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;

import java.util.ArrayList;

public class MuseumActivity extends AppCompatActivity implements OnItemClickListener {
    MuseumType museumType;
    ArrayList<Museum> museums;
    RecyclerView recyclerView;
    MuseumAdapter museumAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_museum);
        museumType = (MuseumType) getIntent().getSerializableExtra("data");
        recyclerView = findViewById(R.id.recyclerview);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Địa điểm Bảo tàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setAdapter();
    }

    private void setAdapter() {
        museums = new ArrayList<>();
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Washington", R.drawable.baotang, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Metropolitan", R.drawable.bg, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));
        museums.add(new Museum(1, "Việt Nam", R.drawable.vn, "Viện bảo tàng mỹ thuật Việt Nam"));

        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        museumAdapter = new MuseumAdapter(museums, this);
        recyclerView.setAdapter(museumAdapter);


    }

    @Override
    public void onItemClick(Object o) {
        Museum data = (Museum) o;
        Intent intent = new Intent(this, ImageGroupActivity.class);
        intent.putExtra("data", data);
        startActivity(intent);
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}