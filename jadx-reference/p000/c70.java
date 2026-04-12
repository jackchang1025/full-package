package p000;

import org.bouncycastle.util.Strings;

/* loaded from: classes2.dex */
public class c70 extends AbstractC0158c3 {
    private C1268tp distributionPoint;
    private boolean indirectCRL;
    private boolean onlyContainsAttributeCerts;
    private boolean onlyContainsCACerts;
    private boolean onlyContainsUserCerts;
    private dq0 onlySomeReasons;
    private AbstractC0400d2 seq;

    private c70(AbstractC0400d2 abstractC0400d2) {
        this.seq = abstractC0400d2;
        for (int i = 0; i != abstractC0400d2.size(); i++) {
            AbstractC0439e0 abstractC0439e0 = AbstractC0439e0.getInstance(abstractC0400d2.getObjectAt(i));
            int tagNo = abstractC0439e0.getTagNo();
            if (tagNo == 0) {
                this.distributionPoint = C1268tp.getInstance(abstractC0439e0, true);
            } else if (tagNo == 1) {
                this.onlyContainsUserCerts = C0009a8.getInstance(abstractC0439e0, false).isTrue();
            } else if (tagNo == 2) {
                this.onlyContainsCACerts = C0009a8.getInstance(abstractC0439e0, false).isTrue();
            } else if (tagNo == 3) {
                this.onlySomeReasons = new dq0(AbstractC0007a6.getInstance(abstractC0439e0, false));
            } else if (tagNo == 4) {
                this.indirectCRL = C0009a8.getInstance(abstractC0439e0, false).isTrue();
            } else {
                if (tagNo != 5) {
                    throw new IllegalArgumentException("unknown tag in IssuingDistributionPoint");
                }
                this.onlyContainsAttributeCerts = C0009a8.getInstance(abstractC0439e0, false).isTrue();
            }
        }
    }

    private void appendObject(StringBuffer stringBuffer, String str, String str2, String str3) {
        stringBuffer.append("    ");
        stringBuffer.append(str2);
        stringBuffer.append(":");
        stringBuffer.append(str);
        stringBuffer.append("    ");
        stringBuffer.append("    ");
        stringBuffer.append(str3);
        stringBuffer.append(str);
    }

    private String booleanToString(boolean z) {
        return z ? "true" : "false";
    }

    public static c70 getInstance(AbstractC0439e0 abstractC0439e0, boolean z) {
        return getInstance(AbstractC0400d2.getInstance(abstractC0439e0, z));
    }

    public C1268tp getDistributionPoint() {
        return this.distributionPoint;
    }

    public dq0 getOnlySomeReasons() {
        return this.onlySomeReasons;
    }

    public boolean isIndirectCRL() {
        return this.indirectCRL;
    }

    public boolean onlyContainsAttributeCerts() {
        return this.onlyContainsAttributeCerts;
    }

    public boolean onlyContainsCACerts() {
        return this.onlyContainsCACerts;
    }

    public boolean onlyContainsUserCerts() {
        return this.onlyContainsUserCerts;
    }

    @Override // p000.AbstractC0158c3, p000.InterfaceC0117b0
    public AbstractC0164c9 toASN1Primitive() {
        return this.seq;
    }

    public String toString() {
        String strLineSeparator = Strings.lineSeparator();
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append("IssuingDistributionPoint: [");
        stringBuffer.append(strLineSeparator);
        C1268tp c1268tp = this.distributionPoint;
        if (c1268tp != null) {
            appendObject(stringBuffer, strLineSeparator, "distributionPoint", c1268tp.toString());
        }
        boolean z = this.onlyContainsUserCerts;
        if (z) {
            appendObject(stringBuffer, strLineSeparator, "onlyContainsUserCerts", booleanToString(z));
        }
        boolean z2 = this.onlyContainsCACerts;
        if (z2) {
            appendObject(stringBuffer, strLineSeparator, "onlyContainsCACerts", booleanToString(z2));
        }
        dq0 dq0Var = this.onlySomeReasons;
        if (dq0Var != null) {
            appendObject(stringBuffer, strLineSeparator, "onlySomeReasons", dq0Var.toString());
        }
        boolean z3 = this.onlyContainsAttributeCerts;
        if (z3) {
            appendObject(stringBuffer, strLineSeparator, "onlyContainsAttributeCerts", booleanToString(z3));
        }
        boolean z4 = this.indirectCRL;
        if (z4) {
            appendObject(stringBuffer, strLineSeparator, "indirectCRL", booleanToString(z4));
        }
        stringBuffer.append("]");
        stringBuffer.append(strLineSeparator);
        return stringBuffer.toString();
    }

    public c70(C1268tp c1268tp, boolean z, boolean z2) {
        this(c1268tp, false, false, null, z, z2);
    }

    public static c70 getInstance(Object obj) {
        if (obj instanceof c70) {
            return (c70) obj;
        }
        if (obj != null) {
            return new c70(AbstractC0400d2.getInstance(obj));
        }
        return null;
    }

    public c70(C1268tp c1268tp, boolean z, boolean z2, dq0 dq0Var, boolean z3, boolean z4) {
        this.distributionPoint = c1268tp;
        this.indirectCRL = z3;
        this.onlyContainsAttributeCerts = z4;
        this.onlyContainsCACerts = z2;
        this.onlyContainsUserCerts = z;
        this.onlySomeReasons = dq0Var;
        C0118b1 c0118b1 = new C0118b1(6);
        if (c1268tp != null) {
            c0118b1.add(new C1067pf(true, 0, (InterfaceC0117b0) c1268tp));
        }
        if (z) {
            c0118b1.add(new C1067pf(false, 1, (InterfaceC0117b0) C0009a8.getInstance(true)));
        }
        if (z2) {
            c0118b1.add(new C1067pf(false, 2, (InterfaceC0117b0) C0009a8.getInstance(true)));
        }
        if (dq0Var != null) {
            c0118b1.add(new C1067pf(false, 3, (InterfaceC0117b0) dq0Var));
        }
        if (z3) {
            c0118b1.add(new C1067pf(false, 4, (InterfaceC0117b0) C0009a8.getInstance(true)));
        }
        if (z4) {
            c0118b1.add(new C1067pf(false, 5, (InterfaceC0117b0) C0009a8.getInstance(true)));
        }
        this.seq = new C1064pc(c0118b1);
    }
}
