package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERUniversalString extends ASN1UniversalString {
    public DERUniversalString(byte[] bArr) {
        this(bArr, true);
    }

    public static DERUniversalString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERUniversalString)) {
            return (DERUniversalString) obj;
        }
        if (obj instanceof ASN1UniversalString) {
            return new DERUniversalString(((ASN1UniversalString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERUniversalString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error getInstance: ")));
        }
    }

    public DERUniversalString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERUniversalString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERUniversalString)) ? getInstance((Object) object) : new DERUniversalString(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
