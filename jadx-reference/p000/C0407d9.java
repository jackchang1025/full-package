package p000;

/* renamed from: d9 */
/* loaded from: classes2.dex */
public final class C0407d9 {
    private final int tagClass;
    private final int tagNumber;

    private C0407d9(int i, int i2) {
        this.tagClass = i;
        this.tagNumber = i2;
    }

    public static C0407d9 create(int i, int i2) {
        return new C0407d9(i, i2);
    }

    public int getTagClass() {
        return this.tagClass;
    }

    public int getTagNumber() {
        return this.tagNumber;
    }
}
