package org.bouncycastle.its;

import org.bouncycastle.oer.its.PublicEncryptionKey;
import org.bouncycastle.oer.its.SymmAlgorithm;

/* loaded from: classes.dex */
public class ITSPublicEncryptionKey {
    protected final PublicEncryptionKey encryptionKey;

    public enum symmAlgorithm {
        aes128Ccm(SymmAlgorithm.aes128Ccm.intValueExact());

        private final int tagValue;

        symmAlgorithm(int i2) {
            this.tagValue = i2;
        }
    }

    public ITSPublicEncryptionKey(PublicEncryptionKey publicEncryptionKey) {
        this.encryptionKey = publicEncryptionKey;
    }

    public PublicEncryptionKey toASN1Structure() {
        return this.encryptionKey;
    }
}
