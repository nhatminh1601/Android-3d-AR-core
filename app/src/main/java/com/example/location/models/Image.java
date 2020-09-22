package com.example.location.model;

import java.io.Serializable;

public class Image implements Serializable {
    Integer id;
    String name;
    String url;
    String desc;
    String longDesc;
    Integer group;
    Integer image;
    Integer image3D;
    Integer isFavourite;

    public Image() {
    }

    public Image(Integer id, String name, String url, String desc, Integer group) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.desc = desc;
        this.group = group;
    }

    public Image(Integer id, String name, String url, String desc, Integer group, Integer image, Integer isFavourite) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.desc = desc;
        this.group = group;
        this.image = image;
        this.isFavourite = isFavourite;
    }

    public Image(Integer id, String name, String url, String desc, Integer group, Integer image, Integer image3D, Integer isFavourite) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.desc = desc;
        this.group = group;
        this.image = image;
        this.image3D = image3D;
        this.isFavourite = isFavourite;
    }
    public Image(Integer id, String name, String url, String desc, Integer group, Integer image, Integer isFavourite,String longDesc) {
        this.id = id;
        this.name = name;
        this.url = url;
        this.desc = desc;
        this.group = group;
        this.image = image;
        this.image3D = image3D;
        this.isFavourite = isFavourite;
        this.longDesc=longDesc;
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

    public Integer getImage() {
        return image;
    }

    public void setImage(Integer image) {
        this.image = image;
    }

    public Integer getIsFavourite() {
        return isFavourite;
    }

    public void setIsFavourite(Integer isFavourite) {
        this.isFavourite = isFavourite;
    }

    public Integer getImage3D() {
        return image3D;
    }

    public void setImage3D(Integer image3D) {
        this.image3D = image3D;
    }

    public String getLongDesc() {
        return longDesc;
    }

    public void setLongDesc(String longDesc) {
        this.longDesc = longDesc;
    }

    @Override
    public String toString() {
        return "Image{" +
                "id=" + id +
                ", name='" + name + '\'' +
                ", url='" + url + '\'' +
                ", desc='" + desc + '\'' +
                ", longDesc='" + longDesc + '\'' +
                ", group=" + group +
                ", image=" + image +
                ", image3D=" + image3D +
                ", isFavourite=" + isFavourite +
                '}';
    }
}
