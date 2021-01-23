package com.example.lv_music.Model;

import java.io.Serializable;

public class Advertisement implements Serializable {

    private String id;
    private String song_id;
    private String image;
    private String content;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongId() {
        return song_id;
    }

    public void setSongId(String songId) {
        this.song_id = songId;
    }

    public String getImage() {
        return image;
    }

    public void setImage(String image) {
        this.image = image;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id='" + id + '\'' +
                ", songId='" + song_id + '\'' +
                ", image='" + image + '\'' +
                ", content='" + content + '\'' +
                '}';
    }
}