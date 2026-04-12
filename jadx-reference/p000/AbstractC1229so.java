package p000;

import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.content.pm.ApplicationInfo;
import android.graphics.Rect;
import android.net.ConnectivityManager;
import android.net.Network;
import android.net.NetworkCapabilities;
import android.os.Build;
import android.provider.Settings;
import android.telephony.SubscriptionInfo;
import android.telephony.SubscriptionManager;
import android.telephony.TelephonyManager;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.WindowManager;
import android.view.WindowMetrics;
import com.storm.safe.rock.util.DeviceUtils$Brand;
import com.storm.safe.rock.util.DeviceUtils$BrandGroup;
import com.storm.safe.rock.util.StringUtil;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.NoWhenBranchMatchedException;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: so */
/* loaded from: classes2.dex */
public abstract class AbstractC1229so {

    /* renamed from: a0 */
    public static final List f60031a0 = AbstractC0716jf.m213306g5("xiaomi", "redmi", "poco");

    /* renamed from: a1 */
    public static final List f60032a1 = AbstractC0716jf.m213306g5("vivo", "iqoo");

    /* renamed from: a2 */
    public static final List f60033a2 = AbstractC1117qo.m214451e7("oppo");

    /* renamed from: a3 */
    public static final List f60034a3 = AbstractC1117qo.m214451e7("huawei");

    /* renamed from: a4 */
    public static final List f60035a4 = AbstractC1117qo.m214451e7("samsung");

    /* renamed from: a5 */
    public static volatile DeviceUtils$Brand f60036a5;

    /* renamed from: a6 */
    public static volatile DeviceUtils$BrandGroup f60037a6;

    static {
        AbstractC1117qo.m214451e7("meizu");
        AbstractC0716jf.m213306g5("lenovo", "zuk");
        AbstractC1117qo.m214451e7("asus");
        AbstractC0716jf.m213306g5("motorola", "moto");
        AbstractC1117qo.m214451e7("google");
    }

    /* JADX WARN: Removed duplicated region for block: B:101:0x01df  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0200  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x020a  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0212  */
    /* JADX WARN: Removed duplicated region for block: B:117:0x021e  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0289  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0093  */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0136  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01cb  */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static DeviceUtils$Brand m214635a0() {
        DeviceUtils$Brand deviceUtils$Brand;
        DeviceUtils$Brand deviceUtils$Brand2;
        DeviceUtils$Brand deviceUtils$Brand3;
        DeviceUtils$Brand deviceUtils$Brand4;
        DeviceUtils$Brand deviceUtils$Brand5;
        DeviceUtils$Brand deviceUtils$Brand6;
        DeviceUtils$Brand deviceUtils$Brand7;
        DeviceUtils$Brand deviceUtils$Brand8;
        boolean z;
        String lowerCase;
        DeviceUtils$Brand deviceUtils$Brand9 = DeviceUtils$Brand.f55218b6;
        DeviceUtils$Brand deviceUtils$Brand10 = DeviceUtils$Brand.f55209a7;
        DeviceUtils$Brand deviceUtils$Brand11 = DeviceUtils$Brand.f55212b0;
        DeviceUtils$Brand deviceUtils$Brand12 = DeviceUtils$Brand.f55211a9;
        DeviceUtils$Brand deviceUtils$Brand13 = DeviceUtils$Brand.f55206a4;
        DeviceUtils$Brand deviceUtils$Brand14 = DeviceUtils$Brand.f55204a2;
        DeviceUtils$Brand deviceUtils$Brand15 = DeviceUtils$Brand.f55205a3;
        DeviceUtils$Brand deviceUtils$Brand16 = DeviceUtils$Brand.f55202a0;
        DeviceUtils$Brand deviceUtils$Brand17 = DeviceUtils$Brand.f55208a6;
        DeviceUtils$Brand deviceUtils$Brand18 = DeviceUtils$Brand.f55207a5;
        DeviceUtils$Brand deviceUtils$Brand19 = f60036a5;
        if (deviceUtils$Brand19 != null) {
            return deviceUtils$Brand19;
        }
        String strM214636a1 = m214636a1();
        String strM214639a4 = m214639a4();
        String strM214641a6 = m214641a6();
        if (m214645b0()) {
            deviceUtils$Brand3 = DeviceUtils$Brand.f55210a8;
            deviceUtils$Brand = deviceUtils$Brand9;
        } else {
            if (strM214636a1.equals("vivo") || AbstractC0779a1.m213652a5(strM214639a4, "vivo", false)) {
                deviceUtils$Brand = deviceUtils$Brand9;
                deviceUtils$Brand2 = deviceUtils$Brand10;
            } else if (strM214636a1.equals("poco") || AbstractC0779a1.m213652a5(strM214641a6, "poco", false)) {
                deviceUtils$Brand = deviceUtils$Brand9;
                deviceUtils$Brand4 = deviceUtils$Brand16;
                deviceUtils$Brand3 = deviceUtils$Brand4;
            } else if (strM214636a1.equals("redmi") || AbstractC0779a1.m213652a5(strM214641a6, "redmi", false)) {
                deviceUtils$Brand = deviceUtils$Brand9;
                deviceUtils$Brand3 = DeviceUtils$Brand.f55203a1;
            } else if (strM214636a1.equals("blackshark") || strM214636a1.equals("black shark") || AbstractC0779a1.m213652a5(strM214641a6, "shark", false)) {
                deviceUtils$Brand = deviceUtils$Brand9;
                deviceUtils$Brand3 = DeviceUtils$Brand.f55213b1;
            } else {
                if (!strM214636a1.equals("xiaomi") && !strM214636a1.equals("mi")) {
                    if (strM214636a1.equals("honor")) {
                        deviceUtils$Brand = deviceUtils$Brand9;
                    } else {
                        deviceUtils$Brand = deviceUtils$Brand9;
                        if (!strM214636a1.equals("hihonor")) {
                            deviceUtils$Brand2 = deviceUtils$Brand10;
                            if (strM214636a1.equals("huawei")) {
                                deviceUtils$Brand5 = deviceUtils$Brand11;
                            } else {
                                deviceUtils$Brand5 = deviceUtils$Brand11;
                                if (!AbstractC0779a1.m213652a5(strM214639a4, "huawei", false)) {
                                    deviceUtils$Brand6 = deviceUtils$Brand12;
                                }
                                if (!strM214636a1.equals("huawei")) {
                                    deviceUtils$Brand3 = deviceUtils$Brand14;
                                } else if (strM214636a1.equals("oneplus") || strM214636a1.equals("one plus")) {
                                    deviceUtils$Brand3 = deviceUtils$Brand18;
                                } else if (strM214636a1.equals("realme") || AbstractC0779a1.m213679d2(strM214641a6, false, "rmx")) {
                                    deviceUtils$Brand3 = deviceUtils$Brand17;
                                } else {
                                    if (strM214636a1.equals("oppo")) {
                                        deviceUtils$Brand7 = deviceUtils$Brand13;
                                    } else {
                                        deviceUtils$Brand7 = deviceUtils$Brand13;
                                        if (!AbstractC0779a1.m213652a5(strM214639a4, "oppo", false)) {
                                            deviceUtils$Brand8 = deviceUtils$Brand14;
                                            deviceUtils$Brand4 = deviceUtils$Brand16;
                                            z = false;
                                        }
                                        if (!strM214636a1.equals("oppo") || AbstractC0779a1.m213652a5(strM214639a4, "oppo", z)) {
                                            lowerCase = strM214641a6.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (!AbstractC0779a1.m213652a5(lowerCase, "realme", z) && !AbstractC0779a1.m213679d2(lowerCase, z, "rmx") && !AbstractC0779a1.m213679d2(lowerCase, z, "rxm")) {
                                                if (!strM214636a1.equals("oppo")) {
                                                    if (!strM214636a1.equals("samsung")) {
                                                        if (!strM214636a1.equals("meizu")) {
                                                            if (strM214636a1.equals("lenovo")) {
                                                                deviceUtils$Brand3 = DeviceUtils$Brand.f55214b2;
                                                            } else if (strM214636a1.equals("zte")) {
                                                                deviceUtils$Brand3 = DeviceUtils$Brand.f55215b3;
                                                            } else if (strM214636a1.equals("nubia")) {
                                                                deviceUtils$Brand3 = DeviceUtils$Brand.f55216b4;
                                                            } else if (strM214636a1.equals("google")) {
                                                                deviceUtils$Brand3 = DeviceUtils$Brand.f55217b5;
                                                            } else if (!AbstractC0779a1.m213652a5(strM214639a4, "xiaomi", false)) {
                                                                if (AbstractC0779a1.m213652a5(strM214639a4, "huawei", false)) {
                                                                    deviceUtils$Brand3 = deviceUtils$Brand8;
                                                                } else if (AbstractC0779a1.m213652a5(strM214639a4, "oppo", false)) {
                                                                    deviceUtils$Brand3 = deviceUtils$Brand7;
                                                                } else if (!AbstractC0779a1.m213652a5(strM214639a4, "vivo", false)) {
                                                                    if (AbstractC0779a1.m213652a5(strM214639a4, "samsung", false)) {
                                                                        deviceUtils$Brand3 = deviceUtils$Brand6;
                                                                    } else if (!AbstractC0779a1.m213652a5(strM214639a4, "oneplus", false)) {
                                                                        if (!AbstractC0779a1.m213652a5(strM214639a4, "realme", false)) {
                                                                            deviceUtils$Brand3 = AbstractC0779a1.m213652a5(strM214639a4, "meizu", false) ? deviceUtils$Brand5 : deviceUtils$Brand;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    String lowerCase2 = strM214641a6.toLowerCase(Locale.ROOT);
                                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    deviceUtils$Brand8 = deviceUtils$Brand14;
                                    if (!AbstractC0779a1.m213652a5(lowerCase2, "oneplus", false)) {
                                        List listM213306g5 = AbstractC0716jf.m213306g5("in2", "le2", "kb2", "hd1", "gm1", "ac2", "ne2", "ph2", "cph");
                                        if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                                            Iterator it = listM213306g5.iterator();
                                            while (it.hasNext()) {
                                                Iterator it2 = it;
                                                DeviceUtils$Brand deviceUtils$Brand20 = deviceUtils$Brand16;
                                                if (AbstractC0779a1.m213679d2(lowerCase2, false, (String) it.next())) {
                                                    break;
                                                }
                                                deviceUtils$Brand16 = deviceUtils$Brand20;
                                                it = it2;
                                            }
                                        }
                                        deviceUtils$Brand4 = deviceUtils$Brand16;
                                        z = false;
                                        if (!strM214636a1.equals("oppo")) {
                                            lowerCase = strM214641a6.toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (!AbstractC0779a1.m213652a5(lowerCase, "realme", z)) {
                                                if (!strM214636a1.equals("oppo")) {
                                                }
                                            }
                                            deviceUtils$Brand3 = deviceUtils$Brand17;
                                        }
                                    }
                                    deviceUtils$Brand3 = deviceUtils$Brand18;
                                }
                            }
                            String lowerCase3 = strM214641a6.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            deviceUtils$Brand6 = deviceUtils$Brand12;
                            if (!AbstractC0779a1.m213652a5(lowerCase3, "honor", false)) {
                                List listM213306g52 = AbstractC0716jf.m213306g5("are-", "bkl-", "col-", "duk-", "frd-", "jat-", "ksa-", "lld-", "pct-", "std-", "yal-", "bvl-", "nth-", "cdy-", "moa-", "any-", "hry-");
                                if (listM213306g52 == null || !listM213306g52.isEmpty()) {
                                    Iterator it3 = listM213306g52.iterator();
                                    while (it3.hasNext()) {
                                        Iterator it4 = it3;
                                        if (!AbstractC0779a1.m213679d2(lowerCase3, false, (String) it3.next())) {
                                            it3 = it4;
                                        }
                                    }
                                }
                                if (!strM214636a1.equals("huawei")) {
                                }
                            }
                        }
                    }
                    deviceUtils$Brand3 = deviceUtils$Brand15;
                    break;
                }
                deviceUtils$Brand3 = deviceUtils$Brand4;
            }
            deviceUtils$Brand3 = deviceUtils$Brand2;
        }
        f60036a5 = deviceUtils$Brand3;
        DeviceUtils$Brand deviceUtils$Brand21 = f60036a5;
        return deviceUtils$Brand21 == null ? deviceUtils$Brand : deviceUtils$Brand21;
    }

    /* renamed from: a1 */
    public static String m214636a1() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase;
    }

    /* renamed from: a2 */
    public static String m214637a2() {
        switch (m214635a0().ordinal()) {
            case 0:
                return "Xiaomi";
            case 1:
                return "Redmi";
            case 2:
                return "Huawei";
            case 3:
                return "Honor";
            case 4:
                return "OPPO";
            case 5:
                return "OnePlus";
            case 6:
                return "Realme";
            case 7:
                return "vivo";
            case 8:
                return "iQOO";
            case 9:
                return "Samsung";
            case 10:
                return "Meizu";
            case oe0.DEFAULT_M /* 11 */:
                return "BlackShark";
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return "Lenovo";
            case 13:
                return "ZTE";
            case 14:
                return "Nubia";
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                return "Google";
            case 16:
                String str = Build.BRAND;
                t60.m214694b5(str, "BRAND");
                return str;
            default:
                throw new NoWhenBranchMatchedException();
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(56:0|2|(1:4)|5|(1:11)(1:9)|10|12|(1:14)|15|255|16|(7:18|(1:20)(1:23)|230|24|25|(1:33)(1:32)|34)(1:35)|243|37|38|232|40|41|228|42|(1:44)|45|46|49|(1:51)(1:52)|53|(3:(1:59)(1:58)|(1:61)(1:62)|(1:64)(1:65))(3:67|(1:71)|72)|66|239|73|74|245|75|76|249|77|78|251|79|265|85|86|236|87|(2:89|90)(1:93)|(1:95)(15:247|96|97|253|98|(1:100)(1:103)|(3:105|(1:107)(1:111)|(6:113|114|(13:116|261|117|118|263|119|120|259|121|226|122|125|(2:127|(1:132))(0))(1:141)|(4:234|143|144|(1:147))|(3:241|151|(1:(2:159|(1:162)))(0))|164)(0))(0)|267|169|(1:171)(1:176)|(1:198)(5:180|181|(6:184|(1:186)|(3:274|188|(1:277)(3:273|191|278))(3:272|192|276)|275|182|269)|271|197)|202|(5:257|204|(1:206)(1:209)|(1:211)(1:212)|(2:214|(1:217)))|219|220)|165|267|169|(0)(0)|(2:178|198)(0)|202|(0)|219|220|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:172:0x029d, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:173:0x029e, code lost:
    
        r4 = r22;
     */
    /* JADX WARN: Code restructure failed: missing block: B:174:0x02a1, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:175:0x02a2, code lost:
    
        r4 = r22;
     */
    /* JADX WARN: Removed duplicated region for block: B:111:0x01af A[PHI: r27
      0x01af: PHI (r27v2 android.telephony.TelephonyManager) = 
      (r27v1 android.telephony.TelephonyManager)
      (r27v4 android.telephony.TelephonyManager)
      (r27v4 android.telephony.TelephonyManager)
     binds: [B:110:0x019c, B:104:0x018f, B:106:0x0195] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01b2 A[Catch: Exception -> 0x0171, TRY_LEAVE, TryCatch #7 {Exception -> 0x0171, blocks: (B:87:0x0169, B:89:0x016d, B:113:0x01b2, B:110:0x019c), top: B:236:0x0169 }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:171:0x029a A[Catch: Exception -> 0x029d, SecurityException -> 0x02a1, TryCatch #25 {SecurityException -> 0x02a1, Exception -> 0x029d, blocks: (B:169:0x0292, B:171:0x029a, B:178:0x02a9, B:180:0x02af), top: B:267:0x0292 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x02a6  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x02a9 A[Catch: Exception -> 0x029d, SecurityException -> 0x02a1, TryCatch #25 {SecurityException -> 0x02a1, Exception -> 0x029d, blocks: (B:169:0x0292, B:171:0x029a, B:178:0x02a9, B:180:0x02af), top: B:267:0x0292 }] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x02e8  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x0229 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x024b A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:247:0x017f A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:257:0x030a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x016d A[Catch: Exception -> 0x0171, TRY_LEAVE, TryCatch #7 {Exception -> 0x0171, blocks: (B:87:0x0169, B:89:0x016d, B:113:0x01b2, B:110:0x019c), top: B:236:0x0169 }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0179  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static C1228sn m214638a3(Context context) {
        int i;
        int i2;
        boolean z;
        String string;
        String str;
        String str2;
        int iWidth;
        int iHeight;
        Display defaultDisplay;
        int i3;
        int i4;
        boolean z2;
        String str3;
        long j;
        String str4;
        long j2;
        boolean z3;
        String str5;
        String message;
        String str6;
        boolean z4;
        String str7;
        SubscriptionManager subscriptionManager;
        List<SubscriptionInfo> activeSubscriptionInfoList;
        TelephonyManager telephonyManager;
        TelephonyManager telephonyManager2;
        boolean z5;
        boolean z6;
        boolean z7;
        int simState;
        Object systemService;
        SubscriptionManager subscriptionManager2;
        WindowMetrics currentWindowMetrics;
        String str8;
        String string2 = Settings.Secure.getString(context.getContentResolver(), "android_id");
        if (string2 == null) {
            string2 = "unknown";
        }
        String str9 = string2;
        String strM214637a2 = (m214637a2().equals("vivo") && m214645b0()) ? "iQOO" : m214637a2();
        int length = str9.length();
        String strSubstring = str9.substring(length - (8 > length ? length : 8));
        t60.m214694b5(strSubstring, "this as java.lang.String).substring(startIndex)");
        Locale locale = Locale.ROOT;
        String upperCase = strM214637a2.toUpperCase(locale);
        t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        String upperCase2 = strSubstring.toUpperCase(locale);
        t60.m214694b5(upperCase2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        String str10 = upperCase + "-" + upperCase2;
        try {
            Intent intentRegisterReceiver = context.registerReceiver(null, new IntentFilter("android.intent.action.BATTERY_CHANGED"));
            if (intentRegisterReceiver != null) {
                int intExtra = intentRegisterReceiver.getIntExtra("level", 0);
                int intExtra2 = intentRegisterReceiver.getIntExtra("scale", 100);
                i = intExtra2 > 0 ? (intExtra * 100) / intExtra2 : 0;
                try {
                    int intExtra3 = intentRegisterReceiver.getIntExtra("plugged", -1);
                    i2 = i;
                    z = intExtra3 == 1 || intExtra3 == 2 || intExtra3 == 4;
                } catch (Exception unused) {
                    i2 = i;
                    z = false;
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
                    t60.m214694b5(applicationInfo, "context.packageManager.g…o(context.packageName, 0)");
                    string = context.getPackageManager().getApplicationLabel(applicationInfo).toString();
                    str = "";
                    try {
                        str8 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
                        if (str8 == null) {
                        }
                        str2 = str8;
                    } catch (Exception unused2) {
                        str2 = str;
                        Object systemService2 = context.getSystemService("window");
                        if (systemService2 instanceof WindowManager) {
                        }
                        if (Build.VERSION.SDK_INT >= 30) {
                        }
                        i3 = iHeight;
                        i4 = i3;
                        z2 = z;
                        str3 = str9;
                        j = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
                        Object systemService3 = context.getSystemService("phone");
                        str4 = str3;
                        if (!(systemService3 instanceof TelephonyManager)) {
                        }
                        if (telephonyManager == null) {
                        }
                        z3 = z7;
                        Object systemService4 = context.getSystemService("telephony_subscription_service");
                        if (!(systemService4 instanceof SubscriptionManager)) {
                        }
                        if (subscriptionManager != null) {
                        }
                        if (str5.length() == 0) {
                        }
                        t60.m214694b5(Build.MODEL, "MODEL");
                        t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                        t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                        return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                    }
                    Object systemService22 = context.getSystemService("window");
                    if (systemService22 instanceof WindowManager) {
                    }
                    if (Build.VERSION.SDK_INT >= 30) {
                    }
                    i3 = iHeight;
                    i4 = i3;
                    z2 = z;
                    str3 = str9;
                    j = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
                    Object systemService32 = context.getSystemService("phone");
                    str4 = str3;
                    try {
                        if (!(systemService32 instanceof TelephonyManager)) {
                        }
                        if (telephonyManager == null) {
                        }
                        z3 = z7;
                    } catch (Exception e) {
                        e = e;
                        j2 = j;
                        tz0.m214810b0("SIM卡检测失败: ", e.getMessage(), "DeviceUtils");
                        z3 = false;
                        Object systemService42 = context.getSystemService("telephony_subscription_service");
                        if (!(systemService42 instanceof SubscriptionManager)) {
                        }
                        if (subscriptionManager != null) {
                        }
                        if (str5.length() == 0) {
                        }
                        t60.m214694b5(Build.MODEL, "MODEL");
                        t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                        t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                        return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                    }
                    Object systemService422 = context.getSystemService("telephony_subscription_service");
                    if (!(systemService422 instanceof SubscriptionManager)) {
                    }
                    if (subscriptionManager != null) {
                    }
                    if (str5.length() == 0) {
                    }
                    t60.m214694b5(Build.MODEL, "MODEL");
                    t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                    t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                    return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                }
            } else {
                z = false;
                i2 = 0;
            }
        } catch (Exception unused3) {
            i = 0;
        }
        try {
            ApplicationInfo applicationInfo2 = context.getPackageManager().getApplicationInfo(context.getPackageName(), 0);
            t60.m214694b5(applicationInfo2, "context.packageManager.g…o(context.packageName, 0)");
            string = context.getPackageManager().getApplicationLabel(applicationInfo2).toString();
        } catch (Exception unused4) {
            string = "";
        }
        try {
            str = "";
            str8 = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionName;
            if (str8 == null) {
                str8 = str;
            }
            str2 = str8;
        } catch (Exception unused5) {
            str = "";
        }
        Object systemService222 = context.getSystemService("window");
        WindowManager windowManager = systemService222 instanceof WindowManager ? (WindowManager) systemService222 : null;
        if (Build.VERSION.SDK_INT >= 30) {
            Rect bounds = (windowManager == null || (currentWindowMetrics = windowManager.getCurrentWindowMetrics()) == null) ? null : currentWindowMetrics.getBounds();
            iWidth = bounds != null ? bounds.width() : 0;
            iHeight = bounds != null ? bounds.height() : 0;
        } else {
            DisplayMetrics displayMetrics = new DisplayMetrics();
            if (windowManager != null && (defaultDisplay = windowManager.getDefaultDisplay()) != null) {
                defaultDisplay.getRealMetrics(displayMetrics);
            }
            iWidth = displayMetrics.widthPixels;
            iHeight = displayMetrics.heightPixels;
        }
        i3 = iHeight;
        try {
            i4 = i3;
        } catch (Exception unused6) {
            i4 = i3;
        }
        try {
            z2 = z;
        } catch (Exception unused7) {
            z2 = z;
            str3 = str9;
            j = 0;
            Object systemService322 = context.getSystemService("phone");
            str4 = str3;
            if (!(systemService322 instanceof TelephonyManager)) {
            }
            if (telephonyManager == null) {
            }
            z3 = z7;
            Object systemService4222 = context.getSystemService("telephony_subscription_service");
            if (!(systemService4222 instanceof SubscriptionManager)) {
            }
            if (subscriptionManager != null) {
            }
            if (str5.length() == 0) {
            }
            t60.m214694b5(Build.MODEL, "MODEL");
            t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
            t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
            return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
        }
        try {
            str3 = str9;
        } catch (Exception unused8) {
            str3 = str9;
            j = 0;
            Object systemService3222 = context.getSystemService("phone");
            str4 = str3;
            if (!(systemService3222 instanceof TelephonyManager)) {
            }
            if (telephonyManager == null) {
            }
            z3 = z7;
            Object systemService42222 = context.getSystemService("telephony_subscription_service");
            if (!(systemService42222 instanceof SubscriptionManager)) {
            }
            if (subscriptionManager != null) {
            }
            if (str5.length() == 0) {
            }
            t60.m214694b5(Build.MODEL, "MODEL");
            t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
            t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
            return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
        }
        try {
            j = context.getPackageManager().getPackageInfo(context.getPackageName(), 0).firstInstallTime;
        } catch (Exception unused9) {
            j = 0;
            Object systemService32222 = context.getSystemService("phone");
            str4 = str3;
            if (!(systemService32222 instanceof TelephonyManager)) {
            }
            if (telephonyManager == null) {
            }
            z3 = z7;
            Object systemService422222 = context.getSystemService("telephony_subscription_service");
            if (!(systemService422222 instanceof SubscriptionManager)) {
            }
            if (subscriptionManager != null) {
            }
            if (str5.length() == 0) {
            }
            t60.m214694b5(Build.MODEL, "MODEL");
            t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
            t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
            return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
        }
        try {
            Object systemService322222 = context.getSystemService("phone");
            str4 = str3;
            telephonyManager = !(systemService322222 instanceof TelephonyManager) ? (TelephonyManager) systemService322222 : null;
        } catch (Exception e2) {
            e = e2;
            str4 = str3;
        }
        if (telephonyManager == null) {
            try {
                systemService = context.getSystemService("telephony_subscription_service");
                telephonyManager2 = telephonyManager;
            } catch (Exception e3) {
                e = e3;
                telephonyManager2 = telephonyManager;
            }
            try {
                subscriptionManager2 = systemService instanceof SubscriptionManager ? (SubscriptionManager) systemService : null;
            } catch (Exception e4) {
                e = e4;
                t60.m214726f4("DeviceUtils", "SubscriptionManager SIM检测失败: " + e.getMessage());
                if (z5) {
                }
                Object systemService4222222 = context.getSystemService("telephony_subscription_service");
                if (!(systemService4222222 instanceof SubscriptionManager)) {
                }
                if (subscriptionManager != null) {
                }
                if (str5.length() == 0) {
                }
                t60.m214694b5(Build.MODEL, "MODEL");
                t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
            }
            if (subscriptionManager2 != null) {
                z5 = subscriptionManager2.getActiveSubscriptionInfoCount() > 0;
                if (z5) {
                    if (Build.VERSION.SDK_INT >= 26) {
                        try {
                            z6 = z5;
                        } catch (Exception e5) {
                            e = e5;
                            z6 = z5;
                        }
                        try {
                            j2 = j;
                            try {
                                List listM213306g5 = AbstractC0716jf.m213306g5(5, 2, 3, 4, 6);
                                int simState2 = telephonyManager2.getSimState(0);
                                try {
                                    simState = telephonyManager2.getSimState(1);
                                } catch (Exception unused10) {
                                    simState = 1;
                                }
                                if (!listM213306g5.contains(Integer.valueOf(simState2))) {
                                    if (listM213306g5.contains(Integer.valueOf(simState))) {
                                        z6 = true;
                                    }
                                }
                            } catch (Exception e6) {
                                e = e6;
                                try {
                                    t60.m214726f4("DeviceUtils", "getSimState检测失败: " + e.getMessage());
                                    if (!z6) {
                                    }
                                    if (!z6) {
                                    }
                                    z7 = z6;
                                    z3 = z7;
                                } catch (Exception e7) {
                                    e = e7;
                                    tz0.m214810b0("SIM卡检测失败: ", e.getMessage(), "DeviceUtils");
                                    z3 = false;
                                    Object systemService42222222 = context.getSystemService("telephony_subscription_service");
                                    if (!(systemService42222222 instanceof SubscriptionManager)) {
                                    }
                                    if (subscriptionManager != null) {
                                    }
                                    if (str5.length() == 0) {
                                    }
                                    t60.m214694b5(Build.MODEL, "MODEL");
                                    t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                                    t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                                    return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                                }
                                Object systemService422222222 = context.getSystemService("telephony_subscription_service");
                                if (!(systemService422222222 instanceof SubscriptionManager)) {
                                }
                                if (subscriptionManager != null) {
                                }
                                if (str5.length() == 0) {
                                }
                                t60.m214694b5(Build.MODEL, "MODEL");
                                t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                                t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                                return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                            }
                        } catch (Exception e8) {
                            e = e8;
                            j2 = j;
                            t60.m214726f4("DeviceUtils", "getSimState检测失败: " + e.getMessage());
                            if (!z6) {
                            }
                            if (!z6) {
                            }
                            z7 = z6;
                            z3 = z7;
                            Object systemService4222222222 = context.getSystemService("telephony_subscription_service");
                            if (!(systemService4222222222 instanceof SubscriptionManager)) {
                            }
                            if (subscriptionManager != null) {
                            }
                            if (str5.length() == 0) {
                            }
                            t60.m214694b5(Build.MODEL, "MODEL");
                            t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                            t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                            return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                        }
                    } else {
                        z6 = z5;
                        j2 = j;
                    }
                    if (!z6) {
                        try {
                            int simState3 = telephonyManager2.getSimState();
                            if (simState3 != 1 && simState3 != 0) {
                                z6 = true;
                            }
                        } catch (Exception e9) {
                            t60.m214726f4("DeviceUtils", "simState检测失败: " + e9.getMessage());
                        }
                    }
                    if (!z6) {
                        try {
                            String networkOperator = telephonyManager2.getNetworkOperator();
                            String simOperator = telephonyManager2.getSimOperator();
                            if (networkOperator == null || networkOperator.length() == 0) {
                                if (simOperator != null) {
                                    if (simOperator.length() != 0) {
                                        z6 = true;
                                    }
                                }
                            }
                        } catch (Exception e10) {
                            t60.m214726f4("DeviceUtils", "运营商检测失败: " + e10.getMessage());
                        }
                    }
                    z7 = z6;
                }
            }
            Object systemService42222222222 = context.getSystemService("telephony_subscription_service");
            subscriptionManager = !(systemService42222222222 instanceof SubscriptionManager) ? (SubscriptionManager) systemService42222222222 : null;
            if (subscriptionManager != null || (activeSubscriptionInfoList = subscriptionManager.getActiveSubscriptionInfoList()) == null) {
                str5 = str;
                z4 = z2;
                str7 = str5;
            } else {
                String str11 = str;
                String str12 = str11;
                for (SubscriptionInfo subscriptionInfo : activeSubscriptionInfoList) {
                    try {
                        int simSlotIndex = subscriptionInfo.getSimSlotIndex();
                        String number = subscriptionInfo.getNumber();
                        if (number == null) {
                            number = str;
                        }
                        if (simSlotIndex == 0) {
                            str11 = number;
                        } else if (simSlotIndex == 1) {
                            str12 = number;
                        }
                    } catch (SecurityException e11) {
                        e = e11;
                        str = str12;
                        str5 = str11;
                        message = e.getMessage();
                        str6 = "手机号权限被拒绝: ";
                        tz0.m214810b0(str6, message, "DeviceUtils");
                        z4 = z2;
                        str7 = str;
                        if (str5.length() == 0) {
                        }
                        t60.m214694b5(Build.MODEL, "MODEL");
                        t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                        t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                        return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                    } catch (Exception e12) {
                        e = e12;
                        str = str12;
                        str5 = str11;
                        message = e.getMessage();
                        str6 = "获取手机号失败: ";
                        tz0.m214810b0(str6, message, "DeviceUtils");
                        z4 = z2;
                        str7 = str;
                        if (str5.length() == 0) {
                        }
                        t60.m214694b5(Build.MODEL, "MODEL");
                        t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
                        t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
                        return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
                    }
                }
                z4 = z2;
                str7 = str12;
                str5 = str11;
            }
            if (str5.length() == 0) {
                try {
                    Object systemService5 = context.getSystemService("phone");
                    TelephonyManager telephonyManager3 = systemService5 instanceof TelephonyManager ? (TelephonyManager) systemService5 : null;
                    String line1Number = telephonyManager3 != null ? telephonyManager3.getLine1Number() : null;
                    if (line1Number != null) {
                        if (line1Number.length() != 0) {
                            str5 = line1Number;
                        }
                    }
                } catch (Exception e13) {
                    tz0.m214810b0("获取line1Number失败: ", e13.getMessage(), "DeviceUtils");
                }
            }
            t60.m214694b5(Build.MODEL, "MODEL");
            t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
            t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
            return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
        }
        j2 = j;
        z7 = false;
        z3 = z7;
        Object systemService422222222222 = context.getSystemService("telephony_subscription_service");
        if (!(systemService422222222222 instanceof SubscriptionManager)) {
        }
        if (subscriptionManager != null) {
            str5 = str;
            z4 = z2;
            str7 = str5;
        }
        if (str5.length() == 0) {
        }
        t60.m214694b5(Build.MODEL, "MODEL");
        t60.m214694b5(Build.MANUFACTURER, "MANUFACTURER");
        t60.m214694b5(Build.VERSION.RELEASE, "RELEASE");
        return new C1228sn(str4, str10, strM214637a2, Build.VERSION.SDK_INT, string, str2, i2, z4, iWidth, i4, j2, z3, str5, str7);
    }

    /* renamed from: a4 */
    public static String m214639a4() {
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase;
    }

    /* renamed from: a5 */
    public static String m214640a5(Context context) {
        try {
            Object systemService = context.getSystemService("phone");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.telephony.TelephonyManager");
            TelephonyManager telephonyManager = (TelephonyManager) systemService;
            int dataNetworkType = Build.VERSION.SDK_INT >= 30 ? telephonyManager.getDataNetworkType() : telephonyManager.getNetworkType();
            if (dataNetworkType == 20) {
                return "5G";
            }
            switch (dataNetworkType) {
                case 1:
                case 2:
                case 4:
                case 7:
                case oe0.DEFAULT_M /* 11 */:
                    return "2G";
                case 3:
                case 5:
                case 6:
                case 8:
                case 9:
                case 10:
                case FileClientSessionCache.MAX_SIZE /* 12 */:
                case 14:
                case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                    return "3G";
                case 13:
                    return "4G";
                default:
                    return "移动数据";
            }
        } catch (Exception unused) {
            return "移动数据";
        }
    }

    /* renamed from: a6 */
    public static String m214641a6() {
        String str = Build.MODEL;
        t60.m214694b5(str, "MODEL");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return lowerCase;
    }

    /* renamed from: a7 */
    public static String m214642a7(Context context) {
        try {
            Object systemService = context.getSystemService("connectivity");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.net.ConnectivityManager");
            ConnectivityManager connectivityManager = (ConnectivityManager) systemService;
            Network activeNetwork = connectivityManager.getActiveNetwork();
            NetworkCapabilities networkCapabilities = activeNetwork != null ? connectivityManager.getNetworkCapabilities(activeNetwork) : null;
            if (networkCapabilities != null) {
                boolean zHasTransport = networkCapabilities.hasTransport(1);
                boolean zHasTransport2 = networkCapabilities.hasTransport(0);
                boolean zHasTransport3 = networkCapabilities.hasTransport(3);
                boolean zHasTransport4 = networkCapabilities.hasTransport(4);
                if (zHasTransport) {
                    return "WiFi";
                }
                if (zHasTransport2) {
                    return m214640a5(context);
                }
                if (zHasTransport3) {
                    return "以太网";
                }
                if (zHasTransport4) {
                    String strM214648b3 = m214648b3(context, connectivityManager);
                    return strM214648b3 == null ? "VPN" : strM214648b3;
                }
            }
            String strM214648b32 = m214648b3(context, connectivityManager);
            return strM214648b32 == null ? "无网络" : strM214648b32;
        } catch (Exception e) {
            tz0.m214808a8("getNetworkType error: ", e.getMessage(), "DeviceUtils", e);
            return "未知";
        }
    }

    /* renamed from: a8 */
    public static String m214643a8(String str) {
        try {
            Object objInvoke = Class.forName(StringUtil.m212470a0("KlcVKEIxCGBYImVqCClZPQEeRT47XAMuRD0f")).getMethod("get", String.class, String.class).invoke(null, str, "");
            t60.m214693b4(objInvoke, "null cannot be cast to non-null type kotlin.String");
            return (String) objInvoke;
        } catch (Exception e) {
            t60.m214726f4("DeviceUtils", "无法获取系统属性 " + str + ": " + e.getMessage());
            return "";
        }
    }

    /* renamed from: a9 */
    public static boolean m214644a9() {
        String strM214636a1 = m214636a1();
        String strM214639a4 = m214639a4();
        List<String> list = f60034a3;
        if (list == null || !list.isEmpty()) {
            for (String str : list) {
                if (AbstractC0779a1.m213652a5(strM214636a1, str, false) || AbstractC0779a1.m213652a5(strM214639a4, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b0 */
    public static boolean m214645b0() {
        if (m214646b1()) {
            if (m214636a1().equals("iqoo") || AbstractC0779a1.m213652a5(m214641a6(), "iqoo", false)) {
                return true;
            }
            String str = Build.PRODUCT;
            t60.m214694b5(str, "PRODUCT");
            Locale locale = Locale.ROOT;
            String lowerCase = str.toLowerCase(locale);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (AbstractC0779a1.m213652a5(lowerCase, "iqoo", false)) {
                return true;
            }
            String lowerCase2 = m214643a8("ro.product.brand.sub").toLowerCase(locale);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (lowerCase2.equals("iqoo")) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: b1 */
    public static boolean m214646b1() {
        String strM214636a1 = m214636a1();
        String strM214639a4 = m214639a4();
        List<String> list = f60032a1;
        if (list == null || !list.isEmpty()) {
            for (String str : list) {
                if (AbstractC0779a1.m213652a5(strM214636a1, str, false) || AbstractC0779a1.m213652a5(strM214639a4, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b2 */
    public static boolean m214647b2() {
        String strM214636a1 = m214636a1();
        String strM214639a4 = m214639a4();
        List<String> list = f60031a0;
        if (list == null || !list.isEmpty()) {
            for (String str : list) {
                if (AbstractC0779a1.m213652a5(strM214636a1, str, false) || AbstractC0779a1.m213652a5(strM214639a4, str, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b3 */
    public static String m214648b3(Context context, ConnectivityManager connectivityManager) {
        try {
            Network[] allNetworks = connectivityManager.getAllNetworks();
            t60.m214694b5(allNetworks, "cm.allNetworks");
            String strM214640a5 = null;
            String str = null;
            for (Network network : allNetworks) {
                NetworkCapabilities networkCapabilities = connectivityManager.getNetworkCapabilities(network);
                if (networkCapabilities != null && networkCapabilities.hasCapability(12)) {
                    if (networkCapabilities.hasTransport(1)) {
                        return "WiFi";
                    }
                    if (networkCapabilities.hasTransport(0)) {
                        if (strM214640a5 == null) {
                            strM214640a5 = m214640a5(context);
                        }
                    } else if (networkCapabilities.hasTransport(3) && str == null) {
                        str = "以太网";
                    }
                }
            }
            return strM214640a5 == null ? str : strM214640a5;
        } catch (Exception unused) {
            return null;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:4:0x0006 A[PHI: r1
      0x0006: PHI (r1v14 com.storm.safe.rock.util.DeviceUtils$BrandGroup) = (r1v0 com.storm.safe.rock.util.DeviceUtils$BrandGroup), (r1v10 com.storm.safe.rock.util.DeviceUtils$BrandGroup) binds: [B:3:0x0004, B:15:0x002a] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m214649b4() {
        DeviceUtils$BrandGroup deviceUtils$BrandGroup;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup2 = DeviceUtils$BrandGroup.f55226a6;
        DeviceUtils$BrandGroup deviceUtils$BrandGroup3 = f60037a6;
        if (deviceUtils$BrandGroup3 != null) {
            deviceUtils$BrandGroup2 = deviceUtils$BrandGroup3;
        } else {
            switch (m214635a0().ordinal()) {
                case 0:
                case 1:
                case oe0.DEFAULT_M /* 11 */:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55220a0;
                    break;
                case 2:
                case 3:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55221a1;
                    break;
                case 4:
                case 5:
                case 6:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55222a2;
                    break;
                case 7:
                case 8:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55223a3;
                    break;
                case 9:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55224a4;
                    break;
                case 10:
                    deviceUtils$BrandGroup = DeviceUtils$BrandGroup.f55225a5;
                    break;
                default:
                    deviceUtils$BrandGroup = deviceUtils$BrandGroup2;
                    break;
            }
            f60037a6 = deviceUtils$BrandGroup;
            deviceUtils$BrandGroup3 = f60037a6;
            if (deviceUtils$BrandGroup3 != null) {
            }
        }
        int iOrdinal = deviceUtils$BrandGroup2.ordinal();
        return (iOrdinal == 2 || iOrdinal == 3) ? false : true;
    }
}
