package com.example.location.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.LinearLayout;

import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.adapters.ImageAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;

import java.util.ArrayList;

public class ImageGroupActivity extends AppCompatActivity implements OnItemClickListener {
    ArrayList<Image> images;
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    ImageAdapter imageAdapter1, imageAdapter2, imageAdapter3;
    RecyclerView.LayoutManager layoutManager, layoutManager1, layoutManager2;
    ActionBar actionBar;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_image_group);
        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView3 = findViewById(R.id.recyclerview3);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Bảo Tàng Việt Nam");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setAdapter();
        setAdapterNew();
        setAdapterPo();
    }

    private void setAdapterPo() {
        layoutManager2 = new GridLayoutManager(this, 2);
        recyclerView3.setLayoutManager(layoutManager2);
        imageAdapter3 = new ImageAdapter(images, this, 1);
        recyclerView3.setAdapter(imageAdapter3);
    }

    private void setAdapterNew() {
        layoutManager1 = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView2.setLayoutManager(layoutManager1);
        imageAdapter2 = new ImageAdapter(images, this, 0);
        recyclerView2.setAdapter(imageAdapter2);

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

        layoutManager = new LinearLayoutManager(this, LinearLayoutManager.HORIZONTAL, false);
        recyclerView1.setLayoutManager(layoutManager);
        imageAdapter1 = new ImageAdapter(images, this, 0);
        recyclerView1.setAdapter(imageAdapter1);


    }

    @Override
    public void onItemClick(Object o) {
        Image data= (Image) o;
        Intent intent= new Intent(this.getApplicationContext(),ImageActivity.class);
        intent.putExtra("data",data);
        startActivity(intent);

    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        MenuInflater inflater = getMenuInflater();
        inflater.inflate(R.menu.home, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        switch (item.getItemId()) {
            case R.id.btnHome:
                Intent intent = new Intent(this, MainActivity.class);
                startActivity(intent);
                finish();
                return true;
            default:
                return super.onOptionsItemSelected(item);
        }

    }
}