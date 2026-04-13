package org.bouncycastle.crypto.digests;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SHA3Digest extends KeccakDigest {
    public SHA3Digest() {
        this(256);
    }

    private static int checkBitLength(int i2) {
        if (i2 == 224 || i2 == 256 || i2 == 384 || i2 == 512) {
            return i2;
        }
        throw new IllegalArgumentException(AbstractC0000a.m12h("'bitLength' ", i2, " not supported for SHA-3"));
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest, org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        absorbBits(2, 2);
        return super.doFinal(bArr, i2);
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest, org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SHA3-" + this.fixedOutputLength;
    }

    public SHA3Digest(int i2) {
        super(checkBitLength(i2));
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest
    public int doFinal(byte[] bArr, int i2, byte b, int i3) {
        if (i3 < 0 || i3 > 7) {
            throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
        }
        int i4 = (b & ((1 << i3) - 1)) | (2 << i3);
        int i5 = i3 + 2;
        if (i5 >= 8) {
            absorb((byte) i4);
            i5 -= 8;
            i4 >>>= 8;
        }
        return super.doFinal(bArr, i2, (byte) i4, i5);
    }

    public SHA3Digest(SHA3Digest sHA3Digest) {
        super(sHA3Digest);
    }
}
