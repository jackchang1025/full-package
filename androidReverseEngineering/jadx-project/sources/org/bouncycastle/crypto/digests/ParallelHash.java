package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class ParallelHash implements Xof, Digest {
    private static final byte[] N_PARALLEL_HASH = Strings.toByteArray("ParallelHash");

    /* renamed from: B */
    private final int f1180B;
    private final int bitLength;
    private int bufOff;
    private final byte[] buffer;
    private final CSHAKEDigest compressor;
    private final byte[] compressorBuffer;
    private final CSHAKEDigest cshake;
    private boolean firstOutput;
    private int nCount;
    private final int outputLength;

    public ParallelHash(int i2, byte[] bArr, int i3) {
        this(i2, bArr, i3, i2 * 2);
    }

    private void compress() {
        compress(this.buffer, 0, this.bufOff);
        this.bufOff = 0;
    }

    private void wrapUp(int i2) {
        if (this.bufOff != 0) {
            compress();
        }
        byte[] rightEncode = XofUtils.rightEncode(this.nCount);
        byte[] rightEncode2 = XofUtils.rightEncode(i2 * 8);
        this.cshake.update(rightEncode, 0, rightEncode.length);
        this.cshake.update(rightEncode2, 0, rightEncode2.length);
        this.firstOutput = false;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        if (this.firstOutput) {
            wrapUp(this.outputLength);
        }
        int doFinal = this.cshake.doFinal(bArr, i2, getDigestSize());
        reset();
        return doFinal;
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doOutput(byte[] bArr, int i2, int i3) {
        if (this.firstOutput) {
            wrapUp(0);
        }
        return this.cshake.doOutput(bArr, i2, i3);
    }

    @Override // org.bouncycastle.crypto.Digest
    public String getAlgorithmName() {
        return "ParallelHash" + this.cshake.getAlgorithmName().substring(6);
    }

    @Override // org.bouncycastle.crypto.ExtendedDigest
    public int getByteLength() {
        return this.cshake.getByteLength();
    }

    @Override // org.bouncycastle.crypto.Digest
    public int getDigestSize() {
        return this.outputLength;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void reset() {
        this.cshake.reset();
        Arrays.clear(this.buffer);
        byte[] leftEncode = XofUtils.leftEncode(this.f1180B);
        this.cshake.update(leftEncode, 0, leftEncode.length);
        this.nCount = 0;
        this.bufOff = 0;
        this.firstOutput = true;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] bArr = this.buffer;
        int i2 = this.bufOff;
        int i3 = i2 + 1;
        this.bufOff = i3;
        bArr[i2] = b;
        if (i3 == bArr.length) {
            compress();
        }
    }

    public ParallelHash(int i2, byte[] bArr, int i3, int i4) {
        this.cshake = new CSHAKEDigest(i2, N_PARALLEL_HASH, bArr);
        this.compressor = new CSHAKEDigest(i2, new byte[0], new byte[0]);
        this.bitLength = i2;
        this.f1180B = i3;
        this.outputLength = (i4 + 7) / 8;
        this.buffer = new byte[i3];
        this.compressorBuffer = new byte[(i2 * 2) / 8];
        reset();
    }

    private void compress(byte[] bArr, int i2, int i3) {
        this.compressor.update(bArr, i2, i3);
        CSHAKEDigest cSHAKEDigest = this.compressor;
        byte[] bArr2 = this.compressorBuffer;
        cSHAKEDigest.doFinal(bArr2, 0, bArr2.length);
        CSHAKEDigest cSHAKEDigest2 = this.cshake;
        byte[] bArr3 = this.compressorBuffer;
        cSHAKEDigest2.update(bArr3, 0, bArr3.length);
        this.nCount++;
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doFinal(byte[] bArr, int i2, int i3) {
        if (this.firstOutput) {
            wrapUp(this.outputLength);
        }
        int doFinal = this.cshake.doFinal(bArr, i2, i3);
        reset();
        return doFinal;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        int i4 = 0;
        int max = Math.max(0, i3);
        if (this.bufOff != 0) {
            while (i4 < max) {
                int i5 = this.bufOff;
                byte[] bArr2 = this.buffer;
                if (i5 == bArr2.length) {
                    break;
                }
                this.bufOff = i5 + 1;
                bArr2[i5] = bArr[i4 + i2];
                i4++;
            }
            if (this.bufOff == this.buffer.length) {
                compress();
            }
        }
        if (i4 < max) {
            while (true) {
                int i6 = max - i4;
                int i7 = this.f1180B;
                if (i6 <= i7) {
                    break;
                }
                compress(bArr, i2 + i4, i7);
                i4 += this.f1180B;
            }
        }
        while (i4 < max) {
            update(bArr[i4 + i2]);
            i4++;
        }
    }

    public ParallelHash(ParallelHash parallelHash) {
        this.cshake = new CSHAKEDigest(parallelHash.cshake);
        this.compressor = new CSHAKEDigest(parallelHash.compressor);
        this.bitLength = parallelHash.bitLength;
        this.f1180B = parallelHash.f1180B;
        this.outputLength = parallelHash.outputLength;
        this.buffer = Arrays.clone(parallelHash.buffer);
        this.compressorBuffer = Arrays.clone(parallelHash.compressorBuffer);
    }
}
