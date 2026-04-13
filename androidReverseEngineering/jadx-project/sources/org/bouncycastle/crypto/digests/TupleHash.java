package org.bouncycastle.crypto.digests;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class TupleHash implements Xof, Digest {
    private static final byte[] N_TUPLE_HASH = Strings.toByteArray("TupleHash");
    private final int bitLength;
    private final CSHAKEDigest cshake;
    private boolean firstOutput;
    private final int outputLength;

    public TupleHash(int i2, byte[] bArr) {
        this(i2, bArr, i2 * 2);
    }

    private void wrapUp(int i2) {
        byte[] rightEncode = XofUtils.rightEncode(i2 * 8);
        this.cshake.update(rightEncode, 0, rightEncode.length);
        this.firstOutput = false;
    }

    @Override // org.bouncycastle.crypto.Digest
    public int doFinal(byte[] bArr, int i2) {
        if (this.firstOutput) {
            wrapUp(getDigestSize());
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
        return "TupleHash" + this.cshake.getAlgorithmName().substring(6);
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
        this.firstOutput = true;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte b) {
        byte[] encode = XofUtils.encode(b);
        this.cshake.update(encode, 0, encode.length);
    }

    public TupleHash(int i2, byte[] bArr, int i3) {
        this.cshake = new CSHAKEDigest(i2, N_TUPLE_HASH, bArr);
        this.bitLength = i2;
        this.outputLength = (i3 + 7) / 8;
        reset();
    }

    @Override // org.bouncycastle.crypto.Xof
    public int doFinal(byte[] bArr, int i2, int i3) {
        if (this.firstOutput) {
            wrapUp(getDigestSize());
        }
        int doFinal = this.cshake.doFinal(bArr, i2, i3);
        reset();
        return doFinal;
    }

    @Override // org.bouncycastle.crypto.Digest
    public void update(byte[] bArr, int i2, int i3) {
        byte[] encode = XofUtils.encode(bArr, i2, i3);
        this.cshake.update(encode, 0, encode.length);
    }

    public TupleHash(TupleHash tupleHash) {
        CSHAKEDigest cSHAKEDigest = new CSHAKEDigest(tupleHash.cshake);
        this.cshake = cSHAKEDigest;
        int i2 = cSHAKEDigest.fixedOutputLength;
        this.bitLength = i2;
        this.outputLength = (i2 * 2) / 8;
        this.firstOutput = tupleHash.firstOutput;
    }
}
