package p000;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: ck */
/* loaded from: classes2.dex */
public class C0175ck implements InterfaceC0162c7 {
    private C0404d6 _parser;

    public C0175ck(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    public static C0174cj parse(C0404d6 c0404d6) throws IOException {
        return new C0174cj(i21.readAll(new C0842lz(c0404d6)));
    }

    @Override // p000.InterfaceC0162c7, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return parse(this._parser);
    }

    @Override // p000.InterfaceC0162c7
    public InputStream getOctetStream() {
        return new C0842lz(this._parser);
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
