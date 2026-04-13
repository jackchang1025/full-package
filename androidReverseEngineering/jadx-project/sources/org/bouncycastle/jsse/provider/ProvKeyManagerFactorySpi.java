package org.bouncycastle.jsse.provider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactorySpi;
import javax.net.ssl.KeyStoreBuilderParameters;
import javax.net.ssl.ManagerFactoryParameters;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCX509ExtendedKeyManager;
import org.bouncycastle.tls.TlsUtils;

/* loaded from: classes.dex */
class ProvKeyManagerFactorySpi extends KeyManagerFactorySpi {
    private static final Logger LOG = Logger.getLogger(ProvKeyManagerFactorySpi.class.getName());
    protected final JcaJceHelper helper;
    protected final boolean isInFipsMode;
    protected BCX509ExtendedKeyManager x509KeyManager;

    public ProvKeyManagerFactorySpi(boolean z2, JcaJceHelper jcaJceHelper) {
        this.isInFipsMode = z2;
        this.helper = jcaJceHelper;
    }

    private static KeyStore createKeyStore(String str) {
        String keyStoreType = getKeyStoreType(str);
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.keyStoreProvider");
        return TlsUtils.isNullOrEmpty(stringSystemProperty) ? KeyStore.getInstance(keyStoreType) : KeyStore.getInstance(keyStoreType, stringSystemProperty);
    }

    public static KeyStoreConfig getDefaultKeyStore() {
        BufferedInputStream bufferedInputStream;
        String defaultType = KeyStore.getDefaultType();
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.keyStore");
        BufferedInputStream bufferedInputStream2 = null;
        if ("NONE".equals(stringSystemProperty) || stringSystemProperty == null || !new File(stringSystemProperty).exists()) {
            stringSystemProperty = null;
        }
        KeyStore createKeyStore = createKeyStore(defaultType);
        String sensitiveStringSystemProperty = PropertyUtils.getSensitiveStringSystemProperty("javax.net.ssl.keyStorePassword");
        char[] charArray = sensitiveStringSystemProperty != null ? sensitiveStringSystemProperty.toCharArray() : null;
        try {
            if (stringSystemProperty == null) {
                LOG.config("Initializing default key store as empty");
                bufferedInputStream = null;
            } else {
                LOG.config("Initializing default key store from path: " + stringSystemProperty);
                bufferedInputStream = new BufferedInputStream(new FileInputStream(stringSystemProperty));
            }
            try {
                try {
                    createKeyStore.load(bufferedInputStream, charArray);
                } catch (NullPointerException unused) {
                    createKeyStore = KeyStore.getInstance("BCFKS");
                    createKeyStore.load(null, null);
                }
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                return new KeyStoreConfig(createKeyStore, charArray);
            } catch (Throwable th) {
                bufferedInputStream2 = bufferedInputStream;
                th = th;
                if (bufferedInputStream2 != null) {
                    bufferedInputStream2.close();
                }
                throw th;
            }
        } catch (Throwable th2) {
            th = th2;
        }
    }

    private static String getKeyStoreType(String str) {
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.keyStoreType");
        return stringSystemProperty == null ? str : stringSystemProperty;
    }

    @Override // javax.net.ssl.KeyManagerFactorySpi
    public KeyManager[] engineGetKeyManagers() {
        BCX509ExtendedKeyManager bCX509ExtendedKeyManager = this.x509KeyManager;
        if (bCX509ExtendedKeyManager != null) {
            return new KeyManager[]{bCX509ExtendedKeyManager};
        }
        throw new IllegalStateException("KeyManagerFactory not initialized");
    }

    @Override // javax.net.ssl.KeyManagerFactorySpi
    public void engineInit(KeyStore keyStore, char[] cArr) {
        this.x509KeyManager = new ProvX509KeyManagerSimple(this.isInFipsMode, this.helper, keyStore, cArr);
    }

    @Override // javax.net.ssl.KeyManagerFactorySpi
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        if (!(managerFactoryParameters instanceof KeyStoreBuilderParameters)) {
            throw new InvalidAlgorithmParameterException("Parameters must be instance of KeyStoreBuilderParameters");
        }
        this.x509KeyManager = new ProvX509KeyManager(this.isInFipsMode, this.helper, ((KeyStoreBuilderParameters) managerFactoryParameters).getParameters());
    }
}
