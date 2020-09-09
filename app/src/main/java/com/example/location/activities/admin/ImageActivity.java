package com.example.location.activities.admin;

import android.os.Bundle;

import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.adapters.ImageAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.example.location.model.ImageGroup;

import java.util.ArrayList;

public class ImageActivity extends AppCompatActivity implements OnItemClickListener {
    ArrayList<Image> images;
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
        setAdapter();
    }

    private void setAdapter() {
        images = new ArrayList<>();
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        images.add(new Image(1, "Hình 1", "test", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));

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