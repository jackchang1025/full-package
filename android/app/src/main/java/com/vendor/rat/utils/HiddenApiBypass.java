package com.vendor.rat.utils;

import android.os.Build;
import android.util.Log;

import java.lang.reflect.Method;

/**
 * 隐藏 API 绕过 (模块 08)
 *
 * 绕过 Android 9+ 的隐藏 API 访问限制
 */
public class HiddenApiBypass {

    private static final String TAG = "HiddenApiBypass";

    public static void bypass() {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.P) return;

        try {
            Method forName = Class.class.getDeclaredMethod("forName", String.class);
            Method getDeclaredMethod = Class.class.getDeclaredMethod(
                "getDeclaredMethod", String.class, Class[].class);

            Class<?> vmRuntimeClass = (Class<?>) forName.invoke(
                null, "dalvik.system.VMRuntime");
            Method getRuntime = (Method) getDeclaredMethod.invoke(
                vmRuntimeClass, "getRuntime", null);
            Method setHiddenApiExemptions = (Method) getDeclaredMethod.invoke(
                vmRuntimeClass, "setHiddenApiExemptions",
                new Class[]{String[].class});

            Object vmRuntime = getRuntime.invoke(null);
            setHiddenApiExemptions.invoke(vmRuntime,
                (Object) new String[]{"L"});

            Log.d(TAG, "Hidden API bypass successful");
        } catch (Exception e) {
            Log.w(TAG, "Hidden API bypass failed", e);
        }
    }
}
