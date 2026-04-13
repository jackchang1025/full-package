package org.bouncycastle.jsse.provider;

import java.lang.reflect.Constructor;
import javax.net.ssl.X509TrustManager;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCX509ExtendedTrustManager;

/* loaded from: classes.dex */
abstract class X509TrustManagerUtil {
    private static final Constructor<? extends X509TrustManager> exportX509TrustManagerConstructor;
    private static final Constructor<? extends BCX509ExtendedTrustManager> importX509TrustManagerConstructor;
    private static final Class<?> x509ExtendedTrustManagerClass;

    /* JADX WARN: Removed duplicated region for block: B:14:0x0027 A[EXC_TOP_SPLITTER, SYNTHETIC] */
    static {
        Class<?> cls;
        Constructor<? extends X509TrustManager> constructor;
        Class<?> cls2;
        Constructor<? extends BCX509ExtendedTrustManager> constructor2 = null;
        try {
            cls = ReflectionUtil.getClass("javax.net.ssl.X509ExtendedTrustManager");
        } catch (Exception unused) {
            cls = null;
        }
        x509ExtendedTrustManagerClass = cls;
        if (ReflectionUtil.getMethods("javax.net.ssl.X509ExtendedTrustManager") != null) {
            constructor = ReflectionUtil.getDeclaredConstructor("org.bouncycastle.jsse.provider.ExportX509TrustManager_7", BCX509ExtendedTrustManager.class);
            exportX509TrustManagerConstructor = constructor;
            cls2 = x509ExtendedTrustManagerClass;
            if (cls2 != null) {
                try {
                    constructor2 = ReflectionUtil.getDeclaredConstructor("org.bouncycastle.jsse.provider.ImportX509TrustManager_7", cls2);
                } catch (Exception unused2) {
                }
            }
            importX509TrustManagerConstructor = constructor2;
        }
        constructor = null;
        exportX509TrustManagerConstructor = constructor;
        cls2 = x509ExtendedTrustManagerClass;
        if (cls2 != null) {
        }
        importX509TrustManagerConstructor = constructor2;
    }

    public static X509TrustManager exportX509TrustManager(BCX509ExtendedTrustManager bCX509ExtendedTrustManager) {
        if (bCX509ExtendedTrustManager instanceof ImportX509TrustManager) {
            return ((ImportX509TrustManager) bCX509ExtendedTrustManager).unwrap();
        }
        Constructor<? extends X509TrustManager> constructor = exportX509TrustManagerConstructor;
        if (constructor != null) {
            try {
                return constructor.newInstance(bCX509ExtendedTrustManager);
            } catch (Exception unused) {
            }
        }
        return new ExportX509TrustManager_5(bCX509ExtendedTrustManager);
    }

    public static BCX509ExtendedTrustManager importX509TrustManager(boolean z2, JcaJceHelper jcaJceHelper, X509TrustManager x509TrustManager) {
        if (x509TrustManager instanceof BCX509ExtendedTrustManager) {
            return (BCX509ExtendedTrustManager) x509TrustManager;
        }
        if (x509TrustManager instanceof ExportX509TrustManager) {
            return ((ExportX509TrustManager) x509TrustManager).unwrap();
        }
        Constructor<? extends BCX509ExtendedTrustManager> constructor = importX509TrustManagerConstructor;
        if (constructor != null && x509ExtendedTrustManagerClass.isInstance(x509TrustManager)) {
            try {
                return constructor.newInstance(x509TrustManager);
            } catch (Exception unused) {
            }
        }
        return new ImportX509TrustManager_5(z2, jcaJceHelper, x509TrustManager);
    }
}
