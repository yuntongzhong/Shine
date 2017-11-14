package com.zyt.shine.ui.activity;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.util.Log;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.Toast;

import com.bumptech.glide.Glide;
import com.zyt.shine.R;
import com.zyt.shine.bean.Example;
import com.zyt.shine.bean.Weather;
import com.zyt.shine.glide.GlideCircleTransform;
import com.zyt.shine.network.RetrofitManager;
import com.zyt.shine.ui.view.TwoBtnFragmentDialog;
import com.zyt.shine.utils.ImmersedStatusbarUtils;

import org.androidannotations.annotations.AfterViews;
import org.androidannotations.annotations.Click;
import org.androidannotations.annotations.EActivity;
import org.androidannotations.annotations.ViewById;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.Response;
import rx.Subscriber;
import rx.Subscription;
import rx.android.schedulers.AndroidSchedulers;
import rx.observers.SafeSubscriber;
import rx.schedulers.Schedulers;

/**
 * Created by zyt on 2015/11/20.
 */
@EActivity(R.layout.activity_login)
public class LoginActivity extends AppCompatActivity {
    @ViewById(R.id.toolbar)
    Toolbar toolbar;
    @ViewById(R.id.login_button)
    Button loginBtn;
    @ViewById(R.id.myImage)
    ImageView icon;
    private String url = "http://img0.imgtn.bdimg.com/it/u=2294871039,3507383977&fm=206&gp=0.jpg";

    private Subscription subscribe;

    @AfterViews
    protected void afterViews() {
        ImmersedStatusbarUtils.initAfterSetContentView(this, toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("用户登录");
        setHeadPortrait(url);
    }

    @Click(R.id.myImage)
    void clickIcon() {
        startActivity(new Intent(this, PersonInfoActivity.class));
    }


    /**
     * 点击登录按钮
     */
    @Click(R.id.login_button)
    void loginClick() {

        getWeather();
    }

    void getWeather() {
        Log.e("LoginActivity", "start");
        //subscribe=
        RetrofitManager.getApiService().getWeather("杭州")
                .subscribeOn(Schedulers.io())
                .observeOn(AndroidSchedulers.mainThread())
                .subscribe(new Subscriber<Example>() {
                    @Override
                    public void onCompleted() {
                        Log.e("LoginActivity", "onCompleted");
                    }

                    @Override
                    public void onError(Throwable e) {
                        e.printStackTrace();
                    }

                    @Override
                    public void onNext(Example example) {
                       String city= example.getData().getCity();
                        String wendu=example.getData().getWendu();
                        Toast.makeText(LoginActivity.this, city+":"+wendu, Toast.LENGTH_SHORT).show();
                        Log.e("LoginActivity", city);
                    }
                });
    }

    /**
     * 设置头像
     */
    private void setHeadPortrait(String url) {
        Glide.with(this).load(url)
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(icon);
        System.out.println("test");
        System.out.println("test2");
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }

    @Override
    public boolean onOptionsItemSelected(MenuItem item) {
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }
}