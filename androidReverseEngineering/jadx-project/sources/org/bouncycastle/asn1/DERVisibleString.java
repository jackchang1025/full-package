package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERVisibleString extends ASN1VisibleString {
    public DERVisibleString(String str) {
        super(str);
    }

    public static DERVisibleString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERVisibleString)) {
            return (DERVisibleString) obj;
        }
        if (obj instanceof ASN1VisibleString) {
            return new DERVisibleString(((ASN1VisibleString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERVisibleString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERVisibleString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERVisibleString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERVisibleString)) ? getInstance((Object) object) : new DERVisibleString(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
