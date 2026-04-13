package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes.dex */
class DLTaggedObjectParser extends BERTaggedObjectParser {
    private final boolean _constructed;

    public DLTaggedObjectParser(int i2, int i3, boolean z2, ASN1StreamParser aSN1StreamParser) {
        super(i2, i3, aSN1StreamParser);
        this._constructed = z2;
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser, org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return this._parser.loadTaggedDL(this._tagClass, this._tagNo, this._constructed);
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser
    public boolean isConstructed() {
        return this._constructed;
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser, org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseBaseUniversal(boolean z2, int i2) {
        if (!z2) {
            return this._constructed ? this._parser.parseImplicitConstructedDL(i2) : this._parser.parseImplicitPrimitive(i2);
        }
        if (this._constructed) {
            return this._parser.parseObject(i2);
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser, org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1Encodable parseExplicitBaseObject() {
        if (this._constructed) {
            return this._parser.readObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser, org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseExplicitBaseTagged() {
        if (this._constructed) {
            return this._parser.parseTaggedObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // org.bouncycastle.asn1.BERTaggedObjectParser, org.bouncycastle.asn1.ASN1TaggedObjectParser
    public ASN1TaggedObjectParser parseImplicitBaseTagged(int i2, int i3) {
        return 64 == i2 ? (DLApplicationSpecific) this._parser.loadTaggedDL(i2, i3, this._constructed) : new DLTaggedObjectParser(i2, i3, this._constructed, this._parser);
    }
}
