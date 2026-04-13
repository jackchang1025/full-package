package org.bouncycastle.tsp.ers;

import java.io.File;
import java.io.FileInputStream;
import java.io.InputStream;
import org.bouncycastle.operator.DigestCalculator;

/* loaded from: classes.dex */
public class ERSInputStreamData extends ERSCachingData {
    private final InputStream content;

    public ERSInputStreamData(File file) {
        if (file.isDirectory()) {
            throw new IllegalArgumentException("directory not allowed");
        }
        this.content = new FileInputStream(file);
    }

    @Override // org.bouncycastle.tsp.ers.ERSCachingData
    public byte[] calculateHash(DigestCalculator digestCalculator) {
        return ERSUtil.calculateDigest(digestCalculator, this.content);
    }

    public ERSInputStreamData(InputStream inputStream) {
        this.content = inputStream;
    }
}
