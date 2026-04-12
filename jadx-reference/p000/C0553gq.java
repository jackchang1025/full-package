package p000;

import java.util.Enumeration;
import p000.s41;

/* renamed from: gq */
/* loaded from: classes2.dex */
public class C0553gq extends AbstractC0158c3 {
    int hashCodeValue;
    boolean isHashCodeSet = false;
    AbstractC0007a6 sig;
    C1168r5 sigAlgId;
    s41 tbsCertList;

    private C0553gq(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() != 3) {
            throw new IllegalArgumentException("sequence wrong size for CertificateList");
        }
        this.tbsCertList = s41.getInstance(abstractC0400d2.getObjectAt(0));
        this.sigAlgId = C1168r5.getInstance(abstractC0400d2.getObjectAt(1));
        this.sig = C0991oo.getInstance((Object) abstractC0400d2.getObjectAt(2));
    }

    public static C0553gq getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public kh1 getIssuer() {
        return this.tbsCertList.getIssuer();
    }

    public p61 getNextUpdate() {
        return this.tbsCertList.getNextUpdate();
    }

    public Enumeration getRevokedCertificateEnumeration() {
        return this.tbsCertList.getRevokedCertificateEnumeration();
    }

    public s41.C1207a0[] getRevokedCertificates() {
        return this.tbsCertList.getRevokedCertificates();
    }

    public AbstractC0007a6 getSignature() {
        return this.sig;
    }

    public C1168r5 getSignatureAlgorithm() {
        return this.sigAlgId;
    }

    public s41 getTBSCertList() {
        return this.tbsCertList;
    }

    public p61 getThisUpdate() {
        return this.tbsCertList.getThisUpdate();
    }

    public int getVersionNumber() {
        return this.tbsCertList.getVersionNumber();
    }

    @Override // p000.AbstractC0158c3
    public int hashCode() {
        if (!this.isHashCodeSet) {
            this.hashCodeValue = super.hashCode();
            this.isHashCodeSet = true;
        }
        return this.hashCodeValue;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(this.tbsCertList);
        c0118b1.add(this.sigAlgId);
        c0118b1.add(this.sig);
        return new C1064pc(c0118b1);
    }

    public static C0553gq getInstance(Object obj) {
        if (obj instanceof C0553gq) {
            return (C0553gq) obj;
        }
        if (obj != null) {
            return new C0553gq(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
