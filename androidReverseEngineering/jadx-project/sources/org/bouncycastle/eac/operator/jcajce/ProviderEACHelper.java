package org.bouncycastle.eac.operator.jcajce;

import java.security.Provider;
import java.security.Signature;

/* loaded from: classes.dex */
class ProviderEACHelper extends EACHelper {
    private final Provider provider;

    public ProviderEACHelper(Provider provider) {
        this.provider = provider;
    }

    @Override // org.bouncycastle.eac.operator.jcajce.EACHelper
    public Signature createSignature(String str) {
        return Signature.getInstance(str, this.provider);
    }
}
