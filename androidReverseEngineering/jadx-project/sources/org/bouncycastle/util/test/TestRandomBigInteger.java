package org.bouncycastle.util.test;

import java.math.BigInteger;
import org.bouncycastle.util.BigIntegers;
import org.bouncycastle.util.test.FixedSecureRandom;

/* loaded from: classes.dex */
public class TestRandomBigInteger extends FixedSecureRandom {
    public TestRandomBigInteger(int i2, byte[] bArr) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(i2, bArr)});
    }

    public TestRandomBigInteger(String str) {
        this(str, 10);
    }

    public TestRandomBigInteger(String str, int i2) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(BigIntegers.asUnsignedByteArray(new BigInteger(str, i2)))});
    }

    public TestRandomBigInteger(byte[] bArr) {
        super(new FixedSecureRandom.Source[]{new FixedSecureRandom.BigInteger(bArr)});
    }
}
