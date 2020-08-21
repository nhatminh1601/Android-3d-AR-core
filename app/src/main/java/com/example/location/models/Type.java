package com.example.location.models;

public enum Type {
    LEFT("left.gltf"),
    RIGHT("right.gltf"),
    STRAIGHT("straight.gltf");

    String url;

    Type(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
