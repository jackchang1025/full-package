package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Integer;

/* loaded from: classes.dex */
public class Longitude extends OneEightyDegreeInt {
    public Longitude(long j2) {
        super(j2);
    }

    public static Longitude getInstance(Object obj) {
        return obj instanceof Longitude ? (Longitude) obj : obj instanceof OneEightyDegreeInt ? new Longitude(((OneEightyDegreeInt) obj).getValue()) : new Longitude(ASN1Integer.getInstance(obj).getValue());
    }

    public Longitude(BigInteger bigInteger) {
        super(bigInteger);
    }

    public Longitude(byte[] bArr) {
        super(bArr);
    }
}
