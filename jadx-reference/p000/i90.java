package p000;

import org.bouncycastle.pqc.crypto.lms.LMOtsParameters;
import org.bouncycastle.pqc.crypto.lms.LMSigParameters;

/* loaded from: classes2.dex */
public class i90 {
    static final short D_INTR = -31869;
    static final short D_LEAF = -32126;

    public static p90 generateKeys(LMSigParameters lMSigParameters, LMOtsParameters lMOtsParameters, int i, byte[] bArr, byte[] bArr2) throws IllegalArgumentException {
        if (bArr2 != null && bArr2.length >= lMSigParameters.getM()) {
            return new p90(lMSigParameters, lMOtsParameters, i, bArr, 1 << lMSigParameters.getH(), bArr2);
        }
        throw new IllegalArgumentException("root seed is less than " + lMSigParameters.getM());
    }

    public static r90 generateSign(j90 j90Var) {
        return new r90(j90Var.getPrivateKey().getQ(), t90.lm_ots_generate_signature(j90Var.getPrivateKey(), j90Var.getQ(), j90Var.getC()), j90Var.getSigParams(), j90Var.getPath());
    }

    public static boolean verifySignature(q90 q90Var, j90 j90Var) {
        r90 r90Var = (r90) j90Var.getSignature();
        LMSigParameters parameter = r90Var.getParameter();
        int h = parameter.getH();
        byte[][] y = r90Var.getY();
        byte[] bArrLm_ots_validate_signature_calculate = t90.lm_ots_validate_signature_calculate(j90Var);
        int q = r90Var.getQ() + (1 << h);
        byte[] i = q90Var.getI();
        InterfaceC1236sv digest = C1256te.getDigest(parameter.getDigestOID());
        int digestSize = digest.getDigestSize();
        byte[] bArr = new byte[digestSize];
        digest.update(i, 0, i.length);
        xb0.u32str(q, digest);
        xb0.u16str(D_LEAF, digest);
        digest.update(bArrLm_ots_validate_signature_calculate, 0, bArrLm_ots_validate_signature_calculate.length);
        digest.doFinal(bArr, 0);
        int i2 = 0;
        while (q > 1) {
            if ((q & 1) == 1) {
                digest.update(i, 0, i.length);
                xb0.u32str(q / 2, digest);
                xb0.u16str(D_INTR, digest);
                byte[] bArr2 = y[i2];
                digest.update(bArr2, 0, bArr2.length);
                digest.update(bArr, 0, digestSize);
            } else {
                digest.update(i, 0, i.length);
                xb0.u32str(q / 2, digest);
                xb0.u16str(D_INTR, digest);
                digest.update(bArr, 0, digestSize);
                byte[] bArr3 = y[i2];
                digest.update(bArr3, 0, bArr3.length);
            }
            digest.doFinal(bArr, 0);
            q /= 2;
            i2++;
        }
        return q90Var.matchesT1(bArr);
    }

    public static r90 generateSign(p90 p90Var, byte[] bArr) {
        j90 j90VarGenerateLMSContext = p90Var.generateLMSContext();
        j90VarGenerateLMSContext.update(bArr, 0, bArr.length);
        return generateSign(j90VarGenerateLMSContext);
    }

    public static boolean verifySignature(q90 q90Var, r90 r90Var, byte[] bArr) {
        j90 j90VarGenerateOtsContext = q90Var.generateOtsContext(r90Var);
        xb0.byteArray(bArr, j90VarGenerateOtsContext);
        return verifySignature(q90Var, j90VarGenerateOtsContext);
    }

    public static boolean verifySignature(q90 q90Var, byte[] bArr, byte[] bArr2) {
        j90 j90VarGenerateLMSContext = q90Var.generateLMSContext(bArr);
        xb0.byteArray(bArr2, j90VarGenerateLMSContext);
        return verifySignature(q90Var, j90VarGenerateLMSContext);
    }
}
