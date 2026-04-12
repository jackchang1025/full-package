package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class y10 extends i91 {

    /* renamed from: v */
    private int[] f61221v;

    public y10(int i) {
        if (i < 0) {
            throw new ArithmeticException("Negative length.");
        }
        this.length = i;
        this.f61221v = new int[(i + 31) >> 5];
    }

    public static y10 OS2VP(int i, byte[] bArr) {
        if (i < 0) {
            throw new ArithmeticException("negative length");
        }
        if (bArr.length <= ((i + 7) >> 3)) {
            return new y10(i, ub0.toIntArray(bArr));
        }
        throw new ArithmeticException("length mismatch");
    }

    @Override // p000.i91
    public i91 add(i91 i91Var) {
        if (!(i91Var instanceof y10)) {
            throw new ArithmeticException("vector is not defined over GF(2)");
        }
        y10 y10Var = (y10) i91Var;
        if (this.length != y10Var.length) {
            throw new ArithmeticException("length mismatch");
        }
        int[] iArrClone = o60.clone(y10Var.f61221v);
        for (int length = iArrClone.length - 1; length >= 0; length--) {
            iArrClone[length] = iArrClone[length] ^ this.f61221v[length];
        }
        return new y10(this.length, iArrClone);
    }

    @Override // p000.i91
    public boolean equals(Object obj) {
        if (!(obj instanceof y10)) {
            return false;
        }
        y10 y10Var = (y10) obj;
        return this.length == y10Var.length && o60.equals(this.f61221v, y10Var.f61221v);
    }

    public y10 extractLeftVector(int i) {
        int i2 = this.length;
        if (i > i2) {
            throw new ArithmeticException("invalid length");
        }
        if (i == i2) {
            return new y10(this);
        }
        y10 y10Var = new y10(i);
        int i3 = i >> 5;
        int i4 = i & 31;
        System.arraycopy(this.f61221v, 0, y10Var.f61221v, 0, i3);
        if (i4 != 0) {
            y10Var.f61221v[i3] = ((1 << i4) - 1) & this.f61221v[i3];
        }
        return y10Var;
    }

    public y10 extractRightVector(int i) {
        int i2;
        int i3 = this.length;
        if (i > i3) {
            throw new ArithmeticException("invalid length");
        }
        if (i == i3) {
            return new y10(this);
        }
        y10 y10Var = new y10(i);
        int i4 = this.length;
        int i5 = (i4 - i) >> 5;
        int i6 = (i4 - i) & 31;
        int i7 = (i + 31) >> 5;
        int i8 = 0;
        if (i6 == 0) {
            System.arraycopy(this.f61221v, i5, y10Var.f61221v, 0, i7);
            return y10Var;
        }
        while (true) {
            i2 = i7 - 1;
            if (i8 >= i2) {
                break;
            }
            int[] iArr = y10Var.f61221v;
            int[] iArr2 = this.f61221v;
            int i9 = i5 + 1;
            iArr[i8] = (iArr2[i5] >>> i6) | (iArr2[i9] << (32 - i6));
            i8++;
            i5 = i9;
        }
        int[] iArr3 = y10Var.f61221v;
        int[] iArr4 = this.f61221v;
        int i10 = i5 + 1;
        int i11 = iArr4[i5] >>> i6;
        iArr3[i2] = i11;
        if (i10 < iArr4.length) {
            iArr3[i2] = (iArr4[i10] << (32 - i6)) | i11;
        }
        return y10Var;
    }

    public y10 extractVector(int[] iArr) {
        int length = iArr.length;
        if (iArr[length - 1] > this.length) {
            throw new ArithmeticException("invalid index set");
        }
        y10 y10Var = new y10(length);
        for (int i = 0; i < length; i++) {
            int[] iArr2 = this.f61221v;
            int i2 = iArr[i];
            if ((iArr2[i2 >> 5] & (1 << (i2 & 31))) != 0) {
                int[] iArr3 = y10Var.f61221v;
                int i3 = i >> 5;
                iArr3[i3] = (1 << (i & 31)) | iArr3[i3];
            }
        }
        return y10Var;
    }

    public int getBit(int i) {
        if (i >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int i2 = i >> 5;
        int i3 = i & 31;
        return (this.f61221v[i2] & (1 << i3)) >>> i3;
    }

    @Override // p000.i91
    public byte[] getEncoded() {
        return ub0.toByteArray(this.f61221v, (this.length + 7) >> 3);
    }

    public int getHammingWeight() {
        int i = 0;
        int i2 = 0;
        while (true) {
            int[] iArr = this.f61221v;
            if (i >= iArr.length) {
                return i2;
            }
            int i3 = iArr[i];
            for (int i4 = 0; i4 < 32; i4++) {
                if ((i3 & 1) != 0) {
                    i2++;
                }
                i3 >>>= 1;
            }
            i++;
        }
    }

    public int[] getVecArray() {
        return this.f61221v;
    }

    @Override // p000.i91
    public int hashCode() {
        return C0133bg.hashCode(this.f61221v) + (this.length * 31);
    }

    @Override // p000.i91
    public boolean isZero() {
        for (int length = this.f61221v.length - 1; length >= 0; length--) {
            if (this.f61221v[length] != 0) {
                return false;
            }
        }
        return true;
    }

    @Override // p000.i91
    public i91 multiply(kn0 kn0Var) {
        int[] vector = kn0Var.getVector();
        int i = this.length;
        if (i != vector.length) {
            throw new ArithmeticException("length mismatch");
        }
        y10 y10Var = new y10(i);
        for (int i2 = 0; i2 < vector.length; i2++) {
            int[] iArr = this.f61221v;
            int i3 = vector[i2];
            if ((iArr[i3 >> 5] & (1 << (i3 & 31))) != 0) {
                int[] iArr2 = y10Var.f61221v;
                int i4 = i2 >> 5;
                iArr2[i4] = (1 << (i2 & 31)) | iArr2[i4];
            }
        }
        return y10Var;
    }

    public void setBit(int i) {
        if (i >= this.length) {
            throw new IndexOutOfBoundsException();
        }
        int[] iArr = this.f61221v;
        int i2 = i >> 5;
        iArr[i2] = (1 << (i & 31)) | iArr[i2];
    }

    public a20 toExtensionFieldVector(z10 z10Var) {
        int degree = z10Var.getDegree();
        int i = this.length;
        if (i % degree != 0) {
            throw new ArithmeticException("conversion is impossible");
        }
        int i2 = i / degree;
        int[] iArr = new int[i2];
        int i3 = 0;
        for (int i4 = i2 - 1; i4 >= 0; i4--) {
            for (int degree2 = z10Var.getDegree() - 1; degree2 >= 0; degree2--) {
                if (((this.f61221v[i3 >>> 5] >>> (i3 & 31)) & 1) == 1) {
                    iArr[i4] = iArr[i4] ^ (1 << degree2);
                }
                i3++;
            }
        }
        return new a20(z10Var, iArr);
    }

    @Override // p000.i91
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.length; i++) {
            if (i != 0 && (i & 31) == 0) {
                stringBuffer.append(' ');
            }
            stringBuffer.append((this.f61221v[i >> 5] & (1 << (i & 31))) == 0 ? '0' : '1');
        }
        return stringBuffer.toString();
    }

    public y10(int i, int i2, SecureRandom secureRandom) {
        if (i2 > i) {
            throw new ArithmeticException("The hamming weight is greater than the length of vector.");
        }
        this.length = i;
        this.f61221v = new int[(i + 31) >> 5];
        int[] iArr = new int[i];
        for (int i3 = 0; i3 < i; i3++) {
            iArr[i3] = i3;
        }
        for (int i4 = 0; i4 < i2; i4++) {
            int iNextInt = zp0.nextInt(secureRandom, i);
            setBit(iArr[iNextInt]);
            i--;
            iArr[iNextInt] = iArr[i];
        }
    }

    public y10(int i, SecureRandom secureRandom) {
        this.length = i;
        int i2 = (i + 31) >> 5;
        this.f61221v = new int[i2];
        int i3 = i2 - 1;
        for (int i4 = i3; i4 >= 0; i4--) {
            this.f61221v[i4] = secureRandom.nextInt();
        }
        int i5 = i & 31;
        if (i5 != 0) {
            int[] iArr = this.f61221v;
            iArr[i3] = ((1 << i5) - 1) & iArr[i3];
        }
    }

    public y10(int i, int[] iArr) {
        if (i < 0) {
            throw new ArithmeticException("negative length");
        }
        this.length = i;
        int i2 = (i + 31) >> 5;
        if (iArr.length != i2) {
            throw new ArithmeticException("length mismatch");
        }
        int[] iArrClone = o60.clone(iArr);
        this.f61221v = iArrClone;
        int i3 = i & 31;
        if (i3 != 0) {
            int i4 = i2 - 1;
            iArrClone[i4] = ((1 << i3) - 1) & iArrClone[i4];
        }
    }

    public y10(y10 y10Var) {
        this.length = y10Var.length;
        this.f61221v = o60.clone(y10Var.f61221v);
    }

    public y10(int[] iArr, int i) {
        this.f61221v = iArr;
        this.length = i;
    }
}
