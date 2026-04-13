package org.bouncycastle.tls;

import java.io.OutputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ByteQueue {
    private int available;
    private byte[] databuf;
    private boolean readOnlyBuf;
    private int skipped;

    public ByteQueue() {
        this(0);
    }

    public static int nextTwoPow(int i2) {
        int i3 = i2 | (i2 >> 1);
        int i4 = i3 | (i3 >> 2);
        int i5 = i4 | (i4 >> 4);
        int i6 = i5 | (i5 >> 8);
        return (i6 | (i6 >> 16)) + 1;
    }

    public void addData(byte[] bArr, int i2, int i3) {
        if (this.readOnlyBuf) {
            throw new IllegalStateException("Cannot add data to read-only buffer");
        }
        int i4 = this.skipped;
        int i5 = this.available;
        if (i4 + i5 + i3 > this.databuf.length) {
            int nextTwoPow = nextTwoPow(i5 + i3);
            byte[] bArr2 = this.databuf;
            if (nextTwoPow > bArr2.length) {
                byte[] bArr3 = new byte[nextTwoPow];
                System.arraycopy(bArr2, this.skipped, bArr3, 0, this.available);
                this.databuf = bArr3;
            } else {
                System.arraycopy(bArr2, this.skipped, bArr2, 0, this.available);
            }
            this.skipped = 0;
        }
        System.arraycopy(bArr, i2, this.databuf, this.skipped + this.available, i3);
        this.available += i3;
    }

    public int available() {
        return this.available;
    }

    public void copyTo(OutputStream outputStream, int i2) {
        if (i2 <= this.available) {
            outputStream.write(this.databuf, this.skipped, i2);
        } else {
            StringBuilder m21q = AbstractC0000a.m21q("Cannot copy ", i2, " bytes, only got ");
            m21q.append(this.available);
            throw new IllegalStateException(m21q.toString());
        }
    }

    public void read(byte[] bArr, int i2, int i3, int i4) {
        if (bArr.length - i2 >= i3) {
            if (this.available - i4 < i3) {
                throw new IllegalStateException("Not enough data to read");
            }
            System.arraycopy(this.databuf, this.skipped + i4, bArr, i2, i3);
        } else {
            throw new IllegalArgumentException("Buffer size of " + bArr.length + " is too small for a read of " + i3 + " bytes");
        }
    }

    public HandshakeMessageInput readHandshakeMessage(int i2) {
        int i3 = this.available;
        if (i2 > i3) {
            StringBuilder m21q = AbstractC0000a.m21q("Cannot read ", i2, " bytes, only got ");
            m21q.append(this.available);
            throw new IllegalStateException(m21q.toString());
        }
        int i4 = this.skipped;
        this.available = i3 - i2;
        this.skipped = i4 + i2;
        return new HandshakeMessageInput(this.databuf, i4, i2);
    }

    public int readInt32() {
        if (this.available >= 4) {
            return TlsUtils.readInt32(this.databuf, this.skipped);
        }
        throw new IllegalStateException("Not enough data to read");
    }

    public void removeData(int i2) {
        int i3 = this.available;
        if (i2 <= i3) {
            this.available = i3 - i2;
            this.skipped += i2;
        } else {
            StringBuilder m21q = AbstractC0000a.m21q("Cannot remove ", i2, " bytes, only got ");
            m21q.append(this.available);
            throw new IllegalStateException(m21q.toString());
        }
    }

    public void shrink() {
        int i2 = this.available;
        if (i2 == 0) {
            this.databuf = TlsUtils.EMPTY_BYTES;
        } else {
            int nextTwoPow = nextTwoPow(i2);
            byte[] bArr = this.databuf;
            if (nextTwoPow >= bArr.length) {
                return;
            }
            byte[] bArr2 = new byte[nextTwoPow];
            System.arraycopy(bArr, this.skipped, bArr2, 0, this.available);
            this.databuf = bArr2;
        }
        this.skipped = 0;
    }

    public ByteQueue(int i2) {
        this.skipped = 0;
        this.available = 0;
        this.readOnlyBuf = false;
        this.databuf = i2 == 0 ? TlsUtils.EMPTY_BYTES : new byte[i2];
    }

    public ByteQueue(byte[] bArr, int i2, int i3) {
        this.databuf = bArr;
        this.skipped = i2;
        this.available = i3;
        this.readOnlyBuf = true;
    }

    public void removeData(byte[] bArr, int i2, int i3, int i4) {
        read(bArr, i2, i3, i4);
        removeData(i4 + i3);
    }

    public byte[] removeData(int i2, int i3) {
        byte[] bArr = new byte[i2];
        removeData(bArr, 0, i2, i3);
        return bArr;
    }
}
