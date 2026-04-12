package p000;

import java.util.HashMap;
import java.util.Map;

/* renamed from: qs */
/* loaded from: classes2.dex */
public class C1121qs implements InterfaceC1237sw {
    private static Map digestOids = new HashMap();
    private static Map digestNameToOids = new HashMap();
    private static Map digestOidToAlgIds = new HashMap();

    static {
        Map map = digestOids;
        C0160c5 c0160c5 = pk0.dsaWithSHA1;
        C0160c5 c0160c52 = pk0.idSHA1;
        map.put(c0160c5, c0160c52);
        Map map2 = digestOids;
        C0160c5 c0160c53 = pk0.md4WithRSAEncryption;
        C0160c5 c0160c54 = ul0.md4;
        map2.put(c0160c53, c0160c54);
        digestOids.put(pk0.md4WithRSA, c0160c54);
        digestOids.put(pk0.sha1WithRSA, c0160c52);
        Map map3 = digestOids;
        C0160c5 c0160c55 = ul0.sha224WithRSAEncryption;
        C0160c5 c0160c56 = lh0.id_sha224;
        map3.put(c0160c55, c0160c56);
        Map map4 = digestOids;
        C0160c5 c0160c57 = ul0.sha256WithRSAEncryption;
        C0160c5 c0160c58 = lh0.id_sha256;
        map4.put(c0160c57, c0160c58);
        Map map5 = digestOids;
        C0160c5 c0160c59 = ul0.sha384WithRSAEncryption;
        C0160c5 c0160c510 = lh0.id_sha384;
        map5.put(c0160c59, c0160c510);
        Map map6 = digestOids;
        C0160c5 c0160c511 = ul0.sha512WithRSAEncryption;
        C0160c5 c0160c512 = lh0.id_sha512;
        map6.put(c0160c511, c0160c512);
        Map map7 = digestOids;
        C0160c5 c0160c513 = ul0.sha512_224WithRSAEncryption;
        C0160c5 c0160c514 = lh0.id_sha512_224;
        map7.put(c0160c513, c0160c514);
        Map map8 = digestOids;
        C0160c5 c0160c515 = ul0.sha512_256WithRSAEncryption;
        C0160c5 c0160c516 = lh0.id_sha512_256;
        map8.put(c0160c515, c0160c516);
        Map map9 = digestOids;
        C0160c5 c0160c517 = ul0.md2WithRSAEncryption;
        C0160c5 c0160c518 = ul0.md2;
        map9.put(c0160c517, c0160c518);
        digestOids.put(ul0.md4WithRSAEncryption, c0160c54);
        Map map10 = digestOids;
        C0160c5 c0160c519 = ul0.md5WithRSAEncryption;
        C0160c5 c0160c520 = ul0.md5;
        map10.put(c0160c519, c0160c520);
        digestOids.put(ul0.sha1WithRSAEncryption, c0160c52);
        digestOids.put(hi1.ecdsa_with_SHA1, c0160c52);
        digestOids.put(hi1.ecdsa_with_SHA224, c0160c56);
        digestOids.put(hi1.ecdsa_with_SHA256, c0160c58);
        digestOids.put(hi1.ecdsa_with_SHA384, c0160c510);
        digestOids.put(hi1.ecdsa_with_SHA512, c0160c512);
        digestOids.put(hi1.id_dsa_with_sha1, c0160c52);
        digestOids.put(InterfaceC0388cr.ecdsa_plain_SHA1, c0160c52);
        digestOids.put(InterfaceC0388cr.ecdsa_plain_SHA224, c0160c56);
        digestOids.put(InterfaceC0388cr.ecdsa_plain_SHA256, c0160c58);
        digestOids.put(InterfaceC0388cr.ecdsa_plain_SHA384, c0160c510);
        digestOids.put(InterfaceC0388cr.ecdsa_plain_SHA512, c0160c512);
        Map map11 = digestOids;
        C0160c5 c0160c521 = InterfaceC0388cr.ecdsa_plain_SHA3_224;
        C0160c5 c0160c522 = lh0.id_sha3_224;
        map11.put(c0160c521, c0160c522);
        Map map12 = digestOids;
        C0160c5 c0160c523 = InterfaceC0388cr.ecdsa_plain_SHA3_256;
        C0160c5 c0160c524 = lh0.id_sha3_256;
        map12.put(c0160c523, c0160c524);
        Map map13 = digestOids;
        C0160c5 c0160c525 = InterfaceC0388cr.ecdsa_plain_SHA3_384;
        C0160c5 c0160c526 = lh0.id_sha3_384;
        map13.put(c0160c525, c0160c526);
        Map map14 = digestOids;
        C0160c5 c0160c527 = InterfaceC0388cr.ecdsa_plain_SHA3_512;
        C0160c5 c0160c528 = lh0.id_sha3_512;
        map14.put(c0160c527, c0160c528);
        Map map15 = digestOids;
        C0160c5 c0160c529 = InterfaceC0388cr.ecdsa_plain_RIPEMD160;
        C0160c5 c0160c530 = o51.ripemd160;
        map15.put(c0160c529, c0160c530);
        digestOids.put(InterfaceC1312ut.id_TA_ECDSA_SHA_1, c0160c52);
        digestOids.put(InterfaceC1312ut.id_TA_ECDSA_SHA_224, c0160c56);
        digestOids.put(InterfaceC1312ut.id_TA_ECDSA_SHA_256, c0160c58);
        digestOids.put(InterfaceC1312ut.id_TA_ECDSA_SHA_384, c0160c510);
        digestOids.put(InterfaceC1312ut.id_TA_ECDSA_SHA_512, c0160c512);
        digestOids.put(lh0.dsa_with_sha224, c0160c56);
        digestOids.put(lh0.dsa_with_sha256, c0160c58);
        digestOids.put(lh0.dsa_with_sha384, c0160c510);
        digestOids.put(lh0.dsa_with_sha512, c0160c512);
        digestOids.put(lh0.id_rsassa_pkcs1_v1_5_with_sha3_224, c0160c522);
        digestOids.put(lh0.id_rsassa_pkcs1_v1_5_with_sha3_256, c0160c524);
        digestOids.put(lh0.id_rsassa_pkcs1_v1_5_with_sha3_384, c0160c526);
        digestOids.put(lh0.id_rsassa_pkcs1_v1_5_with_sha3_512, c0160c528);
        digestOids.put(lh0.id_dsa_with_sha3_224, c0160c522);
        digestOids.put(lh0.id_dsa_with_sha3_256, c0160c524);
        digestOids.put(lh0.id_dsa_with_sha3_384, c0160c526);
        digestOids.put(lh0.id_dsa_with_sha3_512, c0160c528);
        digestOids.put(lh0.id_ecdsa_with_sha3_224, c0160c522);
        digestOids.put(lh0.id_ecdsa_with_sha3_256, c0160c524);
        digestOids.put(lh0.id_ecdsa_with_sha3_384, c0160c526);
        digestOids.put(lh0.id_ecdsa_with_sha3_512, c0160c528);
        Map map16 = digestOids;
        C0160c5 c0160c531 = o51.rsaSignatureWithripemd128;
        C0160c5 c0160c532 = o51.ripemd128;
        map16.put(c0160c531, c0160c532);
        digestOids.put(o51.rsaSignatureWithripemd160, c0160c530);
        Map map17 = digestOids;
        C0160c5 c0160c533 = o51.rsaSignatureWithripemd256;
        C0160c5 c0160c534 = o51.ripemd256;
        map17.put(c0160c533, c0160c534);
        Map map18 = digestOids;
        C0160c5 c0160c535 = InterfaceC0928nw.gostR3411_94_with_gostR3410_94;
        C0160c5 c0160c536 = InterfaceC0928nw.gostR3411;
        map18.put(c0160c535, c0160c536);
        digestOids.put(InterfaceC0928nw.gostR3411_94_with_gostR3410_2001, c0160c536);
        Map map19 = digestOids;
        C0160c5 c0160c537 = ks0.id_tc26_signwithdigest_gost_3410_12_256;
        C0160c5 c0160c538 = ks0.id_tc26_gost_3411_12_256;
        map19.put(c0160c537, c0160c538);
        Map map20 = digestOids;
        C0160c5 c0160c539 = ks0.id_tc26_signwithdigest_gost_3410_12_512;
        C0160c5 c0160c540 = ks0.id_tc26_gost_3411_12_512;
        map20.put(c0160c539, c0160c540);
        digestOids.put(InterfaceC0167cc.sphincs256_with_SHA3_512, c0160c528);
        digestOids.put(InterfaceC0167cc.sphincs256_with_SHA512, c0160c512);
        digestOids.put(g20.sm2sign_with_sha256, c0160c58);
        Map map21 = digestOids;
        C0160c5 c0160c541 = g20.sm2sign_with_sm3;
        C0160c5 c0160c542 = g20.sm3;
        map21.put(c0160c541, c0160c542);
        Map map22 = digestOids;
        C0160c5 c0160c543 = InterfaceC0501fm.id_RSASSA_PSS_SHAKE128;
        C0160c5 c0160c544 = lh0.id_shake128;
        map22.put(c0160c543, c0160c544);
        Map map23 = digestOids;
        C0160c5 c0160c545 = InterfaceC0501fm.id_RSASSA_PSS_SHAKE256;
        C0160c5 c0160c546 = lh0.id_shake256;
        map23.put(c0160c545, c0160c546);
        digestOids.put(InterfaceC0501fm.id_ecdsa_with_shake128, c0160c544);
        digestOids.put(InterfaceC0501fm.id_ecdsa_with_shake256, c0160c546);
        digestNameToOids.put("SHA-1", c0160c52);
        digestNameToOids.put("SHA-224", c0160c56);
        digestNameToOids.put(ki1.SHA_256, c0160c58);
        digestNameToOids.put("SHA-384", c0160c510);
        digestNameToOids.put(ki1.SHA_512, c0160c512);
        digestNameToOids.put("SHA-512-224", c0160c514);
        digestNameToOids.put("SHA-512-256", c0160c516);
        digestNameToOids.put("SHA1", c0160c52);
        digestNameToOids.put("SHA224", c0160c56);
        digestNameToOids.put("SHA256", c0160c58);
        digestNameToOids.put("SHA384", c0160c510);
        digestNameToOids.put("SHA512", c0160c512);
        digestNameToOids.put("SHA512-224", c0160c514);
        digestNameToOids.put("SHA512-256", c0160c516);
        digestNameToOids.put("SHA3-224", c0160c522);
        digestNameToOids.put(ft0.SHA3_256, c0160c524);
        digestNameToOids.put("SHA3-384", c0160c526);
        digestNameToOids.put("SHA3-512", c0160c528);
        digestNameToOids.put(ki1.SHAKE128, c0160c544);
        digestNameToOids.put(ki1.SHAKE256, c0160c546);
        digestNameToOids.put("SHAKE-128", c0160c544);
        digestNameToOids.put("SHAKE-256", c0160c546);
        digestNameToOids.put("GOST3411", c0160c536);
        digestNameToOids.put("GOST3411-2012-256", c0160c538);
        digestNameToOids.put("GOST3411-2012-512", c0160c540);
        digestNameToOids.put("MD2", c0160c518);
        digestNameToOids.put("MD4", c0160c54);
        digestNameToOids.put("MD5", c0160c520);
        digestNameToOids.put("RIPEMD128", c0160c532);
        digestNameToOids.put("RIPEMD160", c0160c530);
        digestNameToOids.put("RIPEMD256", c0160c534);
        digestNameToOids.put("SM3", c0160c542);
        addDigestAlgId(c0160c52, true);
        addDigestAlgId(c0160c56, false);
        addDigestAlgId(c0160c58, false);
        addDigestAlgId(c0160c510, false);
        addDigestAlgId(c0160c512, false);
        addDigestAlgId(c0160c514, false);
        addDigestAlgId(c0160c516, false);
        addDigestAlgId(c0160c522, false);
        addDigestAlgId(c0160c524, false);
        addDigestAlgId(c0160c526, false);
        addDigestAlgId(c0160c528, false);
        addDigestAlgId(c0160c544, false);
        addDigestAlgId(c0160c546, false);
        addDigestAlgId(c0160c536, true);
        addDigestAlgId(c0160c538, false);
        addDigestAlgId(c0160c540, false);
        addDigestAlgId(c0160c518, true);
        addDigestAlgId(c0160c54, true);
        addDigestAlgId(c0160c520, true);
        addDigestAlgId(c0160c532, true);
        addDigestAlgId(c0160c530, true);
        addDigestAlgId(c0160c534, true);
    }

    private static void addDigestAlgId(C0160c5 c0160c5, boolean z) {
        digestOidToAlgIds.put(c0160c5, z ? new C1168r5(c0160c5, C1046ow.INSTANCE) : new C1168r5(c0160c5));
    }

    @Override // p000.InterfaceC1237sw
    public C1168r5 find(C0160c5 c0160c5) {
        if (c0160c5 == null) {
            throw new NullPointerException("digest OID is null");
        }
        C1168r5 c1168r5 = (C1168r5) digestOidToAlgIds.get(c0160c5);
        return c1168r5 == null ? new C1168r5(c0160c5) : c1168r5;
    }

    @Override // p000.InterfaceC1237sw
    public C1168r5 find(C1168r5 c1168r5) {
        C0160c5 algorithm = c1168r5.getAlgorithm();
        if (algorithm.equals((AbstractC0164c9) InterfaceC1348vs.id_Ed448)) {
            return new C1168r5(lh0.id_shake256_len, new C0155c0(512L));
        }
        return find(algorithm.equals((AbstractC0164c9) ul0.id_RSASSA_PSS) ? op0.getInstance(c1168r5.getParameters()).getHashAlgorithm().getAlgorithm() : algorithm.equals((AbstractC0164c9) InterfaceC1348vs.id_Ed25519) ? lh0.id_sha512 : (C0160c5) digestOids.get(c1168r5.getAlgorithm()));
    }

    @Override // p000.InterfaceC1237sw
    public C1168r5 find(String str) {
        C0160c5 c0160c5 = (C0160c5) digestNameToOids.get(str);
        if (c0160c5 != null) {
            return find(c0160c5);
        }
        try {
            return find(new C0160c5(str));
        } catch (IllegalArgumentException unused) {
            return null;
        }
    }
}
