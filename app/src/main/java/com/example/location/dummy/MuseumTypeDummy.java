package com.example.location.dummy;

import com.example.location.R;
import com.example.location.model.MuseumType;

import java.util.ArrayList;
import java.util.List;

public class MuseumTypeDummy {
    List<MuseumType> museumTypes = new ArrayList<>();

    public MuseumTypeDummy() {
        museumTypes.add(new MuseumType(1, "Tác phẩm nghệ thuật", R.drawable.bg,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Viện bảo tàng",R.drawable.baotang,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Tranh phong cảnh",R.drawable.khampha,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Địa điểm",R.drawable.phongcanh,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Âm Nhạc",R.drawable.khampha,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Tác phẩm nghệ thuật",R.drawable.bg,"Những tác phẩm nghệ thuật nổi tiếng"));
        museumTypes.add(new MuseumType(1, "Tác phẩm nghệ thuật",R.drawable.bg,"Những tác phẩm nghệ thuật nổi tiếng"));
    }

    public List<MuseumType> list() {
        return museumTypes;
    }
}
