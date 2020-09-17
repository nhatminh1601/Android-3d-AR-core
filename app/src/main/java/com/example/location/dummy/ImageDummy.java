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
            imageList.add(new Image(1, "Carnotaurus", "dinosaur/carnotaurus.sfb", "Hình khủng long", 1, R.drawable.d_carnotaurus, 1));
            imageList.add(new Image(2, "Ceratosaurus", "dinosaur/ceratosaurus.sfb", "Hình khủng long", 1, R.drawable.d_ceratosaurus, 1));
            imageList.add(new Image(3, "Cryolophosaurus", "dinosaur/cryolophosaurus.sfb", "Hình khủng long", 1, R.drawable.d_cryolophosaurus, 1));
            imageList.add(new Image(4, "Diabloceratops", "dinosaur/diabloceratops.sfb", "Hình khủng long", 1, R.drawable.d_diabloceratops, 1));
            imageList.add(new Image(5, "Diplodocus", "dinosaur/diplodocus.sfb", "Hình khủng long", 1, R.drawable.d_diplodocus, 1));
            imageList.add(new Image(6, "Gorgosaurus", "dinosaur/gorgosaurus.sfb", "Hình khủng long", 1, R.drawable.d_gorgosaurus, 1));
            imageList.add(new Image(7, "Skeletondiplodocus", "dinosaur/skeletondiplodocus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(8, "Skeletonteratophoneus", "dinosaur/skeletonteratophoneus.sfb", "Hình khủng long", 1, R.drawable.f_stegosaurus, 1));
            imageList.add(new Image(9, "Spinosaurus", "dinosaur/spinosaurus.sfb", "Hình khủng long", 1, R.drawable.d_spinosaurus, 1));
            // cá
            imageList.add(new Image(10, "Angel 1", "fish/Angle1/AngelFish3.gltf", "Hình cá", 2, R.drawable.angel1, 1));
            imageList.add(new Image(11, "Angel Fish", "fish/AngelFish/AngelFish.gltf", "Hình cá", 2, R.drawable.angelfish, 1));
            imageList.add(new Image(12, "Angler", "fish/Angler/AngelerlFish.gltf", "Hình cá", 2, R.drawable.angler, 1));
            imageList.add(new Image(13, "Clown Fish", "fish/ClownFish/ClownFish.gltf", "Hình cá", 2, R.drawable.clown_fish, 1));
            imageList.add(new Image(14, "Fish", "fish/fish/fish.gltf", "Hình cá", 2, R.drawable.fish, 1));
            imageList.add(new Image(15, "Koi", "fish/Koi/Koifish.gltf", "Hình cá", 2, R.drawable.koi, 1));
            // chim
            imageList.add(new Image(16, "Canada", "bird/Canada/Canada.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            imageList.add(new Image(17, "Flamingo", "bird/Flamingo/Flamingo.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            imageList.add(new Image(18, "Seagull", "bird/Seagull/Seagull.gltf", "Hình Chim", 3, R.drawable.chim, 1));
            // thực vật
            imageList.add(new Image(19, "tree", "tree/tree/Tree02.gltf", "tree", 4, R.drawable.cay, 1));
            // xương
            imageList.add(new Image(1, "Bone", "bone/Bone/Bone01.gltf", "Bone", 5, R.drawable.xuong, 1));
            imageList.add(new Image(2, "Dinosaur", "bone/Dinosaur/DinosaurSkull.gltf", "Xương khủng long", 5, R.drawable.xuong, 1));
            imageList.add(new Image(3, "SprineBase", "bone/SprineBase/SprineBase.gltf", "SprineBase", 5, R.drawable.xuong, 1));
            imageList.add(new Image(3, "Trilobite", "bone/Trilobite/TrilobiteA.gltf", "TrilobiteA", 5, R.drawable.xuong, 1));
            imageList.add(new Image(3, "Trilobited", "bone/Trilobited/TrilobitedB.gltf", "TrilobitedB", 5, R.drawable.xuong, 1));
            // tranh
            imageList.add(new Image(20, "Van gogh", "tranh/vangoghmuseum/vangoghmuseum.gltf", "Tranh của Van gogh", 6, R.drawable.h1, 1));
            imageList.add(new Image(21, "Van gogh", "tranh/b2/b2.gltf", "Tranh của Van gogh", 6, R.drawable.h4, 1));
            imageList.add(new Image(22, "Van gogh", "tranh/b3/b3.gltf", "Tranh của Van gogh", 6, R.drawable.h3, 1));
            imageList.add(new Image(23, "Van gogh", "tranh/b4/b4.gltf", "Tranh của Van gogh", 6, R.drawable.h2, 1));
        }
      

    }

    public List<Image> List() {
        return imageList;
    }
}
