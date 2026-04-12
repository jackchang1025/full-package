package p000;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1Exception;

/* renamed from: d6 */
/* loaded from: classes2.dex */
public class C0404d6 {
    private final InputStream _in;
    private final int _limit;
    private final byte[][] tmpBuffers;

    public C0404d6(InputStream inputStream) {
        this(inputStream, h21.findLimit(inputStream));
    }

    private void set00Check(boolean z) {
        InputStream inputStream = this._in;
        if (inputStream instanceof m50) {
            ((m50) inputStream).setEofOn00(z);
        }
    }

    public InterfaceC0117b0 implParseObject(int i) throws IOException {
        set00Check(false);
        int tagNumber = C0126b9.readTagNumber(this._in, i);
        int length = C0126b9.readLength(this._in, this._limit, tagNumber == 3 || tagNumber == 4 || tagNumber == 16 || tagNumber == 17 || tagNumber == 8);
        if (length < 0) {
            if ((i & 32) == 0) {
                throw new IOException("indefinite-length primitive encoding encountered");
            }
            C0404d6 c0404d6 = new C0404d6(new m50(this._in, this._limit), this._limit, this.tmpBuffers);
            int i2 = i & 192;
            return i2 != 0 ? 64 == i2 ? new C0170cf(tagNumber, c0404d6) : new C0387cq(i2, tagNumber, c0404d6) : c0404d6.parseImplicitConstructedIL(tagNumber);
        }
        C1190rr c1190rr = new C1190rr(this._in, length, this._limit);
        if ((i & 224) == 0) {
            return parseImplicitPrimitive(tagNumber, c1190rr);
        }
        C0404d6 c0404d62 = new C0404d6(c1190rr, c1190rr.getLimit(), this.tmpBuffers);
        int i3 = i & 192;
        if (i3 == 0) {
            return c0404d62.parseImplicitConstructedDL(tagNumber);
        }
        boolean z = (i & 32) != 0;
        return 64 == i3 ? (C1076po) c0404d62.loadTaggedDL(i3, tagNumber, z) : new C1090pz(i3, tagNumber, z, c0404d62);
    }

    public AbstractC0164c9 loadTaggedDL(int i, int i2, boolean z) throws IOException {
        return !z ? AbstractC0439e0.createPrimitive(i, i2, ((C1190rr) this._in).toByteArray()) : AbstractC0439e0.createConstructedDL(i, i2, readVector());
    }

    public AbstractC0164c9 loadTaggedIL(int i, int i2) throws IOException {
        return AbstractC0439e0.createConstructedIL(i, i2, readVector());
    }

    public InterfaceC0117b0 parseImplicitConstructedDL(int i) throws IOException {
        if (i == 3) {
            return new C0172ch(this);
        }
        if (i == 4) {
            return new C0175ck(this);
        }
        if (i == 8) {
            return new C0993oq(this);
        }
        if (i == 16) {
            return new C1083pv(this);
        }
        if (i == 17) {
            return new C1087px(this);
        }
        throw new ASN1Exception("unknown DL object encountered: 0x" + Integer.toHexString(i));
    }

    public InterfaceC0117b0 parseImplicitConstructedIL(int i) throws IOException {
        if (i == 3) {
            return new C0172ch(this);
        }
        if (i == 4) {
            return new C0175ck(this);
        }
        if (i == 8) {
            return new C0993oq(this);
        }
        if (i == 16) {
            return new C0177cm(this);
        }
        if (i == 17) {
            return new C0179co(this);
        }
        throw new ASN1Exception("unknown BER object encountered: 0x" + Integer.toHexString(i));
    }

    public InterfaceC0117b0 parseImplicitPrimitive(int i) throws IOException {
        return parseImplicitPrimitive(i, (C1190rr) this._in);
    }

    public InterfaceC0117b0 parseObject(int i) throws IOException {
        if (i < 0 || i > 30) {
            throw new IllegalArgumentException(tz0.m214802a2(i, "invalid universal tag number: "));
        }
        int i2 = this._in.read();
        if (i2 < 0) {
            return null;
        }
        if ((i2 & (-33)) == i) {
            return implParseObject(i2);
        }
        throw new IOException(tz0.m214802a2(i2, "unexpected identifier encountered: "));
    }

    public InterfaceC0440e1 parseTaggedObject() throws IOException {
        int i = this._in.read();
        if (i < 0) {
            return null;
        }
        if ((i & 192) != 0) {
            return (InterfaceC0440e1) implParseObject(i);
        }
        throw new ASN1Exception("no tagged object found");
    }

    public InterfaceC0117b0 readObject() throws IOException {
        int i = this._in.read();
        if (i < 0) {
            return null;
        }
        return implParseObject(i);
    }

    public C0118b1 readVector() throws IOException {
        int i = this._in.read();
        if (i < 0) {
            return new C0118b1(0);
        }
        C0118b1 c0118b1 = new C0118b1();
        do {
            InterfaceC0117b0 interfaceC0117b0ImplParseObject = implParseObject(i);
            c0118b1.add(interfaceC0117b0ImplParseObject instanceof i50 ? ((i50) interfaceC0117b0ImplParseObject).getLoadedObject() : interfaceC0117b0ImplParseObject.toASN1Primitive());
            i = this._in.read();
        } while (i >= 0);
        return c0118b1;
    }

    public C0404d6(InputStream inputStream, int i) {
        this(inputStream, i, new byte[11][]);
    }

    public InterfaceC0117b0 parseImplicitPrimitive(int i, C1190rr c1190rr) throws IOException {
        if (i == 3) {
            return new C1078pq(c1190rr);
        }
        if (i == 4) {
            return new C1049oz(c1190rr);
        }
        if (i == 8) {
            throw new ASN1Exception("externals must use constructed encoding (see X.690 8.18)");
        }
        if (i == 16) {
            throw new ASN1Exception("sets must use constructed encoding (see X.690 8.11.1/8.12.1)");
        }
        if (i == 17) {
            throw new ASN1Exception("sequences must use constructed encoding (see X.690 8.9.1/8.10.1)");
        }
        try {
            return C0126b9.createPrimitiveDERObject(i, c1190rr, this.tmpBuffers);
        } catch (IllegalArgumentException e) {
            throw new ASN1Exception("corrupted stream detected", e);
        }
    }

    public C0404d6(InputStream inputStream, int i, byte[][] bArr) {
        this._in = inputStream;
        this._limit = i;
        this.tmpBuffers = bArr;
    }

    public C0404d6(byte[] bArr) {
        this(new ByteArrayInputStream(bArr), bArr.length);
    }
}
