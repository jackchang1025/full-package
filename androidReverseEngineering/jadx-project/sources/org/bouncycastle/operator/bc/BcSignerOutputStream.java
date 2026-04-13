package org.bouncycastle.operator.bc;

import java.io.OutputStream;
import org.bouncycastle.crypto.Signer;

/* loaded from: classes.dex */
public class BcSignerOutputStream extends OutputStream {
    private Signer sig;

    public BcSignerOutputStream(Signer signer) {
        this.sig = signer;
    }

    public byte[] getSignature() {
        return this.sig.generateSignature();
    }

    public boolean verify(byte[] bArr) {
        return this.sig.verifySignature(bArr);
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.sig.update((byte) i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        this.sig.update(bArr, 0, bArr.length);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.sig.update(bArr, i2, i3);
    }
}
