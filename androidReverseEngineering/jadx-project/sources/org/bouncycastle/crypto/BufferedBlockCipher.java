package org.bouncycastle.crypto;

/* loaded from: classes.dex */
public class BufferedBlockCipher {
    protected byte[] buf;
    protected int bufOff;
    protected BlockCipher cipher;
    protected boolean forEncryption;
    protected boolean partialBlockOkay;
    protected boolean pgpCFB;

    public BufferedBlockCipher() {
    }

    public BufferedBlockCipher(BlockCipher blockCipher) {
        this.cipher = blockCipher;
        this.buf = new byte[blockCipher.getBlockSize()];
        boolean z2 = false;
        this.bufOff = 0;
        String algorithmName = blockCipher.getAlgorithmName();
        int indexOf = algorithmName.indexOf(47) + 1;
        boolean z3 = indexOf > 0 && algorithmName.startsWith("PGP", indexOf);
        this.pgpCFB = z3;
        if (z3 || (blockCipher instanceof StreamCipher)) {
            this.partialBlockOkay = true;
            return;
        }
        if (indexOf > 0 && algorithmName.startsWith("OpenPGP", indexOf)) {
            z2 = true;
        }
        this.partialBlockOkay = z2;
    }

    public int doFinal(byte[] bArr, int i2) {
        try {
            int i3 = this.bufOff;
            if (i2 + i3 > bArr.length) {
                throw new OutputLengthException("output buffer too short for doFinal()");
            }
            int i4 = 0;
            if (i3 != 0) {
                if (!this.partialBlockOkay) {
                    throw new DataLengthException("data not block size aligned");
                }
                BlockCipher blockCipher = this.cipher;
                byte[] bArr2 = this.buf;
                blockCipher.processBlock(bArr2, 0, bArr2, 0);
                int i5 = this.bufOff;
                this.bufOff = 0;
                System.arraycopy(this.buf, 0, bArr, i2, i5);
                i4 = i5;
            }
            return i4;
        } finally {
            reset();
        }
    }

    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    public int getOutputSize(int i2) {
        return i2 + this.bufOff;
    }

    public BlockCipher getUnderlyingCipher() {
        return this.cipher;
    }

    public int getUpdateOutputSize(int i2) {
        int length;
        int i3;
        int i4 = i2 + this.bufOff;
        if (!this.pgpCFB) {
            length = this.buf.length;
        } else {
            if (this.forEncryption) {
                i3 = (i4 % this.buf.length) - (this.cipher.getBlockSize() + 2);
                return i4 - i3;
            }
            length = this.buf.length;
        }
        i3 = i4 % length;
        return i4 - i3;
    }

    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        reset();
        this.cipher.init(z2, cipherParameters);
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
        int processBlock = this.cipher.processBlock(bArr2, 0, bArr, i2);
        this.bufOff = 0;
        return processBlock;
    }

    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        int i5;
        if (i3 < 0) {
            throw new IllegalArgumentException("Can't have a negative input length!");
        }
        int blockSize = getBlockSize();
        int updateOutputSize = getUpdateOutputSize(i3);
        if (updateOutputSize > 0 && updateOutputSize + i4 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        byte[] bArr3 = this.buf;
        int length = bArr3.length;
        int i6 = this.bufOff;
        int i7 = length - i6;
        if (i3 > i7) {
            System.arraycopy(bArr, i2, bArr3, i6, i7);
            i5 = this.cipher.processBlock(this.buf, 0, bArr2, i4) + 0;
            this.bufOff = 0;
            i3 -= i7;
            i2 += i7;
            while (i3 > this.buf.length) {
                i5 += this.cipher.processBlock(bArr, i2, bArr2, i4 + i5);
                i3 -= blockSize;
                i2 += blockSize;
            }
        } else {
            i5 = 0;
        }
        System.arraycopy(bArr, i2, this.buf, this.bufOff, i3);
        int i8 = this.bufOff + i3;
        this.bufOff = i8;
        byte[] bArr4 = this.buf;
        if (i8 != bArr4.length) {
            return i5;
        }
        int processBlock = i5 + this.cipher.processBlock(bArr4, 0, bArr2, i4 + i5);
        this.bufOff = 0;
        return processBlock;
    }

    public void reset() {
        int i2 = 0;
        while (true) {
            byte[] bArr = this.buf;
            if (i2 >= bArr.length) {
                this.bufOff = 0;
                this.cipher.reset();
                return;
            } else {
                bArr[i2] = 0;
                i2++;
            }
        }
    }
}
