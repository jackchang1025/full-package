package org.bouncycastle.tls;

/* loaded from: classes.dex */
public interface TlsAuthentication {
    TlsCredentials getClientCredentials(CertificateRequest certificateRequest);

    void notifyServerCertificate(TlsServerCertificate tlsServerCertificate);
}
