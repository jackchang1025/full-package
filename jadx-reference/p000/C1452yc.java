package p000;

import java.io.IOException;

/* renamed from: yc */
/* loaded from: classes2.dex */
public class C1452yc extends AbstractC0158c3 {
    private boolean critical;
    private C0160c5 extnId;
    private AbstractC0161c6 value;
    public static final C0160c5 subjectDirectoryAttributes = AbstractC0003a2.m22a3("2.5.29.9");
    public static final C0160c5 subjectKeyIdentifier = AbstractC0003a2.m22a3("2.5.29.14");
    public static final C0160c5 keyUsage = AbstractC0003a2.m22a3("2.5.29.15");
    public static final C0160c5 privateKeyUsagePeriod = AbstractC0003a2.m22a3("2.5.29.16");
    public static final C0160c5 subjectAlternativeName = AbstractC0003a2.m22a3("2.5.29.17");
    public static final C0160c5 issuerAlternativeName = AbstractC0003a2.m22a3("2.5.29.18");
    public static final C0160c5 basicConstraints = AbstractC0003a2.m22a3("2.5.29.19");
    public static final C0160c5 cRLNumber = AbstractC0003a2.m22a3("2.5.29.20");
    public static final C0160c5 reasonCode = AbstractC0003a2.m22a3("2.5.29.21");
    public static final C0160c5 instructionCode = AbstractC0003a2.m22a3("2.5.29.23");
    public static final C0160c5 invalidityDate = AbstractC0003a2.m22a3("2.5.29.24");
    public static final C0160c5 deltaCRLIndicator = AbstractC0003a2.m22a3("2.5.29.27");
    public static final C0160c5 issuingDistributionPoint = AbstractC0003a2.m22a3("2.5.29.28");
    public static final C0160c5 certificateIssuer = AbstractC0003a2.m22a3("2.5.29.29");
    public static final C0160c5 nameConstraints = AbstractC0003a2.m22a3("2.5.29.30");
    public static final C0160c5 cRLDistributionPoints = AbstractC0003a2.m22a3("2.5.29.31");
    public static final C0160c5 certificatePolicies = AbstractC0003a2.m22a3("2.5.29.32");
    public static final C0160c5 policyMappings = AbstractC0003a2.m22a3("2.5.29.33");
    public static final C0160c5 authorityKeyIdentifier = AbstractC0003a2.m22a3("2.5.29.35");
    public static final C0160c5 policyConstraints = AbstractC0003a2.m22a3("2.5.29.36");
    public static final C0160c5 extendedKeyUsage = AbstractC0003a2.m22a3("2.5.29.37");
    public static final C0160c5 freshestCRL = AbstractC0003a2.m22a3("2.5.29.46");
    public static final C0160c5 inhibitAnyPolicy = AbstractC0003a2.m22a3("2.5.29.54");
    public static final C0160c5 authorityInfoAccess = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.1");
    public static final C0160c5 subjectInfoAccess = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.11");
    public static final C0160c5 logoType = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.12");
    public static final C0160c5 biometricInfo = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.2");
    public static final C0160c5 qCStatements = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.3");
    public static final C0160c5 auditIdentity = AbstractC0003a2.m22a3("1.3.6.1.5.5.7.1.4");
    public static final C0160c5 noRevAvail = AbstractC0003a2.m22a3("2.5.29.56");
    public static final C0160c5 targetInformation = AbstractC0003a2.m22a3("2.5.29.55");
    public static final C0160c5 expiredCertsOnCRL = AbstractC0003a2.m22a3("2.5.29.60");

    public C1452yc(C0160c5 c0160c5, C0009a8 c0009a8, AbstractC0161c6 abstractC0161c6) {
        this(c0160c5, c0009a8.isTrue(), abstractC0161c6);
    }

    private static AbstractC0164c9 convertValueToObject(C1452yc c1452yc) throws IllegalArgumentException {
        try {
            return AbstractC0164c9.fromByteArray(c1452yc.getExtnValue().getOctets());
        } catch (IOException e) {
            throw new IllegalArgumentException("can't convert extension: " + e);
        }
    }

    public static C1452yc create(C0160c5 c0160c5, boolean z, InterfaceC0117b0 interfaceC0117b0) throws IOException {
        return new C1452yc(c0160c5, z, interfaceC0117b0.toASN1Primitive().getEncoded());
    }

    public static C1452yc getInstance(Object obj) {
        if (obj instanceof C1452yc) {
            return (C1452yc) obj;
        }
        if (obj != null) {
            return new C1452yc(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    @Override // p000.AbstractC0158c3
    public boolean equals(Object obj) {
        if (!(obj instanceof C1452yc)) {
            return false;
        }
        C1452yc c1452yc = (C1452yc) obj;
        return c1452yc.getExtnId().equals((AbstractC0164c9) getExtnId()) && c1452yc.getExtnValue().equals((AbstractC0164c9) getExtnValue()) && c1452yc.isCritical() == isCritical();
    }

    public C0160c5 getExtnId() {
        return this.extnId;
    }

    public AbstractC0161c6 getExtnValue() {
        return this.value;
    }

    public InterfaceC0117b0 getParsedValue() {
        return convertValueToObject(this);
    }

    @Override // p000.AbstractC0158c3
    public int hashCode() {
        return isCritical() ? getExtnValue().hashCode() ^ getExtnId().hashCode() : ~(getExtnValue().hashCode() ^ getExtnId().hashCode());
    }

    public boolean isCritical() {
        return this.critical;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(3);
        c0118b1.add(this.extnId);
        if (this.critical) {
            c0118b1.add(C0009a8.getInstance(true));
        }
        c0118b1.add(this.value);
        return new C1064pc(c0118b1);
    }

    public C1452yc(C0160c5 c0160c5, boolean z, AbstractC0161c6 abstractC0161c6) {
        this.extnId = c0160c5;
        this.critical = z;
        this.value = abstractC0161c6;
    }

    public C1452yc(C0160c5 c0160c5, boolean z, byte[] bArr) {
        this(c0160c5, z, new C1048oy(bArr));
    }

    private C1452yc(AbstractC0400d2 abstractC0400d2) {
        InterfaceC0117b0 objectAt;
        if (abstractC0400d2.size() == 2) {
            this.extnId = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
            this.critical = false;
            objectAt = abstractC0400d2.getObjectAt(1);
        } else {
            if (abstractC0400d2.size() != 3) {
                throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d2.size());
            }
            this.extnId = C0160c5.getInstance(abstractC0400d2.getObjectAt(0));
            this.critical = C0009a8.getInstance(abstractC0400d2.getObjectAt(1)).isTrue();
            objectAt = abstractC0400d2.getObjectAt(2);
        }
        this.value = AbstractC0161c6.getInstance(objectAt);
    }
}
