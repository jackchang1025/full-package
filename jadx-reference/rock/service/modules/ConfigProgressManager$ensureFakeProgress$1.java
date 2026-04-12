package com.storm.safe.rock.service.modules;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.b81;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.ConfigProgressManager$ensureFakeProgress$1", m214403f = "ConfigProgressManager.kt", m214404l = {268, 271}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class ConfigProgressManager$ensureFakeProgress$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f52771a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0318a3 f52772a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public ConfigProgressManager$ensureFakeProgress$1(C0318a3 c0318a3, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f52772a2 = c0318a3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        return new ConfigProgressManager$ensureFakeProgress$1(this.f52772a2, interfaceC0876mv);
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((ConfigProgressManager$ensureFakeProgress$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        int i;
        C0318a3 c0318a3 = this.f52772a2;
        long j = c0318a3.f53052a7;
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i2 = this.f52771a1;
        if (i2 == 0) {
            kg1.m213544f4(obj);
        } else {
            if (i2 != 1 && i2 != 2) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            try {
                kg1.m213544f4(obj);
            } catch (Exception unused) {
            }
        }
        while (c0318a3.f53047a2 && (i = c0318a3.f53048a3) < 100) {
            int i3 = i + 1;
            int i4 = c0318a3.f53049a4;
            if (i3 > i4) {
                i3 = i4;
            }
            if (i3 != i) {
                c0318a3.f53048a3 = i3;
                ConfigProgressManager$ConfigStage configProgressManager$ConfigStage = c0318a3.f53046a1;
                c0318a3.m211568a2(configProgressManager$ConfigStage, i3, configProgressManager$ConfigStage.f52770a2);
            }
            if (c0318a3.f53048a3 >= c0318a3.f53049a4) {
                this.f52771a1 = 1;
                if (b81.m210571b1(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            } else {
                this.f52771a1 = 2;
                if (b81.m210571b1(j, this) == coroutineSingletons) {
                    return coroutineSingletons;
                }
            }
        }
        return C1351vv.f60710b1;
    }
}
