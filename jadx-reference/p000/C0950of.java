package p000;

import p000.AbstractC1341vl;

/* renamed from: of */
/* loaded from: classes2.dex */
public class C0950of extends AbstractC1341vl.a2 {
    public C0950of(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
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
        C0949oe c0949oe = (C0949oe) this.f60653x;
        C0949oe c0949oe2 = (C0949oe) this.f60654y;
        C0949oe c0949oe3 = (C0949oe) this.f60655zs[0];
        C0949oe c0949oe4 = (C0949oe) abstractC1341vl.getXCoord();
        C0949oe c0949oe5 = (C0949oe) abstractC1341vl.getYCoord();
        C0949oe c0949oe6 = (C0949oe) abstractC1341vl.getZCoord(0);
        int[] iArrCreateExt = sh0.createExt();
        int[] iArrCreate = sh0.create();
        int[] iArrCreate2 = sh0.create();
        int[] iArrCreate3 = sh0.create();
        boolean zIsOne = c0949oe3.isOne();
        if (zIsOne) {
            iArr = c0949oe4.f58791x;
            iArr2 = c0949oe5.f58791x;
        } else {
            C0948od.square(c0949oe3.f58791x, iArrCreate2);
            C0948od.multiply(iArrCreate2, c0949oe4.f58791x, iArrCreate);
            C0948od.multiply(iArrCreate2, c0949oe3.f58791x, iArrCreate2);
            C0948od.multiply(iArrCreate2, c0949oe5.f58791x, iArrCreate2);
            iArr = iArrCreate;
            iArr2 = iArrCreate2;
        }
        boolean zIsOne2 = c0949oe6.isOne();
        if (zIsOne2) {
            iArr3 = c0949oe.f58791x;
            iArr4 = c0949oe2.f58791x;
        } else {
            C0948od.square(c0949oe6.f58791x, iArrCreate3);
            C0948od.multiply(iArrCreate3, c0949oe.f58791x, iArrCreateExt);
            C0948od.multiply(iArrCreate3, c0949oe6.f58791x, iArrCreate3);
            C0948od.multiply(iArrCreate3, c0949oe2.f58791x, iArrCreate3);
            iArr3 = iArrCreateExt;
            iArr4 = iArrCreate3;
        }
        int[] iArrCreate4 = sh0.create();
        C0948od.subtract(iArr3, iArr, iArrCreate4);
        C0948od.subtract(iArr4, iArr2, iArrCreate);
        if (sh0.isZero(iArrCreate4)) {
            return sh0.isZero(iArrCreate) ? twice() : curve.getInfinity();
        }
        int[] iArrCreate5 = sh0.create();
        C0948od.square(iArrCreate4, iArrCreate5);
        int[] iArrCreate6 = sh0.create();
        C0948od.multiply(iArrCreate5, iArrCreate4, iArrCreate6);
        C0948od.multiply(iArrCreate5, iArr3, iArrCreate2);
        C0948od.negate(iArrCreate6, iArrCreate6);
        sh0.mul(iArr4, iArrCreate6, iArrCreateExt);
        C0948od.reduce27(sh0.addBothTo(iArrCreate2, iArrCreate2, iArrCreate6), iArrCreate6);
        C0949oe c0949oe7 = new C0949oe(iArrCreate3);
        C0948od.square(iArrCreate, c0949oe7.f58791x);
        int[] iArr5 = c0949oe7.f58791x;
        C0948od.subtract(iArr5, iArrCreate6, iArr5);
        C0949oe c0949oe8 = new C0949oe(iArrCreate6);
        C0948od.subtract(iArrCreate2, c0949oe7.f58791x, c0949oe8.f58791x);
        C0948od.multiplyAddToExt(c0949oe8.f58791x, iArrCreate, iArrCreateExt);
        C0948od.reduce(iArrCreateExt, c0949oe8.f58791x);
        C0949oe c0949oe9 = new C0949oe(iArrCreate4);
        if (!zIsOne) {
            int[] iArr6 = c0949oe9.f58791x;
            C0948od.multiply(iArr6, c0949oe3.f58791x, iArr6);
        }
        if (!zIsOne2) {
            int[] iArr7 = c0949oe9.f58791x;
            C0948od.multiply(iArr7, c0949oe6.f58791x, iArr7);
        }
        if (!zIsOne || !zIsOne2) {
            iArrCreate5 = null;
        }
        return new C0950of(curve, c0949oe7, c0949oe8, new AbstractC1330va[]{c0949oe9, calculateJacobianModifiedW(c0949oe9, iArrCreate5)});
    }

    public C0949oe calculateJacobianModifiedW(C0949oe c0949oe, int[] iArr) {
        C0949oe c0949oe2 = (C0949oe) getCurve().getA();
        if (c0949oe.isOne()) {
            return c0949oe2;
        }
        C0949oe c0949oe3 = new C0949oe();
        if (iArr == null) {
            iArr = c0949oe3.f58791x;
            C0948od.square(c0949oe.f58791x, iArr);
        }
        C0948od.square(iArr, c0949oe3.f58791x);
        int[] iArr2 = c0949oe3.f58791x;
        C0948od.multiply(iArr2, c0949oe2.f58791x, iArr2);
        return c0949oe3;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new C0950of(null, getAffineXCoord(), getAffineYCoord());
    }

    public C0949oe getJacobianModifiedW() {
        AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
        C0949oe c0949oe = (C0949oe) abstractC1330vaArr[1];
        if (c0949oe != null) {
            return c0949oe;
        }
        C0949oe c0949oeCalculateJacobianModifiedW = calculateJacobianModifiedW((C0949oe) abstractC1330vaArr[0], null);
        abstractC1330vaArr[1] = c0949oeCalculateJacobianModifiedW;
        return c0949oeCalculateJacobianModifiedW;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1330va getZCoord(int i) {
        return i == 1 ? getJacobianModifiedW() : super.getZCoord(i);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        return isInfinity() ? this : new C0950of(getCurve(), this.f60653x, this.f60654y.negate(), this.f60655zs);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl threeTimes() {
        return (isInfinity() || this.f60654y.isZero()) ? this : twiceJacobianModified(false).add(this);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twice() {
        if (isInfinity()) {
            return this;
        }
        return this.f60654y.isZero() ? getCurve().getInfinity() : twiceJacobianModified(true);
    }

    public C0950of twiceJacobianModified(boolean z) {
        C0949oe c0949oe;
        C0949oe c0949oe2 = (C0949oe) this.f60653x;
        C0949oe c0949oe3 = (C0949oe) this.f60654y;
        C0949oe c0949oe4 = (C0949oe) this.f60655zs[0];
        C0949oe jacobianModifiedW = getJacobianModifiedW();
        int[] iArrCreate = sh0.create();
        C0948od.square(c0949oe2.f58791x, iArrCreate);
        C0948od.reduce27(sh0.addTo(jacobianModifiedW.f58791x, iArrCreate) + sh0.addBothTo(iArrCreate, iArrCreate, iArrCreate), iArrCreate);
        int[] iArrCreate2 = sh0.create();
        C0948od.twice(c0949oe3.f58791x, iArrCreate2);
        int[] iArrCreate3 = sh0.create();
        C0948od.multiply(iArrCreate2, c0949oe3.f58791x, iArrCreate3);
        int[] iArrCreate4 = sh0.create();
        C0948od.multiply(iArrCreate3, c0949oe2.f58791x, iArrCreate4);
        C0948od.twice(iArrCreate4, iArrCreate4);
        int[] iArrCreate5 = sh0.create();
        C0948od.square(iArrCreate3, iArrCreate5);
        C0948od.twice(iArrCreate5, iArrCreate5);
        C0949oe c0949oe5 = new C0949oe(iArrCreate3);
        C0948od.square(iArrCreate, c0949oe5.f58791x);
        int[] iArr = c0949oe5.f58791x;
        C0948od.subtract(iArr, iArrCreate4, iArr);
        int[] iArr2 = c0949oe5.f58791x;
        C0948od.subtract(iArr2, iArrCreate4, iArr2);
        C0949oe c0949oe6 = new C0949oe(iArrCreate4);
        C0948od.subtract(iArrCreate4, c0949oe5.f58791x, c0949oe6.f58791x);
        int[] iArr3 = c0949oe6.f58791x;
        C0948od.multiply(iArr3, iArrCreate, iArr3);
        int[] iArr4 = c0949oe6.f58791x;
        C0948od.subtract(iArr4, iArrCreate5, iArr4);
        C0949oe c0949oe7 = new C0949oe(iArrCreate2);
        if (!sh0.isOne(c0949oe4.f58791x)) {
            int[] iArr5 = c0949oe7.f58791x;
            C0948od.multiply(iArr5, c0949oe4.f58791x, iArr5);
        }
        if (z) {
            c0949oe = new C0949oe(iArrCreate5);
            int[] iArr6 = c0949oe.f58791x;
            C0948od.multiply(iArr6, jacobianModifiedW.f58791x, iArr6);
            int[] iArr7 = c0949oe.f58791x;
            C0948od.twice(iArr7, iArr7);
        } else {
            c0949oe = null;
        }
        return new C0950of(getCurve(), c0949oe5, c0949oe6, new AbstractC1330va[]{c0949oe7, c0949oe});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return this == abstractC1341vl ? threeTimes() : isInfinity() ? abstractC1341vl : abstractC1341vl.isInfinity() ? twice() : this.f60654y.isZero() ? abstractC1341vl : twiceJacobianModified(false).add(abstractC1341vl);
    }

    public C0950of(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
