package org.bouncycastle.eac.jcajce;

import java.security.KeyFactory;
import java.security.Provider;

/* loaded from: classes.dex */
class ProviderEACHelper implements EACHelper {
    private final Provider provider;

    public ProviderEACHelper(Provider provider) {
        this.provider = provider;
    }

    @Override // org.bouncycastle.eac.jcajce.EACHelper
    public KeyFactory createKeyFactory(String str) {
        return KeyFactory.getInstance(str, this.provider);
    }
}
