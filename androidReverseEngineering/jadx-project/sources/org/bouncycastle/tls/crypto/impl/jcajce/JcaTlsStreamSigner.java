package org.bouncycastle.tls.crypto.impl.jcajce;

import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsStreamSigner;

/* loaded from: classes.dex */
class JcaTlsStreamSigner implements TlsStreamSigner {
    private final OutputStream output;
    private final Signature signer;

    public JcaTlsStreamSigner(Signature signature) {
        this.signer = signature;
        this.output = OutputStreamFactory.createStream(signature);
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamSigner
    public OutputStream getOutputStream() {
        return this.output;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamSigner
    public byte[] getSignature() {
        try {
            return this.signer.sign();
        } catch (SignatureException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
