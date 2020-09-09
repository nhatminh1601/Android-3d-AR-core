package com.example.location.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.admin.AdminActivity;
import com.example.location.adapters.ImageAdapter;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference imageRef = FirebaseDatabase.getInstance().getReference("images");
    Museum museum;
    ArrayList<Image> images = new ArrayList<>();
    RecyclerView recyclerView1, recyclerView2, recyclerView3;
    ImageAdapter imageAdapter1, imageAdapter2, imageAdapter3;
    RecyclerView.LayoutManager layoutManager, layoutManager1, layoutManager2;
    ActionBar actionBar;

    ImageView imgPicture;
    TextView textViewDesc;
    TextView title3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        museum = (Museum) getIntent().getSerializableExtra("museumData");
        setContentView(R.layout.activity_image_group);
        recyclerView1 = findViewById(R.id.recyclerview1);
        recyclerView2 = findViewById(R.id.recyclerview2);
        recyclerView3 = findViewById(R.id.recyclerview3);
        actionBar = getSupportActionBar();
        actionBar.setTitle(museum.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        getImageList();
        setView();
    }

    private void getImageList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image image = child.getValue(Image.class);
                    if(image != null){
                        images.add(image);
                    }
                }
                setAdapterPo();
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        imageRef.addValueEventListener(eventListener);
    }

    private void setView() {
        textViewDesc = findViewById(R.id.textViewDesc);
        title3 = findViewById(R.id.title3);
        imgPicture = findViewById(R.id.imageView);

        textViewDesc.setText(museum.getDescription());
        if (museum.getImage() != null && !museum.getImage().isEmpty()){
            Glide.with(this).load(museum.getImage()).placeholder(R.drawable.noimage).error(R.drawable.noimage).into(imgPicture);
        }
    }

    private void setAdapterPo() {
        ArrayList<Image> imageList = new ArrayList<>();
        for (int i = 0; i < images.size(); i++) {
            if (museum.getImages().contains(images.get(i).getId())) {
                imageList.add(images.get(i));
            }
        }

        layoutManager2 = new GridLayoutManager(this, 2);
        recyclerView3.setLayoutManager(layoutManager2);
        imageAdapter3 = new ImageAdapter(imageList, this, 1);
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