package org.bouncycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.security.SecureRandom;

/* loaded from: classes.dex */
public final class GoppaCode {

    public static class MaMaPe {

        /* renamed from: h */
        private GF2Matrix f1627h;

        /* renamed from: p */
        private Permutation f1628p;

        /* renamed from: s */
        private GF2Matrix f1629s;

        public MaMaPe(GF2Matrix gF2Matrix, GF2Matrix gF2Matrix2, Permutation permutation) {
            this.f1629s = gF2Matrix;
            this.f1627h = gF2Matrix2;
            this.f1628p = permutation;
        }

        public GF2Matrix getFirstMatrix() {
            return this.f1629s;
        }

        public Permutation getPermutation() {
            return this.f1628p;
        }

        public GF2Matrix getSecondMatrix() {
            return this.f1627h;
        }
    }

    public static class MatrixSet {

        /* renamed from: g */
        private GF2Matrix f1630g;
        private int[] setJ;

        public MatrixSet(GF2Matrix gF2Matrix, int[] iArr) {
            this.f1630g = gF2Matrix;
            this.setJ = iArr;
        }

        public GF2Matrix getG() {
            return this.f1630g;
        }

        public int[] getSetJ() {
            return this.setJ;
        }
    }

    private GoppaCode() {
    }

    public static MaMaPe computeSystematicForm(GF2Matrix gF2Matrix, SecureRandom secureRandom) {
        GF2Matrix gF2Matrix2;
        boolean z2;
        int numColumns = gF2Matrix.getNumColumns();
        GF2Matrix gF2Matrix3 = null;
        while (true) {
            Permutation permutation = new Permutation(numColumns, secureRandom);
            GF2Matrix gF2Matrix4 = (GF2Matrix) gF2Matrix.rightMultiply(permutation);
            GF2Matrix leftSubMatrix = gF2Matrix4.getLeftSubMatrix();
            try {
                gF2Matrix2 = (GF2Matrix) leftSubMatrix.computeInverse();
                z2 = true;
            } catch (ArithmeticException unused) {
                gF2Matrix2 = gF2Matrix3;
                z2 = false;
            }
            if (z2) {
                return new MaMaPe(leftSubMatrix, ((GF2Matrix) gF2Matrix2.rightMultiply(gF2Matrix4)).getRightSubMatrix(), permutation);
            }
            gF2Matrix3 = gF2Matrix2;
        }
    }

    public static GF2Matrix createCanonicalCheckMatrix(GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM) {
        int degree = gF2mField.getDegree();
        int i2 = 1 << degree;
        int degree2 = polynomialGF2mSmallM.getDegree();
        int i3 = 0;
        Class cls = Integer.TYPE;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) cls, degree2, i2);
        int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) cls, degree2, i2);
        for (int i4 = 0; i4 < i2; i4++) {
            iArr2[0][i4] = gF2mField.inverse(polynomialGF2mSmallM.evaluateAt(i4));
        }
        for (int i5 = 1; i5 < degree2; i5++) {
            for (int i6 = 0; i6 < i2; i6++) {
                iArr2[i5][i6] = gF2mField.mult(iArr2[i5 - 1][i6], i6);
            }
        }
        int i7 = 0;
        while (i7 < degree2) {
            int i8 = i3;
            while (i8 < i2) {
                for (int i9 = i3; i9 <= i7; i9++) {
                    int[] iArr3 = iArr[i7];
                    iArr3[i8] = gF2mField.add(iArr3[i8], gF2mField.mult(iArr2[i9][i8], polynomialGF2mSmallM.getCoefficient((degree2 + i9) - i7)));
                }
                i8++;
                i3 = 0;
            }
            i7++;
            i3 = 0;
        }
        int[][] iArr4 = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, degree2 * degree, (i2 + 31) >>> 5);
        for (int i10 = 0; i10 < i2; i10++) {
            int i11 = i10 >>> 5;
            int i12 = 1 << (i10 & 31);
            for (int i13 = 0; i13 < degree2; i13++) {
                int i14 = iArr[i13][i10];
                for (int i15 = 0; i15 < degree; i15++) {
                    if (((i14 >>> i15) & 1) != 0) {
                        int[] iArr5 = iArr4[(((i13 + 1) * degree) - i15) - 1];
                        iArr5[i11] = iArr5[i11] ^ i12;
                    }
                }
            }
        }
        return new GF2Matrix(i2, iArr4);
    }

    public static GF2Vector syndromeDecode(GF2Vector gF2Vector, GF2mField gF2mField, PolynomialGF2mSmallM polynomialGF2mSmallM, PolynomialGF2mSmallM[] polynomialGF2mSmallMArr) {
        int degree = 1 << gF2mField.getDegree();
        GF2Vector gF2Vector2 = new GF2Vector(degree);
        if (!gF2Vector.isZero()) {
            PolynomialGF2mSmallM[] modPolynomialToFracton = new PolynomialGF2mSmallM(gF2Vector.toExtensionFieldVector(gF2mField)).modInverse(polynomialGF2mSmallM).addMonomial(1).modSquareRootMatrix(polynomialGF2mSmallMArr).modPolynomialToFracton(polynomialGF2mSmallM);
            PolynomialGF2mSmallM polynomialGF2mSmallM2 = modPolynomialToFracton[0];
            PolynomialGF2mSmallM multiply = polynomialGF2mSmallM2.multiply(polynomialGF2mSmallM2);
            PolynomialGF2mSmallM polynomialGF2mSmallM3 = modPolynomialToFracton[1];
            PolynomialGF2mSmallM add = multiply.add(polynomialGF2mSmallM3.multiply(polynomialGF2mSmallM3).multWithMonomial(1));
            PolynomialGF2mSmallM multWithElement = add.multWithElement(gF2mField.inverse(add.getHeadCoefficient()));
            for (int i2 = 0; i2 < degree; i2++) {
                if (multWithElement.evaluateAt(i2) == 0) {
                    gF2Vector2.setBit(i2);
                }
            }
        }
        return gF2Vector2;
    }
}
