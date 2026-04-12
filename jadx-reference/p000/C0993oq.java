package p000;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1Exception;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: oq */
/* loaded from: classes2.dex */
public class C0993oq implements InterfaceC0121b4 {
    private C0404d6 _parser;

    public C0993oq(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    public static C1079pr parse(C0404d6 c0404d6) throws IOException {
        try {
            return new C1079pr(c0404d6.readVector());
        } catch (IllegalArgumentException e) {
            throw new ASN1Exception(e.getMessage(), e);
        }
    }

    @Override // p000.InterfaceC0121b4, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return parse(this._parser);
    }

    @Override // p000.InterfaceC0121b4
    public InterfaceC0117b0 readObject() throws IOException {
        return this._parser.readObject();
    }

    @Override // p000.InterfaceC0121b4, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException("unable to get DER object", e);
        } catch (IllegalArgumentException e2) {
            throw new ASN1ParsingException("unable to get DER object", e2);
        }
    }
}
