package org.bouncycastle.operator;

import java.util.HashMap;
import java.util.Map;
import org.bouncycastle.asn1.ASN1Encodable;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.DERNull;
import org.bouncycastle.asn1.bsi.BSIObjectIdentifiers;
import org.bouncycastle.asn1.cms.CMSObjectIdentifiers;
import org.bouncycastle.asn1.cryptopro.CryptoProObjectIdentifiers;
import org.bouncycastle.asn1.eac.EACObjectIdentifiers;
import org.bouncycastle.asn1.edec.EdECObjectIdentifiers;
import org.bouncycastle.asn1.isara.IsaraObjectIdentifiers;
import org.bouncycastle.asn1.nist.NISTObjectIdentifiers;
import org.bouncycastle.asn1.oiw.OIWObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.PKCSObjectIdentifiers;
import org.bouncycastle.asn1.pkcs.RSASSAPSSparams;
import org.bouncycastle.asn1.rosstandart.RosstandartObjectIdentifiers;
import org.bouncycastle.asn1.teletrust.TeleTrusTObjectIdentifiers;
import org.bouncycastle.asn1.x509.AlgorithmIdentifier;
import org.bouncycastle.asn1.x9.X9ObjectIdentifiers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DefaultSignatureNameFinder implements AlgorithmNameFinder {
    private static final Map digests;
    private static final Map oids;

    static {
        HashMap hashMap = new HashMap();
        oids = hashMap;
        HashMap hashMap2 = new HashMap();
        digests = hashMap2;
        hashMap.put(PKCSObjectIdentifiers.id_RSASSA_PSS, "RSASSA-PSS");
        hashMap.put(EdECObjectIdentifiers.id_Ed25519, "ED25519");
        hashMap.put(EdECObjectIdentifiers.id_Ed448, "ED448");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha224WithRSAEncryption, "SHA224WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha256WithRSAEncryption, "SHA256WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha384WithRSAEncryption, "SHA384WITHRSA");
        hashMap.put(PKCSObjectIdentifiers.sha512WithRSAEncryption, "SHA512WITHRSA");
        hashMap.put(CMSObjectIdentifiers.id_RSASSA_PSS_SHAKE128, "SHAKE128WITHRSAPSS");
        hashMap.put(CMSObjectIdentifiers.id_RSASSA_PSS_SHAKE256, "SHAKE256WITHRSAPSS");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
        hashMap.put(CryptoProObjectIdentifiers.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_256, "GOST3411-2012-256WITHECGOST3410-2012-256");
        hashMap.put(RosstandartObjectIdentifiers.id_tc26_signwithdigest_gost_3410_12_512, "GOST3411-2012-512WITHECGOST3410-2012-512");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA1, "SHA1WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA224, "SHA224WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA256, "SHA256WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA384, "SHA384WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA512, "SHA512WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA3_224, "SHA3-224WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA3_256, "SHA3-256WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA3_384, "SHA3-384WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_SHA3_512, "SHA3-512WITHPLAIN-ECDSA");
        hashMap.put(BSIObjectIdentifiers.ecdsa_plain_RIPEMD160, "RIPEMD160WITHPLAIN-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_1, "SHA1WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_224, "SHA224WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_256, "SHA256WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_384, "SHA384WITHCVC-ECDSA");
        hashMap.put(EACObjectIdentifiers.id_TA_ECDSA_SHA_512, "SHA512WITHCVC-ECDSA");
        hashMap.put(IsaraObjectIdentifiers.id_alg_xmss, "XMSS");
        hashMap.put(IsaraObjectIdentifiers.id_alg_xmssmt, "XMSSMT");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd128, "RIPEMD128WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd160, "RIPEMD160WITHRSA");
        hashMap.put(TeleTrusTObjectIdentifiers.rsaSignatureWithripemd256, "RIPEMD256WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        hashMap.put(new ASN1ObjectIdentifier("1.2.840.10040.4.3"), "SHA1WITHDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA1, "SHA1WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA224, "SHA224WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA256, "SHA256WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA384, "SHA384WITHECDSA");
        hashMap.put(X9ObjectIdentifiers.ecdsa_with_SHA512, "SHA512WITHECDSA");
        hashMap.put(CMSObjectIdentifiers.id_ecdsa_with_shake128, "SHAKE128WITHECDSA");
        hashMap.put(CMSObjectIdentifiers.id_ecdsa_with_shake256, "SHAKE256WITHECDSA");
        hashMap.put(OIWObjectIdentifiers.sha1WithRSA, "SHA1WITHRSA");
        hashMap.put(OIWObjectIdentifiers.dsaWithSHA1, "SHA1WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha224, "SHA224WITHDSA");
        hashMap.put(NISTObjectIdentifiers.dsa_with_sha256, "SHA256WITHDSA");
        hashMap2.put(OIWObjectIdentifiers.idSHA1, "SHA1");
        hashMap2.put(NISTObjectIdentifiers.id_sha224, "SHA224");
        hashMap2.put(NISTObjectIdentifiers.id_sha256, "SHA256");
        hashMap2.put(NISTObjectIdentifiers.id_sha384, "SHA384");
        hashMap2.put(NISTObjectIdentifiers.id_sha512, "SHA512");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_224, "SHA3-224");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_256, "SHA3-256");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_384, "SHA3-384");
        hashMap2.put(NISTObjectIdentifiers.id_sha3_512, "SHA3-512");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd128, "RIPEMD128");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd160, "RIPEMD160");
        hashMap2.put(TeleTrusTObjectIdentifiers.ripemd256, "RIPEMD256");
    }

    private static String getDigestName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) digests.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    @Override // org.bouncycastle.operator.AlgorithmNameFinder
    public String getAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        String str = (String) oids.get(aSN1ObjectIdentifier);
        return str != null ? str : aSN1ObjectIdentifier.getId();
    }

    @Override // org.bouncycastle.operator.AlgorithmNameFinder
    public boolean hasAlgorithmName(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
        return oids.containsKey(aSN1ObjectIdentifier);
    }

    @Override // org.bouncycastle.operator.AlgorithmNameFinder
    public String getAlgorithmName(AlgorithmIdentifier algorithmIdentifier) {
        ASN1Encodable parameters = algorithmIdentifier.getParameters();
        if (parameters == null || DERNull.INSTANCE.equals(parameters) || !algorithmIdentifier.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_RSASSA_PSS)) {
            Map map = oids;
            boolean containsKey = map.containsKey(algorithmIdentifier.getAlgorithm());
            ASN1ObjectIdentifier algorithm = algorithmIdentifier.getAlgorithm();
            return containsKey ? (String) map.get(algorithm) : algorithm.getId();
        }
        RSASSAPSSparams rSASSAPSSparams = RSASSAPSSparams.getInstance(parameters);
        AlgorithmIdentifier maskGenAlgorithm = rSASSAPSSparams.getMaskGenAlgorithm();
        if (!maskGenAlgorithm.getAlgorithm().equals((ASN1Primitive) PKCSObjectIdentifiers.id_mgf1)) {
            return getDigestName(rSASSAPSSparams.getHashAlgorithm().getAlgorithm()) + "WITHRSAAND" + maskGenAlgorithm.getAlgorithm().getId();
        }
        AlgorithmIdentifier hashAlgorithm = rSASSAPSSparams.getHashAlgorithm();
        ASN1ObjectIdentifier algorithm2 = AlgorithmIdentifier.getInstance(maskGenAlgorithm.getParameters()).getAlgorithm();
        if (algorithm2.equals((ASN1Primitive) hashAlgorithm.getAlgorithm())) {
            return AbstractC0000a.m18n(new StringBuilder(), getDigestName(hashAlgorithm.getAlgorithm()), "WITHRSAANDMGF1");
        }
        return getDigestName(hashAlgorithm.getAlgorithm()) + "WITHRSAANDMGF1USING" + getDigestName(algorithm2);
    }
}
