package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.MeizuSteps", m214403f = "MeizuSteps.kt", m214404l = {497}, m214405m = "clickAllAppsFilter")
/* loaded from: classes2.dex */
final class MeizuSteps$clickAllAppsFilter$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public /* synthetic */ Object f54176a0;

    /* renamed from: a1 */
    public final /* synthetic */ C0366a3 f54177a1;

    /* renamed from: a2 */
    public int f54178a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MeizuSteps$clickAllAppsFilter$1(C0366a3 c0366a3, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54177a1 = c0366a3;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54176a0 = obj;
        this.f54178a2 |= Integer.MIN_VALUE;
        return this.f54177a1.m212224a4(this);
    }
}
