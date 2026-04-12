package p000;

/* renamed from: or */
/* loaded from: classes2.dex */
public class C0994or {
    static final C1064pc EMPTY_SEQUENCE = new C1064pc();
    static final C1065pd EMPTY_SET = new C1065pd();

    public static C1064pc createSequence(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SEQUENCE : new C1064pc(c0118b1);
    }

    public static C1065pd createSet(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SET : new C1065pd(c0118b1);
    }
}
