package p000;

/* renamed from: b1 */
/* loaded from: classes2.dex */
public class C0118b1 {
    private static final int DEFAULT_CAPACITY = 10;
    static final InterfaceC0117b0[] EMPTY_ELEMENTS = new InterfaceC0117b0[0];
    private boolean copyOnWrite;
    private int elementCount;
    private InterfaceC0117b0[] elements;

    public C0118b1() {
        this(10);
    }

    public static InterfaceC0117b0[] cloneElements(InterfaceC0117b0[] interfaceC0117b0Arr) {
        return interfaceC0117b0Arr.length < 1 ? EMPTY_ELEMENTS : (InterfaceC0117b0[]) interfaceC0117b0Arr.clone();
    }

    private void doAddAll(InterfaceC0117b0[] interfaceC0117b0Arr, String str) {
        int length = interfaceC0117b0Arr.length;
        if (length < 1) {
            return;
        }
        int length2 = this.elements.length;
        int i = this.elementCount + length;
        int i2 = 0;
        if ((i > length2) | this.copyOnWrite) {
            reallocate(i);
        }
        do {
            InterfaceC0117b0 interfaceC0117b0 = interfaceC0117b0Arr[i2];
            if (interfaceC0117b0 == null) {
                throw new NullPointerException(str);
            }
            this.elements[this.elementCount + i2] = interfaceC0117b0;
            i2++;
        } while (i2 < length);
        this.elementCount = i;
    }

    private void reallocate(int i) {
        InterfaceC0117b0[] interfaceC0117b0Arr = new InterfaceC0117b0[Math.max(this.elements.length, i + (i >> 1))];
        System.arraycopy(this.elements, 0, interfaceC0117b0Arr, 0, this.elementCount);
        this.elements = interfaceC0117b0Arr;
        this.copyOnWrite = false;
    }

    public void add(InterfaceC0117b0 interfaceC0117b0) {
        if (interfaceC0117b0 == null) {
            throw new NullPointerException("'element' cannot be null");
        }
        int length = this.elements.length;
        int i = this.elementCount + 1;
        if (this.copyOnWrite | (i > length)) {
            reallocate(i);
        }
        this.elements[this.elementCount] = interfaceC0117b0;
        this.elementCount = i;
    }

    public void addAll(C0118b1 c0118b1) {
        if (c0118b1 == null) {
            throw new NullPointerException("'other' cannot be null");
        }
        doAddAll(c0118b1.elements, "'other' elements cannot be null");
    }

    public InterfaceC0117b0[] copyElements() {
        int i = this.elementCount;
        if (i == 0) {
            return EMPTY_ELEMENTS;
        }
        InterfaceC0117b0[] interfaceC0117b0Arr = new InterfaceC0117b0[i];
        System.arraycopy(this.elements, 0, interfaceC0117b0Arr, 0, i);
        return interfaceC0117b0Arr;
    }

    public InterfaceC0117b0 get(int i) {
        if (i < this.elementCount) {
            return this.elements[i];
        }
        throw new ArrayIndexOutOfBoundsException(i + " >= " + this.elementCount);
    }

    public int size() {
        return this.elementCount;
    }

    public InterfaceC0117b0[] takeElements() {
        int i = this.elementCount;
        if (i == 0) {
            return EMPTY_ELEMENTS;
        }
        InterfaceC0117b0[] interfaceC0117b0Arr = this.elements;
        if (interfaceC0117b0Arr.length == i) {
            this.copyOnWrite = true;
            return interfaceC0117b0Arr;
        }
        InterfaceC0117b0[] interfaceC0117b0Arr2 = new InterfaceC0117b0[i];
        System.arraycopy(interfaceC0117b0Arr, 0, interfaceC0117b0Arr2, 0, i);
        return interfaceC0117b0Arr2;
    }

    public C0118b1(int i) {
        if (i < 0) {
            throw new IllegalArgumentException("'initialCapacity' must not be negative");
        }
        this.elements = i == 0 ? EMPTY_ELEMENTS : new InterfaceC0117b0[i];
        this.elementCount = 0;
        this.copyOnWrite = false;
    }

    public void addAll(InterfaceC0117b0[] interfaceC0117b0Arr) {
        if (interfaceC0117b0Arr == null) {
            throw new NullPointerException("'others' cannot be null");
        }
        doAddAll(interfaceC0117b0Arr, "'others' elements cannot be null");
    }
}
