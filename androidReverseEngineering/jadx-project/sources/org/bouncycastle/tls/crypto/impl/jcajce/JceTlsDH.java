package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.KeyPair;
import javax.crypto.interfaces.DHPrivateKey;
import javax.crypto.interfaces.DHPublicKey;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsSecret;

/* loaded from: classes.dex */
public class JceTlsDH implements TlsAgreement {
    protected final JceTlsDHDomain domain;
    protected KeyPair localKeyPair;
    protected DHPublicKey peerPublicKey;

    public JceTlsDH(JceTlsDHDomain jceTlsDHDomain) {
        this.domain = jceTlsDHDomain;
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public TlsSecret calculateSecret() {
        return this.domain.calculateDHAgreement((DHPrivateKey) this.localKeyPair.getPrivate(), this.peerPublicKey);
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public byte[] generateEphemeral() {
        KeyPair generateKeyPair = this.domain.generateKeyPair();
        this.localKeyPair = generateKeyPair;
        return this.domain.encodePublicKey((DHPublicKey) generateKeyPair.getPublic());
    }

    @Override // org.bouncycastle.tls.crypto.TlsAgreement
    public void receivePeerValue(byte[] bArr) {
        this.peerPublicKey = this.domain.decodePublicKey(bArr);
    }
}
