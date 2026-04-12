package p000;

import java.util.Enumeration;
import java.util.Hashtable;
import java.util.Vector;

/* loaded from: classes2.dex */
public class sh1 extends AbstractC0158c3 {
    private Hashtable extensions;
    private Vector ordering;
    public static final C0160c5 SubjectDirectoryAttributes = new C0160c5("2.5.29.9");
    public static final C0160c5 SubjectKeyIdentifier = new C0160c5("2.5.29.14");
    public static final C0160c5 KeyUsage = new C0160c5("2.5.29.15");
    public static final C0160c5 PrivateKeyUsagePeriod = new C0160c5("2.5.29.16");
    public static final C0160c5 SubjectAlternativeName = new C0160c5("2.5.29.17");
    public static final C0160c5 IssuerAlternativeName = new C0160c5("2.5.29.18");
    public static final C0160c5 BasicConstraints = new C0160c5("2.5.29.19");
    public static final C0160c5 CRLNumber = new C0160c5("2.5.29.20");
    public static final C0160c5 ReasonCode = new C0160c5("2.5.29.21");
    public static final C0160c5 InstructionCode = new C0160c5("2.5.29.23");
    public static final C0160c5 InvalidityDate = new C0160c5("2.5.29.24");
    public static final C0160c5 DeltaCRLIndicator = new C0160c5("2.5.29.27");
    public static final C0160c5 IssuingDistributionPoint = new C0160c5("2.5.29.28");
    public static final C0160c5 CertificateIssuer = new C0160c5("2.5.29.29");
    public static final C0160c5 NameConstraints = new C0160c5("2.5.29.30");
    public static final C0160c5 CRLDistributionPoints = new C0160c5("2.5.29.31");
    public static final C0160c5 CertificatePolicies = new C0160c5("2.5.29.32");
    public static final C0160c5 PolicyMappings = new C0160c5("2.5.29.33");
    public static final C0160c5 AuthorityKeyIdentifier = new C0160c5("2.5.29.35");
    public static final C0160c5 PolicyConstraints = new C0160c5("2.5.29.36");
    public static final C0160c5 ExtendedKeyUsage = new C0160c5("2.5.29.37");
    public static final C0160c5 FreshestCRL = new C0160c5("2.5.29.46");
    public static final C0160c5 InhibitAnyPolicy = new C0160c5("2.5.29.54");
    public static final C0160c5 AuthorityInfoAccess = new C0160c5("1.3.6.1.5.5.7.1.1");
    public static final C0160c5 SubjectInfoAccess = new C0160c5("1.3.6.1.5.5.7.1.11");
    public static final C0160c5 LogoType = new C0160c5("1.3.6.1.5.5.7.1.12");
    public static final C0160c5 BiometricInfo = new C0160c5("1.3.6.1.5.5.7.1.2");
    public static final C0160c5 QCStatements = new C0160c5("1.3.6.1.5.5.7.1.3");
    public static final C0160c5 AuditIdentity = new C0160c5("1.3.6.1.5.5.7.1.4");
    public static final C0160c5 NoRevAvail = new C0160c5("2.5.29.56");
    public static final C0160c5 TargetInformation = new C0160c5("2.5.29.55");

    public sh1(AbstractC0400d2 abstractC0400d2) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration objects = abstractC0400d2.getObjects();
        while (objects.hasMoreElements()) {
            AbstractC0400d2 abstractC0400d22 = AbstractC0400d2.getInstance(objects.nextElement());
            if (abstractC0400d22.size() == 3) {
                this.extensions.put(abstractC0400d22.getObjectAt(0), new qh1(C0009a8.getInstance(abstractC0400d22.getObjectAt(1)), AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(2))));
            } else {
                if (abstractC0400d22.size() != 2) {
                    throw new IllegalArgumentException("Bad sequence size: " + abstractC0400d22.size());
                }
                this.extensions.put(abstractC0400d22.getObjectAt(0), new qh1(false, AbstractC0161c6.getInstance(abstractC0400d22.getObjectAt(1))));
            }
            this.ordering.addElement(abstractC0400d22.getObjectAt(0));
        }
    }

    public static sh1 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    private C0160c5[] toOidArray(Vector vector) {
        int size = vector.size();
        C0160c5[] c0160c5Arr = new C0160c5[size];
        for (int i = 0; i != size; i++) {
            c0160c5Arr[i] = (C0160c5) vector.elementAt(i);
        }
        return c0160c5Arr;
    }

    public boolean equivalent(sh1 sh1Var) {
        if (this.extensions.size() != sh1Var.extensions.size()) {
            return false;
        }
        Enumeration enumerationKeys = this.extensions.keys();
        while (enumerationKeys.hasMoreElements()) {
            Object objNextElement = enumerationKeys.nextElement();
            if (!this.extensions.get(objNextElement).equals(sh1Var.extensions.get(objNextElement))) {
                return false;
            }
        }
        return true;
    }

    public C0160c5[] getCriticalExtensionOIDs() {
        return getExtensionOIDs(true);
    }

    public qh1 getExtension(C0160c5 c0160c5) {
        return (qh1) this.extensions.get(c0160c5);
    }

    public C0160c5[] getExtensionOIDs() {
        return toOidArray(this.ordering);
    }

    public C0160c5[] getNonCriticalExtensionOIDs() {
        return getExtensionOIDs(false);
    }

    public Enumeration oids() {
        return this.ordering.elements();
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        C0118b1 c0118b1 = new C0118b1(this.ordering.size());
        Enumeration enumerationElements = this.ordering.elements();
        while (enumerationElements.hasMoreElements()) {
            C0118b1 c0118b12 = new C0118b1(3);
            C0160c5 c0160c5 = (C0160c5) enumerationElements.nextElement();
            qh1 qh1Var = (qh1) this.extensions.get(c0160c5);
            c0118b12.add(c0160c5);
            if (qh1Var.isCritical()) {
                c0118b12.add(C0009a8.TRUE);
            }
            c0118b12.add(qh1Var.getValue());
            c0118b1.add(new C1064pc(c0118b12));
        }
        return new C1064pc(c0118b1);
    }

    public sh1(Hashtable hashtable) {
        this((Vector) null, hashtable);
    }

    private C0160c5[] getExtensionOIDs(boolean z) {
        Vector vector = new Vector();
        for (int i = 0; i != this.ordering.size(); i++) {
            Object objElementAt = this.ordering.elementAt(i);
            if (((qh1) this.extensions.get(objElementAt)).isCritical() == z) {
                vector.addElement(objElementAt);
            }
        }
        return toOidArray(vector);
    }

    public static sh1 getInstance(Object obj) {
        if (obj == null || (obj instanceof sh1)) {
            return (sh1) obj;
        }
        if (obj instanceof AbstractC0400d2) {
            return new sh1((AbstractC0400d2) obj);
        }
        if (obj instanceof C1454ye) {
            return new sh1((AbstractC0400d2) ((C1454ye) obj).toASN1Primitive());
        }
        if (obj instanceof AbstractC0439e0) {
            return getInstance(((AbstractC0439e0) obj).getObject());
        }
        throw new IllegalArgumentException(AbstractC0003a2.m28a9(obj, "illegal object in getInstance: "));
    }

    public sh1(Vector vector, Hashtable hashtable) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration enumerationKeys = vector == null ? hashtable.keys() : vector.elements();
        while (enumerationKeys.hasMoreElements()) {
            this.ordering.addElement(C0160c5.getInstance(enumerationKeys.nextElement()));
        }
        Enumeration enumerationElements = this.ordering.elements();
        while (enumerationElements.hasMoreElements()) {
            C0160c5 c0160c5 = C0160c5.getInstance(enumerationElements.nextElement());
            this.extensions.put(c0160c5, (qh1) hashtable.get(c0160c5));
        }
    }

    public sh1(Vector vector, Vector vector2) {
        this.extensions = new Hashtable();
        this.ordering = new Vector();
        Enumeration enumerationElements = vector.elements();
        while (enumerationElements.hasMoreElements()) {
            this.ordering.addElement(enumerationElements.nextElement());
        }
        Enumeration enumerationElements2 = this.ordering.elements();
        int i = 0;
        while (enumerationElements2.hasMoreElements()) {
            this.extensions.put((C0160c5) enumerationElements2.nextElement(), (qh1) vector2.elementAt(i));
            i++;
        }
    }
}
