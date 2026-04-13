package org.bouncycastle.jcajce.io;

import java.io.OutputStream;
import javax.crypto.Mac;

/* loaded from: classes.dex */
class MacUpdatingOutputStream extends OutputStream {
    private Mac mac;

    public MacUpdatingOutputStream(Mac mac) {
        this.mac = mac;
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        this.mac.update((byte) i2);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr) {
        this.mac.update(bArr);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        this.mac.update(bArr, i2, i3);
    }
}
