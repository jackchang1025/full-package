package p000;

import java.io.IOException;

/* renamed from: pw */
/* loaded from: classes2.dex */
public class C1085pw extends AbstractC0402d4 {
    private int contentsLength;

    public C1085pw() {
        this.contentsLength = -1;
    }

    private int getContentsLength() throws IOException {
        if (this.contentsLength < 0) {
            int length = this.elements.length;
            int iEncodedLength = 0;
            for (int i = 0; i < length; i++) {
                iEncodedLength += this.elements[i].toASN1Primitive().toDLObject().encodedLength(true);
            }
            this.contentsLength = iEncodedLength;
        }
        return this.contentsLength;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        c0163c8.writeIdentifier(z, 49);
        C1081pt dLSubStream = c0163c8.getDLSubStream();
        int length = this.elements.length;
        int i = 0;
        if (this.contentsLength >= 0 || length > 16) {
            c0163c8.writeDL(getContentsLength());
            while (i < length) {
                dLSubStream.writePrimitive(this.elements[i].toASN1Primitive(), true);
                i++;
            }
            return;
        }
        AbstractC0164c9[] abstractC0164c9Arr = new AbstractC0164c9[length];
        int iEncodedLength = 0;
        for (int i2 = 0; i2 < length; i2++) {
            AbstractC0164c9 dLObject = this.elements[i2].toASN1Primitive().toDLObject();
            abstractC0164c9Arr[i2] = dLObject;
            iEncodedLength += dLObject.encodedLength(true);
        }
        this.contentsLength = iEncodedLength;
        c0163c8.writeDL(iEncodedLength);
        while (i < length) {
            dLSubStream.writePrimitive(abstractC0164c9Arr[i], true);
            i++;
        }
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        return C0163c8.getLengthOfEncodingDL(z, getContentsLength());
    }

    public C1085pw(InterfaceC0117b0 interfaceC0117b0) {
        super(interfaceC0117b0);
        this.contentsLength = -1;
    }

    public C1085pw(C0118b1 c0118b1) {
        super(c0118b1, false);
        this.contentsLength = -1;
    }

    public C1085pw(boolean z, InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(z, interfaceC0117b0Arr);
        this.contentsLength = -1;
    }

    public C1085pw(InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(interfaceC0117b0Arr, false);
        this.contentsLength = -1;
    }

    @Override // p000.AbstractC0402d4, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
