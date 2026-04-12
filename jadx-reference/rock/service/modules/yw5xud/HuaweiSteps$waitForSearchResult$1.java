package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.HuaweiSteps", m214403f = "HuaweiSteps.kt", m214404l = {4797}, m214405m = "waitForSearchResult")
/* loaded from: classes2.dex */
final class HuaweiSteps$waitForSearchResult$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0365a2 f54164a0;

    /* renamed from: a1 */
    public String f54165a1;

    /* renamed from: a2 */
    public int f54166a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f54167a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0365a2 f54168a4;

    /* renamed from: a5 */
    public int f54169a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HuaweiSteps$waitForSearchResult$1(C0365a2 c0365a2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54168a4 = c0365a2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54167a3 = obj;
        this.f54169a5 |= Integer.MIN_VALUE;
        return this.f54168a4.m212217h5(null, this);
    }
}
