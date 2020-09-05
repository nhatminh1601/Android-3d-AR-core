package com.example.location.activities.user;

import android.app.ProgressDialog;
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
import com.example.location.activities.admin.AdminActivity;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;
import java.util.List;

public class LoginActivity extends AppCompatActivity {
    DatabaseReference usersRef = FirebaseDatabase.getInstance().getReference("users");
    CustomAlertDialog alertDialog;

    EditText extUsername;
    EditText extPassword;
    Button btnAccess;
    List<User> users = new ArrayList<>();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        setContentView(R.layout.activity_login);
        alertDialog = new CustomAlertDialog(this);
        getAdminInfo();
        extUsername = findViewById(R.id.editTextUsername);
        extUsername.requestFocus();
        extPassword = findViewById(R.id.editTextPassword);
        btnAccess = findViewById(R.id.cirLoginButton);
        btnAccess.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                authenticate(extUsername.getText().toString(), extPassword.getText().toString());
            }
        });
    }

    public void register(View view) {
        Intent intent = new Intent(getApplicationContext(), RegisterActivity.class);
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

    private boolean authenticate(final String username, final String password) {
        if (!checkEmpty(username, password)) {
            return false;
        }
        ProgressDialog dialog = new ProgressDialog(LoginActivity.this);
        dialog.setMessage("Authenticate...");
        dialog.setCancelable(false);
        dialog.setInverseBackgroundForced(false);
        dialog.show();
        for (int i = 0; i < users.size(); i++) {
            if (users.get(i).getUsername().equals(username) && users.get(i).getPassword().equals(password)) {
                Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
                intent.putExtra("userData", users.get(i));
                startActivity(intent);
                dialog.dismiss();
                return true;
            }
        }
        dialog.dismiss();
        alertDialog.show("Username hoặc password không chính xác!");
        extUsername.requestFocus();
        return false;
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
