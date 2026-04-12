package p000;

import java.io.IOException;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.NoSuchElementException;
import okhttp3.HttpUrl;
import p000.C0133bg;

/* renamed from: d2 */
/* loaded from: classes2.dex */
public abstract class AbstractC0400d2 extends AbstractC0164c9 implements d70 {
    static final AbstractC0445e6 TYPE = new a0(AbstractC0400d2.class, 16);
    InterfaceC0117b0[] elements;

    /* renamed from: d2$a1 */
    public class a1 implements Enumeration {
        private int pos = 0;

        public a1() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.pos < AbstractC0400d2.this.elements.length;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            int i = this.pos;
            InterfaceC0117b0[] interfaceC0117b0Arr = AbstractC0400d2.this.elements;
            if (i >= interfaceC0117b0Arr.length) {
                throw new NoSuchElementException();
            }
            this.pos = i + 1;
            return interfaceC0117b0Arr[i];
        }
    }

    /* renamed from: d2$a2 */
    public class a2 implements InterfaceC0401d3 {
        private int pos = 0;
        final /* synthetic */ int val$count;

        public a2(int i) {
            this.val$count = i;
        }

        @Override // p000.InterfaceC0401d3, p000.i50
        public AbstractC0164c9 getLoadedObject() {
            return AbstractC0400d2.this;
        }

        @Override // p000.InterfaceC0401d3
        public InterfaceC0117b0 readObject() throws IOException {
            int i = this.val$count;
            int i2 = this.pos;
            if (i == i2) {
                return null;
            }
            InterfaceC0117b0[] interfaceC0117b0Arr = AbstractC0400d2.this.elements;
            this.pos = i2 + 1;
            InterfaceC0117b0 interfaceC0117b0 = interfaceC0117b0Arr[i2];
            return interfaceC0117b0 instanceof AbstractC0400d2 ? ((AbstractC0400d2) interfaceC0117b0).parser() : interfaceC0117b0 instanceof AbstractC0402d4 ? ((AbstractC0402d4) interfaceC0117b0).parser() : interfaceC0117b0;
        }

        @Override // p000.InterfaceC0401d3, p000.InterfaceC0117b0
        public AbstractC0164c9 toASN1Primitive() {
            return AbstractC0400d2.this;
        }
    }

    public AbstractC0400d2() {
        this.elements = C0118b1.EMPTY_ELEMENTS;
    }

    public static AbstractC0400d2 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return (AbstractC0400d2) TYPE.getContextInstance(abstractC0439e0, z);
    }

    @Override // p000.AbstractC0164c9
    public boolean asn1Equals(AbstractC0164c9 abstractC0164c9) {
        if (!(abstractC0164c9 instanceof AbstractC0400d2)) {
            return false;
        }
        AbstractC0400d2 abstractC0400d2 = (AbstractC0400d2) abstractC0164c9;
        int size = size();
        if (abstractC0400d2.size() != size) {
            return false;
        }
        for (int i = 0; i < size; i++) {
            AbstractC0164c9 aSN1Primitive = this.elements[i].toASN1Primitive();
            AbstractC0164c9 aSN1Primitive2 = abstractC0400d2.elements[i].toASN1Primitive();
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

    public AbstractC0007a6[] getConstructedBitStrings() {
        int size = size();
        AbstractC0007a6[] abstractC0007a6Arr = new AbstractC0007a6[size];
        for (int i = 0; i < size; i++) {
            abstractC0007a6Arr[i] = AbstractC0007a6.getInstance(this.elements[i]);
        }
        return abstractC0007a6Arr;
    }

    public AbstractC0161c6[] getConstructedOctetStrings() {
        int size = size();
        AbstractC0161c6[] abstractC0161c6Arr = new AbstractC0161c6[size];
        for (int i = 0; i < size; i++) {
            abstractC0161c6Arr[i] = AbstractC0161c6.getInstance(this.elements[i]);
        }
        return abstractC0161c6Arr;
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
            iHashCode = (iHashCode * 257) ^ this.elements[length].toASN1Primitive().hashCode();
        }
    }

    public Iterator<InterfaceC0117b0> iterator() {
        return new C0133bg.a0(this.elements);
    }

    public InterfaceC0401d3 parser() {
        return new a2(size());
    }

    public int size() {
        return this.elements.length;
    }

    public abstract AbstractC0007a6 toASN1BitString();

    public abstract AbstractC0120b3 toASN1External();

    public abstract AbstractC0161c6 toASN1OctetString();

    public abstract AbstractC0402d4 toASN1Set();

    public InterfaceC0117b0[] toArray() {
        return C0118b1.cloneElements(this.elements);
    }

    public InterfaceC0117b0[] toArrayInternal() {
        return this.elements;
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDERObject() {
        return new C1064pc(this.elements, false);
    }

    @Override // p000.AbstractC0164c9
    public AbstractC0164c9 toDLObject() {
        return new C1082pu(this.elements, false);
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

    public AbstractC0400d2(InterfaceC0117b0 interfaceC0117b0) {
        if (interfaceC0117b0 == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        this.elements = new InterfaceC0117b0[]{interfaceC0117b0};
    }

    public static AbstractC0400d2 getInstance(Object obj) {
        if (obj == null || (obj instanceof AbstractC0400d2)) {
            return (AbstractC0400d2) obj;
        }
        if (obj instanceof InterfaceC0117b0) {
            AbstractC0164c9 aSN1Primitive = ((InterfaceC0117b0) obj).toASN1Primitive();
            if (aSN1Primitive instanceof AbstractC0400d2) {
                return (AbstractC0400d2) aSN1Primitive;
            }
        } else if (obj instanceof byte[]) {
            try {
                return (AbstractC0400d2) TYPE.fromByteArray((byte[]) obj);
            } catch (IOException e) {
                throw new IllegalArgumentException(AbstractC0003a2.m26a7(e, new StringBuilder("failed to construct sequence from byte[]: ")));
            }
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "unknown object in getInstance: "));
    }

    public AbstractC0400d2(C0118b1 c0118b1) {
        if (c0118b1 == null) {
            throw new NullPointerException("'elementVector' cannot be null");
        }
        this.elements = c0118b1.takeElements();
    }

    public AbstractC0400d2(InterfaceC0117b0[] interfaceC0117b0Arr) {
        if (C0133bg.isNullOrContainsNull(interfaceC0117b0Arr)) {
            throw new NullPointerException("'elements' cannot be null, or contain null");
        }
        this.elements = C0118b1.cloneElements(interfaceC0117b0Arr);
    }

    public AbstractC0400d2(InterfaceC0117b0[] interfaceC0117b0Arr, boolean z) {
        this.elements = z ? C0118b1.cloneElements(interfaceC0117b0Arr) : interfaceC0117b0Arr;
    }

    /* renamed from: d2$a0 */
    public static class a0 extends AbstractC0445e6 {
        public a0(Class cls, int i) {
            super(cls, i);
        }

        @Override // p000.AbstractC0445e6
        public AbstractC0164c9 fromImplicitConstructed(AbstractC0400d2 abstractC0400d2) {
            return abstractC0400d2;
        }
    }
}
