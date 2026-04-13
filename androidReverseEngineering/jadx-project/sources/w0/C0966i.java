package w0;

import a1.AbstractC0026q;
import a1.C0014e;
import android.os.Build;
import java.net.InetSocketAddress;
import java.net.Socket;
import java.security.AccessControlException;
import java.security.NoSuchAlgorithmException;
import java.security.Security;
import java.util.ArrayList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.X509TrustManager;
import org.conscrypt.Conscrypt;
import p0.b0;
import p0.c0;
import p000a.AbstractC0000a;
import q0.AbstractC0887c;
import z0.C0982a;
import z0.C0983b;
import z0.InterfaceC0985d;

/* renamed from: w0.i */
/* loaded from: classes.dex */
public class C0966i {

    /* renamed from: a */
    public static final C0966i f2293a;

    /* renamed from: b */
    public static final Logger f2294b;

    /* JADX WARN: Code restructure failed: missing block: B:37:0x00a8, code lost:
    
        if (r1 != null) goto L34;
     */
    /* JADX WARN: Code restructure failed: missing block: B:48:0x00d6, code lost:
    
        if (r1 != null) goto L58;
     */
    /* JADX WARN: Removed duplicated region for block: B:56:0x00fa  */
    /* JADX WARN: Removed duplicated region for block: B:61:0x013e  */
    /* JADX WARN: Removed duplicated region for block: B:7:0x0034  */
    static {
        String str;
        C0966i c0966i;
        int i2;
        C0966i c0966i2 = null;
        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
            if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
                try {
                    try {
                        i2 = Build.VERSION.SDK_INT;
                    } catch (NoClassDefFoundError unused) {
                        i2 = 0;
                    }
                } catch (ReflectiveOperationException unused2) {
                }
                if (i2 >= 29) {
                    Class.forName("com.android.org.conscrypt.SSLParametersImpl");
                    c0966i = new C0958a();
                    if (c0966i == null) {
                        if ("Dalvik".equals(System.getProperty("java.vm.name"))) {
                            try {
                                Class.forName("com.android.org.conscrypt.SSLParametersImpl");
                                Class<?> cls = Class.forName("com.android.org.conscrypt.OpenSSLSocketImpl");
                                try {
                                    c0966i2 = new C0961d(cls, cls.getDeclaredMethod("setUseSessionTickets", Boolean.TYPE), cls.getMethod("setHostname", String.class), cls.getMethod("getAlpnSelectedProtocol", new Class[0]), cls.getMethod("setAlpnProtocols", byte[].class));
                                } catch (NoSuchMethodException unused3) {
                                    throw new IllegalStateException("Expected Android API level 21+ but was " + Build.VERSION.SDK_INT);
                                }
                            } catch (ClassNotFoundException unused4) {
                            }
                        }
                        if (c0966i2 == null) {
                            throw new NullPointerException("No platform found on Android");
                        }
                        c0966i = c0966i2;
                    }
                    f2293a = c0966i;
                    f2294b = Logger.getLogger(b0.class.getName());
                }
            }
            c0966i = null;
            if (c0966i == null) {
            }
            f2293a = c0966i;
            f2294b = Logger.getLogger(b0.class.getName());
        }
        byte[] bArr = AbstractC0887c.f1934a;
        try {
            str = System.getProperty("okhttp.platform");
        } catch (AccessControlException unused5) {
        }
        if (c0966i == null) {
            try {
                Class<?> cls2 = Class.forName("org.eclipse.jetty.alpn.ALPN", true, null);
                c0966i2 = new C0964g(cls2.getMethod("put", SSLSocket.class, Class.forName("org.eclipse.jetty.alpn.ALPN$Provider", true, null)), cls2.getMethod("get", SSLSocket.class), cls2.getMethod("remove", SSLSocket.class), Class.forName("org.eclipse.jetty.alpn.ALPN$ClientProvider", true, null), Class.forName("org.eclipse.jetty.alpn.ALPN$ServerProvider", true, null));
            } catch (ClassNotFoundException | NoSuchMethodException unused6) {
            }
            if (c0966i2 == null) {
                c0966i = new C0966i();
            }
            c0966i = c0966i2;
        }
        f2293a = c0966i;
        f2294b = Logger.getLogger(b0.class.getName());
        if (c0966i2 == null) {
        }
        c0966i = c0966i2;
        f2293a = c0966i;
        f2294b = Logger.getLogger(b0.class.getName());
        str = null;
        if ("conscrypt".equals(str) ? true : "Conscrypt".equals(Security.getProviders()[0].getName())) {
            c0966i = !Conscrypt.isAvailable() ? null : new C0962e();
        }
        try {
            c0966i = new C0965h(SSLParameters.class.getMethod("setApplicationProtocols", String[].class), SSLSocket.class.getMethod("getApplicationProtocol", new Class[0]));
        } catch (NoSuchMethodException unused7) {
            c0966i = null;
        }
        if (c0966i == null) {
        }
        f2293a = c0966i;
        f2294b = Logger.getLogger(b0.class.getName());
    }

    /* renamed from: b */
    public static ArrayList m1459b(List list) {
        ArrayList arrayList = new ArrayList(list.size());
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            c0 c0Var = (c0) list.get(i2);
            if (c0Var != c0.HTTP_1_0) {
                arrayList.add(c0Var.f1754a);
            }
        }
        return arrayList;
    }

    /* renamed from: e */
    public static byte[] m1460e(List list) {
        C0014e c0014e = new C0014e();
        int size = list.size();
        for (int i2 = 0; i2 < size; i2++) {
            c0 c0Var = (c0) list.get(i2);
            if (c0Var != c0.HTTP_1_0) {
                c0014e.m86J(c0Var.f1754a.length());
                String str = c0Var.f1754a;
                c0014e.m91O(str, 0, str.length());
            }
        }
        return c0014e.mo103m();
    }

    /* renamed from: a */
    public void mo1458a(SSLSocket sSLSocket) {
    }

    /* renamed from: c */
    public AbstractC0026q mo1449c(X509TrustManager x509TrustManager) {
        return new C0982a(mo1450d(x509TrustManager));
    }

    /* renamed from: d */
    public InterfaceC0985d mo1450d(X509TrustManager x509TrustManager) {
        return new C0983b(x509TrustManager.getAcceptedIssuers());
    }

    /* renamed from: f */
    public void mo1457f(SSLSocketFactory sSLSocketFactory) {
    }

    /* renamed from: g */
    public void mo1445g(SSLSocket sSLSocket, String str, List list) {
    }

    /* renamed from: h */
    public void mo1451h(Socket socket, InetSocketAddress inetSocketAddress, int i2) {
        socket.connect(inetSocketAddress, i2);
    }

    /* renamed from: i */
    public SSLContext mo1452i() {
        try {
            return SSLContext.getInstance("TLS");
        } catch (NoSuchAlgorithmException e2) {
            throw new IllegalStateException("No TLS provider", e2);
        }
    }

    /* renamed from: j */
    public String mo1446j(SSLSocket sSLSocket) {
        return null;
    }

    /* renamed from: k */
    public Object mo1453k() {
        if (f2294b.isLoggable(Level.FINE)) {
            return new Throwable("response.body().close()");
        }
        return null;
    }

    /* renamed from: l */
    public boolean mo1454l(String str) {
        return true;
    }

    /* renamed from: m */
    public void mo1455m(int i2, String str, Throwable th) {
        f2294b.log(i2 == 5 ? Level.WARNING : Level.INFO, str, th);
    }

    /* renamed from: n */
    public void mo1456n(Object obj, String str) {
        if (obj == null) {
            str = AbstractC0000a.m30z(str, " To see where this was allocated, set the OkHttpClient logger level to FINE: Logger.getLogger(OkHttpClient.class.getName()).setLevel(Level.FINE);");
        }
        mo1455m(5, str, (Throwable) obj);
    }

    public final String toString() {
        return getClass().getSimpleName();
    }
}
