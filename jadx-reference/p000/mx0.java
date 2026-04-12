package p000;

import java.math.BigInteger;
import p000.AbstractC1330va;

/* loaded from: classes2.dex */
public class mx0 extends AbstractC1330va.a0 {

    /* renamed from: x */
    protected long[] f58408x;

    public mx0() {
        this.f58408x = qh0.create64();
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va add(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = qh0.create64();
        lx0.add(this.f58408x, ((mx0) abstractC1330va).f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va addOne() {
        long[] jArrCreate64 = qh0.create64();
        lx0.addOne(this.f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va divide(AbstractC1330va abstractC1330va) {
        return multiply(abstractC1330va.invert());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof mx0) {
            return qh0.eq64(this.f58408x, ((mx0) obj).f58408x);
        }
        return false;
    }

    @Override // p000.AbstractC1330va
    public String getFieldName() {
        return "SecT131Field";
    }

    @Override // p000.AbstractC1330va
    public int getFieldSize() {
        return 131;
    }

    public int getK1() {
        return 2;
    }

    public int getK2() {
        return 3;
    }

    public int getK3() {
        return 8;
    }

    public int getM() {
        return 131;
    }

    public int getRepresentation() {
        return 3;
    }

    @Override // p000.AbstractC1330va.a0
    public AbstractC1330va halfTrace() {
        long[] jArrCreate64 = qh0.create64();
        lx0.halfTrace(this.f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va.a0
    public boolean hasFastTrace() {
        return true;
    }

    public int hashCode() {
        return C0133bg.hashCode(this.f58408x, 0, 3) ^ 131832;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va invert() {
        long[] jArrCreate64 = qh0.create64();
        lx0.invert(this.f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public boolean isOne() {
        return qh0.isOne64(this.f58408x);
    }

    @Override // p000.AbstractC1330va
    public boolean isZero() {
        return qh0.isZero64(this.f58408x);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiply(AbstractC1330va abstractC1330va) {
        long[] jArrCreate64 = qh0.create64();
        lx0.multiply(this.f58408x, ((mx0) abstractC1330va).f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        return multiplyPlusProduct(abstractC1330va, abstractC1330va2, abstractC1330va3);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va multiplyPlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3) {
        long[] jArr = this.f58408x;
        long[] jArr2 = ((mx0) abstractC1330va).f58408x;
        long[] jArr3 = ((mx0) abstractC1330va2).f58408x;
        long[] jArr4 = ((mx0) abstractC1330va3).f58408x;
        long[] jArrCreate64 = yh0.create64(5);
        lx0.multiplyAddToExt(jArr, jArr2, jArrCreate64);
        lx0.multiplyAddToExt(jArr3, jArr4, jArrCreate64);
        long[] jArrCreate642 = qh0.create64();
        lx0.reduce(jArrCreate64, jArrCreate642);
        return new mx0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va sqrt() {
        long[] jArrCreate64 = qh0.create64();
        lx0.sqrt(this.f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va square() {
        long[] jArrCreate64 = qh0.create64();
        lx0.square(this.f58408x, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squareMinusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return squarePlusProduct(abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePlusProduct(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        long[] jArr = this.f58408x;
        long[] jArr2 = ((mx0) abstractC1330va).f58408x;
        long[] jArr3 = ((mx0) abstractC1330va2).f58408x;
        long[] jArrCreate64 = yh0.create64(5);
        lx0.squareAddToExt(jArr, jArrCreate64);
        lx0.multiplyAddToExt(jArr2, jArr3, jArrCreate64);
        long[] jArrCreate642 = qh0.create64();
        lx0.reduce(jArrCreate64, jArrCreate642);
        return new mx0(jArrCreate642);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va squarePow(int i) {
        if (i < 1) {
            return this;
        }
        long[] jArrCreate64 = qh0.create64();
        lx0.squareN(this.f58408x, i, jArrCreate64);
        return new mx0(jArrCreate64);
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va subtract(AbstractC1330va abstractC1330va) {
        return add(abstractC1330va);
    }

    @Override // p000.AbstractC1330va
    public boolean testBitZero() {
        return (this.f58408x[0] & 1) != 0;
    }

    @Override // p000.AbstractC1330va
    public BigInteger toBigInteger() {
        return qh0.toBigInteger64(this.f58408x);
    }

    @Override // p000.AbstractC1330va.a0
    public int trace() {
        return lx0.trace(this.f58408x);
    }

    public mx0(BigInteger bigInteger) {
        if (bigInteger == null || bigInteger.signum() < 0 || bigInteger.bitLength() > 131) {
            throw new IllegalArgumentException("x value invalid for SecT131FieldElement");
        }
        this.f58408x = lx0.fromBigInteger(bigInteger);
    }

    public mx0(long[] jArr) {
        this.f58408x = jArr;
    }

    @Override // p000.AbstractC1330va
    public AbstractC1330va negate() {
        return this;
    }
}
