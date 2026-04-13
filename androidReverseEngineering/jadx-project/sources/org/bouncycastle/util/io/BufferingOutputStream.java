package org.bouncycastle.util.io;

import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BufferingOutputStream extends OutputStream {
    private final byte[] buf;
    private int bufOff;
    private final OutputStream other;

    public BufferingOutputStream(OutputStream outputStream) {
        this.other = outputStream;
        this.buf = new byte[4096];
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        flush();
        this.other.close();
    }

    @Override // java.io.OutputStream, java.io.Flushable
    public void flush() {
        this.other.write(this.buf, 0, this.bufOff);
        this.bufOff = 0;
        Arrays.fill(this.buf, (byte) 0);
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        byte[] bArr = this.buf;
        int i3 = this.bufOff;
        int i4 = i3 + 1;
        this.bufOff = i4;
        bArr[i3] = (byte) i2;
        if (i4 == bArr.length) {
            flush();
        }
    }

    public BufferingOutputStream(OutputStream outputStream, int i2) {
        this.other = outputStream;
        this.buf = new byte[i2];
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        byte[] bArr2;
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i4 = this.bufOff;
        if (i3 < length - i4) {
            System.arraycopy(bArr, i2, bArr3, i4, i3);
        } else {
            int length2 = bArr3.length - i4;
            System.arraycopy(bArr, i2, bArr3, i4, length2);
            this.bufOff += length2;
            flush();
            int i5 = i2 + length2;
            i3 -= length2;
            while (true) {
                bArr2 = this.buf;
                if (i3 < bArr2.length) {
                    break;
                }
                this.other.write(bArr, i5, bArr2.length);
                byte[] bArr4 = this.buf;
                i5 += bArr4.length;
                i3 -= bArr4.length;
            }
            if (i3 <= 0) {
                return;
            } else {
                System.arraycopy(bArr, i5, bArr2, this.bufOff, i3);
            }
        }
        this.bufOff += i3;
    }
}
