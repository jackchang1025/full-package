package com.storm.safe.rock.service.modules.yw5xud;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import okhttp3.internal.p032ws.WebSocketProtocol;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.service.modules.yw5xud.SamsungSteps", m214403f = "SamsungSteps.kt", m214404l = {WebSocketProtocol.CLOSE_CLIENT_GOING_AWAY, WebSocketProtocol.CLOSE_NO_STATUS_CODE, 1009, 1016, 1022, 1028, 1030, 1032, 1037, 1050}, m214405m = "executeAllFilesAccess")
/* loaded from: classes2.dex */
final class SamsungSteps$executeAllFilesAccess$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0370a7 f54684a0;

    /* renamed from: a1 */
    public /* synthetic */ Object f54685a1;

    /* renamed from: a2 */
    public final /* synthetic */ C0370a7 f54686a2;

    /* renamed from: a3 */
    public int f54687a3;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public SamsungSteps$executeAllFilesAccess$1(C0370a7 c0370a7, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f54686a2 = c0370a7;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f54685a1 = obj;
        this.f54687a3 |= Integer.MIN_VALUE;
        return this.f54686a2.m212362a5(this);
    }
}
