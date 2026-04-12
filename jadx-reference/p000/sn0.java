package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class sn0 {
    public static final char RANDOM_IRREDUCIBLE_POLYNOMIAL = 'I';
    private int[] coefficients;
    private int degree;
    private z10 field;

    public sn0(z10 z10Var) {
        this.field = z10Var;
        this.degree = -1;
        this.coefficients = new int[1];
    }

    private static int computeDegree(int[] iArr) {
        int length = iArr.length - 1;
        while (length >= 0 && iArr[length] == 0) {
            length--;
        }
        return length;
    }

    private int[] createRandomIrreduciblePolynomial(int i, SecureRandom secureRandom) {
        int[] iArr = new int[i + 1];
        iArr[i] = 1;
        iArr[0] = this.field.getRandomNonZeroElement(secureRandom);
        for (int i2 = 1; i2 < i; i2++) {
            iArr[i2] = this.field.getRandomElement(secureRandom);
        }
        while (!isIrreducible(iArr)) {
            int iNextInt = zp0.nextInt(secureRandom, i);
            if (iNextInt == 0) {
                iArr[0] = this.field.getRandomNonZeroElement(secureRandom);
            } else {
                iArr[iNextInt] = this.field.getRandomElement(secureRandom);
            }
        }
        return iArr;
    }

    private static int headCoefficient(int[] iArr) {
        int iComputeDegree = computeDegree(iArr);
        if (iComputeDegree == -1) {
            return 0;
        }
        return iArr[iComputeDegree];
    }

    private static boolean isEqual(int[] iArr, int[] iArr2) {
        int iComputeDegree = computeDegree(iArr);
        if (iComputeDegree != computeDegree(iArr2)) {
            return false;
        }
        for (int i = 0; i <= iComputeDegree; i++) {
            if (iArr[i] != iArr2[i]) {
                return false;
            }
        }
        return true;
    }

    private boolean isIrreducible(int[] iArr) {
        if (iArr[0] == 0) {
            return false;
        }
        int iComputeDegree = computeDegree(iArr) >> 1;
        int[] iArrNormalForm = {0, 1};
        int[] iArr2 = {0, 1};
        int degree = this.field.getDegree();
        for (int i = 0; i < iComputeDegree; i++) {
            for (int i2 = degree - 1; i2 >= 0; i2--) {
                iArrNormalForm = modMultiply(iArrNormalForm, iArrNormalForm, iArr);
            }
            iArrNormalForm = normalForm(iArrNormalForm);
            if (computeDegree(gcd(add(iArrNormalForm, iArr2), iArr)) != 0) {
                return false;
            }
        }
        return true;
    }

    private static int[] normalForm(int[] iArr) {
        int iComputeDegree = computeDegree(iArr);
        if (iComputeDegree == -1) {
            return new int[1];
        }
        int i = iComputeDegree + 1;
        if (iArr.length == i) {
            return o60.clone(iArr);
        }
        int[] iArr2 = new int[i];
        System.arraycopy(iArr, 0, iArr2, 0, i);
        return iArr2;
    }

    public sn0 add(sn0 sn0Var) {
        return new sn0(this.field, add(this.coefficients, sn0Var.coefficients));
    }

    public sn0 addMonomial(int i) {
        int[] iArr = new int[i + 1];
        iArr[i] = 1;
        return new sn0(this.field, add(this.coefficients, iArr));
    }

    public void addToThis(sn0 sn0Var) {
        this.coefficients = add(this.coefficients, sn0Var.coefficients);
        computeDegree();
    }

    public sn0[] div(sn0 sn0Var) {
        int[][] iArrDiv = div(this.coefficients, sn0Var.coefficients);
        return new sn0[]{new sn0(this.field, iArrDiv[0]), new sn0(this.field, iArrDiv[1])};
    }

    public boolean equals(Object obj) {
        if (obj != null && (obj instanceof sn0)) {
            sn0 sn0Var = (sn0) obj;
            if (this.field.equals(sn0Var.field) && this.degree == sn0Var.degree && isEqual(this.coefficients, sn0Var.coefficients)) {
                return true;
            }
        }
        return false;
    }

    public int evaluateAt(int i) {
        int[] iArr = this.coefficients;
        int i2 = this.degree;
        int iMult = iArr[i2];
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            iMult = this.field.mult(iMult, i) ^ this.coefficients[i3];
        }
        return iMult;
    }

    public sn0 gcd(sn0 sn0Var) {
        return new sn0(this.field, gcd(this.coefficients, sn0Var.coefficients));
    }

    public int getCoefficient(int i) {
        if (i < 0 || i > this.degree) {
            return 0;
        }
        return this.coefficients[i];
    }

    public int getDegree() {
        int[] iArr = this.coefficients;
        int length = iArr.length - 1;
        if (iArr[length] == 0) {
            return -1;
        }
        return length;
    }

    public byte[] getEncoded() {
        int i = 8;
        int i2 = 1;
        while (this.field.getDegree() > i) {
            i2++;
            i += 8;
        }
        byte[] bArr = new byte[this.coefficients.length * i2];
        int i3 = 0;
        for (int i4 = 0; i4 < this.coefficients.length; i4++) {
            int i5 = 0;
            while (i5 < i) {
                bArr[i3] = (byte) (this.coefficients[i4] >>> i5);
                i5 += 8;
                i3++;
            }
        }
        return bArr;
    }

    public int getHeadCoefficient() {
        int i = this.degree;
        if (i == -1) {
            return 0;
        }
        return this.coefficients[i];
    }

    public int hashCode() {
        int iHashCode = this.field.hashCode();
        int i = 0;
        while (true) {
            int[] iArr = this.coefficients;
            if (i >= iArr.length) {
                return iHashCode;
            }
            iHashCode = (iHashCode * 31) + iArr[i];
            i++;
        }
    }

    public sn0 mod(sn0 sn0Var) {
        return new sn0(this.field, mod(this.coefficients, sn0Var.coefficients));
    }

    public sn0 modDiv(sn0 sn0Var, sn0 sn0Var2) {
        return new sn0(this.field, modDiv(this.coefficients, sn0Var.coefficients, sn0Var2.coefficients));
    }

    public sn0 modInverse(sn0 sn0Var) {
        return new sn0(this.field, modDiv(new int[]{1}, this.coefficients, sn0Var.coefficients));
    }

    public sn0 modMultiply(sn0 sn0Var, sn0 sn0Var2) {
        return new sn0(this.field, modMultiply(this.coefficients, sn0Var.coefficients, sn0Var2.coefficients));
    }

    public sn0[] modPolynomialToFracton(sn0 sn0Var) {
        int i = sn0Var.degree >> 1;
        int[] iArrNormalForm = normalForm(sn0Var.coefficients);
        int[] iArrMod = mod(this.coefficients, sn0Var.coefficients);
        int[] iArr = {0};
        int[] iArr2 = {1};
        while (computeDegree(iArrMod) > i) {
            int[][] iArrDiv = div(iArrNormalForm, iArrMod);
            int[] iArr3 = iArrDiv[1];
            int[] iArrAdd = add(iArr, modMultiply(iArrDiv[0], iArr2, sn0Var.coefficients));
            iArr = iArr2;
            iArr2 = iArrAdd;
            iArrNormalForm = iArrMod;
            iArrMod = iArr3;
        }
        return new sn0[]{new sn0(this.field, iArrMod), new sn0(this.field, iArr2)};
    }

    public sn0 modSquareMatrix(sn0[] sn0VarArr) {
        int length = sn0VarArr.length;
        int[] iArr = new int[length];
        int[] iArr2 = new int[length];
        int i = 0;
        while (true) {
            int[] iArr3 = this.coefficients;
            if (i >= iArr3.length) {
                break;
            }
            z10 z10Var = this.field;
            int i2 = iArr3[i];
            iArr2[i] = z10Var.mult(i2, i2);
            i++;
        }
        for (int i3 = 0; i3 < length; i3++) {
            for (int i4 = 0; i4 < length; i4++) {
                int[] iArr4 = sn0VarArr[i4].coefficients;
                if (i3 < iArr4.length) {
                    iArr[i3] = this.field.add(iArr[i3], this.field.mult(iArr4[i3], iArr2[i4]));
                }
            }
        }
        return new sn0(this.field, iArr);
    }

    public sn0 modSquareRoot(sn0 sn0Var) {
        int[] iArrClone = o60.clone(this.coefficients);
        int[] iArrModMultiply = modMultiply(iArrClone, iArrClone, sn0Var.coefficients);
        while (!isEqual(iArrModMultiply, this.coefficients)) {
            iArrClone = normalForm(iArrModMultiply);
            iArrModMultiply = modMultiply(iArrClone, iArrClone, sn0Var.coefficients);
        }
        return new sn0(this.field, iArrClone);
    }

    public sn0 modSquareRootMatrix(sn0[] sn0VarArr) {
        int length = sn0VarArr.length;
        int[] iArr = new int[length];
        for (int i = 0; i < length; i++) {
            for (int i2 = 0; i2 < length; i2++) {
                int[] iArr2 = sn0VarArr[i2].coefficients;
                if (i < iArr2.length) {
                    int[] iArr3 = this.coefficients;
                    if (i2 < iArr3.length) {
                        iArr[i] = this.field.add(iArr[i], this.field.mult(iArr2[i], iArr3[i2]));
                    }
                }
            }
        }
        for (int i3 = 0; i3 < length; i3++) {
            iArr[i3] = this.field.sqRoot(iArr[i3]);
        }
        return new sn0(this.field, iArr);
    }

    public void multThisWithElement(int i) {
        if (!this.field.isElementOfThisField(i)) {
            throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
        }
        this.coefficients = multWithElement(this.coefficients, i);
        computeDegree();
    }

    public sn0 multWithElement(int i) {
        if (!this.field.isElementOfThisField(i)) {
            throw new ArithmeticException("Not an element of the finite field this polynomial is defined over.");
        }
        return new sn0(this.field, multWithElement(this.coefficients, i));
    }

    public sn0 multWithMonomial(int i) {
        return new sn0(this.field, multWithMonomial(this.coefficients, i));
    }

    public sn0 multiply(sn0 sn0Var) {
        return new sn0(this.field, multiply(this.coefficients, sn0Var.coefficients));
    }

    public String toString() {
        String string = " Polynomial over " + this.field.toString() + ": \n";
        for (int i = 0; i < this.coefficients.length; i++) {
            StringBuilder sbM37b8 = AbstractC0003a2.m37b8(string);
            sbM37b8.append(this.field.elementToStr(this.coefficients[i]));
            sbM37b8.append("Y^");
            sbM37b8.append(i);
            sbM37b8.append("+");
            string = sbM37b8.toString();
        }
        return AbstractC0003a2.m32b3(string, ";");
    }

    public sn0(z10 z10Var, int i) {
        this.field = z10Var;
        this.degree = i;
        int[] iArr = new int[i + 1];
        this.coefficients = iArr;
        iArr[i] = 1;
    }

    private int[] add(int[] iArr, int[] iArr2) {
        int[] iArr3;
        if (iArr.length < iArr2.length) {
            iArr3 = new int[iArr2.length];
            System.arraycopy(iArr2, 0, iArr3, 0, iArr2.length);
        } else {
            iArr3 = new int[iArr.length];
            System.arraycopy(iArr, 0, iArr3, 0, iArr.length);
            iArr = iArr2;
        }
        for (int length = iArr.length - 1; length >= 0; length--) {
            iArr3[length] = this.field.add(iArr3[length], iArr[length]);
        }
        return iArr3;
    }

    private void computeDegree() {
        int length = this.coefficients.length;
        do {
            this.degree = length - 1;
            length = this.degree;
            if (length < 0) {
                return;
            }
        } while (this.coefficients[length] == 0);
    }

    private int[][] div(int[] iArr, int[] iArr2) {
        int iComputeDegree = computeDegree(iArr2);
        int iComputeDegree2 = computeDegree(iArr) + 1;
        if (iComputeDegree == -1) {
            throw new ArithmeticException("Division by zero.");
        }
        int[][] iArr3 = {new int[1], new int[iComputeDegree2]};
        int iInverse = this.field.inverse(headCoefficient(iArr2));
        iArr3[0][0] = 0;
        int[] iArr4 = iArr3[1];
        System.arraycopy(iArr, 0, iArr4, 0, iArr4.length);
        while (iComputeDegree <= computeDegree(iArr3[1])) {
            int[] iArr5 = {this.field.mult(headCoefficient(iArr3[1]), iInverse)};
            int[] iArrMultWithElement = multWithElement(iArr2, iArr5[0]);
            int iComputeDegree3 = computeDegree(iArr3[1]) - iComputeDegree;
            int[] iArrMultWithMonomial = multWithMonomial(iArrMultWithElement, iComputeDegree3);
            iArr3[0] = add(multWithMonomial(iArr5, iComputeDegree3), iArr3[0]);
            iArr3[1] = add(iArrMultWithMonomial, iArr3[1]);
        }
        return iArr3;
    }

    private int[] gcd(int[] iArr, int[] iArr2) {
        if (computeDegree(iArr) == -1) {
            return iArr2;
        }
        while (computeDegree(iArr2) != -1) {
            int[] iArrMod = mod(iArr, iArr2);
            int length = iArr2.length;
            int[] iArr3 = new int[length];
            System.arraycopy(iArr2, 0, iArr3, 0, length);
            int length2 = iArrMod.length;
            int[] iArr4 = new int[length2];
            System.arraycopy(iArrMod, 0, iArr4, 0, length2);
            iArr2 = iArr4;
            iArr = iArr3;
        }
        return multWithElement(iArr, this.field.inverse(headCoefficient(iArr)));
    }

    private int[] mod(int[] iArr, int[] iArr2) {
        int iComputeDegree = computeDegree(iArr2);
        if (iComputeDegree == -1) {
            throw new ArithmeticException("Division by zero");
        }
        int length = iArr.length;
        int[] iArrAdd = new int[length];
        int iInverse = this.field.inverse(headCoefficient(iArr2));
        System.arraycopy(iArr, 0, iArrAdd, 0, length);
        while (iComputeDegree <= computeDegree(iArrAdd)) {
            iArrAdd = add(multWithElement(multWithMonomial(iArr2, computeDegree(iArrAdd) - iComputeDegree), this.field.mult(headCoefficient(iArrAdd), iInverse)), iArrAdd);
        }
        return iArrAdd;
    }

    private int[] modDiv(int[] iArr, int[] iArr2, int[] iArr3) {
        int[] iArrNormalForm = normalForm(iArr3);
        int[] iArrMod = mod(iArr2, iArr3);
        int[] iArrNormalForm2 = {0};
        int[] iArrMod2 = mod(iArr, iArr3);
        while (computeDegree(iArrMod) != -1) {
            int[][] iArrDiv = div(iArrNormalForm, iArrMod);
            int[] iArrNormalForm3 = normalForm(iArrMod);
            int[] iArrNormalForm4 = normalForm(iArrDiv[1]);
            int[] iArrAdd = add(iArrNormalForm2, modMultiply(iArrDiv[0], iArrMod2, iArr3));
            iArrNormalForm2 = normalForm(iArrMod2);
            iArrMod2 = normalForm(iArrAdd);
            iArrNormalForm = iArrNormalForm3;
            iArrMod = iArrNormalForm4;
        }
        return multWithElement(iArrNormalForm2, this.field.inverse(headCoefficient(iArrNormalForm)));
    }

    private int[] modMultiply(int[] iArr, int[] iArr2, int[] iArr3) {
        return mod(multiply(iArr, iArr2), iArr3);
    }

    private int[] multWithElement(int[] iArr, int i) {
        int iComputeDegree = computeDegree(iArr);
        if (iComputeDegree == -1 || i == 0) {
            return new int[1];
        }
        if (i == 1) {
            return o60.clone(iArr);
        }
        int[] iArr2 = new int[iComputeDegree + 1];
        while (iComputeDegree >= 0) {
            iArr2[iComputeDegree] = this.field.mult(iArr[iComputeDegree], i);
            iComputeDegree--;
        }
        return iArr2;
    }

    private static int[] multWithMonomial(int[] iArr, int i) {
        int iComputeDegree = computeDegree(iArr);
        if (iComputeDegree == -1) {
            return new int[1];
        }
        int[] iArr2 = new int[iComputeDegree + i + 1];
        System.arraycopy(iArr, 0, iArr2, i, iComputeDegree + 1);
        return iArr2;
    }

    private int[] multiply(int[] iArr, int[] iArr2) {
        if (computeDegree(iArr) < computeDegree(iArr2)) {
            iArr2 = iArr;
            iArr = iArr2;
        }
        int[] iArrNormalForm = normalForm(iArr);
        int[] iArrNormalForm2 = normalForm(iArr2);
        if (iArrNormalForm2.length == 1) {
            return multWithElement(iArrNormalForm, iArrNormalForm2[0]);
        }
        int length = iArrNormalForm.length;
        int length2 = iArrNormalForm2.length;
        int[] iArr3 = new int[(length + length2) - 1];
        if (length2 != length) {
            int[] iArr4 = new int[length2];
            int i = length - length2;
            int[] iArr5 = new int[i];
            System.arraycopy(iArrNormalForm, 0, iArr4, 0, length2);
            System.arraycopy(iArrNormalForm, length2, iArr5, 0, i);
            return add(multiply(iArr4, iArrNormalForm2), multWithMonomial(multiply(iArr5, iArrNormalForm2), length2));
        }
        int i2 = (length + 1) >>> 1;
        int i3 = length - i2;
        int[] iArr6 = new int[i2];
        int[] iArr7 = new int[i2];
        int[] iArr8 = new int[i3];
        int[] iArr9 = new int[i3];
        System.arraycopy(iArrNormalForm, 0, iArr6, 0, i2);
        System.arraycopy(iArrNormalForm, i2, iArr8, 0, i3);
        System.arraycopy(iArrNormalForm2, 0, iArr7, 0, i2);
        System.arraycopy(iArrNormalForm2, i2, iArr9, 0, i3);
        int[] iArrAdd = add(iArr6, iArr8);
        int[] iArrAdd2 = add(iArr7, iArr9);
        int[] iArrMultiply = multiply(iArr6, iArr7);
        int[] iArrMultiply2 = multiply(iArrAdd, iArrAdd2);
        int[] iArrMultiply3 = multiply(iArr8, iArr9);
        return add(multWithMonomial(add(add(add(iArrMultiply2, iArrMultiply), iArrMultiply3), multWithMonomial(iArrMultiply3, i2)), i2), iArrMultiply);
    }

    public sn0(z10 z10Var, int i, char c, SecureRandom secureRandom) {
        this.field = z10Var;
        if (c == 'I') {
            this.coefficients = createRandomIrreduciblePolynomial(i, secureRandom);
            computeDegree();
        } else {
            throw new IllegalArgumentException(" Error: type " + c + " is not defined for GF2smallmPolynomial");
        }
    }

    public sn0(z10 z10Var, byte[] bArr) {
        this.field = z10Var;
        int i = 8;
        int i2 = 1;
        while (z10Var.getDegree() > i) {
            i2++;
            i += 8;
        }
        if (bArr.length % i2 != 0) {
            throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
        }
        this.coefficients = new int[bArr.length / i2];
        int i3 = 0;
        int i4 = 0;
        while (true) {
            int[] iArr = this.coefficients;
            if (i3 >= iArr.length) {
                if (iArr.length != 1 && iArr[iArr.length - 1] == 0) {
                    throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
                }
                computeDegree();
                return;
            }
            int i5 = 0;
            while (i5 < i) {
                int[] iArr2 = this.coefficients;
                iArr2[i3] = ((bArr[i4] & 255) << i5) ^ iArr2[i3];
                i5 += 8;
                i4++;
            }
            if (!this.field.isElementOfThisField(this.coefficients[i3])) {
                throw new IllegalArgumentException(" Error: byte array is not encoded polynomial over given finite field GF2m");
            }
            i3++;
        }
    }

    public sn0(z10 z10Var, int[] iArr) {
        this.field = z10Var;
        this.coefficients = normalForm(iArr);
        computeDegree();
    }

    public sn0(a20 a20Var) {
        this(a20Var.getField(), a20Var.getIntArrayForm());
    }

    public sn0(sn0 sn0Var) {
        this.field = sn0Var.field;
        this.degree = sn0Var.degree;
        this.coefficients = o60.clone(sn0Var.coefficients);
    }
}
