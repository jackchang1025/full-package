package org.bouncycastle.crypto.encodings;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.DataLengthException;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.util.DigestFactory;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Pack;

/* loaded from: classes.dex */
public class OAEPEncoding implements AsymmetricBlockCipher {
    private byte[] defHash;
    private AsymmetricBlockCipher engine;
    private boolean forEncryption;
    private Digest mgf1Hash;
    private SecureRandom random;

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher) {
        this(asymmetricBlockCipher, DigestFactory.createSHA1(), null);
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i2, int i3, int i4) {
        byte[] bArr2 = new byte[i4];
        int digestSize = this.mgf1Hash.getDigestSize();
        byte[] bArr3 = new byte[digestSize];
        byte[] bArr4 = new byte[4];
        this.mgf1Hash.reset();
        int i5 = 0;
        while (i5 < i4 / digestSize) {
            Pack.intToBigEndian(i5, bArr4, 0);
            this.mgf1Hash.update(bArr, i2, i3);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i5 * digestSize, digestSize);
            i5++;
        }
        int i6 = digestSize * i5;
        if (i6 < i4) {
            Pack.intToBigEndian(i5, bArr4, 0);
            this.mgf1Hash.update(bArr, i2, i3);
            this.mgf1Hash.update(bArr4, 0, 4);
            this.mgf1Hash.doFinal(bArr3, 0);
            System.arraycopy(bArr3, 0, bArr2, i6, i4 - i6);
        }
        return bArr2;
    }

    public byte[] decodeBlock(byte[] bArr, int i2, int i3) {
        byte[] bArr2;
        byte[] bArr3;
        byte[] processBlock = this.engine.processBlock(bArr, i2, i3);
        int outputBlockSize = this.engine.getOutputBlockSize();
        byte[] bArr4 = new byte[outputBlockSize];
        boolean z2 = outputBlockSize < (this.defHash.length * 2) + 1;
        if (processBlock.length <= outputBlockSize) {
            System.arraycopy(processBlock, 0, bArr4, outputBlockSize - processBlock.length, processBlock.length);
        } else {
            System.arraycopy(processBlock, 0, bArr4, 0, outputBlockSize);
            z2 = true;
        }
        byte[] bArr5 = this.defHash;
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(bArr4, bArr5.length, outputBlockSize - bArr5.length, bArr5.length);
        int i4 = 0;
        while (true) {
            bArr2 = this.defHash;
            if (i4 == bArr2.length) {
                break;
            }
            bArr4[i4] = (byte) (bArr4[i4] ^ maskGeneratorFunction1[i4]);
            i4++;
        }
        byte[] maskGeneratorFunction12 = maskGeneratorFunction1(bArr4, 0, bArr2.length, outputBlockSize - bArr2.length);
        for (int length = this.defHash.length; length != outputBlockSize; length++) {
            bArr4[length] = (byte) (bArr4[length] ^ maskGeneratorFunction12[length - this.defHash.length]);
        }
        int i5 = 0;
        boolean z3 = false;
        while (true) {
            bArr3 = this.defHash;
            if (i5 == bArr3.length) {
                break;
            }
            if (bArr3[i5] != bArr4[bArr3.length + i5]) {
                z3 = true;
            }
            i5++;
        }
        int i6 = outputBlockSize;
        for (int length2 = bArr3.length * 2; length2 != outputBlockSize; length2++) {
            if ((bArr4[length2] != 0) & (i6 == outputBlockSize)) {
                i6 = length2;
            }
        }
        boolean z4 = i6 > outputBlockSize + (-1);
        boolean z5 = bArr4[i6] != 1;
        int i7 = i6 + 1;
        if ((z4 | z5) || (z2 | z3)) {
            Arrays.fill(bArr4, (byte) 0);
            throw new InvalidCipherTextException("data wrong");
        }
        int i8 = outputBlockSize - i7;
        byte[] bArr6 = new byte[i8];
        System.arraycopy(bArr4, i7, bArr6, 0, i8);
        Arrays.fill(bArr4, (byte) 0);
        return bArr6;
    }

    public byte[] encodeBlock(byte[] bArr, int i2, int i3) {
        if (i3 > getInputBlockSize()) {
            throw new DataLengthException("input data too long");
        }
        int length = (this.defHash.length * 2) + getInputBlockSize() + 1;
        byte[] bArr2 = new byte[length];
        int i4 = length - i3;
        System.arraycopy(bArr, i2, bArr2, i4, i3);
        bArr2[i4 - 1] = 1;
        byte[] bArr3 = this.defHash;
        System.arraycopy(bArr3, 0, bArr2, bArr3.length, bArr3.length);
        int length2 = this.defHash.length;
        byte[] bArr4 = new byte[length2];
        this.random.nextBytes(bArr4);
        byte[] maskGeneratorFunction1 = maskGeneratorFunction1(bArr4, 0, length2, length - this.defHash.length);
        for (int length3 = this.defHash.length; length3 != length; length3++) {
            bArr2[length3] = (byte) (bArr2[length3] ^ maskGeneratorFunction1[length3 - this.defHash.length]);
        }
        System.arraycopy(bArr4, 0, bArr2, 0, this.defHash.length);
        byte[] bArr5 = this.defHash;
        byte[] maskGeneratorFunction12 = maskGeneratorFunction1(bArr2, bArr5.length, length - bArr5.length, bArr5.length);
        for (int i5 = 0; i5 != this.defHash.length; i5++) {
            bArr2[i5] = (byte) (bArr2[i5] ^ maskGeneratorFunction12[i5]);
        }
        return this.engine.processBlock(bArr2, 0, length);
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getInputBlockSize() {
        int inputBlockSize = this.engine.getInputBlockSize();
        return this.forEncryption ? (inputBlockSize - 1) - (this.defHash.length * 2) : inputBlockSize;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public int getOutputBlockSize() {
        int outputBlockSize = this.engine.getOutputBlockSize();
        return this.forEncryption ? outputBlockSize : (outputBlockSize - 1) - (this.defHash.length * 2);
    }

    public AsymmetricBlockCipher getUnderlyingCipher() {
        return this.engine;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public void init(boolean z2, CipherParameters cipherParameters) {
        this.random = cipherParameters instanceof ParametersWithRandom ? ((ParametersWithRandom) cipherParameters).getRandom() : CryptoServicesRegistrar.getSecureRandom();
        this.engine.init(z2, cipherParameters);
        this.forEncryption = z2;
    }

    @Override // org.bouncycastle.crypto.AsymmetricBlockCipher
    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        return this.forEncryption ? encodeBlock(bArr, i2, i3) : decodeBlock(bArr, i2, i3);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, null);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] bArr) {
        this.engine = asymmetricBlockCipher;
        this.mgf1Hash = digest2;
        this.defHash = new byte[digest.getDigestSize()];
        digest.reset();
        if (bArr != null) {
            digest.update(bArr, 0, bArr.length);
        }
        digest.doFinal(this.defHash, 0);
    }

    public OAEPEncoding(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest, bArr);
    }
}
