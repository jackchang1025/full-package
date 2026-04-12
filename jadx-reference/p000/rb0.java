package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class rb0 extends sb0 {

    /* renamed from: a0 */
    public final C1106qd f59666a0;

    public rb0(C1106qd c1106qd) {
        this.f59666a0 = c1106qd;
    }

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || rb0.class != obj.getClass()) {
            return false;
        }
        return this.f59666a0.equals(((rb0) obj).f59666a0);
    }

    public final int hashCode() {
        return this.f59666a0.hashCode() + (rb0.class.getName().hashCode() * 31);
    }

    public final String toString() {
        return "Success {mOutputData=" + this.f59666a0 + '}';
    }
}
