package com.zyt.shine.utils;

import android.content.Context;
import android.util.TypedValue;

/**
 * Created by zyt on 2015/11/24.
 * Java代码获取xml中定义的dp\sp值的方法
 */
public class ResourceUtils {
    private static TypedValue mTmpValue = new TypedValue();

    private ResourceUtils() {
    }

    public static int getXmlDef(Context context, int id) {
        synchronized (mTmpValue) {
            TypedValue value = mTmpValue;
            context.getResources().getValue(id, value, true);
            return (int) TypedValue.complexToFloat(value.data);
        }
    }
}
