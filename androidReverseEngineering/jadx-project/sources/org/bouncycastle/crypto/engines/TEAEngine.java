package org.bouncycastle.crypto.engines;

import android.sun.security.util.DerValue;
import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.KeyParameter;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class TEAEngine implements BlockCipher {
    private static final int block_size = 8;
    private static final int d_sum = -957401312;
    private static final int delta = -1640531527;
    private static final int rounds = 32;
    private int _a;
    private int _b;
    private int _c;
    private int _d;
    private boolean _forEncryption;
    private boolean _initialised = false;

    private int bytesToInt(byte[] bArr, int i2) {
        int i3 = i2 + 1;
        int i4 = i3 + 1;
        int i5 = (bArr[i2] << DerValue.tag_GeneralizedTime) | ((bArr[i3] & 255) << 16);
        int i6 = i4 + 1;
        return (bArr[i6] & 255) | i5 | ((bArr[i4] & 255) << 8);
    }

    private int decryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToInt = bytesToInt(bArr, i2);
        int bytesToInt2 = bytesToInt(bArr, i2 + 4);
        int i4 = d_sum;
        for (int i5 = 0; i5 != 32; i5++) {
            bytesToInt2 -= (((bytesToInt << 4) + this._c) ^ (bytesToInt + i4)) ^ ((bytesToInt >>> 5) + this._d);
            bytesToInt -= (((bytesToInt2 << 4) + this._a) ^ (bytesToInt2 + i4)) ^ ((bytesToInt2 >>> 5) + this._b);
            i4 += 1640531527;
        }
        unpackInt(bytesToInt, bArr2, i3);
        unpackInt(bytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private int encryptBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        int bytesToInt = bytesToInt(bArr, i2);
        int bytesToInt2 = bytesToInt(bArr, i2 + 4);
        int i4 = bytesToInt;
        int i5 = 0;
        for (int i6 = 0; i6 != 32; i6++) {
            i5 -= 1640531527;
            i4 += (((bytesToInt2 << 4) + this._a) ^ (bytesToInt2 + i5)) ^ ((bytesToInt2 >>> 5) + this._b);
            bytesToInt2 += (((i4 << 4) + this._c) ^ (i4 + i5)) ^ ((i4 >>> 5) + this._d);
        }
        unpackInt(i4, bArr2, i3);
        unpackInt(bytesToInt2, bArr2, i3 + 4);
        return 8;
    }

    private void setKey(byte[] bArr) {
        if (bArr.length != 16) {
            throw new IllegalArgumentException("Key size must be 128 bits.");
        }
        this._a = bytesToInt(bArr, 0);
        this._b = bytesToInt(bArr, 4);
        this._c = bytesToInt(bArr, 8);
        this._d = bytesToInt(bArr, 12);
    }

    private void unpackInt(int i2, byte[] bArr, int i3) {
        int i4 = i3 + 1;
        bArr[i3] = (byte) (i2 >>> 24);
        int i5 = i4 + 1;
        bArr[i4] = (byte) (i2 >>> 16);
        bArr[i5] = (byte) (i2 >>> 8);
        bArr[i5 + 1] = (byte) i2;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public String getAlgorithmName() {
        return "TEA";
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int getBlockSize() {
        return 8;
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof KeyParameter)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "invalid parameter passed to TEA init - "));
        }
        this._forEncryption = z2;
        this._initialised = true;
        setKey(((KeyParameter) cipherParameters).getKey());
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public int processBlock(byte[] bArr, int i2, byte[] bArr2, int i3) {
        if (!this._initialised) {
            throw new IllegalStateException(getAlgorithmName() + " not initialised");
        }
        if (i2 + 8 > bArr.length) {
            throw new DataLengthException("input buffer too short");
        }
        if (i3 + 8 <= bArr2.length) {
            return this._forEncryption ? encryptBlock(bArr, i2, bArr2, i3) : decryptBlock(bArr, i2, bArr2, i3);
        }
        throw new OutputLengthException("output buffer too short");
    }

    @Override // org.bouncycastle.crypto.BlockCipher
    public void reset() {
    }
}
