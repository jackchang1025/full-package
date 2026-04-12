package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public final class pb0 extends sb0 {

    /* renamed from: a0 */
    public final C1106qd f59186a0 = C1106qd.f59467a1;

    public final boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj == null || pb0.class != obj.getClass()) {
            return false;
        }
        return this.f59186a0.equals(((pb0) obj).f59186a0);
    }

    public final int hashCode() {
        return this.f59186a0.hashCode() + (pb0.class.getName().hashCode() * 31);
    }

    public final String toString() {
        return "Failure {mOutputData=" + this.f59186a0 + '}';
    }
}
