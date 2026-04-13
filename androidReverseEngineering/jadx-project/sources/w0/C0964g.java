package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.List;
import javax.net.ssl.SSLSocket;

/* renamed from: w0.g */
/* loaded from: classes.dex */
public final class C0964g extends C0966i {

    /* renamed from: c */
    public final Method f2286c;

    /* renamed from: d */
    public final Method f2287d;

    /* renamed from: e */
    public final Method f2288e;

    /* renamed from: f */
    public final Class f2289f;

    /* renamed from: g */
    public final Class f2290g;

    public C0964g(Method method, Method method2, Method method3, Class cls, Class cls2) {
        this.f2286c = method;
        this.f2287d = method2;
        this.f2288e = method3;
        this.f2289f = cls;
        this.f2290g = cls2;
    }

    @Override // w0.C0966i
    /* renamed from: a */
    public final void mo1458a(SSLSocket sSLSocket) {
        try {
            this.f2288e.invoke(null, sSLSocket);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new AssertionError("failed to remove ALPN", e2);
        }
    }

    @Override // w0.C0966i
    /* renamed from: g */
    public final void mo1445g(SSLSocket sSLSocket, String str, List list) {
        try {
            this.f2286c.invoke(null, sSLSocket, Proxy.newProxyInstance(C0966i.class.getClassLoader(), new Class[]{this.f2289f, this.f2290g}, new C0963f(C0966i.m1459b(list))));
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new AssertionError("failed to set ALPN", e2);
        }
    }

    @Override // w0.C0966i
    /* renamed from: j */
    public final String mo1446j(SSLSocket sSLSocket) {
        try {
            C0963f c0963f = (C0963f) Proxy.getInvocationHandler(this.f2287d.invoke(null, sSLSocket));
            boolean z2 = c0963f.f2284b;
            if (!z2 && c0963f.f2285c == null) {
                C0966i.f2293a.mo1455m(4, "ALPN callback dropped: HTTP/2 is disabled. Is alpn-boot on the boot class path?", null);
                return null;
            }
            if (z2) {
                return null;
            }
            return c0963f.f2285c;
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new AssertionError("failed to get ALPN selected protocol", e2);
        }
    }
}
