package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BERBitStringParser implements ASN1BitStringParser {
    private ConstructedBitStream _bitStream;
    private ASN1StreamParser _parser;

    public BERBitStringParser(ASN1StreamParser aSN1StreamParser) {
        this._parser = aSN1StreamParser;
    }

    public static BERBitString parse(ASN1StreamParser aSN1StreamParser) {
        ConstructedBitStream constructedBitStream = new ConstructedBitStream(aSN1StreamParser, false);
        return new BERBitString(Streams.readAll(constructedBitStream), constructedBitStream.getPadBits());
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public InputStream getBitStream() {
        ConstructedBitStream constructedBitStream = new ConstructedBitStream(this._parser, false);
        this._bitStream = constructedBitStream;
        return constructedBitStream;
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return parse(this._parser);
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public InputStream getOctetStream() {
        ConstructedBitStream constructedBitStream = new ConstructedBitStream(this._parser, true);
        this._bitStream = constructedBitStream;
        return constructedBitStream;
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public int getPadBits() {
        return this._bitStream.getPadBits();
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(AbstractC0000a.m8d(e2, new StringBuilder("IOException converting stream to byte array: ")), e2);
        }
    }
}
