package p014r;

/* renamed from: r.g */
/* loaded from: classes.dex */
public enum EnumC0894g {
    PAIR_DEPT_UNKNOWN(0),
    PAIR_DEPT_PAIR_LEAVE_DEV_OPT(1),
    PAIR_DEPT_PAIR_SUCCESS(2),
    PAIR_DEPT_PAIR_RETRY(3),
    PAIR_DEPT_PAIRING(4),
    PAIR_DEPT_PAIR_FAIL(5),
    PAIR_DEPT_PAIR_PREPARE_FINISH(6),
    PAIR_DEPT_PAIR_FINISH(7);


    /* renamed from: a */
    public final int f1985a;

    EnumC0894g(int i2) {
        this.f1985a = i2;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f1985a + " " + name();
    }
}
