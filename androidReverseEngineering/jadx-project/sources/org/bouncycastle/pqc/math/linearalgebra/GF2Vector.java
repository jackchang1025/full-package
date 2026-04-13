package org.bouncycastle.pqc.math.linearalgebra;

import java.security.SecureRandom;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class GF2Vector extends Vector {

    /* renamed from: v */
    private int[] f1626v;

    public GF2Vector(int i2) {
        if (i2 < 0) {
            throw new ArithmeticException("Negative length.");
        }
        this.length = i2;
        this.f1626v = new int[(i2 + 31) >> 5];
    }

    public static GF2Vector OS2VP(int i2, byte[] bArr) {
        if (i2 < 0) {
            throw new ArithmeticException("negative length");
        }
        if (bArr.length <= ((i2 + 7) >> 3)) {
            return new GF2Vector(i2, LittleEndianConversions.toIntArray(bArr));
        }
        throw new ArithmeticException("length mismatch");
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public Vector add(Vector vector) {
        if (!(vector instanceof GF2Vector)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        }
        GF2Vector gF2Vector = (GF2Vector) vector;
        if (this.length != gF2Vector.length) {
            throw new ArithmeticException("length mismatch");
        }
        int[] clone = IntUtils.clone(gF2Vector.f1626v);
        for (int length = clone.length - 1; length >= 0; length--) {
            clone[length] = clone[length] ^ this.f1626v[length];
        }
        return new GF2Vector(this.length, clone);
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public boolean equals(Object obj) {
        if (!(obj instanceof GF2Vector)) {
            return false;
        }
        GF2Vector gF2Vector = (GF2Vector) obj;
        return this.length == gF2Vector.length && IntUtils.equals(this.f1626v, gF2Vector.f1626v);
    }

    public GF2Vector extractLeftVector(int i2) {
        int i3 = this.length;
        if (i2 > i3) {
            throw new ArithmeticException("invalid length");
        }
        if (i2 == i3) {
            return new GF2Vector(this);
        }
        GF2Vector gF2Vector = new GF2Vector(i2);
        int i4 = i2 >> 5;
        int i5 = i2 & 31;
        System.arraycopy(this.f1626v, 0, gF2Vector.f1626v, 0, i4);
        if (i5 != 0) {
            gF2Vector.f1626v[i4] = ((1 << i5) - 1) & this.f1626v[i4];
        }
        return gF2Vector;
    }

    public GF2Vector extractRightVector(int i2) {
        int i3;
        int i4 = this.length;
        if (i2 > i4) {
            throw new ArithmeticException("invalid length");
        }
        if (i2 == i4) {
            return new GF2Vector(this);
        }
        GF2Vector gF2Vector = new GF2Vector(i2);
        int i5 = this.length;
        int i6 = (i5 - i2) >> 5;
        int i7 = (i5 - i2) & 31;
        int i8 = (i2 + 31) >> 5;
        int i9 = 0;
        if (i7 != 0) {
            while (true) {
                i3 = i8 - 1;
                if (i9 >= i3) {
                    break;
                }
                int[] iArr = gF2Vector.f1626v;
                int[] iArr2 = this.f1626v;
                int i10 = i6 + 1;
                iArr[i9] = (iArr2[i6] >>> i7) | (iArr2[i10] << (32 - i7));
                i9++;
                i6 = i10;
            }
            int[] iArr3 = gF2Vector.f1626v;
            int[] iArr4 = this.f1626v;
            int i11 = i6 + 1;
            int i12 = iArr4[i6] >>> i7;
            iArr3[i3] = i12;
            if (i11 < iArr4.length) {
                iArr3[i3] = (iArr4[i11] << (32 - i7)) | i12;
            }
        } else {
            System.arraycopy(this.f1626v, i6, gF2Vector.f1626v, 0, i8);
        }
        return gF2Vector;
    }

    public GF2Vector extractVector(int[] iArr) {
        int length = iArr.length;
        if (iArr[length - 1] > this.length) {
            throw new ArithmeticException("invalid index set");
        }
        GF2Vector gF2Vector = new GF2Vector(length);
        for (int i2 = 0; i2 < length; i2++) {
            int[] iArr2 = this.f1626v;
            int i3 = iArr[i2];
            if ((iArr2[i3 >> 5] & (1 << (i3 & 31))) != 0) {
                int[] iArr3 = gF2Vector.f1626v;
                int i4 = i2 >> 5;
                iArr3[i4] = (1 << (i2 & 31)) | iArr3[i4];
            }
        }
        return gF2Vector;
    }

    public int getBit(int i2) {
        if (i2 >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int i3 = i2 >> 5;
        int i4 = i2 & 31;
        return (this.f1626v[i3] & (1 << i4)) >>> i4;
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public byte[] getEncoded() {
        return LittleEndianConversions.toByteArray(this.f1626v, (this.length + 7) >> 3);
    }

    public int getHammingWeight() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            int[] iArr = this.f1626v;
            if (i2 >= iArr.length) {
                return i3;
            }
            int i4 = iArr[i2];
            for (int i5 = 0; i5 < 32; i5++) {
                if ((i4 & 1) != 0) {
                    i3++;
                }
                i4 >>>= 1;
            }
            i2++;
        }
    }

    public int[] getVecArray() {
        return this.f1626v;
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public int hashCode() {
        return Arrays.hashCode(this.f1626v) + (this.length * 31);
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public boolean isZero() {
        for (int length = this.f1626v.length - 1; length >= 0; length--) {
            if (this.f1626v[length] != 0) {
                return false;
            }
        }
        return true;
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public Vector multiply(Permutation permutation) {
        int[] vector = permutation.getVector();
        int i2 = this.length;
        if (i2 != vector.length) {
            throw new ArithmeticException("length mismatch");
        }
        GF2Vector gF2Vector = new GF2Vector(i2);
        for (int i3 = 0; i3 < vector.length; i3++) {
            int[] iArr = this.f1626v;
            int i4 = vector[i3];
            if ((iArr[i4 >> 5] & (1 << (i4 & 31))) != 0) {
                int[] iArr2 = gF2Vector.f1626v;
                int i5 = i3 >> 5;
                iArr2[i5] = (1 << (i3 & 31)) | iArr2[i5];
            }
        }
        return gF2Vector;
    }

    public void setBit(int i2) {
        if (i2 >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int[] iArr = this.f1626v;
        int i3 = i2 >> 5;
        iArr[i3] = (1 << (i2 & 31)) | iArr[i3];
    }

    public GF2mVector toExtensionFieldVector(GF2mField gF2mField) {
        int degree = gF2mField.getDegree();
        int i2 = this.length;
        if (i2 % degree != 0) {
            throw new ArithmeticException("conversion is impossible");
        }
        int i3 = i2 / degree;
        int[] iArr = new int[i3];
        int i4 = 0;
        for (int i5 = i3 - 1; i5 >= 0; i5--) {
            for (int degree2 = gF2mField.getDegree() - 1; degree2 >= 0; degree2--) {
                if (((this.f1626v[i4 >>> 5] >>> (i4 & 31)) & 1) == 1) {
                    iArr[i5] = iArr[i5] ^ (1 << degree2);
                }
                i4++;
            }
        }
        return new GF2mVector(gF2mField, iArr);
    }

    @Override // org.bouncycastle.pqc.math.linearalgebra.Vector
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i2 = 0; i2 < this.length; i2++) {
            if (i2 != 0 && (i2 & 31) == 0) {
                stringBuffer.append(' ');
            }
            stringBuffer.append((this.f1626v[i2 >> 5] & (1 << (i2 & 31))) == 0 ? '0' : '1');
        }
        return stringBuffer.toString();
    }

    public GF2Vector(int i2, int i3, SecureRandom secureRandom) {
        if (i3 > i2) {
            throw new ArithmeticException("The hamming weight is greater than the length of vector.");
        }
        this.length = i2;
        this.f1626v = new int[(i2 + 31) >> 5];
        int[] iArr = new int[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            iArr[i4] = i4;
        }
        for (int i5 = 0; i5 < i3; i5++) {
            int nextInt = RandUtils.nextInt(secureRandom, i2);
            setBit(iArr[nextInt]);
            i2--;
            iArr[nextInt] = iArr[i2];
        }
    }

    public GF2Vector(int i2, SecureRandom secureRandom) {
        this.length = i2;
        int i3 = (i2 + 31) >> 5;
        this.f1626v = new int[i3];
        int i4 = i3 - 1;
        for (int i5 = i4; i5 >= 0; i5--) {
            this.f1626v[i5] = secureRandom.nextInt();
        }
        int i6 = i2 & 31;
        if (i6 != 0) {
            int[] iArr = this.f1626v;
            iArr[i4] = ((1 << i6) - 1) & iArr[i4];
        }
    }

    public GF2Vector(int i2, int[] iArr) {
        if (i2 < 0) {
            throw new ArithmeticException("negative length");
        }
        this.length = i2;
        int i3 = (i2 + 31) >> 5;
        if (iArr.length != i3) {
            throw new ArithmeticException("length mismatch");
        }
        int[] clone = IntUtils.clone(iArr);
        this.f1626v = clone;
        int i4 = i2 & 31;
        if (i4 != 0) {
            int i5 = i3 - 1;
            clone[i5] = ((1 << i4) - 1) & clone[i5];
        }
    }

    public GF2Vector(GF2Vector gF2Vector) {
        this.length = gF2Vector.length;
        this.f1626v = IntUtils.clone(gF2Vector.f1626v);
    }

    public GF2Vector(int[] iArr, int i2) {
        this.f1626v = iArr;
        this.length = i2;
    }
}
