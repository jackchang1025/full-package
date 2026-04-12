package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class ax0 extends AbstractC1341vl.a2 {
    public ax0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        zw0 zw0Var = (zw0) this.f60653x;
        zw0 zw0Var2 = (zw0) this.f60654y;
        zw0 zw0Var3 = (zw0) abstractC1341vl.getXCoord();
        zw0 zw0Var4 = (zw0) abstractC1341vl.getYCoord();
        zw0 zw0Var5 = (zw0) this.f60655zs[0];
        zw0 zw0Var6 = (zw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreate = yh0.create(24);
        int[] iArrCreate2 = yh0.create(24);
        int[] iArrCreate3 = yh0.create(12);
        int[] iArrCreate4 = yh0.create(12);
        boolean zIsOne = zw0Var5.isOne();
        if (zIsOne) {
            iArr = zw0Var3.f61595x;
            iArr2 = zw0Var4.f61595x;
        } else {
            yw0.square(zw0Var5.f61595x, iArrCreate3);
            yw0.multiply(iArrCreate3, zw0Var3.f61595x, iArrCreate2);
            yw0.multiply(iArrCreate3, zw0Var5.f61595x, iArrCreate3);
            yw0.multiply(iArrCreate3, zw0Var4.f61595x, iArrCreate3);
            iArr = iArrCreate2;
            iArr2 = iArrCreate3;
        }
        boolean zIsOne2 = zw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = zw0Var.f61595x;
            iArr4 = zw0Var2.f61595x;
            c = 0;
        } else {
            c = 0;
            yw0.square(zw0Var6.f61595x, iArrCreate4);
            yw0.multiply(iArrCreate4, zw0Var.f61595x, iArrCreate);
            yw0.multiply(iArrCreate4, zw0Var6.f61595x, iArrCreate4);
            yw0.multiply(iArrCreate4, zw0Var2.f61595x, iArrCreate4);
            iArr3 = iArrCreate;
            iArr4 = iArrCreate4;
        }
        int[] iArrCreate5 = yh0.create(12);
        yw0.subtract(iArr3, iArr, iArrCreate5);
        int[] iArrCreate6 = yh0.create(12);
        yw0.subtract(iArr4, iArr2, iArrCreate6);
        if (yh0.isZero(12, iArrCreate5)) {
            return yh0.isZero(12, iArrCreate6) ? twice() : curve.getInfinity();
        }
        yw0.square(iArrCreate5, iArrCreate3);
        int[] iArrCreate7 = yh0.create(12);
        yw0.multiply(iArrCreate3, iArrCreate5, iArrCreate7);
        yw0.multiply(iArrCreate3, iArr3, iArrCreate3);
        yw0.negate(iArrCreate7, iArrCreate7);
        uh0.mul(iArr4, iArrCreate7, iArrCreate);
        yw0.reduce32(yh0.addBothTo(12, iArrCreate3, iArrCreate3, iArrCreate7), iArrCreate7);
        zw0 zw0Var7 = new zw0(iArrCreate4);
        yw0.square(iArrCreate6, zw0Var7.f61595x);
        int[] iArr5 = zw0Var7.f61595x;
        yw0.subtract(iArr5, iArrCreate7, iArr5);
        zw0 zw0Var8 = new zw0(iArrCreate7);
        yw0.subtract(iArrCreate3, zw0Var7.f61595x, zw0Var8.f61595x);
        uh0.mul(zw0Var8.f61595x, iArrCreate6, iArrCreate2);
        yw0.addExt(iArrCreate, iArrCreate2, iArrCreate);
        yw0.reduce(iArrCreate, zw0Var8.f61595x);
        zw0 zw0Var9 = new zw0(iArrCreate5);
        if (!zIsOne) {
            int[] iArr6 = zw0Var9.f61595x;
            yw0.multiply(iArr6, zw0Var5.f61595x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = zw0Var9.f61595x;
            yw0.multiply(iArr7, zw0Var6.f61595x, iArr7);
        }
        AbstractC1330va[] abstractC1330vaArr = new AbstractC1330va[1];
        abstractC1330vaArr[c] = zw0Var9;
        return new ax0(curve, zw0Var7, zw0Var8, abstractC1330vaArr);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new ax0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new ax0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        zw0 zw0Var = (zw0) this.f60654y;
        if (zw0Var.isZero()) {
            return curve.getInfinity();
        }
        zw0 zw0Var2 = (zw0) this.f60653x;
        zw0 zw0Var3 = (zw0) this.f60655zs[0];
        int[] iArrCreate = yh0.create(12);
        int[] iArrCreate2 = yh0.create(12);
        int[] iArrCreate3 = yh0.create(12);
        yw0.square(zw0Var.f61595x, iArrCreate3);
        int[] iArrCreate4 = yh0.create(12);
        yw0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = zw0Var3.isOne();
        int[] iArr = zw0Var3.f61595x;
        if (!zIsOne) {
            yw0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        yw0.subtract(zw0Var2.f61595x, iArr, iArrCreate);
        yw0.add(zw0Var2.f61595x, iArr, iArrCreate2);
        yw0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        yw0.reduce32(yh0.addBothTo(12, iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        yw0.multiply(iArrCreate3, zw0Var2.f61595x, iArrCreate3);
        yw0.reduce32(yh0.shiftUpBits(12, iArrCreate3, 2, 0), iArrCreate3);
        yw0.reduce32(yh0.shiftUpBits(12, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        zw0 zw0Var4 = new zw0(iArrCreate4);
        yw0.square(iArrCreate2, zw0Var4.f61595x);
        int[] iArr2 = zw0Var4.f61595x;
        yw0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = zw0Var4.f61595x;
        yw0.subtract(iArr3, iArrCreate3, iArr3);
        zw0 zw0Var5 = new zw0(iArrCreate3);
        yw0.subtract(iArrCreate3, zw0Var4.f61595x, zw0Var5.f61595x);
        int[] iArr4 = zw0Var5.f61595x;
        yw0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = zw0Var5.f61595x;
        yw0.subtract(iArr5, iArrCreate, iArr5);
        zw0 zw0Var6 = new zw0(iArrCreate2);
        yw0.twice(zw0Var.f61595x, zw0Var6.f61595x);
        if (!zIsOne) {
            int[] iArr6 = zw0Var6.f61595x;
            yw0.multiply(iArr6, zw0Var3.f61595x, iArr6);
        }
        return new ax0(curve, zw0Var4, zw0Var5, new AbstractC1330va[]{zw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public ax0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
