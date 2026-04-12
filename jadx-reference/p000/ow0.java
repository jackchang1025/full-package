package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class ow0 extends AbstractC1341vl.a2 {
    public ow0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        nw0 nw0Var = (nw0) this.f60653x;
        nw0 nw0Var2 = (nw0) this.f60654y;
        nw0 nw0Var3 = (nw0) abstractC1341vl.getXCoord();
        nw0 nw0Var4 = (nw0) abstractC1341vl.getYCoord();
        nw0 nw0Var5 = (nw0) this.f60655zs[0];
        nw0 nw0Var6 = (nw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = rh0.createExt();
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        int[] iArrCreate3 = rh0.create();
        boolean zIsOne = nw0Var5.isOne();
        if (zIsOne) {
            iArr = nw0Var3.f58700x;
            iArr2 = nw0Var4.f58700x;
        } else {
            mw0.square(nw0Var5.f58700x, iArrCreate2);
            mw0.multiply(iArrCreate2, nw0Var3.f58700x, iArrCreate);
            mw0.multiply(iArrCreate2, nw0Var5.f58700x, iArrCreate2);
            mw0.multiply(iArrCreate2, nw0Var4.f58700x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = nw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = nw0Var.f58700x;
            iArr4 = nw0Var2.f58700x;
        } else {
            mw0.square(nw0Var6.f58700x, iArrCreate3);
            mw0.multiply(iArrCreate3, nw0Var.f58700x, iArrCreateExt);
            mw0.multiply(iArrCreate3, nw0Var6.f58700x, iArrCreate3);
            mw0.multiply(iArrCreate3, nw0Var2.f58700x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = rh0.create();
        mw0.subtract(iArr3, iArr, iArrCreate4);
        mw0.subtract(iArr4, iArr2, iArrCreate);
        if (rh0.isZero(iArrCreate4)) {
            return rh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        mw0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = rh0.create();
        mw0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        mw0.multiply(iArrCreate2, iArr3, iArrCreate2);
        mw0.negate(iArrCreate5, iArrCreate5);
        rh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        mw0.reduce32(rh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        nw0 nw0Var7 = new nw0(iArrCreate3);
        mw0.square(iArrCreate, nw0Var7.f58700x);
        int[] iArr5 = nw0Var7.f58700x;
        mw0.subtract(iArr5, iArrCreate5, iArr5);
        nw0 nw0Var8 = new nw0(iArrCreate5);
        mw0.subtract(iArrCreate2, nw0Var7.f58700x, nw0Var8.f58700x);
        mw0.multiplyAddToExt(nw0Var8.f58700x, iArrCreate, iArrCreateExt);
        mw0.reduce(iArrCreateExt, nw0Var8.f58700x);
        nw0 nw0Var9 = new nw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = nw0Var9.f58700x;
            mw0.multiply(iArr6, nw0Var5.f58700x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = nw0Var9.f58700x;
            mw0.multiply(iArr7, nw0Var6.f58700x, iArr7);
        }
        return new ow0(curve, nw0Var7, nw0Var8, new AbstractC1330va[]{nw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new ow0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new ow0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        nw0 nw0Var = (nw0) this.f60654y;
        if (nw0Var.isZero()) {
            return curve.getInfinity();
        }
        nw0 nw0Var2 = (nw0) this.f60653x;
        nw0 nw0Var3 = (nw0) this.f60655zs[0];
        int[] iArrCreate = rh0.create();
        int[] iArrCreate2 = rh0.create();
        int[] iArrCreate3 = rh0.create();
        mw0.square(nw0Var.f58700x, iArrCreate3);
        int[] iArrCreate4 = rh0.create();
        mw0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = nw0Var3.isOne();
        int[] iArr = nw0Var3.f58700x;
        if (!zIsOne) {
            mw0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        mw0.subtract(nw0Var2.f58700x, iArr, iArrCreate);
        mw0.add(nw0Var2.f58700x, iArr, iArrCreate2);
        mw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        mw0.reduce32(rh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        mw0.multiply(iArrCreate3, nw0Var2.f58700x, iArrCreate3);
        mw0.reduce32(yh0.shiftUpBits(7, iArrCreate3, 2, 0), iArrCreate3);
        mw0.reduce32(yh0.shiftUpBits(7, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        nw0 nw0Var4 = new nw0(iArrCreate4);
        mw0.square(iArrCreate2, nw0Var4.f58700x);
        int[] iArr2 = nw0Var4.f58700x;
        mw0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = nw0Var4.f58700x;
        mw0.subtract(iArr3, iArrCreate3, iArr3);
        nw0 nw0Var5 = new nw0(iArrCreate3);
        mw0.subtract(iArrCreate3, nw0Var4.f58700x, nw0Var5.f58700x);
        int[] iArr4 = nw0Var5.f58700x;
        mw0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = nw0Var5.f58700x;
        mw0.subtract(iArr5, iArrCreate, iArr5);
        nw0 nw0Var6 = new nw0(iArrCreate2);
        mw0.twice(nw0Var.f58700x, nw0Var6.f58700x);
        if (!zIsOne) {
            int[] iArr6 = nw0Var6.f58700x;
            mw0.multiply(iArr6, nw0Var3.f58700x, iArr6);
        }
        return new ow0(curve, nw0Var4, nw0Var5, new AbstractC1330va[]{nw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public ow0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
