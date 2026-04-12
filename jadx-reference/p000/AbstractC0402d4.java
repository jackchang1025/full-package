package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import okhttp3.HttpUrl;
import p000.C0133bg;

/* renamed from: d4 */
/* loaded from: classes2.dex */
public abstract class AbstractC0402d4 extends AbstractC0164c9 implements d70 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0402d4.class, 17);
    protected final InterfaceC0117b0[] elements;
    protected final boolean isSorted;

    /* renamed from: d4$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return abstractC0400d2.toASN1Set();
        }
    }

    /* renamed from: d4$a1 */
    public class a1 implements Enumeration {
        private int pos = 0;

        public a1() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.pos < AbstractC0402d4.this.elements.length;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            int i = this.pos;
            InterfaceC0117b0[] interfaceC0117b0Arr = AbstractC0402d4.this.elements;
            if (i >= interfaceC0117b0Arr.length) {
                throw new NoSuchElementException();
            }
            this.pos = i + 1;
            return interfaceC0117b0Arr[i];
        }
    }

    /* renamed from: d4$a2 */
    public class a2 implements InterfaceC0403d5 {
        private int pos = 0;
        final /* synthetic */ int val$count;

        public a2(int i) {
            this.val$count = i;
        }

        @Override // p000.InterfaceC0403d5, p000.i50
        public AbstractC0164c9 getLoadedObject() {
            return AbstractC0402d4.this;
        }

        @Override // p000.InterfaceC0403d5
        public InterfaceC0117b0 readObject() throws IOException {
            int i = this.val$count;
            int i2 = this.pos;
            if (i == i2) {
                return null;
            }
            InterfaceC0117b0[] interfaceC0117b0Arr = AbstractC0402d4.this.elements;
            this.pos = i2 + 1;
            InterfaceC0117b0 interfaceC0117b0 = interfaceC0117b0Arr[i2];
            return interfaceC0117b0 instanceof AbstractC0400d2 ? ((AbstractC0400d2) interfaceC0117b0).parser() : interfaceC0117b0 instanceof AbstractC0402d4 ? ((AbstractC0402d4) interfaceC0117b0).parser() : interfaceC0117b0;
        }

        @Override // p000.InterfaceC0403d5, p000.InterfaceC0117b0
        public AbstractC0164c9 toASN1Primitive() {
            return AbstractC0402d4.this;
        }
    }

    public AbstractC0402d4() {
        this.elements = C0118b1.EMPTY_ELEMENTS;
        this.isSorted = true;
    }

    private static byte[] getDEREncoded(InterfaceC0117b0 interfaceC0117b0) {
        try {
            return interfaceC0117b0.toASN1Primitive().getEncoded("DER");
        } catch (IOException unused) {
            throw new IllegalArgumentException("cannot encode object added to SET");
        }
    }

    public static AbstractC0402d4 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0402d4) TYPE.getContextInstance(abstractC0439e0, z);
    }

    private static boolean lessThanOrEqual(byte[] bArr, byte[] bArr2) {
        int i = bArr[0] & (-33);
        int i2 = bArr2[0] & (-33);
        if (i != i2) {
            return i < i2;
        }
        int iMin = Math.min(bArr.length, bArr2.length) - 1;
        for (int i3 = 1; i3 < iMin; i3++) {
            byte b = bArr[i3];
            byte b2 = bArr2[i3];
            if (b != b2) {
                return (b & 255) < (b2 & 255);
            }
        }
        return (bArr[iMin] & 255) <= (bArr2[iMin] & 255);
    }

    private static void sort(InterfaceC0117b0[] interfaceC0117b0Arr) {
        int i;
        int length = interfaceC0117b0Arr.length;
        if (length < 2) {
            return;
        }
        InterfaceC0117b0 interfaceC0117b0 = interfaceC0117b0Arr[0];
        InterfaceC0117b0 interfaceC0117b02 = interfaceC0117b0Arr[1];
        byte[] dEREncoded = getDEREncoded(interfaceC0117b0);
        byte[] dEREncoded2 = getDEREncoded(interfaceC0117b02);
        if (lessThanOrEqual(dEREncoded2, dEREncoded)) {
            interfaceC0117b02 = interfaceC0117b0;
            interfaceC0117b0 = interfaceC0117b02;
            dEREncoded2 = dEREncoded;
            dEREncoded = dEREncoded2;
        }
        for (int i2 = 2; i2 < length; i2++) {
            InterfaceC0117b0 interfaceC0117b03 = interfaceC0117b0Arr[i2];
            byte[] dEREncoded3 = getDEREncoded(interfaceC0117b03);
            if (lessThanOrEqual(dEREncoded2, dEREncoded3)) {
                interfaceC0117b0Arr[i2 - 2] = interfaceC0117b0;
                interfaceC0117b0 = interfaceC0117b02;
                dEREncoded = dEREncoded2;
                interfaceC0117b02 = interfaceC0117b03;
                dEREncoded2 = dEREncoded3;
            } else if (lessThanOrEqual(dEREncoded, dEREncoded3)) {
                interfaceC0117b0Arr[i2 - 2] = interfaceC0117b0;
                interfaceC0117b0 = interfaceC0117b03;
                dEREncoded = dEREncoded3;
            } else {
                int i3 = i2 - 1;
                while (true) {
                    i = i3 - 1;
                    if (i <= 0) {
                        break;
                    }
                    InterfaceC0117b0 interfaceC0117b04 = interfaceC0117b0Arr[i3 - 2];
                    if (lessThanOrEqual(getDEREncoded(interfaceC0117b04), dEREncoded3)) {
                        break;
                    }
                    interfaceC0117b0Arr[i] = interfaceC0117b04;
                    i3 = i;
                }
                interfaceC0117b0Arr[i] = interfaceC0117b03;
            }
        }
        interfaceC0117b0Arr[length - 2] = interfaceC0117b0;
        interfaceC0117b0Arr[length - 1] = interfaceC0117b02;
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (!(abstractC0164c9 instanceof AbstractC0402d4)) {
            return false;
        }
        AbstractC0402d4 abstractC0402d4 = (AbstractC0402d4) abstractC0164c9;
        int size = size();
        if (abstractC0402d4.size() != size) {
            return false;
        }
        C1065pd c1065pd = (C1065pd) toDERObject();
        C1065pd c1065pd2 = (C1065pd) abstractC0402d4.toDERObject();
        for (int i = 0; i < size; i++) {
            AbstractC0164c9 aSN1Primitive = c1065pd.elements[i].toASN1Primitive();
            AbstractC0164c9 aSN1Primitive2 = c1065pd2.elements[i].toASN1Primitive();
            if (aSN1Primitive != aSN1Primitive2 && !aSN1Primitive.asn1Equals(aSN1Primitive2)) {
                return false;
            }
        }
        return true;
    }

    @Override // p000.AbstractC0164c9
    public boolean encodeConstructed() {
        return true;
    }

    public InterfaceC0117b0 getObjectAt(int i) {
        return this.elements[i];
    }

    public Enumeration getObjects() {
        return new a1();
    }

    @Override // p000.AbstractC0164c9, p000.AbstractC0158c3
    public int hashCode() {
        int length = this.elements.length;
        int iHashCode = length + 1;
        while (true) {
            length--;
            if (length < 0) {
                return iHashCode;
            }
            iHashCode += this.elements[length].toASN1Primitive().hashCode();
        }
    }

    @Override // p000.d70, java.lang.Iterable
    public Iterator<InterfaceC0117b0> iterator() {
        return new C0133bg.a0(toArray());
    }

    public InterfaceC0403d5 parser() {
        return new a2(size());
    }

    public int size() {
        return this.elements.length;
    }

    public InterfaceC0117b0[] toArray() {
        return C0118b1.cloneElements(this.elements);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        InterfaceC0117b0[] interfaceC0117b0Arr;
        if (this.isSorted) {
            interfaceC0117b0Arr = this.elements;
        } else {
            interfaceC0117b0Arr = (InterfaceC0117b0[]) this.elements.clone();
            sort(interfaceC0117b0Arr);
        }
        return new C1065pd(true, interfaceC0117b0Arr);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1085pw(this.isSorted, this.elements);
    }

    public String toString() {
        int size = size();
        if (size == 0) {
            return HttpUrl.PATH_SEGMENT_ENCODE_SET_URI;
        }
        StringBuffer stringBuffer = new StringBuffer("[");
        int i = 0;
        while (true) {
            stringBuffer.append(this.elements[i]);
            i++;
            if (i >= size) {
                stringBuffer.append(']');
                return stringBuffer.toString();
            }
            stringBuffer.append(", ");
        }
    }

    public AbstractC0402d4(InterfaceC0117b0 interfaceC0117b0) {
        if (interfaceC0117b0 == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        this.elements = new InterfaceC0117b0[]{interfaceC0117b0};
        this.isSorted = true;
    }

    public static AbstractC0402d4 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0402d4)) {
            return (AbstractC0402d4) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0402d4) {
                return (AbstractC0402d4) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (AbstractC0402d4) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct set from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in getInstance: "));
    }

    public AbstractC0402d4(C0118b1 c0118b1, boolean z) {
        InterfaceC0117b0[] interfaceC0117b0ArrTakeElements;
        if (c0118b1 == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        if (!z || c0118b1.size() < 2) {
            interfaceC0117b0ArrTakeElements = c0118b1.takeElements();
        } else {
            interfaceC0117b0ArrTakeElements = c0118b1.copyElements();
            sort(interfaceC0117b0ArrTakeElements);
        }
        this.elements = interfaceC0117b0ArrTakeElements;
        this.isSorted = z || interfaceC0117b0ArrTakeElements.length < 2;
    }

    public AbstractC0402d4(boolean z, InterfaceC0117b0[] interfaceC0117b0Arr) {
        this.elements = interfaceC0117b0Arr;
        this.isSorted = z || interfaceC0117b0Arr.length < 2;
    }

    public AbstractC0402d4(InterfaceC0117b0[] interfaceC0117b0Arr, boolean z) {
        if (C0133bg.isNullOrContainsNull(interfaceC0117b0Arr)) {
            throw new NullPointerException("'elements' cannot be null, or contain null");
        }
        InterfaceC0117b0[] interfaceC0117b0ArrCloneElements = C0118b1.cloneElements(interfaceC0117b0Arr);
        if (z && interfaceC0117b0ArrCloneElements.length >= 2) {
            sort(interfaceC0117b0ArrCloneElements);
        }
        this.elements = interfaceC0117b0ArrCloneElements;
        this.isSorted = z || interfaceC0117b0ArrCloneElements.length < 2;
    }
}
