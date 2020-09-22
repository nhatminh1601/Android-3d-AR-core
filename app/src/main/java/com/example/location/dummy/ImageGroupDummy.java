package com.example.location.dummy;

import com.example.location.R;
import com.example.location.model.ImageGroup;

import java.util.ArrayList;
import java.util.List;

public class ImageGroupDummy {
    List<ImageGroup> imageGroups = new ArrayList<>();

    public ImageGroupDummy() {
        imageGroups.add(new ImageGroup(1, "Khủng long","Những mẫu khủng long 3d", R.drawable.khunglong));
        imageGroups.add(new ImageGroup(2, "Cá","Những mẫu cá tiêu bản, hình 3d", R.drawable.ca));
        imageGroups.add(new ImageGroup(3, "Chim","Những chim 3d", R.drawable.chim));
        imageGroups.add(new ImageGroup(4, "Thực vật","Những loại thực vật 3d", R.drawable.bg));
        imageGroups.add(new ImageGroup(5, "Xương","Những tiêu bản xướng động thực vật", R.drawable.xuong));
        imageGroups.add(new ImageGroup(6, "Tranh","Những tác phẩm nghệ thuật nổi tiếng", R.drawable.hoa));
    }

    public List<ImageGroup> list() {
        return imageGroups;
    }
}