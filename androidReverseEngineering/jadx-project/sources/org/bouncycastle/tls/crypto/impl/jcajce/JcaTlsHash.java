package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.MessageDigest;
import org.bouncycastle.tls.crypto.TlsHash;

/* loaded from: classes.dex */
public class JcaTlsHash implements TlsHash {
    private final MessageDigest digest;

    public JcaTlsHash(MessageDigest messageDigest) {
        this.digest = messageDigest;
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public byte[] calculateHash() {
        return this.digest.digest();
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public TlsHash cloneHash() {
        try {
            return new JcaTlsHash((MessageDigest) this.digest.clone());
        } catch (CloneNotSupportedException unused) {
            throw new UnsupportedOperationException("unable to clone digest");
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void reset() {
        this.digest.reset();
    }

    @Override // org.bouncycastle.tls.crypto.TlsHash
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
