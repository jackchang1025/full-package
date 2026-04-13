package org.bouncycastle.crypto.kems;

import java.math.BigInteger;
import java.security.SecureRandom;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.DerivationFunction;
import org.bouncycastle.crypto.KeyEncapsulation;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECKeyParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.KDFParameters;
import org.bouncycastle.crypto.params.KeyParameter;
import org.bouncycastle.math.ec.ECCurve;
import org.bouncycastle.math.ec.ECMultiplier;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.math.ec.FixedPointCombMultiplier;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECIESKeyEncapsulation implements KeyEncapsulation {
    private static final BigInteger ONE = BigInteger.valueOf(1);
    private boolean CofactorMode;
    private boolean OldCofactorMode;
    private boolean SingleHashMode;
    private DerivationFunction kdf;
    private ECKeyParameters key;
    private SecureRandom rnd;

    public ECIESKeyEncapsulation(DerivationFunction derivationFunction, SecureRandom secureRandom) {
        this.kdf = derivationFunction;
        this.rnd = secureRandom;
        this.CofactorMode = false;
        this.OldCofactorMode = false;
        this.SingleHashMode = false;
    }

    public ECMultiplier createBasePointMultiplier() {
        return new FixedPointCombMultiplier();
    }

    public CipherParameters decrypt(byte[] bArr, int i2) {
        return decrypt(bArr, 0, bArr.length, i2);
    }

    public KeyParameter deriveKey(int i2, byte[] bArr, byte[] bArr2) {
        if (!this.SingleHashMode) {
            byte[] concatenate = Arrays.concatenate(bArr, bArr2);
            Arrays.fill(bArr2, (byte) 0);
            bArr2 = concatenate;
        }
        try {
            this.kdf.init(new KDFParameters(bArr2, null));
            byte[] bArr3 = new byte[i2];
            this.kdf.generateBytes(bArr3, 0, i2);
            return new KeyParameter(bArr3);
        } finally {
            Arrays.fill(bArr2, (byte) 0);
        }
    }

    public CipherParameters encrypt(byte[] bArr, int i2) {
        return encrypt(bArr, 0, i2);
    }

    @Override // org.bouncycastle.crypto.KeyEncapsulation
    public void init(CipherParameters cipherParameters) {
        if (!(cipherParameters instanceof ECKeyParameters)) {
            throw new IllegalArgumentException("EC key required");
        }
        this.key = (ECKeyParameters) cipherParameters;
    }

    public ECIESKeyEncapsulation(DerivationFunction derivationFunction, SecureRandom secureRandom, boolean z2, boolean z3, boolean z4) {
        this.kdf = derivationFunction;
        this.rnd = secureRandom;
        this.CofactorMode = z2;
        if (z2) {
            this.OldCofactorMode = false;
        } else {
            this.OldCofactorMode = z3;
        }
        this.SingleHashMode = z4;
    }

    @Override // org.bouncycastle.crypto.KeyEncapsulation
    public CipherParameters decrypt(byte[] bArr, int i2, int i3, int i4) {
        ECKeyParameters eCKeyParameters = this.key;
        if (!(eCKeyParameters instanceof ECPrivateKeyParameters)) {
            throw new IllegalArgumentException("Private key required for encryption");
        }
        ECPrivateKeyParameters eCPrivateKeyParameters = (ECPrivateKeyParameters) eCKeyParameters;
        ECDomainParameters parameters = eCPrivateKeyParameters.getParameters();
        ECCurve curve = parameters.getCurve();
        BigInteger n2 = parameters.getN();
        BigInteger h2 = parameters.getH();
        byte[] bArr2 = new byte[i3];
        System.arraycopy(bArr, i2, bArr2, 0, i3);
        ECPoint decodePoint = curve.decodePoint(bArr2);
        if (this.CofactorMode || this.OldCofactorMode) {
            decodePoint = decodePoint.multiply(h2);
        }
        BigInteger d2 = eCPrivateKeyParameters.getD();
        if (this.CofactorMode) {
            d2 = d2.multiply(parameters.getHInv()).mod(n2);
        }
        return deriveKey(i4, bArr2, decodePoint.multiply(d2).normalize().getAffineXCoord().getEncoded());
    }

    @Override // org.bouncycastle.crypto.KeyEncapsulation
    public CipherParameters encrypt(byte[] bArr, int i2, int i3) {
        ECKeyParameters eCKeyParameters = this.key;
        if (!(eCKeyParameters instanceof ECPublicKeyParameters)) {
            throw new IllegalArgumentException("Public key required for encryption");
        }
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) eCKeyParameters;
        ECDomainParameters parameters = eCPublicKeyParameters.getParameters();
        ECCurve curve = parameters.getCurve();
        BigInteger n2 = parameters.getN();
        BigInteger h2 = parameters.getH();
        BigInteger createRandomInRange = BigIntegers.createRandomInRange(ONE, n2, this.rnd);
        ECPoint[] eCPointArr = {createBasePointMultiplier().multiply(parameters.getG(), createRandomInRange), eCPublicKeyParameters.getQ().multiply(this.OldCofactorMode ? createRandomInRange.multiply(h2).mod(n2) : createRandomInRange)};
        curve.normalizeAll(eCPointArr);
        ECPoint eCPoint = eCPointArr[0];
        ECPoint eCPoint2 = eCPointArr[1];
        byte[] encoded = eCPoint.getEncoded(false);
        System.arraycopy(encoded, 0, bArr, i2, encoded.length);
        return deriveKey(i3, encoded, eCPoint2.getAffineXCoord().getEncoded());
    }
}
