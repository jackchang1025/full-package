package org.bouncycastle.tsp;

import java.io.IOException;
import java.io.OutputStream;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1EncodableVector;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Set;
import org.bouncycastle.asn1.cms.Attribute;
import org.bouncycastle.asn1.cms.AttributeTable;
import org.bouncycastle.asn1.cms.ContentInfo;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.gm.GMObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.ExtendedKeyUsage;
import org.bouncycastle.asn1.x509.Extension;
import org.bouncycastle.asn1.x509.Extensions;
import org.bouncycastle.asn1.x509.ExtensionsGenerator;
import org.bouncycastle.asn1.x509.KeyPurposeId;
import org.bouncycastle.cert.X509CertificateHolder;
import org.bouncycastle.cms.SignerInformation;
import org.bouncycastle.operator.DigestCalculator;
import org.bouncycastle.operator.DigestCalculatorProvider;
import org.bouncycastle.operator.OperatorCreationException;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class TSPUtil {
    private static List EMPTY_LIST = Collections.unmodifiableList(new ArrayList());
    private static final Map digestLengths;
    private static final Map digestNames;

    static {
        HashMap hashMap = new HashMap();
        digestLengths = hashMap;
        HashMap hashMap2 = new HashMap();
        digestNames = hashMap2;
        ASN1ObjectIdentifier aSN1ObjectIdentifier = PKCSObjectIdentifiers.md5;
        hashMap.put(aSN1ObjectIdentifier.getId(), Integers.valueOf(16));
        ASN1ObjectIdentifier aSN1ObjectIdentifier2 = OIWObjectIdentifiers.idSHA1;
        hashMap.put(aSN1ObjectIdentifier2.getId(), Integers.valueOf(20));
        ASN1ObjectIdentifier aSN1ObjectIdentifier3 = NISTObjectIdentifiers.id_sha224;
        hashMap.put(aSN1ObjectIdentifier3.getId(), Integers.valueOf(28));
        ASN1ObjectIdentifier aSN1ObjectIdentifier4 = NISTObjectIdentifiers.id_sha256;
        hashMap.put(aSN1ObjectIdentifier4.getId(), Integers.valueOf(32));
        ASN1ObjectIdentifier aSN1ObjectIdentifier5 = NISTObjectIdentifiers.id_sha384;
        hashMap.put(aSN1ObjectIdentifier5.getId(), Integers.valueOf(48));
        ASN1ObjectIdentifier aSN1ObjectIdentifier6 = NISTObjectIdentifiers.id_sha512;
        hashMap.put(aSN1ObjectIdentifier6.getId(), Integers.valueOf(64));
        ASN1ObjectIdentifier aSN1ObjectIdentifier7 = TeleTrusTObjectIdentifiers.ripemd128;
        hashMap.put(aSN1ObjectIdentifier7.getId(), Integers.valueOf(16));
        ASN1ObjectIdentifier aSN1ObjectIdentifier8 = TeleTrusTObjectIdentifiers.ripemd160;
        hashMap.put(aSN1ObjectIdentifier8.getId(), Integers.valueOf(20));
        ASN1ObjectIdentifier aSN1ObjectIdentifier9 = TeleTrusTObjectIdentifiers.ripemd256;
        hashMap.put(aSN1ObjectIdentifier9.getId(), Integers.valueOf(32));
        ASN1ObjectIdentifier aSN1ObjectIdentifier10 = CryptoProObjectIdentifiers.gostR3411;
        hashMap.put(aSN1ObjectIdentifier10.getId(), Integers.valueOf(32));
        ASN1ObjectIdentifier aSN1ObjectIdentifier11 = RosstandartObjectIdentifiers.id_tc26_gost_3411_12_256;
        hashMap.put(aSN1ObjectIdentifier11.getId(), Integers.valueOf(32));
        ASN1ObjectIdentifier aSN1ObjectIdentifier12 = RosstandartObjectIdentifiers.id_tc26_gost_3411_12_512;
        hashMap.put(aSN1ObjectIdentifier12.getId(), Integers.valueOf(64));
        ASN1ObjectIdentifier aSN1ObjectIdentifier13 = GMObjectIdentifiers.sm3;
        hashMap.put(aSN1ObjectIdentifier13.getId(), Integers.valueOf(32));
        hashMap2.put(aSN1ObjectIdentifier.getId(), "MD5");
        hashMap2.put(aSN1ObjectIdentifier2.getId(), "SHA1");
        hashMap2.put(aSN1ObjectIdentifier3.getId(), "SHA224");
        hashMap2.put(aSN1ObjectIdentifier4.getId(), "SHA256");
        hashMap2.put(aSN1ObjectIdentifier5.getId(), "SHA384");
        hashMap2.put(aSN1ObjectIdentifier6.getId(), "SHA512");
        hashMap2.put(PKCSObjectIdentifiers.sha1WithRSAEncryption.getId(), "SHA1");
        hashMap2.put(PKCSObjectIdentifiers.sha224WithRSAEncryption.getId(), "SHA224");
        hashMap2.put(PKCSObjectIdentifiers.sha256WithRSAEncryption.getId(), "SHA256");
        hashMap2.put(PKCSObjectIdentifiers.sha384WithRSAEncryption.getId(), "SHA384");
        hashMap2.put(PKCSObjectIdentifiers.sha512WithRSAEncryption.getId(), "SHA512");
        hashMap2.put(aSN1ObjectIdentifier7.getId(), "RIPEMD128");
        hashMap2.put(aSN1ObjectIdentifier8.getId(), "RIPEMD160");
        hashMap2.put(aSN1ObjectIdentifier9.getId(), "RIPEMD256");
        hashMap2.put(aSN1ObjectIdentifier10.getId(), "GOST3411");
        hashMap2.put(aSN1ObjectIdentifier11.getId(), "GOST3411-2012-256");
        hashMap2.put(aSN1ObjectIdentifier12.getId(), "GOST3411-2012-512");
        hashMap2.put(aSN1ObjectIdentifier13.getId(), "SM3");
    }

    public static void addExtension(ExtensionsGenerator extensionsGenerator, ASN1ObjectIdentifier aSN1ObjectIdentifier, boolean z2, ASN1Encodable aSN1Encodable) {
        try {
            extensionsGenerator.addExtension(aSN1ObjectIdentifier, z2, aSN1Encodable);
        } catch (IOException e2) {
            throw new TSPIOException(AbstractC0000a.m8d(e2, new StringBuilder("cannot encode extension: ")), e2);
        }
    }

    public static int getDigestLength(String str) {
        Integer num = (Integer) digestLengths.get(str);
        if (num != null) {
            return num.intValue();
        }
        throw new TSPException("digest algorithm cannot be found.");
    }

    public static List getExtensionOIDs(Extensions extensions) {
        return extensions == null ? EMPTY_LIST : Collections.unmodifiableList(Arrays.asList(extensions.getExtensionOIDs()));
    }

    public static Collection getSignatureTimestamps(SignerInformation signerInformation, DigestCalculatorProvider digestCalculatorProvider) {
        ArrayList arrayList = new ArrayList();
        AttributeTable unsignedAttributes = signerInformation.getUnsignedAttributes();
        if (unsignedAttributes != null) {
            ASN1EncodableVector all = unsignedAttributes.getAll(PKCSObjectIdentifiers.id_aa_signatureTimeStampToken);
            for (int i2 = 0; i2 < all.size(); i2++) {
                ASN1Set attrValues = ((Attribute) all.get(i2)).getAttrValues();
                for (int i3 = 0; i3 < attrValues.size(); i3++) {
                    try {
                        TimeStampToken timeStampToken = new TimeStampToken(ContentInfo.getInstance(attrValues.getObjectAt(i3)));
                        TimeStampTokenInfo timeStampInfo = timeStampToken.getTimeStampInfo();
                        DigestCalculator digestCalculator = digestCalculatorProvider.get(timeStampInfo.getHashAlgorithm());
                        OutputStream outputStream = digestCalculator.getOutputStream();
                        outputStream.write(signerInformation.getSignature());
                        outputStream.close();
                        if (!org.bouncycastle.util.Arrays.constantTimeAreEqual(digestCalculator.getDigest(), timeStampInfo.getMessageImprintDigest())) {
                            throw new TSPValidationException("Incorrect digest in message imprint");
                        }
                        arrayList.add(timeStampToken);
                    } catch (OperatorCreationException unused) {
                        throw new TSPValidationException("Unknown hash algorithm specified in timestamp");
                    } catch (Exception unused2) {
                        throw new TSPValidationException("Timestamp could not be parsed");
                    }
                }
            }
        }
        return arrayList;
    }

    public static void validateCertificate(X509CertificateHolder x509CertificateHolder) {
        if (x509CertificateHolder.toASN1Structure().getVersionNumber() != 3) {
            throw new IllegalArgumentException("Certificate must have an ExtendedKeyUsage extension.");
        }
        Extension extension = x509CertificateHolder.getExtension(Extension.extendedKeyUsage);
        if (extension == null) {
            throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension.");
        }
        if (!extension.isCritical()) {
            throw new TSPValidationException("Certificate must have an ExtendedKeyUsage extension marked as critical.");
        }
        ExtendedKeyUsage extendedKeyUsage = ExtendedKeyUsage.getInstance(extension.getParsedValue());
        if (!extendedKeyUsage.hasKeyPurposeId(KeyPurposeId.id_kp_timeStamping) || extendedKeyUsage.size() != 1) {
            throw new TSPValidationException("ExtendedKeyUsage not solely time stamping.");
        }
    }
}
