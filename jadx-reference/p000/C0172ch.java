package p000;

import java.io.IOException;
import java.io.InputStream;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: ch */
/* loaded from: classes2.dex */
public class C0172ch implements InterfaceC0008a7 {
    private C0841ly _bitStream;
    private C0404d6 _parser;

    public C0172ch(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    public static C0171cg parse(C0404d6 c0404d6) throws IOException {
        C0841ly c0841ly = new C0841ly(c0404d6, false);
        return new C0171cg(i21.readAll(c0841ly), c0841ly.getPadBits());
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getBitStream() throws IOException {
        C0841ly c0841ly = new C0841ly(this._parser, false);
        this._bitStream = c0841ly;
        return c0841ly;
    }

    @Override // p000.InterfaceC0008a7, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return parse(this._parser);
    }

    @Override // p000.InterfaceC0008a7
    public InputStream getOctetStream() throws IOException {
        C0841ly c0841ly = new C0841ly(this._parser, true);
        this._bitStream = c0841ly;
        return c0841ly;
    }

    @Override // p000.InterfaceC0008a7
    public int getPadBits() {
        return this._bitStream.getPadBits();
    }

    @Override // p000.InterfaceC0008a7, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(AbstractC0003a2.m26a7(e, new StringBuilder("IOException converting stream to byte array: ")), e);
        }
    }
}
