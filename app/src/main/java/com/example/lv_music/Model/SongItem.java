package com.example.lv_music.Model;

import java.io.Serializable;
import java.util.List;

// implements Serializable để putextra đối tượng giữa các activity
public class SongItem implements Serializable {

    private String id;
    private String name;
    private String image;
    private List<String> lst_singer_names = null; // dùng jsonschemapojo => tên sai lstSingerNames... (nếu xóa đi serialize)

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public List<String> getLstSingerNames() {
        return lst_singer_names;
    }

    public void setLstSingerNames(List<String> lstSingerNames) {
        this.lst_singer_names = lstSingerNames;
    }

    @Override
    public String toString() {
        return "SongItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", lst_singer_names=" + lst_singer_names +
                '}';
    }
}