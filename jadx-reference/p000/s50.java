package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* loaded from: classes.dex */
public final class s50 implements nb1 {

    /* renamed from: a0 */
    public final jb1[] f59865a0;

    public s50(jb1... jb1VarArr) {
        t60.m214695b6(jb1VarArr, "initializers");
        this.f59865a0 = jb1VarArr;
    }

    @Override // p000.nb1
    /* renamed from: a1 */
    public final ib1 mo213829a1(Class cls, gh0 gh0Var) {
        ib1 ib1Var = null;
        for (jb1 jb1Var : this.f59865a0) {
            if (jb1Var.f57316a0.equals(cls)) {
                Object objInvoke = jb1Var.f57317a1.invoke(gh0Var);
                ib1Var = objInvoke instanceof ib1 ? (ib1) objInvoke : null;
            }
        }
        if (ib1Var != null) {
            return ib1Var;
        }
        throw new IllegalArgumentException("No initializer set for given class ".concat(cls.getName()));
    }
}
