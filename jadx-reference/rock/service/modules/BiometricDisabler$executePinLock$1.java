package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.BiometricDisabler", m214403f = "BiometricDisabler.kt", m214404l = {260, 263, 266, 270}, m214405m = "executePinLock")
/* loaded from: classes2.dex */
final class BiometricDisabler$executePinLock$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0317a2 f52751a0;

    /* renamed from: a1 */
    public int f52752a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f52753a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0317a2 f52754a3;

    /* renamed from: a4 */
    public int f52755a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public BiometricDisabler$executePinLock$1(C0317a2 c0317a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52754a3 = c0317a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52753a2 = obj;
        this.f52755a4 |= Integer.MIN_VALUE;
        return C0317a2.m211551a2(this.f52754a3, this);
    }
}
