package org.bouncycastle.jsse.provider;

import java.io.Closeable;
import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.TlsServerProtocol;

/* loaded from: classes.dex */
class ProvTlsServerProtocol extends TlsServerProtocol {
    private final Closeable closeable;

    public ProvTlsServerProtocol(InputStream inputStream, OutputStream outputStream, Closeable closeable) {
        super(inputStream, outputStream);
        this.closeable = closeable;
    }

    @Override // org.bouncycastle.tls.TlsProtocol
    public void closeConnection() {
        this.closeable.close();
    }
}
