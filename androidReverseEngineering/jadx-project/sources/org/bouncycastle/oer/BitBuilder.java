package org.bouncycastle.oer;

import android.sun.security.util.DerValue;
import java.io.OutputStream;
import java.math.BigInteger;
import org.bouncycastle.math.ec.Tnaf;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BitBuilder {
    private static final byte[] bits = {DerValue.TAG_CONTEXT, DerValue.TAG_APPLICATION, 32, Tnaf.POW_2_WIDTH, 8, 4, 2, 1};
    byte[] buf = new byte[1];
    int pos = 0;

    public void finalize() {
        zero();
        super.finalize();
    }

    public void pad() {
        int i2 = this.pos;
        this.pos = (i2 % 8) + i2;
    }

    public int write(OutputStream outputStream) {
        int i2 = this.pos;
        int i3 = ((i2 % 8) + i2) / 8;
        outputStream.write(this.buf, 0, i3);
        outputStream.flush();
        return i3;
    }

    public void write7BitBytes(int i2) {
        boolean z2 = false;
        for (int i3 = 4; i3 >= 0; i3--) {
            if (!z2 && ((-33554432) & i2) != 0) {
                z2 = true;
            }
            if (z2) {
                writeBit(i3).writeBits(i2, 32, 7);
            }
            i2 <<= 7;
        }
    }

    public int writeAndClear(OutputStream outputStream) {
        int i2 = this.pos;
        int i3 = ((i2 % 8) + i2) / 8;
        outputStream.write(this.buf, 0, i3);
        outputStream.flush();
        zero();
        return i3;
    }

    public BitBuilder writeBit(int i2) {
        int i3 = this.pos;
        int i4 = i3 / 8;
        byte[] bArr = this.buf;
        if (i4 >= bArr.length) {
            byte[] bArr2 = new byte[bArr.length + 4];
            System.arraycopy(bArr, 0, bArr2, 0, i3 / 8);
            Arrays.clear(this.buf);
            this.buf = bArr2;
        }
        if (i2 == 0) {
            byte[] bArr3 = this.buf;
            int i5 = this.pos;
            int i6 = i5 / 8;
            bArr3[i6] = (byte) ((~bits[i5 % 8]) & bArr3[i6]);
        } else {
            byte[] bArr4 = this.buf;
            int i7 = this.pos;
            int i8 = i7 / 8;
            bArr4[i8] = (byte) (bits[i7 % 8] | bArr4[i8]);
        }
        this.pos++;
        return this;
    }

    public BitBuilder writeBits(long j2, int i2) {
        for (int i3 = i2 - 1; i3 >= 0; i3--) {
            writeBit(((1 << i3) & j2) > 0 ? 1 : 0);
        }
        return this;
    }

    public void zero() {
        Arrays.clear(this.buf);
        this.pos = 0;
    }

    public void write7BitBytes(BigInteger bigInteger) {
        int bitLength = ((bigInteger.bitLength() % 8) + bigInteger.bitLength()) / 8;
        BigInteger shiftLeft = BigInteger.valueOf(254L).shiftLeft(bitLength * 8);
        boolean z2 = false;
        while (bitLength >= 0) {
            if (!z2 && bigInteger.and(shiftLeft).compareTo(BigInteger.ZERO) != 0) {
                z2 = true;
            }
            if (z2) {
                writeBit(bitLength).writeBits(bigInteger.and(shiftLeft).shiftRight(r3 - 8).intValue(), 8, 7);
            }
            bigInteger = bigInteger.shiftLeft(7);
            bitLength--;
        }
    }

    public BitBuilder writeBits(long j2, int i2, int i3) {
        for (int i4 = i2 - 1; i4 >= i2 - i3; i4--) {
            writeBit(((1 << i4) & j2) != 0 ? 1 : 0);
        }
        return this;
    }
}
