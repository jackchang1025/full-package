package p000;

/* loaded from: classes2.dex */
public class vp0 extends AbstractC0158c3 {
    private byte[][] coeffQuadratic;
    private byte[] coeffScalar;
    private byte[][] coeffSingular;
    private C0155c0 docLength;
    private C0160c5 oid;
    private C0155c0 version;

    public vp0(int i, short[][] sArr, short[][] sArr2, short[] sArr3) {
        this.version = new C0155c0(0L);
        this.docLength = new C0155c0(i);
        this.coeffQuadratic = yp0.convertArray(sArr);
        this.coeffSingular = yp0.convertArray(sArr2);
        this.coeffScalar = yp0.convertArray(sArr3);
    }

    public static vp0 getInstance(Object obj) {
        if (obj instanceof vp0) {
            return (vp0) obj;
        }
        if (obj != null) {
            return new vp0(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public short[][] getCoeffQuadratic() {
        return yp0.convertArray(this.coeffQuadratic);
    }

    public short[] getCoeffScalar() {
        return yp0.convertArray(this.coeffScalar);
    }

    public short[][] getCoeffSingular() {
        return yp0.convertArray(this.coeffSingular);
    }

    public int getDocLength() {
        return this.docLength.intValueExact();
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1();
        InterfaceC0117b0 interfaceC0117b0 = this.version;
        if (interfaceC0117b0 == null) {
            interfaceC0117b0 = this.oid;
        }
        c0118b1.add(interfaceC0117b0);
        c0118b1.add(this.docLength);
        C0118b1 c0118b12 = new C0118b1();
        for (int i = 0; i < this.coeffQuadratic.length; i++) {
            c0118b12.add(new C1048oy(this.coeffQuadratic[i]));
        }
        c0118b1.add(new C1064pc(c0118b12));
        C0118b1 c0118b13 = new C0118b1();
        for (int i2 = 0; i2 < this.coeffSingular.length; i2++) {
            c0118b13.add(new C1048oy(this.coeffSingular[i2]));
        }
        c0118b1.add(new C1064pc(c0118b13));
        C0118b1 c0118b14 = new C0118b1();
        c0118b14.add(new C1048oy(this.coeffScalar));
        c0118b1.add(new C1064pc(c0118b14));
        return new C1064pc(c0118b1);
    }

    private vp0(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.getObjectAt(0) instanceof C0155c0) {
            this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
        } else {
            this.oid = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
        }
        this.docLength = C0155c0.getInstance(abstractC0400d2.getObjectAt(1));
        AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(abstractC0400d2.getObjectAt(2));
        this.coeffQuadratic = new byte[abstractC0400d22.size()][];
        for (int i = 0; i < abstractC0400d22.size(); i++) {
            this.coeffQuadratic[i] = AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(i)).getOctets();
        }
        AbstractC0400d2 abstractC0400d23 = (AbstractC0400d2) abstractC0400d2.getObjectAt(3);
        this.coeffSingular = new byte[abstractC0400d23.size()][];
        for (int i2 = 0; i2 < abstractC0400d23.size(); i2++) {
            this.coeffSingular[i2] = AbstractC0161c6.getInstance(abstractC0400d23.getObjectAt(i2)).getOctets();
        }
        this.coeffScalar = AbstractC0161c6.getInstance(((AbstractC0400d2) abstractC0400d2.getObjectAt(4)).getObjectAt(0)).getOctets();
    }
}
