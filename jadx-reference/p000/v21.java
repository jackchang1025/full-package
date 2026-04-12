package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public class v21 {
    private v21() {
    }

    public static u21 createSubjectPublicKeyInfo(C0136bj c0136bj) throws IOException {
        if (c0136bj instanceof lp0) {
            lp0 lp0Var = (lp0) c0136bj;
            return new u21(e91.qTeslaLookupAlgID(lp0Var.getSecurityCategory()), lp0Var.getPublicData());
        }
        if (c0136bj instanceof ht0) {
            ht0 ht0Var = (ht0) c0136bj;
            return new u21(new C1168r5(vl0.sphincs256, new et0(e91.sphincs256LookupTreeAlgID(ht0Var.getTreeDigest()))), ht0Var.getKeyData());
        }
        if (c0136bj instanceof jh0) {
            return new u21(new C1168r5(vl0.newHope), ((jh0) c0136bj).getPubData());
        }
        if (c0136bj instanceof q90) {
            return new u21(new C1168r5(ul0.id_alg_hss_lms_hashsig), new C1048oy(C0752kb.compose().u32str(1).bytes((q90) c0136bj).build()));
        }
        if (c0136bj instanceof t30) {
            t30 t30Var = (t30) c0136bj;
            return new u21(new C1168r5(ul0.id_alg_hss_lms_hashsig), new C1048oy(C0752kb.compose().u32str(t30Var.getL()).bytes(t30Var.getLMSPublicKey()).build()));
        }
        if (c0136bj instanceof dj1) {
            dj1 dj1Var = (dj1) c0136bj;
            byte[] publicSeed = dj1Var.getPublicSeed();
            byte[] root = dj1Var.getRoot();
            byte[] encoded = dj1Var.getEncoded();
            return encoded.length > publicSeed.length + root.length ? new u21(new C1168r5(a70.id_alg_xmss), new C1048oy(encoded)) : new u21(new C1168r5(vl0.xmss, new li1(dj1Var.getParameters().getHeight(), e91.xmssLookupTreeAlgID(dj1Var.getTreeDigest()))), new bj1(publicSeed, root));
        }
        if (!(c0136bj instanceof wi1)) {
            if (!(c0136bj instanceof le0)) {
                throw new IOException("key parameters not recognized");
            }
            le0 le0Var = (le0) c0136bj;
            return new u21(new C1168r5(vl0.mcElieceCca2), new ke0(le0Var.getN(), le0Var.getT(), le0Var.getG(), e91.getAlgorithmIdentifier(le0Var.getDigest())));
        }
        wi1 wi1Var = (wi1) c0136bj;
        byte[] publicSeed2 = wi1Var.getPublicSeed();
        byte[] root2 = wi1Var.getRoot();
        byte[] encoded2 = wi1Var.getEncoded();
        return encoded2.length > publicSeed2.length + root2.length ? new u21(new C1168r5(a70.id_alg_xmssmt), new C1048oy(encoded2)) : new u21(new C1168r5(vl0.xmss_mt, new pi1(wi1Var.getParameters().getHeight(), wi1Var.getParameters().getLayers(), e91.xmssLookupTreeAlgID(wi1Var.getTreeDigest()))), new ui1(wi1Var.getPublicSeed(), wi1Var.getRoot()));
    }
}
