package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERVideotexString extends ASN1VideotexString {
    public DERVideotexString(byte[] bArr) {
        this(bArr, true);
    }

    public static DERVideotexString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERVideotexString)) {
            return (DERVideotexString) obj;
        }
        if (obj instanceof ASN1VideotexString) {
            return new DERVideotexString(((ASN1VideotexString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERVideotexString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERVideotexString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERVideotexString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERVideotexString)) ? getInstance((Object) object) : new DERVideotexString(ASN1OctetString.getInstance(object).getOctets());
    }
}
