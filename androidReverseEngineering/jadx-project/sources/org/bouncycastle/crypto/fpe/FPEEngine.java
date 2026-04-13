package org.bouncycastle.crypto.fpe;

import org.bouncycastle.crypto.BlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.OutputLengthException;
import org.bouncycastle.crypto.params.FPEParameters;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public abstract class FPEEngine {
    protected final BlockCipher baseCipher;
    protected boolean forEncryption;
    protected FPEParameters fpeParameters;

    public FPEEngine(BlockCipher blockCipher) {
        this.baseCipher = blockCipher;
    }

    public static byte[] toByteArray(short[] sArr) {
        byte[] bArr = new byte[sArr.length * 2];
        for (int i2 = 0; i2 != sArr.length; i2++) {
            Pack.shortToBigEndian(sArr[i2], bArr, i2 * 2);
        }
        return bArr;
    }

    public static short[] toShortArray(byte[] bArr) {
        if ((bArr.length & 1) != 0) {
            throw new IllegalArgumentException("data must be an even number of bytes for a wide radix");
        }
        int length = bArr.length / 2;
        short[] sArr = new short[length];
        for (int i2 = 0; i2 != length; i2++) {
            sArr[i2] = Pack.bigEndianToShort(bArr, i2 * 2);
        }
        return sArr;
    }

    public abstract int decryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4);

    public abstract int encryptBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4);

    public abstract String getAlgorithmName();

    public abstract void init(boolean z2, CipherParameters cipherParameters);

    public int processBlock(byte[] bArr, int i2, int i3, byte[] bArr2, int i4) {
        if (this.fpeParameters == null) {
            throw new IllegalStateException("FPE engine not initialized");
        }
        if (i3 < 0) {
            throw new IllegalArgumentException("input length cannot be negative");
        }
        if (bArr == null || bArr2 == null) {
            throw new NullPointerException("buffer value is null");
        }
        if (bArr.length < i2 + i3) {
            throw new DataLengthException("input buffer too short");
        }
        if (bArr2.length >= i4 + i3) {
            return this.forEncryption ? encryptBlock(bArr, i2, i3, bArr2, i4) : decryptBlock(bArr, i2, i3, bArr2, i4);
        }
        throw new OutputLengthException("output buffer too short");
    }
}
