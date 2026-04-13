package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERGraphicString extends ASN1GraphicString {
    public DERGraphicString(byte[] bArr) {
        this(bArr, true);
    }

    public static DERGraphicString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERGraphicString)) {
            return (DERGraphicString) obj;
        }
        if (obj instanceof ASN1GraphicString) {
            return new DERGraphicString(((ASN1GraphicString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERGraphicString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERGraphicString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERGraphicString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERGraphicString)) ? getInstance((Object) object) : new DERGraphicString(ASN1OctetString.getInstance(object).getOctets());
    }
}
