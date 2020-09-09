package com.example.location.activities.user;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.location.R;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.model.Role;
import com.example.location.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class RegisterActivity extends AppCompatActivity {
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    CustomAlertDialog alertDialog;

    EditText extUsername;
    EditText extPassword;
    EditText extEmail;
    Button btnAccess;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_register);
        alertDialog = new CustomAlertDialog(this);
        getAdminInfo();
        extUsername = findViewById(R.id.editTextUsername);
        extUsername.requestFocus();
        extPassword = findViewById(R.id.editTextPassword);
        extEmail = findViewById(R.id.editTextEmail);
        btnAccess = findViewById(R.id.cirLoginButton);
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                register(extUsername.getText().toString(), extPassword.getText().toString(), extEmail.getText().toString());
            }
        });
    }

    public void login(View view) {
        Intent intent = new Intent(getApplicationContext(), LoginActivity.class);
        startActivity(intent);
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

    private boolean register(final String username, final String password, final String email) {
        if (!checkEmpty(username, password)) {
            return false;
        }

        boolean checkExists = false;
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equalsIgnoreCase(username) || users.get(i).getEmail().equalsIgnoreCase(email)) {
                checkExists = true;
                break;
            }
        }

        if (checkExists) {
            alertDialog.show("Username hoặc email đã được đăng ký!");
            return false;
        }

        int lastId = users.get(users.size() - 1).getId();
        User user = new User(++lastId, username, password, email, Role.Admin);
        users.add(user);

        alertDialog.show("Đăng ký thành công!");
        usersRef.setValue(users);
        clearText();
        return true;
    }

    private void clearText() {
        extEmail.setText("");
        extPassword.setText("");
        extUsername.setText("");
    }

    private boolean checkEmpty(String username, String password) {
        if (username.isEmpty() || password.isEmpty()) {
            alertDialog.show("Vui lòng nhập vào username & password!");
            extUsername.requestFocus();
            return false;
        }
        return true;
    }
}
