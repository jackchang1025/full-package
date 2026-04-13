package org.bouncycastle.tls.crypto.impl;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1Encoding;
import org.bouncycastle.asn1.ASN1Integer;
import org.bouncycastle.asn1.ASN1Null;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x509.X509ObjectIdentifiers;
import org.bouncycastle.tls.crypto.TlsCryptoUtils;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class RSAUtil {
    private static final byte[] RSAPSSParams_256_A;
    private static final byte[] RSAPSSParams_256_B;
    private static final byte[] RSAPSSParams_384_A;
    private static final byte[] RSAPSSParams_384_B;
    private static final byte[] RSAPSSParams_512_A;
    private static final byte[] RSAPSSParams_512_B;

    static {
        ASN1ObjectIdentifier aSN1ObjectIdentifier = NISTObjectIdentifiers.id_sha256;
        AlgorithmIdentifier algorithmIdentifier = new AlgorithmIdentifier(aSN1ObjectIdentifier);
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = NISTObjectIdentifiers.id_sha384;
        AlgorithmIdentifier algorithmIdentifier2 = new AlgorithmIdentifier(aSN1ObjectIdentifier2);
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_sha512;
        AlgorithmIdentifier algorithmIdentifier3 = new AlgorithmIdentifier(aSN1ObjectIdentifier3);
        DERNull dERNull = DERNull.INSTANCE;
        AlgorithmIdentifier algorithmIdentifier4 = new AlgorithmIdentifier(aSN1ObjectIdentifier, dERNull);
        AlgorithmIdentifier algorithmIdentifier5 = new AlgorithmIdentifier(aSN1ObjectIdentifier2, dERNull);
        AlgorithmIdentifier algorithmIdentifier6 = new AlgorithmIdentifier(aSN1ObjectIdentifier3, dERNull);
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = PKCSObjectIdentifiers.id_mgf1;
        AlgorithmIdentifier algorithmIdentifier7 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier);
        AlgorithmIdentifier algorithmIdentifier8 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier2);
        AlgorithmIdentifier algorithmIdentifier9 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier3);
        AlgorithmIdentifier algorithmIdentifier10 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier4);
        AlgorithmIdentifier algorithmIdentifier11 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier5);
        AlgorithmIdentifier algorithmIdentifier12 = new AlgorithmIdentifier(aSN1ObjectIdentifier4, algorithmIdentifier6);
        ASN1Integer aSN1Integer = new ASN1Integer(TlsCryptoUtils.getHashOutputSize(4));
        ASN1Integer aSN1Integer2 = new ASN1Integer(TlsCryptoUtils.getHashOutputSize(5));
        ASN1Integer aSN1Integer3 = new ASN1Integer(TlsCryptoUtils.getHashOutputSize(6));
        ASN1Integer aSN1Integer4 = new ASN1Integer(1L);
        try {
            RSAPSSParams_256_A = new RSASSAPSSparams(algorithmIdentifier, algorithmIdentifier7, aSN1Integer, aSN1Integer4).getEncoded(ASN1Encoding.DER);
            RSAPSSParams_384_A = new RSASSAPSSparams(algorithmIdentifier2, algorithmIdentifier8, aSN1Integer2, aSN1Integer4).getEncoded(ASN1Encoding.DER);
            RSAPSSParams_512_A = new RSASSAPSSparams(algorithmIdentifier3, algorithmIdentifier9, aSN1Integer3, aSN1Integer4).getEncoded(ASN1Encoding.DER);
            RSAPSSParams_256_B = new RSASSAPSSparams(algorithmIdentifier4, algorithmIdentifier10, aSN1Integer, aSN1Integer4).getEncoded(ASN1Encoding.DER);
            RSAPSSParams_384_B = new RSASSAPSSparams(algorithmIdentifier5, algorithmIdentifier11, aSN1Integer2, aSN1Integer4).getEncoded(ASN1Encoding.DER);
            RSAPSSParams_512_B = new RSASSAPSSparams(algorithmIdentifier6, algorithmIdentifier12, aSN1Integer3, aSN1Integer4).getEncoded(ASN1Encoding.DER);
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }

    public static boolean supportsPKCS1(AlgorithmIdentifier algorithmIdentifier) {
        ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
        return PKCSObjectIdentifiers.rsaEncryption.equals((ASN1Primitive) algorithm) || X509ObjectIdentifiers.id_ea_rsa.equals((ASN1Primitive) algorithm);
    }

    public static boolean supportsPSS_PSS(short s2, AlgorithmIdentifier algorithmIdentifier) {
        byte[] bArr;
        byte[] bArr2;
        if (!PKCSObjectIdentifiers.id_RSASSA_PSS.equals((ASN1Primitive) algorithmIdentifier.getAlgorithm())) {
            return false;
        }
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters == null || (parameters instanceof ASN1Null)) {
            switch (s2) {
            }
            return false;
        }
        try {
            byte[] encoded = parameters.toASN1Primitive().getEncoded(ASN1Encoding.DER);
            switch (s2) {
                case 9:
                    bArr = RSAPSSParams_256_A;
                    bArr2 = RSAPSSParams_256_B;
                    break;
                case 10:
                    bArr = RSAPSSParams_384_A;
                    bArr2 = RSAPSSParams_384_B;
                    break;
                case 11:
                    bArr = RSAPSSParams_512_A;
                    bArr2 = RSAPSSParams_512_B;
                    break;
                default:
                    return false;
            }
            return Arrays.areEqual(bArr, encoded) || Arrays.areEqual(bArr2, encoded);
        } catch (Exception unused) {
            return false;
        }
    }

    public static boolean supportsPSS_RSAE(AlgorithmIdentifier algorithmIdentifier) {
        return PKCSObjectIdentifiers.rsaEncryption.equals((ASN1Primitive) algorithmIdentifier.getAlgorithm());
    }
}
