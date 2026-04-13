package org.bouncycastle.openssl;

import java.io.IOException;
import java.math.BigInteger;
import java.util.ArrayList;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERSequence;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PrivateKeyInfo;
import org.bouncycastle.asn1.x509.DSAParameter;
import org.bouncycastle.asn1.x509.SubjectPublicKeyInfo;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import org.bouncycastle.cert.X509AttributeCertificateHolder;
import org.bouncycastle.cert.X509CRLHolder;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.pkcs.PKCS10CertificationRequest;
import org.bouncycastle.pkcs.PKCS8EncryptedPrivateKeyInfo;
import org.bouncycastle.util.Strings;
import org.bouncycastle.util.io.pem.PemGenerationException;
import org.bouncycastle.util.io.pem.PemHeader;
import org.bouncycastle.util.io.pem.PemObject;
import org.bouncycastle.util.io.pem.PemObjectGenerator;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class MiscPEMGenerator implements PemObjectGenerator {
    private static final ASN1ObjectIdentifier[] dsaOids = {X9ObjectIdentifiers.id_dsa, OIWObjectIdentifiers.dsaWithSHA1};
    private static final byte[] hexEncodingTable = {48, 49, 50, 51, 52, 53, 54, 55, 56, 57, 65, 66, 67, 68, 69, 70};
    private final PEMEncryptor encryptor;
    private final Object obj;

    public MiscPEMGenerator(Object obj) {
        this.obj = obj;
        this.encryptor = null;
    }

    private PemObject createPemObject(Object obj) {
        byte[] encoded;
        String str;
        if (obj instanceof PemObject) {
            return (PemObject) obj;
        }
        if (obj instanceof PemObjectGenerator) {
            return ((PemObjectGenerator) obj).generate();
        }
        if (obj instanceof X509CertificateHolder) {
            encoded = ((X509CertificateHolder) obj).getEncoded();
            str = PEMParser.TYPE_CERTIFICATE;
        } else if (obj instanceof X509CRLHolder) {
            encoded = ((X509CRLHolder) obj).getEncoded();
            str = PEMParser.TYPE_X509_CRL;
        } else if (obj instanceof X509TrustedCertificateBlock) {
            encoded = ((X509TrustedCertificateBlock) obj).getEncoded();
            str = PEMParser.TYPE_TRUSTED_CERTIFICATE;
        } else if (obj instanceof PrivateKeyInfo) {
            PrivateKeyInfo privateKeyInfo = (PrivateKeyInfo) obj;
            ASN1ObjectIdentifier algorithm = privateKeyInfo.getPrivateKeyAlgorithm().getAlgorithm();
            if (algorithm.equals((ASN1Primitive) PKCSObjectIdentifiers.rsaEncryption)) {
                encoded = privateKeyInfo.parsePrivateKey().toASN1Primitive().getEncoded();
                str = PEMParser.TYPE_RSA_PRIVATE_KEY;
            } else {
                ASN1ObjectIdentifier[] aSN1ObjectIdentifierArr = dsaOids;
                if (algorithm.equals((ASN1Primitive) aSN1ObjectIdentifierArr[0]) || algorithm.equals((ASN1Primitive) aSN1ObjectIdentifierArr[1])) {
                    DSAParameter dSAParameter = DSAParameter.getInstance(privateKeyInfo.getPrivateKeyAlgorithm().getParameters());
                    ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
                    aSN1EncodableVector.add(new ASN1Integer(0L));
                    aSN1EncodableVector.add(new ASN1Integer(dSAParameter.getP()));
                    aSN1EncodableVector.add(new ASN1Integer(dSAParameter.getQ()));
                    aSN1EncodableVector.add(new ASN1Integer(dSAParameter.getG()));
                    BigInteger value = ASN1Integer.getInstance(privateKeyInfo.parsePrivateKey()).getValue();
                    aSN1EncodableVector.add(new ASN1Integer(dSAParameter.getG().modPow(value, dSAParameter.getP())));
                    aSN1EncodableVector.add(new ASN1Integer(value));
                    encoded = new DERSequence(aSN1EncodableVector).getEncoded();
                    str = PEMParser.TYPE_DSA_PRIVATE_KEY;
                } else if (algorithm.equals((ASN1Primitive) X9ObjectIdentifiers.id_ecPublicKey)) {
                    encoded = privateKeyInfo.parsePrivateKey().toASN1Primitive().getEncoded();
                    str = PEMParser.TYPE_EC_PRIVATE_KEY;
                } else {
                    encoded = privateKeyInfo.getEncoded();
                    str = PEMParser.TYPE_PRIVATE_KEY;
                }
            }
        } else if (obj instanceof SubjectPublicKeyInfo) {
            encoded = ((SubjectPublicKeyInfo) obj).getEncoded();
            str = PEMParser.TYPE_PUBLIC_KEY;
        } else if (obj instanceof X509AttributeCertificateHolder) {
            encoded = ((X509AttributeCertificateHolder) obj).getEncoded();
            str = PEMParser.TYPE_ATTRIBUTE_CERTIFICATE;
        } else if (obj instanceof PKCS10CertificationRequest) {
            encoded = ((PKCS10CertificationRequest) obj).getEncoded();
            str = PEMParser.TYPE_CERTIFICATE_REQUEST;
        } else if (obj instanceof PKCS8EncryptedPrivateKeyInfo) {
            encoded = ((PKCS8EncryptedPrivateKeyInfo) obj).getEncoded();
            str = PEMParser.TYPE_ENCRYPTED_PRIVATE_KEY;
        } else {
            if (!(obj instanceof ContentInfo)) {
                throw new PemGenerationException("unknown object passed - can't encode.");
            }
            encoded = ((ContentInfo) obj).getEncoded();
            str = PEMParser.TYPE_PKCS7;
        }
        PEMEncryptor pEMEncryptor = this.encryptor;
        if (pEMEncryptor == null) {
            return new PemObject(str, encoded);
        }
        String upperCase = Strings.toUpperCase(pEMEncryptor.getAlgorithm());
        if (upperCase.equals("DESEDE")) {
            upperCase = "DES-EDE3-CBC";
        }
        byte[] iv = this.encryptor.getIV();
        byte[] encrypt = this.encryptor.encrypt(encoded);
        ArrayList arrayList = new ArrayList(2);
        arrayList.add(new PemHeader("Proc-Type", "4,ENCRYPTED"));
        StringBuilder m22r = AbstractC0000a.m22r(upperCase, ",");
        m22r.append(getHexEncoded(iv));
        arrayList.add(new PemHeader("DEK-Info", m22r.toString()));
        return new PemObject(str, arrayList, encrypt);
    }

    private String getHexEncoded(byte[] bArr) {
        char[] cArr = new char[bArr.length * 2];
        for (int i2 = 0; i2 != bArr.length; i2++) {
            int i3 = bArr[i2] & 255;
            int i4 = i2 * 2;
            byte[] bArr2 = hexEncodingTable;
            cArr[i4] = (char) bArr2[i3 >>> 4];
            cArr[i4 + 1] = (char) bArr2[i3 & 15];
        }
        return new String(cArr);
    }

    @Override // org.bouncycastle.util.io.pem.PemObjectGenerator
    public PemObject generate() {
        try {
            return createPemObject(this.obj);
        } catch (IOException e2) {
            throw new PemGenerationException(AbstractC0000a.m8d(e2, new StringBuilder("encoding exception: ")), e2);
        }
    }

    public MiscPEMGenerator(Object obj, PEMEncryptor pEMEncryptor) {
        this.obj = obj;
        this.encryptor = pEMEncryptor;
    }
}
