package org.bouncycastle.jcajce.io;

import java.io.OutputStream;
import java.security.MessageDigest;

/* loaded from: classes.dex */
class DigestUpdatingOutputStream extends OutputStream {
    private MessageDigest digest;

    public DigestUpdatingOutputStream(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.digest.update((byte) i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        this.digest.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
