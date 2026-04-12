package p000;

import java.math.BigInteger;

/* loaded from: classes2.dex */
public class b70 extends AbstractC0158c3 {
    r20 issuer;
    AbstractC0007a6 issuerUID;
    C0155c0 serial;

    private b70(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() != 2 && abstractC0400d2.size() != 3) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        this.issuer = r20.getInstance(abstractC0400d2.getObjectAt(0));
        this.serial = C0155c0.getInstance(abstractC0400d2.getObjectAt(1));
        if (abstractC0400d2.size() == 3) {
            this.issuerUID = C0991oo.getInstance((Object) abstractC0400d2.getObjectAt(2));
        }
    }

    public static b70 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public r20 getIssuer() {
        return this.issuer;
    }

    public AbstractC0007a6 getIssuerUID() {
        return this.issuerUID;
    }

    public C0155c0 getSerial() {
        return this.serial;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(this.issuer);
        c0118b1.add(this.serial);
        AbstractC0007a6 abstractC0007a6 = this.issuerUID;
        if (abstractC0007a6 != null) {
            c0118b1.add(abstractC0007a6);
        }
        return new C1064pc(c0118b1);
    }

    public b70(r20 r20Var, C0155c0 c0155c0) {
        this.issuer = r20Var;
        this.serial = c0155c0;
    }

    public static b70 getInstance(Object obj) {
        if (obj instanceof b70) {
            return (b70) obj;
        }
        if (obj != null) {
            return new b70(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public b70(r20 r20Var, BigInteger bigInteger) {
        this(r20Var, new C0155c0(bigInteger));
    }

    public b70(kh1 kh1Var, BigInteger bigInteger) {
        this(new r20(new q20(kh1Var)), new C0155c0(bigInteger));
    }
}
