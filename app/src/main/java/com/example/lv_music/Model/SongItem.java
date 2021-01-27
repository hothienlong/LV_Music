package com.example.lv_music.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;
import java.util.List;

// implements Serializable để putextra đối tượng giữa các activity
public class SongItem implements Parcelable {

    private String id;
    private String name;
    private String image;
    private String song_link;
    private String mv_link;
    private String lyric;
    private List<String> lst_singer_names = null; // dùng jsonschemapojo => tên sai lstSingerNames... (nếu xóa đi serialize)

    protected SongItem(Parcel in) {
        id = in.readString();
        name = in.readString();
        image = in.readString();
        song_link = in.readString();
        mv_link = in.readString();
        lyric = in.readString();
        lst_singer_names = in.createStringArrayList();
    }

    public static final Creator<SongItem> CREATOR = new Creator<SongItem>() {
        @Override
        public SongItem createFromParcel(Parcel in) {
            return new SongItem(in);
        }

        @Override
        public SongItem[] newArray(int size) {
            return new SongItem[size];
        }
    };

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
        return "SongItem{" +
                "id='" + id + '\'' +
                ", name='" + name + '\'' +
                ", image='" + image + '\'' +
                ", song_link='" + song_link + '\'' +
                ", mv_link='" + mv_link + '\'' +
                ", lyric='" + lyric + '\'' +
                ", lst_singer_names=" + lst_singer_names +
                '}';
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(name);
        dest.writeString(image);
        dest.writeString(song_link);
        dest.writeString(mv_link);
        dest.writeString(lyric);
        dest.writeStringList(lst_singer_names);
    }

}