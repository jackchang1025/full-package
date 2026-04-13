package org.bouncycastle.tls.crypto.impl.bc;

import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.params.DHPrivateKeyParameters;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class BcTlsDH implements TlsAgreement {
    protected final BcTlsDHDomain domain;
    protected AsymmetricCipherKeyPair localKeyPair;
    protected DHPublicKeyParameters peerPublicKey;

    public BcTlsDH(BcTlsDHDomain bcTlsDHDomain) {
        this.domain = bcTlsDHDomain;
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public TlsSecret calculateSecret() {
        return this.domain.calculateDHAgreement((DHPrivateKeyParameters) this.localKeyPair.getPrivate(), this.peerPublicKey);
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public byte[] generateEphemeral() {
        AsymmetricCipherKeyPair generateKeyPair = this.domain.generateKeyPair();
        this.localKeyPair = generateKeyPair;
        return this.domain.encodePublicKey((DHPublicKeyParameters) generateKeyPair.getPublic());
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public void receivePeerValue(byte[] bArr) {
        this.peerPublicKey = this.domain.decodePublicKey(bArr);
    }
}
