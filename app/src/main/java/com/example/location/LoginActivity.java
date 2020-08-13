package com.example.location;

import android.app.AlertDialog;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

public class LoginActivity extends AppCompatActivity {
    private final String ADMIN_NAME = "admin";
    private final String ADMIN_PASS = "admin";

    EditText extUsername;
    EditText extPassword;
    Button btnAccess;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_main);
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

    private boolean authenticate(String username, String password) {
        if (!checkEmpty(username, password)) {
            return false;
        }

        if (ADMIN_NAME.equals(username) && ADMIN_PASS.equals(password)) {
            Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
            startActivity(intent);
            return true;
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
