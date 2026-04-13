package org.bouncycastle.crypto.engines;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.params.RC5Parameters;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class RC564Engine implements BlockCipher {
    private static final long P64 = -5196783011329398165L;
    private static final long Q64 = -7046029254386353131L;
    private static final int bytesPerWord = 8;
    private static final int wordSize = 64;
    private boolean forEncryption;
    private int _noRounds = 12;
    private long[] _S = null;

    private long bytesToWord(byte[] bArr, int i2) {
        long j2 = 0;
        for (int i3 = 7; i3 >= 0; i3--) {
            j2 = (j2 << 8) + (bArr[i3 + i2] & 255);
        }
        return j2;
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        long bytesToWord = bytesToWord(bArr, i2);
        long bytesToWord2 = bytesToWord(bArr, i2 + 8);
        for (int i4 = this._noRounds; i4 >= 1; i4--) {
            int i5 = i4 * 2;
            bytesToWord2 = rotateRight(bytesToWord2 - this._S[i5 + 1], bytesToWord) ^ bytesToWord;
            bytesToWord = rotateRight(bytesToWord - this._S[i5], bytesToWord2) ^ bytesToWord2;
        }
        wordToBytes(bytesToWord - this._S[0], bArr2, i3);
        wordToBytes(bytesToWord2 - this._S[1], bArr2, i3 + 8);
        return 16;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        long bytesToWord = bytesToWord(bArr, i2) + this._S[0];
        long bytesToWord2 = bytesToWord(bArr, i2 + 8) + this._S[1];
        for (int i4 = 1; i4 <= this._noRounds; i4++) {
            int i5 = i4 * 2;
            bytesToWord = rotateLeft(bytesToWord ^ bytesToWord2, bytesToWord2) + this._S[i5];
            bytesToWord2 = rotateLeft(bytesToWord2 ^ bytesToWord, bytesToWord) + this._S[i5 + 1];
        }
        wordToBytes(bytesToWord, bArr2, i3);
        wordToBytes(bytesToWord2, bArr2, i3 + 8);
        return 16;
    }

    private long rotateLeft(long j2, long j3) {
        long j4 = j3 & 63;
        return (j2 >>> ((int) (64 - j4))) | (j2 << ((int) j4));
    }

    private long rotateRight(long j2, long j3) {
        long j4 = j3 & 63;
        return (j2 << ((int) (64 - j4))) | (j2 >>> ((int) j4));
    }

    private void setKey(byte[] bArr) {
        long[] jArr;
        int length = (bArr.length + 7) / 8;
        long[] jArr2 = new long[length];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            int i3 = i2 / 8;
            jArr2[i3] = jArr2[i3] + ((bArr[i2] & 255) << ((i2 % 8) * 8));
        }
        long[] jArr3 = new long[(this._noRounds + 1) * 2];
        this._S = jArr3;
        jArr3[0] = -5196783011329398165L;
        int i4 = 1;
        while (true) {
            jArr = this._S;
            if (i4 >= jArr.length) {
                break;
            }
            jArr[i4] = jArr[i4 - 1] + Q64;
            i4++;
        }
        int length2 = length > jArr.length ? length * 3 : jArr.length * 3;
        long j2 = 0;
        long j3 = 0;
        int i5 = 0;
        int i6 = 0;
        for (int i7 = 0; i7 < length2; i7++) {
            long[] jArr4 = this._S;
            j2 = rotateLeft(jArr4[i5] + j2 + j3, 3L);
            jArr4[i5] = j2;
            j3 = rotateLeft(jArr2[i6] + j2 + j3, j3 + j2);
            jArr2[i6] = j3;
            i5 = (i5 + 1) % this._S.length;
            i6 = (i6 + 1) % length;
        }
    }

    private void wordToBytes(long j2, byte[] bArr, int i2) {
        for (int i3 = 0; i3 < 8; i3++) {
            bArr[i3 + i2] = (byte) j2;
            j2 >>>= 8;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "RC5-64";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof RC5Parameters)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to RC564 init - "));
        }
        RC5Parameters rC5Parameters = (RC5Parameters) cipherParameters;
        this.forEncryption = z2;
        this._noRounds = rC5Parameters.getRounds();
        setKey(rC5Parameters.getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        return this.forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
