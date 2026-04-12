package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class kw0 extends AbstractC1341vl.a2 {
    public kw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
        int[] iArr;
        int[] iArr2;
        int[] iArr3;
        int[] iArr4;
        if (isInfinity()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return this;
        }
        if (this == abstractC1341vl) {
            return twice();
        }
        AbstractC1316ux curve = getCurve();
        jw0 jw0Var = (jw0) this.f60653x;
        jw0 jw0Var2 = (jw0) this.f60654y;
        jw0 jw0Var3 = (jw0) abstractC1341vl.getXCoord();
        jw0 jw0Var4 = (jw0) abstractC1341vl.getYCoord();
        jw0 jw0Var5 = (jw0) this.f60655zs[0];
        jw0 jw0Var6 = (jw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = rh0.createExt();
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        int[] iArrCreate3 = rh0.create();
        boolean zIsOne = jw0Var5.isOne();
        if (zIsOne) {
            iArr = jw0Var3.f57390x;
            iArr2 = jw0Var4.f57390x;
        } else {
            iw0.square(jw0Var5.f57390x, iArrCreate2);
            iw0.multiply(iArrCreate2, jw0Var3.f57390x, iArrCreate);
            iw0.multiply(iArrCreate2, jw0Var5.f57390x, iArrCreate2);
            iw0.multiply(iArrCreate2, jw0Var4.f57390x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = jw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = jw0Var.f57390x;
            iArr4 = jw0Var2.f57390x;
        } else {
            iw0.square(jw0Var6.f57390x, iArrCreate3);
            iw0.multiply(iArrCreate3, jw0Var.f57390x, iArrCreateExt);
            iw0.multiply(iArrCreate3, jw0Var6.f57390x, iArrCreate3);
            iw0.multiply(iArrCreate3, jw0Var2.f57390x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = rh0.create();
        iw0.subtract(iArr3, iArr, iArrCreate4);
        iw0.subtract(iArr4, iArr2, iArrCreate);
        if (rh0.isZero(iArrCreate4)) {
            return rh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        iw0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = rh0.create();
        iw0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        iw0.multiply(iArrCreate2, iArr3, iArrCreate2);
        iw0.negate(iArrCreate5, iArrCreate5);
        rh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        iw0.reduce32(rh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        jw0 jw0Var7 = new jw0(iArrCreate3);
        iw0.square(iArrCreate, jw0Var7.f57390x);
        int[] iArr5 = jw0Var7.f57390x;
        iw0.subtract(iArr5, iArrCreate5, iArr5);
        jw0 jw0Var8 = new jw0(iArrCreate5);
        iw0.subtract(iArrCreate2, jw0Var7.f57390x, jw0Var8.f57390x);
        iw0.multiplyAddToExt(jw0Var8.f57390x, iArrCreate, iArrCreateExt);
        iw0.reduce(iArrCreateExt, jw0Var8.f57390x);
        jw0 jw0Var9 = new jw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = jw0Var9.f57390x;
            iw0.multiply(iArr6, jw0Var5.f57390x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = jw0Var9.f57390x;
            iw0.multiply(iArr7, jw0Var6.f57390x, iArr7);
        }
        return new kw0(curve, jw0Var7, jw0Var8, new AbstractC1330va[]{jw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new kw0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new kw0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl threeTimes() {
        return (isInfinity() || this.f60654y.isZero()) ? this : twice().add(this);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twice() {
        if (isInfinity()) {
            return this;
        }
        AbstractC1316ux curve = getCurve();
        jw0 jw0Var = (jw0) this.f60654y;
        if (jw0Var.isZero()) {
            return curve.getInfinity();
        }
        jw0 jw0Var2 = (jw0) this.f60653x;
        jw0 jw0Var3 = (jw0) this.f60655zs[0];
        int[] iArrCreate = rh0.create();
        iw0.square(jw0Var.f57390x, iArrCreate);
        int[] iArrCreate2 = rh0.create();
        iw0.square(iArrCreate, iArrCreate2);
        int[] iArrCreate3 = rh0.create();
        iw0.square(jw0Var2.f57390x, iArrCreate3);
        iw0.reduce32(rh0.addBothTo(iArrCreate3, iArrCreate3, iArrCreate3), iArrCreate3);
        iw0.multiply(iArrCreate, jw0Var2.f57390x, iArrCreate);
        iw0.reduce32(yh0.shiftUpBits(7, iArrCreate, 2, 0), iArrCreate);
        int[] iArrCreate4 = rh0.create();
        iw0.reduce32(yh0.shiftUpBits(7, iArrCreate2, 3, 0, iArrCreate4), iArrCreate4);
        jw0 jw0Var4 = new jw0(iArrCreate2);
        iw0.square(iArrCreate3, jw0Var4.f57390x);
        int[] iArr = jw0Var4.f57390x;
        iw0.subtract(iArr, iArrCreate, iArr);
        int[] iArr2 = jw0Var4.f57390x;
        iw0.subtract(iArr2, iArrCreate, iArr2);
        jw0 jw0Var5 = new jw0(iArrCreate);
        iw0.subtract(iArrCreate, jw0Var4.f57390x, jw0Var5.f57390x);
        int[] iArr3 = jw0Var5.f57390x;
        iw0.multiply(iArr3, iArrCreate3, iArr3);
        int[] iArr4 = jw0Var5.f57390x;
        iw0.subtract(iArr4, iArrCreate4, iArr4);
        jw0 jw0Var6 = new jw0(iArrCreate3);
        iw0.twice(jw0Var.f57390x, jw0Var6.f57390x);
        if (!jw0Var3.isOne()) {
            int[] iArr5 = jw0Var6.f57390x;
            iw0.multiply(iArr5, jw0Var3.f57390x, iArr5);
        }
        return new kw0(curve, jw0Var4, jw0Var5, new AbstractC1330va[]{jw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public kw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
