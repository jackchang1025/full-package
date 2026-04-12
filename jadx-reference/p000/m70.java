package p000;

import java.util.concurrent.atomic.AtomicReferenceFieldUpdater;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class m70 extends y70 {

    /* renamed from: a2 */
    public final boolean f58282a2;

    /* JADX WARN: 'super' call moved to the top of the method (can break code semantics) */
    public m70() {
        super(true);
        boolean z = true;
        m215263c8(null);
        AtomicReferenceFieldUpdater atomicReferenceFieldUpdater = y70.f61261a1;
        InterfaceC0581hh interfaceC0581hh = (InterfaceC0581hh) atomicReferenceFieldUpdater.get(this);
        C0582hi c0582hi = interfaceC0581hh instanceof C0582hi ? (C0582hi) interfaceC0581hh : null;
        if (c0582hi == null) {
            z = false;
            break;
        }
        y70 y70VarM214818b0 = c0582hi.m214818b0();
        while (!y70VarM214818b0.mo213945c1()) {
            InterfaceC0581hh interfaceC0581hh2 = (InterfaceC0581hh) atomicReferenceFieldUpdater.get(y70VarM214818b0);
            C0582hi c0582hi2 = interfaceC0581hh2 instanceof C0582hi ? (C0582hi) interfaceC0581hh2 : null;
            if (c0582hi2 == null) {
                z = false;
                break;
            }
            y70VarM214818b0 = c0582hi2.m214818b0();
        }
        this.f58282a2 = z;
    }

    @Override // p000.y70
    /* renamed from: c1 */
    public final boolean mo213945c1() {
        return this.f58282a2;
    }

    @Override // p000.y70
    /* renamed from: c2 */
    public final boolean mo213946c2() {
        return true;
    }
}
