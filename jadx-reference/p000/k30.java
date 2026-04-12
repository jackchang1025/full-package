package p000;

import java.lang.reflect.Array;
import java.security.SecureRandom;

/* loaded from: classes2.dex */
public final class k30 {

    /* renamed from: k30$a0 */
    public static class C0744a0 {

        /* renamed from: h */
        private w10 f57432h;

        /* renamed from: p */
        private kn0 f57433p;

        /* renamed from: s */
        private w10 f57434s;

        public C0744a0(w10 w10Var, w10 w10Var2, kn0 kn0Var) {
            this.f57434s = w10Var;
            this.f57432h = w10Var2;
            this.f57433p = kn0Var;
        }

        public w10 getFirstMatrix() {
            return this.f57434s;
        }

        public kn0 getPermutation() {
            return this.f57433p;
        }

        public w10 getSecondMatrix() {
            return this.f57432h;
        }
    }

    private k30() {
    }

    public static C0744a0 computeSystematicForm(w10 w10Var, SecureRandom secureRandom) {
        w10 w10Var2;
        boolean z;
        int numColumns = w10Var.getNumColumns();
        w10 w10Var3 = null;
        while (true) {
            kn0 kn0Var = new kn0(numColumns, secureRandom);
            w10 w10Var4 = (w10) w10Var.rightMultiply(kn0Var);
            w10 leftSubMatrix = w10Var4.getLeftSubMatrix();
            try {
                w10Var2 = (w10) leftSubMatrix.computeInverse();
                z = true;
            } catch (ArithmeticException unused) {
                w10Var2 = w10Var3;
                z = false;
            }
            if (z) {
                return new C0744a0(leftSubMatrix, ((w10) w10Var2.rightMultiply(w10Var4)).getRightSubMatrix(), kn0Var);
            }
            w10Var3 = w10Var2;
        }
    }

    public static w10 createCanonicalCheckMatrix(z10 z10Var, sn0 sn0Var) {
        int degree = z10Var.getDegree();
        int i = 1;
        int i2 = 1 << degree;
        int degree2 = sn0Var.getDegree();
        int i3 = 0;
        Class cls = Integer.TYPE;
        int[][] iArr = (int[][]) Array.newInstance((Class<?>) cls, degree2, i2);
        int[][] iArr2 = (int[][]) Array.newInstance((Class<?>) cls, degree2, i2);
        for (int i4 = 0; i4 < i2; i4++) {
            iArr2[0][i4] = z10Var.inverse(sn0Var.evaluateAt(i4));
        }
        for (int i5 = 1; i5 < degree2; i5++) {
            for (int i6 = 0; i6 < i2; i6++) {
                iArr2[i5][i6] = z10Var.mult(iArr2[i5 - 1][i6], i6);
            }
        }
        for (int i7 = 0; i7 < degree2; i7++) {
            for (int i8 = i3; i8 < i2; i8++) {
                int i9 = i3;
                while (i9 <= i7) {
                    int[] iArr3 = iArr[i7];
                    iArr3[i8] = z10Var.add(iArr3[i8], z10Var.mult(iArr2[i9][i8], sn0Var.getCoefficient((degree2 + i9) - i7)));
                    i9++;
                    i = i;
                    i3 = i3;
                }
            }
        }
        int i10 = i;
        int i11 = i3;
        int[] iArr4 = new int[2];
        iArr4[i10] = (i2 + 31) >>> 5;
        iArr4[i11] = degree2 * degree;
        int[][] iArr5 = (int[][]) Array.newInstance((Class<?>) cls, iArr4);
        for (int i12 = i11; i12 < i2; i12++) {
            int i13 = i12 >>> 5;
            int i14 = i10 << (i12 & 31);
            for (int i15 = i11; i15 < degree2; i15++) {
                int i16 = iArr[i15][i12];
                for (int i17 = i11; i17 < degree; i17++) {
                    if (((i16 >>> i17) & 1) != 0) {
                        int[] iArr6 = iArr5[(((i15 + 1) * degree) - i17) - 1];
                        iArr6[i13] = iArr6[i13] ^ i14;
                    }
                }
            }
        }
        return new w10(i2, iArr5);
    }

    public static y10 syndromeDecode(y10 y10Var, z10 z10Var, sn0 sn0Var, sn0[] sn0VarArr) {
        int degree = 1 << z10Var.getDegree();
        y10 y10Var2 = new y10(degree);
        if (!y10Var.isZero()) {
            sn0[] sn0VarArrModPolynomialToFracton = new sn0(y10Var.toExtensionFieldVector(z10Var)).modInverse(sn0Var).addMonomial(1).modSquareRootMatrix(sn0VarArr).modPolynomialToFracton(sn0Var);
            sn0 sn0Var2 = sn0VarArrModPolynomialToFracton[0];
            sn0 sn0VarMultiply = sn0Var2.multiply(sn0Var2);
            sn0 sn0Var3 = sn0VarArrModPolynomialToFracton[1];
            sn0 sn0VarAdd = sn0VarMultiply.add(sn0Var3.multiply(sn0Var3).multWithMonomial(1));
            sn0 sn0VarMultWithElement = sn0VarAdd.multWithElement(z10Var.inverse(sn0VarAdd.getHeadCoefficient()));
            for (int i = 0; i < degree; i++) {
                if (sn0VarMultWithElement.evaluateAt(i) == 0) {
                    y10Var2.setBit(i);
                }
            }
        }
        return y10Var2;
    }
}
