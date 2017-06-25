package com.fjsd.yyd.whiteboard.util;

import android.content.Context;

/**
 * AppContextUtil
 *
 */
public class AppContextUtil {

    private static Context sContext;
    //初始化上下文
    public static void init(Context ctx) {
        sContext = ctx.getApplicationContext();
    }
    //获取上下文
    public static Context getContext() {
        return sContext;
    }
    //获取ID对应的颜色
    public static int getColor(int resId) {
        if (sContext == null) {
            return -1;
        }
        return sContext.getResources().getColor(resId);
    }
    //获取ID对应的String
    public static String getString(int resId) {
        if (sContext == null) {
            return null;
        }
        return sContext.getResources().getString(resId);
    }

    public static String getString(int resId, Object... objs) {
        if (sContext == null) {
            return null;
        }
        return sContext.getResources().getString(resId, objs);
    }

    /**
     * dip转px
     */
    public static int dip2px(float dpValue) {
        final float scale = AppContextUtil.getContext().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }
}
