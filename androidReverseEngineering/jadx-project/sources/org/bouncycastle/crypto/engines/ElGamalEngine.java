package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.params.ElGamalKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPrivateKeyParameters;
import org.bouncycastle.crypto.params.ElGamalPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ElGamalEngine implements AsymmetricBlockCipher {
    private int bitSize;
    private boolean forEncryption;
    private ElGamalKeyParameters key;
    private SecureRandom random;
    private static final BigInteger ZERO = BigInteger.valueOf(0);
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private static final BigInteger TWO = BigInteger.valueOf(2);

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        return this.forEncryption ? (this.bitSize - 1) / 8 : ((this.bitSize + 7) / 8) * 2;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        return this.forEncryption ? ((this.bitSize + 7) / 8) * 2 : (this.bitSize - 1) / 8;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.key = (ElGamalKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.key = (ElGamalKeyParameters) cipherParameters;
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        this.random = secureRandom;
        this.forEncryption = z2;
        this.bitSize = this.key.getParameters().getP().bitLength();
        if (z2) {
            if (!(this.key instanceof ElGamalPublicKeyParameters)) {
                throw new IllegalArgumentException("ElGamalPublicKeyParameters are required for encryption.");
            }
        } else if (!(this.key instanceof ElGamalPrivateKeyParameters)) {
            throw new IllegalArgumentException("ElGamalPrivateKeyParameters are required for decryption.");
        }
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        BigInteger createRandomBigInteger;
        if (this.key == null) {
            throw new IllegalStateException("ElGamal engine not initialised");
        }
        if (i3 > (this.forEncryption ? ((this.bitSize - 1) + 7) / 8 : getInputBlockSize())) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        BigInteger p2 = this.key.getParameters().getP();
        if (this.key instanceof ElGamalPrivateKeyParameters) {
            int i4 = i3 / 2;
            byte[] bArr2 = new byte[i4];
            byte[] bArr3 = new byte[i4];
            System.arraycopy(bArr, i2, bArr2, 0, i4);
            System.arraycopy(bArr, i2 + i4, bArr3, 0, i4);
            return BigIntegers.asUnsignedByteArray(new BigInteger(1, bArr2).modPow(p2.subtract(ONE).subtract(((ElGamalPrivateKeyParameters) this.key).getX()), p2).multiply(new BigInteger(1, bArr3)).mod(p2));
        }
        if (i2 != 0 || i3 != bArr.length) {
            byte[] bArr4 = new byte[i3];
            System.arraycopy(bArr, i2, bArr4, 0, i3);
            bArr = bArr4;
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.compareTo(p2) >= 0) {
            throw new DataLengthException("input too large for ElGamal cipher.\n");
        }
        ElGamalPublicKeyParameters elGamalPublicKeyParameters = (ElGamalPublicKeyParameters) this.key;
        int bitLength = p2.bitLength();
        while (true) {
            createRandomBigInteger = BigIntegers.createRandomBigInteger(bitLength, this.random);
            if (!createRandomBigInteger.equals(ZERO) && createRandomBigInteger.compareTo(p2.subtract(TWO)) <= 0) {
                break;
            }
        }
        BigInteger modPow = this.key.getParameters().getG().modPow(createRandomBigInteger, p2);
        BigInteger mod = bigInteger.multiply(elGamalPublicKeyParameters.getY().modPow(createRandomBigInteger, p2)).mod(p2);
        byte[] byteArray = modPow.toByteArray();
        byte[] byteArray2 = mod.toByteArray();
        int outputBlockSize = getOutputBlockSize();
        byte[] bArr5 = new byte[outputBlockSize];
        int i5 = outputBlockSize / 2;
        if (byteArray.length > i5) {
            System.arraycopy(byteArray, 1, bArr5, i5 - (byteArray.length - 1), byteArray.length - 1);
        } else {
            System.arraycopy(byteArray, 0, bArr5, i5 - byteArray.length, byteArray.length);
        }
        if (byteArray2.length > i5) {
            System.arraycopy(byteArray2, 1, bArr5, outputBlockSize - (byteArray2.length - 1), byteArray2.length - 1);
        } else {
            System.arraycopy(byteArray2, 0, bArr5, outputBlockSize - byteArray2.length, byteArray2.length);
        }
        return bArr5;
    }
}
