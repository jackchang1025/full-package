package org.bouncycastle.jsse;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import org.bouncycastle.jsse.java.security.BCAlgorithmConstraints;
import org.bouncycastle.tls.TlsUtils;
import p000a.AbstractC0000a;

/* loaded from: classes.dex */
public final class BCSSLParameters {
    private BCAlgorithmConstraints algorithmConstraints;
    private String[] applicationProtocols = TlsUtils.EMPTY_STRINGS;
    private String[] cipherSuites;
    private String endpointIdentificationAlgorithm;
    private boolean needClientAuth;
    private String[] protocols;
    private List<BCSNIServerName> serverNames;
    private List<BCSNIMatcher> sniMatchers;
    private boolean useCipherSuitesOrder;
    private boolean wantClientAuth;

    public BCSSLParameters() {
    }

    private static <T> List<T> copyList(Collection<T> collection) {
        if (collection == null) {
            return null;
        }
        return collection.isEmpty() ? Collections.emptyList() : Collections.unmodifiableList(new ArrayList(collection));
    }

    public BCAlgorithmConstraints getAlgorithmConstraints() {
        return this.algorithmConstraints;
    }

    public String[] getApplicationProtocols() {
        return TlsUtils.clone(this.applicationProtocols);
    }

    public String[] getCipherSuites() {
        return TlsUtils.clone(this.cipherSuites);
    }

    public String getEndpointIdentificationAlgorithm() {
        return this.endpointIdentificationAlgorithm;
    }

    public boolean getNeedClientAuth() {
        return this.needClientAuth;
    }

    public String[] getProtocols() {
        return TlsUtils.clone(this.protocols);
    }

    public Collection<BCSNIMatcher> getSNIMatchers() {
        return copyList(this.sniMatchers);
    }

    public List<BCSNIServerName> getServerNames() {
        return copyList(this.serverNames);
    }

    public boolean getUseCipherSuitesOrder() {
        return this.useCipherSuitesOrder;
    }

    public boolean getWantClientAuth() {
        return this.wantClientAuth;
    }

    public void setAlgorithmConstraints(BCAlgorithmConstraints bCAlgorithmConstraints) {
        this.algorithmConstraints = bCAlgorithmConstraints;
    }

    public void setApplicationProtocols(String[] strArr) {
        if (strArr == null) {
            throw new NullPointerException("'applicationProtocols' cannot be null");
        }
        String[] clone = TlsUtils.clone(strArr);
        for (String str : clone) {
            if (TlsUtils.isNullOrEmpty(str)) {
                throw new IllegalArgumentException("'applicationProtocols' entries cannot be null or empty strings");
            }
        }
        this.applicationProtocols = clone;
    }

    public void setCipherSuites(String[] strArr) {
        this.cipherSuites = TlsUtils.clone(strArr);
    }

    public void setEndpointIdentificationAlgorithm(String str) {
        this.endpointIdentificationAlgorithm = str;
    }

    public void setNeedClientAuth(boolean z2) {
        this.needClientAuth = z2;
        this.wantClientAuth = false;
    }

    public void setProtocols(String[] strArr) {
        this.protocols = TlsUtils.clone(strArr);
    }

    public void setSNIMatchers(Collection<BCSNIMatcher> collection) {
        List<BCSNIMatcher> copyList;
        if (collection == null) {
            copyList = null;
        } else {
            copyList = copyList(collection);
            HashSet hashSet = new HashSet();
            Iterator<BCSNIMatcher> it = copyList.iterator();
            while (it.hasNext()) {
                int type = it.next().getType();
                if (!hashSet.add(Integer.valueOf(type))) {
                    throw new IllegalArgumentException(AbstractC0000a.m11g("Found duplicate SNI matcher entry of type ", type));
                }
            }
        }
        this.sniMatchers = copyList;
    }

    public void setServerNames(List<BCSNIServerName> list) {
        List<BCSNIServerName> copyList;
        if (list == null) {
            copyList = null;
        } else {
            copyList = copyList(list);
            HashSet hashSet = new HashSet();
            Iterator<BCSNIServerName> it = copyList.iterator();
            while (it.hasNext()) {
                int type = it.next().getType();
                if (!hashSet.add(Integer.valueOf(type))) {
                    throw new IllegalArgumentException(AbstractC0000a.m11g("Found duplicate SNI server name entry of type ", type));
                }
            }
        }
        this.serverNames = copyList;
    }

    public void setUseCipherSuitesOrder(boolean z2) {
        this.useCipherSuitesOrder = z2;
    }

    public void setWantClientAuth(boolean z2) {
        this.wantClientAuth = z2;
        this.needClientAuth = false;
    }

    public BCSSLParameters(String[] strArr) {
        setCipherSuites(strArr);
    }

    public BCSSLParameters(String[] strArr, String[] strArr2) {
        setCipherSuites(strArr);
        setProtocols(strArr2);
    }
}
