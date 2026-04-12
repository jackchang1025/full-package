package com.storm.safe.rock.service.modules.yw5xud;

import java.util.List;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.Yw5xudAuthorizationHandler", m214403f = "Yw5xudAuthorizationHandler.kt", m214404l = {2729}, m214405m = "executeGenericAuthorization")
/* loaded from: classes2.dex */
final class Yw5xudAuthorizationHandler$executeGenericAuthorization$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0372a9 f55001a0;

    /* renamed from: a1 */
    public List f55002a1;

    /* renamed from: a2 */
    public List f55003a2;

    /* renamed from: a3 */
    public List f55004a3;

    /* renamed from: a4 */
    public /* synthetic */ Object f55005a4;

    /* renamed from: a5 */
    public final /* synthetic */ C0372a9 f55006a5;

    /* renamed from: a6 */
    public int f55007a6;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public Yw5xudAuthorizationHandler$executeGenericAuthorization$1(C0372a9 c0372a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f55006a5 = c0372a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f55005a4 = obj;
        this.f55007a6 |= Integer.MIN_VALUE;
        return this.f55006a5.m212450a8(null, null, null, this);
    }
}
