package org.bouncycastle.jsse.provider;

import java.net.Socket;
import java.security.KeyStore;
import java.security.Principal;
import java.security.PrivateKey;
import java.security.PublicKey;
import java.security.cert.CertPathValidatorException;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.security.interfaces.DSAPublicKey;
import java.security.interfaces.ECPublicKey;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Date;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import javax.net.ssl.SSLEngine;
import org.bouncycastle.asn1.ASN1ObjectIdentifier;
import org.bouncycastle.asn1.ASN1Primitive;
import org.bouncycastle.asn1.x9.ECNamedCurveTable;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jcajce.util.JcaJceHelper;
import org.bouncycastle.jsse.BCExtendedSSLSession;
import org.bouncycastle.jsse.BCSNIHostName;
import org.bouncycastle.jsse.BCX509ExtendedKeyManager;
import org.bouncycastle.jsse.BCX509Key;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsUtils;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class ProvX509KeyManagerSimple extends BCX509ExtendedKeyManager {
    private final Map<String, Credential> credentials;
    private final JcaJceHelper helper;
    private final boolean isInFipsMode;
    private static final Logger LOG = Logger.getLogger(ProvX509KeyManagerSimple.class.getName());
    private static final Map<String, PublicKeyFilter> FILTERS_CLIENT = createFiltersClient();
    private static final Map<String, PublicKeyFilter> FILTERS_SERVER = createFiltersServer();

    public static class Credential {
        private final String alias;
        private final X509Certificate[] certificateChain;
        private final PrivateKey privateKey;

        public Credential(String str, PrivateKey privateKey, X509Certificate[] x509CertificateArr) {
            this.alias = str;
            this.privateKey = privateKey;
            this.certificateChain = x509CertificateArr;
        }
    }

    public static final class DefaultPublicKeyFilter implements PublicKeyFilter {
        final String algorithm;
        final Class<? extends PublicKey> clazz;
        final int keyUsageBit;

        public DefaultPublicKeyFilter(String str, Class<? extends PublicKey> cls, int i2) {
            this.algorithm = str;
            this.clazz = cls;
            this.keyUsageBit = i2;
        }

        private boolean appliesTo(PublicKey publicKey) {
            Class<? extends PublicKey> cls;
            String str = this.algorithm;
            return (str != null && str.equalsIgnoreCase(JsseUtils.getPublicKeyAlgorithm(publicKey))) || ((cls = this.clazz) != null && cls.isInstance(publicKey));
        }

        @Override // org.bouncycastle.jsse.provider.ProvX509KeyManagerSimple.PublicKeyFilter
        public boolean accepts(PublicKey publicKey, boolean[] zArr, BCAlgorithmConstraints bCAlgorithmConstraints) {
            return appliesTo(publicKey) && ProvAlgorithmChecker.permitsKeyUsage(publicKey, zArr, this.keyUsageBit, bCAlgorithmConstraints);
        }
    }

    public static final class ECPublicKeyFilter13 implements PublicKeyFilter {
        final ASN1ObjectIdentifier standardOID;

        public ECPublicKeyFilter13(ASN1ObjectIdentifier aSN1ObjectIdentifier) {
            this.standardOID = aSN1ObjectIdentifier;
        }

        private boolean appliesTo(PublicKey publicKey) {
            if ("EC".equalsIgnoreCase(JsseUtils.getPublicKeyAlgorithm(publicKey)) || ECPublicKey.class.isInstance(publicKey)) {
                return this.standardOID.equals((ASN1Primitive) JsseUtils.getNamedCurveOID(publicKey));
            }
            return false;
        }

        @Override // org.bouncycastle.jsse.provider.ProvX509KeyManagerSimple.PublicKeyFilter
        public boolean accepts(PublicKey publicKey, boolean[] zArr, BCAlgorithmConstraints bCAlgorithmConstraints) {
            return appliesTo(publicKey) && ProvAlgorithmChecker.permitsKeyUsage(publicKey, zArr, 0, bCAlgorithmConstraints);
        }
    }

    public static final class Match implements Comparable<Match> {
        static final Quality INVALID = Quality.MISMATCH_SNI;
        static final Match NOTHING = new Match(Quality.NONE, Integer.MAX_VALUE, null);
        final Credential credential;
        final int keyTypeIndex;
        final Quality quality;

        public enum Quality {
            OK,
            RSA_MULTI_USE,
            MISMATCH_SNI,
            EXPIRED,
            NONE
        }

        public Match(Quality quality, int i2, Credential credential) {
            this.quality = quality;
            this.keyTypeIndex = i2;
            this.credential = credential;
        }

        public boolean isIdeal() {
            return Quality.OK == this.quality && this.keyTypeIndex == 0;
        }

        public boolean isValid() {
            return this.quality.compareTo(INVALID) < 0;
        }

        @Override // java.lang.Comparable
        public int compareTo(Match match) {
            int compare = Boolean.compare(match.isValid(), isValid());
            if (compare != 0) {
                return compare;
            }
            int compare2 = Integer.compare(this.keyTypeIndex, match.keyTypeIndex);
            return compare2 == 0 ? this.quality.compareTo(match.quality) : compare2;
        }
    }

    public interface PublicKeyFilter {
        boolean accepts(PublicKey publicKey, boolean[] zArr, BCAlgorithmConstraints bCAlgorithmConstraints);
    }

    public ProvX509KeyManagerSimple(boolean z2, JcaJceHelper jcaJceHelper, KeyStore keyStore, char[] cArr) {
        this.isInFipsMode = z2;
        this.helper = jcaJceHelper;
        this.credentials = loadCredentials(keyStore, cArr);
    }

    private static void addECFilter13(Map<String, PublicKeyFilter> map, int i2) {
        ASN1ObjectIdentifier oid;
        if (!NamedGroup.canBeNegotiated(i2, ProtocolVersion.TLSv13)) {
            throw new IllegalStateException("Invalid named group for TLS 1.3 EC filter");
        }
        String curveName = NamedGroup.getCurveName(i2);
        if (curveName != null && (oid = ECNamedCurveTable.getOID(curveName)) != null) {
            addFilterToMap(map, JsseUtils.getKeyType13("EC", i2), new ECPublicKeyFilter13(oid));
            return;
        }
        LOG.warning("Failed to register public key filter for EC with " + NamedGroup.getText(i2));
    }

    private static void addFilter(Map<String, PublicKeyFilter> map, int i2, String str, Class<? extends PublicKey> cls, String... strArr) {
        DefaultPublicKeyFilter defaultPublicKeyFilter = new DefaultPublicKeyFilter(str, cls, i2);
        for (String str2 : strArr) {
            addFilterToMap(map, str2, defaultPublicKeyFilter);
        }
    }

    private static void addFilterLegacyServer(Map<String, PublicKeyFilter> map, int i2, String str, Class<? extends PublicKey> cls, int... iArr) {
        addFilter(map, i2, str, cls, getKeyTypesLegacyServer(iArr));
    }

    private static void addFilterToMap(Map<String, PublicKeyFilter> map, String str, PublicKeyFilter publicKeyFilter) {
        if (map.put(str, publicKeyFilter) != null) {
            throw new IllegalStateException("Duplicate keys in filters");
        }
    }

    private static List<Match> addToMatches(List<Match> list, Match match) {
        if (list == null) {
            list = new ArrayList<>();
        }
        list.add(match);
        return list;
    }

    private String chooseAlias(List<String> list, Principal[] principalArr, TransportData transportData, boolean z2) {
        Match bestMatch = getBestMatch(list, principalArr, transportData, z2);
        if (bestMatch.compareTo(Match.NOTHING) >= 0) {
            LOG.fine("No matching key found");
            return null;
        }
        String str = list.get(bestMatch.keyTypeIndex);
        String alias = getAlias(bestMatch);
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            logger.fine("Found matching key of type: " + str + ", returning alias: " + alias);
        }
        return alias;
    }

    private BCX509Key chooseKeyBC(List<String> list, Principal[] principalArr, TransportData transportData, boolean z2) {
        String str;
        BCX509Key createKeyBC;
        Match bestMatch = getBestMatch(list, principalArr, transportData, z2);
        if (bestMatch.compareTo(Match.NOTHING) >= 0 || (createKeyBC = createKeyBC((str = list.get(bestMatch.keyTypeIndex)), bestMatch.credential)) == null) {
            LOG.fine("No matching key found");
            return null;
        }
        Logger logger = LOG;
        if (logger.isLoggable(Level.FINE)) {
            StringBuilder m23s = AbstractC0000a.m23s("Found matching key of type: ", str, ", from alias: ");
            m23s.append(getAlias(bestMatch));
            logger.fine(m23s.toString());
        }
        return createKeyBC;
    }

    private static Map<String, PublicKeyFilter> createFiltersClient() {
        HashMap hashMap = new HashMap();
        addFilter(hashMap, EdDSAParameterSpec.Ed25519);
        addFilter(hashMap, EdDSAParameterSpec.Ed448);
        addECFilter13(hashMap, 31);
        addECFilter13(hashMap, 32);
        addECFilter13(hashMap, 33);
        addECFilter13(hashMap, 23);
        addECFilter13(hashMap, 24);
        addECFilter13(hashMap, 25);
        addFilter(hashMap, "RSA");
        addFilter(hashMap, "RSASSA-PSS");
        addFilter(hashMap, DSAPublicKey.class, "DSA");
        addFilter(hashMap, ECPublicKey.class, "EC");
        return Collections.unmodifiableMap(hashMap);
    }

    private static Map<String, PublicKeyFilter> createFiltersServer() {
        HashMap hashMap = new HashMap();
        addFilter(hashMap, EdDSAParameterSpec.Ed25519);
        addFilter(hashMap, EdDSAParameterSpec.Ed448);
        addECFilter13(hashMap, 31);
        addECFilter13(hashMap, 32);
        addECFilter13(hashMap, 33);
        addECFilter13(hashMap, 23);
        addECFilter13(hashMap, 24);
        addECFilter13(hashMap, 25);
        addFilter(hashMap, "RSA");
        addFilter(hashMap, "RSASSA-PSS");
        addFilterLegacyServer(hashMap, (Class<? extends PublicKey>) DSAPublicKey.class, 3, 22);
        addFilterLegacyServer(hashMap, (Class<? extends PublicKey>) ECPublicKey.class, 17);
        addFilterLegacyServer(hashMap, "RSA", 5, 19, 23);
        addFilterLegacyServer(hashMap, 2, "RSA", 1);
        return Collections.unmodifiableMap(hashMap);
    }

    private BCX509Key createKeyBC(String str, Credential credential) {
        if (credential == null) {
            return null;
        }
        return new ProvX509Key(str, credential.privateKey, credential.certificateChain);
    }

    private static String getAlias(Match match) {
        return match.credential.alias;
    }

    private static String[] getAliases(List<Match> list) {
        String[] strArr = new String[list.size()];
        Iterator<Match> it = list.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            strArr[i2] = getAlias(it.next());
            i2++;
        }
        return strArr;
    }

    private Match getBestMatch(List<String> list, Principal[] principalArr, TransportData transportData, boolean z2) {
        boolean z3;
        Match match = Match.NOTHING;
        if (this.credentials.isEmpty() || list.isEmpty()) {
            return match;
        }
        int size = list.size();
        Set<Principal> uniquePrincipals = getUniquePrincipals(principalArr);
        BCAlgorithmConstraints algorithmConstraints = TransportData.getAlgorithmConstraints(transportData, true);
        Date date = new Date();
        String requestedHostName = getRequestedHostName(transportData, z2);
        Iterator<Credential> it = this.credentials.values().iterator();
        Match match2 = match;
        int i2 = size;
        while (it.hasNext()) {
            int i3 = i2;
            Match match3 = match2;
            match2 = getPotentialMatch(it.next(), list, i2, uniquePrincipals, algorithmConstraints, z2, date, requestedHostName);
            if (match2.compareTo(match3) >= 0) {
                z3 = true;
                i2 = i3;
                match2 = match3;
            } else {
                if (match2.isIdeal()) {
                    return match2;
                }
                if (match2.isValid()) {
                    z3 = true;
                    i2 = Math.min(i3, match2.keyTypeIndex + 1);
                } else {
                    z3 = true;
                    i2 = i3;
                }
            }
        }
        return match2;
    }

    private static Match.Quality getCertificateQuality(X509Certificate x509Certificate, Date date, String str) {
        try {
            x509Certificate.checkValidity(date);
            if (str != null) {
                try {
                    ProvX509TrustManager.checkEndpointID(str, x509Certificate, "HTTPS");
                } catch (CertificateException unused) {
                    return Match.Quality.MISMATCH_SNI;
                }
            }
            if ("RSA".equalsIgnoreCase(JsseUtils.getPublicKeyAlgorithm(x509Certificate.getPublicKey()))) {
                boolean[] keyUsage = x509Certificate.getKeyUsage();
                if (ProvAlgorithmChecker.supportsKeyUsage(keyUsage, 0) && ProvAlgorithmChecker.supportsKeyUsage(keyUsage, 2)) {
                    return Match.Quality.RSA_MULTI_USE;
                }
            }
            return Match.Quality.OK;
        } catch (CertificateException unused2) {
            return Match.Quality.EXPIRED;
        }
    }

    private Credential getCredential(String str) {
        if (str == null) {
            return null;
        }
        return this.credentials.get(str);
    }

    private static List<String> getKeyTypes(String... strArr) {
        if (strArr == null || strArr.length <= 0) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(strArr.length);
        for (String str : strArr) {
            if (str == null) {
                throw new IllegalArgumentException("Key types cannot be null");
            }
            if (!arrayList.contains(str)) {
                arrayList.add(str);
            }
        }
        return Collections.unmodifiableList(arrayList);
    }

    private static String[] getKeyTypesLegacyServer(int... iArr) {
        int length = iArr.length;
        String[] strArr = new String[length];
        for (int i2 = 0; i2 < length; i2++) {
            strArr[i2] = JsseUtils.getKeyTypeLegacyServer(iArr[i2]);
        }
        return strArr;
    }

    private Match getPotentialMatch(Credential credential, List<String> list, int i2, Set<Principal> set, BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2, Date date, String str) {
        int suitableKeyTypeForEECert;
        X509Certificate[] x509CertificateArr = credential.certificateChain;
        if (!TlsUtils.isNullOrEmpty(x509CertificateArr) && isSuitableChainForIssuers(x509CertificateArr, set) && (suitableKeyTypeForEECert = getSuitableKeyTypeForEECert(x509CertificateArr[0], list, i2, bCAlgorithmConstraints, z2)) >= 0) {
            String str2 = list.get(suitableKeyTypeForEECert);
            Logger logger = LOG;
            logger.finer("EE cert potentially usable for key type: " + str2);
            if (isSuitableChain(x509CertificateArr, bCAlgorithmConstraints, z2)) {
                return new Match(getCertificateQuality(x509CertificateArr[0], date, str), suitableKeyTypeForEECert, credential);
            }
            logger.finer("Unsuitable chain for key type: " + str2);
        }
        return Match.NOTHING;
    }

    private static String getRequestedHostName(TransportData transportData, boolean z2) {
        BCExtendedSSLSession handshakeSession;
        BCSNIHostName sNIHostName;
        if (transportData == null || !z2 || (handshakeSession = transportData.getHandshakeSession()) == null || (sNIHostName = JsseUtils.getSNIHostName(handshakeSession.getRequestedServerNames())) == null) {
            return null;
        }
        return sNIHostName.getAsciiName();
    }

    private static int getSuitableKeyTypeForEECert(X509Certificate x509Certificate, List<String> list, int i2, BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2) {
        Map<String, PublicKeyFilter> map = z2 ? FILTERS_SERVER : FILTERS_CLIENT;
        PublicKey publicKey = x509Certificate.getPublicKey();
        boolean[] keyUsage = x509Certificate.getKeyUsage();
        for (int i3 = 0; i3 < i2; i3++) {
            PublicKeyFilter publicKeyFilter = map.get(list.get(i3));
            if (publicKeyFilter != null && publicKeyFilter.accepts(publicKey, keyUsage, bCAlgorithmConstraints)) {
                return i3;
            }
        }
        return -1;
    }

    private static Set<Principal> getUniquePrincipals(Principal[] principalArr) {
        if (principalArr == null) {
            return null;
        }
        if (principalArr.length > 0) {
            HashSet hashSet = new HashSet();
            for (Principal principal : principalArr) {
                if (principal != null) {
                    hashSet.add(principal);
                }
            }
            if (!hashSet.isEmpty()) {
                return Collections.unmodifiableSet(hashSet);
            }
        }
        return Collections.emptySet();
    }

    private boolean isSuitableChain(X509Certificate[] x509CertificateArr, BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2) {
        try {
            ProvAlgorithmChecker.checkChain(this.isInFipsMode, this.helper, bCAlgorithmConstraints, Collections.emptySet(), x509CertificateArr, ProvX509KeyManager.getRequiredExtendedKeyUsage(z2), -1);
            return true;
        } catch (CertPathValidatorException e2) {
            LOG.log(Level.FINEST, "Certificate chain check failed", (Throwable) e2);
            return false;
        }
    }

    private static boolean isSuitableChainForIssuers(X509Certificate[] x509CertificateArr, Set<Principal> set) {
        if (set == null || set.isEmpty()) {
            return true;
        }
        int length = x509CertificateArr.length;
        do {
            length--;
            if (length < 0) {
                X509Certificate x509Certificate = x509CertificateArr[0];
                return x509Certificate.getBasicConstraints() >= 0 && set.contains(x509Certificate.getSubjectX500Principal());
            }
        } while (!set.contains(x509CertificateArr[length].getIssuerX500Principal()));
        return true;
    }

    private static Map<String, Credential> loadCredentials(KeyStore keyStore, char[] cArr) {
        PrivateKey privateKey;
        HashMap hashMap = new HashMap(4);
        if (keyStore != null) {
            Enumeration<String> aliases = keyStore.aliases();
            while (aliases.hasMoreElements()) {
                String nextElement = aliases.nextElement();
                if (keyStore.entryInstanceOf(nextElement, KeyStore.PrivateKeyEntry.class) && (privateKey = (PrivateKey) keyStore.getKey(nextElement, cArr)) != null) {
                    X509Certificate[] x509CertificateChain = JsseUtils.getX509CertificateChain(keyStore.getCertificateChain(nextElement));
                    if (!TlsUtils.isNullOrEmpty(x509CertificateChain)) {
                        hashMap.put(nextElement, new Credential(nextElement, privateKey, x509CertificateChain));
                    }
                }
            }
        }
        return Collections.unmodifiableMap(hashMap);
    }

    @Override // javax.net.ssl.X509KeyManager
    public String chooseClientAlias(String[] strArr, Principal[] principalArr, Socket socket) {
        return chooseAlias(getKeyTypes(strArr), principalArr, TransportData.from(socket), false);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedKeyManager
    public BCX509Key chooseClientKeyBC(String[] strArr, Principal[] principalArr, Socket socket) {
        return chooseKeyBC(getKeyTypes(strArr), principalArr, TransportData.from(socket), false);
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineClientAlias(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        return chooseAlias(getKeyTypes(strArr), principalArr, TransportData.from(sSLEngine), false);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedKeyManager
    public BCX509Key chooseEngineClientKeyBC(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        return chooseKeyBC(getKeyTypes(strArr), principalArr, TransportData.from(sSLEngine), false);
    }

    @Override // javax.net.ssl.X509ExtendedKeyManager
    public String chooseEngineServerAlias(String str, Principal[] principalArr, SSLEngine sSLEngine) {
        return chooseAlias(getKeyTypes(str), principalArr, TransportData.from(sSLEngine), true);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedKeyManager
    public BCX509Key chooseEngineServerKeyBC(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        return chooseKeyBC(getKeyTypes(strArr), principalArr, TransportData.from(sSLEngine), true);
    }

    @Override // javax.net.ssl.X509KeyManager
    public String chooseServerAlias(String str, Principal[] principalArr, Socket socket) {
        return chooseAlias(getKeyTypes(str), principalArr, TransportData.from(socket), true);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedKeyManager
    public BCX509Key chooseServerKeyBC(String[] strArr, Principal[] principalArr, Socket socket) {
        return chooseKeyBC(getKeyTypes(strArr), principalArr, TransportData.from(socket), true);
    }

    @Override // javax.net.ssl.X509KeyManager
    public X509Certificate[] getCertificateChain(String str) {
        Credential credential = getCredential(str);
        if (credential == null) {
            return null;
        }
        return (X509Certificate[]) credential.certificateChain.clone();
    }

    @Override // javax.net.ssl.X509KeyManager
    public String[] getClientAliases(String str, Principal[] principalArr) {
        return getAliases(getKeyTypes(str), principalArr, null, false);
    }

    @Override // org.bouncycastle.jsse.BCX509ExtendedKeyManager
    public BCX509Key getKeyBC(String str, String str2) {
        return createKeyBC(str, getCredential(str2));
    }

    @Override // javax.net.ssl.X509KeyManager
    public PrivateKey getPrivateKey(String str) {
        Credential credential = getCredential(str);
        if (credential == null) {
            return null;
        }
        return credential.privateKey;
    }

    @Override // javax.net.ssl.X509KeyManager
    public String[] getServerAliases(String str, Principal[] principalArr) {
        return getAliases(getKeyTypes(str), principalArr, null, true);
    }

    private static void addFilter(Map<String, PublicKeyFilter> map, Class<? extends PublicKey> cls, String... strArr) {
        addFilter(map, 0, null, cls, strArr);
    }

    private static void addFilterLegacyServer(Map<String, PublicKeyFilter> map, int i2, String str, int... iArr) {
        addFilterLegacyServer(map, i2, str, null, iArr);
    }

    private String[] getAliases(List<String> list, Principal[] principalArr, TransportData transportData, boolean z2) {
        if (this.credentials.isEmpty() || list.isEmpty()) {
            return null;
        }
        int size = list.size();
        Set<Principal> uniquePrincipals = getUniquePrincipals(principalArr);
        BCAlgorithmConstraints algorithmConstraints = TransportData.getAlgorithmConstraints(transportData, true);
        Date date = new Date();
        String requestedHostName = getRequestedHostName(transportData, z2);
        Iterator<Credential> it = this.credentials.values().iterator();
        List<Match> list2 = null;
        while (it.hasNext()) {
            List<Match> list3 = list2;
            Match potentialMatch = getPotentialMatch(it.next(), list, size, uniquePrincipals, algorithmConstraints, z2, date, requestedHostName);
            list2 = potentialMatch.compareTo(Match.NOTHING) < 0 ? addToMatches(list3, potentialMatch) : list3;
        }
        List<Match> list4 = list2;
        if (list4 == null || list4.isEmpty()) {
            return null;
        }
        Collections.sort(list4);
        return getAliases(list4);
    }

    private static void addFilter(Map<String, PublicKeyFilter> map, String str) {
        addFilter(map, 0, str, null, str);
    }

    private static void addFilterLegacyServer(Map<String, PublicKeyFilter> map, Class<? extends PublicKey> cls, int... iArr) {
        addFilterLegacyServer(map, 0, null, cls, iArr);
    }

    private static void addFilterLegacyServer(Map<String, PublicKeyFilter> map, String str, int... iArr) {
        addFilterLegacyServer(map, 0, str, iArr);
    }
}
