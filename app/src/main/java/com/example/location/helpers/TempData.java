package com.example.location.helpers;

import com.example.location.models.Place;

import java.util.ArrayList;

public class TempData {
 public ArrayList<Place> GetData(){
     ArrayList<Place> data= new ArrayList<>();
     data.add(new Place(1,"Hội trường A","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(2,"Sảnh H","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(3,"Phòng đào tạo","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(4,"Khu A","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(5,"Khu B","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(6,"Khu C","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(7,"Khu E","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Place(8,"Khu F","","Phòng hội nghị, tổ chức sự kiện"));
     return data;
 }
}
