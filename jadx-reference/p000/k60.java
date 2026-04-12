package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes2.dex */
public class k60 implements Iterable, d80 {

    /* renamed from: a3 */
    public static final j60 f57460a3 = new j60(null);

    /* renamed from: a0 */
    public final int f57461a0;

    /* renamed from: a1 */
    public final int f57462a1;

    /* renamed from: a2 */
    public final int f57463a2;

    public k60(int i, int i2, int i3) {
        if (i3 == 0) {
            throw new IllegalArgumentException("Step must be non-zero.");
        }
        if (i3 == Integer.MIN_VALUE) {
            throw new IllegalArgumentException("Step must be greater than Int.MIN_VALUE to avoid overflow on negation.");
        }
        this.f57461a0 = i;
        this.f57462a1 = kg1.m213513b8(i, i2, i3);
        this.f57463a2 = i3;
    }

    @Override // java.lang.Iterable
    /* renamed from: a0, reason: merged with bridge method [inline-methods] */
    public final l60 iterator() {
        return new l60(this.f57461a0, this.f57462a1, this.f57463a2);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof k60)) {
            return false;
        }
        if (isEmpty() && ((k60) obj).isEmpty()) {
            return true;
        }
        k60 k60Var = (k60) obj;
        return this.f57461a0 == k60Var.f57461a0 && this.f57462a1 == k60Var.f57462a1 && this.f57463a2 == k60Var.f57463a2;
    }

    public int hashCode() {
        if (isEmpty()) {
            return -1;
        }
        return (((this.f57461a0 * 31) + this.f57462a1) * 31) + this.f57463a2;
    }

    public boolean isEmpty() {
        int i = this.f57463a2;
        int i2 = this.f57462a1;
        int i3 = this.f57461a0;
        return i > 0 ? i3 > i2 : i3 < i2;
    }

    public String toString() {
        StringBuilder sb;
        int i = this.f57462a1;
        int i2 = this.f57461a0;
        int i3 = this.f57463a2;
        if (i3 > 0) {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append("..");
            sb.append(i);
            sb.append(" step ");
            sb.append(i3);
        } else {
            sb = new StringBuilder();
            sb.append(i2);
            sb.append(" downTo ");
            sb.append(i);
            sb.append(" step ");
            sb.append(-i3);
        }
        return sb.toString();
    }
}
