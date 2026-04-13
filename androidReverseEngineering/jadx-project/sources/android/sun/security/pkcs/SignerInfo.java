package android.sun.security.pkcs;

import android.sun.misc.HexDumpEncoder;
import android.sun.security.util.Debug;
import android.sun.security.util.DerEncoder;
import android.sun.security.util.DerInputStream;
import android.sun.security.util.DerOutputStream;
import android.sun.security.util.DerValue;
import android.sun.security.util.ObjectIdentifier;
import android.sun.security.x509.AlgorithmId;
import android.sun.security.x509.KeyUsageExtension;
import android.sun.security.x509.X500Name;
import java.io.IOException;
import java.io.OutputStream;
import java.math.BigInteger;
import java.security.InvalidKeyException;
import java.security.MessageDigest;
import java.security.Principal;
import java.security.Signature;
import java.security.SignatureException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SignerInfo implements DerEncoder {
    PKCS9Attributes authenticatedAttributes;
    BigInteger certificateSerialNumber;
    AlgorithmId digestAlgorithmId;
    AlgorithmId digestEncryptionAlgorithmId;
    byte[] encryptedDigest;
    X500Name issuerName;
    PKCS9Attributes unauthenticatedAttributes;
    BigInteger version;

    public SignerInfo(DerInputStream derInputStream) {
        this(derInputStream, false);
    }

    @Override // android.sun.security.util.DerEncoder
    public void derEncode(OutputStream outputStream) {
        DerOutputStream derOutputStream = new DerOutputStream();
        derOutputStream.putInteger(this.version);
        DerOutputStream derOutputStream2 = new DerOutputStream();
        this.issuerName.encode(derOutputStream2);
        derOutputStream2.putInteger(this.certificateSerialNumber);
        derOutputStream.write((byte) 48, derOutputStream2);
        this.digestAlgorithmId.encode(derOutputStream);
        PKCS9Attributes pKCS9Attributes = this.authenticatedAttributes;
        if (pKCS9Attributes != null) {
            pKCS9Attributes.encode((byte) -96, derOutputStream);
        }
        this.digestEncryptionAlgorithmId.encode(derOutputStream);
        derOutputStream.putOctetString(this.encryptedDigest);
        PKCS9Attributes pKCS9Attributes2 = this.unauthenticatedAttributes;
        if (pKCS9Attributes2 != null) {
            pKCS9Attributes2.encode((byte) -95, derOutputStream);
        }
        DerOutputStream derOutputStream3 = new DerOutputStream();
        derOutputStream3.write((byte) 48, derOutputStream);
        outputStream.write(derOutputStream3.toByteArray());
    }

    public void encode(DerOutputStream derOutputStream) {
        derEncode(derOutputStream);
    }

    public PKCS9Attributes getAuthenticatedAttributes() {
        return this.authenticatedAttributes;
    }

    public X509Certificate getCertificate(PKCS7 pkcs7) {
        return pkcs7.getCertificate(this.certificateSerialNumber, this.issuerName);
    }

    public ArrayList<X509Certificate> getCertificateChain(PKCS7 pkcs7) {
        boolean z2;
        X509Certificate certificate = pkcs7.getCertificate(this.certificateSerialNumber, this.issuerName);
        if (certificate == null) {
            return null;
        }
        ArrayList<X509Certificate> arrayList = new ArrayList<>();
        arrayList.add(certificate);
        X509Certificate[] certificates = pkcs7.getCertificates();
        if (certificates != null && !certificate.getSubjectDN().equals(certificate.getIssuerDN())) {
            Principal issuerDN = certificate.getIssuerDN();
            int i2 = 0;
            do {
                int i3 = i2;
                while (true) {
                    if (i3 >= certificates.length) {
                        z2 = false;
                        break;
                    }
                    if (issuerDN.equals(certificates[i3].getSubjectDN())) {
                        arrayList.add(certificates[i3]);
                        if (certificates[i3].getSubjectDN().equals(certificates[i3].getIssuerDN())) {
                            i2 = certificates.length;
                        } else {
                            issuerDN = certificates[i3].getIssuerDN();
                            X509Certificate x509Certificate = certificates[i2];
                            certificates[i2] = certificates[i3];
                            certificates[i3] = x509Certificate;
                            i2++;
                        }
                        z2 = true;
                    } else {
                        i3++;
                    }
                }
            } while (z2);
        }
        return arrayList;
    }

    public BigInteger getCertificateSerialNumber() {
        return this.certificateSerialNumber;
    }

    public AlgorithmId getDigestAlgorithmId() {
        return this.digestAlgorithmId;
    }

    public AlgorithmId getDigestEncryptionAlgorithmId() {
        return this.digestEncryptionAlgorithmId;
    }

    public byte[] getEncryptedDigest() {
        return this.encryptedDigest;
    }

    public X500Name getIssuerName() {
        return this.issuerName;
    }

    public PKCS9Attributes getUnauthenticatedAttributes() {
        return this.unauthenticatedAttributes;
    }

    public BigInteger getVersion() {
        return this.version;
    }

    public String toString() {
        HexDumpEncoder hexDumpEncoder = new HexDumpEncoder();
        StringBuilder m22r = AbstractC0000a.m22r("Signer Info for (issuer): " + this.issuerName + "\n", "\tversion: ");
        m22r.append(Debug.toHexString(this.version));
        m22r.append("\n");
        StringBuilder m22r2 = AbstractC0000a.m22r(m22r.toString(), "\tcertificateSerialNumber: ");
        m22r2.append(Debug.toHexString(this.certificateSerialNumber));
        m22r2.append("\n");
        StringBuilder m22r3 = AbstractC0000a.m22r(m22r2.toString(), "\tdigestAlgorithmId: ");
        m22r3.append(this.digestAlgorithmId);
        m22r3.append("\n");
        String sb = m22r3.toString();
        if (this.authenticatedAttributes != null) {
            StringBuilder m22r4 = AbstractC0000a.m22r(sb, "\tauthenticatedAttributes: ");
            m22r4.append(this.authenticatedAttributes);
            m22r4.append("\n");
            sb = m22r4.toString();
        }
        StringBuilder m22r5 = AbstractC0000a.m22r(sb, "\tdigestEncryptionAlgorithmId: ");
        m22r5.append(this.digestEncryptionAlgorithmId);
        m22r5.append("\n");
        StringBuilder m22r6 = AbstractC0000a.m22r(m22r5.toString(), "\tencryptedDigest: \n");
        m22r6.append(hexDumpEncoder.encodeBuffer(this.encryptedDigest));
        m22r6.append("\n");
        String sb2 = m22r6.toString();
        if (this.unauthenticatedAttributes == null) {
            return sb2;
        }
        StringBuilder m22r7 = AbstractC0000a.m22r(sb2, "\tunauthenticatedAttributes: ");
        m22r7.append(this.unauthenticatedAttributes);
        m22r7.append("\n");
        return m22r7.toString();
    }

    public SignerInfo verify(PKCS7 pkcs7) {
        return verify(pkcs7, null);
    }

    public SignerInfo(DerInputStream derInputStream, boolean z2) {
        this.version = derInputStream.getBigInteger();
        DerValue[] sequence = derInputStream.getSequence(2);
        this.issuerName = new X500Name(new DerValue((byte) 48, sequence[0].toByteArray()));
        this.certificateSerialNumber = sequence[1].getBigInteger();
        this.digestAlgorithmId = AlgorithmId.parse(derInputStream.getDerValue());
        if (z2) {
            derInputStream.getSet(0);
        } else if (((byte) derInputStream.peekByte()) == -96) {
            this.authenticatedAttributes = new PKCS9Attributes(derInputStream);
        }
        this.digestEncryptionAlgorithmId = AlgorithmId.parse(derInputStream.getDerValue());
        this.encryptedDigest = derInputStream.getOctetString();
        if (z2) {
            derInputStream.getSet(0);
        } else if (derInputStream.available() != 0 && ((byte) derInputStream.peekByte()) == -95) {
            this.unauthenticatedAttributes = new PKCS9Attributes(derInputStream, true);
        }
        if (derInputStream.available() != 0) {
            throw new ParsingException("extra data at the end");
        }
    }

    public SignerInfo verify(PKCS7 pkcs7, byte[] bArr) {
        byte[] bArr2;
        try {
            try {
                ContentInfo contentInfo = pkcs7.getContentInfo();
                if (bArr == null) {
                    bArr = contentInfo.getContentBytes();
                }
                String name = getDigestAlgorithmId().getName();
                PKCS9Attributes pKCS9Attributes = this.authenticatedAttributes;
                if (pKCS9Attributes != null) {
                    ObjectIdentifier objectIdentifier = (ObjectIdentifier) pKCS9Attributes.getAttributeValue(PKCS9Attribute.CONTENT_TYPE_OID);
                    if (objectIdentifier == null || !objectIdentifier.equals(contentInfo.contentType) || (bArr2 = (byte[]) this.authenticatedAttributes.getAttributeValue(PKCS9Attribute.MESSAGE_DIGEST_OID)) == null) {
                        return null;
                    }
                    byte[] digest = MessageDigest.getInstance(name).digest(bArr);
                    if (bArr2.length != digest.length) {
                        return null;
                    }
                    for (int i2 = 0; i2 < bArr2.length; i2++) {
                        if (bArr2[i2] != digest[i2]) {
                            return null;
                        }
                    }
                    bArr = this.authenticatedAttributes.getDerEncoding();
                }
                String name2 = getDigestEncryptionAlgorithmId().getName();
                String encAlgFromSigAlg = AlgorithmId.getEncAlgFromSigAlg(name2);
                if (encAlgFromSigAlg != null) {
                    name2 = encAlgFromSigAlg;
                }
                Signature signature = Signature.getInstance(AlgorithmId.makeSigAlg(name, name2));
                X509Certificate certificate = getCertificate(pkcs7);
                if (certificate == null) {
                    return null;
                }
                if (certificate.hasUnsupportedCriticalExtension()) {
                    throw new SignatureException("Certificate has unsupported critical extension(s)");
                }
                boolean[] keyUsage = certificate.getKeyUsage();
                if (keyUsage != null) {
                    try {
                        KeyUsageExtension keyUsageExtension = new KeyUsageExtension(keyUsage);
                        boolean booleanValue = ((Boolean) keyUsageExtension.get(KeyUsageExtension.DIGITAL_SIGNATURE)).booleanValue();
                        boolean booleanValue2 = ((Boolean) keyUsageExtension.get(KeyUsageExtension.NON_REPUDIATION)).booleanValue();
                        if (!booleanValue && !booleanValue2) {
                            throw new SignatureException("Key usage restricted: cannot be used for digital signatures");
                        }
                    } catch (IOException unused) {
                        throw new SignatureException("Failed to parse keyUsage extension");
                    }
                }
                signature.initVerify(certificate.getPublicKey());
                signature.update(bArr);
                if (signature.verify(this.encryptedDigest)) {
                    return this;
                }
                return null;
            } catch (InvalidKeyException e2) {
                throw new SignatureException("InvalidKey: " + e2.getMessage());
            }
        } catch (IOException e3) {
            throw new SignatureException(AbstractC0000a.m8d(e3, new StringBuilder("IO error verifying signature:\n")));
        }
    }

    public SignerInfo(X500Name x500Name, BigInteger bigInteger, AlgorithmId algorithmId, PKCS9Attributes pKCS9Attributes, AlgorithmId algorithmId2, byte[] bArr, PKCS9Attributes pKCS9Attributes2) {
        this.version = BigInteger.ONE;
        this.issuerName = x500Name;
        this.certificateSerialNumber = bigInteger;
        this.digestAlgorithmId = algorithmId;
        this.authenticatedAttributes = pKCS9Attributes;
        this.digestEncryptionAlgorithmId = algorithmId2;
        this.encryptedDigest = bArr;
        this.unauthenticatedAttributes = pKCS9Attributes2;
    }

    public SignerInfo(X500Name x500Name, BigInteger bigInteger, AlgorithmId algorithmId, AlgorithmId algorithmId2, byte[] bArr) {
        this.version = BigInteger.ONE;
        this.issuerName = x500Name;
        this.certificateSerialNumber = bigInteger;
        this.digestAlgorithmId = algorithmId;
        this.digestEncryptionAlgorithmId = algorithmId2;
        this.encryptedDigest = bArr;
    }
}
