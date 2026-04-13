package org.bouncycastle.tls;

import java.io.InputStream;
import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public interface TlsKeyExchange {
    void generateClientKeyExchange(OutputStream outputStream);

    TlsSecret generatePreMasterSecret();

    byte[] generateServerKeyExchange();

    short[] getClientCertificateTypes();

    void init(TlsContext tlsContext);

    void processClientCertificate(Certificate certificate);

    void processClientCredentials(TlsCredentials tlsCredentials);

    void processClientKeyExchange(InputStream inputStream);

    void processServerCertificate(Certificate certificate);

    void processServerCredentials(TlsCredentials tlsCredentials);

    void processServerKeyExchange(InputStream inputStream);

    boolean requiresCertificateVerify();

    boolean requiresServerKeyExchange();

    void skipClientCredentials();

    void skipServerCredentials();

    void skipServerKeyExchange();
}
