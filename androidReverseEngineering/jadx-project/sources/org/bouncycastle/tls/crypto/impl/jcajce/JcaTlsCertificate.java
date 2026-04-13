package org.bouncycastle.tls.crypto.impl.jcajce;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.math.BigInteger;
import java.security.GeneralSecurityException;
import java.security.PublicKey;
import java.security.cert.CertificateEncodingException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.security.interfaces.RSAPublicKey;
import javax.crypto.interfaces.DHPublicKey;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1OctetString;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsCryptoException;
import org.bouncycastle.tls.crypto.TlsEncryptor;
import org.bouncycastle.tls.crypto.TlsVerifier;

/* loaded from: classes.dex */
public class JcaTlsCertificate implements TlsCertificate {
    protected static final int KU_CRL_SIGN = 6;
    protected static final int KU_DATA_ENCIPHERMENT = 3;
    protected static final int KU_DECIPHER_ONLY = 8;
    protected static final int KU_DIGITAL_SIGNATURE = 0;
    protected static final int KU_ENCIPHER_ONLY = 7;
    protected static final int KU_KEY_AGREEMENT = 4;
    protected static final int KU_KEY_CERT_SIGN = 5;
    protected static final int KU_KEY_ENCIPHERMENT = 2;
    protected static final int KU_NON_REPUDIATION = 1;
    protected final X509Certificate certificate;
    protected final JcaTlsCrypto crypto;
    protected DHPublicKey pubKeyDH;
    protected ECPublicKey pubKeyEC;
    protected PublicKey pubKeyRSA;

    public JcaTlsCertificate(JcaTlsCrypto jcaTlsCrypto, X509Certificate x509Certificate) {
        this.pubKeyDH = null;
        this.pubKeyEC = null;
        this.pubKeyRSA = null;
        this.crypto = jcaTlsCrypto;
        this.certificate = x509Certificate;
    }

    public static JcaTlsCertificate convert(JcaTlsCrypto jcaTlsCrypto, TlsCertificate tlsCertificate) {
        return tlsCertificate instanceof JcaTlsCertificate ? (JcaTlsCertificate) tlsCertificate : new JcaTlsCertificate(jcaTlsCrypto, tlsCertificate.getEncoded());
    }

    public static X509Certificate parseCertificate(JcaJceHelper jcaJceHelper, byte[] bArr) {
        try {
            ByteArrayInputStream byteArrayInputStream = new ByteArrayInputStream(Certificate.getInstance(TlsUtils.readASN1Object(bArr)).getEncoded(ASN1Encoding.DER));
            X509Certificate x509Certificate = (X509Certificate) jcaJceHelper.createCertificateFactory("X.509").generateCertificate(byteArrayInputStream);
            if (byteArrayInputStream.available() == 0) {
                return x509Certificate;
            }
            throw new IOException("Extra data detected in stream");
        } catch (GeneralSecurityException e2) {
            throw new TlsCryptoException("unable to decode certificate", e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsCertificate checkUsageInRole(int i2) {
        if (i2 == 1) {
            validateKeyUsageBit(4);
            this.pubKeyDH = getPubKeyDH();
            return this;
        }
        if (i2 != 2) {
            throw new TlsFatalAlert((short) 46);
        }
        validateKeyUsageBit(4);
        this.pubKeyEC = getPubKeyEC();
        return this;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsEncryptor createEncryptor(int i2) {
        validateKeyUsageBit(2);
        if (i2 != 3) {
            throw new TlsFatalAlert((short) 46);
        }
        PublicKey pubKeyRSA = getPubKeyRSA();
        this.pubKeyRSA = pubKeyRSA;
        return new JcaTlsRSAEncryptor(this.crypto, pubKeyRSA);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsVerifier createVerifier(int i2) {
        validateKeyUsageBit(0);
        if (i2 != 513) {
            if (i2 != 515) {
                if (i2 != 1025) {
                    if (i2 != 1027) {
                        if (i2 != 1281) {
                            if (i2 != 1283) {
                                if (i2 != 1537) {
                                    if (i2 != 1539) {
                                        switch (i2) {
                                            case SignatureScheme.rsa_pss_rsae_sha256 /* 2052 */:
                                            case SignatureScheme.rsa_pss_rsae_sha384 /* 2053 */:
                                            case SignatureScheme.rsa_pss_rsae_sha512 /* 2054 */:
                                                validateRSA_PSS_RSAE();
                                                return new JcaTlsRSAPSSVerifier(this.crypto, getPubKeyRSA(), i2);
                                            case SignatureScheme.ed25519 /* 2055 */:
                                                return new JcaTlsEd25519Verifier(this.crypto, getPubKeyEd25519());
                                            case SignatureScheme.ed448 /* 2056 */:
                                                return new JcaTlsEd448Verifier(this.crypto, getPubKeyEd448());
                                            case SignatureScheme.rsa_pss_pss_sha256 /* 2057 */:
                                            case SignatureScheme.rsa_pss_pss_sha384 /* 2058 */:
                                            case SignatureScheme.rsa_pss_pss_sha512 /* 2059 */:
                                                validateRSA_PSS_PSS(SignatureScheme.getSignatureAlgorithm(i2));
                                                return new JcaTlsRSAPSSVerifier(this.crypto, getPubKeyRSA(), i2);
                                            default:
                                                switch (i2) {
                                                    case SignatureScheme.ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
                                                    case SignatureScheme.ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
                                                    case SignatureScheme.ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                                                        break;
                                                    default:
                                                        throw new TlsFatalAlert((short) 46);
                                                }
                                        }
                                    }
                                }
                            }
                        }
                    }
                }
            }
            return new JcaTlsECDSA13Verifier(this.crypto, getPubKeyEC(), i2);
        }
        validateRSA_PKCS1();
        return new JcaTlsRSAVerifier(this.crypto, getPubKeyRSA());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public byte[] getEncoded() {
        try {
            return this.certificate.getEncoded();
        } catch (CertificateEncodingException e2) {
            throw new TlsCryptoException("unable to encode certificate: " + e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public byte[] getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        byte[] extensionValue = this.certificate.getExtensionValue(aSN1ObjectIdentifier.getId());
        if (extensionValue == null) {
            return null;
        }
        return ((ASN1OctetString) ASN1Primitive.fromByteArray(extensionValue)).getOctets();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public short getLegacySignatureAlgorithm() {
        PublicKey publicKey = getPublicKey();
        if (!supportsKeyUsageBit(0)) {
            return (short) -1;
        }
        if (publicKey instanceof RSAPublicKey) {
            return (short) 1;
        }
        if (publicKey instanceof DSAPublicKey) {
            return (short) 2;
        }
        return publicKey instanceof ECPublicKey ? (short) 3 : (short) -1;
    }

    public DHPublicKey getPubKeyDH() {
        try {
            return (DHPublicKey) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public DSAPublicKey getPubKeyDSS() {
        try {
            return (DSAPublicKey) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public ECPublicKey getPubKeyEC() {
        try {
            return (ECPublicKey) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public PublicKey getPubKeyEd25519() {
        PublicKey publicKey = getPublicKey();
        if (EdDSAParameterSpec.Ed25519.equals(publicKey.getAlgorithm())) {
            return publicKey;
        }
        throw new TlsFatalAlert((short) 46);
    }

    public PublicKey getPubKeyEd448() {
        PublicKey publicKey = getPublicKey();
        if (EdDSAParameterSpec.Ed448.equals(publicKey.getAlgorithm())) {
            return publicKey;
        }
        throw new TlsFatalAlert((short) 46);
    }

    public PublicKey getPubKeyRSA() {
        return getPublicKey();
    }

    public PublicKey getPublicKey() {
        try {
            return this.certificate.getPublicKey();
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 42, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public BigInteger getSerialNumber() {
        return this.certificate.getSerialNumber();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public String getSigAlgOID() {
        return this.certificate.getSigAlgOID();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public ASN1Encodable getSigAlgParams() {
        byte[] sigAlgParams = this.certificate.getSigAlgParams();
        if (sigAlgParams == null) {
            return null;
        }
        ASN1Primitive readASN1Object = TlsUtils.readASN1Object(sigAlgParams);
        TlsUtils.requireDEREncoding(readASN1Object, sigAlgParams);
        return readASN1Object;
    }

    public SubjectPublicKeyInfo getSubjectPublicKeyInfo() {
        return SubjectPublicKeyInfo.getInstance(getPublicKey().getEncoded());
    }

    public X509Certificate getX509Certificate() {
        return this.certificate;
    }

    /* JADX WARN: Can't fix incorrect switch cases order, some code will duplicate */
    /* JADX WARN: Removed duplicated region for block: B:24:0x0035  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean implSupportsSignatureAlgorithm(short s2) {
        String str;
        PublicKey publicKey = getPublicKey();
        switch (s2) {
            case 1:
                if (!supportsRSA_PKCS1() || !(publicKey instanceof RSAPublicKey)) {
                    break;
                }
                break;
            case 2:
                break;
            case 3:
                break;
            case 4:
            case 5:
            case 6:
                if (!supportsRSA_PSS_RSAE() || !(publicKey instanceof RSAPublicKey)) {
                    break;
                }
                break;
            case 7:
                str = EdDSAParameterSpec.Ed25519;
                break;
            case 8:
                str = EdDSAParameterSpec.Ed448;
                break;
            case 9:
            case 10:
            case 11:
                if (!supportsRSA_PSS_PSS(s2) || !(publicKey instanceof RSAPublicKey)) {
                    break;
                }
                break;
            default:
                switch (s2) {
                }
        }
        return false;
    }

    public boolean supportsKeyUsageBit(int i2) {
        boolean[] keyUsage = this.certificate.getKeyUsage();
        return keyUsage == null || (keyUsage.length > i2 && keyUsage[i2]);
    }

    public boolean supportsRSA_PKCS1() {
        return org.bouncycastle.tls.crypto.impl.RSAUtil.supportsPKCS1(getSubjectPublicKeyInfo().getAlgorithm());
    }

    public boolean supportsRSA_PSS_PSS(short s2) {
        return org.bouncycastle.tls.crypto.impl.RSAUtil.supportsPSS_PSS(s2, getSubjectPublicKeyInfo().getAlgorithm());
    }

    public boolean supportsRSA_PSS_RSAE() {
        return org.bouncycastle.tls.crypto.impl.RSAUtil.supportsPSS_RSAE(getSubjectPublicKeyInfo().getAlgorithm());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public boolean supportsSignatureAlgorithm(short s2) {
        if (supportsKeyUsageBit(0)) {
            return implSupportsSignatureAlgorithm(s2);
        }
        return false;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public boolean supportsSignatureAlgorithmCA(short s2) {
        return implSupportsSignatureAlgorithm(s2);
    }

    public void validateKeyUsageBit(int i2) {
        if (!supportsKeyUsageBit(i2)) {
            throw new TlsFatalAlert((short) 46);
        }
    }

    public void validateRSA_PKCS1() {
        if (!supportsRSA_PKCS1()) {
            throw new TlsFatalAlert((short) 46);
        }
    }

    public void validateRSA_PSS_PSS(short s2) {
        if (!supportsRSA_PSS_PSS(s2)) {
            throw new TlsFatalAlert((short) 46);
        }
    }

    public void validateRSA_PSS_RSAE() {
        if (!supportsRSA_PSS_RSAE()) {
            throw new TlsFatalAlert((short) 46);
        }
    }

    public JcaTlsCertificate(JcaTlsCrypto jcaTlsCrypto, byte[] bArr) {
        this(jcaTlsCrypto, parseCertificate(jcaTlsCrypto.getHelper(), bArr));
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsVerifier createVerifier(short s2) {
        switch (s2) {
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                return createVerifier(SignatureScheme.from((short) 8, s2));
            default:
                validateKeyUsageBit(0);
                if (s2 == 1) {
                    validateRSA_PKCS1();
                    return new JcaTlsRSAVerifier(this.crypto, getPubKeyRSA());
                }
                if (s2 == 2) {
                    return new JcaTlsDSAVerifier(this.crypto, getPubKeyDSS());
                }
                if (s2 == 3) {
                    return new JcaTlsECDSAVerifier(this.crypto, getPubKeyEC());
                }
                throw new TlsFatalAlert((short) 46);
        }
    }
}
