package p000;

import java.util.Enumeration;
import java.util.NoSuchElementException;

/* loaded from: classes2.dex */
public class s41 extends AbstractC0158c3 {
    C1454ye crlExtensions;
    kh1 issuer;
    p61 nextUpdate;
    AbstractC0400d2 revokedCertificates;
    C1168r5 signature;
    p61 thisUpdate;
    C0155c0 version;

    /* renamed from: s41$a0 */
    public static class C1207a0 extends AbstractC0158c3 {
        C1454ye crlEntryExtensions;
        AbstractC0400d2 seq;

        private C1207a0(AbstractC0400d2 abstractC0400d2) {
            if (abstractC0400d2.size() >= 2 && abstractC0400d2.size() <= 3) {
                this.seq = abstractC0400d2;
            } else {
                throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
            }
        }

        public static C1207a0 getInstance(Object obj) {
            if (obj instanceof C1207a0) {
                return (C1207a0) obj;
            }
            if (obj != null) {
                return new C1207a0(AbstractC0400d2.getInstance(obj));
            }
            return null;
        }

        public C1454ye getExtensions() {
            if (this.crlEntryExtensions == null && this.seq.size() == 3) {
                this.crlEntryExtensions = C1454ye.getInstance(this.seq.getObjectAt(2));
            }
            return this.crlEntryExtensions;
        }

        public p61 getRevocationDate() {
            return p61.getInstance(this.seq.getObjectAt(1));
        }

        public C0155c0 getUserCertificate() {
            return C0155c0.getInstance(this.seq.getObjectAt(0));
        }

        public boolean hasExtensions() {
            return this.seq.size() == 3;
        }

        @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
        public AbstractC0164c9 toASN1Primitive() {
            return this.seq;
        }
    }

    /* renamed from: s41$a1 */
    public class C1208a1 implements Enumeration {
        private C1208a1() {
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return false;
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            throw new NoSuchElementException("Empty Enumeration");
        }
    }

    /* renamed from: s41$a2 */
    public class C1209a2 implements Enumeration {

        /* renamed from: en */
        private final Enumeration f59864en;

        public C1209a2(Enumeration enumeration) {
            this.f59864en = enumeration;
        }

        @Override // java.util.Enumeration
        public boolean hasMoreElements() {
            return this.f59864en.hasMoreElements();
        }

        @Override // java.util.Enumeration
        public Object nextElement() {
            return C1207a0.getInstance(this.f59864en.nextElement());
        }
    }

    public s41(AbstractC0400d2 abstractC0400d2) {
        if (abstractC0400d2.size() < 3 || abstractC0400d2.size() > 7) {
            throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
        }
        int i = 0;
        if (abstractC0400d2.getObjectAt(0) instanceof C0155c0) {
            this.version = C0155c0.getInstance(abstractC0400d2.getObjectAt(0));
            i = 1;
        } else {
            this.version = null;
        }
        this.signature = C1168r5.getInstance(abstractC0400d2.getObjectAt(i));
        this.issuer = kh1.getInstance(abstractC0400d2.getObjectAt(i + 1));
        int i2 = i + 3;
        this.thisUpdate = p61.getInstance(abstractC0400d2.getObjectAt(i + 2));
        if (i2 < abstractC0400d2.size() && ((abstractC0400d2.getObjectAt(i2) instanceof C0442e3) || (abstractC0400d2.getObjectAt(i2) instanceof C0123b6) || (abstractC0400d2.getObjectAt(i2) instanceof p61))) {
            this.nextUpdate = p61.getInstance(abstractC0400d2.getObjectAt(i2));
            i2 = i + 4;
        }
        if (i2 < abstractC0400d2.size() && !(abstractC0400d2.getObjectAt(i2) instanceof AbstractC0439e0)) {
            this.revokedCertificates = AbstractC0400d2.getInstance(abstractC0400d2.getObjectAt(i2));
            i2++;
        }
        if (i2 >= abstractC0400d2.size() || !(abstractC0400d2.getObjectAt(i2) instanceof AbstractC0439e0)) {
            return;
        }
        this.crlExtensions = C1454ye.getInstance(AbstractC0400d2.getInstance((AbstractC0439e0) abstractC0400d2.getObjectAt(i2), true));
    }

    public static s41 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C1454ye getExtensions() {
        return this.crlExtensions;
    }

    public kh1 getIssuer() {
        return this.issuer;
    }

    public p61 getNextUpdate() {
        return this.nextUpdate;
    }

    public Enumeration getRevokedCertificateEnumeration() {
        AbstractC0400d2 abstractC0400d2 = this.revokedCertificates;
        return abstractC0400d2 == null ? new C1208a1() : new C1209a2(abstractC0400d2.getObjects());
    }

    public C1207a0[] getRevokedCertificates() {
        AbstractC0400d2 abstractC0400d2 = this.revokedCertificates;
        if (abstractC0400d2 == null) {
            return new C1207a0[0];
        }
        int size = abstractC0400d2.size();
        C1207a0[] c1207a0Arr = new C1207a0[size];
        for (int i = 0; i < size; i++) {
            c1207a0Arr[i] = C1207a0.getInstance(this.revokedCertificates.getObjectAt(i));
        }
        return c1207a0Arr;
    }

    public C1168r5 getSignature() {
        return this.signature;
    }

    public p61 getThisUpdate() {
        return this.thisUpdate;
    }

    public C0155c0 getVersion() {
        return this.version;
    }

    public int getVersionNumber() {
        C0155c0 c0155c0 = this.version;
        if (c0155c0 == null) {
            return 1;
        }
        return c0155c0.intValueExact() + 1;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(7);
        C0155c0 c0155c0 = this.version;
        if (c0155c0 != null) {
            c0118b1.add(c0155c0);
        }
        c0118b1.add(this.signature);
        c0118b1.add(this.issuer);
        c0118b1.add(this.thisUpdate);
        p61 p61Var = this.nextUpdate;
        if (p61Var != null) {
            c0118b1.add(p61Var);
        }
        AbstractC0400d2 abstractC0400d2 = this.revokedCertificates;
        if (abstractC0400d2 != null) {
            c0118b1.add(abstractC0400d2);
        }
        C1454ye c1454ye = this.crlExtensions;
        if (c1454ye != null) {
            c0118b1.add(new C1067pf(0, c1454ye));
        }
        return new C1064pc(c0118b1);
    }

    public static s41 getInstance(Object obj) {
        if (obj instanceof s41) {
            return (s41) obj;
        }
        if (obj != null) {
            return new s41(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }
}
