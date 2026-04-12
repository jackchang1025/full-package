package p000;

import java.io.IOException;

/* renamed from: pc */
/* loaded from: classes2.dex */
public class C1064pc extends AbstractC0400d2 {
    private int contentsLength;

    public C1064pc() {
        this.contentsLength = -1;
    }

    public static C1064pc convert(AbstractC0400d2 abstractC0400d2) {
        return (C1064pc) abstractC0400d2.toDERObject();
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
        c0163c8.writeIdentifier(z, 48);
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

    @Override // p000.AbstractC0400d2
    public AbstractC0007a6 toASN1BitString() {
        return new C0991oo(C0171cg.flattenBitStrings(getConstructedBitStrings()), false);
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0120b3 toASN1External() {
        return new C0992op(this);
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0161c6 toASN1OctetString() {
        return new C1048oy(C0174cj.flattenOctetStrings(getConstructedOctetStrings()));
    }

    @Override // p000.AbstractC0400d2
    public AbstractC0402d4 toASN1Set() {
        return new C1085pw(false, toArrayInternal());
    }

    public C1064pc(InterfaceC0117b0 interfaceC0117b0) {
        super(interfaceC0117b0);
        this.contentsLength = -1;
    }

    public C1064pc(C0118b1 c0118b1) {
        super(c0118b1);
        this.contentsLength = -1;
    }

    public C1064pc(InterfaceC0117b0[] interfaceC0117b0Arr) {
        super(interfaceC0117b0Arr);
        this.contentsLength = -1;
    }

    public C1064pc(InterfaceC0117b0[] interfaceC0117b0Arr, boolean z) {
        super(interfaceC0117b0Arr, z);
        this.contentsLength = -1;
    }

    @Override // p000.AbstractC0400d2, p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return this;
    }

    @Override // p000.AbstractC0400d2, p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return this;
    }
}
