package org.bouncycastle.cert.jcajce;

import java.security.cert.CertificateFactory;

/* loaded from: classes.dex */
abstract class CertHelper {
    public abstract CertificateFactory createCertificateFactory(String str);

    public CertificateFactory getCertificateFactory(String str) {
        return createCertificateFactory(str);
    }
}
