package org.bouncycastle.jcajce.provider.digest;

import java.security.DigestException;
import java.security.MessageDigest;
import org.bouncycastle.crypto.Digest;

/* loaded from: classes.dex */
public class BCMessageDigest extends MessageDigest {
    protected Digest digest;
    protected int digestSize;

    public BCMessageDigest(Digest digest) {
        super(digest.getAlgorithmName());
        this.digest = digest;
        this.digestSize = digest.getDigestSize();
    }

    @Override // java.security.MessageDigestSpi
    public int engineDigest(byte[] bArr, int i2, int i3) {
        int i4 = this.digestSize;
        if (i3 < i4) {
            throw new DigestException("partial digests not returned");
        }
        if (bArr.length - i2 < i4) {
            throw new DigestException("insufficient space in the output buffer to store the digest");
        }
        this.digest.doFinal(bArr, i2);
        return this.digestSize;
    }

    @Override // java.security.MessageDigestSpi
    public int engineGetDigestLength() {
        return this.digestSize;
    }

    @Override // java.security.MessageDigestSpi
    public void engineReset() {
        this.digest.reset();
    }

    @Override // java.security.MessageDigestSpi
    public void engineUpdate(byte b) {
        this.digest.update(b);
    }

    @Override // java.security.MessageDigestSpi
    public byte[] engineDigest() {
        byte[] bArr = new byte[this.digestSize];
        this.digest.doFinal(bArr, 0);
        return bArr;
    }

    @Override // java.security.MessageDigestSpi
    public void engineUpdate(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
