package org.bouncycastle.tls;

import java.io.OutputStream;

/* loaded from: classes.dex */
class TlsOutputStream extends OutputStream {
    private final TlsProtocol handler;

    public TlsOutputStream(TlsProtocol tlsProtocol) {
        this.handler = tlsProtocol;
    }

    @Override // java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        this.handler.close();
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        write(new byte[]{(byte) i2}, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.handler.writeApplicationData(bArr, i2, i3);
    }
}
