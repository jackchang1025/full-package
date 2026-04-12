package p000;

/* loaded from: classes2.dex */
public class f91 extends AbstractC0158c3 {
    b70 baseCertificateID;
    r20 issuerName;
    rk0 objectDigestInfo;

    private f91(AbstractC0400d2 abstractC0400d2) {
        int i;
        if (abstractC0400d2.size() > 3) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        if (abstractC0400d2.getObjectAt(0) instanceof AbstractC0439e0) {
            i = 0;
        } else {
            this.issuerName = r20.getInstance(abstractC0400d2.getObjectAt(0));
            i = 1;
        }
        while (i != abstractC0400d2.size()) {
            AbstractC0439e0 abstractC0439e0 = AbstractC0439e0.getInstance(abstractC0400d2.getObjectAt(i));
            if (abstractC0439e0.getTagNo() == 0) {
                this.baseCertificateID = b70.getInstance(abstractC0439e0, false);
            } else {
                if (abstractC0439e0.getTagNo() != 1) {
                    throw new IllegalArgumentException("Bad tag number: " + abstractC0439e0.getTagNo());
                }
                this.objectDigestInfo = rk0.getInstance(abstractC0439e0, false);
            }
            i++;
        }
    }

    public static f91 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public b70 getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public r20 getIssuerName() {
        return this.issuerName;
    }

    public rk0 getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        r20 r20Var = this.issuerName;
        if (r20Var != null) {
            c0118b1.add(r20Var);
        }
        b70 b70Var = this.baseCertificateID;
        if (b70Var != null) {
            c0118b1.add(new C1067pf(false, 0, (InterfaceC0117b0) b70Var));
        }
        rk0 rk0Var = this.objectDigestInfo;
        if (rk0Var != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) rk0Var));
        }
        return new C1064pc(c0118b1);
    }

    public f91(r20 r20Var) {
        this(r20Var, null, null);
    }

    public static f91 getInstance(Object obj) {
        if (obj instanceof f91) {
            return (f91) obj;
        }
        if (obj != null) {
            return new f91(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public f91(r20 r20Var, b70 b70Var) {
        this(r20Var, b70Var, null);
    }

    public f91(r20 r20Var, b70 b70Var, rk0 rk0Var) {
        this.issuerName = r20Var;
        this.baseCertificateID = b70Var;
        this.objectDigestInfo = rk0Var;
    }

    public f91(r20 r20Var, rk0 rk0Var) {
        this(r20Var, null, rk0Var);
    }
}
