package p000;

import java.io.IOException;

/* renamed from: om */
/* loaded from: classes2.dex */
public class C0989om extends AbstractC0004a3 {
    public C0989om(int i, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        this(true, i, interfaceC0117b0);
    }

    public C0989om(int i, C0118b1 c0118b1) {
        super(new C1067pf(false, 64, i, (InterfaceC0117b0) C0994or.createSequence(c0118b1)));
    }

    public C0989om(int i, byte[] bArr) {
        super(new C1067pf(false, 64, i, (InterfaceC0117b0) new C1048oy(bArr)));
    }

    public C0989om(AbstractC0439e0 abstractC0439e0) {
        super(abstractC0439e0);
    }

    public C0989om(boolean z, int i, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        super(new C1067pf(z, 64, i, interfaceC0117b0));
    }

    @Override // p000.AbstractC0004a3, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0004a3, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
