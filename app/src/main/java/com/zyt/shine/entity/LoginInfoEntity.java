package com.zyt.shine.entity;

/**
 * Created by zyt on 2016/3/4.
 */
public class LoginInfoEntity {
    private String userName;
    private Object userIcon;
    private String personalProfile;

    public LoginInfoEntity(String userName, Object userIcon, String personalProfile) {
        this.userName = userName;
        this.userIcon = userIcon;
        this.personalProfile = personalProfile;
    }

    public String getUserName() {
        return userName;

    }

    public void setUserName(String userName) {
        this.userName = userName;
    }

    public Object getUserIcon() {
        return userIcon;
    }

    public void setUserIcon(Object userIcon) {
        this.userIcon = userIcon;
    }

    public String getPersonalProfile() {
        return personalProfile;
    }

    public void setPersonalProfile(String personalProfile) {
        this.personalProfile = personalProfile;
    }
}
