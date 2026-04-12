package p000;

/* renamed from: qb */
/* loaded from: classes2.dex */
public class C1104qb {
    private int counter;
    private byte[] seed;
    private int usageIndex;

    public C1104qb(byte[] bArr, int i) {
        this(bArr, i, -1);
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof C1104qb)) {
            return false;
        }
        C1104qb c1104qb = (C1104qb) obj;
        if (c1104qb.counter != this.counter) {
            return false;
        }
        return C0133bg.areEqual(this.seed, c1104qb.seed);
    }

    public int getCounter() {
        return this.counter;
    }

    public byte[] getSeed() {
        return C0133bg.clone(this.seed);
    }

    public int getUsageIndex() {
        return this.usageIndex;
    }

    public int hashCode() {
        return this.counter ^ C0133bg.hashCode(this.seed);
    }

    public C1104qb(byte[] bArr, int i, int i2) {
        this.seed = C0133bg.clone(bArr);
        this.counter = i;
        this.usageIndex = i2;
    }
}
