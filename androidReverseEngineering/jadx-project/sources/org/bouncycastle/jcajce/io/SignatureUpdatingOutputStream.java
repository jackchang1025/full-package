package org.bouncycastle.jcajce.io;

import java.io.IOException;
import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;

/* loaded from: classes.dex */
class SignatureUpdatingOutputStream extends OutputStream {
    private Signature sig;

    public SignatureUpdatingOutputStream(Signature signature) {
        this.sig = signature;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        try {
            this.sig.update((byte) i2);
        } catch (SignatureException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        try {
            this.sig.update(bArr);
        } catch (SignatureException e2) {
            throw new IOException(e2.getMessage());
        }
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        try {
            this.sig.update(bArr, i2, i3);
        } catch (SignatureException e2) {
            throw new IOException(e2.getMessage());
        }
    }
}
