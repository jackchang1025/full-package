package org.bouncycastle.tls;

/* loaded from: classes.dex */
class TlsServerCertificateImpl implements TlsServerCertificate {
    protected Certificate certificate;
    protected CertificateStatus certificateStatus;

    public TlsServerCertificateImpl(Certificate certificate, CertificateStatus certificateStatus) {
        this.certificate = certificate;
        this.certificateStatus = certificateStatus;
    }

    @Override // org.bouncycastle.tls.TlsServerCertificate
    public Certificate getCertificate() {
        return this.certificate;
    }

    @Override // org.bouncycastle.tls.TlsServerCertificate
    public CertificateStatus getCertificateStatus() {
        return this.certificateStatus;
    }
}
