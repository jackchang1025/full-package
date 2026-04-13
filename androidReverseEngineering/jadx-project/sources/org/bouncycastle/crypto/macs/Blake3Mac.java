package org.bouncycastle.crypto.macs;

import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Mac;
import org.bouncycastle.crypto.digests.Blake3Digest;
import org.bouncycastle.crypto.params.Blake3Parameters;
import org.bouncycastle.crypto.params.KeyParameter;
import p012o.AbstractC0413b;

/* loaded from: classes.dex */
public class Blake3Mac implements Mac {
    private final Blake3Digest theDigest;

    public Blake3Mac(Blake3Digest blake3Digest) {
        this.theDigest = blake3Digest;
    }

    @Override // org.bouncycastle.crypto.Mac
    public int doFinal(byte[] bArr, int i2) {
        return this.theDigest.doFinal(bArr, i2);
    }

    @Override // org.bouncycastle.crypto.Mac
    public String getAlgorithmName() {
        return this.theDigest.getAlgorithmName() + "Mac";
    }

    @Override // org.bouncycastle.crypto.Mac
    public int getMacSize() {
        return this.theDigest.getDigestSize();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void init(CipherParameters cipherParameters) {
        CipherParameters key = cipherParameters instanceof KeyParameter ? Blake3Parameters.key(((KeyParameter) cipherParameters).getKey()) : cipherParameters;
        if (!(key instanceof Blake3Parameters)) {
            throw new IllegalArgumentException(AbstractC0413b.m1014h(cipherParameters, "Invalid parameter passed to Blake3Mac init - "));
        }
        Blake3Parameters blake3Parameters = (Blake3Parameters) key;
        if (blake3Parameters.getKey() == null) {
            throw new IllegalArgumentException("Blake3Mac requires a key parameter.");
        }
        this.theDigest.init(blake3Parameters);
    }

    @Override // org.bouncycastle.crypto.Mac
    public void reset() {
        this.theDigest.reset();
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte b) {
        this.theDigest.update(b);
    }

    @Override // org.bouncycastle.crypto.Mac
    public void update(byte[] bArr, int i2, int i3) {
        this.theDigest.update(bArr, i2, i3);
    }
}
