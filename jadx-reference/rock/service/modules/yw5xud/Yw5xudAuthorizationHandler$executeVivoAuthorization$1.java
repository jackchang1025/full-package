package com.storm.safe.rock.service.modules.yw5xud;

import java.util.List;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.Yw5xudAuthorizationHandler", m214403f = "Yw5xudAuthorizationHandler.kt", m214404l = {2599}, m214405m = "executeVivoAuthorization")
/* loaded from: classes2.dex */
final class Yw5xudAuthorizationHandler$executeVivoAuthorization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0372a9 f55038a0;

    /* renamed from: a1 */
    public List f55039a1;

    /* renamed from: a2 */
    public List f55040a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f55041a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0372a9 f55042a4;

    /* renamed from: a5 */
    public int f55043a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Yw5xudAuthorizationHandler$executeVivoAuthorization$1(C0372a9 c0372a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f55042a4 = c0372a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f55041a3 = obj;
        this.f55043a5 |= Integer.MIN_VALUE;
        return this.f55042a4.m212456b4(null, null, this);
    }
}
