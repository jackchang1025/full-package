package org.bouncycastle.pqc.crypto.lms;

import org.bouncycastle.crypto.Digest;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
class LM_OTS {
    static final short D_MESG = -32383;
    private static final short D_PBLC = -32640;
    private static final int ITER_J = 22;
    private static final int ITER_K = 20;
    private static final int ITER_PREV = 23;
    static final int MAX_HASH = 32;
    static final int SEED_LEN = 32;
    static final int SEED_RANDOMISER_INDEX = -3;

    public static int cksm(byte[] bArr, int i2, LMOtsParameters lMOtsParameters) {
        int w2 = (1 << lMOtsParameters.getW()) - 1;
        int i3 = 0;
        for (int i4 = 0; i4 < (i2 * 8) / lMOtsParameters.getW(); i4++) {
            i3 = (i3 + w2) - coef(bArr, i4, lMOtsParameters.getW());
        }
        return i3 << lMOtsParameters.getLs();
    }

    public static int coef(byte[] bArr, int i2, int i3) {
        int i4 = (i2 * i3) / 8;
        return (bArr[i4] >>> (((~i2) & ((8 / i3) - 1)) * i3)) & ((1 << i3) - 1);
    }

    public static LMOtsSignature lm_ots_generate_signature(LMOtsPrivateKey lMOtsPrivateKey, byte[] bArr, byte[] bArr2) {
        LMOtsParameters parameter = lMOtsPrivateKey.getParameter();
        int n2 = parameter.getN();
        int p2 = parameter.getP();
        int w2 = parameter.getW();
        byte[] bArr3 = new byte[p2 * n2];
        Digest digest = DigestUtil.getDigest(parameter.getDigestOID());
        SeedDerive derivationFunction = lMOtsPrivateKey.getDerivationFunction();
        int cksm = cksm(bArr, n2, parameter);
        bArr[n2] = (byte) ((cksm >>> 8) & 255);
        bArr[n2 + 1] = (byte) cksm;
        int i2 = n2 + 23;
        byte[] build = Composer.compose().bytes(lMOtsPrivateKey.getI()).u32str(lMOtsPrivateKey.getQ()).padUntil(0, i2).build();
        derivationFunction.setJ(0);
        int i3 = 0;
        while (i3 < p2) {
            Pack.shortToBigEndian((short) i3, build, 20);
            int i4 = 23;
            derivationFunction.deriveSeed(build, i3 < p2 + (-1), 23);
            int coef = coef(bArr, i3, w2);
            for (int i5 = 0; i5 < coef; i5++) {
                build[22] = (byte) i5;
                digest.update(build, 0, i2);
                i4 = 23;
                digest.doFinal(build, 23);
            }
            System.arraycopy(build, i4, bArr3, n2 * i3, n2);
            i3++;
        }
        return new LMOtsSignature(parameter, bArr2, bArr3);
    }

    public static boolean lm_ots_validate_signature(LMOtsPublicKey lMOtsPublicKey, LMOtsSignature lMOtsSignature, byte[] bArr, boolean z2) {
        if (lMOtsSignature.getType().equals(lMOtsPublicKey.getParameter())) {
            return Arrays.areEqual(lm_ots_validate_signature_calculate(lMOtsPublicKey, lMOtsSignature, bArr), lMOtsPublicKey.getK());
        }
        throw new LMSException("public key and signature ots types do not match");
    }

    public static byte[] lm_ots_validate_signature_calculate(LMOtsPublicKey lMOtsPublicKey, LMOtsSignature lMOtsSignature, byte[] bArr) {
        LMSContext createOtsContext = lMOtsPublicKey.createOtsContext(lMOtsSignature);
        LmsUtils.byteArray(bArr, createOtsContext);
        return lm_ots_validate_signature_calculate(createOtsContext);
    }

    public static LMOtsPublicKey lms_ots_generatePublicKey(LMOtsPrivateKey lMOtsPrivateKey) {
        return new LMOtsPublicKey(lMOtsPrivateKey.getParameter(), lMOtsPrivateKey.getI(), lMOtsPrivateKey.getQ(), lms_ots_generatePublicKey(lMOtsPrivateKey.getParameter(), lMOtsPrivateKey.getI(), lMOtsPrivateKey.getQ(), lMOtsPrivateKey.getMasterSecret()));
    }

    public static LMOtsSignature lm_ots_generate_signature(LMSigParameters lMSigParameters, LMOtsPrivateKey lMOtsPrivateKey, byte[][] bArr, byte[] bArr2, boolean z2) {
        byte[] bArr3;
        byte[] bArr4 = new byte[34];
        if (z2) {
            bArr3 = new byte[32];
            System.arraycopy(bArr2, 0, bArr4, 0, lMOtsPrivateKey.getParameter().getN());
        } else {
            LMSContext signatureContext = lMOtsPrivateKey.getSignatureContext(lMSigParameters, bArr);
            LmsUtils.byteArray(bArr2, 0, bArr2.length, signatureContext);
            bArr3 = signatureContext.getC();
            bArr4 = signatureContext.getQ();
        }
        return lm_ots_generate_signature(lMOtsPrivateKey, bArr4, bArr3);
    }

    public static byte[] lm_ots_validate_signature_calculate(LMSContext lMSContext) {
        LMOtsPublicKey publicKey = lMSContext.getPublicKey();
        LMOtsParameters parameter = publicKey.getParameter();
        Object signature = lMSContext.getSignature();
        LMOtsSignature otsSignature = signature instanceof LMSSignature ? ((LMSSignature) signature).getOtsSignature() : (LMOtsSignature) signature;
        int n2 = parameter.getN();
        int w2 = parameter.getW();
        int p2 = parameter.getP();
        byte[] q = lMSContext.getQ();
        int cksm = cksm(q, n2, parameter);
        q[n2] = (byte) ((cksm >>> 8) & 255);
        q[n2 + 1] = (byte) cksm;
        byte[] i2 = publicKey.getI();
        int q2 = publicKey.getQ();
        Digest digest = DigestUtil.getDigest(parameter.getDigestOID());
        LmsUtils.byteArray(i2, digest);
        LmsUtils.u32str(q2, digest);
        LmsUtils.u16str(D_PBLC, digest);
        Composer u32str = Composer.compose().bytes(i2).u32str(q2);
        int i3 = n2 + 23;
        byte[] build = u32str.padUntil(0, i3).build();
        int i4 = (1 << w2) - 1;
        byte[] y2 = otsSignature.getY();
        Digest digest2 = DigestUtil.getDigest(parameter.getDigestOID());
        for (int i5 = 0; i5 < p2; i5++) {
            Pack.shortToBigEndian((short) i5, build, 20);
            System.arraycopy(y2, i5 * n2, build, 23, n2);
            for (int coef = coef(q, i5, w2); coef < i4; coef++) {
                build[22] = (byte) coef;
                digest2.update(build, 0, i3);
                digest2.doFinal(build, 23);
            }
            digest.update(build, 23, n2);
        }
        byte[] bArr = new byte[n2];
        digest.doFinal(bArr, 0);
        return bArr;
    }

    public static byte[] lms_ots_generatePublicKey(LMOtsParameters lMOtsParameters, byte[] bArr, int i2, byte[] bArr2) {
        Digest digest = DigestUtil.getDigest(lMOtsParameters.getDigestOID());
        byte[] build = Composer.compose().bytes(bArr).u32str(i2).u16str(-32640).padUntil(0, 22).build();
        digest.update(build, 0, build.length);
        Digest digest2 = DigestUtil.getDigest(lMOtsParameters.getDigestOID());
        byte[] build2 = Composer.compose().bytes(bArr).u32str(i2).padUntil(0, digest2.getDigestSize() + 23).build();
        SeedDerive seedDerive = new SeedDerive(bArr, bArr2, DigestUtil.getDigest(lMOtsParameters.getDigestOID()));
        seedDerive.setQ(i2);
        seedDerive.setJ(0);
        int p2 = lMOtsParameters.getP();
        int n2 = lMOtsParameters.getN();
        int w2 = (1 << lMOtsParameters.getW()) - 1;
        int i3 = 0;
        while (i3 < p2) {
            seedDerive.deriveSeed(build2, i3 < p2 + (-1), 23);
            Pack.shortToBigEndian((short) i3, build2, 20);
            for (int i4 = 0; i4 < w2; i4++) {
                build2[22] = (byte) i4;
                digest2.update(build2, 0, build2.length);
                digest2.doFinal(build2, 23);
            }
            digest.update(build2, 23, n2);
            i3++;
        }
        byte[] bArr3 = new byte[digest.getDigestSize()];
        digest.doFinal(bArr3, 0);
        return bArr3;
    }
}
