package p000;

import java.util.Arrays;
import java.util.List;
import org.bouncycastle.pqc.crypto.ExhaustedPrivateKeyException;
import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class q30 {

    /* renamed from: q30$a0 */
    public static class C1096a0 extends p90 {
        public C1096a0(LMSigParameters lMSigParameters, LMOtsParameters lMOtsParameters, int i, byte[] bArr, int i2, byte[] bArr2) {
            super(lMSigParameters, lMOtsParameters, i, bArr, i2, bArr2);
        }

        @Override // p000.p90
        public f90 getNextOtsPrivateKey() {
            throw new RuntimeException("placeholder only");
        }

        @Override // p000.p90
        public q90 getPublicKey() {
            throw new RuntimeException("placeholder only");
        }
    }

    public static s30 generateHSSKeyPair(r30 r30Var) {
        byte[] bArr;
        int depth = r30Var.getDepth();
        p90[] p90VarArr = new p90[depth];
        r90[] r90VarArr = new r90[r30Var.getDepth() - 1];
        byte[] bArr2 = new byte[32];
        r30Var.getRandom().nextBytes(bArr2);
        byte[] bArr3 = new byte[16];
        r30Var.getRandom().nextBytes(bArr3);
        int i = 0;
        byte[] bArr4 = new byte[0];
        long h = 1;
        while (i < depth) {
            if (i == 0) {
                p90VarArr[i] = new p90(r30Var.getLmsParameters()[i].getLMSigParam(), r30Var.getLmsParameters()[i].getLMOTSParam(), 0, bArr3, 1 << r30Var.getLmsParameters()[i].getLMSigParam().getH(), bArr2);
                bArr = bArr4;
            } else {
                bArr = bArr4;
                p90VarArr[i] = new C1096a0(r30Var.getLmsParameters()[i].getLMSigParam(), r30Var.getLmsParameters()[i].getLMOTSParam(), -1, bArr, 1 << r30Var.getLmsParameters()[i].getLMSigParam().getH(), bArr);
            }
            h *= 1 << r30Var.getLmsParameters()[i].getLMSigParam().getH();
            i++;
            bArr4 = bArr;
        }
        if (h == 0) {
            h = Long.MAX_VALUE;
        }
        return new s30(r30Var.getDepth(), Arrays.asList(p90VarArr), Arrays.asList(r90VarArr), 0L, h);
    }

    public static u30 generateSignature(int i, j90 j90Var) {
        return new u30(i - 1, j90Var.getSignedPubKeys(), i90.generateSign(j90Var));
    }

    public static void incrementIndex(s30 s30Var) {
        synchronized (s30Var) {
            rangeTestKeys(s30Var);
            s30Var.incIndex();
            s30Var.getKeys().get(s30Var.getL() - 1).incIndex();
        }
    }

    public static void rangeTestKeys(s30 s30Var) {
        synchronized (s30Var) {
            try {
                if (s30Var.getIndex() >= s30Var.getIndexLimit()) {
                    StringBuilder sb = new StringBuilder("hss private key");
                    sb.append(s30Var.isShard() ? " shard" : "");
                    sb.append(" is exhausted");
                    throw new ExhaustedPrivateKeyException(sb.toString());
                }
                int l = s30Var.getL();
                List<p90> keys = s30Var.getKeys();
                int i = l;
                while (true) {
                    int i2 = i - 1;
                    if (keys.get(i2).getIndex() != (1 << keys.get(i2).getSigParameters().getH())) {
                        while (i < l) {
                            s30Var.replaceConsumedKey(i);
                            i++;
                        }
                    } else {
                        if (i2 == 0) {
                            StringBuilder sb2 = new StringBuilder();
                            sb2.append("hss private key");
                            sb2.append(s30Var.isShard() ? " shard" : "");
                            sb2.append(" is exhausted the maximum limit for this HSS private key");
                            throw new ExhaustedPrivateKeyException(sb2.toString());
                        }
                        i = i2;
                    }
                }
            } finally {
            }
        }
    }

    public static boolean verifySignature(t30 t30Var, u30 u30Var, byte[] bArr) {
        int i = u30Var.getlMinus1();
        int i2 = i + 1;
        if (i2 != t30Var.getL()) {
            return false;
        }
        r90[] r90VarArr = new r90[i2];
        q90[] q90VarArr = new q90[i];
        for (int i3 = 0; i3 < i; i3++) {
            r90VarArr[i3] = u30Var.getSignedPubKey()[i3].getSignature();
            q90VarArr[i3] = u30Var.getSignedPubKey()[i3].getPublicKey();
        }
        r90VarArr[i] = u30Var.getSignature();
        q90 lMSPublicKey = t30Var.getLMSPublicKey();
        for (int i4 = 0; i4 < i; i4++) {
            if (!i90.verifySignature(lMSPublicKey, r90VarArr[i4], q90VarArr[i4].toByteArray())) {
                return false;
            }
            try {
                lMSPublicKey = q90VarArr[i4];
            } catch (Exception e) {
                throw new IllegalStateException(e.getMessage(), e);
            }
        }
        return i90.verifySignature(lMSPublicKey, r90VarArr[i], bArr);
    }

    public static u30 generateSignature(s30 s30Var, byte[] bArr) {
        p90 p90Var;
        s90[] s90VarArr;
        int l = s30Var.getL();
        synchronized (s30Var) {
            try {
                rangeTestKeys(s30Var);
                List<p90> keys = s30Var.getKeys();
                List<r90> sig = s30Var.getSig();
                int i = l - 1;
                p90Var = s30Var.getKeys().get(i);
                s90VarArr = new s90[i];
                int i2 = 0;
                while (i2 < i) {
                    int i3 = i2 + 1;
                    s90VarArr[i2] = new s90(sig.get(i2), keys.get(i3).getPublicKey());
                    i2 = i3;
                }
                s30Var.incIndex();
            } catch (Throwable th) {
                throw th;
            }
        }
        j90 j90VarWithSignedPublicKeys = p90Var.generateLMSContext().withSignedPublicKeys(s90VarArr);
        j90VarWithSignedPublicKeys.update(bArr, 0, bArr.length);
        return generateSignature(l, j90VarWithSignedPublicKeys);
    }
}
