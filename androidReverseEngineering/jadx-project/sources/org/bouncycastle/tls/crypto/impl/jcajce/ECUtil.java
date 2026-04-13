package org.bouncycastle.tls.crypto.impl.jcajce;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.KeyPairGenerator;
import java.security.PrivateKey;
import java.security.interfaces.ECKey;
import java.security.interfaces.ECPrivateKey;
import java.security.spec.AlgorithmParameterSpec;
import java.security.spec.ECField;
import java.security.spec.ECFieldF2m;
import java.security.spec.ECFieldFp;
import java.security.spec.ECGenParameterSpec;
import java.security.spec.ECParameterSpec;
import java.security.spec.EllipticCurve;
import org.bouncycastle.math.ec.ECCurve;

/* loaded from: classes.dex */
class ECUtil {
    public static ECCurve convertCurve(EllipticCurve ellipticCurve, BigInteger bigInteger, int i2) {
        ECField field = ellipticCurve.getField();
        BigInteger a2 = ellipticCurve.getA();
        BigInteger b = ellipticCurve.getB();
        if (field instanceof ECFieldFp) {
            return new ECCurve.Fp(((ECFieldFp) field).getP(), a2, b, bigInteger, BigInteger.valueOf(i2));
        }
        ECFieldF2m eCFieldF2m = (ECFieldF2m) field;
        int m2 = eCFieldF2m.getM();
        int[] convertMidTerms = convertMidTerms(eCFieldF2m.getMidTermsOfReductionPolynomial());
        return new ECCurve.F2m(m2, convertMidTerms[0], convertMidTerms[1], convertMidTerms[2], a2, b, bigInteger, BigInteger.valueOf(i2));
    }

    public static int[] convertMidTerms(int[] iArr) {
        int i2;
        int[] iArr2 = new int[3];
        if (iArr.length == 1) {
            iArr2[0] = iArr[0];
        } else {
            if (iArr.length != 3) {
                throw new IllegalArgumentException("Only Trinomials and pentanomials supported");
            }
            int i3 = iArr[0];
            int i4 = iArr[1];
            if (i3 >= i4 || i3 >= (i2 = iArr[2])) {
                int i5 = iArr[2];
                if (i4 < i5) {
                    iArr2[0] = i4;
                    int i6 = iArr[0];
                    if (i6 < i5) {
                        iArr2[1] = i6;
                        iArr2[2] = i5;
                    } else {
                        iArr2[1] = i5;
                        iArr2[2] = i6;
                    }
                } else {
                    iArr2[0] = i5;
                    int i7 = iArr[0];
                    if (i7 < i4) {
                        iArr2[1] = i7;
                        iArr2[2] = iArr[1];
                    } else {
                        iArr2[1] = i4;
                        iArr2[2] = i7;
                    }
                }
            } else {
                iArr2[0] = i3;
                if (i4 < i2) {
                    iArr2[1] = i4;
                    iArr2[2] = i2;
                } else {
                    iArr2[1] = i2;
                    iArr2[2] = iArr[1];
                }
            }
        }
        return iArr2;
    }

    public static AlgorithmParameterSpec createInitSpec(String str) {
        return new ECGenParameterSpec(str);
    }

    public static AlgorithmParameters getAlgorithmParameters(JcaTlsCrypto jcaTlsCrypto, String str) {
        return getAlgorithmParameters(jcaTlsCrypto, new ECGenParameterSpec(str));
    }

    public static ECParameterSpec getECParameterSpec(JcaTlsCrypto jcaTlsCrypto, String str) {
        return getECParameterSpec(jcaTlsCrypto, createInitSpec(str));
    }

    public static boolean isCurveSupported(JcaTlsCrypto jcaTlsCrypto, String str) {
        return str != null && isCurveSupported(jcaTlsCrypto, new ECGenParameterSpec(str));
    }

    public static boolean isECPrivateKey(PrivateKey privateKey) {
        return (privateKey instanceof ECPrivateKey) || "EC".equalsIgnoreCase(privateKey.getAlgorithm());
    }

    public static AlgorithmParameters getAlgorithmParameters(JcaTlsCrypto jcaTlsCrypto, AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            AlgorithmParameters createAlgorithmParameters = jcaTlsCrypto.getHelper().createAlgorithmParameters("EC");
            createAlgorithmParameters.init(algorithmParameterSpec);
            if (((ECParameterSpec) createAlgorithmParameters.getParameterSpec(ECParameterSpec.class)) != null) {
                return createAlgorithmParameters;
            }
            return null;
        } catch (AssertionError | Exception unused) {
            return null;
        }
    }

    public static ECParameterSpec getECParameterSpec(JcaTlsCrypto jcaTlsCrypto, AlgorithmParameterSpec algorithmParameterSpec) {
        try {
            KeyPairGenerator createKeyPairGenerator = jcaTlsCrypto.getHelper().createKeyPairGenerator("EC");
            createKeyPairGenerator.initialize(algorithmParameterSpec, jcaTlsCrypto.getSecureRandom());
            try {
                AlgorithmParameters createAlgorithmParameters = jcaTlsCrypto.getHelper().createAlgorithmParameters("EC");
                createAlgorithmParameters.init(algorithmParameterSpec);
                ECParameterSpec eCParameterSpec = (ECParameterSpec) createAlgorithmParameters.getParameterSpec(ECParameterSpec.class);
                if (eCParameterSpec != null) {
                    return eCParameterSpec;
                }
            } catch (AssertionError | Exception unused) {
            }
            return ((ECKey) createKeyPairGenerator.generateKeyPair().getPrivate()).getParams();
        } catch (AssertionError | Exception unused2) {
            return null;
        }
    }

    public static boolean isCurveSupported(JcaTlsCrypto jcaTlsCrypto, ECGenParameterSpec eCGenParameterSpec) {
        return getECParameterSpec(jcaTlsCrypto, eCGenParameterSpec) != null;
    }
}
