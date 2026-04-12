package p000;

import java.io.IOException;
import org.bouncycastle.pqc.crypto.xmss.BDS;
import org.bouncycastle.pqc.crypto.xmss.BDSStateMap;
import org.bouncycastle.pqc.crypto.xmss.C1004a6;

/* loaded from: classes2.dex */
public class jo0 {
    private jo0() {
    }

    public static io0 createPrivateKeyInfo(C0136bj c0136bj) throws IOException {
        return createPrivateKeyInfo(c0136bj, null);
    }

    private static zi1 xmssCreateKeyStructure(C1004a6 c1004a6) throws IOException {
        byte[] encoded = c1004a6.getEncoded();
        int treeDigestSize = c1004a6.getParameters().getTreeDigestSize();
        int height = c1004a6.getParameters().getHeight();
        int iBytesToXBigEndian = (int) fj1.bytesToXBigEndian(encoded, 0, 4);
        if (!fj1.isIndexValid(height, iBytesToXBigEndian)) {
            throw new IllegalArgumentException("index out of bounds");
        }
        byte[] bArrExtractBytesAtOffset = fj1.extractBytesAtOffset(encoded, 4, treeDigestSize);
        int i = 4 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset2 = fj1.extractBytesAtOffset(encoded, i, treeDigestSize);
        int i2 = i + treeDigestSize;
        byte[] bArrExtractBytesAtOffset3 = fj1.extractBytesAtOffset(encoded, i2, treeDigestSize);
        int i3 = i2 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset4 = fj1.extractBytesAtOffset(encoded, i3, treeDigestSize);
        int i4 = i3 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset5 = fj1.extractBytesAtOffset(encoded, i4, encoded.length - i4);
        try {
            BDS bds = (BDS) fj1.deserialize(bArrExtractBytesAtOffset5, BDS.class);
            return bds.getMaxIndex() != (1 << height) - 1 ? new zi1(iBytesToXBigEndian, bArrExtractBytesAtOffset, bArrExtractBytesAtOffset2, bArrExtractBytesAtOffset3, bArrExtractBytesAtOffset4, bArrExtractBytesAtOffset5, bds.getMaxIndex()) : new zi1(iBytesToXBigEndian, bArrExtractBytesAtOffset, bArrExtractBytesAtOffset2, bArrExtractBytesAtOffset3, bArrExtractBytesAtOffset4, bArrExtractBytesAtOffset5);
        } catch (ClassNotFoundException e) {
            throw new IOException("cannot parse BDS: " + e.getMessage());
        }
    }

    private static ri1 xmssmtCreateKeyStructure(ti1 ti1Var) throws IOException {
        byte[] encoded = ti1Var.getEncoded();
        int treeDigestSize = ti1Var.getParameters().getTreeDigestSize();
        int height = ti1Var.getParameters().getHeight();
        int i = (height + 7) / 8;
        long jBytesToXBigEndian = (int) fj1.bytesToXBigEndian(encoded, 0, i);
        if (!fj1.isIndexValid(height, jBytesToXBigEndian)) {
            throw new IllegalArgumentException("index out of bounds");
        }
        byte[] bArrExtractBytesAtOffset = fj1.extractBytesAtOffset(encoded, i, treeDigestSize);
        int i2 = i + treeDigestSize;
        byte[] bArrExtractBytesAtOffset2 = fj1.extractBytesAtOffset(encoded, i2, treeDigestSize);
        int i3 = i2 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset3 = fj1.extractBytesAtOffset(encoded, i3, treeDigestSize);
        int i4 = i3 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset4 = fj1.extractBytesAtOffset(encoded, i4, treeDigestSize);
        int i5 = i4 + treeDigestSize;
        byte[] bArrExtractBytesAtOffset5 = fj1.extractBytesAtOffset(encoded, i5, encoded.length - i5);
        try {
            BDSStateMap bDSStateMap = (BDSStateMap) fj1.deserialize(bArrExtractBytesAtOffset5, BDSStateMap.class);
            return bDSStateMap.getMaxIndex() != (1 << height) - 1 ? new ri1(jBytesToXBigEndian, bArrExtractBytesAtOffset, bArrExtractBytesAtOffset2, bArrExtractBytesAtOffset3, bArrExtractBytesAtOffset4, bArrExtractBytesAtOffset5, bDSStateMap.getMaxIndex()) : new ri1(jBytesToXBigEndian, bArrExtractBytesAtOffset, bArrExtractBytesAtOffset2, bArrExtractBytesAtOffset3, bArrExtractBytesAtOffset4, bArrExtractBytesAtOffset5);
        } catch (ClassNotFoundException e) {
            throw new IOException("cannot parse BDSStateMap: " + e.getMessage());
        }
    }

    public static io0 createPrivateKeyInfo(C0136bj c0136bj, AbstractC0402d4 abstractC0402d4) throws IOException {
        if (c0136bj instanceof kp0) {
            kp0 kp0Var = (kp0) c0136bj;
            return new io0(e91.qTeslaLookupAlgID(kp0Var.getSecurityCategory()), new C1048oy(kp0Var.getSecret()), abstractC0402d4);
        }
        if (c0136bj instanceof gt0) {
            gt0 gt0Var = (gt0) c0136bj;
            return new io0(new C1168r5(vl0.sphincs256, new et0(e91.sphincs256LookupTreeAlgID(gt0Var.getTreeDigest()))), new C1048oy(gt0Var.getKeyData()));
        }
        if (c0136bj instanceof ih0) {
            C1168r5 c1168r5 = new C1168r5(vl0.newHope);
            short[] secData = ((ih0) c0136bj).getSecData();
            byte[] bArr = new byte[secData.length * 2];
            for (int i = 0; i != secData.length; i++) {
                wl0.shortToLittleEndian(secData[i], bArr, i * 2);
            }
            return new io0(c1168r5, new C1048oy(bArr));
        }
        if (c0136bj instanceof p90) {
            p90 p90Var = (p90) c0136bj;
            byte[] bArrBuild = C0752kb.compose().u32str(1).bytes(p90Var).build();
            return new io0(new C1168r5(ul0.id_alg_hss_lms_hashsig), new C1048oy(bArrBuild), abstractC0402d4, C0752kb.compose().u32str(1).bytes(p90Var.getPublicKey()).build());
        }
        if (c0136bj instanceof s30) {
            s30 s30Var = (s30) c0136bj;
            byte[] bArrBuild2 = C0752kb.compose().u32str(s30Var.getL()).bytes(s30Var).build();
            return new io0(new C1168r5(ul0.id_alg_hss_lms_hashsig), new C1048oy(bArrBuild2), abstractC0402d4, C0752kb.compose().u32str(s30Var.getL()).bytes(s30Var.getPublicKey().getLMSPublicKey()).build());
        }
        if (c0136bj instanceof C1004a6) {
            C1004a6 c1004a6 = (C1004a6) c0136bj;
            return new io0(new C1168r5(vl0.xmss, new li1(c1004a6.getParameters().getHeight(), e91.xmssLookupTreeAlgID(c1004a6.getTreeDigest()))), xmssCreateKeyStructure(c1004a6), abstractC0402d4);
        }
        if (c0136bj instanceof ti1) {
            ti1 ti1Var = (ti1) c0136bj;
            return new io0(new C1168r5(vl0.xmss_mt, new pi1(ti1Var.getParameters().getHeight(), ti1Var.getParameters().getLayers(), e91.xmssLookupTreeAlgID(ti1Var.getTreeDigest()))), xmssmtCreateKeyStructure(ti1Var), abstractC0402d4);
        }
        if (!(c0136bj instanceof je0)) {
            throw new IOException("key parameters not recognized");
        }
        je0 je0Var = (je0) c0136bj;
        return new io0(new C1168r5(vl0.mcElieceCca2), new ie0(je0Var.getN(), je0Var.getK(), je0Var.getField(), je0Var.getGoppaPoly(), je0Var.getP(), e91.getAlgorithmIdentifier(je0Var.getDigest())));
    }
}
