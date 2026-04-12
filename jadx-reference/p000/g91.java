package p000;

/* loaded from: classes2.dex */
public class g91 {
    private boolean altNamePresentAndCritical;
    p61 endDate;
    C1454ye extensions;
    kh1 issuer;
    private C0991oo issuerUniqueID;
    C0155c0 serialNumber;
    C1168r5 signature;
    p61 startDate;
    kh1 subject;
    u21 subjectPublicKeyInfo;
    private C0991oo subjectUniqueID;
    C1067pf version = new C1067pf(true, 0, (InterfaceC0117b0) new C0155c0(2));

    public t41 generateTBSCertificate() {
        if (this.serialNumber == null || this.signature == null || this.issuer == null || this.startDate == null || this.endDate == null || ((this.subject == null && !this.altNamePresentAndCritical) || this.subjectPublicKeyInfo == null)) {
            throw new IllegalStateException("not all mandatory fields set in V3 TBScertificate generator");
        }
        C0118b1 c0118b1 = new C0118b1(10);
        c0118b1.add(this.version);
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
        C0991oo c0991oo = this.issuerUniqueID;
        if (c0991oo != null) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) c0991oo));
        }
        C0991oo c0991oo2 = this.subjectUniqueID;
        if (c0991oo2 != null) {
            c0118b1.add(new C1067pf(false, 2, (InterfaceC0117b0) c0991oo2));
        }
        C1454ye c1454ye = this.extensions;
        if (c1454ye != null) {
            c0118b1.add(new C1067pf(true, 3, (InterfaceC0117b0) c1454ye));
        }
        return t41.getInstance(new C1064pc(c0118b1));
    }

    public void setEndDate(C0442e3 c0442e3) {
        this.endDate = new p61(c0442e3);
    }

    public void setExtensions(C1454ye c1454ye) {
        C1452yc extension;
        this.extensions = c1454ye;
        if (c1454ye == null || (extension = c1454ye.getExtension(C1452yc.subjectAlternativeName)) == null || !extension.isCritical()) {
            return;
        }
        this.altNamePresentAndCritical = true;
    }

    public void setIssuer(kh1 kh1Var) {
        this.issuer = kh1Var;
    }

    public void setIssuerUniqueID(C0991oo c0991oo) {
        this.issuerUniqueID = c0991oo;
    }

    public void setSerialNumber(C0155c0 c0155c0) {
        this.serialNumber = c0155c0;
    }

    public void setSignature(C1168r5 c1168r5) {
        this.signature = c1168r5;
    }

    public void setStartDate(C0442e3 c0442e3) {
        this.startDate = new p61(c0442e3);
    }

    public void setSubject(kh1 kh1Var) {
        this.subject = kh1Var;
    }

    public void setSubjectPublicKeyInfo(u21 u21Var) {
        this.subjectPublicKeyInfo = u21Var;
    }

    public void setSubjectUniqueID(C0991oo c0991oo) {
        this.subjectUniqueID = c0991oo;
    }

    public void setEndDate(p61 p61Var) {
        this.endDate = p61Var;
    }

    public void setExtensions(sh1 sh1Var) {
        setExtensions(C1454ye.getInstance(sh1Var));
    }

    public void setIssuer(th1 th1Var) {
        this.issuer = kh1.getInstance(th1Var);
    }

    public void setStartDate(p61 p61Var) {
        this.startDate = p61Var;
    }

    public void setSubject(th1 th1Var) {
        this.subject = kh1.getInstance(th1Var.toASN1Primitive());
    }
}
