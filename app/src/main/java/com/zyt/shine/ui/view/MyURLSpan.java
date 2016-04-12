package com.zyt.shine.ui.view;

import android.content.Context;
import android.graphics.Color;
import android.text.TextPaint;
import android.text.style.ClickableSpan;
import android.view.View;
import android.widget.Toast;

/**
 * Created by zyt on 2016/3/10.
 */
public class MyURLSpan extends ClickableSpan{

    private String url;
    private Context mContext;

    @Override
    public void updateDrawState(TextPaint ds) {
        super.updateDrawState(ds);
        ds.setColor(Color.parseColor("#f37021"));//颜色
        ds.setUnderlineText(false);//去掉下划线
    }

    public MyURLSpan(Context context,String url) {
        this.url = url;
        this.mContext=context;
    }

    @Override
    public void onClick(View widget) {
        Toast.makeText(mContext, url + " is clicked", Toast.LENGTH_SHORT).show();
    }
}
