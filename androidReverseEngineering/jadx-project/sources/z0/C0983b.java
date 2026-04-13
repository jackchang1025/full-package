package z0;

import java.security.cert.X509Certificate;
import java.util.LinkedHashMap;
import java.util.LinkedHashSet;
import java.util.Set;
import javax.security.auth.x500.X500Principal;

/* renamed from: z0.b */
/* loaded from: classes.dex */
public final class C0983b implements InterfaceC0985d {

    /* renamed from: a */
    public final LinkedHashMap f2329a = new LinkedHashMap();

    public C0983b(X509Certificate... x509CertificateArr) {
        for (X509Certificate x509Certificate : x509CertificateArr) {
            X500Principal subjectX500Principal = x509Certificate.getSubjectX500Principal();
            Set set = (Set) this.f2329a.get(subjectX500Principal);
            if (set == null) {
                set = new LinkedHashSet(1);
                this.f2329a.put(subjectX500Principal, set);
            }
            set.add(x509Certificate);
        }
    }

    @Override // z0.InterfaceC0985d
    /* renamed from: a */
    public final X509Certificate mo1447a(X509Certificate x509Certificate) {
        Set<X509Certificate> set = (Set) this.f2329a.get(x509Certificate.getIssuerX500Principal());
        if (set == null) {
            return null;
        }
        for (X509Certificate x509Certificate2 : set) {
            try {
                x509Certificate.verify(x509Certificate2.getPublicKey());
                return x509Certificate2;
            } catch (Exception unused) {
            }
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof C0983b) && ((C0983b) obj).f2329a.equals(this.f2329a);
    }

    public final int hashCode() {
        return this.f2329a.hashCode();
    }
}
