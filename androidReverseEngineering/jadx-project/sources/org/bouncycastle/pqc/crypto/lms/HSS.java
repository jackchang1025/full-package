package org.bouncycastle.pqc.crypto.lms;

import java.util.Arrays;
import java.util.List;
import org.bouncycastle.pqc.crypto.ExhaustedPrivateKeyException;
import com.guard.wallet.entity.BuildConfig;

/* loaded from: classes.dex */
class HSS {

    public static class PlaceholderLMSPrivateKey extends LMSPrivateKeyParameters {
        public PlaceholderLMSPrivateKey(LMSigParameters lMSigParameters, LMOtsParameters lMOtsParameters, int i2, byte[] bArr, int i3, byte[] bArr2) {
            super(lMSigParameters, lMOtsParameters, i2, bArr, i3, bArr2);
        }

        @Override // org.bouncycastle.pqc.crypto.lms.LMSPrivateKeyParameters
        public LMOtsPrivateKey getNextOtsPrivateKey() {
            throw new RuntimeException("placeholder only");
        }

        @Override // org.bouncycastle.pqc.crypto.lms.LMSPrivateKeyParameters
        public LMSPublicKeyParameters getPublicKey() {
            throw new RuntimeException("placeholder only");
        }
    }

    public static HSSPrivateKeyParameters generateHSSKeyPair(HSSKeyGenerationParameters hSSKeyGenerationParameters) {
        int i2;
        byte[] bArr;
        int depth = hSSKeyGenerationParameters.getDepth();
        LMSPrivateKeyParameters[] lMSPrivateKeyParametersArr = new LMSPrivateKeyParameters[depth];
        LMSSignature[] lMSSignatureArr = new LMSSignature[hSSKeyGenerationParameters.getDepth() - 1];
        byte[] bArr2 = new byte[32];
        hSSKeyGenerationParameters.getRandom().nextBytes(bArr2);
        byte[] bArr3 = new byte[16];
        hSSKeyGenerationParameters.getRandom().nextBytes(bArr3);
        byte[] bArr4 = new byte[0];
        int i3 = 0;
        long j2 = 1;
        while (i3 < depth) {
            if (i3 == 0) {
                lMSPrivateKeyParametersArr[i3] = new LMSPrivateKeyParameters(hSSKeyGenerationParameters.getLmsParameters()[i3].getLMSigParam(), hSSKeyGenerationParameters.getLmsParameters()[i3].getLMOTSParam(), 0, bArr3, 1 << hSSKeyGenerationParameters.getLmsParameters()[i3].getLMSigParam().getH(), bArr2);
                i2 = i3;
                bArr = bArr4;
            } else {
                i2 = i3;
                bArr = bArr4;
                lMSPrivateKeyParametersArr[i2] = new PlaceholderLMSPrivateKey(hSSKeyGenerationParameters.getLmsParameters()[i3].getLMSigParam(), hSSKeyGenerationParameters.getLmsParameters()[i3].getLMOTSParam(), -1, bArr, 1 << hSSKeyGenerationParameters.getLmsParameters()[i3].getLMSigParam().getH(), bArr);
            }
            j2 *= 1 << hSSKeyGenerationParameters.getLmsParameters()[i2].getLMSigParam().getH();
            i3 = i2 + 1;
            bArr4 = bArr;
        }
        if (j2 == 0) {
            j2 = Long.MAX_VALUE;
        }
        return new HSSPrivateKeyParameters(hSSKeyGenerationParameters.getDepth(), Arrays.asList(lMSPrivateKeyParametersArr), Arrays.asList(lMSSignatureArr), 0L, j2);
    }

    public static HSSSignature generateSignature(int i2, LMSContext lMSContext) {
        return new HSSSignature(i2 - 1, lMSContext.getSignedPubKeys(), LMS.generateSign(lMSContext));
    }

    public static void incrementIndex(HSSPrivateKeyParameters hSSPrivateKeyParameters) {
        synchronized (hSSPrivateKeyParameters) {
            rangeTestKeys(hSSPrivateKeyParameters);
            hSSPrivateKeyParameters.incIndex();
            hSSPrivateKeyParameters.getKeys().get(hSSPrivateKeyParameters.getL() - 1).incIndex();
        }
    }

    public static void rangeTestKeys(HSSPrivateKeyParameters hSSPrivateKeyParameters) {
        synchronized (hSSPrivateKeyParameters) {
            if (hSSPrivateKeyParameters.getIndex() >= hSSPrivateKeyParameters.getIndexLimit()) {
                StringBuilder sb = new StringBuilder("hss private key");
                sb.append(hSSPrivateKeyParameters.isShard() ? " shard" : BuildConfig.FLAVOR);
                sb.append(" is exhausted");
                throw new ExhaustedPrivateKeyException(sb.toString());
            }
            int l2 = hSSPrivateKeyParameters.getL();
            List<LMSPrivateKeyParameters> keys = hSSPrivateKeyParameters.getKeys();
            int i2 = l2;
            while (true) {
                int i3 = i2 - 1;
                if (keys.get(i3).getIndex() != (1 << keys.get(i3).getSigParameters().getH())) {
                    while (i2 < l2) {
                        hSSPrivateKeyParameters.replaceConsumedKey(i2);
                        i2++;
                    }
                } else {
                    if (i3 == 0) {
                        StringBuilder sb2 = new StringBuilder();
                        sb2.append("hss private key");
                        sb2.append(hSSPrivateKeyParameters.isShard() ? " shard" : BuildConfig.FLAVOR);
                        sb2.append(" is exhausted the maximum limit for this HSS private key");
                        throw new ExhaustedPrivateKeyException(sb2.toString());
                    }
                    i2 = i3;
                }
            }
        }
    }

    public static boolean verifySignature(HSSPublicKeyParameters hSSPublicKeyParameters, HSSSignature hSSSignature, byte[] bArr) {
        int i2 = hSSSignature.getlMinus1();
        int i3 = i2 + 1;
        if (i3 != hSSPublicKeyParameters.getL()) {
            return false;
        }
        LMSSignature[] lMSSignatureArr = new LMSSignature[i3];
        LMSPublicKeyParameters[] lMSPublicKeyParametersArr = new LMSPublicKeyParameters[i2];
        for (int i4 = 0; i4 < i2; i4++) {
            lMSSignatureArr[i4] = hSSSignature.getSignedPubKey()[i4].getSignature();
            lMSPublicKeyParametersArr[i4] = hSSSignature.getSignedPubKey()[i4].getPublicKey();
        }
        lMSSignatureArr[i2] = hSSSignature.getSignature();
        LMSPublicKeyParameters lMSPublicKey = hSSPublicKeyParameters.getLMSPublicKey();
        for (int i5 = 0; i5 < i2; i5++) {
            if (!LMS.verifySignature(lMSPublicKey, lMSSignatureArr[i5], lMSPublicKeyParametersArr[i5].toByteArray())) {
                return false;
            }
            try {
                lMSPublicKey = lMSPublicKeyParametersArr[i5];
            } catch (Exception e2) {
                throw new IllegalStateException(e2.getMessage(), e2);
            }
        }
        return LMS.verifySignature(lMSPublicKey, lMSSignatureArr[i2], bArr);
    }

    public static HSSSignature generateSignature(HSSPrivateKeyParameters hSSPrivateKeyParameters, byte[] bArr) {
        LMSPrivateKeyParameters lMSPrivateKeyParameters;
        LMSSignedPubKey[] lMSSignedPubKeyArr;
        int l2 = hSSPrivateKeyParameters.getL();
        synchronized (hSSPrivateKeyParameters) {
            rangeTestKeys(hSSPrivateKeyParameters);
            List<LMSPrivateKeyParameters> keys = hSSPrivateKeyParameters.getKeys();
            List<LMSSignature> sig = hSSPrivateKeyParameters.getSig();
            int i2 = l2 - 1;
            lMSPrivateKeyParameters = hSSPrivateKeyParameters.getKeys().get(i2);
            lMSSignedPubKeyArr = new LMSSignedPubKey[i2];
            int i3 = 0;
            while (i3 < i2) {
                int i4 = i3 + 1;
                lMSSignedPubKeyArr[i3] = new LMSSignedPubKey(sig.get(i3), keys.get(i4).getPublicKey());
                i3 = i4;
            }
            hSSPrivateKeyParameters.incIndex();
        }
        LMSContext withSignedPublicKeys = lMSPrivateKeyParameters.generateLMSContext().withSignedPublicKeys(lMSSignedPubKeyArr);
        withSignedPublicKeys.update(bArr, 0, bArr.length);
        return generateSignature(l2, withSignedPublicKeys);
    }
}
