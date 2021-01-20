package com.example.lv_music.Model;

public class Advertisement {

    private String id;
    private String songId;
    private String adImage;
    private String content;
    private String songImage;
    private String songLink;

    public String getId() {
        return id;
    }

    public void setId(String id) {
        this.id = id;
    }

    public String getSongId() {
        return songId;
    }

    public void setSongId(String songId) {
        this.songId = songId;
    }

    public String getAdImage() {
        return adImage;
    }

    public void setAdImage(String adImage) {
        this.adImage = adImage;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

    public String getSongImage() {
        return songImage;
    }

    public void setSongImage(String songImage) {
        this.songImage = songImage;
    }

    public String getSongLink() {
        return songLink;
    }

    public void setSongLink(String songLink) {
        this.songLink = songLink;
    }

    @Override
    public String toString() {
        return "Advertisement{" +
                "id='" + id + '\'' +
                ", songId='" + songId + '\'' +
                ", adImage='" + adImage + '\'' +
                ", content='" + content + '\'' +
                ", songImage='" + songImage + '\'' +
                ", songLink='" + songLink + '\'' +
                '}';
    }
}