package com.example.location.dummy;

import com.example.location.model.Image;
import com.example.location.model.Museum;
import com.example.location.model.User;
import com.google.firebase.database.DatabaseReference;
import com.google.firebase.database.FirebaseDatabase;

import java.util.ArrayList;
import java.util.List;

public class DataGenerate {
    FirebaseDatabase firebaseDatabase = FirebaseDatabase.getInstance();
    DatabaseReference museumsRef = firebaseDatabase.getReference("museums");
    DatabaseReference imagesRef = firebaseDatabase.getReference("images");
    DatabaseReference usersRef = firebaseDatabase.getReference("users");

    List<Museum> museums;
    List<Image> images;
    List<User> users;

    public DataGenerate() {
        museums = new ArrayList<>();
        images = new ArrayList<>();
        users = new ArrayList<>();
    }

    public void generate() {
        generateUsers();
        generateImages();
        generateMuseums();
        saveData();
    }

    private void generateUsers() {

    }

    private void generateImages() {
        ImageDummy imageDummy = new ImageDummy(1);
        images = imageDummy.List();
    }

    private void generateMuseums() {

    }

    private void saveData() {
        if (!museums.isEmpty())
            museumsRef.setValue(museums);
        if (!images.isEmpty())
            imagesRef.setValue(images);
        if (!users.isEmpty())
            usersRef.setValue(users);
    }
}
