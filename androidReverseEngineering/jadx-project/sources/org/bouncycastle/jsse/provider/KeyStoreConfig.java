package org.bouncycastle.jsse.provider;

import java.security.KeyStore;

/* loaded from: classes.dex */
class KeyStoreConfig {
    final KeyStore keyStore;
    final char[] password;

    public KeyStoreConfig(KeyStore keyStore, char[] cArr) {
        this.keyStore = keyStore;
        this.password = cArr;
    }
}
