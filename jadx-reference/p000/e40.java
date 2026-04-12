package p000;

/* loaded from: classes2.dex */
public class e40 extends AbstractC0158c3 {
    public static final int V1_CERTIFICATE_HOLDER = 0;
    public static final int V2_CERTIFICATE_HOLDER = 1;
    b70 baseCertificateID;
    r20 entityName;
    rk0 objectDigestInfo;
    private int version;

    private e40(AbstractC0400d2 abstractC0400d2) {
        this.version = 1;
        if (abstractC0400d2.size() > 3) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        for (int i = 0; i != abstractC0400d2.size(); i++) {
            AbstractC0439e0 abstractC0439e0 = AbstractC0439e0.getInstance(abstractC0400d2.getObjectAt(i));
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo == 0) {
                this.baseCertificateID = b70.getInstance(abstractC0439e0, false);
            } else if (tagNo == 1) {
                this.entityName = r20.getInstance(abstractC0439e0, false);
            } else {
                if (tagNo != 2) {
                    throw new IllegalArgumentException("unknown tag in Holder");
                }
                this.objectDigestInfo = rk0.getInstance(abstractC0439e0, false);
            }
        }
        this.version = 1;
    }

    public static e40 getInstance(Object obj) {
        if (obj instanceof e40) {
            return (e40) obj;
        }
        if (obj instanceof AbstractC0439e0) {
            return new e40(AbstractC0439e0.getInstance(obj));
        }
        if (obj != null) {
            return new e40(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public b70 getBaseCertificateID() {
        return this.baseCertificateID;
    }

    public r20 getEntityName() {
        return this.entityName;
    }

    public rk0 getObjectDigestInfo() {
        return this.objectDigestInfo;
    }

    public int getVersion() {
        return this.version;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        if (this.version != 1) {
            r20 r20Var = this.entityName;
            return r20Var != null ? new C1067pf(true, 1, (InterfaceC0117b0) r20Var) : new C1067pf(true, 0, (InterfaceC0117b0) this.baseCertificateID);
        }
        C0118b1 c0118b1 = new C0118b1(3);
        b70 b70Var = this.baseCertificateID;
        if (b70Var != null) {
            c0118b1.add(new C1067pf(false, 0, (InterfaceC0117b0) b70Var));
        }
        r20 r20Var2 = this.entityName;
        if (r20Var2 != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) r20Var2));
        }
        rk0 rk0Var = this.objectDigestInfo;
        if (rk0Var != null) {
            c0118b1.add(new C1067pf(false, 2, (InterfaceC0117b0) rk0Var));
        }
        return new C1064pc(c0118b1);
    }

    private e40(AbstractC0439e0 abstractC0439e0) {
        this.version = 1;
        int tagNo = abstractC0439e0.getTagNo();
        if (tagNo == 0) {
            this.baseCertificateID = b70.getInstance(abstractC0439e0, true);
        } else {
            if (tagNo != 1) {
                throw new IllegalArgumentException("unknown tag in Holder");
            }
            this.entityName = r20.getInstance(abstractC0439e0, true);
        }
        this.version = 0;
    }

    public e40(r20 r20Var) {
        this(r20Var, 1);
    }

    public e40(r20 r20Var, int i) {
        this.entityName = r20Var;
        this.version = i;
    }

    public e40(b70 b70Var) {
        this(b70Var, 1);
    }

    public e40(b70 b70Var, int i) {
        this.baseCertificateID = b70Var;
        this.version = i;
    }

    public e40(rk0 rk0Var) {
        this.version = 1;
        this.objectDigestInfo = rk0Var;
    }
}
