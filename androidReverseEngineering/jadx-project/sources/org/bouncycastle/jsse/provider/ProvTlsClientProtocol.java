package org.bouncycastle.jsse.provider;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.TlsClientProtocol;

/* loaded from: classes.dex */
class ProvTlsClientProtocol extends TlsClientProtocol {
    private static final boolean provAcceptRenegotiation = PropertyUtils.getBooleanSystemProperty("org.bouncycastle.jsse.client.acceptRenegotiation", false);
    private final Closeable closeable;

    public ProvTlsClientProtocol(InputStream inputStream, OutputStream outputStream, Closeable closeable) {
        super(inputStream, outputStream);
        this.closeable = closeable;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void closeConnection() {
        this.closeable.close();
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public int getRenegotiationPolicy() {
        return provAcceptRenegotiation ? 2 : 0;
    }
}
