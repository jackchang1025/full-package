package org.bouncycastle.tls.crypto.impl.bc;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.x509.Certificate;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.KeyUsage;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.DHPublicKeyParameters;
import org.bouncycastle.crypto.params.DSAPublicKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.Ed25519PublicKeyParameters;
import org.bouncycastle.crypto.params.Ed448PublicKeyParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.util.PublicKeyFactory;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsFatalAlert;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.TlsCertificate;
import org.bouncycastle.tls.crypto.TlsEncryptor;
import org.bouncycastle.tls.crypto.TlsVerifier;
import org.bouncycastle.tls.crypto.impl.RSAUtil;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class BcTlsCertificate implements TlsCertificate {
    protected final Certificate certificate;
    protected final BcTlsCrypto crypto;
    protected DHPublicKeyParameters pubKeyDH;
    protected ECPublicKeyParameters pubKeyEC;
    protected Ed25519PublicKeyParameters pubKeyEd25519;
    protected Ed448PublicKeyParameters pubKeyEd448;
    protected RSAKeyParameters pubKeyRSA;

    public BcTlsCertificate(BcTlsCrypto bcTlsCrypto, Certificate certificate) {
        this.pubKeyDH = null;
        this.pubKeyEC = null;
        this.pubKeyEd25519 = null;
        this.pubKeyEd448 = null;
        this.pubKeyRSA = null;
        this.crypto = bcTlsCrypto;
        this.certificate = certificate;
    }

    public static BcTlsCertificate convert(BcTlsCrypto bcTlsCrypto, TlsCertificate tlsCertificate) {
        return tlsCertificate instanceof BcTlsCertificate ? (BcTlsCertificate) tlsCertificate : new BcTlsCertificate(bcTlsCrypto, tlsCertificate.getEncoded());
    }

    public static Certificate parseCertificate(byte[] bArr) {
        try {
            return Certificate.getInstance(TlsUtils.readASN1Object(bArr));
        } catch (IllegalArgumentException e2) {
            throw new TlsFatalAlert((short) 42, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsCertificate checkUsageInRole(int i2) {
        if (i2 == 1) {
            validateKeyUsage(8);
            this.pubKeyDH = getPubKeyDH();
            return this;
        }
        if (i2 != 2) {
            throw new TlsFatalAlert((short) 46);
        }
        validateKeyUsage(8);
        this.pubKeyEC = getPubKeyEC();
        return this;
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsEncryptor createEncryptor(int i2) {
        validateKeyUsage(32);
        if (i2 != 3) {
            throw new TlsFatalAlert((short) 46);
        }
        RSAKeyParameters pubKeyRSA = getPubKeyRSA();
        this.pubKeyRSA = pubKeyRSA;
        return new BcTlsRSAEncryptor(this.crypto, pubKeyRSA);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public TlsVerifier createVerifier(int i2) {
        validateKeyUsage(128);
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
                                                return new BcTlsRSAPSSVerifier(this.crypto, getPubKeyRSA(), i2);
                                            case SignatureScheme.ed25519 /* 2055 */:
                                                return new BcTlsEd25519Verifier(this.crypto, getPubKeyEd25519());
                                            case SignatureScheme.ed448 /* 2056 */:
                                                return new BcTlsEd448Verifier(this.crypto, getPubKeyEd448());
                                            case SignatureScheme.rsa_pss_pss_sha256 /* 2057 */:
                                            case SignatureScheme.rsa_pss_pss_sha384 /* 2058 */:
                                            case SignatureScheme.rsa_pss_pss_sha512 /* 2059 */:
                                                validateRSA_PSS_PSS(SignatureScheme.getSignatureAlgorithm(i2));
                                                return new BcTlsRSAPSSVerifier(this.crypto, getPubKeyRSA(), i2);
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
            return new BcTlsECDSA13Verifier(this.crypto, getPubKeyEC(), i2);
        }
        validateRSA_PKCS1();
        return new BcTlsRSAVerifier(this.crypto, getPubKeyRSA());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public byte[] getEncoded() {
        return this.certificate.getEncoded(ASN1Encoding.DER);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public byte[] getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        Extension extension;
        Extensions extensions = this.certificate.getTBSCertificate().getExtensions();
        if (extensions == null || (extension = extensions.getExtension(aSN1ObjectIdentifier)) == null) {
            return null;
        }
        return Arrays.clone(extension.getExtnValue().getOctets());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public short getLegacySignatureAlgorithm() {
        AsymmetricKeyParameter publicKey = getPublicKey();
        if (publicKey.isPrivate()) {
            throw new TlsFatalAlert((short) 80);
        }
        if (!supportsKeyUsage(128)) {
            return (short) -1;
        }
        if (publicKey instanceof RSAKeyParameters) {
            return (short) 1;
        }
        if (publicKey instanceof DSAPublicKeyParameters) {
            return (short) 2;
        }
        return publicKey instanceof ECPublicKeyParameters ? (short) 3 : (short) -1;
    }

    public DHPublicKeyParameters getPubKeyDH() {
        try {
            return (DHPublicKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public DSAPublicKeyParameters getPubKeyDSS() {
        try {
            return (DSAPublicKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public ECPublicKeyParameters getPubKeyEC() {
        try {
            return (ECPublicKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public Ed25519PublicKeyParameters getPubKeyEd25519() {
        try {
            return (Ed25519PublicKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public Ed448PublicKeyParameters getPubKeyEd448() {
        try {
            return (Ed448PublicKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public RSAKeyParameters getPubKeyRSA() {
        try {
            return (RSAKeyParameters) getPublicKey();
        } catch (ClassCastException e2) {
            throw new TlsFatalAlert((short) 46, (Throwable) e2);
        }
    }

    public AsymmetricKeyParameter getPublicKey() {
        try {
            return PublicKeyFactory.createKey(this.certificate.getSubjectPublicKeyInfo());
        } catch (RuntimeException e2) {
            throw new TlsFatalAlert((short) 43, (Throwable) e2);
        }
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public BigInteger getSerialNumber() {
        return this.certificate.getSerialNumber().getValue();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public String getSigAlgOID() {
        return this.certificate.getSignatureAlgorithm().getAlgorithm().getId();
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public ASN1Encodable getSigAlgParams() {
        return this.certificate.getSignatureAlgorithm().getParameters();
    }

    public boolean supportsKeyUsage(int i2) {
        KeyUsage fromExtensions;
        Extensions extensions = this.certificate.getTBSCertificate().getExtensions();
        return extensions == null || (fromExtensions = KeyUsage.fromExtensions(extensions)) == null || ((fromExtensions.getBytes()[0] & 255) & i2) == i2;
    }

    public boolean supportsRSA_PKCS1() {
        return RSAUtil.supportsPKCS1(this.certificate.getSubjectPublicKeyInfo().getAlgorithm());
    }

    public boolean supportsRSA_PSS_PSS(short s2) {
        return RSAUtil.supportsPSS_PSS(s2, this.certificate.getSubjectPublicKeyInfo().getAlgorithm());
    }

    public boolean supportsRSA_PSS_RSAE() {
        return RSAUtil.supportsPSS_RSAE(this.certificate.getSubjectPublicKeyInfo().getAlgorithm());
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public boolean supportsSignatureAlgorithm(short s2) {
        return supportsSignatureAlgorithm(s2, 128);
    }

    @Override // org.bouncycastle.tls.crypto.TlsCertificate
    public boolean supportsSignatureAlgorithmCA(short s2) {
        return supportsSignatureAlgorithm(s2, 4);
    }

    public void validateKeyUsage(int i2) {
        if (!supportsKeyUsage(i2)) {
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

    public BcTlsCertificate(BcTlsCrypto bcTlsCrypto, byte[] bArr) {
        this(bcTlsCrypto, parseCertificate(bArr));
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
                validateKeyUsage(128);
                if (s2 == 1) {
                    validateRSA_PKCS1();
                    return new BcTlsRSAVerifier(this.crypto, getPubKeyRSA());
                }
                if (s2 == 2) {
                    return new BcTlsDSAVerifier(this.crypto, getPubKeyDSS());
                }
                if (s2 == 3) {
                    return new BcTlsECDSAVerifier(this.crypto, getPubKeyEC());
                }
                throw new TlsFatalAlert((short) 46);
        }
    }

    public boolean supportsSignatureAlgorithm(short s2, int i2) {
        if (!supportsKeyUsage(i2)) {
            return false;
        }
        AsymmetricKeyParameter publicKey = getPublicKey();
        switch (s2) {
            case 1:
                return supportsRSA_PKCS1() && (publicKey instanceof RSAKeyParameters);
            case 2:
                return publicKey instanceof DSAPublicKeyParameters;
            case 3:
                break;
            case 4:
            case 5:
            case 6:
                return supportsRSA_PSS_RSAE() && (publicKey instanceof RSAKeyParameters);
            case 7:
                return publicKey instanceof Ed25519PublicKeyParameters;
            case 8:
                return publicKey instanceof Ed448PublicKeyParameters;
            case 9:
            case 10:
            case 11:
                return supportsRSA_PSS_PSS(s2) && (publicKey instanceof RSAKeyParameters);
            default:
                switch (s2) {
                }
                return false;
        }
        return publicKey instanceof ECPublicKeyParameters;
    }
}
