package com.example.leniovo.andelaproject;

/**
 * Created by LENIOVO on 4/19/2017.
 */

public class User {


    private String username, avatarURl,profileUrl;

    public User(String username, String avatarURl, String profileUrl) {
        this.username = username;
        this.avatarURl = avatarURl;
        this.profileUrl = profileUrl;
    }

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

}
