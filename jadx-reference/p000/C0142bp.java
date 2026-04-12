package p000;

import java.math.BigInteger;
import java.util.ArrayList;
import org.bouncycastle.cert.X509CertificateHolder;

/* renamed from: bp */
/* loaded from: classes2.dex */
public class C0142bp implements kz0 {
    private static InterfaceC1239sy digestCalculatorProvider;
    final e40 holder;

    public C0142bp(int i, C0160c5 c0160c5, C0160c5 c0160c52, byte[] bArr) {
        this.holder = new e40(new rk0(i, c0160c52, new C1168r5(c0160c5), C0133bg.clone(bArr)));
    }

    private r20 generateGeneralNames(kh1 kh1Var) {
        return new r20(new q20(kh1Var));
    }

    private kh1[] getPrincipals(q20[] q20VarArr) {
        ArrayList arrayList = new ArrayList(q20VarArr.length);
        for (int i = 0; i != q20VarArr.length; i++) {
            if (q20VarArr[i].getTagNo() == 4) {
                arrayList.add(kh1.getInstance(q20VarArr[i].getName()));
            }
        }
        return (kh1[]) arrayList.toArray(new kh1[arrayList.size()]);
    }

    private boolean matchesDN(kh1 kh1Var, r20 r20Var) {
        q20[] names = r20Var.getNames();
        for (int i = 0; i != names.length; i++) {
            q20 q20Var = names[i];
            if (q20Var.getTagNo() == 4 && kh1.getInstance(q20Var.getName()).equals(kh1Var)) {
                return true;
            }
        }
        return false;
    }

    @Override // p000.kz0
    public Object clone() {
        return new C0142bp((AbstractC0400d2) this.holder.toASN1Primitive());
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0142bp) {
            return this.holder.equals(((C0142bp) obj).holder);
        }
        return false;
    }

    public C1168r5 getDigestAlgorithm() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestAlgorithm();
        }
        return null;
    }

    public int getDigestedObjectType() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getDigestedObjectType().intValueExact();
        }
        return -1;
    }

    public kh1[] getEntityNames() {
        if (this.holder.getEntityName() != null) {
            return getPrincipals(this.holder.getEntityName().getNames());
        }
        return null;
    }

    public kh1[] getIssuer() {
        if (this.holder.getBaseCertificateID() != null) {
            return getPrincipals(this.holder.getBaseCertificateID().getIssuer().getNames());
        }
        return null;
    }

    public byte[] getObjectDigest() {
        if (this.holder.getObjectDigestInfo() != null) {
            return this.holder.getObjectDigestInfo().getObjectDigest().getBytes();
        }
        return null;
    }

    public C0160c5 getOtherObjectTypeID() {
        if (this.holder.getObjectDigestInfo() == null) {
            return null;
        }
        new C0160c5(this.holder.getObjectDigestInfo().getOtherObjectTypeID().getId());
        return null;
    }

    public BigInteger getSerialNumber() {
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().getValue();
        }
        return null;
    }

    public int hashCode() {
        return this.holder.hashCode();
    }

    @Override // p000.kz0
    public boolean match(Object obj) {
        if (!(obj instanceof X509CertificateHolder)) {
            return false;
        }
        X509CertificateHolder x509CertificateHolder = (X509CertificateHolder) obj;
        if (this.holder.getBaseCertificateID() != null) {
            return this.holder.getBaseCertificateID().getSerial().hasValue(x509CertificateHolder.getSerialNumber()) && matchesDN(x509CertificateHolder.getIssuer(), this.holder.getBaseCertificateID().getIssuer());
        }
        if (this.holder.getEntityName() != null && matchesDN(x509CertificateHolder.getSubject(), this.holder.getEntityName())) {
            return true;
        }
        if (this.holder.getObjectDigestInfo() == null) {
            return false;
        }
        try {
            this.holder.getObjectDigestInfo().getDigestAlgorithm();
            throw null;
        } catch (Exception unused) {
            return false;
        }
    }

    public C0142bp(AbstractC0400d2 abstractC0400d2) {
        this.holder = e40.getInstance(abstractC0400d2);
    }

    public C0142bp(kh1 kh1Var) {
        this.holder = new e40(generateGeneralNames(kh1Var));
    }

    public C0142bp(kh1 kh1Var, BigInteger bigInteger) {
        this.holder = new e40(new b70(generateGeneralNames(kh1Var), new C0155c0(bigInteger)));
    }

    public C0142bp(X509CertificateHolder x509CertificateHolder) {
        this.holder = new e40(new b70(generateGeneralNames(x509CertificateHolder.getIssuer()), new C0155c0(x509CertificateHolder.getSerialNumber())));
    }

    public static void setDigestCalculatorProvider(InterfaceC1239sy interfaceC1239sy) {
    }
}
