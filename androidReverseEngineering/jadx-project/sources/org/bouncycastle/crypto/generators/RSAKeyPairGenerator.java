package org.bouncycastle.crypto.generators;

import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.RSAKeyGenerationParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.math.Primes;
import org.bouncycastle.math.ec.WNafUtil;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class RSAKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private RSAKeyGenerationParameters param;

    private static int getNumberOfIterations(int i2, int i3) {
        if (i2 >= 1536) {
            if (i3 <= 100) {
                return 3;
            }
            if (i3 <= 128) {
                return 4;
            }
            return 4 + (((i3 - 128) + 1) / 2);
        }
        if (i2 >= 1024) {
            if (i3 <= 100) {
                return 4;
            }
            if (i3 <= 112) {
                return 5;
            }
            return (((i3 - 112) + 1) / 2) + 5;
        }
        if (i2 < 512) {
            if (i3 <= 80) {
                return 40;
            }
            return 40 + (((i3 - 80) + 1) / 2);
        }
        if (i3 <= 80) {
            return 5;
        }
        if (i3 <= 100) {
            return 7;
        }
        return 7 + (((i3 - 100) + 1) / 2);
    }

    public BigInteger chooseRandomPrime(int i2, BigInteger bigInteger, BigInteger bigInteger2) {
        for (int i3 = 0; i3 != i2 * 5; i3++) {
            BigInteger createRandomPrime = BigIntegers.createRandomPrime(i2, 1, this.param.getRandom());
            BigInteger mod = createRandomPrime.mod(bigInteger);
            BigInteger bigInteger3 = ONE;
            if (!mod.equals(bigInteger3) && createRandomPrime.multiply(createRandomPrime).compareTo(bigInteger2) >= 0 && isProbablePrime(createRandomPrime) && bigInteger.gcd(createRandomPrime.subtract(bigInteger3)).equals(bigInteger3)) {
                return createRandomPrime;
            }
        }
        throw new IllegalStateException("unable to generate prime number for RSA key");
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger chooseRandomPrime;
        BigInteger chooseRandomPrime2;
        BigInteger multiply;
        BigInteger bigInteger;
        RSAKeyPairGenerator rSAKeyPairGenerator = this;
        int strength = rSAKeyPairGenerator.param.getStrength();
        int i2 = (strength + 1) / 2;
        int i3 = strength - i2;
        int i4 = strength / 2;
        int i5 = i4 - 100;
        int i6 = strength / 3;
        if (i5 < i6) {
            i5 = i6;
        }
        int i7 = strength >> 2;
        BigInteger pow = BigInteger.valueOf(2L).pow(i4);
        BigInteger bigInteger2 = ONE;
        BigInteger shiftLeft = bigInteger2.shiftLeft(strength - 1);
        BigInteger shiftLeft2 = bigInteger2.shiftLeft(i5);
        AsymmetricCipherKeyPair asymmetricCipherKeyPair = null;
        boolean z2 = false;
        while (!z2) {
            BigInteger publicExponent = rSAKeyPairGenerator.param.getPublicExponent();
            do {
                chooseRandomPrime = rSAKeyPairGenerator.chooseRandomPrime(i2, publicExponent, shiftLeft);
                while (true) {
                    chooseRandomPrime2 = rSAKeyPairGenerator.chooseRandomPrime(i3, publicExponent, shiftLeft);
                    BigInteger abs = chooseRandomPrime2.subtract(chooseRandomPrime).abs();
                    if (abs.bitLength() >= i5 && abs.compareTo(shiftLeft2) > 0) {
                        multiply = chooseRandomPrime.multiply(chooseRandomPrime2);
                        if (multiply.bitLength() == strength) {
                            break;
                        }
                        chooseRandomPrime = chooseRandomPrime.max(chooseRandomPrime2);
                    } else {
                        rSAKeyPairGenerator = this;
                        strength = strength;
                    }
                }
            } while (WNafUtil.getNafWeight(multiply) < i7);
            if (chooseRandomPrime.compareTo(chooseRandomPrime2) < 0) {
                bigInteger = chooseRandomPrime;
                chooseRandomPrime = chooseRandomPrime2;
            } else {
                bigInteger = chooseRandomPrime2;
            }
            BigInteger bigInteger3 = ONE;
            BigInteger subtract = chooseRandomPrime.subtract(bigInteger3);
            BigInteger subtract2 = bigInteger.subtract(bigInteger3);
            int i8 = strength;
            BigInteger modInverse = publicExponent.modInverse(subtract.divide(subtract.gcd(subtract2)).multiply(subtract2));
            if (modInverse.compareTo(pow) <= 0) {
                rSAKeyPairGenerator = this;
                strength = i8;
            } else {
                asymmetricCipherKeyPair = new AsymmetricCipherKeyPair((AsymmetricKeyParameter) new RSAKeyParameters(false, multiply, publicExponent), (AsymmetricKeyParameter) new RSAPrivateCrtKeyParameters(multiply, publicExponent, modInverse, chooseRandomPrime, bigInteger, modInverse.remainder(subtract), modInverse.remainder(subtract2), BigIntegers.modOddInverse(chooseRandomPrime, bigInteger)));
                z2 = true;
                strength = i8;
                rSAKeyPairGenerator = this;
            }
        }
        return asymmetricCipherKeyPair;
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (RSAKeyGenerationParameters) keyGenerationParameters;
    }

    public boolean isProbablePrime(BigInteger bigInteger) {
        return !Primes.hasAnySmallFactors(bigInteger) && Primes.isMRProbablePrime(bigInteger, this.param.getRandom(), getNumberOfIterations(bigInteger.bitLength(), this.param.getCertainty()));
    }
}
