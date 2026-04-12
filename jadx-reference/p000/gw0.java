package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class gw0 extends AbstractC1341vl.a2 {
    public gw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        fw0 fw0Var = (fw0) this.f60653x;
        fw0 fw0Var2 = (fw0) this.f60654y;
        fw0 fw0Var3 = (fw0) abstractC1341vl.getXCoord();
        fw0 fw0Var4 = (fw0) abstractC1341vl.getYCoord();
        fw0 fw0Var5 = (fw0) this.f60655zs[0];
        fw0 fw0Var6 = (fw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = qh0.createExt();
        int[] iArrCreate = qh0.create();
        int[] iArrCreate2 = qh0.create();
        int[] iArrCreate3 = qh0.create();
        boolean zIsOne = fw0Var5.isOne();
        if (zIsOne) {
            iArr = fw0Var3.f56339x;
            iArr2 = fw0Var4.f56339x;
        } else {
            ew0.square(fw0Var5.f56339x, iArrCreate2);
            ew0.multiply(iArrCreate2, fw0Var3.f56339x, iArrCreate);
            ew0.multiply(iArrCreate2, fw0Var5.f56339x, iArrCreate2);
            ew0.multiply(iArrCreate2, fw0Var4.f56339x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = fw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = fw0Var.f56339x;
            iArr4 = fw0Var2.f56339x;
        } else {
            ew0.square(fw0Var6.f56339x, iArrCreate3);
            ew0.multiply(iArrCreate3, fw0Var.f56339x, iArrCreateExt);
            ew0.multiply(iArrCreate3, fw0Var6.f56339x, iArrCreate3);
            ew0.multiply(iArrCreate3, fw0Var2.f56339x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = qh0.create();
        ew0.subtract(iArr3, iArr, iArrCreate4);
        ew0.subtract(iArr4, iArr2, iArrCreate);
        if (qh0.isZero(iArrCreate4)) {
            return qh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        ew0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = qh0.create();
        ew0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        ew0.multiply(iArrCreate2, iArr3, iArrCreate2);
        ew0.negate(iArrCreate5, iArrCreate5);
        qh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        ew0.reduce32(qh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        fw0 fw0Var7 = new fw0(iArrCreate3);
        ew0.square(iArrCreate, fw0Var7.f56339x);
        int[] iArr5 = fw0Var7.f56339x;
        ew0.subtract(iArr5, iArrCreate5, iArr5);
        fw0 fw0Var8 = new fw0(iArrCreate5);
        ew0.subtract(iArrCreate2, fw0Var7.f56339x, fw0Var8.f56339x);
        ew0.multiplyAddToExt(fw0Var8.f56339x, iArrCreate, iArrCreateExt);
        ew0.reduce(iArrCreateExt, fw0Var8.f56339x);
        fw0 fw0Var9 = new fw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = fw0Var9.f56339x;
            ew0.multiply(iArr6, fw0Var5.f56339x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = fw0Var9.f56339x;
            ew0.multiply(iArr7, fw0Var6.f56339x, iArr7);
        }
        return new gw0(curve, fw0Var7, fw0Var8, new AbstractC1330va[]{fw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new gw0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new gw0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        fw0 fw0Var = (fw0) this.f60654y;
        if (fw0Var.isZero()) {
            return curve.getInfinity();
        }
        fw0 fw0Var2 = (fw0) this.f60653x;
        fw0 fw0Var3 = (fw0) this.f60655zs[0];
        int[] iArrCreate = qh0.create();
        int[] iArrCreate2 = qh0.create();
        int[] iArrCreate3 = qh0.create();
        ew0.square(fw0Var.f56339x, iArrCreate3);
        int[] iArrCreate4 = qh0.create();
        ew0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = fw0Var3.isOne();
        int[] iArr = fw0Var3.f56339x;
        if (!zIsOne) {
            ew0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        ew0.subtract(fw0Var2.f56339x, iArr, iArrCreate);
        ew0.add(fw0Var2.f56339x, iArr, iArrCreate2);
        ew0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        ew0.reduce32(qh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        ew0.multiply(iArrCreate3, fw0Var2.f56339x, iArrCreate3);
        ew0.reduce32(yh0.shiftUpBits(6, iArrCreate3, 2, 0), iArrCreate3);
        ew0.reduce32(yh0.shiftUpBits(6, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        fw0 fw0Var4 = new fw0(iArrCreate4);
        ew0.square(iArrCreate2, fw0Var4.f56339x);
        int[] iArr2 = fw0Var4.f56339x;
        ew0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = fw0Var4.f56339x;
        ew0.subtract(iArr3, iArrCreate3, iArr3);
        fw0 fw0Var5 = new fw0(iArrCreate3);
        ew0.subtract(iArrCreate3, fw0Var4.f56339x, fw0Var5.f56339x);
        int[] iArr4 = fw0Var5.f56339x;
        ew0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = fw0Var5.f56339x;
        ew0.subtract(iArr5, iArrCreate, iArr5);
        fw0 fw0Var6 = new fw0(iArrCreate2);
        ew0.twice(fw0Var.f56339x, fw0Var6.f56339x);
        if (!zIsOne) {
            int[] iArr6 = fw0Var6.f56339x;
            ew0.multiply(iArr6, fw0Var3.f56339x, iArr6);
        }
        return new gw0(curve, fw0Var4, fw0Var5, new AbstractC1330va[]{fw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public gw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
