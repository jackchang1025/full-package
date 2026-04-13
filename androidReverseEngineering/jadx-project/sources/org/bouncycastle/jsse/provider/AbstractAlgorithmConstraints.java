package org.bouncycastle.jsse.provider;

import java.security.Key;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.Set;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.jsse.java.security.BCCryptoPrimitive;

/* loaded from: classes.dex */
abstract class AbstractAlgorithmConstraints implements BCAlgorithmConstraints {
    protected final AlgorithmDecomposer decomposer;

    public AbstractAlgorithmConstraints(AlgorithmDecomposer algorithmDecomposer) {
        this.decomposer = algorithmDecomposer;
    }

    public static Set<String> asSet(String[] strArr) {
        HashSet hashSet = new HashSet();
        if (strArr != null) {
            for (String str : strArr) {
                if (str != null) {
                    hashSet.add(str);
                }
            }
        }
        return hashSet;
    }

    public static Set<String> asUnmodifiableSet(String[] strArr) {
        if (strArr != null && strArr.length > 0) {
            Set<String> asSet = asSet(strArr);
            if (!asSet.isEmpty()) {
                return Collections.unmodifiableSet(asSet);
            }
        }
        return Collections.emptySet();
    }

    public void checkAlgorithmName(String str) {
        if (!JsseUtils.isNameSpecified(str)) {
            throw new IllegalArgumentException("No algorithm name specified");
        }
    }

    public void checkKey(Key key) {
        if (key == null) {
            throw new NullPointerException("'key' cannot be null");
        }
    }

    public void checkPrimitives(Set<BCCryptoPrimitive> set) {
        if (!isPrimitivesSpecified(set)) {
            throw new IllegalArgumentException("No cryptographic primitive specified");
        }
    }

    public boolean containsAnyPartIgnoreCase(Set<String> set, String str) {
        if (set.isEmpty()) {
            return false;
        }
        if (containsIgnoreCase(set, str)) {
            return true;
        }
        AlgorithmDecomposer algorithmDecomposer = this.decomposer;
        if (algorithmDecomposer != null) {
            Iterator<String> it = algorithmDecomposer.decompose(str).iterator();
            while (it.hasNext()) {
                if (containsIgnoreCase(set, it.next())) {
                    return true;
                }
            }
        }
        return false;
    }

    public boolean containsIgnoreCase(Set<String> set, String str) {
        Iterator<String> it = set.iterator();
        while (it.hasNext()) {
            if (it.next().equalsIgnoreCase(str)) {
                return true;
            }
        }
        return false;
    }

    public boolean isPrimitivesSpecified(Set<BCCryptoPrimitive> set) {
        return (set == null || set.isEmpty()) ? false : true;
    }
}
