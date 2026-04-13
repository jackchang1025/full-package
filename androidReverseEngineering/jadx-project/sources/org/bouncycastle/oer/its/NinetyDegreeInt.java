package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

/* loaded from: classes.dex */
public class NinetyDegreeInt extends ASN1Integer {
    private static final BigInteger loweBound = new BigInteger("-900000000");
    private static final BigInteger upperBound = new BigInteger("900000000");
    private static final BigInteger unknown = new BigInteger("900000001");

    public NinetyDegreeInt(long j2) {
        super(j2);
        assertValue();
    }

    public static NinetyDegreeInt getInstance(Object obj) {
        return obj instanceof NinetyDegreeInt ? (NinetyDegreeInt) obj : new NinetyDegreeInt(ASN1Integer.getInstance(obj).getValue());
    }

    public void assertValue() {
        BigInteger value = getValue();
        if (value.compareTo(loweBound) < 0) {
            throw new IllegalStateException("ninety degree int cannot be less than -900000000");
        }
        if (!value.equals(unknown) && value.compareTo(upperBound) > 0) {
            throw new IllegalStateException("ninety degree int cannot be greater than 900000000");
        }
    }

    public NinetyDegreeInt(BigInteger bigInteger) {
        super(bigInteger);
        assertValue();
    }

    public NinetyDegreeInt(byte[] bArr) {
        super(bArr);
        assertValue();
    }
}
