package p000;

import io.socket.engineio.parser.Base64;
import java.io.ByteArrayInputStream;
import java.io.EOFException;
import java.io.FilterInputStream;
import java.io.IOException;
import java.io.InputStream;
import okhttp3.internal.p032ws.WebSocketProtocol;
import org.bouncycastle.asn1.ASN1Exception;
import org.conscrypt.FileClientSessionCache;

/* renamed from: b9 */
/* loaded from: classes2.dex */
public class C0126b9 extends FilterInputStream {
    private final boolean lazyEvaluate;
    private final int limit;
    private final byte[][] tmpBuffers;

    public C0126b9(InputStream inputStream) {
        this(inputStream, h21.findLimit(inputStream));
    }

    public static AbstractC0164c9 createPrimitiveDERObject(int i, C1190rr c1190rr, byte[][] bArr) throws IOException {
        switch (i) {
            case 1:
                return C0009a8.createPrimitive(getBuffer(c1190rr, bArr));
            case 2:
                return C0155c0.createPrimitive(c1190rr.toByteArray());
            case 3:
                return AbstractC0007a6.createPrimitive(c1190rr.toByteArray());
            case 4:
                return AbstractC0161c6.createPrimitive(c1190rr.toByteArray());
            case 5:
                return AbstractC0156c1.createPrimitive(c1190rr.toByteArray());
            case 6:
                return C0160c5.createPrimitive(getBuffer(c1190rr, bArr), true);
            case 7:
                return C0159c4.createPrimitive(c1190rr.toByteArray());
            case 8:
            case 9:
            case oe0.DEFAULT_M /* 11 */:
            case 14:
            case WebSocketProtocol.B0_MASK_OPCODE /* 15 */:
            case 16:
            case 17:
            case 29:
            default:
                throw new IOException(AbstractC0003a2.m30b1("unknown tag ", i, " encountered"));
            case 10:
                return C0119b2.createPrimitive(getBuffer(c1190rr, bArr), true);
            case FileClientSessionCache.MAX_SIZE /* 12 */:
                return AbstractC0443e4.createPrimitive(c1190rr.toByteArray());
            case 13:
                return C0399d1.createPrimitive(c1190rr.toByteArray(), false);
            case 18:
                return AbstractC0157c2.createPrimitive(c1190rr.toByteArray());
            case Base64.Encoder.LINE_GROUPS /* 19 */:
                return AbstractC0398d0.createPrimitive(c1190rr.toByteArray());
            case 20:
                return AbstractC0406d8.createPrimitive(c1190rr.toByteArray());
            case 21:
                return AbstractC0448e9.createPrimitive(c1190rr.toByteArray());
            case 22:
                return AbstractC0125b8.createPrimitive(c1190rr.toByteArray());
            case 23:
                return C0442e3.createPrimitive(c1190rr.toByteArray());
            case 24:
                return C0123b6.createPrimitive(c1190rr.toByteArray());
            case 25:
                return AbstractC0124b7.createPrimitive(c1190rr.toByteArray());
            case 26:
                return AbstractC0476f0.createPrimitive(c1190rr.toByteArray());
            case 27:
                return AbstractC0122b5.createPrimitive(c1190rr.toByteArray());
            case 28:
                return AbstractC0444e5.createPrimitive(c1190rr.toByteArray());
            case 30:
                return AbstractC0006a5.createPrimitive(getBMPCharBuffer(c1190rr));
        }
    }

    private static char[] getBMPCharBuffer(C1190rr c1190rr) throws IOException {
        int remaining = c1190rr.getRemaining();
        if ((remaining & 1) != 0) {
            throw new IOException("malformed BMPString encoding encountered");
        }
        int i = remaining / 2;
        char[] cArr = new char[i];
        byte[] bArr = new byte[8];
        int i2 = 0;
        int i3 = 0;
        while (remaining >= 8) {
            if (i21.readFully(c1190rr, bArr, 0, 8) != 8) {
                throw new EOFException("EOF encountered in middle of BMPString");
            }
            cArr[i3] = (char) ((bArr[0] << 8) | (bArr[1] & 255));
            cArr[i3 + 1] = (char) ((bArr[2] << 8) | (bArr[3] & 255));
            cArr[i3 + 2] = (char) ((bArr[4] << 8) | (bArr[5] & 255));
            cArr[i3 + 3] = (char) ((bArr[6] << 8) | (bArr[7] & 255));
            i3 += 4;
            remaining -= 8;
        }
        if (remaining > 0) {
            if (i21.readFully(c1190rr, bArr, 0, remaining) != remaining) {
                throw new EOFException("EOF encountered in middle of BMPString");
            }
            do {
                int i4 = i2 + 1;
                int i5 = bArr[i2] << 8;
                i2 += 2;
                cArr[i3] = (char) ((bArr[i4] & 255) | i5);
                i3++;
            } while (i2 < remaining);
        }
        if (c1190rr.getRemaining() == 0 && i == i3) {
            return cArr;
        }
        throw new IllegalStateException();
    }

    private static byte[] getBuffer(C1190rr c1190rr, byte[][] bArr) throws IOException {
        int remaining = c1190rr.getRemaining();
        if (remaining >= bArr.length) {
            return c1190rr.toByteArray();
        }
        byte[] bArr2 = bArr[remaining];
        if (bArr2 == null) {
            bArr2 = new byte[remaining];
            bArr[remaining] = bArr2;
        }
        c1190rr.readAllIntoByteArray(bArr2);
        return bArr2;
    }

    public static int readTagNumber(InputStream inputStream, int i) throws IOException {
        int i2 = i & 31;
        if (i2 != 31) {
            return i2;
        }
        int i3 = inputStream.read();
        if (i3 < 31) {
            if (i3 < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
            throw new IOException("corrupted stream - high tag number < 31 found");
        }
        if ((i3 & 127) == 0) {
            throw new IOException("corrupted stream - invalid high tag number found");
        }
        int i4 = 0;
        while ((i3 & 128) != 0) {
            if ((i4 >>> 24) != 0) {
                throw new IOException("Tag number more than 31 bits");
            }
            i4 = ((i3 & 127) | i4) << 7;
            i3 = inputStream.read();
            if (i3 < 0) {
                throw new EOFException("EOF found inside tag value.");
            }
        }
        return (i3 & 127) | i4;
    }

    public AbstractC0007a6 buildConstructedBitString(C0118b1 c0118b1) throws IOException {
        int size = c0118b1.size();
        AbstractC0007a6[] abstractC0007a6Arr = new AbstractC0007a6[size];
        for (int i = 0; i != size; i++) {
            InterfaceC0117b0 interfaceC0117b0 = c0118b1.get(i);
            if (!(interfaceC0117b0 instanceof AbstractC0007a6)) {
                throw new ASN1Exception("unknown object encountered in constructed BIT STRING: " + interfaceC0117b0.getClass());
            }
            abstractC0007a6Arr[i] = (AbstractC0007a6) interfaceC0117b0;
        }
        return new C0171cg(abstractC0007a6Arr);
    }

    public AbstractC0161c6 buildConstructedOctetString(C0118b1 c0118b1) throws IOException {
        int size = c0118b1.size();
        AbstractC0161c6[] abstractC0161c6Arr = new AbstractC0161c6[size];
        for (int i = 0; i != size; i++) {
            InterfaceC0117b0 interfaceC0117b0 = c0118b1.get(i);
            if (!(interfaceC0117b0 instanceof AbstractC0161c6)) {
                throw new ASN1Exception("unknown object encountered in constructed OCTET STRING: " + interfaceC0117b0.getClass());
            }
            abstractC0161c6Arr[i] = (AbstractC0161c6) interfaceC0117b0;
        }
        return new C0174cj(abstractC0161c6Arr);
    }

    public AbstractC0164c9 buildObject(int i, int i2, int i3) throws IOException {
        C1190rr c1190rr = new C1190rr(this, i3, this.limit);
        if ((i & 224) == 0) {
            return createPrimitiveDERObject(i2, c1190rr, this.tmpBuffers);
        }
        int i4 = i & 192;
        if (i4 != 0) {
            return readTaggedObjectDL(i4, i2, (i & 32) != 0, c1190rr);
        }
        if (i2 == 3) {
            return buildConstructedBitString(readVector(c1190rr));
        }
        if (i2 == 4) {
            return buildConstructedOctetString(readVector(c1190rr));
        }
        if (i2 == 8) {
            return C1080ps.createSequence(readVector(c1190rr)).toASN1External();
        }
        if (i2 == 16) {
            return c1190rr.getRemaining() < 1 ? C1080ps.EMPTY_SEQUENCE : this.lazyEvaluate ? new aa0(c1190rr.toByteArray()) : C1080ps.createSequence(readVector(c1190rr));
        }
        if (i2 == 17) {
            return C1080ps.createSet(readVector(c1190rr));
        }
        throw new IOException(AbstractC0003a2.m30b1("unknown tag ", i2, " encountered"));
    }

    public int getLimit() {
        return this.limit;
    }

    public void readFully(byte[] bArr) throws IOException {
        if (i21.readFully(this, bArr, 0, bArr.length) != bArr.length) {
            throw new EOFException("EOF encountered in middle of object");
        }
    }

    public int readLength() throws IOException {
        return readLength(this, this.limit, false);
    }

    public AbstractC0164c9 readObject() throws IOException {
        int i = read();
        if (i <= 0) {
            if (i != 0) {
                return null;
            }
            throw new IOException("unexpected end-of-contents marker");
        }
        int tagNumber = readTagNumber(this, i);
        int length = readLength();
        if (length >= 0) {
            try {
                return buildObject(i, tagNumber, length);
            } catch (IllegalArgumentException e) {
                throw new ASN1Exception("corrupted stream detected", e);
            }
        }
        if ((i & 32) == 0) {
            throw new IOException("indefinite-length primitive encoding encountered");
        }
        C0404d6 c0404d6 = new C0404d6(new m50(this, this.limit), this.limit, this.tmpBuffers);
        int i2 = i & 192;
        if (i2 != 0) {
            return c0404d6.loadTaggedIL(i2, tagNumber);
        }
        if (tagNumber == 3) {
            return C0172ch.parse(c0404d6);
        }
        if (tagNumber == 4) {
            return C0175ck.parse(c0404d6);
        }
        if (tagNumber == 8) {
            return C0993oq.parse(c0404d6);
        }
        if (tagNumber == 16) {
            return C0177cm.parse(c0404d6);
        }
        if (tagNumber == 17) {
            return C0179co.parse(c0404d6);
        }
        throw new IOException("unknown BER object encountered");
    }

    public AbstractC0164c9 readTaggedObjectDL(int i, int i2, boolean z, C1190rr c1190rr) throws IOException {
        return !z ? AbstractC0439e0.createPrimitive(i, i2, c1190rr.toByteArray()) : AbstractC0439e0.createConstructedDL(i, i2, readVector(c1190rr));
    }

    public C0118b1 readVector() throws IOException {
        AbstractC0164c9 object = readObject();
        if (object == null) {
            return new C0118b1(0);
        }
        C0118b1 c0118b1 = new C0118b1();
        do {
            c0118b1.add(object);
            object = readObject();
        } while (object != null);
        return c0118b1;
    }

    public C0126b9(InputStream inputStream, int i) {
        this(inputStream, i, false);
    }

    public static int readLength(InputStream inputStream, int i, boolean z) throws IOException {
        int i2 = inputStream.read();
        if ((i2 >>> 7) == 0) {
            return i2;
        }
        if (128 == i2) {
            return -1;
        }
        if (i2 < 0) {
            throw new EOFException("EOF found when length expected");
        }
        if (255 == i2) {
            throw new IOException("invalid long form definite-length 0xFF");
        }
        int i3 = i2 & 127;
        int i4 = 0;
        int i5 = 0;
        do {
            int i6 = inputStream.read();
            if (i6 < 0) {
                throw new EOFException("EOF found reading length");
            }
            if ((i4 >>> 23) != 0) {
                throw new IOException("long form definite-length more than 31 bits");
            }
            i4 = (i4 << 8) + i6;
            i5++;
        } while (i5 < i3);
        if (i4 < i || z) {
            return i4;
        }
        throw new IOException("corrupted stream - out of bounds length found: " + i4 + " >= " + i);
    }

    public C0118b1 readVector(C1190rr c1190rr) throws IOException {
        int remaining = c1190rr.getRemaining();
        return remaining < 1 ? new C0118b1(0) : new C0126b9(c1190rr, remaining, this.lazyEvaluate, this.tmpBuffers).readVector();
    }

    public C0126b9(InputStream inputStream, int i, boolean z) {
        this(inputStream, i, z, new byte[11][]);
    }

    private C0126b9(InputStream inputStream, int i, boolean z, byte[][] bArr) {
        super(inputStream);
        this.limit = i;
        this.lazyEvaluate = z;
        this.tmpBuffers = bArr;
    }

    public C0126b9(InputStream inputStream, boolean z) {
        this(inputStream, h21.findLimit(inputStream), z);
    }

    public C0126b9(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }

    public C0126b9(byte[] bArr, boolean z) {
        this(new ByteArrayInputStream(bArr), bArr.length, z);
    }
}
