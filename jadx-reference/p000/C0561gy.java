package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: gy */
/* loaded from: classes2.dex */
public final class C0561gy extends C0574ha {

    /* renamed from: a0 */
    public final Throwable f56589a0;

    public C0561gy(Throwable th) {
        this.f56589a0 = th;
    }

    public final boolean equals(Object obj) {
        if (obj instanceof C0561gy) {
            return t60.m214686a2(this.f56589a0, ((C0561gy) obj).f56589a0);
        }
        return false;
    }

    public final int hashCode() {
        Throwable th = this.f56589a0;
        if (th != null) {
            return th.hashCode();
        }
        return 0;
    }

    @Override // p000.C0574ha
    public final String toString() {
        return "Closed(" + this.f56589a0 + ')';
    }
}
