package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class qv0 extends AbstractC1341vl.a2 {
    public qv0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        xv0 xv0Var = (xv0) this.f60653x;
        xv0 xv0Var2 = (xv0) this.f60654y;
        xv0 xv0Var3 = (xv0) abstractC1341vl.getXCoord();
        xv0 xv0Var4 = (xv0) abstractC1341vl.getYCoord();
        xv0 xv0Var5 = (xv0) this.f60655zs[0];
        xv0 xv0Var6 = (xv0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = ph0.createExt();
        int[] iArrCreate = ph0.create();
        int[] iArrCreate2 = ph0.create();
        int[] iArrCreate3 = ph0.create();
        boolean zIsOne = xv0Var5.isOne();
        if (zIsOne) {
            iArr = xv0Var3.f61197x;
            iArr2 = xv0Var4.f61197x;
        } else {
            wv0.square(xv0Var5.f61197x, iArrCreate2);
            wv0.multiply(iArrCreate2, xv0Var3.f61197x, iArrCreate);
            wv0.multiply(iArrCreate2, xv0Var5.f61197x, iArrCreate2);
            wv0.multiply(iArrCreate2, xv0Var4.f61197x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = xv0Var6.isOne();
        if (zIsOne2) {
            iArr3 = xv0Var.f61197x;
            iArr4 = xv0Var2.f61197x;
        } else {
            wv0.square(xv0Var6.f61197x, iArrCreate3);
            wv0.multiply(iArrCreate3, xv0Var.f61197x, iArrCreateExt);
            wv0.multiply(iArrCreate3, xv0Var6.f61197x, iArrCreate3);
            wv0.multiply(iArrCreate3, xv0Var2.f61197x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = ph0.create();
        wv0.subtract(iArr3, iArr, iArrCreate4);
        wv0.subtract(iArr4, iArr2, iArrCreate);
        if (ph0.isZero(iArrCreate4)) {
            return ph0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        wv0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = ph0.create();
        wv0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        wv0.multiply(iArrCreate2, iArr3, iArrCreate2);
        wv0.negate(iArrCreate5, iArrCreate5);
        ph0.mul(iArr4, iArrCreate5, iArrCreateExt);
        wv0.reduce32(ph0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        xv0 xv0Var7 = new xv0(iArrCreate3);
        wv0.square(iArrCreate, xv0Var7.f61197x);
        int[] iArr5 = xv0Var7.f61197x;
        wv0.subtract(iArr5, iArrCreate5, iArr5);
        xv0 xv0Var8 = new xv0(iArrCreate5);
        wv0.subtract(iArrCreate2, xv0Var7.f61197x, xv0Var8.f61197x);
        wv0.multiplyAddToExt(xv0Var8.f61197x, iArrCreate, iArrCreateExt);
        wv0.reduce(iArrCreateExt, xv0Var8.f61197x);
        xv0 xv0Var9 = new xv0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = xv0Var9.f61197x;
            wv0.multiply(iArr6, xv0Var5.f61197x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = xv0Var9.f61197x;
            wv0.multiply(iArr7, xv0Var6.f61197x, iArr7);
        }
        return new qv0(curve, xv0Var7, xv0Var8, new AbstractC1330va[]{xv0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new qv0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new qv0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        xv0 xv0Var = (xv0) this.f60654y;
        if (xv0Var.isZero()) {
            return curve.getInfinity();
        }
        xv0 xv0Var2 = (xv0) this.f60653x;
        xv0 xv0Var3 = (xv0) this.f60655zs[0];
        int[] iArrCreate = ph0.create();
        wv0.square(xv0Var.f61197x, iArrCreate);
        int[] iArrCreate2 = ph0.create();
        wv0.square(iArrCreate, iArrCreate2);
        int[] iArrCreate3 = ph0.create();
        wv0.square(xv0Var2.f61197x, iArrCreate3);
        wv0.reduce32(ph0.addBothTo(iArrCreate3, iArrCreate3, iArrCreate3), iArrCreate3);
        wv0.multiply(iArrCreate, xv0Var2.f61197x, iArrCreate);
        wv0.reduce32(yh0.shiftUpBits(5, iArrCreate, 2, 0), iArrCreate);
        int[] iArrCreate4 = ph0.create();
        wv0.reduce32(yh0.shiftUpBits(5, iArrCreate2, 3, 0, iArrCreate4), iArrCreate4);
        xv0 xv0Var4 = new xv0(iArrCreate2);
        wv0.square(iArrCreate3, xv0Var4.f61197x);
        int[] iArr = xv0Var4.f61197x;
        wv0.subtract(iArr, iArrCreate, iArr);
        int[] iArr2 = xv0Var4.f61197x;
        wv0.subtract(iArr2, iArrCreate, iArr2);
        xv0 xv0Var5 = new xv0(iArrCreate);
        wv0.subtract(iArrCreate, xv0Var4.f61197x, xv0Var5.f61197x);
        int[] iArr3 = xv0Var5.f61197x;
        wv0.multiply(iArr3, iArrCreate3, iArr3);
        int[] iArr4 = xv0Var5.f61197x;
        wv0.subtract(iArr4, iArrCreate4, iArr4);
        xv0 xv0Var6 = new xv0(iArrCreate3);
        wv0.twice(xv0Var.f61197x, xv0Var6.f61197x);
        if (!xv0Var3.isOne()) {
            int[] iArr5 = xv0Var6.f61197x;
            wv0.multiply(iArr5, xv0Var3.f61197x, iArr5);
        }
        return new qv0(curve, xv0Var4, xv0Var5, new AbstractC1330va[]{xv0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public qv0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
