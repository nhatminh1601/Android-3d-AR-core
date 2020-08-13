package com.example.location;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.location.models.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");

    EditText extUsername;
    EditText extPassword;
    Button btnAccess;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
        getAdminInfo();
        extUsername = findViewById(R.id.extUsername);
        extUsername.requestFocus();
        extPassword = findViewById(R.id.extPassword);
        btnAccess = findViewById(R.id.btnAccess);
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate(extUsername.getText().toString(), extPassword.getText().toString());
            }
        });
    }

    private void getAdminInfo() {
        usersRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    User user = child.getValue(User.class);
                    if(user != null){
                        users.add(user);
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

    private boolean authenticate(final String username, final String password) {
        if (!checkEmpty(username, password)) {
            return false;
        }

        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                startActivity(intent);
                return true;
            }
        }

        new AlertDialog.Builder(LoginActivity.this)
                .setTitle("Thông báo")
                .setMessage("Username hoặc password không chính xác!")
                .setPositiveButton(android.R.string.ok, null)
                .show();
        extUsername.requestFocus();
        return false;
    }

    private boolean checkEmpty(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            new AlertDialog.Builder(LoginActivity.this)
                    .setTitle("Thông báo")
                    .setMessage("Vui lòng nhập vào username & password!")
                    .setPositiveButton(android.R.string.ok, null)
                    .show();
            extUsername.requestFocus();
            return false;
        }
        return true;
    }
}
