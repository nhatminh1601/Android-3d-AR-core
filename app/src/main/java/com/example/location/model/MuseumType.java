package com.example.location.model;

import java.io.Serializable;

public class MuseumType implements Serializable {
    Integer id;
    String name;
    Integer image;
    String description;

    public MuseumType() {
    }

    public MuseumType(Integer id, String name, Integer image, String description) {
        this.id = id;
        this.name = name;
        this.image = image;
        this.description = description;
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

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    @Override
    public String toString() {
        return "MuseumType{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", image=" + image +
                ", description='" + description + '\'' +
                '}';
    }
}
