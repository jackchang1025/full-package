package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class uv0 extends AbstractC1341vl.a2 {
    public uv0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        tv0 tv0Var = (tv0) this.f60653x;
        tv0 tv0Var2 = (tv0) this.f60654y;
        tv0 tv0Var3 = (tv0) abstractC1341vl.getXCoord();
        tv0 tv0Var4 = (tv0) abstractC1341vl.getYCoord();
        tv0 tv0Var5 = (tv0) this.f60655zs[0];
        tv0 tv0Var6 = (tv0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = ph0.createExt();
        int[] iArrCreate = ph0.create();
        int[] iArrCreate2 = ph0.create();
        int[] iArrCreate3 = ph0.create();
        boolean zIsOne = tv0Var5.isOne();
        if (zIsOne) {
            iArr = tv0Var3.f60286x;
            iArr2 = tv0Var4.f60286x;
        } else {
            sv0.square(tv0Var5.f60286x, iArrCreate2);
            sv0.multiply(iArrCreate2, tv0Var3.f60286x, iArrCreate);
            sv0.multiply(iArrCreate2, tv0Var5.f60286x, iArrCreate2);
            sv0.multiply(iArrCreate2, tv0Var4.f60286x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = tv0Var6.isOne();
        if (zIsOne2) {
            iArr3 = tv0Var.f60286x;
            iArr4 = tv0Var2.f60286x;
        } else {
            sv0.square(tv0Var6.f60286x, iArrCreate3);
            sv0.multiply(iArrCreate3, tv0Var.f60286x, iArrCreateExt);
            sv0.multiply(iArrCreate3, tv0Var6.f60286x, iArrCreate3);
            sv0.multiply(iArrCreate3, tv0Var2.f60286x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = ph0.create();
        sv0.subtract(iArr3, iArr, iArrCreate4);
        sv0.subtract(iArr4, iArr2, iArrCreate);
        if (ph0.isZero(iArrCreate4)) {
            return ph0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        sv0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = ph0.create();
        sv0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        sv0.multiply(iArrCreate2, iArr3, iArrCreate2);
        sv0.negate(iArrCreate5, iArrCreate5);
        ph0.mul(iArr4, iArrCreate5, iArrCreateExt);
        sv0.reduce32(ph0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        tv0 tv0Var7 = new tv0(iArrCreate3);
        sv0.square(iArrCreate, tv0Var7.f60286x);
        int[] iArr5 = tv0Var7.f60286x;
        sv0.subtract(iArr5, iArrCreate5, iArr5);
        tv0 tv0Var8 = new tv0(iArrCreate5);
        sv0.subtract(iArrCreate2, tv0Var7.f60286x, tv0Var8.f60286x);
        sv0.multiplyAddToExt(tv0Var8.f60286x, iArrCreate, iArrCreateExt);
        sv0.reduce(iArrCreateExt, tv0Var8.f60286x);
        tv0 tv0Var9 = new tv0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = tv0Var9.f60286x;
            sv0.multiply(iArr6, tv0Var5.f60286x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = tv0Var9.f60286x;
            sv0.multiply(iArr7, tv0Var6.f60286x, iArr7);
        }
        return new uv0(curve, tv0Var7, tv0Var8, new AbstractC1330va[]{tv0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new uv0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new uv0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        tv0 tv0Var = (tv0) this.f60654y;
        if (tv0Var.isZero()) {
            return curve.getInfinity();
        }
        tv0 tv0Var2 = (tv0) this.f60653x;
        tv0 tv0Var3 = (tv0) this.f60655zs[0];
        int[] iArrCreate = ph0.create();
        int[] iArrCreate2 = ph0.create();
        int[] iArrCreate3 = ph0.create();
        sv0.square(tv0Var.f60286x, iArrCreate3);
        int[] iArrCreate4 = ph0.create();
        sv0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = tv0Var3.isOne();
        int[] iArr = tv0Var3.f60286x;
        if (!zIsOne) {
            sv0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        sv0.subtract(tv0Var2.f60286x, iArr, iArrCreate);
        sv0.add(tv0Var2.f60286x, iArr, iArrCreate2);
        sv0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        sv0.reduce32(ph0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        sv0.multiply(iArrCreate3, tv0Var2.f60286x, iArrCreate3);
        sv0.reduce32(yh0.shiftUpBits(5, iArrCreate3, 2, 0), iArrCreate3);
        sv0.reduce32(yh0.shiftUpBits(5, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        tv0 tv0Var4 = new tv0(iArrCreate4);
        sv0.square(iArrCreate2, tv0Var4.f60286x);
        int[] iArr2 = tv0Var4.f60286x;
        sv0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = tv0Var4.f60286x;
        sv0.subtract(iArr3, iArrCreate3, iArr3);
        tv0 tv0Var5 = new tv0(iArrCreate3);
        sv0.subtract(iArrCreate3, tv0Var4.f60286x, tv0Var5.f60286x);
        int[] iArr4 = tv0Var5.f60286x;
        sv0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = tv0Var5.f60286x;
        sv0.subtract(iArr5, iArrCreate, iArr5);
        tv0 tv0Var6 = new tv0(iArrCreate2);
        sv0.twice(tv0Var.f60286x, tv0Var6.f60286x);
        if (!zIsOne) {
            int[] iArr6 = tv0Var6.f60286x;
            sv0.multiply(iArr6, tv0Var3.f60286x, iArr6);
        }
        return new uv0(curve, tv0Var4, tv0Var5, new AbstractC1330va[]{tv0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public uv0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
