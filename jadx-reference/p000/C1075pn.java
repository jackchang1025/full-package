package p000;

/* renamed from: pn */
/* loaded from: classes2.dex */
public class C1075pn {
    private int counter;
    private byte[] seed;

    public C1075pn(byte[] bArr, int i) {
        this.seed = C0133bg.clone(bArr);
        this.counter = i;
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1075pn)) {
            return false;
        }
        C1075pn c1075pn = (C1075pn) obj;
        if (c1075pn.counter != this.counter) {
            return false;
        }
        return C0133bg.areEqual(this.seed, c1075pn.seed);
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return C0133bg.clone(this.seed);
    }

    public int hashCode() {
        return this.counter ^ C0133bg.hashCode(this.seed);
    }
}
