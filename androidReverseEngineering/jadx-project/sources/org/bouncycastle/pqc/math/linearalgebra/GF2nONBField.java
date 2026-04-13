package org.bouncycastle.pqc.math.linearalgebra;

import java.lang.reflect.Array;
import java.security.SecureRandom;
import java.util.Random;

/* loaded from: classes.dex */
public class GF2nONBField extends GF2nField {
    private static final int MAXLONG = 64;
    private int mBit;
    private int mLength;
    int[][] mMult;
    private int mType;

    public GF2nONBField(int i2, SecureRandom secureRandom) {
        super(secureRandom);
        if (i2 < 3) {
            throw new IllegalArgumentException("k must be at least 3");
        }
        this.mDegree = i2;
        int i3 = i2 / 64;
        this.mLength = i3;
        int i4 = i2 & 63;
        this.mBit = i4;
        if (i4 == 0) {
            this.mBit = 64;
        } else {
            this.mLength = i3 + 1;
        }
        computeType();
        if (this.mType >= 3) {
            throw new RuntimeException("\nThe type of this field is " + this.mType);
        }
        this.mMult = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, this.mDegree, 2);
        for (int i5 = 0; i5 < this.mDegree; i5++) {
            int[] iArr = this.mMult[i5];
            iArr[0] = -1;
            iArr[1] = -1;
        }
        computeMultMatrix();
        computeFieldPolynomial();
        this.fields = new java.util.Vector();
        this.matrices = new java.util.Vector();
    }

    private void computeMultMatrix() {
        int i2;
        int i3 = this.mType;
        if ((i3 & 7) == 0) {
            throw new RuntimeException("bisher nur fuer Gausssche Normalbasen implementiert");
        }
        int i4 = (this.mDegree * i3) + 1;
        int[] iArr = new int[i4];
        int elementOfOrder = i3 == 1 ? 1 : i3 == 2 ? i4 - 1 : elementOfOrder(i3, i4);
        int i5 = 1;
        int i6 = 0;
        while (true) {
            i2 = this.mType;
            if (i6 >= i2) {
                break;
            }
            int i7 = i5;
            for (int i8 = 0; i8 < this.mDegree; i8++) {
                iArr[i7] = i8;
                i7 = (i7 << 1) % i4;
                if (i7 < 0) {
                    i7 += i4;
                }
            }
            i5 = (i5 * elementOfOrder) % i4;
            if (i5 < 0) {
                i5 += i4;
            }
            i6++;
        }
        if (i2 != 1) {
            if (i2 != 2) {
                throw new RuntimeException("only type 1 or type 2 implemented");
            }
            int i9 = 1;
            while (i9 < i4 - 1) {
                int i10 = i9 + 1;
                int[] iArr2 = this.mMult[iArr[i10]];
                int i11 = i4 - i9;
                if (iArr2[0] == -1) {
                    iArr2[0] = iArr[i11];
                } else {
                    iArr2[1] = iArr[i11];
                }
                i9 = i10;
            }
            return;
        }
        int i12 = 1;
        while (i12 < i4 - 1) {
            int i13 = i12 + 1;
            int[] iArr3 = this.mMult[iArr[i13]];
            int i14 = i4 - i12;
            if (iArr3[0] == -1) {
                iArr3[0] = iArr[i14];
            } else {
                iArr3[1] = iArr[i14];
            }
            i12 = i13;
        }
        int i15 = this.mDegree >> 1;
        for (int i16 = 1; i16 <= i15; i16++) {
            int[][] iArr4 = this.mMult;
            int i17 = i16 - 1;
            int[] iArr5 = iArr4[i17];
            if (iArr5[0] == -1) {
                iArr5[0] = (i15 + i16) - 1;
            } else {
                iArr5[1] = (i15 + i16) - 1;
            }
            int[] iArr6 = iArr4[(i15 + i16) - 1];
            if (iArr6[0] == -1) {
                iArr6[0] = i17;
            } else {
                iArr6[1] = i17;
            }
        }
    }

    private void computeType() {
        if ((this.mDegree & 7) == 0) {
            throw new RuntimeException("The extension degree is divisible by 8!");
        }
        this.mType = 1;
        int i2 = 0;
        while (i2 != 1) {
            int i3 = (this.mType * this.mDegree) + 1;
            if (IntegerFunctions.isPrime(i3)) {
                int order = IntegerFunctions.order(2, i3);
                int i4 = this.mType;
                int i5 = this.mDegree;
                i2 = IntegerFunctions.gcd((i4 * i5) / order, i5);
            }
            this.mType++;
        }
        int i6 = this.mType - 1;
        this.mType = i6;
        if (i6 == 1) {
            int i7 = (this.mDegree << 1) + 1;
            if (IntegerFunctions.isPrime(i7)) {
                int order2 = IntegerFunctions.order(2, i7);
                int i8 = this.mDegree;
                if (IntegerFunctions.gcd((i8 << 1) / order2, i8) == 1) {
                    this.mType++;
                }
            }
        }
    }

    private int elementOfOrder(int i2, int i3) {
        int order;
        Random random = new Random();
        int i4 = 0;
        while (i4 == 0) {
            int i5 = i3 - 1;
            i4 = random.nextInt() % i5;
            if (i4 < 0) {
                i4 += i5;
            }
        }
        while (true) {
            order = IntegerFunctions.order(i4, i3);
            if (order % i2 == 0 && order != 0) {
                break;
            }
            while (i4 == 0) {
                int i6 = i3 - 1;
                i4 = random.nextInt() % i6;
                if (i4 < 0) {
                    i4 += i6;
                }
            }
        }
        int i7 = i4;
        for (int i8 = 2; i8 <= i2 / order; i8++) {
            i7 *= i4;
        }
        return i7;
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.GF2nField
    public void computeCOBMatrix(GF2nField gF2nField) {
        GF2nElement randomRoot;
        int i2 = this.mDegree;
        if (i2 != gF2nField.mDegree) {
            throw new IllegalArgumentException("GF2nField.computeCOBMatrix: B1 has a different degree and thus cannot be coverted to!");
        }
        GF2Polynomial[] gF2PolynomialArr = new GF2Polynomial[i2];
        for (int i3 = 0; i3 < this.mDegree; i3++) {
            gF2PolynomialArr[i3] = new GF2Polynomial(this.mDegree);
        }
        do {
            randomRoot = gF2nField.getRandomRoot(this.fieldPolynomial);
        } while (randomRoot.isZero());
        GF2nElement[] gF2nElementArr = new GF2nPolynomialElement[this.mDegree];
        gF2nElementArr[0] = (GF2nElement) randomRoot.clone();
        for (int i4 = 1; i4 < this.mDegree; i4++) {
            gF2nElementArr[i4] = gF2nElementArr[i4 - 1].square();
        }
        for (int i5 = 0; i5 < this.mDegree; i5++) {
            for (int i6 = 0; i6 < this.mDegree; i6++) {
                if (gF2nElementArr[i5].testBit(i6)) {
                    int i7 = this.mDegree;
                    gF2PolynomialArr[(i7 - i6) - 1].setBit((i7 - i5) - 1);
                }
            }
        }
        this.fields.addElement(gF2nField);
        this.matrices.addElement(gF2PolynomialArr);
        gF2nField.fields.addElement(this);
        gF2nField.matrices.addElement(invertMatrix(gF2PolynomialArr));
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.GF2nField
    public void computeFieldPolynomial() {
        GF2Polynomial gF2Polynomial;
        int i2 = this.mType;
        if (i2 == 1) {
            gF2Polynomial = new GF2Polynomial(this.mDegree + 1, "ALL");
        } else {
            if (i2 != 2) {
                return;
            }
            GF2Polynomial gF2Polynomial2 = new GF2Polynomial(this.mDegree + 1, "ONE");
            GF2Polynomial gF2Polynomial3 = new GF2Polynomial(this.mDegree + 1, "X");
            gF2Polynomial3.addToThis(gF2Polynomial2);
            GF2Polynomial gF2Polynomial4 = gF2Polynomial2;
            gF2Polynomial = gF2Polynomial3;
            int i3 = 1;
            while (i3 < this.mDegree) {
                GF2Polynomial shiftLeft = gF2Polynomial.shiftLeft();
                shiftLeft.addToThis(gF2Polynomial4);
                i3++;
                gF2Polynomial4 = gF2Polynomial;
                gF2Polynomial = shiftLeft;
            }
        }
        this.fieldPolynomial = gF2Polynomial;
    }

    public int getONBBit() {
        return this.mBit;
    }

    public int getONBLength() {
        return this.mLength;
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.GF2nField
    public GF2nElement getRandomRoot(GF2Polynomial gF2Polynomial) {
        GF2nPolynomial gcd;
        int degree;
        int degree2;
        GF2nPolynomial gF2nPolynomial = new GF2nPolynomial(gF2Polynomial, this);
        while (gF2nPolynomial.getDegree() > 1) {
            while (true) {
                GF2nONBElement gF2nONBElement = new GF2nONBElement(this, this.random);
                GF2nPolynomial gF2nPolynomial2 = new GF2nPolynomial(2, GF2nONBElement.ZERO(this));
                gF2nPolynomial2.set(1, gF2nONBElement);
                GF2nPolynomial gF2nPolynomial3 = new GF2nPolynomial(gF2nPolynomial2);
                for (int i2 = 1; i2 <= this.mDegree - 1; i2++) {
                    gF2nPolynomial3 = gF2nPolynomial3.multiplyAndReduce(gF2nPolynomial3, gF2nPolynomial).add(gF2nPolynomial2);
                }
                gcd = gF2nPolynomial3.gcd(gF2nPolynomial);
                degree = gcd.getDegree();
                degree2 = gF2nPolynomial.getDegree();
                if (degree != 0 && degree != degree2) {
                    break;
                }
            }
            gF2nPolynomial = (degree << 1) > degree2 ? gF2nPolynomial.quotient(gcd) : new GF2nPolynomial(gcd);
        }
        return gF2nPolynomial.at(0);
    }

    public int[][] invMatrix(int[][] iArr) {
        int i2 = this.mDegree;
        int[] iArr2 = {i2, i2};
        Class cls = Integer.TYPE;
        int i3 = this.mDegree;
        int[][] iArr3 = (int[][]) Array.newInstance((Class<?>) cls, i3, i3);
        for (int i4 = 0; i4 < this.mDegree; i4++) {
            iArr3[i4][i4] = 1;
        }
        for (int i5 = 0; i5 < this.mDegree; i5++) {
            int i6 = i5;
            while (true) {
                int i7 = this.mDegree;
                if (i6 < i7) {
                    iArr[(i7 - 1) - i5][i6] = iArr[i5][i5];
                    i6++;
                }
            }
        }
        return null;
    }
}
