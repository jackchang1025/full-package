package org.bouncycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.InputStream;
import org.bouncycastle.crypto.Signer;

/* loaded from: classes.dex */
public class SignerInputStream extends FilterInputStream {
    protected Signer signer;

    public SignerInputStream(InputStream inputStream, Signer signer) {
        super(inputStream);
        this.signer = signer;
    }

    public Signer getSigner() {
        return this.signer;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() {
        int read = ((FilterInputStream) this).in.read();
        if (read >= 0) {
            this.signer.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        int read = ((FilterInputStream) this).in.read(bArr, i2, i3);
        if (read > 0) {
            this.signer.update(bArr, i2, read);
        }
        return read;
    }
}
