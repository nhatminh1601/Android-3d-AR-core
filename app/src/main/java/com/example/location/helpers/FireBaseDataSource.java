package com.example.location.helpers;

import com.example.location.models.Place;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;

public class FireBaseDataSource {
    DatabaseReference placesRef = FirebaseDatabase.getInstance().getReference("places");

    ArrayList<Place> dataPlaces = new ArrayList<>();
}
