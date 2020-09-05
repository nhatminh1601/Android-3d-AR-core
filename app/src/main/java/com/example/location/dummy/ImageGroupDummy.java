package com.example.location.dummy;

import com.example.location.R;
import com.example.location.model.ImageGroup;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupDummy {
    List<ImageGroup> imageGroups = new ArrayList<>();

    public ImageGroupDummy() {
        imageGroups.add(new ImageGroup(1, "Khủng long","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.bg));
        imageGroups.add(new ImageGroup(2, "Cá","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.bg));
        imageGroups.add(new ImageGroup(3, "Chim","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.bg));
        imageGroups.add(new ImageGroup(4, "Thực vật","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.bg));
        imageGroups.add(new ImageGroup(5, "Xương","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.bg));
    }

    public List<ImageGroup> list() {
        return imageGroups;
    }
}
