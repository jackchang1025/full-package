package org.bouncycastle.jsse.provider;

import java.security.AlgorithmParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;
import org.bouncycastle.jcajce.spec.EdDSAParameterSpec;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;
import org.bouncycastle.jsse.provider.NamedGroupInfo;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.SignatureAndHashAlgorithm;
import org.bouncycastle.tls.SignatureScheme;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class SignatureSchemeInfo {
    private static final String PROPERTY_CLIENT_SIGNATURE_SCHEMES = "jdk.tls.client.SignatureSchemes";
    private static final String PROPERTY_SERVER_SIGNATURE_SCHEMES = "jdk.tls.server.SignatureSchemes";
    static final int historical_dsa_sha1 = 514;
    static final int historical_dsa_sha224 = 770;
    static final int historical_dsa_sha256 = 1026;
    static final int historical_ecdsa_sha224 = 771;
    static final int historical_rsa_md5 = 257;
    static final int historical_rsa_sha224 = 769;
    private final AlgorithmParameters algorithmParameters;
    private final All all;
    private final boolean disabled13;
    private final boolean enabled;
    private final NamedGroupInfo namedGroupInfo;
    private static final Logger LOG = Logger.getLogger(SignatureSchemeInfo.class.getName());
    private static final int[] CANDIDATES_DEFAULT = createCandidatesDefault();

    public enum All {
        ed25519(SignatureScheme.ed25519, EdDSAParameterSpec.Ed25519, EdDSAParameterSpec.Ed25519),
        ed448(SignatureScheme.ed448, EdDSAParameterSpec.Ed448, EdDSAParameterSpec.Ed448),
        ecdsa_secp256r1_sha256(SignatureScheme.ecdsa_secp256r1_sha256, "SHA256withECDSA", "EC"),
        ecdsa_secp384r1_sha384(SignatureScheme.ecdsa_secp384r1_sha384, "SHA384withECDSA", "EC"),
        ecdsa_secp521r1_sha512(SignatureScheme.ecdsa_secp521r1_sha512, "SHA512withECDSA", "EC"),
        ecdsa_brainpoolP256r1tls13_sha256(SignatureScheme.ecdsa_brainpoolP256r1tls13_sha256, "SHA256withECDSA", "EC"),
        ecdsa_brainpoolP384r1tls13_sha384(SignatureScheme.ecdsa_brainpoolP384r1tls13_sha384, "SHA384withECDSA", "EC"),
        ecdsa_brainpoolP512r1tls13_sha512(SignatureScheme.ecdsa_brainpoolP512r1tls13_sha512, "SHA512withECDSA", "EC"),
        rsa_pss_pss_sha256(SignatureScheme.rsa_pss_pss_sha256, "SHA256withRSAandMGF1", "RSASSA-PSS"),
        rsa_pss_pss_sha384(SignatureScheme.rsa_pss_pss_sha384, "SHA384withRSAandMGF1", "RSASSA-PSS"),
        rsa_pss_pss_sha512(SignatureScheme.rsa_pss_pss_sha512, "SHA512withRSAandMGF1", "RSASSA-PSS"),
        rsa_pss_rsae_sha256(SignatureScheme.rsa_pss_rsae_sha256, "SHA256withRSAandMGF1", "RSA"),
        rsa_pss_rsae_sha384(SignatureScheme.rsa_pss_rsae_sha384, "SHA384withRSAandMGF1", "RSA"),
        rsa_pss_rsae_sha512(SignatureScheme.rsa_pss_rsae_sha512, "SHA512withRSAandMGF1", "RSA"),
        rsa_pkcs1_sha256(1025, "SHA256withRSA", "RSA", true),
        rsa_pkcs1_sha384(SignatureScheme.rsa_pkcs1_sha384, "SHA384withRSA", "RSA", true),
        rsa_pkcs1_sha512(SignatureScheme.rsa_pkcs1_sha512, "SHA512withRSA", "RSA", true),
        sm2sig_sm3(SignatureScheme.sm2sig_sm3, "SM3withSM2", "EC"),
        dsa_sha256(SignatureSchemeInfo.historical_dsa_sha256, "dsa_sha256", "SHA256withDSA", "DSA"),
        ecdsa_sha224(SignatureSchemeInfo.historical_ecdsa_sha224, "ecdsa_sha224", "SHA224withECDSA", "EC"),
        rsa_sha224(SignatureSchemeInfo.historical_rsa_sha224, "rsa_sha224", "SHA224withRSA", "RSA"),
        dsa_sha224(SignatureSchemeInfo.historical_dsa_sha224, "dsa_sha224", "SHA224withDSA", "DSA"),
        ecdsa_sha1(SignatureScheme.ecdsa_sha1, "SHA1withECDSA", "EC", true),
        rsa_pkcs1_sha1(513, "SHA1withRSA", "RSA", true),
        dsa_sha1(SignatureSchemeInfo.historical_dsa_sha1, "dsa_sha1", "SHA1withDSA", "DSA"),
        rsa_md5(257, "rsa_md5", "MD5withRSA", "RSA");

        private final String jcaSignatureAlgorithm;
        private final String jcaSignatureAlgorithmBC;
        private final String keyAlgorithm;
        private final String keyType13;
        private final String name;
        private final int namedGroup13;
        private final int signatureScheme;
        private final boolean supportedCerts13;
        private final boolean supportedPost13;
        private final boolean supportedPre13;
        private final String text;

        All(int i2, String str, String str2) {
            this(i2, str, str2, true, true, SignatureScheme.getNamedGroup(i2));
        }

        All(int i2, String str, String str2, String str3) {
            this(i2, str, str2, str3, false, false, -1);
        }

        All(int i2, String str, String str2, String str3, boolean z2, boolean z3, int i3) {
            String keyType13 = JsseUtils.getKeyType13(str3, i3);
            String jcaSignatureAlgorithmBC = JsseUtils.getJcaSignatureAlgorithmBC(str2, str3);
            this.signatureScheme = i2;
            this.name = str;
            StringBuilder m22r = AbstractC0000a.m22r(str, "(0x");
            m22r.append(Integer.toHexString(i2));
            m22r.append(")");
            this.text = m22r.toString();
            this.jcaSignatureAlgorithm = str2;
            this.jcaSignatureAlgorithmBC = jcaSignatureAlgorithmBC;
            this.keyAlgorithm = str3;
            this.keyType13 = keyType13;
            this.supportedPost13 = z2;
            this.supportedPre13 = i3 < 0 || NamedGroup.canBeNegotiated(i3, ProtocolVersion.TLSv12);
            this.supportedCerts13 = z3;
            this.namedGroup13 = i3;
        }

        All(int i2, String str, String str2, boolean z2) {
            this(i2, str, str2, false, z2, -1);
        }

        All(int i2, String str, String str2, boolean z2, boolean z3, int i3) {
            this(i2, SignatureScheme.getName(i2), str, str2, z2, z3, i3);
        }
    }

    public static class PerContext {
        private final int[] candidatesClient;
        private final int[] candidatesServer;
        private final Map<Integer, SignatureSchemeInfo> index;

        public PerContext(Map<Integer, SignatureSchemeInfo> map, int[] iArr, int[] iArr2) {
            this.index = map;
            this.candidatesClient = iArr;
            this.candidatesServer = iArr2;
        }
    }

    public SignatureSchemeInfo(All all, AlgorithmParameters algorithmParameters, NamedGroupInfo namedGroupInfo, boolean z2, boolean z3) {
        this.all = all;
        this.algorithmParameters = algorithmParameters;
        this.namedGroupInfo = namedGroupInfo;
        this.enabled = z2;
        this.disabled13 = z3;
    }

    private static void addSignatureScheme(boolean z2, JcaTlsCrypto jcaTlsCrypto, NamedGroupInfo.PerContext perContext, Map<Integer, SignatureSchemeInfo> map, All all) {
        boolean z3;
        NamedGroupInfo namedGroupInfo;
        boolean z4;
        int i2 = all.signatureScheme;
        if (!z2 || FipsUtils.isFipsSignatureScheme(i2)) {
            int i3 = all.namedGroup13;
            AlgorithmParameters algorithmParameters = null;
            if (i3 >= 0) {
                NamedGroupInfo namedGroup = NamedGroupInfo.getNamedGroup(perContext, i3);
                if (namedGroup != null && namedGroup.isEnabled() && namedGroup.isSupportedPost13()) {
                    namedGroupInfo = namedGroup;
                    z3 = false;
                } else {
                    namedGroupInfo = namedGroup;
                    z3 = true;
                }
            } else {
                z3 = false;
                namedGroupInfo = null;
            }
            boolean hasSignatureScheme = jcaTlsCrypto.hasSignatureScheme(i2);
            if (hasSignatureScheme) {
                try {
                    algorithmParameters = jcaTlsCrypto.getSignatureSchemeAlgorithmParameters(i2);
                } catch (Exception unused) {
                    z4 = false;
                }
            }
            z4 = hasSignatureScheme;
            if (map.put(Integer.valueOf(i2), new SignatureSchemeInfo(all, algorithmParameters, namedGroupInfo, z4, z3)) != null) {
                throw new IllegalStateException("Duplicate entries for SignatureSchemeInfo");
            }
        }
    }

    private static int[] createCandidates(Map<Integer, SignatureSchemeInfo> map, String str) {
        Logger logger;
        StringBuilder sb;
        String str2;
        String[] stringArraySystemProperty = PropertyUtils.getStringArraySystemProperty(str);
        if (stringArraySystemProperty == null) {
            return CANDIDATES_DEFAULT;
        }
        int length = stringArraySystemProperty.length;
        int[] iArr = new int[length];
        int i2 = 0;
        for (String str3 : stringArraySystemProperty) {
            int signatureSchemeByName = getSignatureSchemeByName(str3);
            if (signatureSchemeByName < 0) {
                logger = LOG;
                sb = new StringBuilder("'");
                sb.append(str);
                str2 = "' contains unrecognised SignatureScheme: ";
            } else {
                SignatureSchemeInfo signatureSchemeInfo = map.get(Integer.valueOf(signatureSchemeByName));
                if (signatureSchemeInfo == null) {
                    logger = LOG;
                    sb = new StringBuilder("'");
                    sb.append(str);
                    str2 = "' contains unsupported SignatureScheme: ";
                } else if (signatureSchemeInfo.isEnabled()) {
                    iArr[i2] = signatureSchemeByName;
                    i2++;
                } else {
                    logger = LOG;
                    sb = new StringBuilder("'");
                    sb.append(str);
                    str2 = "' contains disabled SignatureScheme: ";
                }
            }
            sb.append(str2);
            sb.append(str3);
            logger.warning(sb.toString());
        }
        if (i2 < length) {
            iArr = Arrays.copyOf(iArr, i2);
        }
        if (iArr.length < 1) {
            LOG.severe("'" + str + "' contained no usable SignatureScheme values");
        }
        return iArr;
    }

    private static int[] createCandidatesDefault() {
        All[] values = All.values();
        int[] iArr = new int[values.length];
        for (int i2 = 0; i2 < values.length; i2++) {
            iArr[i2] = values[i2].signatureScheme;
        }
        return iArr;
    }

    private static Map<Integer, SignatureSchemeInfo> createIndex(boolean z2, JcaTlsCrypto jcaTlsCrypto, NamedGroupInfo.PerContext perContext) {
        TreeMap treeMap = new TreeMap();
        for (All all : All.values()) {
            addSignatureScheme(z2, jcaTlsCrypto, perContext, treeMap, all);
        }
        return treeMap;
    }

    public static PerContext createPerContext(boolean z2, JcaTlsCrypto jcaTlsCrypto, NamedGroupInfo.PerContext perContext) {
        Map<Integer, SignatureSchemeInfo> createIndex = createIndex(z2, jcaTlsCrypto, perContext);
        return new PerContext(createIndex, createCandidates(createIndex, PROPERTY_CLIENT_SIGNATURE_SCHEMES), createCandidates(createIndex, PROPERTY_SERVER_SIGNATURE_SCHEMES));
    }

    public static List<SignatureSchemeInfo> getActiveCertsSignatureSchemes(PerContext perContext, boolean z2, ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr, NamedGroupInfo.PerConnection perConnection) {
        ProtocolVersion latestTLS = ProtocolVersion.getLatestTLS(protocolVersionArr);
        if (!TlsUtils.isSignatureAlgorithmsExtensionAllowed(latestTLS)) {
            return null;
        }
        int[] iArr = z2 ? perContext.candidatesServer : perContext.candidatesClient;
        ProtocolVersion earliestTLS = ProtocolVersion.getEarliestTLS(protocolVersionArr);
        BCAlgorithmConstraints algorithmConstraints = provSSLParameters.getAlgorithmConstraints();
        boolean isTLSv13 = TlsUtils.isTLSv13(latestTLS);
        boolean z3 = !TlsUtils.isTLSv13(earliestTLS);
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            SignatureSchemeInfo signatureSchemeInfo = (SignatureSchemeInfo) perContext.index.get(Integers.valueOf(i2));
            if (signatureSchemeInfo != null && signatureSchemeInfo.isActiveCerts(algorithmConstraints, isTLSv13, z3, perConnection)) {
                arrayList.add(signatureSchemeInfo);
            }
        }
        if (arrayList.isEmpty()) {
            return Collections.emptyList();
        }
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    public static String[] getJcaSignatureAlgorithms(Collection<SignatureSchemeInfo> collection) {
        if (collection == null) {
            return TlsUtils.EMPTY_STRINGS;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<SignatureSchemeInfo> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getJcaSignatureAlgorithm());
        }
        return (String[]) arrayList.toArray(TlsUtils.EMPTY_STRINGS);
    }

    public static String[] getJcaSignatureAlgorithmsBC(Collection<SignatureSchemeInfo> collection) {
        if (collection == null) {
            return TlsUtils.EMPTY_STRINGS;
        }
        ArrayList arrayList = new ArrayList();
        Iterator<SignatureSchemeInfo> it = collection.iterator();
        while (it.hasNext()) {
            arrayList.add(it.next().getJcaSignatureAlgorithmBC());
        }
        return (String[]) arrayList.toArray(TlsUtils.EMPTY_STRINGS);
    }

    public static Vector<SignatureAndHashAlgorithm> getSignatureAndHashAlgorithms(List<SignatureSchemeInfo> list) {
        if (list == null || list.isEmpty()) {
            return null;
        }
        Vector<SignatureAndHashAlgorithm> vector = new Vector<>(list.size());
        for (SignatureSchemeInfo signatureSchemeInfo : list) {
            if (signatureSchemeInfo != null) {
                vector.add(signatureSchemeInfo.getSignatureAndHashAlgorithm());
            }
        }
        if (vector.isEmpty()) {
            return null;
        }
        vector.trimToSize();
        return vector;
    }

    private static int getSignatureSchemeByName(String str) {
        for (All all : All.values()) {
            if (all.name.equalsIgnoreCase(str)) {
                return all.signatureScheme;
            }
        }
        return -1;
    }

    public static List<SignatureSchemeInfo> getSignatureSchemes(PerContext perContext, Vector<SignatureAndHashAlgorithm> vector) {
        if (vector == null || vector.isEmpty()) {
            return null;
        }
        int size = vector.size();
        ArrayList arrayList = new ArrayList(size);
        for (int i2 = 0; i2 < size; i2++) {
            SignatureAndHashAlgorithm elementAt = vector.elementAt(i2);
            if (elementAt != null) {
                SignatureSchemeInfo signatureSchemeInfo = (SignatureSchemeInfo) perContext.index.get(Integer.valueOf(SignatureScheme.from(elementAt)));
                if (signatureSchemeInfo != null) {
                    arrayList.add(signatureSchemeInfo);
                }
            }
        }
        if (arrayList.isEmpty()) {
            return null;
        }
        arrayList.trimToSize();
        return Collections.unmodifiableList(arrayList);
    }

    private static boolean isECDSA(int i2) {
        if (i2 == 515 || i2 == historical_ecdsa_sha224 || i2 == 1027 || i2 == 1283 || i2 == 1539) {
            return true;
        }
        switch (i2) {
            case SignatureScheme.ecdsa_brainpoolP256r1tls13_sha256 /* 2074 */:
            case SignatureScheme.ecdsa_brainpoolP384r1tls13_sha384 /* 2075 */:
            case SignatureScheme.ecdsa_brainpoolP512r1tls13_sha512 /* 2076 */:
                return true;
            default:
                return false;
        }
    }

    private boolean isNamedGroupOK(boolean z2, boolean z3, NamedGroupInfo.PerConnection perConnection) {
        NamedGroupInfo namedGroupInfo = this.namedGroupInfo;
        if (namedGroupInfo != null) {
            return (z2 && NamedGroupInfo.hasLocal(perConnection, namedGroupInfo.getNamedGroup())) || (z3 && NamedGroupInfo.hasAnyECDSALocal(perConnection));
        }
        if (z2 || z3) {
            return !isECDSA(this.all.signatureScheme) || NamedGroupInfo.hasAnyECDSALocal(perConnection);
        }
        return false;
    }

    private boolean isPermittedBy(BCAlgorithmConstraints bCAlgorithmConstraints) {
        Set<BCCryptoPrimitive> set = JsseUtils.SIGNATURE_CRYPTO_PRIMITIVES_BC;
        return bCAlgorithmConstraints.permits(set, this.all.name, null) && bCAlgorithmConstraints.permits(set, this.all.keyAlgorithm, null) && bCAlgorithmConstraints.permits(set, this.all.jcaSignatureAlgorithm, this.algorithmParameters);
    }

    public short getHashAlgorithm() {
        return SignatureScheme.getHashAlgorithm(this.all.signatureScheme);
    }

    public String getJcaSignatureAlgorithm() {
        return this.all.jcaSignatureAlgorithm;
    }

    public String getJcaSignatureAlgorithmBC() {
        return this.all.jcaSignatureAlgorithmBC;
    }

    public String getKeyType() {
        return this.all.keyAlgorithm;
    }

    public String getKeyType13() {
        return this.all.keyType13;
    }

    public String getName() {
        return this.all.name;
    }

    public NamedGroupInfo getNamedGroupInfo() {
        return this.namedGroupInfo;
    }

    public short getSignatureAlgorithm() {
        return SignatureScheme.getSignatureAlgorithm(this.all.signatureScheme);
    }

    public SignatureAndHashAlgorithm getSignatureAndHashAlgorithm() {
        return getSignatureAndHashAlgorithm(this.all.signatureScheme);
    }

    public int getSignatureScheme() {
        return this.all.signatureScheme;
    }

    public boolean isActive(BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2, boolean z3, NamedGroupInfo.PerConnection perConnection) {
        if (this.enabled) {
            return isNamedGroupOK(z2 && isSupportedPost13(), z3 && isSupportedPre13(), perConnection) && isPermittedBy(bCAlgorithmConstraints);
        }
        return false;
    }

    public boolean isActiveCerts(BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2, boolean z3, NamedGroupInfo.PerConnection perConnection) {
        if (this.enabled) {
            return isNamedGroupOK(z2 && isSupportedCerts13(), z3 && isSupportedPre13(), perConnection) && isPermittedBy(bCAlgorithmConstraints);
        }
        return false;
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isSupportedCerts13() {
        return !this.disabled13 && this.all.supportedCerts13;
    }

    public boolean isSupportedPost13() {
        return !this.disabled13 && this.all.supportedPost13;
    }

    public boolean isSupportedPre13() {
        return this.all.supportedPre13;
    }

    public String toString() {
        return this.all.text;
    }

    public static SignatureAndHashAlgorithm getSignatureAndHashAlgorithm(int i2) {
        if (TlsUtils.isValidUint16(i2)) {
            return SignatureScheme.getSignatureAndHashAlgorithm(i2);
        }
        throw new IllegalArgumentException();
    }
}
