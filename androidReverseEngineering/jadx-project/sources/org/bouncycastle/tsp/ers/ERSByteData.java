package org.bouncycastle.tsp.ers;

import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public class ERSByteData extends ERSCachingData {
    private final byte[] content;

    public ERSByteData(byte[] bArr) {
        this.content = bArr;
    }

    @Override // org.bouncycastle.tsp.ers.ERSCachingData
    public byte[] calculateHash(DigestCalculator digestCalculator) {
        return ERSUtil.calculateDigest(digestCalculator, this.content);
    }
}
