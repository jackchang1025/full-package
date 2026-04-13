package org.bouncycastle.tls.crypto;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;

/* loaded from: classes.dex */
public interface TlsCertificate {
    TlsCertificate checkUsageInRole(int i2);

    TlsEncryptor createEncryptor(int i2);

    TlsVerifier createVerifier(int i2);

    TlsVerifier createVerifier(short s2);

    byte[] getEncoded();

    byte[] getExtension(ASN1ObjectIdentifier aSN1ObjectIdentifier);

    short getLegacySignatureAlgorithm();

    BigInteger getSerialNumber();

    String getSigAlgOID();

    ASN1Encodable getSigAlgParams();

    boolean supportsSignatureAlgorithm(short s2);

    boolean supportsSignatureAlgorithmCA(short s2);
}
