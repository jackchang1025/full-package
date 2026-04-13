package org.bouncycastle.asn1.crmf;

import org.bouncycastle.asn1.ASN1Integer;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class SubsequentMessage extends ASN1Integer {
    public static final SubsequentMessage encrCert = new SubsequentMessage(0);
    public static final SubsequentMessage challengeResp = new SubsequentMessage(1);

    private SubsequentMessage(int i2) {
        super(i2);
    }

    public static SubsequentMessage valueOf(int i2) {
        if (i2 == 0) {
            return encrCert;
        }
        if (i2 == 1) {
            return challengeResp;
        }
        throw new IllegalArgumentException(AbstractC0000a.m11g("unknown value: ", i2));
    }
}
