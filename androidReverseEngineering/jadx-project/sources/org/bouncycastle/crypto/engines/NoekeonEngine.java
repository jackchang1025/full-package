package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.signers.PSSSigner;
import org.bouncycastle.util.Integers;
import org.bouncycastle.util.Pack;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class NoekeonEngine implements BlockCipher {
    private static final int SIZE = 16;
    private static final byte[] roundConstants = {DerValue.TAG_CONTEXT, DerValue.tag_GeneralString, 54, 108, -40, -85, 77, -102, 47, 94, PSSSigner.TRAILER_IMPLICIT, 99, -58, -105, 53, 106, -44};
    private boolean _forEncryption;

    /* renamed from: k */
    private final int[] f1220k = new int[4];
    private boolean _initialised = false;

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bigEndianToInt = Pack.bigEndianToInt(bArr, i2);
        int bigEndianToInt2 = Pack.bigEndianToInt(bArr, i2 + 4);
        int bigEndianToInt3 = Pack.bigEndianToInt(bArr, i2 + 8);
        int bigEndianToInt4 = Pack.bigEndianToInt(bArr, i2 + 12);
        int[] iArr = this.f1220k;
        int i4 = iArr[0];
        int i5 = iArr[1];
        int i6 = iArr[2];
        int i7 = iArr[3];
        int i8 = 16;
        while (true) {
            int i9 = bigEndianToInt ^ bigEndianToInt3;
            int rotateLeft = i9 ^ (Integers.rotateLeft(i9, 8) ^ Integers.rotateLeft(i9, 24));
            int i10 = bigEndianToInt2 ^ i5;
            int i11 = bigEndianToInt4 ^ i7;
            int i12 = i10 ^ i11;
            int rotateLeft2 = (Integers.rotateLeft(i12, 24) ^ Integers.rotateLeft(i12, 8)) ^ i12;
            int i13 = i10 ^ rotateLeft;
            int i14 = (bigEndianToInt3 ^ i6) ^ rotateLeft2;
            int i15 = i11 ^ rotateLeft;
            int i16 = ((bigEndianToInt ^ i4) ^ rotateLeft2) ^ (roundConstants[i8] & 255);
            i8--;
            if (i8 < 0) {
                Pack.intToBigEndian(i16, bArr2, i3);
                Pack.intToBigEndian(i13, bArr2, i3 + 4);
                Pack.intToBigEndian(i14, bArr2, i3 + 8);
                Pack.intToBigEndian(i15, bArr2, i3 + 12);
                return 16;
            }
            int rotateLeft3 = Integers.rotateLeft(i13, 1);
            int rotateLeft4 = Integers.rotateLeft(i14, 5);
            int rotateLeft5 = Integers.rotateLeft(i15, 2);
            int i17 = rotateLeft3 ^ (rotateLeft5 | rotateLeft4);
            int i18 = ~i17;
            int i19 = i16 ^ (rotateLeft4 & i18);
            int i20 = (rotateLeft4 ^ (i18 ^ rotateLeft5)) ^ i19;
            int i21 = i17 ^ (i19 | i20);
            int i22 = rotateLeft5 ^ (i20 & i21);
            bigEndianToInt2 = Integers.rotateLeft(i21, 31);
            bigEndianToInt3 = Integers.rotateLeft(i20, 27);
            int rotateLeft6 = Integers.rotateLeft(i19, 30);
            bigEndianToInt = i22;
            bigEndianToInt4 = rotateLeft6;
        }
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bigEndianToInt = Pack.bigEndianToInt(bArr, i2);
        int bigEndianToInt2 = Pack.bigEndianToInt(bArr, i2 + 4);
        int bigEndianToInt3 = Pack.bigEndianToInt(bArr, i2 + 8);
        int bigEndianToInt4 = Pack.bigEndianToInt(bArr, i2 + 12);
        int[] iArr = this.f1220k;
        int i4 = 0;
        int i5 = iArr[0];
        int i6 = iArr[1];
        int i7 = iArr[2];
        int i8 = iArr[3];
        while (true) {
            int i9 = bigEndianToInt ^ (roundConstants[i4] & 255);
            int i10 = i9 ^ bigEndianToInt3;
            int rotateLeft = i10 ^ (Integers.rotateLeft(i10, 8) ^ Integers.rotateLeft(i10, 24));
            int i11 = bigEndianToInt2 ^ i6;
            int i12 = bigEndianToInt4 ^ i8;
            int i13 = i11 ^ i12;
            int rotateLeft2 = i13 ^ (Integers.rotateLeft(i13, 24) ^ Integers.rotateLeft(i13, 8));
            int i14 = (i9 ^ i5) ^ rotateLeft2;
            int i15 = i11 ^ rotateLeft;
            int i16 = (bigEndianToInt3 ^ i7) ^ rotateLeft2;
            int i17 = i12 ^ rotateLeft;
            i4++;
            if (i4 > 16) {
                Pack.intToBigEndian(i14, bArr2, i3);
                Pack.intToBigEndian(i15, bArr2, i3 + 4);
                Pack.intToBigEndian(i16, bArr2, i3 + 8);
                Pack.intToBigEndian(i17, bArr2, i3 + 12);
                return 16;
            }
            int rotateLeft3 = Integers.rotateLeft(i15, 1);
            int rotateLeft4 = Integers.rotateLeft(i16, 5);
            int rotateLeft5 = Integers.rotateLeft(i17, 2);
            int i18 = rotateLeft3 ^ (rotateLeft5 | rotateLeft4);
            int i19 = ~i18;
            int i20 = i14 ^ (rotateLeft4 & i19);
            int i21 = (rotateLeft4 ^ (i19 ^ rotateLeft5)) ^ i20;
            int i22 = i18 ^ (i20 | i21);
            int i23 = rotateLeft5 ^ (i21 & i22);
            bigEndianToInt2 = Integers.rotateLeft(i22, 31);
            bigEndianToInt3 = Integers.rotateLeft(i21, 27);
            int rotateLeft6 = Integers.rotateLeft(i20, 30);
            bigEndianToInt = i23;
            bigEndianToInt4 = rotateLeft6;
        }
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "Noekeon";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 16;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to Noekeon init - "));
        }
        byte[] key = ((KeyParameter) cipherParameters).getKey();
        if (key.length != 16) {
            throw new IllegalArgumentException("Key length not 128 bits.");
        }
        Pack.bigEndianToInt(key, 0, this.f1220k, 0, 4);
        if (!z2) {
            int[] iArr = this.f1220k;
            int i2 = iArr[0];
            int i3 = iArr[1];
            int i4 = iArr[2];
            int i5 = iArr[3];
            int i6 = i2 ^ i4;
            int rotateLeft = i6 ^ (Integers.rotateLeft(i6, 8) ^ Integers.rotateLeft(i6, 24));
            int i7 = i3 ^ i5;
            int rotateLeft2 = (Integers.rotateLeft(i7, 8) ^ Integers.rotateLeft(i7, 24)) ^ i7;
            int i8 = i3 ^ rotateLeft;
            int i9 = i5 ^ rotateLeft;
            int[] iArr2 = this.f1220k;
            iArr2[0] = i2 ^ rotateLeft2;
            iArr2[1] = i8;
            iArr2[2] = i4 ^ rotateLeft2;
            iArr2[3] = i9;
        }
        this._forEncryption = z2;
        this._initialised = true;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (!this._initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 > bArr.length - 16) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 <= bArr2.length - 16) {
            return this._forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
        }
        throw new OutputLengthException("output buffer too short");
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
