package com.example.location.models;

public class Anchor {
    String id;
    Type type;
    Boolean isLast;

    public Anchor(String id, Type type, Boolean isLast) {
        this.id = id;
        this.type = type;
        this.isLast = isLast;
    }

    public Anchor(String id, String type, boolean isLast) {
        this.id = id;
        this.type = Type.valueOf(type);
        this.isLast = isLast;
    }

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public Type getType() {
        return type;
    }

    public void setType(Type type) {
        this.type = type;
    }

    public Boolean getLast() {
        return isLast;
    }

    public void setLast(Boolean last) {
        isLast = last;
    }
}
