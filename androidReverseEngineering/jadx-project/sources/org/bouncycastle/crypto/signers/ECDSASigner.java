package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DSAExt;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECConstants;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECDSASigner implements ECConstants, DSAExt {
    private final DSAKCalculator kCalculator;
    private ECKeyParameters key;
    private SecureRandom random;

    public ECDSASigner() {
        this.kCalculator = new RandomDSAKCalculator();
    }

    public BigInteger calculateE(BigInteger bigInteger, byte[] bArr) {
        int bitLength = bigInteger.bitLength();
        int length = bArr.length * 8;
        BigInteger bigInteger2 = new BigInteger(1, bArr);
        return bitLength < length ? bigInteger2.shiftRight(length - bitLength) : bigInteger2;
    }

    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    @Override // org.bouncycastle.crypto.DSA
    public BigInteger[] generateSignature(byte[] bArr) {
        ECDomainParameters parameters = this.key.getParameters();
        BigInteger n2 = parameters.getN();
        BigInteger calculateE = calculateE(n2, bArr);
        BigInteger d2 = ((ECPrivateKeyParameters) this.key).getD();
        if (this.kCalculator.isDeterministic()) {
            this.kCalculator.init(n2, d2, bArr);
        } else {
            this.kCalculator.init(n2, this.random);
        }
        ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        while (true) {
            BigInteger nextK = this.kCalculator.nextK();
            BigInteger mod = createBasePointMultiplier.multiply(parameters.getG(), nextK).normalize().getAffineXCoord().toBigInteger().mod(n2);
            BigInteger bigInteger = ECConstants.ZERO;
            if (!mod.equals(bigInteger)) {
                BigInteger mod2 = BigIntegers.modOddInverse(n2, nextK).multiply(calculateE.add(d2.multiply(mod))).mod(n2);
                if (!mod2.equals(bigInteger)) {
                    return new BigInteger[]{mod, mod2};
                }
            }
        }
    }

    public ECFieldElement getDenominator(int i2, ECPoint eCPoint) {
        if (i2 != 1) {
            if (i2 == 2 || i2 == 3 || i2 == 4) {
                return eCPoint.getZCoord(0).square();
            }
            if (i2 != 6 && i2 != 7) {
                return null;
            }
        }
        return eCPoint.getZCoord(0);
    }

    @Override // org.bouncycastle.crypto.DSAExt
    public BigInteger getOrder() {
        return this.key.getParameters().getN();
    }

    @Override // org.bouncycastle.crypto.DSA
    public void init(boolean z2, CipherParameters cipherParameters) {
        ECKeyParameters eCKeyParameters;
        SecureRandom secureRandom;
        if (!z2) {
            eCKeyParameters = (ECPublicKeyParameters) cipherParameters;
        } else {
            if (cipherParameters instanceof ParametersWithRandom) {
                ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
                this.key = (ECPrivateKeyParameters) parametersWithRandom.getParameters();
                secureRandom = parametersWithRandom.getRandom();
                this.random = initSecureRandom((z2 || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
            }
            eCKeyParameters = (ECPrivateKeyParameters) cipherParameters;
        }
        this.key = eCKeyParameters;
        secureRandom = null;
        this.random = initSecureRandom((z2 || this.kCalculator.isDeterministic()) ? false : true, secureRandom);
    }

    public SecureRandom initSecureRandom(boolean z2, SecureRandom secureRandom) {
        if (z2) {
            return CryptoServicesRegistrar.getSecureRandom(secureRandom);
        }
        return null;
    }

    @Override // org.bouncycastle.crypto.DSA
    public boolean verifySignature(byte[] bArr, BigInteger bigInteger, BigInteger bigInteger2) {
        BigInteger cofactor;
        ECFieldElement denominator;
        ECDomainParameters parameters = this.key.getParameters();
        BigInteger n2 = parameters.getN();
        BigInteger calculateE = calculateE(n2, bArr);
        BigInteger bigInteger3 = ECConstants.ONE;
        if (bigInteger.compareTo(bigInteger3) < 0 || bigInteger.compareTo(n2) >= 0 || bigInteger2.compareTo(bigInteger3) < 0 || bigInteger2.compareTo(n2) >= 0) {
            return false;
        }
        BigInteger modOddInverseVar = BigIntegers.modOddInverseVar(n2, bigInteger2);
        ECPoint sumOfTwoMultiplies = ECAlgorithms.sumOfTwoMultiplies(parameters.getG(), calculateE.multiply(modOddInverseVar).mod(n2), ((ECPublicKeyParameters) this.key).getQ(), bigInteger.multiply(modOddInverseVar).mod(n2));
        if (sumOfTwoMultiplies.isInfinity()) {
            return false;
        }
        ECCurve curve = sumOfTwoMultiplies.getCurve();
        if (curve == null || (cofactor = curve.getCofactor()) == null || cofactor.compareTo(ECConstants.EIGHT) > 0 || (denominator = getDenominator(curve.getCoordinateSystem(), sumOfTwoMultiplies)) == null || denominator.isZero()) {
            return sumOfTwoMultiplies.normalize().getAffineXCoord().toBigInteger().mod(n2).equals(bigInteger);
        }
        ECFieldElement xCoord = sumOfTwoMultiplies.getXCoord();
        while (curve.isValidFieldElement(bigInteger)) {
            if (curve.fromBigInteger(bigInteger).multiply(denominator).equals(xCoord)) {
                return true;
            }
            bigInteger = bigInteger.add(n2);
        }
        return false;
    }

    public ECDSASigner(DSAKCalculator dSAKCalculator) {
        this.kCalculator = dSAKCalculator;
    }
}
