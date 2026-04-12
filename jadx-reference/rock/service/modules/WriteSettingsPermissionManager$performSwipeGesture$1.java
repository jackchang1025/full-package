package com.storm.safe.rock.service.modules;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import kotlin.jvm.internal.Ref$BooleanRef;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.WriteSettingsPermissionManager", m214403f = "WriteSettingsPermissionManager.kt", m214404l = {3954}, m214405m = "performSwipeGesture")
/* loaded from: classes2.dex */
final class WriteSettingsPermissionManager$performSwipeGesture$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public Ref$BooleanRef f52984a0;

    /* renamed from: a1 */
    public Ref$BooleanRef f52985a1;

    /* renamed from: a2 */
    public int f52986a2;

    /* renamed from: a3 */
    public /* synthetic */ Object f52987a3;

    /* renamed from: a4 */
    public final /* synthetic */ C0327b2 f52988a4;

    /* renamed from: a5 */
    public int f52989a5;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public WriteSettingsPermissionManager$performSwipeGesture$1(C0327b2 c0327b2, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52988a4 = c0327b2;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52987a3 = obj;
        this.f52989a5 |= Integer.MIN_VALUE;
        return this.f52988a4.m211748f3(0.0f, 0.0f, 0.0f, 0.0f, this);
    }
}
