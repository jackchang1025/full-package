package org.bouncycastle.crypto.io;

import java.io.FilterInputStream;
import java.io.InputStream;
import org.bouncycastle.crypto.Mac;

/* loaded from: classes.dex */
public class MacInputStream extends FilterInputStream {
    protected Mac mac;

    public MacInputStream(InputStream inputStream, Mac mac) {
        super(inputStream);
        this.mac = mac;
    }

    public Mac getMac() {
        return this.mac;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read() {
        int read = ((FilterInputStream) this).in.read();
        if (read >= 0) {
            this.mac.update((byte) read);
        }
        return read;
    }

    @Override // java.io.FilterInputStream, java.io.InputStream
    public int read(byte[] bArr, int i2, int i3) {
        int read = ((FilterInputStream) this).in.read(bArr, i2, i3);
        if (read >= 0) {
            this.mac.update(bArr, i2, read);
        }
        return read;
    }
}
