package org.bouncycastle.jsse.provider;

import java.io.InputStream;
import java.lang.reflect.Method;
import java.net.InetAddress;
import java.net.Socket;
import java.util.concurrent.atomic.AtomicInteger;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocket;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.BCSSLSocket;

/* loaded from: classes.dex */
abstract class SSLSocketUtil {
    private static final Method getHandshakeSession;
    private static final Method getSSLParameters;
    private static AtomicInteger threadNumber = new AtomicInteger();
    private static final boolean useSocket8;

    static {
        Method[] methods = ReflectionUtil.getMethods("javax.net.ssl.SSLSocket");
        getHandshakeSession = ReflectionUtil.findMethod(methods, "getHandshakeSession");
        getSSLParameters = ReflectionUtil.findMethod(methods, "getSSLParameters");
        useSocket8 = ReflectionUtil.hasMethod(methods, "getApplicationProtocol");
    }

    public static ProvSSLSocketDirect create(ContextData contextData) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData) : new ProvSSLSocketDirect(contextData);
    }

    public static void handshakeCompleted(Runnable runnable) {
        new Thread(runnable, "BCJSSE-HandshakeCompleted-" + (threadNumber.getAndIncrement() & Integer.MAX_VALUE)).start();
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BCExtendedSSLSession importHandshakeSession(SSLSocket sSLSocket) {
        Method method;
        SSLSession sSLSession;
        if (sSLSocket instanceof BCSSLSocket) {
            return ((BCSSLSocket) sSLSocket).getBCHandshakeSession();
        }
        if (sSLSocket == 0 || (method = getHandshakeSession) == null || (sSLSession = (SSLSession) ReflectionUtil.invokeGetter(sSLSocket, method)) == null) {
            return null;
        }
        return SSLSessionUtil.importSSLSession(sSLSession);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BCSSLParameters importSSLParameters(SSLSocket sSLSocket) {
        Method method;
        if (sSLSocket instanceof BCSSLSocket) {
            return ((BCSSLSocket) sSLSocket).getParameters();
        }
        if (sSLSocket == 0 || (method = getSSLParameters) == null) {
            return null;
        }
        SSLParameters sSLParameters = (SSLParameters) ReflectionUtil.invokeGetter(sSLSocket, method);
        if (sSLParameters != null) {
            return SSLParametersUtil.importSSLParameters(sSLParameters);
        }
        throw new RuntimeException("SSLSocket.getSSLParameters returned null");
    }

    public static ProvSSLSocketDirect create(ContextData contextData, String str, int i2) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData, str, i2) : new ProvSSLSocketDirect(contextData, str, i2);
    }

    public static ProvSSLSocketDirect create(ContextData contextData, String str, int i2, InetAddress inetAddress, int i3) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData, str, i2, inetAddress, i3) : new ProvSSLSocketDirect(contextData, str, i2, inetAddress, i3);
    }

    public static ProvSSLSocketDirect create(ContextData contextData, InetAddress inetAddress, int i2) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData, inetAddress, i2) : new ProvSSLSocketDirect(contextData, inetAddress, i2);
    }

    public static ProvSSLSocketDirect create(ContextData contextData, InetAddress inetAddress, int i2, InetAddress inetAddress2, int i3) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData, inetAddress, i2, inetAddress2, i3) : new ProvSSLSocketDirect(contextData, inetAddress, i2, inetAddress2, i3);
    }

    public static ProvSSLSocketDirect create(ContextData contextData, boolean z2, boolean z3, ProvSSLParameters provSSLParameters) {
        return useSocket8 ? new ProvSSLSocketDirect_8(contextData, z2, z3, provSSLParameters) : new ProvSSLSocketDirect(contextData, z2, z3, provSSLParameters);
    }

    public static ProvSSLSocketWrap create(ContextData contextData, Socket socket, InputStream inputStream, boolean z2) {
        return useSocket8 ? new ProvSSLSocketWrap_8(contextData, socket, inputStream, z2) : new ProvSSLSocketWrap(contextData, socket, inputStream, z2);
    }

    public static ProvSSLSocketWrap create(ContextData contextData, Socket socket, String str, int i2, boolean z2) {
        return useSocket8 ? new ProvSSLSocketWrap_8(contextData, socket, str, i2, z2) : new ProvSSLSocketWrap(contextData, socket, str, i2, z2);
    }
}
