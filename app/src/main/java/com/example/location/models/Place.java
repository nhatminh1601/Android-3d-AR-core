package com.example.location.models;

import java.io.Serializable;
import java.util.ArrayList;

public class Place implements Serializable {
    int id;
    String name, url, description;
    ArrayList<String> anchors;

    public Place() {
    }

    public Place(int id, String name, String url, String description) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
        this.anchors = new ArrayList<>();
    }

    public Place(int id, String name, String url, String description, ArrayList<String> anchors) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.description = description;
        this.anchors = anchors;
    }

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public ArrayList<String> getAnchors() {
        return anchors;
    }

    public void setAnchors(ArrayList<String> anchors) {
        this.anchors = anchors;
    }

    @Override
    public String toString() {
        return "Place{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", description='" + description + '\'' +
                ", anchors=" + anchors +
                '}';
    }
}
