package com.storm.safe.rock.service.modules.command;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;
import p000.uz0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.command.MediaCommandHandler", m214403f = "MediaCommandHandler.kt", m214404l = {113, 120}, m214405m = "handleCameraSwitch")
/* loaded from: classes2.dex */
final class MediaCommandHandler$handleCameraSwitch$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public uz0 f53516a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f53517a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0349a6 f53518a2;

    /* renamed from: a3 */
    public int f53519a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MediaCommandHandler$handleCameraSwitch$1(C0349a6 c0349a6, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f53518a2 = c0349a6;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f53517a1 = obj;
        this.f53519a3 |= Integer.MIN_VALUE;
        return this.f53518a2.m211882a3(null, null, this);
    }
}
