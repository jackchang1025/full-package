package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class qy0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f59566x;

    public qy0() {
        this.f59566x = th0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = th0.create64();
        py0.add(this.f59566x, ((qy0) abstractC1330va).f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = th0.create64();
        py0.addOne(this.f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof qy0) {
            return th0.eq64(this.f59566x, ((qy0) obj).f59566x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT283Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 283;
    }

    public int getK1() {
        return 5;
    }

    public int getK2() {
        return 7;
    }

    public int getK3() {
        return 12;
    }

    public int getM() {
        return 283;
    }

    public int getRepresentation() {
        return 3;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = th0.create64();
        py0.halfTrace(this.f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f59566x, 0, 5) ^ 2831275;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = th0.create64();
        py0.invert(this.f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return th0.isOne64(this.f59566x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return th0.isZero64(this.f59566x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = th0.create64();
        py0.multiply(this.f59566x, ((qy0) abstractC1330va).f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f59566x;
        long[] jArr2 = ((qy0) abstractC1330va).f59566x;
        long[] jArr3 = ((qy0) abstractC1330va2).f59566x;
        long[] jArr4 = ((qy0) abstractC1330va3).f59566x;
        long[] jArrCreate64 = yh0.create64(9);
        py0.multiplyAddToExt(jArr, jArr2, jArrCreate64);
        py0.multiplyAddToExt(jArr3, jArr4, jArrCreate64);
        long[] jArrCreate642 = th0.create64();
        py0.reduce(jArrCreate64, jArrCreate642);
        return new qy0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = th0.create64();
        py0.sqrt(this.f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = th0.create64();
        py0.square(this.f59566x, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f59566x;
        long[] jArr2 = ((qy0) abstractC1330va).f59566x;
        long[] jArr3 = ((qy0) abstractC1330va2).f59566x;
        long[] jArrCreate64 = yh0.create64(9);
        py0.squareAddToExt(jArr, jArrCreate64);
        py0.multiplyAddToExt(jArr2, jArr3, jArrCreate64);
        long[] jArrCreate642 = th0.create64();
        py0.reduce(jArrCreate64, jArrCreate642);
        return new qy0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = th0.create64();
        py0.squareN(this.f59566x, i, jArrCreate64);
        return new qy0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f59566x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return th0.toBigInteger64(this.f59566x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return py0.trace(this.f59566x);
    }

    public qy0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 283) {
            throw new IllegalArgumentException("x value invalid for SecT283FieldElement");
        }
        this.f59566x = py0.fromBigInteger(bigInteger);
    }

    public qy0(long[] jArr) {
        this.f59566x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
