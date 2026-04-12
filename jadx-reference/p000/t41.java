package p000;

/* loaded from: classes2.dex */
public class t41 extends AbstractC0158c3 {
    p61 endDate;
    C1454ye extensions;
    kh1 issuer;
    AbstractC0007a6 issuerUniqueId;
    AbstractC0400d2 seq;
    C0155c0 serialNumber;
    C1168r5 signature;
    p61 startDate;
    kh1 subject;
    u21 subjectPublicKeyInfo;
    AbstractC0007a6 subjectUniqueId;
    C0155c0 version;

    private t41(AbstractC0400d2 abstractC0400d2) {
        int i;
        boolean z;
        boolean z2;
        this.seq = abstractC0400d2;
        if (abstractC0400d2.getObjectAt(0) instanceof AbstractC0439e0) {
            this.version = C0155c0.getInstance((AbstractC0439e0) abstractC0400d2.getObjectAt(0), true);
            i = 0;
        } else {
            this.version = new C0155c0(0L);
            i = -1;
        }
        if (this.version.hasValue(0)) {
            z2 = false;
            z = true;
        } else if (this.version.hasValue(1)) {
            z = false;
            z2 = true;
        } else {
            if (!this.version.hasValue(2)) {
                throw new IllegalArgumentException("version number not recognised");
            }
            z = false;
            z2 = false;
        }
        this.serialNumber = C0155c0.getInstance(abstractC0400d2.getObjectAt(i + 1));
        this.signature = C1168r5.getInstance(abstractC0400d2.getObjectAt(i + 2));
        this.issuer = kh1.getInstance(abstractC0400d2.getObjectAt(i + 3));
        AbstractC0400d2 abstractC0400d22 = (AbstractC0400d2) abstractC0400d2.getObjectAt(i + 4);
        this.startDate = p61.getInstance(abstractC0400d22.getObjectAt(0));
        this.endDate = p61.getInstance(abstractC0400d22.getObjectAt(1));
        this.subject = kh1.getInstance(abstractC0400d2.getObjectAt(i + 5));
        int i2 = i + 6;
        this.subjectPublicKeyInfo = u21.getInstance(abstractC0400d2.getObjectAt(i2));
        int size = (abstractC0400d2.size() - i2) - 1;
        if (size != 0 && z) {
            throw new IllegalArgumentException("version 1 certificate contains extra data");
        }
        while (size > 0) {
            AbstractC0439e0 abstractC0439e0 = (AbstractC0439e0) abstractC0400d2.getObjectAt(i2 + size);
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo == 1) {
                this.issuerUniqueId = C0991oo.getInstance(abstractC0439e0, false);
            } else if (tagNo == 2) {
                this.subjectUniqueId = C0991oo.getInstance(abstractC0439e0, false);
            } else {
                if (tagNo != 3) {
                    throw new IllegalArgumentException("Unknown tag encountered in structure: " + abstractC0439e0.getTagNo());
                }
                if (z2) {
                    throw new IllegalArgumentException("version 2 certificate cannot contain extensions");
                }
                this.extensions = C1454ye.getInstance(AbstractC0400d2.getInstance(abstractC0439e0, true));
            }
            size--;
        }
    }

    public static t41 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public p61 getEndDate() {
        return this.endDate;
    }

    public C1454ye getExtensions() {
        return this.extensions;
    }

    public kh1 getIssuer() {
        return this.issuer;
    }

    public AbstractC0007a6 getIssuerUniqueId() {
        return this.issuerUniqueId;
    }

    public C0155c0 getSerialNumber() {
        return this.serialNumber;
    }

    public C1168r5 getSignature() {
        return this.signature;
    }

    public p61 getStartDate() {
        return this.startDate;
    }

    public kh1 getSubject() {
        return this.subject;
    }

    public u21 getSubjectPublicKeyInfo() {
        return this.subjectPublicKeyInfo;
    }

    public AbstractC0007a6 getSubjectUniqueId() {
        return this.subjectUniqueId;
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    public int getVersionNumber() {
        return this.version.intValueExact() + 1;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        if (ap0.getPropertyValue("org.bouncycastle.x509.allow_non-der_tbscert") == null) {
            return this.seq;
        }
        if (ap0.isOverrideSet("org.bouncycastle.x509.allow_non-der_tbscert")) {
            return this.seq;
        }
        C0118b1 c0118b1 = new C0118b1();
        if (!this.version.hasValue(0)) {
            c0118b1.add(new C1067pf(true, 0, (InterfaceC0117b0) this.version));
        }
        c0118b1.add(this.serialNumber);
        c0118b1.add(this.signature);
        c0118b1.add(this.issuer);
        C0118b1 c0118b12 = new C0118b1(2);
        c0118b12.add(this.startDate);
        c0118b12.add(this.endDate);
        c0118b1.add(new C1064pc(c0118b12));
        InterfaceC0117b0 c1064pc = this.subject;
        if (c1064pc == null) {
            c1064pc = new C1064pc();
        }
        c0118b1.add(c1064pc);
        c0118b1.add(this.subjectPublicKeyInfo);
        AbstractC0007a6 abstractC0007a6 = this.issuerUniqueId;
        if (abstractC0007a6 != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) abstractC0007a6));
        }
        AbstractC0007a6 abstractC0007a62 = this.subjectUniqueId;
        if (abstractC0007a62 != null) {
            c0118b1.add(new C1067pf(false, 2, (InterfaceC0117b0) abstractC0007a62));
        }
        C1454ye c1454ye = this.extensions;
        if (c1454ye != null) {
            c0118b1.add(new C1067pf(true, 3, (InterfaceC0117b0) c1454ye));
        }
        return new C1064pc(c0118b1);
    }

    public static t41 getInstance(Object obj) {
        if (obj instanceof t41) {
            return (t41) obj;
        }
        if (obj != null) {
            return new t41(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
