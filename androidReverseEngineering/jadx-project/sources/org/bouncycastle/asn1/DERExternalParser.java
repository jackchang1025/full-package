package org.bouncycastle.asn1;

import java.io.IOException;

/* loaded from: classes.dex */
public class DERExternalParser implements ASN1ExternalParser {
    private ASN1StreamParser _parser;

    public DERExternalParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    public static DLExternal parse(ASN1StreamParser aSN1StreamParser) {
        try {
            return new DLExternal(aSN1StreamParser.readVector());
        } catch (IllegalArgumentException e2) {
            throw new ASN1Exception(e2.getMessage(), e2);
        }
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return parse(this._parser);
    }

    @Override // org.bouncycastle.asn1.ASN1ExternalParser
    public ASN1Encodable readObject() {
        return this._parser.readObject();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException("unable to get DER object", e2);
        } catch (IllegalArgumentException e3) {
            throw new ASN1ParsingException("unable to get DER object", e3);
        }
    }
}
