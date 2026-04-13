package org.bouncycastle.jsse.provider;

import java.lang.reflect.Method;
import java.util.Collection;
import java.util.List;
import javax.net.ssl.SSLParameters;
import org.bouncycastle.jsse.BCSNIMatcher;
import org.bouncycastle.jsse.BCSNIServerName;
import org.bouncycastle.jsse.BCSSLParameters;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;

/* loaded from: classes.dex */
abstract class SSLParametersUtil {
    private static final Method getAlgorithmConstraints;
    private static final Method getApplicationProtocols;
    private static final Method getEndpointIdentificationAlgorithm;
    private static final Method getSNIMatchers;
    private static final Method getServerNames;
    private static final Method getUseCipherSuitesOrder;
    private static final Method setAlgorithmConstraints;
    private static final Method setApplicationProtocols;
    private static final Method setEndpointIdentificationAlgorithm;
    private static final Method setSNIMatchers;
    private static final Method setServerNames;
    private static final Method setUseCipherSuitesOrder;

    static {
        Method[] methods = ReflectionUtil.getMethods("javax.net.ssl.SSLParameters");
        getAlgorithmConstraints = ReflectionUtil.findMethod(methods, "getAlgorithmConstraints");
        setAlgorithmConstraints = ReflectionUtil.findMethod(methods, "setAlgorithmConstraints");
        getApplicationProtocols = ReflectionUtil.findMethod(methods, "getApplicationProtocols");
        setApplicationProtocols = ReflectionUtil.findMethod(methods, "setApplicationProtocols");
        getEndpointIdentificationAlgorithm = ReflectionUtil.findMethod(methods, "getEndpointIdentificationAlgorithm");
        setEndpointIdentificationAlgorithm = ReflectionUtil.findMethod(methods, "setEndpointIdentificationAlgorithm");
        getServerNames = ReflectionUtil.findMethod(methods, "getServerNames");
        setServerNames = ReflectionUtil.findMethod(methods, "setServerNames");
        getSNIMatchers = ReflectionUtil.findMethod(methods, "getSNIMatchers");
        setSNIMatchers = ReflectionUtil.findMethod(methods, "setSNIMatchers");
        getUseCipherSuitesOrder = ReflectionUtil.findMethod(methods, "getUseCipherSuitesOrder");
        setUseCipherSuitesOrder = ReflectionUtil.findMethod(methods, "setUseCipherSuitesOrder");
    }

    private static Object get(Object obj, Method method) {
        return ReflectionUtil.invokeGetter(obj, method);
    }

    public static BCSSLParameters getParameters(ProvSSLParameters provSSLParameters) {
        BCSSLParameters bCSSLParameters = new BCSSLParameters(provSSLParameters.getCipherSuites(), provSSLParameters.getProtocols());
        if (provSSLParameters.getNeedClientAuth()) {
            bCSSLParameters.setNeedClientAuth(true);
        } else if (provSSLParameters.getWantClientAuth()) {
            bCSSLParameters.setWantClientAuth(true);
        } else {
            bCSSLParameters.setWantClientAuth(false);
        }
        bCSSLParameters.setAlgorithmConstraints(provSSLParameters.getAlgorithmConstraints());
        bCSSLParameters.setEndpointIdentificationAlgorithm(provSSLParameters.getEndpointIdentificationAlgorithm());
        bCSSLParameters.setUseCipherSuitesOrder(provSSLParameters.getUseCipherSuitesOrder());
        bCSSLParameters.setServerNames(provSSLParameters.getServerNames());
        bCSSLParameters.setSNIMatchers(provSSLParameters.getSNIMatchers());
        bCSSLParameters.setApplicationProtocols(provSSLParameters.getApplicationProtocols());
        return bCSSLParameters;
    }

    public static SSLParameters getSSLParameters(ProvSSLParameters provSSLParameters) {
        String[] applicationProtocols;
        Collection<BCSNIMatcher> sNIMatchers;
        List<BCSNIServerName> serverNames;
        SSLParameters sSLParameters = new SSLParameters(provSSLParameters.getCipherSuites(), provSSLParameters.getProtocols());
        if (provSSLParameters.getNeedClientAuth()) {
            sSLParameters.setNeedClientAuth(true);
        } else if (provSSLParameters.getWantClientAuth()) {
            sSLParameters.setWantClientAuth(true);
        } else {
            sSLParameters.setWantClientAuth(false);
        }
        Method method = setAlgorithmConstraints;
        if (method != null) {
            set(sSLParameters, method, JsseUtils_7.exportAlgorithmConstraintsDynamic(provSSLParameters.getAlgorithmConstraints()));
        }
        Method method2 = setEndpointIdentificationAlgorithm;
        if (method2 != null) {
            set(sSLParameters, method2, provSSLParameters.getEndpointIdentificationAlgorithm());
        }
        Method method3 = setUseCipherSuitesOrder;
        if (method3 != null) {
            set(sSLParameters, method3, Boolean.valueOf(provSSLParameters.getUseCipherSuitesOrder()));
        }
        Method method4 = setServerNames;
        if (method4 != null && (serverNames = provSSLParameters.getServerNames()) != null) {
            set(sSLParameters, method4, JsseUtils_8.exportSNIServerNamesDynamic(serverNames));
        }
        Method method5 = setSNIMatchers;
        if (method5 != null && (sNIMatchers = provSSLParameters.getSNIMatchers()) != null) {
            set(sSLParameters, method5, JsseUtils_8.exportSNIMatchersDynamic(sNIMatchers));
        }
        Method method6 = setApplicationProtocols;
        if (method6 != null && (applicationProtocols = provSSLParameters.getApplicationProtocols()) != null) {
            set(sSLParameters, method6, applicationProtocols);
        }
        return sSLParameters;
    }

    public static BCSSLParameters importSSLParameters(SSLParameters sSLParameters) {
        String[] strArr;
        Object obj;
        Object obj2;
        String str;
        Object obj3;
        BCSSLParameters bCSSLParameters = new BCSSLParameters(sSLParameters.getCipherSuites(), sSLParameters.getProtocols());
        if (sSLParameters.getNeedClientAuth()) {
            bCSSLParameters.setNeedClientAuth(true);
        } else if (sSLParameters.getWantClientAuth()) {
            bCSSLParameters.setWantClientAuth(true);
        } else {
            bCSSLParameters.setWantClientAuth(false);
        }
        Method method = getAlgorithmConstraints;
        if (method != null && (obj3 = get(sSLParameters, method)) != null) {
            bCSSLParameters.setAlgorithmConstraints(JsseUtils_7.importAlgorithmConstraintsDynamic(obj3));
        }
        Method method2 = getEndpointIdentificationAlgorithm;
        if (method2 != null && (str = (String) get(sSLParameters, method2)) != null) {
            bCSSLParameters.setEndpointIdentificationAlgorithm(str);
        }
        Method method3 = getUseCipherSuitesOrder;
        if (method3 != null) {
            bCSSLParameters.setUseCipherSuitesOrder(((Boolean) get(sSLParameters, method3)).booleanValue());
        }
        Method method4 = getServerNames;
        if (method4 != null && (obj2 = get(sSLParameters, method4)) != null) {
            bCSSLParameters.setServerNames(JsseUtils_8.importSNIServerNamesDynamic(obj2));
        }
        Method method5 = getSNIMatchers;
        if (method5 != null && (obj = get(sSLParameters, method5)) != null) {
            bCSSLParameters.setSNIMatchers(JsseUtils_8.importSNIMatchersDynamic(obj));
        }
        Method method6 = getApplicationProtocols;
        if (method6 != null && (strArr = (String[]) get(sSLParameters, method6)) != null) {
            bCSSLParameters.setApplicationProtocols(strArr);
        }
        return bCSSLParameters;
    }

    private static void set(Object obj, Method method, Object obj2) {
        ReflectionUtil.invokeSetter(obj, method, obj2);
    }

    public static void setParameters(ProvSSLParameters provSSLParameters, BCSSLParameters bCSSLParameters) {
        String[] cipherSuites = bCSSLParameters.getCipherSuites();
        if (cipherSuites != null) {
            provSSLParameters.setCipherSuites(cipherSuites);
        }
        String[] protocols = bCSSLParameters.getProtocols();
        if (protocols != null) {
            provSSLParameters.setProtocols(protocols);
        }
        if (bCSSLParameters.getNeedClientAuth()) {
            provSSLParameters.setNeedClientAuth(true);
        } else if (bCSSLParameters.getWantClientAuth()) {
            provSSLParameters.setWantClientAuth(true);
        } else {
            provSSLParameters.setWantClientAuth(false);
        }
        BCAlgorithmConstraints algorithmConstraints = bCSSLParameters.getAlgorithmConstraints();
        if (algorithmConstraints != null) {
            provSSLParameters.setAlgorithmConstraints(algorithmConstraints);
        }
        String endpointIdentificationAlgorithm = bCSSLParameters.getEndpointIdentificationAlgorithm();
        if (endpointIdentificationAlgorithm != null) {
            provSSLParameters.setEndpointIdentificationAlgorithm(endpointIdentificationAlgorithm);
        }
        provSSLParameters.setUseCipherSuitesOrder(bCSSLParameters.getUseCipherSuitesOrder());
        List<BCSNIServerName> serverNames = bCSSLParameters.getServerNames();
        if (serverNames != null) {
            provSSLParameters.setServerNames(serverNames);
        }
        Collection<BCSNIMatcher> sNIMatchers = bCSSLParameters.getSNIMatchers();
        if (sNIMatchers != null) {
            provSSLParameters.setSNIMatchers(sNIMatchers);
        }
        String[] applicationProtocols = bCSSLParameters.getApplicationProtocols();
        if (applicationProtocols != null) {
            provSSLParameters.setApplicationProtocols(applicationProtocols);
        }
    }

    public static void setSSLParameters(ProvSSLParameters provSSLParameters, SSLParameters sSLParameters) {
        String[] strArr;
        Object obj;
        Object obj2;
        String str;
        Object obj3;
        String[] cipherSuites = sSLParameters.getCipherSuites();
        if (cipherSuites != null) {
            provSSLParameters.setCipherSuites(cipherSuites);
        }
        String[] protocols = sSLParameters.getProtocols();
        if (protocols != null) {
            provSSLParameters.setProtocols(protocols);
        }
        if (sSLParameters.getNeedClientAuth()) {
            provSSLParameters.setNeedClientAuth(true);
        } else if (sSLParameters.getWantClientAuth()) {
            provSSLParameters.setWantClientAuth(true);
        } else {
            provSSLParameters.setWantClientAuth(false);
        }
        Method method = getAlgorithmConstraints;
        if (method != null && (obj3 = get(sSLParameters, method)) != null) {
            provSSLParameters.setAlgorithmConstraints(JsseUtils_7.importAlgorithmConstraintsDynamic(obj3));
        }
        Method method2 = getEndpointIdentificationAlgorithm;
        if (method2 != null && (str = (String) get(sSLParameters, method2)) != null) {
            provSSLParameters.setEndpointIdentificationAlgorithm(str);
        }
        Method method3 = getUseCipherSuitesOrder;
        if (method3 != null) {
            provSSLParameters.setUseCipherSuitesOrder(((Boolean) get(sSLParameters, method3)).booleanValue());
        }
        Method method4 = getServerNames;
        if (method4 != null && (obj2 = get(sSLParameters, method4)) != null) {
            provSSLParameters.setServerNames(JsseUtils_8.importSNIServerNamesDynamic(obj2));
        }
        Method method5 = getSNIMatchers;
        if (method5 != null && (obj = get(sSLParameters, method5)) != null) {
            provSSLParameters.setSNIMatchers(JsseUtils_8.importSNIMatchersDynamic(obj));
        }
        Method method6 = getApplicationProtocols;
        if (method6 == null || (strArr = (String[]) get(sSLParameters, method6)) == null) {
            return;
        }
        provSSLParameters.setApplicationProtocols(strArr);
    }
}
