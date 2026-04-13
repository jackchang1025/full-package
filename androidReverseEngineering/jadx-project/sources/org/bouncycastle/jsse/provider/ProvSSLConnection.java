package org.bouncycastle.jsse.provider;

import org.bouncycastle.jsse.BCSSLConnection;
import org.bouncycastle.tls.TlsContext;

/* loaded from: classes.dex */
class ProvSSLConnection implements BCSSLConnection {
    protected final ProvSSLSession session;
    protected final TlsContext tlsContext;

    public ProvSSLConnection(TlsContext tlsContext, ProvSSLSession provSSLSession) {
        this.tlsContext = tlsContext;
        this.session = provSSLSession;
    }

    @Override // org.bouncycastle.jsse.BCSSLConnection
    public String getApplicationProtocol() {
        return JsseUtils.getApplicationProtocol(this.tlsContext.getSecurityParametersConnection());
    }

    @Override // org.bouncycastle.jsse.BCSSLConnection
    public byte[] getChannelBinding(String str) {
        TlsContext tlsContext;
        int i2;
        if (str.equals("tls-server-end-point")) {
            tlsContext = this.tlsContext;
            i2 = 0;
        } else {
            if (!str.equals("tls-unique")) {
                throw new UnsupportedOperationException();
            }
            tlsContext = this.tlsContext;
            i2 = 1;
        }
        return tlsContext.exportChannelBinding(i2);
    }

    @Override // org.bouncycastle.jsse.BCSSLConnection
    public ProvSSLSession getSession() {
        return this.session;
    }
}
