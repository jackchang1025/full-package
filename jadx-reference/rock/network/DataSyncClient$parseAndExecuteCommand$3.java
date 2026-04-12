package com.storm.safe.rock.network;

import java.util.concurrent.CancellationException;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import kotlinx.coroutines.AbstractC0780a0;
import p000.C1108qf;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;
import p000.r61;
import p000.t60;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.DataSyncClient$parseAndExecuteCommand$3", m214403f = "DataSyncClient.kt", m214404l = {512}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class DataSyncClient$parseAndExecuteCommand$3 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52185a1;

    /* renamed from: a2 */
    public final /* synthetic */ String f52186a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0267a0 f52187a3;

    /* renamed from: a4 */
    public final /* synthetic */ C1108qf f52188a4;

    /* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
    @InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.DataSyncClient$parseAndExecuteCommand$3$1", m214403f = "DataSyncClient.kt", m214404l = {}, m214405m = "invokeSuspend")
    /* renamed from: com.storm.safe.rock.network.DataSyncClient$parseAndExecuteCommand$3$1 */
    final class C02661 extends SuspendLambda implements l10 {

        /* renamed from: a1 */
        public final /* synthetic */ C0267a0 f52189a1;

        /* renamed from: a2 */
        public final /* synthetic */ C1108qf f52190a2;

        /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
        public C02661(C0267a0 c0267a0, C1108qf c1108qf, InterfaceC0876mv interfaceC0876mv) {
            super(2, interfaceC0876mv);
            this.f52189a1 = c0267a0;
            this.f52190a2 = c1108qf;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
            return new C02661(this.f52189a1, this.f52190a2, interfaceC0876mv);
        }

        @Override // p000.l10
        public final Object invoke(Object obj, Object obj2) throws Throwable {
            C02661 c02661 = (C02661) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2);
            C1351vv c1351vv = C1351vv.f60710b1;
            c02661.invokeSuspend(c1351vv);
            return c1351vv;
        }

        @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
        public final Object invokeSuspend(Object obj) throws Throwable {
            CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
            kg1.m213544f4(obj);
            this.f52189a1.f52261a1.invoke(this.f52190a2);
            return C1351vv.f60710b1;
        }
    }

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public DataSyncClient$parseAndExecuteCommand$3(String str, C0267a0 c0267a0, C1108qf c1108qf, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52186a2 = str;
        this.f52187a3 = c0267a0;
        this.f52188a4 = c1108qf;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new DataSyncClient$parseAndExecuteCommand$3(this.f52186a2, this.f52187a3, this.f52188a4, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((DataSyncClient$parseAndExecuteCommand$3) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f52185a1;
        try {
            if (i == 0) {
                kg1.m213544f4(obj);
                C02661 c02661 = new C02661(this.f52187a3, this.f52188a4, null);
                this.f52185a1 = 1;
                if (AbstractC0780a0.m213694a5(new r61(30000L, this), c02661) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                if (i != 1) {
                    throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
                }
                kg1.m213544f4(obj);
            }
        } catch (CancellationException e) {
            throw e;
        } catch (Exception e2) {
            t60.m214705c6("DataSyncClient", "命令执行失败: ".concat(this.f52186a2), e2);
        }
        return C1351vv.f60710b1;
    }
}
