package p000;

import java.security.SecureRandom;

/* loaded from: classes2.dex */
public class kn0 {
    private int[] perm;

    public kn0(int i) {
        if (i <= 0) {
            throw new IllegalArgumentException("invalid length");
        }
        this.perm = new int[i];
        for (int i2 = i - 1; i2 >= 0; i2--) {
            this.perm[i2] = i2;
        }
    }

    private boolean isPermutation(int[] iArr) {
        int length = iArr.length;
        boolean[] zArr = new boolean[length];
        for (int i : iArr) {
            if (i < 0 || i >= length || zArr[i]) {
                return false;
            }
            zArr[i] = true;
        }
        return true;
    }

    public kn0 computeInverse() {
        kn0 kn0Var = new kn0(this.perm.length);
        for (int length = this.perm.length - 1; length >= 0; length--) {
            kn0Var.perm[this.perm[length]] = length;
        }
        return kn0Var;
    }

    public boolean equals(Object obj) {
        if (obj instanceof kn0) {
            return o60.equals(this.perm, ((kn0) obj).perm);
        }
        return false;
    }

    public byte[] getEncoded() {
        int length = this.perm.length;
        int iCeilLog256 = p60.ceilLog256(length - 1);
        byte[] bArr = new byte[(length * iCeilLog256) + 4];
        ub0.I2OSP(length, bArr, 0);
        for (int i = 0; i < length; i++) {
            ub0.I2OSP(this.perm[i], bArr, (i * iCeilLog256) + 4, iCeilLog256);
        }
        return bArr;
    }

    public int[] getVector() {
        return o60.clone(this.perm);
    }

    public int hashCode() {
        return C0133bg.hashCode(this.perm);
    }

    public kn0 rightMultiply(kn0 kn0Var) {
        int length = kn0Var.perm.length;
        int[] iArr = this.perm;
        if (length != iArr.length) {
            throw new IllegalArgumentException("length mismatch");
        }
        kn0 kn0Var2 = new kn0(iArr.length);
        for (int length2 = this.perm.length - 1; length2 >= 0; length2--) {
            kn0Var2.perm[length2] = this.perm[kn0Var.perm[length2]];
        }
        return kn0Var2;
    }

    public String toString() {
        String string = "[" + this.perm[0];
        for (int i = 1; i < this.perm.length; i++) {
            StringBuilder sbM39c0 = AbstractC0003a2.m39c0(string, ", ");
            sbM39c0.append(this.perm[i]);
            string = sbM39c0.toString();
        }
        return AbstractC0003a2.m32b3(string, "]");
    }

    public kn0(int i, SecureRandom secureRandom) {
        if (i <= 0) {
            throw new IllegalArgumentException("invalid length");
        }
        this.perm = new int[i];
        int[] iArr = new int[i];
        for (int i2 = 0; i2 < i; i2++) {
            iArr[i2] = i2;
        }
        int i3 = i;
        for (int i4 = 0; i4 < i; i4++) {
            int iNextInt = zp0.nextInt(secureRandom, i3);
            i3--;
            this.perm[i4] = iArr[iNextInt];
            iArr[iNextInt] = iArr[i3];
        }
    }

    public kn0(byte[] bArr) {
        if (bArr.length <= 4) {
            throw new IllegalArgumentException("invalid encoding");
        }
        int iOS2IP = ub0.OS2IP(bArr, 0);
        int iCeilLog256 = p60.ceilLog256(iOS2IP - 1);
        if (bArr.length != (iOS2IP * iCeilLog256) + 4) {
            throw new IllegalArgumentException("invalid encoding");
        }
        this.perm = new int[iOS2IP];
        for (int i = 0; i < iOS2IP; i++) {
            this.perm[i] = ub0.OS2IP(bArr, (i * iCeilLog256) + 4, iCeilLog256);
        }
        if (!isPermutation(this.perm)) {
            throw new IllegalArgumentException("invalid encoding");
        }
    }

    public kn0(int[] iArr) {
        if (!isPermutation(iArr)) {
            throw new IllegalArgumentException("array is not a permutation vector");
        }
        this.perm = o60.clone(iArr);
    }
}
