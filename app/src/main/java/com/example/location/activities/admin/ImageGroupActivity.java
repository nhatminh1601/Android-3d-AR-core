package com.example.location.activities.admin;

import android.os.Bundle;
import android.util.Log;

import androidx.annotation.Nullable;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;

import com.example.location.R;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.model.Museum;
import com.example.location.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupActivity extends AppCompatActivity {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    CustomAlertDialog alertDialog;
    ActionBar actionBar;

    User user;
    Museum museum;
    List<Museum> museums = new ArrayList<>();

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
        getMuseumList();
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
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}
