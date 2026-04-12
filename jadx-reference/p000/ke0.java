package p000;

/* loaded from: classes2.dex */
public class ke0 extends AbstractC0158c3 {
    private final C1168r5 digest;

    /* renamed from: g */
    private final w10 f57509g;

    /* renamed from: n */
    private final int f57510n;

    /* renamed from: t */
    private final int f57511t;

    public ke0(int i, int i2, w10 w10Var, C1168r5 c1168r5) {
        this.f57510n = i;
        this.f57511t = i2;
        this.f57509g = new w10(w10Var.getEncoded());
        this.digest = c1168r5;
    }

    public static ke0 getInstance(Object obj) {
        if (obj instanceof ke0) {
            return (ke0) obj;
        }
        if (obj != null) {
            return new ke0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C1168r5 getDigest() {
        return this.digest;
    }

    public w10 getG() {
        return this.f57509g;
    }

    public int getN() {
        return this.f57510n;
    }

    public int getT() {
        return this.f57511t;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(new C0155c0(this.f57510n));
        c0118b1.add(new C0155c0(this.f57511t));
        c0118b1.add(new C1048oy(this.f57509g.getEncoded()));
        c0118b1.add(this.digest);
        return new C1064pc(c0118b1);
    }

    private ke0(AbstractC0400d2 abstractC0400d2) {
        this.f57510n = ((C0155c0) abstractC0400d2.getObjectAt(0)).intValueExact();
        this.f57511t = ((C0155c0) abstractC0400d2.getObjectAt(1)).intValueExact();
        this.f57509g = new w10(((AbstractC0161c6) abstractC0400d2.getObjectAt(2)).getOctets());
        this.digest = C1168r5.getInstance(abstractC0400d2.getObjectAt(3));
    }
}
