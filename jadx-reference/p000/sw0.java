package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class sw0 extends AbstractC1341vl.a2 {
    public sw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        rw0 rw0Var = (rw0) this.f60653x;
        rw0 rw0Var2 = (rw0) this.f60654y;
        rw0 rw0Var3 = (rw0) abstractC1341vl.getXCoord();
        rw0 rw0Var4 = (rw0) abstractC1341vl.getYCoord();
        rw0 rw0Var5 = (rw0) this.f60655zs[0];
        rw0 rw0Var6 = (rw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = sh0.createExt();
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        boolean zIsOne = rw0Var5.isOne();
        if (zIsOne) {
            iArr = rw0Var3.f59827x;
            iArr2 = rw0Var4.f59827x;
        } else {
            qw0.square(rw0Var5.f59827x, iArrCreate2);
            qw0.multiply(iArrCreate2, rw0Var3.f59827x, iArrCreate);
            qw0.multiply(iArrCreate2, rw0Var5.f59827x, iArrCreate2);
            qw0.multiply(iArrCreate2, rw0Var4.f59827x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = rw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = rw0Var.f59827x;
            iArr4 = rw0Var2.f59827x;
        } else {
            qw0.square(rw0Var6.f59827x, iArrCreate3);
            qw0.multiply(iArrCreate3, rw0Var.f59827x, iArrCreateExt);
            qw0.multiply(iArrCreate3, rw0Var6.f59827x, iArrCreate3);
            qw0.multiply(iArrCreate3, rw0Var2.f59827x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = sh0.create();
        qw0.subtract(iArr3, iArr, iArrCreate4);
        qw0.subtract(iArr4, iArr2, iArrCreate);
        if (sh0.isZero(iArrCreate4)) {
            return sh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        qw0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = sh0.create();
        qw0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        qw0.multiply(iArrCreate2, iArr3, iArrCreate2);
        qw0.negate(iArrCreate5, iArrCreate5);
        sh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        qw0.reduce32(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        rw0 rw0Var7 = new rw0(iArrCreate3);
        qw0.square(iArrCreate, rw0Var7.f59827x);
        int[] iArr5 = rw0Var7.f59827x;
        qw0.subtract(iArr5, iArrCreate5, iArr5);
        rw0 rw0Var8 = new rw0(iArrCreate5);
        qw0.subtract(iArrCreate2, rw0Var7.f59827x, rw0Var8.f59827x);
        qw0.multiplyAddToExt(rw0Var8.f59827x, iArrCreate, iArrCreateExt);
        qw0.reduce(iArrCreateExt, rw0Var8.f59827x);
        rw0 rw0Var9 = new rw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = rw0Var9.f59827x;
            qw0.multiply(iArr6, rw0Var5.f59827x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = rw0Var9.f59827x;
            qw0.multiply(iArr7, rw0Var6.f59827x, iArr7);
        }
        return new sw0(curve, rw0Var7, rw0Var8, new AbstractC1330va[]{rw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new sw0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new sw0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        rw0 rw0Var = (rw0) this.f60654y;
        if (rw0Var.isZero()) {
            return curve.getInfinity();
        }
        rw0 rw0Var2 = (rw0) this.f60653x;
        rw0 rw0Var3 = (rw0) this.f60655zs[0];
        int[] iArrCreate = sh0.create();
        qw0.square(rw0Var.f59827x, iArrCreate);
        int[] iArrCreate2 = sh0.create();
        qw0.square(iArrCreate, iArrCreate2);
        int[] iArrCreate3 = sh0.create();
        qw0.square(rw0Var2.f59827x, iArrCreate3);
        qw0.reduce32(sh0.addBothTo(iArrCreate3, iArrCreate3, iArrCreate3), iArrCreate3);
        qw0.multiply(iArrCreate, rw0Var2.f59827x, iArrCreate);
        qw0.reduce32(yh0.shiftUpBits(8, iArrCreate, 2, 0), iArrCreate);
        int[] iArrCreate4 = sh0.create();
        qw0.reduce32(yh0.shiftUpBits(8, iArrCreate2, 3, 0, iArrCreate4), iArrCreate4);
        rw0 rw0Var4 = new rw0(iArrCreate2);
        qw0.square(iArrCreate3, rw0Var4.f59827x);
        int[] iArr = rw0Var4.f59827x;
        qw0.subtract(iArr, iArrCreate, iArr);
        int[] iArr2 = rw0Var4.f59827x;
        qw0.subtract(iArr2, iArrCreate, iArr2);
        rw0 rw0Var5 = new rw0(iArrCreate);
        qw0.subtract(iArrCreate, rw0Var4.f59827x, rw0Var5.f59827x);
        int[] iArr3 = rw0Var5.f59827x;
        qw0.multiply(iArr3, iArrCreate3, iArr3);
        int[] iArr4 = rw0Var5.f59827x;
        qw0.subtract(iArr4, iArrCreate4, iArr4);
        rw0 rw0Var6 = new rw0(iArrCreate3);
        qw0.twice(rw0Var.f59827x, rw0Var6.f59827x);
        if (!rw0Var3.isOne()) {
            int[] iArr5 = rw0Var6.f59827x;
            qw0.multiply(iArr5, rw0Var3.f59827x, iArr5);
        }
        return new sw0(curve, rw0Var4, rw0Var5, new AbstractC1330va[]{rw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public sw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
