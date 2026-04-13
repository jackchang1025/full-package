package org.bouncycastle.jsse.provider;

import java.security.KeyManagementException;
import java.security.SecureRandom;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.TrustManager;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCryptoProvider;

/* loaded from: classes.dex */
class DefaultSSLContextSpi extends ProvSSLContextSpi {
    private static final Logger LOG = Logger.getLogger(DefaultSSLContextSpi.class.getName());

    public static class LazyInstance {
        private static final Exception initException;
        private static final DefaultSSLContextSpi instance;

        static {
            Exception exc = LazyManagers.initException;
            DefaultSSLContextSpi defaultSSLContextSpi = null;
            if (exc == null) {
                try {
                    defaultSSLContextSpi = new DefaultSSLContextSpi(false, new JcaTlsCryptoProvider());
                } catch (Exception e2) {
                    DefaultSSLContextSpi.LOG.log(Level.WARNING, "Failed to load default SSLContext", (Throwable) e2);
                    exc = DefaultSSLContextSpi.avoidCapturingException(e2);
                }
            }
            initException = exc;
            instance = defaultSSLContextSpi;
        }

        private LazyInstance() {
        }
    }

    public static class LazyManagers {
        private static final Exception initException;
        private static final KeyManager[] keyManagers;
        private static final TrustManager[] trustManagers;

        /* JADX WARN: Removed duplicated region for block: B:12:0x0031  */
        /* JADX WARN: Removed duplicated region for block: B:8:0x002b  */
        static {
            TrustManager[] trustManagerArr;
            KeyManager[] defaultKeyManagers;
            KeyManager[] keyManagerArr = null;
            try {
                trustManagerArr = ProvSSLContextSpi.getDefaultTrustManagers();
                e = null;
            } catch (Exception e2) {
                e = e2;
                DefaultSSLContextSpi.LOG.log(Level.WARNING, "Failed to load default trust managers", (Throwable) e);
                trustManagerArr = null;
            }
            if (e == null) {
                try {
                    defaultKeyManagers = ProvSSLContextSpi.getDefaultKeyManagers();
                } catch (Exception e3) {
                    e = e3;
                    DefaultSSLContextSpi.LOG.log(Level.WARNING, "Failed to load default key managers", (Throwable) e);
                }
                if (e == null) {
                    e = DefaultSSLContextSpi.avoidCapturingException(e);
                    trustManagerArr = null;
                } else {
                    keyManagerArr = defaultKeyManagers;
                }
                initException = e;
                keyManagers = keyManagerArr;
                trustManagers = trustManagerArr;
            }
            defaultKeyManagers = null;
            if (e == null) {
            }
            initException = e;
            keyManagers = keyManagerArr;
            trustManagers = trustManagerArr;
        }

        private LazyManagers() {
        }
    }

    public DefaultSSLContextSpi(boolean z2, JcaTlsCryptoProvider jcaTlsCryptoProvider) {
        super(z2, jcaTlsCryptoProvider, null);
        if (LazyManagers.initException != null) {
            throw new KeyManagementException("Default key/trust managers unavailable", LazyManagers.initException);
        }
        super.engineInit(LazyManagers.keyManagers, LazyManagers.trustManagers, null);
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static Exception avoidCapturingException(Exception exc) {
        return new KeyManagementException(exc.getMessage());
    }

    public static ProvSSLContextSpi getDefaultInstance() {
        if (LazyInstance.initException == null) {
            return LazyInstance.instance;
        }
        throw LazyInstance.initException;
    }

    @Override // org.bouncycastle.jsse.provider.ProvSSLContextSpi, javax.net.ssl.SSLContextSpi
    public void engineInit(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, SecureRandom secureRandom) {
        throw new KeyManagementException("Default SSLContext is initialized automatically");
    }
}
