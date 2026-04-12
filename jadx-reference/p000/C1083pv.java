package p000;

import java.io.IOException;

/* renamed from: pv */
/* loaded from: classes2.dex */
public class C1083pv implements InterfaceC0401d3 {
    private C0404d6 _parser;

    public C1083pv(C0404d6 c0404d6) {
        this._parser = c0404d6;
    }

    @Override // p000.InterfaceC0401d3, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return C1080ps.createSequence(this._parser.readVector());
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
