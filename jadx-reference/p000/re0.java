package p000;

/* loaded from: classes2.dex */
public class re0 extends AbstractC0158c3 {

    /* renamed from: g */
    private final w10 f59688g;

    /* renamed from: n */
    private final int f59689n;

    /* renamed from: t */
    private final int f59690t;

    public re0(int i, int i2, w10 w10Var) {
        this.f59689n = i;
        this.f59690t = i2;
        this.f59688g = new w10(w10Var);
    }

    public static re0 getInstance(Object obj) {
        if (obj instanceof re0) {
            return (re0) obj;
        }
        if (obj != null) {
            return new re0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public w10 getG() {
        return new w10(this.f59688g);
    }

    public int getN() {
        return this.f59689n;
    }

    public int getT() {
        return this.f59690t;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(new C0155c0(this.f59689n));
        c0118b1.add(new C0155c0(this.f59690t));
        c0118b1.add(new C1048oy(this.f59688g.getEncoded()));
        return new C1064pc(c0118b1);
    }

    private re0(AbstractC0400d2 abstractC0400d2) {
        this.f59689n = ((C0155c0) abstractC0400d2.getObjectAt(0)).intValueExact();
        this.f59690t = ((C0155c0) abstractC0400d2.getObjectAt(1)).intValueExact();
        this.f59688g = new w10(((AbstractC0161c6) abstractC0400d2.getObjectAt(2)).getOctets());
    }
}
