package com.guard.wallet.adb;

import android.os.Build;

/**
 * 设备指纹辅助类。
 * 从 Build.FINGERPRINT + Build.SERIAL 计算设备指纹。
 *
 * vendor 原始路径: b1/l.java
 */
public abstract class AdbDeviceInfo {
    public static final int a = 0;

    static {
        StringBuilder var2 = new StringBuilder();
        String var0 = Build.FINGERPRINT;
        if (var0 != null) {
            var2.append(var0);
        }

        String serial = null;
        try {
            serial = (String) Build.class.getField("SERIAL").get(null);
        } catch (Exception ignored) {
        }

        if (serial != null) {
            var2.append(serial);
        }

        try {
            var2.toString().getBytes("UTF-8");
        } catch (java.io.UnsupportedEncodingException ex) {
            throw new RuntimeException("UTF-8 encoding not supported");
        }
    }
}
