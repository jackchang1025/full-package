package org.bouncycastle.math.ec;

import java.math.BigInteger;
import java.util.Hashtable;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public abstract class ECPoint {
    protected static final ECFieldElement[] EMPTY_ZS = new ECFieldElement[0];
    protected ECCurve curve;
    protected Hashtable preCompTable;

    /* renamed from: x */
    protected ECFieldElement f1419x;

    /* renamed from: y */
    protected ECFieldElement f1420y;
    protected ECFieldElement[] zs;

    public static abstract class AbstractF2m extends ECPoint {
        public AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public boolean satisfiesCurveEquation() {
            ECFieldElement multiplyPlusProduct;
            ECFieldElement squarePlusProduct;
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.f1419x;
            ECFieldElement a2 = curve.getA();
            ECFieldElement b = curve.getB();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem != 6) {
                ECFieldElement eCFieldElement2 = this.f1420y;
                ECFieldElement multiply = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement2);
                if (coordinateSystem != 0) {
                    if (coordinateSystem != 1) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        ECFieldElement multiply2 = eCFieldElement3.multiply(eCFieldElement3.square());
                        multiply = multiply.multiply(eCFieldElement3);
                        a2 = a2.multiply(eCFieldElement3);
                        b = b.multiply(multiply2);
                    }
                }
                return multiply.equals(eCFieldElement.add(a2).multiply(eCFieldElement.square()).add(b));
            }
            ECFieldElement eCFieldElement4 = this.zs[0];
            boolean isOne = eCFieldElement4.isOne();
            if (eCFieldElement.isZero()) {
                ECFieldElement square = this.f1420y.square();
                if (!isOne) {
                    b = b.multiply(eCFieldElement4.square());
                }
                return square.equals(b);
            }
            ECFieldElement eCFieldElement5 = this.f1420y;
            ECFieldElement square2 = eCFieldElement.square();
            if (isOne) {
                multiplyPlusProduct = AbstractC0413b.m1026t(eCFieldElement5, eCFieldElement5, a2);
                squarePlusProduct = square2.square().add(b);
            } else {
                ECFieldElement square3 = eCFieldElement4.square();
                ECFieldElement square4 = square3.square();
                multiplyPlusProduct = eCFieldElement5.add(eCFieldElement4).multiplyPlusProduct(eCFieldElement5, a2, square3);
                squarePlusProduct = square2.squarePlusProduct(b, square4);
            }
            return multiplyPlusProduct.multiply(square2).equals(squarePlusProduct);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public boolean satisfiesOrder() {
            BigInteger cofactor = this.curve.getCofactor();
            if (ECConstants.TWO.equals(cofactor)) {
                return ((ECFieldElement.AbstractF2m) normalize().getAffineXCoord()).trace() != 0;
            }
            if (!ECConstants.FOUR.equals(cofactor)) {
                return super.satisfiesOrder();
            }
            ECPoint normalize = normalize();
            ECFieldElement affineXCoord = normalize.getAffineXCoord();
            ECCurve eCCurve = this.curve;
            ECFieldElement solveQuadraticEquation = ((ECCurve.AbstractF2m) eCCurve).solveQuadraticEquation(affineXCoord.add(eCCurve.getA()));
            if (solveQuadraticEquation == null) {
                return false;
            }
            return ((ECFieldElement.AbstractF2m) affineXCoord.multiply(solveQuadraticEquation).add(normalize.getAffineYCoord())).trace() == 0;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleX(ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 5) {
                ECFieldElement rawXCoord = getRawXCoord();
                return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).divide(eCFieldElement).add(rawXCoord.multiply(eCFieldElement)), getRawZCoords());
            }
            if (curveCoordinateSystem != 6) {
                return super.scaleX(eCFieldElement);
            }
            ECFieldElement rawXCoord2 = getRawXCoord();
            ECFieldElement rawYCoord = getRawYCoord();
            ECFieldElement eCFieldElement2 = getRawZCoords()[0];
            ECFieldElement multiply = rawXCoord2.multiply(eCFieldElement.square());
            return getCurve().createRawPoint(multiply, rawYCoord.add(rawXCoord2).add(multiply), new ECFieldElement[]{eCFieldElement2.multiply(eCFieldElement)});
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleXNegateY(ECFieldElement eCFieldElement) {
            return scaleX(eCFieldElement);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleY(ECFieldElement eCFieldElement) {
            if (isInfinity()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 5 && curveCoordinateSystem != 6) {
                return super.scaleY(eCFieldElement);
            }
            ECFieldElement rawXCoord = getRawXCoord();
            return getCurve().createRawPoint(rawXCoord, getRawYCoord().add(rawXCoord).multiply(eCFieldElement).add(rawXCoord), getRawZCoords());
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint scaleYNegateX(ECFieldElement eCFieldElement) {
            return scaleY(eCFieldElement);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            return eCPoint.isInfinity() ? this : add(eCPoint.negate());
        }

        public AbstractF2m tau() {
            ECPoint createRawPoint;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement = this.f1419x;
            if (coordinateSystem != 0) {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                }
                createRawPoint = curve.createRawPoint(eCFieldElement.square(), this.f1420y.square(), new ECFieldElement[]{this.zs[0].square()});
                return (AbstractF2m) createRawPoint;
            }
            createRawPoint = curve.createRawPoint(eCFieldElement.square(), this.f1420y.square());
            return (AbstractF2m) createRawPoint;
        }

        public AbstractF2m tauPow(int i2) {
            ECPoint createRawPoint;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement = this.f1419x;
            if (coordinateSystem != 0) {
                if (coordinateSystem != 1) {
                    if (coordinateSystem != 5) {
                        if (coordinateSystem != 6) {
                            throw new IllegalStateException("unsupported coordinate system");
                        }
                    }
                }
                createRawPoint = curve.createRawPoint(eCFieldElement.squarePow(i2), this.f1420y.squarePow(i2), new ECFieldElement[]{this.zs[0].squarePow(i2)});
                return (AbstractF2m) createRawPoint;
            }
            createRawPoint = curve.createRawPoint(eCFieldElement.squarePow(i2), this.f1420y.squarePow(i2));
            return (AbstractF2m) createRawPoint;
        }

        public AbstractF2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }
    }

    public static abstract class AbstractFp extends ECPoint {
        public AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public boolean getCompressionYTilde() {
            return getAffineYCoord().testBitZero();
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public boolean satisfiesCurveEquation() {
            ECFieldElement eCFieldElement = this.f1419x;
            ECFieldElement eCFieldElement2 = this.f1420y;
            ECFieldElement a2 = this.curve.getA();
            ECFieldElement b = this.curve.getB();
            ECFieldElement square = eCFieldElement2.square();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 0) {
                if (curveCoordinateSystem == 1) {
                    ECFieldElement eCFieldElement3 = this.zs[0];
                    if (!eCFieldElement3.isOne()) {
                        ECFieldElement square2 = eCFieldElement3.square();
                        ECFieldElement multiply = eCFieldElement3.multiply(square2);
                        square = square.multiply(eCFieldElement3);
                        a2 = a2.multiply(square2);
                        b = b.multiply(multiply);
                    }
                } else {
                    if (curveCoordinateSystem != 2 && curveCoordinateSystem != 3 && curveCoordinateSystem != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    ECFieldElement eCFieldElement4 = this.zs[0];
                    if (!eCFieldElement4.isOne()) {
                        ECFieldElement square3 = eCFieldElement4.square();
                        ECFieldElement square4 = square3.square();
                        ECFieldElement multiply2 = square3.multiply(square4);
                        a2 = a2.multiply(square4);
                        b = b.multiply(multiply2);
                    }
                }
            }
            return square.equals(eCFieldElement.square().add(a2).multiply(eCFieldElement).add(b));
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint subtract(ECPoint eCPoint) {
            return eCPoint.isInfinity() ? this : add(eCPoint.negate());
        }

        public AbstractFp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }
    }

    public static class F2m extends AbstractF2m {
        public F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint add(ECPoint eCPoint) {
            ECFieldElement eCFieldElement;
            ECFieldElement eCFieldElement2;
            ECFieldElement eCFieldElement3;
            ECFieldElement squarePlusProduct;
            ECFieldElement multiply;
            ECFieldElement eCFieldElement4;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement5 = this.f1419x;
            ECFieldElement eCFieldElement6 = eCPoint.f1419x;
            if (coordinateSystem == 0) {
                ECFieldElement eCFieldElement7 = this.f1420y;
                ECFieldElement eCFieldElement8 = eCPoint.f1420y;
                ECFieldElement add = eCFieldElement5.add(eCFieldElement6);
                ECFieldElement add2 = eCFieldElement7.add(eCFieldElement8);
                if (add.isZero()) {
                    return add2.isZero() ? twice() : curve.getInfinity();
                }
                ECFieldElement divide = add2.divide(add);
                ECFieldElement add3 = AbstractC0413b.m1026t(divide, divide, add).add(curve.getA());
                return new F2m(curve, add3, divide.multiply(eCFieldElement5.add(add3)).add(add3).add(eCFieldElement7));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement9 = this.f1420y;
                ECFieldElement eCFieldElement10 = this.zs[0];
                ECFieldElement eCFieldElement11 = eCPoint.f1420y;
                ECFieldElement eCFieldElement12 = eCPoint.zs[0];
                boolean isOne = eCFieldElement12.isOne();
                ECFieldElement add4 = eCFieldElement10.multiply(eCFieldElement11).add(isOne ? eCFieldElement9 : eCFieldElement9.multiply(eCFieldElement12));
                ECFieldElement add5 = eCFieldElement10.multiply(eCFieldElement6).add(isOne ? eCFieldElement5 : eCFieldElement5.multiply(eCFieldElement12));
                if (add5.isZero()) {
                    return add4.isZero() ? twice() : curve.getInfinity();
                }
                ECFieldElement square = add5.square();
                ECFieldElement multiply2 = square.multiply(add5);
                if (!isOne) {
                    eCFieldElement10 = eCFieldElement10.multiply(eCFieldElement12);
                }
                ECFieldElement add6 = add4.add(add5);
                ECFieldElement add7 = add6.multiplyPlusProduct(add4, square, curve.getA()).multiply(eCFieldElement10).add(multiply2);
                ECFieldElement multiply3 = add5.multiply(add7);
                if (!isOne) {
                    square = square.multiply(eCFieldElement12);
                }
                return new F2m(curve, multiply3, add4.multiplyPlusProduct(eCFieldElement5, add5, eCFieldElement9).multiplyPlusProduct(square, add6, add7), new ECFieldElement[]{multiply2.multiply(eCFieldElement10)});
            }
            if (coordinateSystem != 6) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            if (eCFieldElement5.isZero()) {
                return eCFieldElement6.isZero() ? curve.getInfinity() : eCPoint.add(this);
            }
            ECFieldElement eCFieldElement13 = this.f1420y;
            ECFieldElement eCFieldElement14 = this.zs[0];
            ECFieldElement eCFieldElement15 = eCPoint.f1420y;
            ECFieldElement eCFieldElement16 = eCPoint.zs[0];
            boolean isOne2 = eCFieldElement14.isOne();
            if (isOne2) {
                eCFieldElement = eCFieldElement6;
                eCFieldElement2 = eCFieldElement15;
            } else {
                eCFieldElement = eCFieldElement6.multiply(eCFieldElement14);
                eCFieldElement2 = eCFieldElement15.multiply(eCFieldElement14);
            }
            boolean isOne3 = eCFieldElement16.isOne();
            if (isOne3) {
                eCFieldElement3 = eCFieldElement13;
            } else {
                eCFieldElement5 = eCFieldElement5.multiply(eCFieldElement16);
                eCFieldElement3 = eCFieldElement13.multiply(eCFieldElement16);
            }
            ECFieldElement add8 = eCFieldElement3.add(eCFieldElement2);
            ECFieldElement add9 = eCFieldElement5.add(eCFieldElement);
            if (add9.isZero()) {
                return add8.isZero() ? twice() : curve.getInfinity();
            }
            if (eCFieldElement6.isZero()) {
                ECPoint normalize = normalize();
                ECFieldElement xCoord = normalize.getXCoord();
                ECFieldElement yCoord = normalize.getYCoord();
                ECFieldElement divide2 = yCoord.add(eCFieldElement15).divide(xCoord);
                eCFieldElement4 = AbstractC0413b.m1026t(divide2, divide2, xCoord).add(curve.getA());
                if (eCFieldElement4.isZero()) {
                    return new F2m(curve, eCFieldElement4, curve.getB().sqrt());
                }
                squarePlusProduct = divide2.multiply(xCoord.add(eCFieldElement4)).add(eCFieldElement4).add(yCoord).divide(eCFieldElement4).add(eCFieldElement4);
                multiply = curve.fromBigInteger(ECConstants.ONE);
            } else {
                ECFieldElement square2 = add9.square();
                ECFieldElement multiply4 = add8.multiply(eCFieldElement5);
                ECFieldElement multiply5 = add8.multiply(eCFieldElement);
                ECFieldElement multiply6 = multiply4.multiply(multiply5);
                if (multiply6.isZero()) {
                    return new F2m(curve, multiply6, curve.getB().sqrt());
                }
                ECFieldElement multiply7 = add8.multiply(square2);
                if (!isOne3) {
                    multiply7 = multiply7.multiply(eCFieldElement16);
                }
                squarePlusProduct = multiply5.add(square2).squarePlusProduct(multiply7, eCFieldElement13.add(eCFieldElement14));
                multiply = !isOne2 ? multiply7.multiply(eCFieldElement14) : multiply7;
                eCFieldElement4 = multiply6;
            }
            return new F2m(curve, eCFieldElement4, squarePlusProduct, new ECFieldElement[]{multiply});
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint detach() {
            return new F2m(null, getAffineXCoord(), getAffineYCoord());
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public boolean getCompressionYTilde() {
            ECFieldElement rawXCoord = getRawXCoord();
            if (rawXCoord.isZero()) {
                return false;
            }
            ECFieldElement rawYCoord = getRawYCoord();
            int curveCoordinateSystem = getCurveCoordinateSystem();
            return (curveCoordinateSystem == 5 || curveCoordinateSystem == 6) ? rawYCoord.testBitZero() != rawXCoord.testBitZero() : rawYCoord.divide(rawXCoord).testBitZero();
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECFieldElement getYCoord() {
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem != 5 && curveCoordinateSystem != 6) {
                return this.f1420y;
            }
            ECFieldElement eCFieldElement = this.f1419x;
            ECFieldElement eCFieldElement2 = this.f1420y;
            if (isInfinity() || eCFieldElement.isZero()) {
                return eCFieldElement2;
            }
            ECFieldElement multiply = eCFieldElement2.add(eCFieldElement).multiply(eCFieldElement);
            if (6 != curveCoordinateSystem) {
                return multiply;
            }
            ECFieldElement eCFieldElement3 = this.zs[0];
            return !eCFieldElement3.isOne() ? multiply.divide(eCFieldElement3) : multiply;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            if (isInfinity()) {
                return this;
            }
            ECFieldElement eCFieldElement = this.f1419x;
            if (eCFieldElement.isZero()) {
                return this;
            }
            int curveCoordinateSystem = getCurveCoordinateSystem();
            if (curveCoordinateSystem == 0) {
                return new F2m(this.curve, eCFieldElement, this.f1420y.add(eCFieldElement));
            }
            if (curveCoordinateSystem == 1) {
                return new F2m(this.curve, eCFieldElement, this.f1420y.add(eCFieldElement), new ECFieldElement[]{this.zs[0]});
            }
            if (curveCoordinateSystem == 5) {
                return new F2m(this.curve, eCFieldElement, this.f1420y.addOne());
            }
            if (curveCoordinateSystem != 6) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement2 = this.f1420y;
            ECFieldElement eCFieldElement3 = this.zs[0];
            return new F2m(this.curve, eCFieldElement, eCFieldElement2.add(eCFieldElement3), new ECFieldElement[]{eCFieldElement3});
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement add;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.f1419x;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem == 0) {
                ECFieldElement add2 = this.f1420y.divide(eCFieldElement).add(eCFieldElement);
                ECFieldElement add3 = add2.square().add(add2).add(curve.getA());
                return new F2m(curve, add3, eCFieldElement.squarePlusProduct(add3, add2.addOne()));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement2 = this.f1420y;
                ECFieldElement eCFieldElement3 = this.zs[0];
                boolean isOne = eCFieldElement3.isOne();
                ECFieldElement multiply = isOne ? eCFieldElement : eCFieldElement.multiply(eCFieldElement3);
                if (!isOne) {
                    eCFieldElement2 = eCFieldElement2.multiply(eCFieldElement3);
                }
                ECFieldElement square = eCFieldElement.square();
                ECFieldElement add4 = square.add(eCFieldElement2);
                ECFieldElement square2 = multiply.square();
                ECFieldElement add5 = add4.add(multiply);
                ECFieldElement multiplyPlusProduct = add5.multiplyPlusProduct(add4, square2, curve.getA());
                return new F2m(curve, multiply.multiply(multiplyPlusProduct), square.square().multiplyPlusProduct(multiply, multiplyPlusProduct, add5), new ECFieldElement[]{multiply.multiply(square2)});
            }
            if (coordinateSystem != 6) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement4 = this.f1420y;
            ECFieldElement eCFieldElement5 = this.zs[0];
            boolean isOne2 = eCFieldElement5.isOne();
            ECFieldElement multiply2 = isOne2 ? eCFieldElement4 : eCFieldElement4.multiply(eCFieldElement5);
            ECFieldElement square3 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
            ECFieldElement a2 = curve.getA();
            ECFieldElement multiply3 = isOne2 ? a2 : a2.multiply(square3);
            ECFieldElement m1026t = AbstractC0413b.m1026t(eCFieldElement4, multiply2, multiply3);
            if (m1026t.isZero()) {
                return new F2m(curve, m1026t, curve.getB().sqrt());
            }
            ECFieldElement square4 = m1026t.square();
            ECFieldElement multiply4 = isOne2 ? m1026t : m1026t.multiply(square3);
            ECFieldElement b = curve.getB();
            if (b.bitLength() < (curve.getFieldSize() >> 1)) {
                ECFieldElement square5 = eCFieldElement4.add(eCFieldElement).square();
                add = square5.add(m1026t).add(square3).multiply(square5).add(b.isOne() ? multiply3.add(square3).square() : multiply3.squarePlusProduct(b, square3.square())).add(square4);
                if (!a2.isZero()) {
                    if (!a2.isOne()) {
                        add = add.add(a2.addOne().multiply(multiply4));
                    }
                    return new F2m(curve, square4, add, new ECFieldElement[]{multiply4});
                }
            } else {
                if (!isOne2) {
                    eCFieldElement = eCFieldElement.multiply(eCFieldElement5);
                }
                add = eCFieldElement.squarePlusProduct(m1026t, multiply2).add(square4);
            }
            add = add.add(multiply4);
            return new F2m(curve, square4, add, new ECFieldElement[]{multiply4});
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twicePlus(ECPoint eCPoint) {
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return twice();
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.f1419x;
            if (eCFieldElement.isZero()) {
                return eCPoint;
            }
            if (curve.getCoordinateSystem() != 6) {
                return twice().add(eCPoint);
            }
            ECFieldElement eCFieldElement2 = eCPoint.f1419x;
            ECFieldElement eCFieldElement3 = eCPoint.zs[0];
            if (eCFieldElement2.isZero() || !eCFieldElement3.isOne()) {
                return twice().add(eCPoint);
            }
            ECFieldElement eCFieldElement4 = this.f1420y;
            ECFieldElement eCFieldElement5 = this.zs[0];
            ECFieldElement eCFieldElement6 = eCPoint.f1420y;
            ECFieldElement square = eCFieldElement.square();
            ECFieldElement square2 = eCFieldElement4.square();
            ECFieldElement square3 = eCFieldElement5.square();
            ECFieldElement add = curve.getA().multiply(square3).add(square2).add(eCFieldElement4.multiply(eCFieldElement5));
            ECFieldElement addOne = eCFieldElement6.addOne();
            ECFieldElement multiplyPlusProduct = curve.getA().add(addOne).multiply(square3).add(square2).multiplyPlusProduct(add, square, square3);
            ECFieldElement multiply = eCFieldElement2.multiply(square3);
            ECFieldElement square4 = multiply.add(add).square();
            if (square4.isZero()) {
                return multiplyPlusProduct.isZero() ? eCPoint.twice() : curve.getInfinity();
            }
            if (multiplyPlusProduct.isZero()) {
                return new F2m(curve, multiplyPlusProduct, curve.getB().sqrt());
            }
            ECFieldElement multiply2 = multiplyPlusProduct.square().multiply(multiply);
            ECFieldElement multiply3 = multiplyPlusProduct.multiply(square4).multiply(square3);
            return new F2m(curve, multiply2, multiplyPlusProduct.add(square4).square().multiplyPlusProduct(add, addOne, multiply3), new ECFieldElement[]{multiply3});
        }

        public F2m(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }
    }

    public static class Fp extends AbstractFp {
        public Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            super(eCCurve, eCFieldElement, eCFieldElement2);
        }

        /* JADX WARN: Removed duplicated region for block: B:35:0x0128  */
        /* JADX WARN: Removed duplicated region for block: B:38:0x0136  */
        @Override // org.bouncycastle.math.ec.ECPoint
        /*
            Code decompiled incorrectly, please refer to instructions dump.
        */
        public ECPoint add(ECPoint eCPoint) {
            ECFieldElement square;
            ECFieldElement multiplyMinusProduct;
            ECFieldElement multiply;
            ECFieldElement eCFieldElement;
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return this;
            }
            if (this == eCPoint) {
                return twice();
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement2 = this.f1419x;
            ECFieldElement eCFieldElement3 = this.f1420y;
            ECFieldElement eCFieldElement4 = eCPoint.f1419x;
            ECFieldElement eCFieldElement5 = eCPoint.f1420y;
            if (coordinateSystem == 0) {
                ECFieldElement subtract = eCFieldElement4.subtract(eCFieldElement2);
                ECFieldElement subtract2 = eCFieldElement5.subtract(eCFieldElement3);
                if (subtract.isZero()) {
                    return subtract2.isZero() ? twice() : curve.getInfinity();
                }
                ECFieldElement divide = subtract2.divide(subtract);
                ECFieldElement subtract3 = divide.square().subtract(eCFieldElement2).subtract(eCFieldElement4);
                return new Fp(curve, subtract3, divide.multiply(eCFieldElement2.subtract(subtract3)).subtract(eCFieldElement3));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement6 = this.zs[0];
                ECFieldElement eCFieldElement7 = eCPoint.zs[0];
                boolean isOne = eCFieldElement6.isOne();
                boolean isOne2 = eCFieldElement7.isOne();
                if (!isOne) {
                    eCFieldElement5 = eCFieldElement5.multiply(eCFieldElement6);
                }
                if (!isOne2) {
                    eCFieldElement3 = eCFieldElement3.multiply(eCFieldElement7);
                }
                ECFieldElement subtract4 = eCFieldElement5.subtract(eCFieldElement3);
                if (!isOne) {
                    eCFieldElement4 = eCFieldElement4.multiply(eCFieldElement6);
                }
                if (!isOne2) {
                    eCFieldElement2 = eCFieldElement2.multiply(eCFieldElement7);
                }
                ECFieldElement subtract5 = eCFieldElement4.subtract(eCFieldElement2);
                if (subtract5.isZero()) {
                    return subtract4.isZero() ? twice() : curve.getInfinity();
                }
                if (isOne) {
                    eCFieldElement6 = eCFieldElement7;
                } else if (!isOne2) {
                    eCFieldElement6 = eCFieldElement6.multiply(eCFieldElement7);
                }
                ECFieldElement square2 = subtract5.square();
                ECFieldElement multiply2 = square2.multiply(subtract5);
                ECFieldElement multiply3 = square2.multiply(eCFieldElement2);
                ECFieldElement subtract6 = subtract4.square().multiply(eCFieldElement6).subtract(multiply2).subtract(two(multiply3));
                return new Fp(curve, subtract5.multiply(subtract6), multiply3.subtract(subtract6).multiplyMinusProduct(subtract4, eCFieldElement3, multiply2), new ECFieldElement[]{multiply2.multiply(eCFieldElement6)});
            }
            if (coordinateSystem != 2 && coordinateSystem != 4) {
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement8 = this.zs[0];
            ECFieldElement eCFieldElement9 = eCPoint.zs[0];
            boolean isOne3 = eCFieldElement8.isOne();
            if (isOne3 || !eCFieldElement8.equals(eCFieldElement9)) {
                if (!isOne3) {
                    ECFieldElement square3 = eCFieldElement8.square();
                    eCFieldElement4 = square3.multiply(eCFieldElement4);
                    eCFieldElement5 = square3.multiply(eCFieldElement8).multiply(eCFieldElement5);
                }
                boolean isOne4 = eCFieldElement9.isOne();
                if (!isOne4) {
                    ECFieldElement square4 = eCFieldElement9.square();
                    eCFieldElement2 = square4.multiply(eCFieldElement2);
                    eCFieldElement3 = square4.multiply(eCFieldElement9).multiply(eCFieldElement3);
                }
                ECFieldElement subtract7 = eCFieldElement2.subtract(eCFieldElement4);
                ECFieldElement subtract8 = eCFieldElement3.subtract(eCFieldElement5);
                if (subtract7.isZero()) {
                    return subtract8.isZero() ? twice() : curve.getInfinity();
                }
                square = subtract7.square();
                ECFieldElement multiply4 = square.multiply(subtract7);
                ECFieldElement multiply5 = square.multiply(eCFieldElement2);
                ECFieldElement subtract9 = subtract8.square().add(multiply4).subtract(two(multiply5));
                multiplyMinusProduct = multiply5.subtract(subtract9).multiplyMinusProduct(subtract8, multiply4, eCFieldElement3);
                ECFieldElement multiply6 = !isOne3 ? subtract7.multiply(eCFieldElement8) : subtract7;
                multiply = !isOne4 ? multiply6.multiply(eCFieldElement9) : multiply6;
                if (multiply == subtract7) {
                    eCFieldElement = subtract9;
                    return new Fp(curve, eCFieldElement, multiplyMinusProduct, coordinateSystem != 4 ? new ECFieldElement[]{multiply, calculateJacobianModifiedW(multiply, square)} : new ECFieldElement[]{multiply});
                }
                eCFieldElement = subtract9;
            } else {
                ECFieldElement subtract10 = eCFieldElement2.subtract(eCFieldElement4);
                ECFieldElement subtract11 = eCFieldElement3.subtract(eCFieldElement5);
                if (subtract10.isZero()) {
                    return subtract11.isZero() ? twice() : curve.getInfinity();
                }
                ECFieldElement square5 = subtract10.square();
                ECFieldElement multiply7 = eCFieldElement2.multiply(square5);
                ECFieldElement multiply8 = eCFieldElement4.multiply(square5);
                ECFieldElement multiply9 = multiply7.subtract(multiply8).multiply(eCFieldElement3);
                eCFieldElement = subtract11.square().subtract(multiply7).subtract(multiply8);
                multiplyMinusProduct = multiply7.subtract(eCFieldElement).multiply(subtract11).subtract(multiply9);
                multiply = subtract10.multiply(eCFieldElement8);
            }
            square = null;
            return new Fp(curve, eCFieldElement, multiplyMinusProduct, coordinateSystem != 4 ? new ECFieldElement[]{multiply, calculateJacobianModifiedW(multiply, square)} : new ECFieldElement[]{multiply});
        }

        public ECFieldElement calculateJacobianModifiedW(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
            ECFieldElement a2 = getCurve().getA();
            if (a2.isZero() || eCFieldElement.isOne()) {
                return a2;
            }
            if (eCFieldElement2 == null) {
                eCFieldElement2 = eCFieldElement.square();
            }
            ECFieldElement square = eCFieldElement2.square();
            ECFieldElement negate = a2.negate();
            return negate.bitLength() < a2.bitLength() ? square.multiply(negate).negate() : square.multiply(a2);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint detach() {
            return new Fp(null, getAffineXCoord(), getAffineYCoord());
        }

        public ECFieldElement doubleProductFromSquares(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement eCFieldElement3, ECFieldElement eCFieldElement4) {
            return eCFieldElement.add(eCFieldElement2).square().subtract(eCFieldElement3).subtract(eCFieldElement4);
        }

        public ECFieldElement eight(ECFieldElement eCFieldElement) {
            return four(two(eCFieldElement));
        }

        public ECFieldElement four(ECFieldElement eCFieldElement) {
            return two(two(eCFieldElement));
        }

        public ECFieldElement getJacobianModifiedW() {
            ECFieldElement[] eCFieldElementArr = this.zs;
            ECFieldElement eCFieldElement = eCFieldElementArr[1];
            if (eCFieldElement != null) {
                return eCFieldElement;
            }
            ECFieldElement calculateJacobianModifiedW = calculateJacobianModifiedW(eCFieldElementArr[0], null);
            eCFieldElementArr[1] = calculateJacobianModifiedW;
            return calculateJacobianModifiedW;
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECFieldElement getZCoord(int i2) {
            return (i2 == 1 && 4 == getCurveCoordinateSystem()) ? getJacobianModifiedW() : super.getZCoord(i2);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint negate() {
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            return curve.getCoordinateSystem() != 0 ? new Fp(curve, this.f1419x, this.f1420y.negate(), this.zs) : new Fp(curve, this.f1419x, this.f1420y.negate());
        }

        public ECFieldElement three(ECFieldElement eCFieldElement) {
            return two(eCFieldElement).add(eCFieldElement);
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint threeTimes() {
            if (isInfinity()) {
                return this;
            }
            ECFieldElement eCFieldElement = this.f1420y;
            if (eCFieldElement.isZero()) {
                return this;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem != 0) {
                return coordinateSystem != 4 ? twice().add(this) : twiceJacobianModified(false).add(this);
            }
            ECFieldElement eCFieldElement2 = this.f1419x;
            ECFieldElement two = two(eCFieldElement);
            ECFieldElement square = two.square();
            ECFieldElement add = three(eCFieldElement2.square()).add(getCurve().getA());
            ECFieldElement subtract = three(eCFieldElement2).multiply(square).subtract(add.square());
            if (subtract.isZero()) {
                return getCurve().getInfinity();
            }
            ECFieldElement invert = subtract.multiply(two).invert();
            ECFieldElement multiply = subtract.multiply(invert).multiply(add);
            ECFieldElement subtract2 = square.square().multiply(invert).subtract(multiply);
            ECFieldElement add2 = subtract2.subtract(multiply).multiply(multiply.add(subtract2)).add(eCFieldElement2);
            return new Fp(curve, add2, eCFieldElement2.subtract(add2).multiply(subtract2).subtract(eCFieldElement));
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint timesPow2(int i2) {
            ECFieldElement square;
            if (i2 < 0) {
                throw new IllegalArgumentException("'e' cannot be negative");
            }
            if (i2 == 0 || isInfinity()) {
                return this;
            }
            if (i2 == 1) {
                return twice();
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement = this.f1420y;
            if (eCFieldElement.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement a2 = curve.getA();
            ECFieldElement eCFieldElement2 = this.f1419x;
            ECFieldElement[] eCFieldElementArr = this.zs;
            ECFieldElement fromBigInteger = eCFieldElementArr.length < 1 ? curve.fromBigInteger(ECConstants.ONE) : eCFieldElementArr[0];
            if (!fromBigInteger.isOne() && coordinateSystem != 0) {
                if (coordinateSystem == 1) {
                    square = fromBigInteger.square();
                    eCFieldElement2 = eCFieldElement2.multiply(fromBigInteger);
                    eCFieldElement = eCFieldElement.multiply(square);
                } else if (coordinateSystem == 2) {
                    square = null;
                } else {
                    if (coordinateSystem != 4) {
                        throw new IllegalStateException("unsupported coordinate system");
                    }
                    a2 = getJacobianModifiedW();
                }
                a2 = calculateJacobianModifiedW(fromBigInteger, square);
            }
            int i3 = 0;
            while (i3 < i2) {
                if (eCFieldElement.isZero()) {
                    return curve.getInfinity();
                }
                ECFieldElement three = three(eCFieldElement2.square());
                ECFieldElement two = two(eCFieldElement);
                ECFieldElement multiply = two.multiply(eCFieldElement);
                ECFieldElement two2 = two(eCFieldElement2.multiply(multiply));
                ECFieldElement two3 = two(multiply.square());
                if (!a2.isZero()) {
                    three = three.add(a2);
                    a2 = two(two3.multiply(a2));
                }
                ECFieldElement subtract = three.square().subtract(two(two2));
                eCFieldElement = three.multiply(two2.subtract(subtract)).subtract(two3);
                fromBigInteger = fromBigInteger.isOne() ? two : two.multiply(fromBigInteger);
                i3++;
                eCFieldElement2 = subtract;
            }
            if (coordinateSystem == 0) {
                ECFieldElement invert = fromBigInteger.invert();
                ECFieldElement square2 = invert.square();
                return new Fp(curve, eCFieldElement2.multiply(square2), eCFieldElement.multiply(square2.multiply(invert)));
            }
            if (coordinateSystem == 1) {
                return new Fp(curve, eCFieldElement2.multiply(fromBigInteger), eCFieldElement, new ECFieldElement[]{fromBigInteger.multiply(fromBigInteger.square())});
            }
            if (coordinateSystem == 2) {
                return new Fp(curve, eCFieldElement2, eCFieldElement, new ECFieldElement[]{fromBigInteger});
            }
            if (coordinateSystem == 4) {
                return new Fp(curve, eCFieldElement2, eCFieldElement, new ECFieldElement[]{fromBigInteger, a2});
            }
            throw new IllegalStateException("unsupported coordinate system");
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twice() {
            ECFieldElement eCFieldElement;
            ECFieldElement multiply;
            if (isInfinity()) {
                return this;
            }
            ECCurve curve = getCurve();
            ECFieldElement eCFieldElement2 = this.f1420y;
            if (eCFieldElement2.isZero()) {
                return curve.getInfinity();
            }
            int coordinateSystem = curve.getCoordinateSystem();
            ECFieldElement eCFieldElement3 = this.f1419x;
            if (coordinateSystem == 0) {
                ECFieldElement divide = three(eCFieldElement3.square()).add(getCurve().getA()).divide(two(eCFieldElement2));
                ECFieldElement subtract = divide.square().subtract(two(eCFieldElement3));
                return new Fp(curve, subtract, divide.multiply(eCFieldElement3.subtract(subtract)).subtract(eCFieldElement2));
            }
            if (coordinateSystem == 1) {
                ECFieldElement eCFieldElement4 = this.zs[0];
                boolean isOne = eCFieldElement4.isOne();
                ECFieldElement a2 = curve.getA();
                if (!a2.isZero() && !isOne) {
                    a2 = a2.multiply(eCFieldElement4.square());
                }
                ECFieldElement add = a2.add(three(eCFieldElement3.square()));
                ECFieldElement multiply2 = isOne ? eCFieldElement2 : eCFieldElement2.multiply(eCFieldElement4);
                ECFieldElement square = isOne ? eCFieldElement2.square() : multiply2.multiply(eCFieldElement2);
                ECFieldElement four = four(eCFieldElement3.multiply(square));
                ECFieldElement subtract2 = add.square().subtract(two(four));
                ECFieldElement two = two(multiply2);
                ECFieldElement multiply3 = subtract2.multiply(two);
                ECFieldElement two2 = two(square);
                return new Fp(curve, multiply3, four.subtract(subtract2).multiply(add).subtract(two(two2.square())), new ECFieldElement[]{two(isOne ? two(two2) : two.square()).multiply(multiply2)});
            }
            if (coordinateSystem != 2) {
                if (coordinateSystem == 4) {
                    return twiceJacobianModified(true);
                }
                throw new IllegalStateException("unsupported coordinate system");
            }
            ECFieldElement eCFieldElement5 = this.zs[0];
            boolean isOne2 = eCFieldElement5.isOne();
            ECFieldElement square2 = eCFieldElement2.square();
            ECFieldElement square3 = square2.square();
            ECFieldElement a3 = curve.getA();
            ECFieldElement negate = a3.negate();
            if (negate.toBigInteger().equals(BigInteger.valueOf(3L))) {
                ECFieldElement square4 = isOne2 ? eCFieldElement5 : eCFieldElement5.square();
                eCFieldElement = three(eCFieldElement3.add(square4).multiply(eCFieldElement3.subtract(square4)));
                multiply = square2.multiply(eCFieldElement3);
            } else {
                ECFieldElement three = three(eCFieldElement3.square());
                if (!isOne2) {
                    if (a3.isZero()) {
                        eCFieldElement = three;
                    } else {
                        ECFieldElement square5 = eCFieldElement5.square().square();
                        if (negate.bitLength() < a3.bitLength()) {
                            eCFieldElement = three.subtract(square5.multiply(negate));
                        } else {
                            a3 = square5.multiply(a3);
                        }
                    }
                    multiply = eCFieldElement3.multiply(square2);
                }
                eCFieldElement = three.add(a3);
                multiply = eCFieldElement3.multiply(square2);
            }
            ECFieldElement four2 = four(multiply);
            ECFieldElement subtract3 = eCFieldElement.square().subtract(two(four2));
            ECFieldElement subtract4 = four2.subtract(subtract3).multiply(eCFieldElement).subtract(eight(square3));
            ECFieldElement two3 = two(eCFieldElement2);
            if (!isOne2) {
                two3 = two3.multiply(eCFieldElement5);
            }
            return new Fp(curve, subtract3, subtract4, new ECFieldElement[]{two3});
        }

        public Fp twiceJacobianModified(boolean z2) {
            ECFieldElement eCFieldElement = this.f1419x;
            ECFieldElement eCFieldElement2 = this.f1420y;
            ECFieldElement eCFieldElement3 = this.zs[0];
            ECFieldElement jacobianModifiedW = getJacobianModifiedW();
            ECFieldElement add = three(eCFieldElement.square()).add(jacobianModifiedW);
            ECFieldElement two = two(eCFieldElement2);
            ECFieldElement multiply = two.multiply(eCFieldElement2);
            ECFieldElement two2 = two(eCFieldElement.multiply(multiply));
            ECFieldElement subtract = add.square().subtract(two(two2));
            ECFieldElement two3 = two(multiply.square());
            ECFieldElement subtract2 = add.multiply(two2.subtract(subtract)).subtract(two3);
            ECFieldElement two4 = z2 ? two(two3.multiply(jacobianModifiedW)) : null;
            if (!eCFieldElement3.isOne()) {
                two = two.multiply(eCFieldElement3);
            }
            return new Fp(getCurve(), subtract, subtract2, new ECFieldElement[]{two, two4});
        }

        @Override // org.bouncycastle.math.ec.ECPoint
        public ECPoint twicePlus(ECPoint eCPoint) {
            if (this == eCPoint) {
                return threeTimes();
            }
            if (isInfinity()) {
                return eCPoint;
            }
            if (eCPoint.isInfinity()) {
                return twice();
            }
            ECFieldElement eCFieldElement = this.f1420y;
            if (eCFieldElement.isZero()) {
                return eCPoint;
            }
            ECCurve curve = getCurve();
            int coordinateSystem = curve.getCoordinateSystem();
            if (coordinateSystem != 0) {
                return coordinateSystem != 4 ? twice().add(eCPoint) : twiceJacobianModified(false).add(eCPoint);
            }
            ECFieldElement eCFieldElement2 = this.f1419x;
            ECFieldElement eCFieldElement3 = eCPoint.f1419x;
            ECFieldElement eCFieldElement4 = eCPoint.f1420y;
            ECFieldElement subtract = eCFieldElement3.subtract(eCFieldElement2);
            ECFieldElement subtract2 = eCFieldElement4.subtract(eCFieldElement);
            if (subtract.isZero()) {
                return subtract2.isZero() ? threeTimes() : this;
            }
            ECFieldElement square = subtract.square();
            ECFieldElement subtract3 = square.multiply(two(eCFieldElement2).add(eCFieldElement3)).subtract(subtract2.square());
            if (subtract3.isZero()) {
                return curve.getInfinity();
            }
            ECFieldElement invert = subtract3.multiply(subtract).invert();
            ECFieldElement multiply = subtract3.multiply(invert).multiply(subtract2);
            ECFieldElement subtract4 = two(eCFieldElement).multiply(square).multiply(subtract).multiply(invert).subtract(multiply);
            ECFieldElement add = subtract4.subtract(multiply).multiply(multiply.add(subtract4)).add(eCFieldElement3);
            return new Fp(curve, add, eCFieldElement2.subtract(add).multiply(subtract4).subtract(eCFieldElement));
        }

        public ECFieldElement two(ECFieldElement eCFieldElement) {
            return eCFieldElement.add(eCFieldElement);
        }

        public Fp(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
            super(eCCurve, eCFieldElement, eCFieldElement2, eCFieldElementArr);
        }
    }

    public ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        this(eCCurve, eCFieldElement, eCFieldElement2, getInitialZCoords(eCCurve));
    }

    public static ECFieldElement[] getInitialZCoords(ECCurve eCCurve) {
        int coordinateSystem = eCCurve == null ? 0 : eCCurve.getCoordinateSystem();
        if (coordinateSystem == 0 || coordinateSystem == 5) {
            return EMPTY_ZS;
        }
        ECFieldElement fromBigInteger = eCCurve.fromBigInteger(ECConstants.ONE);
        if (coordinateSystem != 1 && coordinateSystem != 2) {
            if (coordinateSystem == 3) {
                return new ECFieldElement[]{fromBigInteger, fromBigInteger, fromBigInteger};
            }
            if (coordinateSystem == 4) {
                return new ECFieldElement[]{fromBigInteger, eCCurve.getA()};
            }
            if (coordinateSystem != 6) {
                throw new IllegalArgumentException("unknown coordinate system");
            }
        }
        return new ECFieldElement[]{fromBigInteger};
    }

    public abstract ECPoint add(ECPoint eCPoint);

    public void checkNormalized() {
        if (!isNormalized()) {
            throw new IllegalStateException("point not in normal form");
        }
    }

    public ECPoint createScaledPoint(ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2) {
        return getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().multiply(eCFieldElement2));
    }

    public abstract ECPoint detach();

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof ECPoint) {
            return equals((ECPoint) obj);
        }
        return false;
    }

    public ECFieldElement getAffineXCoord() {
        checkNormalized();
        return getXCoord();
    }

    public ECFieldElement getAffineYCoord() {
        checkNormalized();
        return getYCoord();
    }

    public abstract boolean getCompressionYTilde();

    public ECCurve getCurve() {
        return this.curve;
    }

    public int getCurveCoordinateSystem() {
        ECCurve eCCurve = this.curve;
        if (eCCurve == null) {
            return 0;
        }
        return eCCurve.getCoordinateSystem();
    }

    public final ECPoint getDetachedPoint() {
        return normalize().detach();
    }

    public byte[] getEncoded(boolean z2) {
        if (isInfinity()) {
            return new byte[1];
        }
        ECPoint normalize = normalize();
        byte[] encoded = normalize.getXCoord().getEncoded();
        if (z2) {
            byte[] bArr = new byte[encoded.length + 1];
            bArr[0] = (byte) (normalize.getCompressionYTilde() ? 3 : 2);
            System.arraycopy(encoded, 0, bArr, 1, encoded.length);
            return bArr;
        }
        byte[] encoded2 = normalize.getYCoord().getEncoded();
        byte[] bArr2 = new byte[encoded.length + encoded2.length + 1];
        bArr2[0] = 4;
        System.arraycopy(encoded, 0, bArr2, 1, encoded.length);
        System.arraycopy(encoded2, 0, bArr2, encoded.length + 1, encoded2.length);
        return bArr2;
    }

    public final ECFieldElement getRawXCoord() {
        return this.f1419x;
    }

    public final ECFieldElement getRawYCoord() {
        return this.f1420y;
    }

    public final ECFieldElement[] getRawZCoords() {
        return this.zs;
    }

    public ECFieldElement getXCoord() {
        return this.f1419x;
    }

    public ECFieldElement getYCoord() {
        return this.f1420y;
    }

    public ECFieldElement getZCoord(int i2) {
        if (i2 >= 0) {
            ECFieldElement[] eCFieldElementArr = this.zs;
            if (i2 < eCFieldElementArr.length) {
                return eCFieldElementArr[i2];
            }
        }
        return null;
    }

    public ECFieldElement[] getZCoords() {
        ECFieldElement[] eCFieldElementArr = this.zs;
        int length = eCFieldElementArr.length;
        if (length == 0) {
            return EMPTY_ZS;
        }
        ECFieldElement[] eCFieldElementArr2 = new ECFieldElement[length];
        System.arraycopy(eCFieldElementArr, 0, eCFieldElementArr2, 0, length);
        return eCFieldElementArr2;
    }

    public int hashCode() {
        ECCurve curve = getCurve();
        int i2 = curve == null ? 0 : ~curve.hashCode();
        if (isInfinity()) {
            return i2;
        }
        ECPoint normalize = normalize();
        return (i2 ^ (normalize.getXCoord().hashCode() * 17)) ^ (normalize.getYCoord().hashCode() * 257);
    }

    public boolean implIsValid(final boolean z2, final boolean z3) {
        if (isInfinity()) {
            return true;
        }
        return !((ValidityPrecompInfo) getCurve().precompute(this, "bc_validity", new PreCompCallback() { // from class: org.bouncycastle.math.ec.ECPoint.1
            @Override // org.bouncycastle.math.ec.PreCompCallback
            public PreCompInfo precompute(PreCompInfo preCompInfo) {
                ValidityPrecompInfo validityPrecompInfo = preCompInfo instanceof ValidityPrecompInfo ? (ValidityPrecompInfo) preCompInfo : null;
                if (validityPrecompInfo == null) {
                    validityPrecompInfo = new ValidityPrecompInfo();
                }
                if (validityPrecompInfo.hasFailed()) {
                    return validityPrecompInfo;
                }
                if (!validityPrecompInfo.hasCurveEquationPassed()) {
                    if (!z2 && !ECPoint.this.satisfiesCurveEquation()) {
                        validityPrecompInfo.reportFailed();
                        return validityPrecompInfo;
                    }
                    validityPrecompInfo.reportCurveEquationPassed();
                }
                if (z3 && !validityPrecompInfo.hasOrderPassed()) {
                    if (!ECPoint.this.satisfiesOrder()) {
                        validityPrecompInfo.reportFailed();
                        return validityPrecompInfo;
                    }
                    validityPrecompInfo.reportOrderPassed();
                }
                return validityPrecompInfo;
            }
        })).hasFailed();
    }

    public boolean isInfinity() {
        if (this.f1419x != null && this.f1420y != null) {
            ECFieldElement[] eCFieldElementArr = this.zs;
            if (eCFieldElementArr.length <= 0 || !eCFieldElementArr[0].isZero()) {
                return false;
            }
        }
        return true;
    }

    public boolean isNormalized() {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        return curveCoordinateSystem == 0 || curveCoordinateSystem == 5 || isInfinity() || this.zs[0].isOne();
    }

    public boolean isValid() {
        return implIsValid(false, true);
    }

    public boolean isValidPartial() {
        return implIsValid(false, false);
    }

    public ECPoint multiply(BigInteger bigInteger) {
        return getCurve().getMultiplier().multiply(this, bigInteger);
    }

    public abstract ECPoint negate();

    public ECPoint normalize() {
        int curveCoordinateSystem;
        if (isInfinity() || (curveCoordinateSystem = getCurveCoordinateSystem()) == 0 || curveCoordinateSystem == 5) {
            return this;
        }
        ECFieldElement zCoord = getZCoord(0);
        if (zCoord.isOne()) {
            return this;
        }
        if (this.curve == null) {
            throw new IllegalStateException("Detached points must be in affine coordinates");
        }
        ECFieldElement randomFieldElementMult = this.curve.randomFieldElementMult(CryptoServicesRegistrar.getSecureRandom());
        return normalize(zCoord.multiply(randomFieldElementMult).invert().multiply(randomFieldElementMult));
    }

    public abstract boolean satisfiesCurveEquation();

    public boolean satisfiesOrder() {
        BigInteger order;
        return ECConstants.ONE.equals(this.curve.getCofactor()) || (order = this.curve.getOrder()) == null || ECAlgorithms.referenceMultiply(this, order).isInfinity();
    }

    public ECPoint scaleX(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord(), getRawZCoords());
    }

    public ECPoint scaleXNegateY(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().multiply(eCFieldElement), getRawYCoord().negate(), getRawZCoords());
    }

    public ECPoint scaleY(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public ECPoint scaleYNegateX(ECFieldElement eCFieldElement) {
        return isInfinity() ? this : getCurve().createRawPoint(getRawXCoord().negate(), getRawYCoord().multiply(eCFieldElement), getRawZCoords());
    }

    public abstract ECPoint subtract(ECPoint eCPoint);

    public ECPoint threeTimes() {
        return twicePlus(this);
    }

    public ECPoint timesPow2(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("'e' cannot be negative");
        }
        ECPoint eCPoint = this;
        while (true) {
            i2--;
            if (i2 < 0) {
                return eCPoint;
            }
            eCPoint = eCPoint.twice();
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
        for (int i2 = 0; i2 < this.zs.length; i2++) {
            stringBuffer.append(',');
            stringBuffer.append(this.zs[i2]);
        }
        stringBuffer.append(')');
        return stringBuffer.toString();
    }

    public abstract ECPoint twice();

    public ECPoint twicePlus(ECPoint eCPoint) {
        return twice().add(eCPoint);
    }

    public ECPoint(ECCurve eCCurve, ECFieldElement eCFieldElement, ECFieldElement eCFieldElement2, ECFieldElement[] eCFieldElementArr) {
        this.preCompTable = null;
        this.curve = eCCurve;
        this.f1419x = eCFieldElement;
        this.f1420y = eCFieldElement2;
        this.zs = eCFieldElementArr;
    }

    public boolean equals(ECPoint eCPoint) {
        ECPoint eCPoint2;
        if (eCPoint == null) {
            return false;
        }
        ECCurve curve = getCurve();
        ECCurve curve2 = eCPoint.getCurve();
        boolean z2 = curve == null;
        boolean z3 = curve2 == null;
        boolean isInfinity = isInfinity();
        boolean isInfinity2 = eCPoint.isInfinity();
        if (isInfinity || isInfinity2) {
            if (isInfinity && isInfinity2) {
                return z2 || z3 || curve.equals(curve2);
            }
            return false;
        }
        if (!z2 || !z3) {
            if (!z2) {
                if (z3) {
                    eCPoint2 = normalize();
                } else {
                    if (!curve.equals(curve2)) {
                        return false;
                    }
                    ECPoint[] eCPointArr = {this, curve.importPoint(eCPoint)};
                    curve.normalizeAll(eCPointArr);
                    eCPoint2 = eCPointArr[0];
                    eCPoint = eCPointArr[1];
                }
                return eCPoint2.getXCoord().equals(eCPoint.getXCoord()) && eCPoint2.getYCoord().equals(eCPoint.getYCoord());
            }
            eCPoint = eCPoint.normalize();
        }
        eCPoint2 = this;
        if (eCPoint2.getXCoord().equals(eCPoint.getXCoord())) {
            return false;
        }
    }

    public ECPoint normalize(ECFieldElement eCFieldElement) {
        int curveCoordinateSystem = getCurveCoordinateSystem();
        if (curveCoordinateSystem != 1) {
            if (curveCoordinateSystem == 2 || curveCoordinateSystem == 3 || curveCoordinateSystem == 4) {
                ECFieldElement square = eCFieldElement.square();
                return createScaledPoint(square, square.multiply(eCFieldElement));
            }
            if (curveCoordinateSystem != 6) {
                throw new IllegalStateException("not a projective coordinate system");
            }
        }
        return createScaledPoint(eCFieldElement, eCFieldElement);
    }
}
