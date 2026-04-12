package p000;

import java.io.IOException;

/* renamed from: cl */
/* loaded from: classes2.dex */
public class C0176cl extends AbstractC0400d2 {
    public C0176cl() {
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeEncodingIL(z, 48, this.elements);
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

    @Override // p000.AbstractC0400d2
    public AbstractC0007a6 toASN1BitString() {
        return new C0171cg(getConstructedBitStrings());
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0120b3 toASN1External() {
        return ((AbstractC0400d2) toDLObject()).toASN1External();
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0161c6 toASN1OctetString() {
        return new C0174cj(getConstructedOctetStrings());
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0402d4 toASN1Set() {
        return new C0178cn(false, toArrayInternal());
    }

    public C0176cl(InterfaceC0117b0 interfaceC0117b0) {
        super(interfaceC0117b0);
    }

    public C0176cl(C0118b1 c0118b1) {
        super(c0118b1);
    }

    public C0176cl(InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(interfaceC0117b0Arr);
    }
}
