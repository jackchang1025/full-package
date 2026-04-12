package p000;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: oz */
/* loaded from: classes2.dex */
public class C1049oz implements InterfaceC0162c7 {
    private C1190rr stream;

    public C1049oz(C1190rr c1190rr) {
        this.stream = c1190rr;
    }

    @Override // p000.InterfaceC0162c7, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return new C1048oy(this.stream.toByteArray());
    }

    @Override // p000.InterfaceC0162c7
    public InputStream getOctetStream() {
        return this.stream;
    }

    @Override // p000.InterfaceC0162c7, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(AbstractC0003a2.m26a7(e, new StringBuilder("IOException converting stream to byte array: ")), e);
        }
    }
}
