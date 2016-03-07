package com.zyt.shine.entity;

/**
 * Created by zyt on 2016/3/4.
 */

import android.graphics.drawable.Icon;
import android.support.v7.widget.RecyclerView;
import android.view.View;

/**
 * 推荐实体类
 */
public class NewsEntity {
    private Object icon;
    private String name;
    private String url;
    private String msg;

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
}
