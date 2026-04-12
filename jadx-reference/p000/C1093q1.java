package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: q1 */
/* loaded from: classes.dex */
public final class C1093q1 {

    /* renamed from: a0 */
    public int f59354a0;

    /* renamed from: a1 */
    public int f59355a1;

    /* renamed from: a2 */
    public int f59356a2;

    public final boolean equals(Object obj) {
        if (this != obj) {
            if (obj == null || C1093q1.class != obj.getClass()) {
                return false;
            }
            C1093q1 c1093q1 = (C1093q1) obj;
            int i = this.f59354a0;
            if (i != c1093q1.f59354a0) {
                return false;
            }
            if (i != 8 || Math.abs(this.f59356a2 - this.f59355a1) != 1 || this.f59356a2 != c1093q1.f59355a1 || this.f59355a1 != c1093q1.f59356a2) {
                return this.f59356a2 == c1093q1.f59356a2 && this.f59355a1 == c1093q1.f59355a1;
            }
        }
        return true;
    }

    public final int hashCode() {
        return (((this.f59354a0 * 31) + this.f59355a1) * 31) + this.f59356a2;
    }

    public final String toString() {
        StringBuilder sb = new StringBuilder();
        sb.append(Integer.toHexString(System.identityHashCode(this)));
        sb.append("[");
        int i = this.f59354a0;
        sb.append(i != 1 ? i != 2 ? i != 4 ? i != 8 ? "??" : "mv" : "up" : "rm" : "add");
        sb.append(",s:");
        sb.append(this.f59355a1);
        sb.append("c:");
        sb.append(this.f59356a2);
        sb.append(",p:null]");
        return sb.toString();
    }
}
