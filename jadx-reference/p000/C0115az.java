package p000;

/* compiled from: r8-map-id-6aa8c380066ac34eca52ba03819884bf743dac35e85c6cbaefaab30e4b90b459 */
/* renamed from: az */
/* loaded from: classes2.dex */
public final class C0115az {
    public /* synthetic */ C0115az(AbstractC1120qr abstractC1120qr) {
        this();
    }

    public final int newCapacity$kotlin_stdlib(int i, int i2) {
        int i3 = i + (i >> 1);
        if (i3 - i2 < 0) {
            i3 = i2;
        }
        return i3 - 2147483639 > 0 ? i2 > 2147483639 ? Integer.MAX_VALUE : 2147483639 : i3;
    }

    private C0115az() {
    }
}
