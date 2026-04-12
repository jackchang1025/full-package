package p000;

/* renamed from: bq */
/* loaded from: classes2.dex */
public class C0143bq extends AbstractC0158c3 {
    private C0139bm attrCertValidityPeriod;
    private AbstractC0400d2 attributes;
    private C1454ye extensions;
    private e40 holder;
    private C0138bl issuer;
    private AbstractC0007a6 issuerUniqueID;
    private C0155c0 serialNumber;
    private C1168r5 signature;
    private C0155c0 version;

    private C0143bq(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() < 6 || abstractC0400d2.size() > 9) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        int i = 0;
        if (abstractC0400d2.getObjectAt(0) instanceof C0155c0) {
            this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
            i = 1;
        } else {
            this.version = new C0155c0(0L);
        }
        this.holder = e40.getInstance(abstractC0400d2.getObjectAt(i));
        this.issuer = C0138bl.getInstance(abstractC0400d2.getObjectAt(i + 1));
        this.signature = C1168r5.getInstance(abstractC0400d2.getObjectAt(i + 2));
        this.serialNumber = C0155c0.getInstance(abstractC0400d2.getObjectAt(i + 3));
        this.attrCertValidityPeriod = C0139bm.getInstance(abstractC0400d2.getObjectAt(i + 4));
        this.attributes = AbstractC0400d2.getInstance(abstractC0400d2.getObjectAt(i + 5));
        for (int i2 = i + 6; i2 < abstractC0400d2.size(); i2++) {
            InterfaceC0117b0 objectAt = abstractC0400d2.getObjectAt(i2);
            if (objectAt instanceof AbstractC0007a6) {
                this.issuerUniqueID = AbstractC0007a6.getInstance(abstractC0400d2.getObjectAt(i2));
            } else if ((objectAt instanceof AbstractC0400d2) || (objectAt instanceof C1454ye)) {
                this.extensions = C1454ye.getInstance(abstractC0400d2.getObjectAt(i2));
            }
        }
    }

    public static C0143bq getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C0139bm getAttrCertValidityPeriod() {
        return this.attrCertValidityPeriod;
    }

    public AbstractC0400d2 getAttributes() {
        return this.attributes;
    }

    public C1454ye getExtensions() {
        return this.extensions;
    }

    public e40 getHolder() {
        return this.holder;
    }

    public C0138bl getIssuer() {
        return this.issuer;
    }

    public AbstractC0007a6 getIssuerUniqueID() {
        return this.issuerUniqueID;
    }

    public C0155c0 getSerialNumber() {
        return this.serialNumber;
    }

    public C1168r5 getSignature() {
        return this.signature;
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(9);
        if (!this.version.hasValue(0)) {
            c0118b1.add(this.version);
        }
        c0118b1.add(this.holder);
        c0118b1.add(this.issuer);
        c0118b1.add(this.signature);
        c0118b1.add(this.serialNumber);
        c0118b1.add(this.attrCertValidityPeriod);
        c0118b1.add(this.attributes);
        AbstractC0007a6 abstractC0007a6 = this.issuerUniqueID;
        if (abstractC0007a6 != null) {
            c0118b1.add(abstractC0007a6);
        }
        C1454ye c1454ye = this.extensions;
        if (c1454ye != null) {
            c0118b1.add(c1454ye);
        }
        return new C1064pc(c0118b1);
    }

    public static C0143bq getInstance(Object obj) {
        if (obj instanceof C0143bq) {
            return (C0143bq) obj;
        }
        if (obj != null) {
            return new C0143bq(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
