package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.RC5Parameters;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RC532Engine implements BlockCipher {
    private static final int P32 = -1209970333;
    private static final int Q32 = -1640531527;
    private boolean forEncryption;
    private int _noRounds = 12;
    private int[] _S = null;

    private int bytesToWord(byte[] bArr, int i2) {
        return ((bArr[i2 + 3] & 255) << 24) | (bArr[i2] & 255) | ((bArr[i2 + 1] & 255) << 8) | ((bArr[i2 + 2] & 255) << 16);
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToWord = bytesToWord(bArr, i2);
        int bytesToWord2 = bytesToWord(bArr, i2 + 4);
        for (int i4 = this._noRounds; i4 >= 1; i4--) {
            int i5 = i4 * 2;
            bytesToWord2 = rotateRight(bytesToWord2 - this._S[i5 + 1], bytesToWord) ^ bytesToWord;
            bytesToWord = rotateRight(bytesToWord - this._S[i5], bytesToWord2) ^ bytesToWord2;
        }
        wordToBytes(bytesToWord - this._S[0], bArr2, i3);
        wordToBytes(bytesToWord2 - this._S[1], bArr2, i3 + 4);
        return 8;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToWord = bytesToWord(bArr, i2) + this._S[0];
        int bytesToWord2 = bytesToWord(bArr, i2 + 4) + this._S[1];
        for (int i4 = 1; i4 <= this._noRounds; i4++) {
            int i5 = i4 * 2;
            bytesToWord = rotateLeft(bytesToWord ^ bytesToWord2, bytesToWord2) + this._S[i5];
            bytesToWord2 = rotateLeft(bytesToWord2 ^ bytesToWord, bytesToWord) + this._S[i5 + 1];
        }
        wordToBytes(bytesToWord, bArr2, i3);
        wordToBytes(bytesToWord2, bArr2, i3 + 4);
        return 8;
    }

    private int rotateLeft(int i2, int i3) {
        int i4 = i3 & 31;
        return (i2 >>> (32 - i4)) | (i2 << i4);
    }

    private int rotateRight(int i2, int i3) {
        int i4 = i3 & 31;
        return (i2 << (32 - i4)) | (i2 >>> i4);
    }

    private void setKey(byte[] bArr) {
        int[] iArr;
        int length = (bArr.length + 3) / 4;
        int[] iArr2 = new int[length];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            int i3 = i2 / 4;
            iArr2[i3] = iArr2[i3] + ((bArr[i2] & 255) << ((i2 % 4) * 8));
        }
        int[] iArr3 = new int[(this._noRounds + 1) * 2];
        this._S = iArr3;
        iArr3[0] = P32;
        int i4 = 1;
        while (true) {
            iArr = this._S;
            if (i4 >= iArr.length) {
                break;
            }
            iArr[i4] = iArr[i4 - 1] + Q32;
            i4++;
        }
        int length2 = length > iArr.length ? length * 3 : iArr.length * 3;
        int i5 = 0;
        int i6 = 0;
        int i7 = 0;
        int i8 = 0;
        for (int i9 = 0; i9 < length2; i9++) {
            int[] iArr4 = this._S;
            i6 = rotateLeft(iArr4[i5] + i6 + i7, 3);
            iArr4[i5] = i6;
            i7 = rotateLeft(iArr2[i8] + i6 + i7, i7 + i6);
            iArr2[i8] = i7;
            i5 = (i5 + 1) % this._S.length;
            i8 = (i8 + 1) % length;
        }
    }

    private void wordToBytes(int i2, byte[] bArr, int i3) {
        bArr[i3] = (byte) i2;
        bArr[i3 + 1] = (byte) (i2 >> 8);
        bArr[i3 + 2] = (byte) (i2 >> 16);
        bArr[i3 + 3] = (byte) (i2 >> 24);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC5-32";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (cipherParameters instanceof RC5Parameters) {
            RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
            this._noRounds = rC5Parameters.getRounds();
            setKey(rC5Parameters.getKey());
        } else {
            if (!(cipherParameters instanceof KeyParameter)) {
                throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to RC532 init - "));
            }
            setKey(((KeyParameter) cipherParameters).getKey());
        }
        this.forEncryption = z2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        return this.forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
