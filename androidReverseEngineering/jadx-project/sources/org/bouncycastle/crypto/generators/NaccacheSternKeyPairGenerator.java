package org.bouncycastle.crypto.generators;

import java.io.PrintStream;
import java.math.BigInteger;
import java.security.SecureRandom;
import java.util.Vector;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.crypto.AsymmetricCipherKeyPair;
import org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator;
import org.bouncycastle.crypto.KeyGenerationParameters;
import org.bouncycastle.crypto.params.AsymmetricKeyParameter;
import org.bouncycastle.crypto.params.NaccacheSternKeyGenerationParameters;
import org.bouncycastle.crypto.params.NaccacheSternKeyParameters;
import org.bouncycastle.crypto.params.NaccacheSternPrivateKeyParameters;
import org.bouncycastle.math.Primes;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.BigIntegers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class NaccacheSternKeyPairGenerator implements AsymmetricCipherKeyPairGenerator {
    private NaccacheSternKeyGenerationParameters param;
    private static int[] smallPrimes = {3, 5, 7, 11, 13, 17, 19, 23, 29, 31, 37, 41, 43, 47, 53, 59, 61, 67, 71, 73, 79, 83, 89, 97, 101, CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256, CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_AES_256_CBC_SHA256, 113, CertificateBody.profileType, 131, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA, CipherSuite.TLS_PSK_WITH_3DES_EDE_CBC_SHA, CipherSuite.TLS_RSA_PSK_WITH_AES_256_CBC_SHA, CipherSuite.TLS_DH_DSS_WITH_SEED_CBC_SHA, CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DH_anon_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_RSA_PSK_WITH_AES_256_GCM_SHA384, CipherSuite.TLS_DHE_PSK_WITH_AES_256_CBC_SHA384, CipherSuite.TLS_DHE_PSK_WITH_NULL_SHA384, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_128_CBC_SHA256, CipherSuite.TLS_DH_DSS_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_DH_anon_WITH_CAMELLIA_256_CBC_SHA256, CipherSuite.TLS_SM4_CCM_SM3, Primes.SMALL_FACTOR_LIMIT, 223, 227, 229, 233, 239, 241, 251, 257, 263, 269, 271, 277, 281, 283, 293, 307, 311, 313, 317, 331, 337, 347, 349, 353, 359, 367, 373, 379, 383, 389, 397, 401, 409, 419, 421, 431, 433, 439, 443, 449, 457, 461, 463, 467, 479, 487, 491, 499, 503, 509, 521, 523, 541, 547, 557};
    private static final BigInteger ONE = BigInteger.valueOf(1);

    private static Vector findFirstPrimes(int i2) {
        Vector vector = new Vector(i2);
        for (int i3 = 0; i3 != i2; i3++) {
            vector.addElement(BigInteger.valueOf(smallPrimes[i3]));
        }
        return vector;
    }

    private static BigInteger generatePrime(int i2, int i3, SecureRandom secureRandom) {
        BigInteger createRandomPrime;
        do {
            createRandomPrime = BigIntegers.createRandomPrime(i2, i3, secureRandom);
        } while (createRandomPrime.bitLength() != i2);
        return createRandomPrime;
    }

    private static int getInt(SecureRandom secureRandom, int i2) {
        int nextInt;
        int i3;
        if (((-i2) & i2) == i2) {
            return (int) ((i2 * (secureRandom.nextInt() & Integer.MAX_VALUE)) >> 31);
        }
        do {
            nextInt = secureRandom.nextInt() & Integer.MAX_VALUE;
            i3 = nextInt % i2;
        } while ((i2 - 1) + (nextInt - i3) < 0);
        return i3;
    }

    private static Vector permuteList(Vector vector, SecureRandom secureRandom) {
        Vector vector2 = new Vector();
        Vector vector3 = new Vector();
        for (int i2 = 0; i2 < vector.size(); i2++) {
            vector3.addElement(vector.elementAt(i2));
        }
        vector2.addElement(vector3.elementAt(0));
        while (true) {
            vector3.removeElementAt(0);
            if (vector3.size() == 0) {
                return vector2;
            }
            vector2.insertElementAt(vector3.elementAt(0), getInt(secureRandom, vector2.size() + 1));
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public AsymmetricCipherKeyPair generateKeyPair() {
        BigInteger generatePrime;
        BigInteger add;
        BigInteger generatePrime2;
        BigInteger bigInteger;
        BigInteger bigInteger2;
        BigInteger add2;
        BigInteger bigInteger3;
        BigInteger bigInteger4;
        BigInteger bigInteger5;
        BigInteger bigInteger6;
        BigInteger bigInteger7;
        boolean z2;
        BigInteger bigInteger8;
        BigInteger bigInteger9;
        int i2;
        PrintStream printStream;
        StringBuilder sb;
        long j2;
        BigInteger createRandomPrime;
        int i3;
        int strength = this.param.getStrength();
        SecureRandom random = this.param.getRandom();
        int certainty = this.param.getCertainty();
        boolean isDebug = this.param.isDebug();
        if (isDebug) {
            System.out.println("Fetching first " + this.param.getCntSmallPrimes() + " primes.");
        }
        Vector permuteList = permuteList(findFirstPrimes(this.param.getCntSmallPrimes()), random);
        BigInteger bigInteger10 = ONE;
        BigInteger bigInteger11 = bigInteger10;
        for (int i4 = 0; i4 < permuteList.size() / 2; i4++) {
            bigInteger11 = bigInteger11.multiply((BigInteger) permuteList.elementAt(i4));
        }
        for (int size = permuteList.size() / 2; size < permuteList.size(); size++) {
            bigInteger10 = bigInteger10.multiply((BigInteger) permuteList.elementAt(size));
        }
        BigInteger multiply = bigInteger11.multiply(bigInteger10);
        int bitLength = (((strength - multiply.bitLength()) - 48) / 2) + 1;
        BigInteger generatePrime3 = generatePrime(bitLength, certainty, random);
        BigInteger generatePrime4 = generatePrime(bitLength, certainty, random);
        if (isDebug) {
            System.out.println("generating p and q");
        }
        BigInteger shiftLeft = generatePrime3.multiply(bigInteger11).shiftLeft(1);
        BigInteger shiftLeft2 = generatePrime4.multiply(bigInteger10).shiftLeft(1);
        long j3 = 0;
        while (true) {
            j3++;
            generatePrime = generatePrime(24, certainty, random);
            add = generatePrime.multiply(shiftLeft).add(ONE);
            if (add.isProbablePrime(certainty)) {
                while (true) {
                    do {
                        generatePrime2 = generatePrime(24, certainty, random);
                    } while (generatePrime.equals(generatePrime2));
                    BigInteger multiply2 = generatePrime2.multiply(shiftLeft2);
                    bigInteger = shiftLeft2;
                    bigInteger2 = ONE;
                    add2 = multiply2.add(bigInteger2);
                    if (add2.isProbablePrime(certainty)) {
                        break;
                    }
                    shiftLeft2 = bigInteger;
                    certainty = certainty;
                }
                bigInteger3 = shiftLeft;
                if (!multiply.gcd(generatePrime.multiply(generatePrime2)).equals(bigInteger2)) {
                    continue;
                } else {
                    if (add.multiply(add2).bitLength() >= strength) {
                        break;
                    }
                    if (isDebug) {
                        PrintStream printStream2 = System.out;
                        StringBuilder m21q = AbstractC0000a.m21q("key size too small. Should be ", strength, " but is actually ");
                        m21q.append(add.multiply(add2).bitLength());
                        printStream2.println(m21q.toString());
                    }
                }
            } else {
                bigInteger = shiftLeft2;
                bigInteger3 = shiftLeft;
            }
            shiftLeft2 = bigInteger;
            shiftLeft = bigInteger3;
        }
        BigInteger bigInteger12 = generatePrime4;
        if (isDebug) {
            bigInteger4 = generatePrime3;
            System.out.println("needed " + j3 + " tries to generate p and q.");
        } else {
            bigInteger4 = generatePrime3;
        }
        BigInteger multiply3 = add.multiply(add2);
        BigInteger multiply4 = add.subtract(bigInteger2).multiply(add2.subtract(bigInteger2));
        if (isDebug) {
            System.out.println("generating g");
        }
        long j4 = 0;
        while (true) {
            Vector vector = new Vector();
            bigInteger5 = add;
            bigInteger6 = add2;
            int i5 = 0;
            while (i5 != permuteList.size()) {
                BigInteger divide = multiply4.divide((BigInteger) permuteList.elementAt(i5));
                while (true) {
                    j2 = j4 + 1;
                    createRandomPrime = BigIntegers.createRandomPrime(strength, certainty, random);
                    i3 = strength;
                    if (createRandomPrime.modPow(divide, multiply3).equals(ONE)) {
                        j4 = j2;
                        strength = i3;
                    }
                }
                vector.addElement(createRandomPrime);
                i5++;
                j4 = j2;
                strength = i3;
            }
            int i6 = strength;
            bigInteger7 = ONE;
            int i7 = 0;
            while (i7 < permuteList.size()) {
                bigInteger7 = bigInteger7.multiply(((BigInteger) vector.elementAt(i7)).modPow(multiply.divide((BigInteger) permuteList.elementAt(i7)), multiply3)).mod(multiply3);
                i7++;
                random = random;
            }
            SecureRandom secureRandom = random;
            int i8 = 0;
            while (true) {
                if (i8 >= permuteList.size()) {
                    z2 = false;
                    break;
                }
                if (bigInteger7.modPow(multiply4.divide((BigInteger) permuteList.elementAt(i8)), multiply3).equals(ONE)) {
                    if (isDebug) {
                        System.out.println("g has order phi(n)/" + permuteList.elementAt(i8) + "\n g: " + bigInteger7);
                    }
                    z2 = true;
                } else {
                    i8++;
                }
            }
            if (!z2) {
                BigInteger modPow = bigInteger7.modPow(multiply4.divide(BigInteger.valueOf(4L)), multiply3);
                BigInteger bigInteger13 = ONE;
                if (!modPow.equals(bigInteger13)) {
                    if (!bigInteger7.modPow(multiply4.divide(generatePrime), multiply3).equals(bigInteger13)) {
                        if (!bigInteger7.modPow(multiply4.divide(generatePrime2), multiply3).equals(bigInteger13)) {
                            bigInteger9 = bigInteger4;
                            if (!bigInteger7.modPow(multiply4.divide(bigInteger9), multiply3).equals(bigInteger13)) {
                                bigInteger8 = bigInteger12;
                                if (!bigInteger7.modPow(multiply4.divide(bigInteger8), multiply3).equals(bigInteger13)) {
                                    break;
                                }
                                if (isDebug) {
                                    i2 = certainty;
                                    System.out.println("g has order phi(n)/b\n g: " + bigInteger7);
                                    bigInteger4 = bigInteger9;
                                    certainty = i2;
                                    add2 = bigInteger6;
                                    strength = i6;
                                    random = secureRandom;
                                    bigInteger12 = bigInteger8;
                                    add = bigInteger5;
                                }
                            } else {
                                if (isDebug) {
                                    System.out.println("g has order phi(n)/a\n g: " + bigInteger7);
                                }
                                bigInteger8 = bigInteger12;
                            }
                        } else if (isDebug) {
                            printStream = System.out;
                            sb = new StringBuilder("g has order phi(n)/q'\n g: ");
                            sb.append(bigInteger7);
                            printStream.println(sb.toString());
                        }
                    } else if (isDebug) {
                        printStream = System.out;
                        sb = new StringBuilder("g has order phi(n)/p'\n g: ");
                        sb.append(bigInteger7);
                        printStream.println(sb.toString());
                    }
                } else if (isDebug) {
                    printStream = System.out;
                    sb = new StringBuilder("g has order phi(n)/4\n g:");
                    sb.append(bigInteger7);
                    printStream.println(sb.toString());
                }
                i2 = certainty;
                bigInteger4 = bigInteger9;
                certainty = i2;
                add2 = bigInteger6;
                strength = i6;
                random = secureRandom;
                bigInteger12 = bigInteger8;
                add = bigInteger5;
            }
            bigInteger8 = bigInteger12;
            bigInteger9 = bigInteger4;
            i2 = certainty;
            bigInteger4 = bigInteger9;
            certainty = i2;
            add2 = bigInteger6;
            strength = i6;
            random = secureRandom;
            bigInteger12 = bigInteger8;
            add = bigInteger5;
        }
        if (isDebug) {
            System.out.println("needed " + j4 + " tries to generate g");
            System.out.println();
            System.out.println("found new NaccacheStern cipher variables:");
            System.out.println("smallPrimes: " + permuteList);
            System.out.println("sigma:...... " + multiply + " (" + multiply.bitLength() + " bits)");
            PrintStream printStream3 = System.out;
            StringBuilder sb2 = new StringBuilder("a:.......... ");
            sb2.append(bigInteger9);
            printStream3.println(sb2.toString());
            System.out.println("b:.......... " + bigInteger8);
            System.out.println("p':......... " + generatePrime);
            System.out.println("q':......... " + generatePrime2);
            System.out.println("p:.......... " + bigInteger5);
            System.out.println("q:.......... " + bigInteger6);
            System.out.println("n:.......... " + multiply3);
            System.out.println("phi(n):..... " + multiply4);
            System.out.println("g:.......... " + bigInteger7);
            System.out.println();
        }
        return new AsymmetricCipherKeyPair((AsymmetricKeyParameter) new NaccacheSternKeyParameters(false, bigInteger7, multiply3, multiply.bitLength()), (AsymmetricKeyParameter) new NaccacheSternPrivateKeyParameters(bigInteger7, multiply3, multiply.bitLength(), permuteList, multiply4));
    }

    @Override // org.bouncycastle.crypto.AsymmetricCipherKeyPairGenerator
    public void init(KeyGenerationParameters keyGenerationParameters) {
        this.param = (NaccacheSternKeyGenerationParameters) keyGenerationParameters;
    }
}
