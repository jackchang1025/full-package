package org.bouncycastle.crypto.engines;

import java.lang.reflect.Array;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class LEAEngine implements BlockCipher {
    private static final int BASEROUNDS = 16;
    private static final int BLOCKSIZE = 16;
    private static final int[] DELTA = {-1007687205, 1147300610, 2044886154, 2027892972, 1902027934, -947529206, -531697110, -440137385};
    private static final int KEY0 = 0;
    private static final int KEY1 = 1;
    private static final int KEY2 = 2;
    private static final int KEY3 = 3;
    private static final int KEY4 = 4;
    private static final int KEY5 = 5;
    private static final int MASK128 = 3;
    private static final int MASK256 = 7;
    private static final int NUMWORDS = 4;
    private static final int NUMWORDS128 = 4;
    private static final int NUMWORDS192 = 6;
    private static final int NUMWORDS256 = 8;
    private static final int ROT1 = 1;
    private static final int ROT11 = 11;
    private static final int ROT13 = 13;
    private static final int ROT17 = 17;
    private static final int ROT3 = 3;
    private static final int ROT5 = 5;
    private static final int ROT6 = 6;
    private static final int ROT9 = 9;
    private boolean forEncryption;
    private final int[] theBlock = new int[4];
    private int[][] theRoundKeys;
    private int theRounds;

    private static int bufLength(byte[] bArr) {
        if (bArr == null) {
            return 0;
        }
        return bArr.length;
    }

    private static void checkBuffer(byte[] bArr, int i2, boolean z2) {
        int bufLength = bufLength(bArr);
        int i3 = i2 + 16;
        if ((i2 < 0 || i3 < 0) || i3 > bufLength) {
            if (!z2) {
                throw new DataLengthException("Input buffer too short.");
            }
        }
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        Pack.littleEndianToInt(bArr, i2, this.theBlock, 0, 4);
        for (int i4 = this.theRounds - 1; i4 >= 0; i4--) {
            decryptRound(i4);
        }
        Pack.intToLittleEndian(this.theBlock, bArr2, i3);
        return 16;
    }

    private void decryptRound(int i2) {
        int[] iArr = this.theRoundKeys[i2];
        int i3 = i2 % 4;
        int rightIndex = rightIndex(i3);
        int[] iArr2 = this.theBlock;
        iArr2[rightIndex] = iArr[1] ^ (ror32(iArr2[rightIndex], 9) - (this.theBlock[i3] ^ iArr[0]));
        int rightIndex2 = rightIndex(rightIndex);
        int[] iArr3 = this.theBlock;
        iArr3[rightIndex2] = (rol32(iArr3[rightIndex2], 5) - (this.theBlock[rightIndex] ^ iArr[2])) ^ iArr[3];
        int rightIndex3 = rightIndex(rightIndex2);
        int[] iArr4 = this.theBlock;
        iArr4[rightIndex3] = iArr[5] ^ (rol32(iArr4[rightIndex3], 3) - (this.theBlock[rightIndex2] ^ iArr[4]));
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        Pack.littleEndianToInt(bArr, i2, this.theBlock, 0, 4);
        for (int i4 = 0; i4 < this.theRounds; i4++) {
            encryptRound(i4);
        }
        Pack.intToLittleEndian(this.theBlock, bArr2, i3);
        return 16;
    }

    private void encryptRound(int i2) {
        int[] iArr = this.theRoundKeys[i2];
        int i3 = (i2 + 3) % 4;
        int leftIndex = leftIndex(i3);
        int[] iArr2 = this.theBlock;
        iArr2[i3] = ror32((iArr[4] ^ iArr2[leftIndex]) + (iArr2[i3] ^ iArr[5]), 3);
        int leftIndex2 = leftIndex(leftIndex);
        int[] iArr3 = this.theBlock;
        iArr3[leftIndex] = ror32((iArr3[leftIndex2] ^ iArr[2]) + (iArr[3] ^ iArr3[leftIndex]), 5);
        int leftIndex3 = leftIndex(leftIndex2);
        int[] iArr4 = this.theBlock;
        iArr4[leftIndex2] = rol32((iArr4[leftIndex3] ^ iArr[0]) + (iArr[1] ^ iArr4[leftIndex2]), 9);
    }

    private void generate128RoundKeys(int[] iArr) {
        for (int i2 = 0; i2 < this.theRounds; i2++) {
            int rol32 = rol32(DELTA[i2 & 3], i2);
            iArr[0] = rol32(iArr[0] + rol32, 1);
            iArr[1] = rol32(iArr[1] + rol32(rol32, 1), 3);
            iArr[2] = rol32(iArr[2] + rol32(rol32, 2), 6);
            iArr[3] = rol32(iArr[3] + rol32(rol32, 3), 11);
            int[] iArr2 = this.theRoundKeys[i2];
            iArr2[0] = iArr[0];
            iArr2[1] = iArr[1];
            iArr2[2] = iArr[2];
            int i3 = iArr[1];
            iArr2[3] = i3;
            iArr2[4] = iArr[3];
            iArr2[5] = i3;
        }
    }

    private void generate192RoundKeys(int[] iArr) {
        for (int i2 = 0; i2 < this.theRounds; i2++) {
            int rol32 = rol32(DELTA[i2 % 6], i2);
            iArr[0] = rol32(iArr[0] + rol32(rol32, 0), 1);
            iArr[1] = rol32(iArr[1] + rol32(rol32, 1), 3);
            iArr[2] = rol32(iArr[2] + rol32(rol32, 2), 6);
            iArr[3] = rol32(iArr[3] + rol32(rol32, 3), 11);
            iArr[4] = rol32(iArr[4] + rol32(rol32, 4), 13);
            iArr[5] = rol32(iArr[5] + rol32(rol32, 5), 17);
            System.arraycopy(iArr, 0, this.theRoundKeys[i2], 0, 6);
        }
    }

    private void generate256RoundKeys(int[] iArr) {
        int i2 = 0;
        for (int i3 = 0; i3 < this.theRounds; i3++) {
            int rol32 = rol32(DELTA[i3 & 7], i3);
            int[] iArr2 = this.theRoundKeys[i3];
            int i4 = i2 & 7;
            int rol322 = rol32(iArr[i4] + rol32, 1);
            iArr2[0] = rol322;
            int i5 = i2 + 1;
            iArr[i4] = rol322;
            int i6 = i5 & 7;
            int rol323 = rol32(iArr[i6] + rol32(rol32, 1), 3);
            iArr2[1] = rol323;
            int i7 = i5 + 1;
            iArr[i6] = rol323;
            int i8 = i7 & 7;
            int rol324 = rol32(iArr[i8] + rol32(rol32, 2), 6);
            iArr2[2] = rol324;
            int i9 = i7 + 1;
            iArr[i8] = rol324;
            int i10 = i9 & 7;
            int rol325 = rol32(iArr[i10] + rol32(rol32, 3), 11);
            iArr2[3] = rol325;
            int i11 = i9 + 1;
            iArr[i10] = rol325;
            int i12 = i11 & 7;
            int rol326 = rol32(iArr[i12] + rol32(rol32, 4), 13);
            iArr2[4] = rol326;
            int i13 = i11 + 1;
            iArr[i12] = rol326;
            int i14 = i13 & 7;
            int rol327 = rol32(iArr[i14] + rol32(rol32, 5), 17);
            iArr2[5] = rol327;
            i2 = i13 + 1;
            iArr[i14] = rol327;
        }
    }

    private void generateRoundKeys(byte[] bArr) {
        int length = (bArr.length >> 1) + 16;
        this.theRounds = length;
        this.theRoundKeys = (int[][]) Array.newInstance((Class<?>) Integer.TYPE, length, 6);
        int length2 = bArr.length / 4;
        int[] iArr = new int[length2];
        Pack.littleEndianToInt(bArr, 0, iArr, 0, length2);
        if (length2 == 4) {
            generate128RoundKeys(iArr);
        } else if (length2 != 6) {
            generate256RoundKeys(iArr);
        } else {
            generate192RoundKeys(iArr);
        }
    }

    private static int leftIndex(int i2) {
        if (i2 == 0) {
            return 3;
        }
        return i2 - 1;
    }

    private static int rightIndex(int i2) {
        if (i2 == 3) {
            return 0;
        }
        return i2 + 1;
    }

    private static int rol32(int i2, int i3) {
        return (i2 >>> (32 - i3)) | (i2 << i3);
    }

    private static int ror32(int i2, int i3) {
        return (i2 << (32 - i3)) | (i2 >>> i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "LEA";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "Invalid parameter passed to LEA init - "));
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        int length = key.length;
        if ((length << 1) % 16 != 0 || length < 16 || length > 32) {
            throw new IllegalArgumentException("KeyBitSize must be 128, 192 or 256");
        }
        this.forEncryption = z2;
        generateRoundKeys(key);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        checkBuffer(bArr, i2, false);
        checkBuffer(bArr2, i3, true);
        return this.forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
