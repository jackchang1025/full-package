package p000;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;
import java.util.Map;
import p000.dj1;
import p000.wi1;

/* loaded from: classes2.dex */
public class gp0 {
    private static Map converters;

    /* renamed from: gp0$a0 */
    public static class C0545a0 extends AbstractC0550a5 {
        private C0545a0() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            byte[] octets = AbstractC0161c6.getInstance(u21Var.parsePublicKey()).getOctets();
            if (wl0.bigEndianToInt(octets, 0) == 1) {
                return q90.getInstance(C0133bg.copyOfRange(octets, 4, octets.length));
            }
            if (octets.length == 64) {
                octets = C0133bg.copyOfRange(octets, 4, octets.length);
            }
            return t30.getInstance(octets);
        }
    }

    /* renamed from: gp0$a1 */
    public static class C0546a1 extends AbstractC0550a5 {
        private C0546a1() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            ke0 ke0Var = ke0.getInstance(u21Var.parsePublicKey());
            return new le0(ke0Var.getN(), ke0Var.getT(), ke0Var.getG(), e91.getDigestName(ke0Var.getDigest().getAlgorithm()));
        }
    }

    /* renamed from: gp0$a2 */
    public static class C0547a2 extends AbstractC0550a5 {
        private C0547a2() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            return new jh0(u21Var.getPublicKeyData().getBytes());
        }
    }

    /* renamed from: gp0$a3 */
    public static class C0548a3 extends AbstractC0550a5 {
        private C0548a3() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            return new lp0(e91.qTeslaLookupSecurityCategory(u21Var.getAlgorithm()), u21Var.getPublicKeyData().getOctets());
        }
    }

    /* renamed from: gp0$a4 */
    public static class C0549a4 extends AbstractC0550a5 {
        private C0549a4() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            return new ht0(u21Var.getPublicKeyData().getBytes(), e91.sphincs256LookupTreeAlgName(et0.getInstance(u21Var.getAlgorithm().getParameters())));
        }
    }

    /* renamed from: gp0$a5 */
    public static abstract class AbstractC0550a5 {
        private AbstractC0550a5() {
        }

        public abstract C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException;
    }

    /* renamed from: gp0$a6 */
    public static class C0551a6 extends AbstractC0550a5 {
        private C0551a6() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            dj1.C0419a0 c0419a0WithPublicKey;
            li1 li1Var = li1.getInstance(u21Var.getAlgorithm().getParameters());
            if (li1Var != null) {
                C0160c5 algorithm = li1Var.getTreeDigest().getAlgorithm();
                bj1 bj1Var = bj1.getInstance(u21Var.parsePublicKey());
                c0419a0WithPublicKey = new dj1.C0419a0(new yi1(li1Var.getHeight(), e91.getDigest(algorithm))).withPublicSeed(bj1Var.getPublicSeed()).withRoot(bj1Var.getRoot());
            } else {
                byte[] octets = AbstractC0161c6.getInstance(u21Var.parsePublicKey()).getOctets();
                c0419a0WithPublicKey = new dj1.C0419a0(yi1.lookupByOID(wl0.bigEndianToInt(octets, 0))).withPublicKey(octets);
            }
            return c0419a0WithPublicKey.build();
        }
    }

    /* renamed from: gp0$a7 */
    public static class C0552a7 extends AbstractC0550a5 {
        private C0552a7() {
            super();
        }

        @Override // p000.gp0.AbstractC0550a5
        public C0136bj getPublicKeyParameters(u21 u21Var, Object obj) throws IOException {
            wi1.C1378a0 c1378a0WithPublicKey;
            pi1 pi1Var = pi1.getInstance(u21Var.getAlgorithm().getParameters());
            if (pi1Var != null) {
                C0160c5 algorithm = pi1Var.getTreeDigest().getAlgorithm();
                bj1 bj1Var = bj1.getInstance(u21Var.parsePublicKey());
                c1378a0WithPublicKey = new wi1.C1378a0(new qi1(pi1Var.getHeight(), pi1Var.getLayers(), e91.getDigest(algorithm))).withPublicSeed(bj1Var.getPublicSeed()).withRoot(bj1Var.getRoot());
            } else {
                byte[] octets = AbstractC0161c6.getInstance(u21Var.parsePublicKey()).getOctets();
                c1378a0WithPublicKey = new wi1.C1378a0(qi1.lookupByOID(wl0.bigEndianToInt(octets, 0))).withPublicKey(octets);
            }
            return c1378a0WithPublicKey.build();
        }
    }

    static {
        HashMap map = new HashMap();
        converters = map;
        map.put(vl0.qTESLA_p_I, new C0548a3());
        converters.put(vl0.qTESLA_p_III, new C0548a3());
        converters.put(vl0.sphincs256, new C0549a4());
        converters.put(vl0.newHope, new C0547a2());
        converters.put(vl0.xmss, new C0551a6());
        converters.put(vl0.xmss_mt, new C0552a7());
        converters.put(a70.id_alg_xmss, new C0551a6());
        converters.put(a70.id_alg_xmssmt, new C0552a7());
        converters.put(ul0.id_alg_hss_lms_hashsig, new C0545a0());
        converters.put(vl0.mcElieceCca2, new C0546a1());
    }

    public static C0136bj createKey(u21 u21Var) throws IOException {
        return createKey(u21Var, null);
    }

    public static C0136bj createKey(u21 u21Var, Object obj) throws IOException {
        C1168r5 algorithm = u21Var.getAlgorithm();
        AbstractC0550a5 abstractC0550a5 = (AbstractC0550a5) converters.get(algorithm.getAlgorithm());
        if (abstractC0550a5 != null) {
            return abstractC0550a5.getPublicKeyParameters(u21Var, obj);
        }
        throw new IOException("algorithm identifier in public key not recognised: " + algorithm.getAlgorithm());
    }

    public static C0136bj createKey(InputStream inputStream) throws IOException {
        return createKey(u21.getInstance(new C0126b9(inputStream).readObject()));
    }

    public static C0136bj createKey(byte[] bArr) throws IOException {
        return createKey(u21.getInstance(AbstractC0164c9.fromByteArray(bArr)));
    }
}
