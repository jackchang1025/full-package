package org.bouncycastle.cms.jcajce;

import java.io.IOException;
import java.security.Provider;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.DEROctetString;
import org.bouncycastle.asn1.cms.IssuerAndSerialNumber;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.cert.jcajce.JcaX509CertificateHolder;
import org.bouncycastle.cms.KeyTransRecipientInfoGenerator;
import org.bouncycastle.operator.jcajce.JceAsymmetricKeyWrapper;
import org.bouncycastle.operator.jcajce.JceKTSKeyWrapper;
import org.bouncycastle.util.encoders.Hex;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class JceKTSKeyTransRecipientInfoGenerator extends KeyTransRecipientInfoGenerator {
    private static final byte[] ANONYMOUS_SENDER = Hex.decode("0c14416e6f6e796d6f75732053656e64657220202020");

    public JceKTSKeyTransRecipientInfoGenerator(X509Certificate x509Certificate, String str, int i2) {
        this(x509Certificate, new IssuerAndSerialNumber(new JcaX509CertificateHolder(x509Certificate).toASN1Structure()), str, i2);
    }

    private static byte[] getEncodedRecipID(IssuerAndSerialNumber issuerAndSerialNumber) {
        try {
            return issuerAndSerialNumber.getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new CertificateEncodingException(AbstractC0000a.m8d(e2, new StringBuilder("Cannot process extracted IssuerAndSerialNumber: "))) { // from class: org.bouncycastle.cms.jcajce.JceKTSKeyTransRecipientInfoGenerator.1
                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return e2;
                }
            };
        }
    }

    private static byte[] getEncodedSubKeyId(byte[] bArr) {
        try {
            return new DEROctetString(bArr).getEncoded();
        } catch (IOException e2) {
            throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("Cannot process subject key identifier: "))) { // from class: org.bouncycastle.cms.jcajce.JceKTSKeyTransRecipientInfoGenerator.2
                @Override // java.lang.Throwable
                public Throwable getCause() {
                    return e2;
                }
            };
        }
    }

    public JceKTSKeyTransRecipientInfoGenerator setProvider(String str) {
        ((JceKTSKeyWrapper) this.wrapper).setProvider(str);
        return this;
    }

    private JceKTSKeyTransRecipientInfoGenerator(X509Certificate x509Certificate, IssuerAndSerialNumber issuerAndSerialNumber, String str, int i2) {
        super(issuerAndSerialNumber, new JceKTSKeyWrapper(x509Certificate, str, i2, ANONYMOUS_SENDER, getEncodedRecipID(issuerAndSerialNumber)));
    }

    public JceKTSKeyTransRecipientInfoGenerator setProvider(Provider provider) {
        ((JceKTSKeyWrapper) this.wrapper).setProvider(provider);
        return this;
    }

    public JceKTSKeyTransRecipientInfoGenerator(X509Certificate x509Certificate, AlgorithmIdentifier algorithmIdentifier) {
        super(new IssuerAndSerialNumber(new JcaX509CertificateHolder(x509Certificate).toASN1Structure()), new JceAsymmetricKeyWrapper(algorithmIdentifier, x509Certificate.getPublicKey()));
    }

    public JceKTSKeyTransRecipientInfoGenerator(byte[] bArr, PublicKey publicKey, String str, int i2) {
        super(bArr, new JceKTSKeyWrapper(publicKey, str, i2, ANONYMOUS_SENDER, getEncodedSubKeyId(bArr)));
    }

    public JceKTSKeyTransRecipientInfoGenerator(byte[] bArr, AlgorithmIdentifier algorithmIdentifier, PublicKey publicKey) {
        super(bArr, new JceAsymmetricKeyWrapper(algorithmIdentifier, publicKey));
    }
}
