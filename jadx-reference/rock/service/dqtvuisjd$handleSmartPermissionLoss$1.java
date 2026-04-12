package com.storm.safe.rock.service;

import com.storm.safe.rock.manager.C0263a5;
import com.storm.safe.rock.manager.SmartMediaProjectionManager$LossReason;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleSmartPermissionLoss$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleSmartPermissionLoss$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ SmartMediaProjectionManager$LossReason f52561a1;

    /* renamed from: a2 */
    public final /* synthetic */ dqtvuisjd f52562a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleSmartPermissionLoss$1(SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason, dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52561a1 = smartMediaProjectionManager$LossReason;
        this.f52562a2 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleSmartPermissionLoss$1(this.f52561a1, this.f52562a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$handleSmartPermissionLoss$1 dqtvuisjd_handlesmartpermissionloss_1 = (dqtvuisjd$handleSmartPermissionLoss$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_handlesmartpermissionloss_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        SmartMediaProjectionManager$LossReason smartMediaProjectionManager$LossReason = this.f52561a1;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            t60.m214726f4("dqtvuisjd", "🔧 处理智能权限丢失: " + smartMediaProjectionManager$LossReason);
            C0263a5 c0263a5 = this.f52562a2.f52370a1;
            if (c0263a5 != null) {
                c0263a5.m211352a8();
            }
            int iOrdinal = smartMediaProjectionManager$LossReason.ordinal();
            if (iOrdinal == 0) {
                t60.m214714d6("dqtvuisjd", "👤 用户主动停止投屏，暂停服务但保持连接");
            } else if (iOrdinal == 1) {
                t60.m214714d6("dqtvuisjd", "🔒 系统锁屏导致权限丢失，等待解锁后恢复");
            } else if (iOrdinal == 2 || iOrdinal == 3 || iOrdinal == 4) {
                t60.m214714d6("dqtvuisjd", "🤖 系统自动停止，智能管理器将尝试恢复");
            }
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理智能权限丢失失败", e);
        }
        return C1351vv.f60710b1;
    }
}
