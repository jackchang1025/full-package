package org.bouncycastle.cert.dane;

import java.io.OutputStream;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public class TruncatingDigestCalculator implements DigestCalculator {
    private final DigestCalculator baseCalculator;
    private final int length;

    public TruncatingDigestCalculator(DigestCalculator digestCalculator) {
        this(digestCalculator, 28);
    }

    @Override // org.bouncycastle.operator.DigestCalculator
    public AlgorithmIdentifier getAlgorithmIdentifier() {
        return this.baseCalculator.getAlgorithmIdentifier();
    }

    @Override // org.bouncycastle.operator.DigestCalculator
    public byte[] getDigest() {
        int i2 = this.length;
        byte[] bArr = new byte[i2];
        System.arraycopy(this.baseCalculator.getDigest(), 0, bArr, 0, i2);
        return bArr;
    }

    @Override // org.bouncycastle.operator.DigestCalculator
    public OutputStream getOutputStream() {
        return this.baseCalculator.getOutputStream();
    }

    public TruncatingDigestCalculator(DigestCalculator digestCalculator, int i2) {
        this.baseCalculator = digestCalculator;
        this.length = i2;
    }
}
