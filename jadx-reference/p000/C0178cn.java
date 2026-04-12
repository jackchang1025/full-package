package p000;

import java.io.IOException;

/* renamed from: cn */
/* loaded from: classes2.dex */
public class C0178cn extends AbstractC0402d4 {
    public C0178cn() {
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingIL(z, 49, this.elements);
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        int iEncodedLength = z ? 4 : 3;
        int length = this.elements.length;
        for (int i = 0; i < length; i++) {
            iEncodedLength += this.elements[i].toASN1Primitive().encodedLength(true);
        }
        return iEncodedLength;
    }

    public C0178cn(InterfaceC0117b0 interfaceC0117b0) {
        super(interfaceC0117b0);
    }

    public C0178cn(C0118b1 c0118b1) {
        super(c0118b1, false);
    }

    public C0178cn(boolean z, InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(z, interfaceC0117b0Arr);
    }

    public C0178cn(InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(interfaceC0117b0Arr, false);
    }
}
