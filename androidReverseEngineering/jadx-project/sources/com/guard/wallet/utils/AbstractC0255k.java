package com.guard.wallet.utils;

import a0.AbstractC0004d;
import a1.AbstractC0026q;
import android.os.Build;
import android.os.Looper;
import android.provider.Settings;
import android.util.Log;
import android.view.AttachedSurfaceControl;
import android.view.SurfaceControl;
import android.view.View;
import java.lang.reflect.Field;
import p005h.C0318e;

/* renamed from: com.guard.wallet.utils.k */
/* loaded from: classes.dex */
public abstract class AbstractC0255k {
    /* renamed from: a */
    public static boolean m727a() {
        return Looper.getMainLooper() == Looper.myLooper();
    }

    /* renamed from: b */
    public static void m728b(View view) {
        AttachedSurfaceControl rootSurfaceControl;
        try {
            if (Build.VERSION.SDK_INT >= 31) {
                Field declaredField = Class.forName("android.view.ViewRootImpl").getDeclaredField("mSurfaceControl");
                declaredField.setAccessible(true);
                rootSurfaceControl = view.getRootSurfaceControl();
                SurfaceControl surfaceControl = (SurfaceControl) declaredField.get(rootSurfaceControl);
                if (surfaceControl != null) {
                    AbstractC0004d.m53v();
                    Object invoke = SurfaceControl.Transaction.class.getDeclaredMethod("setSkipScreenshot", SurfaceControl.class, Boolean.TYPE).invoke(AbstractC0004d.m41j(), surfaceControl, Boolean.TRUE);
                    if (invoke != null) {
                        ((SurfaceControl.Transaction) invoke).apply();
                    }
                }
            } else {
                Log.d("WindowUtils", "Android11 and lower not support skipScreenshot");
            }
        } catch (Exception e2) {
            AbstractC0026q.m186s("WindowUtils", e2);
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0042 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0043  */
    /* renamed from: c */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m729c(int i2) {
        boolean z2;
        if (i2 < 0) {
            return false;
        }
        try {
        } catch (Exception e2) {
            AbstractC0026q.m186s("ApplicationUtil", e2);
        }
        if (AbstractC0251g.m653Z() != null && (Settings.System.canWrite(AbstractC0251g.m653Z()) || AbstractC0251g.m663j())) {
            Log.d("ApplicationUtil", "已有系统设置修改权限");
            Settings.System.putInt(AbstractC0251g.m653Z().getContentResolver(), "screen_brightness", i2);
            if (AbstractC0251g.O0() == i2) {
                Log.d("ApplicationUtil", "已有系统设置修改权限,调整屏幕亮度成功");
                z2 = true;
                if (!z2) {
                    return true;
                }
                if (C0318e.m844S() == null || !C0318e.m844S().mo302D()) {
                    return false;
                }
                return C0318e.m844S().m855N("settings put system screen_brightness ".concat(String.valueOf(i2))) && AbstractC0251g.O0() == i2;
            }
        }
        z2 = false;
        if (!z2) {
        }
    }
}
