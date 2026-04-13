package org.bouncycastle.crypto.engines;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.InvalidCipherTextException;
import org.bouncycastle.crypto.digests.SM3Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithRandom;
import org.bouncycastle.math.ec.ECFieldElement;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.Memoable;
import org.bouncycastle.util.Pack;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SM2Engine {
    private int curveLength;
    private final Digest digest;
    private ECKeyParameters ecKey;
    private ECDomainParameters ecParams;
    private boolean forEncryption;
    private final Mode mode;
    private SecureRandom random;

    /* renamed from: org.bouncycastle.crypto.engines.SM2Engine$1 */
    public static /* synthetic */ class C05691 {
        static final /* synthetic */ int[] $SwitchMap$org$bouncycastle$crypto$engines$SM2Engine$Mode;

        static {
            int[] iArr = new int[Mode.values().length];
            $SwitchMap$org$bouncycastle$crypto$engines$SM2Engine$Mode = iArr;
            try {
                iArr[Mode.C1C3C2.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
        }
    }

    public enum Mode {
        C1C2C3,
        C1C3C2
    }

    public SM2Engine() {
        this(new SM3Digest());
    }

    private void addFieldElement(Digest digest, ECFieldElement eCFieldElement) {
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(this.curveLength, eCFieldElement.toBigInteger());
        digest.update(asUnsignedByteArray, 0, asUnsignedByteArray.length);
    }

    private byte[] decrypt(byte[] bArr, int i2, int i3) {
        int i4;
        int i5 = (this.curveLength * 2) + 1;
        byte[] bArr2 = new byte[i5];
        System.arraycopy(bArr, i2, bArr2, 0, i5);
        ECPoint decodePoint = this.ecParams.getCurve().decodePoint(bArr2);
        if (decodePoint.multiply(this.ecParams.getH()).isInfinity()) {
            throw new InvalidCipherTextException("[h]C1 at infinity");
        }
        ECPoint normalize = decodePoint.multiply(((ECPrivateKeyParameters) this.ecKey).getD()).normalize();
        int digestSize = this.digest.getDigestSize();
        int i6 = (i3 - i5) - digestSize;
        byte[] bArr3 = new byte[i6];
        Mode mode = this.mode;
        Mode mode2 = Mode.C1C3C2;
        if (mode == mode2) {
            System.arraycopy(bArr, i2 + i5 + digestSize, bArr3, 0, i6);
        } else {
            System.arraycopy(bArr, i2 + i5, bArr3, 0, i6);
        }
        kdf(this.digest, normalize, bArr3);
        int digestSize2 = this.digest.getDigestSize();
        byte[] bArr4 = new byte[digestSize2];
        addFieldElement(this.digest, normalize.getAffineXCoord());
        this.digest.update(bArr3, 0, i6);
        addFieldElement(this.digest, normalize.getAffineYCoord());
        this.digest.doFinal(bArr4, 0);
        if (this.mode == mode2) {
            i4 = 0;
            for (int i7 = 0; i7 != digestSize2; i7++) {
                i4 |= bArr4[i7] ^ bArr[(i2 + i5) + i7];
            }
        } else {
            i4 = 0;
            for (int i8 = 0; i8 != digestSize2; i8++) {
                i4 |= bArr4[i8] ^ bArr[AbstractC0000a.m5a(i2, i5, i6, i8)];
            }
        }
        Arrays.fill(bArr2, (byte) 0);
        Arrays.fill(bArr4, (byte) 0);
        if (i4 == 0) {
            return bArr3;
        }
        Arrays.fill(bArr3, (byte) 0);
        throw new InvalidCipherTextException("invalid cipher text");
    }

    private byte[] encrypt(byte[] bArr, int i2, int i3) {
        byte[] encoded;
        ECPoint normalize;
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        ECMultiplier createBasePointMultiplier = createBasePointMultiplier();
        do {
            BigInteger nextK = nextK();
            encoded = createBasePointMultiplier.multiply(this.ecParams.getG(), nextK).normalize().getEncoded(false);
            normalize = ((ECPublicKeyParameters) this.ecKey).getQ().multiply(nextK).normalize();
            kdf(this.digest, normalize, bArr2);
        } while (notEncrypted(bArr2, bArr, i2));
        byte[] bArr3 = new byte[this.digest.getDigestSize()];
        addFieldElement(this.digest, normalize.getAffineXCoord());
        this.digest.update(bArr, i2, i3);
        addFieldElement(this.digest, normalize.getAffineYCoord());
        this.digest.doFinal(bArr3, 0);
        return C05691.$SwitchMap$org$bouncycastle$crypto$engines$SM2Engine$Mode[this.mode.ordinal()] != 1 ? Arrays.concatenate(encoded, bArr2, bArr3) : Arrays.concatenate(encoded, bArr3, bArr2);
    }

    private void kdf(Digest digest, ECPoint eCPoint, byte[] bArr) {
        Memoable memoable;
        Memoable memoable2;
        int digestSize = digest.getDigestSize();
        byte[] bArr2 = new byte[Math.max(4, digestSize)];
        if (digest instanceof Memoable) {
            addFieldElement(digest, eCPoint.getAffineXCoord());
            addFieldElement(digest, eCPoint.getAffineYCoord());
            memoable = (Memoable) digest;
            memoable2 = memoable.copy();
        } else {
            memoable = null;
            memoable2 = null;
        }
        int i2 = 0;
        int i3 = 0;
        while (i2 < bArr.length) {
            if (memoable != null) {
                memoable.reset(memoable2);
            } else {
                addFieldElement(digest, eCPoint.getAffineXCoord());
                addFieldElement(digest, eCPoint.getAffineYCoord());
            }
            i3++;
            Pack.intToBigEndian(i3, bArr2, 0);
            digest.update(bArr2, 0, 4);
            digest.doFinal(bArr2, 0);
            int min = Math.min(digestSize, bArr.length - i2);
            xor(bArr, bArr2, i2, min);
            i2 += min;
        }
    }

    private BigInteger nextK() {
        int bitLength = this.ecParams.getN().bitLength();
        while (true) {
            BigInteger createRandomBigInteger = BigIntegers.createRandomBigInteger(bitLength, this.random);
            if (!createRandomBigInteger.equals(BigIntegers.ZERO) && createRandomBigInteger.compareTo(this.ecParams.getN()) < 0) {
                return createRandomBigInteger;
            }
        }
    }

    private boolean notEncrypted(byte[] bArr, byte[] bArr2, int i2) {
        for (int i3 = 0; i3 != bArr.length; i3++) {
            if (bArr[i3] != bArr2[i2 + i3]) {
                return false;
            }
        }
        return true;
    }

    private void xor(byte[] bArr, byte[] bArr2, int i2, int i3) {
        for (int i4 = 0; i4 != i3; i4++) {
            int i5 = i2 + i4;
            bArr[i5] = (byte) (bArr[i5] ^ bArr2[i4]);
        }
    }

    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    public int getOutputSize(int i2) {
        return this.digest.getDigestSize() + (this.curveLength * 2) + 1 + i2;
    }

    public void init(boolean z2, CipherParameters cipherParameters) {
        this.forEncryption = z2;
        if (z2) {
            ParametersWithRandom parametersWithRandom = (ParametersWithRandom) cipherParameters;
            ECKeyParameters eCKeyParameters = (ECKeyParameters) parametersWithRandom.getParameters();
            this.ecKey = eCKeyParameters;
            this.ecParams = eCKeyParameters.getParameters();
            if (((ECPublicKeyParameters) this.ecKey).getQ().multiply(this.ecParams.getH()).isInfinity()) {
                throw new IllegalArgumentException("invalid key: [h]Q at infinity");
            }
            this.random = parametersWithRandom.getRandom();
        } else {
            ECKeyParameters eCKeyParameters2 = (ECKeyParameters) cipherParameters;
            this.ecKey = eCKeyParameters2;
            this.ecParams = eCKeyParameters2.getParameters();
        }
        this.curveLength = (this.ecParams.getCurve().getFieldSize() + 7) / 8;
    }

    public byte[] processBlock(byte[] bArr, int i2, int i3) {
        return this.forEncryption ? encrypt(bArr, i2, i3) : decrypt(bArr, i2, i3);
    }

    public SM2Engine(Digest digest) {
        this(digest, Mode.C1C2C3);
    }

    public SM2Engine(Digest digest, Mode mode) {
        if (mode == null) {
            throw new IllegalArgumentException("mode cannot be NULL");
        }
        this.digest = digest;
        this.mode = mode;
    }

    public SM2Engine(Mode mode) {
        this(new SM3Digest(), mode);
    }
}
