package org.bouncycastle.crypto.io;

import java.io.OutputStream;
import org.bouncycastle.crypto.Signer;

/* loaded from: classes.dex */
public class SignerOutputStream extends OutputStream {
    protected Signer signer;

    public SignerOutputStream(Signer signer) {
        this.signer = signer;
    }

    public Signer getSigner() {
        return this.signer;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.signer.update((byte) i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.signer.update(bArr, i2, i3);
    }
}
