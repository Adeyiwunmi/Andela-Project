package com.example.leniovo.andelaproject;

import android.os.Parcel;
import android.os.Parcelable;

/**
 * Created by LENIOVO on 4/19/2017.
 */

public class User implements Parcelable {


    private String username, avatarURl,profileUrl;

    public User(String username, String avatarURl, String profileUrl) {
        this.username = username;
        this.avatarURl = avatarURl;
        this.profileUrl = profileUrl;
    }

    protected User(Parcel in) {
        username = in.readString();
        avatarURl = in.readString();
        profileUrl = in.readString();
    }

    public static final Creator<User> CREATOR = new Creator<User>() {
        @Override
        public User createFromParcel(Parcel in) {
            return new User(in);
        }

        @Override
        public User[] newArray(int size) {
            return new User[size];
        }
    };

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getAvatarURl() {
        return avatarURl;
    }

    public void setAvatarURl(String avatarURl) {
        this.avatarURl = avatarURl;
    }

    public String getProfileUrl() {
        return profileUrl;
    }

    public void setProfileUrl(String profileUrl) {
        this.profileUrl = profileUrl;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(username);
        dest.writeString(avatarURl);
        dest.writeString(profileUrl);
    }
}
