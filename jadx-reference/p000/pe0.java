package p000;

/* loaded from: classes2.dex */
public class pe0 extends AbstractC0158c3 {
    private byte[] encField;
    private byte[] encGp;
    private byte[] encP1;
    private byte[] encP2;
    private byte[] encSInv;

    /* renamed from: k */
    private int f59222k;

    /* renamed from: n */
    private int f59223n;

    public pe0(int i, int i2, z10 z10Var, sn0 sn0Var, kn0 kn0Var, kn0 kn0Var2, w10 w10Var) {
        this.f59223n = i;
        this.f59222k = i2;
        this.encField = z10Var.getEncoded();
        this.encGp = sn0Var.getEncoded();
        this.encSInv = w10Var.getEncoded();
        this.encP1 = kn0Var.getEncoded();
        this.encP2 = kn0Var2.getEncoded();
    }

    public static pe0 getInstance(Object obj) {
        if (obj instanceof pe0) {
            return (pe0) obj;
        }
        if (obj != null) {
            return new pe0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public z10 getField() {
        return new z10(this.encField);
    }

    public sn0 getGoppaPoly() {
        return new sn0(getField(), this.encGp);
    }

    public int getK() {
        return this.f59222k;
    }

    public int getN() {
        return this.f59223n;
    }

    public kn0 getP1() {
        return new kn0(this.encP1);
    }

    public kn0 getP2() {
        return new kn0(this.encP2);
    }

    public w10 getSInv() {
        return new w10(this.encSInv);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(new C0155c0(this.f59223n));
        c0118b1.add(new C0155c0(this.f59222k));
        c0118b1.add(new C1048oy(this.encField));
        c0118b1.add(new C1048oy(this.encGp));
        c0118b1.add(new C1048oy(this.encP1));
        c0118b1.add(new C1048oy(this.encP2));
        c0118b1.add(new C1048oy(this.encSInv));
        return new C1064pc(c0118b1);
    }

    private pe0(AbstractC0400d2 abstractC0400d2) {
        this.f59223n = ((C0155c0) abstractC0400d2.getObjectAt(0)).intValueExact();
        this.f59222k = ((C0155c0) abstractC0400d2.getObjectAt(1)).intValueExact();
        this.encField = ((AbstractC0161c6) abstractC0400d2.getObjectAt(2)).getOctets();
        this.encGp = ((AbstractC0161c6) abstractC0400d2.getObjectAt(3)).getOctets();
        this.encP1 = ((AbstractC0161c6) abstractC0400d2.getObjectAt(4)).getOctets();
        this.encP2 = ((AbstractC0161c6) abstractC0400d2.getObjectAt(5)).getOctets();
        this.encSInv = ((AbstractC0161c6) abstractC0400d2.getObjectAt(6)).getOctets();
    }
}
