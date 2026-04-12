package p000;

/* renamed from: bm */
/* loaded from: classes2.dex */
public class C0139bm extends AbstractC0158c3 {
    C0123b6 notAfterTime;
    C0123b6 notBeforeTime;

    public C0139bm(C0123b6 c0123b6, C0123b6 c0123b62) {
        this.notBeforeTime = c0123b6;
        this.notAfterTime = c0123b62;
    }

    public static C0139bm getInstance(Object obj) {
        if (obj instanceof C0139bm) {
            return (C0139bm) obj;
        }
        if (obj != null) {
            return new C0139bm(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C0123b6 getNotAfterTime() {
        return this.notAfterTime;
    }

    public C0123b6 getNotBeforeTime() {
        return this.notBeforeTime;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.notBeforeTime);
        c0118b1.add(this.notAfterTime);
        return new C1064pc(c0118b1);
    }

    private C0139bm(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() == 2) {
            this.notBeforeTime = C0123b6.getInstance(abstractC0400d2.getObjectAt(0));
            this.notAfterTime = C0123b6.getInstance(abstractC0400d2.getObjectAt(1));
        } else {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
    }
}
