package android.sun.security.util;

import java.io.ByteArrayOutputStream;
import java.util.Arrays;

/* loaded from: classes.dex */
public class BitArray {
    private static final int BITS_PER_UNIT = 8;
    private static final int BYTES_PER_LINE = 8;
    private static final byte[][] NYBBLE = {new byte[]{48, 48, 48, 48}, new byte[]{48, 48, 48, 49}, new byte[]{48, 48, 49, 48}, new byte[]{48, 48, 49, 49}, new byte[]{48, 49, 48, 48}, new byte[]{48, 49, 48, 49}, new byte[]{48, 49, 49, 48}, new byte[]{48, 49, 49, 49}, new byte[]{49, 48, 48, 48}, new byte[]{49, 48, 48, 49}, new byte[]{49, 48, 49, 48}, new byte[]{49, 48, 49, 49}, new byte[]{49, 49, 48, 48}, new byte[]{49, 49, 48, 49}, new byte[]{49, 49, 49, 48}, new byte[]{49, 49, 49, 49}};
    private int length;
    private byte[] repn;

    public BitArray(int i2) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Negative length for BitArray");
        }
        this.length = i2;
        this.repn = new byte[((i2 + 8) - 1) / 8];
    }

    private static int position(int i2) {
        return 1 << (7 - (i2 % 8));
    }

    private static int subscript(int i2) {
        return i2 / 8;
    }

    public Object clone() {
        return new BitArray(this);
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj == null || !(obj instanceof BitArray)) {
            return false;
        }
        BitArray bitArray = (BitArray) obj;
        if (bitArray.length != this.length) {
            return false;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr = this.repn;
            if (i2 >= bArr.length) {
                return true;
            }
            if (bArr[i2] != bitArray.repn[i2]) {
                return false;
            }
            i2++;
        }
    }

    public boolean get(int i2) {
        if (i2 < 0 || i2 >= this.length) {
            throw new ArrayIndexOutOfBoundsException(Integer.toString(i2));
        }
        return (position(i2) & this.repn[subscript(i2)]) != 0;
    }

    public int hashCode() {
        int i2 = 0;
        int i3 = 0;
        while (true) {
            byte[] bArr = this.repn;
            if (i2 >= bArr.length) {
                return this.length ^ i3;
            }
            i3 = (i3 * 31) + bArr[i2];
            i2++;
        }
    }

    public int length() {
        return this.length;
    }

    public void set(int i2, boolean z2) {
        if (i2 < 0 || i2 >= this.length) {
            throw new ArrayIndexOutOfBoundsException(Integer.toString(i2));
        }
        int subscript = subscript(i2);
        int position = position(i2);
        if (z2) {
            byte[] bArr = this.repn;
            bArr[subscript] = (byte) (position | bArr[subscript]);
        } else {
            byte[] bArr2 = this.repn;
            bArr2[subscript] = (byte) ((~position) & bArr2[subscript]);
        }
    }

    public boolean[] toBooleanArray() {
        boolean[] zArr = new boolean[this.length];
        for (int i2 = 0; i2 < this.length; i2++) {
            zArr[i2] = get(i2);
        }
        return zArr;
    }

    public byte[] toByteArray() {
        return (byte[]) this.repn.clone();
    }

    public String toString() {
        byte[] bArr;
        ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
        int i2 = 0;
        while (true) {
            bArr = this.repn;
            if (i2 >= bArr.length - 1) {
                break;
            }
            byte[][] bArr2 = NYBBLE;
            byteArrayOutputStream.write(bArr2[(bArr[i2] >> 4) & 15], 0, 4);
            byteArrayOutputStream.write(bArr2[this.repn[i2] & 15], 0, 4);
            byteArrayOutputStream.write(i2 % 8 == 7 ? 10 : 32);
            i2++;
        }
        for (int length = (bArr.length - 1) * 8; length < this.length; length++) {
            byteArrayOutputStream.write(get(length) ? 49 : 48);
        }
        return new String(byteArrayOutputStream.toByteArray());
    }

    public BitArray truncate() {
        for (int i2 = this.length - 1; i2 >= 0; i2--) {
            if (get(i2)) {
                return new BitArray(i2 + 1, Arrays.copyOf(this.repn, (i2 + 8) / 8));
            }
        }
        return new BitArray(1);
    }

    public BitArray(int i2, byte[] bArr) {
        if (i2 < 0) {
            throw new IllegalArgumentException("Negative length for BitArray");
        }
        if (bArr.length * 8 < i2) {
            throw new IllegalArgumentException("Byte array too short to represent bit array of given length");
        }
        this.length = i2;
        int i3 = ((i2 + 8) - 1) / 8;
        byte b = (byte) (255 << ((i3 * 8) - i2));
        byte[] bArr2 = new byte[i3];
        this.repn = bArr2;
        System.arraycopy(bArr, 0, bArr2, 0, i3);
        if (i3 > 0) {
            byte[] bArr3 = this.repn;
            int i4 = i3 - 1;
            bArr3[i4] = (byte) (b & bArr3[i4]);
        }
    }

    private BitArray(BitArray bitArray) {
        this.length = bitArray.length;
        this.repn = (byte[]) bitArray.repn.clone();
    }

    public BitArray(boolean[] zArr) {
        int length = zArr.length;
        this.length = length;
        this.repn = new byte[(length + 7) / 8];
        for (int i2 = 0; i2 < this.length; i2++) {
            set(i2, zArr[i2]);
        }
    }
}
