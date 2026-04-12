package com.storm.safe.rock.util;

import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.SuspendLambda;
import p000.C1351vv;
import p000.InterfaceC0876mv;
import p000.InterfaceC0920no;
import p000.InterfaceC1116qn;
import p000.kg1;
import p000.l10;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.util.AppScope$launch$1", m214403f = "AppScope.kt", m214404l = {67}, m214405m = "invokeSuspend")
/* loaded from: classes2.dex */
final class AppScope$launch$1 extends SuspendLambda implements l10 {

    /* renamed from: a1 */
    public int f55199a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f55200a2;

    /* renamed from: a3 */
    public final /* synthetic */ SuspendLambda f55201a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    /* JADX WARN: Multi-variable type inference failed */
    public AppScope$launch$1(l10 l10Var, InterfaceC0876mv interfaceC0876mv) {
        super(2, interfaceC0876mv);
        this.f55201a3 = (SuspendLambda) l10Var;
    }

    /* JADX WARN: Type inference failed for: r1v0, types: [kotlin.coroutines.jvm.internal.SuspendLambda, l10] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final InterfaceC0876mv create(Object obj, InterfaceC0876mv interfaceC0876mv) {
        AppScope$launch$1 appScope$launch$1 = new AppScope$launch$1(this.f55201a3, interfaceC0876mv);
        appScope$launch$1.f55200a2 = obj;
        return appScope$launch$1;
    }

    @Override // p000.l10
    public final Object invoke(Object obj, Object obj2) {
        return ((AppScope$launch$1) create((InterfaceC0920no) obj, (InterfaceC0876mv) obj2)).invokeSuspend(C1351vv.f60710b1);
    }

    /* JADX WARN: Type inference failed for: r1v1, types: [kotlin.coroutines.jvm.internal.SuspendLambda, l10] */
    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) throws Throwable {
        CoroutineSingletons coroutineSingletons = CoroutineSingletons.f57606a0;
        int i = this.f55199a1;
        if (i == 0) {
            kg1.m213544f4(obj);
            InterfaceC0920no interfaceC0920no = (InterfaceC0920no) this.f55200a2;
            this.f55199a1 = 1;
            if (this.f55201a3.invoke(interfaceC0920no, this) == coroutineSingletons) {
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
