package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1StreamParser {
    private final InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public ASN1StreamParser(InputStream inputStream) {
        this(inputStream, StreamUtil.findLimit(inputStream));
    }

    private void set00Check(boolean z2) {
        InputStream inputStream = this._in;
        if (inputStream instanceof IndefiniteLengthInputStream) {
            ((IndefiniteLengthInputStream) inputStream).setEofOn00(z2);
        }
    }

    public ASN1Encodable implParseObject(int i2) {
        set00Check(false);
        int readTagNumber = ASN1InputStream.readTagNumber(this._in, i2);
        int readLength = ASN1InputStream.readLength(this._in, this._limit, readTagNumber == 3 || readTagNumber == 4 || readTagNumber == 16 || readTagNumber == 17 || readTagNumber == 8);
        if (readLength < 0) {
            if ((i2 & 32) == 0) {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
            ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this._in, this._limit), this._limit, this.tmpBuffers);
            int i3 = i2 & 192;
            return i3 != 0 ? 64 == i3 ? new BERApplicationSpecificParser(readTagNumber, aSN1StreamParser) : new BERTaggedObjectParser(i3, readTagNumber, aSN1StreamParser) : aSN1StreamParser.parseImplicitConstructedIL(readTagNumber);
        }
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this._in, readLength, this._limit);
        if ((i2 & BERTags.FLAGS) == 0) {
            return parseImplicitPrimitive(readTagNumber, definiteLengthInputStream);
        }
        ASN1StreamParser aSN1StreamParser2 = new ASN1StreamParser(definiteLengthInputStream, definiteLengthInputStream.getLimit(), this.tmpBuffers);
        int i4 = i2 & 192;
        if (i4 == 0) {
            return aSN1StreamParser2.parseImplicitConstructedDL(readTagNumber);
        }
        boolean z2 = (i2 & 32) != 0;
        return 64 == i4 ? (DLApplicationSpecific) aSN1StreamParser2.loadTaggedDL(i4, readTagNumber, z2) : new DLTaggedObjectParser(i4, readTagNumber, z2, aSN1StreamParser2);
    }

    public ASN1Primitive loadTaggedDL(int i2, int i3, boolean z2) {
        return !z2 ? ASN1TaggedObject.createPrimitive(i2, i3, ((DefiniteLengthInputStream) this._in).toByteArray()) : ASN1TaggedObject.createConstructedDL(i2, i3, readVector());
    }

    public ASN1Primitive loadTaggedIL(int i2, int i3) {
        return ASN1TaggedObject.createConstructedIL(i2, i3, readVector());
    }

    public ASN1Encodable parseImplicitConstructedDL(int i2) {
        if (i2 == 3) {
            return new BERBitStringParser(this);
        }
        if (i2 == 4) {
            return new BEROctetStringParser(this);
        }
        if (i2 == 8) {
            return new DERExternalParser(this);
        }
        if (i2 == 16) {
            return new DLSequenceParser(this);
        }
        if (i2 == 17) {
            return new DLSetParser(this);
        }
        throw new ASN1Exception("unknown DL object encountered: 0x" + Integer.toHexString(i2));
    }

    public ASN1Encodable parseImplicitConstructedIL(int i2) {
        if (i2 == 3) {
            return new BERBitStringParser(this);
        }
        if (i2 == 4) {
            return new BEROctetStringParser(this);
        }
        if (i2 == 8) {
            return new DERExternalParser(this);
        }
        if (i2 == 16) {
            return new BERSequenceParser(this);
        }
        if (i2 == 17) {
            return new BERSetParser(this);
        }
        throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i2));
    }

    public ASN1Encodable parseImplicitPrimitive(int i2) {
        return parseImplicitPrimitive(i2, (DefiniteLengthInputStream) this._in);
    }

    public ASN1Encodable parseObject(int i2) {
        if (i2 < 0 || i2 > 30) {
            throw new IllegalArgumentException(AbstractC0000a.m11g("invalid universal tag number: ", i2));
        }
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        if ((read & (-33)) == i2) {
            return implParseObject(read);
        }
        throw new IOException(AbstractC0000a.m11g("unexpected identifier encountered: ", read));
    }

    public ASN1TaggedObjectParser parseTaggedObject() {
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        if ((read & 192) != 0) {
            return (ASN1TaggedObjectParser) implParseObject(read);
        }
        throw new ASN1Exception("no tagged object found");
    }

    public ASN1Encodable readObject() {
        int read = this._in.read();
        if (read < 0) {
            return null;
        }
        return implParseObject(read);
    }

    public ASN1EncodableVector readVector() {
        int read = this._in.read();
        if (read < 0) {
            return new ASN1EncodableVector(0);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        do {
            ASN1Encodable implParseObject = implParseObject(read);
            aSN1EncodableVector.add(implParseObject instanceof InMemoryRepresentable ? ((InMemoryRepresentable) implParseObject).getLoadedObject() : implParseObject.toASN1Primitive());
            read = this._in.read();
        } while (read >= 0);
        return aSN1EncodableVector;
    }

    public ASN1StreamParser(InputStream inputStream, int i2) {
        this(inputStream, i2, new byte[11][]);
    }

    public ASN1Encodable parseImplicitPrimitive(int i2, DefiniteLengthInputStream definiteLengthInputStream) {
        if (i2 == 3) {
            return new DLBitStringParser(definiteLengthInputStream);
        }
        if (i2 == 4) {
            return new DEROctetStringParser(definiteLengthInputStream);
        }
        if (i2 == 8) {
            throw new ASN1Exception("externals must use constructed encoding (see X.690 8.18)");
        }
        if (i2 == 16) {
            throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
        }
        if (i2 == 17) {
            throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
        }
        try {
            return ASN1InputStream.createPrimitiveDERObject(i2, definiteLengthInputStream, this.tmpBuffers);
        } catch (IllegalArgumentException e2) {
            throw new ASN1Exception("corrupted stream detected", e2);
        }
    }

    public ASN1StreamParser(InputStream inputStream, int i2, byte[][] bArr) {
        this._in = inputStream;
        this._limit = i2;
        this.tmpBuffers = bArr;
    }

    public ASN1StreamParser(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }
}
