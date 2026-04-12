package com.storm.safe.rock.service.modules.yw5xud;

import java.util.List;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.Yw5xudAuthorizationHandler", m214403f = "Yw5xudAuthorizationHandler.kt", m214404l = {2706}, m214405m = "executeMeizuAuthorization")
/* loaded from: classes2.dex */
final class Yw5xudAuthorizationHandler$executeMeizuAuthorization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0372a9 f55014a0;

    /* renamed from: a1 */
    public List f55015a1;

    /* renamed from: a2 */
    public List f55016a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f55017a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0372a9 f55018a4;

    /* renamed from: a5 */
    public int f55019a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Yw5xudAuthorizationHandler$executeMeizuAuthorization$1(C0372a9 c0372a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f55018a4 = c0372a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f55017a3 = obj;
        this.f55019a5 |= Integer.MIN_VALUE;
        return this.f55018a4.m212452b0(null, null, this);
    }
}
