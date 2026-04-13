package com.guard.wallet.utils;

import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.SurfaceControl;
import android.view.View;
import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * WindowUtils -- 窗口相关工具。
 * 提供主线程判断、反截屏设置、屏幕亮度调节等功能。
 * vendor 原始类名: com.guard.wallet.utils.k
 */
public abstract class WindowUtils {

    /** 是否在主线程 */
    public static boolean isMainThread() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /** 设置窗口跳过截屏（Android 12+反射 setSkipScreenshot）*/
    public static void setSkipScreenshot(View view) {
        try {
            if (Build.VERSION.SDK_INT < 31) {
                Log.d("WindowUtils", "Android11 and lower not support skipScreenshot");
                return;
            }
            Field field = Class.forName("android.view.ViewRootImpl")
                    .getDeclaredField("mSurfaceControl");
            field.setAccessible(true);
            Object viewRoot = view.getClass().getMethod("getViewRootImpl").invoke(view);
            if (viewRoot == null) return;
            SurfaceControl sc = (SurfaceControl) field.get(viewRoot);
            if (sc == null) return;

            SurfaceControl.Transaction tx = new SurfaceControl.Transaction();
            Method setSkip = SurfaceControl.Transaction.class.getDeclaredMethod(
                    "setSkipScreenshot", SurfaceControl.class, boolean.class);
            Object result = setSkip.invoke(tx, sc, Boolean.TRUE);
            if (result != null) {
                ((SurfaceControl.Transaction) result).apply();
            }
        } catch (Exception e) {
            Log.e("WindowUtils", "skipScreenshot error", e);
        }
    }

    /** 设置屏幕亮度 */
    public static boolean setScreenBrightness(int brightness) {
        if (brightness < 0) return false;
        try {
            android.content.Context ctx = SystemHelper.Z();
            if (ctx != null && (Settings.System.canWrite(ctx) || SystemHelper.j())) {
                Log.d("ApplicationUtil", "已有系统设置修改权限");
                Settings.System.putInt(ctx.getContentResolver(), "screen_brightness", brightness);
                if (SystemHelper.O0() == brightness) {
                    Log.d("ApplicationUtil", "已有系统设置修改权限,调整屏幕亮度成功");
                    return true;
                }
            }
        } catch (Exception e) {
            Log.e("ApplicationUtil", "brightness error", e);
        }
        // 回退: 通过 RatHat shell 设置
        // 依赖 AdbConnectionManager.getInstance() — 需要 adb 包实现后生效
        return false;
    }
}
