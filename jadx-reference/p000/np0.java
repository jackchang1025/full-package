package p000;

/* loaded from: classes2.dex */
public class np0 extends AbstractC0158c3 {
    private AbstractC0402d4 values;

    public np0(C0160c5 c0160c5, InterfaceC0117b0 interfaceC0117b0) {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(c0160c5);
        c0118b1.add(interfaceC0117b0);
        this.values = new C1065pd(new C1064pc(c0118b1));
    }

    public static np0 getInstance(Object obj) {
        if (obj instanceof np0) {
            return (np0) obj;
        }
        if (obj != null) {
            return new np0(AbstractC0402d4.getInstance(obj));
        }
        return null;
    }

    public int collectAttributeTypes(C0160c5[] c0160c5Arr, int i) {
        int size = this.values.size();
        for (int i2 = 0; i2 < size; i2++) {
            c0160c5Arr[i + i2] = C0145bs.getInstance(this.values.getObjectAt(i2)).getType();
        }
        return size;
    }

    public boolean containsAttributeType(C0160c5 c0160c5) {
        int size = this.values.size();
        for (int i = 0; i < size; i++) {
            if (C0145bs.getInstance(this.values.getObjectAt(i)).getType().equals((AbstractC0164c9) c0160c5)) {
                return true;
            }
        }
        return false;
    }

    public C0145bs getFirst() {
        if (this.values.size() == 0) {
            return null;
        }
        return C0145bs.getInstance(this.values.getObjectAt(0));
    }

    public C0145bs[] getTypesAndValues() {
        int size = this.values.size();
        C0145bs[] c0145bsArr = new C0145bs[size];
        for (int i = 0; i != size; i++) {
            c0145bsArr[i] = C0145bs.getInstance(this.values.getObjectAt(i));
        }
        return c0145bsArr;
    }

    public boolean isMultiValued() {
        return this.values.size() > 1;
    }

    public int size() {
        return this.values.size();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.values;
    }

    private np0(AbstractC0402d4 abstractC0402d4) {
        this.values = abstractC0402d4;
    }

    public np0(C0145bs c0145bs) {
        this.values = new C1065pd(c0145bs);
    }

    public np0(C0145bs[] c0145bsArr) {
        this.values = new C1065pd(c0145bsArr);
    }
}
