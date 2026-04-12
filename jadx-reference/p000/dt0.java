package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class dt0 extends AbstractC1341vl.a2 {
    public dt0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        ct0 ct0Var = (ct0) this.f60653x;
        ct0 ct0Var2 = (ct0) this.f60654y;
        ct0 ct0Var3 = (ct0) abstractC1341vl.getXCoord();
        ct0 ct0Var4 = (ct0) abstractC1341vl.getYCoord();
        ct0 ct0Var5 = (ct0) this.f60655zs[0];
        ct0 ct0Var6 = (ct0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = sh0.createExt();
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        boolean zIsOne = ct0Var5.isOne();
        if (zIsOne) {
            iArr = ct0Var3.f55518x;
            iArr2 = ct0Var4.f55518x;
        } else {
            bt0.square(ct0Var5.f55518x, iArrCreate2);
            bt0.multiply(iArrCreate2, ct0Var3.f55518x, iArrCreate);
            bt0.multiply(iArrCreate2, ct0Var5.f55518x, iArrCreate2);
            bt0.multiply(iArrCreate2, ct0Var4.f55518x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = ct0Var6.isOne();
        if (zIsOne2) {
            iArr3 = ct0Var.f55518x;
            iArr4 = ct0Var2.f55518x;
        } else {
            bt0.square(ct0Var6.f55518x, iArrCreate3);
            bt0.multiply(iArrCreate3, ct0Var.f55518x, iArrCreateExt);
            bt0.multiply(iArrCreate3, ct0Var6.f55518x, iArrCreate3);
            bt0.multiply(iArrCreate3, ct0Var2.f55518x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = sh0.create();
        bt0.subtract(iArr3, iArr, iArrCreate4);
        bt0.subtract(iArr4, iArr2, iArrCreate);
        if (sh0.isZero(iArrCreate4)) {
            return sh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        bt0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = sh0.create();
        bt0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        bt0.multiply(iArrCreate2, iArr3, iArrCreate2);
        bt0.negate(iArrCreate5, iArrCreate5);
        sh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        bt0.reduce32(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        ct0 ct0Var7 = new ct0(iArrCreate3);
        bt0.square(iArrCreate, ct0Var7.f55518x);
        int[] iArr5 = ct0Var7.f55518x;
        bt0.subtract(iArr5, iArrCreate5, iArr5);
        ct0 ct0Var8 = new ct0(iArrCreate5);
        bt0.subtract(iArrCreate2, ct0Var7.f55518x, ct0Var8.f55518x);
        bt0.multiplyAddToExt(ct0Var8.f55518x, iArrCreate, iArrCreateExt);
        bt0.reduce(iArrCreateExt, ct0Var8.f55518x);
        ct0 ct0Var9 = new ct0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = ct0Var9.f55518x;
            bt0.multiply(iArr6, ct0Var5.f55518x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = ct0Var9.f55518x;
            bt0.multiply(iArr7, ct0Var6.f55518x, iArr7);
        }
        return new dt0(curve, ct0Var7, ct0Var8, new AbstractC1330va[]{ct0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new dt0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new dt0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        ct0 ct0Var = (ct0) this.f60654y;
        if (ct0Var.isZero()) {
            return curve.getInfinity();
        }
        ct0 ct0Var2 = (ct0) this.f60653x;
        ct0 ct0Var3 = (ct0) this.f60655zs[0];
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        bt0.square(ct0Var.f55518x, iArrCreate3);
        int[] iArrCreate4 = sh0.create();
        bt0.square(iArrCreate3, iArrCreate4);
        boolean zIsOne = ct0Var3.isOne();
        int[] iArr = ct0Var3.f55518x;
        if (!zIsOne) {
            bt0.square(iArr, iArrCreate2);
            iArr = iArrCreate2;
        }
        bt0.subtract(ct0Var2.f55518x, iArr, iArrCreate);
        bt0.add(ct0Var2.f55518x, iArr, iArrCreate2);
        bt0.multiply(iArrCreate2, iArrCreate, iArrCreate2);
        bt0.reduce32(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate2), iArrCreate2);
        bt0.multiply(iArrCreate3, ct0Var2.f55518x, iArrCreate3);
        bt0.reduce32(yh0.shiftUpBits(8, iArrCreate3, 2, 0), iArrCreate3);
        bt0.reduce32(yh0.shiftUpBits(8, iArrCreate4, 3, 0, iArrCreate), iArrCreate);
        ct0 ct0Var4 = new ct0(iArrCreate4);
        bt0.square(iArrCreate2, ct0Var4.f55518x);
        int[] iArr2 = ct0Var4.f55518x;
        bt0.subtract(iArr2, iArrCreate3, iArr2);
        int[] iArr3 = ct0Var4.f55518x;
        bt0.subtract(iArr3, iArrCreate3, iArr3);
        ct0 ct0Var5 = new ct0(iArrCreate3);
        bt0.subtract(iArrCreate3, ct0Var4.f55518x, ct0Var5.f55518x);
        int[] iArr4 = ct0Var5.f55518x;
        bt0.multiply(iArr4, iArrCreate2, iArr4);
        int[] iArr5 = ct0Var5.f55518x;
        bt0.subtract(iArr5, iArrCreate, iArr5);
        ct0 ct0Var6 = new ct0(iArrCreate2);
        bt0.twice(ct0Var.f55518x, ct0Var6.f55518x);
        if (!zIsOne) {
            int[] iArr6 = ct0Var6.f55518x;
            bt0.multiply(iArr6, ct0Var3.f55518x, iArr6);
        }
        return new dt0(curve, ct0Var4, ct0Var5, new AbstractC1330va[]{ct0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public dt0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
