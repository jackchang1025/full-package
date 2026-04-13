package org.bouncycastle.crypto.util;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
class SSHBuffer {
    private final byte[] buffer;
    private int pos = 0;

    public SSHBuffer(byte[] bArr) {
        this.buffer = bArr;
    }

    public byte[] getBuffer() {
        return Arrays.clone(this.buffer);
    }

    public boolean hasRemaining() {
        return this.pos < this.buffer.length;
    }

    public BigInteger readBigNumPositive() {
        int readU32 = readU32();
        int i2 = this.pos;
        int i3 = i2 + readU32;
        byte[] bArr = this.buffer;
        if (i3 > bArr.length) {
            throw new IllegalArgumentException("not enough data for big num");
        }
        int i4 = readU32 + i2;
        this.pos = i4;
        return new BigInteger(1, Arrays.copyOfRange(bArr, i2, i4));
    }

    public byte[] readBlock() {
        int readU32 = readU32();
        if (readU32 == 0) {
            return new byte[0];
        }
        int i2 = this.pos;
        byte[] bArr = this.buffer;
        if (i2 > bArr.length - readU32) {
            throw new IllegalArgumentException("not enough data for block");
        }
        int i3 = readU32 + i2;
        this.pos = i3;
        return Arrays.copyOfRange(bArr, i2, i3);
    }

    public byte[] readPaddedBlock() {
        return readPaddedBlock(8);
    }

    public String readString() {
        return Strings.fromByteArray(readBlock());
    }

    public int readU32() {
        int i2 = this.pos;
        byte[] bArr = this.buffer;
        if (i2 > bArr.length - 4) {
            throw new IllegalArgumentException("4 bytes for U32 exceeds buffer.");
        }
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = ((bArr[i2] & 255) << 24) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        int i7 = i5 | ((bArr[i4] & 255) << 8);
        this.pos = i6 + 1;
        return i7 | (bArr[i6] & 255);
    }

    public void skipBlock() {
        int readU32 = readU32();
        int i2 = this.pos;
        if (i2 > this.buffer.length - readU32) {
            throw new IllegalArgumentException("not enough data for block");
        }
        this.pos = i2 + readU32;
    }

    public SSHBuffer(byte[] bArr, byte[] bArr2) {
        this.buffer = bArr2;
        for (int i2 = 0; i2 != bArr.length; i2++) {
            if (bArr[i2] != bArr2[i2]) {
                throw new IllegalArgumentException("magic-number incorrect");
            }
        }
        this.pos += bArr.length;
    }

    public byte[] readPaddedBlock(int i2) {
        int i3;
        int readU32 = readU32();
        if (readU32 == 0) {
            return new byte[0];
        }
        int i4 = this.pos;
        byte[] bArr = this.buffer;
        if (i4 > bArr.length - readU32) {
            throw new IllegalArgumentException("not enough data for block");
        }
        if (readU32 % i2 != 0) {
            throw new IllegalArgumentException("missing padding");
        }
        int i5 = i4 + readU32;
        this.pos = i5;
        if (readU32 > 0 && (i3 = bArr[i5 - 1] & 255) > 0 && i3 < i2) {
            i5 -= i3;
            int i6 = 1;
            int i7 = i5;
            while (i6 <= i3) {
                if (i6 != (this.buffer[i7] & 255)) {
                    throw new IllegalArgumentException("incorrect padding");
                }
                i6++;
                i7++;
            }
        }
        return Arrays.copyOfRange(this.buffer, i4, i5);
    }
}
