package org.bouncycastle.asn1;

/* loaded from: classes.dex */
public class BERApplicationSpecificParser extends BERTaggedObjectParser implements ASN1ApplicationSpecificParser {
    public BERApplicationSpecificParser(int i2, ASN1StreamParser aSN1StreamParser) {
        super(64, i2, aSN1StreamParser);
    }

    @Override // org.bouncycastle.asn1.ASN1ApplicationSpecificParser
    public ASN1Encodable readObject() {
        return parseExplicitBaseObject();
    }
}
