package p000;

import java.util.HashMap;
import java.util.Map;

/* loaded from: classes2.dex */
public class xf0 {
    private static Map<C0160c5, String> digestOidMap;

    static {
        HashMap map = new HashMap();
        digestOidMap = map;
        map.put(ul0.md2, "MD2");
        digestOidMap.put(ul0.md4, "MD4");
        digestOidMap.put(ul0.md5, "MD5");
        digestOidMap.put(pk0.idSHA1, "SHA-1");
        digestOidMap.put(lh0.id_sha224, "SHA-224");
        digestOidMap.put(lh0.id_sha256, ki1.SHA_256);
        digestOidMap.put(lh0.id_sha384, "SHA-384");
        digestOidMap.put(lh0.id_sha512, ki1.SHA_512);
        digestOidMap.put(lh0.id_sha512_224, "SHA-512(224)");
        digestOidMap.put(lh0.id_sha512_256, "SHA-512(256)");
        digestOidMap.put(o51.ripemd128, "RIPEMD-128");
        digestOidMap.put(o51.ripemd160, "RIPEMD-160");
        digestOidMap.put(o51.ripemd256, "RIPEMD-128");
        digestOidMap.put(t40.ripemd128, "RIPEMD-128");
        digestOidMap.put(t40.ripemd160, "RIPEMD-160");
        digestOidMap.put(InterfaceC0928nw.gostR3411, "GOST3411");
        digestOidMap.put(h20.Tiger_192, "Tiger");
        digestOidMap.put(t40.whirlpool, "Whirlpool");
        digestOidMap.put(lh0.id_sha3_224, "SHA3-224");
        digestOidMap.put(lh0.id_sha3_256, ft0.SHA3_256);
        digestOidMap.put(lh0.id_sha3_384, "SHA3-384");
        digestOidMap.put(lh0.id_sha3_512, "SHA3-512");
        digestOidMap.put(lh0.id_shake128, ki1.SHAKE128);
        digestOidMap.put(lh0.id_shake256, ki1.SHAKE256);
        digestOidMap.put(g20.sm3, "SM3");
    }

    public static String getDigestName(C0160c5 c0160c5) {
        String str = digestOidMap.get(c0160c5);
        return str != null ? str : c0160c5.getId();
    }
}
