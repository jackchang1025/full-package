package p000;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: pq */
/* loaded from: classes2.dex */
public class C1078pq implements InterfaceC0008a7 {
    private int padBits = 0;
    private final C1190rr stream;

    public C1078pq(C1190rr c1190rr) {
        this.stream = c1190rr;
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getBitStream() throws IOException {
        return getBitStream(false);
    }

    @Override // p000.InterfaceC0008a7, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return AbstractC0007a6.createPrimitive(this.stream.toByteArray());
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getOctetStream() throws IOException {
        return getBitStream(true);
    }

    @Override // p000.InterfaceC0008a7
    public int getPadBits() {
        return this.padBits;
    }

    @Override // p000.InterfaceC0008a7, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(AbstractC0003a2.m26a7(e, new StringBuilder("IOException converting stream to byte array: ")), e);
        }
    }

    private InputStream getBitStream(boolean z) throws IOException {
        int remaining = this.stream.getRemaining();
        if (remaining < 1) {
            throw new IllegalStateException("content octets cannot be empty");
        }
        int i = this.stream.read();
        this.padBits = i;
        if (i > 0) {
            if (remaining < 2) {
                throw new IllegalStateException("zero length data with non-zero pad bits");
            }
            if (i > 7) {
                throw new IllegalStateException("pad bits cannot be greater than 7 or less than 0");
            }
            if (z) {
                throw new IOException("expected octet-aligned bitstring, but found padBits: " + this.padBits);
            }
        }
        return this.stream;
    }
}
