package org.bouncycastle.jsse;

import java.net.Socket;
import java.security.Principal;
import javax.net.ssl.SSLEngine;
import javax.net.ssl.X509ExtendedKeyManager;

/* loaded from: classes.dex */
public abstract class BCX509ExtendedKeyManager extends X509ExtendedKeyManager {
    public BCX509Key chooseClientKeyBC(String[] strArr, Principal[] principalArr, Socket socket) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            String chooseClientAlias = chooseClientAlias(new String[]{str}, principalArr, socket);
            if (chooseClientAlias != null) {
                return getKeyBC(str, chooseClientAlias);
            }
        }
        return null;
    }

    public BCX509Key chooseEngineClientKeyBC(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            String chooseEngineClientAlias = chooseEngineClientAlias(new String[]{str}, principalArr, sSLEngine);
            if (chooseEngineClientAlias != null) {
                return getKeyBC(str, chooseEngineClientAlias);
            }
        }
        return null;
    }

    public BCX509Key chooseEngineServerKeyBC(String[] strArr, Principal[] principalArr, SSLEngine sSLEngine) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            String chooseEngineServerAlias = chooseEngineServerAlias(str, principalArr, sSLEngine);
            if (chooseEngineServerAlias != null) {
                return getKeyBC(str, chooseEngineServerAlias);
            }
        }
        return null;
    }

    public BCX509Key chooseServerKeyBC(String[] strArr, Principal[] principalArr, Socket socket) {
        if (strArr == null) {
            return null;
        }
        for (String str : strArr) {
            String chooseServerAlias = chooseServerAlias(str, principalArr, socket);
            if (chooseServerAlias != null) {
                return getKeyBC(str, chooseServerAlias);
            }
        }
        return null;
    }

    public abstract BCX509Key getKeyBC(String str, String str2);
}
