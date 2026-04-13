package org.bouncycastle.mime;

import java.io.InputStream;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class BoundaryLimitedInputStream extends InputStream {
    private final byte[] boundary;
    private final byte[] buf;
    private int bufOff;
    private int lastI;
    private final InputStream src;
    private int index = 0;
    private boolean ended = false;

    public BoundaryLimitedInputStream(InputStream inputStream, String str) {
        this.bufOff = 0;
        this.src = inputStream;
        this.boundary = Strings.toByteArray(str);
        this.buf = new byte[str.length() + 3];
        this.bufOff = 0;
    }

    /* JADX WARN: Removed duplicated region for block: B:25:0x0052  */
    /* JADX WARN: Removed duplicated region for block: B:27:0x0064  */
    /* JADX WARN: Removed duplicated region for block: B:43:0x00a2  */
    @Override // java.io.InputStream
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int read() {
        int read;
        int read2;
        int read3;
        if (this.ended) {
            return -1;
        }
        int i2 = this.index;
        int i3 = this.bufOff;
        if (i2 < i3) {
            byte[] bArr = this.buf;
            int i4 = i2 + 1;
            this.index = i4;
            read = bArr[i2] & 255;
            if (i4 < i3) {
                return read;
            }
            this.bufOff = 0;
            this.index = 0;
        } else {
            read = this.src.read();
        }
        this.lastI = read;
        if (read < 0) {
            return -1;
        }
        if (read == 13 || read == 10) {
            this.index = 0;
            if (read == 13) {
                read2 = this.src.read();
                if (read2 == 10) {
                    byte[] bArr2 = this.buf;
                    int i5 = this.bufOff;
                    this.bufOff = i5 + 1;
                    bArr2[i5] = 10;
                }
                if (read2 == 45) {
                    byte[] bArr3 = this.buf;
                    int i6 = this.bufOff;
                    this.bufOff = i6 + 1;
                    bArr3[i6] = 45;
                    read2 = this.src.read();
                }
                if (read2 != 45) {
                    byte[] bArr4 = this.buf;
                    int i7 = this.bufOff;
                    int i8 = i7 + 1;
                    this.bufOff = i8;
                    bArr4[i7] = 45;
                    while (true) {
                        if (this.bufOff - i8 == this.boundary.length || (read3 = this.src.read()) < 0) {
                            break;
                        }
                        byte[] bArr5 = this.buf;
                        int i9 = this.bufOff;
                        byte b = (byte) read3;
                        bArr5[i9] = b;
                        if (b != this.boundary[i9 - i8]) {
                            this.bufOff = i9 + 1;
                            break;
                        }
                        this.bufOff = i9 + 1;
                    }
                    if (this.bufOff - i8 == this.boundary.length) {
                        this.ended = true;
                        return -1;
                    }
                } else if (read2 >= 0) {
                    byte[] bArr6 = this.buf;
                    int i10 = this.bufOff;
                    this.bufOff = i10 + 1;
                    bArr6[i10] = (byte) read2;
                }
            }
            read2 = this.src.read();
            if (read2 == 45) {
            }
            if (read2 != 45) {
            }
        }
        return read;
    }
}
