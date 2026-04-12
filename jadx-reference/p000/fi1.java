package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class fi1 extends AbstractC0158c3 implements hi1 {

    /* renamed from: id */
    private C0160c5 f56278id;
    private AbstractC0164c9 parameters;

    public fi1(int i, int i2) {
        this(i, i2, 0, 0);
    }

    public static fi1 getInstance(Object obj) {
        if (obj instanceof fi1) {
            return (fi1) obj;
        }
        if (obj != null) {
            return new fi1(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public C0160c5 getIdentifier() {
        return this.f56278id;
    }

    public AbstractC0164c9 getParameters() {
        return this.parameters;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(2);
        c0118b1.add(this.f56278id);
        c0118b1.add(this.parameters);
        return new C1064pc(c0118b1);
    }

    public fi1(int i, int i2, int i3, int i4) {
        this.f56278id = hi1.characteristic_two_field;
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(new C0155c0(i));
        if (i3 == 0) {
            if (i4 != 0) {
                throw new IllegalArgumentException("inconsistent k values");
            }
            c0118b1.add(hi1.tpBasis);
            c0118b1.add(new C0155c0(i2));
        } else {
            if (i3 <= i2 || i4 <= i3) {
                throw new IllegalArgumentException("inconsistent k values");
            }
            c0118b1.add(hi1.ppBasis);
            C0118b1 c0118b12 = new C0118b1(3);
            c0118b12.add(new C0155c0(i2));
            c0118b12.add(new C0155c0(i3));
            c0118b12.add(new C0155c0(i4));
            c0118b1.add(new C1064pc(c0118b12));
        }
        this.parameters = new C1064pc(c0118b1);
    }

    private fi1(AbstractC0400d2 abstractC0400d2) {
        this.f56278id = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
        this.parameters = abstractC0400d2.getObjectAt(1).toASN1Primitive();
    }

    public fi1(BigInteger bigInteger) {
        this.f56278id = hi1.prime_field;
        this.parameters = new C0155c0(bigInteger);
    }
}
