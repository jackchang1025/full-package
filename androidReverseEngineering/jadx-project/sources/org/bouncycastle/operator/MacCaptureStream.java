package org.bouncycastle.operator;

import java.io.OutputStream;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class MacCaptureStream extends OutputStream {
    private final OutputStream cOut;
    private final byte[] mac;
    int macIndex = 0;

    public MacCaptureStream(OutputStream outputStream, int i2) {
        this.cOut = outputStream;
        this.mac = new byte[i2];
    }

    public byte[] getMac() {
        return Arrays.clone(this.mac);
    }

    @Override // java.io.OutputStream
    public void write(int i2) {
        int i3 = this.macIndex;
        byte[] bArr = this.mac;
        if (i3 != bArr.length) {
            this.macIndex = i3 + 1;
            bArr[i3] = (byte) i2;
            return;
        }
        byte b = bArr[0];
        System.arraycopy(bArr, 1, bArr, 0, bArr.length - 1);
        byte[] bArr2 = this.mac;
        bArr2[bArr2.length - 1] = (byte) i2;
        this.cOut.write(b);
    }

    @Override // java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        byte[] bArr2 = this.mac;
        if (i3 < bArr2.length) {
            for (int i4 = 0; i4 != i3; i4++) {
                write(bArr[i2 + i4]);
            }
        } else {
            this.cOut.write(bArr2, 0, this.macIndex);
            byte[] bArr3 = this.mac;
            this.macIndex = bArr3.length;
            System.arraycopy(bArr, (i2 + i3) - bArr3.length, bArr3, 0, bArr3.length);
            this.cOut.write(bArr, i2, i3 - this.mac.length);
        }
    }
}
