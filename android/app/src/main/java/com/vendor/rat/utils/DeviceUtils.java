package com.vendor.rat.utils;

import android.os.Build;

/**
 * 设备工具类 (模块 03)
 *
 * 检测设备品牌，用于选择对应的厂商适配引擎
 */
public class DeviceUtils {

    public static boolean isXiaomi() {
        return "xiaomi".equalsIgnoreCase(Build.MANUFACTURER)
            || "redmi".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isHuawei() {
        return "huawei".equalsIgnoreCase(Build.MANUFACTURER)
            || "honor".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isOppo() {
        return "oppo".equalsIgnoreCase(Build.MANUFACTURER)
            || "realme".equalsIgnoreCase(Build.MANUFACTURER)
            || "oneplus".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isVivo() {
        return "vivo".equalsIgnoreCase(Build.MANUFACTURER)
            || "iqoo".equalsIgnoreCase(Build.MANUFACTURER);
    }

    public static boolean isSamsung() {
        return "samsung".equalsIgnoreCase(Build.MANUFACTURER);
    }

    /**
     * 获取品牌名称
     */
    public static String getBrandName() {
        if (isXiaomi()) return "Xiaomi";
        if (isHuawei()) return "Huawei";
        if (isOppo()) return "OPPO";
        if (isVivo()) return "vivo";
        if (isSamsung()) return "Samsung";
        return "Unknown (" + Build.MANUFACTURER + ")";
    }
}
