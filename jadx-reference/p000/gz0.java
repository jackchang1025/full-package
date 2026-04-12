package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class gz0 extends AbstractC1341vl.a1 {
    public gz0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
        char c;
        long[] jArr;
        long[] jArr2;
        long[] jArr3;
        cz0 cz0Var;
        cz0 cz0Var2;
        cz0 cz0Var3;
        if (isInfinity()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return this;
        }
        AbstractC1316ux curve = getCurve();
        cz0 cz0Var4 = (cz0) this.f60653x;
        cz0 cz0Var5 = (cz0) abstractC1341vl.getRawXCoord();
        if (cz0Var4.isZero()) {
            return cz0Var5.isZero() ? curve.getInfinity() : abstractC1341vl.add(this);
        }
        cz0 cz0Var6 = (cz0) this.f60654y;
        cz0 cz0Var7 = (cz0) this.f60655zs[0];
        cz0 cz0Var8 = (cz0) abstractC1341vl.getRawYCoord();
        cz0 cz0Var9 = (cz0) abstractC1341vl.getZCoord(0);
        long[] jArrCreate64 = xh0.create64();
        long[] jArrCreate642 = xh0.create64();
        long[] jArrCreate643 = xh0.create64();
        long[] jArrCreate644 = xh0.create64();
        long[] jArrPrecompMultiplicand = cz0Var7.isOne() ? null : bz0.precompMultiplicand(cz0Var7.f55543x);
        if (jArrPrecompMultiplicand == null) {
            jArr = cz0Var5.f55543x;
            c = 0;
            jArr2 = cz0Var8.f55543x;
        } else {
            c = 0;
            bz0.multiplyPrecomp(cz0Var5.f55543x, jArrPrecompMultiplicand, jArrCreate642);
            bz0.multiplyPrecomp(cz0Var8.f55543x, jArrPrecompMultiplicand, jArrCreate644);
            jArr = jArrCreate642;
            jArr2 = jArrCreate644;
        }
        long[] jArrPrecompMultiplicand2 = cz0Var9.isOne() ? null : bz0.precompMultiplicand(cz0Var9.f55543x);
        long[] jArr4 = cz0Var4.f55543x;
        if (jArrPrecompMultiplicand2 == null) {
            jArr3 = cz0Var6.f55543x;
        } else {
            bz0.multiplyPrecomp(jArr4, jArrPrecompMultiplicand2, jArrCreate64);
            bz0.multiplyPrecomp(cz0Var6.f55543x, jArrPrecompMultiplicand2, jArrCreate643);
            jArr4 = jArrCreate64;
            jArr3 = jArrCreate643;
        }
        bz0.add(jArr3, jArr2, jArrCreate643);
        bz0.add(jArr4, jArr, jArrCreate644);
        if (xh0.isZero64(jArrCreate644)) {
            return xh0.isZero64(jArrCreate643) ? twice() : curve.getInfinity();
        }
        if (cz0Var5.isZero()) {
            AbstractC1341vl abstractC1341vlNormalize = normalize();
            cz0 cz0Var10 = (cz0) abstractC1341vlNormalize.getXCoord();
            AbstractC1330va yCoord = abstractC1341vlNormalize.getYCoord();
            AbstractC1330va abstractC1330vaDivide = yCoord.add(cz0Var8).divide(cz0Var10);
            cz0Var = (cz0) abstractC1330vaDivide.square().add(abstractC1330vaDivide).add(cz0Var10).addOne();
            if (cz0Var.isZero()) {
                return new gz0(curve, cz0Var, fz0.SecT571R1_B_SQRT);
            }
            cz0Var2 = (cz0) abstractC1330vaDivide.multiply(cz0Var10.add(cz0Var)).add(cz0Var).add(yCoord).divide(cz0Var).add(cz0Var);
            cz0Var3 = (cz0) curve.fromBigInteger(InterfaceC1315uw.ONE);
        } else {
            bz0.square(jArrCreate644, jArrCreate644);
            long[] jArrPrecompMultiplicand3 = bz0.precompMultiplicand(jArrCreate643);
            bz0.multiplyPrecomp(jArr4, jArrPrecompMultiplicand3, jArrCreate64);
            bz0.multiplyPrecomp(jArr, jArrPrecompMultiplicand3, jArrCreate642);
            cz0 cz0Var11 = new cz0(jArrCreate64);
            bz0.multiply(jArrCreate64, jArrCreate642, cz0Var11.f55543x);
            if (cz0Var11.isZero()) {
                return new gz0(curve, cz0Var11, fz0.SecT571R1_B_SQRT);
            }
            cz0 cz0Var12 = new cz0(jArrCreate643);
            bz0.multiplyPrecomp(jArrCreate644, jArrPrecompMultiplicand3, cz0Var12.f55543x);
            if (jArrPrecompMultiplicand2 != null) {
                long[] jArr5 = cz0Var12.f55543x;
                bz0.multiplyPrecomp(jArr5, jArrPrecompMultiplicand2, jArr5);
            }
            long[] jArrCreateExt64 = xh0.createExt64();
            bz0.add(jArrCreate642, jArrCreate644, jArrCreate644);
            bz0.squareAddToExt(jArrCreate644, jArrCreateExt64);
            bz0.add(cz0Var6.f55543x, cz0Var7.f55543x, jArrCreate644);
            bz0.multiplyAddToExt(jArrCreate644, cz0Var12.f55543x, jArrCreateExt64);
            cz0 cz0Var13 = new cz0(jArrCreate644);
            bz0.reduce(jArrCreateExt64, cz0Var13.f55543x);
            if (jArrPrecompMultiplicand != null) {
                long[] jArr6 = cz0Var12.f55543x;
                bz0.multiplyPrecomp(jArr6, jArrPrecompMultiplicand, jArr6);
            }
            cz0Var = cz0Var11;
            cz0Var2 = cz0Var13;
            cz0Var3 = cz0Var12;
        }
        AbstractC1330va[] abstractC1330vaArr = new AbstractC1330va[1];
        abstractC1330vaArr[c] = cz0Var3;
        return new gz0(curve, cz0Var, cz0Var2, abstractC1330vaArr);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new gz0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public boolean getCompressionYTilde() {
        AbstractC1330va rawXCoord = getRawXCoord();
        return (rawXCoord.isZero() || getRawYCoord().testBitZero() == rawXCoord.testBitZero()) ? false : true;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1330va getYCoord() {
        AbstractC1330va abstractC1330va = this.f60653x;
        AbstractC1330va abstractC1330va2 = this.f60654y;
        if (isInfinity() || abstractC1330va.isZero()) {
            return abstractC1330va2;
        }
        AbstractC1330va abstractC1330vaMultiply = abstractC1330va2.add(abstractC1330va).multiply(abstractC1330va);
        AbstractC1330va abstractC1330va3 = this.f60655zs[0];
        return !abstractC1330va3.isOne() ? abstractC1330vaMultiply.divide(abstractC1330va3) : abstractC1330vaMultiply;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        if (!isInfinity()) {
            AbstractC1330va abstractC1330va = this.f60653x;
            if (!abstractC1330va.isZero()) {
                AbstractC1330va abstractC1330va2 = this.f60654y;
                AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                return new gz0(this.curve, abstractC1330va, abstractC1330va2.add(abstractC1330va3), new AbstractC1330va[]{abstractC1330va3});
            }
        }
        return this;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twice() {
        long[] jArr;
        if (isInfinity()) {
            return this;
        }
        AbstractC1316ux curve = getCurve();
        cz0 cz0Var = (cz0) this.f60653x;
        if (cz0Var.isZero()) {
            return curve.getInfinity();
        }
        cz0 cz0Var2 = (cz0) this.f60654y;
        cz0 cz0Var3 = (cz0) this.f60655zs[0];
        long[] jArrCreate64 = xh0.create64();
        long[] jArrCreate642 = xh0.create64();
        long[] jArrPrecompMultiplicand = cz0Var3.isOne() ? null : bz0.precompMultiplicand(cz0Var3.f55543x);
        long[] jArr2 = cz0Var2.f55543x;
        if (jArrPrecompMultiplicand == null) {
            jArr = cz0Var3.f55543x;
        } else {
            bz0.multiplyPrecomp(jArr2, jArrPrecompMultiplicand, jArrCreate64);
            bz0.square(cz0Var3.f55543x, jArrCreate642);
            jArr2 = jArrCreate64;
            jArr = jArrCreate642;
        }
        long[] jArrCreate643 = xh0.create64();
        bz0.square(cz0Var2.f55543x, jArrCreate643);
        bz0.addBothTo(jArr2, jArr, jArrCreate643);
        if (xh0.isZero64(jArrCreate643)) {
            return new gz0(curve, new cz0(jArrCreate643), fz0.SecT571R1_B_SQRT);
        }
        long[] jArrCreateExt64 = xh0.createExt64();
        bz0.multiplyAddToExt(jArrCreate643, jArr2, jArrCreateExt64);
        cz0 cz0Var4 = new cz0(jArrCreate64);
        bz0.square(jArrCreate643, cz0Var4.f55543x);
        cz0 cz0Var5 = new cz0(jArrCreate643);
        if (jArrPrecompMultiplicand != null) {
            long[] jArr3 = cz0Var5.f55543x;
            bz0.multiply(jArr3, jArr, jArr3);
        }
        long[] jArr4 = cz0Var.f55543x;
        if (jArrPrecompMultiplicand != null) {
            bz0.multiplyPrecomp(jArr4, jArrPrecompMultiplicand, jArrCreate642);
            jArr4 = jArrCreate642;
        }
        bz0.squareAddToExt(jArr4, jArrCreateExt64);
        bz0.reduce(jArrCreateExt64, jArrCreate642);
        bz0.addBothTo(cz0Var4.f55543x, cz0Var5.f55543x, jArrCreate642);
        return new gz0(curve, cz0Var4, new cz0(jArrCreate642), new AbstractC1330va[]{cz0Var5});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        if (isInfinity()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return twice();
        }
        AbstractC1316ux curve = getCurve();
        cz0 cz0Var = (cz0) this.f60653x;
        if (cz0Var.isZero()) {
            return abstractC1341vl;
        }
        cz0 cz0Var2 = (cz0) abstractC1341vl.getRawXCoord();
        cz0 cz0Var3 = (cz0) abstractC1341vl.getZCoord(0);
        if (cz0Var2.isZero() || !cz0Var3.isOne()) {
            return twice().add(abstractC1341vl);
        }
        cz0 cz0Var4 = (cz0) this.f60654y;
        cz0 cz0Var5 = (cz0) this.f60655zs[0];
        cz0 cz0Var6 = (cz0) abstractC1341vl.getRawYCoord();
        long[] jArrCreate64 = xh0.create64();
        long[] jArrCreate642 = xh0.create64();
        long[] jArrCreate643 = xh0.create64();
        long[] jArrCreate644 = xh0.create64();
        bz0.square(cz0Var.f55543x, jArrCreate64);
        bz0.square(cz0Var4.f55543x, jArrCreate642);
        bz0.square(cz0Var5.f55543x, jArrCreate643);
        bz0.multiply(cz0Var4.f55543x, cz0Var5.f55543x, jArrCreate644);
        bz0.addBothTo(jArrCreate643, jArrCreate642, jArrCreate644);
        long[] jArrPrecompMultiplicand = bz0.precompMultiplicand(jArrCreate643);
        bz0.multiplyPrecomp(cz0Var6.f55543x, jArrPrecompMultiplicand, jArrCreate643);
        bz0.add(jArrCreate643, jArrCreate642, jArrCreate643);
        long[] jArrCreateExt64 = xh0.createExt64();
        bz0.multiplyAddToExt(jArrCreate643, jArrCreate644, jArrCreateExt64);
        bz0.multiplyPrecompAddToExt(jArrCreate64, jArrPrecompMultiplicand, jArrCreateExt64);
        bz0.reduce(jArrCreateExt64, jArrCreate643);
        bz0.multiplyPrecomp(cz0Var2.f55543x, jArrPrecompMultiplicand, jArrCreate64);
        bz0.add(jArrCreate64, jArrCreate644, jArrCreate642);
        bz0.square(jArrCreate642, jArrCreate642);
        if (xh0.isZero64(jArrCreate642)) {
            return xh0.isZero64(jArrCreate643) ? abstractC1341vl.twice() : curve.getInfinity();
        }
        if (xh0.isZero64(jArrCreate643)) {
            return new gz0(curve, new cz0(jArrCreate643), fz0.SecT571R1_B_SQRT);
        }
        cz0 cz0Var7 = new cz0();
        bz0.square(jArrCreate643, cz0Var7.f55543x);
        long[] jArr = cz0Var7.f55543x;
        bz0.multiply(jArr, jArrCreate64, jArr);
        cz0 cz0Var8 = new cz0(jArrCreate64);
        bz0.multiply(jArrCreate643, jArrCreate642, cz0Var8.f55543x);
        long[] jArr2 = cz0Var8.f55543x;
        bz0.multiplyPrecomp(jArr2, jArrPrecompMultiplicand, jArr2);
        cz0 cz0Var9 = new cz0(jArrCreate642);
        bz0.add(jArrCreate643, jArrCreate642, cz0Var9.f55543x);
        long[] jArr3 = cz0Var9.f55543x;
        bz0.square(jArr3, jArr3);
        yh0.zero64(18, jArrCreateExt64);
        bz0.multiplyAddToExt(cz0Var9.f55543x, jArrCreate644, jArrCreateExt64);
        bz0.addOne(cz0Var6.f55543x, jArrCreate644);
        bz0.multiplyAddToExt(jArrCreate644, cz0Var8.f55543x, jArrCreateExt64);
        bz0.reduce(jArrCreateExt64, cz0Var9.f55543x);
        return new gz0(curve, cz0Var7, cz0Var9, new AbstractC1330va[]{cz0Var8});
    }

    public gz0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
