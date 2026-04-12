package p000;

/* loaded from: classes2.dex */
public class x10 implements qn0 {
    protected final int[] exponents;

    public x10(int[] iArr) {
        this.exponents = C0133bg.clone(iArr);
    }

    public boolean equals(Object obj) {
        if (this == obj) {
            return true;
        }
        if (obj instanceof x10) {
            return C0133bg.areEqual(this.exponents, ((x10) obj).exponents);
        }
        return false;
    }

    @Override // p000.qn0
    public int getDegree() {
        return this.exponents[r0.length - 1];
    }

    @Override // p000.qn0
    public int[] getExponentsPresent() {
        return C0133bg.clone(this.exponents);
    }

    public int hashCode() {
        return C0133bg.hashCode(this.exponents);
    }
}
