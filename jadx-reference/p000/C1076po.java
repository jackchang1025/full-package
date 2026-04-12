package p000;

import java.io.IOException;

/* renamed from: po */
/* loaded from: classes2.dex */
public class C1076po extends AbstractC0004a3 {
    public C1076po(int i, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        this(true, i, interfaceC0117b0);
    }

    public C1076po(int i, C0118b1 c0118b1) {
        super(new C1089py(false, 64, i, (InterfaceC0117b0) C1080ps.createSequence(c0118b1)));
    }

    public C1076po(int i, byte[] bArr) {
        super(new C1089py(false, 64, i, (InterfaceC0117b0) new C1048oy(bArr)));
    }

    public C1076po(AbstractC0439e0 abstractC0439e0) {
        super(abstractC0439e0);
    }

    public C1076po(boolean z, int i, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        super(new C1089py(z, 64, i, interfaceC0117b0));
    }

    @Override // p000.AbstractC0004a3, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
