package org.bouncycastle.crypto.modes;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.SkippingStreamCipher;
import org.bouncycastle.crypto.StreamBlockCipher;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SICBlockCipher extends StreamBlockCipher implements SkippingStreamCipher {
    private byte[] IV;
    private final int blockSize;
    private int byteCount;
    private final BlockCipher cipher;
    private byte[] counter;
    private byte[] counterOut;

    public SICBlockCipher(BlockCipher blockCipher) {
        super(blockCipher);
        this.cipher = blockCipher;
        int blockSize = blockCipher.getBlockSize();
        this.blockSize = blockSize;
        this.IV = new byte[blockSize];
        this.counter = new byte[blockSize];
        this.counterOut = new byte[blockSize];
        this.byteCount = 0;
    }

    private void adjustCounter(long j2) {
        int i2 = 5;
        if (j2 >= 0) {
            long j3 = (this.byteCount + j2) / this.blockSize;
            long j4 = j3;
            if (j3 > 255) {
                while (i2 >= 1) {
                    long j5 = 1 << (i2 * 8);
                    while (j4 >= j5) {
                        incrementCounterAt(i2);
                        j4 -= j5;
                    }
                    i2--;
                }
            }
            incrementCounter((int) j4);
            this.byteCount = (int) ((j2 + this.byteCount) - (this.blockSize * j3));
            return;
        }
        long j6 = ((-j2) - this.byteCount) / this.blockSize;
        long j7 = j6;
        if (j6 > 255) {
            while (i2 >= 1) {
                long j8 = 1 << (i2 * 8);
                while (j7 > j8) {
                    decrementCounterAt(i2);
                    j7 -= j8;
                }
                i2--;
            }
        }
        for (long j9 = 0; j9 != j7; j9++) {
            decrementCounterAt(0);
        }
        int i3 = (int) ((this.blockSize * j6) + this.byteCount + j2);
        if (i3 >= 0) {
            this.byteCount = 0;
        } else {
            decrementCounterAt(0);
            this.byteCount = this.blockSize + i3;
        }
    }

    private void checkCounter() {
        if (this.IV.length >= this.blockSize) {
            return;
        }
        int i2 = 0;
        while (true) {
            byte[] bArr = this.IV;
            if (i2 == bArr.length) {
                return;
            }
            if (this.counter[i2] != bArr[i2]) {
                throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
            }
            i2++;
        }
    }

    private void decrementCounterAt(int i2) {
        byte b;
        int length = this.counter.length - i2;
        do {
            length--;
            if (length < 0) {
                return;
            }
            b = (byte) (r1[length] - 1);
            this.counter[length] = b;
        } while (b == -1);
    }

    private void incrementCounter(int i2) {
        byte[] bArr = this.counter;
        byte b = bArr[bArr.length - 1];
        int length = bArr.length - 1;
        bArr[length] = (byte) (bArr[length] + i2);
        if (b == 0 || bArr[bArr.length - 1] >= b) {
            return;
        }
        incrementCounterAt(1);
    }

    private void incrementCounterAt(int i2) {
        byte b;
        int length = this.counter.length - i2;
        do {
            length--;
            if (length < 0) {
                return;
            }
            byte[] bArr = this.counter;
            b = (byte) (bArr[length] + 1);
            bArr[length] = b;
        } while (b == 0);
    }

    private void incrementCounterChecked() {
        byte b;
        int length = this.counter.length;
        do {
            length--;
            if (length < 0) {
                break;
            }
            byte[] bArr = this.counter;
            b = (byte) (bArr[length] + 1);
            bArr[length] = b;
        } while (b == 0);
        byte[] bArr2 = this.IV;
        if (length < bArr2.length && bArr2.length < this.blockSize) {
            throw new IllegalStateException("Counter in CTR/SIC mode out of range.");
        }
    }

    @Override // org.bouncycastle.crypto.StreamBlockCipher
    public byte calculateByte(byte b) {
        int i2 = this.byteCount;
        if (i2 == 0) {
            this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
            byte[] bArr = this.counterOut;
            int i3 = this.byteCount;
            this.byteCount = i3 + 1;
            return (byte) (b ^ bArr[i3]);
        }
        byte[] bArr2 = this.counterOut;
        int i4 = i2 + 1;
        this.byteCount = i4;
        byte b2 = (byte) (b ^ bArr2[i2]);
        if (i4 == this.counter.length) {
            this.byteCount = 0;
            incrementCounterChecked();
        }
        return b2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return this.cipher.getAlgorithmName() + "/SIC";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return this.cipher.getBlockSize();
    }

    @Override // org.bouncycastle.crypto.SkippingCipher
    public long getPosition() {
        byte[] bArr = this.counter;
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        System.arraycopy(bArr, 0, bArr2, 0, length);
        int i2 = length - 1;
        while (i2 >= 1) {
            byte[] bArr3 = this.IV;
            int i3 = i2 < bArr3.length ? (bArr2[i2] & 255) - (bArr3[i2] & 255) : bArr2[i2] & 255;
            if (i3 < 0) {
                int i4 = i2 - 1;
                bArr2[i4] = (byte) (bArr2[i4] - 1);
                i3 += 256;
            }
            bArr2[i2] = (byte) i3;
            i2--;
        }
        return (Pack.bigEndianToLong(bArr2, length - 8) * this.blockSize) + this.byteCount;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ParametersWithIV)) {
            throw new IllegalArgumentException("CTR/SIC mode requires ParametersWithIV");
        }
        ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
        byte[] clone = Arrays.clone(parametersWithIV.getIV());
        this.IV = clone;
        int i2 = this.blockSize;
        if (i2 < clone.length) {
            throw new IllegalArgumentException(AbstractC0000a.m17m(new StringBuilder("CTR/SIC mode requires IV no greater than: "), this.blockSize, " bytes."));
        }
        int i3 = 8 > i2 / 2 ? i2 / 2 : 8;
        if (i2 - clone.length <= i3) {
            if (parametersWithIV.getParameters() != null) {
                this.cipher.init(true, parametersWithIV.getParameters());
            }
            reset();
        } else {
            throw new IllegalArgumentException("CTR/SIC mode requires IV of at least: " + (this.blockSize - i3) + " bytes.");
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (this.byteCount != 0) {
            processBytes(bArr, i2, this.blockSize, bArr2, i3);
        } else {
            int i4 = this.blockSize;
            if (i2 + i4 > bArr.length) {
                throw new DataLengthException("input buffer too small");
            }
            if (i4 + i3 > bArr2.length) {
                throw new OutputLengthException("output buffer too short");
            }
            this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
            for (int i5 = 0; i5 < this.blockSize; i5++) {
                bArr2[i3 + i5] = (byte) (bArr[i2 + i5] ^ this.counterOut[i5]);
            }
            incrementCounterChecked();
        }
        return this.blockSize;
    }

    @Override // org.bouncycastle.crypto.StreamBlockCipher, org.bouncycastle.crypto.StreamCipher
    public int processBytes(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        byte b;
        if (i2 + i3 > bArr.length) {
            throw new DataLengthException("input buffer too small");
        }
        if (i4 + i3 > bArr2.length) {
            throw new OutputLengthException("output buffer too short");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            int i6 = this.byteCount;
            if (i6 == 0) {
                this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
                byte b2 = bArr[i2 + i5];
                byte[] bArr3 = this.counterOut;
                int i7 = this.byteCount;
                this.byteCount = i7 + 1;
                b = (byte) (b2 ^ bArr3[i7]);
            } else {
                byte b3 = bArr[i2 + i5];
                byte[] bArr4 = this.counterOut;
                int i8 = i6 + 1;
                this.byteCount = i8;
                b = (byte) (bArr4[i6] ^ b3);
                if (i8 == this.counter.length) {
                    this.byteCount = 0;
                    incrementCounterChecked();
                }
            }
            bArr2[i4 + i5] = b;
        }
        return i3;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
        Arrays.fill(this.counter, (byte) 0);
        byte[] bArr = this.IV;
        System.arraycopy(bArr, 0, this.counter, 0, bArr.length);
        this.cipher.reset();
        this.byteCount = 0;
    }

    @Override // org.bouncycastle.crypto.SkippingCipher
    public long seekTo(long j2) {
        reset();
        return skip(j2);
    }

    @Override // org.bouncycastle.crypto.SkippingCipher
    public long skip(long j2) {
        adjustCounter(j2);
        checkCounter();
        this.cipher.processBlock(this.counter, 0, this.counterOut, 0);
        return j2;
    }
}
