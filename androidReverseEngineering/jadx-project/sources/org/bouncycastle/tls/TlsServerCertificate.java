package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface TlsServerCertificate {
    Certificate getCertificate();

    CertificateStatus getCertificateStatus();
}
