package org.bouncycastle.util.encoders;

/* loaded from: classes.dex */
public class BufferedEncoder {
    protected byte[] buf;
    protected int bufOff;
    protected Translator translator;

    public BufferedEncoder(Translator translator, int i2) {
        this.translator = translator;
        if (i2 % translator.getEncodedBlockSize() != 0) {
            throw new IllegalArgumentException("buffer size not multiple of input block size");
        }
        this.buf = new byte[i2];
        this.bufOff = 0;
    }

    public int processByte(byte b, byte[] bArr, int i2) {
        byte[] bArr2 = this.buf;
        int i3 = this.bufOff;
        int i4 = i3 + 1;
        this.bufOff = i4;
        bArr2[i3] = b;
        if (i4 != bArr2.length) {
            return 0;
        }
        int encode = this.translator.encode(bArr2, 0, bArr2.length, bArr, i2);
        this.bufOff = 0;
        return encode;
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i5 = this.bufOff;
        int i6 = length - i5;
        int i7 = 0;
        if (i3 > i6) {
            System.arraycopy(bArr, i2, bArr3, i5, i6);
            Translator translator = this.translator;
            byte[] bArr4 = this.buf;
            int encode = translator.encode(bArr4, 0, bArr4.length, bArr2, i4) + 0;
            this.bufOff = 0;
            int i8 = i3 - i6;
            int i9 = i2 + i6;
            int i10 = i4 + encode;
            int length2 = i8 - (i8 % this.buf.length);
            i7 = encode + this.translator.encode(bArr, i9, length2, bArr2, i10);
            i3 = i8 - length2;
            i2 = i9 + length2;
        }
        if (i3 != 0) {
            System.arraycopy(bArr, i2, this.buf, this.bufOff, i3);
            this.bufOff += i3;
        }
        return i7;
    }
}
