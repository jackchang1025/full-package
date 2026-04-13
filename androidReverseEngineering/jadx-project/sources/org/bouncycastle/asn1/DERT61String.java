package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERT61String extends ASN1T61String {
    public DERT61String(String str) {
        super(str);
    }

    public static DERT61String getInstance(Object obj) {
        if (obj == null || (obj instanceof DERT61String)) {
            return (DERT61String) obj;
        }
        if (obj instanceof ASN1T61String) {
            return new DERT61String(((ASN1T61String) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERT61String) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERT61String(byte[] bArr) {
        this(bArr, true);
    }

    public DERT61String(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERT61String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERT61String)) ? getInstance((Object) object) : new DERT61String(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
