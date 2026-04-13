package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DEROctetStringParser implements ASN1OctetStringParser {
    private DefiniteLengthInputStream stream;

    public DEROctetStringParser(DefiniteLengthInputStream definiteLengthInputStream) {
        this.stream = definiteLengthInputStream;
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return new DEROctetString(this.stream.toByteArray());
    }

    @Override // org.bouncycastle.asn1.ASN1OctetStringParser
    public InputStream getOctetStream() {
        return this.stream;
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
