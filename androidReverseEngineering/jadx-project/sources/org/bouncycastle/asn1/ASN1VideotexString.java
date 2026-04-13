package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1VideotexString extends ASN1Primitive implements ASN1String {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1VideotexString.class, 21) { // from class: org.bouncycastle.asn1.ASN1VideotexString.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1VideotexString.createPrimitive(dEROctetString.getOctets());
        }
    };
    final byte[] contents;

    public ASN1VideotexString(byte[] bArr, boolean z2) {
        this.contents = z2 ? Arrays.clone(bArr) : bArr;
    }

    public static ASN1VideotexString createPrimitive(byte[] bArr) {
        return new DERVideotexString(bArr, false);
    }

    public static ASN1VideotexString getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1VideotexString)) {
            return (ASN1VideotexString) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1VideotexString) {
                return (ASN1VideotexString) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (ASN1VideotexString) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1VideotexString) {
            return Arrays.areEqual(this.contents, ((ASN1VideotexString) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 21, this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    public final byte[] getOctets() {
        return Arrays.clone(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public final String getString() {
        return Strings.fromByteArray(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public final int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    public static ASN1VideotexString getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1VideotexString) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}
