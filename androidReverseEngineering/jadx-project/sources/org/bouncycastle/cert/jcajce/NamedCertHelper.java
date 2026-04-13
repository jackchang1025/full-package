package org.bouncycastle.cert.jcajce;

import java.security.cert.CertificateFactory;

/* loaded from: classes.dex */
class NamedCertHelper extends CertHelper {
    private final String providerName;

    public NamedCertHelper(String str) {
        this.providerName = str;
    }

    @Override // org.bouncycastle.cert.jcajce.CertHelper
    public CertificateFactory createCertificateFactory(String str) {
        return CertificateFactory.getInstance(str, this.providerName);
    }
}
