package org.bouncycastle.crypto.util;

import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.math.BigInteger;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
class SSHBuilder {
    private final ByteArrayOutputStream bos = new ByteArrayOutputStream();

    public byte[] getBytes() {
        return this.bos.toByteArray();
    }

    public byte[] getPaddedBytes() {
        return getPaddedBytes(8);
    }

    public void u32(int i2) {
        this.bos.write((i2 >>> 24) & 255);
        this.bos.write((i2 >>> 16) & 255);
        this.bos.write((i2 >>> 8) & 255);
        this.bos.write(i2 & 255);
    }

    public void writeBigNum(BigInteger bigInteger) {
        writeBlock(bigInteger.toByteArray());
    }

    public void writeBlock(byte[] bArr) {
        u32(bArr.length);
        try {
            this.bos.write(bArr);
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage(), e2);
        }
    }

    public void writeBytes(byte[] bArr) {
        try {
            this.bos.write(bArr);
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage(), e2);
        }
    }

    public void writeString(String str) {
        writeBlock(Strings.toByteArray(str));
    }

    public byte[] getPaddedBytes(int i2) {
        int size = this.bos.size() % i2;
        if (size != 0) {
            int i3 = i2 - size;
            for (int i4 = 1; i4 <= i3; i4++) {
                this.bos.write(i4);
            }
        }
        return this.bos.toByteArray();
    }
}
