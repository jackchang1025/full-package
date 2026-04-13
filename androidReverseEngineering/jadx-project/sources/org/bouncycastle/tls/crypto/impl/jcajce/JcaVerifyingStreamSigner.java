package org.bouncycastle.tls.crypto.impl.jcajce;

import java.io.OutputStream;
import java.security.Signature;
import java.security.SignatureException;
import org.bouncycastle.jcajce.io.OutputStreamFactory;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsStreamSigner;
import org.bouncycastle.util.io.TeeOutputStream;

/* loaded from: classes.dex */
class JcaVerifyingStreamSigner implements TlsStreamSigner {
    private final OutputStream output;
    private final Signature signer;
    private final Signature verifier;

    public JcaVerifyingStreamSigner(Signature signature, Signature signature2) {
        OutputStream createStream = OutputStreamFactory.createStream(signature);
        OutputStream createStream2 = OutputStreamFactory.createStream(signature2);
        this.signer = signature;
        this.verifier = signature2;
        this.output = new TeeOutputStream(createStream, createStream2);
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamSigner
    public OutputStream getOutputStream() {
        return this.output;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamSigner
    public byte[] getSignature() {
        try {
            byte[] sign = this.signer.sign();
            if (this.verifier.verify(sign)) {
                return sign;
            }
            throw new TlsFatalAlert((short) 80);
        } catch (SignatureException e2) {
            throw new TlsFatalAlert((short) 80, (Throwable) e2);
        }
    }
}
