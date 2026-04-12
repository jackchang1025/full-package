package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: h7 */
/* loaded from: classes.dex */
public final class C0571h7 extends t60 {
    @Override // p000.t60
    /* renamed from: b0 */
    public final boolean mo212999b0(AbstractC0573h9 abstractC0573h9, C0569h5 c0569h5, C0569h5 c0569h52) {
        synchronized (abstractC0573h9) {
            try {
                if (abstractC0573h9.f56633a1 != c0569h5) {
                    return false;
                }
                abstractC0573h9.f56633a1 = c0569h52;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.t60
    /* renamed from: b1 */
    public final boolean mo213000b1(AbstractC0573h9 abstractC0573h9, Object obj, Object obj2) {
        synchronized (abstractC0573h9) {
            try {
                if (abstractC0573h9.f56632a0 != obj) {
                    return false;
                }
                abstractC0573h9.f56632a0 = obj2;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.t60
    /* renamed from: b2 */
    public final boolean mo213001b2(AbstractC0573h9 abstractC0573h9, C0572h8 c0572h8, C0572h8 c0572h82) {
        synchronized (abstractC0573h9) {
            try {
                if (abstractC0573h9.f56634a2 != c0572h8) {
                    return false;
                }
                abstractC0573h9.f56634a2 = c0572h82;
                return true;
            } catch (Throwable th) {
                throw th;
            }
        }
    }

    @Override // p000.t60
    /* renamed from: e3 */
    public final void mo213002e3(C0572h8 c0572h8, C0572h8 c0572h82) {
        c0572h8.f56627a1 = c0572h82;
    }

    @Override // p000.t60
    /* renamed from: e4 */
    public final void mo213003e4(C0572h8 c0572h8, Thread thread) {
        c0572h8.f56626a0 = thread;
    }
}
