package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: g2 */
/* loaded from: classes2.dex */
public final class C0519g2 extends AbstractC1117qo {
    @Override // p000.AbstractC1117qo
    /* renamed from: a4 */
    public final boolean mo212871a4(AbstractC0521g4 abstractC0521g4, C0487f9 c0487f9, C0487f9 c0487f92) {
        synchronized (abstractC0521g4) {
            try {
                if (abstractC0521g4.f56382a1 != c0487f9) {
                    return false;
                }
                abstractC0521g4.f56382a1 = c0487f92;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: a5 */
    public final boolean mo212872a5(AbstractC0521g4 abstractC0521g4, Object obj, Object obj2) {
        synchronized (abstractC0521g4) {
            try {
                if (abstractC0521g4.f56381a0 != obj) {
                    return false;
                }
                abstractC0521g4.f56381a0 = obj2;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: a6 */
    public final boolean mo212873a6(AbstractC0521g4 abstractC0521g4, C0520g3 c0520g3, C0520g3 c0520g32) {
        synchronized (abstractC0521g4) {
            try {
                if (abstractC0521g4.f56383a2 != c0520g3) {
                    return false;
                }
                abstractC0521g4.f56383a2 = c0520g32;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: f4 */
    public final void mo212874f4(C0520g3 c0520g3, C0520g3 c0520g32) {
        c0520g3.f56371a1 = c0520g32;
    }

    @Override // p000.AbstractC1117qo
    /* renamed from: f5 */
    public final void mo212875f5(C0520g3 c0520g3, Thread thread) {
        c0520g3.f56370a0 = thread;
    }
}
