package org.bouncycastle.tls;

import java.io.InputStream;

/* loaded from: classes.dex */
public class ByteQueueInputStream extends InputStream {
    private ByteQueue buffer = new ByteQueue();

    public void addBytes(byte[] bArr) {
        this.buffer.addData(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int available() {
        return this.buffer.available();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
    }

    public int peek(byte[] bArr) {
        int min = Math.min(this.buffer.available(), bArr.length);
        this.buffer.read(bArr, 0, min, 0);
        return min;
    }

    @Override // java.io.InputStream
    public int read() {
        if (this.buffer.available() == 0) {
            return -1;
        }
        return this.buffer.removeData(1, 0)[0] & 255;
    }

    @Override // java.io.InputStream
    public long skip(long j2) {
        int min = Math.min((int) j2, this.buffer.available());
        this.buffer.removeData(min);
        return min;
    }

    public void addBytes(byte[] bArr, int i2, int i3) {
        this.buffer.addData(bArr, i2, i3);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr) {
        return read(bArr, 0, bArr.length);
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        int min = Math.min(this.buffer.available(), i3);
        this.buffer.removeData(bArr, i2, min, 0);
        return min;
    }
}
