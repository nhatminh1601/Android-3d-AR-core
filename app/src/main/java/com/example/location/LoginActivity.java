package com.example.location;

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
        extPassword = findViewById(R.id.extPassword);
        btnAccess = findViewById(R.id.btnAccess);
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (authenticate(extUsername.getText().toString(), extPassword.getText().toString())) {
                    Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                    startActivity(intent);
                }
            }
        });
    }

    private boolean authenticate(String username, String password) {
        return ADMIN_NAME.equals(username) && ADMIN_PASS.equals(password);
    }
}
