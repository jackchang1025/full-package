package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.CramerShoupKeyParameters;
import org.bouncycastle.crypto.params.CramerShoupPrivateKeyParameters;
import org.bouncycastle.crypto.params.CramerShoupPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Strings;

/* loaded from: classes.dex */
public class CramerShoupCoreEngine {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private boolean forEncryption;
    private CramerShoupKeyParameters key;
    private byte[] label = null;
    private SecureRandom random;

    public static class CramerShoupCiphertextException extends Exception {
        private static final long serialVersionUID = -6360977166495345076L;

        public CramerShoupCiphertextException(String str) {
            super(str);
        }
    }

    private BigInteger generateRandomElement(BigInteger bigInteger, SecureRandom secureRandom) {
        BigInteger bigInteger2 = ONE;
        return BigIntegers.createRandomInRange(bigInteger2, bigInteger.subtract(bigInteger2), secureRandom);
    }

    private boolean isValidMessage(BigInteger bigInteger, BigInteger bigInteger2) {
        return bigInteger.compareTo(bigInteger2) < 0;
    }

    public BigInteger convertInput(byte[] bArr, int i2, int i3) {
        if (i3 > getInputBlockSize() + 1) {
            throw new DataLengthException("input too large for Cramer Shoup cipher.");
        }
        if (i3 == getInputBlockSize() + 1 && this.forEncryption) {
            throw new DataLengthException("input too large for Cramer Shoup cipher.");
        }
        if (i2 != 0 || i3 != bArr.length) {
            byte[] bArr2 = new byte[i3];
            System.arraycopy(bArr, i2, bArr2, 0, i3);
            bArr = bArr2;
        }
        BigInteger bigInteger = new BigInteger(1, bArr);
        if (bigInteger.compareTo(this.key.getParameters().getP()) < 0) {
            return bigInteger;
        }
        throw new DataLengthException("input too large for Cramer Shoup cipher.");
    }

    public byte[] convertOutput(BigInteger bigInteger) {
        byte[] byteArray = bigInteger.toByteArray();
        if (this.forEncryption) {
            if (byteArray[0] == 0) {
                int length = byteArray.length - 1;
                byte[] bArr = new byte[length];
                System.arraycopy(byteArray, 1, bArr, 0, length);
                return bArr;
            }
        } else {
            if (byteArray[0] == 0 && byteArray.length > getOutputBlockSize()) {
                int length2 = byteArray.length - 1;
                byte[] bArr2 = new byte[length2];
                System.arraycopy(byteArray, 1, bArr2, 0, length2);
                return bArr2;
            }
            if (byteArray.length < getOutputBlockSize()) {
                int outputBlockSize = getOutputBlockSize();
                byte[] bArr3 = new byte[outputBlockSize];
                System.arraycopy(byteArray, 0, bArr3, outputBlockSize - byteArray.length, byteArray.length);
                return bArr3;
            }
        }
        return byteArray;
    }

    public BigInteger decryptBlock(CramerShoupCiphertext cramerShoupCiphertext) {
        if (this.key.isPrivate() && !this.forEncryption) {
            CramerShoupKeyParameters cramerShoupKeyParameters = this.key;
            if (cramerShoupKeyParameters instanceof CramerShoupPrivateKeyParameters) {
                CramerShoupPrivateKeyParameters cramerShoupPrivateKeyParameters = (CramerShoupPrivateKeyParameters) cramerShoupKeyParameters;
                BigInteger p2 = cramerShoupPrivateKeyParameters.getParameters().getP();
                Digest h2 = cramerShoupPrivateKeyParameters.getParameters().getH();
                byte[] byteArray = cramerShoupCiphertext.getU1().toByteArray();
                h2.update(byteArray, 0, byteArray.length);
                byte[] byteArray2 = cramerShoupCiphertext.getU2().toByteArray();
                h2.update(byteArray2, 0, byteArray2.length);
                byte[] byteArray3 = cramerShoupCiphertext.getE().toByteArray();
                h2.update(byteArray3, 0, byteArray3.length);
                byte[] bArr = this.label;
                if (bArr != null) {
                    h2.update(bArr, 0, bArr.length);
                }
                byte[] bArr2 = new byte[h2.getDigestSize()];
                h2.doFinal(bArr2, 0);
                BigInteger bigInteger = new BigInteger(1, bArr2);
                if (cramerShoupCiphertext.f1208v.equals(cramerShoupCiphertext.u1.modPow(cramerShoupPrivateKeyParameters.getX1().add(cramerShoupPrivateKeyParameters.getY1().multiply(bigInteger)), p2).multiply(cramerShoupCiphertext.u2.modPow(cramerShoupPrivateKeyParameters.getX2().add(cramerShoupPrivateKeyParameters.getY2().multiply(bigInteger)), p2)).mod(p2))) {
                    return cramerShoupCiphertext.f1207e.multiply(cramerShoupCiphertext.u1.modPow(cramerShoupPrivateKeyParameters.getZ(), p2).modInverse(p2)).mod(p2);
                }
                throw new CramerShoupCiphertextException("Sorry, that ciphertext is not correct");
            }
        }
        return null;
    }

    public CramerShoupCiphertext encryptBlock(BigInteger bigInteger) {
        if (this.key.isPrivate() || !this.forEncryption) {
            return null;
        }
        CramerShoupKeyParameters cramerShoupKeyParameters = this.key;
        if (!(cramerShoupKeyParameters instanceof CramerShoupPublicKeyParameters)) {
            return null;
        }
        CramerShoupPublicKeyParameters cramerShoupPublicKeyParameters = (CramerShoupPublicKeyParameters) cramerShoupKeyParameters;
        BigInteger p2 = cramerShoupPublicKeyParameters.getParameters().getP();
        BigInteger g12 = cramerShoupPublicKeyParameters.getParameters().getG1();
        BigInteger g2 = cramerShoupPublicKeyParameters.getParameters().getG2();
        BigInteger h2 = cramerShoupPublicKeyParameters.getH();
        if (!isValidMessage(bigInteger, p2)) {
            return null;
        }
        BigInteger generateRandomElement = generateRandomElement(p2, this.random);
        BigInteger modPow = g12.modPow(generateRandomElement, p2);
        BigInteger modPow2 = g2.modPow(generateRandomElement, p2);
        BigInteger mod = h2.modPow(generateRandomElement, p2).multiply(bigInteger).mod(p2);
        Digest h3 = cramerShoupPublicKeyParameters.getParameters().getH();
        byte[] byteArray = modPow.toByteArray();
        h3.update(byteArray, 0, byteArray.length);
        byte[] byteArray2 = modPow2.toByteArray();
        h3.update(byteArray2, 0, byteArray2.length);
        byte[] byteArray3 = mod.toByteArray();
        h3.update(byteArray3, 0, byteArray3.length);
        byte[] bArr = this.label;
        if (bArr != null) {
            h3.update(bArr, 0, bArr.length);
        }
        byte[] bArr2 = new byte[h3.getDigestSize()];
        h3.doFinal(bArr2, 0);
        return new CramerShoupCiphertext(modPow, modPow2, mod, cramerShoupPublicKeyParameters.getC().modPow(generateRandomElement, p2).multiply(cramerShoupPublicKeyParameters.getD().modPow(generateRandomElement.multiply(new BigInteger(1, bArr2)), p2)).mod(p2));
    }

    public int getInputBlockSize() {
        int bitLength = (this.key.getParameters().getP().bitLength() + 7) / 8;
        return this.forEncryption ? bitLength - 1 : bitLength;
    }

    public int getOutputBlockSize() {
        int bitLength = (this.key.getParameters().getP().bitLength() + 7) / 8;
        return this.forEncryption ? bitLength : bitLength - 1;
    }

    public void init(boolean z2, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            this.key = (CramerShoupKeyParameters) parametersWithRandom.getParameters();
            secureRandom = parametersWithRandom.getRandom();
        } else {
            this.key = (CramerShoupKeyParameters) cipherParameters;
            secureRandom = null;
        }
        this.random = initSecureRandom(z2, secureRandom);
        this.forEncryption = z2;
    }

    public SecureRandom initSecureRandom(boolean z2, SecureRandom secureRandom) {
        if (z2) {
            return CryptoServicesRegistrar.getSecureRandom(secureRandom);
        }
        return null;
    }

    public void init(boolean z2, CipherParameters cipherParameters, String str) {
        init(z2, cipherParameters);
        this.label = Strings.toUTF8ByteArray(str);
    }
}
