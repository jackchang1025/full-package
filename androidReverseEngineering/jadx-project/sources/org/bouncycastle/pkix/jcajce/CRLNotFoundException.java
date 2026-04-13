package org.bouncycastle.pkix.jcajce;

import java.security.cert.CertPathValidatorException;

/* loaded from: classes.dex */
class CRLNotFoundException extends CertPathValidatorException {
    public CRLNotFoundException(String str) {
        super(str);
    }

    public CRLNotFoundException(String str, Throwable th) {
        super(str, th);
    }
}
