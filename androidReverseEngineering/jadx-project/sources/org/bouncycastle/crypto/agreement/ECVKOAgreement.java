package org.bouncycastle.crypto.agreement;

import java.math.BigInteger;
import org.bouncycastle.crypto.CipherParameters;
import org.bouncycastle.crypto.Digest;
import org.bouncycastle.crypto.params.ECDomainParameters;
import org.bouncycastle.crypto.params.ECPrivateKeyParameters;
import org.bouncycastle.crypto.params.ECPublicKeyParameters;
import org.bouncycastle.crypto.params.ParametersWithUKM;
import org.bouncycastle.math.ec.ECAlgorithms;
import org.bouncycastle.math.ec.ECPoint;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class ECVKOAgreement {
    private final Digest digest;
    private ECPrivateKeyParameters key;
    private BigInteger ukm;

    public ECVKOAgreement(Digest digest) {
        this.digest = digest;
    }

    private byte[] fromPoint(ECPoint eCPoint) {
        BigInteger bigInteger = eCPoint.getAffineXCoord().toBigInteger();
        BigInteger bigInteger2 = eCPoint.getAffineYCoord().toBigInteger();
        int i2 = bigInteger.toByteArray().length > 33 ? 64 : 32;
        int i3 = i2 * 2;
        byte[] bArr = new byte[i3];
        byte[] asUnsignedByteArray = BigIntegers.asUnsignedByteArray(i2, bigInteger);
        byte[] asUnsignedByteArray2 = BigIntegers.asUnsignedByteArray(i2, bigInteger2);
        for (int i4 = 0; i4 != i2; i4++) {
            bArr[i4] = asUnsignedByteArray[(i2 - i4) - 1];
        }
        for (int i5 = 0; i5 != i2; i5++) {
            bArr[i2 + i5] = asUnsignedByteArray2[(i2 - i5) - 1];
        }
        this.digest.update(bArr, 0, i3);
        byte[] bArr2 = new byte[this.digest.getDigestSize()];
        this.digest.doFinal(bArr2, 0);
        return bArr2;
    }

    private static BigInteger toInteger(byte[] bArr) {
        int length = bArr.length;
        byte[] bArr2 = new byte[length];
        for (int i2 = 0; i2 != length; i2++) {
            bArr2[i2] = bArr[(bArr.length - i2) - 1];
        }
        return new BigInteger(1, bArr2);
    }

    public byte[] calculateAgreement(CipherParameters cipherParameters) {
        ECPublicKeyParameters eCPublicKeyParameters = (ECPublicKeyParameters) cipherParameters;
        ECDomainParameters parameters = this.key.getParameters();
        if (!parameters.equals(eCPublicKeyParameters.getParameters())) {
            throw new IllegalStateException("ECVKO public key has wrong domain parameters");
        }
        BigInteger mod = parameters.getH().multiply(this.ukm).multiply(this.key.getD()).mod(parameters.getN());
        ECPoint cleanPoint = ECAlgorithms.cleanPoint(parameters.getCurve(), eCPublicKeyParameters.getQ());
        if (cleanPoint.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid public key for ECDHC");
        }
        ECPoint normalize = cleanPoint.multiply(mod).normalize();
        if (normalize.isInfinity()) {
            throw new IllegalStateException("Infinity is not a valid agreement value for ECVKO");
        }
        return fromPoint(normalize);
    }

    public int getFieldSize() {
        return (this.key.getParameters().getCurve().getFieldSize() + 7) / 8;
    }

    public void init(CipherParameters cipherParameters) {
        ParametersWithUKM parametersWithUKM = (ParametersWithUKM) cipherParameters;
        this.key = (ECPrivateKeyParameters) parametersWithUKM.getParameters();
        this.ukm = toInteger(parametersWithUKM.getUKM());
    }
}
