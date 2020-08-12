package com.example.location.helpers;

import com.example.location.models.Menu;

import java.util.ArrayList;

public class TempData {
 public ArrayList<Menu> GetData(){
     ArrayList<Menu> data= new ArrayList<>();
     data.add(new Menu(1,"Hội trường A","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(2,"Sảnh H","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(3,"Phòng đào tạo","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(4,"Khu A","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(5,"Khu B","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(6,"Khu C","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(7,"Khu E","","Phòng hội nghị, tổ chức sự kiện"));
     data.add(new Menu(8,"Khu F","","Phòng hội nghị, tổ chức sự kiện"));
     return data;
 }
}
