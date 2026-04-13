package org.bouncycastle.crypto.signers;

import java.math.BigInteger;

/* loaded from: classes.dex */
public interface DSAEncoding {
    BigInteger[] decode(BigInteger bigInteger, byte[] bArr);

    byte[] encode(BigInteger bigInteger, BigInteger bigInteger2, BigInteger bigInteger3);
}
