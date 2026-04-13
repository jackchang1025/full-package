package org.bouncycastle.jsse.provider;

import java.security.AlgorithmParameters;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.Iterator;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;
import java.util.Set;
import java.util.TreeMap;
import java.util.Vector;
import java.util.logging.Logger;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;
import org.bouncycastle.tls.NamedGroup;
import org.bouncycastle.tls.ProtocolVersion;
import org.bouncycastle.tls.TlsUtils;
import org.bouncycastle.tls.crypto.impl.jcajce.JcaTlsCrypto;
import org.bouncycastle.util.Arrays;
import org.bouncycastle.util.Integers;

/* loaded from: classes.dex */
class NamedGroupInfo {
    private static final String PROPERTY_NAMED_GROUPS = "jdk.tls.namedGroups";
    private final AlgorithmParameters algorithmParameters;
    private final All all;
    private final boolean enabled;
    private static final Logger LOG = Logger.getLogger(NamedGroupInfo.class.getName());
    private static final int[] CANDIDATES_DEFAULT = {29, 30, 23, 24, 25, 31, 32, 33, 256, 257, NamedGroup.ffdhe4096};

    public enum All {
        sect163k1(1, "EC"),
        sect163r1(2, "EC"),
        sect163r2(3, "EC"),
        sect193r1(4, "EC"),
        sect193r2(5, "EC"),
        sect233k1(6, "EC"),
        sect233r1(7, "EC"),
        sect239k1(8, "EC"),
        sect283k1(9, "EC"),
        sect283r1(10, "EC"),
        sect409k1(11, "EC"),
        sect409r1(12, "EC"),
        sect571k1(13, "EC"),
        sect571r1(14, "EC"),
        secp160k1(15, "EC"),
        secp160r1(16, "EC"),
        secp160r2(17, "EC"),
        secp192k1(18, "EC"),
        secp192r1(19, "EC"),
        secp224k1(20, "EC"),
        secp224r1(21, "EC"),
        secp256k1(22, "EC"),
        secp256r1(23, "EC"),
        secp384r1(24, "EC"),
        secp521r1(25, "EC"),
        brainpoolP256r1(26, "EC"),
        brainpoolP384r1(27, "EC"),
        brainpoolP512r1(28, "EC"),
        x25519(29, "XDH"),
        x448(30, "XDH"),
        brainpoolP256r1tls13(31, "EC"),
        brainpoolP384r1tls13(32, "EC"),
        brainpoolP512r1tls13(33, "EC"),
        curveSM2(41, "EC"),
        ffdhe2048(256, "DiffieHellman"),
        ffdhe3072(257, "DiffieHellman"),
        ffdhe4096(NamedGroup.ffdhe4096, "DiffieHellman"),
        ffdhe6144(NamedGroup.ffdhe6144, "DiffieHellman"),
        ffdhe8192(NamedGroup.ffdhe8192, "DiffieHellman");

        private final int bitsECDH;
        private final int bitsFFDHE;
        private final boolean char2;
        private final String jcaAlgorithm;
        private final String jcaGroup;
        private final String name;
        private final int namedGroup;
        private final boolean supportedPost13;
        private final boolean supportedPre13;
        private final String text;

        All(int i2, String str) {
            this.namedGroup = i2;
            this.name = NamedGroup.getName(i2);
            this.text = NamedGroup.getText(i2);
            this.jcaAlgorithm = str;
            this.jcaGroup = NamedGroup.getStandardName(i2);
            this.supportedPost13 = NamedGroup.canBeNegotiated(i2, ProtocolVersion.TLSv13);
            this.supportedPre13 = NamedGroup.canBeNegotiated(i2, ProtocolVersion.TLSv12);
            this.char2 = NamedGroup.isChar2Curve(i2);
            this.bitsECDH = NamedGroup.getCurveBits(i2);
            this.bitsFFDHE = NamedGroup.getFiniteFieldBits(i2);
        }
    }

    public static class PerConnection {
        private final Map<Integer, NamedGroupInfo> local;
        private final boolean localECDSA;
        private List<NamedGroupInfo> peer = null;

        public PerConnection(Map<Integer, NamedGroupInfo> map, boolean z2) {
            this.local = map;
            this.localECDSA = z2;
        }

        /* JADX INFO: Access modifiers changed from: private */
        public synchronized void setPeer(List<NamedGroupInfo> list) {
            this.peer = list;
        }

        public synchronized List<NamedGroupInfo> getPeer() {
            return this.peer;
        }
    }

    public static class PerContext {
        private final int[] candidates;
        private final Map<Integer, NamedGroupInfo> index;

        public PerContext(Map<Integer, NamedGroupInfo> map, int[] iArr) {
            this.index = map;
            this.candidates = iArr;
        }
    }

    public NamedGroupInfo(All all, AlgorithmParameters algorithmParameters, boolean z2) {
        this.all = all;
        this.algorithmParameters = algorithmParameters;
        this.enabled = z2;
    }

    private static void addNamedGroup(boolean z2, JcaTlsCrypto jcaTlsCrypto, boolean z3, boolean z4, Map<Integer, NamedGroupInfo> map, All all) {
        int i2 = all.namedGroup;
        if (!z2 || FipsUtils.isFipsNamedGroup(i2)) {
            boolean z5 = false;
            boolean z6 = (((z3 && all.char2) || (z4 && all.bitsFFDHE > 0)) || all.jcaGroup == null || !jcaTlsCrypto.hasNamedGroup(i2)) ? false : true;
            AlgorithmParameters algorithmParameters = null;
            if (z6) {
                try {
                    algorithmParameters = jcaTlsCrypto.getNamedGroupAlgorithmParameters(i2);
                } catch (Exception unused) {
                }
            }
            z5 = z6;
            if (map.put(Integer.valueOf(i2), new NamedGroupInfo(all, algorithmParameters, z5)) != null) {
                throw new IllegalStateException("Duplicate entries for NamedGroupInfo");
            }
        }
    }

    private static int[] createCandidates(Map<Integer, NamedGroupInfo> map) {
        Logger logger;
        StringBuilder sb;
        String[] stringArraySystemProperty = PropertyUtils.getStringArraySystemProperty(PROPERTY_NAMED_GROUPS);
        if (stringArraySystemProperty == null) {
            return CANDIDATES_DEFAULT;
        }
        int length = stringArraySystemProperty.length;
        int[] iArr = new int[length];
        int i2 = 0;
        for (String str : stringArraySystemProperty) {
            int namedGroupByName = getNamedGroupByName(str);
            if (namedGroupByName < 0) {
                logger = LOG;
                sb = new StringBuilder("'jdk.tls.namedGroups' contains unrecognised NamedGroup: ");
            } else {
                NamedGroupInfo namedGroupInfo = map.get(Integer.valueOf(namedGroupByName));
                if (namedGroupInfo == null) {
                    logger = LOG;
                    sb = new StringBuilder("'jdk.tls.namedGroups' contains unsupported NamedGroup: ");
                } else if (namedGroupInfo.isEnabled()) {
                    iArr[i2] = namedGroupByName;
                    i2++;
                } else {
                    logger = LOG;
                    sb = new StringBuilder("'jdk.tls.namedGroups' contains disabled NamedGroup: ");
                }
            }
            sb.append(str);
            logger.warning(sb.toString());
        }
        if (i2 < length) {
            iArr = Arrays.copyOf(iArr, i2);
        }
        if (iArr.length < 1) {
            LOG.severe("'jdk.tls.namedGroups' contained no usable NamedGroup values");
        }
        return iArr;
    }

    private static Map<Integer, NamedGroupInfo> createIndex(boolean z2, JcaTlsCrypto jcaTlsCrypto) {
        TreeMap treeMap = new TreeMap();
        boolean z3 = PropertyUtils.getBooleanSystemProperty("org.bouncycastle.jsse.ec.disableChar2", false) || PropertyUtils.getBooleanSystemProperty("org.bouncycastle.ec.disable_f2m", false);
        boolean z4 = !PropertyUtils.getBooleanSystemProperty("jsse.enableFFDHE", true);
        for (All all : All.values()) {
            addNamedGroup(z2, jcaTlsCrypto, z3, z4, treeMap, all);
        }
        return treeMap;
    }

    private static Map<Integer, NamedGroupInfo> createLocal(PerContext perContext, ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr) {
        ProtocolVersion latestTLS = ProtocolVersion.getLatestTLS(protocolVersionArr);
        ProtocolVersion earliestTLS = ProtocolVersion.getEarliestTLS(protocolVersionArr);
        BCAlgorithmConstraints algorithmConstraints = provSSLParameters.getAlgorithmConstraints();
        boolean isTLSv13 = TlsUtils.isTLSv13(latestTLS);
        boolean z2 = !TlsUtils.isTLSv13(earliestTLS);
        int length = perContext.candidates.length;
        LinkedHashMap linkedHashMap = new LinkedHashMap(length);
        for (int i2 = 0; i2 < length; i2++) {
            Integer valueOf = Integers.valueOf(perContext.candidates[i2]);
            NamedGroupInfo namedGroupInfo = (NamedGroupInfo) perContext.index.get(valueOf);
            if (namedGroupInfo != null && namedGroupInfo.isActive(algorithmConstraints, isTLSv13, z2)) {
                linkedHashMap.put(valueOf, namedGroupInfo);
            }
        }
        return linkedHashMap;
    }

    private static boolean createLocalECDSA(Map<Integer, NamedGroupInfo> map) {
        Iterator<NamedGroupInfo> it = map.values().iterator();
        while (it.hasNext()) {
            if (NamedGroup.refersToAnECDSACurve(it.next().getNamedGroup())) {
                return true;
            }
        }
        return false;
    }

    private static List<NamedGroupInfo> createPeer(PerConnection perConnection, int[] iArr) {
        return getNamedGroupInfos(perConnection.local, iArr);
    }

    public static PerConnection createPerConnection(PerContext perContext, ProvSSLParameters provSSLParameters, ProtocolVersion[] protocolVersionArr) {
        Map<Integer, NamedGroupInfo> createLocal = createLocal(perContext, provSSLParameters, protocolVersionArr);
        return new PerConnection(createLocal, createLocalECDSA(createLocal));
    }

    public static PerContext createPerContext(boolean z2, JcaTlsCrypto jcaTlsCrypto) {
        Map<Integer, NamedGroupInfo> createIndex = createIndex(z2, jcaTlsCrypto);
        return new PerContext(createIndex, createCandidates(createIndex));
    }

    private static Collection<NamedGroupInfo> getEffectivePeer(PerConnection perConnection) {
        List<NamedGroupInfo> peer = perConnection.getPeer();
        return !peer.isEmpty() ? peer : perConnection.local.values();
    }

    public static int getMaximumBitsServerECDH(PerConnection perConnection) {
        Iterator<NamedGroupInfo> it = getEffectivePeer(perConnection).iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 = Math.max(i2, it.next().getBitsECDH());
        }
        return i2;
    }

    public static int getMaximumBitsServerFFDHE(PerConnection perConnection) {
        Iterator<NamedGroupInfo> it = getEffectivePeer(perConnection).iterator();
        int i2 = 0;
        while (it.hasNext()) {
            i2 = Math.max(i2, it.next().getBitsFFDHE());
        }
        return i2;
    }

    private static int getNamedGroupByName(String str) {
        for (All all : All.values()) {
            if (all.name.equalsIgnoreCase(str)) {
                return all.namedGroup;
            }
        }
        return -1;
    }

    private static List<NamedGroupInfo> getNamedGroupInfos(Map<Integer, NamedGroupInfo> map, int[] iArr) {
        if (TlsUtils.isNullOrEmpty(iArr)) {
            return Collections.emptyList();
        }
        ArrayList arrayList = new ArrayList(iArr.length);
        for (int i2 : iArr) {
            NamedGroupInfo namedGroupInfo = map.get(Integer.valueOf(i2));
            if (namedGroupInfo != null) {
                arrayList.add(namedGroupInfo);
            }
        }
        if (arrayList.isEmpty()) {
            return Collections.emptyList();
        }
        arrayList.trimToSize();
        return arrayList;
    }

    public static Vector<Integer> getSupportedGroupsLocalClient(PerConnection perConnection) {
        return new Vector<>(perConnection.local.keySet());
    }

    public static int[] getSupportedGroupsLocalServer(PerConnection perConnection) {
        Set keySet = perConnection.local.keySet();
        int[] iArr = new int[keySet.size()];
        Iterator it = keySet.iterator();
        int i2 = 0;
        while (it.hasNext()) {
            iArr[i2] = ((Integer) it.next()).intValue();
            i2++;
        }
        return iArr;
    }

    public static boolean hasAnyECDSALocal(PerConnection perConnection) {
        return perConnection.localECDSA;
    }

    public static boolean hasLocal(PerConnection perConnection, int i2) {
        return perConnection.local.containsKey(Integer.valueOf(i2));
    }

    private boolean isPermittedBy(BCAlgorithmConstraints bCAlgorithmConstraints) {
        Set<BCCryptoPrimitive> set = JsseUtils.KEY_AGREEMENT_CRYPTO_PRIMITIVES_BC;
        return bCAlgorithmConstraints.permits(set, getJcaGroup(), null) && bCAlgorithmConstraints.permits(set, getJcaAlgorithm(), this.algorithmParameters);
    }

    public static void notifyPeer(PerConnection perConnection, int[] iArr) {
        perConnection.setPeer(createPeer(perConnection, iArr));
    }

    public static int selectServerECDH(PerConnection perConnection, int i2) {
        for (NamedGroupInfo namedGroupInfo : getEffectivePeer(perConnection)) {
            if (namedGroupInfo.getBitsECDH() >= i2) {
                return namedGroupInfo.getNamedGroup();
            }
        }
        return -1;
    }

    public static int selectServerFFDHE(PerConnection perConnection, int i2) {
        for (NamedGroupInfo namedGroupInfo : getEffectivePeer(perConnection)) {
            if (namedGroupInfo.getBitsFFDHE() >= i2) {
                return namedGroupInfo.getNamedGroup();
            }
        }
        return -1;
    }

    public int getBitsECDH() {
        return this.all.bitsECDH;
    }

    public int getBitsFFDHE() {
        return this.all.bitsFFDHE;
    }

    public String getJcaAlgorithm() {
        return this.all.jcaAlgorithm;
    }

    public String getJcaGroup() {
        return this.all.jcaGroup;
    }

    public int getNamedGroup() {
        return this.all.namedGroup;
    }

    public boolean isActive(BCAlgorithmConstraints bCAlgorithmConstraints, boolean z2, boolean z3) {
        return this.enabled && ((z2 && isSupportedPost13()) || (z3 && isSupportedPre13())) && isPermittedBy(bCAlgorithmConstraints);
    }

    public boolean isEnabled() {
        return this.enabled;
    }

    public boolean isSupportedPost13() {
        return this.all.supportedPost13;
    }

    public boolean isSupportedPre13() {
        return this.all.supportedPre13;
    }

    public String toString() {
        return this.all.text;
    }

    public static NamedGroupInfo getNamedGroup(PerContext perContext, int i2) {
        return (NamedGroupInfo) perContext.index.get(Integer.valueOf(i2));
    }
}
