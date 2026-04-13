package org.bouncycastle.jsse.provider;

import java.lang.reflect.Method;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLSession;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSSLEngine;
import org.bouncycastle.jsse.BCSSLParameters;

/* loaded from: classes.dex */
abstract class SSLEngineUtil {
    private static final Method getHandshakeSession;
    private static final Method getSSLParameters;
    private static final boolean useEngine8;

    static {
        Method[] methods = ReflectionUtil.getMethods("javax.net.ssl.SSLEngine");
        getHandshakeSession = ReflectionUtil.findMethod(methods, "getHandshakeSession");
        getSSLParameters = ReflectionUtil.findMethod(methods, "getSSLParameters");
        useEngine8 = ReflectionUtil.hasMethod(methods, "getApplicationProtocol");
    }

    public static SSLEngine create(ContextData contextData) {
        return useEngine8 ? new ProvSSLEngine_8(contextData) : new ProvSSLEngine(contextData);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BCExtendedSSLSession importHandshakeSession(SSLEngine sSLEngine) {
        Method method;
        SSLSession sSLSession;
        if (sSLEngine instanceof BCSSLEngine) {
            return ((BCSSLEngine) sSLEngine).getBCHandshakeSession();
        }
        if (sSLEngine == 0 || (method = getHandshakeSession) == null || (sSLSession = (SSLSession) ReflectionUtil.invokeGetter(sSLEngine, method)) == null) {
            return null;
        }
        return SSLSessionUtil.importSSLSession(sSLSession);
    }

    /* JADX WARN: Multi-variable type inference failed */
    public static BCSSLParameters importSSLParameters(SSLEngine sSLEngine) {
        Method method;
        if (sSLEngine instanceof BCSSLEngine) {
            return ((BCSSLEngine) sSLEngine).getParameters();
        }
        if (sSLEngine == 0 || (method = getSSLParameters) == null) {
            return null;
        }
        SSLParameters sSLParameters = (SSLParameters) ReflectionUtil.invokeGetter(sSLEngine, method);
        if (sSLParameters != null) {
            return SSLParametersUtil.importSSLParameters(sSLParameters);
        }
        throw new RuntimeException("SSLEngine.getSSLParameters returned null");
    }

    public static SSLEngine create(ContextData contextData, String str, int i2) {
        return useEngine8 ? new ProvSSLEngine_8(contextData, str, i2) : new ProvSSLEngine(contextData, str, i2);
    }
}
