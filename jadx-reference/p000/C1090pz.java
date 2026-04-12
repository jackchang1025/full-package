package p000;

import java.io.IOException;

/* renamed from: pz */
/* loaded from: classes2.dex */
public class C1090pz extends C0387cq {
    private final boolean _constructed;

    public C1090pz(int i, int i2, boolean z, C0404d6 c0404d6) {
        super(i, i2, c0404d6);
        this._constructed = z;
    }

    @Override // p000.C0387cq, p000.InterfaceC0440e1, p000.i50
    public AbstractC0164c9 getLoadedObject() throws IOException {
        return this._parser.loadTaggedDL(this._tagClass, this._tagNo, this._constructed);
    }

    @Override // p000.C0387cq
    public boolean isConstructed() {
        return this._constructed;
    }

    @Override // p000.C0387cq, p000.InterfaceC0440e1
    public InterfaceC0117b0 parseBaseUniversal(boolean z, int i) throws IOException {
        if (!z) {
            return this._constructed ? this._parser.parseImplicitConstructedDL(i) : this._parser.parseImplicitPrimitive(i);
        }
        if (this._constructed) {
            return this._parser.parseObject(i);
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // p000.C0387cq, p000.InterfaceC0440e1
    public InterfaceC0117b0 parseExplicitBaseObject() throws IOException {
        if (this._constructed) {
            return this._parser.readObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // p000.C0387cq, p000.InterfaceC0440e1
    public InterfaceC0440e1 parseExplicitBaseTagged() throws IOException {
        if (this._constructed) {
            return this._parser.parseTaggedObject();
        }
        throw new IOException("Explicit tags must be constructed (see X.690 8.14.2)");
    }

    @Override // p000.C0387cq, p000.InterfaceC0440e1
    public InterfaceC0440e1 parseImplicitBaseTagged(int i, int i2) throws IOException {
        return 64 == i ? (C1076po) this._parser.loadTaggedDL(i, i2, this._constructed) : new C1090pz(i, i2, this._constructed, this._parser);
    }
}
