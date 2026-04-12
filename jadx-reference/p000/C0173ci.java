package p000;

/* renamed from: ci */
/* loaded from: classes2.dex */
public class C0173ci {
    static final C0176cl EMPTY_SEQUENCE = new C0176cl();
    static final C0178cn EMPTY_SET = new C0178cn();

    public static C0176cl createSequence(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SEQUENCE : new C0176cl(c0118b1);
    }

    public static C0178cn createSet(C0118b1 c0118b1) {
        return c0118b1.size() < 1 ? EMPTY_SET : new C0178cn(c0118b1);
    }
}
