package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.Xof;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SHAKEDigest extends KeccakDigest implements Xof {
    public SHAKEDigest() {
        this(128);
    }

    private static int checkBitLength(int i2) {
        if (i2 == 128 || i2 == 256) {
            return i2;
        }
        throw new IllegalArgumentException(AbstractC0000a.m12h("'bitLength' ", i2, " not supported for SHAKE"));
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest, org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        return doFinal(bArr, i2, getDigestSize());
    }

    public int doOutput(byte[] bArr, int i2, int i3) {
        if (!this.squeezing) {
            absorbBits(15, 4);
        }
        squeeze(bArr, i2, i3 * 8);
        return i3;
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest, org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "SHAKE" + this.fixedOutputLength;
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest, org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.fixedOutputLength / 4;
    }

    public SHAKEDigest(int i2) {
        super(checkBitLength(i2));
    }

    @Override // org.bouncycastle.crypto.digests.KeccakDigest
    public int doFinal(byte[] bArr, int i2, byte b, int i3) {
        return doFinal(bArr, i2, getDigestSize(), b, i3);
    }

    public SHAKEDigest(SHAKEDigest sHAKEDigest) {
        super(sHAKEDigest);
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doFinal(byte[] bArr, int i2, int i3) {
        int doOutput = doOutput(bArr, i2, i3);
        reset();
        return doOutput;
    }

    public int doFinal(byte[] bArr, int i2, int i3, byte b, int i4) {
        if (i4 < 0 || i4 > 7) {
            throw new IllegalArgumentException("'partialBits' must be in the range [0,7]");
        }
        int i5 = (b & ((1 << i4) - 1)) | (15 << i4);
        int i6 = i4 + 4;
        if (i6 >= 8) {
            absorb((byte) i5);
            i6 -= 8;
            i5 >>>= 8;
        }
        if (i6 > 0) {
            absorbBits(i5, i6);
        }
        squeeze(bArr, i2, i3 * 8);
        reset();
        return i3;
    }
}
