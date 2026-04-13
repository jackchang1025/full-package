package org.bouncycastle.jsse.provider;

import java.io.BufferedInputStream;
import java.io.File;
import java.io.FileInputStream;
import java.security.InvalidAlgorithmParameterException;
import java.security.KeyStore;
import java.security.KeyStoreException;
import java.security.cert.CertPathParameters;
import java.security.cert.Certificate;
import java.security.cert.PKIXParameters;
import java.security.cert.TrustAnchor;
import java.security.cert.X509Certificate;
import java.util.Collections;
import java.util.Enumeration;
import java.util.HashSet;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.CertPathTrustManagerParameters;
import javax.net.ssl.ManagerFactoryParameters;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactorySpi;
import org.bouncycastle.jcajce.provider.keystore.util.AdaptingKeyStoreSpi;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.tls.TlsUtils;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class ProvTrustManagerFactorySpi extends TrustManagerFactorySpi {
    private static final Logger LOG = Logger.getLogger(ProvTrustManagerFactorySpi.class.getName());
    private static final boolean provKeyStoreTypeCompat = PropertyUtils.getBooleanSecurityProperty(AdaptingKeyStoreSpi.COMPAT_OVERRIDE, false);
    protected final JcaJceHelper helper;
    protected final boolean isInFipsMode;
    protected ProvX509TrustManager x509TrustManager;

    public ProvTrustManagerFactorySpi(boolean z2, JcaJceHelper jcaJceHelper) {
        this.isInFipsMode = z2;
        this.helper = jcaJceHelper;
    }

    private static void collectTrustAnchor(Set<TrustAnchor> set, Certificate certificate) {
        if (certificate instanceof X509Certificate) {
            set.add(new TrustAnchor((X509Certificate) certificate, null));
        }
    }

    private static KeyStore createTrustStore(String str) {
        String trustStoreType = getTrustStoreType(str);
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.trustStoreProvider");
        return TlsUtils.isNullOrEmpty(stringSystemProperty) ? KeyStore.getInstance(trustStoreType) : KeyStore.getInstance(trustStoreType, stringSystemProperty);
    }

    /* JADX WARN: Can't wrap try/catch for region: R(10:0|1|(1:48)(1:5)|6|(6:(9:(1:9)(2:36|(2:38|(2:(1:41)|42)(2:43|(1:(1:46)))))|11|(1:13)(1:35)|14|(1:16)(1:32)|17|18|(1:20)|21)|17|18|(0)|21|(1:(1:25)))|47|11|(0)(0)|14|(0)(0)) */
    /* JADX WARN: Can't wrap try/catch for region: R(6:(9:(1:9)(2:36|(2:38|(2:(1:41)|42)(2:43|(1:(1:46)))))|11|(1:13)(1:35)|14|(1:16)(1:32)|17|18|(1:20)|21)|17|18|(0)|21|(1:(1:25))) */
    /* JADX WARN: Code restructure failed: missing block: B:10:0x0030, code lost:
    
        if (new java.io.File(r3).exists() != false) goto L27;
     */
    /* JADX WARN: Code restructure failed: missing block: B:24:0x00c1, code lost:
    
        r1 = java.security.KeyStore.getInstance("BCFKS");
        r1.load(null, null);
     */
    /* JADX WARN: Code restructure failed: missing block: B:33:0x00d2, code lost:
    
        r0 = move-exception;
     */
    /* JADX WARN: Code restructure failed: missing block: B:34:0x00d3, code lost:
    
        r1 = r0;
     */
    /* JADX WARN: Removed duplicated region for block: B:13:0x008f  */
    /* JADX WARN: Removed duplicated region for block: B:16:0x0097 A[Catch: all -> 0x00d2, TRY_ENTER, TryCatch #0 {all -> 0x00d2, blocks: (B:16:0x0097, B:32:0x00a0), top: B:14:0x0095 }] */
    /* JADX WARN: Removed duplicated region for block: B:20:0x00cc  */
    /* JADX WARN: Removed duplicated region for block: B:32:0x00a0 A[Catch: all -> 0x00d2, TRY_LEAVE, TryCatch #0 {all -> 0x00d2, blocks: (B:16:0x0097, B:32:0x00a0), top: B:14:0x0095 }] */
    /* JADX WARN: Removed duplicated region for block: B:35:0x0094  */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public static KeyStore getDefaultTrustStore() {
        BufferedInputStream bufferedInputStream;
        String defaultType = KeyStore.getDefaultType();
        boolean z2 = provKeyStoreTypeCompat && "pkcs12".equalsIgnoreCase(defaultType);
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.trustStore");
        BufferedInputStream bufferedInputStream2 = null;
        try {
            if (!"NONE".equals(stringSystemProperty)) {
                if (stringSystemProperty == null) {
                    String stringSystemProperty2 = PropertyUtils.getStringSystemProperty("java.home");
                    if (stringSystemProperty2 != null) {
                        StringBuilder m20p = AbstractC0000a.m20p(stringSystemProperty2);
                        String str = File.separator;
                        m20p.append("/lib/security/jssecacerts".replace("/", str));
                        String sb = m20p.toString();
                        if (new File(sb).exists()) {
                            if (z2) {
                                defaultType = "jks";
                            }
                            stringSystemProperty = sb;
                        } else {
                            StringBuilder m20p2 = AbstractC0000a.m20p(stringSystemProperty2);
                            m20p2.append("/lib/security/cacerts".replace("/", str));
                            stringSystemProperty = m20p2.toString();
                            if (new File(stringSystemProperty).exists()) {
                                if (z2) {
                                    defaultType = "jks";
                                }
                            }
                        }
                    }
                }
                KeyStore createTrustStore = createTrustStore(defaultType);
                String sensitiveStringSystemProperty = PropertyUtils.getSensitiveStringSystemProperty("javax.net.ssl.trustStorePassword");
                char[] charArray = sensitiveStringSystemProperty == null ? sensitiveStringSystemProperty.toCharArray() : null;
                if (stringSystemProperty != null) {
                    LOG.config("Initializing default trust store as empty");
                    bufferedInputStream = null;
                } else {
                    LOG.config("Initializing default trust store from path: " + stringSystemProperty);
                    bufferedInputStream = new BufferedInputStream(new FileInputStream(stringSystemProperty));
                }
                createTrustStore.load(bufferedInputStream, charArray);
                if (bufferedInputStream != null) {
                    bufferedInputStream.close();
                }
                return createTrustStore;
            }
            createTrustStore.load(bufferedInputStream, charArray);
            if (bufferedInputStream != null) {
            }
            return createTrustStore;
        } catch (Throwable th) {
            Throwable th2 = th;
            bufferedInputStream2 = bufferedInputStream;
            if (bufferedInputStream2 == null) {
                throw th2;
            }
            bufferedInputStream2.close();
            throw th2;
        }
        stringSystemProperty = null;
        KeyStore createTrustStore2 = createTrustStore(defaultType);
        String sensitiveStringSystemProperty2 = PropertyUtils.getSensitiveStringSystemProperty("javax.net.ssl.trustStorePassword");
        if (sensitiveStringSystemProperty2 == null) {
        }
        if (stringSystemProperty != null) {
        }
    }

    private static Set<TrustAnchor> getTrustAnchors(KeyStore keyStore) {
        Certificate certificate;
        Certificate[] certificateChain;
        if (keyStore == null) {
            return Collections.emptySet();
        }
        HashSet hashSet = new HashSet();
        Enumeration<String> aliases = keyStore.aliases();
        while (aliases.hasMoreElements()) {
            String nextElement = aliases.nextElement();
            if (keyStore.isCertificateEntry(nextElement)) {
                certificate = keyStore.getCertificate(nextElement);
            } else if (keyStore.isKeyEntry(nextElement) && (certificateChain = keyStore.getCertificateChain(nextElement)) != null && certificateChain.length > 0) {
                certificate = certificateChain[0];
            }
            collectTrustAnchor(hashSet, certificate);
        }
        return hashSet;
    }

    private static String getTrustStoreType(String str) {
        String stringSystemProperty = PropertyUtils.getStringSystemProperty("javax.net.ssl.trustStoreType");
        return stringSystemProperty == null ? str : stringSystemProperty;
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public TrustManager[] engineGetTrustManagers() {
        ProvX509TrustManager provX509TrustManager = this.x509TrustManager;
        if (provX509TrustManager != null) {
            return new TrustManager[]{provX509TrustManager.getExportX509TrustManager()};
        }
        throw new IllegalStateException("TrustManagerFactory not initialized");
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(KeyStore keyStore) {
        if (keyStore == null) {
            try {
                keyStore = getDefaultTrustStore();
            } catch (Error e2) {
                LOG.log(Level.WARNING, "Skipped default trust store", (Throwable) e2);
                throw e2;
            } catch (SecurityException e3) {
                LOG.log(Level.WARNING, "Skipped default trust store", (Throwable) e3);
            } catch (RuntimeException e4) {
                LOG.log(Level.WARNING, "Skipped default trust store", (Throwable) e4);
                throw e4;
            } catch (Exception e5) {
                LOG.log(Level.WARNING, "Skipped default trust store", (Throwable) e5);
                throw new KeyStoreException("Failed to load default trust store", e5);
            }
        }
        try {
            this.x509TrustManager = new ProvX509TrustManager(this.isInFipsMode, this.helper, getTrustAnchors(keyStore));
        } catch (InvalidAlgorithmParameterException e6) {
            throw new KeyStoreException("Failed to create trust manager", e6);
        }
    }

    @Override // javax.net.ssl.TrustManagerFactorySpi
    public void engineInit(ManagerFactoryParameters managerFactoryParameters) {
        if (!(managerFactoryParameters instanceof CertPathTrustManagerParameters)) {
            if (managerFactoryParameters != null) {
                throw new InvalidAlgorithmParameterException("unknown spec: ".concat(managerFactoryParameters.getClass().getName()));
            }
            throw new InvalidAlgorithmParameterException("spec cannot be null");
        }
        CertPathParameters parameters = ((CertPathTrustManagerParameters) managerFactoryParameters).getParameters();
        if (!(parameters instanceof PKIXParameters)) {
            throw new InvalidAlgorithmParameterException("parameters must inherit from PKIXParameters");
        }
        this.x509TrustManager = new ProvX509TrustManager(this.isInFipsMode, this.helper, (PKIXParameters) parameters);
    }
}
