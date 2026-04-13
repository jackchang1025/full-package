package w0;

import a1.AbstractC0026q;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLPeerUnverifiedException;

/* renamed from: w0.b */
/* loaded from: classes.dex */
public final class C0959b extends AbstractC0026q {

    /* renamed from: o */
    public final Object f2273o;

    /* renamed from: p */
    public final Method f2274p;

    public C0959b(Object obj, Method method) {
        this.f2273o = obj;
        this.f2274p = method;
    }

    public final boolean equals(Object obj) {
        return obj instanceof C0959b;
    }

    @Override // a1.AbstractC0026q
    /* renamed from: f */
    public final List mo195f(String str, List list) {
        try {
            return (List) this.f2274p.invoke(this.f2273o, (X509Certificate[]) list.toArray(new X509Certificate[list.size()]), "RSA", str);
        } catch (IllegalAccessException e2) {
            throw new AssertionError(e2);
        } catch (InvocationTargetException e3) {
            SSLPeerUnverifiedException sSLPeerUnverifiedException = new SSLPeerUnverifiedException(e3.getMessage());
            sSLPeerUnverifiedException.initCause(e3);
            throw sSLPeerUnverifiedException;
        }
    }

    public final int hashCode() {
        return 0;
    }
}
