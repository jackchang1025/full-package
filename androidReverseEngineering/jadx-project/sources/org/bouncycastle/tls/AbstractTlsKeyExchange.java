package org.bouncycastle.tls;

import java.io.InputStream;

/* loaded from: classes.dex */
public abstract class AbstractTlsKeyExchange implements TlsKeyExchange {
    protected TlsContext context;
    protected int keyExchange;

    public AbstractTlsKeyExchange(int i2) {
        this.keyExchange = i2;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public byte[] generateServerKeyExchange() {
        if (requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 80);
        }
        return null;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public short[] getClientCertificateTypes() {
        return null;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void init(TlsContext tlsContext) {
        this.context = tlsContext;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientCertificate(Certificate certificate) {
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processClientKeyExchange(InputStream inputStream) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerCertificate(Certificate certificate) {
        throw new TlsFatalAlert((short) 80);
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void processServerKeyExchange(InputStream inputStream) {
        if (!requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 10);
        }
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresCertificateVerify() {
        return true;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public boolean requiresServerKeyExchange() {
        return false;
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipClientCredentials() {
    }

    @Override // org.bouncycastle.tls.TlsKeyExchange
    public void skipServerKeyExchange() {
        if (requiresServerKeyExchange()) {
            throw new TlsFatalAlert((short) 10);
        }
    }
}
