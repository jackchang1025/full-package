package p0;

import a1.AbstractC0026q;
import a1.C0017h;
import java.security.MessageDigest;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import javax.net.ssl.SSLPeerUnverifiedException;
import p000a.AbstractC0000a;

/* renamed from: p0.g */
/* loaded from: classes.dex */
public final class C0865g {

    /* renamed from: c */
    public static final C0865g f1783c = new C0865g(new LinkedHashSet(new ArrayList()), null);

    /* renamed from: a */
    public final Set f1784a;

    /* renamed from: b */
    public final AbstractC0026q f1785b;

    public C0865g(Set set, AbstractC0026q abstractC0026q) {
        this.f1784a = set;
        this.f1785b = abstractC0026q;
    }

    /* renamed from: b */
    public static String m1255b(X509Certificate x509Certificate) {
        if (!(x509Certificate instanceof X509Certificate)) {
            throw new IllegalArgumentException("Certificate pinning requires X509 certificates");
        }
        StringBuilder sb = new StringBuilder("sha256/");
        try {
            sb.append(C0017h.m119g(MessageDigest.getInstance("SHA-256").digest(C0017h.m119g(x509Certificate.getPublicKey().getEncoded()).f25a)).mo120a());
            return sb.toString();
        } catch (NoSuchAlgorithmException e2) {
            throw new AssertionError(e2);
        }
    }

    /* renamed from: a */
    public final void m1256a(String str, List list) {
        List emptyList = Collections.emptyList();
        Iterator it = this.f1784a.iterator();
        if (it.hasNext()) {
            AbstractC0000a.m27w(it.next());
            throw null;
        }
        if (emptyList.isEmpty()) {
            return;
        }
        AbstractC0026q abstractC0026q = this.f1785b;
        if (abstractC0026q != null) {
            list = abstractC0026q.mo195f(str, list);
        }
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            if (emptyList.size() > 0) {
                AbstractC0000a.m27w(emptyList.get(0));
                throw null;
            }
        }
        StringBuilder sb = new StringBuilder("Certificate pinning failure!\n  Peer certificate chain:");
        int size2 = list.size();
        for (int i3 = 0; i3 < size2; i3++) {
            X509Certificate x509Certificate = (X509Certificate) list.get(i3);
            sb.append("\n    ");
            sb.append(m1255b(x509Certificate));
            sb.append(": ");
            sb.append(x509Certificate.getSubjectDN().getName());
        }
        sb.append("\n  Pinned certificates for ");
        sb.append(str);
        sb.append(":");
        int size3 = emptyList.size();
        for (int i4 = 0; i4 < size3; i4++) {
            AbstractC0000a.m27w(emptyList.get(i4));
            sb.append("\n    null");
        }
        throw new SSLPeerUnverifiedException(sb.toString());
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (obj instanceof C0865g) {
            C0865g c0865g = (C0865g) obj;
            if (Objects.equals(this.f1785b, c0865g.f1785b) && this.f1784a.equals(c0865g.f1784a)) {
                return true;
            }
        }
        return false;
    }

    public final int hashCode() {
        return this.f1784a.hashCode() + (Objects.hashCode(this.f1785b) * 31);
    }
}
