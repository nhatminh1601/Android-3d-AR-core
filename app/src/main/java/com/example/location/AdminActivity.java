package com.example.location;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class AdminActivity extends AppCompatActivity {
    Button btnAddPlace;
    Button btnAddAnchor;
    Button btnExit;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.admin_main);
        btnAddPlace = findViewById(R.id.btnAddPlace);
        btnAddAnchor = findViewById(R.id.btnAddAnchor);
        btnExit = findViewById(R.id.btnExit);
        btnExit.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                startActivity(intent);
            }
        });
    }
}
