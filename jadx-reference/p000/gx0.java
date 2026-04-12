package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class gx0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f56588x;

    public gx0() {
        this.f56588x = oh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = oh0.create64();
        fx0.add(this.f56588x, ((gx0) abstractC1330va).f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = oh0.create64();
        fx0.addOne(this.f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof gx0) {
            return oh0.eq64(this.f56588x, ((gx0) obj).f56588x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT113Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 113;
    }

    public int getK1() {
        return 9;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 113;
    }

    public int getRepresentation() {
        return 2;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = oh0.create64();
        fx0.halfTrace(this.f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f56588x, 0, 2) ^ 113009;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = oh0.create64();
        fx0.invert(this.f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return oh0.isOne64(this.f56588x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return oh0.isZero64(this.f56588x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = oh0.create64();
        fx0.multiply(this.f56588x, ((gx0) abstractC1330va).f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f56588x;
        long[] jArr2 = ((gx0) abstractC1330va).f56588x;
        long[] jArr3 = ((gx0) abstractC1330va2).f56588x;
        long[] jArr4 = ((gx0) abstractC1330va3).f56588x;
        long[] jArrCreateExt64 = oh0.createExt64();
        fx0.multiplyAddToExt(jArr, jArr2, jArrCreateExt64);
        fx0.multiplyAddToExt(jArr3, jArr4, jArrCreateExt64);
        long[] jArrCreate64 = oh0.create64();
        fx0.reduce(jArrCreateExt64, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = oh0.create64();
        fx0.sqrt(this.f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = oh0.create64();
        fx0.square(this.f56588x, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f56588x;
        long[] jArr2 = ((gx0) abstractC1330va).f56588x;
        long[] jArr3 = ((gx0) abstractC1330va2).f56588x;
        long[] jArrCreateExt64 = oh0.createExt64();
        fx0.squareAddToExt(jArr, jArrCreateExt64);
        fx0.multiplyAddToExt(jArr2, jArr3, jArrCreateExt64);
        long[] jArrCreate64 = oh0.create64();
        fx0.reduce(jArrCreateExt64, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = oh0.create64();
        fx0.squareN(this.f56588x, i, jArrCreate64);
        return new gx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f56588x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return oh0.toBigInteger64(this.f56588x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return fx0.trace(this.f56588x);
    }

    public gx0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 113) {
            throw new IllegalArgumentException("x value invalid for SecT113FieldElement");
        }
        this.f56588x = fx0.fromBigInteger(bigInteger);
    }

    public gx0(long[] jArr) {
        this.f56588x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
