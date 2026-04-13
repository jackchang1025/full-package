package org.bouncycastle.its;

import org.bouncycastle.oer.its.PublicVerificationKey;

/* loaded from: classes.dex */
public class ITSPublicVerificationKey {
    protected final PublicVerificationKey verificationKey;

    public ITSPublicVerificationKey(PublicVerificationKey publicVerificationKey) {
        this.verificationKey = publicVerificationKey;
    }

    public PublicVerificationKey toASN1Structure() {
        return this.verificationKey;
    }
}
