package org.bouncycastle.asn1;

import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.eac.CertificateBody;
import org.bouncycastle.util.io.Streams;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class ASN1InputStream extends FilterInputStream implements BERTags {
    private final boolean lazyEvaluate;
    private final int limit;
    private final byte[][] tmpBuffers;

    public ASN1InputStream(InputStream inputStream) {
        this(inputStream, StreamUtil.findLimit(inputStream));
    }

    public static ASN1Primitive createPrimitiveDERObject(int i2, DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) {
        switch (i2) {
            case 1:
                return ASN1Boolean.createPrimitive(getBuffer(definiteLengthInputStream, bArr));
            case 2:
                return ASN1Integer.createPrimitive(definiteLengthInputStream.toByteArray());
            case 3:
                return ASN1BitString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 4:
                return ASN1OctetString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 5:
                return ASN1Null.createPrimitive(definiteLengthInputStream.toByteArray());
            case 6:
                return ASN1ObjectIdentifier.createPrimitive(getBuffer(definiteLengthInputStream, bArr), true);
            case 7:
                return ASN1ObjectDescriptor.createPrimitive(definiteLengthInputStream.toByteArray());
            case 8:
            case 9:
            case 11:
            case 14:
            case 15:
            case 16:
            case 17:
            case 29:
            default:
                throw new IOException(AbstractC0000a.m12h("unknown tag ", i2, " encountered"));
            case 10:
                return ASN1Enumerated.createPrimitive(getBuffer(definiteLengthInputStream, bArr), true);
            case 12:
                return ASN1UTF8String.createPrimitive(definiteLengthInputStream.toByteArray());
            case 13:
                return ASN1RelativeOID.createPrimitive(definiteLengthInputStream.toByteArray(), false);
            case 18:
                return ASN1NumericString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 19:
                return ASN1PrintableString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 20:
                return ASN1T61String.createPrimitive(definiteLengthInputStream.toByteArray());
            case 21:
                return ASN1VideotexString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 22:
                return ASN1IA5String.createPrimitive(definiteLengthInputStream.toByteArray());
            case 23:
                return ASN1UTCTime.createPrimitive(definiteLengthInputStream.toByteArray());
            case 24:
                return ASN1GeneralizedTime.createPrimitive(definiteLengthInputStream.toByteArray());
            case 25:
                return ASN1GraphicString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 26:
                return ASN1VisibleString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 27:
                return ASN1GeneralString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 28:
                return ASN1UniversalString.createPrimitive(definiteLengthInputStream.toByteArray());
            case 30:
                return ASN1BMPString.createPrimitive(getBMPCharBuffer(definiteLengthInputStream));
        }
    }

    private static char[] getBMPCharBuffer(DefiniteLengthInputStream definiteLengthInputStream) {
        int i2;
        int remaining = definiteLengthInputStream.getRemaining();
        if ((remaining & 1) != 0) {
            throw new IOException("malformed BMPString encoding encountered");
        }
        int i3 = remaining / 2;
        char[] cArr = new char[i3];
        byte[] bArr = new byte[8];
        int i4 = 0;
        int i5 = 0;
        while (remaining >= 8) {
            if (Streams.readFully(definiteLengthInputStream, bArr, 0, 8) != 8) {
                throw new EOFException("EOF encountered in middle of BMPString");
            }
            cArr[i5] = (char) ((bArr[0] << 8) | (bArr[1] & 255));
            cArr[i5 + 1] = (char) ((bArr[2] << 8) | (bArr[3] & 255));
            cArr[i5 + 2] = (char) ((bArr[4] << 8) | (bArr[5] & 255));
            cArr[i5 + 3] = (char) ((bArr[6] << 8) | (bArr[7] & 255));
            i5 += 4;
            remaining -= 8;
        }
        if (remaining > 0) {
            if (Streams.readFully(definiteLengthInputStream, bArr, 0, remaining) != remaining) {
                throw new EOFException("EOF encountered in middle of BMPString");
            }
            while (true) {
                int i6 = i4 + 1;
                int i7 = i6 + 1;
                i2 = i5 + 1;
                cArr[i5] = (char) ((bArr[i4] << 8) | (bArr[i6] & 255));
                if (i7 >= remaining) {
                    break;
                }
                i4 = i7;
                i5 = i2;
            }
            i5 = i2;
        }
        if (definiteLengthInputStream.getRemaining() == 0 && i3 == i5) {
            return cArr;
        }
        throw new IllegalStateException();
    }

    private static byte[] getBuffer(DefiniteLengthInputStream definiteLengthInputStream, byte[][] bArr) {
        int remaining = definiteLengthInputStream.getRemaining();
        if (remaining >= bArr.length) {
            return definiteLengthInputStream.toByteArray();
        }
        byte[] bArr2 = bArr[remaining];
        if (bArr2 == null) {
            bArr2 = new byte[remaining];
            bArr[remaining] = bArr2;
        }
        definiteLengthInputStream.readAllIntoByteArray(bArr2);
        return bArr2;
    }

    public static int readTagNumber(InputStream inputStream, int i2) {
        int i3 = i2 & 31;
        if (i3 != 31) {
            return i3;
        }
        int read = inputStream.read();
        if (read < 31) {
            if (read < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
            throw new IOException("corrupted stream - high tag number < 31 found");
        }
        if ((read & CertificateBody.profileType) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i4 = 0;
        while ((read & 128) != 0) {
            if ((i4 >>> 24) != 0) {
                throw new IOException("Tag number more than 31 bits");
            }
            i4 = ((read & CertificateBody.profileType) | i4) << 7;
            read = inputStream.read();
            if (read < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
        }
        return i4 | (read & CertificateBody.profileType);
    }

    public ASN1BitString buildConstructedBitString(ASN1EncodableVector aSN1EncodableVector) {
        int size = aSN1EncodableVector.size();
        ASN1BitString[] aSN1BitStringArr = new ASN1BitString[size];
        for (int i2 = 0; i2 != size; i2++) {
            ASN1Encodable aSN1Encodable = aSN1EncodableVector.get(i2);
            if (!(aSN1Encodable instanceof ASN1BitString)) {
                throw new ASN1Exception("unknown object encountered in constructed BIT STRING: " + aSN1Encodable.getClass());
            }
            aSN1BitStringArr[i2] = (ASN1BitString) aSN1Encodable;
        }
        return new BERBitString(aSN1BitStringArr);
    }

    public ASN1OctetString buildConstructedOctetString(ASN1EncodableVector aSN1EncodableVector) {
        int size = aSN1EncodableVector.size();
        ASN1OctetString[] aSN1OctetStringArr = new ASN1OctetString[size];
        for (int i2 = 0; i2 != size; i2++) {
            ASN1Encodable aSN1Encodable = aSN1EncodableVector.get(i2);
            if (!(aSN1Encodable instanceof ASN1OctetString)) {
                throw new ASN1Exception("unknown object encountered in constructed OCTET STRING: " + aSN1Encodable.getClass());
            }
            aSN1OctetStringArr[i2] = (ASN1OctetString) aSN1Encodable;
        }
        return new BEROctetString(aSN1OctetStringArr);
    }

    public ASN1Primitive buildObject(int i2, int i3, int i4) {
        DefiniteLengthInputStream definiteLengthInputStream = new DefiniteLengthInputStream(this, i4, this.limit);
        if ((i2 & BERTags.FLAGS) == 0) {
            return createPrimitiveDERObject(i3, definiteLengthInputStream, this.tmpBuffers);
        }
        int i5 = i2 & 192;
        if (i5 != 0) {
            return readTaggedObjectDL(i5, i3, (i2 & 32) != 0, definiteLengthInputStream);
        }
        if (i3 == 3) {
            return buildConstructedBitString(readVector(definiteLengthInputStream));
        }
        if (i3 == 4) {
            return buildConstructedOctetString(readVector(definiteLengthInputStream));
        }
        if (i3 == 8) {
            return DLFactory.createSequence(readVector(definiteLengthInputStream)).toASN1External();
        }
        if (i3 == 16) {
            return definiteLengthInputStream.getRemaining() < 1 ? DLFactory.EMPTY_SEQUENCE : this.lazyEvaluate ? new LazyEncodedSequence(definiteLengthInputStream.toByteArray()) : DLFactory.createSequence(readVector(definiteLengthInputStream));
        }
        if (i3 == 17) {
            return DLFactory.createSet(readVector(definiteLengthInputStream));
        }
        throw new IOException(AbstractC0000a.m12h("unknown tag ", i3, " encountered"));
    }

    public int getLimit() {
        return this.limit;
    }

    public void readFully(byte[] bArr) {
        if (Streams.readFully(this, bArr, 0, bArr.length) != bArr.length) {
            throw new EOFException("EOF encountered in middle of object");
        }
    }

    public int readLength() {
        return readLength(this, this.limit, false);
    }

    public ASN1Primitive readObject() {
        int read = read();
        if (read <= 0) {
            if (read != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int readTagNumber = readTagNumber(this, read);
        int readLength = readLength();
        if (readLength >= 0) {
            try {
                return buildObject(read, readTagNumber, readLength);
            } catch (IllegalArgumentException e2) {
                throw new ASN1Exception("corrupted stream detected", e2);
            }
        }
        if ((read & 32) == 0) {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        ASN1StreamParser aSN1StreamParser = new ASN1StreamParser(new IndefiniteLengthInputStream(this, this.limit), this.limit, this.tmpBuffers);
        int i2 = read & 192;
        if (i2 != 0) {
            return aSN1StreamParser.loadTaggedIL(i2, readTagNumber);
        }
        if (readTagNumber == 3) {
            return BERBitStringParser.parse(aSN1StreamParser);
        }
        if (readTagNumber == 4) {
            return BEROctetStringParser.parse(aSN1StreamParser);
        }
        if (readTagNumber == 8) {
            return DERExternalParser.parse(aSN1StreamParser);
        }
        if (readTagNumber == 16) {
            return BERSequenceParser.parse(aSN1StreamParser);
        }
        if (readTagNumber == 17) {
            return BERSetParser.parse(aSN1StreamParser);
        }
        throw new IOException("unknown BER object encountered");
    }

    public ASN1Primitive readTaggedObjectDL(int i2, int i3, boolean z2, DefiniteLengthInputStream definiteLengthInputStream) {
        return !z2 ? ASN1TaggedObject.createPrimitive(i2, i3, definiteLengthInputStream.toByteArray()) : ASN1TaggedObject.createConstructedDL(i2, i3, readVector(definiteLengthInputStream));
    }

    public ASN1EncodableVector readVector() {
        ASN1Primitive readObject = readObject();
        if (readObject == null) {
            return new ASN1EncodableVector(0);
        }
        ASN1EncodableVector aSN1EncodableVector = new ASN1EncodableVector();
        do {
            aSN1EncodableVector.add(readObject);
            readObject = readObject();
        } while (readObject != null);
        return aSN1EncodableVector;
    }

    public ASN1InputStream(InputStream inputStream, int i2) {
        this(inputStream, i2, false);
    }

    public static int readLength(InputStream inputStream, int i2, boolean z2) {
        int read = inputStream.read();
        if ((read >>> 7) == 0) {
            return read;
        }
        if (128 == read) {
            return -1;
        }
        if (read < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (255 == read) {
            throw new IOException("invalid long form definite-length 0xFF");
        }
        int i3 = read & CertificateBody.profileType;
        int i4 = 0;
        int i5 = 0;
        do {
            int read2 = inputStream.read();
            if (read2 < 0) {
                throw new EOFException("EOF found reading length");
            }
            if ((i4 >>> 23) != 0) {
                throw new IOException("long form definite-length more than 31 bits");
            }
            i4 = (i4 << 8) + read2;
            i5++;
        } while (i5 < i3);
        if (i4 < i2 || z2) {
            return i4;
        }
        throw new IOException("corrupted stream - out of bounds length found: " + i4 + " >= " + i2);
    }

    public ASN1EncodableVector readVector(DefiniteLengthInputStream definiteLengthInputStream) {
        int remaining = definiteLengthInputStream.getRemaining();
        return remaining < 1 ? new ASN1EncodableVector(0) : new ASN1InputStream(definiteLengthInputStream, remaining, this.lazyEvaluate, this.tmpBuffers).readVector();
    }

    public ASN1InputStream(InputStream inputStream, int i2, boolean z2) {
        this(inputStream, i2, z2, new byte[11][]);
    }

    private ASN1InputStream(InputStream inputStream, int i2, boolean z2, byte[][] bArr) {
        super(inputStream);
        this.limit = i2;
        this.lazyEvaluate = z2;
        this.tmpBuffers = bArr;
    }

    public ASN1InputStream(InputStream inputStream, boolean z2) {
        this(inputStream, StreamUtil.findLimit(inputStream), z2);
    }

    public ASN1InputStream(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public ASN1InputStream(byte[] bArr, boolean z2) {
        this(new ByteArrayInputStream(bArr), bArr.length, z2);
    }
}
