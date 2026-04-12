package p000;

import p000.AbstractC1341vl;

/* loaded from: classes2.dex */
public class kx0 extends AbstractC1341vl.a1 {
    public kx0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2);
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
        AbstractC1330va abstractC1330vaMultiply;
        AbstractC1330va abstractC1330vaMultiply2;
        AbstractC1330va abstractC1330vaMultiply3;
        AbstractC1330va abstractC1330vaAdd;
        AbstractC1330va abstractC1330vaFromBigInteger;
        AbstractC1330va abstractC1330vaAdd2;
        if (isInfinity()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return this;
        }
        AbstractC1316ux curve = getCurve();
        AbstractC1330va abstractC1330vaMultiply4 = this.f60653x;
        AbstractC1330va rawXCoord = abstractC1341vl.getRawXCoord();
        if (abstractC1330vaMultiply4.isZero()) {
            return rawXCoord.isZero() ? curve.getInfinity() : abstractC1341vl.add(this);
        }
        AbstractC1330va abstractC1330va = this.f60654y;
        AbstractC1330va abstractC1330va2 = this.f60655zs[0];
        AbstractC1330va rawYCoord = abstractC1341vl.getRawYCoord();
        AbstractC1330va zCoord = abstractC1341vl.getZCoord(0);
        boolean zIsOne = abstractC1330va2.isOne();
        if (zIsOne) {
            abstractC1330vaMultiply = rawXCoord;
            abstractC1330vaMultiply2 = rawYCoord;
        } else {
            abstractC1330vaMultiply = rawXCoord.multiply(abstractC1330va2);
            abstractC1330vaMultiply2 = rawYCoord.multiply(abstractC1330va2);
        }
        boolean zIsOne2 = zCoord.isOne();
        if (zIsOne2) {
            abstractC1330vaMultiply3 = abstractC1330va;
        } else {
            abstractC1330vaMultiply4 = abstractC1330vaMultiply4.multiply(zCoord);
            abstractC1330vaMultiply3 = abstractC1330va.multiply(zCoord);
        }
        AbstractC1330va abstractC1330vaAdd3 = abstractC1330vaMultiply3.add(abstractC1330vaMultiply2);
        AbstractC1330va abstractC1330vaAdd4 = abstractC1330vaMultiply4.add(abstractC1330vaMultiply);
        if (abstractC1330vaAdd4.isZero()) {
            return abstractC1330vaAdd3.isZero() ? twice() : curve.getInfinity();
        }
        if (rawXCoord.isZero()) {
            AbstractC1341vl abstractC1341vlNormalize = normalize();
            AbstractC1330va xCoord = abstractC1341vlNormalize.getXCoord();
            AbstractC1330va yCoord = abstractC1341vlNormalize.getYCoord();
            AbstractC1330va abstractC1330vaDivide = yCoord.add(rawYCoord).divide(xCoord);
            abstractC1330vaAdd = AbstractC0003a2.m23a4(abstractC1330vaDivide, abstractC1330vaDivide, xCoord).add(curve.getA());
            if (abstractC1330vaAdd.isZero()) {
                return new kx0(curve, abstractC1330vaAdd, curve.getB().sqrt());
            }
            abstractC1330vaAdd2 = abstractC1330vaDivide.multiply(xCoord.add(abstractC1330vaAdd)).add(abstractC1330vaAdd).add(yCoord).divide(abstractC1330vaAdd).add(abstractC1330vaAdd);
            abstractC1330vaFromBigInteger = curve.fromBigInteger(InterfaceC1315uw.ONE);
        } else {
            AbstractC1330va abstractC1330vaSquare = abstractC1330vaAdd4.square();
            AbstractC1330va abstractC1330vaMultiply5 = abstractC1330vaAdd3.multiply(abstractC1330vaMultiply4);
            AbstractC1330va abstractC1330vaMultiply6 = abstractC1330vaAdd3.multiply(abstractC1330vaMultiply);
            AbstractC1330va abstractC1330vaMultiply7 = abstractC1330vaMultiply5.multiply(abstractC1330vaMultiply6);
            if (abstractC1330vaMultiply7.isZero()) {
                return new kx0(curve, abstractC1330vaMultiply7, curve.getB().sqrt());
            }
            AbstractC1330va abstractC1330vaMultiply8 = abstractC1330vaAdd3.multiply(abstractC1330vaSquare);
            AbstractC1330va abstractC1330vaMultiply9 = !zIsOne2 ? abstractC1330vaMultiply8.multiply(zCoord) : abstractC1330vaMultiply8;
            AbstractC1330va abstractC1330vaSquarePlusProduct = abstractC1330vaMultiply6.add(abstractC1330vaSquare).squarePlusProduct(abstractC1330vaMultiply9, abstractC1330va.add(abstractC1330va2));
            if (!zIsOne) {
                abstractC1330vaMultiply9 = abstractC1330vaMultiply9.multiply(abstractC1330va2);
            }
            abstractC1330vaAdd = abstractC1330vaMultiply7;
            abstractC1330vaFromBigInteger = abstractC1330vaMultiply9;
            abstractC1330vaAdd2 = abstractC1330vaSquarePlusProduct;
        }
        return new kx0(curve, abstractC1330vaAdd, abstractC1330vaAdd2, new AbstractC1330va[]{abstractC1330vaFromBigInteger});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl detach() {
        return new kx0(null, getAffineXCoord(), getAffineYCoord());
    }

    @Override // p000.AbstractC1341vl
    public boolean getCompressionYTilde() {
        AbstractC1330va rawXCoord = getRawXCoord();
        return (rawXCoord.isZero() || getRawYCoord().testBitZero() == rawXCoord.testBitZero()) ? false : true;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1330va getYCoord() {
        AbstractC1330va abstractC1330va = this.f60653x;
        AbstractC1330va abstractC1330va2 = this.f60654y;
        if (isInfinity() || abstractC1330va.isZero()) {
            return abstractC1330va2;
        }
        AbstractC1330va abstractC1330vaMultiply = abstractC1330va2.add(abstractC1330va).multiply(abstractC1330va);
        AbstractC1330va abstractC1330va3 = this.f60655zs[0];
        return !abstractC1330va3.isOne() ? abstractC1330vaMultiply.divide(abstractC1330va3) : abstractC1330vaMultiply;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl negate() {
        if (!isInfinity()) {
            AbstractC1330va abstractC1330va = this.f60653x;
            if (!abstractC1330va.isZero()) {
                AbstractC1330va abstractC1330va2 = this.f60654y;
                AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                return new kx0(this.curve, abstractC1330va, abstractC1330va2.add(abstractC1330va3), new AbstractC1330va[]{abstractC1330va3});
            }
        }
        return this;
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twice() {
        if (isInfinity()) {
            return this;
        }
        AbstractC1316ux curve = getCurve();
        AbstractC1330va abstractC1330vaMultiply = this.f60653x;
        if (abstractC1330vaMultiply.isZero()) {
            return curve.getInfinity();
        }
        AbstractC1330va abstractC1330va = this.f60654y;
        AbstractC1330va abstractC1330va2 = this.f60655zs[0];
        boolean zIsOne = abstractC1330va2.isOne();
        AbstractC1330va abstractC1330vaMultiply2 = zIsOne ? abstractC1330va : abstractC1330va.multiply(abstractC1330va2);
        AbstractC1330va abstractC1330vaSquare = zIsOne ? abstractC1330va2 : abstractC1330va2.square();
        AbstractC1330va a = curve.getA();
        if (!zIsOne) {
            a = a.multiply(abstractC1330vaSquare);
        }
        AbstractC1330va abstractC1330vaM23a4 = AbstractC0003a2.m23a4(abstractC1330va, abstractC1330vaMultiply2, a);
        if (abstractC1330vaM23a4.isZero()) {
            return new kx0(curve, abstractC1330vaM23a4, curve.getB().sqrt());
        }
        AbstractC1330va abstractC1330vaSquare2 = abstractC1330vaM23a4.square();
        AbstractC1330va abstractC1330vaMultiply3 = zIsOne ? abstractC1330vaM23a4 : abstractC1330vaM23a4.multiply(abstractC1330vaSquare);
        if (!zIsOne) {
            abstractC1330vaMultiply = abstractC1330vaMultiply.multiply(abstractC1330va2);
        }
        return new kx0(curve, abstractC1330vaSquare2, abstractC1330vaMultiply.squarePlusProduct(abstractC1330vaM23a4, abstractC1330vaMultiply2).add(abstractC1330vaSquare2).add(abstractC1330vaMultiply3), new AbstractC1330va[]{abstractC1330vaMultiply3});
    }

    @Override // p000.AbstractC1341vl
    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        if (isInfinity()) {
            return abstractC1341vl;
        }
        if (abstractC1341vl.isInfinity()) {
            return twice();
        }
        AbstractC1316ux curve = getCurve();
        AbstractC1330va abstractC1330va = this.f60653x;
        if (abstractC1330va.isZero()) {
            return abstractC1341vl;
        }
        AbstractC1330va rawXCoord = abstractC1341vl.getRawXCoord();
        AbstractC1330va zCoord = abstractC1341vl.getZCoord(0);
        if (rawXCoord.isZero() || !zCoord.isOne()) {
            return twice().add(abstractC1341vl);
        }
        AbstractC1330va abstractC1330va2 = this.f60654y;
        AbstractC1330va abstractC1330va3 = this.f60655zs[0];
        AbstractC1330va rawYCoord = abstractC1341vl.getRawYCoord();
        AbstractC1330va abstractC1330vaSquare = abstractC1330va.square();
        AbstractC1330va abstractC1330vaSquare2 = abstractC1330va2.square();
        AbstractC1330va abstractC1330vaSquare3 = abstractC1330va3.square();
        AbstractC1330va abstractC1330vaAdd = curve.getA().multiply(abstractC1330vaSquare3).add(abstractC1330vaSquare2).add(abstractC1330va2.multiply(abstractC1330va3));
        AbstractC1330va abstractC1330vaAddOne = rawYCoord.addOne();
        AbstractC1330va abstractC1330vaMultiplyPlusProduct = curve.getA().add(abstractC1330vaAddOne).multiply(abstractC1330vaSquare3).add(abstractC1330vaSquare2).multiplyPlusProduct(abstractC1330vaAdd, abstractC1330vaSquare, abstractC1330vaSquare3);
        AbstractC1330va abstractC1330vaMultiply = rawXCoord.multiply(abstractC1330vaSquare3);
        AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaMultiply.add(abstractC1330vaAdd).square();
        if (abstractC1330vaSquare4.isZero()) {
            return abstractC1330vaMultiplyPlusProduct.isZero() ? abstractC1341vl.twice() : curve.getInfinity();
        }
        if (abstractC1330vaMultiplyPlusProduct.isZero()) {
            return new kx0(curve, abstractC1330vaMultiplyPlusProduct, curve.getB().sqrt());
        }
        AbstractC1330va abstractC1330vaMultiply2 = abstractC1330vaMultiplyPlusProduct.square().multiply(abstractC1330vaMultiply);
        AbstractC1330va abstractC1330vaMultiply3 = abstractC1330vaMultiplyPlusProduct.multiply(abstractC1330vaSquare4).multiply(abstractC1330vaSquare3);
        return new kx0(curve, abstractC1330vaMultiply2, abstractC1330vaMultiplyPlusProduct.add(abstractC1330vaSquare4).square().multiplyPlusProduct(abstractC1330vaAdd, abstractC1330vaAddOne, abstractC1330vaMultiply3), new AbstractC1330va[]{abstractC1330vaMultiply3});
    }

    public kx0(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
    }
}
