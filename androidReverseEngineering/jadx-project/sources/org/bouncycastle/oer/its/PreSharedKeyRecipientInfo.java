package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1OctetString;

/* loaded from: classes.dex */
public class PreSharedKeyRecipientInfo extends HashedId {
    public PreSharedKeyRecipientInfo(byte[] bArr) {
        super(bArr);
    }

    public static PreSharedKeyRecipientInfo getInstance(Object obj) {
        return obj instanceof PreSharedKeyRecipientInfo ? (PreSharedKeyRecipientInfo) obj : new PreSharedKeyRecipientInfo(ASN1OctetString.getInstance(obj).getOctets());
    }
}
