package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERGeneralString extends ASN1GeneralString {
    public DERGeneralString(String str) {
        super(str);
    }

    public static DERGeneralString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERGeneralString)) {
            return (DERGeneralString) obj;
        }
        if (obj instanceof ASN1GeneralString) {
            return new DERGeneralString(((ASN1GeneralString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERGeneralString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERGeneralString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERGeneralString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERGeneralString)) ? getInstance((Object) object) : new DERGeneralString(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
