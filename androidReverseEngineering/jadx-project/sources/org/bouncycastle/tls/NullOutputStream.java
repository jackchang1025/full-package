package org.bouncycastle.tls;

import java.io.OutputStream;

/* loaded from: classes.dex */
class NullOutputStream extends OutputStream {
    static final NullOutputStream INSTANCE = new NullOutputStream();

    private NullOutputStream() {
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
    }
}
