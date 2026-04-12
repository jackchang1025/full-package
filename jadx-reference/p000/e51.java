package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class e51 implements Comparable {

    /* renamed from: a0 */
    public final int f55932a0;

    /* renamed from: a1 */
    public final int f55933a1;

    /* renamed from: a2 */
    public final String f55934a2;

    /* renamed from: a3 */
    public final String f55935a3;

    public e51(String str, String str2, int i, int i2) {
        this.f55932a0 = i;
        this.f55933a1 = i2;
        this.f55934a2 = str;
        this.f55935a3 = str2;
    }

    @Override // java.lang.Comparable
    public final int compareTo(Object obj) {
        e51 e51Var = (e51) obj;
        t60.m214695b6(e51Var, "other");
        int i = this.f55932a0 - e51Var.f55932a0;
        return i == 0 ? this.f55933a1 - e51Var.f55933a1 : i;
    }
}
