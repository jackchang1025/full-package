package p000;

import java.io.IOException;

/* renamed from: cg */
/* loaded from: classes2.dex */
public class C0171cg extends AbstractC0007a6 {
    private static final int DEFAULT_SEGMENT_LIMIT = 1000;
    private final AbstractC0007a6[] elements;
    private final int segmentLimit;

    public C0171cg(byte b, int i) {
        super(b, i);
        this.elements = null;
        this.segmentLimit = DEFAULT_SEGMENT_LIMIT;
    }

    public static byte[] flattenBitStrings(AbstractC0007a6[] abstractC0007a6Arr) {
        int length = abstractC0007a6Arr.length;
        if (length == 0) {
            return new byte[]{0};
        }
        if (length == 1) {
            return abstractC0007a6Arr[0].contents;
        }
        int i = length - 1;
        int length2 = 0;
        for (int i2 = 0; i2 < i; i2++) {
            byte[] bArr = abstractC0007a6Arr[i2].contents;
            if (bArr[0] != 0) {
                throw new IllegalArgumentException("only the last nested bitstring can have padding");
            }
            length2 += bArr.length - 1;
        }
        byte[] bArr2 = abstractC0007a6Arr[i].contents;
        byte b = bArr2[0];
        byte[] bArr3 = new byte[length2 + bArr2.length];
        bArr3[0] = b;
        int i3 = 1;
        for (AbstractC0007a6 abstractC0007a6 : abstractC0007a6Arr) {
            byte[] bArr4 = abstractC0007a6.contents;
            int length3 = bArr4.length - 1;
            System.arraycopy(bArr4, 1, bArr3, i3, length3);
            i3 += length3;
        }
        return bArr3;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        C0163c8 c0163c82;
        if (!encodeConstructed()) {
            byte[] bArr = this.contents;
            C1077pp.encode(c0163c8, z, bArr, 0, bArr.length);
            return;
        }
        c0163c8.writeIdentifier(z, 35);
        c0163c8.write(128);
        AbstractC0007a6[] abstractC0007a6Arr = this.elements;
        if (abstractC0007a6Arr == null) {
            byte[] bArr2 = this.contents;
            if (bArr2.length >= 2) {
                byte b = bArr2[0];
                int length = bArr2.length;
                int i = length - 1;
                int i2 = this.segmentLimit - 1;
                while (i > i2) {
                    C0163c8 c0163c83 = c0163c8;
                    C1077pp.encode(c0163c83, true, (byte) 0, this.contents, length - i, i2);
                    i -= i2;
                    c0163c8 = c0163c83;
                }
                c0163c82 = c0163c8;
                C1077pp.encode(c0163c82, true, b, this.contents, length - i, i);
            }
            c0163c82.write(0);
            c0163c82.write(0);
        }
        c0163c8.writePrimitives(abstractC0007a6Arr);
        c0163c82 = c0163c8;
        c0163c82.write(0);
        c0163c82.write(0);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return this.elements != null || this.contents.length > this.segmentLimit;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        if (!encodeConstructed()) {
            return C1077pp.encodedLength(z, this.contents.length);
        }
        int iEncodedLength = z ? 4 : 3;
        if (this.elements == null) {
            byte[] bArr = this.contents;
            if (bArr.length < 2) {
                return iEncodedLength;
            }
            int length = bArr.length - 2;
            int i = this.segmentLimit;
            int i2 = length / (i - 1);
            return C1077pp.encodedLength(true, this.contents.length - ((this.segmentLimit - 1) * i2)) + (C1077pp.encodedLength(true, i) * i2) + iEncodedLength;
        }
        int i3 = 0;
        while (true) {
            AbstractC0007a6[] abstractC0007a6Arr = this.elements;
            if (i3 >= abstractC0007a6Arr.length) {
                return iEncodedLength;
            }
            iEncodedLength += abstractC0007a6Arr[i3].encodedLength(true);
            i3++;
        }
    }

    public C0171cg(InterfaceC0117b0 interfaceC0117b0) throws IOException {
        this(interfaceC0117b0.toASN1Primitive().getEncoded("DER"), 0);
    }

    public C0171cg(byte[] bArr) {
        this(bArr, 0);
    }

    public C0171cg(byte[] bArr, int i) {
        this(bArr, i, DEFAULT_SEGMENT_LIMIT);
    }

    public C0171cg(byte[] bArr, int i, int i2) {
        super(bArr, i);
        this.elements = null;
        this.segmentLimit = i2;
    }

    public C0171cg(byte[] bArr, boolean z) {
        super(bArr, z);
        this.elements = null;
        this.segmentLimit = DEFAULT_SEGMENT_LIMIT;
    }

    public C0171cg(AbstractC0007a6[] abstractC0007a6Arr) {
        this(abstractC0007a6Arr, DEFAULT_SEGMENT_LIMIT);
    }

    public C0171cg(AbstractC0007a6[] abstractC0007a6Arr, int i) {
        super(flattenBitStrings(abstractC0007a6Arr), false);
        this.elements = abstractC0007a6Arr;
        this.segmentLimit = i;
    }
}
