package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC1116qn;
import p000.h10;
import p000.kg1;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.VivoSteps$execute$5", m214403f = "VivoSteps.kt", m214404l = {566}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class VivoSteps$execute$5 extends SuspendLambda implements h10 {

    /* renamed from: a1 */
    public int f54804a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0371a8 f54805a2;

    /* renamed from: a3 */
    public final /* synthetic */ String f54806a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VivoSteps$execute$5(C0371a8 c0371a8, String str, InterfaceC0876mv interfaceC0876mv) {
        super(1, interfaceC0876mv);
        this.f54805a2 = c0371a8;
        this.f54806a3 = str;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(InterfaceC0876mv interfaceC0876mv) {
        return new VivoSteps$execute$5(this.f54805a2, this.f54806a3, interfaceC0876mv);
    }

    @Override // p000.h10
    public final Object invoke(Object obj) {
        return ((VivoSteps$execute$5) create((InterfaceC0876mv) obj)).invokeSuspend(C1351vv.f60710b1);
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f54804a1;
        if (i != 0) {
            if (i != 1) {
                throw new IllegalStateException("call to 'resume' before 'invoke' with coroutine");
            }
            kg1.m213544f4(obj);
            return obj;
        }
        kg1.m213544f4(obj);
        this.f54804a1 = 1;
        Object objM212373a2 = C0371a8.m212373a2(this.f54805a2, this);
        return objM212373a2 == coroutineSingletons ? coroutineSingletons : objM212373a2;
    }
}
