package p000;

import java.util.HashMap;
import java.util.Map;

/* renamed from: rj */
/* loaded from: classes2.dex */
public class C1182rj implements InterfaceC1169r6 {
    private static final Map digests;
    private static final Map oids;

    static {
        HashMap map = new HashMap();
        oids = map;
        HashMap map2 = new HashMap();
        digests = map2;
        map.put(ul0.id_RSASSA_PSS, "RSASSA-PSS");
        map.put(InterfaceC1348vs.id_Ed25519, "ED25519");
        map.put(InterfaceC1348vs.id_Ed448, "ED448");
        map.put(new C0160c5("1.2.840.113549.1.1.5"), "SHA1WITHRSA");
        map.put(ul0.sha224WithRSAEncryption, "SHA224WITHRSA");
        map.put(ul0.sha256WithRSAEncryption, "SHA256WITHRSA");
        map.put(ul0.sha384WithRSAEncryption, "SHA384WITHRSA");
        map.put(ul0.sha512WithRSAEncryption, "SHA512WITHRSA");
        map.put(InterfaceC0501fm.id_RSASSA_PSS_SHAKE128, "SHAKE128WITHRSAPSS");
        map.put(InterfaceC0501fm.id_RSASSA_PSS_SHAKE256, "SHAKE256WITHRSAPSS");
        map.put(InterfaceC0928nw.gostR3411_94_with_gostR3410_94, "GOST3411WITHGOST3410");
        map.put(InterfaceC0928nw.gostR3411_94_with_gostR3410_2001, "GOST3411WITHECGOST3410");
        map.put(ks0.id_tc26_signwithdigest_gost_3410_12_256, "GOST3411-2012-256WITHECGOST3410-2012-256");
        map.put(ks0.id_tc26_signwithdigest_gost_3410_12_512, "GOST3411-2012-512WITHECGOST3410-2012-512");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA1, "SHA1WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA224, "SHA224WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA256, "SHA256WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA384, "SHA384WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA512, "SHA512WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA3_224, "SHA3-224WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA3_256, "SHA3-256WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA3_384, "SHA3-384WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_SHA3_512, "SHA3-512WITHPLAIN-ECDSA");
        map.put(InterfaceC0388cr.ecdsa_plain_RIPEMD160, "RIPEMD160WITHPLAIN-ECDSA");
        map.put(InterfaceC1312ut.id_TA_ECDSA_SHA_1, "SHA1WITHCVC-ECDSA");
        map.put(InterfaceC1312ut.id_TA_ECDSA_SHA_224, "SHA224WITHCVC-ECDSA");
        map.put(InterfaceC1312ut.id_TA_ECDSA_SHA_256, "SHA256WITHCVC-ECDSA");
        map.put(InterfaceC1312ut.id_TA_ECDSA_SHA_384, "SHA384WITHCVC-ECDSA");
        map.put(InterfaceC1312ut.id_TA_ECDSA_SHA_512, "SHA512WITHCVC-ECDSA");
        map.put(a70.id_alg_xmss, "XMSS");
        map.put(a70.id_alg_xmssmt, "XMSSMT");
        map.put(o51.rsaSignatureWithripemd128, "RIPEMD128WITHRSA");
        map.put(o51.rsaSignatureWithripemd160, "RIPEMD160WITHRSA");
        map.put(o51.rsaSignatureWithripemd256, "RIPEMD256WITHRSA");
        map.put(new C0160c5("1.2.840.113549.1.1.4"), "MD5WITHRSA");
        map.put(new C0160c5("1.2.840.113549.1.1.2"), "MD2WITHRSA");
        map.put(new C0160c5("1.2.840.10040.4.3"), "SHA1WITHDSA");
        map.put(hi1.ecdsa_with_SHA1, "SHA1WITHECDSA");
        map.put(hi1.ecdsa_with_SHA224, "SHA224WITHECDSA");
        map.put(hi1.ecdsa_with_SHA256, "SHA256WITHECDSA");
        map.put(hi1.ecdsa_with_SHA384, "SHA384WITHECDSA");
        map.put(hi1.ecdsa_with_SHA512, "SHA512WITHECDSA");
        map.put(InterfaceC0501fm.id_ecdsa_with_shake128, "SHAKE128WITHECDSA");
        map.put(InterfaceC0501fm.id_ecdsa_with_shake256, "SHAKE256WITHECDSA");
        map.put(pk0.sha1WithRSA, "SHA1WITHRSA");
        map.put(pk0.dsaWithSHA1, "SHA1WITHDSA");
        map.put(lh0.dsa_with_sha224, "SHA224WITHDSA");
        map.put(lh0.dsa_with_sha256, "SHA256WITHDSA");
        map2.put(pk0.idSHA1, "SHA1");
        map2.put(lh0.id_sha224, "SHA224");
        map2.put(lh0.id_sha256, "SHA256");
        map2.put(lh0.id_sha384, "SHA384");
        map2.put(lh0.id_sha512, "SHA512");
        map2.put(lh0.id_sha3_224, "SHA3-224");
        map2.put(lh0.id_sha3_256, ft0.SHA3_256);
        map2.put(lh0.id_sha3_384, "SHA3-384");
        map2.put(lh0.id_sha3_512, "SHA3-512");
        map2.put(o51.ripemd128, "RIPEMD128");
        map2.put(o51.ripemd160, "RIPEMD160");
        map2.put(o51.ripemd256, "RIPEMD256");
    }

    private static String getDigestName(C0160c5 c0160c5) {
        String str = (String) digests.get(c0160c5);
        return str != null ? str : c0160c5.getId();
    }

    @Override // p000.InterfaceC1169r6
    public String getAlgorithmName(C0160c5 c0160c5) {
        String str = (String) oids.get(c0160c5);
        return str != null ? str : c0160c5.getId();
    }

    @Override // p000.InterfaceC1169r6
    public boolean hasAlgorithmName(C0160c5 c0160c5) {
        return oids.containsKey(c0160c5);
    }

    @Override // p000.InterfaceC1169r6
    public String getAlgorithmName(C1168r5 c1168r5) {
        InterfaceC0117b0 parameters = c1168r5.getParameters();
        if (parameters == null || C1046ow.INSTANCE.equals(parameters) || !c1168r5.getAlgorithm().equals((AbstractC0164c9) ul0.id_RSASSA_PSS)) {
            Map map = oids;
            boolean zContainsKey = map.containsKey(c1168r5.getAlgorithm());
            C0160c5 algorithm = c1168r5.getAlgorithm();
            return zContainsKey ? (String) map.get(algorithm) : algorithm.getId();
        }
        op0 op0Var = op0.getInstance(parameters);
        C1168r5 maskGenAlgorithm = op0Var.getMaskGenAlgorithm();
        if (!maskGenAlgorithm.getAlgorithm().equals((AbstractC0164c9) ul0.id_mgf1)) {
            return getDigestName(op0Var.getHashAlgorithm().getAlgorithm()) + "WITHRSAAND" + maskGenAlgorithm.getAlgorithm().getId();
        }
        C1168r5 hashAlgorithm = op0Var.getHashAlgorithm();
        C0160c5 algorithm2 = C1168r5.getInstance(maskGenAlgorithm.getParameters()).getAlgorithm();
        if (algorithm2.equals((AbstractC0164c9) hashAlgorithm.getAlgorithm())) {
            return AbstractC0003a2.m35b6(new StringBuilder(), getDigestName(hashAlgorithm.getAlgorithm()), "WITHRSAANDMGF1");
        }
        return getDigestName(hashAlgorithm.getAlgorithm()) + "WITHRSAANDMGF1USING" + getDigestName(algorithm2);
    }
}
