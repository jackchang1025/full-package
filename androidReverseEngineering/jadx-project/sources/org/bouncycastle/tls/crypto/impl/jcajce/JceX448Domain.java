package org.bouncycastle.tls.crypto.impl.jcajce;

import java.security.GeneralSecurityException;
import java.security.KeyPair;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.PublicKey;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.jcajce.spec.XDHParameterSpec;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.crypto.TlsAgreement;
import org.bouncycastle.tls.crypto.TlsCryptoException;
import org.bouncycastle.tls.crypto.TlsECDomain;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JceX448Domain implements TlsECDomain {
    protected final JcaTlsCrypto crypto;

    public JceX448Domain(JcaTlsCrypto jcaTlsCrypto) {
        this.crypto = jcaTlsCrypto;
    }

    public JceTlsSecret calculateECDHAgreement(PrivateKey privateKey, PublicKey publicKey) {
        try {
            byte[] calculateKeyAgreement = this.crypto.calculateKeyAgreement(XDHParameterSpec.X448, privateKey, publicKey, "TlsPremasterSecret");
            if (calculateKeyAgreement == null || calculateKeyAgreement.length != 56) {
                throw new TlsCryptoException("invalid secret calculated");
            }
            if (Arrays.areAllZeroes(calculateKeyAgreement, 0, calculateKeyAgreement.length)) {
                throw new TlsFatalAlert((short) 40);
            }
            return this.crypto.adoptLocalSecret(calculateKeyAgreement);
        } catch (GeneralSecurityException e2) {
            throw new TlsCryptoException("cannot calculate secret", e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsECDomain
    public TlsAgreement createECDH() {
        return new JceX448(this);
    }

    public PublicKey decodePublicKey(byte[] bArr) {
        return XDHUtil.decodePublicKey(this.crypto, XDHParameterSpec.X448, EdECObjectIdentifiers.id_X448, bArr);
    }

    public byte[] encodePublicKey(PublicKey publicKey) {
        return XDHUtil.encodePublicKey(publicKey);
    }

    public KeyPair generateKeyPair() {
        try {
            KeyPairGenerator createKeyPairGenerator = this.crypto.getHelper().createKeyPairGenerator(XDHParameterSpec.X448);
            createKeyPairGenerator.initialize(448, this.crypto.getSecureRandom());
            return createKeyPairGenerator.generateKeyPair();
        } catch (GeneralSecurityException e2) {
            throw Exceptions.illegalStateException(AbstractC0000a.m19o(e2, new StringBuilder("unable to create key pair: ")), e2);
        }
    }
}
