package p014r;

/* renamed from: r.d */
/* loaded from: classes.dex */
public enum EnumC0891d {
    INTERACTIVE_STATUS_UNKNOWN(-1),
    INTERACTIVE_STATUS_IDLE(0),
    /* JADX INFO: Fake field, exist only in values array */
    PASSIVE_INTERACTIVE_BUSY(1),
    USER_INTERACTIVE_BUSY(2),
    /* JADX INFO: Fake field, exist only in values array */
    INTERACTIVE_STATUS_ANY(3);


    /* renamed from: a */
    public final int f1956a;

    EnumC0891d(int i2) {
        this.f1956a = i2;
    }

    @Override // java.lang.Enum
    public final String toString() {
        return this.f1956a + " " + name();
    }
}
