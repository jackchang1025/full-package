package org.bouncycastle.cms.jcajce;

import java.io.OutputStream;
import javax.crypto.Cipher;

/* loaded from: classes.dex */
class JceAADStream extends OutputStream {
    private static final byte[] SINGLE_BYTE = new byte[1];
    private Cipher cipher;

    public JceAADStream(Cipher cipher) {
        this.cipher = cipher;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        byte[] bArr = SINGLE_BYTE;
        bArr[0] = (byte) i2;
        this.cipher.updateAAD(bArr, 0, 1);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.cipher.updateAAD(bArr, i2, i3);
    }
}
