package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class my0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f58409x;

    public my0() {
        this.f58409x = sh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = sh0.create64();
        ly0.add(this.f58409x, ((my0) abstractC1330va).f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = sh0.create64();
        ly0.addOne(this.f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof my0) {
            return sh0.eq64(this.f58409x, ((my0) obj).f58409x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT239Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 239;
    }

    public int getK1() {
        return 158;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 239;
    }

    public int getRepresentation() {
        return 2;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = sh0.create64();
        ly0.halfTrace(this.f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f58409x, 0, 4) ^ 23900158;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = sh0.create64();
        ly0.invert(this.f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return sh0.isOne64(this.f58409x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return sh0.isZero64(this.f58409x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = sh0.create64();
        ly0.multiply(this.f58409x, ((my0) abstractC1330va).f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f58409x;
        long[] jArr2 = ((my0) abstractC1330va).f58409x;
        long[] jArr3 = ((my0) abstractC1330va2).f58409x;
        long[] jArr4 = ((my0) abstractC1330va3).f58409x;
        long[] jArrCreateExt64 = sh0.createExt64();
        ly0.multiplyAddToExt(jArr, jArr2, jArrCreateExt64);
        ly0.multiplyAddToExt(jArr3, jArr4, jArrCreateExt64);
        long[] jArrCreate64 = sh0.create64();
        ly0.reduce(jArrCreateExt64, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = sh0.create64();
        ly0.sqrt(this.f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = sh0.create64();
        ly0.square(this.f58409x, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f58409x;
        long[] jArr2 = ((my0) abstractC1330va).f58409x;
        long[] jArr3 = ((my0) abstractC1330va2).f58409x;
        long[] jArrCreateExt64 = sh0.createExt64();
        ly0.squareAddToExt(jArr, jArrCreateExt64);
        ly0.multiplyAddToExt(jArr2, jArr3, jArrCreateExt64);
        long[] jArrCreate64 = sh0.create64();
        ly0.reduce(jArrCreateExt64, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = sh0.create64();
        ly0.squareN(this.f58409x, i, jArrCreate64);
        return new my0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f58409x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return sh0.toBigInteger64(this.f58409x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return ly0.trace(this.f58409x);
    }

    public my0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 239) {
            throw new IllegalArgumentException("x value invalid for SecT239FieldElement");
        }
        this.f58409x = ly0.fromBigInteger(bigInteger);
    }

    public my0(long[] jArr) {
        this.f58409x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
