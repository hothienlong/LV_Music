package com.example.lv_music.Model;


public class Song {

    private String id;
    private String name;
    private String image;
    private String song_link;
    private String mv_link;
    private String lyric;

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

    public String getSong_link() {
        return song_link;
    }

    public void setSong_link(String song_link) {
        this.song_link = song_link;
    }

    public String getMv_link() {
        return mv_link;
    }

    public void setMv_link(String mv_link) {
        this.mv_link = mv_link;
    }

    public String getLyric() {
        return lyric;
    }

    public void setLyric(String lyric) {
        this.lyric = lyric;
    }

    @Override
    public String toString() {
        return "Song{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", song_link='" + song_link + '\'' +
                ", mv_link='" + mv_link + '\'' +
                ", lyric='" + lyric + '\'' +
                '}';
    }
}