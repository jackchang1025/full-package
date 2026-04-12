package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class ex0 extends AbstractC1341vl.a2 {
    public ex0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
        int[] iArr;
        int[] iArr2;
        char c;
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
        dx0 dx0Var = (dx0) this.f60653x;
        dx0 dx0Var2 = (dx0) this.f60654y;
        dx0 dx0Var3 = (dx0) abstractC1341vl.getXCoord();
        dx0 dx0Var4 = (dx0) abstractC1341vl.getYCoord();
        dx0 dx0Var5 = (dx0) this.f60655zs[0];
        dx0 dx0Var6 = (dx0) abstractC1341vl.getZCoord(0);
        int[] iArrCreate = yh0.create(17);
        int[] iArrCreate2 = yh0.create(17);
        int[] iArrCreate3 = yh0.create(17);
        int[] iArrCreate4 = yh0.create(17);
        boolean zIsOne = dx0Var5.isOne();
        if (zIsOne) {
            iArr = dx0Var3.f55890x;
            iArr2 = dx0Var4.f55890x;
        } else {
            cx0.square(dx0Var5.f55890x, iArrCreate3);
            cx0.multiply(iArrCreate3, dx0Var3.f55890x, iArrCreate2);
            cx0.multiply(iArrCreate3, dx0Var5.f55890x, iArrCreate3);
            cx0.multiply(iArrCreate3, dx0Var4.f55890x, iArrCreate3);
            iArr = iArrCreate2;
            iArr2 = iArrCreate3;
        }
        boolean zIsOne2 = dx0Var6.isOne();
        if (zIsOne2) {
            iArr3 = dx0Var.f55890x;
            iArr4 = dx0Var2.f55890x;
            c = 0;
        } else {
            c = 0;
            cx0.square(dx0Var6.f55890x, iArrCreate4);
            cx0.multiply(iArrCreate4, dx0Var.f55890x, iArrCreate);
            cx0.multiply(iArrCreate4, dx0Var6.f55890x, iArrCreate4);
            cx0.multiply(iArrCreate4, dx0Var2.f55890x, iArrCreate4);
            iArr3 = iArrCreate;
            iArr4 = iArrCreate4;
        }
        int[] iArrCreate5 = yh0.create(17);
        cx0.subtract(iArr3, iArr, iArrCreate5);
        cx0.subtract(iArr4, iArr2, iArrCreate2);
        if (yh0.isZero(17, iArrCreate5)) {
            return yh0.isZero(17, iArrCreate2) ? twice() : curve.getInfinity();
        }
        cx0.square(iArrCreate5, iArrCreate3);
        int[] iArrCreate6 = yh0.create(17);
        cx0.multiply(iArrCreate3, iArrCreate5, iArrCreate6);
        cx0.multiply(iArrCreate3, iArr3, iArrCreate3);
        cx0.multiply(iArr4, iArrCreate6, iArrCreate);
        dx0 dx0Var7 = new dx0(iArrCreate4);
        cx0.square(iArrCreate2, dx0Var7.f55890x);
        int[] iArr5 = dx0Var7.f55890x;
        cx0.add(iArr5, iArrCreate6, iArr5);
        int[] iArr6 = dx0Var7.f55890x;
        cx0.subtract(iArr6, iArrCreate3, iArr6);
        int[] iArr7 = dx0Var7.f55890x;
        cx0.subtract(iArr7, iArrCreate3, iArr7);
        dx0 dx0Var8 = new dx0(iArrCreate6);
        cx0.subtract(iArrCreate3, dx0Var7.f55890x, dx0Var8.f55890x);
        cx0.multiply(dx0Var8.f55890x, iArrCreate2, iArrCreate2);
        cx0.subtract(iArrCreate2, iArrCreate, dx0Var8.f55890x);
        dx0 dx0Var9 = new dx0(iArrCreate5);
        if (!zIsOne) {
            int[] iArr8 = dx0Var9.f55890x;
            cx0.multiply(iArr8, dx0Var5.f55890x, iArr8);
        }
        if (!zIsOne2) {
            int[] iArr9 = dx0Var9.f55890x;
            cx0.multiply(iArr9, dx0Var6.f55890x, iArr9);
        }
        AbstractC1330va[] abstractC1330vaArr = new AbstractC1330va[1];
        abstractC1330vaArr[c] = dx0Var9;
        return new ex0(curve, dx0Var7, dx0Var8, abstractC1330vaArr);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new ex0(null, getAffineXCoord(), getAffineYCoord());
    }

    public AbstractC1330va doubleProductFromSquares(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va abstractC1330va3, AbstractC1330va abstractC1330va4) {
        return abstractC1330va.add(abstractC1330va2).square().subtract(abstractC1330va3).subtract(abstractC1330va4);
    }

    public AbstractC1330va eight(AbstractC1330va abstractC1330va) {
        return four(two(abstractC1330va));
    }

    public AbstractC1330va four(AbstractC1330va abstractC1330va) {
        return two(two(abstractC1330va));
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new ex0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
    }

    public AbstractC1330va three(AbstractC1330va abstractC1330va) {
        return two(abstractC1330va).add(abstractC1330va);
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
        dx0 dx0Var = (dx0) this.f60654y;
        if (dx0Var.isZero()) {
            return curve.getInfinity();
        }
        dx0 dx0Var2 = (dx0) this.f60653x;
        dx0 dx0Var3 = (dx0) this.f60655zs[0];
        int[] iArrCreate = yh0.create(17);
        int[] iArrCreate2 = yh0.create(17);
        int[] iArrCreate3 = yh0.create(17);
        cx0.square(dx0Var.f55890x, iArrCreate3);
        int[] iArrCreate4 = yh0.create(17);
        cx0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = dx0Var3.isOne();
        int[] iArr = dx0Var3.f55890x;
        if (!zIsOne) {
            cx0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        cx0.subtract(dx0Var2.f55890x, iArr, iArrCreate);
        cx0.add(dx0Var2.f55890x, iArr, iArrCreate2);
        cx0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        yh0.addBothTo(17, iArrCreate2, iArrCreate2, iArrCreate2);
        cx0.reduce23(iArrCreate2);
        cx0.multiply(iArrCreate3, dx0Var2.f55890x, iArrCreate3);
        yh0.shiftUpBits(17, iArrCreate3, 2, 0);
        cx0.reduce23(iArrCreate3);
        yh0.shiftUpBits(17, iArrCreate4, 3, 0, iArrCreate);
        cx0.reduce23(iArrCreate);
        dx0 dx0Var4 = new dx0(iArrCreate4);
        cx0.square(iArrCreate2, dx0Var4.f55890x);
        int[] iArr2 = dx0Var4.f55890x;
        cx0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = dx0Var4.f55890x;
        cx0.subtract(iArr3, iArrCreate3, iArr3);
        dx0 dx0Var5 = new dx0(iArrCreate3);
        cx0.subtract(iArrCreate3, dx0Var4.f55890x, dx0Var5.f55890x);
        int[] iArr4 = dx0Var5.f55890x;
        cx0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = dx0Var5.f55890x;
        cx0.subtract(iArr5, iArrCreate, iArr5);
        dx0 dx0Var6 = new dx0(iArrCreate2);
        cx0.twice(dx0Var.f55890x, dx0Var6.f55890x);
        if (!zIsOne) {
            int[] iArr6 = dx0Var6.f55890x;
            cx0.multiply(iArr6, dx0Var3.f55890x, iArr6);
        }
        return new ex0(curve, dx0Var4, dx0Var5, new AbstractC1330va[]{dx0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public AbstractC1330va two(AbstractC1330va abstractC1330va) {
        return abstractC1330va.add(abstractC1330va);
    }

    public ex0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
