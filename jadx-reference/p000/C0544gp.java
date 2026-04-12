package p000;

/* renamed from: gp */
/* loaded from: classes2.dex */
public class C0544gp extends AbstractC0158c3 {
    AbstractC0400d2 seq;
    AbstractC0007a6 sig;
    C1168r5 sigAlgId;
    t41 tbsCert;

    private C0544gp(AbstractC0400d2 abstractC0400d2) {
        this.seq = abstractC0400d2;
        if (abstractC0400d2.size() != 3) {
            throw new IllegalArgumentException("sequence wrong size for a certificate");
        }
        this.tbsCert = t41.getInstance(abstractC0400d2.getObjectAt(0));
        this.sigAlgId = C1168r5.getInstance(abstractC0400d2.getObjectAt(1));
        this.sig = AbstractC0007a6.getInstance(abstractC0400d2.getObjectAt(2));
    }

    public static C0544gp getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public p61 getEndDate() {
        return this.tbsCert.getEndDate();
    }

    public kh1 getIssuer() {
        return this.tbsCert.getIssuer();
    }

    public C0155c0 getSerialNumber() {
        return this.tbsCert.getSerialNumber();
    }

    public AbstractC0007a6 getSignature() {
        return this.sig;
    }

    public C1168r5 getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public p61 getStartDate() {
        return this.tbsCert.getStartDate();
    }

    public kh1 getSubject() {
        return this.tbsCert.getSubject();
    }

    public u21 getSubjectPublicKeyInfo() {
        return this.tbsCert.getSubjectPublicKeyInfo();
    }

    public t41 getTBSCertificate() {
        return this.tbsCert;
    }

    public C0155c0 getVersion() {
        return this.tbsCert.getVersion();
    }

    public int getVersionNumber() {
        return this.tbsCert.getVersionNumber();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.seq;
    }

    public static C0544gp getInstance(Object obj) {
        if (obj instanceof C0544gp) {
            return (C0544gp) obj;
        }
        if (obj != null) {
            return new C0544gp(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
