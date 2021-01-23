package com.example.lv_music.Model;

import com.google.gson.annotations.Expose;
import com.google.gson.annotations.SerializedName;

public class Singer {

private String singer_id;
private String stage_name;
private String real_name;
private String birthdate;
private String country;
private String information;
private String avatar;

public String getSingerId() {
return singer_id;
}

public void setSingerId(String singerId) {
this.singer_id = singerId;
}

public String getStageName() {
return stage_name;
}

public void setStageName(String stageName) {
this.stage_name = stageName;
}

public String getRealName() {
return real_name;
}

public void setRealName(String realName) {
this.real_name = realName;
}

public String getBirthdate() {
return birthdate;
}

public void setBirthdate(String birthdate) {
this.birthdate = birthdate;
}

public String getCountry() {
return country;
}

public void setCountry(String country) {
this.country = country;
}

public String getInformation() {
return information;
}

public void setInformation(String information) {
this.information = information;
}

public String getAvatar() {
return avatar;
}

public void setAvatar(String avatar) {
this.avatar = avatar;
}

    @Override
    public String toString() {
        return "Singer{" +
                "singerId='" + singer_id + '\'' +
                ", stageName='" + stage_name + '\'' +
                ", realName='" + real_name + '\'' +
                ", birthdate='" + birthdate + '\'' +
                ", country='" + country + '\'' +
                ", information='" + information + '\'' +
                ", avatar='" + avatar + '\'' +
                '}';
    }
}