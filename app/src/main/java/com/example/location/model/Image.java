package com.example.location.model;

import java.io.Serializable;

public class Image implements Serializable {
    Integer id;
    String url;
    String desc;
    Integer group;

    public Image(Integer id, String url, String desc, Integer group) {
        this.id = id;
        this.url = url;
        this.desc = desc;
        this.group = group;
    }

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getDesc() {
        return desc;
    }

    public void setDesc(String desc) {
        this.desc = desc;
    }

    public Integer getGroup() {
        return group;
    }

    public void setGroup(Integer group) {
        this.group = group;
    }
}
