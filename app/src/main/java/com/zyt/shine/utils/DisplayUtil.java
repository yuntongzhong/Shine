package com.zyt.shine.utils;

/**
 * Created by zyt on 2015/11/24.
 */
import android.content.Context;

/**
 * ResourceUtils.getXmlDef(context, R.dimen.test_dp) 搞定，事情变的简单了~
 * 根据id不同，如果id对应的是dp的，那函数返回的就是dp值，如果id对应的是sp的，那函数返回的就是sp值，一个函数入口就行了~
 * 这里有个android的小的优化点值得学习
 * ，就是mTmpValue临时变量的设计。为了不在每一个getResources().getXXX()函数里都new
 * TypedValue()，使用一个mTmpValue临时变量进行缓存重复使用，并加上同步保护，减少了内存占用，可见google为了优化内存的用心~
 */
public class DisplayUtil {
    private DisplayUtil() {
    }

    /**
     * 将px值转换为dip或dp值，保证尺寸大小不变
     *
     * @param context
     * @param pxValue
     * @return
     */
    public static int px2dip(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    /**
     * 将dip或dp值转换为px值，保证尺寸大小不变
     *
     * @param dipValue

     * @return
     */
    public static int dip2px(Context context, float dipValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (dipValue * scale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param pxValue

     * @return
     */
    public static int px2sp(Context context, float pxValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (pxValue / fontScale + 0.5f);
    }

    /**
     * 将px值转换为sp值，保证文字大小不变
     *
     * @param spValue

     * @return
     */
    public static int sp2px(Context context, float spValue) {
        final float fontScale = context.getResources().getDisplayMetrics().scaledDensity;
        return (int) (spValue * fontScale + 0.5f);
    }

}

