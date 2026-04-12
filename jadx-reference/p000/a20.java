package p000;

/* loaded from: classes2.dex */
public class a20 extends i91 {
    private z10 field;
    private int[] vector;

    public a20(z10 z10Var, byte[] bArr) {
        this.field = new z10(z10Var);
        int i = 8;
        int i2 = 1;
        while (z10Var.getDegree() > i) {
            i2++;
            i += 8;
        }
        if (bArr.length % i2 != 0) {
            throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
        }
        int length = bArr.length / i2;
        this.length = length;
        this.vector = new int[length];
        int i3 = 0;
        for (int i4 = 0; i4 < this.vector.length; i4++) {
            int i5 = 0;
            while (i5 < i) {
                int[] iArr = this.vector;
                iArr[i4] = ((bArr[i3] & 255) << i5) | iArr[i4];
                i5 += 8;
                i3++;
            }
            if (!z10Var.isElementOfThisField(this.vector[i4])) {
                throw new IllegalArgumentException("Byte array is not an encoded vector over the given finite field.");
            }
        }
    }

    @Override // p000.i91
    public i91 add(i91 i91Var) {
        throw new RuntimeException("not implemented");
    }

    @Override // p000.i91
    public boolean equals(Object obj) {
        if (!(obj instanceof a20)) {
            return false;
        }
        a20 a20Var = (a20) obj;
        if (this.field.equals(a20Var.field)) {
            return o60.equals(this.vector, a20Var.vector);
        }
        return false;
    }

    @Override // p000.i91
    public byte[] getEncoded() {
        int i = 8;
        int i2 = 1;
        while (this.field.getDegree() > i) {
            i2++;
            i += 8;
        }
        byte[] bArr = new byte[this.vector.length * i2];
        int i3 = 0;
        for (int i4 = 0; i4 < this.vector.length; i4++) {
            int i5 = 0;
            while (i5 < i) {
                bArr[i3] = (byte) (this.vector[i4] >>> i5);
                i5 += 8;
                i3++;
            }
        }
        return bArr;
    }

    public z10 getField() {
        return this.field;
    }

    public int[] getIntArrayForm() {
        return o60.clone(this.vector);
    }

    @Override // p000.i91
    public int hashCode() {
        return C0133bg.hashCode(this.vector) + (this.field.hashCode() * 31);
    }

    @Override // p000.i91
    public boolean isZero() {
        for (int length = this.vector.length - 1; length >= 0; length--) {
            if (this.vector[length] != 0) {
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
            throw new ArithmeticException("permutation size and vector size mismatch");
        }
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < vector.length; i2++) {
            iArr[i2] = this.vector[vector[i2]];
        }
        return new a20(this.field, iArr);
    }

    @Override // p000.i91
    public String toString() {
        StringBuffer stringBuffer = new StringBuffer();
        for (int i = 0; i < this.vector.length; i++) {
            for (int i2 = 0; i2 < this.field.getDegree(); i2++) {
                stringBuffer.append(((1 << (i2 & 31)) & this.vector[i]) != 0 ? '1' : '0');
            }
            stringBuffer.append(' ');
        }
        return stringBuffer.toString();
    }

    public a20(z10 z10Var, int[] iArr) {
        this.field = z10Var;
        this.length = iArr.length;
        for (int length = iArr.length - 1; length >= 0; length--) {
            if (!z10Var.isElementOfThisField(iArr[length])) {
                throw new ArithmeticException("Element array is not specified over the given finite field.");
            }
        }
        this.vector = o60.clone(iArr);
    }

    public a20(a20 a20Var) {
        this.field = new z10(a20Var.field);
        this.length = a20Var.length;
        this.vector = o60.clone(a20Var.vector);
    }
}
