package org.bouncycastle.tls;

/* loaded from: classes.dex */
public abstract class ServerOnlyTlsAuthentication implements TlsAuthentication {
    @Override // org.bouncycastle.tls.TlsAuthentication
    public final TlsCredentials getClientCredentials(CertificateRequest certificateRequest) {
        return null;
    }
}
