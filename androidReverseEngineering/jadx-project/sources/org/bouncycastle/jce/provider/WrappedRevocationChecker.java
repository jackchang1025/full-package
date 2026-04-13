package org.bouncycastle.jce.provider;

import java.security.cert.Certificate;
import java.security.cert.PKIXCertPathChecker;
import org.bouncycastle.jcajce.PKIXCertRevocationChecker;
import org.bouncycastle.jcajce.PKIXCertRevocationCheckerParameters;

/* loaded from: classes.dex */
class WrappedRevocationChecker implements PKIXCertRevocationChecker {
    private final PKIXCertPathChecker checker;

    public WrappedRevocationChecker(PKIXCertPathChecker pKIXCertPathChecker) {
        this.checker = pKIXCertPathChecker;
    }

    @Override // org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void check(Certificate certificate) {
        this.checker.check(certificate);
    }

    @Override // org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void initialize(PKIXCertRevocationCheckerParameters pKIXCertRevocationCheckerParameters) {
        this.checker.init(false);
    }

    @Override // org.bouncycastle.jcajce.PKIXCertRevocationChecker
    public void setParameter(String str, Object obj) {
    }
}
