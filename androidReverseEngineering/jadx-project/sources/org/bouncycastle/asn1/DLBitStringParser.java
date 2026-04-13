package org.bouncycastle.asn1;

import java.io.IOException;
import java.io.InputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class DLBitStringParser implements ASN1BitStringParser {
    private int padBits = 0;
    private final DefiniteLengthInputStream stream;

    public DLBitStringParser(DefiniteLengthInputStream definiteLengthInputStream) {
        this.stream = definiteLengthInputStream;
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public InputStream getBitStream() {
        return getBitStream(false);
    }

    @Override // org.bouncycastle.asn1.InMemoryRepresentable
    public ASN1Primitive getLoadedObject() {
        return ASN1BitString.createPrimitive(this.stream.toByteArray());
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public InputStream getOctetStream() {
        return getBitStream(true);
    }

    @Override // org.bouncycastle.asn1.ASN1BitStringParser
    public int getPadBits() {
        return this.padBits;
    }

    @Override // org.bouncycastle.asn1.ASN1Encodable
    public ASN1Primitive toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e2) {
            throw new ASN1ParsingException(AbstractC0000a.m8d(e2, new StringBuilder("IOException converting stream to byte array: ")), e2);
        }
    }

    private InputStream getBitStream(boolean z2) {
        int remaining = this.stream.getRemaining();
        if (remaining < 1) {
            throw new IllegalStateException("content octets cannot be empty");
        }
        int read = this.stream.read();
        this.padBits = read;
        if (read > 0) {
            if (remaining < 2) {
                throw new IllegalStateException("zero length data with non-zero pad bits");
            }
            if (read > 7) {
                throw new IllegalStateException("pad bits cannot be greater than 7 or less than 0");
            }
            if (z2) {
                throw new IOException("expected octet-aligned bitstring, but found padBits: " + this.padBits);
            }
        }
        return this.stream;
    }
}
