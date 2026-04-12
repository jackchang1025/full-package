package p000;

import java.io.IOException;
import java.io.OutputStream;

/* renamed from: pa */
/* loaded from: classes2.dex */
public class C1062pa extends C1081pt {
    public C1062pa(OutputStream outputStream) {
        super(outputStream);
    }

    @Override // p000.C1081pt, p000.C0163c8
    public void writeElements(InterfaceC0117b0[] interfaceC0117b0Arr) throws IOException {
        for (InterfaceC0117b0 interfaceC0117b0 : interfaceC0117b0Arr) {
            interfaceC0117b0.toASN1Primitive().toDERObject().encode(this, true);
        }
    }

    @Override // p000.C1081pt, p000.C0163c8
    public void writePrimitive(AbstractC0164c9 abstractC0164c9, boolean z) throws IOException {
        abstractC0164c9.toDERObject().encode(this, z);
    }

    @Override // p000.C1081pt, p000.C0163c8
    public void writePrimitives(AbstractC0164c9[] abstractC0164c9Arr) throws IOException {
        for (AbstractC0164c9 abstractC0164c9 : abstractC0164c9Arr) {
            abstractC0164c9.toDERObject().encode(this, true);
        }
    }

    @Override // p000.C0163c8
    public C1062pa getDERSubStream() {
        return this;
    }
}
