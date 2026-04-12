package p000;

/* renamed from: ps */
/* loaded from: classes2.dex */
public class C1080ps {
    static final C1082pu EMPTY_SEQUENCE = new C1082pu();
    static final C1085pw EMPTY_SET = new C1085pw();

    public static C1082pu createSequence(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SEQUENCE : new C1082pu(c0118b1);
    }

    public static C1085pw createSet(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SET : new C1085pw(c0118b1);
    }
}
