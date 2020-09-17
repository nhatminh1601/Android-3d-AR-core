package com.example.location.activities.admin;

import android.os.Bundle;
import android.util.Log;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.adapters.ImageAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.example.location.model.ImageGroup;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference("images");
    ArrayList<Image> images = new ArrayList<>();
    RecyclerView recyclerView;
    ImageAdapter imageAdapter;
    RecyclerView.LayoutManager layoutManager;
    ActionBar actionBar;

    ImageGroup imageGroup;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        imageGroup = (ImageGroup) getIntent().getSerializableExtra("imageGroupData");
        setContentView(R.layout.activity_admin_image);
        recyclerView = findViewById(R.id.recyclerView);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Tác phẩm " + imageGroup.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        getImageList();
    }

    private void getImageList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image image = child.getValue(Image.class);
                    if(image != null && image.getGroup().equals(imageGroup.getId())){
                        images.add(image);
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
        imageRef.addValueEventListener(eventListener);
    }

    private void setAdapter() {
        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        imageAdapter = new ImageAdapter(images, this, 1);
        recyclerView.setAdapter(imageAdapter);

    }

    @Override
    public void onItemClick(Object o) {
        Image data = (Image) o;
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}