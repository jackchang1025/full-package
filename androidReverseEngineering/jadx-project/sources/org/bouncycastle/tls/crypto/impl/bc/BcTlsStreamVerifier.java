package org.bouncycastle.tls.crypto.impl.bc;

import java.io.OutputStream;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.io.SignerOutputStream;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;

/* loaded from: classes.dex */
class BcTlsStreamVerifier implements TlsStreamVerifier {
    private final SignerOutputStream output;
    private final byte[] signature;

    public BcTlsStreamVerifier(Signer signer, byte[] bArr) {
        this.output = new SignerOutputStream(signer);
        this.signature = bArr;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamVerifier
    public OutputStream getOutputStream() {
        return this.output;
    }

    @Override // org.bouncycastle.tls.crypto.TlsStreamVerifier
    public boolean isVerified() {
        return this.output.getSigner().verifySignature(this.signature);
    }
}
