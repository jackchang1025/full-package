package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class wy0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f60977x;

    public wy0() {
        this.f60977x = vh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = vh0.create64();
        vy0.add(this.f60977x, ((wy0) abstractC1330va).f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = vh0.create64();
        vy0.addOne(this.f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof wy0) {
            return vh0.eq64(this.f60977x, ((wy0) obj).f60977x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT409Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 409;
    }

    public int getK1() {
        return 87;
    }

    public int getK2() {
        return 0;
    }

    public int getK3() {
        return 0;
    }

    public int getM() {
        return 409;
    }

    public int getRepresentation() {
        return 2;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = vh0.create64();
        vy0.halfTrace(this.f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f60977x, 0, 7) ^ 4090087;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = vh0.create64();
        vy0.invert(this.f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return vh0.isOne64(this.f60977x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return vh0.isZero64(this.f60977x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = vh0.create64();
        vy0.multiply(this.f60977x, ((wy0) abstractC1330va).f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f60977x;
        long[] jArr2 = ((wy0) abstractC1330va).f60977x;
        long[] jArr3 = ((wy0) abstractC1330va2).f60977x;
        long[] jArr4 = ((wy0) abstractC1330va3).f60977x;
        long[] jArrCreate64 = yh0.create64(13);
        vy0.multiplyAddToExt(jArr, jArr2, jArrCreate64);
        vy0.multiplyAddToExt(jArr3, jArr4, jArrCreate64);
        long[] jArrCreate642 = vh0.create64();
        vy0.reduce(jArrCreate64, jArrCreate642);
        return new wy0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = vh0.create64();
        vy0.sqrt(this.f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = vh0.create64();
        vy0.square(this.f60977x, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f60977x;
        long[] jArr2 = ((wy0) abstractC1330va).f60977x;
        long[] jArr3 = ((wy0) abstractC1330va2).f60977x;
        long[] jArrCreate64 = yh0.create64(13);
        vy0.squareAddToExt(jArr, jArrCreate64);
        vy0.multiplyAddToExt(jArr2, jArr3, jArrCreate64);
        long[] jArrCreate642 = vh0.create64();
        vy0.reduce(jArrCreate64, jArrCreate642);
        return new wy0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = vh0.create64();
        vy0.squareN(this.f60977x, i, jArrCreate64);
        return new wy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f60977x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return vh0.toBigInteger64(this.f60977x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return vy0.trace(this.f60977x);
    }

    public wy0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 409) {
            throw new IllegalArgumentException("x value invalid for SecT409FieldElement");
        }
        this.f60977x = vy0.fromBigInteger(bigInteger);
    }

    public wy0(long[] jArr) {
        this.f60977x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
