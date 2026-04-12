package p000;

import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSException;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class t90 {
    static final short D_MESG = -32383;
    private static final short D_PBLC = -32640;
    private static final int ITER_J = 22;
    private static final int ITER_K = 20;
    private static final int ITER_PREV = 23;
    static final int MAX_HASH = 32;
    static final int SEED_LEN = 32;
    static final int SEED_RANDOMISER_INDEX = -3;

    public static int cksm(byte[] bArr, int i, LMOtsParameters lMOtsParameters) {
        int w = (1 << lMOtsParameters.getW()) - 1;
        int iCoef = 0;
        for (int i2 = 0; i2 < (i * 8) / lMOtsParameters.getW(); i2++) {
            iCoef = (iCoef + w) - coef(bArr, i2, lMOtsParameters.getW());
        }
        return iCoef << lMOtsParameters.getLs();
    }

    public static int coef(byte[] bArr, int i, int i2) {
        int i3 = (i * i2) / 8;
        return (bArr[i3] >>> (((~i) & ((8 / i2) - 1)) * i2)) & ((1 << i2) - 1);
    }

    public static h90 lm_ots_generate_signature(f90 f90Var, byte[] bArr, byte[] bArr2) {
        LMOtsParameters parameter = f90Var.getParameter();
        int n = parameter.getN();
        int p = parameter.getP();
        int w = parameter.getW();
        byte[] bArr3 = new byte[p * n];
        InterfaceC1236sv digest = C1256te.getDigest(parameter.getDigestOID());
        iz0 derivationFunction = f90Var.getDerivationFunction();
        int iCksm = cksm(bArr, n, parameter);
        bArr[n] = (byte) ((iCksm >>> 8) & v10.MASK);
        bArr[n + 1] = (byte) iCksm;
        C0752kb c0752kbU32str = C0752kb.compose().bytes(f90Var.getI()).u32str(f90Var.getQ());
        int i = n + ITER_PREV;
        byte[] bArrBuild = c0752kbU32str.padUntil(0, i).build();
        derivationFunction.setJ(0);
        int i2 = 0;
        while (i2 < p) {
            wl0.shortToBigEndian((short) i2, bArrBuild, ITER_K);
            derivationFunction.deriveSeed(bArrBuild, i2 < p + (-1), ITER_PREV);
            int iCoef = coef(bArr, i2, w);
            for (int i3 = 0; i3 < iCoef; i3++) {
                bArrBuild[ITER_J] = (byte) i3;
                digest.update(bArrBuild, 0, i);
                digest.doFinal(bArrBuild, ITER_PREV);
            }
            System.arraycopy(bArrBuild, ITER_PREV, bArr3, n * i2, n);
            i2++;
        }
        return new h90(parameter, bArr2, bArr3);
    }

    public static boolean lm_ots_validate_signature(g90 g90Var, h90 h90Var, byte[] bArr, boolean z) throws LMSException {
        if (h90Var.getType().equals(g90Var.getParameter())) {
            return C0133bg.areEqual(lm_ots_validate_signature_calculate(g90Var, h90Var, bArr), g90Var.getK());
        }
        throw new LMSException("public key and signature ots types do not match");
    }

    public static byte[] lm_ots_validate_signature_calculate(g90 g90Var, h90 h90Var, byte[] bArr) {
        j90 j90VarCreateOtsContext = g90Var.createOtsContext(h90Var);
        xb0.byteArray(bArr, j90VarCreateOtsContext);
        return lm_ots_validate_signature_calculate(j90VarCreateOtsContext);
    }

    public static g90 lms_ots_generatePublicKey(f90 f90Var) {
        return new g90(f90Var.getParameter(), f90Var.getI(), f90Var.getQ(), lms_ots_generatePublicKey(f90Var.getParameter(), f90Var.getI(), f90Var.getQ(), f90Var.getMasterSecret()));
    }

    public static h90 lm_ots_generate_signature(LMSigParameters lMSigParameters, f90 f90Var, byte[][] bArr, byte[] bArr2, boolean z) {
        byte[] c;
        byte[] q = new byte[34];
        if (z) {
            c = new byte[32];
            System.arraycopy(bArr2, 0, q, 0, f90Var.getParameter().getN());
        } else {
            j90 signatureContext = f90Var.getSignatureContext(lMSigParameters, bArr);
            xb0.byteArray(bArr2, 0, bArr2.length, signatureContext);
            c = signatureContext.getC();
            q = signatureContext.getQ();
        }
        return lm_ots_generate_signature(f90Var, q, c);
    }

    public static byte[] lm_ots_validate_signature_calculate(j90 j90Var) {
        g90 publicKey = j90Var.getPublicKey();
        LMOtsParameters parameter = publicKey.getParameter();
        Object signature = j90Var.getSignature();
        h90 otsSignature = signature instanceof r90 ? ((r90) signature).getOtsSignature() : (h90) signature;
        int n = parameter.getN();
        int w = parameter.getW();
        int p = parameter.getP();
        byte[] q = j90Var.getQ();
        int iCksm = cksm(q, n, parameter);
        q[n] = (byte) ((iCksm >>> 8) & v10.MASK);
        q[n + 1] = (byte) iCksm;
        byte[] i = publicKey.getI();
        int q2 = publicKey.getQ();
        InterfaceC1236sv digest = C1256te.getDigest(parameter.getDigestOID());
        xb0.byteArray(i, digest);
        xb0.u32str(q2, digest);
        xb0.u16str(D_PBLC, digest);
        C0752kb c0752kbU32str = C0752kb.compose().bytes(i).u32str(q2);
        int i2 = n + ITER_PREV;
        byte[] bArrBuild = c0752kbU32str.padUntil(0, i2).build();
        int i3 = (1 << w) - 1;
        byte[] y = otsSignature.getY();
        InterfaceC1236sv digest2 = C1256te.getDigest(parameter.getDigestOID());
        for (int i4 = 0; i4 < p; i4++) {
            wl0.shortToBigEndian((short) i4, bArrBuild, ITER_K);
            System.arraycopy(y, i4 * n, bArrBuild, ITER_PREV, n);
            for (int iCoef = coef(q, i4, w); iCoef < i3; iCoef++) {
                bArrBuild[ITER_J] = (byte) iCoef;
                digest2.update(bArrBuild, 0, i2);
                digest2.doFinal(bArrBuild, ITER_PREV);
            }
            digest.update(bArrBuild, ITER_PREV, n);
        }
        byte[] bArr = new byte[n];
        digest.doFinal(bArr, 0);
        return bArr;
    }

    public static byte[] lms_ots_generatePublicKey(LMOtsParameters lMOtsParameters, byte[] bArr, int i, byte[] bArr2) {
        InterfaceC1236sv digest = C1256te.getDigest(lMOtsParameters.getDigestOID());
        byte[] bArrBuild = C0752kb.compose().bytes(bArr).u32str(i).u16str(-32640).padUntil(0, ITER_J).build();
        digest.update(bArrBuild, 0, bArrBuild.length);
        InterfaceC1236sv digest2 = C1256te.getDigest(lMOtsParameters.getDigestOID());
        byte[] bArrBuild2 = C0752kb.compose().bytes(bArr).u32str(i).padUntil(0, digest2.getDigestSize() + ITER_PREV).build();
        iz0 iz0Var = new iz0(bArr, bArr2, C1256te.getDigest(lMOtsParameters.getDigestOID()));
        iz0Var.setQ(i);
        iz0Var.setJ(0);
        int p = lMOtsParameters.getP();
        int n = lMOtsParameters.getN();
        int w = (1 << lMOtsParameters.getW()) - 1;
        int i2 = 0;
        while (i2 < p) {
            iz0Var.deriveSeed(bArrBuild2, i2 < p + (-1), ITER_PREV);
            wl0.shortToBigEndian((short) i2, bArrBuild2, ITER_K);
            for (int i3 = 0; i3 < w; i3++) {
                bArrBuild2[ITER_J] = (byte) i3;
                digest2.update(bArrBuild2, 0, bArrBuild2.length);
                digest2.doFinal(bArrBuild2, ITER_PREV);
            }
            digest.update(bArrBuild2, ITER_PREV, n);
            i2++;
        }
        byte[] bArr3 = new byte[digest.getDigestSize()];
        digest.doFinal(bArr3, 0);
        return bArr3;
    }
}
