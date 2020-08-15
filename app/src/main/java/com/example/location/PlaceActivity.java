package com.example.location;

import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.location.adapters.MenuAdapter;
import com.example.location.helpers.CustomAlertDialog;
import com.example.location.interfaces.OnItemClickListener;
import com.example.location.models.Place;
import com.google.firebase.database.DataSnapshot;
import com.google.firebase.database.DatabaseError;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;
import com.google.firebase.database.ValueEventListener;

import java.util.ArrayList;

public class PlaceActivity extends AppCompatActivity implements OnItemClickListener {
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");
    CustomAlertDialog alertDialog;

    Place data;
    RecyclerView recyclerView;
    MenuAdapter menuAdapter;
    RecyclerView.LayoutManager layoutManager;
    ArrayList<Place> dataPlaces = new ArrayList<>();
    TextView extName;
    TextView extDesc;
    TextView extUrl;
    Button btnSave;
    Button btnDelete;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.place_main);
        alertDialog = new CustomAlertDialog(this);
        data = (Place) getIntent().getSerializableExtra("placeData");
        getPlaces();
        recyclerView = findViewById(R.id.lvMenu);
        extName = findViewById(R.id.extName);
        extDesc = findViewById(R.id.extDesc);
        extUrl = findViewById(R.id.extUrl);
        appendData(data);
        btnSave = findViewById(R.id.btnSave);
        btnDelete = findViewById(R.id.btnDelete);
        btnSave.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                String name = extName.getText().toString();
                String desc = extDesc.getText().toString();
                String url = extUrl.getText().toString();
                updatePlaces(data.getId(), name, desc, url);
            }
        });
        btnDelete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                deletePlace(data.getId());
            }
        });
    }

    private void deletePlace(int id) {
        new android.app.AlertDialog.Builder(this)
                .setTitle("Thông báo")
                .setMessage("Bạn có chắc chắn muốn xóa không")
                .setPositiveButton(android.R.string.yes, new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        for (int i=0; i < dataPlaces.size(); i++){
                            if (id == dataPlaces.get(i).getId()){
                                dataPlaces.remove(i);
                                break;
                            }
                        }
                        placesRef.setValue(dataPlaces);
                        alertDialog.show("Xóa thành công!");
                        clearText();
                        Intent intent = new Intent(PlaceActivity.this, PlacesActivity.class);
                        startActivity(intent);
                    }

                })
                .setNegativeButton(android.R.string.no, null)
                .show();
    }

    private void updatePlaces(int id, String name, String desc, String url){
        if (!name.isEmpty()){
            int index = -1;
            boolean isExist = false;
            for (int i=0; i < dataPlaces.size(); i++){
                if (id == dataPlaces.get(i).getId()){
                    index = i;
                    isExist = true;
                    break;
                }
            }

            if (isExist){
                dataPlaces.get(index).setName(name);
                dataPlaces.get(index).setDescription(desc);
                dataPlaces.get(index).setUrl(url);
            }
//            else {
//                int lastId = dataPlaces.get(dataPlaces.size() - 1).getId();
//                dataPlaces.add(new Place(++lastId, name, url, desc));
//                clearText();
//            }
            placesRef.setValue(dataPlaces);
            alertDialog.show("Lưu thành công!");
        }

    }

    private void getPlaces() {
        placesRef.addValueEventListener(new ValueEventListener() {
            @Override
            public void onDataChange(DataSnapshot dataSnapshot) {
                dataPlaces.clear();
                for (DataSnapshot child : dataSnapshot.getChildren()) {
                    Place place = child.getValue(Place.class);
                    if(place != null){
                        dataPlaces.add(place);
                    }
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

    private void SetAdapter() {
        layoutManager= new LinearLayoutManager(this);
        recyclerView.setLayoutManager(layoutManager);
        ArrayList<Place> places = new ArrayList<>();
        places.add(data);
        menuAdapter= new MenuAdapter(places, this);

        recyclerView.setAdapter(menuAdapter);
        recyclerView.setHasFixedSize(true);
    }

    private void clearText(){
        extName.setText("");
        extDesc.setText("");
        extUrl.setText("");
    }

    private void appendData(Place place){
        extName.setText(place.getName());
        extDesc.setText(place.getDescription());
        extUrl.setText(place.getUrl());
    }

    @Override
    public void onItemClick(Object o) {
        Place data = (Place) o;
        appendData(data);
        Log.d("TAG", "onItemClick: "+data.toString() );
    }
}