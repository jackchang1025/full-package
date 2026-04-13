package org.bouncycastle.pqc.crypto.sphincsplus;

/* loaded from: classes.dex */
class SIG_FORS {
    final byte[][] authPath;
    final byte[] sk;

    public SIG_FORS(byte[] bArr, byte[][] bArr2) {
        this.authPath = bArr2;
        this.sk = bArr;
    }

    public byte[][] getAuthPath() {
        return this.authPath;
    }

    public byte[] getSK() {
        return this.sk;
    }
}
