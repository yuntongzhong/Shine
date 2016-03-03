package com.zyt.shine.ui.activity;

import android.content.Intent;
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

import com.zyt.shine.glide.GlideCircleTransform;
import com.zyt.shine.ui.fragment.Navigation;
import com.zyt.shine.utils.ImmersedStatusbarUtils;

public class MainActivity extends AppCompatActivity
        implements NavigationView.OnNavigationItemSelectedListener {

    private FragmentTabHost mFragmentTabHost;

    ImageView icon;
    DrawerLayout drawerLayout;
    MenuItem preMenuItem = null;//用于辨别此前是否已有选中条目

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
        // 初始化布局元素
        initViews();
        setHeadPortrait("http://img1.3lian.com/img2008/06/019/ych.jpg");
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

        NavigationView navigationView = (NavigationView) findViewById(R.id.nav_view);
        //navigationView.getMenu().getItem(0).setCheckable(true);
        navigationView.setCheckedItem(R.id.nav_camera);
        //设置头像的点击事件
        icon = (ImageView) navigationView.getHeaderView(0).findViewById(R.id.head_portrait);
        icon.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, LoginActivity_.class);
                startActivity(intent);
            }
        });
        //设置侧滑菜单的监听事件
        navigationView.setNavigationItemSelectedListener(this);

        mFragmentTabHost = (FragmentTabHost) findViewById(android.R.id.tabhost);
        mFragmentTabHost.setup(getApplicationContext(), getSupportFragmentManager(), R.id.realtabcontent);
        Navigation[] navigations = Navigation.values();

        //初始化导航栏
        for (int i = 0; i < navigations.length; i++) {
            TabHost.TabSpec tabSpec = mFragmentTabHost.newTabSpec(navigations[i].getName());
            tabSpec.setIndicator(getNavigationView(navigations[i]));
            mFragmentTabHost.addTab(tabSpec, navigations[i].getClz(), null);
        }
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
        // Handle navigation view item clicks here.

        //  headImage.setOnClickListener(this);
        //首先将选中条目变为选中状态 即checked为true,后关闭Drawer，以前选中的Item需要变为未选中状态
//        if (preMenuItem != null)
//            preMenuItem.setChecked(false);
        item.setChecked(true);
        // drawerLayout.closeDrawers();
        // preMenuItem = item;
        int id = item.getItemId();

        if (id == R.id.nav_camera) {
            // Handle the camera action
            // setTabSelection(0);
        } else if (id == R.id.nav_gallery) {

        } else if (id == R.id.nav_slideshow) {

        } else if (id == R.id.nav_manage) {

        } else if (id == R.id.nav_share) {

        } else if (id == R.id.nav_send) {

        }
        drawerLayout.closeDrawer(GravityCompat.START);
        return true;
    }

}
