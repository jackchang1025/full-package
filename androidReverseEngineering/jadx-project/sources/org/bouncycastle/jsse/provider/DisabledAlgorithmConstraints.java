package org.bouncycastle.jsse.provider;

import java.math.BigInteger;
import java.security.AlgorithmParameters;
import java.security.Key;
import java.security.interfaces.DSAKey;
import java.security.interfaces.DSAParams;
import java.security.interfaces.ECKey;
import java.security.interfaces.RSAKey;
import java.security.spec.ECParameterSpec;
import java.security.spec.InvalidParameterSpecException;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Locale;
import java.util.Map;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Logger;
import javax.crypto.SecretKey;
import javax.crypto.interfaces.DHKey;
import javax.crypto.spec.DHParameterSpec;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
class DisabledAlgorithmConstraints extends AbstractAlgorithmConstraints {
    private static final String INCLUDE_PREFIX = "include ";
    private static final String KEYWORD_KEYSIZE = "keySize";
    private static final Logger LOG = Logger.getLogger(DisabledAlgorithmConstraints.class.getName());
    private final Map<String, List<Constraint>> constraintsMap;
    private final Set<String> disabledAlgorithms;

    /* renamed from: org.bouncycastle.jsse.provider.DisabledAlgorithmConstraints$1 */
    public static /* synthetic */ class C06751 {

        /* renamed from: $SwitchMap$org$bouncycastle$jsse$provider$DisabledAlgorithmConstraints$BinOp */
        static final /* synthetic */ int[] f1406x815f1581;

        static {
            int[] iArr = new int[BinOp.values().length];
            f1406x815f1581 = iArr;
            try {
                iArr[BinOp.EQ.ordinal()] = 1;
            } catch (NoSuchFieldError unused) {
            }
            try {
                f1406x815f1581[BinOp.GE.ordinal()] = 2;
            } catch (NoSuchFieldError unused2) {
            }
            try {
                f1406x815f1581[BinOp.GT.ordinal()] = 3;
            } catch (NoSuchFieldError unused3) {
            }
            try {
                f1406x815f1581[BinOp.LE.ordinal()] = 4;
            } catch (NoSuchFieldError unused4) {
            }
            try {
                f1406x815f1581[BinOp.LT.ordinal()] = 5;
            } catch (NoSuchFieldError unused5) {
            }
            try {
                f1406x815f1581[BinOp.NE.ordinal()] = 6;
            } catch (NoSuchFieldError unused6) {
            }
        }
    }

    public enum BinOp {
        EQ("=="),
        GE(">="),
        GT(">"),
        LE("<="),
        LT("<"),
        NE("!=");


        /* renamed from: s */
        private final String f1407s;

        BinOp(String str) {
            this.f1407s = str;
        }

        public static boolean eval(BinOp binOp, int i2, int i3) {
            switch (C06751.f1406x815f1581[binOp.ordinal()]) {
                case 1:
                    if (i2 == i3) {
                        break;
                    }
                    break;
                case 2:
                    if (i2 >= i3) {
                        break;
                    }
                    break;
                case 3:
                    if (i2 > i3) {
                        break;
                    }
                    break;
                case 4:
                    if (i2 <= i3) {
                        break;
                    }
                    break;
                case 5:
                    if (i2 < i3) {
                        break;
                    }
                    break;
                case 6:
                    if (i2 != i3) {
                        break;
                    }
                    break;
            }
            return true;
        }

        public static BinOp parse(String str) {
            for (BinOp binOp : values()) {
                if (binOp.f1407s.equals(str)) {
                    return binOp;
                }
            }
            throw new IllegalArgumentException(AbstractC0000a.m15k("'s' is not a valid operator: ", str));
        }
    }

    public static abstract class Constraint {
        private Constraint() {
        }

        public /* synthetic */ Constraint(C06751 c06751) {
            this();
        }

        public boolean permits(AlgorithmParameters algorithmParameters) {
            return true;
        }

        public boolean permits(Key key) {
            return true;
        }
    }

    public static class DisabledConstraint extends Constraint {
        static final DisabledConstraint INSTANCE = new DisabledConstraint();

        private DisabledConstraint() {
            super(null);
        }

        @Override // org.bouncycastle.jsse.provider.DisabledAlgorithmConstraints.Constraint
        public boolean permits(Key key) {
            return false;
        }
    }

    public static class KeySizeConstraint extends Constraint {
        private final int constraint;
        private final BinOp op;

        public KeySizeConstraint(BinOp binOp, int i2) {
            super(null);
            this.op = binOp;
            this.constraint = i2;
        }

        private boolean checkKeySize(int i2) {
            return i2 < 1 ? i2 < 0 : !BinOp.eval(this.op, i2, this.constraint);
        }

        private static int getKeySize(AlgorithmParameters algorithmParameters) {
            DHParameterSpec dHParameterSpec;
            String algorithm = algorithmParameters.getAlgorithm();
            try {
                if ("EC".equals(algorithm)) {
                    ECParameterSpec eCParameterSpec = (ECParameterSpec) algorithmParameters.getParameterSpec(ECParameterSpec.class);
                    if (eCParameterSpec != null) {
                        return eCParameterSpec.getOrder().bitLength();
                    }
                    return -1;
                }
                if (!"DiffieHellman".equals(algorithm) || (dHParameterSpec = (DHParameterSpec) algorithmParameters.getParameterSpec(DHParameterSpec.class)) == null) {
                    return -1;
                }
                return dHParameterSpec.getP().bitLength();
            } catch (InvalidParameterSpecException unused) {
                return -1;
            }
        }

        @Override // org.bouncycastle.jsse.provider.DisabledAlgorithmConstraints.Constraint
        public boolean permits(AlgorithmParameters algorithmParameters) {
            return checkKeySize(getKeySize(algorithmParameters));
        }

        private static int getKeySize(Key key) {
            byte[] encoded;
            BigInteger p2;
            if (key instanceof RSAKey) {
                p2 = ((RSAKey) key).getModulus();
            } else if (key instanceof ECKey) {
                p2 = ((ECKey) key).getParams().getOrder();
            } else if (key instanceof DSAKey) {
                DSAParams params = ((DSAKey) key).getParams();
                if (params == null) {
                    return -1;
                }
                p2 = params.getP();
            } else {
                if (!(key instanceof DHKey)) {
                    if (!(key instanceof SecretKey)) {
                        return -1;
                    }
                    SecretKey secretKey = (SecretKey) key;
                    if (!"RAW".equals(secretKey.getFormat()) || (encoded = secretKey.getEncoded()) == null) {
                        return -1;
                    }
                    if (encoded.length > 268435455) {
                        return 0;
                    }
                    return encoded.length * 8;
                }
                p2 = ((DHKey) key).getParams().getP();
            }
            return p2.bitLength();
        }

        @Override // org.bouncycastle.jsse.provider.DisabledAlgorithmConstraints.Constraint
        public boolean permits(Key key) {
            return checkKeySize(getKeySize(key));
        }
    }

    private DisabledAlgorithmConstraints(AlgorithmDecomposer algorithmDecomposer, Set<String> set, Map<String, List<Constraint>> map) {
        super(algorithmDecomposer);
        this.disabledAlgorithms = set;
        this.constraintsMap = map;
    }

    private static void addConstraint(Map<String, List<Constraint>> map, String str, Constraint constraint) {
        List<Constraint> list = map.get(str);
        if (list == null) {
            list = new ArrayList<>(1);
            map.put(str, list);
        }
        list.add(constraint);
    }

    private boolean checkConstraints(Set<BCCryptoPrimitive> set, String str, Key key, AlgorithmParameters algorithmParameters) {
        checkPrimitives(set);
        checkKey(key);
        if ((JsseUtils.isNameSpecified(str) && !permits(set, str, algorithmParameters)) || !permits(set, JsseUtils.getKeyAlgorithm(key), null)) {
            return false;
        }
        Iterator<Constraint> it = getConstraints(getConstraintsAlgorithm(key)).iterator();
        while (it.hasNext()) {
            if (!it.next().permits(key)) {
                return false;
            }
        }
        return true;
    }

    public static DisabledAlgorithmConstraints create(AlgorithmDecomposer algorithmDecomposer, String str, String str2) {
        String[] stringArraySecurityProperty = PropertyUtils.getStringArraySecurityProperty(str, str2);
        if (stringArraySecurityProperty == null) {
            return null;
        }
        HashSet hashSet = new HashSet();
        HashMap hashMap = new HashMap();
        for (int i2 = 0; i2 < stringArraySecurityProperty.length; i2++) {
            if (!addConstraint(hashSet, hashMap, stringArraySecurityProperty[i2])) {
                Logger logger = LOG;
                StringBuilder m23s = AbstractC0000a.m23s("Ignoring unsupported entry in '", str, "': ");
                m23s.append(stringArraySecurityProperty[i2]);
                logger.warning(m23s.toString());
            }
        }
        return new DisabledAlgorithmConstraints(algorithmDecomposer, Collections.unmodifiableSet(hashSet), Collections.unmodifiableMap(hashMap));
    }

    private static String getCanonicalAlgorithm(String str) {
        return "DiffieHellman".equalsIgnoreCase(str) ? "DH" : str.toUpperCase(Locale.ENGLISH).replace("SHA-", "SHA");
    }

    private List<Constraint> getConstraints(String str) {
        List<Constraint> list;
        return (str == null || (list = this.constraintsMap.get(str)) == null) ? Collections.emptyList() : list;
    }

    private static String getConstraintsAlgorithm(String str, AlgorithmParameters algorithmParameters) {
        String algorithm;
        if (algorithmParameters == null || (algorithm = algorithmParameters.getAlgorithm()) == null) {
            return null;
        }
        String canonicalAlgorithm = getCanonicalAlgorithm(str);
        if (canonicalAlgorithm.equalsIgnoreCase(getCanonicalAlgorithm(algorithm))) {
            return canonicalAlgorithm;
        }
        return null;
    }

    @Override // org.bouncycastle.jsse.java.security.BCAlgorithmConstraints
    public final boolean permits(Set<BCCryptoPrimitive> set, String str, AlgorithmParameters algorithmParameters) {
        checkPrimitives(set);
        checkAlgorithmName(str);
        if (containsAnyPartIgnoreCase(this.disabledAlgorithms, str)) {
            return false;
        }
        Iterator<Constraint> it = getConstraints(getConstraintsAlgorithm(str, algorithmParameters)).iterator();
        while (it.hasNext()) {
            if (!it.next().permits(algorithmParameters)) {
                return false;
            }
        }
        return true;
    }

    private static boolean addConstraint(Set<String> set, Map<String, List<Constraint>> map, String str) {
        if (str.regionMatches(true, 0, INCLUDE_PREFIX, 0, 8)) {
            return false;
        }
        int indexOf = str.indexOf(32);
        if (indexOf < 0) {
            String canonicalAlgorithm = getCanonicalAlgorithm(str);
            set.add(canonicalAlgorithm);
            addConstraint(map, canonicalAlgorithm, DisabledConstraint.INSTANCE);
            return true;
        }
        String canonicalAlgorithm2 = getCanonicalAlgorithm(str.substring(0, indexOf));
        String trim = str.substring(indexOf + 1).trim();
        if (trim.indexOf(38) >= 0 || !trim.startsWith(KEYWORD_KEYSIZE)) {
            return false;
        }
        StringTokenizer stringTokenizer = new StringTokenizer(trim);
        if (!KEYWORD_KEYSIZE.equals(stringTokenizer.nextToken())) {
            return false;
        }
        BinOp parse = BinOp.parse(stringTokenizer.nextToken());
        int parseInt = Integer.parseInt(stringTokenizer.nextToken());
        if (stringTokenizer.hasMoreTokens()) {
            return false;
        }
        addConstraint(map, canonicalAlgorithm2, new KeySizeConstraint(parse, parseInt));
        return true;
    }

    private static String getConstraintsAlgorithm(Key key) {
        String keyAlgorithm;
        if (key == null || (keyAlgorithm = JsseUtils.getKeyAlgorithm(key)) == null) {
            return null;
        }
        return getCanonicalAlgorithm(keyAlgorithm);
    }

    @Override // org.bouncycastle.jsse.java.security.BCAlgorithmConstraints
    public final boolean permits(Set<BCCryptoPrimitive> set, String str, Key key, AlgorithmParameters algorithmParameters) {
        checkAlgorithmName(str);
        return checkConstraints(set, str, key, algorithmParameters);
    }

    @Override // org.bouncycastle.jsse.java.security.BCAlgorithmConstraints
    public final boolean permits(Set<BCCryptoPrimitive> set, Key key) {
        return checkConstraints(set, null, key, null);
    }
}
