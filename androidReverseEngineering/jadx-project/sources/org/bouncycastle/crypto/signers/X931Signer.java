package org.bouncycastle.crypto.signers;

import android.sun.security.util.DerValue;
import java.math.BigInteger;
import org.bouncycastle.crypto.AsymmetricBlockCipher;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.Signer;
import org.bouncycastle.crypto.params.RSAKeyParameters;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class X931Signer implements Signer {
    public static final int TRAILER_IMPLICIT = 188;
    public static final int TRAILER_RIPEMD128 = 13004;
    public static final int TRAILER_RIPEMD160 = 12748;
    public static final int TRAILER_SHA1 = 13260;
    public static final int TRAILER_SHA224 = 14540;
    public static final int TRAILER_SHA256 = 13516;
    public static final int TRAILER_SHA384 = 14028;
    public static final int TRAILER_SHA512 = 13772;
    public static final int TRAILER_WHIRLPOOL = 14284;
    private byte[] block;
    private AsymmetricBlockCipher cipher;
    private Digest digest;
    private RSAKeyParameters kParam;
    private int keyBits;
    private int trailer;

    public X931Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest) {
        this(asymmetricBlockCipher, digest, false);
    }

    private void clearBlock(byte[] bArr) {
        for (int i2 = 0; i2 != bArr.length; i2++) {
            bArr[i2] = 0;
        }
    }

    private void createSignatureBlock(int i2) {
        int i3;
        int digestSize = this.digest.getDigestSize();
        if (i2 == 188) {
            byte[] bArr = this.block;
            i3 = (bArr.length - digestSize) - 1;
            this.digest.doFinal(bArr, i3);
            this.block[r5.length - 1] = PSSSigner.TRAILER_IMPLICIT;
        } else {
            byte[] bArr2 = this.block;
            int length = (bArr2.length - digestSize) - 2;
            this.digest.doFinal(bArr2, length);
            byte[] bArr3 = this.block;
            bArr3[bArr3.length - 2] = (byte) (i2 >>> 8);
            bArr3[bArr3.length - 1] = (byte) i2;
            i3 = length;
        }
        this.block[0] = 107;
        for (int i4 = i3 - 2; i4 != 0; i4--) {
            this.block[i4] = -69;
        }
        this.block[i3 - 1] = -70;
    }

    @Override // org.bouncycastle.crypto.Signer
    public byte[] generateSignature() {
        createSignatureBlock(this.trailer);
        AsymmetricBlockCipher asymmetricBlockCipher = this.cipher;
        byte[] bArr = this.block;
        BigInteger bigInteger = new BigInteger(1, asymmetricBlockCipher.processBlock(bArr, 0, bArr.length));
        clearBlock(this.block);
        return BigIntegers.asUnsignedByteArray(BigIntegers.getUnsignedByteLength(this.kParam.getModulus()), bigInteger.min(this.kParam.getModulus().subtract(bigInteger)));
    }

    @Override // org.bouncycastle.crypto.Signer
    public void init(boolean z2, CipherParameters cipherParameters) {
        RSAKeyParameters rSAKeyParameters = (RSAKeyParameters) cipherParameters;
        this.kParam = rSAKeyParameters;
        this.cipher.init(z2, rSAKeyParameters);
        int bitLength = this.kParam.getModulus().bitLength();
        this.keyBits = bitLength;
        this.block = new byte[(bitLength + 7) / 8];
        reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void reset() {
        this.digest.reset();
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte b) {
        this.digest.update(b);
    }

    /* JADX WARN: Code restructure failed: missing block: B:7:0x002d, code lost:
    
        if ((r4.intValue() & 15) == 12) goto L9;
     */
    @Override // org.bouncycastle.crypto.Signer
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public boolean verifySignature(byte[] bArr) {
        boolean z2 = false;
        try {
            this.block = this.cipher.processBlock(bArr, 0, bArr.length);
            BigInteger bigInteger = new BigInteger(1, this.block);
            if ((bigInteger.intValue() & 15) != 12) {
                bigInteger = this.kParam.getModulus().subtract(bigInteger);
            }
            createSignatureBlock(this.trailer);
            byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(this.block.length, bigInteger);
            z2 = Arrays.constantTimeAreEqual(this.block, asUnsignedByteArray);
            if (this.trailer == 15052 && !z2) {
                byte[] bArr2 = this.block;
                bArr2[bArr2.length - 2] = DerValue.TAG_APPLICATION;
                z2 = Arrays.constantTimeAreEqual(bArr2, asUnsignedByteArray);
            }
            clearBlock(this.block);
            clearBlock(asUnsignedByteArray);
        } catch (Exception unused) {
        }
        return z2;
    }

    public X931Signer(AsymmetricBlockCipher asymmetricBlockCipher, Digest digest, boolean z2) {
        int intValue;
        this.cipher = asymmetricBlockCipher;
        this.digest = digest;
        if (z2) {
            intValue = 188;
        } else {
            Integer trailer = ISOTrailers.getTrailer(digest);
            if (trailer == null) {
                throw new IllegalArgumentException("no valid trailer for digest: " + digest.getAlgorithmName());
            }
            intValue = trailer.intValue();
        }
        this.trailer = intValue;
    }

    @Override // org.bouncycastle.crypto.Signer
    public void update(byte[] bArr, int i2, int i3) {
        this.digest.update(bArr, i2, i3);
    }
}
