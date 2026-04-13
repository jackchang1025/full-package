package org.bouncycastle.asn1;

import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1UTF8String extends ASN1Primitive implements ASN1String {
    static final ASN1UniversalType TYPE = new ASN1UniversalType(ASN1UTF8String.class, 12) { // from class: org.bouncycastle.asn1.ASN1UTF8String.1
        @Override // org.bouncycastle.asn1.ASN1UniversalType
        public ASN1Primitive fromImplicitPrimitive(DEROctetString dEROctetString) {
            return ASN1UTF8String.createPrimitive(dEROctetString.getOctets());
        }
    };
    final byte[] contents;

    public ASN1UTF8String(String str) {
        this(Strings.toUTF8ByteArray(str), false);
    }

    public static ASN1UTF8String createPrimitive(byte[] bArr) {
        return new DERUTF8String(bArr, false);
    }

    public static ASN1UTF8String getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1UTF8String)) {
            return (ASN1UTF8String) obj;
        }
        if (obj instanceof ASN1Encodable) {
            ASN1Primitive aSN1Primitive = ((ASN1Encodable) obj).toASN1Primitive();
            if (aSN1Primitive instanceof ASN1UTF8String) {
                return (ASN1UTF8String) aSN1Primitive;
            }
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "illegal object in getInstance: "));
        }
        try {
            return (ASN1UTF8String) TYPE.fromByteArray((byte[]) obj);
        } catch (Exception e2) {
            throw new IllegalArgumentException(AbstractC0000a.m29y(e2, new StringBuilder("encoding error in getInstance: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        if (aSN1Primitive instanceof ASN1UTF8String) {
            return Arrays.areEqual(this.contents, ((ASN1UTF8String) aSN1Primitive).contents);
        }
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        aSN1OutputStream.writeEncodingDL(z2, 12, this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final boolean encodeConstructed() {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public final int encodedLength(boolean z2) {
        return ASN1OutputStream.getLengthOfEncodingDL(z2, this.contents.length);
    }

    @Override // org.bouncycastle.asn1.ASN1String
    public final String getString() {
        return Strings.fromUTF8ByteArray(this.contents);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public final int hashCode() {
        return Arrays.hashCode(this.contents);
    }

    public String toString() {
        return getString();
    }

    public ASN1UTF8String(byte[] bArr, boolean z2) {
        this.contents = z2 ? Arrays.clone(bArr) : bArr;
    }

    public static ASN1UTF8String getInstance(ASN1TaggedObject aSN1TaggedObject, boolean z2) {
        return (ASN1UTF8String) TYPE.getContextInstance(aSN1TaggedObject, z2);
    }
}
