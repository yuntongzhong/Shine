package com.zyt.shine.fragment;

import com.zyt.shine.R;

/**
 * 导航栏，用于切换fragment
 * Created by zyt on 2016/3/2.
 */
public enum Navigation {
    //    message{1,"消息",MessageFragment.class}contacts,news,setting
    MESSAGE(0, "消息", R.drawable.tab_icon_message,
            MessageFragment.class),
    CONTACTS(0, "联系人", R.drawable.tab_icon_contacts,
            ContactsFragment.class),
    NEWS(0, "推荐", R.drawable.tab_icon_news,
            NewsFragment.class),
    SETTING(0, "设置", R.drawable.tab_icon_setting,
            SettingFragment.class);
    private int id;

    public String getName() {
        return name;
    }

    public Class<?> getClz() {
        return clz;
    }

    public int getResIcon() {
        return resIcon;
    }

    public int getId() {
        return id;
    }

    private String name;
    private int resIcon;
    private Class<?> clz;

    private Navigation(int id, String name, int resIcon, Class<?> clz) {
        this.id = id;
        this.name = name;
        this.resIcon = resIcon;
        this.clz = clz;
    }



}
