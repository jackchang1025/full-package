package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.tls.crypto.TlsHash;

/* loaded from: classes.dex */
final class BcTlsHash implements TlsHash {
    private final BcTlsCrypto crypto;
    private final int cryptoHashAlgorithm;
    private final Digest digest;

    public BcTlsHash(BcTlsCrypto bcTlsCrypto, int i2) {
        this(bcTlsCrypto, i2, bcTlsCrypto.createDigest(i2));
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public byte[] calculateHash() {
        byte[] bArr = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr, 0);
        return bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public TlsHash cloneHash() {
        BcTlsCrypto bcTlsCrypto = this.crypto;
        int i2 = this.cryptoHashAlgorithm;
        return new BcTlsHash(bcTlsCrypto, i2, bcTlsCrypto.cloneDigest(i2, this.digest));
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void reset() {
        this.digest.reset();
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }

    private BcTlsHash(BcTlsCrypto bcTlsCrypto, int i2, Digest digest) {
        this.crypto = bcTlsCrypto;
        this.cryptoHashAlgorithm = i2;
        this.digest = digest;
    }
}
