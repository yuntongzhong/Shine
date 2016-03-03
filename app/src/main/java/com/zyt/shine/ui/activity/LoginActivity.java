package com.zyt.shine.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;

import com.bumptech.glide.Glide;
import com.bumptech.glide.load.resource.drawable.GlideDrawable;
import com.bumptech.glide.request.animation.GlideAnimation;
import com.bumptech.glide.request.target.BitmapImageViewTarget;
import com.bumptech.glide.request.target.SimpleTarget;
import com.zyt.shine.R;
import com.zyt.shine.glide.GlideCircleTransform;
import com.zyt.shine.glide.GlideRoundTransform;
import com.zyt.shine.utils.ImmersedStatusbarUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

/**
 * Created by zyt on 2015/11/20.
 */
@EActivity(R.layout.login_activity)
public class LoginActivity extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.login_button)
    Button loginBtn;
    @ViewById(R.id.myImage)
    ImageView icon;

    @AfterViews
    protected void afterViews() {
        ImmersedStatusbarUtils.initAfterSetContentView(this, toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("用户登录");
    }

    /**
     * 点击登录按钮
     */
    @Click(R.id.login_button)
    void loginClick() {
        setHeadPortrait("http://img1.3lian.com/img2008/06/019/ych.jpg");
    }

    /**
     * 设置头像
     */
    private void setHeadPortrait(String url){
        Glide.with(this).load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(icon);
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        // Handle action bar item clicks here. The action bar will
        // automatically handle clicks on the Home/Up button, so long
        // as you specify a parent activity in AndroidManifest.xml.

        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
//            case R.id.action_settings:
//                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}
