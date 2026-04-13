package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.crypto.params.RSAPrivateCrtKeyParameters;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class RSABlindedEngine implements AsymmetricBlockCipher {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private RSACoreEngine core = new RSACoreEngine();
    private RSAKeyParameters key;
    private SecureRandom random;

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.core.getInputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.core.getOutputBlockSize();
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.core.init(z2, cipherParameters);
        if (!(cipherParameters instanceof ParametersWithRandom)) {
            RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
            this.key = rSAKeyParameters;
            if (rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) {
                secureRandom = CryptoServicesRegistrar.getSecureRandom();
                this.random = secureRandom;
                return;
            }
            this.random = null;
        }
        ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
        RSAKeyParameters rSAKeyParameters2 = (RSAKeyParameters) parametersWithRandom.getParameters();
        this.key = rSAKeyParameters2;
        if (rSAKeyParameters2 instanceof RSAPrivateCrtKeyParameters) {
            secureRandom = parametersWithRandom.getRandom();
            this.random = secureRandom;
            return;
        }
        this.random = null;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        BigInteger processBlock;
        RSAPrivateCrtKeyParameters rSAPrivateCrtKeyParameters;
        BigInteger publicExponent;
        if (this.key == null) {
            throw new IllegalStateException("RSA engine not initialised");
        }
        BigInteger convertInput = this.core.convertInput(bArr, i2, i3);
        RSAKeyParameters rSAKeyParameters = this.key;
        if (!(rSAKeyParameters instanceof RSAPrivateCrtKeyParameters) || (publicExponent = (rSAPrivateCrtKeyParameters = (RSAPrivateCrtKeyParameters) rSAKeyParameters).getPublicExponent()) == null) {
            processBlock = this.core.processBlock(convertInput);
        } else {
            BigInteger modulus = rSAPrivateCrtKeyParameters.getModulus();
            BigInteger bigInteger = ONE;
            BigInteger createRandomInRange = BigIntegers.createRandomInRange(bigInteger, modulus.subtract(bigInteger), this.random);
            processBlock = this.core.processBlock(createRandomInRange.modPow(publicExponent, modulus).multiply(convertInput).mod(modulus)).multiply(BigIntegers.modOddInverse(modulus, createRandomInRange)).mod(modulus);
            if (!convertInput.equals(processBlock.modPow(publicExponent, modulus))) {
                throw new IllegalStateException("RSA engine faulty decryption/signing detected");
            }
        }
        return this.core.convertOutput(processBlock);
    }
}
