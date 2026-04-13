package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.tls.DigitallySigned;
import org.bouncycastle.tls.crypto.TlsStreamVerifier;
import org.bouncycastle.tls.crypto.TlsVerifier;

/* loaded from: classes.dex */
public abstract class BcTlsVerifier implements TlsVerifier {
    protected final BcTlsCrypto crypto;
    protected final AsymmetricKeyParameter publicKey;

    public BcTlsVerifier(BcTlsCrypto bcTlsCrypto, AsymmetricKeyParameter asymmetricKeyParameter) {
        if (bcTlsCrypto == null) {
            throw new NullPointerException("'crypto' cannot be null");
        }
        if (asymmetricKeyParameter == null) {
            throw new NullPointerException("'publicKey' cannot be null");
        }
        if (asymmetricKeyParameter.isPrivate()) {
            throw new IllegalArgumentException("'publicKey' must be public");
        }
        this.crypto = bcTlsCrypto;
        this.publicKey = asymmetricKeyParameter;
    }

    @Override // org.bouncycastle.tls.crypto.TlsVerifier
    public TlsStreamVerifier getStreamVerifier(DigitallySigned digitallySigned) {
        return null;
    }
}
