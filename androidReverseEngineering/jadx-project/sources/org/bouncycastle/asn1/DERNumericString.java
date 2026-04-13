package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERNumericString extends ASN1NumericString {
    public DERNumericString(String str) {
        this(str, false);
    }

    public static DERNumericString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERNumericString)) {
            return (DERNumericString) obj;
        }
        if (obj instanceof ASN1NumericString) {
            return new DERNumericString(((ASN1NumericString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERNumericString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERNumericString(String str, boolean z2) {
        super(str, z2);
    }

    public DERNumericString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERNumericString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERNumericString)) ? getInstance((Object) object) : new DERNumericString(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
