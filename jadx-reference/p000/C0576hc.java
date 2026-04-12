package p000;

import java.util.concurrent.atomic.AtomicLongFieldUpdater;
import java.util.concurrent.atomic.AtomicReferenceArray;
import kotlinx.coroutines.channels.C0786a0;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: hc */
/* loaded from: classes2.dex */
public final class C0576hc extends jz0 {

    /* renamed from: a4 */
    public final C0786a0 f56646a4;

    /* renamed from: a5 */
    public final AtomicReferenceArray f56647a5;

    public C0576hc(long j, C0576hc c0576hc, C0786a0 c0786a0, int i) {
        super(j, c0576hc, i);
        this.f56646a4 = c0786a0;
        this.f56647a5 = new AtomicReferenceArray(AbstractC0494fg.f56238a1 * 2);
    }

    @Override // p000.jz0
    /* renamed from: a5 */
    public final int mo213019a5() {
        return AbstractC0494fg.f56238a1;
    }

    /* JADX WARN: Code restructure failed: missing block: B:34:0x0059, code lost:
    
        m213024b2(r5, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:35:0x005c, code lost:
    
        if (r0 == false) goto L60;
     */
    /* JADX WARN: Code restructure failed: missing block: B:36:0x005e, code lost:
    
        p000.t60.m214692b3(r2);
     */
    /* JADX WARN: Code restructure failed: missing block: B:37:0x0061, code lost:
    
        return;
     */
    /* JADX WARN: Code restructure failed: missing block: B:60:?, code lost:
    
        return;
     */
    @Override // p000.jz0
    /* renamed from: a6 */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final void mo213020a6(int i, InterfaceC0912ng interfaceC0912ng) {
        int i2 = AbstractC0494fg.f56238a1;
        boolean z = i >= i2;
        if (z) {
            i -= i2;
        }
        this.f56647a5.get(i * 2);
        while (true) {
            Object objM213022b0 = m213022b0(i);
            boolean z2 = objM213022b0 instanceof fe1;
            C0786a0 c0786a0 = this.f56646a4;
            if (z2 || (objM213022b0 instanceof ge1)) {
                if (m213021a9(objM213022b0, i, z ? AbstractC0494fg.f56246a9 : AbstractC0494fg.f56247b0)) {
                    m213024b2(i, null);
                    m213023b1(i, !z);
                    if (z) {
                        t60.m214692b3(c0786a0);
                        return;
                    }
                    return;
                }
            } else {
                if (objM213022b0 == AbstractC0494fg.f56246a9 || objM213022b0 == AbstractC0494fg.f56247b0) {
                    break;
                }
                if (objM213022b0 != AbstractC0494fg.f56243a6 && objM213022b0 != AbstractC0494fg.f56242a5) {
                    if (objM213022b0 == AbstractC0494fg.f56245a8 || objM213022b0 == AbstractC0494fg.f56240a3 || objM213022b0 == AbstractC0494fg.f56248b1) {
                        return;
                    }
                    throw new IllegalStateException(("unexpected state: " + objM213022b0).toString());
                }
            }
        }
    }

    /* renamed from: a9 */
    public final boolean m213021a9(Object obj, int i, Object obj2) {
        AtomicReferenceArray atomicReferenceArray;
        int i2 = (i * 2) + 1;
        do {
            atomicReferenceArray = this.f56647a5;
            if (atomicReferenceArray.compareAndSet(i2, obj, obj2)) {
                return true;
            }
        } while (atomicReferenceArray.get(i2) == obj);
        return false;
    }

    /* renamed from: b0 */
    public final Object m213022b0(int i) {
        return this.f56647a5.get((i * 2) + 1);
    }

    /* renamed from: b1 */
    public final void m213023b1(int i, boolean z) {
        long j;
        AtomicLongFieldUpdater atomicLongFieldUpdater;
        long j2;
        if (z) {
            C0786a0 c0786a0 = this.f56646a4;
            t60.m214692b3(c0786a0);
            long j3 = (this.f57401a2 * AbstractC0494fg.f56238a1) + i;
            AtomicLongFieldUpdater atomicLongFieldUpdater2 = C0786a0.f57675a4;
            AtomicLongFieldUpdater atomicLongFieldUpdater3 = C0786a0.f57674a3;
            if (!c0786a0.m213723b3()) {
                while (atomicLongFieldUpdater3.get(c0786a0) <= j3) {
                }
                int i2 = AbstractC0494fg.f56239a2;
                int i3 = 0;
                while (true) {
                    if (i3 < i2) {
                        long j4 = atomicLongFieldUpdater3.get(c0786a0);
                        if (j4 == (4611686018427387903L & atomicLongFieldUpdater2.get(c0786a0)) && j4 == atomicLongFieldUpdater3.get(c0786a0)) {
                            break;
                        } else {
                            i3++;
                        }
                    } else {
                        do {
                            j = atomicLongFieldUpdater2.get(c0786a0);
                        } while (!atomicLongFieldUpdater2.compareAndSet(c0786a0, j, (j & 4611686018427387903L) + 4611686018427387904L));
                        while (true) {
                            long j5 = atomicLongFieldUpdater3.get(c0786a0);
                            atomicLongFieldUpdater = C0786a0.f57675a4;
                            long j6 = atomicLongFieldUpdater.get(c0786a0);
                            long j7 = j6 & 4611686018427387903L;
                            boolean z2 = (j6 & 4611686018427387904L) != 0;
                            if (j5 == j7 && j5 == atomicLongFieldUpdater3.get(c0786a0)) {
                                break;
                            } else if (!z2) {
                                atomicLongFieldUpdater.compareAndSet(c0786a0, j6, 4611686018427387904L + j7);
                            }
                        }
                        do {
                            j2 = atomicLongFieldUpdater.get(c0786a0);
                        } while (!atomicLongFieldUpdater.compareAndSet(c0786a0, j2, j2 & 4611686018427387903L));
                    }
                }
            }
        }
        m213363a7();
    }

    /* renamed from: b2 */
    public final void m213024b2(int i, C1351vv c1351vv) {
        this.f56647a5.lazySet(i * 2, c1351vv);
    }

    /* renamed from: b3 */
    public final void m213025b3(int i, Object obj) {
        this.f56647a5.set((i * 2) + 1, obj);
    }
}
