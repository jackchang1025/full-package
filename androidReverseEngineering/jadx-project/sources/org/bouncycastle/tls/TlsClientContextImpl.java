package org.bouncycastle.tls;

import org.bouncycastle.tls.crypto.TlsCrypto;

/* loaded from: classes.dex */
class TlsClientContextImpl extends AbstractTlsContext implements TlsClientContext {
    public TlsClientContextImpl(TlsCrypto tlsCrypto) {
        super(tlsCrypto, 1);
    }

    @Override // org.bouncycastle.tls.TlsContext
    public boolean isServer() {
        return false;
    }
}
