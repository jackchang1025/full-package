package com.storm.safe.rock.network;

import kotlin.Result;
import kotlin.coroutines.intrinsics.CoroutineSingletons;
import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$LongRef;
import kotlin.jvm.internal.Ref$ObjectRef;
import okhttp3.Request;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.network.HttpManager", m214403f = "HttpManager.kt", m214404l = {331, 366}, m214405m = "executeRequest-BWLJW6A")
/* loaded from: classes2.dex */
final class HttpManager$executeRequest$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0268a1 f52191a0;

    /* renamed from: a1 */
    public Request f52192a1;

    /* renamed from: a2 */
    public Ref$ObjectRef f52193a2;

    /* renamed from: a3 */
    public Ref$LongRef f52194a3;

    /* renamed from: a4 */
    public int f52195a4;

    /* renamed from: a5 */
    public int f52196a5;

    /* renamed from: a6 */
    public int f52197a6;

    /* renamed from: a7 */
    public /* synthetic */ Object f52198a7;

    /* renamed from: a8 */
    public final /* synthetic */ C0268a1 f52199a8;

    /* renamed from: a9 */
    public int f52200a9;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public HttpManager$executeRequest$1(C0268a1 c0268a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52199a8 = c0268a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52198a7 = obj;
        this.f52200a9 |= Integer.MIN_VALUE;
        Object objM211370a0 = this.f52199a8.m211370a0(null, 0, 0L, this);
        return objM211370a0 == CoroutineSingletons.f57606a0 ? objM211370a0 : new Result(objM211370a0);
    }
}
