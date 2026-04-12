package p000;

import java.util.ArrayList;
import org.bouncycastle.cert.X509CertificateHolder;

/* renamed from: br */
/* loaded from: classes2.dex */
public class C0144br implements kz0 {
    final InterfaceC0117b0 form;

    public C0144br(C0138bl c0138bl) {
        this.form = c0138bl.getIssuer();
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
        return new C0144br(C0138bl.getInstance(this.form));
    }

    public boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0144br) {
            return this.form.equals(((C0144br) obj).form);
        }
        return false;
    }

    public kh1[] getNames() {
        InterfaceC0117b0 interfaceC0117b0 = this.form;
        q20[] names = (interfaceC0117b0 instanceof f91 ? ((f91) interfaceC0117b0).getIssuerName() : (r20) interfaceC0117b0).getNames();
        ArrayList arrayList = new ArrayList(names.length);
        for (int i = 0; i != names.length; i++) {
            if (names[i].getTagNo() == 4) {
                arrayList.add(kh1.getInstance(names[i].getName()));
            }
        }
        return (kh1[]) arrayList.toArray(new kh1[arrayList.size()]);
    }

    public int hashCode() {
        return this.form.hashCode();
    }

    @Override // p000.kz0
    public boolean match(Object obj) {
        if (!(obj instanceof X509CertificateHolder)) {
            return false;
        }
        X509CertificateHolder x509CertificateHolder = (X509CertificateHolder) obj;
        InterfaceC0117b0 interfaceC0117b0 = this.form;
        if (interfaceC0117b0 instanceof f91) {
            f91 f91Var = (f91) interfaceC0117b0;
            if (f91Var.getBaseCertificateID() != null) {
                return f91Var.getBaseCertificateID().getSerial().hasValue(x509CertificateHolder.getSerialNumber()) && matchesDN(x509CertificateHolder.getIssuer(), f91Var.getBaseCertificateID().getIssuer());
            }
            if (matchesDN(x509CertificateHolder.getSubject(), f91Var.getIssuerName())) {
                return true;
            }
        } else {
            if (matchesDN(x509CertificateHolder.getSubject(), (r20) interfaceC0117b0)) {
                return true;
            }
        }
        return false;
    }

    public C0144br(kh1 kh1Var) {
        this.form = new f91(new r20(new q20(kh1Var)));
    }
}
