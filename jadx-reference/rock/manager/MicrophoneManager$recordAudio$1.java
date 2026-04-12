package com.storm.safe.rock.manager;

import kotlin.coroutines.jvm.internal.ContinuationImpl;
import p000.InterfaceC1116qn;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
@InterfaceC1116qn(m214402c = "com.storm.safe.rock.manager.MicrophoneManager", m214403f = "MicrophoneManager.kt", m214404l = {327}, m214405m = "recordAudio")
/* loaded from: classes2.dex */
final class MicrophoneManager$recordAudio$1 extends ContinuationImpl {

    /* renamed from: a0 */
    public C0259a1 f51997a0;

    /* renamed from: a1 */
    public byte[] f51998a1;

    /* renamed from: a2 */
    public byte[] f51999a2;

    /* renamed from: a3 */
    public int f52000a3;

    /* renamed from: a4 */
    public int f52001a4;

    /* renamed from: a5 */
    public int f52002a5;

    /* renamed from: a6 */
    public int f52003a6;

    /* renamed from: a7 */
    public int f52004a7;

    /* renamed from: a8 */
    public long f52005a8;

    /* renamed from: a9 */
    public /* synthetic */ Object f52006a9;

    /* renamed from: b0 */
    public final /* synthetic */ C0259a1 f52007b0;

    /* renamed from: b1 */
    public int f52008b1;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public MicrophoneManager$recordAudio$1(C0259a1 c0259a1, ContinuationImpl continuationImpl) {
        super(continuationImpl);
        this.f52007b0 = c0259a1;
    }

    @Override // kotlin.coroutines.jvm.internal.BaseContinuationImpl
    public final Object invokeSuspend(Object obj) {
        this.f52006a9 = obj;
        this.f52008b1 |= Integer.MIN_VALUE;
        return C0259a1.m211251a0(this.f52007b0, this);
    }
}
