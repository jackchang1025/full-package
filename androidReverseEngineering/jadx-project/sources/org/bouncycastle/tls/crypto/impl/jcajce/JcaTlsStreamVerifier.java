package org.bouncycastle.tls.crypto.impl.jcajce;

import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;

/* loaded from: classes.dex */
class JcaTlsStreamVerifier implements TlsStreamVerifier {
    private final OutputStream output;
    private final byte[] signature;
    private final Signature verifier;

    public JcaTlsStreamVerifier(Signature signature, byte[] bArr) {
        this.verifier = signature;
        this.output = OutputStreamFactory.createStream(signature);
        this.signature = bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamVerifier
    public OutputStream getOutputStream() {
        return this.output;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamVerifier
    public boolean isVerified() {
        try {
            return this.verifier.verify(this.signature);
        } catch (SignatureException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
