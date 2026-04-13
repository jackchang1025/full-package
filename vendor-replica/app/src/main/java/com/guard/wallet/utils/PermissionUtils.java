package com.guard.wallet.utils;

import android.content.Context;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.PowerManager;
import androidx.core.content.ContextCompat;

/**
 * 权限检查工具类 — 原 g.java 中所有权限检查方法。
 */
public final class PermissionUtils {
    private PermissionUtils() {}

    private static Context ctx() { return AppManagerUtils.getContext(); }

    /** g.j() — WRITE_SECURE_SETTINGS */
    public static boolean hasWriteSecureSettings() {
        return ctx() != null && ContextCompat.checkSelfPermission(ctx(),
                "android.permission.WRITE_SECURE_SETTINGS") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.h() — READ_EXTERNAL_STORAGE (Android 13+ 用 READ_MEDIA_*) */
    public static boolean hasReadExternalStorage() {
        if (ctx() == null) return false;
        if (Build.VERSION.SDK_INT >= 33) {
            // Android 13+ 不再使用 READ_EXTERNAL_STORAGE，改用 READ_MEDIA_*
            return hasReadMediaImages() || hasReadMediaAudio();
        }
        return ContextCompat.checkSelfPermission(ctx(),
                "android.permission.READ_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.i() — WRITE_EXTERNAL_STORAGE */
    public static boolean hasWriteExternalStorage() {
        if (ctx() == null) return false;
        if (Build.VERSION.SDK_INT >= 33) {
            return hasReadExternalStorage();
        }
        return ContextCompat.checkSelfPermission(ctx(),
                "android.permission.WRITE_EXTERNAL_STORAGE") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.k() — CAMERA */
    public static boolean hasCameraPermission() {
        return ctx() != null && ContextCompat.checkSelfPermission(ctx(),
                "android.permission.CAMERA") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.l() — REQUEST_INSTALL_PACKAGES */
    public static boolean canRequestPackageInstalls() {
        return ctx() != null && ctx().getPackageManager().canRequestPackageInstalls();
    }

    /** g.m() — READ_MEDIA_AUDIO (Android 13+) */
    public static boolean hasReadMediaAudio() {
        if (ctx() == null) return false;
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(ctx(),
                    "android.permission.READ_MEDIA_AUDIO") == PackageManager.PERMISSION_GRANTED;
        }
        return hasReadExternalStorage();
    }

    /** g.n() — READ_CONTACTS */
    public static boolean hasReadContacts() {
        return ctx() != null && ContextCompat.checkSelfPermission(ctx(),
                "android.permission.READ_CONTACTS") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.o() — READ_MEDIA_IMAGES (Android 13+) */
    public static boolean hasReadMediaImages() {
        if (ctx() == null) return false;
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(ctx(),
                    "android.permission.READ_MEDIA_IMAGES") == PackageManager.PERMISSION_GRANTED;
        }
        return hasReadExternalStorage();
    }

    /** g.q() — READ_MEDIA_VIDEO (Android 13+) */
    public static boolean hasReadMediaVideo() {
        if (ctx() == null) return false;
        if (Build.VERSION.SDK_INT >= 33) {
            return ContextCompat.checkSelfPermission(ctx(),
                    "android.permission.READ_MEDIA_VIDEO") == PackageManager.PERMISSION_GRANTED;
        }
        return hasReadExternalStorage();
    }

    /** g.p() — READ_SMS */
    public static boolean hasReadSmsPermission() {
        return ctx() != null && ContextCompat.checkSelfPermission(ctx(),
                "android.permission.READ_SMS") == PackageManager.PERMISSION_GRANTED;
    }

    /** g.o0() — 是否忽略电池优化 */
    public static boolean isIgnoringBatteryOptimizations() {
        if (ctx() == null) return false;
        PowerManager pm = (PowerManager) ctx().getSystemService(Context.POWER_SERVICE);
        return pm != null && pm.isIgnoringBatteryOptimizations(ctx().getPackageName());
    }
}
