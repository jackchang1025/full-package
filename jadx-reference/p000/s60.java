package p000;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Build;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public abstract class s60 {

    /* renamed from: a0 */
    public static final List f59870a0 = AbstractC0716jf.m213306g5("xiaomi", "redmi", "poco");

    /* renamed from: a1 */
    public static final List f59871a1 = AbstractC1117qo.m214451e7("huawei");

    /* renamed from: a2 */
    public static final List f59872a2 = AbstractC0716jf.m213306g5("honor", "hihonor");

    /* renamed from: a3 */
    public static final List f59873a3 = AbstractC1117qo.m214451e7("oppo");

    /* renamed from: a4 */
    public static final List f59874a4 = AbstractC1117qo.m214451e7("oneplus");

    /* renamed from: a5 */
    public static final List f59875a5 = AbstractC1117qo.m214451e7("realme");

    /* renamed from: a6 */
    public static final List f59876a6 = AbstractC0716jf.m213306g5("vivo", "iqoo");

    /* renamed from: a7 */
    public static final List f59877a7 = AbstractC1117qo.m214451e7("samsung");

    /* renamed from: a8 */
    public static final List f59878a8 = AbstractC1117qo.m214451e7("nokia");

    /* renamed from: a9 */
    public static final List f59879a9 = AbstractC1117qo.m214451e7("asus");

    /* renamed from: b0 */
    public static final List f59880b0 = AbstractC1117qo.m214451e7("meizu");

    /* renamed from: b1 */
    public static final List f59881b1 = AbstractC0716jf.m213306g5("letv", "leeco");

    /* renamed from: b2 */
    public static final List f59882b2 = AbstractC0716jf.m213306g5("tecno", "infinix", "itel");

    /* renamed from: b3 */
    public static final String f59883b3 = StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=");

    /* renamed from: b4 */
    public static final String f59884b4 = StringUtil.m212470a0("KFYcdEAxGScZISROFChGPQk+UiM=");

    /* renamed from: b5 */
    public static final String f59885b5 = StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo");

    /* renamed from: b6 */
    public static final List f59886b6 = AbstractC0716jf.m213306g5("com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity", StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQoAzccOl48IkMUdF0qAy1SIjgXIShCLAktQxAoTRgsRCwV"), "com.huawei.systemmanager.optimize.bootstart.BootStartActivity", "com.huawei.systemmanager.startupmgr.ui.StartupAwakedAppListActivity", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity");

    /* renamed from: b7 */
    public static final String f59887b7 = StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQoAzccOl48IkMUdF0qAy1SIjgXIShCLAktQxAoTRgsRCwV");

    /* renamed from: b8 */
    public static final List f59888b8;

    /* renamed from: b9 */
    public static final List f59889b9;

    /* renamed from: c0 */
    public static final List f59890c0;

    /* renamed from: c1 */
    public static final List f59891c1;

    /* renamed from: c2 */
    public static final List f59892c2;

    static {
        String strM212470a0 = StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw==");
        f59888b8 = AbstractC0716jf.m213306g5(new Pair(strM212470a0, "com.coloros.safecenter.permission.startup.StartupAppListActivity"), new Pair(StringUtil.m212470a0("KFYcdEIoHCEZIipfFA=="), "com.oppo.safe.permission.startup.StartupAppListActivity"), new Pair(strM212470a0, "com.coloros.safecenter.startupapp.StartupAppListActivity"), new Pair("com.coloros.oppoguardelf", "com.coloros.powermanager.fuelgaue.PowerUsageModelActivity"));
        f59889b9 = AbstractC0716jf.m213306g5(new Pair("com.oneplus.security", "com.oneplus.security.chainlaunch.view.ChainLaunchAppListActivity"), new Pair("com.oplus.securitypermission", "com.oplus.securitypermission.startup.StartupAppListActivity"), new Pair("com.oneplus.security", "com.oneplus.security.startupapp.StartupAppListActivity"), new Pair("com.oplus.battery", "com.oplus.powermanager.fuelgaue.PowerControlActivity"));
        String strM212470a02 = StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI");
        String strM212470a03 = StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=");
        f59890c0 = AbstractC0716jf.m213306g5(new Pair(strM212470a02, "com.iqoo.secure.ui.phoneoptimize.AddWhiteListActivity"), new Pair(strM212470a03, "com.vivo.permissionmanager.activity.BgStartUpManagerActivity"), new Pair(strM212470a02, "com.iqoo.secure.ui.phoneoptimize.BgStartUpManager"), new Pair("com.iqoo.powersaving", "com.iqoo.powersaving.PowerSavingManagerActivity"), new Pair(StringUtil.m212470a0("KFYcdFsxGiEZMClc"), "com.vivo.applicationbehaviorengine.ui.ExcessivePowerManager"), new Pair(strM212470a03, "com.vivo.permissionmanager.activity.PurviewTabActivity"));
        f59891c1 = AbstractC0716jf.m213306g5(new Pair("com.samsung.android.lool", "com.samsung.android.sm.ui.battery.BatteryActivity"), new Pair("com.samsung.android.lool", "com.samsung.android.sm.battery.ui.usage.CheckableAppListActivity"), new Pair("com.samsung.android.lool", "com.samsung.android.sm.battery.ui.BatteryActivity"));
        f59892c2 = AbstractC0716jf.m213306g5(new Pair("com.asus.mobilemanager", "com.asus.mobilemanager.powersaver.PowerSaverSettings"), new Pair("com.asus.mobilemanager", "com.asus.mobilemanager.autostart.AutoStartActivity"));
    }

    /* renamed from: a0 */
    public static String m214562a0() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        Locale locale = Locale.ROOT;
        t60.m214694b5(locale, "ROOT");
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        String str2 = Build.MANUFACTURER;
        t60.m214694b5(str2, "MANUFACTURER");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(locale)");
        List<String> list = f59870a0;
        if (list == null || !list.isEmpty()) {
            for (String str3 : list) {
                if (lowerCase.equals(str3) || lowerCase2.equals(str3)) {
                    return "xiaomi";
                }
            }
        }
        List<String> list2 = f59871a1;
        if (list2 == null || !list2.isEmpty()) {
            for (String str4 : list2) {
                if (lowerCase.equals(str4) || lowerCase2.equals(str4)) {
                    return "huawei";
                }
            }
        }
        List<String> list3 = f59872a2;
        if (list3 == null || !list3.isEmpty()) {
            for (String str5 : list3) {
                if (lowerCase.equals(str5) || lowerCase2.equals(str5)) {
                    return "honor";
                }
            }
        }
        List<String> list4 = f59874a4;
        if (list4 == null || !list4.isEmpty()) {
            for (String str6 : list4) {
                if (lowerCase.equals(str6) || lowerCase2.equals(str6)) {
                    return "oneplus";
                }
            }
        }
        List<String> list5 = f59875a5;
        if (list5 == null || !list5.isEmpty()) {
            for (String str7 : list5) {
                if (lowerCase.equals(str7) || lowerCase2.equals(str7)) {
                    return "realme";
                }
            }
        }
        List<String> list6 = f59873a3;
        if (list6 == null || !list6.isEmpty()) {
            for (String str8 : list6) {
                if (lowerCase.equals(str8) || lowerCase2.equals(str8)) {
                    return "oppo";
                }
            }
        }
        List<String> list7 = f59876a6;
        if (list7 == null || !list7.isEmpty()) {
            for (String str9 : list7) {
                if (lowerCase.equals(str9) || lowerCase2.equals(str9)) {
                    return "vivo";
                }
            }
        }
        List<String> list8 = f59877a7;
        if (list8 == null || !list8.isEmpty()) {
            for (String str10 : list8) {
                if (lowerCase.equals(str10) || lowerCase2.equals(str10)) {
                    return "samsung";
                }
            }
        }
        List<String> list9 = f59878a8;
        if (list9 == null || !list9.isEmpty()) {
            for (String str11 : list9) {
                if (lowerCase.equals(str11) || lowerCase2.equals(str11)) {
                    return "nokia";
                }
            }
        }
        List<String> list10 = f59879a9;
        if (list10 == null || !list10.isEmpty()) {
            for (String str12 : list10) {
                if (lowerCase.equals(str12) || lowerCase2.equals(str12)) {
                    return "asus";
                }
            }
        }
        List<String> list11 = f59880b0;
        if (list11 == null || !list11.isEmpty()) {
            for (String str13 : list11) {
                if (lowerCase.equals(str13) || lowerCase2.equals(str13)) {
                    return "meizu";
                }
            }
        }
        List<String> list12 = f59881b1;
        if (list12 == null || !list12.isEmpty()) {
            for (String str14 : list12) {
                if (lowerCase.equals(str14) || lowerCase2.equals(str14)) {
                    return "letv";
                }
            }
        }
        List<String> list13 = f59882b2;
        if (list13 != null && list13.isEmpty()) {
            return "generic";
        }
        for (String str15 : list13) {
            if (lowerCase.equals(str15) || lowerCase2.equals(str15)) {
                return "tecno";
            }
        }
        return "generic";
    }

    /* renamed from: a1 */
    public static boolean m214563a1(Context context, String str) throws PackageManager.NameNotFoundException {
        try {
            context.getPackageManager().getPackageInfo(str, 1);
            return true;
        } catch (PackageManager.NameNotFoundException unused) {
            return false;
        }
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:101:0x0197, code lost:
    
        return m214566a4(r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x011e, code lost:
    
        if (r0.equals("oppo") == false) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:98:0x0190, code lost:
    
        if (r0.equals("realme") == false) goto L109;
     */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m214564a2(Context context) {
        t60.m214695b6(context, "context");
        String strM214562a0 = m214562a0();
        String str = Build.BRAND;
        String str2 = Build.MANUFACTURER;
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🌍 [国际适配] 品牌: ", strM214562a0, " (", str, "/");
        sbM41c2.append(str2);
        sbM41c2.append(")");
        t60.m214714d6("IntlBrandAdapter", sbM41c2.toString());
        switch (strM214562a0.hashCode()) {
            case -1320380160:
                if (strM214562a0.equals("oneplus")) {
                    for (Pair pair : f59889b9) {
                        String str3 = (String) pair.f57556a0;
                        String str4 = (String) pair.f57557a1;
                        if (!m214563a1(context, str3) || !m214567a5(context, str3, str4)) {
                        }
                    }
                    try {
                        Intent intent = new Intent();
                        intent.setAction("com.android.settings.action.BACKGROUND_OPTIMIZE");
                        intent.addFlags(276824064);
                        context.startActivity(intent);
                        t60.m214714d6("IntlBrandAdapter", "🌍 ✅ 成功打开Action: com.android.settings.action.BACKGROUND_OPTIMIZE");
                        return true;
                    } catch (Exception e) {
                        t60.m214702c3("IntlBrandAdapter", "🌍 ❌ 无法打开Action: com.android.settings.action.BACKGROUND_OPTIMIZE (" + e.getMessage() + ")");
                        return m214566a4(context);
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case -1206476313:
                if (strM214562a0.equals("huawei")) {
                    return m214565a3(context);
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case -934971466:
                break;
            case -759499589:
                if (strM214562a0.equals("xiaomi")) {
                    String str5 = f59883b3;
                    if (!m214563a1(context, str5) || !m214567a5(context, str5, "com.miui.permcenter.autostart.AutoStartManagementActivity")) {
                        String str6 = f59884b4;
                        if (!m214563a1(context, str6) || !m214567a5(context, str6, "com.miui.powerkeeper.ui.HiddenAppsConfigActivity")) {
                            t60.m214726f4("IntlBrandAdapter", "🌍 [小米] 所有自启动页面均无法打开");
                            return false;
                        }
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 3003984:
                if (strM214562a0.equals("asus")) {
                    if (m214563a1(context, "com.asus.mobilemanager")) {
                        for (Pair pair2 : f59892c2) {
                            if (m214567a5(context, (String) pair2.f57556a0, (String) pair2.f57557a1)) {
                            }
                        }
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 3418016:
                break;
            case 3620012:
                if (strM214562a0.equals("vivo")) {
                    for (Pair pair3 : f59890c0) {
                        String str7 = (String) pair3.f57556a0;
                        String str8 = (String) pair3.f57557a1;
                        if (!m214563a1(context, str7) || !m214567a5(context, str7, str8)) {
                        }
                    }
                    t60.m214726f4("IntlBrandAdapter", "🌍 [VIVO] 所有自启动页面均无法打开");
                    return false;
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 99462250:
                if (strM214562a0.equals("honor")) {
                    String str9 = f59885b5;
                    if (m214563a1(context, str9)) {
                        if (!m214567a5(context, str9, f59887b7)) {
                            return m214565a3(context);
                        }
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 103777484:
                if (strM214562a0.equals("meizu")) {
                    if (m214563a1(context, "com.meizu.safe")) {
                        return m214567a5(context, "com.meizu.safe", "com.meizu.safe.permission.SmartBGActivity");
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 105000290:
                if (strM214562a0.equals("nokia")) {
                    if (m214563a1(context, "com.evenwell.powersaving.g3")) {
                        return m214567a5(context, "com.evenwell.powersaving.g3", "com.evenwell.powersaving.g3.exception.PowerSaverExceptionActivity");
                    }
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 110235987:
                if (strM214562a0.equals("tecno")) {
                    return m214563a1(context, "com.transsion.phonemanager") && m214567a5(context, "com.transsion.phonemanager", "com.transsion.phonemanager.module.autostart.AutoStartActivity");
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            case 1864941562:
                if (strM214562a0.equals("samsung")) {
                    for (Pair pair4 : f59891c1) {
                        String str10 = (String) pair4.f57556a0;
                        String str11 = (String) pair4.f57557a1;
                        if (!m214563a1(context, str10) || !m214567a5(context, str10, str11)) {
                        }
                    }
                    t60.m214726f4("IntlBrandAdapter", "🌍 [三星] 所有电池优化页面均无法打开");
                    return false;
                }
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
            default:
                t60.m214714d6("IntlBrandAdapter", "🌍 [国际适配] " + strM214562a0 + " 无需品牌专属自启动适配");
                return false;
        }
    }

    /* renamed from: a3 */
    public static boolean m214565a3(Context context) {
        String str = f59885b5;
        if (!m214563a1(context, str)) {
            return false;
        }
        Iterator it = f59886b6.iterator();
        while (it.hasNext()) {
            if (m214567a5(context, str, (String) it.next())) {
                return true;
            }
        }
        t60.m214726f4("IntlBrandAdapter", "🌍 [华为] 所有自启动页面均无法打开");
        return false;
    }

    /* renamed from: a4 */
    public static boolean m214566a4(Context context) {
        for (Pair pair : f59888b8) {
            String str = (String) pair.f57556a0;
            String str2 = (String) pair.f57557a1;
            if (m214563a1(context, str) && m214567a5(context, str, str2)) {
                return true;
            }
        }
        t60.m214726f4("IntlBrandAdapter", "🌍 [OPPO] 所有自启动页面均无法打开");
        return false;
    }

    /* renamed from: a5 */
    public static boolean m214567a5(Context context, String str, String str2) {
        try {
            Intent intent = new Intent();
            intent.setComponent(new ComponentName(str, str2));
            intent.addFlags(276824064);
            context.startActivity(intent);
            t60.m214714d6("IntlBrandAdapter", "🌍 ✅ 成功打开: " + str + "/" + str2);
            return true;
        } catch (Exception e) {
            t60.m214702c3("IntlBrandAdapter", AbstractC0003a2.m34b5("🌍 ❌ 无法打开: ", str2, " (", e.getMessage(), ")"));
            return false;
        }
    }
}
