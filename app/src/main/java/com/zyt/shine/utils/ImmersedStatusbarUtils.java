package com.zyt.shine.utils;

import android.annotation.TargetApi;
import android.app.Activity;
import android.os.Build;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.readystatesoftware.systembartint.SystemBarTintManager;
import com.zyt.shine.R;

/**
 * 改变状态栏颜色
 * Created by zyt on 2016/2/26.
 */
public class ImmersedStatusbarUtils {
    /**
     * 在{@link Activity#setContentView}之后调用
     *
     * @param activity       要实现的沉浸式状态栏的Activity
     * @param titleViewGroup 头部控件的ViewGroup,若为null,整个界面将和状态栏重叠
     */

    @TargetApi(Build.VERSION_CODES.KITKAT)
    public static void initAfterSetContentView(Activity activity, View titleViewGroup) {
        if (activity == null)
            return;
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
            Window window = activity.getWindow();
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS);
            window.addFlags(WindowManager.LayoutParams.FLAG_TRANSLUCENT_NAVIGATION);
        }
        if (titleViewGroup == null)
            return;
       SystemBarTintManager tintManager;
        tintManager = new SystemBarTintManager(activity);
        //int color = Color.parseColor(titleViewGroup.getTag().toString());
       // ContextCompat.getColor(activity, R.color.colorAccent)
        tintManager.setStatusBarTintColor(ContextCompat.getColor(activity, R.color.colorPrimaryDark));
        tintManager.setStatusBarTintEnabled(true);
    }
}
