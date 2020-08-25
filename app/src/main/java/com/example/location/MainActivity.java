package com.example.location;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.app.ProgressDialog;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.AnimationDrawable;
import android.graphics.drawable.ColorDrawable;
import android.os.Build;
import android.os.Bundle;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.view.WindowManager;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.TextView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.helpers.TempData;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Anchor;
import com.example.location.models.Place;
import com.example.location.models.Storage;
import com.example.location.models.Type;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.Iterator;

public class MainActivity extends AppCompatActivity implements OnItemClickListener {
    ImageView imgBg;
    RecyclerView recyclerView;
    EditText edSearch;
    MenuAdapter menuAdapter;
    RecyclerView.LayoutManager layoutManager;
    TempData data;
    ArrayList<Place> dataPlaces;
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");
    ImageView btnBack;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {

            getWindow().setFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS,
                    WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        }
        dataPlaces = new ArrayList<>();
        SetViewId();
        SetAnimationBG();

        getPlaces();
        handlerSearch();
        btnBack = findViewById(R.id.btnBack);
        goBack();


    }

    private void goBack() {
        btnBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(view.getContext(), HomeActivity.class);
                startActivity(intent);
                finish();

            }
        });
    }

    private void handlerSearch() {
        edSearch.setOnKeyListener(new View.OnKeyListener() {
            @Override
            public boolean onKey(View view, int i, KeyEvent keyEvent) {
                menuAdapter.getFilter().filter(edSearch.getText());
                return true;
            }
        });
    }

    private void SetViewId() {
        imgBg = findViewById(R.id.imgBg);
        recyclerView = findViewById(R.id.lvMenu);
        edSearch = findViewById(R.id.edSearch);
    }

    private void SetAdapter() {
        data = new TempData();


        layoutManager = new GridLayoutManager(this, 3);
        recyclerView.setLayoutManager(layoutManager);
        menuAdapter = new MenuAdapter(dataPlaces, this);
        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void SetAnimationBG() {
        AnimationDrawable animationDrawable = (AnimationDrawable) imgBg.getDrawable();
        animationDrawable.setEnterFadeDuration(2000);
        animationDrawable.setExitFadeDuration(3000);
        animationDrawable.start();
    }

    @Override
    public void onItemClick(Object o) {
        Place data = (Place) o;
        Intent intent = new Intent(MainActivity.this, CameraUserActivity.class);
        intent.putExtra("item", data);
        startActivity(intent);
    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int id = child.child("id").getValue(Integer.class);
                    String name = child.child("name").getValue(String.class);
                    String url = child.child("url").getValue(String.class);
                    String description = child.child("description").getValue(String.class);
                    ArrayList<com.example.location.models.Anchor> anchors = new ArrayList<>();
                    for (DataSnapshot child2 : child.child("anchors").getChildren()) {
                        String ID = child2.child("id").getValue(String.class);
                        Anchor anchor = new Anchor(ID, Type.LEFT, true);
                        anchors.add(anchor);

                    }
                    Place place = new Place(id, name, url, description, anchors);
                    dataPlaces.add(place);
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
}