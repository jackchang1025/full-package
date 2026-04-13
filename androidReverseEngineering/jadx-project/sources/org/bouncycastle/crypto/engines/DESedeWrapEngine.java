package org.bouncycastle.crypto.engines;

import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.Wrapper;
import org.bouncycastle.crypto.modes.CBCBlockCipher;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.crypto.params.ParametersWithIV;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.util.Arrays;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DESedeWrapEngine implements Wrapper {
    private static final byte[] IV2 = {74, -35, -94, 44, 121, -24, 33, 5};
    private CBCBlockCipher engine;
    private boolean forWrapping;
    private byte[] iv;
    private KeyParameter param;
    private ParametersWithIV paramPlusIV;
    Digest sha1 = DigestFactory.createSHA1();
    byte[] digest = new byte[20];

    private byte[] calculateCMSKeyChecksum(byte[] bArr) {
        byte[] bArr2 = new byte[8];
        this.sha1.update(bArr, 0, bArr.length);
        this.sha1.doFinal(this.digest, 0);
        System.arraycopy(this.digest, 0, bArr2, 0, 8);
        return bArr2;
    }

    private boolean checkCMSKeyChecksum(byte[] bArr, byte[] bArr2) {
        return Arrays.constantTimeAreEqual(calculateCMSKeyChecksum(bArr), bArr2);
    }

    private static byte[] reverse(byte[] bArr) {
        byte[] bArr2 = new byte[bArr.length];
        int i2 = 0;
        while (i2 < bArr.length) {
            int i3 = i2 + 1;
            bArr2[i2] = bArr[bArr.length - i3];
            i2 = i3;
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public String getAlgorithmName() {
        return "DESede";
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public void init(boolean z2, CipherParameters cipherParameters) {
        SecureRandom secureRandom;
        this.forWrapping = z2;
        this.engine = new CBCBlockCipher(new DESedeEngine());
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            CipherParameters parameters = parametersWithRandom.getParameters();
            SecureRandom random = parametersWithRandom.getRandom();
            cipherParameters = parameters;
            secureRandom = random;
        } else {
            secureRandom = CryptoServicesRegistrar.getSecureRandom();
        }
        if (cipherParameters instanceof KeyParameter) {
            this.param = (KeyParameter) cipherParameters;
            if (this.forWrapping) {
                byte[] bArr = new byte[8];
                this.iv = bArr;
                secureRandom.nextBytes(bArr);
                this.paramPlusIV = new ParametersWithIV(this.param, this.iv);
                return;
            }
            return;
        }
        if (cipherParameters instanceof ParametersWithIV) {
            ParametersWithIV parametersWithIV = (ParametersWithIV) cipherParameters;
            this.paramPlusIV = parametersWithIV;
            this.iv = parametersWithIV.getIV();
            this.param = (KeyParameter) this.paramPlusIV.getParameters();
            if (!this.forWrapping) {
                throw new IllegalArgumentException("You should not supply an IV for unwrapping");
            }
            byte[] bArr2 = this.iv;
            if (bArr2 == null || bArr2.length != 8) {
                throw new IllegalArgumentException("IV is not 8 octets");
            }
        }
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] unwrap(byte[] bArr, int i2, int i3) {
        if (this.forWrapping) {
            throw new IllegalStateException("Not set for unwrapping");
        }
        if (bArr == null) {
            throw new InvalidCipherTextException("Null pointer as ciphertext");
        }
        int blockSize = this.engine.getBlockSize();
        if (i3 % blockSize != 0) {
            throw new InvalidCipherTextException(AbstractC0000a.m11g("Ciphertext not multiple of ", blockSize));
        }
        this.engine.init(false, new ParametersWithIV(this.param, IV2));
        byte[] bArr2 = new byte[i3];
        for (int i4 = 0; i4 != i3; i4 += blockSize) {
            this.engine.processBlock(bArr, i2 + i4, bArr2, i4);
        }
        byte[] reverse = reverse(bArr2);
        byte[] bArr3 = new byte[8];
        this.iv = bArr3;
        int length = reverse.length - 8;
        byte[] bArr4 = new byte[length];
        System.arraycopy(reverse, 0, bArr3, 0, 8);
        System.arraycopy(reverse, 8, bArr4, 0, reverse.length - 8);
        ParametersWithIV parametersWithIV = new ParametersWithIV(this.param, this.iv);
        this.paramPlusIV = parametersWithIV;
        this.engine.init(false, parametersWithIV);
        byte[] bArr5 = new byte[length];
        for (int i5 = 0; i5 != length; i5 += blockSize) {
            this.engine.processBlock(bArr4, i5, bArr5, i5);
        }
        int i6 = length - 8;
        byte[] bArr6 = new byte[i6];
        byte[] bArr7 = new byte[8];
        System.arraycopy(bArr5, 0, bArr6, 0, i6);
        System.arraycopy(bArr5, i6, bArr7, 0, 8);
        if (checkCMSKeyChecksum(bArr6, bArr7)) {
            return bArr6;
        }
        throw new InvalidCipherTextException("Checksum inside ciphertext is corrupted");
    }

    @Override // org.bouncycastle.crypto.Wrapper
    public byte[] wrap(byte[] bArr, int i2, int i3) {
        if (!this.forWrapping) {
            throw new IllegalStateException("Not initialized for wrapping");
        }
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        byte[] calculateCMSKeyChecksum = calculateCMSKeyChecksum(bArr2);
        int length = calculateCMSKeyChecksum.length + i3;
        byte[] bArr3 = new byte[length];
        System.arraycopy(bArr2, 0, bArr3, 0, i3);
        System.arraycopy(calculateCMSKeyChecksum, 0, bArr3, i3, calculateCMSKeyChecksum.length);
        int blockSize = this.engine.getBlockSize();
        if (length % blockSize != 0) {
            throw new IllegalStateException("Not multiple of block length");
        }
        this.engine.init(true, this.paramPlusIV);
        byte[] bArr4 = new byte[length];
        for (int i4 = 0; i4 != length; i4 += blockSize) {
            this.engine.processBlock(bArr3, i4, bArr4, i4);
        }
        byte[] bArr5 = this.iv;
        byte[] bArr6 = new byte[bArr5.length + length];
        System.arraycopy(bArr5, 0, bArr6, 0, bArr5.length);
        System.arraycopy(bArr4, 0, bArr6, this.iv.length, length);
        byte[] reverse = reverse(bArr6);
        this.engine.init(true, new ParametersWithIV(this.param, IV2));
        for (int i5 = 0; i5 != reverse.length; i5 += blockSize) {
            this.engine.processBlock(reverse, i5, reverse, i5);
        }
        return reverse;
    }
}
