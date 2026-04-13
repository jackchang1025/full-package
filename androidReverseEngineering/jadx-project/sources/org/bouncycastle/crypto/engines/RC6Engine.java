package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RC6Engine implements BlockCipher {
    private static final int LGW = 5;
    private static final int P32 = -1209970333;
    private static final int Q32 = -1640531527;
    private static final int _noRounds = 20;
    private static final int bytesPerWord = 4;
    private static final int wordSize = 32;
    private int[] _S = null;
    private boolean forEncryption;

    private int bytesToWord(byte[] bArr, int i2) {
        int i3 = 0;
        for (int i4 = 3; i4 >= 0; i4--) {
            i3 = (i3 << 8) + (bArr[i4 + i2] & 255);
        }
        return i3;
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToWord = bytesToWord(bArr, i2);
        int bytesToWord2 = bytesToWord(bArr, i2 + 4);
        int bytesToWord3 = bytesToWord(bArr, i2 + 8);
        int bytesToWord4 = bytesToWord(bArr, i2 + 12);
        int[] iArr = this._S;
        int i4 = bytesToWord3 - iArr[43];
        int i5 = bytesToWord - iArr[42];
        int i6 = 20;
        while (i6 >= 1) {
            int rotateLeft = rotateLeft(((i5 * 2) + 1) * i5, 5);
            int rotateLeft2 = rotateLeft(((i4 * 2) + 1) * i4, 5);
            int i7 = i6 * 2;
            int rotateRight = rotateRight(bytesToWord2 - this._S[i7 + 1], rotateLeft) ^ rotateLeft2;
            i6--;
            int i8 = i5;
            i5 = rotateRight(bytesToWord4 - this._S[i7], rotateLeft2) ^ rotateLeft;
            bytesToWord4 = i4;
            i4 = rotateRight;
            bytesToWord2 = i8;
        }
        int[] iArr2 = this._S;
        int i9 = bytesToWord4 - iArr2[1];
        int i10 = bytesToWord2 - iArr2[0];
        wordToBytes(i5, bArr2, i3);
        wordToBytes(i10, bArr2, i3 + 4);
        wordToBytes(i4, bArr2, i3 + 8);
        wordToBytes(i9, bArr2, i3 + 12);
        return 16;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToWord = bytesToWord(bArr, i2);
        int bytesToWord2 = bytesToWord(bArr, i2 + 4);
        int bytesToWord3 = bytesToWord(bArr, i2 + 8);
        int bytesToWord4 = bytesToWord(bArr, i2 + 12);
        int[] iArr = this._S;
        int i4 = bytesToWord2 + iArr[0];
        int i5 = bytesToWord4 + iArr[1];
        int i6 = 1;
        while (i6 <= 20) {
            int rotateLeft = rotateLeft(((i4 * 2) + 1) * i4, 5);
            int rotateLeft2 = rotateLeft(((i5 * 2) + 1) * i5, 5);
            int i7 = i6 * 2;
            int rotateLeft3 = rotateLeft(bytesToWord ^ rotateLeft, rotateLeft2) + this._S[i7];
            int rotateLeft4 = rotateLeft(bytesToWord3 ^ rotateLeft2, rotateLeft) + this._S[i7 + 1];
            i6++;
            bytesToWord3 = i5;
            i5 = rotateLeft3;
            bytesToWord = i4;
            i4 = rotateLeft4;
        }
        int[] iArr2 = this._S;
        int i8 = bytesToWord + iArr2[42];
        int i9 = bytesToWord3 + iArr2[43];
        wordToBytes(i8, bArr2, i3);
        wordToBytes(i4, bArr2, i3 + 4);
        wordToBytes(i9, bArr2, i3 + 8);
        wordToBytes(i5, bArr2, i3 + 12);
        return 16;
    }

    private int rotateLeft(int i2, int i3) {
        return (i2 >>> (-i3)) | (i2 << i3);
    }

    private int rotateRight(int i2, int i3) {
        return (i2 << (-i3)) | (i2 >>> i3);
    }

    private void setKey(byte[] bArr) {
        int[] iArr;
        int length = (bArr.length + 3) / 4;
        int length2 = ((bArr.length + 4) - 1) / 4;
        int[] iArr2 = new int[length2];
        for (int length3 = bArr.length - 1; length3 >= 0; length3--) {
            int i2 = length3 / 4;
            iArr2[i2] = (iArr2[i2] << 8) + (bArr[length3] & 255);
        }
        int[] iArr3 = new int[44];
        this._S = iArr3;
        iArr3[0] = P32;
        int i3 = 1;
        while (true) {
            iArr = this._S;
            if (i3 >= iArr.length) {
                break;
            }
            iArr[i3] = iArr[i3 - 1] + Q32;
            i3++;
        }
        int length4 = length2 > iArr.length ? length2 * 3 : iArr.length * 3;
        int i4 = 0;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        for (int i8 = 0; i8 < length4; i8++) {
            int[] iArr4 = this._S;
            i5 = rotateLeft(iArr4[i4] + i5 + i6, 3);
            iArr4[i4] = i5;
            i6 = rotateLeft(iArr2[i7] + i5 + i6, i6 + i5);
            iArr2[i7] = i6;
            i4 = (i4 + 1) % this._S.length;
            i7 = (i7 + 1) % length2;
        }
    }

    private void wordToBytes(int i2, byte[] bArr, int i3) {
        for (int i4 = 0; i4 < 4; i4++) {
            bArr[i4 + i3] = (byte) i2;
            i2 >>>= 8;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC6";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to RC6 init - "));
        }
        this.forEncryption = z2;
        setKey(((KeyParameter) cipherParameters).getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int blockSize = getBlockSize();
        if (this._S == null) {
            throw new IllegalStateException("RC6 engine not initialised");
        }
        if (i2 + blockSize > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (blockSize + i3 <= bArr2.length) {
            return this.forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
        }
        throw new OutputLengthException("output buffer too short");
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
