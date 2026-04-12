package p000;

/* renamed from: bs */
/* loaded from: classes2.dex */
public class C0145bs extends AbstractC0158c3 {
    private C0160c5 type;
    private InterfaceC0117b0 value;

    public C0145bs(C0160c5 c0160c5, InterfaceC0117b0 interfaceC0117b0) {
        this.type = c0160c5;
        this.value = interfaceC0117b0;
    }

    public static C0145bs getInstance(Object obj) {
        if (obj instanceof C0145bs) {
            return (C0145bs) obj;
        }
        if (obj != null) {
            return new C0145bs(AbstractC0400d2.getInstance(obj));
        }
        throw new IllegalArgumentException("null value in getInstance()");
    }

    public C0160c5 getType() {
        return this.type;
    }

    public InterfaceC0117b0 getValue() {
        return this.value;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.type);
        c0118b1.add(this.value);
        return new C1064pc(c0118b1);
    }

    private C0145bs(AbstractC0400d2 abstractC0400d2) {
        this.type = (C0160c5) abstractC0400d2.getObjectAt(0);
        this.value = abstractC0400d2.getObjectAt(1);
    }
}
