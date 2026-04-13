package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import javax.net.ssl.X509TrustManager;
import z0.InterfaceC0985d;

/* renamed from: w0.c */
/* loaded from: classes.dex */
public final class C0960c implements InterfaceC0985d {

    /* renamed from: a */
    public final X509TrustManager f2275a;

    /* renamed from: b */
    public final Method f2276b;

    public C0960c(X509TrustManager x509TrustManager, Method method) {
        this.f2276b = method;
        this.f2275a = x509TrustManager;
    }

    @Override // z0.InterfaceC0985d
    /* renamed from: a */
    public final X509Certificate mo1447a(X509Certificate x509Certificate) {
        try {
            TrustAnchor trustAnchor = (TrustAnchor) this.f2276b.invoke(this.f2275a, x509Certificate);
            if (trustAnchor != null) {
                return trustAnchor.getTrustedCert();
            }
        } catch (IllegalAccessException e2) {
            throw new AssertionError("unable to get issues and signature", e2);
        } catch (InvocationTargetException unused) {
        }
        return null;
    }

    public final boolean equals(Object obj) {
        if (obj == this) {
            return true;
        }
        if (!(obj instanceof C0960c)) {
            return false;
        }
        C0960c c0960c = (C0960c) obj;
        return this.f2275a.equals(c0960c.f2275a) && this.f2276b.equals(c0960c.f2276b);
    }

    public final int hashCode() {
        return (this.f2276b.hashCode() * 31) + this.f2275a.hashCode();
    }
}
