package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.Xof;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class Blake2xsDigest implements Xof {
    private static final int DIGEST_LENGTH = 32;
    private static final long MAX_NUMBER_BLOCKS = 4294967296L;
    public static final int UNKNOWN_DIGEST_LENGTH = 65535;
    private long blockPos;
    private byte[] buf;
    private int bufPos;
    private int digestLength;
    private int digestPos;

    /* renamed from: h0, reason: collision with root package name */
    private byte[] f2335h0;
    private Blake2sDigest hash;
    private long nodeOffset;

    public Blake2xsDigest() {
        this(65535);
    }

    private long computeNodeOffset() {
        return this.digestLength * MAX_NUMBER_BLOCKS;
    }

    private int computeStepLength() {
        int i2 = this.digestLength;
        if (i2 == 65535) {
            return 32;
        }
        return Math.min(32, i2 - this.digestPos);
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        return doFinal(bArr, i2, bArr.length);
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doOutput(byte[] bArr, int i2, int i3) {
        if (this.f2335h0 == null) {
            byte[] bArr2 = new byte[this.hash.getDigestSize()];
            this.f2335h0 = bArr2;
            this.hash.doFinal(bArr2, 0);
        }
        int i4 = this.digestLength;
        if (i4 != 65535) {
            if (this.digestPos + i3 > i4) {
                throw new IllegalArgumentException("Output length is above the digest length");
            }
        } else if ((this.blockPos << 5) >= getUnknownMaxLength()) {
            throw new IllegalArgumentException("Maximum length is 2^32 blocks of 32 bytes");
        }
        for (int i5 = 0; i5 < i3; i5++) {
            if (this.bufPos >= 32) {
                Blake2sDigest blake2sDigest = new Blake2sDigest(computeStepLength(), 32, this.nodeOffset);
                byte[] bArr3 = this.f2335h0;
                blake2sDigest.update(bArr3, 0, bArr3.length);
                Arrays.fill(this.buf, (byte) 0);
                blake2sDigest.doFinal(this.buf, 0);
                this.bufPos = 0;
                this.nodeOffset++;
                this.blockPos++;
            }
            byte[] bArr4 = this.buf;
            int i6 = this.bufPos;
            bArr[i5] = bArr4[i6];
            this.bufPos = i6 + 1;
            this.digestPos++;
        }
        return i3;
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "BLAKE2xs";
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.hash.getByteLength();
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.digestLength;
    }

    public long getUnknownMaxLength() {
        return 137438953472L;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.hash.reset();
        this.f2335h0 = null;
        this.bufPos = 32;
        this.digestPos = 0;
        this.blockPos = 0L;
        this.nodeOffset = computeNodeOffset();
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        this.hash.update(b);
    }

    public Blake2xsDigest(int i2) {
        this(i2, null, null, null);
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doFinal(byte[] bArr, int i2, int i3) {
        int doOutput = doOutput(bArr, i2, i3);
        reset();
        return doOutput;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        this.hash.update(bArr, i2, i3);
    }

    public Blake2xsDigest(int i2, byte[] bArr) {
        this(i2, bArr, null, null);
    }

    public Blake2xsDigest(int i2, byte[] bArr, byte[] bArr2, byte[] bArr3) {
        this.f2335h0 = null;
        this.buf = new byte[32];
        this.bufPos = 32;
        this.digestPos = 0;
        this.blockPos = 0L;
        if (i2 < 1 || i2 > 65535) {
            throw new IllegalArgumentException("BLAKE2xs digest length must be between 1 and 2^16-1");
        }
        this.digestLength = i2;
        long computeNodeOffset = computeNodeOffset();
        this.nodeOffset = computeNodeOffset;
        this.hash = new Blake2sDigest(32, bArr, bArr2, bArr3, computeNodeOffset);
    }

    public Blake2xsDigest(Blake2xsDigest blake2xsDigest) {
        this.f2335h0 = null;
        this.buf = new byte[32];
        this.bufPos = 32;
        this.digestPos = 0;
        this.blockPos = 0L;
        this.digestLength = blake2xsDigest.digestLength;
        this.hash = new Blake2sDigest(blake2xsDigest.hash);
        this.f2335h0 = Arrays.clone(blake2xsDigest.f2335h0);
        this.buf = Arrays.clone(blake2xsDigest.buf);
        this.bufPos = blake2xsDigest.bufPos;
        this.digestPos = blake2xsDigest.digestPos;
        this.blockPos = blake2xsDigest.blockPos;
        this.nodeOffset = blake2xsDigest.nodeOffset;
    }
}
