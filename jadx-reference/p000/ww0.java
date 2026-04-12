package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class ww0 extends AbstractC1341vl.a2 {
    public ww0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        vw0 vw0Var = (vw0) this.f60653x;
        vw0 vw0Var2 = (vw0) this.f60654y;
        vw0 vw0Var3 = (vw0) abstractC1341vl.getXCoord();
        vw0 vw0Var4 = (vw0) abstractC1341vl.getYCoord();
        vw0 vw0Var5 = (vw0) this.f60655zs[0];
        vw0 vw0Var6 = (vw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = sh0.createExt();
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        boolean zIsOne = vw0Var5.isOne();
        if (zIsOne) {
            iArr = vw0Var3.f60716x;
            iArr2 = vw0Var4.f60716x;
        } else {
            uw0.square(vw0Var5.f60716x, iArrCreate2);
            uw0.multiply(iArrCreate2, vw0Var3.f60716x, iArrCreate);
            uw0.multiply(iArrCreate2, vw0Var5.f60716x, iArrCreate2);
            uw0.multiply(iArrCreate2, vw0Var4.f60716x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = vw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = vw0Var.f60716x;
            iArr4 = vw0Var2.f60716x;
        } else {
            uw0.square(vw0Var6.f60716x, iArrCreate3);
            uw0.multiply(iArrCreate3, vw0Var.f60716x, iArrCreateExt);
            uw0.multiply(iArrCreate3, vw0Var6.f60716x, iArrCreate3);
            uw0.multiply(iArrCreate3, vw0Var2.f60716x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = sh0.create();
        uw0.subtract(iArr3, iArr, iArrCreate4);
        uw0.subtract(iArr4, iArr2, iArrCreate);
        if (sh0.isZero(iArrCreate4)) {
            return sh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        uw0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = sh0.create();
        uw0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        uw0.multiply(iArrCreate2, iArr3, iArrCreate2);
        uw0.negate(iArrCreate5, iArrCreate5);
        sh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        uw0.reduce32(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        vw0 vw0Var7 = new vw0(iArrCreate3);
        uw0.square(iArrCreate, vw0Var7.f60716x);
        int[] iArr5 = vw0Var7.f60716x;
        uw0.subtract(iArr5, iArrCreate5, iArr5);
        vw0 vw0Var8 = new vw0(iArrCreate5);
        uw0.subtract(iArrCreate2, vw0Var7.f60716x, vw0Var8.f60716x);
        uw0.multiplyAddToExt(vw0Var8.f60716x, iArrCreate, iArrCreateExt);
        uw0.reduce(iArrCreateExt, vw0Var8.f60716x);
        vw0 vw0Var9 = new vw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = vw0Var9.f60716x;
            uw0.multiply(iArr6, vw0Var5.f60716x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = vw0Var9.f60716x;
            uw0.multiply(iArr7, vw0Var6.f60716x, iArr7);
        }
        return new ww0(curve, vw0Var7, vw0Var8, new AbstractC1330va[]{vw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new ww0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new ww0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        vw0 vw0Var = (vw0) this.f60654y;
        if (vw0Var.isZero()) {
            return curve.getInfinity();
        }
        vw0 vw0Var2 = (vw0) this.f60653x;
        vw0 vw0Var3 = (vw0) this.f60655zs[0];
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        uw0.square(vw0Var.f60716x, iArrCreate3);
        int[] iArrCreate4 = sh0.create();
        uw0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = vw0Var3.isOne();
        int[] iArr = vw0Var3.f60716x;
        if (!zIsOne) {
            uw0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        uw0.subtract(vw0Var2.f60716x, iArr, iArrCreate);
        uw0.add(vw0Var2.f60716x, iArr, iArrCreate2);
        uw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        uw0.reduce32(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        uw0.multiply(iArrCreate3, vw0Var2.f60716x, iArrCreate3);
        uw0.reduce32(yh0.shiftUpBits(8, iArrCreate3, 2, 0), iArrCreate3);
        uw0.reduce32(yh0.shiftUpBits(8, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        vw0 vw0Var4 = new vw0(iArrCreate4);
        uw0.square(iArrCreate2, vw0Var4.f60716x);
        int[] iArr2 = vw0Var4.f60716x;
        uw0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = vw0Var4.f60716x;
        uw0.subtract(iArr3, iArrCreate3, iArr3);
        vw0 vw0Var5 = new vw0(iArrCreate3);
        uw0.subtract(iArrCreate3, vw0Var4.f60716x, vw0Var5.f60716x);
        int[] iArr4 = vw0Var5.f60716x;
        uw0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = vw0Var5.f60716x;
        uw0.subtract(iArr5, iArrCreate, iArr5);
        vw0 vw0Var6 = new vw0(iArrCreate2);
        uw0.twice(vw0Var.f60716x, vw0Var6.f60716x);
        if (!zIsOne) {
            int[] iArr6 = vw0Var6.f60716x;
            uw0.multiply(iArr6, vw0Var3.f60716x, iArr6);
        }
        return new ww0(curve, vw0Var4, vw0Var5, new AbstractC1330va[]{vw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public ww0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
