package p000;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.pqc.crypto.xmss.BDS;
import org.bouncycastle.pqc.crypto.xmss.BDSStateMap;
import org.bouncycastle.pqc.crypto.xmss.C1004a6;
import p000.ti1;

/* loaded from: classes2.dex */
public class ho0 {
    private static short[] convert(byte[] bArr) {
        int length = bArr.length / 2;
        short[] sArr = new short[length];
        for (int i = 0; i != length; i++) {
            sArr[i] = wl0.littleEndianToShort(bArr, i * 2);
        }
        return sArr;
    }

    public static C0136bj createKey(io0 io0Var) throws IOException {
        C0160c5 algorithm = io0Var.getPrivateKeyAlgorithm().getAlgorithm();
        if (algorithm.m210761on(InterfaceC0167cc.qTESLA)) {
            return new kp0(e91.qTeslaLookupSecurityCategory(io0Var.getPrivateKeyAlgorithm()), AbstractC0161c6.getInstance(io0Var.parsePrivateKey()).getOctets());
        }
        if (algorithm.equals((AbstractC0164c9) InterfaceC0167cc.sphincs256)) {
            return new gt0(AbstractC0161c6.getInstance(io0Var.parsePrivateKey()).getOctets(), e91.sphincs256LookupTreeAlgName(et0.getInstance(io0Var.getPrivateKeyAlgorithm().getParameters())));
        }
        if (algorithm.equals((AbstractC0164c9) InterfaceC0167cc.newHope)) {
            return new ih0(convert(AbstractC0161c6.getInstance(io0Var.parsePrivateKey()).getOctets()));
        }
        if (algorithm.equals((AbstractC0164c9) ul0.id_alg_hss_lms_hashsig)) {
            byte[] octets = AbstractC0161c6.getInstance(io0Var.parsePrivateKey()).getOctets();
            AbstractC0007a6 publicKeyData = io0Var.getPublicKeyData();
            if (wl0.bigEndianToInt(octets, 0) == 1) {
                if (publicKeyData == null) {
                    return p90.getInstance(C0133bg.copyOfRange(octets, 4, octets.length));
                }
                byte[] octets2 = publicKeyData.getOctets();
                return p90.getInstance(C0133bg.copyOfRange(octets, 4, octets.length), C0133bg.copyOfRange(octets2, 4, octets2.length));
            }
            if (publicKeyData == null) {
                return s30.getInstance(C0133bg.copyOfRange(octets, 4, octets.length));
            }
            return s30.getInstance(C0133bg.copyOfRange(octets, 4, octets.length), publicKeyData.getOctets());
        }
        if (algorithm.equals((AbstractC0164c9) InterfaceC0167cc.xmss)) {
            li1 li1Var = li1.getInstance(io0Var.getPrivateKeyAlgorithm().getParameters());
            C0160c5 algorithm2 = li1Var.getTreeDigest().getAlgorithm();
            zi1 zi1Var = zi1.getInstance(io0Var.parsePrivateKey());
            try {
                C1004a6.a0 a0VarWithRoot = new C1004a6.a0(new yi1(li1Var.getHeight(), e91.getDigest(algorithm2))).withIndex(zi1Var.getIndex()).withSecretKeySeed(zi1Var.getSecretKeySeed()).withSecretKeyPRF(zi1Var.getSecretKeyPRF()).withPublicSeed(zi1Var.getPublicSeed()).withRoot(zi1Var.getRoot());
                if (zi1Var.getVersion() != 0) {
                    a0VarWithRoot.withMaxIndex(zi1Var.getMaxIndex());
                }
                if (zi1Var.getBdsState() != null) {
                    a0VarWithRoot.withBDSState(((BDS) fj1.deserialize(zi1Var.getBdsState(), BDS.class)).withWOTSDigest(algorithm2));
                }
                return a0VarWithRoot.build();
            } catch (ClassNotFoundException e) {
                throw new IOException("ClassNotFoundException processing BDS state: " + e.getMessage());
            }
        }
        if (!algorithm.equals((AbstractC0164c9) vl0.xmss_mt)) {
            if (!algorithm.equals((AbstractC0164c9) vl0.mcElieceCca2)) {
                throw new RuntimeException("algorithm identifier in private key not recognised");
            }
            ie0 ie0Var = ie0.getInstance(io0Var.parsePrivateKey());
            return new je0(ie0Var.getN(), ie0Var.getK(), ie0Var.getField(), ie0Var.getGoppaPoly(), ie0Var.getP(), e91.getDigestName(ie0Var.getDigest().getAlgorithm()));
        }
        pi1 pi1Var = pi1.getInstance(io0Var.getPrivateKeyAlgorithm().getParameters());
        C0160c5 algorithm3 = pi1Var.getTreeDigest().getAlgorithm();
        try {
            ri1 ri1Var = ri1.getInstance(io0Var.parsePrivateKey());
            ti1.C1261a0 c1261a0WithRoot = new ti1.C1261a0(new qi1(pi1Var.getHeight(), pi1Var.getLayers(), e91.getDigest(algorithm3))).withIndex(ri1Var.getIndex()).withSecretKeySeed(ri1Var.getSecretKeySeed()).withSecretKeyPRF(ri1Var.getSecretKeyPRF()).withPublicSeed(ri1Var.getPublicSeed()).withRoot(ri1Var.getRoot());
            if (ri1Var.getVersion() != 0) {
                c1261a0WithRoot.withMaxIndex(ri1Var.getMaxIndex());
            }
            if (ri1Var.getBdsState() != null) {
                c1261a0WithRoot.withBDSState(((BDSStateMap) fj1.deserialize(ri1Var.getBdsState(), BDSStateMap.class)).withWOTSDigest(algorithm3));
            }
            return c1261a0WithRoot.build();
        } catch (ClassNotFoundException e2) {
            throw new IOException("ClassNotFoundException processing BDS state: " + e2.getMessage());
        }
    }

    public static C0136bj createKey(InputStream inputStream) throws IOException {
        return createKey(io0.getInstance(new C0126b9(inputStream).readObject()));
    }

    public static C0136bj createKey(byte[] bArr) throws IOException {
        return createKey(io0.getInstance(AbstractC0164c9.fromByteArray(bArr)));
    }
}
