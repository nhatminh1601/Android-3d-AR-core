package com.example.location.activities.user;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.ColorDrawable;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.MenuItem;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.example.location.R;
import com.example.location.activities.MainActivity;
import com.example.location.activities.admin.AdminActivity;
import com.example.location.adapters.ImageAdapter;
import com.example.location.adapters.ParentImageAdapter;
import com.example.location.dummy.ImageGroupDummy;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.Image;
import com.example.location.model.ImageGroup;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;
import com.example.location.model.ParentImage;
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
    List<ImageGroup> imageGroups = new ArrayList<>();
    ArrayList<Image> images = new ArrayList<>();
    ArrayList<ParentImage> parentImages = new ArrayList<>();
    RecyclerView recyclerView;
    ActionBar actionBar;
    ProgressDialog dialog;

    ImageView imgPicture;
    TextView textViewDesc;
    int width;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        museum = (Museum) getIntent().getSerializableExtra("museumData");
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION, WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        getSupportActionBar().setBackgroundDrawable(new ColorDrawable(Color.TRANSPARENT));
        setContentView(R.layout.activity_image_group);
        recyclerView = findViewById(R.id.recyclerview);
        actionBar = getSupportActionBar();
        actionBar.setTitle(museum.getName());
        actionBar.setDisplayHomeAsUpEnabled(true);
        setImageGroup();
        getImageList();
        setView();
        dialog = new ProgressDialog(this);
        dialog.setMessage("Loading...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
    }

    private void setImageGroup() {
        if (museum.getImages() == null) {
            return;
        }
        ImageGroupDummy imageGroupDummy = new ImageGroupDummy();
        List<ImageGroup> groupList = imageGroupDummy.list();
        for (int i = 0; i < groupList.size(); i++) {
            if (museum.getImages().contains(groupList.get(i).getId())) {
                imageGroups.add(groupList.get(i));
            }
        }

        for (int i = 0; i < imageGroups.size(); i++) {
            ParentImage parentImage = new ParentImage(imageGroups.get(i).getId(), imageGroups.get(i).getName(), new ArrayList<>());
            parentImages.add(parentImage);
        }
    }

    private void getImageList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Image image = child.getValue(Image.class);
                    if (image != null) {
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
        imgPicture = findViewById(R.id.imageView);
        textViewDesc.setText(museum.getDescription());
        if (museum.getImage() != null && !museum.getImage().isEmpty()) {
            DisplayMetrics dimension = new DisplayMetrics();
            getWindowManager().getDefaultDisplay().getMetrics(dimension);
            float density  = getResources().getDisplayMetrics().density;
            float width = dimension.widthPixels/density+3;
            Glide.with(this).load(museum.getImage()).override((int) width,250).placeholder(R.drawable.noimage).error(R.drawable.noimage).into(imgPicture);
        }
    }

    private void setAdapterPo() {
        for (int i = 0; i < images.size(); i++) {
            for (int j = 0; j < parentImages.size(); j++) {
                if (images.get(i).getGroup().equals(parentImages.get(j).getId())) {
                    parentImages.get(j).addImage(images.get(i));
                }
            }
        }

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        ParentImageAdapter parentImageAdapter = new ParentImageAdapter(parentImages, this);
        recyclerView.setLayoutManager(layoutManager);
        recyclerView.setAdapter(parentImageAdapter);
        dialog.dismiss();
    }

    @Override
    public void onItemClick(Object o) {
        Image data = (Image) o;
        Intent intent = new Intent(this.getApplicationContext(), ImageActivity.class);
        intent.putExtra("data", data);
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