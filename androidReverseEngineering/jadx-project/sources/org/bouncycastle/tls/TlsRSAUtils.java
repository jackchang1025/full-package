package org.bouncycastle.tls;

import java.io.OutputStream;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public abstract class TlsRSAUtils {
    public static TlsSecret generateEncryptedPreMasterSecret(TlsContext tlsContext, TlsCertificate tlsCertificate, OutputStream outputStream) {
        return TlsUtils.generateEncryptedPreMasterSecret(tlsContext, tlsCertificate.createEncryptor(3), outputStream);
    }
}
