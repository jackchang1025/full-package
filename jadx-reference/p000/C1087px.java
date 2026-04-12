package p000;

import java.io.IOException;
import org.bouncycastle.asn1.ASN1ParsingException;

/* renamed from: px */
/* loaded from: classes2.dex */
public class C1087px implements InterfaceC0403d5 {
    private C0404d6 _parser;

    public C1087px(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    @Override // p000.InterfaceC0403d5, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return C1080ps.createSet(this._parser.readVector());
    }

    @Override // p000.InterfaceC0403d5
    public InterfaceC0117b0 readObject() throws IOException {
        return this._parser.readObject();
    }

    @Override // p000.InterfaceC0403d5, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new ASN1ParsingException(e.getMessage(), e);
        }
    }
}
