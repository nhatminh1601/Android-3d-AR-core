package com.example.location.dummy;

import com.example.location.R;
import com.example.location.model.Image;

import java.util.ArrayList;
import java.util.List;

public class ImageDummy {
    List<Image> imageList;

    public ImageDummy(int type) {
        if (type == 1) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Allosaurus", "dinosaur/allosaurus.sfb", "Hình khủng long", 1, R.drawable.d_allosaurus, 1));
            imageList.add(new Image(2, "Carnotaurus", "dinosaur/carnotaurus.sfb", "Hình khủng long", 1, R.drawable.d_carnotaurus, 1));
            imageList.add(new Image(3, "Ceratosaurus", "dinosaur/ceratosaurus.sfb", "Hình khủng long", 1, R.drawable.d_ceratosaurus, 1));
            imageList.add(new Image(4, "Cryolophosaurus", "dinosaur/cryolophosaurus.sfb", "Hình khủng long", 1, R.drawable.d_cryolophosaurus, 1));
            imageList.add(new Image(5, "Diabloceratops", "dinosaur/diabloceratops.sfb", "Hình khủng long", 1, R.drawable.d_diabloceratops, 1));
            imageList.add(new Image(6, "Diplodocus", "dinosaur/diplodocus.sfb", "Hình khủng long", 1, R.drawable.d_diplodocus, 1));
            imageList.add(new Image(7, "Fossilcoelophysis", "dinosaur/fossilcoelophysis.sfb", "Hình khủng long", 1, R.drawable.f_coelophysis_2, 1));
            imageList.add(new Image(8, "Fossilcoelophysissecond", "dinosaur/fossilcoelophysissecond.sfb", "Hình khủng long", 1, R.drawable.f_coelophysis, 1));
            imageList.add(new Image(9, "Gorgosaurus", "dinosaur/gorgosaurus.sfb", "Hình khủng long", 1, R.drawable.d_gorgosaurus, 1));
            imageList.add(new Image(10, "Skeletondiplodocus", "dinosaur/skeletondiplodocus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(11, "Skeletonteratophoneus", "dinosaur/skeletonteratophoneus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(12, "Spinosaurus", "dinosaur/spinosaurus.sfb", "Hình khủng long", 1, R.drawable.d_spinosaurus, 1));
        }
        if (type == 2) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.ca, 1));
            imageList.add(new Image(2, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.ca, 1));
            imageList.add(new Image(3, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.ca, 1));

        }
        if (type == 3) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.chim, 1));
        }
        if (type == 4) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));

        }
        if (type == 5) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(2, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(3, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(4, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        }
        if (type == 6) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(2, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(3, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
            imageList.add(new Image(4, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        }
        if (type == 7) {
            imageList = new ArrayList<>();
            imageList.add(new Image(1, "Hình 1", "dinosaur/allosaurus.sfb", "hình ảnh phong cảnh", 1, R.drawable.khampha, 1));
        }


    }

    public List<Image> List() {
        return imageList;
    }
}
