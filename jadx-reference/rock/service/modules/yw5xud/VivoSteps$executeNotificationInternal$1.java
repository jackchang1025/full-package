package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.VivoSteps", m214403f = "VivoSteps.kt", m214404l = {2372, 2384, 2409, 2416, 2421, 2427, 2429, 2432, 2440}, m214405m = "executeNotificationInternal")
/* loaded from: classes2.dex */
final class VivoSteps$executeNotificationInternal$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0371a8 f54901a0;

    /* renamed from: a1 */
    public int f54902a1;

    /* renamed from: a2 */
    public int f54903a2;

    /* renamed from: a3 */
    public int f54904a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f54905a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0371a8 f54906a5;

    /* renamed from: a6 */
    public int f54907a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public VivoSteps$executeNotificationInternal$1(C0371a8 c0371a8, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54906a5 = c0371a8;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54905a4 = obj;
        this.f54907a6 |= Integer.MIN_VALUE;
        return C0371a8.m212373a2(this.f54906a5, this);
    }
}
