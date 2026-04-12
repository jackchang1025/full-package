package p000;

import kotlinx.coroutines.channels.BufferOverflow;
import kotlinx.coroutines.channels.C0786a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: ks */
/* loaded from: classes2.dex */
public final class C0794ks extends C0786a0 {

    /* renamed from: a9 */
    public final BufferOverflow f57715a9;

    public C0794ks(BufferOverflow bufferOverflow) {
        super(1);
        this.f57715a9 = bufferOverflow;
        if (bufferOverflow != BufferOverflow.f57667a0) {
            return;
        }
        throw new IllegalArgumentException(("This implementation does not support suspension for senders, use " + C0627in.f56913a1.getClassSimpleName(fr0.m212854a0(C0786a0.class).f56917a0) + " instead").toString());
    }

    @Override // kotlinx.coroutines.channels.C0786a0
    /* renamed from: b2 */
    public final boolean mo213722b2() {
        return this.f57715a9 == BufferOverflow.f57668a1;
    }
}
