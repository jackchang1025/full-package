package org.bouncycastle.asn1;

import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DERPrintableString extends ASN1PrintableString {
    public DERPrintableString(String str) {
        this(str, false);
    }

    public static DERPrintableString getInstance(Object obj) {
        if (obj == null || (obj instanceof DERPrintableString)) {
            return (DERPrintableString) obj;
        }
        if (obj instanceof ASN1PrintableString) {
            return new DERPrintableString(((ASN1PrintableString) obj).contents, false);
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (DERPrintableString) ASN1Primitive.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    public DERPrintableString(String str, boolean z2) {
        super(str, z2);
    }

    public DERPrintableString(byte[] bArr, boolean z2) {
        super(bArr, z2);
    }

    public static DERPrintableString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        ASN1Primitive object = aSN1TaggedObject.getObject();
        return (z2 || (object instanceof DERPrintableString)) ? getInstance((Object) object) : new DERPrintableString(ASN1OctetString.getInstance(object).getOctets(), true);
    }
}
