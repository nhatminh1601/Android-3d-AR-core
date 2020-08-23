package com.example.location;

import android.Manifest;
import android.app.ProgressDialog;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBar;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.core.content.ContextCompat;

import com.example.location.helpers.CustomAlertDialog;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;
import com.google.android.gms.tasks.OnFailureListener;
import com.google.android.gms.tasks.OnSuccessListener;
import com.google.android.gms.tasks.Task;
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
import java.util.ArrayList;
import java.util.UUID;

public class AddPlaceActivity extends AppCompatActivity implements OnItemClickListener {
    private static final int CAMERA_PERMISSION_CODE = 100;
    private static final int STORAGE_PERMISSION_CODE = 101;
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");
    StorageReference storageReference = FirebaseStorage.getInstance().getReference("images");
    ArrayList<Place> dataPlaces = new ArrayList<>();
    CustomAlertDialog alertDialog;

    TextView extName;
    TextView extDesc;
    TextView extUrl;
    Button btnAdd;
    ActionBar actionBar;

    ImageButton btnCapture;
    ImageButton btnChoose;
    ImageView imgPicture;
    Bitmap selectedBitmap;
    Uri filePath;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.READ_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.WRITE_EXTERNAL_STORAGE) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.WRITE_EXTERNAL_STORAGE}, 1);
        }

        if (ContextCompat.checkSelfPermission(this, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.CAMERA}, 1);
        }
        actionBar = getSupportActionBar();
        actionBar.setTitle("Thêm địa điểm mới");
        actionBar.setDisplayHomeAsUpEnabled(true);
        setContentView(R.layout.add_place_main);
        alertDialog = new CustomAlertDialog(this);
        getPlaces();
        extName = findViewById(R.id.extName);
        extDesc = findViewById(R.id.extDesc);
        extUrl = findViewById(R.id.extUrl);
        btnAdd = findViewById(R.id.btnAdd);
        btnAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = extName.getText().toString();
                String desc = extDesc.getText().toString();
                String url = extUrl.getText().toString();
                updatePlaces(name, desc, url);
            }
        });
        btnCapture = findViewById(R.id.btnCapture);
        btnChoose= findViewById(R.id.btnChoose);
        imgPicture=findViewById(R.id.imgPicture);
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

    private void choosePicture() {
        Intent pickPhoto = new Intent(Intent.ACTION_PICK,
                android.provider.MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        startActivityForResult(pickPhoto , 200);//one can be replaced with any action code
    }

    private void capturePicture() {
        Intent cInt = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        startActivityForResult(cInt,100);
    }

    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == 100 && resultCode == RESULT_OK) {
            filePath = data.getData();
            selectedBitmap = (Bitmap) data.getExtras().get("data");
            imgPicture.setImageBitmap(selectedBitmap);
        } else if (requestCode == 200 && resultCode == RESULT_OK) {
            try {

                Uri imageUri = data.getData();
                filePath = imageUri;
                selectedBitmap = MediaStore.Images.Media.getBitmap(this.getContentResolver(), imageUri);
                imgPicture.setImageBitmap(selectedBitmap);
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        uploadImage();
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
                            Toast.makeText(AddPlaceActivity.this, "Uploaded", Toast.LENGTH_SHORT).show();
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
                            Toast.makeText(AddPlaceActivity.this, "Failed "+e.getMessage(), Toast.LENGTH_SHORT).show();
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

    private void updatePlaces(String name, String desc, String url) {
        if (!name.isEmpty()) {
            boolean isExist = false;
            for (int i = 0; i < dataPlaces.size(); i++) {
                if (name.equalsIgnoreCase(dataPlaces.get(i).getName())) {
                    isExist = true;
                    break;
                }
            }

            if (isExist) {
                alertDialog.show("Tên địa điểm đã tồn tại!");
                return;
            }
            int lastId = dataPlaces.get(dataPlaces.size() - 1).getId();
            dataPlaces.add(new Place(++lastId, name, url, desc));
            clearText();
            placesRef.setValue(dataPlaces);
            alertDialog.show("Thêm thành công!");
        }

    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataPlaces.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    int id = child.child("id").getValue(Integer.class);
                    String name = child.child("name").getValue(String.class);
                    String url = child.child("url").getValue(String.class);
                    String description = child.child("description").getValue(String.class);
                    ArrayList<com.example.location.models.Anchor> anchors = (ArrayList<com.example.location.models.Anchor>) child.child("anchors").getValue();
                    Place place = new Place(id, name, url, description, anchors);
                    dataPlaces.add(place);
                }
                Log.d("TAG", "Value is: " + dataSnapshot.getValue());
            }

            @Override
            public void onCancelled(DatabaseError error) {
                Log.w("TAG", "Failed to read value.", error.toException());
            }
        });
    }

    private void clearText() {
        extName.setText("");
        extDesc.setText("");
        extUrl.setText("");
        imgPicture.setImageResource(0);
    }

    @Override
    public void onItemClick(Object o) {
        Place data = (Place) o;
        Log.d("TAG", "onItemClick: " + data.toString());
    }

    @Override
    public boolean onSupportNavigateUp() {
        finish();
        return super.onSupportNavigateUp();
    }
}