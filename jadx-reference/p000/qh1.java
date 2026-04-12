package p000;

import java.io.IOException;

/* loaded from: classes2.dex */
public class qh1 {
    boolean critical;
    AbstractC0161c6 value;
    public static final C0160c5 subjectDirectoryAttributes = new C0160c5("2.5.29.9");
    public static final C0160c5 subjectKeyIdentifier = new C0160c5("2.5.29.14");
    public static final C0160c5 keyUsage = new C0160c5("2.5.29.15");
    public static final C0160c5 privateKeyUsagePeriod = new C0160c5("2.5.29.16");
    public static final C0160c5 subjectAlternativeName = new C0160c5("2.5.29.17");
    public static final C0160c5 issuerAlternativeName = new C0160c5("2.5.29.18");
    public static final C0160c5 basicConstraints = new C0160c5("2.5.29.19");
    public static final C0160c5 cRLNumber = new C0160c5("2.5.29.20");
    public static final C0160c5 reasonCode = new C0160c5("2.5.29.21");
    public static final C0160c5 instructionCode = new C0160c5("2.5.29.23");
    public static final C0160c5 invalidityDate = new C0160c5("2.5.29.24");
    public static final C0160c5 deltaCRLIndicator = new C0160c5("2.5.29.27");
    public static final C0160c5 issuingDistributionPoint = new C0160c5("2.5.29.28");
    public static final C0160c5 certificateIssuer = new C0160c5("2.5.29.29");
    public static final C0160c5 nameConstraints = new C0160c5("2.5.29.30");
    public static final C0160c5 cRLDistributionPoints = new C0160c5("2.5.29.31");
    public static final C0160c5 certificatePolicies = new C0160c5("2.5.29.32");
    public static final C0160c5 policyMappings = new C0160c5("2.5.29.33");
    public static final C0160c5 authorityKeyIdentifier = new C0160c5("2.5.29.35");
    public static final C0160c5 policyConstraints = new C0160c5("2.5.29.36");
    public static final C0160c5 extendedKeyUsage = new C0160c5("2.5.29.37");
    public static final C0160c5 freshestCRL = new C0160c5("2.5.29.46");
    public static final C0160c5 inhibitAnyPolicy = new C0160c5("2.5.29.54");
    public static final C0160c5 authorityInfoAccess = new C0160c5("1.3.6.1.5.5.7.1.1");
    public static final C0160c5 subjectInfoAccess = new C0160c5("1.3.6.1.5.5.7.1.11");
    public static final C0160c5 logoType = new C0160c5("1.3.6.1.5.5.7.1.12");
    public static final C0160c5 biometricInfo = new C0160c5("1.3.6.1.5.5.7.1.2");
    public static final C0160c5 qCStatements = new C0160c5("1.3.6.1.5.5.7.1.3");
    public static final C0160c5 auditIdentity = new C0160c5("1.3.6.1.5.5.7.1.4");
    public static final C0160c5 noRevAvail = new C0160c5("2.5.29.56");
    public static final C0160c5 targetInformation = new C0160c5("2.5.29.55");

    public qh1(C0009a8 c0009a8, AbstractC0161c6 abstractC0161c6) {
        this.critical = c0009a8.isTrue();
        this.value = abstractC0161c6;
    }

    public static AbstractC0164c9 convertValueToObject(qh1 qh1Var) throws IllegalArgumentException {
        try {
            return AbstractC0164c9.fromByteArray(qh1Var.getValue().getOctets());
        } catch (IOException e) {
            throw new IllegalArgumentException("can't convert extension: " + e);
        }
    }

    public boolean equals(Object obj) {
        if (!(obj instanceof qh1)) {
            return false;
        }
        qh1 qh1Var = (qh1) obj;
        return qh1Var.getValue().equals((AbstractC0164c9) getValue()) && qh1Var.isCritical() == isCritical();
    }

    public InterfaceC0117b0 getParsedValue() {
        return convertValueToObject(this);
    }

    public AbstractC0161c6 getValue() {
        return this.value;
    }

    public int hashCode() {
        return isCritical() ? getValue().hashCode() : ~getValue().hashCode();
    }

    public boolean isCritical() {
        return this.critical;
    }

    public qh1(boolean z, AbstractC0161c6 abstractC0161c6) {
        this.critical = z;
        this.value = abstractC0161c6;
    }
}
