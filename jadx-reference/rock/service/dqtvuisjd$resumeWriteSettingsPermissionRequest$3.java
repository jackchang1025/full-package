package com.storm.safe.rock.service;

import com.storm.safe.rock.service.dqtvuisjd;
import com.storm.safe.rock.service.modules.C0327b2;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.android.C0785a0;
import p000.AbstractC1262tj;
import p000.C1180rh;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;
import p000.sc0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$resumeWriteSettingsPermissionRequest$3", m214403f = "dqtvuisjd.kt", m214404l = {6450, 6452}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$resumeWriteSettingsPermissionRequest$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52670a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52671a2;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$resumeWriteSettingsPermissionRequest$3$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.service.dqtvuisjd$resumeWriteSettingsPermissionRequest$3$1 */
    final class C02991 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ dqtvuisjd f52672a1;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02991(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52672a1 = dqtvuisjdVar;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02991(this.f52672a1, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02991 c02991 = (C02991) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02991.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            dqtvuisjd dqtvuisjdVar = this.f52672a1;
            dqtvuisjd.C0290a0 c0290a0 = dqtvuisjd.f52358m1;
            dqtvuisjdVar.getClass();
            try {
                if (dqtvuisjdVar.f52432g3) {
                    t60.m214714d6("dqtvuisjd", "⏸️ WRITE_SETTINGS权限申请已被暂停，跳过申请");
                } else {
                    t60.m214714d6("dqtvuisjd", "🔧 开始申请WRITE_SETTINGS权限");
                    C0327b2 c0327b2 = dqtvuisjdVar.f52429g0;
                    if (c0327b2 == null) {
                        t60.m214704c5("dqtvuisjd", "❌ WriteSettingsPermissionManager未初始化，跳过权限申请");
                    } else {
                        c0327b2.m211751f7();
                    }
                }
            } catch (Exception e) {
                t60.m214705c6("dqtvuisjd", "❌ 申请WRITE_SETTINGS权限失败", e);
            }
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$resumeWriteSettingsPermissionRequest$3(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52671a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$resumeWriteSettingsPermissionRequest$3(this.f52671a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$resumeWriteSettingsPermissionRequest$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Code restructure failed: missing block: B:14:0x0043, code lost:
    
        if (kotlinx.coroutines.AbstractC0780a0.m213696a7(r6, r1, r5) == r0) goto L15;
     */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52670a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            this.f52670a1 = 1;
            if (b81.m210571b1(800L, this) != coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        t60.m214704c5("dqtvuisjd", "🔐🔐🔐 [密码调试] 800ms延迟结束，直接调用requestWriteSettingsPermission()");
        C1180rh c1180rh = AbstractC1262tj.f60233a0;
        C0785a0 c0785a0 = sc0.f59953a0;
        C02991 c02991 = new C02991(this.f52671a2, null);
        this.f52670a1 = 2;
    }
}
