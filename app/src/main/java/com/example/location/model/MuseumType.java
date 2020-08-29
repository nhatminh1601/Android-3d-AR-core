package com.example.location.model;

import java.io.Serializable;

public class MuseumType implements Serializable {
    Integer id;
    String name;

    public MuseumType(Integer id, String name) {
        this.id = id;
        this.name = name;
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
}
