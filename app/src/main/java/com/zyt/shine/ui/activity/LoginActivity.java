package com.zyt.shine.ui.activity;

import android.content.res.Resources;
import android.graphics.Bitmap;
import android.support.v4.graphics.drawable.RoundedBitmapDrawable;
import android.support.v4.graphics.drawable.RoundedBitmapDrawableFactory;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.Toolbar;
import android.util.Log;
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

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

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
    //http://gaoxiao.jokeji.cn/GrapHtml/dongtai/20120921221658.htm
    String url = "http://www.quanjing.com/imgbuy/488-0145.html";
    List<String> inmUrl;

    @AfterViews
    protected void afterViews() {
        ImmersedStatusbarUtils.initAfterSetContentView(this, toolbar);
        setSupportActionBar(toolbar);
        //设置是否有返回箭头
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setTitle("用户登录");
        getHtmlStr(url);
    }

    private void getHtmlStr(String url) {
        //创建okHttpClient对象
        OkHttpClient mOkHttpClient = new OkHttpClient();
        //创建一个Request
        final Request request = new Request.Builder()
                .url(url)
                .build();
        mOkHttpClient.newCall(request).enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {

            }
            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String str = response.body().string();
                Log.e("ingurl", "网页源码:" + str);
                System.out.print(str);
                inmUrl = getImgStr(str);
                for (String url : inmUrl) {
                    Log.e("ingurl", "url:" + url);
                }
            }
        });
    }

    int i = 0;

    /**
     * 点击登录按钮
     */
    @Click(R.id.login_button)
    void loginClick() {
        //setHeadPortrait("http://img1.3lian.com/img2008/06/019/ych.jpg");
        setHeadPortrait(inmUrl.get(i % inmUrl.size()));
        i++;
    }

    /**
     * 设置头像
     */
    private void setHeadPortrait(String url) {
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
        switch (item.getItemId()) {
            case android.R.id.home:
                finish();
                break;
            default:
                break;
        }
        return super.onOptionsItemSelected(item);
    }

    public static List<String> getImgStr(String htmlStr) {
        String img = "";
        Pattern p_image;
        Matcher m_image;
        List<String> pics = new ArrayList<String>();

        String regEx_img = "<img.*src=(.*?)[^>]*?>"; //图片链接地址
        p_image = Pattern.compile
                (regEx_img, Pattern.CASE_INSENSITIVE);
        m_image = p_image.matcher(htmlStr);
        while (m_image.find()) {
            img = img + "," + m_image.group();
            Matcher m = Pattern.compile("src=\"?(.*?)(\"|>|\\s+)").matcher(img); //匹配src
            while (m.find()) {
                pics.add(m.group(1));
            }
        }
        return pics;
    }
}