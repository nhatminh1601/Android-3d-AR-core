package com.example.location.models;

public enum Type {
    LEFT("imagea/model.gltf"),
    RIGHT("imageb/model.gltf"),
    STRAIGHT("imagec/159790.gltf");

    String url;

    Type(String url) {
        this.url = url;
    }

    public String getUrl() {
        return url;
    }
}
