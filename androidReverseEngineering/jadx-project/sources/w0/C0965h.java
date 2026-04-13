package w0;

import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.ArrayList;
import java.util.List;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSocket;
import com.guard.wallet.entity.BuildConfig;

/* renamed from: w0.h */
/* loaded from: classes.dex */
public final class C0965h extends C0966i {

    /* renamed from: c */
    public final Method f2291c;

    /* renamed from: d */
    public final Method f2292d;

    public C0965h(Method method, Method method2) {
        this.f2291c = method;
        this.f2292d = method2;
    }

    @Override // w0.C0966i
    /* renamed from: g */
    public final void mo1445g(SSLSocket sSLSocket, String str, List list) {
        try {
            SSLParameters sSLParameters = sSLSocket.getSSLParameters();
            ArrayList m1459b = C0966i.m1459b(list);
            this.f2291c.invoke(sSLParameters, m1459b.toArray(new String[m1459b.size()]));
            sSLSocket.setSSLParameters(sSLParameters);
        } catch (IllegalAccessException | InvocationTargetException e2) {
            throw new AssertionError("failed to set SSL parameters", e2);
        }
    }

    @Override // w0.C0966i
    /* renamed from: j */
    public final String mo1446j(SSLSocket sSLSocket) {
        try {
            String str = (String) this.f2292d.invoke(sSLSocket, new Object[0]);
            if (str != null) {
                if (!str.equals(BuildConfig.FLAVOR)) {
                    return str;
                }
            }
            return null;
        } catch (IllegalAccessException e2) {
            throw new AssertionError("failed to get ALPN selected protocol", e2);
        } catch (InvocationTargetException e3) {
            if (e3.getCause() instanceof UnsupportedOperationException) {
                return null;
            }
            throw new AssertionError("failed to get ALPN selected protocol", e3);
        }
    }
}
