package p000;

import java.math.BigInteger;
import java.util.Hashtable;
import p000.AbstractC1316ux;
import p000.AbstractC1330va;

/* renamed from: vl */
/* loaded from: classes2.dex */
public abstract class AbstractC1341vl {
    protected static final AbstractC1330va[] EMPTY_ZS = new AbstractC1330va[0];
    protected AbstractC1316ux curve;
    protected Hashtable preCompTable;

    /* renamed from: x */
    protected AbstractC1330va f60653x;

    /* renamed from: y */
    protected AbstractC1330va f60654y;

    /* renamed from: zs */
    protected AbstractC1330va[] f60655zs;

    /* renamed from: vl$a0 */
    public class a0 implements zn0 {
        final /* synthetic */ boolean val$checkOrder;
        final /* synthetic */ boolean val$decompressed;

        public a0(boolean z, boolean z2) {
            this.val$decompressed = z;
            this.val$checkOrder = z2;
        }

        @Override // p000.zn0
        public ao0 precompute(ao0 ao0Var) {
            h91 h91Var = ao0Var instanceof h91 ? (h91) ao0Var : null;
            if (h91Var == null) {
                h91Var = new h91();
            }
            if (!h91Var.hasFailed()) {
                if (!h91Var.hasCurveEquationPassed()) {
                    if (!this.val$decompressed && !AbstractC1341vl.this.satisfiesCurveEquation()) {
                        h91Var.reportFailed();
                        return h91Var;
                    }
                    h91Var.reportCurveEquationPassed();
                }
                if (this.val$checkOrder && !h91Var.hasOrderPassed()) {
                    if (!AbstractC1341vl.this.satisfiesOrder()) {
                        h91Var.reportFailed();
                        return h91Var;
                    }
                    h91Var.reportOrderPassed();
                }
            }
            return h91Var;
        }
    }

    /* renamed from: vl$a1 */
    public static abstract class a1 extends AbstractC1341vl {
        public a1(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1341vl
        public boolean satisfiesCurveEquation() {
            AbstractC1330va abstractC1330vaMultiplyPlusProduct;
            AbstractC1330va abstractC1330vaSquarePlusProduct;
            AbstractC1316ux curve = getCurve();
            AbstractC1330va abstractC1330va = this.f60653x;
            AbstractC1330va a = curve.getA();
            AbstractC1330va b = curve.getB();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem != 6) {
                AbstractC1330va abstractC1330va2 = this.f60654y;
                AbstractC1330va abstractC1330vaMultiply = abstractC1330va2.add(abstractC1330va).multiply(abstractC1330va2);
                if (coordinateSystem != 0) {
                    if (coordinateSystem != 1) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                    if (!abstractC1330va3.isOne()) {
                        AbstractC1330va abstractC1330vaMultiply2 = abstractC1330va3.multiply(abstractC1330va3.square());
                        abstractC1330vaMultiply = abstractC1330vaMultiply.multiply(abstractC1330va3);
                        a = a.multiply(abstractC1330va3);
                        b = b.multiply(abstractC1330vaMultiply2);
                    }
                }
                return abstractC1330vaMultiply.equals(abstractC1330va.add(a).multiply(abstractC1330va.square()).add(b));
            }
            AbstractC1330va abstractC1330va4 = this.f60655zs[0];
            boolean zIsOne = abstractC1330va4.isOne();
            if (abstractC1330va.isZero()) {
                AbstractC1330va abstractC1330vaSquare = this.f60654y.square();
                if (!zIsOne) {
                    b = b.multiply(abstractC1330va4.square());
                }
                return abstractC1330vaSquare.equals(b);
            }
            AbstractC1330va abstractC1330va5 = this.f60654y;
            AbstractC1330va abstractC1330vaSquare2 = abstractC1330va.square();
            if (zIsOne) {
                abstractC1330vaMultiplyPlusProduct = AbstractC0003a2.m23a4(abstractC1330va5, abstractC1330va5, a);
                abstractC1330vaSquarePlusProduct = abstractC1330vaSquare2.square().add(b);
            } else {
                AbstractC1330va abstractC1330vaSquare3 = abstractC1330va4.square();
                AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaSquare3.square();
                abstractC1330vaMultiplyPlusProduct = abstractC1330va5.add(abstractC1330va4).multiplyPlusProduct(abstractC1330va5, a, abstractC1330vaSquare3);
                abstractC1330vaSquarePlusProduct = abstractC1330vaSquare2.squarePlusProduct(b, abstractC1330vaSquare4);
            }
            return abstractC1330vaMultiplyPlusProduct.multiply(abstractC1330vaSquare2).equals(abstractC1330vaSquarePlusProduct);
        }

        @Override // p000.AbstractC1341vl
        public boolean satisfiesOrder() {
            BigInteger cofactor = this.curve.getCofactor();
            if (InterfaceC1315uw.TWO.equals(cofactor)) {
                return ((AbstractC1330va.a0) normalize().getAffineXCoord()).trace() != 0;
            }
            if (!InterfaceC1315uw.FOUR.equals(cofactor)) {
                return super.satisfiesOrder();
            }
            AbstractC1341vl abstractC1341vlNormalize = normalize();
            AbstractC1330va affineXCoord = abstractC1341vlNormalize.getAffineXCoord();
            AbstractC1316ux abstractC1316ux = this.curve;
            AbstractC1330va abstractC1330vaSolveQuadraticEquation = ((AbstractC1316ux.a1) abstractC1316ux).solveQuadraticEquation(affineXCoord.add(abstractC1316ux.getA()));
            if (abstractC1330vaSolveQuadraticEquation == null) {
                return false;
            }
            return ((AbstractC1330va.a0) affineXCoord.multiply(abstractC1330vaSolveQuadraticEquation).add(abstractC1341vlNormalize.getAffineYCoord())).trace() == 0;
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl scaleX(AbstractC1330va abstractC1330va) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5) {
                AbstractC1330va rawXCoord = getRawXCoord();
                AbstractC1330va rawYCoord = getRawYCoord();
                return getCurve().createRawPoint(rawXCoord, rawYCoord.add(rawXCoord).divide(abstractC1330va).add(rawXCoord.multiply(abstractC1330va)), getRawZCoords());
            }
            if (curveCoordinateSystem != 6) {
                return super.scaleX(abstractC1330va);
            }
            AbstractC1330va rawXCoord2 = getRawXCoord();
            AbstractC1330va rawYCoord2 = getRawYCoord();
            AbstractC1330va abstractC1330va2 = getRawZCoords()[0];
            AbstractC1330va abstractC1330vaMultiply = rawXCoord2.multiply(abstractC1330va.square());
            return getCurve().createRawPoint(abstractC1330vaMultiply, rawYCoord2.add(rawXCoord2).add(abstractC1330vaMultiply), new AbstractC1330va[]{abstractC1330va2.multiply(abstractC1330va)});
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl scaleXNegateY(AbstractC1330va abstractC1330va) {
            return scaleX(abstractC1330va);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl scaleY(AbstractC1330va abstractC1330va) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 5 && curveCoordinateSystem != 6) {
                return super.scaleY(abstractC1330va);
            }
            AbstractC1330va rawXCoord = getRawXCoord();
            return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).multiply(abstractC1330va).add(rawXCoord), getRawZCoords());
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl scaleYNegateX(AbstractC1330va abstractC1330va) {
            return scaleY(abstractC1330va);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl subtract(AbstractC1341vl abstractC1341vl) {
            return abstractC1341vl.isInfinity() ? this : add(abstractC1341vl.negate());
        }

        public a1 tau() {
            AbstractC1341vl abstractC1341vlCreateRawPoint;
            if (isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va abstractC1330va = this.f60653x;
            if (coordinateSystem == 0) {
                abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.square(), this.f60654y.square());
            } else {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                    abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.square(), this.f60654y.square());
                }
                abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.square(), this.f60654y.square(), new AbstractC1330va[]{this.f60655zs[0].square()});
            }
            return (a1) abstractC1341vlCreateRawPoint;
        }

        public a1 tauPow(int i) {
            AbstractC1341vl abstractC1341vlCreateRawPoint;
            if (isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va abstractC1330va = this.f60653x;
            if (coordinateSystem == 0) {
                abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.squarePow(i), this.f60654y.squarePow(i));
            } else {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                    abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.squarePow(i), this.f60654y.squarePow(i));
                }
                abstractC1341vlCreateRawPoint = curve.createRawPoint(abstractC1330va.squarePow(i), this.f60654y.squarePow(i), new AbstractC1330va[]{this.f60655zs[0].squarePow(i)});
            }
            return (a1) abstractC1341vlCreateRawPoint;
        }

        public a1(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }
    }

    /* renamed from: vl$a2 */
    public static abstract class a2 extends AbstractC1341vl {
        public a2(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1341vl
        public boolean getCompressionYTilde() {
            return getAffineYCoord().testBitZero();
        }

        @Override // p000.AbstractC1341vl
        public boolean satisfiesCurveEquation() {
            AbstractC1330va abstractC1330va = this.f60653x;
            AbstractC1330va abstractC1330va2 = this.f60654y;
            AbstractC1330va a = this.curve.getA();
            AbstractC1330va b = this.curve.getB();
            AbstractC1330va abstractC1330vaSquare = abstractC1330va2.square();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 0) {
                if (curveCoordinateSystem == 1) {
                    AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                    if (!abstractC1330va3.isOne()) {
                        AbstractC1330va abstractC1330vaSquare2 = abstractC1330va3.square();
                        AbstractC1330va abstractC1330vaMultiply = abstractC1330va3.multiply(abstractC1330vaSquare2);
                        abstractC1330vaSquare = abstractC1330vaSquare.multiply(abstractC1330va3);
                        a = a.multiply(abstractC1330vaSquare2);
                        b = b.multiply(abstractC1330vaMultiply);
                    }
                } else {
                    if (curveCoordinateSystem != 2 && curveCoordinateSystem != 3 && curveCoordinateSystem != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    AbstractC1330va abstractC1330va4 = this.f60655zs[0];
                    if (!abstractC1330va4.isOne()) {
                        AbstractC1330va abstractC1330vaSquare3 = abstractC1330va4.square();
                        AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaSquare3.square();
                        AbstractC1330va abstractC1330vaMultiply2 = abstractC1330vaSquare3.multiply(abstractC1330vaSquare4);
                        a = a.multiply(abstractC1330vaSquare4);
                        b = b.multiply(abstractC1330vaMultiply2);
                    }
                }
            }
            return abstractC1330vaSquare.equals(abstractC1330va.square().add(a).multiply(abstractC1330va).add(b));
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl subtract(AbstractC1341vl abstractC1341vl) {
            return abstractC1341vl.isInfinity() ? this : add(abstractC1341vl.negate());
        }

        public a2(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }
    }

    /* renamed from: vl$a3 */
    public static class a3 extends a1 {
        public a3(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
            AbstractC1330va abstractC1330vaMultiply;
            AbstractC1330va abstractC1330vaMultiply2;
            AbstractC1330va abstractC1330vaMultiply3;
            AbstractC1330va abstractC1330vaFromBigInteger;
            AbstractC1330va abstractC1330vaAdd;
            AbstractC1330va abstractC1330vaAdd2;
            if (isInfinity()) {
                return abstractC1341vl;
            }
            if (abstractC1341vl.isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va abstractC1330vaMultiply4 = this.f60653x;
            AbstractC1330va abstractC1330va = abstractC1341vl.f60653x;
            if (coordinateSystem == 0) {
                AbstractC1330va abstractC1330va2 = this.f60654y;
                AbstractC1330va abstractC1330va3 = abstractC1341vl.f60654y;
                AbstractC1330va abstractC1330vaAdd3 = abstractC1330vaMultiply4.add(abstractC1330va);
                AbstractC1330va abstractC1330vaAdd4 = abstractC1330va2.add(abstractC1330va3);
                if (abstractC1330vaAdd3.isZero()) {
                    return abstractC1330vaAdd4.isZero() ? twice() : curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaDivide = abstractC1330vaAdd4.divide(abstractC1330vaAdd3);
                AbstractC1330va abstractC1330vaAdd5 = AbstractC0003a2.m23a4(abstractC1330vaDivide, abstractC1330vaDivide, abstractC1330vaAdd3).add(curve.getA());
                return new a3(curve, abstractC1330vaAdd5, abstractC1330vaDivide.multiply(abstractC1330vaMultiply4.add(abstractC1330vaAdd5)).add(abstractC1330vaAdd5).add(abstractC1330va2));
            }
            if (coordinateSystem == 1) {
                AbstractC1330va abstractC1330va4 = this.f60654y;
                AbstractC1330va abstractC1330vaMultiply5 = this.f60655zs[0];
                AbstractC1330va abstractC1330va5 = abstractC1341vl.f60654y;
                AbstractC1330va abstractC1330va6 = abstractC1341vl.f60655zs[0];
                boolean zIsOne = abstractC1330va6.isOne();
                AbstractC1330va abstractC1330vaAdd6 = abstractC1330vaMultiply5.multiply(abstractC1330va5).add(zIsOne ? abstractC1330va4 : abstractC1330va4.multiply(abstractC1330va6));
                AbstractC1330va abstractC1330vaAdd7 = abstractC1330vaMultiply5.multiply(abstractC1330va).add(zIsOne ? abstractC1330vaMultiply4 : abstractC1330vaMultiply4.multiply(abstractC1330va6));
                if (abstractC1330vaAdd7.isZero()) {
                    return abstractC1330vaAdd6.isZero() ? twice() : curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaSquare = abstractC1330vaAdd7.square();
                AbstractC1330va abstractC1330vaMultiply6 = abstractC1330vaSquare.multiply(abstractC1330vaAdd7);
                if (!zIsOne) {
                    abstractC1330vaMultiply5 = abstractC1330vaMultiply5.multiply(abstractC1330va6);
                }
                AbstractC1330va abstractC1330vaAdd8 = abstractC1330vaAdd6.add(abstractC1330vaAdd7);
                AbstractC1330va abstractC1330vaAdd9 = abstractC1330vaAdd8.multiplyPlusProduct(abstractC1330vaAdd6, abstractC1330vaSquare, curve.getA()).multiply(abstractC1330vaMultiply5).add(abstractC1330vaMultiply6);
                AbstractC1330va abstractC1330vaMultiply7 = abstractC1330vaAdd7.multiply(abstractC1330vaAdd9);
                if (!zIsOne) {
                    abstractC1330vaSquare = abstractC1330vaSquare.multiply(abstractC1330va6);
                }
                return new a3(curve, abstractC1330vaMultiply7, abstractC1330vaAdd6.multiplyPlusProduct(abstractC1330vaMultiply4, abstractC1330vaAdd7, abstractC1330va4).multiplyPlusProduct(abstractC1330vaSquare, abstractC1330vaAdd8, abstractC1330vaAdd9), new AbstractC1330va[]{abstractC1330vaMultiply6.multiply(abstractC1330vaMultiply5)});
            }
            if (coordinateSystem != 6) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            if (abstractC1330vaMultiply4.isZero()) {
                return abstractC1330va.isZero() ? curve.getInfinity() : abstractC1341vl.add(this);
            }
            AbstractC1330va abstractC1330va7 = this.f60654y;
            AbstractC1330va abstractC1330va8 = this.f60655zs[0];
            AbstractC1330va abstractC1330va9 = abstractC1341vl.f60654y;
            AbstractC1330va abstractC1330va10 = abstractC1341vl.f60655zs[0];
            boolean zIsOne2 = abstractC1330va8.isOne();
            if (zIsOne2) {
                abstractC1330vaMultiply = abstractC1330va;
                abstractC1330vaMultiply2 = abstractC1330va9;
            } else {
                abstractC1330vaMultiply = abstractC1330va.multiply(abstractC1330va8);
                abstractC1330vaMultiply2 = abstractC1330va9.multiply(abstractC1330va8);
            }
            boolean zIsOne3 = abstractC1330va10.isOne();
            if (zIsOne3) {
                abstractC1330vaMultiply3 = abstractC1330va7;
            } else {
                abstractC1330vaMultiply4 = abstractC1330vaMultiply4.multiply(abstractC1330va10);
                abstractC1330vaMultiply3 = abstractC1330va7.multiply(abstractC1330va10);
            }
            AbstractC1330va abstractC1330vaAdd10 = abstractC1330vaMultiply3.add(abstractC1330vaMultiply2);
            AbstractC1330va abstractC1330vaAdd11 = abstractC1330vaMultiply4.add(abstractC1330vaMultiply);
            if (abstractC1330vaAdd11.isZero()) {
                return abstractC1330vaAdd10.isZero() ? twice() : curve.getInfinity();
            }
            if (abstractC1330va.isZero()) {
                AbstractC1341vl abstractC1341vlNormalize = normalize();
                AbstractC1330va xCoord = abstractC1341vlNormalize.getXCoord();
                AbstractC1330va yCoord = abstractC1341vlNormalize.getYCoord();
                AbstractC1330va abstractC1330vaDivide2 = yCoord.add(abstractC1330va9).divide(xCoord);
                abstractC1330vaAdd2 = AbstractC0003a2.m23a4(abstractC1330vaDivide2, abstractC1330vaDivide2, xCoord).add(curve.getA());
                if (abstractC1330vaAdd2.isZero()) {
                    return new a3(curve, abstractC1330vaAdd2, curve.getB().sqrt());
                }
                abstractC1330vaAdd = abstractC1330vaDivide2.multiply(xCoord.add(abstractC1330vaAdd2)).add(abstractC1330vaAdd2).add(yCoord).divide(abstractC1330vaAdd2).add(abstractC1330vaAdd2);
                abstractC1330vaFromBigInteger = curve.fromBigInteger(InterfaceC1315uw.ONE);
            } else {
                AbstractC1330va abstractC1330vaSquare2 = abstractC1330vaAdd11.square();
                AbstractC1330va abstractC1330vaMultiply8 = abstractC1330vaAdd10.multiply(abstractC1330vaMultiply4);
                AbstractC1330va abstractC1330vaMultiply9 = abstractC1330vaAdd10.multiply(abstractC1330vaMultiply);
                AbstractC1330va abstractC1330vaMultiply10 = abstractC1330vaMultiply8.multiply(abstractC1330vaMultiply9);
                if (abstractC1330vaMultiply10.isZero()) {
                    return new a3(curve, abstractC1330vaMultiply10, curve.getB().sqrt());
                }
                AbstractC1330va abstractC1330vaMultiply11 = abstractC1330vaAdd10.multiply(abstractC1330vaSquare2);
                AbstractC1330va abstractC1330vaMultiply12 = !zIsOne3 ? abstractC1330vaMultiply11.multiply(abstractC1330va10) : abstractC1330vaMultiply11;
                AbstractC1330va abstractC1330vaSquarePlusProduct = abstractC1330vaMultiply9.add(abstractC1330vaSquare2).squarePlusProduct(abstractC1330vaMultiply12, abstractC1330va7.add(abstractC1330va8));
                if (!zIsOne2) {
                    abstractC1330vaMultiply12 = abstractC1330vaMultiply12.multiply(abstractC1330va8);
                }
                abstractC1330vaFromBigInteger = abstractC1330vaMultiply12;
                abstractC1330vaAdd = abstractC1330vaSquarePlusProduct;
                abstractC1330vaAdd2 = abstractC1330vaMultiply10;
            }
            return new a3(curve, abstractC1330vaAdd2, abstractC1330vaAdd, new AbstractC1330va[]{abstractC1330vaFromBigInteger});
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl detach() {
            return new a3(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // p000.AbstractC1341vl
        public boolean getCompressionYTilde() {
            AbstractC1330va rawXCoord = getRawXCoord();
            if (rawXCoord.isZero()) {
                return false;
            }
            AbstractC1330va rawYCoord = getRawYCoord();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            return (curveCoordinateSystem == 5 || curveCoordinateSystem == 6) ? rawYCoord.testBitZero() != rawXCoord.testBitZero() : rawYCoord.divide(rawXCoord).testBitZero();
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1330va getYCoord() {
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 5 && curveCoordinateSystem != 6) {
                return this.f60654y;
            }
            AbstractC1330va abstractC1330va = this.f60653x;
            AbstractC1330va abstractC1330va2 = this.f60654y;
            if (isInfinity() || abstractC1330va.isZero()) {
                return abstractC1330va2;
            }
            AbstractC1330va abstractC1330vaMultiply = abstractC1330va2.add(abstractC1330va).multiply(abstractC1330va);
            if (6 == curveCoordinateSystem) {
                AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                if (!abstractC1330va3.isOne()) {
                    return abstractC1330vaMultiply.divide(abstractC1330va3);
                }
            }
            return abstractC1330vaMultiply;
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl negate() {
            if (!isInfinity()) {
                AbstractC1330va abstractC1330va = this.f60653x;
                if (!abstractC1330va.isZero()) {
                    int curveCoordinateSystem = getCurveCoordinateSystem();
                    if (curveCoordinateSystem == 0) {
                        return new a3(this.curve, abstractC1330va, this.f60654y.add(abstractC1330va));
                    }
                    if (curveCoordinateSystem == 1) {
                        return new a3(this.curve, abstractC1330va, this.f60654y.add(abstractC1330va), new AbstractC1330va[]{this.f60655zs[0]});
                    }
                    if (curveCoordinateSystem == 5) {
                        return new a3(this.curve, abstractC1330va, this.f60654y.addOne());
                    }
                    if (curveCoordinateSystem != 6) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    AbstractC1330va abstractC1330va2 = this.f60654y;
                    AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                    return new a3(this.curve, abstractC1330va, abstractC1330va2.add(abstractC1330va3), new AbstractC1330va[]{abstractC1330va3});
                }
            }
            return this;
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl twice() {
            AbstractC1330va abstractC1330vaAdd;
            if (isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            AbstractC1330va abstractC1330vaMultiply = this.f60653x;
            if (abstractC1330vaMultiply.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 0) {
                AbstractC1330va abstractC1330vaAdd2 = this.f60654y.divide(abstractC1330vaMultiply).add(abstractC1330vaMultiply);
                AbstractC1330va abstractC1330vaAdd3 = abstractC1330vaAdd2.square().add(abstractC1330vaAdd2).add(curve.getA());
                return new a3(curve, abstractC1330vaAdd3, abstractC1330vaMultiply.squarePlusProduct(abstractC1330vaAdd3, abstractC1330vaAdd2.addOne()));
            }
            if (coordinateSystem == 1) {
                AbstractC1330va abstractC1330vaMultiply2 = this.f60654y;
                AbstractC1330va abstractC1330va = this.f60655zs[0];
                boolean zIsOne = abstractC1330va.isOne();
                AbstractC1330va abstractC1330vaMultiply3 = zIsOne ? abstractC1330vaMultiply : abstractC1330vaMultiply.multiply(abstractC1330va);
                if (!zIsOne) {
                    abstractC1330vaMultiply2 = abstractC1330vaMultiply2.multiply(abstractC1330va);
                }
                AbstractC1330va abstractC1330vaSquare = abstractC1330vaMultiply.square();
                AbstractC1330va abstractC1330vaAdd4 = abstractC1330vaSquare.add(abstractC1330vaMultiply2);
                AbstractC1330va abstractC1330vaSquare2 = abstractC1330vaMultiply3.square();
                AbstractC1330va abstractC1330vaAdd5 = abstractC1330vaAdd4.add(abstractC1330vaMultiply3);
                AbstractC1330va abstractC1330vaMultiplyPlusProduct = abstractC1330vaAdd5.multiplyPlusProduct(abstractC1330vaAdd4, abstractC1330vaSquare2, curve.getA());
                return new a3(curve, abstractC1330vaMultiply3.multiply(abstractC1330vaMultiplyPlusProduct), abstractC1330vaSquare.square().multiplyPlusProduct(abstractC1330vaMultiply3, abstractC1330vaMultiplyPlusProduct, abstractC1330vaAdd5), new AbstractC1330va[]{abstractC1330vaMultiply3.multiply(abstractC1330vaSquare2)});
            }
            if (coordinateSystem != 6) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            AbstractC1330va abstractC1330va2 = this.f60654y;
            AbstractC1330va abstractC1330va3 = this.f60655zs[0];
            boolean zIsOne2 = abstractC1330va3.isOne();
            AbstractC1330va abstractC1330vaMultiply4 = zIsOne2 ? abstractC1330va2 : abstractC1330va2.multiply(abstractC1330va3);
            AbstractC1330va abstractC1330vaSquare3 = zIsOne2 ? abstractC1330va3 : abstractC1330va3.square();
            AbstractC1330va a = curve.getA();
            AbstractC1330va abstractC1330vaMultiply5 = zIsOne2 ? a : a.multiply(abstractC1330vaSquare3);
            AbstractC1330va abstractC1330vaM23a4 = AbstractC0003a2.m23a4(abstractC1330va2, abstractC1330vaMultiply4, abstractC1330vaMultiply5);
            if (abstractC1330vaM23a4.isZero()) {
                return new a3(curve, abstractC1330vaM23a4, curve.getB().sqrt());
            }
            AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaM23a4.square();
            AbstractC1330va abstractC1330vaMultiply6 = zIsOne2 ? abstractC1330vaM23a4 : abstractC1330vaM23a4.multiply(abstractC1330vaSquare3);
            AbstractC1330va b = curve.getB();
            if (b.bitLength() < (curve.getFieldSize() >> 1)) {
                AbstractC1330va abstractC1330vaSquare5 = abstractC1330va2.add(abstractC1330vaMultiply).square();
                abstractC1330vaAdd = abstractC1330vaSquare5.add(abstractC1330vaM23a4).add(abstractC1330vaSquare3).multiply(abstractC1330vaSquare5).add(b.isOne() ? abstractC1330vaMultiply5.add(abstractC1330vaSquare3).square() : abstractC1330vaMultiply5.squarePlusProduct(b, abstractC1330vaSquare3.square())).add(abstractC1330vaSquare4);
                if (!a.isZero()) {
                    if (!a.isOne()) {
                        abstractC1330vaAdd = abstractC1330vaAdd.add(a.addOne().multiply(abstractC1330vaMultiply6));
                    }
                }
                return new a3(curve, abstractC1330vaSquare4, abstractC1330vaAdd, new AbstractC1330va[]{abstractC1330vaMultiply6});
            }
            if (!zIsOne2) {
                abstractC1330vaMultiply = abstractC1330vaMultiply.multiply(abstractC1330va3);
            }
            abstractC1330vaAdd = abstractC1330vaMultiply.squarePlusProduct(abstractC1330vaM23a4, abstractC1330vaMultiply4).add(abstractC1330vaSquare4);
            abstractC1330vaAdd = abstractC1330vaAdd.add(abstractC1330vaMultiply6);
            return new a3(curve, abstractC1330vaSquare4, abstractC1330vaAdd, new AbstractC1330va[]{abstractC1330vaMultiply6});
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
            if (curve.getCoordinateSystem() != 6) {
                return twice().add(abstractC1341vl);
            }
            AbstractC1330va abstractC1330va2 = abstractC1341vl.f60653x;
            AbstractC1330va abstractC1330va3 = abstractC1341vl.f60655zs[0];
            if (abstractC1330va2.isZero() || !abstractC1330va3.isOne()) {
                return twice().add(abstractC1341vl);
            }
            AbstractC1330va abstractC1330va4 = this.f60654y;
            AbstractC1330va abstractC1330va5 = this.f60655zs[0];
            AbstractC1330va abstractC1330va6 = abstractC1341vl.f60654y;
            AbstractC1330va abstractC1330vaSquare = abstractC1330va.square();
            AbstractC1330va abstractC1330vaSquare2 = abstractC1330va4.square();
            AbstractC1330va abstractC1330vaSquare3 = abstractC1330va5.square();
            AbstractC1330va abstractC1330vaAdd = curve.getA().multiply(abstractC1330vaSquare3).add(abstractC1330vaSquare2).add(abstractC1330va4.multiply(abstractC1330va5));
            AbstractC1330va abstractC1330vaAddOne = abstractC1330va6.addOne();
            AbstractC1330va abstractC1330vaMultiplyPlusProduct = curve.getA().add(abstractC1330vaAddOne).multiply(abstractC1330vaSquare3).add(abstractC1330vaSquare2).multiplyPlusProduct(abstractC1330vaAdd, abstractC1330vaSquare, abstractC1330vaSquare3);
            AbstractC1330va abstractC1330vaMultiply = abstractC1330va2.multiply(abstractC1330vaSquare3);
            AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaMultiply.add(abstractC1330vaAdd).square();
            if (abstractC1330vaSquare4.isZero()) {
                return abstractC1330vaMultiplyPlusProduct.isZero() ? abstractC1341vl.twice() : curve.getInfinity();
            }
            if (abstractC1330vaMultiplyPlusProduct.isZero()) {
                return new a3(curve, abstractC1330vaMultiplyPlusProduct, curve.getB().sqrt());
            }
            AbstractC1330va abstractC1330vaMultiply2 = abstractC1330vaMultiplyPlusProduct.square().multiply(abstractC1330vaMultiply);
            AbstractC1330va abstractC1330vaMultiply3 = abstractC1330vaMultiplyPlusProduct.multiply(abstractC1330vaSquare4).multiply(abstractC1330vaSquare3);
            return new a3(curve, abstractC1330vaMultiply2, abstractC1330vaMultiplyPlusProduct.add(abstractC1330vaSquare4).square().multiplyPlusProduct(abstractC1330vaAdd, abstractC1330vaAddOne, abstractC1330vaMultiply3), new AbstractC1330va[]{abstractC1330vaMultiply3});
        }

        public a3(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }
    }

    /* renamed from: vl$a4 */
    public static class a4 extends a2 {
        public a4(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2);
        }

        /* JADX WARN: Removed duplicated region for block: B:61:0x0131  */
        /* JADX WARN: Removed duplicated region for block: B:62:0x013d  */
        @Override // p000.AbstractC1341vl
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public AbstractC1341vl add(AbstractC1341vl abstractC1341vl) {
            char c;
            int i;
            AbstractC1330va abstractC1330vaMultiplyMinusProduct;
            AbstractC1330va abstractC1330vaMultiply;
            AbstractC1330va abstractC1330vaSubtract;
            AbstractC1330va abstractC1330va;
            AbstractC1330va[] abstractC1330vaArr;
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
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va abstractC1330vaMultiply2 = this.f60653x;
            AbstractC1330va abstractC1330vaMultiply3 = this.f60654y;
            AbstractC1330va abstractC1330vaMultiply4 = abstractC1341vl.f60653x;
            AbstractC1330va abstractC1330vaMultiply5 = abstractC1341vl.f60654y;
            if (coordinateSystem == 0) {
                AbstractC1330va abstractC1330vaSubtract2 = abstractC1330vaMultiply4.subtract(abstractC1330vaMultiply2);
                AbstractC1330va abstractC1330vaSubtract3 = abstractC1330vaMultiply5.subtract(abstractC1330vaMultiply3);
                if (abstractC1330vaSubtract2.isZero()) {
                    return abstractC1330vaSubtract3.isZero() ? twice() : curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaDivide = abstractC1330vaSubtract3.divide(abstractC1330vaSubtract2);
                AbstractC1330va abstractC1330vaSubtract4 = abstractC1330vaDivide.square().subtract(abstractC1330vaMultiply2).subtract(abstractC1330vaMultiply4);
                return new a4(curve, abstractC1330vaSubtract4, abstractC1330vaDivide.multiply(abstractC1330vaMultiply2.subtract(abstractC1330vaSubtract4)).subtract(abstractC1330vaMultiply3));
            }
            if (coordinateSystem == 1) {
                AbstractC1330va abstractC1330vaMultiply6 = this.f60655zs[0];
                AbstractC1330va abstractC1330va2 = abstractC1341vl.f60655zs[0];
                boolean zIsOne = abstractC1330vaMultiply6.isOne();
                boolean zIsOne2 = abstractC1330va2.isOne();
                if (!zIsOne) {
                    abstractC1330vaMultiply5 = abstractC1330vaMultiply5.multiply(abstractC1330vaMultiply6);
                }
                if (!zIsOne2) {
                    abstractC1330vaMultiply3 = abstractC1330vaMultiply3.multiply(abstractC1330va2);
                }
                AbstractC1330va abstractC1330vaSubtract5 = abstractC1330vaMultiply5.subtract(abstractC1330vaMultiply3);
                if (!zIsOne) {
                    abstractC1330vaMultiply4 = abstractC1330vaMultiply4.multiply(abstractC1330vaMultiply6);
                }
                if (!zIsOne2) {
                    abstractC1330vaMultiply2 = abstractC1330vaMultiply2.multiply(abstractC1330va2);
                }
                AbstractC1330va abstractC1330vaSubtract6 = abstractC1330vaMultiply4.subtract(abstractC1330vaMultiply2);
                if (abstractC1330vaSubtract6.isZero()) {
                    return abstractC1330vaSubtract5.isZero() ? twice() : curve.getInfinity();
                }
                if (zIsOne) {
                    abstractC1330vaMultiply6 = abstractC1330va2;
                } else if (!zIsOne2) {
                    abstractC1330vaMultiply6 = abstractC1330vaMultiply6.multiply(abstractC1330va2);
                }
                AbstractC1330va abstractC1330vaSquare = abstractC1330vaSubtract6.square();
                AbstractC1330va abstractC1330vaMultiply7 = abstractC1330vaSquare.multiply(abstractC1330vaSubtract6);
                AbstractC1330va abstractC1330vaMultiply8 = abstractC1330vaSquare.multiply(abstractC1330vaMultiply2);
                AbstractC1330va abstractC1330vaSubtract7 = abstractC1330vaSubtract5.square().multiply(abstractC1330vaMultiply6).subtract(abstractC1330vaMultiply7).subtract(two(abstractC1330vaMultiply8));
                return new a4(curve, abstractC1330vaSubtract6.multiply(abstractC1330vaSubtract7), abstractC1330vaMultiply8.subtract(abstractC1330vaSubtract7).multiplyMinusProduct(abstractC1330vaSubtract5, abstractC1330vaMultiply3, abstractC1330vaMultiply7), new AbstractC1330va[]{abstractC1330vaMultiply7.multiply(abstractC1330vaMultiply6)});
            }
            if (coordinateSystem != 2 && coordinateSystem != 4) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            AbstractC1330va abstractC1330va3 = this.f60655zs[0];
            AbstractC1330va abstractC1330va4 = abstractC1341vl.f60655zs[0];
            boolean zIsOne3 = abstractC1330va3.isOne();
            if (zIsOne3 || !abstractC1330va3.equals(abstractC1330va4)) {
                if (!zIsOne3) {
                    AbstractC1330va abstractC1330vaSquare2 = abstractC1330va3.square();
                    abstractC1330vaMultiply4 = abstractC1330vaSquare2.multiply(abstractC1330vaMultiply4);
                    abstractC1330vaMultiply5 = abstractC1330vaSquare2.multiply(abstractC1330va3).multiply(abstractC1330vaMultiply5);
                }
                boolean zIsOne4 = abstractC1330va4.isOne();
                c = 0;
                if (!zIsOne4) {
                    AbstractC1330va abstractC1330vaSquare3 = abstractC1330va4.square();
                    abstractC1330vaMultiply2 = abstractC1330vaSquare3.multiply(abstractC1330vaMultiply2);
                    abstractC1330vaMultiply3 = abstractC1330vaSquare3.multiply(abstractC1330va4).multiply(abstractC1330vaMultiply3);
                }
                AbstractC1330va abstractC1330vaSubtract8 = abstractC1330vaMultiply2.subtract(abstractC1330vaMultiply4);
                AbstractC1330va abstractC1330vaSubtract9 = abstractC1330vaMultiply3.subtract(abstractC1330vaMultiply5);
                if (abstractC1330vaSubtract8.isZero()) {
                    return abstractC1330vaSubtract9.isZero() ? twice() : curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaSquare4 = abstractC1330vaSubtract8.square();
                AbstractC1330va abstractC1330vaMultiply9 = abstractC1330vaSquare4.multiply(abstractC1330vaSubtract8);
                AbstractC1330va abstractC1330vaMultiply10 = abstractC1330vaSquare4.multiply(abstractC1330vaMultiply2);
                i = 1;
                AbstractC1330va abstractC1330vaSubtract10 = abstractC1330vaSubtract9.square().add(abstractC1330vaMultiply9).subtract(two(abstractC1330vaMultiply10));
                abstractC1330vaMultiplyMinusProduct = abstractC1330vaMultiply10.subtract(abstractC1330vaSubtract10).multiplyMinusProduct(abstractC1330vaSubtract9, abstractC1330vaMultiply9, abstractC1330vaMultiply3);
                AbstractC1330va abstractC1330vaMultiply11 = !zIsOne3 ? abstractC1330vaSubtract8.multiply(abstractC1330va3) : abstractC1330vaSubtract8;
                abstractC1330vaMultiply = !zIsOne4 ? abstractC1330vaMultiply11.multiply(abstractC1330va4) : abstractC1330vaMultiply11;
                if (abstractC1330vaMultiply == abstractC1330vaSubtract8) {
                    abstractC1330vaSubtract = abstractC1330vaSubtract10;
                    abstractC1330va = abstractC1330vaSquare4;
                    if (coordinateSystem != 4) {
                        AbstractC1330va abstractC1330vaCalculateJacobianModifiedW = calculateJacobianModifiedW(abstractC1330vaMultiply, abstractC1330va);
                        abstractC1330vaArr = new AbstractC1330va[2];
                        abstractC1330vaArr[c] = abstractC1330vaMultiply;
                        abstractC1330vaArr[i] = abstractC1330vaCalculateJacobianModifiedW;
                    } else {
                        abstractC1330vaArr = new AbstractC1330va[i];
                        abstractC1330vaArr[c] = abstractC1330vaMultiply;
                    }
                    return new a4(curve, abstractC1330vaSubtract, abstractC1330vaMultiplyMinusProduct, abstractC1330vaArr);
                }
                abstractC1330vaSubtract = abstractC1330vaSubtract10;
            } else {
                AbstractC1330va abstractC1330vaSubtract11 = abstractC1330vaMultiply2.subtract(abstractC1330vaMultiply4);
                AbstractC1330va abstractC1330vaSubtract12 = abstractC1330vaMultiply3.subtract(abstractC1330vaMultiply5);
                if (abstractC1330vaSubtract11.isZero()) {
                    return abstractC1330vaSubtract12.isZero() ? twice() : curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaSquare5 = abstractC1330vaSubtract11.square();
                AbstractC1330va abstractC1330vaMultiply12 = abstractC1330vaMultiply2.multiply(abstractC1330vaSquare5);
                AbstractC1330va abstractC1330vaMultiply13 = abstractC1330vaMultiply4.multiply(abstractC1330vaSquare5);
                AbstractC1330va abstractC1330vaMultiply14 = abstractC1330vaMultiply12.subtract(abstractC1330vaMultiply13).multiply(abstractC1330vaMultiply3);
                abstractC1330vaSubtract = abstractC1330vaSubtract12.square().subtract(abstractC1330vaMultiply12).subtract(abstractC1330vaMultiply13);
                abstractC1330vaMultiplyMinusProduct = abstractC1330vaMultiply12.subtract(abstractC1330vaSubtract).multiply(abstractC1330vaSubtract12).subtract(abstractC1330vaMultiply14);
                abstractC1330vaMultiply = abstractC1330vaSubtract11.multiply(abstractC1330va3);
                i = 1;
                c = 0;
            }
            abstractC1330va = null;
            if (coordinateSystem != 4) {
            }
            return new a4(curve, abstractC1330vaSubtract, abstractC1330vaMultiplyMinusProduct, abstractC1330vaArr);
        }

        public AbstractC1330va calculateJacobianModifiedW(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
            AbstractC1330va a = getCurve().getA();
            if (a.isZero() || abstractC1330va.isOne()) {
                return a;
            }
            if (abstractC1330va2 == null) {
                abstractC1330va2 = abstractC1330va.square();
            }
            AbstractC1330va abstractC1330vaSquare = abstractC1330va2.square();
            AbstractC1330va abstractC1330vaNegate = a.negate();
            return abstractC1330vaNegate.bitLength() < a.bitLength() ? abstractC1330vaSquare.multiply(abstractC1330vaNegate).negate() : abstractC1330vaSquare.multiply(a);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl detach() {
            return new a4(null, getAffineXCoord(), getAffineYCoord());
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

        public AbstractC1330va getJacobianModifiedW() {
            AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
            AbstractC1330va abstractC1330va = abstractC1330vaArr[1];
            if (abstractC1330va != null) {
                return abstractC1330va;
            }
            AbstractC1330va abstractC1330vaCalculateJacobianModifiedW = calculateJacobianModifiedW(abstractC1330vaArr[0], null);
            abstractC1330vaArr[1] = abstractC1330vaCalculateJacobianModifiedW;
            return abstractC1330vaCalculateJacobianModifiedW;
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1330va getZCoord(int i) {
            return (i == 1 && 4 == getCurveCoordinateSystem()) ? getJacobianModifiedW() : super.getZCoord(i);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl negate() {
            if (isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            return curve.getCoordinateSystem() != 0 ? new a4(curve, this.f60653x, this.f60654y.negate(), this.f60655zs) : new a4(curve, this.f60653x, this.f60654y.negate());
        }

        public AbstractC1330va three(AbstractC1330va abstractC1330va) {
            return two(abstractC1330va).add(abstractC1330va);
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl threeTimes() {
            if (!isInfinity()) {
                AbstractC1330va abstractC1330va = this.f60654y;
                if (!abstractC1330va.isZero()) {
                    AbstractC1316ux curve = getCurve();
                    int coordinateSystem = curve.getCoordinateSystem();
                    if (coordinateSystem != 0) {
                        return coordinateSystem != 4 ? twice().add(this) : twiceJacobianModified(false).add(this);
                    }
                    AbstractC1330va abstractC1330va2 = this.f60653x;
                    AbstractC1330va abstractC1330vaTwo = two(abstractC1330va);
                    AbstractC1330va abstractC1330vaSquare = abstractC1330vaTwo.square();
                    AbstractC1330va abstractC1330vaAdd = three(abstractC1330va2.square()).add(getCurve().getA());
                    AbstractC1330va abstractC1330vaSubtract = three(abstractC1330va2).multiply(abstractC1330vaSquare).subtract(abstractC1330vaAdd.square());
                    if (abstractC1330vaSubtract.isZero()) {
                        return getCurve().getInfinity();
                    }
                    AbstractC1330va abstractC1330vaInvert = abstractC1330vaSubtract.multiply(abstractC1330vaTwo).invert();
                    AbstractC1330va abstractC1330vaMultiply = abstractC1330vaSubtract.multiply(abstractC1330vaInvert).multiply(abstractC1330vaAdd);
                    AbstractC1330va abstractC1330vaSubtract2 = abstractC1330vaSquare.square().multiply(abstractC1330vaInvert).subtract(abstractC1330vaMultiply);
                    AbstractC1330va abstractC1330vaAdd2 = abstractC1330vaSubtract2.subtract(abstractC1330vaMultiply).multiply(abstractC1330vaMultiply.add(abstractC1330vaSubtract2)).add(abstractC1330va2);
                    return new a4(curve, abstractC1330vaAdd2, abstractC1330va2.subtract(abstractC1330vaAdd2).multiply(abstractC1330vaSubtract2).subtract(abstractC1330va));
                }
            }
            return this;
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl timesPow2(int i) {
            AbstractC1330va abstractC1330vaSquare;
            if (i < 0) {
                throw new IllegalArgumentException("'e' cannot be negative");
            }
            if (i == 0 || isInfinity()) {
                return this;
            }
            if (i == 1) {
                return twice();
            }
            AbstractC1316ux curve = getCurve();
            AbstractC1330va abstractC1330vaSubtract = this.f60654y;
            if (abstractC1330vaSubtract.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va a = curve.getA();
            AbstractC1330va abstractC1330vaMultiply = this.f60653x;
            AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
            int i2 = 0;
            AbstractC1330va abstractC1330vaFromBigInteger = abstractC1330vaArr.length < 1 ? curve.fromBigInteger(InterfaceC1315uw.ONE) : abstractC1330vaArr[0];
            if (!abstractC1330vaFromBigInteger.isOne() && coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    abstractC1330vaSquare = abstractC1330vaFromBigInteger.square();
                    abstractC1330vaMultiply = abstractC1330vaMultiply.multiply(abstractC1330vaFromBigInteger);
                    abstractC1330vaSubtract = abstractC1330vaSubtract.multiply(abstractC1330vaSquare);
                } else if (coordinateSystem == 2) {
                    abstractC1330vaSquare = null;
                } else {
                    if (coordinateSystem != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    a = getJacobianModifiedW();
                }
                a = calculateJacobianModifiedW(abstractC1330vaFromBigInteger, abstractC1330vaSquare);
            }
            while (i2 < i) {
                if (abstractC1330vaSubtract.isZero()) {
                    return curve.getInfinity();
                }
                AbstractC1330va abstractC1330vaThree = three(abstractC1330vaMultiply.square());
                AbstractC1330va abstractC1330vaTwo = two(abstractC1330vaSubtract);
                AbstractC1330va abstractC1330vaMultiply2 = abstractC1330vaTwo.multiply(abstractC1330vaSubtract);
                AbstractC1330va abstractC1330vaTwo2 = two(abstractC1330vaMultiply.multiply(abstractC1330vaMultiply2));
                AbstractC1330va abstractC1330vaTwo3 = two(abstractC1330vaMultiply2.square());
                if (!a.isZero()) {
                    abstractC1330vaThree = abstractC1330vaThree.add(a);
                    a = two(abstractC1330vaTwo3.multiply(a));
                }
                AbstractC1330va abstractC1330vaSubtract2 = abstractC1330vaThree.square().subtract(two(abstractC1330vaTwo2));
                abstractC1330vaSubtract = abstractC1330vaThree.multiply(abstractC1330vaTwo2.subtract(abstractC1330vaSubtract2)).subtract(abstractC1330vaTwo3);
                abstractC1330vaFromBigInteger = abstractC1330vaFromBigInteger.isOne() ? abstractC1330vaTwo : abstractC1330vaTwo.multiply(abstractC1330vaFromBigInteger);
                i2++;
                abstractC1330vaMultiply = abstractC1330vaSubtract2;
            }
            if (coordinateSystem == 0) {
                AbstractC1330va abstractC1330vaInvert = abstractC1330vaFromBigInteger.invert();
                AbstractC1330va abstractC1330vaSquare2 = abstractC1330vaInvert.square();
                return new a4(curve, abstractC1330vaMultiply.multiply(abstractC1330vaSquare2), abstractC1330vaSubtract.multiply(abstractC1330vaSquare2.multiply(abstractC1330vaInvert)));
            }
            if (coordinateSystem == 1) {
                return new a4(curve, abstractC1330vaMultiply.multiply(abstractC1330vaFromBigInteger), abstractC1330vaSubtract, new AbstractC1330va[]{abstractC1330vaFromBigInteger.multiply(abstractC1330vaFromBigInteger.square())});
            }
            if (coordinateSystem == 2) {
                return new a4(curve, abstractC1330vaMultiply, abstractC1330vaSubtract, new AbstractC1330va[]{abstractC1330vaFromBigInteger});
            }
            if (coordinateSystem == 4) {
                return new a4(curve, abstractC1330vaMultiply, abstractC1330vaSubtract, new AbstractC1330va[]{abstractC1330vaFromBigInteger, a});
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl twice() {
            AbstractC1330va abstractC1330vaSubtract;
            AbstractC1330va abstractC1330vaMultiply;
            if (isInfinity()) {
                return this;
            }
            AbstractC1316ux curve = getCurve();
            AbstractC1330va abstractC1330va = this.f60654y;
            if (abstractC1330va.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            AbstractC1330va abstractC1330va2 = this.f60653x;
            if (coordinateSystem == 0) {
                AbstractC1330va abstractC1330vaDivide = three(abstractC1330va2.square()).add(getCurve().getA()).divide(two(abstractC1330va));
                AbstractC1330va abstractC1330vaSubtract2 = abstractC1330vaDivide.square().subtract(two(abstractC1330va2));
                return new a4(curve, abstractC1330vaSubtract2, abstractC1330vaDivide.multiply(abstractC1330va2.subtract(abstractC1330vaSubtract2)).subtract(abstractC1330va));
            }
            if (coordinateSystem == 1) {
                AbstractC1330va abstractC1330va3 = this.f60655zs[0];
                boolean zIsOne = abstractC1330va3.isOne();
                AbstractC1330va a = curve.getA();
                if (!a.isZero() && !zIsOne) {
                    a = a.multiply(abstractC1330va3.square());
                }
                AbstractC1330va abstractC1330vaAdd = a.add(three(abstractC1330va2.square()));
                AbstractC1330va abstractC1330vaMultiply2 = zIsOne ? abstractC1330va : abstractC1330va.multiply(abstractC1330va3);
                AbstractC1330va abstractC1330vaSquare = zIsOne ? abstractC1330va.square() : abstractC1330vaMultiply2.multiply(abstractC1330va);
                AbstractC1330va abstractC1330vaFour = four(abstractC1330va2.multiply(abstractC1330vaSquare));
                AbstractC1330va abstractC1330vaSubtract3 = abstractC1330vaAdd.square().subtract(two(abstractC1330vaFour));
                AbstractC1330va abstractC1330vaTwo = two(abstractC1330vaMultiply2);
                AbstractC1330va abstractC1330vaMultiply3 = abstractC1330vaSubtract3.multiply(abstractC1330vaTwo);
                AbstractC1330va abstractC1330vaTwo2 = two(abstractC1330vaSquare);
                return new a4(curve, abstractC1330vaMultiply3, abstractC1330vaFour.subtract(abstractC1330vaSubtract3).multiply(abstractC1330vaAdd).subtract(two(abstractC1330vaTwo2.square())), new AbstractC1330va[]{two(zIsOne ? two(abstractC1330vaTwo2) : abstractC1330vaTwo.square()).multiply(abstractC1330vaMultiply2)});
            }
            if (coordinateSystem != 2) {
                if (coordinateSystem == 4) {
                    return twiceJacobianModified(true);
                }
                throw new IllegalStateException("unsupported coordinate system");
            }
            AbstractC1330va abstractC1330va4 = this.f60655zs[0];
            boolean zIsOne2 = abstractC1330va4.isOne();
            AbstractC1330va abstractC1330vaSquare2 = abstractC1330va.square();
            AbstractC1330va abstractC1330vaSquare3 = abstractC1330vaSquare2.square();
            AbstractC1330va a2 = curve.getA();
            AbstractC1330va abstractC1330vaNegate = a2.negate();
            if (abstractC1330vaNegate.toBigInteger().equals(BigInteger.valueOf(3L))) {
                AbstractC1330va abstractC1330vaSquare4 = zIsOne2 ? abstractC1330va4 : abstractC1330va4.square();
                abstractC1330vaSubtract = three(abstractC1330va2.add(abstractC1330vaSquare4).multiply(abstractC1330va2.subtract(abstractC1330vaSquare4)));
                abstractC1330vaMultiply = abstractC1330vaSquare2.multiply(abstractC1330va2);
            } else {
                AbstractC1330va abstractC1330vaThree = three(abstractC1330va2.square());
                if (zIsOne2) {
                    abstractC1330vaSubtract = abstractC1330vaThree.add(a2);
                    abstractC1330vaMultiply = abstractC1330va2.multiply(abstractC1330vaSquare2);
                } else {
                    if (a2.isZero()) {
                        abstractC1330vaSubtract = abstractC1330vaThree;
                    } else {
                        AbstractC1330va abstractC1330vaSquare5 = abstractC1330va4.square().square();
                        if (abstractC1330vaNegate.bitLength() < a2.bitLength()) {
                            abstractC1330vaSubtract = abstractC1330vaThree.subtract(abstractC1330vaSquare5.multiply(abstractC1330vaNegate));
                        } else {
                            a2 = abstractC1330vaSquare5.multiply(a2);
                            abstractC1330vaSubtract = abstractC1330vaThree.add(a2);
                        }
                    }
                    abstractC1330vaMultiply = abstractC1330va2.multiply(abstractC1330vaSquare2);
                }
            }
            AbstractC1330va abstractC1330vaFour2 = four(abstractC1330vaMultiply);
            AbstractC1330va abstractC1330vaSubtract4 = abstractC1330vaSubtract.square().subtract(two(abstractC1330vaFour2));
            AbstractC1330va abstractC1330vaSubtract5 = abstractC1330vaFour2.subtract(abstractC1330vaSubtract4).multiply(abstractC1330vaSubtract).subtract(eight(abstractC1330vaSquare3));
            AbstractC1330va abstractC1330vaTwo3 = two(abstractC1330va);
            if (!zIsOne2) {
                abstractC1330vaTwo3 = abstractC1330vaTwo3.multiply(abstractC1330va4);
            }
            return new a4(curve, abstractC1330vaSubtract4, abstractC1330vaSubtract5, new AbstractC1330va[]{abstractC1330vaTwo3});
        }

        public a4 twiceJacobianModified(boolean z) {
            AbstractC1330va abstractC1330va = this.f60653x;
            AbstractC1330va abstractC1330va2 = this.f60654y;
            AbstractC1330va abstractC1330va3 = this.f60655zs[0];
            AbstractC1330va jacobianModifiedW = getJacobianModifiedW();
            AbstractC1330va abstractC1330vaAdd = three(abstractC1330va.square()).add(jacobianModifiedW);
            AbstractC1330va abstractC1330vaTwo = two(abstractC1330va2);
            AbstractC1330va abstractC1330vaMultiply = abstractC1330vaTwo.multiply(abstractC1330va2);
            AbstractC1330va abstractC1330vaTwo2 = two(abstractC1330va.multiply(abstractC1330vaMultiply));
            AbstractC1330va abstractC1330vaSubtract = abstractC1330vaAdd.square().subtract(two(abstractC1330vaTwo2));
            AbstractC1330va abstractC1330vaTwo3 = two(abstractC1330vaMultiply.square());
            AbstractC1330va abstractC1330vaSubtract2 = abstractC1330vaAdd.multiply(abstractC1330vaTwo2.subtract(abstractC1330vaSubtract)).subtract(abstractC1330vaTwo3);
            AbstractC1330va abstractC1330vaTwo4 = z ? two(abstractC1330vaTwo3.multiply(jacobianModifiedW)) : null;
            if (!abstractC1330va3.isOne()) {
                abstractC1330vaTwo = abstractC1330vaTwo.multiply(abstractC1330va3);
            }
            return new a4(getCurve(), abstractC1330vaSubtract, abstractC1330vaSubtract2, new AbstractC1330va[]{abstractC1330vaTwo, abstractC1330vaTwo4});
        }

        @Override // p000.AbstractC1341vl
        public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
            if (this == abstractC1341vl) {
                return threeTimes();
            }
            if (isInfinity()) {
                return abstractC1341vl;
            }
            if (abstractC1341vl.isInfinity()) {
                return twice();
            }
            AbstractC1330va abstractC1330va = this.f60654y;
            if (abstractC1330va.isZero()) {
                return abstractC1341vl;
            }
            AbstractC1316ux curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem != 0) {
                return coordinateSystem != 4 ? twice().add(abstractC1341vl) : twiceJacobianModified(false).add(abstractC1341vl);
            }
            AbstractC1330va abstractC1330va2 = this.f60653x;
            AbstractC1330va abstractC1330va3 = abstractC1341vl.f60653x;
            AbstractC1330va abstractC1330va4 = abstractC1341vl.f60654y;
            AbstractC1330va abstractC1330vaSubtract = abstractC1330va3.subtract(abstractC1330va2);
            AbstractC1330va abstractC1330vaSubtract2 = abstractC1330va4.subtract(abstractC1330va);
            if (abstractC1330vaSubtract.isZero()) {
                return abstractC1330vaSubtract2.isZero() ? threeTimes() : this;
            }
            AbstractC1330va abstractC1330vaSquare = abstractC1330vaSubtract.square();
            AbstractC1330va abstractC1330vaSubtract3 = abstractC1330vaSquare.multiply(two(abstractC1330va2).add(abstractC1330va3)).subtract(abstractC1330vaSubtract2.square());
            if (abstractC1330vaSubtract3.isZero()) {
                return curve.getInfinity();
            }
            AbstractC1330va abstractC1330vaInvert = abstractC1330vaSubtract3.multiply(abstractC1330vaSubtract).invert();
            AbstractC1330va abstractC1330vaMultiply = abstractC1330vaSubtract3.multiply(abstractC1330vaInvert).multiply(abstractC1330vaSubtract2);
            AbstractC1330va abstractC1330vaSubtract4 = two(abstractC1330va).multiply(abstractC1330vaSquare).multiply(abstractC1330vaSubtract).multiply(abstractC1330vaInvert).subtract(abstractC1330vaMultiply);
            AbstractC1330va abstractC1330vaAdd = abstractC1330vaSubtract4.subtract(abstractC1330vaMultiply).multiply(abstractC1330vaMultiply.add(abstractC1330vaSubtract4)).add(abstractC1330va3);
            return new a4(curve, abstractC1330vaAdd, abstractC1330va2.subtract(abstractC1330vaAdd).multiply(abstractC1330vaSubtract4).subtract(abstractC1330va));
        }

        public AbstractC1330va two(AbstractC1330va abstractC1330va) {
            return abstractC1330va.add(abstractC1330va);
        }

        public a4(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
            super(abstractC1316ux, abstractC1330va, abstractC1330va2, abstractC1330vaArr);
        }
    }

    public AbstractC1341vl(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        this(abstractC1316ux, abstractC1330va, abstractC1330va2, getInitialZCoords(abstractC1316ux));
    }

    public static AbstractC1330va[] getInitialZCoords(AbstractC1316ux abstractC1316ux) {
        int coordinateSystem = abstractC1316ux == null ? 0 : abstractC1316ux.getCoordinateSystem();
        if (coordinateSystem == 0 || coordinateSystem == 5) {
            return EMPTY_ZS;
        }
        AbstractC1330va abstractC1330vaFromBigInteger = abstractC1316ux.fromBigInteger(InterfaceC1315uw.ONE);
        if (coordinateSystem != 1 && coordinateSystem != 2) {
            if (coordinateSystem == 3) {
                return new AbstractC1330va[]{abstractC1330vaFromBigInteger, abstractC1330vaFromBigInteger, abstractC1330vaFromBigInteger};
            }
            if (coordinateSystem == 4) {
                return new AbstractC1330va[]{abstractC1330vaFromBigInteger, abstractC1316ux.getA()};
            }
            if (coordinateSystem != 6) {
                throw new IllegalArgumentException("unknown coordinate system");
            }
        }
        return new AbstractC1330va[]{abstractC1330vaFromBigInteger};
    }

    public abstract AbstractC1341vl add(AbstractC1341vl abstractC1341vl);

    public void checkNormalized() {
        if (!isNormalized()) {
            throw new IllegalStateException("point not in normal form");
        }
    }

    public AbstractC1341vl createScaledPoint(AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2) {
        return getCurve().createRawPoint(getRawXCoord().multiply(abstractC1330va), getRawYCoord().multiply(abstractC1330va2));
    }

    public abstract AbstractC1341vl detach();

    public boolean equals(AbstractC1341vl abstractC1341vl) {
        AbstractC1341vl abstractC1341vlNormalize;
        if (abstractC1341vl == null) {
            return false;
        }
        AbstractC1316ux curve = getCurve();
        AbstractC1316ux curve2 = abstractC1341vl.getCurve();
        boolean z = curve == null;
        boolean z2 = curve2 == null;
        boolean zIsInfinity = isInfinity();
        boolean zIsInfinity2 = abstractC1341vl.isInfinity();
        if (zIsInfinity || zIsInfinity2) {
            return zIsInfinity && zIsInfinity2 && (z || z2 || curve.equals(curve2));
        }
        if (z && z2) {
            abstractC1341vlNormalize = this;
        } else if (z) {
            abstractC1341vl = abstractC1341vl.normalize();
            abstractC1341vlNormalize = this;
        } else if (z2) {
            abstractC1341vlNormalize = normalize();
        } else {
            if (!curve.equals(curve2)) {
                return false;
            }
            AbstractC1341vl[] abstractC1341vlArr = {this, curve.importPoint(abstractC1341vl)};
            curve.normalizeAll(abstractC1341vlArr);
            abstractC1341vlNormalize = abstractC1341vlArr[0];
            abstractC1341vl = abstractC1341vlArr[1];
        }
        return abstractC1341vlNormalize.getXCoord().equals(abstractC1341vl.getXCoord()) && abstractC1341vlNormalize.getYCoord().equals(abstractC1341vl.getYCoord());
    }

    public AbstractC1330va getAffineXCoord() {
        checkNormalized();
        return getXCoord();
    }

    public AbstractC1330va getAffineYCoord() {
        checkNormalized();
        return getYCoord();
    }

    public abstract boolean getCompressionYTilde();

    public AbstractC1316ux getCurve() {
        return this.curve;
    }

    public int getCurveCoordinateSystem() {
        AbstractC1316ux abstractC1316ux = this.curve;
        if (abstractC1316ux == null) {
            return 0;
        }
        return abstractC1316ux.getCoordinateSystem();
    }

    public final AbstractC1341vl getDetachedPoint() {
        return normalize().detach();
    }

    public byte[] getEncoded(boolean z) {
        if (isInfinity()) {
            return new byte[1];
        }
        AbstractC1341vl abstractC1341vlNormalize = normalize();
        byte[] encoded = abstractC1341vlNormalize.getXCoord().getEncoded();
        if (z) {
            byte[] bArr = new byte[encoded.length + 1];
            bArr[0] = (byte) (abstractC1341vlNormalize.getCompressionYTilde() ? 3 : 2);
            System.arraycopy(encoded, 0, bArr, 1, encoded.length);
            return bArr;
        }
        byte[] encoded2 = abstractC1341vlNormalize.getYCoord().getEncoded();
        byte[] bArr2 = new byte[encoded.length + encoded2.length + 1];
        bArr2[0] = 4;
        System.arraycopy(encoded, 0, bArr2, 1, encoded.length);
        System.arraycopy(encoded2, 0, bArr2, encoded.length + 1, encoded2.length);
        return bArr2;
    }

    public final AbstractC1330va getRawXCoord() {
        return this.f60653x;
    }

    public final AbstractC1330va getRawYCoord() {
        return this.f60654y;
    }

    public final AbstractC1330va[] getRawZCoords() {
        return this.f60655zs;
    }

    public AbstractC1330va getXCoord() {
        return this.f60653x;
    }

    public AbstractC1330va getYCoord() {
        return this.f60654y;
    }

    public AbstractC1330va getZCoord(int i) {
        if (i < 0) {
            return null;
        }
        AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
        if (i >= abstractC1330vaArr.length) {
            return null;
        }
        return abstractC1330vaArr[i];
    }

    public AbstractC1330va[] getZCoords() {
        AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
        int length = abstractC1330vaArr.length;
        if (length == 0) {
            return EMPTY_ZS;
        }
        AbstractC1330va[] abstractC1330vaArr2 = new AbstractC1330va[length];
        System.arraycopy(abstractC1330vaArr, 0, abstractC1330vaArr2, 0, length);
        return abstractC1330vaArr2;
    }

    public int hashCode() {
        AbstractC1316ux curve = getCurve();
        int i = curve == null ? 0 : ~curve.hashCode();
        if (isInfinity()) {
            return i;
        }
        AbstractC1341vl abstractC1341vlNormalize = normalize();
        return (i ^ (abstractC1341vlNormalize.getXCoord().hashCode() * 17)) ^ (abstractC1341vlNormalize.getYCoord().hashCode() * 257);
    }

    public boolean implIsValid(boolean z, boolean z2) {
        if (isInfinity()) {
            return true;
        }
        return !((h91) getCurve().precompute(this, "bc_validity", new a0(z, z2))).hasFailed();
    }

    public boolean isInfinity() {
        if (this.f60653x == null || this.f60654y == null) {
            return true;
        }
        AbstractC1330va[] abstractC1330vaArr = this.f60655zs;
        return abstractC1330vaArr.length > 0 && abstractC1330vaArr[0].isZero();
    }

    public boolean isNormalized() {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        return curveCoordinateSystem == 0 || curveCoordinateSystem == 5 || isInfinity() || this.f60655zs[0].isOne();
    }

    public boolean isValid() {
        return implIsValid(false, true);
    }

    public boolean isValidPartial() {
        return implIsValid(false, false);
    }

    public AbstractC1341vl multiply(BigInteger bigInteger) {
        return getCurve().getMultiplier().multiply(this, bigInteger);
    }

    public abstract AbstractC1341vl negate();

    public AbstractC1341vl normalize() {
        int curveCoordinateSystem;
        if (!isInfinity() && (curveCoordinateSystem = getCurveCoordinateSystem()) != 0 && curveCoordinateSystem != 5) {
            AbstractC1330va zCoord = getZCoord(0);
            if (!zCoord.isOne()) {
                if (this.curve == null) {
                    throw new IllegalStateException("Detached points must be in affine coordinates");
                }
                AbstractC1330va abstractC1330vaRandomFieldElementMult = this.curve.randomFieldElementMult(C0929nx.getSecureRandom());
                return normalize(zCoord.multiply(abstractC1330vaRandomFieldElementMult).invert().multiply(abstractC1330vaRandomFieldElementMult));
            }
        }
        return this;
    }

    public abstract boolean satisfiesCurveEquation();

    public boolean satisfiesOrder() {
        BigInteger order;
        return InterfaceC1315uw.ONE.equals(this.curve.getCofactor()) || (order = this.curve.getOrder()) == null || C1314uv.referenceMultiply(this, order).isInfinity();
    }

    public AbstractC1341vl scaleX(AbstractC1330va abstractC1330va) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(abstractC1330va), getRawYCoord(), getRawZCoords());
    }

    public AbstractC1341vl scaleXNegateY(AbstractC1330va abstractC1330va) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(abstractC1330va), getRawYCoord().negate(), getRawZCoords());
    }

    public AbstractC1341vl scaleY(AbstractC1330va abstractC1330va) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord(), getRawYCoord().multiply(abstractC1330va), getRawZCoords());
    }

    public AbstractC1341vl scaleYNegateX(AbstractC1330va abstractC1330va) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().negate(), getRawYCoord().multiply(abstractC1330va), getRawZCoords());
    }

    public abstract AbstractC1341vl subtract(AbstractC1341vl abstractC1341vl);

    public AbstractC1341vl threeTimes() {
        return twicePlus(this);
    }

    public AbstractC1341vl timesPow2(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("'e' cannot be negative");
        }
        AbstractC1341vl abstractC1341vlTwice = this;
        while (true) {
            i--;
            if (i < 0) {
                return abstractC1341vlTwice;
            }
            abstractC1341vlTwice = abstractC1341vlTwice.twice();
        }
    }

    public String toString() {
        if (isInfinity()) {
            return "INF";
        }
        StringBuffer stringBuffer = new StringBuffer("(");
        stringBuffer.append(getRawXCoord());
        stringBuffer.append(',');
        stringBuffer.append(getRawYCoord());
        for (int i = 0; i < this.f60655zs.length; i++) {
            stringBuffer.append(',');
            stringBuffer.append(this.f60655zs[i]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public abstract AbstractC1341vl twice();

    public AbstractC1341vl twicePlus(AbstractC1341vl abstractC1341vl) {
        return twice().add(abstractC1341vl);
    }

    public AbstractC1341vl(AbstractC1316ux abstractC1316ux, AbstractC1330va abstractC1330va, AbstractC1330va abstractC1330va2, AbstractC1330va[] abstractC1330vaArr) {
        this.preCompTable = null;
        this.curve = abstractC1316ux;
        this.f60653x = abstractC1330va;
        this.f60654y = abstractC1330va2;
        this.f60655zs = abstractC1330vaArr;
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof AbstractC1341vl) {
            return equals((AbstractC1341vl) obj);
        }
        return false;
    }

    public AbstractC1341vl normalize(AbstractC1330va abstractC1330va) {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        if (curveCoordinateSystem != 1) {
            if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                AbstractC1330va abstractC1330vaSquare = abstractC1330va.square();
                return createScaledPoint(abstractC1330vaSquare, abstractC1330vaSquare.multiply(abstractC1330va));
            }
            if (curveCoordinateSystem != 6) {
                throw new IllegalStateException("not a projective coordinate system");
            }
        }
        return createScaledPoint(abstractC1330va, abstractC1330va);
    }
}
