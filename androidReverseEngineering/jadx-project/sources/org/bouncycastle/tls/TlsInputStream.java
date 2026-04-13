package org.bouncycastle.tls;

import java.io.InputStream;

/* loaded from: classes.dex */
class TlsInputStream extends InputStream {
    private final TlsProtocol handler;

    public TlsInputStream(TlsProtocol tlsProtocol) {
        this.handler = tlsProtocol;
    }

    @Override // java.io.InputStream
    public int available() {
        return this.handler.applicationDataAvailable();
    }

    @Override // java.io.InputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.handler.close();
    }

    @Override // java.io.InputStream
    public int read() {
        byte[] bArr = new byte[1];
        if (read(bArr, 0, 1) <= 0) {
            return -1;
        }
        return bArr[0] & 255;
    }

    @Override // java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        return this.handler.readApplicationData(bArr, i2, i3);
    }
}
