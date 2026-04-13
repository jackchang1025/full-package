package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes.dex */
public class BERTaggedObjectParser implements ASN1TaggedObjectParser {
    final ASN1StreamParser _parser;
    final int _tagClass;
    final int _tagNo;

    public BERTaggedObjectParser(int i2, int i3, ASN1StreamParser aSN1StreamParser) {
        this._tagClass = i2;
        this._tagNo = i3;
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return this._parser.loadTaggedIL(this._tagClass, this._tagNo);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable getObjectParser(int i2, boolean z2) {
        if (128 == getTagClass()) {
            return parseBaseUniversal(z2, i2);
        }
        throw new ASN1Exception("this method only valid for CONTEXT_SPECIFIC tags");
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagClass() {
        return this._tagClass;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public int getTagNo() {
        return this._tagNo;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasContextTag(int i2) {
        return this._tagClass == 128 && this._tagNo == i2;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public boolean hasTag(int i2, int i3) {
        return this._tagClass == i2 && this._tagNo == i3;
    }

    public boolean isConstructed() {
        return true;
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z2, int i2) {
        return z2 ? this._parser.parseObject(i2) : this._parser.parseImplicitConstructedIL(i2);
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() {
        return this._parser.readObject();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() {
        return this._parser.parseTaggedObject();
    }

    @Override // org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i2, int i3) {
        return 64 == i2 ? new BERApplicationSpecificParser(i3, this._parser) : new BERTaggedObjectParser(i2, i3, this._parser);
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(e2.getMessage());
        }
    }
}
