package p000;

/* loaded from: classes2.dex */
public class ie0 extends AbstractC0158c3 {
    private C1168r5 digest;
    private byte[] encField;
    private byte[] encGp;
    private byte[] encP;

    /* renamed from: k */
    private int f56874k;

    /* renamed from: n */
    private int f56875n;

    public ie0(int i, int i2, z10 z10Var, sn0 sn0Var, kn0 kn0Var, C1168r5 c1168r5) {
        this.f56875n = i;
        this.f56874k = i2;
        this.encField = z10Var.getEncoded();
        this.encGp = sn0Var.getEncoded();
        this.encP = kn0Var.getEncoded();
        this.digest = c1168r5;
    }

    public static ie0 getInstance(Object obj) {
        if (obj instanceof ie0) {
            return (ie0) obj;
        }
        if (obj != null) {
            return new ie0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C1168r5 getDigest() {
        return this.digest;
    }

    public z10 getField() {
        return new z10(this.encField);
    }

    public sn0 getGoppaPoly() {
        return new sn0(getField(), this.encGp);
    }

    public int getK() {
        return this.f56874k;
    }

    public int getN() {
        return this.f56875n;
    }

    public kn0 getP() {
        return new kn0(this.encP);
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        c0118b1.add(new C0155c0(this.f56875n));
        c0118b1.add(new C0155c0(this.f56874k));
        c0118b1.add(new C1048oy(this.encField));
        c0118b1.add(new C1048oy(this.encGp));
        c0118b1.add(new C1048oy(this.encP));
        c0118b1.add(this.digest);
        return new C1064pc(c0118b1);
    }

    private ie0(AbstractC0400d2 abstractC0400d2) {
        this.f56875n = ((C0155c0) abstractC0400d2.getObjectAt(0)).intValueExact();
        this.f56874k = ((C0155c0) abstractC0400d2.getObjectAt(1)).intValueExact();
        this.encField = ((AbstractC0161c6) abstractC0400d2.getObjectAt(2)).getOctets();
        this.encGp = ((AbstractC0161c6) abstractC0400d2.getObjectAt(3)).getOctets();
        this.encP = ((AbstractC0161c6) abstractC0400d2.getObjectAt(4)).getOctets();
        this.digest = C1168r5.getInstance(abstractC0400d2.getObjectAt(5));
    }
}
