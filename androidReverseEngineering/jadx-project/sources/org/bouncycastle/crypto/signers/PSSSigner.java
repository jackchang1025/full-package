package org.bouncycastle.crypto.signers;

import java.security.SecureRandom;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.CryptoServicesRegistrar;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.Xof;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.crypto.params.RSABlindingParameters;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.Arrays;

/* loaded from: classes.dex */
public class PSSSigner implements Signer {
    public static final byte TRAILER_IMPLICIT = -68;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest contentDigest;
    private int emBits;
    private int hLen;
    private byte[] mDash;
    private Digest mgfDigest;
    private int mgfhLen;
    private SecureRandom random;
    private int sLen;
    private boolean sSet;
    private byte[] salt;
    private byte trailer;

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2) {
        this(asymmetricBlockCipher, digest, i2, TRAILER_IMPLICIT);
    }

    private void ItoOSP(int i2, byte[] bArr) {
        bArr[0] = (byte) (i2 >>> 24);
        bArr[1] = (byte) (i2 >>> 16);
        bArr[2] = (byte) (i2 >>> 8);
        bArr[3] = (byte) (i2 >>> 0);
    }

    private void clearBlock(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private byte[] maskGenerator(byte[] bArr, int i2, int i3, int i4) {
        Digest digest = this.mgfDigest;
        if (!(digest instanceof Xof)) {
            return maskGeneratorFunction1(bArr, i2, i3, i4);
        }
        byte[] bArr2 = new byte[i4];
        digest.update(bArr, i2, i3);
        ((Xof) this.mgfDigest).doFinal(bArr2, 0, i4);
        return bArr2;
    }

    private byte[] maskGeneratorFunction1(byte[] bArr, int i2, int i3, int i4) {
        int i5;
        byte[] bArr2 = new byte[i4];
        byte[] bArr3 = new byte[this.mgfhLen];
        byte[] bArr4 = new byte[4];
        this.mgfDigest.reset();
        int i6 = 0;
        while (true) {
            i5 = this.mgfhLen;
            if (i6 >= i4 / i5) {
                break;
            }
            ItoOSP(i6, bArr4);
            this.mgfDigest.update(bArr, i2, i3);
            this.mgfDigest.update(bArr4, 0, 4);
            this.mgfDigest.doFinal(bArr3, 0);
            int i7 = this.mgfhLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i7, i7);
            i6++;
        }
        if (i5 * i6 < i4) {
            ItoOSP(i6, bArr4);
            this.mgfDigest.update(bArr, i2, i3);
            this.mgfDigest.update(bArr4, 0, 4);
            this.mgfDigest.doFinal(bArr3, 0);
            int i8 = this.mgfhLen;
            System.arraycopy(bArr3, 0, bArr2, i6 * i8, i4 - (i6 * i8));
        }
        return bArr2;
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() {
        Digest digest = this.contentDigest;
        byte[] bArr = this.mDash;
        digest.doFinal(bArr, (bArr.length - this.hLen) - this.sLen);
        if (this.sLen != 0) {
            if (!this.sSet) {
                this.random.nextBytes(this.salt);
            }
            byte[] bArr2 = this.salt;
            byte[] bArr3 = this.mDash;
            int length = bArr3.length;
            int i2 = this.sLen;
            System.arraycopy(bArr2, 0, bArr3, length - i2, i2);
        }
        int i3 = this.hLen;
        byte[] bArr4 = new byte[i3];
        Digest digest2 = this.contentDigest;
        byte[] bArr5 = this.mDash;
        digest2.update(bArr5, 0, bArr5.length);
        this.contentDigest.doFinal(bArr4, 0);
        byte[] bArr6 = this.block;
        int length2 = bArr6.length;
        int i4 = this.sLen;
        int i5 = this.hLen;
        bArr6[(((length2 - i4) - 1) - i5) - 1] = 1;
        System.arraycopy(this.salt, 0, bArr6, ((bArr6.length - i4) - i5) - 1, i4);
        byte[] maskGenerator = maskGenerator(bArr4, 0, i3, (this.block.length - this.hLen) - 1);
        for (int i6 = 0; i6 != maskGenerator.length; i6++) {
            byte[] bArr7 = this.block;
            bArr7[i6] = (byte) (bArr7[i6] ^ maskGenerator[i6]);
        }
        byte[] bArr8 = this.block;
        int length3 = bArr8.length;
        int i7 = this.hLen;
        System.arraycopy(bArr4, 0, bArr8, (length3 - i7) - 1, i7);
        byte[] bArr9 = this.block;
        bArr9[0] = (byte) ((255 >>> ((bArr9.length * 8) - this.emBits)) & bArr9[0]);
        bArr9[bArr9.length - 1] = this.trailer;
        byte[] processBlock = this.cipher.processBlock(bArr9, 0, bArr9.length);
        clearBlock(this.block);
        return processBlock;
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        CipherParameters cipherParameters2;
        RSAKeyParameters rSAKeyParameters;
        if (cipherParameters instanceof ParametersWithRandom) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            cipherParameters2 = parametersWithRandom.getParameters();
            this.random = parametersWithRandom.getRandom();
        } else {
            if (z2) {
                this.random = CryptoServicesRegistrar.getSecureRandom();
            }
            cipherParameters2 = cipherParameters;
        }
        if (cipherParameters2 instanceof RSABlindingParameters) {
            rSAKeyParameters = ((RSABlindingParameters) cipherParameters2).getPublicKey();
            this.cipher.init(z2, cipherParameters);
        } else {
            rSAKeyParameters = (RSAKeyParameters) cipherParameters2;
            this.cipher.init(z2, cipherParameters2);
        }
        int bitLength = rSAKeyParameters.getModulus().bitLength() - 1;
        this.emBits = bitLength;
        if (bitLength < (this.sLen * 8) + (this.hLen * 8) + 9) {
            throw new IllegalArgumentException("key too small for specified hash and salt lengths");
        }
        this.block = new byte[(bitLength + 7) / 8];
        reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.contentDigest.reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b) {
        this.contentDigest.update(b);
    }

    @Override // org.bouncycastle.crypto.Signer
    public boolean verifySignature(byte[] bArr) {
        byte[] bArr2;
        int length;
        byte b;
        Digest digest = this.contentDigest;
        byte[] bArr3 = this.mDash;
        digest.doFinal(bArr3, (bArr3.length - this.hLen) - this.sLen);
        try {
            byte[] processBlock = this.cipher.processBlock(bArr, 0, bArr.length);
            byte[] bArr4 = this.block;
            Arrays.fill(bArr4, 0, bArr4.length - processBlock.length, (byte) 0);
            byte[] bArr5 = this.block;
            System.arraycopy(processBlock, 0, bArr5, bArr5.length - processBlock.length, processBlock.length);
            bArr2 = this.block;
            length = 255 >>> ((bArr2.length * 8) - this.emBits);
            b = bArr2[0];
        } catch (Exception unused) {
        }
        if ((b & 255) != (b & length) || bArr2[bArr2.length - 1] != this.trailer) {
            clearBlock(bArr2);
            return false;
        }
        int length2 = bArr2.length;
        int i2 = this.hLen;
        byte[] maskGenerator = maskGenerator(bArr2, (length2 - i2) - 1, i2, (bArr2.length - i2) - 1);
        for (int i3 = 0; i3 != maskGenerator.length; i3++) {
            byte[] bArr6 = this.block;
            bArr6[i3] = (byte) (bArr6[i3] ^ maskGenerator[i3]);
        }
        byte[] bArr7 = this.block;
        bArr7[0] = (byte) (length & bArr7[0]);
        int i4 = 0;
        while (true) {
            byte[] bArr8 = this.block;
            int length3 = bArr8.length;
            int i5 = this.hLen;
            int i6 = this.sLen;
            if (i4 != ((length3 - i5) - i6) - 2) {
                if (bArr8[i4] != 0) {
                    clearBlock(bArr8);
                    return false;
                }
                i4++;
            } else {
                if (bArr8[((bArr8.length - i5) - i6) - 2] != 1) {
                    clearBlock(bArr8);
                    return false;
                }
                if (this.sSet) {
                    byte[] bArr9 = this.salt;
                    byte[] bArr10 = this.mDash;
                    System.arraycopy(bArr9, 0, bArr10, bArr10.length - i6, i6);
                } else {
                    int length4 = ((bArr8.length - i6) - i5) - 1;
                    byte[] bArr11 = this.mDash;
                    System.arraycopy(bArr8, length4, bArr11, bArr11.length - i6, i6);
                }
                Digest digest2 = this.contentDigest;
                byte[] bArr12 = this.mDash;
                digest2.update(bArr12, 0, bArr12.length);
                Digest digest3 = this.contentDigest;
                byte[] bArr13 = this.mDash;
                digest3.doFinal(bArr13, bArr13.length - this.hLen);
                int length5 = this.block.length;
                int i7 = this.hLen;
                int i8 = (length5 - i7) - 1;
                int length6 = this.mDash.length - i7;
                while (true) {
                    byte[] bArr14 = this.mDash;
                    if (length6 == bArr14.length) {
                        clearBlock(bArr14);
                        clearBlock(this.block);
                        return true;
                    }
                    if ((this.block[i8] ^ bArr14[length6]) != 0) {
                        clearBlock(bArr14);
                        clearBlock(this.block);
                        return false;
                    }
                    i8++;
                    length6++;
                }
            }
        }
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, int i2, byte b) {
        this(asymmetricBlockCipher, digest, digest, i2, b);
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        this.contentDigest.update(bArr, i2, i3);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2) {
        this(asymmetricBlockCipher, digest, digest2, i2, TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, int i2, byte b) {
        this.cipher = asymmetricBlockCipher;
        this.contentDigest = digest;
        this.mgfDigest = digest2;
        this.hLen = digest.getDigestSize();
        this.mgfhLen = digest2.getDigestSize();
        this.sSet = false;
        this.sLen = i2;
        this.salt = new byte[i2];
        this.mDash = new byte[i2 + 8 + this.hLen];
        this.trailer = b;
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest2, bArr, TRAILER_IMPLICIT);
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, Digest digest2, byte[] bArr, byte b) {
        this.cipher = asymmetricBlockCipher;
        this.contentDigest = digest;
        this.mgfDigest = digest2;
        this.hLen = digest.getDigestSize();
        this.mgfhLen = digest2.getDigestSize();
        this.sSet = true;
        int length = bArr.length;
        this.sLen = length;
        this.salt = bArr;
        this.mDash = new byte[length + 8 + this.hLen];
        this.trailer = b;
    }

    public PSSSigner(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, byte[] bArr) {
        this(asymmetricBlockCipher, digest, digest, bArr, TRAILER_IMPLICIT);
    }
}
