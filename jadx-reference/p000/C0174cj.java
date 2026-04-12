package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.NoSuchElementException;

/* renamed from: cj */
/* loaded from: classes2.dex */
public class C0174cj extends AbstractC0161c6 {
    private static final int DEFAULT_SEGMENT_LIMIT = 1000;
    private final AbstractC0161c6[] elements;
    private final int segmentLimit;

    /* renamed from: cj$a0 */
    public class a0 implements Enumeration {
        int pos = 0;

        public a0() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.pos < C0174cj.this.string.length;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            int i = this.pos;
            C0174cj c0174cj = C0174cj.this;
            byte[] bArr = c0174cj.string;
            if (i >= bArr.length) {
                throw new NoSuchElementException();
            }
            int iMin = Math.min(bArr.length - i, c0174cj.segmentLimit);
            byte[] bArr2 = new byte[iMin];
            System.arraycopy(C0174cj.this.string, this.pos, bArr2, 0, iMin);
            this.pos += iMin;
            return new C1048oy(bArr2);
        }
    }

    /* renamed from: cj$a1 */
    public class a1 implements Enumeration {
        int counter = 0;

        public a1() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.counter < C0174cj.this.elements.length;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            if (this.counter >= C0174cj.this.elements.length) {
                throw new NoSuchElementException();
            }
            AbstractC0161c6[] abstractC0161c6Arr = C0174cj.this.elements;
            int i = this.counter;
            this.counter = i + 1;
            return abstractC0161c6Arr[i];
        }
    }

    public C0174cj(byte[] bArr) {
        this(bArr, DEFAULT_SEGMENT_LIMIT);
    }

    public static byte[] flattenOctetStrings(AbstractC0161c6[] abstractC0161c6Arr) {
        int length = abstractC0161c6Arr.length;
        if (length == 0) {
            return AbstractC0161c6.EMPTY_OCTETS;
        }
        if (length == 1) {
            return abstractC0161c6Arr[0].string;
        }
        int length2 = 0;
        for (AbstractC0161c6 abstractC0161c6 : abstractC0161c6Arr) {
            length2 += abstractC0161c6.string.length;
        }
        byte[] bArr = new byte[length2];
        int length3 = 0;
        for (AbstractC0161c6 abstractC0161c62 : abstractC0161c6Arr) {
            byte[] bArr2 = abstractC0161c62.string;
            System.arraycopy(bArr2, 0, bArr, length3, bArr2.length);
            length3 += bArr2.length;
        }
        return bArr;
    }

    @Override // p000.AbstractC0164c9
    public void encode(C0163c8 c0163c8, boolean z) throws IOException {
        if (!encodeConstructed()) {
            byte[] bArr = this.string;
            C1048oy.encode(c0163c8, z, bArr, 0, bArr.length);
            return;
        }
        c0163c8.writeIdentifier(z, 36);
        c0163c8.write(128);
        AbstractC0161c6[] abstractC0161c6Arr = this.elements;
        if (abstractC0161c6Arr == null) {
            int i = 0;
            while (true) {
                byte[] bArr2 = this.string;
                if (i >= bArr2.length) {
                    break;
                }
                int iMin = Math.min(bArr2.length - i, this.segmentLimit);
                C1048oy.encode(c0163c8, true, this.string, i, iMin);
                i += iMin;
            }
        } else {
            c0163c8.writePrimitives(abstractC0161c6Arr);
        }
        c0163c8.write(0);
        c0163c8.write(0);
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return this.elements != null || this.string.length > this.segmentLimit;
    }

    @Override // p000.AbstractC0164c9
    public int encodedLength(boolean z) throws IOException {
        if (!encodeConstructed()) {
            return C1048oy.encodedLength(z, this.string.length);
        }
        int iEncodedLength = z ? 4 : 3;
        if (this.elements == null) {
            int length = this.string.length;
            int i = this.segmentLimit;
            int i2 = length / i;
            int iEncodedLength2 = (C1048oy.encodedLength(true, i) * i2) + iEncodedLength;
            int length2 = this.string.length - (i2 * this.segmentLimit);
            return length2 > 0 ? C1048oy.encodedLength(true, length2) + iEncodedLength2 : iEncodedLength2;
        }
        int i3 = 0;
        while (true) {
            AbstractC0161c6[] abstractC0161c6Arr = this.elements;
            if (i3 >= abstractC0161c6Arr.length) {
                return iEncodedLength;
            }
            iEncodedLength += abstractC0161c6Arr[i3].encodedLength(true);
            i3++;
        }
    }

    public Enumeration getObjects() {
        return this.elements == null ? new a0() : new a1();
    }

    public C0174cj(byte[] bArr, int i) {
        this(bArr, null, i);
    }

    private C0174cj(byte[] bArr, AbstractC0161c6[] abstractC0161c6Arr, int i) {
        super(bArr);
        this.elements = abstractC0161c6Arr;
        this.segmentLimit = i;
    }

    public C0174cj(AbstractC0161c6[] abstractC0161c6Arr) {
        this(abstractC0161c6Arr, DEFAULT_SEGMENT_LIMIT);
    }

    public C0174cj(AbstractC0161c6[] abstractC0161c6Arr, int i) {
        this(flattenOctetStrings(abstractC0161c6Arr), abstractC0161c6Arr, i);
    }
}
