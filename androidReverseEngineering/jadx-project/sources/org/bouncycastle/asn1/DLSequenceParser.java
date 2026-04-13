package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes.dex */
public class DLSequenceParser implements ASN1SequenceParser {
    private ASN1StreamParser _parser;

    public DLSequenceParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return DLFactory.createSequence(this._parser.readVector());
    }

    @Override // org.bouncycastle.asn1.ASN1SequenceParser
    public ASN1Encodable readObject() {
        return this._parser.readObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new IllegalStateException(e2.getMessage());
        }
    }
}
