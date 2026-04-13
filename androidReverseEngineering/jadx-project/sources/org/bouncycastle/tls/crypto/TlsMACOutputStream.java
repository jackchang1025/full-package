package org.bouncycastle.tls.crypto;

import java.io.OutputStream;

/* loaded from: classes.dex */
public class TlsMACOutputStream extends OutputStream {
    protected TlsMAC mac;

    public TlsMACOutputStream(TlsMAC tlsMAC) {
        this.mac = tlsMAC;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.mac.update(new byte[]{(byte) i2}, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.mac.update(bArr, i2, i3);
    }
}
