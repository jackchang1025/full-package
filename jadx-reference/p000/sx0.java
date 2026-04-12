package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class sx0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f60103x;

    public sx0() {
        this.f60103x = qh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = qh0.create64();
        rx0.add(this.f60103x, ((sx0) abstractC1330va).f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = qh0.create64();
        rx0.addOne(this.f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof sx0) {
            return qh0.eq64(this.f60103x, ((sx0) obj).f60103x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT163Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 163;
    }

    public int getK1() {
        return 3;
    }

    public int getK2() {
        return 6;
    }

    public int getK3() {
        return 7;
    }

    public int getM() {
        return 163;
    }

    public int getRepresentation() {
        return 3;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = qh0.create64();
        rx0.halfTrace(this.f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f60103x, 0, 3) ^ 163763;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = qh0.create64();
        rx0.invert(this.f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return qh0.isOne64(this.f60103x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return qh0.isZero64(this.f60103x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = qh0.create64();
        rx0.multiply(this.f60103x, ((sx0) abstractC1330va).f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f60103x;
        long[] jArr2 = ((sx0) abstractC1330va).f60103x;
        long[] jArr3 = ((sx0) abstractC1330va2).f60103x;
        long[] jArr4 = ((sx0) abstractC1330va3).f60103x;
        long[] jArrCreateExt64 = qh0.createExt64();
        rx0.multiplyAddToExt(jArr, jArr2, jArrCreateExt64);
        rx0.multiplyAddToExt(jArr3, jArr4, jArrCreateExt64);
        long[] jArrCreate64 = qh0.create64();
        rx0.reduce(jArrCreateExt64, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = qh0.create64();
        rx0.sqrt(this.f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = qh0.create64();
        rx0.square(this.f60103x, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f60103x;
        long[] jArr2 = ((sx0) abstractC1330va).f60103x;
        long[] jArr3 = ((sx0) abstractC1330va2).f60103x;
        long[] jArrCreateExt64 = qh0.createExt64();
        rx0.squareAddToExt(jArr, jArrCreateExt64);
        rx0.multiplyAddToExt(jArr2, jArr3, jArrCreateExt64);
        long[] jArrCreate64 = qh0.create64();
        rx0.reduce(jArrCreateExt64, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = qh0.create64();
        rx0.squareN(this.f60103x, i, jArrCreate64);
        return new sx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f60103x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return qh0.toBigInteger64(this.f60103x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return rx0.trace(this.f60103x);
    }

    public sx0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 163) {
            throw new IllegalArgumentException("x value invalid for SecT163FieldElement");
        }
        this.f60103x = rx0.fromBigInteger(bigInteger);
    }

    public sx0(long[] jArr) {
        this.f60103x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
