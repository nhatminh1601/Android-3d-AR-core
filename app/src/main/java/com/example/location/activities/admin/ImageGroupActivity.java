package com.example.location.activities.admin;

import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.example.location.R;
import com.example.location.dummy.MuseumTypeDummy;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;
import com.example.location.model.User;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;

public class ImageGroupActivity extends AppCompatActivity {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    CustomAlertDialog alertDialog;

    User user;
    List<Museum> museums = new ArrayList<>();
    List<MuseumType> museumTypes = new ArrayList<>();

    EditText extName;
    EditText extDesc;
    AutoCompleteTextView extType;
    Button btnNew;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_welcome);
        alertDialog = new CustomAlertDialog(this);
        user = (User) getIntent().getSerializableExtra("userData");
        getMuseumList();
        getMuseumTypeList();

        extName = findViewById(R.id.editTextName);
        extName.requestFocus();
        extType = findViewById(R.id.editTextType);
        setupAutoComplete(extType, museumTypes);
        extDesc = findViewById(R.id.editTextDesc);
        btnNew = findViewById(R.id.saveButton);
        btnNew.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                createMuseum(extName.getText().toString(), extType.getText().toString(), extDesc.getText().toString());
            }
        });
    }

    private boolean createMuseum(String name, String type, String description) {
        if (!checkEmpty(name, type)) {
            return false;
        }

        boolean checkExists = false;
        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getName().equalsIgnoreCase(name)) {
                checkExists = true;
                break;
            }
        }

        if (checkExists) {
            alertDialog.show("Tên bảo tàng đã được đăng ký!");
            return false;
        }

        Integer typeId = 0;
        for (int i = 0; i < museumTypes.size(); i++) {
            if (museumTypes.get(i).getName().equalsIgnoreCase(type)) {
                typeId = museumTypes.get(i).getId();
                break;
            }
        }

        int lastId = museums.get(museums.size() - 1).getId();
        Museum museum = new Museum(++lastId, name, typeId, user.getId(), description);
        museums.add(museum);
        museumsRef.setValue(museums);
        Intent intent = new Intent(getApplicationContext(), AdminActivity.class);
        intent.putExtra("userData", user);
        startActivity(intent);
        return true;
    }

    private void setupAutoComplete(AutoCompleteTextView view, List<MuseumType> museumTypes) {
        List<String> names = new AbstractList<String>() {
            @Override
            public int size() { return museumTypes.size(); }

            @Override
            public String get(int location) {
                return museumTypes.get(location).getName();
            }
        };

        view.setAdapter(new ArrayAdapter<String>(this, android.R.layout.simple_list_item_1, names));
        view.setKeyListener(null);
        view.setOnTouchListener(new View.OnTouchListener(){
            @Override
            public boolean onTouch(View v, MotionEvent event){
                view.showDropDown();
                return false;
            }
        });
        view.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                view.showDropDown();
                return;
            }
        });
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

    private void getMuseumTypeList() {
        MuseumTypeDummy museumTypeDummy = new MuseumTypeDummy();
        museumTypes = museumTypeDummy.list();
    }

    private boolean checkEmpty(String name, String type) {
        if (name.isEmpty() || type.isEmpty()) {
            alertDialog.show("Vui lòng nhập vào tên & danh mục!");
            extName.requestFocus();
            return false;
        }
        return true;
    }
}
