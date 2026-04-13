package org.bouncycastle.asn1;

import java.io.IOException;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public abstract class ASN1ApplicationSpecific extends ASN1Primitive implements ASN1ApplicationSpecificParser {
    final ASN1TaggedObject taggedObject;

    public ASN1ApplicationSpecific(ASN1TaggedObject aSN1TaggedObject) {
        checkTagClass(aSN1TaggedObject.getTagClass());
        this.taggedObject = aSN1TaggedObject;
    }

    private static int checkTagClass(int i2) {
        if (64 == i2) {
            return i2;
        }
        throw new IllegalArgumentException();
    }

    public static ASN1ApplicationSpecific getInstance(Object obj) {
        if (obj == null || (obj instanceof ASN1ApplicationSpecific)) {
            return (ASN1ApplicationSpecific) obj;
        }
        if (!(obj instanceof byte[])) {
            throw new IllegalArgumentException(AbstractC0000a.m10f(obj, "unknown object in getInstance: "));
        }
        try {
            return getInstance(ASN1Primitive.fromByteArray((byte[]) obj));
        } catch (IOException e2) {
            throw new IllegalArgumentException(AbstractC0000a.m8d(e2, new StringBuilder("Failed to construct object from byte[]: ")));
        }
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean asn1Equals(ASN1Primitive aSN1Primitive) {
        ASN1TaggedObject aSN1TaggedObject;
        if (aSN1Primitive instanceof ASN1ApplicationSpecific) {
            aSN1TaggedObject = ((ASN1ApplicationSpecific) aSN1Primitive).taggedObject;
        } else {
            if (!(aSN1Primitive instanceof ASN1TaggedObject)) {
                return false;
            }
            aSN1TaggedObject = (ASN1TaggedObject) aSN1Primitive;
        }
        return this.taggedObject.equals((ASN1Primitive) aSN1TaggedObject);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public void encode(ASN1OutputStream aSN1OutputStream, boolean z2) {
        this.taggedObject.encode(aSN1OutputStream, z2);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public boolean encodeConstructed() {
        return this.taggedObject.encodeConstructed();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public int encodedLength(boolean z2) {
        return this.taggedObject.encodedLength(z2);
    }

    public int getApplicationTag() {
        return this.taggedObject.getTagNo();
    }

    public byte[] getContents() {
        return this.taggedObject.getContents();
    }

    public ASN1Primitive getEnclosedObject() {
        return this.taggedObject.getBaseObject().toASN1Primitive();
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public final ASN1Primitive getLoadedObject() {
        return this;
    }

    public ASN1Primitive getObject() {
        return getEnclosedObject();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable getObjectParser(int i2, boolean z2) {
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagClass() {
        return 64;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this.taggedObject.getTagNo();
    }

    public ASN1TaggedObject getTaggedObject() {
        return this.taggedObject;
    }

    public boolean hasApplicationTag(int i2) {
        return this.taggedObject.hasTag(64, i2);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag(int i2) {
        return false;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTag(int i2, int i3) {
        return this.taggedObject.hasTag(i2, i3);
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive, org.bouncycastle.asn1.ASN1Object
    public int hashCode() {
        return this.taggedObject.hashCode();
    }

    public boolean isConstructed() {
        return this.taggedObject.isConstructed();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z2, int i2) {
        return this.taggedObject.parseBaseUniversal(z2, i2);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() {
        return this.taggedObject.parseExplicitBaseObject();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() {
        return this.taggedObject.parseExplicitBaseTagged();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i2, int i3) {
        return this.taggedObject.parseImplicitBaseTagged(i2, i3);
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecificParser
    public ASN1Encodable readObject() {
        return parseExplicitBaseObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDERObject() {
        return new DERApplicationSpecific((ASN1TaggedObject) this.taggedObject.toDERObject());
    }

    @Override // org.bouncycastle.asn1.ASN1Primitive
    public ASN1Primitive toDLObject() {
        return new DLApplicationSpecific((ASN1TaggedObject) this.taggedObject.toDLObject());
    }

    public ASN1Primitive getObject(int i2) {
        return this.taggedObject.getBaseUniversal(false, i2);
    }
}
