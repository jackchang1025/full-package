package org.bouncycastle.oer.its;

import java.math.BigInteger;
import org.bouncycastle.asn1.ASN1Enumerated;

/* loaded from: classes.dex */
public class SymmAlgorithm extends ASN1Enumerated {
    public static SymmAlgorithm aes128Ccm = new SymmAlgorithm(0);

    public SymmAlgorithm(int i2) {
        super(i2);
        if (i2 != 0) {
            throw new IllegalArgumentException("ordinal can only be zero");
        }
    }

    public static SymmAlgorithm getInstance(Object obj) {
        if (obj == null) {
            return null;
        }
        if (obj instanceof SymmAlgorithm) {
            return (SymmAlgorithm) obj;
        }
        BigInteger value = ASN1Enumerated.getInstance(obj).getValue();
        if (value.intValue() == 0) {
            return aes128Ccm;
        }
        throw new IllegalArgumentException("unaccounted enum value " + value);
    }
}
