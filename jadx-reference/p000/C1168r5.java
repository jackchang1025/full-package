package p000;

/* renamed from: r5 */
/* loaded from: classes2.dex */
public class C1168r5 extends AbstractC0158c3 {
    private C0160c5 algorithm;
    private InterfaceC0117b0 parameters;

    public C1168r5(C0160c5 c0160c5) {
        this.algorithm = c0160c5;
    }

    public static C1168r5 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C0160c5 getAlgorithm() {
        return this.algorithm;
    }

    public InterfaceC0117b0 getParameters() {
        return this.parameters;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.algorithm);
        InterfaceC0117b0 interfaceC0117b0 = this.parameters;
        if (interfaceC0117b0 != null) {
            c0118b1.add(interfaceC0117b0);
        }
        return new C1064pc(c0118b1);
    }

    public C1168r5(C0160c5 c0160c5, InterfaceC0117b0 interfaceC0117b0) {
        this.algorithm = c0160c5;
        this.parameters = interfaceC0117b0;
    }

    public static C1168r5 getInstance(Object obj) {
        if (obj instanceof C1168r5) {
            return (C1168r5) obj;
        }
        if (obj != null) {
            return new C1168r5(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    private C1168r5(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() < 1 || abstractC0400d2.size() > 2) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        this.algorithm = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
        this.parameters = abstractC0400d2.size() == 2 ? abstractC0400d2.getObjectAt(1) : null;
    }
}
