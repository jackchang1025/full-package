package org.bouncycastle.oer.its;

import org.bouncycastle.asn1.ASN1Object;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.ASN1String;
import org.bouncycastle.asn1.DERUTF8String;

/* loaded from: classes.dex */
public class Hostname extends ASN1Object {
    private final String hostName;

    public Hostname(String str) {
        this.hostName = str;
    }

    public static Hostname getInstance(Object obj) {
        if (obj instanceof Hostname) {
            return (Hostname) obj;
        }
        if (obj instanceof String) {
            return new Hostname((String) obj);
        }
        if (obj instanceof ASN1String) {
            return new Hostname(((ASN1String) obj).getString());
        }
        throw new IllegalArgumentException("hostname accepts Hostname, String and ASN1String");
    }

    @Override // org.bouncycastle.asn1.ASN1Object, org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        return new DERUTF8String(this.hostName);
    }
}
