package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class cw0 extends AbstractC1341vl.a2 {
    public cw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        bw0 bw0Var = (bw0) this.f60653x;
        bw0 bw0Var2 = (bw0) this.f60654y;
        bw0 bw0Var3 = (bw0) abstractC1341vl.getXCoord();
        bw0 bw0Var4 = (bw0) abstractC1341vl.getYCoord();
        bw0 bw0Var5 = (bw0) this.f60655zs[0];
        bw0 bw0Var6 = (bw0) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = qh0.createExt();
        int[] iArrCreate = qh0.create();
        int[] iArrCreate2 = qh0.create();
        int[] iArrCreate3 = qh0.create();
        boolean zIsOne = bw0Var5.isOne();
        if (zIsOne) {
            iArr = bw0Var3.f46010x;
            iArr2 = bw0Var4.f46010x;
        } else {
            aw0.square(bw0Var5.f46010x, iArrCreate2);
            aw0.multiply(iArrCreate2, bw0Var3.f46010x, iArrCreate);
            aw0.multiply(iArrCreate2, bw0Var5.f46010x, iArrCreate2);
            aw0.multiply(iArrCreate2, bw0Var4.f46010x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = bw0Var6.isOne();
        if (zIsOne2) {
            iArr3 = bw0Var.f46010x;
            iArr4 = bw0Var2.f46010x;
        } else {
            aw0.square(bw0Var6.f46010x, iArrCreate3);
            aw0.multiply(iArrCreate3, bw0Var.f46010x, iArrCreateExt);
            aw0.multiply(iArrCreate3, bw0Var6.f46010x, iArrCreate3);
            aw0.multiply(iArrCreate3, bw0Var2.f46010x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = qh0.create();
        aw0.subtract(iArr3, iArr, iArrCreate4);
        aw0.subtract(iArr4, iArr2, iArrCreate);
        if (qh0.isZero(iArrCreate4)) {
            return qh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        aw0.square(iArrCreate4, iArrCreate2);
        int[] iArrCreate5 = qh0.create();
        aw0.multiply(iArrCreate2, iArrCreate4, iArrCreate5);
        aw0.multiply(iArrCreate2, iArr3, iArrCreate2);
        aw0.negate(iArrCreate5, iArrCreate5);
        qh0.mul(iArr4, iArrCreate5, iArrCreateExt);
        aw0.reduce32(qh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate5), iArrCreate5);
        bw0 bw0Var7 = new bw0(iArrCreate3);
        aw0.square(iArrCreate, bw0Var7.f46010x);
        int[] iArr5 = bw0Var7.f46010x;
        aw0.subtract(iArr5, iArrCreate5, iArr5);
        bw0 bw0Var8 = new bw0(iArrCreate5);
        aw0.subtract(iArrCreate2, bw0Var7.f46010x, bw0Var8.f46010x);
        aw0.multiplyAddToExt(bw0Var8.f46010x, iArrCreate, iArrCreateExt);
        aw0.reduce(iArrCreateExt, bw0Var8.f46010x);
        bw0 bw0Var9 = new bw0(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = bw0Var9.f46010x;
            aw0.multiply(iArr6, bw0Var5.f46010x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = bw0Var9.f46010x;
            aw0.multiply(iArr7, bw0Var6.f46010x, iArr7);
        }
        return new cw0(curve, bw0Var7, bw0Var8, new AbstractC1330va[]{bw0Var9});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new cw0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new cw0(this.curve, this.f60653x, this.f60654y.negate(), this.f60655zs);
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
        bw0 bw0Var = (bw0) this.f60654y;
        if (bw0Var.isZero()) {
            return curve.getInfinity();
        }
        bw0 bw0Var2 = (bw0) this.f60653x;
        bw0 bw0Var3 = (bw0) this.f60655zs[0];
        int[] iArrCreate = qh0.create();
        aw0.square(bw0Var.f46010x, iArrCreate);
        int[] iArrCreate2 = qh0.create();
        aw0.square(iArrCreate, iArrCreate2);
        int[] iArrCreate3 = qh0.create();
        aw0.square(bw0Var2.f46010x, iArrCreate3);
        aw0.reduce32(qh0.addBothTo(iArrCreate3, iArrCreate3, iArrCreate3), iArrCreate3);
        aw0.multiply(iArrCreate, bw0Var2.f46010x, iArrCreate);
        aw0.reduce32(yh0.shiftUpBits(6, iArrCreate, 2, 0), iArrCreate);
        int[] iArrCreate4 = qh0.create();
        aw0.reduce32(yh0.shiftUpBits(6, iArrCreate2, 3, 0, iArrCreate4), iArrCreate4);
        bw0 bw0Var4 = new bw0(iArrCreate2);
        aw0.square(iArrCreate3, bw0Var4.f46010x);
        int[] iArr = bw0Var4.f46010x;
        aw0.subtract(iArr, iArrCreate, iArr);
        int[] iArr2 = bw0Var4.f46010x;
        aw0.subtract(iArr2, iArrCreate, iArr2);
        bw0 bw0Var5 = new bw0(iArrCreate);
        aw0.subtract(iArrCreate, bw0Var4.f46010x, bw0Var5.f46010x);
        int[] iArr3 = bw0Var5.f46010x;
        aw0.multiply(iArr3, iArrCreate3, iArr3);
        int[] iArr4 = bw0Var5.f46010x;
        aw0.subtract(iArr4, iArrCreate4, iArr4);
        bw0 bw0Var6 = new bw0(iArrCreate3);
        aw0.twice(bw0Var.f46010x, bw0Var6.f46010x);
        if (!bw0Var3.isOne()) {
            int[] iArr5 = bw0Var6.f46010x;
            aw0.multiply(iArr5, bw0Var3.f46010x, iArr5);
        }
        return new cw0(curve, bw0Var4, bw0Var5, new AbstractC1330va[]{bw0Var6});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twice().add(abstractC1341vl);
    }

    public cw0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
