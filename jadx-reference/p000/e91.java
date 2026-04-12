package p000;

import java.util.HashMap;
import java.util.Map;
import org.conscrypt.PSKKeyManager;

/* loaded from: classes2.dex */
public class e91 {
    static final C1168r5 AlgID_qTESLA_p_I;
    static final C1168r5 AlgID_qTESLA_p_III;
    static final C1168r5 SPHINCS_SHA3_256;
    static final C1168r5 SPHINCS_SHA512_256;
    static final C1168r5 XMSS_SHA256;
    static final C1168r5 XMSS_SHA512;
    static final C1168r5 XMSS_SHAKE128;
    static final C1168r5 XMSS_SHAKE256;
    static final Map categories;

    static {
        C0160c5 c0160c5 = vl0.qTESLA_p_I;
        AlgID_qTESLA_p_I = new C1168r5(c0160c5);
        C0160c5 c0160c52 = vl0.qTESLA_p_III;
        AlgID_qTESLA_p_III = new C1168r5(c0160c52);
        SPHINCS_SHA3_256 = new C1168r5(lh0.id_sha3_256);
        SPHINCS_SHA512_256 = new C1168r5(lh0.id_sha512_256);
        XMSS_SHA256 = new C1168r5(lh0.id_sha256);
        XMSS_SHA512 = new C1168r5(lh0.id_sha512);
        XMSS_SHAKE128 = new C1168r5(lh0.id_shake128);
        XMSS_SHAKE256 = new C1168r5(lh0.id_shake256);
        HashMap map = new HashMap();
        categories = map;
        map.put(c0160c5, q60.valueOf(5));
        map.put(c0160c52, q60.valueOf(6));
    }

    public static C1168r5 getAlgorithmIdentifier(String str) {
        if (str.equals("SHA-1")) {
            return new C1168r5(pk0.idSHA1, C1046ow.INSTANCE);
        }
        if (str.equals("SHA-224")) {
            return new C1168r5(lh0.id_sha224);
        }
        if (str.equals(ki1.SHA_256)) {
            return new C1168r5(lh0.id_sha256);
        }
        if (str.equals("SHA-384")) {
            return new C1168r5(lh0.id_sha384);
        }
        if (str.equals(ki1.SHA_512)) {
            return new C1168r5(lh0.id_sha512);
        }
        throw new IllegalArgumentException("unrecognised digest algorithm: ".concat(str));
    }

    public static InterfaceC1236sv getDigest(C0160c5 c0160c5) {
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha256)) {
            return new us0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha512)) {
            return new xs0();
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake128)) {
            return new zs0(128);
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_shake256)) {
            return new zs0(PSKKeyManager.MAX_KEY_LENGTH_BYTES);
        }
        throw new IllegalArgumentException("unrecognized digest OID: " + c0160c5);
    }

    public static String getDigestName(C0160c5 c0160c5) {
        if (c0160c5.equals((AbstractC0164c9) pk0.idSHA1)) {
            return "SHA-1";
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha224)) {
            return "SHA-224";
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha256)) {
            return ki1.SHA_256;
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha384)) {
            return "SHA-384";
        }
        if (c0160c5.equals((AbstractC0164c9) lh0.id_sha512)) {
            return ki1.SHA_512;
        }
        throw new IllegalArgumentException("unrecognised digest algorithm: " + c0160c5);
    }

    public static C1168r5 qTeslaLookupAlgID(int i) {
        if (i == 5) {
            return AlgID_qTESLA_p_I;
        }
        if (i == 6) {
            return AlgID_qTESLA_p_III;
        }
        throw new IllegalArgumentException(tz0.m214802a2(i, "unknown security category: "));
    }

    public static int qTeslaLookupSecurityCategory(C1168r5 c1168r5) {
        return ((Integer) categories.get(c1168r5.getAlgorithm())).intValue();
    }

    public static C1168r5 sphincs256LookupTreeAlgID(String str) {
        if (str.equals(ft0.SHA3_256)) {
            return SPHINCS_SHA3_256;
        }
        if (str.equals(ft0.SHA512_256)) {
            return SPHINCS_SHA512_256;
        }
        throw new IllegalArgumentException("unknown tree digest: ".concat(str));
    }

    public static String sphincs256LookupTreeAlgName(et0 et0Var) {
        C1168r5 treeDigest = et0Var.getTreeDigest();
        if (treeDigest.getAlgorithm().equals((AbstractC0164c9) SPHINCS_SHA3_256.getAlgorithm())) {
            return ft0.SHA3_256;
        }
        if (treeDigest.getAlgorithm().equals((AbstractC0164c9) SPHINCS_SHA512_256.getAlgorithm())) {
            return ft0.SHA512_256;
        }
        throw new IllegalArgumentException("unknown tree digest: " + treeDigest.getAlgorithm());
    }

    public static C1168r5 xmssLookupTreeAlgID(String str) {
        if (str.equals(ki1.SHA_256)) {
            return XMSS_SHA256;
        }
        if (str.equals(ki1.SHA_512)) {
            return XMSS_SHA512;
        }
        if (str.equals(ki1.SHAKE128)) {
            return XMSS_SHAKE128;
        }
        if (str.equals(ki1.SHAKE256)) {
            return XMSS_SHAKE256;
        }
        throw new IllegalArgumentException("unknown tree digest: ".concat(str));
    }
}
