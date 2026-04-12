package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* renamed from: oe */
/* loaded from: classes2.dex */
public class C0949oe extends AbstractC1330va.a1 {

    /* renamed from: x */
    protected int[] f58791x;

    /* renamed from: Q */
    public static final BigInteger f58790Q = sh0.toBigInteger(C0948od.f58782P);
    private static final int[] PRECOMP_POW2 = {1242472624, -991028441, -1389370248, 792926214, 1039914919, 726466713, 1338105611, 730014848};

    public C0949oe() {
        this.f58791x = sh0.create();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        C0948od.add(this.f58791x, ((C0949oe) abstractC1330va).f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        int[] iArrCreate = sh0.create();
        C0948od.addOne(this.f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        C0948od.inv(((C0949oe) abstractC1330va).f58791x, iArrCreate);
        C0948od.multiply(iArrCreate, this.f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0949oe) {
            return sh0.m214618eq(this.f58791x, ((C0949oe) obj).f58791x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "Curve25519Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return f58790Q.bitLength();
    }

    public int hashCode() {
        return f58790Q.hashCode() ^ C0133bg.hashCode(this.f58791x, 0, 8);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        int[] iArrCreate = sh0.create();
        C0948od.inv(this.f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return sh0.isOne(this.f58791x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return sh0.isZero(this.f58791x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        C0948od.multiply(this.f58791x, ((C0949oe) abstractC1330va).f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        int[] iArrCreate = sh0.create();
        C0948od.negate(this.f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        int[] iArr = this.f58791x;
        if (sh0.isZero(iArr) || sh0.isOne(iArr)) {
            return this;
        }
        int[] iArrCreate = sh0.create();
        C0948od.square(iArr, iArrCreate);
        C0948od.multiply(iArrCreate, iArr, iArrCreate);
        C0948od.square(iArrCreate, iArrCreate);
        C0948od.multiply(iArrCreate, iArr, iArrCreate);
        int[] iArrCreate2 = sh0.create();
        C0948od.square(iArrCreate, iArrCreate2);
        C0948od.multiply(iArrCreate2, iArr, iArrCreate2);
        int[] iArrCreate3 = sh0.create();
        C0948od.squareN(iArrCreate2, 3, iArrCreate3);
        C0948od.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        C0948od.squareN(iArrCreate3, 4, iArrCreate);
        C0948od.multiply(iArrCreate, iArrCreate2, iArrCreate);
        C0948od.squareN(iArrCreate, 4, iArrCreate3);
        C0948od.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        C0948od.squareN(iArrCreate3, 15, iArrCreate2);
        C0948od.multiply(iArrCreate2, iArrCreate3, iArrCreate2);
        C0948od.squareN(iArrCreate2, 30, iArrCreate3);
        C0948od.multiply(iArrCreate3, iArrCreate2, iArrCreate3);
        C0948od.squareN(iArrCreate3, 60, iArrCreate2);
        C0948od.multiply(iArrCreate2, iArrCreate3, iArrCreate2);
        C0948od.squareN(iArrCreate2, 11, iArrCreate3);
        C0948od.multiply(iArrCreate3, iArrCreate, iArrCreate3);
        C0948od.squareN(iArrCreate3, 120, iArrCreate);
        C0948od.multiply(iArrCreate, iArrCreate2, iArrCreate);
        C0948od.square(iArrCreate, iArrCreate);
        C0948od.square(iArrCreate, iArrCreate2);
        if (sh0.m214618eq(iArr, iArrCreate2)) {
            return new C0949oe(iArrCreate);
        }
        C0948od.multiply(iArrCreate, PRECOMP_POW2, iArrCreate);
        C0948od.square(iArrCreate, iArrCreate2);
        if (sh0.m214618eq(iArr, iArrCreate2)) {
            return new C0949oe(iArrCreate);
        }
        return null;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        int[] iArrCreate = sh0.create();
        C0948od.square(this.f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        int[] iArrCreate = sh0.create();
        C0948od.subtract(this.f58791x, ((C0949oe) abstractC1330va).f58791x, iArrCreate);
        return new C0949oe(iArrCreate);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return sh0.getBit(this.f58791x, 0) == 1;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return sh0.toBigInteger(this.f58791x);
    }

    public C0949oe(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.compareTo(f58790Q) >= 0) {
            throw new IllegalArgumentException("x value invalid for Curve25519FieldElement");
        }
        this.f58791x = C0948od.fromBigInteger(bigInteger);
    }

    public C0949oe(int[] iArr) {
        this.f58791x = iArr;
    }
}
