package w0;

import a1.AbstractC0026q;
import android.os.Build;
import android.util.Log;
import f0.C0299t;
import java.io.IOException;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.nio.charset.StandardCharsets;
import java.security.NoSuchAlgorithmException;
import java.security.cert.X509Certificate;
import java.util.List;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.X509TrustManager;
import q0.AbstractC0887c;
import z0.C0983b;
import z0.InterfaceC0985d;

/* renamed from: w0.d */
/* loaded from: classes.dex */
public class C0961d extends C0966i {

    /* renamed from: c */
    public final Class f2277c;

    /* renamed from: d */
    public final Method f2278d;

    /* renamed from: e */
    public final Method f2279e;

    /* renamed from: f */
    public final Method f2280f;

    /* renamed from: g */
    public final Method f2281g;

    /* renamed from: h */
    public final C0299t f2282h;

    public C0961d(Class cls, Method method, Method method2, Method method3, Method method4) {
        Method method5;
        Method method6;
        Method method7;
        try {
            Class<?> cls2 = Class.forName("dalvik.system.CloseGuard");
            method5 = cls2.getMethod("get", new Class[0]);
            method7 = cls2.getMethod("open", String.class);
            method6 = cls2.getMethod("warnIfOpen", new Class[0]);
        } catch (Exception unused) {
            method5 = null;
            method6 = null;
            method7 = null;
        }
        this.f2282h = new C0299t(method5, method7, method6, 6);
        this.f2277c = cls;
        this.f2278d = method;
        this.f2279e = method2;
        this.f2280f = method3;
        this.f2281g = method4;
    }

    /* renamed from: o */
    public static boolean m1448o(String str, Class cls, Object obj) {
        try {
            try {
                return ((Boolean) cls.getMethod("isCleartextTrafficPermitted", String.class).invoke(obj, str)).booleanValue();
            } catch (NoSuchMethodException unused) {
                return ((Boolean) cls.getMethod("isCleartextTrafficPermitted", new Class[0]).invoke(obj, new Object[0])).booleanValue();
            }
        } catch (NoSuchMethodException unused2) {
            return true;
        }
    }

    @Override // w0.C0966i
    /* renamed from: c */
    public final AbstractC0026q mo1449c(X509TrustManager x509TrustManager) {
        try {
            Class<?> cls = Class.forName("android.net.http.X509TrustManagerExtensions");
            return new C0959b(cls.getConstructor(X509TrustManager.class).newInstance(x509TrustManager), cls.getMethod("checkServerTrusted", X509Certificate[].class, String.class, String.class));
        } catch (Exception unused) {
            return super.mo1449c(x509TrustManager);
        }
    }

    @Override // w0.C0966i
    /* renamed from: d */
    public final InterfaceC0985d mo1450d(X509TrustManager x509TrustManager) {
        try {
            Method declaredMethod = x509TrustManager.getClass().getDeclaredMethod("findTrustAnchorByIssuerAndSignature", X509Certificate.class);
            declaredMethod.setAccessible(true);
            return new C0960c(x509TrustManager, declaredMethod);
        } catch (NoSuchMethodException unused) {
            return new C0983b(x509TrustManager.getAcceptedIssuers());
        }
    }

    @Override // w0.C0966i
    /* renamed from: g */
    public void mo1445g(SSLSocket sSLSocket, String str, List list) {
        if (this.f2277c.isInstance(sSLSocket)) {
            if (str != null) {
                try {
                    this.f2278d.invoke(sSLSocket, Boolean.TRUE);
                    this.f2279e.invoke(sSLSocket, str);
                } catch (IllegalAccessException | InvocationTargetException e2) {
                    throw new AssertionError(e2);
                }
            }
            this.f2281g.invoke(sSLSocket, C0966i.m1460e(list));
        }
    }

    @Override // w0.C0966i
    /* renamed from: h */
    public final void mo1451h(Socket socket, InetSocketAddress inetSocketAddress, int i2) {
        try {
            socket.connect(inetSocketAddress, i2);
        } catch (AssertionError e2) {
            if (!AbstractC0887c.m1317n(e2)) {
                throw e2;
            }
            throw new IOException(e2);
        } catch (ClassCastException e3) {
            if (Build.VERSION.SDK_INT != 26) {
                throw e3;
            }
            throw new IOException("Exception in connect", e3);
        }
    }

    @Override // w0.C0966i
    /* renamed from: i */
    public final SSLContext mo1452i() {
        try {
            return SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException("No TLS provider", e2);
        }
    }

    @Override // w0.C0966i
    /* renamed from: j */
    public String mo1446j(SSLSocket sSLSocket) {
        if (!this.f2277c.isInstance(sSLSocket)) {
            return null;
        }
        try {
            byte[] bArr = (byte[]) this.f2280f.invoke(sSLSocket, new Object[0]);
            if (bArr != null) {
                return new String(bArr, StandardCharsets.UTF_8);
            }
            return null;
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new AssertionError(e2);
        }
    }

    @Override // w0.C0966i
    /* renamed from: k */
    public final Object mo1453k() {
        C0299t c0299t = this.f2282h;
        Object obj = c0299t.f554f;
        if (((Method) obj) == null) {
            return null;
        }
        try {
            Object invoke = ((Method) obj).invoke(null, new Object[0]);
            ((Method) c0299t.f553e).invoke(invoke, "response.body().close()");
            return invoke;
        } catch (Exception unused) {
            return null;
        }
    }

    @Override // w0.C0966i
    /* renamed from: l */
    public final boolean mo1454l(String str) {
        try {
            Class<?> cls = Class.forName("android.security.NetworkSecurityPolicy");
            return m1448o(str, cls, cls.getMethod("getInstance", new Class[0]).invoke(null, new Object[0]));
        } catch (ClassNotFoundException | NoSuchMethodException unused) {
            return true;
        } catch (IllegalAccessException e2) {
            e = e2;
            throw new AssertionError("unable to determine cleartext support", e);
        } catch (IllegalArgumentException e3) {
            e = e3;
            throw new AssertionError("unable to determine cleartext support", e);
        } catch (InvocationTargetException e4) {
            e = e4;
            throw new AssertionError("unable to determine cleartext support", e);
        }
    }

    @Override // w0.C0966i
    /* renamed from: m */
    public final void mo1455m(int i2, String str, Throwable th) {
        int min;
        int i3 = i2 != 5 ? 3 : 5;
        if (th != null) {
            str = str + '\n' + Log.getStackTraceString(th);
        }
        int length = str.length();
        int i4 = 0;
        while (i4 < length) {
            int indexOf = str.indexOf(10, i4);
            if (indexOf == -1) {
                indexOf = length;
            }
            while (true) {
                min = Math.min(indexOf, i4 + 4000);
                Log.println(i3, "OkHttp", str.substring(i4, min));
                if (min >= indexOf) {
                    break;
                } else {
                    i4 = min;
                }
            }
            i4 = min + 1;
        }
    }

    @Override // w0.C0966i
    /* renamed from: n */
    public final void mo1456n(Object obj, String str) {
        C0299t c0299t = this.f2282h;
        c0299t.getClass();
        boolean z2 = false;
        if (obj != null) {
            try {
                ((Method) c0299t.f555g).invoke(obj, new Object[0]);
                z2 = true;
            } catch (Exception unused) {
            }
        }
        if (z2) {
            return;
        }
        mo1455m(5, str, null);
    }
}
