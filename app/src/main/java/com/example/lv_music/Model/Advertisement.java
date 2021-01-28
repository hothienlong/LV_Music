package com.example.lv_music.Model;

import android.os.Parcel;
import android.os.Parcelable;

import java.io.Serializable;

public class Advertisement implements Parcelable {

    private String id;
    private String song_id;
    private String image;
    private String content;

    protected Advertisement(Parcel in) {
        id = in.readString();
        song_id = in.readString();
        image = in.readString();
        content = in.readString();
    }

    public static final Creator<Advertisement> CREATOR = new Creator<Advertisement>() {
        @Override
        public Advertisement createFromParcel(Parcel in) {
            return new Advertisement(in);
        }

        @Override
        public Advertisement[] newArray(int size) {
            return new Advertisement[size];
        }
    };

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

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(id);
        dest.writeString(song_id);
        dest.writeString(image);
        dest.writeString(content);
    }
}