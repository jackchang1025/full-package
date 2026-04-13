package org.bouncycastle.jce.provider;

import java.security.cert.CertPath;
import java.security.cert.CertPathValidatorException;

/* loaded from: classes.dex */
class RecoverableCertPathValidatorException extends CertPathValidatorException {
    public RecoverableCertPathValidatorException(String str, Throwable th, CertPath certPath, int i2) {
        super(str, th, certPath, i2);
    }
}
