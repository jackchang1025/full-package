package org.bouncycastle.mime.encoding;

import java.io.FilterOutputStream;
import java.io.OutputStream;
import org.bouncycastle.util.encoders.Base64Encoder;

/* loaded from: classes.dex */
public class Base64OutputStream extends FilterOutputStream {
    private static final Base64Encoder ENCODER = new Base64Encoder();
    private static final int INBUF_SIZE = 54;
    private static final int OUTBUF_SIZE = 74;
    private final byte[] inBuf;
    private int inPos;
    private final byte[] outBuf;

    public Base64OutputStream(OutputStream outputStream) {
        super(outputStream);
        this.inBuf = new byte[54];
        byte[] bArr = new byte[74];
        this.outBuf = bArr;
        this.inPos = 0;
        bArr[72] = 13;
        bArr[73] = 10;
    }

    private void encodeBlock(byte[] bArr, int i2) {
        ENCODER.encode(bArr, i2, 54, this.outBuf, 0);
        ((FilterOutputStream) this).out.write(this.outBuf, 0, 74);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream, java.io.Closeable, java.lang.AutoCloseable
    public void close() {
        int i2 = this.inPos;
        if (i2 > 0) {
            int encode = ENCODER.encode(this.inBuf, 0, i2, this.outBuf, 0);
            this.inPos = 0;
            byte[] bArr = this.outBuf;
            int i3 = encode + 1;
            bArr[encode] = 13;
            bArr[i3] = 10;
            ((FilterOutputStream) this).out.write(bArr, 0, i3 + 1);
        }
        ((FilterOutputStream) this).out.close();
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(int i2) {
        byte[] bArr = this.inBuf;
        int i3 = this.inPos;
        int i4 = i3 + 1;
        this.inPos = i4;
        bArr[i3] = (byte) i2;
        if (i4 == 54) {
            encodeBlock(bArr, 0);
            this.inPos = 0;
        }
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr) {
        write(bArr, 0, bArr.length);
    }

    @Override // java.io.FilterOutputStream, java.io.OutputStream
    public void write(byte[] bArr, int i2, int i3) {
        int i4;
        int i5 = this.inPos;
        int i6 = 54 - i5;
        if (i3 < i6) {
            System.arraycopy(bArr, i2, this.inBuf, i5, i3);
            this.inPos += i3;
            return;
        }
        if (i5 > 0) {
            System.arraycopy(bArr, i2, this.inBuf, i5, i6);
            i4 = i6 + 0;
            encodeBlock(this.inBuf, 0);
        } else {
            i4 = 0;
        }
        while (true) {
            int i7 = i3 - i4;
            if (i7 < 54) {
                System.arraycopy(bArr, i2 + i4, this.inBuf, 0, i7);
                this.inPos = i7;
                return;
            } else {
                encodeBlock(bArr, i2 + i4);
                i4 += 54;
            }
        }
    }
}
