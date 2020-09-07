package com.example.location.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.R;
import com.example.location.adapters.AdminImageGroupAdapter;
import com.example.location.dummy.ImageGroupDummy;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.model.ImageGroup;
import com.example.location.model.Museum;
import com.example.location.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    CustomAlertDialog alertDialog;
    ActionBar actionBar;
    RecyclerView recyclerView;
    AdminImageGroupAdapter adminImageGroupAdapter;
    RecyclerView.LayoutManager layoutManager;

    User user;
    Museum museum;
    List<Museum> museums = new ArrayList<>();
    ArrayList<ImageGroup> imageGroups = new ArrayList<>();
    List<ImageGroup> selectedImageGroups = new ArrayList<>();

    Button addButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm tác phẩm vào bảo tàng");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.activity_admin_image_group);
        alertDialog = new CustomAlertDialog(this);
        user = (User) getIntent().getSerializableExtra("userData");
        museum = (Museum) getIntent().getSerializableExtra("museumData");
        ImageGroupDummy imageGroupDummy = new ImageGroupDummy();
        imageGroups = (ArrayList<ImageGroup>) imageGroupDummy.list();
        setSelectedGroup();
        getMuseumList();
        recyclerView = findViewById(R.id.recyclerView);
        setAdapter();
        addButton = findViewById(R.id.addButton);
        addButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                updateMuseum();
            }
        });
    }

    private void setSelectedGroup() {
        if (museum.getImages() == null){
            return;
        }
        for (int i = 0; i < imageGroups.size(); i++) {
            if (museum.getImages().contains(imageGroups.get(i).getId())) {
                imageGroups.get(i).setSelected(true);
                selectedImageGroups.add(imageGroups.get(i));
            }
        }
    }

    private void updateMuseum() {
        int index = -1;
        boolean isExist = false;
        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getId().equals(museum.getId())) {
                index = i;
                isExist = true;
                break;
            }
        }

        List<Integer> list = new ArrayList<>();
        for (int i = 0; i < selectedImageGroups.size(); i++) {
            list.add(selectedImageGroups.get(i).getId());
        }

        if (isExist) {
            museums.get(index).setImages(list);
        }
        museumsRef.setValue(museums);
        alertDialog.show("Cập nhật thành công!");
    }

    private void setAdapter() {
        layoutManager = new GridLayoutManager(this, 2);
        recyclerView.setLayoutManager(layoutManager);
        adminImageGroupAdapter = new AdminImageGroupAdapter(imageGroups, this);
        recyclerView.setAdapter(adminImageGroupAdapter);
    }

    private void getMuseumList() {
        museumsRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Museum museum = child.getValue(Museum.class);
                    if(museum != null){
                        museums.add(museum);
                    }
                }
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    @Override
    public void onItemClick(Object o) {
        ImageGroup data = (ImageGroup) o;
        if (data.isSelected()) {
            selectedImageGroups.add(data);
        }
        else {
            for (int i = 0; i < selectedImageGroups.size(); i++) {
                if (selectedImageGroups.get(i).getId().equals(data.getId())) {
                    selectedImageGroups.remove(i);
                    break;
                }
            }
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.check, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here.
        int id = item.getItemId();

        if (id == R.id.btnAdd) {
            updateMuseum();
            return true;
        }

        return super.onOptionsItemSelected(item);
    }

    @Override
    public void onBackPressed() {
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        intent.putExtra("userData", user);
        intent.putExtra("museumData", museum);
        startActivity(intent);
        finish();
        return;
    }

    @Override
    public boolean onSupportNavigateUp() {
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        intent.putExtra("userData", user);
        intent.putExtra("museumData", museum);
        startActivity(intent);
        finish();
        return super.onSupportNavigateUp();
    }
}
