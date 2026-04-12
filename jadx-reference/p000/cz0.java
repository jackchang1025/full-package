package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class cz0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f55543x;

    public cz0() {
        this.f55543x = xh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = xh0.create64();
        bz0.add(this.f55543x, ((cz0) abstractC1330va).f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = xh0.create64();
        bz0.addOne(this.f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof cz0) {
            return xh0.eq64(this.f55543x, ((cz0) obj).f55543x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT571Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 571;
    }

    public int getK1() {
        return 2;
    }

    public int getK2() {
        return 5;
    }

    public int getK3() {
        return 10;
    }

    public int getM() {
        return 571;
    }

    public int getRepresentation() {
        return 3;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = xh0.create64();
        bz0.halfTrace(this.f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f55543x, 0, 9) ^ 5711052;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = xh0.create64();
        bz0.invert(this.f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return xh0.isOne64(this.f55543x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return xh0.isZero64(this.f55543x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = xh0.create64();
        bz0.multiply(this.f55543x, ((cz0) abstractC1330va).f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f55543x;
        long[] jArr2 = ((cz0) abstractC1330va).f55543x;
        long[] jArr3 = ((cz0) abstractC1330va2).f55543x;
        long[] jArr4 = ((cz0) abstractC1330va3).f55543x;
        long[] jArrCreateExt64 = xh0.createExt64();
        bz0.multiplyAddToExt(jArr, jArr2, jArrCreateExt64);
        bz0.multiplyAddToExt(jArr3, jArr4, jArrCreateExt64);
        long[] jArrCreate64 = xh0.create64();
        bz0.reduce(jArrCreateExt64, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = xh0.create64();
        bz0.sqrt(this.f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = xh0.create64();
        bz0.square(this.f55543x, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f55543x;
        long[] jArr2 = ((cz0) abstractC1330va).f55543x;
        long[] jArr3 = ((cz0) abstractC1330va2).f55543x;
        long[] jArrCreateExt64 = xh0.createExt64();
        bz0.squareAddToExt(jArr, jArrCreateExt64);
        bz0.multiplyAddToExt(jArr2, jArr3, jArrCreateExt64);
        long[] jArrCreate64 = xh0.create64();
        bz0.reduce(jArrCreateExt64, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = xh0.create64();
        bz0.squareN(this.f55543x, i, jArrCreate64);
        return new cz0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f55543x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return xh0.toBigInteger64(this.f55543x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return bz0.trace(this.f55543x);
    }

    public cz0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 571) {
            throw new IllegalArgumentException("x value invalid for SecT571FieldElement");
        }
        this.f55543x = bz0.fromBigInteger(bigInteger);
    }

    public cz0(long[] jArr) {
        this.f55543x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
