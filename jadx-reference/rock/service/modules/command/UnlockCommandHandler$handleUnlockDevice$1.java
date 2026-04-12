package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.UnlockCommandHandler", m214403f = "UnlockCommandHandler.kt", m214404l = {547, 551, 553}, m214405m = "handleUnlockDevice")
/* loaded from: classes2.dex */
final class UnlockCommandHandler$handleUnlockDevice$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public uz0 f53576a0;

    /* renamed from: a1 */
    public String f53577a1;

    /* renamed from: a2 */
    public /* synthetic */ Object f53578a2;

    /* renamed from: a3 */
    public final /* synthetic */ C0352a9 f53579a3;

    /* renamed from: a4 */
    public int f53580a4;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public UnlockCommandHandler$handleUnlockDevice$1(C0352a9 c0352a9, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53579a3 = c0352a9;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53578a2 = obj;
        this.f53580a4 |= Integer.MIN_VALUE;
        return this.f53579a3.m211892a8(null, null, this);
    }
}
