package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class ov0 extends AbstractC1341vl.a2 {
    public ov0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        nv0 nv0Var = (nv0) this.f60653x;
        nv0 nv0Var2 = (nv0) this.f60654y;
        nv0 nv0Var3 = (nv0) abstractC1341vl.getXCoord();
        nv0 nv0Var4 = (nv0) abstractC1341vl.getYCoord();
        nv0 nv0Var5 = (nv0) this.f60655zs[0];
        nv0 nv0Var6 = (nv0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = oh0.createExt();
        int[] iArrCreate = oh0.create();
        int[] iArrCreate2 = oh0.create();
        int[] iArrCreate3 = oh0.create();
        boolean zIsOne = nv0Var5.isOne();
        if (zIsOne) {
            iArr = nv0Var3.f58698x;
            iArr2 = nv0Var4.f58698x;
        } else {
            mv0.square(nv0Var5.f58698x, iArrCreate2);
            mv0.multiply(iArrCreate2, nv0Var3.f58698x, iArrCreate);
            mv0.multiply(iArrCreate2, nv0Var5.f58698x, iArrCreate2);
            mv0.multiply(iArrCreate2, nv0Var4.f58698x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = nv0Var6.isOne();
        if (zIsOne2) {
            iArr3 = nv0Var.f58698x;
            iArr4 = nv0Var2.f58698x;
        } else {
            mv0.square(nv0Var6.f58698x, iArrCreate3);
            mv0.multiply(iArrCreate3, nv0Var.f58698x, iArrCreateExt);
            mv0.multiply(iArrCreate3, nv0Var6.f58698x, iArrCreate3);
            mv0.multiply(iArrCreate3, nv0Var2.f58698x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = oh0.create();
        mv0.subtract(iArr3, iArr, iArrCreate4);
        mv0.subtract(iArr4, iArr2, iArrCreate);
        if (oh0.isZero(iArrCreate4)) {
            return oh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        mv0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = oh0.create();
        mv0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        mv0.multiply(iArrCreate2, iArr3, iArrCreate2);
        mv0.negate(iArrCreate5, iArrCreate5);
        oh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        mv0.reduce32(oh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        nv0 nv0Var7 = new nv0(iArrCreate3);
        mv0.square(iArrCreate, nv0Var7.f58698x);
        int[] iArr5 = nv0Var7.f58698x;
        mv0.subtract(iArr5, iArrCreate5, iArr5);
        nv0 nv0Var8 = new nv0(iArrCreate5);
        mv0.subtract(iArrCreate2, nv0Var7.f58698x, nv0Var8.f58698x);
        mv0.multiplyAddToExt(nv0Var8.f58698x, iArrCreate, iArrCreateExt);
        mv0.reduce(iArrCreateExt, nv0Var8.f58698x);
        nv0 nv0Var9 = new nv0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = nv0Var9.f58698x;
            mv0.multiply(iArr6, nv0Var5.f58698x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = nv0Var9.f58698x;
            mv0.multiply(iArr7, nv0Var6.f58698x, iArr7);
        }
        return new ov0(curve, nv0Var7, nv0Var8, new AbstractC1330va[]{nv0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new ov0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new ov0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        nv0 nv0Var = (nv0) this.f60654y;
        if (nv0Var.isZero()) {
            return curve.getInfinity();
        }
        nv0 nv0Var2 = (nv0) this.f60653x;
        nv0 nv0Var3 = (nv0) this.f60655zs[0];
        int[] iArrCreate = oh0.create();
        int[] iArrCreate2 = oh0.create();
        int[] iArrCreate3 = oh0.create();
        mv0.square(nv0Var.f58698x, iArrCreate3);
        int[] iArrCreate4 = oh0.create();
        mv0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = nv0Var3.isOne();
        int[] iArr = nv0Var3.f58698x;
        if (!zIsOne) {
            mv0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        mv0.subtract(nv0Var2.f58698x, iArr, iArrCreate);
        mv0.add(nv0Var2.f58698x, iArr, iArrCreate2);
        mv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        mv0.reduce32(oh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        mv0.multiply(iArrCreate3, nv0Var2.f58698x, iArrCreate3);
        mv0.reduce32(yh0.shiftUpBits(4, iArrCreate3, 2, 0), iArrCreate3);
        mv0.reduce32(yh0.shiftUpBits(4, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        nv0 nv0Var4 = new nv0(iArrCreate4);
        mv0.square(iArrCreate2, nv0Var4.f58698x);
        int[] iArr2 = nv0Var4.f58698x;
        mv0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = nv0Var4.f58698x;
        mv0.subtract(iArr3, iArrCreate3, iArr3);
        nv0 nv0Var5 = new nv0(iArrCreate3);
        mv0.subtract(iArrCreate3, nv0Var4.f58698x, nv0Var5.f58698x);
        int[] iArr4 = nv0Var5.f58698x;
        mv0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = nv0Var5.f58698x;
        mv0.subtract(iArr5, iArrCreate, iArr5);
        nv0 nv0Var6 = new nv0(iArrCreate2);
        mv0.twice(nv0Var.f58698x, nv0Var6.f58698x);
        if (!zIsOne) {
            int[] iArr6 = nv0Var6.f58698x;
            mv0.multiply(iArr6, nv0Var3.f58698x, iArr6);
        }
        return new ov0(curve, nv0Var4, nv0Var5, new AbstractC1330va[]{nv0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public ov0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
