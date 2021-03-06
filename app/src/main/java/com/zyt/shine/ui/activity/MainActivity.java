package com.zyt.shine.ui.activity;

import android.content.Intent;
import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.support.design.widget.NavigationView;
import android.support.v4.app.FragmentTabHost;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.GravityCompat;
import android.support.v4.widget.DrawerLayout;
import android.support.v7.app.ActionBarDrawerToggle;
import android.support.v7.app.AppCompatActivity;
import android.support.v7.widget.Toolbar;
import android.text.Html;
import android.text.Spannable;
import android.text.SpannableStringBuilder;
import android.text.method.LinkMovementMethod;
import android.text.style.URLSpan;
import android.util.TypedValue;
import android.view.LayoutInflater;
import android.view.Menu;
import android.view.MenuItem;
import android.view.View;
import android.widget.ImageView;
import android.widget.TabHost;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.zyt.shine.R;
import com.zyt.shine.entity.LoginInfoEntity;
import com.zyt.shine.glide.GlideCircleTransform;
import com.zyt.shine.ui.fragment.Navigation;
import com.zyt.shine.ui.view.MyURLSpan;
import com.zyt.shine.ui.view.TwoBtnFragmentDialog;
import com.zyt.shine.utils.ImmersedStatusbarUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {


    private ImageView icon;
    private DrawerLayout drawerLayout;
    private NavigationView navigationView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化布局元素
        initViews();
        //setUserInfo("http://img1.3lian.com/img2008/06/019/ych.jpg");
    }

    private void initViews() {
        //初始化toolbar及状态栏颜色
        Toolbar toolbar = (Toolbar) findViewById(R.id.toolbar);
        ImmersedStatusbarUtils.initAfterSetContentView(this, toolbar);
        setSupportActionBar(toolbar);
        //绑定侧滑菜单及设置开关
        drawerLayout = (DrawerLayout) findViewById(R.id.drawer_layout);
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar, R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.setDrawerListener(toggle);
        toggle.syncState();

        navigationView = (NavigationView) findViewById(R.id.nav_view);
        //默认选中
        navigationView.setCheckedItem(R.id.nav_camera);
        View headerView = navigationView.getHeaderView(0);
        setUserInfo(headerView, new LoginInfoEntity("叶凡", "http://img1.3lian.com/img2008/06/019/ych.jpg", "来自地球的荒古圣体！"));

        //设置侧滑菜单的监听事件
        navigationView.setNavigationItemSelectedListener(this);

        FragmentTabHost mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realtabcontent);
        Navigation[] navigations = Navigation.values();

        //初始化导航栏
        for (int i = 0; i < navigations.length; i++) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(navigations[i].getName());
            tabSpec.setIndicator(getNavigationView(navigations[i]));
            mFragmentTabHost.addTab(tabSpec, navigations[i].getClz(), null);
        }

        //去除底部按钮之间的分割线
        if (android.os.Build.VERSION.SDK_INT > 10) {
            mFragmentTabHost.getTabWidget().setShowDividers(0);
        }
    }

    /**
     * 设置登录信息
     */
    private void setUserInfo(View headerView, LoginInfoEntity loginInfo) {
        //设置头像的点击事件
        icon = (ImageView) headerView.findViewById(R.id.head_portrait);
        TextView userName = (TextView) headerView.findViewById(R.id.user_name);
        TextView personalProfile = (TextView) headerView.findViewById(R.id.personal_profile);


        userName.setText(loginInfo.getUserName());
        personalProfile.setText(loginInfo.getPersonalProfile());

        setText(personalProfile);
        Glide.with(this).load(loginInfo.getUserIcon())
                .centerCrop()
                .transform(new GlideCircleTransform(this))
                .into(icon);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
                    //drawerLayout.closeDrawer(GravityCompat.START);
                    drawerLayout.closeDrawer(navigationView);
                }

                Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
                startActivity(intent);
            }
        });

    }

    private void setText(TextView textView) {
        StringBuilder sb = new StringBuilder();
        sb.append("个人信息");
        sb.append("<a href=address" + ">"
                + "地址</a>");

        sb.append("<a href=name" + ">"
                + "名字</a>");

        textView.setText(Html.fromHtml(sb.toString()));
        textView.setMovementMethod(LinkMovementMethod.getInstance());
        textView.setHighlightColor(Color.TRANSPARENT);//去掉点击后的蓝色背景

        CharSequence text = textView.getText();
        if (text instanceof Spannable) {
            int end = text.length();
            Spannable sp = (Spannable) textView.getText();
            URLSpan[] urls = sp.getSpans(0, end, URLSpan.class);
            SpannableStringBuilder style = new SpannableStringBuilder(text);
            style.clearSpans();// should clear old spans

            // 循环把链接发过去
            for (URLSpan url : urls) {
                MyURLSpan myURLSpan = new MyURLSpan(this, url.getURL());
                style.setSpan(myURLSpan, sp.getSpanStart(url),
                        sp.getSpanEnd(url), Spannable.SPAN_EXCLUSIVE_INCLUSIVE);
            }
            textView.setText(style);
        }

    }

    /**
     * 返回设置好的底部按钮
     *
     * @return
     */
    private View getNavigationView(Navigation navigation) {
        View view = LayoutInflater.from(this).inflate(R.layout.tab_navigation, null);
        TextView indicator = (TextView) view.findViewById(R.id.tab_title);

        indicator.setText(navigation.getName());

        indicator.setTextSize(TypedValue.COMPLEX_UNIT_SP, 10);
        Drawable icon = ContextCompat.getDrawable(this, navigation.getResIcon());
//        自定义ICON大小
//        icon.setBounds(0, 0, 75, 75);
//        indicator.setCompoundDrawables(null,icon,null,null);
        indicator.setCompoundDrawablePadding(3);
        indicator.setCompoundDrawablesWithIntrinsicBounds(null, icon, null, null);
        indicator.setPadding(0, 8, 0, 4);

        return view;
    }

    @Override
    public void onBackPressed() {
        if (drawerLayout.isDrawerOpen(GravityCompat.START)) {
            drawerLayout.closeDrawer(GravityCompat.START);
        } else {
            super.onBackPressed();
        }
    }

    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        // Inflate the menu; this adds items to the action bar if it is present.
        getMenuInflater().inflate(R.menu.main, menu);
        return true;
    }


    @Override
    public boolean onNavigationItemSelected(MenuItem item) {
        item.setChecked(true);
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            // setTabSelection(0);
        } else if (id == R.id.nav_gallery) {
            TwoBtnFragmentDialog dialog=new TwoBtnFragmentDialog();
            dialog.setMsg("login").setTitle("title");
            dialog.show(getFragmentManager(),"test");

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
