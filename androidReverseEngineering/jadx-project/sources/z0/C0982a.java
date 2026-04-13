package z0;

import a1.AbstractC0026q;
import java.security.GeneralSecurityException;
import java.security.cert.Certificate;
import java.security.cert.X509Certificate;
import java.util.ArrayDeque;
import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

/* renamed from: z0.a */
/* loaded from: classes.dex */
public final class C0982a extends AbstractC0026q {

    /* renamed from: o */
    public final InterfaceC0985d f2328o;

    public C0982a(InterfaceC0985d interfaceC0985d) {
        this.f2328o = interfaceC0985d;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        return (obj instanceof C0982a) && ((C0982a) obj).f2328o.equals(this.f2328o);
    }

    /* JADX WARN: Removed duplicated region for block: B:14:0x0058  */
    /* JADX WARN: Removed duplicated region for block: B:17:0x0057 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:34:0x0084 A[SYNTHETIC] */
    /* JADX WARN: Removed duplicated region for block: B:37:? A[LOOP:1: B:27:0x005e->B:37:?, LOOP_END, SYNTHETIC] */
    @Override // a1.AbstractC0026q
    /* renamed from: f */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public final List mo195f(String str, List list) {
        boolean z2;
        boolean z3;
        ArrayDeque arrayDeque = new ArrayDeque(list);
        ArrayList arrayList = new ArrayList();
        arrayList.add((Certificate) arrayDeque.removeFirst());
        boolean z4 = false;
        for (int i2 = 0; i2 < 9; i2++) {
            X509Certificate x509Certificate = (X509Certificate) arrayList.get(arrayList.size() - 1);
            X509Certificate mo1447a = this.f2328o.mo1447a(x509Certificate);
            if (mo1447a == null) {
                Iterator it = arrayDeque.iterator();
                while (it.hasNext()) {
                    X509Certificate x509Certificate2 = (X509Certificate) it.next();
                    if (x509Certificate.getIssuerDN().equals(x509Certificate2.getSubjectDN())) {
                        try {
                            x509Certificate.verify(x509Certificate2.getPublicKey());
                            z2 = true;
                        } catch (GeneralSecurityException unused) {
                        }
                        if (!z2) {
                            it.remove();
                            arrayList.add(x509Certificate2);
                        }
                    }
                    z2 = false;
                    if (!z2) {
                    }
                }
                if (z4) {
                    return arrayList;
                }
                throw new SSLPeerUnverifiedException("Failed to find a trusted cert that signed " + x509Certificate);
            }
            if (arrayList.size() > 1 || !x509Certificate.equals(mo1447a)) {
                arrayList.add(mo1447a);
            }
            if (mo1447a.getIssuerDN().equals(mo1447a.getSubjectDN())) {
                try {
                    mo1447a.verify(mo1447a.getPublicKey());
                    z3 = true;
                } catch (GeneralSecurityException unused2) {
                }
                if (!z3) {
                    return arrayList;
                }
                z4 = true;
            }
            z3 = false;
            if (!z3) {
            }
        }
        throw new SSLPeerUnverifiedException("Certificate chain too long: " + arrayList);
    }

    public final int hashCode() {
        return this.f2328o.hashCode();
    }
}
