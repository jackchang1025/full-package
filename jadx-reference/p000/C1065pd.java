package p000;

import java.io.IOException;

/* renamed from: pd */
/* loaded from: classes2.dex */
public class C1065pd extends AbstractC0402d4 {
    private int contentsLength;

    public C1065pd() {
        this.contentsLength = -1;
    }

    private static boolean checkSorted(boolean z) {
        if (z) {
            return z;
        }
        throw new IllegalStateException("DERSet elements should always be in sorted order");
    }

    public static C1065pd convert(AbstractC0402d4 abstractC0402d4) {
        return (C1065pd) abstractC0402d4.toDERObject();
    }

    private int getContentsLength() throws IOException {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int iEncodedLength = 0;
            for (int i = 0; i < length; i++) {
                iEncodedLength += this.elements[i].toASN1Primitive().toDERObject().encodedLength(true);
            }
            this.contentsLength = iEncodedLength;
        }
        return this.contentsLength;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeIdentifier(z, 49);
        C1062pa dERSubStream = c0163c8.getDERSubStream();
        int length = this.elements.length;
        int i = 0;
        if (this.contentsLength >= 0 || length > 16) {
            c0163c8.writeDL(getContentsLength());
            while (i < length) {
                this.elements[i].toASN1Primitive().toDERObject().encode(dERSubStream, true);
                i++;
            }
            return;
        }
        AbstractC0164c9[] abstractC0164c9Arr = new AbstractC0164c9[length];
        int iEncodedLength = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0164c9 dERObject = this.elements[i2].toASN1Primitive().toDERObject();
            abstractC0164c9Arr[i2] = dERObject;
            iEncodedLength += dERObject.encodedLength(true);
        }
        this.contentsLength = iEncodedLength;
        c0163c8.writeDL(iEncodedLength);
        while (i < length) {
            abstractC0164c9Arr[i].encode(dERSubStream, true);
            i++;
        }
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        return C0163c8.getLengthOfEncodingDL(z, getContentsLength());
    }

    @Override // p000.AbstractC0402d4, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this.isSorted ? this : super.toDERObject();
    }

    public C1065pd(InterfaceC0117b0 interfaceC0117b0) {
        super(interfaceC0117b0);
        this.contentsLength = -1;
    }

    public C1065pd(C0118b1 c0118b1) {
        super(c0118b1, true);
        this.contentsLength = -1;
    }

    public C1065pd(boolean z, InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(checkSorted(z), interfaceC0117b0Arr);
        this.contentsLength = -1;
    }

    public C1065pd(InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(interfaceC0117b0Arr, true);
        this.contentsLength = -1;
    }

    @Override // p000.AbstractC0402d4, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
