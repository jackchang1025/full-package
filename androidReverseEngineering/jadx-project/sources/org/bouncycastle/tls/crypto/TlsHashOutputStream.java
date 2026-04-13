package org.bouncycastle.tls.crypto;

import java.io.OutputStream;

/* loaded from: classes.dex */
public class TlsHashOutputStream extends OutputStream {
    protected TlsHash hash;

    public TlsHashOutputStream(TlsHash tlsHash) {
        this.hash = tlsHash;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.hash.update(new byte[]{(byte) i2}, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.hash.update(bArr, i2, i3);
    }
}
