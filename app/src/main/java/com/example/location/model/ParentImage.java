package com.example.location.model;

import java.io.Serializable;
import java.util.List;

public class ParentImage implements Serializable {
    Integer id;
    String name;
    List<Image> images;

    public ParentImage() {
    }

    public ParentImage(Integer id, String name, List<Image> images) {
        this.id = id;
        this.name = name;
        this.images = images;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public List<Image> getImages() {
        return images;
    }

    public void setImages(List<Image> images) {
        this.images = images;
    }

    public void addImage(Image image) {
        this.images.add(image);
    }
}
