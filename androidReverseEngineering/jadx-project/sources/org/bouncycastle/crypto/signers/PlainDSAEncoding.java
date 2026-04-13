package org.bouncycastle.crypto.signers;

import java.math.BigInteger;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.BigIntegers;

/* loaded from: classes.dex */
public class PlainDSAEncoding implements DSAEncoding {
    public static final PlainDSAEncoding INSTANCE = new PlainDSAEncoding();

    private void encodeValue(BigInteger bigInteger, BigInteger bigInteger2, byte[] bArr, int i2, int i3) {
        byte[] byteArray = checkValue(bigInteger, bigInteger2).toByteArray();
        int max = Math.max(0, byteArray.length - i3);
        int length = byteArray.length - max;
        int i4 = (i3 - length) + i2;
        Arrays.fill(bArr, i2, i4, (byte) 0);
        System.arraycopy(byteArray, max, bArr, i4, length);
    }

    public BigInteger checkValue(BigInteger bigInteger, BigInteger bigInteger2) {
        if (bigInteger2.signum() < 0 || bigInteger2.compareTo(bigInteger) >= 0) {
            throw new IllegalArgumentException("Value out of range");
        }
        return bigInteger2;
    }

    @Override // org.bouncycastle.crypto.signers.DSAEncoding
    public BigInteger[] decode(BigInteger bigInteger, byte[] bArr) {
        int unsignedByteLength = BigIntegers.getUnsignedByteLength(bigInteger);
        if (bArr.length == unsignedByteLength * 2) {
            return new BigInteger[]{decodeValue(bigInteger, bArr, 0, unsignedByteLength), decodeValue(bigInteger, bArr, unsignedByteLength, unsignedByteLength)};
        }
        throw new IllegalArgumentException("Encoding has incorrect length");
    }

    public BigInteger decodeValue(BigInteger bigInteger, byte[] bArr, int i2, int i3) {
        return checkValue(bigInteger, new BigInteger(1, Arrays.copyOfRange(bArr, i2, i3 + i2)));
    }

    @Override // org.bouncycastle.crypto.signers.DSAEncoding
    public byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3) {
        int unsignedByteLength = BigIntegers.getUnsignedByteLength(bigInteger);
        byte[] bArr = new byte[unsignedByteLength * 2];
        encodeValue(bigInteger, bigInteger2, bArr, 0, unsignedByteLength);
        encodeValue(bigInteger, bigInteger3, bArr, unsignedByteLength, unsignedByteLength);
        return bArr;
    }
}
