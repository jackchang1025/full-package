package p000;

/* loaded from: classes2.dex */
public class d91 {
    public static C1168r5 getDigAlgId(String str) {
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

    public static InterfaceC1236sv getDigest(C1168r5 c1168r5) {
        if (c1168r5.getAlgorithm().equals((AbstractC0164c9) pk0.idSHA1)) {
            return C1240sz.createSHA1();
        }
        if (c1168r5.getAlgorithm().equals((AbstractC0164c9) lh0.id_sha224)) {
            return C1240sz.createSHA224();
        }
        if (c1168r5.getAlgorithm().equals((AbstractC0164c9) lh0.id_sha256)) {
            return C1240sz.createSHA256();
        }
        if (c1168r5.getAlgorithm().equals((AbstractC0164c9) lh0.id_sha384)) {
            return C1240sz.createSHA384();
        }
        if (c1168r5.getAlgorithm().equals((AbstractC0164c9) lh0.id_sha512)) {
            return C1240sz.createSHA512();
        }
        throw new IllegalArgumentException("unrecognised OID in digest algorithm identifier: " + c1168r5.getAlgorithm());
    }
}
