package org.bouncycastle.jsse.provider;

import java.security.AccessController;
import java.security.GeneralSecurityException;
import java.security.NoSuchAlgorithmException;
import java.security.PrivilegedAction;
import java.security.Provider;
import java.security.Security;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.Map;
import java.util.Set;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCryptoProvider;
import org.bouncycastle.util.Strings;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public class BouncyCastleJsseProvider extends Provider {
    private static final String JSSE_CONFIG_PROPERTY = "org.bouncycastle.jsse.config";
    private static final String PROVIDER_INFO = "Bouncy Castle JSSE Provider Version 1.0.13";
    public static final String PROVIDER_NAME = "BCJSSE";
    private static final double PROVIDER_VERSION = 1.0013d;
    private static final Map<Map<String, String>, Map<String, String>> attributeMaps = new HashMap();
    private Map<String, EngineCreator> creatorMap;
    private final boolean isInFipsMode;
    private Map<String, BcJsseService> serviceMap;

    public static class BcJsseService extends Provider.Service {
        private final EngineCreator creator;

        public BcJsseService(Provider provider, String str, String str2, String str3, List<String> list, Map<String, String> map, EngineCreator engineCreator) {
            super(provider, str, str2, str3, list, map);
            this.creator = engineCreator;
        }

        @Override // java.security.Provider.Service
        public Object newInstance(Object obj) {
            try {
                Object createInstance = this.creator.createInstance(obj);
                if (createInstance != null) {
                    return createInstance;
                }
                throw new NoSuchAlgorithmException("No such algorithm in FIPS approved mode: " + getAlgorithm());
            } catch (NoSuchAlgorithmException e2) {
                throw e2;
            } catch (Exception e3) {
                throw new NoSuchAlgorithmException("Unable to invoke creator for " + getAlgorithm() + ": " + e3.getMessage(), e3);
            }
        }
    }

    public BouncyCastleJsseProvider() {
        this(getPropertyValue(JSSE_CONFIG_PROPERTY, "default"));
    }

    private JcaTlsCryptoProvider createCryptoProvider(String str) {
        if (str.equalsIgnoreCase("default")) {
            return new JcaTlsCryptoProvider();
        }
        Provider provider = Security.getProvider(str);
        if (provider != null) {
            return new JcaTlsCryptoProvider().setProvider(provider);
        }
        try {
            Object newInstance = Class.forName(str).newInstance();
            if (newInstance instanceof JcaTlsCryptoProvider) {
                return (JcaTlsCryptoProvider) newInstance;
            }
            if (newInstance instanceof Provider) {
                return new JcaTlsCryptoProvider().setProvider((Provider) newInstance);
            }
            throw new IllegalArgumentException("unrecognized class: ".concat(str));
        } catch (ClassNotFoundException unused) {
            throw new IllegalArgumentException("unable to find Provider/JcaTlsCryptoProvider class: ".concat(str));
        } catch (IllegalAccessException e2) {
            StringBuilder m23s = AbstractC0000a.m23s("unable to create Provider/JcaTlsCryptoProvider class '", str, "': ");
            m23s.append(e2.getMessage());
            throw new IllegalArgumentException(m23s.toString(), e2);
        } catch (InstantiationException e3) {
            StringBuilder m23s2 = AbstractC0000a.m23s("unable to create Provider/JcaTlsCryptoProvider class '", str, "': ");
            m23s2.append(e3.getMessage());
            throw new IllegalArgumentException(m23s2.toString(), e3);
        }
    }

    private static Map<String, String> getAttributeMap(Map<String, String> map) {
        Map<Map<String, String>, Map<String, String>> map2 = attributeMaps;
        Map<String, String> map3 = map2.get(map);
        if (map3 != null) {
            return map3;
        }
        map2.put(map, map);
        return map;
    }

    private static String getPropertyValue(final String str, final String str2) {
        return (String) AccessController.doPrivileged(new PrivilegedAction<String>() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.9
            @Override // java.security.PrivilegedAction
            public String run() {
                String property = Security.getProperty(str);
                if (property != null) {
                    return property;
                }
                String property2 = System.getProperty(str);
                return property2 != null ? property2 : str2;
            }
        });
    }

    /* JADX INFO: Access modifiers changed from: private */
    public static List<String> specifyClientProtocols(String... strArr) {
        return Arrays.asList(strArr);
    }

    public void addAlgorithmImplementation(String str, String str2, EngineCreator engineCreator) {
        if (containsKey(str)) {
            throw new IllegalStateException(AbstractC0000a.m16l("duplicate provider key (", str, ") found"));
        }
        addAttribute(str, "ImplementedIn", "Software");
        put(str, str2);
        this.creatorMap.put(str2, engineCreator);
    }

    public void addAlias(String str, String str2) {
        if (containsKey(str)) {
            throw new IllegalStateException(AbstractC0000a.m16l("duplicate provider key (", str, ") found"));
        }
        put(str, str2);
    }

    public void addAttribute(String str, String str2, String str3) {
        String str4 = str + " " + str2;
        if (containsKey(str4)) {
            throw new IllegalStateException(AbstractC0000a.m16l("duplicate provider attribute key (", str4, ") found"));
        }
        put(str4, str3);
    }

    public Provider configure(String str) {
        return new BouncyCastleJsseProvider(str);
    }

    @Override // java.security.Provider
    public final synchronized Provider.Service getService(String str, String str2) {
        String upperCase = Strings.toUpperCase(str2);
        BcJsseService bcJsseService = this.serviceMap.get(str + "." + upperCase);
        if (bcJsseService == null) {
            String str3 = "Alg.Alias." + str + ".";
            String str4 = (String) get(str3 + upperCase);
            if (str4 == null) {
                str4 = upperCase;
            }
            String str5 = (String) get(str + "." + str4);
            if (str5 == null) {
                return null;
            }
            String str6 = str + "." + upperCase + " ";
            ArrayList arrayList = new ArrayList();
            HashMap hashMap = new HashMap();
            for (Object obj : keySet()) {
                String str7 = (String) obj;
                if (str7.startsWith(str3) && get(obj).equals(str2)) {
                    arrayList.add(str7.substring(str3.length()));
                }
                if (str7.startsWith(str6)) {
                    hashMap.put(str7.substring(str6.length()), (String) get(str7));
                }
            }
            BcJsseService bcJsseService2 = new BcJsseService(this, str, upperCase, str5, arrayList, getAttributeMap(hashMap), this.creatorMap.get(str5));
            this.serviceMap.put(str + "." + upperCase, bcJsseService2);
            bcJsseService = bcJsseService2;
        }
        return bcJsseService;
    }

    @Override // java.security.Provider
    public final synchronized Set<Provider.Service> getServices() {
        HashSet hashSet;
        Set<Provider.Service> services = super.getServices();
        hashSet = new HashSet();
        for (Provider.Service service : services) {
            hashSet.add(getService(service.getType(), service.getAlgorithm()));
        }
        return hashSet;
    }

    public boolean isFipsMode() {
        return this.isInFipsMode;
    }

    public BouncyCastleJsseProvider(String str) {
        super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
        this.serviceMap = new HashMap();
        this.creatorMap = new HashMap();
        String trim = str.trim();
        int indexOf = trim.indexOf(58);
        boolean z2 = false;
        if (indexOf >= 0) {
            String trim2 = trim.substring(0, indexOf).trim();
            trim = trim.substring(indexOf + 1).trim();
            z2 = trim2.equalsIgnoreCase("fips");
        }
        try {
            this.isInFipsMode = configure(z2, createCryptoProvider(trim));
        } catch (GeneralSecurityException e2) {
            throw new IllegalArgumentException(AbstractC0000a.m19o(e2, new StringBuilder("unable to set up JcaTlsCryptoProvider: ")), e2);
        }
    }

    private boolean configure(final boolean z2, final JcaTlsCryptoProvider jcaTlsCryptoProvider) {
        addAlgorithmImplementation("KeyManagerFactory.X.509", "org.bouncycastle.jsse.provider.KeyManagerFactory", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.1
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvKeyManagerFactorySpi(z2, jcaTlsCryptoProvider.getHelper());
            }
        });
        addAlias("Alg.Alias.KeyManagerFactory.X509", "X.509");
        addAlias("Alg.Alias.KeyManagerFactory.PKIX", "X.509");
        addAlgorithmImplementation("TrustManagerFactory.PKIX", "org.bouncycastle.jsse.provider.TrustManagerFactory", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.2
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvTrustManagerFactorySpi(z2, jcaTlsCryptoProvider.getHelper());
            }
        });
        addAlias("Alg.Alias.TrustManagerFactory.X.509", "PKIX");
        addAlias("Alg.Alias.TrustManagerFactory.X509", "PKIX");
        addAlgorithmImplementation("SSLContext.TLS", "org.bouncycastle.jsse.provider.SSLContext.TLS", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.3
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvSSLContextSpi(z2, jcaTlsCryptoProvider, null);
            }
        });
        addAlgorithmImplementation("SSLContext.TLSV1", "org.bouncycastle.jsse.provider.SSLContext.TLSv1", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.4
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvSSLContextSpi(z2, jcaTlsCryptoProvider, BouncyCastleJsseProvider.specifyClientProtocols("TLSv1"));
            }
        });
        addAlgorithmImplementation("SSLContext.TLSV1.1", "org.bouncycastle.jsse.provider.SSLContext.TLSv1_1", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.5
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvSSLContextSpi(z2, jcaTlsCryptoProvider, BouncyCastleJsseProvider.specifyClientProtocols("TLSv1.1", "TLSv1"));
            }
        });
        addAlgorithmImplementation("SSLContext.TLSV1.2", "org.bouncycastle.jsse.provider.SSLContext.TLSv1_2", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.6
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvSSLContextSpi(z2, jcaTlsCryptoProvider, BouncyCastleJsseProvider.specifyClientProtocols("TLSv1.2", "TLSv1.1", "TLSv1"));
            }
        });
        addAlgorithmImplementation("SSLContext.TLSV1.3", "org.bouncycastle.jsse.provider.SSLContext.TLSv1_3", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.7
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new ProvSSLContextSpi(z2, jcaTlsCryptoProvider, BouncyCastleJsseProvider.specifyClientProtocols("TLSv1.3", "TLSv1.2", "TLSv1.1", "TLSv1"));
            }
        });
        addAlgorithmImplementation("SSLContext.DEFAULT", "org.bouncycastle.jsse.provider.SSLContext.Default", new EngineCreator() { // from class: org.bouncycastle.jsse.provider.BouncyCastleJsseProvider.8
            @Override // org.bouncycastle.jsse.provider.EngineCreator
            public Object createInstance(Object obj) {
                return new DefaultSSLContextSpi(z2, jcaTlsCryptoProvider);
            }
        });
        addAlias("Alg.Alias.SSLContext.SSL", "TLS");
        addAlias("Alg.Alias.SSLContext.SSLV3", "TLSV1");
        return z2;
    }

    public BouncyCastleJsseProvider(Provider provider) {
        this(false, provider);
    }

    public BouncyCastleJsseProvider(boolean z2) {
        super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
        this.serviceMap = new HashMap();
        this.creatorMap = new HashMap();
        this.isInFipsMode = configure(z2, new JcaTlsCryptoProvider());
    }

    public BouncyCastleJsseProvider(boolean z2, Provider provider) {
        super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
        this.serviceMap = new HashMap();
        this.creatorMap = new HashMap();
        this.isInFipsMode = configure(z2, new JcaTlsCryptoProvider().setProvider(provider));
    }

    public BouncyCastleJsseProvider(boolean z2, JcaTlsCryptoProvider jcaTlsCryptoProvider) {
        super(PROVIDER_NAME, PROVIDER_VERSION, PROVIDER_INFO);
        this.serviceMap = new HashMap();
        this.creatorMap = new HashMap();
        this.isInFipsMode = configure(z2, jcaTlsCryptoProvider);
    }
}
