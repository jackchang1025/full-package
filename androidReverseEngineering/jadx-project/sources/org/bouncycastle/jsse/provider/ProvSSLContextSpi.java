package org.bouncycastle.jsse.provider;

import java.security.KeyStore;
import java.security.SecureRandom;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Comparator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.TreeSet;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.KeyManager;
import javax.net.ssl.KeyManagerFactory;
import javax.net.ssl.SSLContextSpi;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.SSLParameters;
import javax.net.ssl.SSLServerSocketFactory;
import javax.net.ssl.SSLSessionContext;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.TrustManagerFactory;
import javax.net.ssl.X509KeyManager;
import javax.net.ssl.X509TrustManager;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCX509ExtendedKeyManager;
import org.bouncycastle.jsse.BCX509ExtendedTrustManager;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;
import org.bouncycastle.tls.CipherSuite;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCryptoProvider;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class ProvSSLContextSpi extends SSLContextSpi {
    private static final List<String> DEFAULT_CIPHERSUITE_LIST;
    private static final List<String> DEFAULT_CIPHERSUITE_LIST_FIPS;
    private static final List<String> DEFAULT_PROTOCOL_LIST;
    private static final List<String> DEFAULT_PROTOCOL_LIST_FIPS;
    private static final String PROPERTY_CLIENT_CIPHERSUITES = "jdk.tls.client.cipherSuites";
    private static final String PROPERTY_CLIENT_PROTOCOLS = "jdk.tls.client.protocols";
    private static final String PROPERTY_SERVER_CIPHERSUITES = "jdk.tls.server.cipherSuites";
    private static final String PROPERTY_SERVER_PROTOCOLS = "jdk.tls.server.protocols";
    private static final Map<String, CipherSuiteInfo> SUPPORTED_CIPHERSUITE_MAP;
    private static final Map<String, CipherSuiteInfo> SUPPORTED_CIPHERSUITE_MAP_FIPS;
    private static final Map<String, ProtocolVersion> SUPPORTED_PROTOCOL_MAP;
    private static final Map<String, ProtocolVersion> SUPPORTED_PROTOCOL_MAP_FIPS;
    private ContextData contextData = null;
    protected final JcaTlsCryptoProvider cryptoProvider;
    protected final String[] defaultCipherSuitesClient;
    protected final String[] defaultCipherSuitesServer;
    protected final String[] defaultProtocolsClient;
    protected final String[] defaultProtocolsServer;
    protected final boolean isInFipsMode;
    protected final Map<String, CipherSuiteInfo> supportedCipherSuites;
    protected final Map<String, ProtocolVersion> supportedProtocols;
    private static final Logger LOG = Logger.getLogger(ProvSSLContextSpi.class.getName());
    private static final Set<BCCryptoPrimitive> TLS_CRYPTO_PRIMITIVES_BC = JsseUtils.KEY_AGREEMENT_CRYPTO_PRIMITIVES_BC;

    static {
        Map<String, CipherSuiteInfo> createSupportedCipherSuiteMap = createSupportedCipherSuiteMap();
        SUPPORTED_CIPHERSUITE_MAP = createSupportedCipherSuiteMap;
        SUPPORTED_CIPHERSUITE_MAP_FIPS = createSupportedCipherSuiteMapFips(createSupportedCipherSuiteMap);
        Map<String, ProtocolVersion> createSupportedProtocolMap = createSupportedProtocolMap();
        SUPPORTED_PROTOCOL_MAP = createSupportedProtocolMap;
        SUPPORTED_PROTOCOL_MAP_FIPS = createSupportedProtocolMapFips(createSupportedProtocolMap);
        List<String> createDefaultCipherSuiteList = createDefaultCipherSuiteList(createSupportedCipherSuiteMap.keySet());
        DEFAULT_CIPHERSUITE_LIST = createDefaultCipherSuiteList;
        DEFAULT_CIPHERSUITE_LIST_FIPS = createDefaultCipherSuiteListFips(createDefaultCipherSuiteList);
        List<String> createDefaultProtocolList = createDefaultProtocolList(createSupportedProtocolMap.keySet());
        DEFAULT_PROTOCOL_LIST = createDefaultProtocolList;
        DEFAULT_PROTOCOL_LIST_FIPS = createDefaultProtocolListFips(createDefaultProtocolList);
    }

    public ProvSSLContextSpi(boolean z2, JcaTlsCryptoProvider jcaTlsCryptoProvider, List<String> list) {
        this.isInFipsMode = z2;
        this.cryptoProvider = jcaTlsCryptoProvider;
        Map<String, CipherSuiteInfo> map = z2 ? SUPPORTED_CIPHERSUITE_MAP_FIPS : SUPPORTED_CIPHERSUITE_MAP;
        this.supportedCipherSuites = map;
        Map<String, ProtocolVersion> map2 = z2 ? SUPPORTED_PROTOCOL_MAP_FIPS : SUPPORTED_PROTOCOL_MAP;
        this.supportedProtocols = map2;
        List<String> list2 = z2 ? DEFAULT_CIPHERSUITE_LIST_FIPS : DEFAULT_CIPHERSUITE_LIST;
        List<String> list3 = z2 ? DEFAULT_PROTOCOL_LIST_FIPS : DEFAULT_PROTOCOL_LIST;
        this.defaultCipherSuitesClient = getDefaultEnabledCipherSuitesClient(map, list2);
        this.defaultCipherSuitesServer = getDefaultEnabledCipherSuitesServer(map, list2);
        this.defaultProtocolsClient = getDefaultEnabledProtocolsClient(map2, list3, list);
        this.defaultProtocolsServer = getDefaultEnabledProtocolsServer(map2, list3);
    }

    private static void addCipherSuite(Map<String, CipherSuiteInfo> map, String str, int i2) {
        if (map.put(str, CipherSuiteInfo.forCipherSuite(i2, str)) != null) {
            throw new IllegalStateException("Duplicate names in supported-cipher-suites");
        }
    }

    private static List<String> createDefaultCipherSuiteList(Set<String> set) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("TLS_CHACHA20_POLY1305_SHA256");
        arrayList.add("TLS_AES_256_GCM_SHA384");
        arrayList.add("TLS_AES_128_GCM_SHA256");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256");
        arrayList.add("TLS_DHE_RSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_DHE_DSS_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_DHE_RSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_DHE_DSS_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256");
        arrayList.add("TLS_DHE_RSA_WITH_AES_256_CBC_SHA256");
        arrayList.add("TLS_DHE_DSS_WITH_AES_256_CBC_SHA256");
        arrayList.add("TLS_DHE_RSA_WITH_AES_128_CBC_SHA256");
        arrayList.add("TLS_DHE_DSS_WITH_AES_128_CBC_SHA256");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_DHE_RSA_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_DHE_DSS_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_DHE_RSA_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_DHE_DSS_WITH_AES_128_CBC_SHA");
        arrayList.add("TLS_RSA_WITH_AES_256_GCM_SHA384");
        arrayList.add("TLS_RSA_WITH_AES_128_GCM_SHA256");
        arrayList.add("TLS_RSA_WITH_AES_256_CBC_SHA256");
        arrayList.add("TLS_RSA_WITH_AES_128_CBC_SHA256");
        arrayList.add("TLS_RSA_WITH_AES_256_CBC_SHA");
        arrayList.add("TLS_RSA_WITH_AES_128_CBC_SHA");
        arrayList.retainAll(set);
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static List<String> createDefaultCipherSuiteListFips(List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        FipsUtils.removeNonFipsCipherSuites(arrayList);
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static List<String> createDefaultProtocolList(Set<String> set) {
        ArrayList arrayList = new ArrayList();
        arrayList.add("TLSv1.2");
        arrayList.add("TLSv1.1");
        arrayList.add("TLSv1");
        arrayList.retainAll(set);
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static List<String> createDefaultProtocolListFips(List<String> list) {
        ArrayList arrayList = new ArrayList(list);
        FipsUtils.removeNonFipsProtocols(arrayList);
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static Map<String, CipherSuiteInfo> createSupportedCipherSuiteMap() {
        TreeMap treeMap = new TreeMap();
        addCipherSuite(treeMap, "TLS_AES_128_CCM_8_SHA256", CipherSuite.TLS_AES_128_CCM_8_SHA256);
        addCipherSuite(treeMap, "TLS_AES_128_CCM_SHA256", CipherSuite.TLS_AES_128_CCM_SHA256);
        addCipherSuite(treeMap, "TLS_AES_128_GCM_SHA256", CipherSuite.TLS_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_AES_256_GCM_SHA384", CipherSuite.TLS_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_CHACHA20_POLY1305_SHA256", CipherSuite.TLS_CHACHA20_POLY1305_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_3DES_EDE_CBC_SHA", 19);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_128_CBC_SHA", 50);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_128_CBC_SHA256", 64);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_128_GCM_SHA256", CipherSuite.TLS_DHE_DSS_WITH_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_256_CBC_SHA", 56);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_256_CBC_SHA256", CipherSuite.TLS_DHE_DSS_WITH_AES_256_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_AES_256_GCM_SHA384", CipherSuite.TLS_DHE_DSS_WITH_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_ARIA_128_CBC_SHA256", CipherSuite.TLS_DHE_DSS_WITH_ARIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_ARIA_128_GCM_SHA256", CipherSuite.TLS_DHE_DSS_WITH_ARIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_ARIA_256_CBC_SHA384", CipherSuite.TLS_DHE_DSS_WITH_ARIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_ARIA_256_GCM_SHA384", CipherSuite.TLS_DHE_DSS_WITH_ARIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA", 68);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256", CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256", CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA", CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256", CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384", CipherSuite.TLS_DHE_DSS_WITH_CAMELLIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_3DES_EDE_CBC_SHA", 22);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_128_CBC_SHA", 51);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_128_CBC_SHA256", CipherSuite.TLS_DHE_RSA_WITH_AES_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_128_CCM", CipherSuite.TLS_DHE_RSA_WITH_AES_128_CCM);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_128_CCM_8", CipherSuite.TLS_DHE_RSA_WITH_AES_128_CCM_8);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_128_GCM_SHA256", CipherSuite.TLS_DHE_RSA_WITH_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_256_CBC_SHA", 57);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_256_CBC_SHA256", CipherSuite.TLS_DHE_RSA_WITH_AES_256_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_256_CCM", CipherSuite.TLS_DHE_RSA_WITH_AES_256_CCM);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_256_CCM_8", CipherSuite.TLS_DHE_RSA_WITH_AES_256_CCM_8);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_AES_256_GCM_SHA384", CipherSuite.TLS_DHE_RSA_WITH_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_ARIA_128_CBC_SHA256", CipherSuite.TLS_DHE_RSA_WITH_ARIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_ARIA_128_GCM_SHA256", CipherSuite.TLS_DHE_RSA_WITH_ARIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_ARIA_256_CBC_SHA384", CipherSuite.TLS_DHE_RSA_WITH_ARIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_ARIA_256_GCM_SHA384", CipherSuite.TLS_DHE_RSA_WITH_ARIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA", 69);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256", CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256", CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA", CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256", CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384", CipherSuite.TLS_DHE_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256", CipherSuite.TLS_DHE_RSA_WITH_CHACHA20_POLY1305_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA", CipherSuite.TLS_ECDHE_ECDSA_WITH_3DES_EDE_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_128_CCM", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CCM);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_128_CCM_8", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_CCM_8);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_256_CCM", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CCM);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_256_CCM_8", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_CCM_8);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_ARIA_128_CBC_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_ARIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_ARIA_128_GCM_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_ARIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_ARIA_256_CBC_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_ARIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_ARIA_256_GCM_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_ARIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384", CipherSuite.TLS_ECDHE_ECDSA_WITH_CAMELLIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256", CipherSuite.TLS_ECDHE_ECDSA_WITH_CHACHA20_POLY1305_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_ECDSA_WITH_NULL_SHA", CipherSuite.TLS_ECDHE_ECDSA_WITH_NULL_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA", CipherSuite.TLS_ECDHE_RSA_WITH_3DES_EDE_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA", CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA", CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_ARIA_128_CBC_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_ARIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_ARIA_128_GCM_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_ARIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_ARIA_256_CBC_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_ARIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_ARIA_256_GCM_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_ARIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384", CipherSuite.TLS_ECDHE_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256", CipherSuite.TLS_ECDHE_RSA_WITH_CHACHA20_POLY1305_SHA256);
        addCipherSuite(treeMap, "TLS_ECDHE_RSA_WITH_NULL_SHA", CipherSuite.TLS_ECDHE_RSA_WITH_NULL_SHA);
        addCipherSuite(treeMap, "TLS_RSA_WITH_3DES_EDE_CBC_SHA", 10);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_128_CBC_SHA", 47);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_128_CBC_SHA256", 60);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_128_CCM", CipherSuite.TLS_RSA_WITH_AES_128_CCM);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_128_CCM_8", CipherSuite.TLS_RSA_WITH_AES_128_CCM_8);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_128_GCM_SHA256", CipherSuite.TLS_RSA_WITH_AES_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_256_CBC_SHA", 53);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_256_CBC_SHA256", 61);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_256_CCM", CipherSuite.TLS_RSA_WITH_AES_256_CCM);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_256_CCM_8", CipherSuite.TLS_RSA_WITH_AES_256_CCM_8);
        addCipherSuite(treeMap, "TLS_RSA_WITH_AES_256_GCM_SHA384", CipherSuite.TLS_RSA_WITH_AES_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_RSA_WITH_ARIA_128_CBC_SHA256", CipherSuite.TLS_RSA_WITH_ARIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_RSA_WITH_ARIA_128_GCM_SHA256", CipherSuite.TLS_RSA_WITH_ARIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_RSA_WITH_ARIA_256_CBC_SHA384", CipherSuite.TLS_RSA_WITH_ARIA_256_CBC_SHA384);
        addCipherSuite(treeMap, "TLS_RSA_WITH_ARIA_256_GCM_SHA384", CipherSuite.TLS_RSA_WITH_ARIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_128_CBC_SHA", 65);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256", CipherSuite.TLS_RSA_WITH_CAMELLIA_128_CBC_SHA256);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256", CipherSuite.TLS_RSA_WITH_CAMELLIA_128_GCM_SHA256);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_256_CBC_SHA", CipherSuite.TLS_RSA_WITH_CAMELLIA_256_CBC_SHA);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_256_CBC_SHA256", 192);
        addCipherSuite(treeMap, "TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384", CipherSuite.TLS_RSA_WITH_CAMELLIA_256_GCM_SHA384);
        addCipherSuite(treeMap, "TLS_RSA_WITH_NULL_SHA", 2);
        addCipherSuite(treeMap, "TLS_RSA_WITH_NULL_SHA256", 59);
        return Collections.unmodifiableMap(treeMap);
    }

    private static Map<String, CipherSuiteInfo> createSupportedCipherSuiteMapFips(Map<String, CipherSuiteInfo> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        FipsUtils.removeNonFipsCipherSuites(linkedHashMap.keySet());
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static Map<String, ProtocolVersion> createSupportedProtocolMap() {
        LinkedHashMap linkedHashMap = new LinkedHashMap();
        linkedHashMap.put("TLSv1.3", ProtocolVersion.TLSv13);
        linkedHashMap.put("TLSv1.2", ProtocolVersion.TLSv12);
        linkedHashMap.put("TLSv1.1", ProtocolVersion.TLSv11);
        linkedHashMap.put("TLSv1", ProtocolVersion.TLSv10);
        linkedHashMap.put("SSLv3", ProtocolVersion.SSLv3);
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static Map<String, ProtocolVersion> createSupportedProtocolMapFips(Map<String, ProtocolVersion> map) {
        LinkedHashMap linkedHashMap = new LinkedHashMap(map);
        FipsUtils.removeNonFipsProtocols(linkedHashMap.keySet());
        return Collections.unmodifiableMap(linkedHashMap);
    }

    private static String[] getArray(Collection<String> collection) {
        return (String[]) collection.toArray(new String[collection.size()]);
    }

    public static CipherSuiteInfo getCipherSuiteInfo(String str) {
        return SUPPORTED_CIPHERSUITE_MAP.get(str);
    }

    public static String getCipherSuiteName(int i2) {
        if (i2 == 0) {
            return "SSL_NULL_WITH_NULL_NULL";
        }
        if (!TlsUtils.isValidUint16(i2)) {
            return null;
        }
        for (CipherSuiteInfo cipherSuiteInfo : SUPPORTED_CIPHERSUITE_MAP.values()) {
            if (cipherSuiteInfo.getCipherSuite() == i2) {
                return cipherSuiteInfo.getName();
            }
        }
        return null;
    }

    private static String[] getDefaultEnabledCipherSuites(Map<String, CipherSuiteInfo> map, String str, List<String> list) {
        List<String> jdkTlsCipherSuites = getJdkTlsCipherSuites(str, list);
        String[] strArr = new String[jdkTlsCipherSuites.size()];
        int i2 = 0;
        for (String str2 : jdkTlsCipherSuites) {
            if (map.containsKey(str2) && ProvAlgorithmConstraints.DEFAULT.permits(TLS_CRYPTO_PRIMITIVES_BC, str2, null)) {
                strArr[i2] = str2;
                i2++;
            }
        }
        return JsseUtils.resize(strArr, i2);
    }

    private static String[] getDefaultEnabledCipherSuitesClient(Map<String, CipherSuiteInfo> map, List<String> list) {
        return getDefaultEnabledCipherSuites(map, PROPERTY_CLIENT_CIPHERSUITES, list);
    }

    private static String[] getDefaultEnabledCipherSuitesServer(Map<String, CipherSuiteInfo> map, List<String> list) {
        return getDefaultEnabledCipherSuites(map, PROPERTY_SERVER_CIPHERSUITES, list);
    }

    private static String[] getDefaultEnabledProtocols(Map<String, ProtocolVersion> map, String str, List<String> list, List<String> list2) {
        if (list2 == null) {
            list2 = getJdkTlsProtocols(str, list);
        }
        String[] strArr = new String[list2.size()];
        int i2 = 0;
        for (String str2 : list2) {
            if (map.containsKey(str2) && ProvAlgorithmConstraints.DEFAULT_TLS_ONLY.permits(TLS_CRYPTO_PRIMITIVES_BC, str2, null)) {
                strArr[i2] = str2;
                i2++;
            }
        }
        return JsseUtils.resize(strArr, i2);
    }

    private static String[] getDefaultEnabledProtocolsClient(Map<String, ProtocolVersion> map, List<String> list, List<String> list2) {
        return getDefaultEnabledProtocols(map, PROPERTY_CLIENT_PROTOCOLS, list, list2);
    }

    private static String[] getDefaultEnabledProtocolsServer(Map<String, ProtocolVersion> map, List<String> list) {
        return getDefaultEnabledProtocols(map, PROPERTY_SERVER_PROTOCOLS, list, null);
    }

    public static KeyManager[] getDefaultKeyManagers() {
        KeyStoreConfig defaultKeyStore = ProvKeyManagerFactorySpi.getDefaultKeyStore();
        KeyManagerFactory keyManagerFactory = KeyManagerFactory.getInstance(KeyManagerFactory.getDefaultAlgorithm());
        keyManagerFactory.init(defaultKeyStore.keyStore, defaultKeyStore.password);
        return keyManagerFactory.getKeyManagers();
    }

    public static TrustManager[] getDefaultTrustManagers() {
        KeyStore defaultTrustStore = ProvTrustManagerFactorySpi.getDefaultTrustStore();
        TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
        trustManagerFactory.init(defaultTrustStore);
        return trustManagerFactory.getTrustManagers();
    }

    private static List<String> getJdkTlsCipherSuites(String str, List<String> list) {
        String[] stringArraySystemProperty = PropertyUtils.getStringArraySystemProperty(str);
        if (stringArraySystemProperty == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(stringArraySystemProperty.length);
        for (String str2 : stringArraySystemProperty) {
            if (!arrayList.contains(str2)) {
                if (SUPPORTED_CIPHERSUITE_MAP.containsKey(str2)) {
                    arrayList.add(str2);
                } else {
                    LOG.warning("'" + str + "' contains unsupported cipher suite: " + str2);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        LOG.severe("'" + str + "' contained no supported cipher suites (ignoring)");
        return list;
    }

    private static List<String> getJdkTlsProtocols(String str, List<String> list) {
        String[] stringArraySystemProperty = PropertyUtils.getStringArraySystemProperty(str);
        if (stringArraySystemProperty == null) {
            return list;
        }
        ArrayList arrayList = new ArrayList(stringArraySystemProperty.length);
        for (String str2 : stringArraySystemProperty) {
            if (!arrayList.contains(str2)) {
                if (SUPPORTED_PROTOCOL_MAP.containsKey(str2)) {
                    arrayList.add(str2);
                } else {
                    LOG.warning("'" + str + "' contains unsupported protocol: " + str2);
                }
            }
        }
        if (!arrayList.isEmpty()) {
            return arrayList;
        }
        LOG.severe("'" + str + "' contained no supported protocols (ignoring)");
        return list;
    }

    private static String[] getKeysArray(Map<String, ?> map) {
        return getArray(map.keySet());
    }

    public static ProtocolVersion getProtocolVersion(String str) {
        return SUPPORTED_PROTOCOL_MAP.get(str);
    }

    public static String getProtocolVersionName(ProtocolVersion protocolVersion) {
        if (protocolVersion == null) {
            return "NONE";
        }
        for (Map.Entry<String, ProtocolVersion> entry : SUPPORTED_PROTOCOL_MAP.entrySet()) {
            if (entry.getValue().equals(protocolVersion)) {
                return entry.getKey();
            }
        }
        return "NONE";
    }

    private String[] implGetDefaultCipherSuites(boolean z2) {
        return z2 ? this.defaultCipherSuitesClient : this.defaultCipherSuitesServer;
    }

    private String[] implGetDefaultProtocols(boolean z2) {
        return z2 ? this.defaultProtocolsClient : this.defaultProtocolsServer;
    }

    @Override // javax.net.ssl.SSLContextSpi
    public synchronized SSLEngine engineCreateSSLEngine() {
        return SSLEngineUtil.create(getContextData());
    }

    @Override // javax.net.ssl.SSLContextSpi
    public synchronized SSLSessionContext engineGetClientSessionContext() {
        return getContextData().getClientSessionContext();
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLParameters engineGetDefaultSSLParameters() {
        getContextData();
        return SSLParametersUtil.getSSLParameters(getDefaultSSLParameters(true));
    }

    @Override // javax.net.ssl.SSLContextSpi
    public synchronized SSLSessionContext engineGetServerSessionContext() {
        return getContextData().getServerSessionContext();
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLServerSocketFactory engineGetServerSocketFactory() {
        return new ProvSSLServerSocketFactory(getContextData());
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLSocketFactory engineGetSocketFactory() {
        return new ProvSSLSocketFactory(getContextData());
    }

    @Override // javax.net.ssl.SSLContextSpi
    public SSLParameters engineGetSupportedSSLParameters() {
        getContextData();
        return SSLParametersUtil.getSSLParameters(getSupportedSSLParameters(true));
    }

    @Override // javax.net.ssl.SSLContextSpi
    public synchronized void engineInit(KeyManager[] keyManagerArr, TrustManager[] trustManagerArr, SecureRandom secureRandom) {
        this.contextData = null;
        JcaTlsCrypto create = this.cryptoProvider.create(secureRandom);
        JcaJceHelper helper = create.getHelper();
        BCX509ExtendedKeyManager selectX509KeyManager = selectX509KeyManager(helper, keyManagerArr);
        BCX509ExtendedTrustManager selectX509TrustManager = selectX509TrustManager(helper, trustManagerArr);
        create.getSecureRandom().nextInt();
        this.contextData = new ContextData(this, create, selectX509KeyManager, selectX509TrustManager);
    }

    /* JADX WARN: Removed duplicated region for block: B:10:0x0046  */
    /* JADX WARN: Removed duplicated region for block: B:13:0x004f A[SYNTHETIC] */
    /*
        Code decompiled incorrectly, please refer to instructions dump.
    */
    public int[] getActiveCipherSuites(JcaTlsCrypto jcaTlsCrypto, ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr) {
        String[] cipherSuitesArray = provSSLParameters.getCipherSuitesArray();
        BCAlgorithmConstraints algorithmConstraints = provSSLParameters.getAlgorithmConstraints();
        ProtocolVersion latestTLS = ProtocolVersion.getLatestTLS(protocolVersionArr);
        ProtocolVersion earliestTLS = ProtocolVersion.getEarliestTLS(protocolVersionArr);
        boolean isTLSv13 = TlsUtils.isTLSv13(latestTLS);
        boolean z2 = !TlsUtils.isTLSv13(earliestTLS);
        int[] iArr = new int[cipherSuitesArray.length];
        int i2 = 0;
        for (String str : cipherSuitesArray) {
            CipherSuiteInfo cipherSuiteInfo = this.supportedCipherSuites.get(str);
            if (cipherSuiteInfo != null) {
                if (cipherSuiteInfo.isTLSv13()) {
                    if (!isTLSv13) {
                    }
                    if (!algorithmConstraints.permits(TLS_CRYPTO_PRIMITIVES_BC, str, null)) {
                        iArr[i2] = cipherSuiteInfo.getCipherSuite();
                        i2++;
                    }
                } else {
                    if (!z2) {
                    }
                    if (!algorithmConstraints.permits(TLS_CRYPTO_PRIMITIVES_BC, str, null)) {
                    }
                }
            }
        }
        int[] supportedCipherSuites = TlsUtils.getSupportedCipherSuites(jcaTlsCrypto, iArr, 0, i2);
        if (supportedCipherSuites.length >= 1) {
            return supportedCipherSuites;
        }
        throw new IllegalStateException("No usable cipher suites enabled");
    }

    public ProtocolVersion[] getActiveProtocolVersions(ProvSSLParameters provSSLParameters) {
        String[] protocolsArray = provSSLParameters.getProtocolsArray();
        BCAlgorithmConstraints algorithmConstraints = provSSLParameters.getAlgorithmConstraints();
        TreeSet treeSet = new TreeSet(new Comparator<ProtocolVersion>() { // from class: org.bouncycastle.jsse.provider.ProvSSLContextSpi.1
            @Override // java.util.Comparator
            public int compare(ProtocolVersion protocolVersion, ProtocolVersion protocolVersion2) {
                if (protocolVersion.isLaterVersionOf(protocolVersion2)) {
                    return -1;
                }
                return protocolVersion2.isLaterVersionOf(protocolVersion) ? 1 : 0;
            }
        });
        for (String str : protocolsArray) {
            ProtocolVersion protocolVersion = this.supportedProtocols.get(str);
            if (protocolVersion != null && algorithmConstraints.permits(TLS_CRYPTO_PRIMITIVES_BC, str, null)) {
                treeSet.add(protocolVersion);
            }
        }
        if (treeSet.isEmpty()) {
            throw new IllegalStateException("No usable protocols enabled");
        }
        return (ProtocolVersion[]) treeSet.toArray(new ProtocolVersion[treeSet.size()]);
    }

    public synchronized ContextData getContextData() {
        ContextData contextData;
        contextData = this.contextData;
        if (contextData == null) {
            throw new IllegalStateException("SSLContext has not been initialized.");
        }
        return contextData;
    }

    public String[] getDefaultCipherSuites(boolean z2) {
        return (String[]) implGetDefaultCipherSuites(z2).clone();
    }

    public String[] getDefaultProtocols(boolean z2) {
        return (String[]) implGetDefaultProtocols(z2).clone();
    }

    public ProvSSLParameters getDefaultSSLParameters(boolean z2) {
        return new ProvSSLParameters(this, implGetDefaultCipherSuites(z2), implGetDefaultProtocols(z2));
    }

    public String[] getSupportedCipherSuites() {
        return getKeysArray(this.supportedCipherSuites);
    }

    public String[] getSupportedProtocols() {
        return getKeysArray(this.supportedProtocols);
    }

    public ProvSSLParameters getSupportedSSLParameters(boolean z2) {
        return new ProvSSLParameters(this, getSupportedCipherSuites(), getSupportedProtocols());
    }

    public boolean isFips() {
        return this.isInFipsMode;
    }

    public boolean isSupportedProtocols(String[] strArr) {
        if (strArr == null) {
            return false;
        }
        for (String str : strArr) {
            if (str == null || !this.supportedProtocols.containsKey(str)) {
                return false;
            }
        }
        return true;
    }

    public BCX509ExtendedKeyManager selectX509KeyManager(JcaJceHelper jcaJceHelper, KeyManager[] keyManagerArr) {
        if (keyManagerArr != null) {
            for (KeyManager keyManager : keyManagerArr) {
                if (keyManager instanceof X509KeyManager) {
                    return X509KeyManagerUtil.importX509KeyManager(jcaJceHelper, (X509KeyManager) keyManager);
                }
            }
        }
        return DummyX509KeyManager.INSTANCE;
    }

    public BCX509ExtendedTrustManager selectX509TrustManager(JcaJceHelper jcaJceHelper, TrustManager[] trustManagerArr) {
        if (trustManagerArr == null) {
            try {
                TrustManagerFactory trustManagerFactory = TrustManagerFactory.getInstance(TrustManagerFactory.getDefaultAlgorithm());
                trustManagerFactory.init((KeyStore) null);
                trustManagerArr = trustManagerFactory.getTrustManagers();
            } catch (Exception e2) {
                LOG.log(Level.WARNING, "Failed to load default trust managers", (Throwable) e2);
            }
        }
        if (trustManagerArr != null) {
            for (TrustManager trustManager : trustManagerArr) {
                if (trustManager instanceof X509TrustManager) {
                    return X509TrustManagerUtil.importX509TrustManager(this.isInFipsMode, jcaJceHelper, (X509TrustManager) trustManager);
                }
            }
        }
        return DummyX509TrustManager.INSTANCE;
    }

    public void updateDefaultSSLParameters(ProvSSLParameters provSSLParameters, boolean z2) {
        if (provSSLParameters.getCipherSuitesArray() == implGetDefaultCipherSuites(!z2)) {
            provSSLParameters.setCipherSuitesArray(implGetDefaultCipherSuites(z2));
        }
        if (provSSLParameters.getProtocolsArray() == implGetDefaultProtocols(!z2)) {
            provSSLParameters.setProtocolsArray(implGetDefaultProtocols(z2));
        }
    }

    public String validateNegotiatedCipherSuite(ProvSSLParameters provSSLParameters, int i2) {
        String cipherSuiteName = getCipherSuiteName(i2);
        if (cipherSuiteName != null && JsseUtils.contains(provSSLParameters.getCipherSuitesArray(), cipherSuiteName) && provSSLParameters.getAlgorithmConstraints().permits(TLS_CRYPTO_PRIMITIVES_BC, cipherSuiteName, null) && this.supportedCipherSuites.containsKey(cipherSuiteName) && (!this.isInFipsMode || FipsUtils.isFipsCipherSuite(cipherSuiteName))) {
            return cipherSuiteName;
        }
        throw new IllegalStateException(AbstractC0000a.m11g("SSL connection negotiated unsupported ciphersuite: ", i2));
    }

    public String validateNegotiatedProtocol(ProvSSLParameters provSSLParameters, ProtocolVersion protocolVersion) {
        String protocolVersionName = getProtocolVersionName(protocolVersion);
        if (protocolVersionName != null && JsseUtils.contains(provSSLParameters.getProtocolsArray(), protocolVersionName) && provSSLParameters.getAlgorithmConstraints().permits(TLS_CRYPTO_PRIMITIVES_BC, protocolVersionName, null) && this.supportedProtocols.containsKey(protocolVersionName) && (!this.isInFipsMode || FipsUtils.isFipsProtocol(protocolVersionName))) {
            return protocolVersionName;
        }
        throw new IllegalStateException("SSL connection negotiated unsupported protocol: " + protocolVersion);
    }

    @Override // javax.net.ssl.SSLContextSpi
    public synchronized SSLEngine engineCreateSSLEngine(String str, int i2) {
        return SSLEngineUtil.create(getContextData(), str, i2);
    }

    public String[] getSupportedCipherSuites(String[] strArr) {
        if (strArr == null) {
            throw new NullPointerException("'cipherSuites' cannot be null");
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (TlsUtils.isNullOrEmpty(str)) {
                throw new IllegalArgumentException("'cipherSuites' cannot contain null or empty string elements");
            }
            if (this.supportedCipherSuites.containsKey(str)) {
                arrayList.add(str);
            }
        }
        return getArray(arrayList);
    }
}
