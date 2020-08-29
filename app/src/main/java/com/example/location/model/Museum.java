package com.example.location.model;

import java.io.Serializable;
import java.util.List;

public class Museum implements Serializable {
    Integer id;
    String name;
    Integer type;
    Integer user;
    List<Integer> images;

    public Museum(Integer id, String name, Integer type, Integer user, List<Integer> images) {
        this.id = id;
        this.name = name;
        this.type = type;
        this.user = user;
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

    public Integer getType() {
        return type;
    }

    public void setType(Integer type) {
        this.type = type;
    }

    public Integer getUser() {
        return user;
    }

    public void setUser(Integer user) {
        this.user = user;
    }

    public List<Integer> getImages() {
        return images;
    }

    public void setImages(List<Integer> images) {
        this.images = images;
    }
}
