package com.example.location.activities.admin;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.MenuItem;
import android.view.MotionEvent;
import android.view.View;
import android.view.Window;
import android.widget.ArrayAdapter;
import android.widget.AutoCompleteTextView;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentManager;

import com.bumptech.glide.Glide;
import com.example.location.R;
import com.example.location.activities.HomeActivity;
import com.example.location.activities.admin.fragments.FragmentCreature;
import com.example.location.activities.admin.fragments.FragmentHome;
import com.example.location.activities.user.LoginActivity;
import com.example.location.dummy.DataGenerate;
import com.example.location.dummy.MuseumTypeDummy;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.model.Museum;
import com.example.location.model.MuseumType;
import com.example.location.model.User;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
import com.google.android.material.bottomnavigation.BottomNavigationView;
import com.google.android.material.floatingactionbutton.FloatingActionButton;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;
import com.google.firebase.storage.FirebaseStorage;
import com.google.firebase.storage.OnProgressListener;
import com.google.firebase.storage.StorageReference;
import com.google.firebase.storage.UploadTask;

import java.io.IOException;
import java.util.AbstractList;
import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

public class AdminActivity extends AppCompatActivity {
    DatabaseReference museumsRef = FirebaseDatabase.getInstance().getReference("museums");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");
    ValueEventListener valueEventListener;

    CustomAlertDialog alertDialog;
    BottomNavigationView bottomNavigationView;
    FragmentManager ft;
    FragmentHome fragmentHome;
    FragmentCreature fragmentCreature;
    Fragment active;

    User user;
    List<Museum> museums = new ArrayList<>();
    List<MuseumType> museumTypes = new ArrayList<>();
    Museum museum;

    ImageButton btnCapture;
    ImageButton btnChoose;
    ImageView imgPicture;
    Bitmap selectedBitmap;
    Uri filePath;

    EditText extName;
    EditText extDesc;
    AutoCompleteTextView extType;
    EditText extUrl;
    Button btnNew;
    FloatingActionButton floatingActionButton;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        getWindow().requestFeature(Window.FEATURE_ACTION_BAR);
        getSupportActionBar().hide();
        requestPermission();
        alertDialog = new CustomAlertDialog(this);
        user = (User) getIntent().getSerializableExtra("userData");
        setContentView(R.layout.activity_admin);
        bottomNavigationView = findViewById(R.id.bottomAdminNav);
        handleFragment();
        handleBottomNavigation();
        getMuseumTypeList();
        getMuseumList();
        //DataGenerate dataGenerate = new DataGenerate();
        //dataGenerate.generate();
    }

    @Override
    protected void onStop() {
        super.onStop();
        if ( museumsRef != null ) {
            museumsRef.removeEventListener(valueEventListener);
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        if ( museumsRef != null ) {
            getMuseumList();
        }
    }

    private void updateMuseum(String name, String type, String desc, String url) {
        if (!checkEmpty(name, type)) {
            return;
        }
        int index = -1;
        boolean isExist = false;
        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getId().equals(museum.getId())) {
                index = i;
                isExist = true;
                break;
            }
        }

        Integer typeId = 0;
        for (int i = 0; i < museumTypes.size(); i++) {
            if (museumTypes.get(i).getName().equalsIgnoreCase(type)) {
                typeId = museumTypes.get(i).getId();
                break;
            }
        }

        if (isExist) {
            museums.get(index).setName(name);
            museums.get(index).setDescription(desc);
            museums.get(index).setType(typeId);
            museums.get(index).setImage(url);
        }
        museumsRef.setValue(museums);
        alertDialog.show("Cập nhật thành công!");
    }

    private void requestPermission() {
        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
    }

    private void setMuseumData(Fragment fragment) {
        for (int i = 0; i < museums.size(); i++) {
            if (museums.get(i).getUser().equals(user.getId())) {
                museum = museums.get(i);
                break;
            }
        }
        if (museum != null) {
            if (fragment == fragmentHome) {
                String type = "";
                for (int i = 0; i < museumTypes.size(); i++) {
                    if (museumTypes.get(i).getId().equals(museum.getType())) {
                        type = museumTypes.get(i).getName();
                        break;
                    }
                }
                extName.setText(museum.getName());
                extDesc.setText(museum.getDescription());
                extType.setText(type, false);
                extUrl.setText(museum.getImage());
                if (museum.getImage() != null && !museum.getImage().isEmpty()){
                    Glide.with(AdminActivity.this).load(museum.getImage()).placeholder(R.drawable.noimage).error(R.drawable.noimage).into(imgPicture);
                }
            }
            if (fragment == fragmentCreature) {
                fragmentCreature.renewAdapter(museum);
            }
        }
    }

    private void handleFragment() {
        fragmentHome = FragmentHome.newInstance();
        fragmentCreature = FragmentCreature.newInstance();
        ft = getSupportFragmentManager();
        ft.beginTransaction().add(R.id.frameAdminLayout, fragmentHome,"1").commit();
        ft.beginTransaction().add(R.id.frameAdminLayout, fragmentCreature,"2").commit();
        ft.beginTransaction().hide(fragmentHome).show(fragmentCreature).commit();
        active = fragmentCreature;
    }

    private void handleBottomNavigation() {
        bottomNavigationView.setOnNavigationItemSelectedListener(new BottomNavigationView.OnNavigationItemSelectedListener() {
            @Override
            public boolean onNavigationItemSelected(@NonNull MenuItem item) {
                switch (item.getItemId()) {
                    case R.id.btnHome:
                        ft.beginTransaction().hide(active).show(fragmentHome).commit();
                        active = fragmentHome;
                        setView(active);
                        setMuseumData(active);
                        return true;
                    case R.id.btnCreature:
                        ft.beginTransaction().hide(active).show(fragmentCreature).commit();
                        active = fragmentCreature;
                        setView(active);
                        setMuseumData(active);
                        return true;
                    case R.id.btnExit:
                        Intent intent = new Intent(getApplicationContext(), HomeActivity.class);
                        startActivity(intent);
                        finish();
                        return true;
                }
                return true;
            }
        });
    }

    private void setView(Fragment fragment) {
        if (fragment.equals(fragmentHome)) {
            btnCapture = findViewById(R.id.btnCapture);
            btnChoose= findViewById(R.id.btnChoose);
            imgPicture=findViewById(R.id.imgPicture);
            extName = findViewById(R.id.editTextName);
            extType = findViewById(R.id.editTextType);
            setupAutoComplete(extType, museumTypes);
            extDesc = findViewById(R.id.editTextDesc);
            extUrl = findViewById(R.id.editTextUrl);
            btnNew = findViewById(R.id.saveButton);
            btnNew.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    updateMuseum(extName.getText().toString(), extType.getText().toString(), extDesc.getText().toString(), extUrl.getText().toString());
                }
            });
            btnCapture.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    capturePicture();
                }
            });
            btnChoose.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    choosePicture();
                }
            });
        }
        if (fragment.equals(fragmentCreature)) {
            floatingActionButton = fragmentCreature.getButton();
            floatingActionButton.setOnClickListener(new View.OnClickListener() {
                @Override
                public void onClick(View view) {
                    Intent intent = new Intent(getApplicationContext(), ImageGroupActivity.class);
                    intent.putExtra("userData", user);
                    intent.putExtra("museumData", museum);
                    startActivity(intent);
                    finish();
                }
            });
        }

    }

    private void setupAutoComplete(AutoCompleteTextView view, List<MuseumType> museumTypes) {
        if (view == null) {
            return;
        }

        List<String> names = new AbstractList<String>() {
            @Override
            public int size() { return museumTypes.size(); }

            @Override
            public String get(int location) {
                return museumTypes.get(location).getName();
            }
        };

        view.setAdapter(new ArrayAdapter<String>(AdminActivity.this, android.R.layout.simple_list_item_1, names));
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

    private void getMuseumTypeList() {
        MuseumTypeDummy museumTypeDummy = new MuseumTypeDummy();
        museumTypes = museumTypeDummy.list();
    }

    private void getMuseumList() {
        ValueEventListener eventListener = new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                museums = new ArrayList<>();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Museum museum = child.getValue(Museum.class);
                    if(museum != null){
                        museums.add(museum);
                    }
                }
                setView(active);
                setMuseumData(active);
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        };
        valueEventListener = eventListener;
        museumsRef.addValueEventListener(eventListener);
    }

    private void choosePicture() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 200);//one can be replaced with any action code
    }

    private void capturePicture() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,100);
    }

    private Uri getImageUri(Context inContext, Bitmap inImage) {
        Bitmap OutImage = Bitmap.createScaledBitmap(inImage, 1000, 1000,true);
        String path = MediaStore.Images.Media.insertImage(inContext.getContentResolver(), OutImage, "Title", null);
        return Uri.parse(path);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(selectedBitmap);
            filePath = getImageUri(getApplicationContext(), selectedBitmap);
            uploadImage();
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {

                Uri imageUri = data.getData();
                filePath = imageUri;
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgPicture.setImageBitmap(selectedBitmap);
                uploadImage();
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    private void uploadImage() {

        if(filePath != null)
        {
            final ProgressDialog progressDialog = new ProgressDialog(this);
            progressDialog.setTitle("Uploading...");
            progressDialog.show();

            StorageReference storageRef = storageReference.child(UUID.randomUUID().toString());
            storageRef.putFile(filePath)
                    .addOnSuccessListener(new OnSuccessListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onSuccess(UploadTask.TaskSnapshot taskSnapshot) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
                            Task<Uri> result = taskSnapshot.getMetadata().getReference().getDownloadUrl();
                            result.addOnSuccessListener(new OnSuccessListener<Uri>() {
                                @Override
                                public void onSuccess(Uri uri) {
                                    String generatedFilePath = uri.toString();
                                    extUrl.setText(generatedFilePath);
                                    Log.d("TAG", "Stored path is: "+generatedFilePath);
                                }
                            });

                        }
                    })
                    .addOnFailureListener(new OnFailureListener() {
                        @Override
                        public void onFailure(@NonNull Exception e) {
                            progressDialog.dismiss();
                            Toast.makeText(AdminActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
                        }
                    })
                    .addOnProgressListener(new OnProgressListener<UploadTask.TaskSnapshot>() {
                        @Override
                        public void onProgress(UploadTask.TaskSnapshot taskSnapshot) {
                            double progress = (100.0*taskSnapshot.getBytesTransferred()/taskSnapshot
                                    .getTotalByteCount());
                            progressDialog.setMessage("Uploaded "+(int)progress+"%");
                        }
                    });
        }
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
