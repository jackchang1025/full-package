package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERUTF8String extends ASN1UTF8String {
    public DERUTF8String(String str) {
        super(str);
    }

    public static DERUTF8String getInstance(Object obj) {
        if (obj == null || (obj instanceof DERUTF8String)) {
            return (DERUTF8String) obj;
        }
        if (obj instanceof ASN1UTF8String) {
            return new DERUTF8String(((ASN1UTF8String) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERUTF8String) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERUTF8String(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERUTF8String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERUTF8String)) ? getInstance((Object) object) : new DERUTF8String(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
