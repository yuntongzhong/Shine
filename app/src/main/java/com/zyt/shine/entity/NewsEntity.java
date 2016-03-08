package com.zyt.shine.entity;

/**
 * Created by zyt on 2016/3/4.
 */

import android.graphics.drawable.Icon;
import android.os.Parcel;
import android.os.Parcelable;
import android.support.v7.widget.RecyclerView;
import android.view.View;

import java.io.Serializable;

/**
 * 推荐实体类
 */
public class NewsEntity implements Parcelable {
    private Object icon;
    private String name;
    private String url;
    private String msg;

    protected NewsEntity(Parcel in) {
        name = in.readString();
        url = in.readString();
        msg = in.readString();
        releaseTime = in.readString();
    }

    public static final Creator<NewsEntity> CREATOR = new Creator<NewsEntity>() {
        @Override
        public NewsEntity createFromParcel(Parcel in) {
            return new NewsEntity(in);
        }

        @Override
        public NewsEntity[] newArray(int size) {
            return new NewsEntity[size];
        }
    };

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getUrl() {
        return url;
    }

    public void setUrl(String url) {
        this.url = url;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public String getReleaseTime() {
        return releaseTime;
    }

    public void setReleaseTime(String releaseTime) {
        this.releaseTime = releaseTime;
    }

    private String releaseTime;

    public NewsEntity(Object icon, String name, String url, String msg,String releaseTime) {
        this.icon = icon;
        this.name = name;
        this.url = url;
        this.msg = msg;
        this.releaseTime=releaseTime;
    }


    public Object getIcon() {
        return icon;
    }

    public void setIcon(Object icon) {
        this.icon = icon;
    }

    @Override
    public int describeContents() {
        return 0;
    }

    @Override
    public void writeToParcel(Parcel dest, int flags) {
        dest.writeString(name);
        dest.writeString(url);
        dest.writeString(msg);
        dest.writeString(releaseTime);
    }
}
