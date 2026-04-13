package org.bouncycastle.crypto.generators;

import android.sun.security.util.DerValue;
import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.digests.SHA1Digest;
import org.bouncycastle.crypto.params.DSAParameterGenerationParameters;
import org.bouncycastle.crypto.params.DSAParameters;
import org.bouncycastle.crypto.params.DSAValidationParameters;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.encoders.Hex;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DSAParametersGenerator {

    /* renamed from: L */
    private int f1239L;

    /* renamed from: N */
    private int f1240N;
    private int certainty;
    private Digest digest;
    private int iterations;
    private SecureRandom random;
    private int usageIndex;
    private boolean use186_3;
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    public DSAParametersGenerator() {
        this(DigestFactory.createSHA1());
    }

    private static BigInteger calculateGenerator_FIPS186_2(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        BigInteger modPow;
        BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        BigInteger subtract = bigInteger.subtract(TWO);
        do {
            modPow = BigIntegers.createRandomInRange(TWO, subtract, secureRandom).modPow(divide, bigInteger);
        } while (modPow.bitLength() <= 1);
        return modPow;
    }

    private static BigInteger calculateGenerator_FIPS186_3_Unverifiable(BigInteger bigInteger, BigInteger bigInteger2, SecureRandom secureRandom) {
        return calculateGenerator_FIPS186_2(bigInteger, bigInteger2, secureRandom);
    }

    private static BigInteger calculateGenerator_FIPS186_3_Verifiable(Digest digest, BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i2) {
        BigInteger divide = bigInteger.subtract(ONE).divide(bigInteger2);
        byte[] decodeStrict = Hex.decodeStrict("6767656E");
        int m5a = AbstractC0000a.m5a(bArr.length, decodeStrict.length, 1, 2);
        byte[] bArr2 = new byte[m5a];
        System.arraycopy(bArr, 0, bArr2, 0, bArr.length);
        System.arraycopy(decodeStrict, 0, bArr2, bArr.length, decodeStrict.length);
        bArr2[m5a - 3] = (byte) i2;
        byte[] bArr3 = new byte[digest.getDigestSize()];
        for (int i3 = 1; i3 < 65536; i3++) {
            inc(bArr2);
            hash(digest, bArr2, bArr3, 0);
            BigInteger modPow = new BigInteger(1, bArr3).modPow(divide, bigInteger);
            if (modPow.compareTo(TWO) >= 0) {
                return modPow;
            }
        }
        return null;
    }

    private DSAParameters generateParameters_FIPS186_2() {
        int i2 = 20;
        byte[] bArr = new byte[20];
        byte[] bArr2 = new byte[20];
        byte[] bArr3 = new byte[20];
        byte[] bArr4 = new byte[20];
        int i3 = this.f1239L;
        int i4 = (i3 - 1) / CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
        int i5 = i3 / 8;
        byte[] bArr5 = new byte[i5];
        if (!(this.digest instanceof SHA1Digest)) {
            throw new IllegalStateException("can only use SHA-1 for generating FIPS 186-2 parameters");
        }
        while (true) {
            this.random.nextBytes(bArr);
            hash(this.digest, bArr, bArr2, 0);
            System.arraycopy(bArr, 0, bArr3, 0, i2);
            inc(bArr3);
            hash(this.digest, bArr3, bArr3, 0);
            for (int i6 = 0; i6 != i2; i6++) {
                bArr4[i6] = (byte) (bArr2[i6] ^ bArr3[i6]);
            }
            bArr4[0] = (byte) (bArr4[0] | DerValue.TAG_CONTEXT);
            bArr4[19] = (byte) (bArr4[19] | 1);
            BigInteger bigInteger = new BigInteger(1, bArr4);
            if (isProbablePrime(bigInteger)) {
                byte[] clone = Arrays.clone(bArr);
                inc(clone);
                int i7 = 0;
                while (i7 < 4096) {
                    for (int i8 = 1; i8 <= i4; i8++) {
                        inc(clone);
                        hash(this.digest, clone, bArr5, i5 - (i8 * 20));
                    }
                    int i9 = i5 - (i4 * 20);
                    inc(clone);
                    hash(this.digest, clone, bArr2, 0);
                    System.arraycopy(bArr2, 20 - i9, bArr5, 0, i9);
                    bArr5[0] = (byte) (bArr5[0] | DerValue.TAG_CONTEXT);
                    BigInteger bigInteger2 = new BigInteger(1, bArr5);
                    BigInteger subtract = bigInteger2.subtract(bigInteger2.mod(bigInteger.shiftLeft(1)).subtract(ONE));
                    if (subtract.bitLength() == this.f1239L && isProbablePrime(subtract)) {
                        return new DSAParameters(subtract, bigInteger, calculateGenerator_FIPS186_2(subtract, bigInteger, this.random), new DSAValidationParameters(bArr, i7));
                    }
                    i7++;
                    i2 = 20;
                }
            }
        }
    }

    private DSAParameters generateParameters_FIPS186_3() {
        BigInteger bit;
        int i2;
        BigInteger subtract;
        BigInteger calculateGenerator_FIPS186_3_Verifiable;
        Digest digest = this.digest;
        int digestSize = digest.getDigestSize() * 8;
        byte[] bArr = new byte[this.f1240N / 8];
        int i3 = this.f1239L;
        int i4 = (i3 - 1) / digestSize;
        int i5 = (i3 - 1) % digestSize;
        int i6 = i3 / 8;
        byte[] bArr2 = new byte[i6];
        int digestSize2 = digest.getDigestSize();
        byte[] bArr3 = new byte[digestSize2];
        loop0: while (true) {
            this.random.nextBytes(bArr);
            int i7 = 0;
            hash(digest, bArr, bArr3, 0);
            bit = new BigInteger(1, bArr3).mod(ONE.shiftLeft(this.f1240N - 1)).setBit(0).setBit(this.f1240N - 1);
            if (isProbablePrime(bit)) {
                byte[] clone = Arrays.clone(bArr);
                int i8 = this.f1239L * 4;
                i2 = 0;
                while (i2 < i8) {
                    for (int i9 = 1; i9 <= i4; i9++) {
                        inc(clone);
                        hash(digest, clone, bArr2, i6 - (i9 * digestSize2));
                    }
                    int i10 = i6 - (i4 * digestSize2);
                    inc(clone);
                    hash(digest, clone, bArr3, i7);
                    System.arraycopy(bArr3, digestSize2 - i10, bArr2, i7, i10);
                    bArr2[i7] = (byte) (bArr2[i7] | DerValue.TAG_CONTEXT);
                    BigInteger bigInteger = new BigInteger(1, bArr2);
                    subtract = bigInteger.subtract(bigInteger.mod(bit.shiftLeft(1)).subtract(ONE));
                    if (subtract.bitLength() == this.f1239L && isProbablePrime(subtract)) {
                        break loop0;
                    }
                    i2++;
                    i7 = 0;
                }
            }
        }
        int i11 = this.usageIndex;
        return (i11 < 0 || (calculateGenerator_FIPS186_3_Verifiable = calculateGenerator_FIPS186_3_Verifiable(digest, subtract, bit, bArr, i11)) == null) ? new DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Unverifiable(subtract, bit, this.random), new DSAValidationParameters(bArr, i2)) : new DSAParameters(subtract, bit, calculateGenerator_FIPS186_3_Verifiable, new DSAValidationParameters(bArr, i2, this.usageIndex));
    }

    private static int getDefaultN(int i2) {
        if (i2 > 1024) {
            return 256;
        }
        return CipherSuite.TLS_DH_RSA_WITH_AES_128_GCM_SHA256;
    }

    private static int getMinimumIterations(int i2) {
        if (i2 <= 1024) {
            return 40;
        }
        return (((i2 - 1) / 1024) * 8) + 48;
    }

    private static void hash(Digest digest, byte[] bArr, byte[] bArr2, int i2) {
        digest.update(bArr, 0, bArr.length);
        digest.doFinal(bArr2, i2);
    }

    private static void inc(byte[] bArr) {
        for (int length = bArr.length - 1; length >= 0; length--) {
            byte b = (byte) ((bArr[length] + 1) & 255);
            bArr[length] = b;
            if (b != 0) {
                return;
            }
        }
    }

    private boolean isProbablePrime(BigInteger bigInteger) {
        return bigInteger.isProbablePrime(this.certainty);
    }

    public DSAParameters generateParameters() {
        return this.use186_3 ? generateParameters_FIPS186_3() : generateParameters_FIPS186_2();
    }

    public void init(int i2, int i3, SecureRandom secureRandom) {
        this.f1239L = i2;
        this.f1240N = getDefaultN(i2);
        this.certainty = i3;
        this.iterations = Math.max(getMinimumIterations(this.f1239L), (i3 + 1) / 2);
        this.random = secureRandom;
        this.use186_3 = false;
        this.usageIndex = -1;
    }

    public DSAParametersGenerator(Digest digest) {
        this.digest = digest;
    }

    public void init(DSAParameterGenerationParameters dSAParameterGenerationParameters) {
        int l2 = dSAParameterGenerationParameters.getL();
        int n2 = dSAParameterGenerationParameters.getN();
        if (l2 < 1024 || l2 > 3072 || l2 % 1024 != 0) {
            throw new IllegalArgumentException("L values must be between 1024 and 3072 and a multiple of 1024");
        }
        if (l2 == 1024 && n2 != 160) {
            throw new IllegalArgumentException("N must be 160 for L = 1024");
        }
        if (l2 == 2048 && n2 != 224 && n2 != 256) {
            throw new IllegalArgumentException("N must be 224 or 256 for L = 2048");
        }
        if (l2 == 3072 && n2 != 256) {
            throw new IllegalArgumentException("N must be 256 for L = 3072");
        }
        if (this.digest.getDigestSize() * 8 < n2) {
            throw new IllegalStateException("Digest output size too small for value of N");
        }
        this.f1239L = l2;
        this.f1240N = n2;
        this.certainty = dSAParameterGenerationParameters.getCertainty();
        this.iterations = Math.max(getMinimumIterations(l2), (this.certainty + 1) / 2);
        this.random = dSAParameterGenerationParameters.getRandom();
        this.use186_3 = true;
        this.usageIndex = dSAParameterGenerationParameters.getUsageIndex();
    }
}
