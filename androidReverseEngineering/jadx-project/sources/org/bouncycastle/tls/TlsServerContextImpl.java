package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCrypto;

/* loaded from: classes.dex */
class TlsServerContextImpl extends AbstractTlsContext implements TlsServerContext {
    public TlsServerContextImpl(TlsCrypto tlsCrypto) {
        super(tlsCrypto, 0);
    }

    @Override // org.bouncycastle.tls.TlsContext
    public boolean isServer() {
        return true;
    }
}
