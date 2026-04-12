package p000;

import java.io.IOException;

/* renamed from: cm */
/* loaded from: classes2.dex */
public class C0177cm implements InterfaceC0401d3 {
    private C0404d6 _parser;

    public C0177cm(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    public static C0176cl parse(C0404d6 c0404d6) throws IOException {
        return new C0176cl(c0404d6.readVector());
    }

    @Override // p000.InterfaceC0401d3, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return parse(this._parser);
    }

    @Override // p000.InterfaceC0401d3
    public InterfaceC0117b0 readObject() throws IOException {
        return this._parser.readObject();
    }

    @Override // p000.InterfaceC0401d3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        try {
            return getLoadedObject();
        } catch (IOException e) {
            throw new IllegalStateException(e.getMessage());
        }
    }
}
