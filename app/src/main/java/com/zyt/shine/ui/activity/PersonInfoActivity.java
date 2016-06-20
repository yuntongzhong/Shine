package com.zyt.shine.ui.activity;

import android.os.Build;
import android.os.Bundle;
import android.support.design.widget.CollapsingToolbarLayout;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.ViewGroup;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.engine.DiskCacheStrategy;
import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zyt.shine.R;

/**
 * Created by zyt on 2016/3/9.
 */
public class PersonInfoActivity extends AppCompatActivity {
    Toolbar toolbar;
    CollapsingToolbarLayout collapsingToolbarLayout;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_person_info);
        toolbar = (Toolbar) findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);
        ImageView bannner = (ImageView) findViewById(R.id.bannner);
        //标题
        collapsingToolbarLayout = (CollapsingToolbarLayout) findViewById(R.id.toolbar_layout);
        collapsingToolbarLayout.setTitle("个人资料");
        setStatusBarColorForKitkat(R.color.colorPrimaryDark);

        //Glide.with(this).load(R.mipmap.default_pic).diskCacheStrategy(DiskCacheStrategy.ALL).into(bannner);
        //collapsingToolbarLayout.setContentScrimColor(ContextCompat.getColor(this, R.color.colorPrimary));
//            setStatusBarColorForKitkat(R.color.colorPrimaryDark);
        initToolbar();
    }

    public void setStatusBarColorForKitkat(int color) {
        /**
         * Android4.4
         */
        if (Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
            this.getWindow().addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            SystemBarTintManager tintManager = new SystemBarTintManager(this);
            tintManager.setStatusBarTintEnabled(true);
            tintManager.setStatusBarTintResource(color);
        }
    }

    void initToolbar(){
        //让应用占用状态栏的位置
        getWindow().addFlags(WindowManager.LayoutParams.FLAG_LAYOUT_NO_LIMITS);
        //设置toolbar外上边距=状态栏高度
        ViewGroup.LayoutParams layoutParams = toolbar.getLayoutParams();
        layoutParams.height+=getStatusBarHeight();

    }

    //获取状态栏高度
    public int getStatusBarHeight() {
        int result = 0;
        int resourceId = getResources().getIdentifier("status_bar_height", "dimen", "android");
        if (resourceId > 0) {
            result = getResources().getDimensionPixelSize(resourceId);
        }
        return result;
    }
}
