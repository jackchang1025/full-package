package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

/* loaded from: classes.dex */
public class OneEightyDegreeInt extends ASN1Integer {
    private static final BigInteger loweBound = new BigInteger("-1799999999");
    private static final BigInteger upperBound = new BigInteger("1800000000");
    private static final BigInteger unknown = new BigInteger("1800000001");

    public OneEightyDegreeInt(long j2) {
        super(j2);
        assertValue();
    }

    public static OneEightyDegreeInt getInstance(Object obj) {
        return obj instanceof OneEightyDegreeInt ? (OneEightyDegreeInt) obj : new OneEightyDegreeInt(ASN1Integer.getInstance(obj).getValue());
    }

    public void assertValue() {
        BigInteger value = getValue();
        if (value.compareTo(loweBound) < 0) {
            throw new IllegalStateException("one eighty degree int cannot be less than -1799999999");
        }
        if (!value.equals(unknown) && value.compareTo(upperBound) > 0) {
            throw new IllegalStateException("one eighty degree int cannot be greater than 1800000000");
        }
    }

    public OneEightyDegreeInt(BigInteger bigInteger) {
        super(bigInteger);
        assertValue();
    }

    public OneEightyDegreeInt(byte[] bArr) {
        super(bArr);
        assertValue();
    }
}
